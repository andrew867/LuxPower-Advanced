"""
Serial transport implementation for LuxPower integration.

This module implements direct USB serial/RS485 communication for the LuxPower
integration, using pyserial-asyncio for async serial communication and
implementing Modbus RTU protocol directly.
"""

import asyncio
import logging
from typing import Optional, Dict

try:
    import serial_asyncio
except ImportError:
    serial_asyncio = None

from . import LuxPowerTransport

_LOGGER = logging.getLogger(__name__)


class SerialTransport(LuxPowerTransport):
    """
    Serial transport implementation for LuxPower communication.
    
    This class provides direct USB serial/RS485 communication using
    pyserial-asyncio and implements Modbus RTU protocol directly.
    """

    def __init__(self, port: str, baudrate: int = 19200, dongle_serial: str = ""):
        """
        Initialize Serial transport.
        
        Args:
            port: Serial port path (e.g., "/dev/ttyUSB0" or "COM3")
            baudrate: Serial baud rate (default: 19200)
            dongle_serial: Dongle serial number
        """
        super().__init__(dongle_serial)
        self.port = port
        self.baudrate = baudrate
        self._reader: Optional[asyncio.StreamReader] = None
        self._writer: Optional[asyncio.StreamWriter] = None
        self._listen_task: Optional[asyncio.Task] = None
        self._buffer = bytearray()
        self._crc_fail_count = 0
        self._short_frame_count = 0
        self._packets_ok = 0
        self._last_request_start: Optional[int] = None
        self._last_request_count: Optional[int] = None
        self._last_request_type: Optional[str] = None  # "input" or "hold" or "write"
        self._client = None

    async def connect(self) -> bool:
        """
        Establish serial connection.
        
        Returns:
            True if connection successful, False otherwise
        """
        if serial_asyncio is None:
            self._logger.error("pyserial-asyncio not available. Install with: pip install pyserial-asyncio")
            return False
        
        try:
            self._logger.info(f"Connecting to serial port {self.port} at {self.baudrate} baud")
            
            # Create serial connection
            self._reader, self._writer = await serial_asyncio.open_serial_connection(
                url=self.port,
                baudrate=self.baudrate,
                bytesize=8,
                parity='N',
                stopbits=1,
                xonxoff=False,
                rtscts=False,
                dsrdtr=False
            )
            
            self._connected = True
            self._logger.info(f"Connected to serial port {self.port}")
            
            # Start listening task
            self._listen_task = asyncio.create_task(self._listen_loop())
            
            return True
            
        except Exception as e:
            self._logger.error(f"Failed to connect to serial port {self.port}: {e}")
            self._connected = False
            return False

    async def disconnect(self) -> None:
        """Disconnect from serial port."""
        self._logger.info(f"Disconnecting from serial port {self.port}")
        
        # Stop listening task
        if self._listen_task and not self._listen_task.done():
            self._listen_task.cancel()
            try:
                await self._listen_task
            except asyncio.CancelledError:
                pass
        
        # Close writer
        if self._writer:
            self._writer.close()
            try:
                await self._writer.wait_closed()
            except Exception as e:
                self._logger.warning(f"Error closing serial writer: {e}")
        
        self._connected = False
        self._reader = None
        self._writer = None
        self._buffer.clear()

    async def send_packet(self, packet: bytes) -> bool:
        """
        Send packet through serial connection.
        
        Args:
            packet: Packet data to send
            
        Returns:
            True if send successful, False otherwise
        """
        if not self._connected or not self._writer:
            self._logger.error("Cannot send packet: not connected")
            return False
        
        try:
            # Convert LuxPower packet to Modbus RTU format
            modbus_packet, meta = self._luxpower_to_modbus_rtu(packet)
            if not modbus_packet:
                self._logger.error("Serial send aborted: unable to parse LuxPower packet into Modbus RTU")
                return False

            # Track last request metadata to interpret responses
            self._last_request_start = meta.get("start")
            self._last_request_count = meta.get("count")
            self._last_request_type = meta.get("type")
            
            self._writer.write(modbus_packet)
            await self._writer.drain()
            
            self._logger.debug(f"Sent Modbus RTU packet: {modbus_packet.hex()}")
            return True
            
        except Exception as e:
            self._logger.error(f"Failed to send packet via serial: {e}")
            return False

    async def read_packet(self) -> Optional[bytes]:
        """
        Read packet from serial connection.
        
        Note: Serial transport uses callback-based data reception,
        so this method is not typically used directly.
        
        Returns:
            None (data is handled via callbacks)
        """
        return None

    async def start_listening(self) -> None:
        """Start listening for incoming data."""
        if not self._listen_task or self._listen_task.done():
            self._listen_task = asyncio.create_task(self._listen_loop())

    async def stop_listening(self) -> None:
        """Stop listening for incoming data."""
        if self._listen_task and not self._listen_task.done():
            self._listen_task.cancel()
            try:
                await self._listen_task
            except asyncio.CancelledError:
                pass

    async def _listen_loop(self) -> None:
        """Background task to read data from serial port."""
        while self._connected and self._reader:
            try:
                # Read data from serial port
                data = await self._reader.read(1024)
                if not data:
                    self._logger.warning("No data received from serial port")
                    await asyncio.sleep(0.1)
                    continue
                
                # Add data to buffer
                self._buffer.extend(data)
                
                # Process complete packets
                while self._buffer:
                    packet = self._extract_packet()
                    if packet:
                        # Verify CRC before conversion
                        if not self._verify_crc(packet):
                            self._crc_fail_count += 1
                            self._logger.warning("Discarding packet with bad CRC")
                            continue
                        # Convert Modbus RTU to direct register update (preferred)
                        handled = self._process_rtu_response(packet)
                        if handled:
                            self._packets_ok += 1
                        else:
                            # Fallback: convert to LuxPower-like bytes if needed
                            luxpower_packet = self._modbus_rtu_to_luxpower(packet)
                            if luxpower_packet and self._callback:
                                self._packets_ok += 1
                                self._callback(luxpower_packet)
                    else:
                        break
                
            except Exception as e:
                self._logger.error(f"Error in serial listen loop: {e}")
                break
        
        self._connected = False

    def _extract_packet(self) -> Optional[bytes]:
        """
        Extract a complete packet from the buffer.
        
        Returns:
            Complete packet data, or None if no complete packet available
        """
        # Need at least 4 bytes for addr+func+CRC
        if len(self._buffer) < 4:
            return None

        # Scan for device address start (0x01 typical)
        start_idx = None
        for i in range(len(self._buffer) - 3):
            if self._buffer[i] == 0x01:
                start_idx = i
                break
        if start_idx is None:
            # Drop leading noise, keep last 3 bytes as potential start
            self._buffer = self._buffer[-3:]
            return None

        # Ensure we have function code
        if start_idx + 1 >= len(self._buffer):
            return None
        function_code = self._buffer[start_idx + 1]

        # If response (0x03/0x04), need byte count to determine length
        if function_code in (0x03, 0x04):
            # Need at least addr, func, byte_count
            if start_idx + 3 > len(self._buffer):
                return None
            # If this is a response frame, third byte is byte count
            # For requests, length is fixed 8 bytes, but requests won't arrive here usually
            byte_count = self._buffer[start_idx + 2]
            expected_length = 3 + byte_count + 2  # addr+func+count + data + CRC
        elif function_code == 0x06:  # write single
            expected_length = 8
        else:
            # Default conservative length; re-sync on CRC failure later
            expected_length = 8

        end_idx = start_idx + expected_length
        if len(self._buffer) >= end_idx:
            packet = bytes(self._buffer[start_idx:end_idx])
            del self._buffer[:end_idx]
            return packet

        # Not enough bytes yet; keep buffer but trim leading noise if any
        if start_idx > 0:
            del self._buffer[:start_idx]
        return None

    def _get_modbus_packet_length(self, function_code: int) -> int:
        """
        Get expected Modbus RTU packet length based on function code.
        
        Args:
            function_code: Modbus function code
            
        Returns:
            Expected packet length
        """
        # This is a simplified implementation
        # Actual implementation would need to handle all Modbus function codes
        if function_code in [0x03, 0x04]:  # Read holding/input registers
            return 8  # address + function + start_addr + count + CRC
        elif function_code in [0x06]:  # Write single register
            return 8  # address + function + register + value + CRC
        else:
            return 8  # Default length

    def _luxpower_to_modbus_rtu(self, luxpower_packet: bytes) -> (Optional[bytes], Dict[str, int]):
        """
        Convert LuxPower packet to Modbus RTU format.
        
        Args:
            luxpower_packet: LuxPower packet data
            
        Returns:
            Tuple of (Modbus RTU packet bytes or None, metadata dict with start/count/type)
        """
        try:
            if not isinstance(luxpower_packet, (bytes, bytearray)) or len(luxpower_packet) < 12:
                return None, {}

            # Heuristic scan for register and a second word that is either count (<=125) or value
            start = None
            second = None
            for i in range(0, len(luxpower_packet) - 3):
                reg = int.from_bytes(luxpower_packet[i:i+2], 'little')
                val = int.from_bytes(luxpower_packet[i+2:i+4], 'little')
                if 0 <= reg <= 1200:
                    start = reg
                    second = val
                    break
            if start is None:
                return None, {}

            device_addr = 0x01
            func = 0x04  # default to read input
            count = None
            value = None

            if second is not None and 1 <= second <= 125:
                # Treat as read count
                if second == 127:
                    # Policy: ignore legacy 127-register reads entirely
                    self._logger.warning("Ignoring 127-register read request (serial bridge)")
                    return None, {}
                # Normalize to 40 for best compatibility
                count = 40
                # Attempt to distinguish READ_HOLD: look ahead for a hint byte 0x03 somewhere
                func = 0x04
            else:
                # Treat as write single
                func = 0x06
                value = second if second is not None else 0

            frame = bytearray()
            frame.append(device_addr)
            frame.append(func)
            if func in (0x03, 0x04):
                frame.extend(start.to_bytes(2, 'big'))
                frame.extend((count or 40).to_bytes(2, 'big'))
                meta = {"start": start, "count": count or 40, "type": "input" if func == 0x04 else "hold"}
            else:
                frame.extend(start.to_bytes(2, 'big'))
                frame.extend((value or 0).to_bytes(2, 'big'))
                meta = {"start": start, "count": 1, "type": "write"}

            crc = self._calculate_crc16(frame)
            frame.extend(crc.to_bytes(2, 'little'))
            return bytes(frame), meta
        except Exception as e:
            self._logger.error(f"Failed to build Modbus RTU from LuxPower packet: {e}")
            return None, {}

    def _modbus_rtu_to_luxpower(self, modbus_packet: bytes) -> Optional[bytes]:
        """
        Convert Modbus RTU packet to LuxPower format.
        
        Args:
            modbus_packet: Modbus RTU packet data
            
        Returns:
            LuxPower packet data, or None if conversion failed
        """
        # This is a placeholder implementation
        # The actual implementation would need to:
        # 1. Parse Modbus RTU packet structure
        # 2. Extract register data
        # 3. Create LuxPower packet format
        # 4. Add proper headers and checksums
        
        if len(modbus_packet) < 4:
            return None
        
        # Extract register data
        device_addr = modbus_packet[0]
        function_code = modbus_packet[1]
        
        if function_code == 0x04:  # Read input registers response
            register_count = modbus_packet[2]
            register_data = modbus_packet[3:-2]  # Exclude CRC
            
            # Convert to LuxPower packet format
            luxpower_packet = self._create_luxpower_response_packet(register_data)
            return luxpower_packet
        
        return None

    def _process_rtu_response(self, modbus_packet: bytes) -> bool:
        """Process RTU response by forwarding directly to client if available."""
        try:
            if len(modbus_packet) < 5:
                return False
            if not self._verify_crc(modbus_packet):
                return False
            func = modbus_packet[1]
            if func not in (0x03, 0x04):
                return False
            byte_count = modbus_packet[2]
            data = modbus_packet[3:-2]
            if len(data) != byte_count:
                return False

            # Determine register start/count/type from last request
            start = self._last_request_start if isinstance(self._last_request_start, int) else 0
            count = self._last_request_count if isinstance(self._last_request_count, int) else max(1, byte_count // 2)
            reg_type = self._last_request_type if self._last_request_type in ("input", "hold") else ("input" if func == 0x04 else "hold")

            values = []
            for i in range(0, byte_count, 2):
                # Modbus RTU is big-endian per register
                v = int.from_bytes(data[i:i+2], 'big')
                values.append(v)

            if hasattr(self, '_client') and self._client:
                self._client.process_mqtt_register_data(start, count, values, reg_type)
                return True
            return False
        except Exception as e:
            self._logger.error(f"Error processing RTU response: {e}")
            return False

    def _create_luxpower_response_packet(self, register_data: bytes) -> bytes:
        """
        Create LuxPower response packet from register data.
        
        Args:
            register_data: Raw register data from Modbus
            
        Returns:
            LuxPower formatted packet
        """
        # This is a simplified implementation
        packet = bytearray()
        
        # Add LuxPower packet header
        packet.extend(b'\x55\xAA')  # Prefix
        packet.extend(b'\xC2')      # Protocol number (TRANSLATED_DATA)
        
        # Add register information
        packet.extend((0x0000).to_bytes(2, 'little'))  # Register start
        packet.extend((len(register_data) // 2).to_bytes(2, 'little'))  # Register count
        
        # Add register data
        packet.extend(register_data)
        
        return bytes(packet)

    def set_client(self, client) -> None:
        """Set the LuxPower client instance for direct processing."""
        self._client = client

    def _calculate_crc16(self, data: bytearray) -> int:
        """
        Calculate Modbus RTU CRC16.
        
        Args:
            data: Data to calculate CRC for
            
        Returns:
            CRC16 value
        """
        crc = 0xFFFF
        for byte in data:
            crc ^= byte
            for _ in range(8):
                if crc & 0x0001:
                    crc = (crc >> 1) ^ 0xA001
                else:
                    crc >>= 1
        return crc

    def _verify_crc(self, packet: bytes) -> bool:
        """
        Verify Modbus RTU packet CRC.
        
        Args:
            packet: Modbus RTU packet
            
        Returns:
            True if CRC is valid, False otherwise
        """
        if len(packet) < 4:
            return False
        
        data = bytearray(packet[:-2])
        received_crc = int.from_bytes(packet[-2:], 'little')
        calculated_crc = self._calculate_crc16(data)
        
        return received_crc == calculated_crc
