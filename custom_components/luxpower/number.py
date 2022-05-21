from homeassistant.components.number import NumberEntity
import voluptuous as vol
from typing import Optional, Union, Any, Dict
import logging

from homeassistant.config_entries import ConfigEntry
from homeassistant.helpers.entity import DeviceInfo

from .const import DOMAIN, ATTR_LUX_HOST, ATTR_LUX_PORT, ATTR_LUX_DONGLE_SERIAL, ATTR_LUX_SERIAL_NUMBER
from .LXPPacket import LXPPacket
import socket
from .helpers import Event

'''
Setup some options from this page in Home-assistant and allow times and % to be set.

Examples would be AC Charge enable / disable
AC Charge start Time 1 allow to set time via GUI
AC Charge Power Rate % allow to pick 1-100 ?

'''

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(hass, config_entry: ConfigEntry, async_add_devices):
    """Set up the sensor platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux sensor platform")
    print("Options", len(config_entry.options))
    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, 8000)
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, 8000)
    event = Event(dongle=DONGLE)
    luxpower_client = hass.data[event.CLIENT_DAEMON]

    numberPercentageEntities = []
    register_address = 64
    name = 'LUX - System Charge Power Rate(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 65
    name = 'LUX - System Discharge Power Rate(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 66
    name = 'LUX - AC Charge Power Rate(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 67
    name = 'LUX - AC Battery Charge Level(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 74
    name = 'LUX - Priority Charge Rate(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 75
    name = 'LUX - Priority Charge Level(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 82
    name = 'LUX - Forced Discharge Power Rate(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))

    register_address = 83
    name = 'LUX - Forced Discharge Battery Level(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))

    register_address = 103
    name = 'LUX - Feed-in Grid Power(%)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    register_address = 105
    name = 'LUX - On-grid Discharge Cut-off SOC (?)'
    numberPercentageEntities.append(PercentageNumber(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, "mdi:car-turbocharger", False, event))
    
    async_add_devices(numberPercentageEntities, True)

    print("LuxPower number async_setup_platform number done")


class PercentageNumber(NumberEntity):
    """Representation of a demo Number entity."""

    def __init__(
        self, hass, host, port, dongle, serial, register_address, name, state, icon, assumed, event: Event):
        """Initialize the Demo Number entity."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = name
        self.dongle = dongle
        self.serial = serial
        self._state = state
        self._icon = icon
        self._assumed = assumed
        self._register_address = register_address
        # self.dongle_serial = dongle_serial
        # self.serial_number = serial_number
        self.registers = {}
        self.event = event

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hass %s", self._name)
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_REGISTER_RECEIVED, self.push_update)
        return result

    def push_update(self, event):
        _LOGGER.debug("register event received PercentageNumber ")
        registers = event.data.get('registers', {})
        self.registers = registers
        if self._register_address in registers.keys():
            register_val = registers.get(self._register_address,None)
            if register_val is None:
                return
            oldstate = self._state
            self._state = float(register_val)
            if oldstate != self._state:
                _LOGGER.debug(f"PercentageNumber: Changing the number from {oldstate} to {self._state}")
                self.schedule_update_ha_state()
        return self._state

    @property
    def device_info(self):
        """Return device info."""
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model="LUXPower Inverter",
            name=self.dongle,
            sw_version="1.1",
        )

    @property
    def unique_id(self) -> Optional[str]:
        return "{}_{}_numberpercent_{}".format(DOMAIN, self.dongle, self._register_address)

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

    def set_value(self, value):
        """Update the current value."""
        num_value = float(value)
        print("Calling set_value")

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
            print("SER:", str.encode(str(self.dongle)), str.encode(str(self.serial)))
            lxpPacket = LXPPacket(debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
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
            lxpPacket = LXPPacket(debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1, type=LXPPacket.READ_HOLD)
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
