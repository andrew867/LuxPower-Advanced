import asyncio
import logging
from .helpers import Event
from typing import Optional

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
        print("Connected to LUXPower Server: %s", _peername)
        _LOGGER.info("Connected to Luxpower server")
        self._transport = transport
        self._connected = True

    def connection_lost(self, exc: Optional[Exception]) -> None:
        self._connected = False
        print("Disconnected from LuxPower server")
        _LOGGER.error("Disconnected from Luxpower server")

    def data_received(self, data):
        print(data)
        packet = data
        print('Received: ', packet)
        result = self.lxpPacket.parse_packet(packet)
        if not self.lxpPacket.packet_error:
            print(result)
            if self.lxpPacket.device_function == self.lxpPacket.READ_INPUT:
                event_data = {"data": result.get('data', {})}
                print("EVENT DATA: ", event_data)
                self.hass.bus.fire(self.events.EVENT_DATA_RECEIVED, event_data)
            elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:
                event_data = {"registers": result.get('registers', {})}
                print("EVENT REGISTER: ", event_data)
                self.hass.bus.fire(self.events.EVENT_REGISTER_RECEIVED, event_data)

    async def start_luxpower_client_daemon(self, ):
        while not self._stop_client:
            if not self._connected:
                try:
                    _LOGGER.info("luxpower daemon: Connecting to lux power server")
                    print("luxpower daemon: Connecting to lux power server")
                    await self.hass.loop.create_connection(self.factory, self.server, self.port)
                    print("luxpower daemon: Connected to lux power server")
                except Exception as e:
                    print("Exception luxpower daemon client in open connection retrying in 10 seconds", e)
                    _LOGGER.error(f"Exception luxpower daemon client in open connection retrying in 10 seconds : {e}")
            await asyncio.sleep(10)
        print("Exiting start_luxpower_client_daemon")

    async def get_register_data(self, address_bank):
        try:
            packet = self.lxpPacket.prepare_packet_for_read(address_bank * 40, 40, type=LXPPacket.READ_INPUT)
            self._transport.write(packet)
        except Exception as e:
            print("Exception get_register_data", e)
            _LOGGER.error(f"close error : {e}")

    async def get_holding_data(self, address_bank):
        try:
            packet = self.lxpPacket.prepare_packet_for_read(address_bank * 40, 40, type=LXPPacket.READ_HOLD)
            self._transport.write(packet)
        except Exception as e:
            print("Exception get_holding_data", e)
            _LOGGER.error(f"close error : {e}")

    def stop_client(self):
        print("stop_client called")
        self._stop_client = True
        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                print("Exception ", e)
        print("stop client finished")

    async def reconnect(self):
        _LOGGER.info("Reconnecting to Luxpower server")
        if self._transport is not None:
            try:
                self._transport.close()
            except Exception as e:
                print("Exception ", e)
                _LOGGER.error(f"close error : {e}")
        self._connected = False
        print("reconnect client finished")
        _LOGGER.debug("reconnect finished finished")
