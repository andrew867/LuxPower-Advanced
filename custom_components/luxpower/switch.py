"""

This is a docstring placeholder.

This is where we will describe what this module does

"""
import logging
from typing import Any, Dict, List, Optional

from homeassistant.components.switch import SwitchEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
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
from .LXPPacket import LXPPacket, prepare_binary_value

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices):
    """Set up the switch platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux switch platform")
    _LOGGER.info("Set up the switch platform %s", config_entry.data)
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

    _LOGGER.info(f"Lux switch platform_config: {platform_config}")

    # fmt: off

    switchEntities: List[SwitchEntity] = []

    """ Common Switches Displayed In The App/Web """
    switches = [
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Normal/Standby(ON/OFF)", "register_address": 21, "bitmask": LXPPacket.NORMAL_OR_STANDBY, "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Power Backup Enable", "register_address": 21, "bitmask": LXPPacket.POWER_BACKUP_ENABLE, "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Feed-In Grid", "register_address": 21, "bitmask": LXPPacket.FEED_IN_GRID, "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} DCI Enable", "register_address": 21, "bitmask": LXPPacket.DCI_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} GFCI Enable", "register_address": 21, "bitmask": LXPPacket.GFCI_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Seamless EPS Switching", "register_address": 21, "bitmask": LXPPacket.SEAMLESS_EPS_SWITCHING, "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Grid On Power SS", "register_address": 21, "bitmask": LXPPacket.GRID_ON_POWER_SS, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Neutral Detect Enable", "register_address": 21, "bitmask": LXPPacket.NEUTRAL_DETECT_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Anti Island Enable", "register_address": 21, "bitmask": LXPPacket.ANTI_ISLAND_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} DRMS Enable", "register_address": 21, "bitmask": LXPPacket.DRMS_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} OVF Load Derate Enable", "register_address": 21, "bitmask": LXPPacket.OVF_LOAD_DERATE_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} ISO Enabled", "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_12, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Low Voltage Ride Though Enable", "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_3, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Enable", "register_address": 21, "bitmask": LXPPacket.AC_CHARGE_ENABLE, "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Charge Priority", "register_address": 21, "bitmask": LXPPacket.CHARGE_PRIORITY, "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Enable", "register_address": 21, "bitmask": LXPPacket.FORCED_DISCHARGE_ENABLE, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Take Load Together", "register_address": 110, "bitmask": LXPPacket.TAKE_LOAD_TOGETHER, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Charge Last", "register_address": 110, "bitmask": LXPPacket.CHARGE_LAST, "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Peak-Shaving", "register_address": 179, "bitmask": LXPPacket.ENABLE_PEAK_SHAVING, "enabled": False},
    ]

    for entity_definition in switches:
        etype = entity_definition["etype"]
        if etype == "LVSE":
            switchEntities.append(LuxPowerRegisterValueSwitchEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))

    # fmt: on

    async_add_devices(switchEntities, True)

    _LOGGER.info("LuxPower switch async_setup_platform switch done")


class LuxPowerRegisterValueSwitchEntity(SwitchEntity):
    """Represent a LUX binary switch sensor."""

    _client: LuxPowerClient

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"switch.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event

        # Hidden Inherited Instance Attributes
        self._attr_entity_registry_enabled_default = entity_definition.get("enabled", False)

        # Hidden Class Extended Instance Attributes
        self._client = luxpower_client
        self._register_address = entity_definition["register_address"]
        self._register_value = None
        self._bitmask = entity_definition["bitmask"]
        self._attr_name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        self._attr_available = False
        # self._attr_device_class = DEVICE_CLASS_OPENING
        self._attr_should_poll = False
        self._state = False
        self._read_value = 0
        # self.lxppacket = luxpower_client.lxpPacket
        self.registers: Dict[int, int] = {}
        self.totalregs: Dict[int, int] = {}

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable)
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
            elif 200 <= self._register_address <= 239:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK5_RECEIVED, self.push_update)

    def push_update(self, event):
        registers = event.data.get("registers", {})
        self.registers = registers
        if self._register_address in registers.keys():
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            # Save current register int value
            self._register_value = register_val
            _LOGGER.debug(
                "switch: register event received - register: %s bitmask: %s", self._register_address, self._bitmask
            )
            self.totalregs = self._client.lxpPacket.regValuesInt
            # _LOGGER.debug("totalregs: %s" , self.totalregs)
            oldstate = self._state
            self._state = register_val & self._bitmask == self._bitmask
            if oldstate != self._state or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(
                    f"Reading: {self._register_address} {self._bitmask} Old State {oldstate} Updating state to {self._state} - {self._attr_name}"
                )
                self.schedule_update_ha_state()
            if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:
                if 68 in self.totalregs.keys():
                    self.schedule_update_ha_state()
            if self._register_address == 21 and self._bitmask == LXPPacket.CHARGE_PRIORITY:
                if 76 in self.totalregs.keys():
                    self.schedule_update_ha_state()
            if self._register_address == 21 and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE:
                if 84 in self.totalregs.keys():
                    self.schedule_update_ha_state()
        return self._state

    def gone_unavailable(self, event):
        _LOGGER.warning(f"Register: gone_unavailable event received Name: {self._attr_name} - Register Address: {self._register_address}")  # fmt: skip
        self._attr_available = False
        self.schedule_update_ha_state()

    def update(self):
        _LOGGER.debug(f"{self._register_address} {self._bitmask} updating state to {self._state}")
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_{self._register_address}_{self._bitmask}"

    @property
    def is_on(self):
        """Return true if the binary sensor is on."""
        return self._state

    async def async_turn_on(self) -> None:
        _LOGGER.debug("turn on called ")
        await self.set_register_bit(True)

    async def async_turn_off(self) -> None:
        _LOGGER.debug("turn off called ")
        await self.set_register_bit(False)

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

    async def set_register_bit(self, bit_polarity=False):
        self._read_value = await self._client.read(self._register_address)

        if self._read_value is not None:
            # Read has been successful - use read value
            _LOGGER.info(
                f"Read Register OK - Using INVERTER Register {self._register_address} value of {self._read_value}"
            )
            old_value = int(self._read_value)
        else:
            # Read has been UNsuccessful - use LAST KNOWN register value
            _LOGGER.warning(
                f"Cannot read Register - Using LAST KNOWN Register {self._register_address} value of {self._register_value}"
            )
            old_value = int(self._register_value)

        new_value = prepare_binary_value(old_value, self._bitmask, bit_polarity)

        if new_value != old_value:
            _LOGGER.info(
                f"Writing: OLD: {old_value} REGISTER: {self._register_address} MASK: {self._bitmask} NEW {new_value}"
            )
            self._read_value = await self._client.write(self._register_address, new_value)

            if self._read_value is not None:
                self._state = self._read_value & self._bitmask == self._bitmask
                self.async_write_ha_state()
                _LOGGER.info(
                    f"CAN confirm successful WRITE of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                )
                if self._read_value == new_value:
                    _LOGGER.info(
                        f"CAN confirm WRITTEN value is same as that sent to SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm WRITTEN value is same as that sent to SET Register: {self._register_address} ValueSENT: {new_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                    )
            else:
                _LOGGER.warning(
                    f"CanNOT confirm successful WRITE of SET Register: {self._register_address} Entity: {self.entity_id}"
                )

        self._attr_available = True
        _LOGGER.debug("set_register_bit done")

    def convert_to_time(self, value: int):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    # fmt: off

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:

            _LOGGER.debug("Attrib totalregs: %s", self.totalregs)
            _LOGGER.debug("Attrib registers: %s", self.registers)

            state_attributes["AC_CHARGE_START_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(68, 0)))
            state_attributes["AC_CHARGE_END_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(69, 0)))
            state_attributes["AC_CHARGE_START_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(70, 0)))
            state_attributes["AC_CHARGE_END_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(71, 0)))
            state_attributes["AC_CHARGE_START_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(72, 0)))
            state_attributes["AC_CHARGE_END_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(73, 0)))

        if self._register_address == 21 and self._bitmask == LXPPacket.CHARGE_PRIORITY:

            state_attributes["PRIORITY_CHARGE_START_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(76, 0)))
            state_attributes["PRIORITY_CHARGE_END_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(77, 0)))
            state_attributes["PRIORITY_CHARGE_START_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(78, 0)))
            state_attributes["PRIORITY_CHARGE_END_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(79, 0)))
            state_attributes["PRIORITY_CHARGE_START_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(80, 0)))
            state_attributes["PRIORITY_CHARGE_END_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(81, 0)))

        if self._register_address == 21 and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE:

            state_attributes["FORCED_DISCHARGE_START_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(84, 0)))
            state_attributes["FORCED_DISCHARGE_END_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(85, 0)))
            state_attributes["FORCED_DISCHARGE_START_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(86, 0)))
            state_attributes["FORCED_DISCHARGE_END_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(87, 0)))
            state_attributes["FORCED_DISCHARGE_START_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(88, 0)))
            state_attributes["FORCED_DISCHARGE_END_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(89, 0)))

        return state_attributes

    # fmt: on
