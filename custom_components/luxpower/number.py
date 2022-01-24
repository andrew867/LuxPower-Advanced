from homeassistant.components.number import NumberEntity
import voluptuous as vol
from typing import Optional, Union, Any, Dict
import logging
from .LXPPacket import LXPPacket
import socket

from . import EVENT_DATA_RECEIVED, DOMAIN, DATA_CONFIG, EVENT_REGISTER_RECEIVED, CLIENT_DAEMON

'''
Setup some options from this page in Home-assistant and allow times and % to be set.

Examples would be AC Charge enable / disable
AC Charge start Time 1 allow to set time via GUI
AC Charge Power Rate % allow to pick 1-100 ?

'''

_LOGGER = logging.getLogger(__name__)


async def async_setup_platform(hass, config, async_add_entities, discovery_info=None):
    print("In LuxPower number platform discovery")

    platform_config = hass.data[DATA_CONFIG]

    HOST = platform_config.get("host", "127.0.0.1")
    PORT = platform_config.get("port", 8000)
    DONGLE_SERIAL = platform_config.get("dongle_serial", "BA00000000")
    SERIAL_NUMBER = platform_config.get("serial_number", "0000000000")

    luxpower_client = hass.data[CLIENT_DAEMON]

    numberPercentageEntities = []

    register_address = 64
    name = 'AC Charge Power Rate'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, register_address, name, 42.0, "mdi:car-turbocharger", False,  str.encode(str(DONGLE_SERIAL)), str.encode(str(SERIAL_NUMBER))))

    async_add_entities(numberPercentageEntities, True)

    print("LuxPower number async_setup_platform number done")


class PercentageNumber(NumberEntity):
    """Representation of a demo Number entity."""

    def __init__(
        self, hass, host, port, register_address, name, state, icon, assumed, dongle_serial, serial_number):
        """Initialize the Demo Number entity."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = name
        self._state = state
        self._icon = icon
        self._assumed = assumed
        self._register_address = register_address
        self.dongle_serial = dongle_serial
        self.serial_number = serial_number
        self.registers = {}

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hass %s", self._name)
        if self.hass is not None:
            self.hass.bus.async_listen(EVENT_REGISTER_RECEIVED, self.push_update)
        return result

    def push_update(self, event):
        _LOGGER.info("register event received PercentageNumber ")
        registers = event.data.get('registers', {})
        self.registers = registers
        if self._register_address in registers.keys():
            register_val = registers.get(self._register_address,None)
            if register_val is None:
                return
            oldstate = self._state
            self._state = float(register_val)
            if oldstate != self._state:
                self.schedule_update_ha_state()
        return self._state

    @property
    def device_info(self):
        """Return device info."""
        return {
            "identifiers": {
                # Serial numbers are unique identifiers within a specific domain
                (DOMAIN, self.unique_id)
            },
            "name": self.name,
        }

    @property
    def unique_id(self) -> Optional[str]:
        return "{}_numberpercent_{}".format(DOMAIN, self._register_address)

    @property
    def should_poll(self):
        """No polling needed for a demo Number entity."""
        return False

    @property
    def name(self):
        """Return the name of the device if any."""
        return self._name

    @property
    def icon(self):
        """Return the icon to use for device if any."""
        return self._icon

    @property
    def assumed_state(self):
        """Return if the state is based on assumptions."""
        return self._assumed

    @property
    def value(self):
        """Return the current value."""
        return self._state

    @property
    def min_value(self):
        """Return the minimum value."""
        return 0.0

    @property
    def max_value(self):
        """Return the maximum value."""
        return 100.0

    @property
    def step(self):
        """Return the value step."""
        return 1.0

    def async_set_value(self, value):
        """Update the current value."""
        num_value = float(value)

        if num_value < self.min_value or num_value > self.max_value:
            raise vol.Invalid(
                f"Invalid value for {self.entity_id}: {value} (range {self.min_value} - {self.max_value})"
            )

        self.set_register(int(num_value))
        self._state = self.get_register()
        # self._state = num_value
        self.schedule_update_ha_state()

    def set_register(self, value=0):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((self._host, self._port))
            print("Connected to server")
            print("SER:", self.dongle_serial, self.serial_number)
            lxpPacket = LXPPacket(debug=True, dongle_serial=self.dongle_serial, serial_number=self.serial_number)
            packet = lxpPacket.prepare_packet_for_write(self._register_address, value)
            print("packet to be written ", packet)
            sock.send(packet)
            print("written packet")
            packet = sock.recv(1000)
            print('Received: ', packet)
            result = lxpPacket.parse_packet(packet)
            if not lxpPacket.packet_error:
                print(result)
            sock.close()
        except Exception as e:
            print("Exception ", e)
            # raise vol.Invalid(
            #     f"Couldn't set data for {self.entity_id}: {value} )"
            # )

    def get_register(self):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((self._host, self._port))
            print("Connected to server")
            lxpPacket = LXPPacket(debug=True, dongle_serial=self.dongle_serial, serial_number=self.serial_number)
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1)
            sock.send(packet)

            packet = sock.recv(1000)
            print('Received: ', packet)
            data = lxpPacket.parse_packet(packet)
            print(data)
            if not lxpPacket.packet_error:
                if lxpPacket.device_function == lxpPacket.READ_HOLD and lxpPacket.register == self._register_address:
                    if len(lxpPacket.value) == 2:
                        num_value = lxpPacket.convert_to_int(lxpPacket.value)
                        self._state = float(num_value)
                        self.schedule_update_ha_state()
                        # self._state = bit_polarity
                        # self.schedule_update_ha_state()
                        # await asyncio.sleep(1)
                        # packet = sock.recv(1000)
                        # print('Received: ', packet)
                        # result = lxpPacket.parse_packet(packet)
                        # if not lxpPacket.packet_error:
                        #     print(result)

            sock.close()
        except Exception as e:
            print("Exception ", e)

        return self._state
