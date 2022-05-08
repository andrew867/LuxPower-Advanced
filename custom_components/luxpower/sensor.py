import asyncio
import time
from datetime import timedelta, datetime
from typing import Optional, Union, Any, List, Dict
import logging

from homeassistant.config_entries import ConfigEntry
from homeassistant.const import CONF_MODE, DEVICE_CLASS_POWER, POWER_WATT, ENERGY_KILO_WATT_HOUR, PERCENTAGE, \
    DEVICE_CLASS_BATTERY, DEVICE_CLASS_ENERGY, DEVICE_CLASS_VOLTAGE, ELECTRIC_POTENTIAL_VOLT, DEVICE_CLASS_CURRENT, \
    ELECTRIC_CURRENT_AMPERE
from homeassistant.helpers.entity import Entity, DeviceInfo
from homeassistant.helpers.event import async_track_time_interval, async_track_point_in_time, track_time_interval
from .const import DOMAIN, ATTR_LUX_HOST, ATTR_LUX_PORT, ATTR_LUX_SERIAL_NUMBER, ATTR_LUX_DONGLE_SERIAL
from .LXPPacket import LXPPacket
from . import DATA_CONFIG, INVERTER_ID, DOMAIN, EVENT_DATA_RECEIVED, CLIENT_DAEMON

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(hass, config_entry: ConfigEntry, async_add_devices):
    """Set up the sensor platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux sensor platform")
    platform_config = hass.data[DATA_CONFIG]

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, 8000)
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, 8000)
    stateSensors = []

    luxpower_client = hass.data[CLIENT_DAEMON]

    device_class = CONF_MODE
    unit = ""
    name = "LUXPower" + INVERTER_ID
    stateSensors.append(LuxStateSensorEntity(hass, HOST, PORT, name, device_class, unit))

    sensors = []
    sensors.append({"name": "Lux - Battery Discharge (Live)", "entity": 'lux_batttery_discharge', 'attribute': LXPPacket.p_discharge, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Battery Charge (Live)", "entity": 'lux_battery_charge', 'attribute': LXPPacket.p_charge, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Battery %", "entity": 'lux_battery_percent', 'attribute': LXPPacket.soc, 'device_class': DEVICE_CLASS_BATTERY, 'unit_measure': PERCENTAGE})
    sensors.append({"name": "Lux - Battery Discharge (Daily)", "entity": 'lux_daily_batttery_discharge', 'attribute': LXPPacket.e_dischg_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Battery Discharge (Total)", "entity": 'lux_total_batttery_discharge', 'attribute': LXPPacket.e_dischg_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Battery Charge (Daily)", "entity": 'lux_daily_battery_charge', 'attribute': LXPPacket.e_chg_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Battery Charge (Total)", "entity": 'lux_total_battery_charge', 'attribute': LXPPacket.e_chg_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Battery Voltage (Live)", "entity": 'lux_battery_voltage', 'attribute': LXPPacket.v_bat, 'device_class': DEVICE_CLASS_VOLTAGE, 'unit_measure': ELECTRIC_POTENTIAL_VOLT})
    sensors.append({"name": "Lux - BMS Limit Charge (Live)", "entity": 'lux_bms_limit_charge', 'attribute': LXPPacket.max_chg_curr, 'device_class': DEVICE_CLASS_CURRENT, 'unit_measure': ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": "Lux - BMS Limit Discharge (Live)", "entity": 'lux_bms_limit_discharge', 'attribute': LXPPacket.max_dischg_curr, 'device_class': DEVICE_CLASS_CURRENT, 'unit_measure': ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": "Lux - Power from Inverter (Live)", "entity": 'lux_power_from_inverter_live', 'attribute': LXPPacket.p_inv, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power to Inverter (Live)", "entity": 'lux_power_to_inverter_live', 'attribute': LXPPacket.p_rec, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power from grid to HOUSE (Live)", "entity": 'lux_power_to_home', 'attribute': LXPPacket.p_load, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power from Grid (Live)", "entity": 'lux_power_from_grid_live', 'attribute': LXPPacket.p_to_user, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power from Grid (Daily)", "entity": 'lux_power_from_grid_daily', 'attribute': LXPPacket.e_to_user_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power from Grid (Total)", "entity": 'lux_power_from_grid_total', 'attribute': LXPPacket.e_to_user_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power To Grid (Live)", "entity": 'lux_power_to_grid_live', 'attribute': LXPPacket.p_to_grid, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Power To Grid (Daily)", "entity": 'lux_power_to_grid_daily', 'attribute': LXPPacket.e_to_grid_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power To Grid (Total)", "entity": 'lux_power_to_grid_total', 'attribute': LXPPacket.e_to_grid_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power from Inverter to Home (Daily)", "entity": 'lux_power_from_inverter_daily', 'attribute': LXPPacket.e_inv_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Power to Inverter (Daily)", "entity": 'lux_power_to_inverter_daily', 'attribute': LXPPacket.e_rec_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Solar Output (Live)", "entity": 'lux_current_solar_output', 'attribute': LXPPacket.p_pv_total, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Solar Output Array 1 (Live)", "entity": 'lux_current_solar_output_1', 'attribute': LXPPacket.p_pv_1, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Solar Output Array 2 (Live)", "entity": 'lux_current_solar_output_2', 'attribute': LXPPacket.p_pv_2, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": "Lux - Solar Output (Daily)", "entity": 'lux_daily_solar', 'attribute': LXPPacket.e_pv_total, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Solar Output (Total)", "entity": 'lux_total_solar', 'attribute': LXPPacket.e_pv_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": "Lux - Status", "entity": 'lux_status', 'attribute': LXPPacket.status, 'device_class': '', 'unit_measure': ''})
    for sensor_data in sensors:
        stateSensors.append(LuxpowerSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, sensor_data))

    async_add_devices(stateSensors, True)

    for address_bank in range(0, 3):
        await luxpower_client.get_register_data(address_bank)
        await asyncio.sleep(1)

    _LOGGER.debug("LuxPower sensor async_setup_platform sensor done %s", INVERTER_ID)
    print("LuxPower sensor async_setup_platform sensor done")


class LuxpowerSensorEntity(Entity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""
    def __init__(
        self, hass, host, port, dongle, serial, sensor_data
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
        self.lastupdated_time = 0

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(EVENT_DATA_RECEIVED, self.push_update)
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
            identifiers={(DOMAIN, self.serial)},
            manufacturer="LuxPower",
            model="LUXPower Inverter",
            name=self.serial,
            sw_version="1.1",
        )

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
    def state(self):
        """Return the state of the sensor."""
        return self._state

    @property
    def unit_of_measurement(self):
        """Return the unit this state is expressed in."""
        return self._unit_of_measurement


class LuxStateSensorEntity(Entity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""

    def __init__(
        self, hass, host, port, name, device_class, unit_measure
    ):
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = name
        self._state = "Waiting"
        self._stateval = None
        self._device_class = device_class
        self._unit_of_measurement = unit_measure
        self.is_added_to_hass = False
        self._data = {}
        self.lastupdated_time = 0

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes[LXPPacket.status] = "{}".format(self._data.get(LXPPacket.status, "unavailable"), "")
        state_attributes[LXPPacket.v_pv_1] = "{}".format(self._data.get(LXPPacket.v_pv_1, "unavailable"), "")
        state_attributes[LXPPacket.v_pv_2] = "{}".format( self._data.get(LXPPacket.v_pv_2, "unavailable"), "")
        state_attributes[LXPPacket.v_pv_3] = "{}".format( self._data.get(LXPPacket.v_pv_3, "unavailable"), "")
        state_attributes[LXPPacket.v_bat] = "{}".format( self._data.get(LXPPacket.v_bat, "unavailable"), "")
        state_attributes[LXPPacket.soc] = "{}".format( self._data.get(LXPPacket.soc, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_1] = "{}".format( self._data.get(LXPPacket.p_pv_1, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_2] = "{}".format( self._data.get(LXPPacket.p_pv_2, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_3] = "{}".format( self._data.get(LXPPacket.p_pv_3, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_total] = "{}".format( self._data.get(LXPPacket.p_pv_total, "unavailable"), "")
        state_attributes[LXPPacket.p_charge] = "{}".format( self._data.get(LXPPacket.p_charge, "unavailable"), "")
        state_attributes[LXPPacket.p_discharge] = "{}".format( self._data.get(LXPPacket.p_discharge, "unavailable"), "")
        state_attributes[LXPPacket.v_ac_r] = "{}".format( self._data.get(LXPPacket.v_ac_r, "unavailable"), "")
        state_attributes[LXPPacket.v_ac_s] = "{}".format(self._data.get(LXPPacket.v_ac_s, "unavailable"), "")
        state_attributes[LXPPacket.v_ac_t] = "{}".format( self._data.get(LXPPacket.v_ac_t, "unavailable"), "")
        state_attributes[LXPPacket.f_ac] = "{}".format( self._data.get(LXPPacket.f_ac, "unavailable"), "")
        state_attributes[LXPPacket.p_inv] = "{}".format( self._data.get(LXPPacket.p_inv, "unavailable"), "")
        state_attributes[LXPPacket.p_rec] = "{}".format( self._data.get(LXPPacket.p_rec, "unavailable"), "")
        state_attributes[LXPPacket.pf] = "{}".format( self._data.get(LXPPacket.pf, "unavailable"))
        state_attributes[LXPPacket.v_eps_r] = "{}".format( self._data.get(LXPPacket.v_eps_r, "unavailable"), "")
        state_attributes[LXPPacket.v_eps_s] = "{}".format( self._data.get(LXPPacket.v_eps_s, "unavailable"), "")
        state_attributes[LXPPacket.v_eps_t] = "{}".format( self._data.get(LXPPacket.v_eps_t, "unavailable"), "")
        state_attributes[LXPPacket.f_eps] = "{}".format( self._data.get(LXPPacket.f_eps, "unavailable"), "")
        state_attributes[LXPPacket.p_to_grid] = "{}".format( self._data.get(LXPPacket.p_to_grid, "unavailable"), "")
        state_attributes[LXPPacket.p_to_user] = "{}".format( self._data.get(LXPPacket.p_to_user, "unavailable"), "")
        state_attributes[LXPPacket.p_load] = "{}".format( self._data.get(LXPPacket.p_load, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_1_day] = "{}".format( self._data.get(LXPPacket.e_pv_1_day, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_2_day] = "{}".format( self._data.get(LXPPacket.e_pv_2_day, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_3_day] = "{}".format( self._data.get(LXPPacket.e_pv_3_day, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_total] = "{}".format( self._data.get(LXPPacket.e_pv_total, "unavailable"), "")
        state_attributes[LXPPacket.e_inv_day] = "{}".format( self._data.get(LXPPacket.e_inv_day, "unavailable"), "")
        state_attributes[LXPPacket.e_rec_day] = "{}".format( self._data.get(LXPPacket.e_rec_day, "unavailable"), "")
        state_attributes[LXPPacket.e_chg_day] =  "{}".format(self._data.get(LXPPacket.e_chg_day, "unavailable"), "")
        state_attributes[LXPPacket.e_dischg_day] = "{}".format( self._data.get(LXPPacket.e_dischg_day, "unavailable"), "")
        state_attributes[LXPPacket.e_eps_day] = "{}".format( self._data.get(LXPPacket.e_eps_day, "unavailable"), "")
        state_attributes[LXPPacket.e_to_grid_day] = "{}".format( self._data.get(LXPPacket.e_to_grid_day, "unavailable"), "")
        state_attributes[LXPPacket.e_to_user_day] = "{}".format( self._data.get(LXPPacket.e_to_user_day, "unavailable"), "")
        state_attributes[LXPPacket.v_bus_1] = "{}".format( self._data.get(LXPPacket.v_bus_1, "unavailable"), "")
        state_attributes[LXPPacket.v_bus_2] = "{}".format( self._data.get(LXPPacket.v_bus_2, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_1_all] = "{}".format( self._data.get(LXPPacket.e_pv_1_all, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_2_all] = "{}".format( self._data.get(LXPPacket.e_pv_2_all, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_3_all] = "{}".format( self._data.get(LXPPacket.e_pv_3_all, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_all] = "{}".format( self._data.get(LXPPacket.e_pv_all, "unavailable"), "")
        state_attributes[LXPPacket.e_inv_all] = "{}".format( self._data.get(LXPPacket.e_inv_all, "unavailable"), "")
        state_attributes[LXPPacket.e_rec_all] = "{}".format( self._data.get(LXPPacket.e_rec_all, "unavailable"), "")
        state_attributes[LXPPacket.e_chg_all] = "{}".format( self._data.get(LXPPacket.e_chg_all, "unavailable"), "")
        state_attributes[LXPPacket.e_dischg_all] = "{}".format( self._data.get(LXPPacket.e_dischg_all, "unavailable"), "")
        state_attributes[LXPPacket.e_eps_all] = "{}".format( self._data.get(LXPPacket.e_eps_all, "unavailable"), "")
        state_attributes[LXPPacket.e_to_grid_all] = "{}".format( self._data.get(LXPPacket.e_to_grid_all, "unavailable"), "")
        state_attributes[LXPPacket.e_to_user_all] = "{}".format( self._data.get(LXPPacket.e_to_user_all, "unavailable"), "")
        state_attributes[LXPPacket.t_inner] = "{}".format( self._data.get(LXPPacket.t_inner, "unavailable"), "")
        state_attributes[LXPPacket.t_rad_1] = "{}".format( self._data.get(LXPPacket.t_rad_1, "unavailable"), "")
        state_attributes[LXPPacket.t_rad_2] = "{}".format( self._data.get(LXPPacket.t_rad_2, "unavailable"), "")
        state_attributes[LXPPacket.t_bat] = "{}".format( self._data.get(LXPPacket.t_bat, "unavailable"), "")
        state_attributes[LXPPacket.uptime] = "{}".format( self._data.get(LXPPacket.uptime, "unavailable"), "")
        state_attributes[LXPPacket.max_chg_curr] = "{}".format( self._data.get(LXPPacket.max_chg_curr, "unavailable"), "")
        state_attributes[LXPPacket.max_dischg_curr] = "{}".format( self._data.get(LXPPacket.max_dischg_curr, "unavailable"), "")
        state_attributes[LXPPacket.charge_volt_ref] = "{}".format( self._data.get(LXPPacket.charge_volt_ref, "unavailable"), "")
        state_attributes[LXPPacket.dischg_cut_volt] = "{}".format( self._data.get(LXPPacket.dischg_cut_volt, "unavailable"), "")
        state_attributes[LXPPacket.bat_count] = "{}".format( self._data.get(LXPPacket.bat_count, "unavailable"), "")
        return state_attributes

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(EVENT_DATA_RECEIVED, self.push_update)
        return result

    def checkonline(self, *args, **kwargs):
        print("check online")
        if time.time() - self.lastupdated_time > 10:
            self._state = "OFFLINE"
        self.schedule_update_ha_state()

    def push_update(self, event):
        print("register event received")
        self._data = event.data.get('data', {})
        self._state = "ONLINE"

        self.schedule_update_ha_state()
        return self._state

    def update(self):
        if not self.is_added_to_hass:
            return
        # _LOGGER.info("{} updating state to {}".format(self._dp_id, self._stateval))
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return "{}_{}".format(DOMAIN, "states")

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
    def state(self):
        """Return the state of the sensor."""
        return self._state

    @property
    def unit_of_measurement(self):
        """Return the unit this state is expressed in."""
        return self._unit_of_measurement

