import struct

from homeassistant.components.binary_sensor import DEVICE_CLASS_OPENING
from homeassistant.components.switch import SwitchEntity
import logging
from typing import Optional, Union, Any, Dict
from . import EVENT_DATA_RECEIVED, INVERTER_ID, DOMAIN, DATA_CONFIG, EVENT_REGISTER_RECEIVED, CLIENT_DAEMON
from .LXPPacket import LXPPacket
import socket

_LOGGER = logging.getLogger(__name__)


async def async_setup_platform(hass, config, async_add_entities, discovery_info=None):
    print("In LuxPower switch platform discovery")
    platform_config = hass.data[DATA_CONFIG]

    HOST = platform_config.get("host", "127.0.0.1")
    PORT = platform_config.get("port", 8000)
    DONGLE_SERIAL = platform_config.get("dongle_serial", "BA19520393")
    SERIAL_NUMBER = platform_config.get("serial_number", "0102005050")

    luxpower_client = hass.data[CLIENT_DAEMON]
    device_class = DEVICE_CLASS_OPENING

    switch_configs = [
        {'name': 'Normal/Standby(ON/OFF)', 'key': 'normal_standby', 'bitmask': LXPPacket.NORMAL_OR_STANDBY},
        {'name': 'Power Backup Enable', 'key': 'power_backup_enable', 'bitmask': LXPPacket.POWER_BACKUP_ENABLE},
        {'name': 'Feed-In Grid', 'key': 'feed_in_grid', 'bitmask': LXPPacket.FEED_IN_GRID},
        {'name': 'DCI Enable', 'key': 'dci_enable', 'bitmask': LXPPacket.DCI_ENABLE},
        {'name': 'GFCI Enable', 'key': 'gfci_enable', 'bitmask': LXPPacket.GFCI_ENABLE},
        {'name': 'Seamless EPS Switching', 'key': 'seamless_eps_switching','bitmask': LXPPacket.SEAMLESS_EPS_SWITCHING},
        {'name': 'Grid On Power SS', 'key': 'grid_on_power_ss', 'bitmask': LXPPacket.GRID_ON_POWER_SS},
        {'name': 'Neutral Detect Enable', 'key': 'neutral_detect_enable', 'bitmask': LXPPacket.NEUTRAL_DETECT_ENABLE},
        {'name': 'Anti Island Enable', 'key': 'anti_island_enable', 'bitmask': LXPPacket.ANTI_ISLAND_ENABLE},
        {'name': 'DRMS Enable', 'key': 'drms_enable', 'bitmask': LXPPacket.DRMS_ENABLE},
        {'name': 'OVF Load Derate Enable', 'key': 'ovf_load_derate_enable','bitmask': LXPPacket.OVF_LOAD_DERATE_ENABLE},
        {'name': 'R21 Unknown Bit 12', 'key': 'r21_unknown_bit_12', 'bitmask': LXPPacket.R21_UNKNOWN_BIT_12},
        {'name': 'R21 Unknown Bit 3', 'key': 'r21_unknown_bit_3', 'bitmask': LXPPacket.R21_UNKNOWN_BIT_3},
        {'name': 'AC Charge Enable', 'key': 'ac_charge_enable', 'bitmask': LXPPacket.AC_CHARGE_ENABLE},
        {'name': 'Charge Priority', 'key': 'charge_priority', 'bitmask': LXPPacket.CHARGE_PRIORITY},
        {'name': 'Force Discharge Enable', 'key': 'force_discharge_enable','bitmask': LXPPacket.FORCED_DISCHARGE_ENABLE},
    ]

    binary_switchs = []
    for s in switch_configs:
        binary_switchs.append(LuxPowerRegisterValueSwitchEntity(hass, HOST, PORT, 21, s['bitmask'], s['name'], s['key'], device_class, luxpower_client, str.encode(str(DONGLE_SERIAL)), str.encode(str(SERIAL_NUMBER))))

    async_add_entities(binary_switchs, True)
    print("LuxPower switch async_setup_platform switch done")


class LuxPowerRegisterValueSwitchEntity(SwitchEntity):
    """Represent a binary sensor."""

    def __init__(self, hass, host, port, register_address, bitmask, name, key, device_class, luxpower_client,
                 dongle_serial, serial_number) -> None:
        super().__init__()
        self.hass = hass
        self._host = host
        self._port= port
        self._register_address = register_address
        self._bitmask = bitmask
        self._name = name
        self._key = key
        self._device_class = device_class
        self._state = False
        self.luxpower_client = luxpower_client
        self.dongle_serial = dongle_serial
        self.serial_number = serial_number
        # self.lxppacket = luxpower_client.lxpPacket
        self.registers = {}

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hass %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(EVENT_REGISTER_RECEIVED, self.push_update)
        return result

    def push_update(self, event):
        print("register event received")
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
        return "{}_{}".format(self.serial_number, self._key)

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
        print("turn on called ")
        self.set_register_bit(True)

    def turn_off(self, **kwargs: Any) -> None:
        print("turn off called ")
        self.set_register_bit(False)

    def ping_register(self):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((self._host, self._port))
            print("Connected to server")
            lxpPacket = LXPPacket(dongle_serial=self.dongle_serial, serial_number=self.serial_number)
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1)
            sock.send(packet)
            sock.close()
        except Exception as e:
            print("Exception ", e)

    def set_register_bit(self, bit_polarity=False):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((self._host, self._port))
            print("Connected to server")
            lxpPacket = LXPPacket(dongle_serial=self.dongle_serial, serial_number=self.serial_number)
            packet = lxpPacket.prepare_packet_for_read(self._register_address, 1)
            sock.send(packet)

            packet = sock.recv(1000)
            print('Received: ', packet)
            data = lxpPacket.parse_packet(packet)
            print(data)
            if not lxpPacket.packet_error:
                if lxpPacket.device_function == lxpPacket.READ_HOLD and lxpPacket.register == self._register_address:
                    if len(lxpPacket.value) == 2:
                        old_value = lxpPacket.convert_to_int(lxpPacket.value)
                        new_value = lxpPacket.update_value(old_value, self._bitmask, bit_polarity)
                        print("OLD: ", old_value, " MASK: ", self._bitmask, " NEW: ", new_value)
                        _LOGGER.debug("Writing: OLD: {} REGISTER: {} MASK: {} NEW {}".format(old_value, self._register_address, self._bitmask, new_value))
                        packet = lxpPacket.prepare_packet_for_write(self._register_address, new_value)
                        print("packet to be written ", packet)
                        sock.send(packet)
                        print("written packet")

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

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:
            lxpPacket = LXPPacket(dongle_serial=self.dongle_serial, serial_number=self.serial_number)
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
            lxpPacket = LXPPacket(dongle_serial=self.dongle_serial, serial_number=self.serial_number)
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
            lxpPacket = LXPPacket(dongle_serial=self.dongle_serial, serial_number=self.serial_number)
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
