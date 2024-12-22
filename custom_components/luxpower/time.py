"""

This is a docstring placeholder.

This is where we will describe what this module does

"""
import datetime
import logging
from typing import Any, Dict, List, Optional

import voluptuous as vol
from homeassistant.components.time import TimeEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.helpers.entity import DeviceInfo
from homeassistant.util import slugify

from .lxp.client import LuxPowerClient

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    VERSION,
)
from .helpers import Event

# from homeassistant.const import EntityCategory


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


hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(hass, config_entry: ConfigEntry, async_add_entities):
    """Set up the time platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux time platform")
    _LOGGER.info("Options %s", len(config_entry.options))

    global hyphen
    global nameID_midfix
    global entityID_midfix

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)
    luxpower_client = hass.data[config_entry.domain][config_entry.entry_id]['client']

    # Options For Name Midfix Based Upon Serial Number - Suggest Last Two Digits
    # nameID_midfix = SERIAL if USE_SERIAL else ""
    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""

    # Options For Entity Midfix Based Upon Serial Number - Suggest Full Serial Number
    entityID_midfix = SERIAL if USE_SERIAL else ""

    # Options For Hyphen Use Before Entity Description - Suggest No Hyphen As Of 15/02/23
    # hyphen = " -" if USE_SERIAL else "-"
    hyphen = ""

    event = Event(dongle=DONGLE)
    # luxpower_client = hass.data[event.CLIENT_DAEMON]

    _LOGGER.info(f"Lux time platform_config: {platform_config}")

    minnumb = 0.0

    # maxperc = 100.0
    # maxbyte = 255.0
    # maxtime = 65000.0
    maxtime = 15127.0
    # maxnumb = 65535.0

    # fmt: off

    timeEntities: List[LuxTimeTimeEntity] = []

    times = [
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start1", "register_address": 68, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End1", "register_address": 69, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start2", "register_address": 70, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End2", "register_address": 71, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start3", "register_address": 72, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End3", "register_address": 73, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start1", "register_address": 76, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End1", "register_address": 77, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start2", "register_address": 78, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End2", "register_address": 79, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start3", "register_address": 80, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End3", "register_address": 81, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start1", "register_address": 84, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End1", "register_address": 85, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start2", "register_address": 86, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End2", "register_address": 87, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start3", "register_address": 88, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End3", "register_address": 89, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start1", "register_address": 152, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC First End1", "register_address": 153, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start2", "register_address": 154, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC First End2", "register_address": 155, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start3", "register_address": 156, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} AC First End3", "register_address": 157, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving Start1", "register_address": 209, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving End1", "register_address": 210, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving Start2", "register_address": 211, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTTE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving End2", "register_address": 212, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
    ]

    for entity_definition in times:
        etype = entity_definition["etype"]
        if etype == "LTTE":
            timeEntities.append(LuxTimeTimeEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))

    async_add_entities(timeEntities, True)

    _LOGGER.info("LuxPower time async_setup_platform time done")

    # fmt: on


class LuxTimeTimeEntity(TimeEntity):
    """Representation of a Time entity."""

    _client: LuxPowerClient

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Time entity."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"time.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.register_address = entity_definition["register_address"]
        self.event = event

        # Hidden Inherited Instance Attributes
        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_time_{self.register_address}"
        self._attr_name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        # self._attr_entity_category = entity_definition.get("ent_cat", None)
        # self._attr_native_value = entity_definition.get("def_val", None)
        self._attr_native_value = None
        self._attr_assumed_state = entity_definition.get("assumed", False)
        self._attr_available = False
        self._attr_device_class = entity_definition.get("device_class", None)
        self._attr_icon = entity_definition.get("icon", None)
        # self._attr_mode = entity_definition.get("mode", NumberMode.AUTO)
        # self._attr_native_unit_of_measurement = entity_definition.get("unit_of_measurement", None)
        self._reg_min_value = entity_definition.get("min_val", None)
        self._reg_max_value = entity_definition.get("max_val", None)
        # self._attr_native_step = entity_definition.get("step", 1.0)
        self._attr_should_poll = False
        self._attr_entity_registry_enabled_default = entity_definition.get("enabled", False)

        # Hidden Class Extended Instance Attributes
        self._client = luxpower_client
        self._register_value = 0
        self._bitmask = entity_definition.get("bitmask", 0xFFFF)
        self._bitshift = entity_definition.get("bitshift", 0)
        self._divisor = entity_definition.get("divisor", 1)
        self._read_value = 0
        self._is_time_entity = True
        self._hour_value = -1
        self._minute_value = -1
        _LOGGER.debug(f"Time Finished Init {self._attr_name},  {self.entity_id},  {self.unique_id}")

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug(f"async_added_to_hass {self._attr_name},  {self.entity_id},  {self.unique_id}")
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable)
            if self.register_address == 21:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_21_RECEIVED, self.push_update)
            elif 0 <= self.register_address <= 39:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update)
            elif 40 <= self.register_address <= 79:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK1_RECEIVED, self.push_update)
            elif 80 <= self.register_address <= 119:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK2_RECEIVED, self.push_update)
            elif 120 <= self.register_address <= 159:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK3_RECEIVED, self.push_update)
            elif 160 <= self.register_address <= 199:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK4_RECEIVED, self.push_update)
            elif 200 <= self.register_address <= 239:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK5_RECEIVED, self.push_update)

    def convert_to_time(self, value):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    def push_update(self, event):
        _LOGGER.info(f"Register Event Received Lux****TimeEntity: {self._attr_name} - Register Address: {self.register_address}")  # fmt: skip

        registers = event.data.get("registers", {})
        if self.register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self.register_address} is in register.keys")
            register_val = registers.get(self.register_address, None)
            if register_val is None:
                return
            # Save current register int value
            self._register_value = register_val
            oldstate = self._attr_native_value
            self._hour_value, self._minute_value = self.convert_to_time(register_val)
            self._attr_native_value = datetime.time(self._hour_value, self._minute_value)
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(f"Changing the Time from {oldstate} to {self._attr_native_value}")
                self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        _LOGGER.warning(f"Register: gone_unavailable event received Name: {self._attr_name} - Register Address: {self.register_address}")  # fmt: skip
        self._attr_available = False
        self.schedule_update_ha_state()

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

    async def async_set_value(self, value):
        """Update the current Time value."""
        _LOGGER.info(f"TIME set_value called {value}")

        if value != self._attr_native_value:
            _LOGGER.debug(f"Started set_value {value}")
            new_reg_value = value.minute * 256 + value.hour

            if new_reg_value < self._reg_min_value or new_reg_value > self._reg_max_value:
                raise vol.Invalid(
                    f"Invalid value for {self.entity_id}: {new_reg_value} (range {self._reg_min_value} - {self._reg_max_value})"
                )
                return

            if self._bitmask != 0xFFFF:
                # Not A Full Bitmask 16 bit Integer - Partial Bitmask So READ Register 1st if Possible

                self._read_value = await self._client.read(self.register_address)

                if self._read_value is not None:
                    # Read has been successful - use read value
                    _LOGGER.debug(
                        f"Read Register OK - Using INVERTER Register {self.register_address} value of {self._read_value}"
                    )
                    old_value = int(self._read_value)
                else:
                    # Read has been UNsuccessful - use LAST KNOWN register value
                    _LOGGER.warning(
                        f"Cannot read Register - Using LAST KNOWN Register {self.register_address} value of {self._register_value}"
                    )
                    old_value = int(self._register_value)
            else:
                old_value = int(self._register_value)  # Can be anything!!

            new_value = (old_value & ~self._bitmask) | ((int(round(float(new_reg_value) * self._divisor, 0)) << self._bitshift) & self._bitmask)  # fmt: skip

            _LOGGER.debug(
                f"ENTITY_ID: {self.entity_id} VALUE: {new_reg_value} OLD: {old_value} REGISTER: {self.register_address} MASK: {self._bitmask:04x} SHIFT: {self._bitshift} DIVISOR: {self._divisor} NEW: {new_value}"
            )

            if new_value != old_value or self._bitmask == 0xFFFF:
                _LOGGER.debug(
                    f"Writing: OLD: {old_value} REGISTER: {self.register_address} MASK: {self._bitmask} NEW {new_value}"
                )
                self._read_value = await self._client.write(self.register_address, new_value)

                if self._read_value is not None:
                    _LOGGER.debug(
                        f"CAN confirm successful WRITE of SET Register: {self.register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                    if self._read_value == new_value:
                        _LOGGER.debug(
                            f"CAN confirm WRITTEN value is same as that sent to SET Register: {self.register_address} Value: {self._read_value} Entity: {self.entity_id}"
                        )
                        self._attr_native_value = value
                        if self._is_time_entity:
                            self._hour_value = self._attr_native_value.hour
                            self._minute_value = self._attr_native_value.minute
                            _LOGGER.debug(f"Translating To Time {self._hour_value}:{self._minute_value}")
                        self.async_write_ha_state()
                    else:
                        _LOGGER.warning(
                            f"CanNOT confirm WRITTEN value is same as that sent to SET Register: {self.register_address} ValueSENT: {new_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                        )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm successful WRITE of SET Register: {self.register_address} Entity: {self.entity_id}"
                    )

            _LOGGER.debug("set_value done")

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes["hour"] = self._hour_value
        state_attributes["minute"] = self._minute_value
        return state_attributes
