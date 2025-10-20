"""

This is written by Guy Wells (C) 2025.
This code is from https://github.com/guybw/LuxPython_DEV

This sensor.py is the sensors file for LUXPython
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

# Mapping of firmware model codes to inverter models
MODEL_MAP = {
    "AAAA": "LXP 3-6K Hybrid (Standard)",
    "AAAB": "LXP 3-6K Hybrid (Parallel)",
    "BAAA": "LXP-3600 ACS (Standard)",
    "BAAB": "LXP-3600 ACS (Parallel)",
    "CBAA": "SNA 3000-6000",
    "EAAB": "LXP-LB-EU 7K",
    "CCAA": "SNA-US 6000",
    "FAAB": "LXP-LB-8-12K",
    "ACAB": "GEN-LB-EU 3-6K",
    "HAAA": "GEB-LB-EU 7-10K",
    "CFAA": "SNA 12K",
    "CEAA": "SNA 12K-US",
    # Additional model codes found in LuxPowerTek cloud UI
    "BEAA": "LXP Variant",
    "DAAA": "LXP Variant",
}

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(
    hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices
):
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
        {"etype": "LPMD", "name": "Lux {replaceID_midfix}{hyphen} Inverter Model", "unique": "lux_inverter_model", "bank": 0, "register": 7},

        # 3. Create Attribute Sensors Based On LuxPowerSensorEntity Class
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Live)", "unique": "lux_battery_discharge", "bank": 0, "attribute": LXPPacket.p_discharge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Live)", "unique": "lux_battery_charge", "bank": 0, "attribute": LXPPacket.p_charge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery %", "unique": "lux_battery_percent", "bank": 0, "attribute": LXPPacket.soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT},
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
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Voltage (Live)", "unique": "lux_current_generator_voltage", "bank": 3, "attribute": LXPPacket.gen_input_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Frequency (Live)", "unique": "lux_current_generator_frequency", "bank": 3, "attribute": LXPPacket.gen_input_freq, "device_class": SensorDeviceClass.FREQUENCY, "unit_of_measurement": UnitOfFrequency.HERTZ, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Live)", "unique": "lux_current_generator_power", "bank": 3, "attribute": LXPPacket.gen_power_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Daily)", "unique": "lux_current_generator_power_daily", "bank": 3, "attribute": LXPPacket.gen_power_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Total)", "unique": "lux_current_generator_power_all", "bank": 3, "attribute": LXPPacket.gen_power_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Voltage (Live)", "unique": "lux_current_eps_L1_voltage", "bank": 3, "attribute": LXPPacket.eps_L1_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Voltage (Live)", "unique": "lux_current_eps_L2_voltage", "bank": 3, "attribute": LXPPacket.eps_L2_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Watts (Live)", "unique": "lux_current_eps_L1_watt", "bank": 3, "attribute": LXPPacket.eps_L1_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Watts (Live)", "unique": "lux_current_eps_L2_watt", "bank": 3, "attribute": LXPPacket.eps_L2_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
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
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Temperature (Live)", "unique": "t_bat", "bank": 1, "attribute": LXPPacket.t_bat, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Max Cell Temperature (Live)", "unique": "max_cell_temp", "bank": 2, "attribute": LXPPacket.max_cell_temp, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Min Cell Temperature (Live)", "unique": "min_cell_temp", "bank": 2, "attribute": LXPPacket.min_cell_temp, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Count", "unique": "lux_battery_count", "bank": 2, "attribute": LXPPacket.bat_count, "device_class": None, "unit_of_measurement": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Capacity Ah", "unique": "lux_battery_capacity_ah", "bank": 2, "attribute": LXPPacket.bat_capacity, "device_class": None, "unit_of_measurement": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Current", "unique": "lux_battery_current", "bank": 2, "attribute": LXPPacket.bat_current, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Cycle Count", "unique": "lux_battery_cycle_count", "bank": 2, "attribute": LXPPacket.bat_cycle_count, "device_class": None, "unit_of_measurement": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Internal Fault", "unique": "lux_internal_fault", "bank": 0, "attribute": LXPPacket.internal_fault, "device_class": None, "unit_of_measurement": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Fault Code", "unique": "lux_fault_code", "bank": 1, "attribute": LXPPacket.fault_code, "device_class": None, "unit_of_measurement": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Warning Code", "unique": "lux_warning_code", "bank": 1, "attribute": LXPPacket.warning_code, "device_class": None, "unit_of_measurement": None, "enabled": False},

        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Battery Status", "unique": "lux_battery_status", "bank": 2, "attribute": LXPPacket.bat_status_inv},


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

        # Configuration Diagnostic Sensors (Disabled by Default)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Capacity (Ah)", "unique": "lux_battery_capacity_ah", "bank": 0, "register": 38, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Type Code", "unique": "lux_battery_type_code", "bank": 0, "register": 19, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Module Count", "unique": "lux_battery_module_count", "bank": 2, "register": 113, "enabled": False},

        # System Configuration Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Grid Voltage Upper Limit", "unique": "lux_grid_v_upper", "bank": 2, "register": 90, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Grid Frequency Lower Limit", "unique": "lux_grid_freq_lower", "bank": 2, "register": 91, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Voltage Upper Config", "unique": "lux_bat_v_upper_cfg", "bank": 2, "register": 99, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Voltage Lower Config", "unique": "lux_bat_v_lower_cfg", "bank": 2, "register": 100, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery SOC Upper Config", "unique": "lux_bat_soc_upper_cfg", "bank": 2, "register": 101, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Charge Voltage Reference", "unique": "lux_charge_volt_ref", "bank": 2, "register": 107, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Discharge Cut Voltage", "unique": "lux_discharge_cut_v", "bank": 2, "register": 109, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Control Flags R110", "unique": "lux_sys_ctrl_r110", "bank": 2, "register": 110, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Min SOC Grid Charge", "unique": "lux_min_soc_grid", "bank": 2, "register": 119, "enabled": False},
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Mode Config R120", "unique": "lux_ac_mode_r120", "bank": 3, "register": 120, "enabled": False},


        # Bank 4 Energy Sensors (Available to all models)
        # These are the actual registers implemented in get_device_values_bank4()
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Load On Grid Power", "unique": "lux_load_ongrid_power", "bank": 4, "register": 170, "enabled": False},  # Load on grid power
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Load Energy", "unique": "lux_daily_load_energy", "bank": 4, "register": 171, "enabled": False},  # Daily load energy consumption  
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Total Load Energy", "unique": "lux_total_load_energy", "bank": 4, "register": 172, "enabled": False},  # Total load energy consumption

        # 12K-Specific Advanced Features (CFAA, CEAA, FAAB)
        # These features are available on 12K models but gracefully handled on other models
        # Smart Load Control (12K-specific registers 181-186)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Start SOC", "unique": "lux_smart_load_start_soc", "bank": 4, "register": 181, "attribute": "smart_load_start_soc", "enabled": False},  # Smart load start SOC threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Smart Load End SOC", "unique": "lux_smart_load_end_soc", "bank": 4, "register": 182, "attribute": "smart_load_end_soc", "enabled": False},  # Smart load end SOC threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Start Voltage", "unique": "lux_smart_load_start_volt", "bank": 4, "register": 183, "attribute": "smart_load_start_volt", "enabled": False},  # Smart load start voltage threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Smart Load End Voltage", "unique": "lux_smart_load_end_volt", "bank": 4, "register": 184, "attribute": "smart_load_end_volt", "enabled": False},  # Smart load end voltage threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Smart Load SOC Hysteresis", "unique": "lux_smart_load_soc_hysteresis", "bank": 4, "register": 185, "attribute": "smart_load_soc_hysteresis", "enabled": False},  # Smart load SOC hysteresis
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Voltage Hysteresis", "unique": "lux_smart_load_volt_hysteresis", "bank": 4, "register": 186, "attribute": "smart_load_volt_hysteresis", "enabled": False},  # Smart load voltage hysteresis
        
        # Enhanced Peak Shaving (12K-specific registers 206-208)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Power Limit", "unique": "lux_peak_shaving_power", "bank": 4, "register": 206, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving power limit
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving SOC", "unique": "lux_peak_shaving_soc", "bank": 4, "register": 207, "attribute": "peak_shaving_soc", "enabled": False},  # Peak shaving SOC threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Voltage", "unique": "lux_peak_shaving_volt", "bank": 4, "register": 208, "attribute": "peak_shaving_volt", "enabled": False},  # Peak shaving voltage threshold

        # AC Coupling (12K-specific registers 220-223)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Start SOC", "unique": "lux_ac_couple_start_soc", "bank": 4, "register": 220, "attribute": "ac_couple_start_soc", "enabled": False},  # AC couple start SOC
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Couple End SOC", "unique": "lux_ac_couple_end_soc", "bank": 4, "register": 221, "attribute": "ac_couple_end_soc", "enabled": False},  # AC couple end SOC
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Start Voltage", "unique": "lux_ac_couple_start_volt", "bank": 4, "register": 222, "attribute": "ac_couple_start_volt", "enabled": False},  # AC couple start voltage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Couple End Voltage", "unique": "lux_ac_couple_end_volt", "bank": 4, "register": 223, "attribute": "ac_couple_end_volt", "enabled": False},  # AC couple end voltage

        # Generator Integration (12K-specific registers 194-198)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start Voltage", "unique": "lux_gen_chg_start_volt", "bank": 4, "register": 194, "attribute": "gen_chg_start_volt", "enabled": False},  # Generator charge start voltage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End Voltage", "unique": "lux_gen_chg_end_volt", "bank": 4, "register": 195, "attribute": "gen_chg_end_volt", "enabled": False},  # Generator charge end voltage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start SOC", "unique": "lux_gen_chg_start_soc", "bank": 4, "register": 196, "attribute": "gen_chg_start_soc", "enabled": False},  # Generator charge start SOC
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End SOC", "unique": "lux_gen_chg_end_soc", "bank": 4, "register": 197, "attribute": "gen_chg_end_soc", "enabled": False},  # Generator charge end SOC
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Max Generator Charge Current", "unique": "lux_max_gen_chg_current", "bank": 4, "register": 198, "attribute": "max_gen_chg_current", "enabled": False},  # Max generator charge current

        # 12K System Configuration (registers 176-180)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Max System Power 12K", "unique": "lux_max_sys_power_12k", "bank": 4, "register": 176, "attribute": "max_sys_power_12k", "enabled": False},  # Max system power in watts
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Max AC Charge Power 12K", "unique": "lux_max_ac_chg_12k", "bank": 4, "register": 177, "attribute": "max_ac_chg_12k", "enabled": False},  # Max AC charge power in watts
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Configuration 12K", "unique": "lux_sys_config_12k", "bank": 4, "register": 178, "attribute": "sys_config_12k", "enabled": False},  # System configuration flags
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Config R179", "unique": "lux_peak_shave_r179", "bank": 4, "register": 179, "attribute": "peak_shave_config", "enabled": False},  # Peak shaving control flags
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Power Limit R180", "unique": "lux_power_limit_r180", "bank": 4, "register": 180, "attribute": "power_limit", "enabled": False},  # Power limit setting






        






        



        




        






        # Enhanced Diagnostics & System Status (Phase 1A-B)
        # Fault Code & Warning System - Register 0 is valid for system status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Fault Code", "unique": "lux_fault_code", "bank": 0, "register": 0, "enabled": False},  # Current fault code
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Warning Code", "unique": "lux_warning_code", "bank": 0, "register": 0, "enabled": False},  # Current warning code
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Status Code", "unique": "lux_system_status", "bank": 0, "register": 0, "enabled": False},  # System status code
        
        # Power Flow Sensors (calculated from existing power values) - COMMENTED: register 0 invalid
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV to Battery Power", "unique": "lux_pv_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # PV charging battery
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV to Load Power", "unique": "lux_pv_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # PV directly powering loads
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV to Grid Power", "unique": "lux_pv_to_grid", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # PV exporting to grid
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery to Load Power", "unique": "lux_battery_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Battery discharging to loads
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery to Grid Power", "unique": "lux_battery_to_grid", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Battery exporting to grid
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid to Battery Power", "unique": "lux_grid_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Grid charging battery
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid to Load Power", "unique": "lux_grid_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Grid powering loads
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator to Battery Power", "unique": "lux_generator_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Generator charging
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator to Load Power", "unique": "lux_generator_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Generator powering loads
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple to Battery Power", "unique": "lux_ac_couple_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # AC-coupled PV to battery
        # {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple to Grid Power", "unique": "lux_ac_couple_to_grid", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # AC-coupled PV to grid
        
        # Battery Management System (BMS) Integration - COMMENTED: register 0 invalid
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} BMS Status", "unique": "lux_bms_status", "bank": 0, "register": 0, "enabled": False},  # BMS communication status
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} BMS Fault Code", "unique": "lux_bms_fault", "bank": 0, "register": 0, "enabled": False},  # BMS fault code
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Health %", "unique": "lux_battery_health", "bank": 0, "register": 0, "enabled": False},  # Battery health percentage
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Cycle Count", "unique": "lux_battery_cycles", "bank": 0, "register": 0, "enabled": False},  # Battery cycle count
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Voltage Max", "unique": "lux_cell_voltage_max", "bank": 0, "register": 0, "enabled": False},  # Maximum cell voltage
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Voltage Min", "unique": "lux_cell_voltage_min", "bank": 0, "register": 0, "enabled": False},  # Minimum cell voltage
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Temperature Max", "unique": "lux_cell_temp_max", "bank": 0, "register": 0, "enabled": False},  # Maximum cell temperature
        # {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Temperature Min", "unique": "lux_cell_temp_min", "bank": 0, "register": 0, "enabled": False},  # Minimum cell temperature
        
        # Inverter Health Metrics
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Transformer Temperature", "unique": "lux_transformer_temp", "bank": 0, "register": 0, "enabled": False},  # Transformer temperature
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Heatsink Temperature", "unique": "lux_heatsink_temp", "bank": 0, "register": 0, "enabled": False},  # Heatsink temperature
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Fan Speed Actual", "unique": "lux_fan_speed_actual", "bank": 0, "register": 0, "enabled": False},  # Actual fan speed
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Internal Humidity", "unique": "lux_internal_humidity", "bank": 0, "register": 0, "enabled": False},  # Internal humidity
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} DC AC Efficiency", "unique": "lux_dc_ac_efficiency", "bank": 0, "register": 0, "enabled": False},  # DC/AC conversion efficiency
        
        # Operating Mode Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Operating Mode", "unique": "lux_operating_mode", "bank": 0, "register": 0, "enabled": False},  # Current operating mode
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Charge Mode Status", "unique": "lux_charge_mode", "bank": 0, "register": 0, "enabled": False},  # Charge mode status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Load Priority Mode", "unique": "lux_load_priority", "bank": 0, "register": 0, "enabled": False},  # Load priority mode
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} EPS Status", "unique": "lux_eps_status", "bank": 0, "register": 0, "enabled": False},  # EPS status and switch state

        # Parallel System Support (Phase 2)
        # Multi-Inverter Coordination Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System ID", "unique": "lux_parallel_system_id", "bank": 0, "register": 0, "enabled": False},  # Parallel system identifier
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Unit Count", "unique": "lux_parallel_unit_count", "bank": 0, "register": 0, "enabled": False},  # Number of units in parallel
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Unit Role", "unique": "lux_parallel_unit_role", "bank": 0, "register": 0, "enabled": False},  # Master/Slave role (0=Master, 1=Slave)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Unit Index", "unique": "lux_parallel_unit_index", "bank": 0, "register": 0, "enabled": False},  # Unit index in parallel system
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Total System Power", "unique": "lux_total_system_power", "bank": 0, "register": 0, "enabled": False},  # Combined power of all units
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Communication Status", "unique": "lux_parallel_comm_status", "bank": 0, "register": 0, "enabled": False},  # Communication quality between units
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Phase Sync Quality", "unique": "lux_phase_sync_quality", "bank": 0, "register": 0, "enabled": False},  # Phase synchronization quality
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Load Distribution", "unique": "lux_load_distribution", "bank": 0, "register": 0, "enabled": False},  # Load balancing between units
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Fault Code", "unique": "lux_parallel_fault", "bank": 0, "register": 0, "enabled": False},  # Parallel system fault code

        # Generator Integration (Phase 3)
        # Generator Monitoring Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Runtime Hours", "unique": "lux_gen_runtime_hours", "bank": 0, "register": 0, "enabled": False},  # Total generator runtime
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Fuel Level", "unique": "lux_gen_fuel_level", "bank": 0, "register": 0, "enabled": False},  # Generator fuel level percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Frequency", "unique": "lux_gen_frequency", "bank": 0, "register": 0, "enabled": False},  # Generator frequency
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Voltage", "unique": "lux_gen_voltage", "bank": 0, "register": 0, "enabled": False},  # Generator voltage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Power Output", "unique": "lux_gen_power_output", "bank": 0, "register": 0, "enabled": False},  # Generator power output
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Start Count", "unique": "lux_gen_start_count", "bank": 0, "register": 0, "enabled": False},  # Number of generator starts
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Last Start Time", "unique": "lux_gen_last_start", "bank": 0, "register": 0, "enabled": False},  # Last generator start timestamp
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Charging Contribution", "unique": "lux_gen_charging_contribution", "bank": 0, "register": 0, "enabled": False},  # Power contributed to battery charging
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Generator Load Contribution", "unique": "lux_gen_load_contribution", "bank": 0, "register": 0, "enabled": False},  # Power contributed to load

        # Battery Management Enhancements (Phase 6)
        # Battery Profile & Configuration Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Type", "unique": "lux_battery_type", "bank": 0, "register": 0, "enabled": False},  # Battery type (Lead-Acid, Lithium, LiFePO4)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Profile", "unique": "lux_battery_profile", "bank": 0, "register": 0, "enabled": False},  # Active battery profile
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Temperature Compensation", "unique": "lux_temp_compensation", "bank": 0, "register": 0, "enabled": False},  # Temperature compensation factor
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Aging Compensation", "unique": "lux_aging_compensation", "bank": 0, "register": 0, "enabled": False},  # Battery aging compensation
        
        # Advanced Battery Protection Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Over Temp Protection Threshold", "unique": "lux_over_temp_threshold", "bank": 0, "register": 0, "enabled": False},  # Over-temperature protection threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Under Temp Lockout Threshold", "unique": "lux_under_temp_threshold", "bank": 0, "register": 0, "enabled": False},  # Under-temperature charging lockout
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Imbalance Threshold", "unique": "lux_cell_imbalance_threshold", "bank": 0, "register": 0, "enabled": False},  # Cell imbalance detection threshold
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Equalization Voltage", "unique": "lux_equalization_voltage", "bank": 0, "register": 0, "enabled": False},  # Equalization voltage for lead-acid
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Equalization Time", "unique": "lux_equalization_time", "bank": 0, "register": 0, "enabled": False},  # Equalization time remaining
        
        # Battery Optimization Sensors
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Charge Efficiency", "unique": "lux_charge_efficiency", "bank": 0, "register": 0, "enabled": False},  # Battery charge efficiency percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Depth of Discharge", "unique": "lux_depth_of_discharge", "bank": 0, "register": 0, "enabled": False},  # Current depth of discharge
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Max Depth of Discharge", "unique": "lux_max_depth_of_discharge", "bank": 0, "register": 0, "enabled": False},  # Maximum depth of discharge reached
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Wear Level", "unique": "lux_battery_wear_level", "bank": 0, "register": 0, "enabled": False},  # Battery wear leveling status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Calendar Life Remaining", "unique": "lux_calendar_life_remaining", "bank": 0, "register": 0, "enabled": False},  # Calendar life remaining percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cycle Life Remaining", "unique": "lux_cycle_life_remaining", "bank": 0, "register": 0, "enabled": False},  # Cycle life remaining percentage

        # Grid Management & Export Control (Phase 7)
        # Grid Import/Export Limits
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Grid Import Limit", "unique": "lux_grid_import_limit", "bank": 0, "register": 0, "enabled": False},  # Grid import power limit
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Grid Export Limit", "unique": "lux_grid_export_limit", "bank": 0, "register": 0, "enabled": False},  # Grid export power limit
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Time Based Export Limit", "unique": "lux_time_export_limit", "bank": 0, "register": 0, "enabled": False},  # Time-based export limit
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Dynamic Export Control", "unique": "lux_dynamic_export_control", "bank": 0, "register": 0, "enabled": False},  # Dynamic export based on grid frequency
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Zero Export Mode", "unique": "lux_zero_export_mode", "bank": 0, "register": 0, "enabled": False},  # Zero export mode status
        
        # Grid Support Features
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Reactive Power Control", "unique": "lux_reactive_power_control", "bank": 0, "register": 0, "enabled": False},  # Q control status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Power Factor Setpoint", "unique": "lux_power_factor_setpoint", "bank": 0, "register": 0, "enabled": False},  # Power factor adjustment
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Voltage Support Mode", "unique": "lux_voltage_support_mode", "bank": 0, "register": 0, "enabled": False},  # Voltage support mode
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Frequency Response", "unique": "lux_frequency_response", "bank": 0, "register": 0, "enabled": False},  # Frequency response settings
        
        # Grid Protection Enhancements
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Anti Islanding Sensitivity", "unique": "lux_anti_islanding_sensitivity", "bank": 0, "register": 0, "enabled": False},  # Anti-islanding detection sensitivity
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Grid Reconnection Delay", "unique": "lux_grid_reconnection_delay", "bank": 0, "register": 0, "enabled": False},  # Grid reconnection delay
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Voltage Ride Through", "unique": "lux_voltage_ride_through", "bank": 0, "register": 0, "enabled": False},  # Voltage ride-through settings
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Frequency Ride Through", "unique": "lux_frequency_ride_through", "bank": 0, "register": 0, "enabled": False},  # Frequency ride-through settings
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Grid Fault Detection", "unique": "lux_grid_fault_detection", "bank": 0, "register": 0, "enabled": False},  # Grid fault detection sensitivity

        # Register 179 Bit Analysis (0xD100 = 53504 in SNA-12K-US)
















        
        # Time-of-Use Peak Shaving Schedule
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Schedule Active", "unique": "lux_peak_schedule_active", "bank": 0, "register": 0, "enabled": False},  # Peak shaving schedule status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Start Time", "unique": "lux_peak_start_time", "bank": 0, "register": 0, "enabled": False},  # Peak shaving start time
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving End Time", "unique": "lux_peak_end_time", "bank": 0, "register": 0, "enabled": False},  # Peak shaving end time
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Days", "unique": "lux_peak_shaving_days", "bank": 0, "register": 0, "enabled": False},  # Days of week for peak shaving

        # UI Enhancements & Diagnostic Tools (Phase 8)
        # Communication Quality Metrics
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Communication Quality", "unique": "lux_comm_quality", "bank": 0, "register": 0, "enabled": False},  # Communication quality percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Data Refresh Rate", "unique": "lux_data_refresh_rate", "bank": 0, "register": 0, "enabled": False},  # Data refresh rate in Hz
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Packet Loss Rate", "unique": "lux_packet_loss_rate", "bank": 0, "register": 0, "enabled": False},  # Packet loss rate percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Connection Uptime", "unique": "lux_connection_uptime", "bank": 0, "register": 0, "enabled": False},  # Connection uptime in hours
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Last Data Update", "unique": "lux_last_data_update", "bank": 0, "register": 0, "enabled": False},  # Timestamp of last data update
        
        # Performance Metrics
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Efficiency", "unique": "lux_system_efficiency", "bank": 0, "register": 0, "enabled": False},  # Overall system efficiency percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} PV Efficiency", "unique": "lux_pv_efficiency", "bank": 0, "register": 0, "enabled": False},  # PV system efficiency percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Efficiency", "unique": "lux_battery_efficiency", "bank": 0, "register": 0, "enabled": False},  # Battery round-trip efficiency percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Inverter Efficiency", "unique": "lux_inverter_efficiency", "bank": 0, "register": 0, "enabled": False},  # Inverter conversion efficiency percentage
        
        # Energy Statistics
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Energy Generated", "unique": "lux_daily_energy_generated", "bank": 0, "register": 0, "enabled": False},  # Daily PV energy generation
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Energy Consumed", "unique": "lux_daily_energy_consumed", "bank": 0, "register": 0, "enabled": False},  # Daily energy consumption
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Energy Exported", "unique": "lux_daily_energy_exported", "bank": 0, "register": 0, "enabled": False},  # Daily energy exported to grid
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Energy Imported", "unique": "lux_daily_energy_imported", "bank": 0, "register": 0, "enabled": False},  # Daily energy imported from grid
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Self Consumption Rate", "unique": "lux_self_consumption_rate", "bank": 0, "register": 0, "enabled": False},  # Self-consumption rate percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Autonomy Rate", "unique": "lux_autonomy_rate", "bank": 0, "register": 0, "enabled": False},  # Energy autonomy rate percentage
        
        # Cost Tracking
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Grid Cost", "unique": "lux_daily_grid_cost", "bank": 0, "register": 0, "enabled": False},  # Daily cost of grid energy
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Export Revenue", "unique": "lux_daily_export_revenue", "bank": 0, "register": 0, "enabled": False},  # Daily revenue from energy export
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Daily Net Cost", "unique": "lux_daily_net_cost", "bank": 0, "register": 0, "enabled": False},  # Daily net energy cost (import - export)
        
        # Environmental Impact
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} CO2 Saved Today", "unique": "lux_co2_saved_today", "bank": 0, "register": 0, "enabled": False},  # CO2 emissions saved today
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} CO2 Saved Total", "unique": "lux_co2_saved_total", "bank": 0, "register": 0, "enabled": False},  # Total CO2 emissions saved
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Environmental Impact", "unique": "lux_environmental_impact", "bank": 0, "register": 0, "enabled": False},  # Environmental impact score

        # Firmware Management Sensors (Direct Modbus Only - No Cloud Dependency)
        # These sensors read firmware information directly from inverter registers
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Version PCS", "unique": "lux_firmware_version_pcs", "bank": 0, "register": 0, "enabled": False},  # PCS firmware version (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Version BMS", "unique": "lux_firmware_version_bms", "bank": 0, "register": 0, "enabled": False},  # BMS firmware version (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Version EXT4", "unique": "lux_firmware_version_ext4", "bank": 0, "register": 0, "enabled": False},  # EXT4 firmware version (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Update Status", "unique": "lux_firmware_update_status", "bank": 0, "register": 0, "enabled": False},  # Firmware update status (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Update Progress", "unique": "lux_firmware_update_progress", "bank": 0, "register": 0, "enabled": False},  # Firmware update progress percentage (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Update Error", "unique": "lux_firmware_update_error", "bank": 0, "register": 0, "enabled": False},  # Firmware update error message (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Last Firmware Update", "unique": "lux_last_firmware_update", "bank": 0, "register": 0, "enabled": False},  # Last firmware update timestamp (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Available Firmware Updates", "unique": "lux_available_firmware_updates", "bank": 0, "register": 0, "enabled": False},  # Number of available firmware updates (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Update Mode", "unique": "lux_firmware_update_mode", "bank": 0, "register": 0, "enabled": False},  # Update mode (fast/normal) (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Firmware Update Type", "unique": "lux_firmware_update_type", "bank": 0, "register": 0, "enabled": False},  # Update type (remote/standard) (from inverter register)

        # 12K Parallel System Sensors (Based on Cloud UI Analysis)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Status", "unique": "lux_parallel_system_status", "bank": 0, "register": 0, "enabled": False},  # Parallel system status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Count", "unique": "lux_parallel_system_count", "bank": 0, "register": 0, "enabled": False},  # Number of parallel units
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Role", "unique": "lux_parallel_system_role", "bank": 0, "register": 0, "enabled": False},  # Master/Slave role
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Sync", "unique": "lux_parallel_system_sync", "bank": 0, "register": 0, "enabled": False},  # Parallel system synchronization status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Communication", "unique": "lux_parallel_system_comm", "bank": 0, "register": 0, "enabled": False},  # Parallel system communication status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Load Distribution", "unique": "lux_parallel_system_load_dist", "bank": 0, "register": 0, "enabled": False},  # Load distribution across parallel units
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Phase Sync", "unique": "lux_parallel_system_phase_sync", "bank": 0, "register": 0, "enabled": False},  # Phase synchronization quality
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Fault Code", "unique": "lux_parallel_system_fault_code", "bank": 0, "register": 0, "enabled": False},  # Parallel system fault code

        # Battery Firmware Management Sensors (Direct Modbus Only - No Cloud Dependency)
        # These sensors read battery firmware information directly from inverter registers
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Type", "unique": "lux_battery_firmware_type", "bank": 0, "register": 0, "enabled": False},  # Current battery firmware type (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Version", "unique": "lux_battery_firmware_version", "bank": 0, "register": 0, "enabled": False},  # Current battery firmware version (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Update Status", "unique": "lux_battery_firmware_update_status", "bank": 0, "register": 0, "enabled": False},  # Battery firmware update status (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Update Progress", "unique": "lux_battery_firmware_update_progress", "bank": 0, "register": 0, "enabled": False},  # Battery firmware update progress percentage (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Update Error", "unique": "lux_battery_firmware_update_error", "bank": 0, "register": 0, "enabled": False},  # Battery firmware update error message (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Last Battery Firmware Update", "unique": "lux_last_battery_firmware_update", "bank": 0, "register": 0, "enabled": False},  # Last battery firmware update timestamp (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Available Battery Firmware Updates", "unique": "lux_available_battery_firmware_updates", "bank": 0, "register": 0, "enabled": False},  # Number of available battery firmware updates (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Compatibility", "unique": "lux_battery_firmware_compatibility", "bank": 0, "register": 0, "enabled": False},  # Battery firmware compatibility status (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Manufacturer", "unique": "lux_battery_manufacturer", "bank": 0, "register": 0, "enabled": False},  # Battery manufacturer (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Chemistry", "unique": "lux_battery_chemistry", "bank": 0, "register": 0, "enabled": False},  # Battery chemistry type (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Regional Variant", "unique": "lux_battery_regional_variant", "bank": 0, "register": 0, "enabled": False},  # Regional battery firmware variant (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Checksum", "unique": "lux_battery_firmware_checksum", "bank": 0, "register": 0, "enabled": False},  # Battery firmware checksum (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Upload Time", "unique": "lux_battery_firmware_upload_time", "bank": 0, "register": 0, "enabled": False},  # Battery firmware upload timestamp (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware File Size", "unique": "lux_battery_firmware_file_size", "bank": 0, "register": 0, "enabled": False},  # Battery firmware file size (from inverter register)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Firmware Validation", "unique": "lux_battery_firmware_validation", "bank": 0, "register": 0, "enabled": False},  # Battery firmware validation status (from inverter register)

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
        elif etype == "LPMD":
            sensorEntities.append(LuxPowerModelSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPDR":
            sensorEntities.append(LuxPowerDataReceivedTimestampSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPST":
            sensorEntities.append(LuxPowerStatusTextSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPFS":
            sensorEntities.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPHC":
            sensorEntities.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPBS":
            sensorEntities.append(LuxPowerBatteryStatusSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
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
        self._attr_unique_id = "{}_{}_{}".format(
            DOMAIN, dongle, entity_definition["unique"]
        )
        self._attr_name = entity_definition["name"].format(
            replaceID_midfix=nameID_midfix, hyphen=hyphen
        )
        self._attr_native_value = "Unavailable"
        self._attr_available = False
        self._attr_device_class = entity_definition.get("device_class", None)
        self._attr_state_class = entity_definition.get("state_class", None)
        self._attr_native_unit_of_measurement = entity_definition.get(
            "unit_of_measurement", None
        )
        self._attr_should_poll = False
        self._attr_entity_registry_enabled_default = entity_definition.get(
            "enabled", True
        )

        # Hidden Class Extended Instance Attributes
        self._host = host
        self._port = port
        self._data: Dict[str, str] = {}
        self._bank = entity_definition.get("bank", 0)
        self._device_attribute = entity_definition.get("attribute", None)
        self._decimal_places = entity_definition.get("decimal_places", 1)
        self._calculated = entity_definition.get("calculated", False)

        # _LOGGER.debug("Slugified entity_id: %s", self.entity_id)

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(
                self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable
            )
            if self._bank == 0:
                self.hass.bus.async_listen(
                    self.event.EVENT_DATA_BANK0_RECEIVED, self.push_update
                )
            elif self._bank == 1:
                self.hass.bus.async_listen(
                    self.event.EVENT_DATA_BANK1_RECEIVED, self.push_update
                )
            elif self._bank == 2:
                self.hass.bus.async_listen(
                    self.event.EVENT_DATA_BANK2_RECEIVED, self.push_update
                )
            elif self._bank == 3:
                self.hass.bus.async_listen(
                    self.event.EVENT_DATA_BANK3_RECEIVED, self.push_update
                )
            elif self._bank == 4:
                self.hass.bus.async_listen(
                    self.event.EVENT_DATA_BANK4_RECEIVED, self.push_update
                )
            else:
                self.hass.bus.async_listen(
                    self.event.EVENT_DATA_RECEIVED, self.push_update
                )

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})
        
        # Handle calculated power flow values
        if hasattr(self, '_calculated') and self._calculated:
            # For calculated power flow sensors, use the calculated values
            value = self._data.get(self._device_attribute)
        else:
            # For regular sensors, use the standard attribute
            value = self._data.get(self._device_attribute)
            
        # Handle 12K-specific sensors gracefully
        if value is None and self._device_attribute in [
            "max_sys_power_12k", "max_ac_chg_12k", "sys_config_12k", "peak_shave_config", "power_limit",
            "smart_load_start_soc", "smart_load_end_soc", "smart_load_start_volt", "smart_load_end_volt",
            "smart_load_soc_hysteresis", "smart_load_volt_hysteresis", "gen_chg_start_volt", "gen_chg_end_volt",
            "gen_chg_start_soc", "gen_chg_end_soc", "max_gen_chg_current", "peak_shaving_power",
            "peak_shaving_soc", "peak_shaving_volt", "ac_couple_start_soc", "ac_couple_end_soc",
            "ac_couple_start_volt", "ac_couple_end_volt"
        ]:
            # This is a 12K-specific sensor on a non-12K model
            value = "Not Available (12K Only)"
            self._attr_available = False
        elif isinstance(value, (int, float)):
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
        entry_id = None
        for e_id, data in self.hass.data.get(DOMAIN, {}).items():
            if data.get("DONGLE") == self.dongle:
                entry_id = e_id
                break
        model = (
            self.hass.data[DOMAIN].get(entry_id, {}).get("model", "LUXPower Inverter")
        )
        sw_version = (
            self.hass.data[DOMAIN]
            .get(entry_id, {})
            .get("lux_firmware_version", VERSION)
        )
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model=model,
            name=self.dongle,
            sw_version=sw_version,
        )


class LuxPowerFlowSensor(LuxPowerSensorEntity):
    """
    Representation of a Numeric LUXpower Flow sensor.

    Template equation state = -1*attribute1 if attribute1 > 0 else attribute2
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event
    ):
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

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._device_attribute1 = entity_definition[
            "attribute1"
        ]  # Power from grid to consumer unit
        self._device_attribute2 = entity_definition[
            "attribute2"
        ]  # Power from consumer unit to inverter
        self._device_attribute3 = entity_definition[
            "attribute3"
        ]  # Power from inverter to consumer unit
        self._device_attribute4 = entity_definition[
            "attribute4"
        ]  # Power from consumer unit to grid

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

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._register_address = entity_definition["register"]

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(
                self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable
            )
            if self._register_address == 21:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_21_RECEIVED, self.push_update
                )
            elif 0 <= self._register_address <= 39:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update
                )
            elif 40 <= self._register_address <= 79:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_BANK1_RECEIVED, self.push_update
                )
            elif 80 <= self._register_address <= 119:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_BANK2_RECEIVED, self.push_update
                )
            elif 120 <= self._register_address <= 159:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_BANK3_RECEIVED, self.push_update
                )
            elif 160 <= self._register_address <= 199:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_BANK4_RECEIVED, self.push_update
                )
            elif 200 <= self._register_address <= 239:
                self.hass.bus.async_listen(
                    self.event.EVENT_REGISTER_BANK5_RECEIVED, self.push_update
                )

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Register: {self._register_address} Name: {self._attr_name}")  # fmt: skip
        registers = event.data.get("registers", {})
        self._data = registers

        if self._register_address in registers.keys():
            _LOGGER.debug(
                f"Register Address: {self._register_address} is in register.keys"
            )
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            oldstate = self._attr_native_value
            self._attr_native_value = float(register_val)
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(
                    f"Register sensor has changed from {oldstate} to {self._attr_native_value}"
                )
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
            _LOGGER.debug(
                f"Register Address For FIRMWARE: {self._register_address} is in register.keys"
            )
            reg07_val = registers.get(7, None)
            reg08_val = registers.get(8, None)
            reg09_val = registers.get(9, None)
            reg10_val = registers.get(10, None)
            if reg07_val is None or reg08_val is None:
                _LOGGER.debug(
                    f"ABORTING: reg07_val: {reg07_val} - reg08_val: {reg08_val}"
                )
                return
            reg07_str = int(reg07_val).to_bytes(2, "little").decode()
            reg08_str = int(reg08_val).to_bytes(2, "little").decode()
            reg09_str = int(reg09_val).to_bytes(2, byteorder="big").hex()[0:2]
            reg10_str = int(reg10_val).to_bytes(2, byteorder="little").hex()[0:2]

            oldstate = self._attr_native_value
            firmware = reg07_str + reg08_str + "-" + reg09_str + reg10_str
            self._attr_native_value = firmware

            # Save firmware into hass.data for device_info usage
            entry_id = None
            for e_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    entry_id = e_id
                    break
            if entry_id is not None:
                self.hass.data[DOMAIN].setdefault(entry_id, {})[
                    "lux_firmware_version"
                ] = firmware
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(
                    f"Register sensor has changed from {oldstate} to {self._attr_native_value}"
                )
                self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerModelSensor(LuxPowerRegisterSensor):
    """Sensor that exposes the inverter model based on firmware code."""

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} MODEL Register: {self._register_address} Name: {self._attr_name}"
        )
        registers = event.data.get("registers", {})
        self._data = registers

        if self._register_address in registers.keys():
            reg07_val = registers.get(7, None)
            reg08_val = registers.get(8, None)
            if reg07_val is None or reg08_val is None:
                _LOGGER.debug(
                    f"ABORTING: reg07_val: {reg07_val} - reg08_val: {reg08_val}"
                )
                return
            reg07_str = int(reg07_val).to_bytes(2, "little").decode()
            reg08_str = int(reg08_val).to_bytes(2, "little").decode()
            code = reg07_str + reg08_str
            model = MODEL_MAP.get(code.upper(), "Unknown")

            # Save model into hass.data for device_info usage
            entry_id = None
            for e_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    entry_id = e_id
                    break
            if entry_id is not None:
                self.hass.data[DOMAIN].setdefault(entry_id, {})["model"] = model

            oldstate = self._attr_native_value
            self._attr_native_value = model
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(
                    f"Register sensor has changed from {oldstate} to {self._attr_native_value}"
                )
                self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerTestSensor(LuxPowerRegisterSensor):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)
        self._register_address = entity_definition["register"]
        self.entity_id = "sensor.{}_{}_{}".format(
            "lux", serial, entity_definition["unique"]
        )


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


class LuxPowerBatteryStatusSensor(LuxPowerSensorEntity):
    """Representation of the inverter battery status."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event)

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}"
        )  # fmt: skip
        self._data = event.data.get("data", {})
        status = int(self._data.get(self._device_attribute, 0))

        if status == 0:
            state_text = "Charge Forbidden & Discharge Forbidden"
        elif status == 2:
            state_text = "Charge Forbidden & Discharge Allowed"
        elif status == 3:
            state_text = "Charge Allowed & Discharge Allowed"
        elif status == 17:
            state_text = "Charge Allowed & Discharge Forbidden"
        else:
            state_text = "Unknown"

        self._attr_native_value = state_text
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
        self._attr_native_value = "{}".format(
            datetime.now().strftime("%A %B %-d, %I:%M %p")
        )

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
        self._attr_name = entity_definition["name"].format(
            replaceID_midfix=nameID_midfix, hyphen=hyphen
        )
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
        state_attributes[LXPPacket.bat_status_inv] = f"{self.totaldata.get(LXPPacket.bat_status_inv, UA)}"
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
        state_attributes[LXPPacket.eps_L1_watt] = f"{self.totaldata.get(LXPPacket.eps_L1_watt, UA)}"
        state_attributes[LXPPacket.eps_L2_watt] = f"{self.totaldata.get(LXPPacket.eps_L2_watt, UA)}"
        state_attributes[LXPPacket.p_load_ongrid] = f"{self.totaldata.get(LXPPacket.p_load_ongrid, UA)}"
        state_attributes[LXPPacket.e_load_day] = f"{self.totaldata.get(LXPPacket.e_load_day, UA)}"
        state_attributes[LXPPacket.e_load_all_l] = f"{self.totaldata.get(LXPPacket.e_load_all_l, UA)}"
        state_attributes[LXPPacket.internal_fault] = f"{self.totaldata.get(LXPPacket.internal_fault, UA)}"
        state_attributes[LXPPacket.fault_code] = f"{self.totaldata.get(LXPPacket.fault_code, UA)}"
        state_attributes[LXPPacket.warning_code] = f"{self.totaldata.get(LXPPacket.warning_code, UA)}"

        return state_attributes

    # fmt: on

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(
                self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable
            )
            self.hass.bus.async_listen(
                self.event.EVENT_DATA_BANKX_RECEIVED, self.push_update
            )

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
        entry_id = None
        for e_id, data in self.hass.data.get(DOMAIN, {}).items():
            if data.get("DONGLE") == self.dongle:
                entry_id = e_id
                break
        model = (
            self.hass.data[DOMAIN].get(entry_id, {}).get("model", "LUXPower Inverter")
        )
        sw_version = (
            self.hass.data[DOMAIN]
            .get(entry_id, {})
            .get("lux_firmware_version", VERSION)
        )
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model=model,
            name=self.dongle,
            sw_version=sw_version,
        )
