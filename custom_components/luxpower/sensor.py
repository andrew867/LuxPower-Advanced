"""

This is a docstring placeholder.

This is where we will describe what this module does
"""
import logging
import time
from datetime import datetime
from typing import Any, Dict, List, Optional

from homeassistant.components.sensor import (
    SensorDeviceClass,
    SensorEntity,
    SensorStateClass,
)
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import (
    CONF_MODE,
    PERCENTAGE,
    UnitOfElectricCurrent,
    UnitOfElectricPotential,
    UnitOfEnergy,
    UnitOfFrequency,
    UnitOfPower,
    UnitOfTemperature,
)
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo, Entity
from homeassistant.util import slugify

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    UA,
    VERSION,
)
from .helpers import Event
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices):
    """Set up the sensor platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux sensor platform")
    _LOGGER.info("Options", len(config_entry.options))

    global hyphen
    global nameID_midfix
    global entityID_midfix

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    # Options For Name Midfix Based Upon Serial Number - Suggest Last Two Digits
    # nameID_midfix = SERIAL if USE_SERIAL else ""
    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""

    # Options For Entity Midfix Based Upon Serial Number - Suggest Full Serial Number
    entityID_midfix = SERIAL if USE_SERIAL else ""

    # Options For Hyphen Use Before Entity Description - Suggest No Hyphen As Of 15/02/23
    # hyphen = " -" if USE_SERIAL else "-"
    hyphen = ""

    _LOGGER.info(f"Lux sensor platform_config: {platform_config}")

    event = Event(dongle=DONGLE)

    luxpower_client = hass.data[event.CLIENT_DAEMON]

    # fmt: off

    sensorEntities: List[Entity] = []

    sensors = [

        # 1. Create Overall Master State Sensor
        {"etype": "LPSS", "name": "LUXPower {replaceID_midfix}", "unique": "states", "device_class": CONF_MODE, "luxpower_client": luxpower_client},

        # 2. Create HOLDING Register Based Sensors 1st - As they Are Only Populated By Default At Integration Load - Slow RPi Timing
        {"etype": "LPFW", "name": "Lux {replaceID_midfix}{hyphen} Firmware Version", "unique": "lux_firmware_version", "bank": 0, "register": 7},

        # 3. Create Attribute Sensors Based On LuxPowerSensorEntity Class
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Live)", "unique": "lux_battery_discharge", "bank": 0, "attribute": LXPPacket.p_discharge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Live)", "unique": "lux_battery_charge", "bank": 0, "attribute": LXPPacket.p_charge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery %", "unique": "lux_battery_percent", "bank": 0, "attribute": LXPPacket.soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Daily)", "unique": "lux_daily_battery_discharge", "bank": 0, "attribute": LXPPacket.e_dischg_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Total)", "unique": "lux_total_battery_discharge", "bank": 1, "attribute": LXPPacket.e_dischg_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Daily)", "unique": "lux_daily_battery_charge", "bank": 0, "attribute": LXPPacket.e_chg_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Total)", "unique": "lux_total_battery_charge", "bank": 1, "attribute": LXPPacket.e_chg_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Voltage (Live)", "unique": "lux_battery_voltage", "bank": 0, "attribute": LXPPacket.v_bat, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} BMS Limit Charge (Live)", "unique": "lux_bms_limit_charge", "bank": 2, "attribute": LXPPacket.max_chg_curr, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} BMS Limit Discharge (Live)", "unique": "lux_bms_limit_discharge", "bank": 2, "attribute": LXPPacket.max_dischg_curr, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Inverter (Live)", "unique": "lux_power_from_inverter_live", "bank": 0, "attribute": LXPPacket.p_inv, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Inverter (Live)", "unique": "lux_power_to_inverter_live", "bank": 0, "attribute": LXPPacket.p_rec, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Grid to HOUSE (Live)", "unique": "lux_power_to_home", "bank": 0, "attribute": LXPPacket.p_load, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},

        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} CT Clamp (Live)", "unique": "lux_power_current_clamp", "bank": 0, "attribute": LXPPacket.rms_current, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},

        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To EPS (Live)", "unique": "lux_power_to_eps", "bank": 0, "attribute": LXPPacket.p_to_eps, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To EPS (Daily)", "unique": "lux_power_to_eps_daily", "bank": 0, "attribute": LXPPacket.e_eps_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To EPS (Total)", "unique": "lux_power_to_eps_total", "bank": 1, "attribute": LXPPacket.e_eps_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Grid (Live)", "unique": "lux_power_from_grid_live", "bank": 0, "attribute": LXPPacket.p_to_user, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Grid (Daily)", "unique": "lux_power_from_grid_daily", "bank": 0, "attribute": LXPPacket.e_to_user_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Grid (Total)", "unique": "lux_power_from_grid_total", "bank": 1, "attribute": LXPPacket.e_to_user_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Grid (Live)", "unique": "lux_power_to_grid_live", "bank": 0, "attribute": LXPPacket.p_to_grid, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Grid (Daily)", "unique": "lux_power_to_grid_daily", "bank": 0, "attribute": LXPPacket.e_to_grid_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Grid (Total)", "unique": "lux_power_to_grid_total", "bank": 1, "attribute": LXPPacket.e_to_grid_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Frequency (Live) ", "unique": "lux_grid_frequency_live", "bank": 0, "attribute": LXPPacket.f_ac, "device_class": SensorDeviceClass.FREQUENCY, "unit_of_measurement": UnitOfFrequency.HERTZ},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Voltage (Live) ", "unique": "lux_grid_voltage_live", "bank": 0, "attribute": LXPPacket.v_ac_r, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power from Inverter to Home (Daily)", "unique": "lux_power_from_inverter_daily", "bank": 0, "attribute": LXPPacket.e_inv_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power from Inverter to Home (Total)", "unique": "lux_power_from_inverter_total", "bank": 1, "attribute": LXPPacket.e_inv_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power to Inverter (Daily)", "unique": "lux_power_to_inverter_daily", "bank": 0, "attribute": LXPPacket.e_rec_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power to Inverter (Total)", "unique": "lux_power_to_inverter_total", "bank": 1, "attribute": LXPPacket.e_rec_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output (Live)", "unique": "lux_current_solar_output", "bank": 0, "attribute": LXPPacket.p_pv_total, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 1 (Live)", "unique": "lux_current_solar_output_1", "bank": 0, "attribute": LXPPacket.p_pv_1, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 2 (Live)", "unique": "lux_current_solar_output_2", "bank": 0, "attribute": LXPPacket.p_pv_2, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 3 (Live)", "unique": "lux_current_solar_output_3", "bank": 0, "attribute": LXPPacket.p_pv_3, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 1 (Live)", "unique": "lux_current_solar_voltage_1", "bank": 0, "attribute": LXPPacket.v_pv_1, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 2 (Live)", "unique": "lux_current_solar_voltage_2", "bank": 0, "attribute": LXPPacket.v_pv_2, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 3 (Live)", "unique": "lux_current_solar_voltage_3", "bank": 0, "attribute": LXPPacket.v_pv_3, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Voltage (Live)", "unique": "lux_current_generator_voltage", "bank": 2, "attribute": LXPPacket.gen_input_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Frequency (Live)", "unique": "lux_current_generator_frequency", "bank": 2, "attribute": LXPPacket.gen_input_freq, "device_class": SensorDeviceClass.FREQUENCY, "unit_of_measurement": UnitOfFrequency.HERTZ},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Live)", "unique": "lux_current_generator_power", "bank": 2, "attribute": LXPPacket.gen_power_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Daily)", "unique": "lux_current_generator_power_daily", "bank": 2, "attribute": LXPPacket.gen_power_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Total)", "unique": "lux_current_generator_power_all", "bank": 2, "attribute": LXPPacket.gen_power_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Voltage (Live)", "unique": "lux_current_eps_L1_voltage", "bank": 2, "attribute": LXPPacket.eps_L1_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Voltage (Live)", "unique": "lux_current_eps_L2_voltage", "bank": 2, "attribute": LXPPacket.eps_L2_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output (Daily)", "unique": "lux_daily_solar", "bank": 0, "attribute": LXPPacket.e_pv_total, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 1 (Daily)", "unique": "lux_daily_solar_array_1", "bank": 0, "attribute": LXPPacket.e_pv_1_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 2 (Daily)", "unique": "lux_daily_solar_array_2", "bank": 0, "attribute": LXPPacket.e_pv_2_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 3 (Daily)", "unique": "lux_daily_solar_array_3", "bank": 0, "attribute": LXPPacket.e_pv_3_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output (Total)", "unique": "lux_total_solar", "bank": 1, "attribute": LXPPacket.e_pv_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 1 (Total)", "unique": "lux_total_solar_array_1", "bank": 1, "attribute": LXPPacket.e_pv_1_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 2 (Total)", "unique": "lux_total_solar_array_2", "bank": 1, "attribute": LXPPacket.e_pv_2_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 3 (Total)", "unique": "lux_total_solar_array_3", "bank": 1, "attribute": LXPPacket.e_pv_3_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Internal Temperature (Live)", "unique": "lux_internal_temp", "bank": 1, "attribute": LXPPacket.t_inner, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Radiator 1 Temperature (Live)", "unique": "lux_radiator1_temp", "bank": 1, "attribute": LXPPacket.t_rad_1, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Radiator 2 temperature (Live)", "unique": "lux_radiator2_temp", "bank": 1, "attribute": LXPPacket.t_rad_2, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Max Cell Voltage (Live)", "unique": "max_cell_volt", "bank": 2, "attribute": LXPPacket.max_cell_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "decimal_places": 3},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Min Cell Voltage (Live)", "unique": "min_cell_volt", "bank": 2, "attribute": LXPPacket.min_cell_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "decimal_places": 3},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Temperature (Live)", "unique": "t_bat", "bank": 2, "attribute": LXPPacket.t_bat, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Max Cell Temperature (Live)", "unique": "max_cell_temp", "bank": 2, "attribute": LXPPacket.max_cell_temp, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Min Cell Temperature (Live)", "unique": "min_cell_temp", "bank": 2, "attribute": LXPPacket.min_cell_temp, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Count", "unique": "lux_battery_count", "bank": 2, "attribute": LXPPacket.bat_count, "device_class": None, "unit_of_measurement": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Capacity Ah", "unique": "lux_battery_capacity_ah", "bank": 2, "attribute": LXPPacket.bat_capacity, "device_class": None, "unit_of_measurement": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Current", "unique": "lux_battery_current", "bank": 2, "attribute": LXPPacket.bat_current, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Cycle Count", "unique": "lux_battery_cycle_count", "bank": 2, "attribute": LXPPacket.bat_cycle_count, "device_class": None, "unit_of_measurement": None},


        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Status", "unique": "lux_status", "bank": 0, "attribute": LXPPacket.status},

        # 4. Setup Data Received Timestamp sensor
        {"etype": "LPDR", "name": "Lux {replaceID_midfix}{hyphen} Data Received Time", "unique": "lux_data_last_received_time", "bank": 0, "attribute": LXPPacket.status},

        # 5. Setup State Text sensor
        {"etype": "LPST", "name": "Lux {replaceID_midfix}{hyphen} Status (Text)", "unique": "lux_status_text", "bank": 0, "attribute": LXPPacket.status},

        # Multiple Attribute Calculated Sensors
        # 6. Battery Flow Live
        {"etype": "LPFS", "name": "Lux {replaceID_midfix}{hyphen} Battery Flow (Live)", "unique": "lux_battery_flow", "bank": 0, "attribute": LXPPacket.p_discharge, "attribute1": LXPPacket.p_discharge, "attribute2": LXPPacket.p_charge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT},

        # 7. Grid Flow Live
        {"etype": "LPFS", "name": "Lux {replaceID_midfix}{hyphen} Grid Flow (Live)", "unique": "lux_grid_flow", "bank": 0, "attribute": LXPPacket.p_to_user, "attribute1": LXPPacket.p_to_user, "attribute2": LXPPacket.p_to_grid, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT},

        # 8. Home Consumption Live
        {
            "etype": "LPHC", "name": "Lux {replaceID_midfix}{hyphen} Home Consumption (Live)", "unique": "lux_home_consumption_live", "bank": 0,
            "attribute": LXPPacket.p_to_user, "attribute1": LXPPacket.p_to_user, "attribute2": LXPPacket.p_rec, "attribute3": LXPPacket.p_inv, "attribute4": LXPPacket.p_to_grid,
            "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT
        },
        {
            "etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Home Consumption 2 (Live)", "unique": "lux_home_consumption_2_live", "bank": 2,
            "attribute": LXPPacket.p_load2,
            "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT
        },

        # 9. Home Consumption Daily
        {
            "etype": "LPHC", "name": "Lux {replaceID_midfix}{hyphen} Home Consumption (Daily)", "unique": "lux_home_consumption", "bank": 0,
            "attribute": LXPPacket.e_to_user_day, "attribute1": LXPPacket.e_to_user_day, "attribute2": LXPPacket.e_rec_day, "attribute3": LXPPacket.e_inv_day, "attribute4": LXPPacket.e_to_grid_day,
            "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR,
        },
        # att1. Power from grid to consumer, att2. Power from consumer to invert, att3. power from inv to consumer, att4. power from consumer to grid.

        # 10. Home Consumption Total
        {
            "etype": "LPHC", "name": "Lux {replaceID_midfix}{hyphen} Home Consumption (Total)", "unique": "lux_home_consumption_total", "bank": 1,
            "attribute": LXPPacket.e_to_user_all, "attribute1": LXPPacket.e_to_user_all, "attribute2": LXPPacket.e_rec_all, "attribute3": LXPPacket.e_inv_all, "attribute4": LXPPacket.e_to_grid_all,
            "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING,
        },
        # att1. Power from grid to consumer, att2. Power from consumer to invert, att3. power from inv to consumer, att4. power from consumer to grid.

        # 11. Test Sensor
        # {"etype": "LPTS", "name": "Lux {replaceID_midfix}{hyphen} Testing", "unique": "lux_testing", "bank": 0, "register": 5},

    ]

    for entity_definition in sensors:
        etype = entity_definition["etype"]
        if etype == "LPSS":
            sensorEntities.append(LuxStateSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPSE":
            sensorEntities.append(LuxPowerSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPRS":
            sensorEntities.append(LuxPowerRegisterSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPFW":
            sensorEntities.append(LuxPowerFirmwareSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPDR":
            sensorEntities.append(LuxPowerDataReceivedTimestampSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPST":
            sensorEntities.append(LuxPowerStatusTextSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPFS":
            sensorEntities.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPHC":
            sensorEntities.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPTS":
            sensorEntities.append(LuxPowerTestSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))

    # fmt: on

    async_add_devices(sensorEntities, True)

    _LOGGER.info("LuxPower sensor async_setup_platform sensor done %s", DONGLE)


class LuxPowerSensorEntity(SensorEntity):
    """Representation of a general numeric LUXpower sensor."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the sensor."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"sensor.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event
        self.is_added_to_hass = False
        self.lastupdated_time = 0

        # Hidden Inherited Instance Attributes
        self._attr_unique_id = "{}_{}_{}".format(DOMAIN, dongle, entity_definition["unique"])
        self._attr_name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        self._attr_native_value = "Unavailable"
        self._attr_available = False
        self._attr_device_class = entity_definition.get("device_class", None)
        self._attr_state_class = entity_definition.get("state_class", None)
        self._attr_native_unit_of_measurement = entity_definition.get("unit_of_measurement", None)
        self._attr_should_poll = False

        # Hidden Class Extended Instance Attributes
        self._host = host
        self._port = port
        self._data: Dict[str, str] = {}
        self._bank = entity_definition.get("bank", 0)
        self._device_attribute = entity_definition.get("attribute", None)
        self._decimal_places = entity_definition.get("decimal_places", 1)

        # _LOGGER.debug("Slugified entity_id: %s", self.entity_id)

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable)
            if self._bank == 0:
                self.hass.bus.async_listen(self.event.EVENT_DATA_BANK0_RECEIVED, self.push_update)
            elif self._bank == 1:
                self.hass.bus.async_listen(self.event.EVENT_DATA_BANK1_RECEIVED, self.push_update)
            elif self._bank == 2:
                self.hass.bus.async_listen(self.event.EVENT_DATA_BANK2_RECEIVED, self.push_update)
            else:
                self.hass.bus.async_listen(self.event.EVENT_DATA_RECEIVED, self.push_update)

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})
        value = self._data.get(self._device_attribute)
        if isinstance(value, (int, float)):
            value = round(value, self._decimal_places)
            self._attr_available = True
        else:
            value = UA
            self._attr_available = False
        self._attr_native_value = f"{value}"
        self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        _LOGGER.warning(f"Sensor: gone_unavailable event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
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


class LuxPowerFlowSensor(LuxPowerSensorEntity):
    """
    Representation of a Numeric LUXpower Flow sensor.

    Template equation state = -1*attribute1 if attribute1 > 0 else attribute2
    """

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._device_attribute1 = entity_definition["attribute1"]
        self._device_attribute2 = entity_definition["attribute2"]

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})

        negative_value = float(self._data.get(self._device_attribute1, 0.0))
        positive_value = float(self._data.get(self._device_attribute2, 0.0))
        if negative_value > 0:
            flow_value = -1 * negative_value
        else:
            flow_value = positive_value
        self._attr_native_value = f"{round(flow_value, 1)}"

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerHomeConsumptionSensor(LuxPowerSensorEntity):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._device_attribute1 = entity_definition["attribute1"]  # Power from grid to consumer unit
        self._device_attribute2 = entity_definition["attribute2"]  # Power from consumer unit to inverter
        self._device_attribute3 = entity_definition["attribute3"]  # Power from inverter to consumer unit
        self._device_attribute4 = entity_definition["attribute4"]  # Power from consumer unit to grid

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})

        grid = float(self._data.get(self._device_attribute1, 0.0))
        to_inverter = float(self._data.get(self._device_attribute2, 0.0))
        from_inverter = float(self._data.get(self._device_attribute3, 0.0))
        to_grid = float(self._data.get(self._device_attribute4, 0.0))
        consumption_value = grid - to_inverter + from_inverter - to_grid
        self._attr_native_value = f"{round(consumption_value, 1)}"

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerRegisterSensor(LuxPowerSensorEntity):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._register_address = entity_definition["register"]

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._attr_name)
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
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Register: {self._register_address} Name: {self._attr_name}")  # fmt: skip
        registers = event.data.get("registers", {})
        self._data = registers

        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self._register_address} is in register.keys")
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            oldstate = self._attr_native_value
            self._attr_native_value = float(register_val)
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(f"Register sensor has changed from {oldstate} to {self._attr_native_value}")
                self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerFirmwareSensor(LuxPowerRegisterSensor):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} FIRMWARE Register: {self._register_address} Name: {self._attr_name}")  # fmt: skip
        registers = event.data.get("registers", {})
        self._data = registers

        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address For FIRMWARE: {self._register_address} is in register.keys")
            reg07_val = registers.get(7, None)
            reg08_val = registers.get(8, None)
            reg09_val = registers.get(9, None)
            reg10_val = registers.get(10, None)
            if reg07_val is None or reg08_val is None:
                _LOGGER.debug(f"ABORTING: reg07_val: {reg07_val} - reg08_val: {reg08_val}")
                return
            reg07_str = int(reg07_val).to_bytes(2, "little").decode()
            reg08_str = int(reg08_val).to_bytes(2, "little").decode()
            reg09_str = int(reg09_val).to_bytes(2, byteorder="big").hex()[0:2]
            reg10_str = int(reg10_val).to_bytes(2, byteorder="little").hex()[0:2]

            oldstate = self._attr_native_value
            self._attr_native_value = reg07_str + reg08_str + "-" + reg09_str + reg10_str
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(f"Register sensor has changed from {oldstate} to {self._attr_native_value}")
                self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerTestSensor(LuxPowerRegisterSensor):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._register_address = entity_definition["register"]
        self.entity_id = "sensor.{}_{}_{}".format("lux", serial, entity_definition["unique"])


class LuxPowerStatusTextSensor(LuxPowerSensorEntity):
    """Representation of a Status sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})
        state_text = ""
        status = int(self._data.get(self._device_attribute, 0.0))
        # fmt: off
        if status == 0:
            state_text = "Standby"
        elif status == 1:
            state_text = "Error"
        elif status == 2:
            state_text = "Inverting"
        elif status == 4:
            state_text = "Solar > Load - Surplus > Grid"
        elif status == 5:
            state_text = "Float"
        elif status == 7:
            state_text = "Charger Off"
        elif status == 8:
            state_text = "Supporting"
        elif status == 9:
            state_text = "Selling"
        elif status == 10:
            state_text = "Pass Through"
        elif status == 11:
            state_text = "Offsetting"
        elif status == 12:
            state_text = "Solar > Battery Charging"
        elif status == 16:
            state_text = "Battery Discharging > LOAD - Surplus > Grid"
        elif status == 17:
            state_text = "Temperature Over Range"
        elif status == 20:
            state_text = "Solar + Battery Discharging > LOAD - Surplus > Grid"
        elif status == 32:
            state_text = "AC Battery Charging"
        elif status == 40:
            state_text = "Solar + Grid > Battery Charging"
        elif status == 64:
            state_text = "No Grid : Battery > EPS"
        elif status == 136:
            state_text = "No Grid : Solar > EPS - Surplus > Battery Charging"
        elif status == 192:
            state_text = "No Grid : Solar + Battery Discharging > EPS"
        else:
            state_text = "Unknown"
        self._attr_native_value = f"{state_text}"
        # fmt: on

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerDataReceivedTimestampSensor(LuxPowerSensorEntity):
    """Representation of an Date & Time updated sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self.datetime_last_received = None

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})
        self.datetime_last_received = datetime.now()
        self._attr_native_value = "{}".format(datetime.now().strftime("%A %B %-d, %I:%M %p"))

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes["dongle"] = self.dongle
        if self.datetime_last_received is not None:
            state_attributes["timestamp"] = self.datetime_last_received.timestamp()
        else:
            state_attributes["timestamp"] = 0
        return state_attributes


class LuxStateSensorEntity(SensorEntity):
    """Representation of an overall sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the sensor."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"sensor.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event
        self.is_added_to_hass = False
        self.lastupdated_time = 0
        self.luxpower_client = entity_definition.get("luxpower_client", None)

        # Hidden Inherited Instance Attributes
        self._attr_unique_id = "{}_{}_{}".format(DOMAIN, self.dongle, "states")
        self._attr_name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        self._attr_native_value = "Waiting"
        self._attr_available = False
        self._attr_should_poll = False

        # Hidden Class Extended Instance Attributes
        self._host = host
        self._port = port
        self._data: Dict[str, str] = {}

        self.totaldata: Dict[str, str] = {}

    # fmt: off

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes[LXPPacket.status] = f"{self.totaldata.get(LXPPacket.status, UA)}"
        state_attributes[LXPPacket.v_pv_1] = f"{self.totaldata.get(LXPPacket.v_pv_1, UA)}"
        state_attributes[LXPPacket.v_pv_2] = f"{self.totaldata.get(LXPPacket.v_pv_2, UA)}"
        state_attributes[LXPPacket.v_pv_3] = f"{self.totaldata.get(LXPPacket.v_pv_3, UA)}"
        state_attributes[LXPPacket.v_bat] = f"{self.totaldata.get(LXPPacket.v_bat, UA)}"
        state_attributes[LXPPacket.soc] = f"{self.totaldata.get(LXPPacket.soc, UA)}"
        state_attributes[LXPPacket.p_pv_1] = f"{self.totaldata.get(LXPPacket.p_pv_1, UA)}"
        state_attributes[LXPPacket.p_pv_2] = f"{self.totaldata.get(LXPPacket.p_pv_2, UA)}"
        state_attributes[LXPPacket.p_pv_3] = f"{self.totaldata.get(LXPPacket.p_pv_3, UA)}"
        state_attributes[LXPPacket.p_pv_total] = f"{self.totaldata.get(LXPPacket.p_pv_total, UA)}"
        state_attributes[LXPPacket.p_charge] = f"{self.totaldata.get(LXPPacket.p_charge, UA)}"
        state_attributes[LXPPacket.p_discharge] = f"{self.totaldata.get(LXPPacket.p_discharge, UA)}"
        state_attributes[LXPPacket.v_ac_r] = f"{self.totaldata.get(LXPPacket.v_ac_r, UA)}"
        state_attributes[LXPPacket.v_ac_s] = f"{self.totaldata.get(LXPPacket.v_ac_s, UA)}"
        state_attributes[LXPPacket.v_ac_t] = f"{self.totaldata.get(LXPPacket.v_ac_t, UA)}"
        state_attributes[LXPPacket.f_ac] = f"{self.totaldata.get(LXPPacket.f_ac, UA)}"
        state_attributes[LXPPacket.p_inv] = f"{self.totaldata.get(LXPPacket.p_inv, UA)}"
        state_attributes[LXPPacket.p_rec] = f"{self.totaldata.get(LXPPacket.p_rec, UA)}"
        state_attributes[LXPPacket.rms_current] = f"{self.totaldata.get(LXPPacket.rms_current, UA)}"
        state_attributes[LXPPacket.pf] = f"{self.totaldata.get(LXPPacket.pf, UA)}"
        state_attributes[LXPPacket.v_eps_r] = f"{self.totaldata.get(LXPPacket.v_eps_r, UA)}"
        state_attributes[LXPPacket.v_eps_s] = f"{self.totaldata.get(LXPPacket.v_eps_s, UA)}"
        state_attributes[LXPPacket.v_eps_t] = f"{self.totaldata.get(LXPPacket.v_eps_t, UA)}"
        state_attributes[LXPPacket.f_eps] = f"{self.totaldata.get(LXPPacket.f_eps, UA)}"
        state_attributes[LXPPacket.p_to_eps] = f"{self.totaldata.get(LXPPacket.p_to_eps, UA)}"
        state_attributes[LXPPacket.p_to_grid] = f"{self.totaldata.get(LXPPacket.p_to_grid, UA)}"
        state_attributes[LXPPacket.p_to_user] = f"{self.totaldata.get(LXPPacket.p_to_user, UA)}"
        state_attributes[LXPPacket.p_load] = f"{self.totaldata.get(LXPPacket.p_load, UA)}"
        state_attributes[LXPPacket.e_pv_1_day] = f"{self.totaldata.get(LXPPacket.e_pv_1_day, UA)}"
        state_attributes[LXPPacket.e_pv_2_day] = f"{self.totaldata.get(LXPPacket.e_pv_2_day, UA)}"
        state_attributes[LXPPacket.e_pv_3_day] = f"{self.totaldata.get(LXPPacket.e_pv_3_day, UA)}"
        state_attributes[LXPPacket.e_pv_total] = f"{self.totaldata.get(LXPPacket.e_pv_total, UA)}"
        state_attributes[LXPPacket.e_inv_day] = f"{self.totaldata.get(LXPPacket.e_inv_day, UA)}"
        state_attributes[LXPPacket.e_inv_all] = f"{self.totaldata.get(LXPPacket.e_inv_all, UA)}"
        state_attributes[LXPPacket.e_rec_day] = f"{self.totaldata.get(LXPPacket.e_rec_day, UA)}"
        state_attributes[LXPPacket.e_chg_day] = f"{self.totaldata.get(LXPPacket.e_chg_day, UA)}"
        state_attributes[LXPPacket.e_dischg_day] = f"{self.totaldata.get(LXPPacket.e_dischg_day, UA)}"
        state_attributes[LXPPacket.e_eps_day] = f"{self.totaldata.get(LXPPacket.e_eps_day, UA)}"
        state_attributes[LXPPacket.e_to_grid_day] = f"{self.totaldata.get(LXPPacket.e_to_grid_day, UA)}"
        state_attributes[LXPPacket.e_to_user_day] = f"{self.totaldata.get(LXPPacket.e_to_user_day, UA)}"
        state_attributes[LXPPacket.v_bus_1] = f"{self.totaldata.get(LXPPacket.v_bus_1, UA)}"
        state_attributes[LXPPacket.v_bus_2] = f"{self.totaldata.get(LXPPacket.v_bus_2, UA)}"
        state_attributes[LXPPacket.e_pv_1_all] = f"{self.totaldata.get(LXPPacket.e_pv_1_all, UA)}"
        state_attributes[LXPPacket.e_pv_2_all] = f"{self.totaldata.get(LXPPacket.e_pv_2_all, UA)}"
        state_attributes[LXPPacket.e_pv_3_all] = f"{self.totaldata.get(LXPPacket.e_pv_3_all, UA)}"
        state_attributes[LXPPacket.e_pv_all] = f"{self.totaldata.get(LXPPacket.e_pv_all, UA)}"
        state_attributes[LXPPacket.e_rec_all] = f"{self.totaldata.get(LXPPacket.e_rec_all, UA)}"
        state_attributes[LXPPacket.e_chg_all] = f"{self.totaldata.get(LXPPacket.e_chg_all, UA)}"
        state_attributes[LXPPacket.e_dischg_all] = f"{self.totaldata.get(LXPPacket.e_dischg_all, UA)}"
        state_attributes[LXPPacket.e_eps_all] = f"{self.totaldata.get(LXPPacket.e_eps_all, UA)}"
        state_attributes[LXPPacket.e_to_grid_all] = f"{self.totaldata.get(LXPPacket.e_to_grid_all, UA)}"
        state_attributes[LXPPacket.e_to_user_all] = f"{self.totaldata.get(LXPPacket.e_to_user_all, UA)}"
        state_attributes[LXPPacket.t_inner] = f"{self.totaldata.get(LXPPacket.t_inner, UA)}"
        state_attributes[LXPPacket.t_rad_1] = f"{self.totaldata.get(LXPPacket.t_rad_1, UA)}"
        state_attributes[LXPPacket.t_rad_2] = f"{self.totaldata.get(LXPPacket.t_rad_2, UA)}"
        state_attributes[LXPPacket.t_bat] = f"{self.totaldata.get(LXPPacket.t_bat, UA)}"
        state_attributes[LXPPacket.uptime] = f"{self.totaldata.get(LXPPacket.uptime, UA)}"
        state_attributes[LXPPacket.max_chg_curr] = f"{self.totaldata.get(LXPPacket.max_chg_curr, UA)}"
        state_attributes[LXPPacket.max_dischg_curr] = f"{self.totaldata.get(LXPPacket.max_dischg_curr, UA)}"
        state_attributes[LXPPacket.charge_volt_ref] = f"{self.totaldata.get(LXPPacket.charge_volt_ref, UA)}"
        state_attributes[LXPPacket.dischg_cut_volt] = f"{self.totaldata.get(LXPPacket.dischg_cut_volt, UA)}"
        state_attributes[LXPPacket.bat_count] = f"{self.totaldata.get(LXPPacket.bat_count, UA)}"
        state_attributes[LXPPacket.bat_capacity] = f"{self.totaldata.get(LXPPacket.bat_capacity, UA)}"
        state_attributes[LXPPacket.bat_current] = f"{self.totaldata.get(LXPPacket.bat_current, UA)}"
        state_attributes[LXPPacket.max_cell_volt] = f"{self.totaldata.get(LXPPacket.max_cell_volt, UA)}"
        state_attributes[LXPPacket.min_cell_volt] = f"{self.totaldata.get(LXPPacket.min_cell_volt, UA)}"
        state_attributes[LXPPacket.max_cell_temp] = f"{self.totaldata.get(LXPPacket.max_cell_temp, UA)}"
        state_attributes[LXPPacket.min_cell_temp] = f"{self.totaldata.get(LXPPacket.min_cell_temp, UA)}"

        state_attributes[LXPPacket.gen_input_volt] = f"{self.totaldata.get(LXPPacket.gen_input_volt, UA)}"
        state_attributes[LXPPacket.gen_input_freq] = f"{self.totaldata.get(LXPPacket.gen_input_freq, UA)}"
        state_attributes[LXPPacket.gen_power_watt] = f"{self.totaldata.get(LXPPacket.gen_power_watt, UA)}"
        state_attributes[LXPPacket.gen_power_day] = f"{self.totaldata.get(LXPPacket.gen_power_day, UA)}"
        state_attributes[LXPPacket.gen_power_all] = f"{self.totaldata.get(LXPPacket.gen_power_all, UA)}"
        state_attributes[LXPPacket.eps_L1_volt] = f"{self.totaldata.get(LXPPacket.eps_L1_volt, UA)}"
        state_attributes[LXPPacket.eps_L2_volt] = f"{self.totaldata.get(LXPPacket.eps_L2_volt, UA)}"

        return state_attributes

    # fmt: on

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable)
            self.hass.bus.async_listen(self.event.EVENT_DATA_BANKX_RECEIVED, self.push_update)

    def checkonline(self, *args, **kwargs):
        _LOGGER.debug("check online")
        if time.time() - self.lastupdated_time > 10:
            self._attr_native_value = "OFFLINE"
        self.schedule_update_ha_state()

    def push_update(self, event):
        _LOGGER.debug(f"LUXPOWER State Sensor: register event received Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})
        self._attr_native_value = "ONLINE"

        self.totaldata = self.luxpower_client.lxpPacket.data
        _LOGGER.debug(f"TotalData: {self.totaldata}")

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        _LOGGER.warning(f"LUXPOWER State Sensor: gone_unavailable event received Name: {self._attr_name}")  # fmt: skip
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
