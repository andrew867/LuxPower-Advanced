import struct
import asyncio
import logging
import socket
import datetime
from .helpers import Event
from typing import Optional
from .const import DOMAIN
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)

class LuxPowerClient(asyncio.Protocol):
    def __init__(self, hass, server, port, dongle_serial, serial_number, events: Event):
        self.hass = hass
        self.server = server
        self.port = port
        self.dongle_serial = dongle_serial
        self.serial_number = serial_number
        self.events = events
        self._stop_client = False
        self._transport = None
        self._connected = False
        self._LOGGER = logging.getLogger(__name__)
        self.lxpPacket = LXPPacket(debug=False, dongle_serial=dongle_serial, serial_number=serial_number)

    def factory(self):
        """
        returns reference to itself for using in protocol_factory with
        create_server
        """
        return self

    def connection_made(self, transport):
        """ is called as soon as an ISM8 connects to server """
        _peername = transport.get_extra_info('peername')
        _LOGGER.info("Connected to LUXPower Server: %s", _peername)
        self._transport = transport
        self._connected = True

    def connection_lost(self, exc: Optional[Exception]) -> None:
        self._connected = False
        _LOGGER.error("Disconnected from Luxpower server")

    def data_received(self, data):
        _LOGGER.debug('Inverter: %s', self.lxpPacket.serial_number)
        _LOGGER.debug(data)
        packet = data
        packet_remains = data
        packet_remains_length = len(packet_remains)
        _LOGGER.debug('TCP OVERALL Packet Remains Length : %s', packet_remains_length)

        frame_number = 0

        while packet_remains_length > 0:

            frame_number = frame_number + 1
            if frame_number > 1:
                _LOGGER.debug('*** Multi-Frame *** : %s', frame_number)

            prefix = packet_remains[0:2]
            if prefix != self.lxpPacket.prefix:
                _LOGGER.debug('Invalid Start Of Packet Prefix %s', prefix)
                return

            protocol_number = struct.unpack('H', packet_remains[2:4])[0]
            frame_length_remaining = struct.unpack('H', packet_remains[4:6])[0]
            frame_length_calced = frame_length_remaining + 6
            _LOGGER.debug('CALCULATED Frame Length : %s', frame_length_calced)

            this_frame = packet_remains[0:frame_length_calced]

            _LOGGER.debug('THIS Packet Remains Length : %s', packet_remains_length)
            packet_remains = packet_remains[frame_length_calced:packet_remains_length]
            packet_remains_length = len(packet_remains)
            _LOGGER.debug('NEXT Packet Remains Length : %s', packet_remains_length)

            _LOGGER.debug('Received: %s', this_frame)
            result = self.lxpPacket.parse_packet(this_frame)
            if not self.lxpPacket.packet_error:
                _LOGGER.debug(result)
                if self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                    total_data = {"data": result.get('data', {})}
                    event_data = {"data": result.get('thesedata', {})}
                    _LOGGER.debug("EVENT DATA: %s ", event_data)
                    if 0 <= self.lxpPacket.register <= 39:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK0_RECEIVED, event_data)
                    elif 40 <= self.lxpPacket.register <= 79:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK1_RECEIVED, event_data)
                    elif 80 <= self.lxpPacket.register <= 119:
                        self.hass.bus.fire(self.events.EVENT_DATA_BANK2_RECEIVED, event_data)
 
                    #self.hass.bus.fire(self.events.EVENT_DATA_RECEIVED, event_data)
                elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:
                    total_data = {"registers": result.get('registers', {})}
                    event_data = {"registers": result.get('thesereg', {})}
                    _LOGGER.debug("EVENT REGISTER: %s ", event_data)
                    if self.lxpPacket.register == 160:
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

                    #self.hass.bus.fire(self.events.EVENT_REGISTER_RECEIVED, event_data)

    async def start_luxpower_client_daemon(self, ):
        while not self._stop_client:
            if not self._connected:
                try:
                    _LOGGER.info("luxpower daemon: Connecting to lux power server")
                    await self.hass.loop.create_connection(self.factory, self.server, self.port)
                    _LOGGER.info("luxpower daemon: Connected to lux power server")
                except Exception as e:
                    _LOGGER.error(f"Exception luxpower daemon client in open connection retrying in 10 seconds : {e}")
            await asyncio.sleep(10)
        _LOGGER.info("Exiting start_luxpower_client_daemon")

    async def get_register_data(self, address_bank):
        try:
            _LOGGER.debug("get_register_data for address_bank: %s", address_bank)
            packet = self.lxpPacket.prepare_packet_for_read(address_bank * 40, 40, type=LXPPacket.READ_INPUT)
            self._transport.write(packet)
        except Exception as e:
            _LOGGER.error("Exception get_register_data %s", e)

    async def get_holding_data(self, address_bank):
        serial = self.lxpPacket.serial_number
        number_of_registers = 40
        if address_bank == 4:
            number_of_registers = 40
        try:
            _LOGGER.debug(f"get_holding_data for {serial} address_bank: {address_bank} , {number_of_registers}")
            packet = self.lxpPacket.prepare_packet_for_read(address_bank * 40, number_of_registers, type=LXPPacket.READ_HOLD)
            self._transport.write(packet)
            _LOGGER.debug(f"Packet Written for getting {serial} HOLDING address_bank {address_bank} , {number_of_registers}")
        except Exception as e:
            _LOGGER.error("Exception get_holding_data %s", e)

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
        self._connected = False
        _LOGGER.info("reconnect client finished")


    async def synctime(self):
        _LOGGER.info("Syncing Time to Luxpower Inverter")

        lxpPacket = LXPPacket(debug=True, dongle_serial=self.dongle_serial, serial_number=self.serial_number)

        _LOGGER.info("Register to be read 12")
        read_value = lxpPacket.register_io_with_retry(self.server, self.port, 12, value=1, iotype=lxpPacket.READ_HOLD)

        if read_value is not None:
            #Read has been succesful - use read value
            _LOGGER.info(f"READ Register OK - Using INVERTER Register: 12 Value: {read_value}")
            old12 = read_value
            oldmonth = int(old12/256)
            oldyear = int((old12-(oldmonth*256))+2000)
            _LOGGER.info("Old12: %s, Oldmonth: %s, Oldyear: %s",old12,oldmonth,oldyear)
        else:
            #Read has been UNsuccesful
            _LOGGER.warning(f"Cannot READ Register: 12 - Aborting")
            return

        _LOGGER.info("Register to be read 13")
        read_value = lxpPacket.register_io_with_retry(self.server, self.port, 13, value=1, iotype=lxpPacket.READ_HOLD)

        if read_value is not None:
            #Read has been succesful - use read value
            _LOGGER.info(f"READ Register OK - Using INVERTER Register: 13 Value: {read_value}")
            old13 = read_value
            oldhour = int(old13/256)
            oldday = int(old13-(oldhour*256))
            _LOGGER.info("Old13: %s, Oldhour: %s, Oldday: %s",old13,oldhour,oldday)
        else:
            #Read has been UNsuccesful
            _LOGGER.warning(f"Cannot READ Register: 13 - Aborting")
            return

        _LOGGER.info("Register to be read 14")
        read_value = lxpPacket.register_io_with_retry(self.server, self.port, 14, value=1, iotype=lxpPacket.READ_HOLD)

        if read_value is not None:
            #Read has been succesful - use read value
            _LOGGER.info(f"READ Register OK - Using INVERTER Register: 14 Value: {read_value}")
            old14 = read_value
            oldsecond = int(old14/256)
            oldminute = int(old14-(oldsecond*256))
            _LOGGER.info("Old14: %s, Oldsecond: %s, Oldminute: %s",old14,oldsecond,oldminute)
        else:
            #Read has been UNsuccesful
            _LOGGER.warning(f"Cannot READ Register: 14 - Aborting")
            return

        was = datetime.datetime(int(oldyear), int(oldmonth), int(oldday), int(oldhour), int(oldminute), int(oldsecond))
        _LOGGER.info("was: %s %s %s %s %s %s", was.year, was.month, was.day, was.hour, was.minute, was.second)

        now = datetime.datetime.now()
        _LOGGER.info("now: %s %s %s %s %s %s", now.year, now.month, now.day, now.hour, now.minute, now.second)

        _LOGGER.warning("%s Old Time: %s, New Time: %s, Seconds Diff: %s", str(self.serial_number), was, now, abs(now-was))

        if 1 == 1:
            new_value = (now.month*256)+(now.year-2000)
            
            _LOGGER.info(f"Register to be written 12 with value {new_value}")
            read_value = lxpPacket.register_io_with_retry(self.server, self.port, 12, value=new_value, iotype=lxpPacket.WRITE_SINGLE)

            if read_value is not None:
                #Write has been succesful
                _LOGGER.info(f"WRITE Register OK - Setting INVERTER Register: 12 Value: {read_value}")
            else:
                #Write has been UNsuccesful
                _LOGGER.warning(f"Cannot WRITE Register: 12 Value: {new_value} - Aborting")
                return

            new_value = (now.hour*256)+(now.day)
            
            _LOGGER.info(f"Register to be written 13 with value {new_value}")
            read_value = lxpPacket.register_io_with_retry(self.server, self.port, 13, value=new_value, iotype=lxpPacket.WRITE_SINGLE)

            if read_value is not None:
                #Write has been succesful
                _LOGGER.info(f"WRITE Register OK - Setting INVERTER Register: 13 Value: {read_value}")
            else:
                #Write has been UNsuccesful
                _LOGGER.warning(f"Cannot WRITE Register: 13 Value: {new_value} - Aborting")
                return

            new_value = ((now.second+1)*256)+(now.minute)
            
            _LOGGER.info(f"Register to be written 14 with value {new_value}")
            read_value = lxpPacket.register_io_with_retry(self.server, self.port, 14, value=new_value, iotype=lxpPacket.WRITE_SINGLE)

            if read_value is not None:
                #Write has been succesful
                _LOGGER.info(f"WRITE Register OK - Setting INVERTER Register: 14 Value: {read_value}")
            else:
                #Write has been UNsuccesful
                _LOGGER.warning(f"Cannot WRITE Register: 14 Value: {new_value} - Aborting")
                return

        else:
            _LOGGER.warning("Inverter Time Update Disabled In Code")

        _LOGGER.debug("synctime finished")


class ServiceHelper:
    def __init__(self, hass) -> None:
        self.hass = hass

    async def send_reconnect(self, dongle):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data['DONGLE']:
                luxpower_client = entry_data.get('client')
                break

        if luxpower_client is not None:
            await luxpower_client.reconnect()
            await asyncio.sleep(1)
        _LOGGER.debug("send_reconnect done")

    async def send_synctime(self, dongle):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data['DONGLE']:
                luxpower_client = entry_data.get('client')
                break

        if luxpower_client is not None:
            await luxpower_client.synctime()
            await asyncio.sleep(1)
        _LOGGER.info("send_synctime done")

    async def send_refresh_registers(self, dongle, bank_count):
        _LOGGER.info(f"send_refresh_registers start - Count: {bank_count}")
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data['DONGLE']:
                luxpower_client = entry_data.get('client')
                break

        if luxpower_client is not None:
            # This change stops Spamming of Lux Server Database
            # Really needs seperate function refresh_two_registers
            # for address_bank in range(0, 3):
            for address_bank in range(0, bank_count):
                _LOGGER.info("send_refresh_registers for address_bank: %s", address_bank)
                await luxpower_client.get_register_data(address_bank)
                await asyncio.sleep(1)
        _LOGGER.info("send_refresh_registers done")

    async def send_holding_registers(self, dongle):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data['DONGLE']:
                luxpower_client = entry_data.get('client')
                break

        if luxpower_client is not None:
            for address_bank in range(0, 5):
                _LOGGER.debug("send_holding_registers for address_bank: %s", address_bank)
                await luxpower_client.get_holding_data(address_bank)
                await asyncio.sleep(2)
        _LOGGER.debug("send_holding_registers done")

    async def send_refresh_register_bank(self, dongle, address_bank):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data['DONGLE']:
                luxpower_client = entry_data.get('client')
                break

        if luxpower_client is not None:
            _LOGGER.debug("send_refresh_registers for address_bank: %s", address_bank)
            await luxpower_client.get_register_data(address_bank)
            await asyncio.sleep(1)
        _LOGGER.debug("send_refresh_register_bank done")
