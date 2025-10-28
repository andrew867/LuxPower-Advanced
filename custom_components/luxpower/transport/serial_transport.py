"""
Serial transport implementation for LuxPower integration.

This module implements direct USB serial/RS485 communication for the LuxPower
integration, using pyserial-asyncio for async serial communication and
implementing Modbus RTU protocol directly.
"""

import asyncio
import logging
from typing import Optional

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
            modbus_packet = self._luxpower_to_modbus_rtu(packet)
            
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
                        # Convert Modbus RTU to LuxPower format
                        luxpower_packet = self._modbus_rtu_to_luxpower(packet)
                        if luxpower_packet:
                            if self._callback:
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
        # Modbus RTU packets start with device address and end with CRC
        # Minimum packet size is 4 bytes (address + function + CRC)
        if len(self._buffer) < 4:
            return None
        
        # Look for packet start (device address)
        for i in range(len(self._buffer) - 3):
            if self._buffer[i] == 0x01:  # LuxPower device address
                # Calculate expected packet length based on function code
                if i + 1 < len(self._buffer):
                    function_code = self._buffer[i + 1]
                    expected_length = self._get_modbus_packet_length(function_code)
                    
                    if len(self._buffer) >= i + expected_length:
                        # Extract packet
                        packet = bytes(self._buffer[i:i + expected_length])
                        # Remove packet from buffer
                        del self._buffer[:i + expected_length]
                        return packet
        
        # If no complete packet found, keep only the last few bytes
        if len(self._buffer) > 10:
            self._buffer = self._buffer[-10:]
        
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

    def _luxpower_to_modbus_rtu(self, luxpower_packet: bytes) -> bytes:
        """
        Convert LuxPower packet to Modbus RTU format.
        
        Args:
            luxpower_packet: LuxPower packet data
            
        Returns:
            Modbus RTU packet data
        """
        # This is a placeholder implementation
        # The actual implementation would need to:
        # 1. Parse LuxPower packet structure
        # 2. Extract register information
        # 3. Create appropriate Modbus RTU command
        # 4. Add proper CRC calculation
        
        # For now, return a simple Modbus RTU read command
        modbus_packet = bytearray()
        modbus_packet.append(0x01)  # Device address
        modbus_packet.append(0x04)  # Read input registers
        modbus_packet.extend((0x0000).to_bytes(2, 'big'))  # Start address
        modbus_packet.extend((0x0028).to_bytes(2, 'big'))  # Number of registers
        
        # Calculate CRC (simplified)
        crc = self._calculate_crc16(modbus_packet)
        modbus_packet.extend(crc.to_bytes(2, 'little'))
        
        return bytes(modbus_packet)

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
        
        # Verify CRC
        if not self._verify_crc(modbus_packet):
            self._logger.warning("Modbus RTU packet CRC verification failed")
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
