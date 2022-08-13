import struct
import asyncio
import logging
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
        _LOGGER.info("Disconnected from LuxPower server")
        _LOGGER.error("Disconnected from Luxpower server")

    def data_received(self, data):
        _LOGGER.info('Inverter: %s', self.lxpPacket.serial_number)
        _LOGGER.debug(data)
        packet = data
        packet_remains = data
        packet_remains_length = len(packet_remains)
        _LOGGER.debug('TCP OVERALL Packet Remains Length : %s', packet_remains_length)

        frame_number = 0

        while packet_remains_length > 0:

            frame_number = frame_number + 1
            if frame_number > 1:
                _LOGGER.info('*** Multi-Frame *** : %s', frame_number)

            prefix = packet_remains[0:2]
            if prefix != self.lxpPacket.prefix:
                _LOGGER.debug('Invalid Start Of Packet Prefix %s', prefix)
                return

            protocol_number = struct.unpack('H', packet_remains[2:4])[0]
            frame_length_remaining = struct.unpack('H', packet_remains[4:6])[0]
            frame_length_calced = frame_length_remaining + 6
            _LOGGER.info('CALCULATED Frame Length : %s', frame_length_calced)

            this_frame = packet_remains[0:frame_length_calced]

            _LOGGER.info('THIS Packet Remains Length : %s', packet_remains_length)
            packet_remains = packet_remains[frame_length_calced:packet_remains_length]
            packet_remains_length = len(packet_remains)
            _LOGGER.info('NEXT Packet Remains Length : %s', packet_remains_length)

            _LOGGER.debug('Received: %s', this_frame)
            result = self.lxpPacket.parse_packet(this_frame)
            if not self.lxpPacket.packet_error:
                _LOGGER.info(result)
                if self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                    event_data = {"data": result.get('data', {})}
                    _LOGGER.info("EVENT DATA: %s ", event_data)
                    self.hass.bus.fire(self.events.EVENT_DATA_RECEIVED, event_data)
                elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:
                    event_data = {"registers": result.get('registers', {})}
                    _LOGGER.info("EVENT REGISTER: %s ", event_data)
                    self.hass.bus.fire(self.events.EVENT_REGISTER_RECEIVED, event_data)

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
            _LOGGER.info("Exception get_register_data %s", e)
            _LOGGER.error(f"close error : {e}")

    async def get_holding_data(self, address_bank):
        serial = self.lxpPacket.serial_number
        try:
            _LOGGER.debug(f"get_holding_data for {serial} address_bank: %s", address_bank)
            packet = self.lxpPacket.prepare_packet_for_read(address_bank * 40, 40, type=LXPPacket.READ_HOLD)
            self._transport.write(packet)
            _LOGGER.info(f"Packet Written for getting {serial} HOLDING address_bank {address_bank}")
        except Exception as e:
            _LOGGER.info("Exception get_holding_data %s", e)
            _LOGGER.error(f"close error : {e}")

    def stop_client(self):
        _LOGGER.info("stop_client called")
        self._stop_client = True
        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                _LOGGER.debug("Exception ", e)
        _LOGGER.debug("stop client finished")

    async def reconnect(self):
        _LOGGER.info("Reconnecting to Luxpower server")
        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                _LOGGER.error("Exception ", e)
                _LOGGER.error(f"close error : {e}")
        self._connected = False
        _LOGGER.debug("reconnect client finished")
        _LOGGER.debug("reconnect finished finished")


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

    async def send_refresh_registers(self, dongle):
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
            for address_bank in range(0, 2):
                _LOGGER.debug("send_refresh_registers for address_bank: %s", address_bank)
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
            for address_bank in range(0, 4):
                _LOGGER.debug("send_holding_registers for address_bank: %s", address_bank)
                await luxpower_client.get_holding_data(address_bank)
                await asyncio.sleep(2)
        _LOGGER.info("send_holding_registers done")

    async def send_refresh_register_bank(self, dongle, address_bank):
        luxpower_client = None
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data['DONGLE']:
                luxpower_client = entry_data.get('client')
                break

        if luxpower_client is not None:
            _LOGGER.info("send_refresh_registers for address_bank: %s", address_bank)
            await luxpower_client.get_register_data(address_bank)
            await asyncio.sleep(1)
        _LOGGER.debug("send_refresh_register_bank done")
