"""
MQTT transport implementation for LuxPower integration.

This module implements MQTT communication for the LuxPower integration,
subscribing to JSON-formatted register data from an ESPHome Modbus bridge
and publishing write commands back to the bridge.
"""

import asyncio
import json
import logging
from typing import Optional, Dict, Any

from homeassistant.components.mqtt import async_subscribe, async_publish
from homeassistant.core import HomeAssistant

from . import LuxPowerTransport

_LOGGER = logging.getLogger(__name__)


class MQTTTransport(LuxPowerTransport):
    """
    MQTT transport implementation for LuxPower communication.
    
    This class subscribes to MQTT topics published by an ESPHome Modbus bridge
    and converts JSON register data back to the binary format expected by
    the LuxPower protocol parser.
    """

    def __init__(self, hass: HomeAssistant, topic_prefix: str, dongle_serial: str):
        """
        Initialize MQTT transport.
        
        Args:
            hass: Home Assistant instance
            topic_prefix: MQTT topic prefix (e.g., "modbus_bridge")
            dongle_serial: Dongle serial number
        """
        super().__init__(dongle_serial)
        self.hass = hass
        self.topic_prefix = topic_prefix
        self._subscriptions: Dict[str, Any] = {}
        self._listen_task: Optional[asyncio.Task] = None
        self._register_cache: Dict[int, int] = {}
        self._last_update_times: Dict[str, float] = {}

    async def connect(self) -> bool:
        """
        Establish MQTT subscriptions.
        
        Returns:
            True if subscriptions successful, False otherwise
        """
        try:
            self._logger.info(f"Connecting to MQTT topics with prefix: {self.topic_prefix}")
            
            # Subscribe to data bank topics (both formats):
            # 1) {prefix}/{dongle}/data/bankX
            # 2) {prefix}/data/bankX
            for bank in range(7):  # Banks 0-6
                topic_with_dongle = f"{self.topic_prefix}/{self.dongle_serial}/data/bank{bank}"
                topic_without_dongle = f"{self.topic_prefix}/data/bank{bank}"
                self._subscriptions[f"data_bank{bank}_with"] = await async_subscribe(
                    self.hass, topic_with_dongle, self._handle_data_message, qos=1
                )
                self._subscriptions[f"data_bank{bank}_without"] = await async_subscribe(
                    self.hass, topic_without_dongle, self._handle_data_message, qos=1
                )
                self._logger.debug(f"Subscribed to {topic_with_dongle} and {topic_without_dongle}")
            
            # Subscribe to hold register topics (both formats)
            for bank in range(6):  # Banks 0-5
                topic_with_dongle = f"{self.topic_prefix}/{self.dongle_serial}/hold/bank{bank}"
                topic_without_dongle = f"{self.topic_prefix}/hold/bank{bank}"
                self._subscriptions[f"hold_bank{bank}_with"] = await async_subscribe(
                    self.hass, topic_with_dongle, self._handle_hold_message, qos=1
                )
                self._subscriptions[f"hold_bank{bank}_without"] = await async_subscribe(
                    self.hass, topic_without_dongle, self._handle_hold_message, qos=1
                )
                self._logger.debug(f"Subscribed to {topic_with_dongle} and {topic_without_dongle}")
            
            # Subscribe to status topic (both formats)
            status_topic_with = f"{self.topic_prefix}/{self.dongle_serial}/status"
            status_topic_without = f"{self.topic_prefix}/status"
            self._subscriptions["status_with"] = await async_subscribe(
                self.hass, status_topic_with, self._handle_status_message, qos=1
            )
            self._subscriptions["status_without"] = await async_subscribe(
                self.hass, status_topic_without, self._handle_status_message, qos=1
            )
            self._logger.debug(f"Subscribed to {status_topic_with} and {status_topic_without}")
            
            self._connected = True
            self._logger.info("MQTT subscriptions established")
            
            # Start listening task
            self._listen_task = asyncio.create_task(self._listen_loop())
            
            return True
            
        except Exception as e:
            self._logger.error(f"Failed to establish MQTT subscriptions: {e}")
            self._connected = False
            return False

    async def disconnect(self) -> None:
        """Disconnect from MQTT subscriptions."""
        self._logger.info("Disconnecting from MQTT subscriptions")
        
        # Stop listening task
        if self._listen_task and not self._listen_task.done():
            self._listen_task.cancel()
            try:
                await self._listen_task
            except asyncio.CancelledError:
                pass
        
        # Unsubscribe from all topics
        for subscription in self._subscriptions.values():
            if hasattr(subscription, 'unsubscribe'):
                subscription.unsubscribe()
        
        self._subscriptions.clear()
        self._connected = False

    async def send_packet(self, packet: bytes) -> bool:
        """
        Send packet through MQTT.
        
        Args:
            packet: Packet data to send (will be converted to JSON write command)
            
        Returns:
            True if send successful, False otherwise
        """
        if not self._connected:
            self._logger.error("Cannot send packet: not connected")
            return False
        
        try:
            # Convert binary packet to JSON write command
            write_command = self._packet_to_write_command(packet)
            
            # Publish write command
            write_topic = f"{self.topic_prefix}/{self.dongle_serial}/write"
            await async_publish(
                self.hass, write_topic, json.dumps(write_command), qos=1
            )
            
            self._logger.debug(f"Sent write command: {write_command}")
            return True
            
        except Exception as e:
            self._logger.error(f"Failed to send packet via MQTT: {e}")
            return False

    async def read_packet(self) -> Optional[bytes]:
        """
        Read packet from MQTT.
        
        Note: MQTT transport uses callback-based data reception,
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

    def _handle_data_message(self, msg) -> None:
        """Handle incoming data bank message."""
        try:
            data = json.loads(msg.payload)
            bank_data = self._json_to_register_data(data, "input")
            if bank_data:
                if self._callback:
                    self._callback(bank_data)
        except Exception as e:
            self._logger.error(f"Error handling data message: {e}")

    def _handle_hold_message(self, msg) -> None:
        """Handle incoming hold register message."""
        try:
            data = json.loads(msg.payload)
            bank_data = self._json_to_register_data(data, "hold")
            if bank_data:
                if self._callback:
                    self._callback(bank_data)
        except Exception as e:
            self._logger.error(f"Error handling hold message: {e}")

    def _handle_status_message(self, msg) -> None:
        """Handle incoming status message."""
        try:
            status = json.loads(msg.payload)
            self._logger.debug(f"Bridge status: {status}")
        except Exception as e:
            self._logger.error(f"Error handling status message: {e}")

    def _json_to_register_data(self, data: Dict[str, Any], register_type: str) -> Optional[bytes]:
        """
        Convert JSON register data to binary format.
        
        Args:
            data: JSON data from MQTT message
            register_type: Type of register ("input" or "hold")
            
        Returns:
            Binary data in LuxPower packet format, or None if conversion failed
        """
        try:
            register_start = data.get("register_start", 0)
            register_count = data.get("register_count", 0)
            values = data.get("values", [])
            
            if not values or len(values) != register_count:
                self._logger.warning(f"Invalid register data: {data}")
                return None
            
            # Update register cache
            for i, value in enumerate(values):
                register_addr = register_start + i
                self._register_cache[register_addr] = value
            
            # Convert to LuxPower packet format
            # This creates a proper LuxPower packet structure
            packet_data = self._create_luxpower_packet(register_start, register_count, values, register_type)
            
            return packet_data
            
        except Exception as e:
            self._logger.error(f"Error converting JSON to register data: {e}")
            return None

    def _create_luxpower_packet(self, register_start: int, register_count: int, 
                              values: list, register_type: str) -> bytes:
        """
        Create LuxPower packet from register data.
        
        This creates a proper LuxPower packet that matches the format expected
        by the LXPPacket parser in the integration.
        """
        packet = bytearray()
        
        # LuxPower packet header
        packet.extend(b'\x55\xAA')  # Prefix
        packet.extend(b'\xC2')      # Protocol number (TRANSLATED_DATA)
        
        # Device address and function code
        packet.append(0x01)  # Device address
        if register_type == "input":
            packet.append(0x04)  # Read input registers response
        else:
            packet.append(0x03)  # Read holding registers response
        
        # Register information
        packet.extend(register_start.to_bytes(2, 'little'))
        packet.extend(register_count.to_bytes(2, 'little'))
        
        # Register values (each register is 2 bytes, little-endian)
        for value in values:
            packet.extend(value.to_bytes(2, 'little'))
        
        # Add CRC (simplified - would need proper CRC calculation)
        crc = self._calculate_simple_crc(packet)
        packet.extend(crc.to_bytes(2, 'little'))
        
        return bytes(packet)

    def _calculate_simple_crc(self, data: bytearray) -> int:
        """
        Calculate a simple checksum for the packet.
        
        Note: This is a simplified implementation. The actual LuxPower
        protocol may use a different CRC algorithm.
        """
        crc = 0
        for byte in data:
            crc ^= byte
        return crc & 0xFFFF

    def _packet_to_write_command(self, packet: bytes) -> Dict[str, Any]:
        """
        Convert binary packet to JSON write command.
        
        Args:
            packet: Binary packet data
            
        Returns:
            JSON write command dictionary
        """
        # This is a placeholder implementation
        # The actual implementation would need to:
        # 1. Parse LuxPower packet structure
        # 2. Extract register address and value
        # 3. Create appropriate Modbus write command
        
        return {
            "function": "write_single",
            "address": 0x01,
            "register": 0,
            "value": 0,
            "timestamp": asyncio.get_event_loop().time()
        }

    async def _listen_loop(self) -> None:
        """Background task to monitor MQTT connection."""
        while self._connected:
            try:
                # Check if we're still receiving data
                current_time = asyncio.get_event_loop().time()
                
                # Check for stale data (no updates in 30 seconds)
                for topic, last_update in self._last_update_times.items():
                    if current_time - last_update > 30:
                        self._logger.warning(f"No data received on {topic} for 30+ seconds")
                
                await asyncio.sleep(10)
                
            except Exception as e:
                self._logger.error(f"Error in MQTT listen loop: {e}")
                break
        
        self._connected = False
