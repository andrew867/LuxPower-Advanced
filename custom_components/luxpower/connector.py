"""

This is a docstring placeholder.

This is where we will describe what this module does

"""

import asyncio
import datetime
import logging
import socket
import struct
from typing import Optional

from homeassistant.core import HomeAssistant

from .const import DOMAIN
from .helpers import Event
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)


async def refreshALLPlatforms(hass: HomeAssistant, dongle):
    """

    This is a docstring placeholder.

    This is where we will describe what this function does

    """
    await asyncio.sleep(20)
    await hass.services.async_call(DOMAIN, "luxpower_refresh_holdings", {"dongle": dongle}, blocking=True)  # fmt: skip
    await asyncio.sleep(10)
    await hass.services.async_call(DOMAIN, "luxpower_refresh_registers", {"dongle": dongle, "bank_count": 3}, blocking=True)  # fmt: skip


def make_log_marker(serial, dongle, tag):
    """

    This is a docstring placeholder.

    This is where we will describe what this function does

    """
    now = datetime.datetime.now()
    marker = str(int((now - now.replace(hour=0, minute=0, second=0, microsecond=0)).total_seconds() * 1000)).zfill(8)
    marker = marker + " " + serial.decode() + "/" + dongle.decode() + " " + tag
    return marker


class LuxPowerClient(asyncio.Protocol):
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    def __init__(self, hass, server, port, dongle_serial, serial_number, events: Event):
        """

        This is a docstring placeholder.

        This is where we will describe what this __init__ does

        """
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
        self._reachable = False
        self._already_processing = False
        self._LOGGER = logging.getLogger(__name__)
        self.lxpPacket = LXPPacket(debug=False, dongle_serial=dongle_serial, serial_number=serial_number)

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
        _LOGGER.info("Connected to LUXPower Server: %s", _peername)
        self._transport = transport
        self._connected = True

    def connection_lost(self, exc: Optional[Exception]) -> None:
        self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
        self._connected = False
        _LOGGER.error("connection_lost: Disconnected from Luxpower server")

    def data_received(self, data):
        _LOGGER.debug("Inverter: %s", self.lxpPacket.serial_number)
        _LOGGER.debug(data)
        # packet = data
        packet_remains = data
        packet_remains_length = len(packet_remains)
        _LOGGER.debug("TCP OVERALL Packet Remains Length : %s", packet_remains_length)

        frame_number = 0

        while packet_remains_length > 0:
            frame_number = frame_number + 1
            if frame_number > 1:
                _LOGGER.debug("*** Multi-Frame *** : %s", frame_number)

            prefix = packet_remains[0:2]
            if prefix != self.lxpPacket.prefix:
                _LOGGER.debug("Invalid Start Of Packet Prefix %s", prefix)
                return

            # protocol_number = struct.unpack("H", packet_remains[2:4])[0]
            frame_length_remaining = struct.unpack("H", packet_remains[4:6])[0]
            frame_length_calced = frame_length_remaining + 6
            _LOGGER.debug("CALCULATED Frame Length : %s", frame_length_calced)

            this_frame = packet_remains[0:frame_length_calced]

            _LOGGER.debug("THIS Packet Remains Length : %s", packet_remains_length)
            packet_remains = packet_remains[frame_length_calced:packet_remains_length]
            packet_remains_length = len(packet_remains)
            _LOGGER.debug("NEXT Packet Remains Length : %s", packet_remains_length)

            _LOGGER.debug("Received: %s", this_frame)
            result = self.lxpPacket.parse_packet(this_frame)
            if not self.lxpPacket.packet_error:
                _LOGGER.debug(result)

                if self.lxpPacket.tcp_function == self.lxpPacket.HEARTBEAT:
                    # response back with the packet we got from inverter
                    self._transport.write(data)

                elif self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                    register = self.lxpPacket.register
                    _LOGGER.debug("register: %s ", register)
                    number_of_registers = int(len(result.get("value", "")) / 2)
                    _LOGGER.debug("number_of_registers: %s ", number_of_registers)
                    total_data = {"data": result.get("data", {})}
                    event_data = {"data": result.get("thesedata", {})}
                    _LOGGER.debug("EVENT DATA: %s ", event_data)

                    # Decode Standard Block Registers
                    if register == 0 and number_of_registers == 40:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                    elif register == 40 and number_of_registers == 40:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                    elif register == 80 and number_of_registers == 40:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                    elif register == 0 and number_of_registers == 127:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                    else:
                        if number_of_registers == 1:
                            # Decode Single Register
                            if 0 <= register <= 39:
                                self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
                                self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                            elif 40 <= register <= 79:
                                self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
                                self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                            elif 80 <= register <= 119:
                                self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
                                self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)
                        else:
                            # Decode Series of Registers - Possibly Over Block Boundaries
                            if 0 <= register <= 119:
                                self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
                                self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
                                self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
                                self.hass.bus.fire(self.events.EVENT_DATA_BANKX_RECEIVED, event_data)

                elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:  # fmt: skip
                    total_data = {"registers": result.get("registers", {})}
                    event_data = {"registers": result.get("thesereg", {})}
                    _LOGGER.debug("EVENT REGISTER: %s ", event_data)
                    if self.lxpPacket.register >= 160 and self._warn_registers:
                        _LOGGER.warning("REGISTERS: %s ", total_data)
                    if 0 <= self.lxpPacket.register <= 39:
                        self.hass.bus.fire(self.events.EVENT_REGISTER_BANK0_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_REGISTER_21_RECEIVED, event_data)
                    elif 40 <= self.lxpPacket.register <= 79:
                        self.hass.bus.fire(self.events.EVENT_REGISTER_BANK1_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_REGISTER_21_RECEIVED, event_data)
                    elif 80 <= self.lxpPacket.register <= 119:
                        self.hass.bus.fire(self.events.EVENT_REGISTER_BANK2_RECEIVED, event_data)
                        self.hass.bus.fire(self.events.EVENT_REGISTER_21_RECEIVED, event_data)
                    elif 120 <= self.lxpPacket.register <= 159:
                        self.hass.bus.fire(self.events.EVENT_REGISTER_BANK3_RECEIVED, event_data)
                    elif 160 <= self.lxpPacket.register <= 199:
                        self.hass.bus.fire(self.events.EVENT_REGISTER_BANK4_RECEIVED, event_data)
                    elif 200 <= self.lxpPacket.register <= 239:
                        self.hass.bus.fire(self.events.EVENT_REGISTER_BANK5_RECEIVED, event_data)

                    # self.hass.bus.fire(self.events.EVENT_REGISTER_RECEIVED, event_data)

    async def start_luxpower_client_daemon(self):
        while not self._stop_client:
            if not self._connected:
                try:
                    _LOGGER.info("luxpower daemon: Connecting to lux power server")
                    await self.hass.loop.create_connection(self.factory, self.server, self.port)
                    _LOGGER.info("luxpower daemon: Connected to lux power server")
                except Exception as e:
                    _LOGGER.error(f"Exception luxpower daemon client open failed - retrying in 10 seconds : {e}")

                await asyncio.sleep(1)
                if self._connected:
                    if not self._connect_twice:
                        self._connect_twice = False
                        _LOGGER.warning("Refreshing Lux Platforms With Data")
                        # await self.hass.services.async_call(DOMAIN, "luxpower_refresh_registers", {"dongle": self.dongle_serial.decode(), "bank_count": 3}, blocking=False)  # fmt: skip
                        # await self.hass.services.async_call(DOMAIN, "luxpower_refresh_holdings", {"dongle": self.dongle_serial.decode()}, blocking=False)  # fmt: skip
                        # self.hass.async_create_task(refreshALLPlatforms(self.hass, dongle=self.dongle_serial.decode()))
                        # self.hass.async_create_task(self.do_refresh_data_registers(3, True))
                        # self.hass.async_create_task(self.do_refresh_hold_registers(True))
                        if self._connect_after_failure:
                            await asyncio.sleep(60)
                            self._connect_after_failure = False
                        await self.do_refresh_data_registers(3, True)
                        await self.do_refresh_hold_registers(True)
                    else:
                        self._connect_twice = True
                        await self.reconnect()

            await asyncio.sleep(10)
        _LOGGER.info("Stop Called - Exiting start_luxpower_client_daemon")

    async def inverter_is_reachable(self):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.settimeout(5)
        try:
            sock.connect((self.server, self.port))
            _LOGGER.info(f"Test Connection To Inverter {self.dongle_serial.decode()} At {self.server}:{self.port} Has Succeeded")  # fmt: skip
            self._reachable = True
        except Exception as e:
            _LOGGER.error(f"Test Connection To Inverter {self.dongle_serial.decode()} At {self.server}:{self.port} Has Failed {e}")  # fmt: skip
            self._reachable = False
        sock.close()
        return self._reachable

    async def request_data_bank(self, address_bank):
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        start_register = address_bank * 40
        try:
            _LOGGER.debug("request_data_bank for address_bank: %s", address_bank)
            packet = self.lxpPacket.prepare_packet_for_read(start_register, number_of_registers, type=LXPPacket.READ_INPUT)  # fmt: skip
            self._transport.write(packet)
            _LOGGER.debug(f"Packet Written for getting {serial} DATA registers address_bank {address_bank} , {number_of_registers}")  # fmt: skip
            await asyncio.sleep(1)
        except Exception as e:
            _LOGGER.error("Exception request_data_bank %s", e)

    async def do_refresh_data_registers(self, bank_count, must_run):
        log_marker = make_log_marker(self.serial_number, self.dongle_serial, "do_refresh_data_registers")

        if not self._connected:
            return

        _LOGGER.debug(f"{log_marker} start - Count: {bank_count}")
        if self._already_processing:
            if must_run:
                _LOGGER.debug(f"{log_marker} waiting - Count: {bank_count}")
                while self._already_processing:
                    await asyncio.sleep(1)
                _LOGGER.debug(f"{log_marker} continuing - Count: {bank_count}")
            else:
                _LOGGER.debug(f"{log_marker} aborted - Count: {bank_count}")
                return
        try:
            self._already_processing = True
            await self.inverter_is_reachable()
            if self._reachable:
                for address_bank in range(0, bank_count):
                    _LOGGER.info(f"{log_marker} call request_data - Bank: {address_bank}")
                    await self.request_data_bank(address_bank)
                    # await asyncio.sleep(1)
            else:
                _LOGGER.info(f"{log_marker} Inv Not Reachable - Attempting Reconnect")
                self._connect_after_failure = True
                await self.reconnect()
                await asyncio.sleep(1)
            _LOGGER.debug(f"{log_marker} finish - Count: {bank_count}")
        finally:
            self._already_processing = False

    async def request_hold_bank(self, address_bank):
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        start_register = address_bank * 40
        if address_bank == 6:
            start_register = 560
        try:
            _LOGGER.debug(f"request_hold_bank for {serial} address_bank: {address_bank} , {number_of_registers}")
            packet = self.lxpPacket.prepare_packet_for_read(start_register, number_of_registers, type=LXPPacket.READ_HOLD)  # fmt: skip
            self._transport.write(packet)
            _LOGGER.debug(f"Packet Written for getting {serial} HOLD registers address_bank {address_bank} , {number_of_registers}")  # fmt: skip
            await asyncio.sleep(1)
        except Exception as e:
            _LOGGER.error("Exception request_hold_bank %s", e)

    async def do_refresh_hold_registers(self, must_run):
        log_marker = make_log_marker(self.serial_number, self.dongle_serial, "do_refresh_hold_registers")

        _LOGGER.debug(f"{log_marker} start")
        if self._already_processing:
            if must_run:
                _LOGGER.debug(f"{log_marker} waiting")
                while self._already_processing:
                    await asyncio.sleep(1)
                _LOGGER.debug(f"{log_marker} continuing")
            else:
                _LOGGER.debug(f"{log_marker} aborted")
                return

        try:
            self._already_processing = True
            self._warn_registers = True
            await asyncio.sleep(1)
            for address_bank in range(0, 5):
                _LOGGER.info(f"{log_marker} call request_hold - Bank: {address_bank}")
                await self.request_hold_bank(address_bank)
                # await asyncio.sleep(1)
            if 1 == 1:
                # Request registers 200-239
                _LOGGER.info(f"{log_marker} call request_hold - EXTENDED Bank: 5")
                self._warn_registers = True
                await self.request_hold_bank(5)
                # await asyncio.sleep(1)
            if 1 == 0:
                # Request registers 560-599
                _LOGGER.info(f"{log_marker} call request_hold - HIGH EXTENDED Bank: 6")
                self._warn_registers = True
                await self.request_hold_bank(6)
                # await asyncio.sleep(1)

            _LOGGER.debug(f"{log_marker} finish")
        finally:
            self._warn_registers = False
            self._already_processing = False

    def stop_client(self):
        _LOGGER.info("stop_client called")
        self._stop_client = True
        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                _LOGGER.error("Exception stop_client %s", e)
        _LOGGER.info("stop client finished")

    async def reconnect(self):
        _LOGGER.info("Reconnecting to Luxpower server")
        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                _LOGGER.error("Exception reconnect %s", e)
        self.hass.bus.fire(self.events.EVENT_UNAVAILABLE_RECEIVED, "")
        self._connected = False
        _LOGGER.info("reconnect client finished")

    async def restart(self):
        _LOGGER.warning("Restarting Luxpower Inverter")

        lxpPacket = LXPPacket(debug=True, dongle_serial=self.dongle_serial, serial_number=self.serial_number)

        _LOGGER.warning("Register to be written 11 with value 128")
        lxpPacket.register_io_no_retry(self.server, self.port, 11, value=128, iotype=lxpPacket.WRITE_SINGLE)

        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                _LOGGER.error("Exception restart %s", e)
        self._connected = False
        _LOGGER.warning("restart inverter finished")

    async def synctime(self, do_set_time):
        _LOGGER.info("Syncing Time to Luxpower Inverter")

        lxpPacket = LXPPacket(debug=True, dongle_serial=self.dongle_serial, serial_number=self.serial_number)

        _LOGGER.info("Register to be read 12")
        read_value = lxpPacket.register_io_with_retry(self.server, self.port, 12, value=1, iotype=lxpPacket.READ_HOLD)

        if read_value is not None:
            # Read has been successful - use read value
            _LOGGER.info(f"READ Register OK - Using INVERTER Register: 12 Value: {read_value}")
            old12 = read_value
            oldmonth = int(old12 / 256)
            oldyear = int((old12 - (oldmonth * 256)) + 2000)
            _LOGGER.info("Old12: %s, Oldmonth: %s, Oldyear: %s", old12, oldmonth, oldyear)
        else:
            # Read has been UNsuccessful
            _LOGGER.warning("Cannot READ Register: 12 - Aborting")
            return

        _LOGGER.info("Register to be read 13")
        read_value = lxpPacket.register_io_with_retry(self.server, self.port, 13, value=1, iotype=lxpPacket.READ_HOLD)

        if read_value is not None:
            # Read has been successful - use read value
            _LOGGER.info(f"READ Register OK - Using INVERTER Register: 13 Value: {read_value}")
            old13 = read_value
            oldhour = int(old13 / 256)
            oldday = int(old13 - (oldhour * 256))
            _LOGGER.info("Old13: %s, Oldhour: %s, Oldday: %s", old13, oldhour, oldday)
        else:
            # Read has been UNsuccessful
            _LOGGER.warning("Cannot READ Register: 13 - Aborting")
            return

        _LOGGER.info("Register to be read 14")
        read_value = lxpPacket.register_io_with_retry(self.server, self.port, 14, value=1, iotype=lxpPacket.READ_HOLD)

        if read_value is not None:
            # Read has been successful - use read value
            _LOGGER.info(f"READ Register OK - Using INVERTER Register: 14 Value: {read_value}")
            old14 = read_value
            oldsecond = int(old14 / 256)
            oldminute = int(old14 - (oldsecond * 256))
            _LOGGER.info("Old14: %s, Oldsecond: %s, Oldminute: %s", old14, oldsecond, oldminute)
        else:
            # Read has been UNsuccessful
            _LOGGER.warning("Cannot READ Register: 14 - Aborting")
            return

        was = datetime.datetime(int(oldyear), int(oldmonth), int(oldday), int(oldhour), int(oldminute), int(oldsecond))
        _LOGGER.info("was: %s %s %s %s %s %s", was.year, was.month, was.day, was.hour, was.minute, was.second)

        now = datetime.datetime.now()
        _LOGGER.info("now: %s %s %s %s %s %s", now.year, now.month, now.day, now.hour, now.minute, now.second)

        _LOGGER.warning(
            f"{str(self.serial_number)} Old Time: {was}, New Time: {now}, Seconds Diff: {abs(now - was)} - Updating: {str(do_set_time)}"
        )

        if do_set_time:
            new_value = (now.month * 256) + (now.year - 2000)

            _LOGGER.info(f"Register to be written 12 with value {new_value}")
            read_value = lxpPacket.register_io_with_retry(
                self.server, self.port, 12, value=new_value, iotype=lxpPacket.WRITE_SINGLE
            )

            if read_value is not None:
                # Write has been successful
                _LOGGER.info(f"WRITE Register OK - Setting INVERTER Register: 12 Value: {read_value}")
            else:
                # Write has been UNsuccessful
                _LOGGER.warning(f"Cannot WRITE Register: 12 Value: {new_value} - Aborting")
                return

            new_value = (now.hour * 256) + (now.day)

            _LOGGER.info(f"Register to be written 13 with value {new_value}")
            read_value = lxpPacket.register_io_with_retry(
                self.server, self.port, 13, value=new_value, iotype=lxpPacket.WRITE_SINGLE
            )

            if read_value is not None:
                # Write has been successful
                _LOGGER.info(f"WRITE Register OK - Setting INVERTER Register: 13 Value: {read_value}")
            else:
                # Write has been UNsuccessful
                _LOGGER.warning(f"Cannot WRITE Register: 13 Value: {new_value} - Aborting")
                return

            write_time_allowance = 0

            new_value = ((now.second + write_time_allowance) * 256) + (now.minute)

            _LOGGER.info(f"Register to be written 14 with value {new_value}")
            read_value = lxpPacket.register_io_with_retry(
                self.server, self.port, 14, value=new_value, iotype=lxpPacket.WRITE_SINGLE
            )

            if read_value is not None:
                # Write has been successful
                _LOGGER.info(f"WRITE Register OK - Setting INVERTER Register: 14 Value: {read_value}")
            else:
                # Write has been UNsuccessful
                _LOGGER.warning(f"Cannot WRITE Register: 14 Value: {new_value} - Aborting")
                return

        else:
            _LOGGER.debug("Inverter Time Update Disabled By Parameter")

        _LOGGER.debug("synctime finished")


class ServiceHelper:
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    def __init__(self, hass) -> None:
        """

        This is a docstring placeholder.

        This is where we will describe what this __init__ does

        """
        self.hass = hass

    async def service_reconnect(self, dongle):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                luxpower_client = entry_data.get("client")
                break

        if luxpower_client is not None:
            await luxpower_client.reconnect()
            await asyncio.sleep(1)
        _LOGGER.debug("service_reconnect done")

    async def service_restart(self, dongle):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                luxpower_client = entry_data.get("client")
                break

        if luxpower_client is not None:
            await luxpower_client.restart()
            await asyncio.sleep(1)
        _LOGGER.warning("service_restart done")

    async def service_synctime(self, dongle, do_set_time):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                luxpower_client = entry_data.get("client")
                break

        if luxpower_client is not None:
            await luxpower_client.synctime(do_set_time)
            await asyncio.sleep(1)
        _LOGGER.info("service_synctime done")

    async def service_refresh_data_registers(self, dongle, bank_count):
        _LOGGER.info(f"service_refresh_data_registers start - Count: {bank_count}")
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                luxpower_client = entry_data.get("client")
                break

        if luxpower_client is not None:
            await luxpower_client.do_refresh_data_registers(bank_count, False)

            # await luxpower_client.inverter_is_reachable()
            # if luxpower_client._reachable:
            #    for address_bank in range(0, bank_count):
            #        _LOGGER.info("service_refresh_data_registers for address_bank: %s", address_bank)
            #        await luxpower_client.request_data_bank(address_bank)
            #        await asyncio.sleep(1)
            # else:
            #    _LOGGER.info("Inverter Is Not Reachable - Attempting Reconnect")
            #    await luxpower_client.reconnect()
            #    await asyncio.sleep(1)

        _LOGGER.debug("service_refresh_data_registers done")

    async def service_refresh_hold_registers(self, dongle):
        _LOGGER.debug("service_refresh_hold_registers start")
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                luxpower_client = entry_data.get("client")
                break

        if luxpower_client is not None:
            await luxpower_client.do_refresh_hold_registers(False)

            # luxpower_client._warn_registers = True
            # await asyncio.sleep(5)
            # for address_bank in range(0, 5):
            #    _LOGGER.debug("service_refresh_hold_registers for address_bank: %s", address_bank)
            #    await luxpower_client.request_hold_bank(address_bank)
            #    await asyncio.sleep(2)
            # if 1 == 1:
            #    # Request registers 200-239
            #    _LOGGER.debug("service_holding_register for EXTENDED address_bank: %s", 5)
            #    self._warn_registers = True
            #    await luxpower_client.request_hold_bank(5)
            #    await asyncio.sleep(2)
            # if 1 == 0:
            #    # Request registers 560-599
            #    _LOGGER.debug("service_refresh_hold_registers for HIGH EXTENDED address_bank: %s", 6)
            #    self._warn_registers = True
            #    await luxpower_client.request_hold_bank(6)
            #    await asyncio.sleep(2)
            # luxpower_client._warn_registers = False

        _LOGGER.debug("service_refresh_hold_registers finish")

    async def service_refresh_data_register_bank(self, dongle, address_bank):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                luxpower_client = entry_data.get("client")
                break

        if luxpower_client is not None:
            _LOGGER.debug("service_refresh_register for address_bank: %s", address_bank)
            await luxpower_client.request_data_bank(address_bank)
            await asyncio.sleep(1)
        _LOGGER.debug("service_refresh_data_register_bank done")
