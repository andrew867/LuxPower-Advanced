"""Example Load Platform integration."""
from typing import Optional

from homeassistant.helpers import discovery
import voluptuous as vol
from datetime import timedelta
import logging
import asyncio
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)

"""
For Single Inverter:
INVERTER_ID = ''

For Multiple Inverters just put underscore followed by inverter serial
INVERTER_ID = '_1212016010'
"""

INVERTER_ID = ''

DOMAIN = 'luxpower' + INVERTER_ID
DATA_CONFIG = "luxpower" + INVERTER_ID + "_ism8"

EVENT_DATA_RECEIVED = "{}_data_receive_event".format(DOMAIN)
EVENT_REGISTER_RECEIVED = "{}_register_receive_event".format(DOMAIN)
CLIENT_DAEMON = "{}_client_daemon".format(DOMAIN)

SCHEME_REGISTER_BANK = vol.Schema({
        vol.Required("address_bank"):  vol.Coerce(int),
    })

# DONGLE_SERIAL = b'BA19520393'
# SERIAL_NUMBER = b'0102005050'


class LuxPowerClient(asyncio.Protocol):
    def __init__(self, hass, server, port, dongle_serial, serial_number):
        self.hass = hass
        self.server = server
        self.port = port

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
        self._transport = transport
        self._connected = True

    def connection_lost(self, exc: Optional[Exception]) -> None:
        self._connected = False
        print("Disconnected from LuxPower server")

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
                self.hass.bus.fire(EVENT_DATA_RECEIVED, event_data)
            elif self.lxpPacket.device_function == self.lxpPacket.READ_HOLD or self.lxpPacket.device_function == self.lxpPacket.WRITE_SINGLE:
                event_data = {"registers": result.get('registers', {})}
                print("EVENT REGISTER: ", event_data)
                self.hass.bus.fire(EVENT_REGISTER_RECEIVED, event_data)

    async def start_luxpower_client_daemon(self,):
        while True:
            if not self._connected:
                try:
                    print("luxpower daemon: Connecting to lux power server")
                    await self.hass.loop.create_connection(self.factory, self.server, self.port)
                    print("luxpower daemon: Connected to lux power server")
                except Exception as e:
                    print("Exception luxpower daemon client in open connection retrying in 10 seconds",e)
            await asyncio.sleep(10)

    async def get_register_data(self, address_bank):
        try:
            packet = self.lxpPacket.prepare_packet_for_read(address_bank*40, 40)
            self._transport.write(packet)
        except Exception as e:
            print("Exception get_register_data", e)


async def async_setup(hass, config):
    print("LuxPower init async_setup starting")
    """Your controller/hub specific code."""
    # Data that you want to share with your platforms
    hass.data[DATA_CONFIG] = config[DOMAIN]
    HOST = config[DOMAIN].get("host", "127.0.0.1")
    PORT = config[DOMAIN].get("port", 8000)
    DONGLE_SERIAL = config[DOMAIN].get("dongle_serial", "BA19520393")
    SERIAL_NUMBER = config[DOMAIN].get("serial_number", "0102005050")

    luxpower_client = LuxPowerClient(hass, server=HOST, port=PORT, dongle_serial=str.encode(str(DONGLE_SERIAL)), serial_number=str.encode(str(SERIAL_NUMBER)))
    # _server = await hass.loop.create_connection(luxpower_client.factory, HOST, PORT)
    hass.loop.create_task(luxpower_client.start_luxpower_client_daemon())
    # await hass.async_add_job(luxpower_client.start_luxpower_client_daemon())

    hass.data[CLIENT_DAEMON] = luxpower_client

    await hass.helpers.discovery.async_load_platform("switch", DOMAIN, {}, config)
    await hass.helpers.discovery.async_load_platform("sensor", DOMAIN, {}, config)

    async def handle_refresh_register_bank(call):
        """Handle the service call."""
        _LOGGER.info("handle_refresh_register_bank service: %s", DOMAIN)
        address_bank = call.data.get("address_bank")
        print("handle_refresh_register_bank service ", address_bank)
        await luxpower_client.get_register_data(address_bank)

    async def handle_refresh_registers(call):
        """Handle the service call."""
        _LOGGER.info("handle_refresh_registers service: %s", DOMAIN)
        print("handle_refresh_registers service ")
        for address_bank in range(0,3):
            await luxpower_client.get_register_data(address_bank)
            await asyncio.sleep(1)

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_register_bank",
        handle_refresh_register_bank,
        schema=SCHEME_REGISTER_BANK
    )

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_registers",
        handle_refresh_registers
    )

    print("LuxPower init async_setup done")
    return True

