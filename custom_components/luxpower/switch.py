import asyncio
import logging
import socket
import struct
from typing import Any, Dict, Optional, Union

from homeassistant.components.binary_sensor import DEVICE_CLASS_OPENING
from homeassistant.components.switch import SwitchEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
)
from .helpers import Event
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices):
    """Set up the switch platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux switch platform")
    _LOGGER.info("Set up the switch platform %s", config_entry.data)
    _LOGGER.info("Options %s", len(config_entry.options))
    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE_SERIAL = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL_NUMBER = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    entityID_prefix = SERIAL_NUMBER if USE_SERIAL else ""
    hyphen = " -" if USE_SERIAL else "-"
    # Get Rid Of Hyphen 15/02/2023
    hyphen = ""

    event = Event(dongle=DONGLE_SERIAL)
    luxpower_client = hass.data[event.CLIENT_DAEMON]
    binarySwitchs = []
    device_class = DEVICE_CLASS_OPENING

    _LOGGER.info(f"Lux switch platform_config: {platform_config}")

    # fmt: off

    """ Common Switches Displayed In The App/Web """
    switches = [
        {"name": f"Lux {entityID_prefix}{hyphen} Normal/Standby(ON/OFF)", "register_address": 21, "bitmask": LXPPacket.NORMAL_OR_STANDBY, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Power Backup Enable", "register_address": 21, "bitmask": LXPPacket.POWER_BACKUP_ENABLE, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Feed-In Grid", "register_address": 21, "bitmask": LXPPacket.FEED_IN_GRID, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} DCI Enable", "register_address": 21, "bitmask": LXPPacket.DCI_ENABLE, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} GFCI Enable", "register_address": 21, "bitmask": LXPPacket.GFCI_ENABLE, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Seamless EPS Switching", "register_address": 21, "bitmask": LXPPacket.SEAMLESS_EPS_SWITCHING, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Grid On Power SS", "register_address": 21, "bitmask": LXPPacket.GRID_ON_POWER_SS, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} Neutral Detect Enable", "register_address": 21, "bitmask": LXPPacket.NEUTRAL_DETECT_ENABLE, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} Anti Island Enable", "register_address": 21, "bitmask": LXPPacket.ANTI_ISLAND_ENABLE, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} DRMS Enable", "register_address": 21, "bitmask": LXPPacket.DRMS_ENABLE, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} OVF Load Derate Enable", "register_address": 21, "bitmask": LXPPacket.OVF_LOAD_DERATE_ENABLE, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} R21 Unknown Bit 12", "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_12, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} R21 Unknown Bit 3", "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_3, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} AC Charge Enable", "register_address": 21, "bitmask": LXPPacket.AC_CHARGE_ENABLE, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Charge Priority", "register_address": 21, "bitmask": LXPPacket.CHARGE_PRIORITY, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Force Discharge Enable", "register_address": 21, "bitmask": LXPPacket.FORCED_DISCHARGE_ENABLE, "enabled": True},
        {"name": f"Lux {entityID_prefix}{hyphen} Take Load Together", "register_address": 110, "bitmask": LXPPacket.TAKE_LOAD_TOGETHER, "enabled": False},
        {"name": f"Lux {entityID_prefix}{hyphen} Charge Last", "register_address": 110, "bitmask": LXPPacket.CHARGE_LAST, "enabled": False},
    ]

    for switch_data in switches:
        binarySwitchs.append(LuxPowerRegisterValueSwitchEntity(hass, HOST, PORT, DONGLE_SERIAL, SERIAL_NUMBER, switch_data["register_address"], switch_data["bitmask"], switch_data["name"], switch_data["enabled"], device_class, luxpower_client, event))

    # fmt: on

    async_add_devices(binarySwitchs, True)

    _LOGGER.info("LuxPower switch async_setup_platform switch done")


class LuxPowerRegisterValueSwitchEntity(SwitchEntity):
    """Represent a binary sensor."""

    def __init__(self, hass, host, port, dongle, serial, register_address, bitmask, object_id, create_enabled, device_class, luxpower_client, event: Event) -> None:  # fmt: skip
        super().__init__()
        self.hass = hass
        self._host = host
        self._port = port
        self.dongle = dongle
        self.serial = serial
        self._register_address = register_address
        self._register_value = None
        self._bitmask = bitmask
        self._name = object_id
        self._object_id = object_id
        self._device_class = device_class
        self._state = False
        self._read_value = 0
        self._attr_entity_registry_enabled_default = create_enabled
        self.luxpower_client = luxpower_client
        # self.lxppacket = luxpower_client.lxpPacket
        self.registers: Dict[int, int] = {}
        self.totalregs: Dict[int, int] = {}
        self.event = event

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass %s", self._name)
        self.is_added_to_hass = True
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
            self.totalregs = self.luxpower_client.lxpPacket.regValuesInt
            # _LOGGER.debug("totalregs: %s" , self.totalregs)
            oldstate = self._state
            self._state = register_val & self._bitmask == self._bitmask
            if oldstate != self._state:
                _LOGGER.debug(
                    f"Reading: {self._register_address} {self._bitmask} Old State {oldstate} Updating state to {self._state} - {self._name}"
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

    # @Throttle(MIN_TIME_BETWEEN_UPDATES)
    def update(self):
        # self._state = self._protocol._dp_values.get(self._dp_id, None)
        _LOGGER.debug(f"{self._register_address} {self._bitmask} updating state to {self._state}")
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_{self._register_address}_{self._bitmask}"

    @property
    def available(self):
        return True

    @property
    def device_class(self):
        """Return device class."""
        return self._device_class

    @property
    def should_poll(self):
        """Return True if entity has to be polled for state."""
        return False

    @property
    def name(self):
        """Return entity name."""
        return self._name

    @property
    def is_on(self):
        """Return true if the binary sensor is on."""
        return self._state

    def turn_on(self, **kwargs: Any) -> None:
        _LOGGER.debug("turn on called ")
        self.set_register_bit(True)

    def turn_off(self, **kwargs: Any) -> None:
        _LOGGER.debug("turn off called ")
        self.set_register_bit(False)

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

    def ping_register(self):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((self._host, self._port))
            _LOGGER.debug("Connected to server")
            lxpPacket = LXPPacket(
                dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
            )
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1, type=LXPPacket.READ_HOLD)
            sock.send(packet)
            sock.close()
        except Exception as e:
            _LOGGER.error("Exception ", e)

    def set_register_bit(self, bit_polarity=False):
        lxpPacket = LXPPacket(
            debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
        )

        self._read_value = lxpPacket.register_io_with_retry(
            self._host, self._port, self._register_address, value=1, iotype=lxpPacket.READ_HOLD
        )

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

        new_value = lxpPacket.update_value(old_value, self._bitmask, bit_polarity)

        if new_value != old_value:
            _LOGGER.info(
                f"Writing: OLD: {old_value} REGISTER: {self._register_address} MASK: {self._bitmask} NEW {new_value}"
            )
            self._read_value = lxpPacket.register_io_with_retry(
                self._host, self._port, self._register_address, value=new_value, iotype=lxpPacket.WRITE_SINGLE
            )

            if self._read_value is not None:
                _LOGGER.info(
                    f"CAN confirm successful WRITE of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                )
                if self._read_value == new_value:
                    _LOGGER.info(
                        f"CAN confirm WRITTEN value is same as that sent to SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm WRITTEN value is same as that sent to SET Register: {self._register_address} ValueSENT: {num_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                    )
            else:
                _LOGGER.warning(
                    f"CanNOT confirm successful WRITE of SET Register: {self._register_address} Entity: {self.entity_id}"
                )

        _LOGGER.debug("set_register_bit done")

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:
            lxpPacket = LXPPacket(
                dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
            )
            _LOGGER.debug("Attrib totalregs: %s", self.totalregs)
            _LOGGER.debug("Attrib registers: %s", self.registers)
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(68, 0))
            state_attributes["AC_CHARGE_START"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(69, 0))
            state_attributes["AC_CHARGE_END"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(70, 0))
            state_attributes["AC_CHARGE_START_1"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(71, 0))
            state_attributes["AC_CHARGE_END_1"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(72, 0))
            state_attributes["AC_CHARGE_START_2"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(73, 0))
            state_attributes["AC_CHARGE_END_2"] = f"{hour}:{min}"
        if self._register_address == 21 and self._bitmask == LXPPacket.CHARGE_PRIORITY:
            lxpPacket = LXPPacket(
                dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
            )
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(76, 0))
            state_attributes["PRIORITY_CHARGE_START"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(77, 0))
            state_attributes["PRIORITY_CHARGE_END"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(78, 0))
            state_attributes["PRIORITY_CHARGE_START_1"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(79, 0))
            state_attributes["PRIORITY_CHARGE_END_1"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(80, 0))
            state_attributes["PRIORITY_CHARGE_START_2"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(81, 0))
            state_attributes["PRIORITY_CHARGE_END_2"] = f"{hour}:{min}"
        if self._register_address == 21 and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE:
            lxpPacket = LXPPacket(
                dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
            )
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(84, 0))
            state_attributes["FORCED_DISCHARGE_START"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(85, 0))
            state_attributes["FORCED_DISCHARGE_END"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(86, 0))
            state_attributes["FORCED_DISCHARGE_START_1"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(87, 0))
            state_attributes["FORCED_DISCHARGE_END_1"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(88, 0))
            state_attributes["FORCED_DISCHARGE_START_2"] = f"{hour}:{min}"
            hour, min = lxpPacket.convert_to_time(self.totalregs.get(89, 0))
            state_attributes["FORCED_DISCHARGE_END_2"] = f"{hour}:{min}"
        return state_attributes
