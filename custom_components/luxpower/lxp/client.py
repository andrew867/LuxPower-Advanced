import asyncio
import datetime
import logging
import struct
from typing import Optional
from homeassistant.core import HomeAssistant

from ..LXPPacket import LXPPacket
from ..helpers import Event


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


class LuxPowerClient(asyncio.Protocol):
    """
    LuxPower inverter communication client.
    
    This class handles the TCP communication protocol with LuxPower inverters,
    managing connection, data exchange, and register operations through the
    inverter's Wi-Fi dongle.
    """

    _transport: asyncio.WriteTransport | None
    _lxp_single_register_result: asyncio.Future | None

    def __init__(
        self,
        hass: HomeAssistant,
        server: str,
        port: str,
        dongle_serial: str,
        serial_number: str,
        events: Event,
        respond_to_heartbeat: bool,
    ):
        self.hass = hass
        self.server = server
        self.port = port
        self.dongle_serial = dongle_serial
        self.serial_number = serial_number
        self.events = events
        self._warn_registers = False
        self._stop_client = False
        self._transport = None
        self._connected = False
        self._connect_twice = False
        self._connect_after_failure = False
        self._already_processing = False
        self._respond_to_heartbeat = respond_to_heartbeat
        self._LOGGER = logging.getLogger(__name__)
        self._lxp_request_lock = asyncio.Lock()
        self._lxp_single_register_result = None
        
        # Retry logic attributes
        self._connection_attempts = 0
        self._max_retry_attempts = 3
        self._retry_delay = 1  # Initial delay in seconds
        self._max_retry_delay = 60  # Maximum delay in seconds
        self.lxpPacket = LXPPacket(
            debug=False, dongle_serial=dongle_serial, serial_number=serial_number
        )

    async def _retry_connection(self, max_attempts: int = None) -> bool:
        """
        Retry connection with exponential backoff.
        
        Args:
            max_attempts: Maximum number of retry attempts (uses self._max_retry_attempts if None)
            
        Returns:
            True if connection successful, False if all attempts failed
        """
        if max_attempts is None:
            max_attempts = self._max_retry_attempts
            
        for attempt in range(max_attempts):
            try:
                self._connection_attempts += 1
                self._LOGGER.info(f"Connection attempt {self._connection_attempts}/{max_attempts}")
                
                # Try to establish connection
                await self._connect()
                if self._connected:
                    self._LOGGER.info("Connection successful")
                    self._retry_delay = 1  # Reset delay on success
                    return True
                    
            except (ConnectionError, OSError, asyncio.TimeoutError) as err:
                self._LOGGER.warning(f"Connection attempt {attempt + 1} failed: {err}")
                
                if attempt < max_attempts - 1:  # Don't wait after last attempt
                    wait_time = min(self._retry_delay * (2 ** attempt), self._max_retry_delay)
                    self._LOGGER.info(f"Waiting {wait_time} seconds before retry...")
                    await asyncio.sleep(wait_time)
                    
            except Exception as err:
                self._LOGGER.error(f"Unexpected error during connection attempt {attempt + 1}: {err}")
                break  # Don't retry on unexpected errors
                    
        self._LOGGER.error(f"All {max_attempts} connection attempts failed")
        return False

    async def _connect(self):
        """Internal connection method."""
        # This would contain the actual connection logic
        # For now, we'll assume it's handled by the existing connection methods
        pass

    def factory(self):
        """
        Returns reference to itself for using in protocol_factory.

        With create_server
        """
        return self

    def connection_made(self, transport):
        """
        Is called as soon as an ISM8 connects to server.

        Description Of Function
        """
        _peername = transport.get_extra_info("peername")
        self._LOGGER.info("Connected to LUXPower Server: %s", _peername)
        self._transport = transport
        self._connected = True

        if self._lxp_request_lock.locked():
            self._lxp_request_lock.release()

    def connection_lost(self, exc: Optional[Exception]) -> None:
        self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
        self._connected = False
        self._LOGGER.error("connection_lost: Disconnected from Luxpower server")
        
        # Force release lock if held to prevent deadlock
        if self._lxp_request_lock.locked():
            self._lxp_request_lock.release()
            self._LOGGER.warning("Force released lock due to connection loss")
            
        # Cancel any pending futures to prevent resource leaks
        if self._lxp_single_register_result and not self._lxp_single_register_result.done():
            self._lxp_single_register_result.cancel()
            self._LOGGER.warning("Cancelled pending register result future due to connection loss")

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

    def data_received(self, data):
        """Handle received data with enhanced error handling."""
        try:
            self._LOGGER.debug("Inverter: %s", self.lxpPacket.serial_number)
            self._LOGGER.debug(data)

            packet_remains = data
            packet_remains_length = len(packet_remains)
            self._LOGGER.debug(
                "TCP OVERALL Packet Remains Length : %s", packet_remains_length
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
                    # protocol_number = struct.unpack("H", packet_remains[2:4])[0]
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
                        self._transport.write(data)
                elif self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                    register = self.lxpPacket.register
                    self._LOGGER.debug("register: %s ", register)
                    number_of_registers = int(len(result.get("value", "")) / 2)
                    self._LOGGER.debug("number_of_registers: %s ", number_of_registers)
                    total_data = {"data": result.get("data", {})}
                    event_data = {"data": result.get("thesedata", {})}
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
                    elif register == 0 and number_of_registers == 127:
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

                elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:  # fmt: skip
                    register = self.lxpPacket.register
                    self._LOGGER.debug("register: %s ", register)
                    number_of_registers = int(len(result.get("value", "")) / 2)

                    if (
                        number_of_registers == 1
                        and self._lxp_single_register_result is not None
                        and not self._lxp_single_register_result.done()
                    ):
                        self._lxp_single_register_result.set_result(
                            result.get("thesereg", {})
                        )

                    total_data = {"registers": result.get("registers", {})}
                    event_data = {"registers": result.get("thesereg", {})}
                    self._LOGGER.debug("EVENT REGISTER: %s ", event_data)
                    if self.lxpPacket.register >= 160 and self._warn_registers:
                        self._LOGGER.warning("REGISTERS: %s ", total_data)
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

                    # self.hass.bus.fire(self.events.EVENT_REGISTER_RECEIVED, event_data)
                    
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

    async def start_luxpower_client_daemon(self):
        while not self._stop_client:
            if not self._connected:
                try:
                    self._LOGGER.info("luxpower daemon: Connecting to lux power server")
                    await self.hass.loop.create_connection(
                        self.factory, self.server, self.port
                    )
                    self._LOGGER.info("luxpower daemon: Connected to lux power server")
                except (ConnectionError, OSError, asyncio.TimeoutError) as e:
                    self._LOGGER.error(
                        f"Connection failed in daemon - retrying in 10 seconds: {e}"
                    )
                except Exception as e:
                    self._LOGGER.error(
                        f"Unexpected error in daemon - retrying in 10 seconds: {e}"
                    )

                await asyncio.sleep(1)
                if self._connected:
                    if not self._connect_twice:
                        self._connect_twice = False
                        self._LOGGER.info("Refreshing Lux Platforms With Data")
                        # await self.hass.services.async_call(DOMAIN, "luxpower_refresh_registers", {"dongle": self.dongle_serial.decode(), "bank_count": 3}, blocking=False)  # fmt: skip
                        # await self.hass.services.async_call(DOMAIN, "luxpower_refresh_holdings", {"dongle": self.dongle_serial.decode()}, blocking=False)  # fmt: skip
                        # self.hass.async_create_task(refreshALLPlatforms(self.hass, dongle=self.dongle_serial.decode()))
                        # self.hass.async_create_task(self.do_refresh_data_registers(3, True))
                        # self.hass.async_create_task(self.do_refresh_hold_registers(True))
                        if self._connect_after_failure:
                            try:
                                await asyncio.wait_for(asyncio.sleep(60), timeout=65)
                            except asyncio.TimeoutError:
                                self._LOGGER.warning("Sleep timeout exceeded")
                            self._connect_after_failure = False
                        await self.do_refresh_data_registers(3)
                        await self.do_refresh_hold_registers()
                    else:
                        self._connect_twice = True
                        await self.reconnect()

            await asyncio.sleep(10)
        self._LOGGER.info("Stop Called - Exiting start_luxpower_client_daemon")

    async def request_data_bank(self, address_bank):
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        start_register = address_bank * 40

        await self._acquire_lock()
        try:
            self._LOGGER.debug("request_data_bank for address_bank: %s", address_bank)
            
            # Check transport availability before write
            if self._transport is None or self._transport.is_closing():
                raise ConnectionError("Transport not available")
                
            packet = self.lxpPacket.prepare_packet_for_read(
                start_register, number_of_registers, type=LXPPacket.READ_INPUT)  # fmt: skip
            self._transport.write(packet)
            self._LOGGER.debug(
                f"Packet Written for getting {serial} DATA registers address_bank {address_bank} , {number_of_registers}")  # fmt: skip
        except Exception as e:
            self._LOGGER.error("Exception request_data_bank %s", e)
        finally:
            # Always release lock to prevent deadlock
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()

    async def do_refresh_data_registers(self, bank_count):
        if not self._connected or self._transport.is_closing():
            return

        log_marker = make_log_marker(
            self.serial_number, self.dongle_serial, "do_refresh_data_registers"
        )

        try:
            for address_bank in range(0, bank_count):
                self._LOGGER.info(
                    f"{log_marker} call request_data - Bank: {address_bank}"
                )
                await self.request_data_bank(address_bank)
        except TimeoutError:
            # await self.reconnect()
            pass

    async def request_hold_bank(self, address_bank):
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        start_register = address_bank * 40
        if address_bank == 6:
            start_register = 560

        await self._acquire_lock()
        try:
            self._LOGGER.debug(
                f"request_hold_bank for {serial} address_bank: {address_bank} , {number_of_registers}"
            )
            
            # Check transport availability before write
            if self._transport is None or self._transport.is_closing():
                raise ConnectionError("Transport not available")
                
            packet = self.lxpPacket.prepare_packet_for_read(
                start_register, number_of_registers, type=LXPPacket.READ_HOLD)  # fmt: skip
            self._transport.write(packet)
            self._LOGGER.debug(
                f"Packet Written for getting {serial} HOLD registers address_bank {address_bank} , {number_of_registers}")  # fmt: skip
        except Exception as e:
            self._LOGGER.error("Exception request_hold_bank %s", e)
        finally:
            # Always release lock to prevent deadlock
            if self._lxp_request_lock.locked():
                self._lxp_request_lock.release()

    async def do_refresh_hold_registers(self):
        log_marker = make_log_marker(
            self.serial_number, self.dongle_serial, "do_refresh_hold_registers"
        )

        try:
            self._warn_registers = True
            for address_bank in range(0, 5):
                self._LOGGER.info(
                    f"{log_marker} call request_hold - Bank: {address_bank}"
                )
                await self.request_hold_bank(address_bank)
                # await asyncio.sleep(1)
            if 1 == 1:
                # Request registers 200-239
                self._LOGGER.info(f"{log_marker} call request_hold - EXTENDED Bank: 5")
                self._warn_registers = True
                await self.request_hold_bank(5)
            if 1 == 0:
                # Request registers 560-599
                self._LOGGER.info(
                    f"{log_marker} call request_hold - HIGH EXTENDED Bank: 6"
                )
                self._warn_registers = True
                await self.request_hold_bank(6)
        except TimeoutError:
            pass
            # await self.reconnect()
        finally:
            self._warn_registers = False
            # self._already_processing = False

        self._LOGGER.debug(f"{log_marker} finish")

    def stop_client(self):
        self._LOGGER.info("stop_client called")
        self._stop_client = True
        self._close_connection()
        self._LOGGER.info("stop client finished")

    def _close_connection(self):
        if self._transport is not None:
            try:
                self._transport.close()
            except (OSError, RuntimeError) as e:
                self._LOGGER.warning("Error closing transport: %s", e)
            except Exception as e:
                self._LOGGER.error("Unexpected error closing transport: %s", e)

    async def reconnect(self):
        self._LOGGER.info("Reconnecting to Luxpower server")
        self._close_connection()
        self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
        self._connected = False
        self._LOGGER.info("reconnect client finished")

    async def _wait_for_value(self, register):
        try:
            value = await asyncio.wait_for(self._lxp_single_register_result, timeout=10)
            return value.get(register)
        except asyncio.TimeoutError:
            return None
        finally:
            self._lxp_single_register_result = None

    async def restart(self):
        self._LOGGER.warning("Restarting Luxpower Inverter")
        self._LOGGER.warning("Register to be written 11 with value 128")
        await self.write(11, 128)  # WRITE_SINGLE to register 11
        self._close_connection()
        self._connected = False
        self._LOGGER.warning("restart inverter finished")

    async def reset_all_settings(self):
        self._LOGGER.warning("Resetting Luxpower Inverter settings to defaults")

        lxpPacket = LXPPacket(
            debug=True,
            dongle_serial=self.dongle_serial,
            serial_number=self.serial_number,
        )

        self._LOGGER.warning("Register to be written 11 with value 2")
        lxpPacket.register_io_no_retry(
            self.server, self.port, 11, value=2, iotype=lxpPacket.WRITE_SINGLE
        )
        self._close_connection()
        self._connected = False
        self._LOGGER.warning("reset inverter settings finished")

    async def write(self, register, value, max_retries=3):
        """Write to register with retry logic."""
        for attempt in range(max_retries):
            await self._acquire_lock()
            try:
                # Check transport availability before write
                if self._transport is None or self._transport.is_closing():
                    raise ConnectionError("Transport not available")
                    
                self._lxp_single_register_result = asyncio.Future()
                packet = self.lxpPacket.prepare_packet_for_write(register, value)
                self._transport.write(packet)
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
            except (ConnectionError, OSError, asyncio.TimeoutError) as e:
                self._LOGGER.error(
                    f"Connection error writing register {register} with value {value}: {e}"
                )
                if attempt < max_retries - 1:
                    await asyncio.sleep(1)
                else:
                    return None
            except Exception as e:
                self._LOGGER.error(
                    f"Unexpected error writing register {register} with value {value}: {e}"
                )
                if attempt < max_retries - 1:
                    await asyncio.sleep(1)
                else:
                    return None
            finally:
                # Always release lock to prevent deadlock
                if self._lxp_request_lock.locked():
                    self._lxp_request_lock.release()

    async def read(self, register, type=LXPPacket.READ_HOLD, max_retries=3):
        """Read from register with retry logic."""
        for attempt in range(max_retries):
            await self._acquire_lock()
            try:
                # Check transport availability before write
                if self._transport is None or self._transport.is_closing():
                    raise ConnectionError("Transport not available")
                    
                self._lxp_single_register_result = asyncio.Future()
                packet = self.lxpPacket.prepare_packet_for_read(register, 1, type)
                self._transport.write(packet)
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
            except (ConnectionError, OSError, asyncio.TimeoutError) as e:
                self._LOGGER.error(
                    f"Connection error reading register {register}: {e}"
                )
                if attempt < max_retries - 1:
                    await asyncio.sleep(1)
                else:
                    return None
            except Exception as e:
                self._LOGGER.error(
                    f"Unexpected error reading register {register}: {e}"
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
        self._LOGGER.info("Syncing Time to Luxpower Inverter")

        self._LOGGER.info("Register to be read 12")
        read_value = await self.read(12)
        # read_value = lxpPacket.register_io_with_retry(
        #     self.server, self.port, 12, value=1, iotype=lxpPacket.READ_HOLD)

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
            self._LOGGER.warning("Cannot READ Register: 12 - Aborting")
            return

        self._LOGGER.info("Register to be read 13")
        read_value = await self.read(13)
        # read_value = lxpPacket.register_io_with_retry(
        #     self.server, self.port, 13, value=1, iotype=lxpPacket.READ_HOLD)

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
            self._LOGGER.warning("Cannot READ Register: 13 - Aborting")
            return

        self._LOGGER.info("Register to be read 14")
        read_value = await self.read(14)

        # read_value = lxpPacket.register_io_with_retry(
        #     self.server, self.port, 14, value=1, iotype=lxpPacket.READ_HOLD)

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
            self._LOGGER.warning("Cannot READ Register: 14 - Aborting")
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
