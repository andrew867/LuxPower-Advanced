import asyncio
import datetime
import logging
import struct
from typing import Optional
from homeassistant.core import HomeAssistant

from ..LXPPacket import LXPPacket
from ..helpers import Event, LuxPowerError, LuxPowerConnectionError, validate_ip_address, validate_port

# Security constants
MAX_CONNECTION_ATTEMPTS = 3
CONNECTION_TIMEOUT = 10.0
OPERATION_TIMEOUT = 30.0
MAX_CONCURRENT_OPERATIONS = 5
HEARTBEAT_TIMEOUT = 5.0

def make_log_marker(serial, dongle, tag):
    """Create log marker with validation."""
    try:
        if not isinstance(serial, bytes) or not isinstance(dongle, bytes):
            return f"INVALID_PARAMS {tag}"
        
        now = datetime.datetime.now()
        marker = str(int((now - now.replace(hour=0, minute=0, second=0,
                     microsecond=0)).total_seconds() * 1000)).zfill(8)
        marker = marker + " " + serial.decode('ascii', errors='ignore') + "/" + dongle.decode('ascii', errors='ignore') + " " + tag
        return marker
    except Exception:
        return f"ERROR_MARKER {tag}"


class ConnectionManager:
    """Manages connection state and cleanup."""
    
    def __init__(self):
        self.connected = False
        self.transport = None
        self.connection_lock = asyncio.Lock()
        self.operation_semaphore = asyncio.Semaphore(MAX_CONCURRENT_OPERATIONS)
        
    async def set_transport(self, transport):
        """Set transport with proper locking."""
        async with self.connection_lock:
            if self.transport and not self.transport.is_closing():
                try:
                    self.transport.close()
                except Exception as e:
                    logging.getLogger(__name__).debug(f"Error closing old transport: {e}")
            self.transport = transport
            self.connected = transport is not None
            
    async def close(self):
        """Safely close connection."""
        async with self.connection_lock:
            self.connected = False
            if self.transport:
                try:
                    if not self.transport.is_closing():
                        self.transport.close()
                except Exception as e:
                    logging.getLogger(__name__).debug(f"Error closing transport: {e}")
                finally:
                    self.transport = None
                    
    def is_connected(self):
        """Check if connection is active."""
        return (self.connected and 
                self.transport and 
                not self.transport.is_closing())


class LuxPowerClient(asyncio.Protocol):
    """Secure LuxPower client with proper error handling."""

    def __init__(self, hass: HomeAssistant, server: str, port: str, dongle_serial: str, serial_number: str, events: Event, respond_to_heartbeat: bool):
        # Validate inputs
        if not validate_ip_address(server):
            raise LuxPowerConnectionError(f"Invalid server address: {server}")
        if not validate_port(int(port)):
            raise LuxPowerConnectionError(f"Invalid port: {port}")
        if not isinstance(dongle_serial, bytes) or len(dongle_serial) > 20:
            raise ValueError("Invalid dongle serial")
        if not isinstance(serial_number, bytes) or len(serial_number) > 20:
            raise ValueError("Invalid serial number")
            
        self.hass = hass
        self.server = server
        self.port = int(port)
        self.dongle_serial = dongle_serial
        self.serial_number = serial_number
        self.events = events
        self._warn_registers = False
        self._stop_client = False
        self._connect_twice = False
        self._connect_after_failure = False
        self._already_processing = False
        self._respond_to_heartbeat = respond_to_heartbeat
        self._LOGGER = logging.getLogger(__name__)
        
        # Secure connection management
        self.connection_manager = ConnectionManager()
        self._lxp_request_lock = asyncio.Lock()
        self._lxp_single_register_result = None
        self._connection_attempts = 0
        self._last_heartbeat = None
        
        try:
            self.lxpPacket = LXPPacket(
                debug=False, dongle_serial=dongle_serial, serial_number=serial_number)
        except Exception as e:
            raise LuxPowerError(f"Failed to initialize LXP packet handler: {e}")

    def factory(self):
        """Returns reference to itself for using in protocol_factory."""
        return self

    def connection_made(self, transport):
        """Handle new connection with security measures."""
        try:
            _peername = transport.get_extra_info("peername")
            if _peername:
                self._LOGGER.info("Connected to LUXPower Server: %s", _peername)
            else:
                self._LOGGER.warning("Connected to LUXPower Server: unknown peer")
            
            # Schedule transport update
            asyncio.create_task(self.connection_manager.set_transport(transport))
            self._connection_attempts = 0
            self._last_heartbeat = datetime.datetime.now()
            
            # Release any waiting locks
            if self._lxp_request_lock.locked():
                try:
                    self._lxp_request_lock.release()
                except RuntimeError:
                    pass  # Lock already released
                    
        except Exception as e:
            self._LOGGER.error(f"Error in connection_made: {e}")

    def connection_lost(self, exc: Optional[Exception]) -> None:
        """Handle connection loss with proper cleanup."""
        try:
            self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
            
            # Schedule connection cleanup
            asyncio.create_task(self.connection_manager.close())
            
            if exc:
                self._LOGGER.error("Connection lost with exception: %s", exc)
            else:
                self._LOGGER.error("Connection lost: Disconnected from Luxpower server")
                
            # Clean up any pending operations
            if self._lxp_single_register_result and not self._lxp_single_register_result.done():
                self._lxp_single_register_result.cancel()
                self._lxp_single_register_result = None
                
        except Exception as e:
            self._LOGGER.error(f"Error in connection_lost: {e}")

    async def _acquire_lock(self):
        """Acquire lock with timeout and proper error handling."""
        try:
            await asyncio.wait_for(self._lxp_request_lock.acquire(), timeout=OPERATION_TIMEOUT)
        except asyncio.TimeoutError:
            self._LOGGER.error("Timeout acquiring request lock")
            raise LuxPowerConnectionError("Operation timeout")
        except Exception as e:
            self._LOGGER.error(f"Error acquiring lock: {e}")
            raise

    def _release_lock(self):
        """Safely release lock."""
        try:
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()
        except RuntimeError:
            pass  # Lock already released

    def data_received(self, data):
        """Process received data with comprehensive error handling."""
        try:
            self._process_data_safely(data)
        except Exception as e:
            self._LOGGER.error(f"Error processing received data: {e}")
            self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")

    def _process_data_safely(self, data):
        """Safely process received data."""
        if not isinstance(data, bytes) or len(data) == 0:
            self._LOGGER.error("Invalid data received")
            return
            
        if len(data) > 2048:  # Maximum reasonable packet size
            self._LOGGER.error(f"Received oversized packet: {len(data)} bytes")
            return

        self._LOGGER.debug("Inverter: %s", self.lxpPacket.serial_number)
        self._LOGGER.debug("Received %d bytes", len(data))

        packet_remains = data
        packet_remains_length = len(packet_remains)
        frame_number = 0
        max_frames = 10  # Prevent infinite loops

        while packet_remains_length > 0 and frame_number < max_frames:
            frame_number += 1
            if frame_number > 1:
                self._LOGGER.debug("*** Multi-Frame *** : %s", frame_number)

            # Validate minimum frame size
            if packet_remains_length < 6:
                self._LOGGER.error("Frame too small")
                break

            try:
                prefix = packet_remains[0:2]
                if prefix != self.lxpPacket.prefix:
                    self._LOGGER.error("Invalid Start Of Packet Prefix %s", prefix)
                    break

                frame_length_remaining = struct.unpack("H", packet_remains[4:6])[0]
                frame_length_calced = frame_length_remaining + 6
                
                # Validate frame length
                if frame_length_calced > packet_remains_length:
                    self._LOGGER.error("Frame length exceeds remaining data")
                    break
                    
                if frame_length_calced > 2048:
                    self._LOGGER.error("Frame too large")
                    break

                this_frame = packet_remains[0:frame_length_calced]
                packet_remains = packet_remains[frame_length_calced:]
                packet_remains_length = len(packet_remains)

                self._process_frame(this_frame)
                
            except (struct.error, IndexError) as e:
                self._LOGGER.error(f"Error parsing frame: {e}")
                break
            except Exception as e:
                self._LOGGER.error(f"Unexpected error processing frame: {e}")
                break

    def _process_frame(self, frame):
        """Process individual frame with error handling."""
        try:
            result = self.lxpPacket.parse_packet(frame)
            
            if self._lxp_request_lock.locked() and self.lxpPacket.tcp_function != self.lxpPacket.HEARTBEAT:
                self._release_lock()

            if not self.lxpPacket.packet_error and result:
                self._handle_parsed_packet(result)
            else:
                self._LOGGER.debug("Packet parsing failed or no result")
                
        except Exception as e:
            self._LOGGER.error(f"Error processing frame: {e}")

    def _handle_parsed_packet(self, result):
        """Handle successfully parsed packet."""
        try:
            if self.lxpPacket.tcp_function == self.lxpPacket.HEARTBEAT:
                self._handle_heartbeat()
            elif self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                self._handle_read_input(result)
            elif self.lxpPacket.device_function in [self.lxpPacket.READ_HOLD, self.lxpPacket.WRITE_SINGLE]:
                self._handle_register_operation(result)
        except Exception as e:
            self._LOGGER.error(f"Error handling parsed packet: {e}")

    def _handle_heartbeat(self):
        """Handle heartbeat packet."""
        self._last_heartbeat = datetime.datetime.now()
        if self._respond_to_heartbeat and self.connection_manager.is_connected():
            try:
                # Echo back the heartbeat
                if hasattr(self, '_last_heartbeat_data'):
                    self.connection_manager.transport.write(self._last_heartbeat_data)
            except Exception as e:
                self._LOGGER.error(f"Error responding to heartbeat: {e}")

    def _handle_read_input(self, result):
        """Handle read input packets."""
        register = self.lxpPacket.register
        number_of_registers = int(len(result.get("value", "")) / 2)
        
        event_data = {"data": result.get("thesedata", {})}
        
        # Fire appropriate events based on register range
        if register == 0 and number_of_registers == 40:
            self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
        elif register == 40 and number_of_registers == 40:
            self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
        elif register == 80 and number_of_registers == 40:
            self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
        elif register == 120 and number_of_registers == 40:
            self.hass.bus.fire(self.events.EVENT_DATA_BANK3_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
        elif register == 160 and number_of_registers == 40:
            self.hass.bus.fire(self.events.EVENT_DATA_BANK4_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
        elif register == 0 and number_of_registers == 127:
            self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)

    def _handle_register_operation(self, result):
        """Handle register read/write operations."""
        register = self.lxpPacket.register
        number_of_registers = int(len(result.get("value", "")) / 2)

        if (number_of_registers == 1 and 
            self._lxp_single_register_result is not None and 
            not self._lxp_single_register_result.done()):
            try:
                self._lxp_single_register_result.set_result(result.get("thesereg", {}))
            except Exception as e:
                self._LOGGER.error(f"Error setting register result: {e}")

        event_data = {"registers": result.get("thesereg", {})}
        
        # Fire appropriate register events
        if 0 <= register <= 39:
            self.hass.bus.fire(self.events.EVENT_REGISTER_BANK0_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_REGISTER_21_RECEIVED, event_data)
        elif 40 <= register <= 79:
            self.hass.bus.fire(self.events.EVENT_REGISTER_BANK1_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_REGISTER_21_RECEIVED, event_data)
        elif 80 <= register <= 119:
            self.hass.bus.fire(self.events.EVENT_REGISTER_BANK2_RECEIVED, event_data)
            self.hass.bus.fire(self.events.EVENT_REGISTER_21_RECEIVED, event_data)
        elif 120 <= register <= 159:
            self.hass.bus.fire(self.events.EVENT_REGISTER_BANK3_RECEIVED, event_data)
        elif 160 <= register <= 199:
            self.hass.bus.fire(self.events.EVENT_REGISTER_BANK4_RECEIVED, event_data)
        elif 200 <= register <= 239:
            self.hass.bus.fire(self.events.EVENT_REGISTER_BANK5_RECEIVED, event_data)

    async def start_luxpower_client_daemon(self):
        """Main client daemon with improved error handling."""
        while not self._stop_client:
            try:
                if not self.connection_manager.is_connected():
                    await self._attempt_connection()
                    
                await asyncio.sleep(10)
                
                # Check for heartbeat timeout
                if self._last_heartbeat:
                    time_since_heartbeat = datetime.datetime.now() - self._last_heartbeat
                    if time_since_heartbeat.total_seconds() > 300:  # 5 minutes
                        self._LOGGER.warning("No heartbeat received for 5 minutes, reconnecting")
                        await self.reconnect()
                        
            except Exception as e:
                self._LOGGER.error(f"Error in client daemon: {e}")
                await asyncio.sleep(10)
                
        self._LOGGER.info("Stop Called - Exiting start_luxpower_client_daemon")

    async def _attempt_connection(self):
        """Attempt connection with retry logic."""
        if self._connection_attempts >= MAX_CONNECTION_ATTEMPTS:
            self._LOGGER.error("Max connection attempts reached")
            await asyncio.sleep(60)  # Wait before resetting
            self._connection_attempts = 0
            return
            
        try:
            self._LOGGER.info("luxpower daemon: Connecting to lux power server")
            self._connection_attempts += 1
            
            connect_task = self.hass.loop.create_connection(self.factory, self.server, self.port)
            await asyncio.wait_for(connect_task, timeout=CONNECTION_TIMEOUT)
            
            self._LOGGER.info("luxpower daemon: Connected to lux power server")
            
            # Initial data refresh after connection
            if self.connection_manager.is_connected():
                await asyncio.sleep(1)
                if not self._connect_twice:
                    self._connect_twice = True
                    if self._connect_after_failure:
                        await asyncio.sleep(60)
                        self._connect_after_failure = False
                    await self._initial_refresh()
                else:
                    await self.reconnect()
                    
        except asyncio.TimeoutError:
            self._LOGGER.error("Connection timeout")
        except ConnectionRefusedError:
            self._LOGGER.error("Connection refused")
        except Exception as e:
            self._LOGGER.error(f"Connection failed: {e}")

    async def _initial_refresh(self):
        """Perform initial data refresh after connection."""
        try:
            await self.do_refresh_data_registers(3)
            await asyncio.sleep(2)
            await self.do_refresh_hold_registers()
        except Exception as e:
            self._LOGGER.error(f"Error in initial refresh: {e}")

    async def request_data_bank(self, address_bank):
        """Request data bank with timeout and error handling."""
        if not self.connection_manager.is_connected():
            raise LuxPowerConnectionError("Not connected")
            
        if not isinstance(address_bank, int) or address_bank < 0 or address_bank > 10:
            raise ValueError(f"Invalid address bank: {address_bank}")

        async with self.connection_manager.operation_semaphore:
            await self._acquire_lock()
            try:
                serial = self.lxpPacket.serial_number
                number_of_registers = 40
                start_register = address_bank * 40

                self._LOGGER.debug("request_data_bank for address_bank: %s", address_bank)
                packet = self.lxpPacket.prepare_packet_for_read(
                    start_register, number_of_registers, type=LXPPacket.READ_INPUT)
                
                if not self.connection_manager.transport:
                    raise LuxPowerConnectionError("Transport not available")
                    
                self.connection_manager.transport.write(packet)
                self._LOGGER.debug(
                    f"Packet Written for getting {serial} DATA registers address_bank {address_bank}, {number_of_registers}")
                    
            except Exception as e:
                self._release_lock()
                self._LOGGER.error(f"Exception request_data_bank: {e}")
                raise

    async def do_refresh_data_registers(self, bank_count):
        """Refresh data registers with proper error handling."""
        if not self.connection_manager.is_connected():
            self._LOGGER.warning("Not connected, skipping data refresh")
            return

        if not isinstance(bank_count, int) or bank_count < 1 or bank_count > 10:
            raise ValueError(f"Invalid bank count: {bank_count}")

        log_marker = make_log_marker(
            self.serial_number, self.dongle_serial, "do_refresh_data_registers")

        try:
            for address_bank in range(0, bank_count):
                self._LOGGER.info(f"{log_marker} call request_data - Bank: {address_bank}")
                await self.request_data_bank(address_bank)
                await asyncio.sleep(0.5)  # Small delay between requests
        except Exception as e:
            self._LOGGER.error(f"Error refreshing data registers: {e}")
            raise

    async def request_hold_bank(self, address_bank):
        """Request hold bank with validation."""
        if not self.connection_manager.is_connected():
            raise LuxPowerConnectionError("Not connected")
            
        if not isinstance(address_bank, int) or address_bank < 0 or address_bank > 10:
            raise ValueError(f"Invalid address bank: {address_bank}")

        async with self.connection_manager.operation_semaphore:
            await self._acquire_lock()
            try:
                serial = self.lxpPacket.serial_number
                number_of_registers = 40
                start_register = address_bank * 40
                if address_bank == 6:
                    start_register = 560

                self._LOGGER.debug(
                    f"request_hold_bank for {serial} address_bank: {address_bank}, {number_of_registers}")
                packet = self.lxpPacket.prepare_packet_for_read(
                    start_register, number_of_registers, type=LXPPacket.READ_HOLD)
                
                if not self.connection_manager.transport:
                    raise LuxPowerConnectionError("Transport not available")
                    
                self.connection_manager.transport.write(packet)
                self._LOGGER.debug(
                    f"Packet Written for getting {serial} HOLD registers address_bank {address_bank}, {number_of_registers}")
                    
            except Exception as e:
                self._release_lock()
                self._LOGGER.error(f"Exception request_hold_bank: {e}")
                raise

    async def do_refresh_hold_registers(self):
        """Refresh hold registers with error handling."""
        log_marker = make_log_marker(
            self.serial_number, self.dongle_serial, "do_refresh_hold_registers")

        try:
            self._warn_registers = True
            for address_bank in range(0, 5):
                self._LOGGER.info(f"{log_marker} call request_hold - Bank: {address_bank}")
                await self.request_hold_bank(address_bank)
                await asyncio.sleep(0.5)
                
            # Extended registers
            self._LOGGER.info(f"{log_marker} call request_hold - EXTENDED Bank: 5")
            await self.request_hold_bank(5)
            
        except Exception as e:
            self._LOGGER.error(f"Error refreshing hold registers: {e}")
            raise
        finally:
            self._warn_registers = False

    def stop_client(self):
        """Stop client with proper cleanup."""
        self._LOGGER.info("stop_client called")
        self._stop_client = True
        asyncio.create_task(self.connection_manager.close())
        self._LOGGER.info("stop client finished")

    async def reconnect(self):
        """Reconnect with proper cleanup."""
        self._LOGGER.info("Reconnecting to Luxpower server")
        await self.connection_manager.close()
        self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
        self._connect_after_failure = True
        self._LOGGER.info("reconnect client finished")

    async def _wait_for_value(self, register):
        """Wait for register value with timeout."""
        try:
            async with asyncio.timeout(OPERATION_TIMEOUT):
                value = await self._lxp_single_register_result
                return value.get(register)
        except asyncio.TimeoutError:
            self._LOGGER.error("Timeout waiting for register value")
            return None
        except Exception as e:
            self._LOGGER.error(f"Error waiting for register value: {e}")
            return None
        finally:
            self._lxp_single_register_result = None

    async def restart(self):
        """Restart inverter with validation."""
        self._LOGGER.warning("Restarting Luxpower Inverter")
        self._LOGGER.warning("Register to be written 11 with value 128")
        
        try:
            await self.write(11, 128)
            await self.connection_manager.close()
            self._LOGGER.warning("restart inverter finished")
        except Exception as e:
            self._LOGGER.error(f"Error restarting inverter: {e}")
            raise

    async def reset_all_settings(self):
        """Reset inverter settings with validation."""
        self._LOGGER.warning("Resetting Luxpower Inverter settings to defaults")
        
        try:
            self._LOGGER.warning("Register to be written 11 with value 2")
            await self.write(11, 2)
            await self.connection_manager.close()
            self._LOGGER.warning("reset inverter settings finished")
        except Exception as e:
            self._LOGGER.error(f"Error resetting inverter settings: {e}")
            raise

    async def write(self, register, value):
        """Write register with comprehensive validation."""
        if not isinstance(register, int) or register < 0 or register > 65535:
            raise ValueError(f"Invalid register: {register}")
        if not isinstance(value, int) or value < 0 or value > 65535:
            raise ValueError(f"Invalid value: {value}")
            
        if not self.connection_manager.is_connected():
            raise LuxPowerConnectionError("Not connected")

        async with self.connection_manager.operation_semaphore:
            await self._acquire_lock()
            try:
                self._lxp_single_register_result = asyncio.Future()
                packet = self.lxpPacket.prepare_packet_for_write(register, value)
                
                if not self.connection_manager.transport:
                    raise LuxPowerConnectionError("Transport not available")
                    
                self.connection_manager.transport.write(packet)
                self._LOGGER.info(f"write {register} with value {value}")

                return await self._wait_for_value(register)
                
            except Exception as e:
                self._release_lock()
                if self._lxp_single_register_result and not self._lxp_single_register_result.done():
                    self._lxp_single_register_result.cancel()
                    self._lxp_single_register_result = None
                self._LOGGER.error(f"Exception during writing register {register} with value {value}: {e}")
                raise

    async def read(self, register, type=LXPPacket.READ_HOLD):
        """Read register with validation."""
        if not isinstance(register, int) or register < 0 or register > 65535:
            raise ValueError(f"Invalid register: {register}")
            
        if not self.connection_manager.is_connected():
            raise LuxPowerConnectionError("Not connected")

        async with self.connection_manager.operation_semaphore:
            await self._acquire_lock()
            try:
                self._lxp_single_register_result = asyncio.Future()
                packet = self.lxpPacket.prepare_packet_for_read(register, 1, type)
                
                if not self.connection_manager.transport:
                    raise LuxPowerConnectionError("Transport not available")
                    
                self.connection_manager.transport.write(packet)
                self._LOGGER.info(f"read {register} value with type {type}")
                
                return await self._wait_for_value(register)
                
            except Exception as e:
                self._release_lock()
                if self._lxp_single_register_result and not self._lxp_single_register_result.done():
                    self._lxp_single_register_result.cancel()
                    self._lxp_single_register_result = None
                self._LOGGER.error(f"Exception during reading register {register}: {e}")
                raise

    async def synctime(self, do_set_time):
        """Sync time with comprehensive validation."""
        self._LOGGER.info("Syncing Time to Luxpower Inverter")

        try:
            # Read current time registers
            old12 = await self.read(12)
            if old12 is None:
                self._LOGGER.warning("Cannot READ Register: 12 - Aborting")
                return

            old13 = await self.read(13)
            if old13 is None:
                self._LOGGER.warning("Cannot READ Register: 13 - Aborting")
                return

            old14 = await self.read(14)
            if old14 is None:
                self._LOGGER.warning("Cannot READ Register: 14 - Aborting")
                return

            # Parse current time
            oldmonth = int(old12 / 256)
            oldyear = int((old12 - (oldmonth * 256)) + 2000)
            oldhour = int(old13 / 256)
            oldday = int(old13 - (oldhour * 256))
            oldsecond = int(old14 / 256)
            oldminute = int(old14 - (oldsecond * 256))

            was = datetime.datetime(int(oldyear), int(oldmonth), int(oldday), 
                                  int(oldhour), int(oldminute), int(oldsecond))
            now = datetime.datetime.now()

            self._LOGGER.warning(
                f"{str(self.serial_number)} Old Time: {was}, New Time: {now}, "
                f"Seconds Diff: {abs(now - was)} - Updating: {str(do_set_time)}")

            if do_set_time:
                # Validate time values before writing
                if not (1 <= now.month <= 12):
                    raise ValueError(f"Invalid month: {now.month}")
                if not (2000 <= now.year <= 2099):
                    raise ValueError(f"Invalid year: {now.year}")
                if not (1 <= now.day <= 31):
                    raise ValueError(f"Invalid day: {now.day}")
                if not (0 <= now.hour <= 23):
                    raise ValueError(f"Invalid hour: {now.hour}")
                if not (0 <= now.minute <= 59):
                    raise ValueError(f"Invalid minute: {now.minute}")
                if not (0 <= now.second <= 59):
                    raise ValueError(f"Invalid second: {now.second}")

                new_month_year = (now.month * 256) + (now.year - 2000)
                self._LOGGER.info(f"Register to be written 12 with value {new_month_year}")
                await self.write(12, new_month_year)

                new_hour_day = (now.hour * 256) + (now.day)
                self._LOGGER.info(f"Register to be written 13 with value {new_hour_day}")
                await self.write(13, new_hour_day)

                write_time_allowance = 0
                new_seconds_minutes = ((now.second + write_time_allowance) * 256) + (now.minute)
                self._LOGGER.info(f"Register to be written 14 with value {new_seconds_minutes}")
                await self.write(14, new_seconds_minutes)
            else:
                self._LOGGER.debug("Inverter Time Update Disabled By Parameter")

            self._LOGGER.debug("synctime finished")
            
        except Exception as e:
            self._LOGGER.error(f"Error syncing time: {e}")
            raise