import asyncio
import time
from datetime import timedelta, datetime
from typing import Optional, Union, Any, List, Dict
import logging
from homeassistant.const import CONF_MODE
from homeassistant.helpers.entity import Entity
from homeassistant.helpers.event import async_track_time_interval, async_track_point_in_time, track_time_interval

from .LXPPacket import LXPPacket
from . import DATA_CONFIG, EVENT_REGISTER_RECEIVED, INVERTER_ID, DOMAIN, EVENT_DATA_RECEIVED, CLIENT_DAEMON

_LOGGER = logging.getLogger(__name__)


async def async_setup_platform(hass, config, async_add_entities, discovery_info=None):
    print("In LuxPower sensor platform discovery")
    platform_config = hass.data[DATA_CONFIG]

    HOST = platform_config.get("host", "127.0.0.1")
    PORT = platform_config.get("port", 8000)
    SERIAL_NUMBER = platform_config.get("serial_number", "0102005050")

    luxpower_client = hass.data[CLIENT_DAEMON]
    sensor_configs = [
        {'key': LXPPacket.status, 'name': "Status", 'device_class': None, 'unit': None},
        {'key': LXPPacket.v_pv_1, 'name': "Solar Voltage Array 1 (Live)", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_pv_2, 'name': "Solar Voltage Array 2 (Live)", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_pv_3, 'name': "Solar Voltage Array 3 (Live)", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_bat, 'name': "Battery Current (Live)", 'device_class': "current", 'unit': "A"},
        {'key': LXPPacket.soc, 'name': "Battery %", 'device_class': "battery", 'unit': "%"},
        {'key': LXPPacket.p_pv_1, 'name': "Solar Output Array 1 (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_pv_2, 'name': "Solar Output Array 2 (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_pv_3, 'name': "Solar Output Array 3 (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_pv_total, 'name': "Solar Output (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_charge, 'name': "Battery Charge (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_discharge, 'name': "Battery Discharge (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.v_ac_r, 'name': LXPPacket.v_ac_r, 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_ac_s, 'name': LXPPacket.v_ac_s, 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_ac_t, 'name': LXPPacket.v_ac_t, 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.f_ac, 'name': "AC frequency", 'device_class': "frequency", 'unit': "Hz"},
        {'key': LXPPacket.p_inv, 'name': "Power from Inverter (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_rec, 'name': "Power to Inverter (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.pf, 'name': "Power factor", 'device_class': "power_factor", 'unit': "%"},
        {'key': LXPPacket.v_eps_r, 'name': "v_eps_r", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_eps_s, 'name': "v_eps_s", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_eps_t, 'name': "v_eps_t", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.f_eps, 'name': "EPS frequency", 'device_class': "frequency", 'unit': "Hz"},
        {'key': LXPPacket.p_to_grid, 'name': "Power To Grid (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_to_user, 'name': "Power from Grid (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.p_load, 'name': "Power from grid to house (Live)", 'device_class': "power", 'unit': "W"},
        {'key': LXPPacket.e_pv_1_day, 'name': "Solar Output Array 1 (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_pv_2_day, 'name': "Solar Output Array 2 (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_pv_3_day, 'name': "Solar Output Array 3 (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_pv_total, 'name': "Solar Output (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_inv_day, 'name': "Power from Inverter to Home (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_rec_day, 'name': "Power to Inverter (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_chg_day, 'name': "Battery Charge (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_dischg_day, 'name': "Battery Discharge (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_eps_day, 'name': "EPS power (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_to_grid_day, 'name': "Power To Grid (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_to_user_day, 'name': "Power from Grid (Daily)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.v_bus_1, 'name': "Bus 1 voltage", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.v_bus_2, 'name': "Bus 2 voltage", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.e_pv_1_all, 'name': "Solar Output Array 1 (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_pv_2_all, 'name': "Solar Output Array 1 (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_pv_3_all, 'name': "Solar Output Array 1 (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_pv_all, 'name': "Solar Output (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_inv_all, 'name': "Power from Invester (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_rec_all, 'name': "Power to Inverter (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_chg_all, 'name': "Battery Charge (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_dischg_all, 'name': "Battery Discharge (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_eps_all, 'name': "EPS power (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_to_grid_all, 'name': "Power To Grid (Total)", 'device_class': "energy", 'unit': "kWh"},
        {'key': LXPPacket.e_to_user_all, 'name': "Power from Grid (Total)", 'device_class': "energy", 'unit': "kwH"},
        {'key': LXPPacket.t_inner, 'name': "Inner Temperature", 'device_class': "temperature", 'unit': "°C"},
        {'key': LXPPacket.t_rad_1, 'name': "Temperature 1", 'device_class': "temperature", 'unit': "°C"},
        {'key': LXPPacket.t_rad_2, 'name': "Temperature 2", 'device_class': "temperature", 'unit': "°C"},
        {'key': LXPPacket.t_bat, 'name': "Battery Temperature", 'device_class': "temperature", 'unit': "°C"},
        {'key': LXPPacket.uptime, 'name': "Uptime (Seconds)", 'device_class': None, 'unit': None},
        {'key': LXPPacket.max_chg_curr, 'name': "BMS Limit Charge (Live)", 'device_class': "current", 'unit': "A"},
        {'key': LXPPacket.max_dischg_curr, 'name': "BMS Limit Discharge (Live)", 'device_class': "current", 'unit': "A"},
        {'key': LXPPacket.charge_volt_ref, 'name': "Charge voltage limit", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.dischg_cut_volt, 'name': "Discharge cutoff voltage", 'device_class': "voltage", 'unit': "V"},
        {'key': LXPPacket.bat_count, 'name': "Battery count", 'device_class': None, 'unit': None},
    ]

    state_sensors = []
    for s in sensor_configs:
        state_sensors.append(LuxPowerStateSensorEntity(hass, HOST, PORT, str.encode(str(SERIAL_NUMBER)), s['key'], s['name'], s['device_class'], s['unit']))

    async_add_entities(state_sensors, True)

    for address_bank in range(0, 3):
        await luxpower_client.get_register_data(address_bank)
        await asyncio.sleep(1)

    _LOGGER.debug("LuxPower sensor async_setup_platform sensor done %s", INVERTER_ID)
    print("LuxPower sensor async_setup_platform sensor done")


class LuxPowerStateSensorEntity(Entity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""

    def __init__(
        self, hass, host, port, serial_number, key, name, device_class, unit_measure
    ):
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._key = key
        self._name = name
        self.serial_number = serial_number
        self._stateval = None
        self._device_class = device_class
        self._unit_of_measurement = unit_measure
        self.is_added_to_hass = False
        self._data = {}
        self.lastupdated_time = 0

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._key)
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
    def unit_of_measurement(self):
        """Return the unit this state is expressed in."""
        return self._unit_of_measurement

