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
        self._seq: int = 0
        self._pending_acks: Dict[int, Dict[str, Any]] = {}

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
            for bank in range(10):  # Banks 0-9
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
            for bank in range(10):  # Banks 0-9
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

            # Subscribe to write acks (add-only topic, backwards compatible)
            write_ack_with = f"{self.topic_prefix}/{self.dongle_serial}/write/ack"
            write_ack_without = f"{self.topic_prefix}/write/ack"
            self._subscriptions["write_ack_with"] = await async_subscribe(
                self.hass, write_ack_with, self._handle_write_ack, qos=1
            )
            self._subscriptions["write_ack_without"] = await async_subscribe(
                self.hass, write_ack_without, self._handle_write_ack, qos=1
            )
            self._logger.debug(f"Subscribed to {write_ack_with} and {write_ack_without}")
            
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
            if not write_command:
                self._logger.error("Refusing to send MQTT write: could not parse register/value from packet")
                return False

            # Attach sequence for idempotency and ack correlation
            self._seq = (self._seq + 1) & 0xFFFFFFFF
            write_command["seq"] = self._seq

            # Track pending ack (best-effort, non-blocking)
            self._pending_acks[self._seq] = {
                "command": write_command,
                "timestamp": asyncio.get_event_loop().time(),
                "retries": 0,
            }

            # Publish write command
            write_topic = f"{self.topic_prefix}/{self.dongle_serial}/write"
            await async_publish(self.hass, write_topic, json.dumps(write_command), qos=1)

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
            payload = msg.payload
            if isinstance(payload, bytes):
                payload = payload.decode('utf-8', errors='ignore')
            
            # Try to parse as JSON first
            if payload.startswith('{'):
                status = json.loads(payload)
                self._logger.debug(f"Bridge status: {status}")
            else:
                # Handle plain text status messages
                self._logger.debug(f"Bridge status (text): {payload}")
        except Exception as e:
            self._logger.error(f"Error handling status message: {e}")

    def _handle_write_ack(self, msg) -> None:
        """Handle incoming write acknowledgements."""
        try:
            data = msg.payload
            if isinstance(data, bytes):
                data = data.decode("utf-8", errors="ignore")
            ack = json.loads(data)
            seq = ack.get("seq")
            status = ack.get("status")
            if isinstance(seq, int) and seq in self._pending_acks:
                entry = self._pending_acks.pop(seq, None)
                self._logger.debug(f"Received write ack for seq={seq}, status={status}")
            else:
                self._logger.debug(f"Write ack for unknown seq: {ack}")
        except Exception as e:
            self._logger.error(f"Error handling write ack: {e}")

    def _json_to_register_data(self, data: Dict[str, Any], register_type: str) -> Optional[bytes]:
        """
        Convert JSON register data to direct register format.
        
        Instead of creating LuxPower packets, we'll pass the data directly
        to the entities via a special callback mechanism.
        """
        try:
            register_start = data.get("register_start", 0)
            register_count = data.get("register_count", 0)
            values = data.get("values", [])
            
            if not values or len(values) != register_count:
                self._logger.warning(f"Invalid register data: {data}")
                return None

            # Enforce 40-register bank policy; ignore legacy 127 banks
            if register_count == 127:
                self._logger.warning("Ignoring 127-register MQTT bank (start=%s)", register_start)
                return None
            if register_count != 40:
                self._logger.debug("Normalizing non-standard MQTT bank size=%s to 40-only policy (dropping)", register_count)
                return None
            
            # Update register cache
            for i, value in enumerate(values):
                register_addr = register_start + i
                self._register_cache[register_addr] = value
            
            # Process the data directly without LuxPower packet parsing
            self._process_direct_register_data(register_start, register_count, values, register_type)
            
            # Return None to indicate we handled it directly
            return None
            
        except Exception as e:
            self._logger.error(f"Error converting JSON to register data: {e}")
            return None

    def _process_direct_register_data(self, register_start: int, register_count: int, 
                                    values: list, register_type: str) -> None:
        """
        Process register data directly without LuxPower packet parsing.
        
        This bypasses the complex packet parsing and directly updates the entities.
        """
        try:
            # Get the LuxPower client instance to call the direct processing method
            if hasattr(self, '_client') and self._client:
                # Call the client's direct MQTT processing method
                self._client.process_mqtt_register_data(register_start, register_count, values, register_type)
            else:
                self._logger.warning("No client instance available for direct processing")
                
        except Exception as e:
            self._logger.error(f"Error processing direct register data: {e}")

    def set_client(self, client):
        """Set the LuxPower client instance for direct processing."""
        self._client = client


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

    def _packet_to_write_command(self, packet: bytes) -> Optional[Dict[str, Any]]:
        """
        Convert binary packet to JSON write command.
        
        Args:
            packet: Binary packet data
            
        Returns:
            JSON write command dictionary, or None on parse failure
        """
        try:
            # Minimal, safe parser for LuxPower WRITE_SINGLE frames created by LXPPacket
            # Expected pattern (little-endian fields within framed payload).
            # We search for two consecutive 2-byte values that plausibly represent register and value.
            if not isinstance(packet, (bytes, bytearray)) or len(packet) < 12:
                return None

            # Heuristic: scan for two uint16 little-endian values in a reasonable range
            # Registers typically 0..1000, values 0..65535
            register = None
            value = None
            for i in range(0, len(packet) - 3):
                reg = int.from_bytes(packet[i:i+2], "little")
                val = int.from_bytes(packet[i+2:i+4], "little")
                if 0 <= reg <= 1200:
                    register = reg
                    value = val
                    break

            if register is None:
                self._logger.warning("Could not infer register from packet; write skipped")
                return None

            return {
                "function": "write_single",
                "address": 0x01,
                "register": register,
                "value": value,
                "timestamp": asyncio.get_event_loop().time(),
            }
        except Exception as e:
            self._logger.error(f"Failed to parse write packet: {e}")
            return None

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

                # Retry pending writes if no ack after timeout (best-effort)
                to_retry = []
                for seq, entry in list(self._pending_acks.items()):
                    if current_time - entry.get("timestamp", 0) > 5:
                        to_retry.append(seq)
                for seq in to_retry:
                    entry = self._pending_acks.get(seq)
                    if not entry:
                        continue
                    retries = entry.get("retries", 0)
                    if retries >= 3:
                        self._logger.error(f"Write seq={seq} failed after retries; giving up")
                        self._pending_acks.pop(seq, None)
                        continue
                    entry["retries"] = retries + 1
                    entry["timestamp"] = current_time
                    try:
                        write_topic = f"{self.topic_prefix}/{self.dongle_serial}/write"
                        await async_publish(self.hass, write_topic, json.dumps(entry["command"]), qos=1)
                        self._logger.warning(f"Retried write seq={seq} (attempt {retries + 1})")
                    except Exception as e:
                        self._logger.error(f"Error retrying write seq={seq}: {e}")
                
                await asyncio.sleep(10)
                
            except Exception as e:
                self._logger.error(f"Error in MQTT listen loop: {e}")
                break
        
        self._connected = False
