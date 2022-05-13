import asyncio
import time
from datetime import timedelta, datetime, date
from typing import Optional, Union, Any, List, Dict
import logging

from homeassistant.components.sensor import SensorEntity, SensorStateClass
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import CONF_MODE, DEVICE_CLASS_POWER, POWER_WATT, ENERGY_KILO_WATT_HOUR, PERCENTAGE, \
    DEVICE_CLASS_BATTERY, DEVICE_CLASS_ENERGY, DEVICE_CLASS_VOLTAGE, ELECTRIC_POTENTIAL_VOLT, DEVICE_CLASS_CURRENT, \
    ELECTRIC_CURRENT_AMPERE
from homeassistant.helpers.entity import Entity, DeviceInfo
from homeassistant.helpers.event import async_track_time_interval, async_track_point_in_time, track_time_interval
from homeassistant.helpers.typing import StateType

from .const import DOMAIN, ATTR_LUX_HOST, ATTR_LUX_PORT, ATTR_LUX_SERIAL_NUMBER, ATTR_LUX_DONGLE_SERIAL
from .LXPPacket import LXPPacket
from .helpers import Event

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
    stateSensors = []

    event = Event(dongle=DONGLE)

    luxpower_client = hass.data[event.CLIENT_DAEMON]

    device_class = CONF_MODE
    unit = ""
    name = "LUXPower"
    stateSensors.append(LuxStateSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, name, device_class, unit, event))

    sensors = []
    sensors.append({"name": "Lux - Battery Discharge (Live)", "entity": 'lux_battery_discharge', 'attribute': LXPPacket.p_discharge, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Battery Charge (Live)", "entity": 'lux_battery_charge', 'attribute': LXPPacket.p_charge, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Battery %", "entity": 'lux_battery_percent', 'attribute': LXPPacket.soc, 'device_class': DEVICE_CLASS_BATTERY, 'unit_measure': PERCENTAGE})
    sensors.append({"name": "Lux - Battery Discharge (Daily)", "entity": 'lux_daily_battery_discharge', 'attribute': LXPPacket.e_dischg_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Battery Discharge (Total)", "entity": 'lux_total_battery_discharge', 'attribute': LXPPacket.e_dischg_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": "Lux - Battery Charge (Daily)", "entity": 'lux_daily_battery_charge', 'attribute': LXPPacket.e_chg_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Battery Charge (Total)", "entity": 'lux_total_battery_charge', 'attribute': LXPPacket.e_chg_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": "Lux - Battery Voltage (Live)", "entity": 'lux_battery_voltage', 'attribute': LXPPacket.v_bat, 'device_class': DEVICE_CLASS_VOLTAGE, 'unit_measure': ELECTRIC_POTENTIAL_VOLT})
    sensors.append({"name": "Lux - BMS Limit Charge (Live)", "entity": 'lux_bms_limit_charge', 'attribute': LXPPacket.max_chg_curr, 'device_class': DEVICE_CLASS_CURRENT, 'unit_measure': ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": "Lux - BMS Limit Discharge (Live)", "entity": 'lux_bms_limit_discharge', 'attribute': LXPPacket.max_dischg_curr, 'device_class': DEVICE_CLASS_CURRENT, 'unit_measure': ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": "Lux - Power from Inverter (Live)", "entity": 'lux_power_from_inverter_live', 'attribute': LXPPacket.p_inv, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power to Inverter (Live)", "entity": 'lux_power_to_inverter_live', 'attribute': LXPPacket.p_rec, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power from grid to HOUSE (Live)", "entity": 'lux_power_to_home', 'attribute': LXPPacket.p_load, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power from Grid (Live)", "entity": 'lux_power_from_grid_live', 'attribute': LXPPacket.p_to_user, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power from Grid (Daily)", "entity": 'lux_power_from_grid_daily', 'attribute': LXPPacket.e_to_user_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power from Grid (Total)", "entity": 'lux_power_from_grid_total', 'attribute': LXPPacket.e_to_user_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": "Lux - Power To Grid (Live)", "entity": 'lux_power_to_grid_live', 'attribute': LXPPacket.p_to_grid, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power To Grid (Daily)", "entity": 'lux_power_to_grid_daily', 'attribute': LXPPacket.e_to_grid_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power To Grid (Total)", "entity": 'lux_power_to_grid_total', 'attribute': LXPPacket.e_to_grid_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": "Lux - Power from Inverter to Home (Daily)", "entity": 'lux_power_from_inverter_daily', 'attribute': LXPPacket.e_inv_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power to Inverter (Daily)", "entity": 'lux_power_to_inverter_daily', 'attribute': LXPPacket.e_rec_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Solar Output (Live)", "entity": 'lux_current_solar_output', 'attribute': LXPPacket.p_pv_total, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Solar Output Array 1 (Live)", "entity": 'lux_current_solar_output_1', 'attribute': LXPPacket.p_pv_1, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Solar Output Array 2 (Live)", "entity": 'lux_current_solar_output_2', 'attribute': LXPPacket.p_pv_2, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Solar Output (Daily)", "entity": 'lux_daily_solar', 'attribute': LXPPacket.e_pv_total, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Solar Output (Total)", "entity": 'lux_total_solar', 'attribute': LXPPacket.e_pv_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": "Lux - Status", "entity": 'lux_status', 'attribute': LXPPacket.status, 'device_class': '', 'unit_measure': ''})
    for sensor_data in sensors:
        stateSensors.append(LuxpowerSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    async_add_devices(stateSensors, True)

    for address_bank in range(0, 3):
        await luxpower_client.get_register_data(address_bank)
        await asyncio.sleep(1)

    _LOGGER.debug("LuxPower sensor async_setup_platform sensor done %s", DONGLE)
    print("LuxPower sensor async_setup_platform sensor done")


class LuxpowerSensorEntity(SensorEntity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""
    def __init__(
        self, hass, host, port, dongle, serial, sensor_data, event: Event
    ):
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = sensor_data['name']
        self._state = "Unavailable"
        self._stateval = None
        self.serial = serial
        self.dongle = dongle
        self._device_class = sensor_data['device_class']
        self._unit_of_measurement = sensor_data['unit_measure']
        self.is_added_to_hass = False
        self._data = {}
        self._unique_id = sensor_data['entity']
        self._device_attribute = sensor_data['attribute']
        self._state_class = sensor_data.get('state_class', None)
        self.lastupdated_time = 0
        self.event = event

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_DATA_RECEIVED, self.push_update)
        return result

    def checkonline(self, *args, **kwargs):
        if time.time() - self.lastupdated_time > 10:
            self._state = "{}".format(self._data.get(self._device_attribute, "unavailable"), "")
        self.schedule_update_ha_state()

    def push_update(self, event):
        print("register event received")
        self._data = event.data.get('data', {})
        self._state = "{}".format(self._data.get(self._device_attribute, "unavailable"), "")

        self.schedule_update_ha_state()
        return self._state

    def update(self):
        if not self.is_added_to_hass:
            return
        # _LOGGER.info("{} updating state to {}".format(self._dp_id, self._stateval))
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
    def state_class(self) -> Union[SensorStateClass, str, None]:
        return self._state_class

    @property
    def unique_id(self):
        """Return the unique id."""
        return self._unique_id

    @property
    def should_poll(self):
        """No polling needed for a demo sensor."""
        return False

    @property
    def device_class(self):
        """Return the device class of the sensor."""
        return self._device_class


    @property
    def name(self):
        """Return the name of the sensor."""
        return self._name

    @property
    def native_value(self) -> Union[StateType, date, datetime]:
        return self._state

    @property
    def native_unit_of_measurement(self) -> Optional[str]:
        return self._unit_of_measurement


class LuxStateSensorEntity(Entity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""

    def __init__(
        self, hass, host, port, dongle, serial, name, device_class, unit_measure, event:Event
    ):
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._key = key
        self._name = name
        self.dongle = dongle
        self.serial = serial
        self._state = "Waiting"
        self._stateval = None
        self._device_class = device_class
        self._unit_of_measurement = unit_measure
        self.is_added_to_hass = False
        self._data = {}
        self.lastupdated_time = 0
        self.event = event

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._key)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_DATA_RECEIVED, self.push_update)
        return result

    def checkonline(self, *args, **kwargs):
        print("check online")
        if time.time() - self.lastupdated_time > 10:
            self._state = "OFFLINE"
        self.schedule_update_ha_state()

    def push_update(self, event):
        print("register event received")
        self._data = event.data.get('data', {})
        self._state = self._data.get(self._key, "unavailable")

        self.schedule_update_ha_state()
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return "{}_{}".format(self.serial_number, self._key)

    @property
    def should_poll(self):
        """No polling needed for a demo sensor."""
        return False

    @property
    def device_class(self):
        """Return the device class of the sensor."""
        return self._device_class

    @property
    def name(self):
        """Return the name of the sensor."""
        return self._name

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
    def state(self):
        """Return the state of the sensor."""
        return self._state

    @property
    def unit_of_measurement(self):
        """Return the unit this state is expressed in."""
        return self._unit_of_measurement

