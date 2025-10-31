"""
LuxPower inverter communication client with transport abstraction.

This module provides a refactored LuxPowerClient that uses the transport abstraction
layer to support multiple communication methods (TCP, MQTT, Serial) while maintaining
all existing protocol logic and functionality.
"""

import asyncio
import datetime
import logging
from typing import Optional, TYPE_CHECKING
import os

from homeassistant.core import HomeAssistant

from ..LXPPacket import LXPPacket, MIN_ADAPTIVE_POLLING_INTERVAL, MAX_ADAPTIVE_POLLING_INTERVAL, ADAPTIVE_POLLING_BASE_INTERVAL, ADAPTIVE_POLLING_ADJUSTMENT_FACTOR
from ..helpers import Event
from ..const import DOMAIN

if TYPE_CHECKING:
    from ..transport import LuxPowerTransport

_LOGGER = logging.getLogger(__name__)


def make_log_marker(serial: str, dongle: str, tag: str) -> str:
    """
    Create a standardized log marker for LuxPower client operations.
    
    Args:
        serial: Inverter serial number
        dongle: Dongle serial number  
        tag: Operation tag identifier
        
    Returns:
        Formatted log marker string
    """
    now = datetime.datetime.now()
    marker = str(
        int(
            (
                now - now.replace(hour=0, minute=0, second=0, microsecond=0)
            ).total_seconds()
            * 1000
        )
    ).zfill(8)
    marker = marker + " " + serial.decode() + "/" + dongle.decode() + " " + tag
    return marker


class LuxPowerClient:
    """
    LuxPower inverter communication client with transport abstraction.
    
    This class handles communication with LuxPower inverters using various
    transport methods (TCP, MQTT, Serial) while maintaining the same protocol
    logic and event handling as the original implementation.
    """

    def __init__(
        self,
        hass: HomeAssistant,
        transport: 'LuxPowerTransport',
        dongle_serial: str,
        serial_number: str,
        events: Event,
        respond_to_heartbeat: bool,
    ):
        """
        Initialize LuxPower client with transport abstraction.
        
        Args:
            hass: Home Assistant instance
            transport: Transport instance for communication
            dongle_serial: Dongle serial number
            serial_number: Inverter serial number
            events: Event handler instance
            respond_to_heartbeat: Whether to respond to heartbeat requests
        """
        self.hass = hass
        self._transport = transport
        self.dongle_serial = dongle_serial
        self.serial_number = serial_number
        self.events = events
        self._warn_registers = False
        self._stop_client = False
        self._connected = False
        self._connect_twice = False
        self._connect_after_failure = False
        self._already_processing = False
        self._respond_to_heartbeat = respond_to_heartbeat
        self._LOGGER = logging.getLogger(__name__)
        self._lxp_request_lock = asyncio.Lock()
        self._lxp_single_register_result = None
        
        # Performance tracking attributes
        self._request_times = {}  # Track request start times by bank
        self._response_times = []  # Track response times for averaging
        self._last_request_time = None  # Track time between requests
        self._request_intervals = []  # Track intervals between requests
        self._max_response_time_samples = 100  # Keep last 100 samples for averaging
        
        # Register data received callback with the transport
        self._transport.register_data_received_callback(self._handle_transport_data)
        
        # Set client reference in transport for direct processing (if MQTT transport)
        if hasattr(self._transport, 'set_client'):
            self._transport.set_client(self)
        
        # Retry logic attributes
        self._connection_attempts = 0
        self._max_retry_attempts = 10
        self._retry_delay = 1
        self._max_retry_delay = 60

        # Adaptive polling attributes
        self._adaptive_polling_enabled = True
        self._connection_quality = 1.0
        self._connection_success_count = 0
        self._connection_failure_count = 0
        self._last_successful_connection = None
        self._current_polling_interval = ADAPTIVE_POLLING_BASE_INTERVAL
        self._polling_history = []

        # Policy counters
        self._dropped_read_127 = 0

        # Spacing between bank requests (seconds), configurable via env for tuning
        try:
            self._inter_bank_delay = float(os.getenv("LUXPOWER_INTER_BANK_DELAY", "0.1"))
        except Exception:
            self._inter_bank_delay = 0.1

        # Initialize LXPPacket for protocol handling
        self.lxpPacket = LXPPacket(
            debug=False, dongle_serial=dongle_serial, serial_number=serial_number
        )

    def _record_request_start(self, bank: int) -> None:
        """Record the start time of a request for performance tracking."""
        import time
        current_time = time.time()
        
        # Track time between requests
        if self._last_request_time is not None:
            interval = current_time - self._last_request_time
            self._request_intervals.append(interval)
            # Keep only last 100 intervals
            if len(self._request_intervals) > self._max_response_time_samples:
                self._request_intervals.pop(0)
        
        self._last_request_time = current_time
        self._request_times[bank] = current_time

    def _record_response_time(self, bank: int) -> None:
        """Record the response time for a completed request."""
        import time
        if bank in self._request_times:
            response_time = time.time() - self._request_times[bank]
            self._response_times.append(response_time)
            
            # Keep only last 100 samples
            if len(self._response_times) > self._max_response_time_samples:
                self._response_times.pop(0)
            
            # Remove the request time entry
            del self._request_times[bank]

    def get_performance_stats(self) -> dict:
        """Get current performance statistics."""
        import statistics
        
        stats = {
            "average_response_time": 0.0,
            "min_response_time": 0.0,
            "max_response_time": 0.0,
            "average_request_interval": 0.0,
            "min_request_interval": 0.0,
            "max_request_interval": 0.0,
            "total_requests": len(self._response_times),
            "pending_requests": len(self._request_times)
        }
        
        if self._response_times:
            stats["average_response_time"] = statistics.mean(self._response_times)
            stats["min_response_time"] = min(self._response_times)
            stats["max_response_time"] = max(self._response_times)
        
        if self._request_intervals:
            stats["average_request_interval"] = statistics.mean(self._request_intervals)
            stats["min_request_interval"] = min(self._request_intervals)
            stats["max_request_interval"] = max(self._request_intervals)
        
        # Include policy counters
        try:
            stats["dropped_read_127"] = self._dropped_read_127
        except Exception:
            stats["dropped_read_127"] = 0
        return stats

    def log_performance_stats(self) -> None:
        """Log current performance statistics."""
        stats = self.get_performance_stats()
        self._LOGGER.info(
            f"📊 Performance Stats - "
            f"Avg Response: {stats['average_response_time']:.3f}s "
            f"(Min: {stats['min_response_time']:.3f}s, Max: {stats['max_response_time']:.3f}s), "
            f"Avg Interval: {stats['average_request_interval']:.3f}s "
            f"(Min: {stats['min_request_interval']:.3f}s, Max: {stats['max_request_interval']:.3f}s), "
            f"Total Requests: {stats['total_requests']}, Pending: {stats['pending_requests']}"
        )

    def _handle_transport_data(self, data: bytes) -> None:
        """Callback method to handle data received from the transport."""
        self._LOGGER.debug(f"Client received data from transport: {data.hex()}")
        # Pass the data to the existing LXPPacket parsing logic
        self._data_received(data)

    def process_mqtt_register_data(self, register_start: int, register_count: int, 
                                 values: list, register_type: str) -> None:
        """
        Process MQTT register data directly without LuxPower packet parsing.
        
        This bypasses the complex packet parsing and directly updates the entities.
        
        Args:
            register_start: Starting register address
            register_count: Number of registers
            values: List of register values
            register_type: Type of register ("input" or "hold")
        """
        try:
            self._LOGGER.debug(f"Processing MQTT register data: start={register_start}, count={register_count}, type={register_type}")
            
            # Create a result structure that matches what the client expects
            result = {
                "tcp_function": "TRANSLATED_DATA",
                "device_function": "READ_INPUT" if register_type == "input" else "READ_HOLD",
                "register": register_start,
                "value": b''.join(value.to_bytes(2, 'little') for value in values),
                "data": {},
                "thesedata": {},
                "registers": {register_start + i: values[i] for i in range(len(values))},
                "thesereg": {register_start + i: values[i] for i in range(len(values))}
            }
            
            # Process the result directly using the existing client logic
            self._process_register_result(result)
            
        except Exception as e:
            self._LOGGER.error(f"Error processing MQTT register data: {e}")

    def _process_register_result(self, result: dict) -> None:
        """
        Process register result data directly.
        
        This is the same logic that would be called after packet parsing.
        """
        try:
            if result.get("device_function") == "READ_INPUT":
                # Process input register data
                self._process_input_register_data(result)
            elif result.get("device_function") == "READ_HOLD":
                # Process holding register data
                self._process_holding_register_data(result)
                
        except Exception as e:
            self._LOGGER.error(f"Error processing register result: {e}")

    def _process_input_register_data(self, result: dict) -> None:
        """Process input register data and trigger events."""
        try:
            registers = result.get("thesereg", {})
            serial_number = getattr(self.lxpPacket, 'serial_number', b'').decode('utf-8', errors='ignore').rstrip('\x00')
            
            # Trigger the same events that would be triggered by packet parsing
            event_data = {"registers": registers, "serial_number": serial_number}
            self._LOGGER.debug("EVENT REGISTER: %s", event_data)
            
            # Fire the register update event safely
            try:
                loop = asyncio.get_running_loop()
                loop.create_task(self._fire_register_event(event_data))
            except RuntimeError:
                # No event loop running, fire synchronously
                self.hass.bus.fire(
                    f"{DOMAIN}_register_update_{self.dongle_serial}",
                    event_data
                )
            
        except Exception as e:
            self._LOGGER.error(f"Error processing input register data: {e}")

    def _process_holding_register_data(self, result: dict) -> None:
        """Process holding register data and trigger events."""
        try:
            registers = result.get("thesereg", {})
            serial_number = getattr(self.lxpPacket, 'serial_number', b'').decode('utf-8', errors='ignore').rstrip('\x00')
            
            # Trigger the same events that would be triggered by packet parsing
            event_data = {"registers": registers, "serial_number": serial_number}
            self._LOGGER.debug("EVENT REGISTER: %s", event_data)
            
            # Fire the register update event safely
            try:
                loop = asyncio.get_running_loop()
                loop.create_task(self._fire_register_event(event_data))
            except RuntimeError:
                # No event loop running, fire synchronously
                self.hass.bus.fire(
                    f"{DOMAIN}_register_update_{self.dongle_serial}",
                    event_data
                )
            
        except Exception as e:
            self._LOGGER.error(f"Error processing holding register data: {e}")

    async def _fire_register_event(self, event_data: dict) -> None:
        """Fire the register update event safely from the event loop."""
        try:
            self.hass.bus.async_fire(
                f"{DOMAIN}_register_update_{self.dongle_serial}",
                event_data
            )
        except Exception as e:
            self._LOGGER.error(f"Error firing register event: {e}")

    def update_connection_quality(self, success: bool) -> None:
        """Update connection quality based on success/failure."""
        if success:
            self._connection_success_count += 1
            self._last_successful_connection = asyncio.get_event_loop().time()
        else:
            self._connection_failure_count += 1

        # Calculate connection quality (weighted average)
        total_attempts = self._connection_success_count + self._connection_failure_count
        if total_attempts > 0:
            self._connection_quality = self._connection_success_count / total_attempts

        # Update adaptive polling interval
        self._update_adaptive_polling_interval()

    def _update_adaptive_polling_interval(self) -> None:
        """Update the polling interval based on connection quality."""
        if not self._adaptive_polling_enabled:
            return

        # Base interval adjusted by connection quality
        quality_factor = max(0.5, self._connection_quality)
        new_interval = int(ADAPTIVE_POLLING_BASE_INTERVAL / quality_factor)

        # Clamp to valid range
        new_interval = max(MIN_ADAPTIVE_POLLING_INTERVAL,
                          min(MAX_ADAPTIVE_POLLING_INTERVAL, new_interval))

        # Smooth transitions by averaging with current interval
        if self._current_polling_interval > 0:
            new_interval = int((self._current_polling_interval + new_interval) / 2)

        self._current_polling_interval = new_interval

        # Keep history for debugging
        self._polling_history.append(new_interval)
        if len(self._polling_history) > 10:
            self._polling_history.pop(0)

    def get_current_polling_interval(self) -> int:
        """Get the current polling interval."""
        return self._current_polling_interval

    def set_adaptive_polling(self, enabled: bool, reconnection_delay: int = 5) -> None:
        """Configure adaptive polling and reconnection settings."""
        self._adaptive_polling_enabled = enabled
        self._reconnection_delay = reconnection_delay

        if enabled:
            self._current_polling_interval = ADAPTIVE_POLLING_BASE_INTERVAL
        else:
            self._current_polling_interval = ADAPTIVE_POLLING_BASE_INTERVAL

    async def connect(self) -> bool:
        """
        Establish connection using the transport layer.
        
        Returns:
            True if connection successful, False otherwise
        """
        try:
            self._LOGGER.info(f"Connecting to LuxPower inverter via {self._transport}")
            
            # Connect using transport
            success = await self._transport.connect()
            if success:
                self._connected = True
                self.update_connection_quality(True)
                
                # Start listening for data
                await self._transport.start_listening()
                
                self._LOGGER.info(f"Connected to LuxPower inverter via {self._transport}")
                return True
            else:
                self._connected = False
                self.update_connection_quality(False)
                self._LOGGER.error(f"Failed to connect via {self._transport}")
                return False
                
        except Exception as e:
            self._LOGGER.error(f"Connection error: {e}")
            self._connected = False
            self.update_connection_quality(False)
            return False

    async def disconnect(self) -> None:
        """Disconnect from the inverter."""
        self._LOGGER.info(f"Disconnecting from LuxPower inverter via {self._transport}")
        
        # Stop listening
        await self._transport.stop_listening()
        
        # Disconnect transport
        await self._transport.disconnect()
        
        self._connected = False

    def _data_received(self, data: bytes) -> None:
        """
        Handle received data from transport layer.
        
        Args:
            data: Raw data received from transport
        """
        try:
            self._LOGGER.debug("Inverter: %s", self.lxpPacket.serial_number)
            self._LOGGER.debug(data)

            packet_remains = data
            packet_remains_length = len(packet_remains)
            self._LOGGER.debug(
                "OVERALL Packet Remains Length : %s", packet_remains_length
            )

            frame_number = 0
            max_frames = 10  # Prevent infinite loops

            while packet_remains_length > 0 and frame_number < max_frames:
                frame_number = frame_number + 1
                if frame_number > 1:
                    self._LOGGER.debug("*** Multi-Frame *** : %s", frame_number)

                # Validate minimum packet length
                if packet_remains_length < 6:
                    self._LOGGER.warning("Packet too short, discarding remaining data")
                    break

                prefix = packet_remains[0:2]
                if prefix != self.lxpPacket.prefix:
                    self._LOGGER.warning("Invalid Start Of Packet Prefix %s, discarding packet", prefix)
                    break

                try:
                    import struct
                    frame_length_remaining = struct.unpack("H", packet_remains[4:6])[0]
                    frame_length_calced = frame_length_remaining + 6
                    self._LOGGER.debug("CALCULATED Frame Length : %s", frame_length_calced)

                    # Validate frame length
                    if frame_length_calced > packet_remains_length:
                        self._LOGGER.warning("Frame length exceeds remaining packet length")
                        break
                    if frame_length_calced < 6:
                        self._LOGGER.warning("Invalid frame length")
                        break

                    this_frame = packet_remains[0:frame_length_calced]

                    self._LOGGER.debug("THIS Packet Remains Length : %s", packet_remains_length)
                    packet_remains = packet_remains[frame_length_calced:packet_remains_length]
                    packet_remains_length = len(packet_remains)
                    self._LOGGER.debug("NEXT Packet Remains Length : %s", packet_remains_length)

                    self._LOGGER.debug("Received: %s", this_frame)
                    result = self.lxpPacket.parse_packet(this_frame)
                    
                except struct.error as e:
                    self._LOGGER.error(f"Struct unpack error: {e}")
                    break
                except (ValueError, IndexError, KeyError) as e:
                    self._LOGGER.error(f"Data processing error in frame {frame_number}: {e}")
                    break
                except Exception as e:
                    self._LOGGER.error(f"Unexpected error processing frame {frame_number}: {e}")
                    break

            # Always release lock if it was locked, regardless of packet processing
            if (
                self._lxp_request_lock.locked()
                and self.lxpPacket.tcp_function != self.lxpPacket.HEARTBEAT
            ):
                # do not unlock on heartbeat because it's request from inverter
                # all requests from this integration are sequential and locked
                self._lxp_request_lock.release()

            if not self.lxpPacket.packet_error:
                self._LOGGER.debug(result)

                if self.lxpPacket.tcp_function == self.lxpPacket.HEARTBEAT:
                    if self._respond_to_heartbeat:
                        # response back with the packet we got from inverter
                        asyncio.create_task(self._transport.send_packet(data))
                elif self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                    register = self.lxpPacket.register
                    self._LOGGER.debug("register: %s ", register)
                    number_of_registers = int(len(result.get("value", "")) / 2)
                    self._LOGGER.debug("number_of_registers: %s ", number_of_registers)
                    
                    # Record response time for performance tracking
                    bank = register // 40  # Calculate bank number from register
                    self._record_response_time(bank)
                    total_data = {"data": result.get("data", {})}
                    # Extract serial number from LXPPacket for serialization
                    serial_number = getattr(self.lxpPacket, 'serial_number', b'').decode('utf-8', errors='ignore').rstrip('\x00') if hasattr(self.lxpPacket, 'serial_number') else ""
                    self._LOGGER.debug(f"🔍 CLIENT DEBUG - Raw serial_number bytes: {getattr(self.lxpPacket, 'serial_number', b'')}")
                    self._LOGGER.debug(f"🔍 CLIENT DEBUG - Decoded serial_number: '{serial_number}'")
                    event_data = {"data": result.get("thesedata", {}), "serial_number": serial_number}
                    self._LOGGER.debug("EVENT DATA: %s ", event_data)

                    # Decode Standard Block Registers
                    if register == 0 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK0_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 40 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK1_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 80 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK2_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 120 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK3_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 160 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK4_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 200 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK5_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 240 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANK6_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 280 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_REGISTER_BANK7_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 320 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_REGISTER_BANK8_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    elif register == 360 and number_of_registers == 40:
                        self.hass.bus.fire(
                            self.events.EVENT_REGISTER_BANK9_RECEIVED, event_data
                        )
                        self.hass.bus.fire(
                            self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                        )
                    else:
                        if number_of_registers == 1:
                            # Decode Single Register
                            if 0 <= register <= 39:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK0_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 40 <= register <= 79:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK1_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 80 <= register <= 119:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK2_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 120 <= register <= 159:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK3_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 160 <= register <= 199:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK4_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 200 <= register <= 239:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK5_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 240 <= register <= 279:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK6_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 280 <= register <= 319:
                                self.hass.bus.fire(
                                    self.events.EVENT_REGISTER_BANK7_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 320 <= register <= 359:
                                self.hass.bus.fire(
                                    self.events.EVENT_REGISTER_BANK8_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 360 <= register <= 399:
                                self.hass.bus.fire(
                                    self.events.EVENT_REGISTER_BANK9_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                        else:
                            # Decode Series of Registers - Possibly Over Block Boundaries
                            if 0 <= register <= 119:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK0_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK1_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK2_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 120 <= register <= 199:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK3_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK4_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 200 <= register <= 279:
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK5_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANK6_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )
                            elif 280 <= register <= 399:
                                self.hass.bus.fire(
                                    self.events.EVENT_REGISTER_BANK7_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_REGISTER_BANK8_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_REGISTER_BANK9_RECEIVED, event_data
                                )
                                self.hass.bus.fire(
                                    self.events.EVENT_DATA_BANKX_RECEIVED, event_data
                                )

                elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:
                    register = self.lxpPacket.register
                    self._LOGGER.debug("register: %s ", register)
                    number_of_registers = int(len(result.get("value", "")) / 2)
                    # Enforce 40-register policy in responses; ignore legacy 127
                    if number_of_registers == 127:
                        self._LOGGER.warning("Ignoring 127-register response for bank starting at %s", register)
                        try:
                            self._dropped_read_127 += 1
                        except Exception:
                            pass
                    else:
                        # Record response time for performance tracking
                        bank = register // 40  # Calculate bank number from register
                        self._record_response_time(bank)

                        if (
                            number_of_registers == 1
                            and self._lxp_single_register_result is not None
                            and not self._lxp_single_register_result.done()
                        ):
                            self._lxp_single_register_result.set_result(
                                result.get("thesereg", {})
                            )

                        total_data = {"registers": result.get("registers", {})}
                        # Extract serial number from LXPPacket for serialization
                        serial_number = getattr(self.lxpPacket, 'serial_number', b'').decode('utf-8', errors='ignore').rstrip('\x00') if hasattr(self.lxpPacket, 'serial_number') else ""
                        self._LOGGER.debug(f"🔍 CLIENT REGISTER DEBUG - Raw serial_number bytes: {getattr(self.lxpPacket, 'serial_number', b'')}")
                        self._LOGGER.debug(f"🔍 CLIENT REGISTER DEBUG - Decoded serial_number: '{serial_number}'")
                        event_data = {"registers": result.get("thesereg", {}), "serial_number": serial_number}
                        self._LOGGER.debug("EVENT REGISTER: %s ", event_data)
                        if self.lxpPacket.register >= 160 and self._warn_registers:
                            self._LOGGER.debug("REGISTERS: %s ", total_data)
                        if 0 <= self.lxpPacket.register <= 39:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK0_RECEIVED, event_data
                            )
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_21_RECEIVED, event_data
                            )
                        elif 40 <= self.lxpPacket.register <= 79:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK1_RECEIVED, event_data
                            )
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_21_RECEIVED, event_data
                            )
                        elif 80 <= self.lxpPacket.register <= 119:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK2_RECEIVED, event_data
                            )
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_21_RECEIVED, event_data
                            )
                        elif 120 <= self.lxpPacket.register <= 159:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK3_RECEIVED, event_data
                            )
                        elif 160 <= self.lxpPacket.register <= 199:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK4_RECEIVED, event_data
                            )
                        elif 200 <= self.lxpPacket.register <= 239:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK5_RECEIVED, event_data
                            )
                        elif 240 <= self.lxpPacket.register <= 279:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK6_RECEIVED, event_data
                            )
                        elif 280 <= self.lxpPacket.register <= 319:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK7_RECEIVED, event_data
                            )
                        elif 320 <= self.lxpPacket.register <= 359:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK8_RECEIVED, event_data
                            )
                        elif 360 <= self.lxpPacket.register <= 399:
                            self.hass.bus.fire(
                                self.events.EVENT_REGISTER_BANK9_RECEIVED, event_data
                            )
                    
        except (ConnectionError, OSError, RuntimeError) as e:
            self._LOGGER.error(f"Connection error in data_received: {e}")
            # Ensure lock is released even on critical errors
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()
        except Exception as e:
            self._LOGGER.error(f"Critical error in data_received: {e}")
            # Ensure lock is released even on critical errors
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()

    async def send_packet(self, packet: bytes) -> bool:
        """
        Send packet through transport layer.
        
        Args:
            packet: Packet data to send
            
        Returns:
            True if send successful, False otherwise
        """
        # Check both client and transport connection state
        if not self._connected or not self._transport.connected:
            self._LOGGER.debug("Cannot send packet: not connected")
            # Sync client state with transport state
            if not self._transport.connected:
                self._connected = False
            return False
        
        try:
            success = await self._transport.send_packet(packet)
            if success:
                self._LOGGER.debug(f"Sent packet: {packet.hex()}")
                return True
            else:
                # If send failed, sync connection state
                if not self._transport.connected:
                    self._connected = False
                self._LOGGER.debug("Failed to send packet")
                return False
        except Exception as e:
            self._LOGGER.error(f"Error sending packet: {e}")
            # Mark as disconnected on exception
            self._connected = False
            return False

    async def start_luxpower_client_daemon(self):
        """Start the main client daemon loop."""
        while not self._stop_client:
            # Sync client connection state with transport state
            if self._connected and not self._transport.connected:
                self._LOGGER.debug("Client connection state out of sync with transport, marking as disconnected")
                self._connected = False
            
            if not self._connected:
                try:
                    self._LOGGER.info("luxpower daemon: Connecting to lux power server")
                    success = await self.connect()
                    if success and self._transport.connected:
                        self._LOGGER.info("luxpower daemon: Connected to lux power server")
                    else:
                        self._LOGGER.warning("luxpower daemon: Connection failed or not fully established")
                        self._connected = False
                        await asyncio.sleep(10)
                        continue
                except Exception as e:
                    self._LOGGER.error(f"Connection failed in daemon: {e}")
                    self._connected = False
                    await asyncio.sleep(10)
                    continue

                await asyncio.sleep(1)
                # Re-check connection state before proceeding
                if self._connected and self._transport.connected:
                    if not self._connect_twice:
                        self._connect_twice = False
                        self._LOGGER.info("Refreshing Lux Platforms With Data")
                        if self._connect_after_failure:
                            try:
                                await asyncio.wait_for(asyncio.sleep(60), timeout=65)
                            except asyncio.TimeoutError:
                                self._LOGGER.warning("Sleep timeout exceeded")
                            self._connect_after_failure = False
                        await self.do_refresh_data_registers(10)
                        await self.do_refresh_hold_registers()
                        
                        # Log performance stats after initial data refresh
                        self.log_performance_stats()
                    else:
                        self._connect_twice = True
                        await self.reconnect()

            # Log performance stats periodically during normal operation
            await asyncio.sleep(10)
            if self._connected and self._transport.connected and len(self._response_times) > 0:
                # Only log if we have some response time data
                self.log_performance_stats()
        self._LOGGER.info("Stop Called - Exiting start_luxpower_client_daemon")

    async def request_data_bank(self, address_bank):
        """Request data from a specific register bank."""
        # Check connection state before attempting to send
        if not self._connected or not self._transport.connected:
            self._LOGGER.debug(f"Cannot request data bank {address_bank}: not connected")
            return
        
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        start_register = address_bank * 40
        
        # Prepare packet outside lock to minimize critical section
        try:
            packet = self.lxpPacket.prepare_packet_for_read(
                start_register, number_of_registers, type=LXPPacket.READ_INPUT)
        except Exception as e:
            self._LOGGER.error("Prepare packet failed request_data_bank %s", e)
            return
        
        await self._acquire_lock()
        try:
            # Re-check connection state after acquiring lock
            if not self._connected or not self._transport.connected:
                self._LOGGER.debug(f"Connection lost while waiting for lock in request_data_bank {address_bank}")
                return
                
            self._LOGGER.debug("request_data_bank for address_bank: %s", address_bank)
            # Record request start time for performance tracking
            self._record_request_start(address_bank)
            success = await self._transport.send_packet(packet)
            if success:
                self._LOGGER.debug(
                    f"Packet Written for getting {serial} DATA registers address_bank {address_bank} , {number_of_registers}")
            else:
                self._LOGGER.debug("Failed to send data bank request")
                # Sync connection state if send failed
                if not self._transport.connected:
                    self._connected = False
        except Exception as e:
            self._LOGGER.error("Exception request_data_bank %s", e)
            # Mark as disconnected on exception
            if isinstance(e, (ConnectionError, OSError)):
                self._connected = False
        finally:
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()

    async def do_refresh_data_registers(self, bank_count):
        """Refresh data registers for specified number of banks."""
        if not self._connected:
            return

        # Validate bank count
        from ..LXPPacket import validate_bank_count
        bank_count = validate_bank_count(bank_count)

        log_marker = make_log_marker(
            self.serial_number, self.dongle_serial, "do_refresh_data_registers"
        )

        try:
            for address_bank in range(0, bank_count):
                self._LOGGER.info(
                    f"{log_marker} call request_data - Bank: {address_bank}"
                )
                await self.request_data_bank(address_bank)
                # small inter-bank spacing to avoid bursts
                if self._inter_bank_delay > 0:
                    try:
                        await asyncio.sleep(self._inter_bank_delay)
                    except Exception:
                        pass
        except TimeoutError:
            pass

    async def request_hold_bank(self, address_bank):
        """Request holding registers from a specific bank."""
        # Check connection state before attempting to send
        if not self._connected or not self._transport.connected:
            self._LOGGER.debug(f"Cannot request hold bank {address_bank}: not connected")
            return
        
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        start_register = address_bank * 40
        
        # Prepare packet outside lock to minimize critical section
        try:
            packet = self.lxpPacket.prepare_packet_for_read(
                start_register, number_of_registers, type=LXPPacket.READ_HOLD)
        except Exception as e:
            self._LOGGER.error("Prepare packet failed request_hold_bank %s", e)
            return

        await self._acquire_lock()
        try:
            # Re-check connection state after acquiring lock
            if not self._connected or not self._transport.connected:
                self._LOGGER.debug(f"Connection lost while waiting for lock in request_hold_bank {address_bank}")
                return
                
            self._LOGGER.debug(
                f"request_hold_bank for {serial} address_bank: {address_bank} , {number_of_registers}"
            )
            # Record request start time for performance tracking
            self._record_request_start(address_bank)
            success = await self._transport.send_packet(packet)
            if success:
                self._LOGGER.debug(
                    f"Packet Written for getting {serial} HOLD registers address_bank {address_bank} , {number_of_registers}")
            else:
                self._LOGGER.debug("Failed to send hold bank request")
                # Sync connection state if send failed
                if not self._transport.connected:
                    self._connected = False
        except Exception as e:
            self._LOGGER.error("Exception request_hold_bank %s", e)
            # Mark as disconnected on exception
            if isinstance(e, (ConnectionError, OSError)):
                self._connected = False
        finally:
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()

    async def do_refresh_hold_registers(self):
        """Refresh holding registers."""
        log_marker = make_log_marker(
            self.serial_number, self.dongle_serial, "do_refresh_hold_registers"
        )

        try:
            self._warn_registers = True
            # Request all 10 banks (0-9) with 40-register blocks
            for address_bank in range(0, 10):
                self._LOGGER.info(
                    f"{log_marker} call request_hold - Bank: {address_bank}"
                )
                await self.request_hold_bank(address_bank)
                if self._inter_bank_delay > 0:
                    try:
                        await asyncio.sleep(self._inter_bank_delay)
                    except Exception:
                        pass
        except TimeoutError:
            pass
        finally:
            self._warn_registers = False

        self._LOGGER.debug(f"{log_marker} finish")

    def stop_client(self):
        """Stop the client and disconnect."""
        self._LOGGER.info("stop_client called")
        self._stop_client = True
        # Don't create a new task here - let the caller handle async disconnect
        self._LOGGER.info("stop client finished")

    async def reconnect(self):
        """Reconnect to the inverter."""
        self._LOGGER.info("Reconnecting to Luxpower server")
        await self.disconnect()
        self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
        self._connected = False
        self._LOGGER.info("reconnect client finished")

    async def _acquire_lock(self):
        """Acquire lock with exponential backoff retry."""
        max_retries = 3
        base_delay = 1.0
        
        for attempt in range(max_retries):
            try:
                await asyncio.wait_for(self._lxp_request_lock.acquire(), timeout=10)
                return
            except asyncio.TimeoutError:
                if attempt < max_retries - 1:
                    delay = base_delay * (2 ** attempt)
                    self._LOGGER.warning(f"Lock acquisition timeout, retrying in {delay}s (attempt {attempt + 1}/{max_retries})")
                    await asyncio.sleep(delay)
                else:
                    self._LOGGER.error("Failed to acquire lock after maximum retries")
                    # Force release if stuck
                    if self._lxp_request_lock.locked():
                        self._lxp_request_lock.release()
                    raise

    async def _wait_for_value(self, register):
        """Wait for a single register value response."""
        try:
            value = await asyncio.wait_for(self._lxp_single_register_result, timeout=10)
            return value.get(register)
        except asyncio.TimeoutError:
            return None
        finally:
            self._lxp_single_register_result = None

    async def restart(self):
        """Restart the LuxPower inverter."""
        self._LOGGER.info("Restarting Luxpower Inverter")
        self._LOGGER.debug("Register to be written 11 with value 128")
        await self.write(11, 128)  # WRITE_SINGLE to register 11
        await self.disconnect()
        self._connected = False
        self._LOGGER.info("restart inverter finished")

    async def reset_all_settings(self):
        """Reset all LuxPower inverter settings to defaults."""
        self._LOGGER.info("Resetting Luxpower Inverter settings to defaults")

        lxpPacket = LXPPacket(
            debug=True,
            dongle_serial=self.dongle_serial,
            serial_number=self.serial_number,
        )

        self._LOGGER.warning("Register to be written 11 with value 2")
        await self.write(11, 2)
        await self.disconnect()
        self._connected = False
        self._LOGGER.info("reset inverter settings finished")

    async def write(self, register, value, max_retries=3):
        """Write to register with retry logic."""
        for attempt in range(max_retries):
            await self._acquire_lock()
            try:
                self._lxp_single_register_result = asyncio.Future()
                packet = self.lxpPacket.prepare_packet_for_write(register, value)
                
                success = await self._transport.send_packet(packet)
                if not success:
                    raise Exception("Failed to send write packet")
                
                self._LOGGER.info(f"write {register} with value {value} (attempt {attempt + 1})")

                result = await self._wait_for_value(register)
                if result is not None:
                    return result
                elif attempt < max_retries - 1:
                    self._LOGGER.warning(f"Write attempt {attempt + 1} failed, retrying...")
                    await asyncio.sleep(1)
                else:
                    self._LOGGER.error(f"Write failed after {max_retries} attempts")
                    return None
            except Exception as e:
                self._LOGGER.error(
                    f"Error writing register {register} with value {value}: {e}"
                )
                if attempt < max_retries - 1:
                    await asyncio.sleep(1)
                else:
                    return None
            finally:
                # Always release lock to prevent deadlock
                if self._lxp_request_lock.locked():
                    self._lxp_request_lock.release()

    async def write_register(self, register: int, value: int, mask: int | None = None):
        """
        Write a register with optional bitmask merge.

        If mask is provided, read current value and merge only masked bits
        before writing. The provided value should already be aligned/shifted.

        Args:
            register: Register address
            value: Desired raw value (pre-shifted as needed)
            mask: Optional bit mask specifying which bits to modify

        Returns:
            The read-back value on success, or None on failure
        """
        try:
            new_value = value
            if isinstance(mask, int):
                current = await self.read(register)
                if current is None:
                    return None
                new_value = (current & (~mask & 0xFFFF)) | (value & mask & 0xFFFF)

            return await self.write(register, new_value)
        except Exception as e:
            self._LOGGER.error(f"write_register failed for reg {register}: {e}")
            return None

    async def read(self, register, type=LXPPacket.READ_HOLD, max_retries=3):
        """Read from register with retry logic."""
        for attempt in range(max_retries):
            await self._acquire_lock()
            try:
                self._lxp_single_register_result = asyncio.Future()
                packet = self.lxpPacket.prepare_packet_for_read(register, 1, type)
                
                success = await self._transport.send_packet(packet)
                if not success:
                    raise Exception("Failed to send read packet")
                
                self._LOGGER.info(f"read {register} value with type {type} (attempt {attempt + 1})")
                
                result = await self._wait_for_value(register)
                if result is not None:
                    return result
                elif attempt < max_retries - 1:
                    self._LOGGER.warning(f"Read attempt {attempt + 1} failed, retrying...")
                    await asyncio.sleep(1)
                else:
                    self._LOGGER.error(f"Read failed after {max_retries} attempts")
                    return None
            except Exception as e:
                self._LOGGER.error(
                    f"Error reading register {register}: {e}"
                )
                if attempt < max_retries - 1:
                    await asyncio.sleep(1)
                else:
                    return None
            finally:
                # Always release lock to prevent deadlock
                if self._lxp_request_lock.locked():
                    self._lxp_request_lock.release()

    async def synctime(self, do_set_time):
        """Synchronize time with the LuxPower inverter."""
        self._LOGGER.info("Syncing Time to Luxpower Inverter")

        self._LOGGER.info("Register to be read 12")
        read_value = await self.read(12)

        if read_value is not None:
            # Read has been successful - use read value
            self._LOGGER.info(
                f"READ Register OK - Using INVERTER Register: 12 Value: {read_value}"
            )
            old12 = read_value
            oldmonth = int(old12 / 256)
            oldyear = int((old12 - (oldmonth * 256)) + 2000)
            self._LOGGER.info(
                "Old12: %s, Oldmonth: %s, Oldyear: %s", old12, oldmonth, oldyear
            )
        else:
            # Read has been UNsuccessful
            self._LOGGER.error("Cannot READ Register: 12 - Aborting")
            return

        self._LOGGER.info("Register to be read 13")
        read_value = await self.read(13)

        if read_value is not None:
            # Read has been successful - use read value
            self._LOGGER.info(
                f"READ Register OK - Using INVERTER Register: 13 Value: {read_value}"
            )
            old13 = read_value
            oldhour = int(old13 / 256)
            oldday = int(old13 - (oldhour * 256))
            self._LOGGER.info(
                "Old13: %s, Oldhour: %s, Oldday: %s", old13, oldhour, oldday
            )
        else:
            # Read has been UNsuccessful
            self._LOGGER.error("Cannot READ Register: 13 - Aborting")
            return

        self._LOGGER.info("Register to be read 14")
        read_value = await self.read(14)

        if read_value is not None:
            # Read has been successful - use read value
            self._LOGGER.info(
                f"READ Register OK - Using INVERTER Register: 14 Value: {read_value}"
            )
            old14 = read_value
            oldsecond = int(old14 / 256)
            oldminute = int(old14 - (oldsecond * 256))
            self._LOGGER.info(
                "Old14: %s, Oldsecond: %s, Oldminute: %s", old14, oldsecond, oldminute
            )
        else:
            # Read has been UNsuccessful
            self._LOGGER.error("Cannot READ Register: 14 - Aborting")
            return

        was = datetime.datetime(
            int(oldyear),
            int(oldmonth),
            int(oldday),
            int(oldhour),
            int(oldminute),
            int(oldsecond),
        )
        self._LOGGER.info(
            "was: %s %s %s %s %s %s",
            was.year,
            was.month,
            was.day,
            was.hour,
            was.minute,
            was.second,
        )

        now = datetime.datetime.now()
        self._LOGGER.info(
            "now: %s %s %s %s %s %s",
            now.year,
            now.month,
            now.day,
            now.hour,
            now.minute,
            now.second,
        )

        self._LOGGER.warning(
            f"{str(self.serial_number)} Old Time: {was}, New Time: {now}, Seconds Diff: {abs(now - was)} - Updating: {str(do_set_time)}"
        )

        if do_set_time:
            new_month_year = (now.month * 256) + (now.year - 2000)
            self._LOGGER.info(f"Register to be written 12 with value {new_month_year}")
            await self.write(12, new_month_year)

            new_hour_day = (now.hour * 256) + (now.day)
            self._LOGGER.info(f"Register to be written 13 with value {new_hour_day}")
            await self.write(13, new_hour_day)

            write_time_allowance = 0
            new_seconds_minutes = ((now.second + write_time_allowance) * 256) + (
                now.minute
            )

            self._LOGGER.info(
                f"Register to be written 14 with value {new_seconds_minutes}"
            )
            await self.write(14, new_seconds_minutes)
        else:
            self._LOGGER.debug("Inverter Time Update Disabled By Parameter")

        self._LOGGER.debug("synctime finished")
