import asyncio
import struct

from homeassistant.components.binary_sensor import DEVICE_CLASS_OPENING
from homeassistant.components.switch import SwitchEntity
import logging
from typing import Optional, Union, Any, Dict

from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo

from .const import DOMAIN, ATTR_LUX_PORT, ATTR_LUX_HOST, ATTR_LUX_DONGLE_SERIAL, ATTR_LUX_SERIAL_NUMBER, \
    ATTR_LUX_USE_SERIAL
from .helpers import Event
from .LXPPacket import LXPPacket
import socket

_LOGGER = logging.getLogger(__name__)


async def refreshSwitches(hass: HomeAssistant, dongle):
    await asyncio.sleep(20)
    _LOGGER.info("Refreshing switches")
    status = await hass.services.async_call(DOMAIN, 'luxpower_refresh_holdings', {'dongle': dongle}, blocking=True)
    _LOGGER.info(f"Refreshing switches done with status : {status}")


async def async_setup_entry(hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices):
    """Set up the sensor platform."""
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

    entityID_prefix = SERIAL_NUMBER if USE_SERIAL else ''

    event = Event(dongle=DONGLE_SERIAL)
    luxpower_client = hass.data[event.CLIENT_DAEMON]
    binarySwitchs = []
    device_class = DEVICE_CLASS_OPENING

    _LOGGER.info(f"Lux switch platform_config: {platform_config}")


    """ Common Switches Displayed In The App/Web """
    switches = [
        {"name": f'Lux {entityID_prefix} Normal/Standby(ON/OFF)', "register_address": 21, "bitmask": LXPPacket.NORMAL_OR_STANDBY, "enabled": True},
        {"name": f'Lux {entityID_prefix} Power Backup Enable', "register_address": 21, "bitmask": LXPPacket.POWER_BACKUP_ENABLE, "enabled": True},
        {"name": f'Lux {entityID_prefix} Feed-In Grid', "register_address": 21, "bitmask": LXPPacket.FEED_IN_GRID, "enabled": True},
        {"name": f'Lux {entityID_prefix} DCI Enable', "register_address": 21, "bitmask": LXPPacket.DCI_ENABLE, "enabled": True},
        {"name": f'Lux {entityID_prefix} GFCI Enable', "register_address": 21, "bitmask": LXPPacket.GFCI_ENABLE, "enabled": True},
        {"name": f'Lux {entityID_prefix} Seamless EPS Switching', "register_address": 21, "bitmask": LXPPacket.SEAMLESS_EPS_SWITCHING, "enabled": True},
        {"name": f'Lux {entityID_prefix} Grid On Power SS', "register_address": 21, "bitmask": LXPPacket.GRID_ON_POWER_SS, "enabled": False},
        {"name": f'Lux {entityID_prefix} Neutral Detect Enable', "register_address": 21, "bitmask": LXPPacket.NEUTRAL_DETECT_ENABLE, "enabled": False},
        {"name": f'Lux {entityID_prefix} Anti Island Enable', "register_address": 21, "bitmask": LXPPacket.ANTI_ISLAND_ENABLE, "enabled": False},
        {"name": f'Lux {entityID_prefix} DRMS Enable', "register_address": 21, "bitmask": LXPPacket.DRMS_ENABLE, "enabled": False},
        {"name": f'Lux {entityID_prefix} OVF Load Derate Enable', "register_address": 21, "bitmask": LXPPacket.OVF_LOAD_DERATE_ENABLE, "enabled": False},
        {"name": f'Lux {entityID_prefix} R21 Unknown Bit 12', "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_12, "enabled": False},
        {"name": f'Lux {entityID_prefix} R21 Unknown Bit 3', "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_3, "enabled": False},
        {"name": f'Lux {entityID_prefix} AC Charge Enable', "register_address": 21, "bitmask": LXPPacket.AC_CHARGE_ENABLE, "enabled": True},
        {"name": f'Lux {entityID_prefix} Charge Priority', "register_address": 21, "bitmask": LXPPacket.CHARGE_PRIORITY, "enabled": True},
        {"name": f'Lux {entityID_prefix} Force Discharge Enable', "register_address": 21, "bitmask": LXPPacket.FORCED_DISCHARGE_ENABLE, "enabled": True},
        {"name": f'Lux {entityID_prefix} Take Load Together', "register_address": 110, "bitmask": LXPPacket.TAKE_LOAD_TOGETHER, "enabled": False},
        {"name": f'Lux {entityID_prefix} Charge Last', "register_address": 110, "bitmask": LXPPacket.CHARGE_LAST, "enabled": False},
    ]

    for switch_data in switches:
        binarySwitchs.append(
            LuxPowerRegisterValueSwitchEntity(hass, HOST, PORT, DONGLE_SERIAL, SERIAL_NUMBER, switch_data["register_address"], switch_data["bitmask"], switch_data["name"], switch_data["enabled"], device_class, luxpower_client, event))

    async_add_devices(binarySwitchs, True)

    #  delay service call for some time to give the sensors and swiches time to initialise
    hass.async_create_task(refreshSwitches(hass, dongle=DONGLE_SERIAL))

    _LOGGER.info("LuxPower switch async_setup_platform switch done")


class LuxPowerRegisterValueSwitchEntity(SwitchEntity):
    """Represent a binary sensor."""

    def __init__(self, hass, host, port, dongle, serial, register_address, bitmask, object_id, create_enabled, device_class, luxpower_client, event: Event) -> None:
        super().__init__()
        self.hass = hass
        self._host = host
        self._port = port
        self.dongle = dongle
        self.serial = serial
        self._register_address = register_address
        self._bitmask = bitmask
        self._name = object_id
        self._object_id = object_id
        self._device_class = device_class
        self._state = False
        self._attr_entity_registry_enabled_default = create_enabled
        self.luxpower_client = luxpower_client
        # self.lxppacket = luxpower_client.lxpPacket
        self.registers = {}
        self.event = event

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hass %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_REGISTER_RECEIVED, self.push_update)
        return result

    def push_update(self, event):
        _LOGGER.info("switch: register event received")
        registers = event.data.get('registers', {})
        self.registers = registers
        if self._register_address in registers.keys():
            register_val = registers.get(self._register_address,None)
            if register_val is None:
                return
            oldstate = self._state
            self._state = register_val & self._bitmask == self._bitmask
            if oldstate != self._state:
                _LOGGER.debug("Reading: {} {} Old State {} Updating state to {} - {}".format(self._register_address, self._bitmask, oldstate, self._state, self._name))
                self.schedule_update_ha_state()
            if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:
                if 68 in registers.keys():
                    self.schedule_update_ha_state()
            if self._register_address == 21 and self._bitmask == LXPPacket.CHARGE_PRIORITY:
                if 76 in registers.keys():
                    self.schedule_update_ha_state()
            if self._register_address == 21 and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE:
                if 84 in registers.keys():
                    self.schedule_update_ha_state()
        return self._state

    # @Throttle(MIN_TIME_BETWEEN_UPDATES)
    def update(self):
        # self._state = self._protocol._dp_values.get(self._dp_id, None)
        _LOGGER.info("{} {} updating state to {}".format(self._register_address, self._bitmask, self._state))
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return "{}_{}_{}_{}".format(DOMAIN, self.dongle, self._register_address, self._bitmask)

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
        _LOGGER.info("turn on called ")
        self.set_register_bit(True)

    def turn_off(self, **kwargs: Any) -> None:
        _LOGGER.info("turn off called ")
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
            _LOGGER.info("Connected to server")
            lxpPacket = LXPPacket(dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1, type=LXPPacket.READ_HOLD)
            sock.send(packet)
            sock.close()
        except Exception as e:
            _LOGGER.info("Exception ", e)

    def set_register_bit(self, bit_polarity=False):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((self._host, self._port))
            _LOGGER.info("set_register_bit: Connected to server", self._host, self._port, self._register_address)
            lxpPacket = LXPPacket(debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1, type=LXPPacket.READ_HOLD)
            sock.send(packet)

            packet = sock.recv(1000)
            _LOGGER.info('Received: ', packet)
            data = lxpPacket.parse_packet(packet)
            _LOGGER.info(data)
            if not lxpPacket.packet_error:
                if lxpPacket.device_function == lxpPacket.READ_HOLD and lxpPacket.register == self._register_address:
                    if len(lxpPacket.value) == 2:
                        old_value = lxpPacket.convert_to_int(lxpPacket.value)
                        new_value = lxpPacket.update_value(old_value, self._bitmask, bit_polarity)
                        _LOGGER.info("OLD: ", old_value, " MASK: ", self._bitmask, " NEW: ", new_value)
                        _LOGGER.debug("Writing: OLD: {} REGISTER: {} MASK: {} NEW {}".format(old_value, self._register_address, self._bitmask, new_value))
                        packet = lxpPacket.prepare_packet_for_write(self._register_address, new_value)
                        _LOGGER.info("packet to be written ", packet)
                        sock.send(packet)
                        _LOGGER.info("written packet")

                        # self._state = bit_polarity
                        # self.schedule_update_ha_state()
                        # await asyncio.sleep(1)
                        # packet = sock.recv(1000)
                        # print('Received: ', packet)
                        # result = lxpPacket.parse_packet(packet)
                        # if not lxpPacket.packet_error:
                        #     print(result)
                    else:
                        _LOGGER.debug(f"Length of value packet is not 2, received: {len(lxpPacket.value)}")
                else:
                    _LOGGER.debug("Expected Type: ", lxpPacket.READ_HOLD, ' Received :', lxpPacket.device_function)
                    _LOGGER.debug("Expected Address: ", self._register_address, ' Received :', lxpPacket.register)
            else:
                _LOGGER.debug("LX Packet error")
            sock.close()
            _LOGGER.info("Closing socket...")
        except Exception as e:
            _LOGGER.debug("Exception ", e)
        _LOGGER.debug("set_register_bit done")

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:
            lxpPacket = LXPPacket(dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
            hour, min = lxpPacket.convert_to_time(self.registers.get(68,0))
            state_attributes["AC_CHARGE_START"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(69, 0))
            state_attributes["AC_CHARGE_END"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(70, 0))
            state_attributes["AC_CHARGE_START_1"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(71, 0))
            state_attributes["AC_CHARGE_END_1"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(72, 0))
            state_attributes["AC_CHARGE_START_2"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(73, 0))
            state_attributes["AC_CHARGE_END_2"] = "{}:{}".format(hour, min)
        if self._register_address == 21 and self._bitmask == LXPPacket.CHARGE_PRIORITY:
            lxpPacket = LXPPacket(dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
            hour, min = lxpPacket.convert_to_time(self.registers.get(76,0))
            state_attributes["PRIORITY_CHARGE_START"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(77, 0))
            state_attributes["PRIORITY_CHARGE_END"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(78, 0))
            state_attributes["PRIORITY_CHARGE_START_1"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(79, 0))
            state_attributes["PRIORITY_CHARGE_END_1"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(80, 0))
            state_attributes["PRIORITY_CHARGE_START_2"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(81, 0))
            state_attributes["PRIORITY_CHARGE_END_2"] = "{}:{}".format(hour, min)
        if self._register_address == 21 and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE:
            lxpPacket = LXPPacket(dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial)))
            hour, min = lxpPacket.convert_to_time(self.registers.get(84,0))
            state_attributes["FORCED_DISCHARGE_START"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(85, 0))
            state_attributes["FORCED_DISCHARGE_END"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(86, 0))
            state_attributes["FORCED_DISCHARGE_START_1"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(87, 0))
            state_attributes["FORCED_DISCHARGE_END_1"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(88, 0))
            state_attributes["FORCED_DISCHARGE_START_2"] = "{}:{}".format(hour, min)
            hour, min = lxpPacket.convert_to_time(self.registers.get(89, 0))
            state_attributes["FORCED_DISCHARGE_END_2"] = "{}:{}".format(hour, min)
        return state_attributes
