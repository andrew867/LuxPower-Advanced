"""

This is a docstring placeholder.

This is where we will describe what this module does

"""
import logging
from typing import Any, Dict, List, Optional

import voluptuous as vol
from homeassistant.components.number import NumberEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.helpers.entity import DeviceInfo

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    VERSION,
)
from .helpers import Event
from .LXPPacket import LXPPacket

"""
Setup some options from this page in Home-assistant and allow times and % to be set.

Examples would be AC Charge enable / disable
AC Charge start Time 1 allow to set time via GUI
AC Charge Power Rate % allow to pick 1-100 ?

"""

_LOGGER = logging.getLogger(__name__)


def floatzero(incoming):
    """

    This is a docstring placeholder.

    This is where we will describe what this function does

    """
    try:
        value_we_got = float(incoming)
    except Exception:
        value_we_got = 0
    return value_we_got


async def async_setup_entry(hass, config_entry: ConfigEntry, async_add_devices):
    """Set up the number platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux number platform")
    _LOGGER.info("Options %s", len(config_entry.options))
    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    entityID_prefix = SERIAL if USE_SERIAL else ""
    hyphen = " -" if USE_SERIAL else "-"
    # Get Rid Of Hyphen 15/02/2023
    hyphen = ""

    event = Event(dongle=DONGLE)
    # luxpower_client = hass.data[event.CLIENT_DAEMON]

    _LOGGER.info(f"Lux number platform_config: {platform_config}")

    maxperc = 100.0
    maxbyte = 255.0
    maxtime = 65000.0
    maxnumb = 65535.0

    # fmt: off
    numberEntities: List[LuxNormalNumberEntity] = []

    register_address = 64
    name = f"Lux {entityID_prefix}{hyphen} System Charge Power Rate(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 65
    name = f"Lux {entityID_prefix}{hyphen} System Discharge Power Rate(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 66
    name = f"Lux {entityID_prefix}{hyphen} AC Charge Power Rate(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 67
    name = f"Lux {entityID_prefix}{hyphen} AC Battery Charge Level(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 68
    name = f"Lux {entityID_prefix}{hyphen} AC Charge Start1"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 69
    name = f"Lux {entityID_prefix}{hyphen} AC Charge End1"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 70
    name = f"Lux {entityID_prefix}{hyphen} AC Charge Start2"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 71
    name = f"Lux {entityID_prefix}{hyphen} AC Charge End2"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 72
    name = f"Lux {entityID_prefix}{hyphen} AC Charge Start3"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 73
    name = f"Lux {entityID_prefix}{hyphen} AC Charge End3"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 74
    name = f"Lux {entityID_prefix}{hyphen} Priority Charge Rate(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 75
    name = f"Lux {entityID_prefix}{hyphen} Priority Charge Level(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 76
    name = f"Lux {entityID_prefix}{hyphen} Force Charge Start1"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 77
    name = f"Lux {entityID_prefix}{hyphen} Force Charge End1"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 78
    name = f"Lux {entityID_prefix}{hyphen} Force Charge Start2"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 79
    name = f"Lux {entityID_prefix}{hyphen} Force Charge End2"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 80
    name = f"Lux {entityID_prefix}{hyphen} Force Charge Start3"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 81
    name = f"Lux {entityID_prefix}{hyphen} Force Charge End3"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 82
    name = f"Lux {entityID_prefix}{hyphen} Forced Discharge Power Rate(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 83
    name = f"Lux {entityID_prefix}{hyphen} Forced Discharge Battery Level(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 84
    name = f"Lux {entityID_prefix}{hyphen} Force Discharge Start1"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 85
    name = f"Lux {entityID_prefix}{hyphen} Force Discharge End1"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 86
    name = f"Lux {entityID_prefix}{hyphen} Force Discharge Start2"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 87
    name = f"Lux {entityID_prefix}{hyphen} Force Discharge End2"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 88
    name = f"Lux {entityID_prefix}{hyphen} Force Discharge Start3"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 89
    name = f"Lux {entityID_prefix}{hyphen} Force Discharge End3"
    numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 0.0, maxtime, "mdi:timer-outline", False, event))

    register_address = 103
    name = f"Lux {entityID_prefix}{hyphen} Feed-in Grid Power(%)"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxbyte, "mdi:car-turbocharger", False, event))

    register_address = 105
    name = f"Lux {entityID_prefix}{hyphen} On-grid Discharge Cut-off SOC"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    register_address = 119
    name = f"Lux {entityID_prefix}{hyphen} CT Clamp Offset Amount"
    numberEntities.append(LuxNormalNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxnumb, "mdi:knob", False, event))

    register_address = 125
    name = f"Lux {entityID_prefix}{hyphen} Off-grid Discharge Cut-off SOC"
    numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxperc, "mdi:car-turbocharger", False, event))

    #    register_address = 199
    #    name = f'Lux {entityID_prefix}{hyphen} CT Clamp Offset Amount'
    #    numberEntities.append(LuxNormalNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, register_address, name, 42.0, maxnumb, "mdi:knob", False, event))

    async_add_devices(numberEntities, True)

    _LOGGER.info("LuxPower number async_setup_platform number done")

    # fmt: on


class LuxNormalNumberEntity(NumberEntity):
    """Representation of a Normal Number entity."""

    def __init__(self, hass, host, port, dongle, serial, register_address, name, state, maxval, icon, assumed, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = name
        self.dongle = dongle
        self.serial = serial
        self._state = state
        self._read_value = 0
        self._maxval = maxval
        self._icon = icon
        self._assumed = assumed
        self._register_address = register_address
        self.registers: Dict[int, str] = {}
        self.event = event
        self.hour_val = -1
        self.minute_val = -1

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass %s", self._name)
        if self.hass is not None:
            if self._register_address == 21:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_21_RECEIVED, self.push_update)
            elif 0 <= self._register_address <= 39:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update)
            elif 40 <= self._register_address <= 79:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK1_RECEIVED, self.push_update)
            elif 80 <= self._register_address <= 119:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK2_RECEIVED, self.push_update)
            elif 120 <= self._register_address <= 159:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK3_RECEIVED, self.push_update)
            elif 160 <= self._register_address <= 199:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK4_RECEIVED, self.push_update)

    def convert_to_time(self, value):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    def push_update(self, event):
        _LOGGER.debug(
            f"Register Event Received Lux****NumberEntity: {self._name} - Register Address: {self._register_address}"
        )

        registers = event.data.get("registers", {})
        self.registers = registers

        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self._register_address} is in register.keys")
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            oldstate = self._state
            self._state = float(register_val)
            if oldstate != self._state:
                _LOGGER.debug(f"Changing the number from {oldstate} to {self._state}")
                if self.is_time_entity:
                    self.hour_val, self.minute_val = self.convert_to_time(register_val)
                    _LOGGER.debug(f"Translating To Time {self.hour_val}:{self.minute_val}")
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
            sw_version=VERSION,
        )

    @property
    def is_time_entity(self):
        return False

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_numbernormal_{self._register_address}"

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
    def native_value(self):
        """Return the current value."""
        return self._state

    @property
    def native_min_value(self):
        """Return the minimum value."""
        return 0.0

    @property
    def native_max_value(self):
        """Return the maximum value."""
        return self._maxval

    @property
    def native_step(self):
        """Return the value step."""
        return 1.0

    def set_register(self, new_value=0):
        _LOGGER.debug("Started set_register")

        lxpPacket = LXPPacket(
            debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
        )

        self._read_value = lxpPacket.register_io_with_retry(
            self._host, self._port, self._register_address, value=new_value, iotype=lxpPacket.WRITE_SINGLE
        )

        if self._read_value is not None:
            # Write has been successful
            _LOGGER.info(
                f"WRITE Register OK - Setting INVERTER Register: {self._register_address} Value: {self._read_value}"
            )
        else:
            # Write has been UNsuccessful
            _LOGGER.warning(f"Cannot WRITE Register: {self._register_address} Value: {new_value}")

        return self._read_value

    def get_register(self):
        _LOGGER.debug("Started get_register")

        lxpPacket = LXPPacket(
            debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
        )

        self._read_value = lxpPacket.register_io_with_retry(
            self._host, self._port, self._register_address, value=1, iotype=lxpPacket.READ_HOLD
        )

        if self._read_value is not None:
            # Read has been successful - use read value
            _LOGGER.info(
                f"READ Register OK - Using INVERTER Register: {self._register_address} Value: {self._read_value}"
            )
        else:
            # Read has been UNsuccessful
            _LOGGER.warning(f"Cannot READ Register: {self._register_address}")

        return self._read_value

    def set_native_value(self, value):
        """Update the current value."""
        num_value = float(value)
        if int(num_value) != int(floatzero(self._state)):
            _LOGGER.debug(f"Started set_value {num_value}")

            if num_value < self.min_value or num_value > self.max_value:
                raise vol.Invalid(
                    f"Invalid value for {self.entity_id}: {value} (range {self.min_value} - {self.max_value})"
                )

            self._read_value = self.set_register(int(num_value))
            if self._read_value is not None:
                _LOGGER.info(
                    f"CAN confirm successful WRITE of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                )
                self._read_value = self.get_register()
                if self._read_value is not None:
                    _LOGGER.info(
                        f"CAN confirm successful READ_BACK of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                    if self._read_value == int(num_value):
                        _LOGGER.info(
                            f"CAN confirm READ_BACK value is same as that sent to SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                        )
                        self._state = self._read_value
                        if self.is_time_entity:
                            self.hour_val, self.minute_val = self.convert_to_time(int(self._state))
                            _LOGGER.debug(f"Translating To Time {self.hour_val}:{self.minute_val}")
                        self.schedule_update_ha_state()
                    else:
                        _LOGGER.warning(
                            f"CanNOT confirm READ_BACK value is same as that sent to SET Register: {self._register_address} ValueSENT: {num_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                        )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm successful READ_BACK of SET Register: {self._register_address} Entity: {self.entity_id}"
                    )
            else:
                _LOGGER.warning(
                    f"CanNOT confirm successful WRITE of SET Register: {self._register_address} Entity: {self.entity_id}"
                )


class LuxPercentageNumberEntity(LuxNormalNumberEntity):
    """Representation of a Percentage Number entity."""

    @property
    def is_time_entity(self):
        return False

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_numberpercent_{self._register_address}"

    @property
    def native_unit_of_measurement(self) -> Optional[str]:
        return "%"


class LuxTimeNumberEntity(LuxNormalNumberEntity):
    """Representation of a Time Number entity."""

    @property
    def is_time_entity(self):
        return True

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_hour_{self._register_address}"

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes["hour"] = self.hour_val
        state_attributes["minute"] = self.minute_val
        return state_attributes
