"""
LuxPower sensor platform for Home Assistant.

This module provides sensor entities for monitoring LuxPower inverter data
including power readings, battery status, grid information, and system statistics.
Supports multiple inverter models with appropriate sensor configurations.
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
from homeassistant.helpers.entity import DeviceInfo, Entity, EntityCategory
from homeassistant.util import slugify

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DEFAULT_DONGLE_SERIAL,
    DEFAULT_SERIAL_NUMBER,
    DOMAIN,
    UA,
    VERSION,
    MODEL_MAP,
    is_12k_model,
)
from .helpers import Event, get_comprehensive_device_info, get_device_group_info, get_entity_device_group
from .LXPPacket import LXPPacket


def detect_model_code(registers: dict) -> str:
    """Detect model code from registers 7 and 8."""
    try:
        reg07_val = registers.get(7)
        reg08_val = registers.get(8)
        if reg07_val is not None and reg08_val is not None:
            reg07_str = int(reg07_val).to_bytes(2, "little").decode()
            reg08_str = int(reg08_val).to_bytes(2, "little").decode()
            return (reg07_str + reg08_str).upper()
    except Exception:
        pass
    return None


# List of 12K-specific sensor attributes
TWELVE_K_ATTRIBUTES = {
    "max_sys_power_12k", "max_ac_chg_12k", "sys_config_12k", 
    "peak_shave_config", "power_limit", "smart_load_start_soc",
    "smart_load_end_soc", "smart_load_start_volt", "smart_load_end_volt",
    "smart_load_soc_hysteresis", "smart_load_volt_hysteresis",
    "gen_chg_start_volt", "gen_chg_end_volt", "gen_chg_start_soc",
    "gen_chg_end_soc", "max_gen_chg_current", "peak_shaving_power",
    "peak_shaving_soc", "peak_shaving_volt", "ac_couple_start_soc",
    "ac_couple_end_soc", "ac_couple_start_volt", "ac_couple_end_volt"
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
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, DEFAULT_DONGLE_SERIAL)
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, DEFAULT_SERIAL_NUMBER)
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    # Options For Name Midfix Based Upon Serial Number - Suggest Last Two Digits
    # nameID_midfix = SERIAL if USE_SERIAL else ""
    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""

    # Options For Entity Midfix Based Upon Serial Number - Suggest Full Serial Number
    entityID_midfix = SERIAL if USE_SERIAL else ""

    # Options For Hyphen Use Before Entity Description - Suggest No Hyphen As Of 15/02/23
    # hyphen = " -" if USE_SERIAL else "-"
    hyphen = ""
    
    # Retrieve cached model code
    entry_id = config_entry.entry_id
    model_code = None
    
    # Check config entry options first (persists across restarts)
    if "model_code" in config_entry.options:
        model_code = config_entry.options["model_code"]
        _LOGGER.info(f"Using cached model code from config entry: {model_code}")
    
    # Check hass.data second (available after first register read)
    elif entry_id in hass.data.get(DOMAIN, {}):
        model_code = hass.data[DOMAIN][entry_id].get("model_code")
        if model_code:
            _LOGGER.info(f"Using cached model code from hass.data: {model_code}")
    
    # Log model detection status
    if model_code:
        is_12k = is_12k_model(model_code)
        model_name = MODEL_MAP.get(model_code, "Unknown")
        _LOGGER.info(f"Model detected: {model_name} ({model_code}) - {'12K' if is_12k else 'non-12K'}")
    else:
        _LOGGER.info("No model code available - using default entity enablement")

    _LOGGER.info(f"Lux sensor platform_config: {platform_config}")

    event = Event(dongle=DONGLE)

    luxpower_client = hass.data[event.CLIENT_DAEMON]

    # Check if read-only mode is enabled
    read_only_mode = platform_config.get("lux_read_only_mode", False)
    _LOGGER.info(f"Read-only mode: {read_only_mode}")

    # fmt: off

    sensorEntities: List[Entity] = []

    sensors = [

        # 1. Create Overall Master State Sensor
        {"etype": "LPSS", "name": "LUXPower {replaceID_midfix}", "unique": "states", "device_class": CONF_MODE, "luxpower_client": luxpower_client},

        # 2. Create HOLDING Register Based Sensors 1st - As they Are Only Populated By Default At Integration Load - Slow RPi Timing
        {"etype": "LPFW", "name": "Lux {replaceID_midfix}{hyphen} Firmware Version", "unique": "lux_firmware_version", "bank": 0, "register": 7, "device_class": None, "state_class": None, "unit_of_measurement": None},
        {"etype": "LPMD", "name": "Lux {replaceID_midfix}{hyphen} Inverter Model", "unique": "lux_inverter_model", "bank": 0, "register": [7, 8]},
        {"etype": "LPSN", "name": "Lux {replaceID_midfix}{hyphen} Inverter Serial Number", "unique": "lux_inverter_serial_number", "bank": 0, "register": 0, "device_class": None, "state_class": None, "unit_of_measurement": None},

        # 3. Create Attribute Sensors Based On LuxPowerSensorEntity Class
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Live)", "unique": "lux_battery_discharge", "bank": 0, "attribute": LXPPacket.p_discharge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Live)", "unique": "lux_battery_charge", "bank": 0, "attribute": LXPPacket.p_charge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery SOC", "unique": "lux_battery_soc", "bank": 0, "attribute": LXPPacket.soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Daily)", "unique": "lux_daily_battery_discharge", "bank": 0, "attribute": LXPPacket.e_dischg_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Total)", "unique": "lux_total_battery_discharge", "bank": 1, "attribute": LXPPacket.e_dischg_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Daily)", "unique": "lux_daily_battery_charge", "bank": 0, "attribute": LXPPacket.e_chg_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Total)", "unique": "lux_total_battery_charge", "bank": 1, "attribute": LXPPacket.e_chg_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Voltage (Live)", "unique": "lux_battery_voltage", "bank": 0, "attribute": LXPPacket.v_bat, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} BMS Limit Charge (Live)", "unique": "lux_bms_limit_charge", "bank": 2, "attribute": LXPPacket.max_chg_curr, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} BMS Limit Discharge (Live)", "unique": "lux_bms_limit_discharge", "bank": 2, "attribute": LXPPacket.max_dischg_curr, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Inverter (Live)", "unique": "lux_power_from_inverter_live", "bank": 0, "attribute": LXPPacket.p_inv, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Inverter (Live)", "unique": "lux_power_to_inverter_live", "bank": 0, "attribute": LXPPacket.p_rec, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Load Power", "unique": "lux_load_power", "bank": 0, "attribute": LXPPacket.p_load, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},

        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} CT Clamp (Live)", "unique": "lux_power_current_clamp", "bank": 0, "attribute": LXPPacket.rms_current, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE},

        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To EPS (Live)", "unique": "lux_power_to_eps", "bank": 0, "attribute": LXPPacket.p_to_eps, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To EPS (Daily)", "unique": "lux_power_to_eps_daily", "bank": 0, "attribute": LXPPacket.e_eps_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To EPS (Total)", "unique": "lux_power_to_eps_total", "bank": 1, "attribute": LXPPacket.e_eps_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Import Power", "unique": "lux_grid_import_power", "bank": 0, "attribute": LXPPacket.p_to_user, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Grid (Daily)", "unique": "lux_power_from_grid_daily", "bank": 0, "attribute": LXPPacket.e_to_user_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power From Grid (Total)", "unique": "lux_power_from_grid_total", "bank": 1, "attribute": LXPPacket.e_to_user_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Export Power", "unique": "lux_grid_export_power", "bank": 0, "attribute": LXPPacket.p_to_grid, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Grid (Daily)", "unique": "lux_power_to_grid_daily", "bank": 0, "attribute": LXPPacket.e_to_grid_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power To Grid (Total)", "unique": "lux_power_to_grid_total", "bank": 1, "attribute": LXPPacket.e_to_grid_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        
        # Energy Dashboard Required Entities
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output (Total)", "unique": "lux_solar_output_total", "bank": 1, "attribute": LXPPacket.e_pv_total, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge (Total)", "unique": "lux_battery_charge_total", "bank": 1, "attribute": LXPPacket.e_chg_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge (Total)", "unique": "lux_battery_discharge_total", "bank": 1, "attribute": LXPPacket.e_dischg_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Frequency (Live) ", "unique": "lux_grid_frequency_live", "bank": 0, "attribute": LXPPacket.f_ac, "device_class": SensorDeviceClass.FREQUENCY, "unit_of_measurement": UnitOfFrequency.HERTZ},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Voltage (Live) ", "unique": "lux_grid_voltage_live", "bank": 0, "attribute": LXPPacket.v_ac_r, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power from Inverter to Home (Daily)", "unique": "lux_power_from_inverter_daily", "bank": 0, "attribute": LXPPacket.e_inv_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power from Inverter to Home (Total)", "unique": "lux_power_from_inverter_total", "bank": 1, "attribute": LXPPacket.e_inv_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power to Inverter (Daily)", "unique": "lux_power_to_inverter_daily", "bank": 0, "attribute": LXPPacket.e_rec_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power to Inverter (Total)", "unique": "lux_power_to_inverter_total", "bank": 1, "attribute": LXPPacket.e_rec_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV Power", "unique": "lux_pv_power", "bank": 0, "attribute": LXPPacket.p_pv_total, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 1 (Live)", "unique": "lux_current_solar_output_1", "bank": 0, "attribute": LXPPacket.p_pv_1, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 2 (Live)", "unique": "lux_current_solar_output_2", "bank": 0, "attribute": LXPPacket.p_pv_2, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 3 (Live)", "unique": "lux_current_solar_output_3", "bank": 0, "attribute": LXPPacket.p_pv_3, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 1 (Live)", "unique": "lux_current_solar_voltage_1", "bank": 0, "attribute": LXPPacket.v_pv_1, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 2 (Live)", "unique": "lux_current_solar_voltage_2", "bank": 0, "attribute": LXPPacket.v_pv_2, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 3 (Live)", "unique": "lux_current_solar_voltage_3", "bank": 0, "attribute": LXPPacket.v_pv_3, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Voltage", "unique": "lux_generator_voltage", "bank": 3, "attribute": LXPPacket.gen_input_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Frequency", "unique": "lux_generator_frequency", "bank": 3, "attribute": LXPPacket.gen_input_freq, "device_class": SensorDeviceClass.FREQUENCY, "unit_of_measurement": UnitOfFrequency.HERTZ, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power", "unique": "lux_generator_power", "bank": 3, "attribute": LXPPacket.gen_power_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Daily)", "unique": "lux_current_generator_power_daily", "bank": 3, "attribute": LXPPacket.gen_power_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power (Total)", "unique": "lux_current_generator_power_all", "bank": 3, "attribute": LXPPacket.gen_power_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power S-Phase (Live)", "unique": "lux_current_generator_power_s", "bank": 3, "attribute": LXPPacket.gen_power_s, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Power T-Phase (Live)", "unique": "lux_current_generator_power_t", "bank": 3, "attribute": LXPPacket.gen_power_t, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Rated Power", "unique": "lux_generator_rated_power", "bank": 4, "attribute": LXPPacket.gen_rated_power, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start Voltage", "unique": "lux_generator_chg_start_volt", "bank": 4, "attribute": LXPPacket.gen_chg_start_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End Voltage", "unique": "lux_generator_chg_end_volt", "bank": 4, "attribute": LXPPacket.gen_chg_end_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start SOC", "unique": "lux_generator_chg_start_soc", "bank": 4, "attribute": LXPPacket.gen_chg_start_soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End SOC", "unique": "lux_generator_chg_end_soc", "bank": 4, "attribute": LXPPacket.gen_chg_end_soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Max Generator Charge Current", "unique": "lux_max_generator_chg_current", "bank": 4, "attribute": LXPPacket.max_gen_chg_current, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Type", "unique": "lux_generator_charge_type", "bank": 3, "attribute": LXPPacket.gen_charge_type, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Input Type", "unique": "lux_ac_input_type", "bank": 3, "attribute": LXPPacket.ac_input_type, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Voltage (Live)", "unique": "lux_current_eps_L1_voltage", "bank": 3, "attribute": LXPPacket.eps_L1_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Voltage (Live)", "unique": "lux_current_eps_L2_voltage", "bank": 3, "attribute": LXPPacket.eps_L2_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Watts (Live)", "unique": "lux_current_eps_L1_watt", "bank": 3, "attribute": LXPPacket.eps_L1_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Watts (Live)", "unique": "lux_current_eps_L2_watt", "bank": 3, "attribute": LXPPacket.eps_L2_watt, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT},
        
        # Split-Phase EPS Additional Sensors
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Apparent Power (Live)", "unique": "lux_current_eps_L1_va", "bank": 3, "attribute": LXPPacket.eps_L1_va, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": "VA", "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Apparent Power (Live)", "unique": "lux_current_eps_L2_va", "bank": 3, "attribute": LXPPacket.eps_L2_va, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": "VA", "state_class": SensorStateClass.MEASUREMENT},
        
        # EPS Power Factor Sensors
        {"etype": "LPPF", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Power Factor", "unique": "lux_current_eps_L1_pf", "bank": 3, "attribute_watts": LXPPacket.eps_L1_watt, "attribute_va": LXPPacket.eps_L1_va, "device_class": SensorDeviceClass.POWER_FACTOR, "unit_of_measurement": None, "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPPF", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Power Factor", "unique": "lux_current_eps_L2_pf", "bank": 3, "attribute_watts": LXPPacket.eps_L2_watt, "attribute_va": LXPPacket.eps_L2_va, "device_class": SensorDeviceClass.POWER_FACTOR, "unit_of_measurement": None, "state_class": SensorStateClass.MEASUREMENT},
        
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Energy (Daily)", "unique": "lux_current_eps_L1_day", "bank": 3, "attribute": LXPPacket.eps_L1_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Energy (Daily)", "unique": "lux_current_eps_L2_day", "bank": 3, "attribute": LXPPacket.eps_L2_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L1 Energy (Total)", "unique": "lux_current_eps_L1_all", "bank": 3, "attribute": LXPPacket.eps_L1_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} EPS L2 Energy (Total)", "unique": "lux_current_eps_L2_all", "bank": 3, "attribute": LXPPacket.eps_L2_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING},
        
        # Smart Load Control Sensors (Registers 181-186)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Start SOC", "unique": "lux_smart_load_start_soc", "bank": 4, "attribute": LXPPacket.smart_load_start_soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load End SOC", "unique": "lux_smart_load_end_soc", "bank": 4, "attribute": LXPPacket.smart_load_end_soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Start Voltage", "unique": "lux_smart_load_start_volt", "bank": 4, "attribute": LXPPacket.smart_load_start_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load End Voltage", "unique": "lux_smart_load_end_volt", "bank": 4, "attribute": LXPPacket.smart_load_end_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load SOC Hysteresis", "unique": "lux_smart_load_soc_hysteresis", "bank": 4, "attribute": LXPPacket.smart_load_soc_hysteresis, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Voltage Hysteresis", "unique": "lux_smart_load_volt_hysteresis", "bank": 4, "attribute": LXPPacket.smart_load_volt_hysteresis, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # Enhanced Peak Shaving Sensors (Registers 206-208)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Power", "unique": "lux_peak_shaving_power", "bank": 4, "attribute": LXPPacket.peak_shaving_power, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving SOC", "unique": "lux_peak_shaving_soc", "bank": 4, "attribute": LXPPacket.peak_shaving_soc, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Voltage", "unique": "lux_peak_shaving_volt", "bank": 4, "attribute": LXPPacket.peak_shaving_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # NEW 2025.03.05 Protocol: PV4-PV6 Support (6-MPPT Systems) - Trip 6-20K Models Only
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 4 (Live)", "unique": "lux_current_solar_voltage_4", "bank": 5, "attribute": LXPPacket.v_pv_4, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 5 (Live)", "unique": "lux_current_solar_voltage_5", "bank": 5, "attribute": LXPPacket.v_pv_5, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Voltage Array 6 (Live)", "unique": "lux_current_solar_voltage_6", "bank": 5, "attribute": LXPPacket.v_pv_6, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 4 (Live)", "unique": "lux_current_solar_output_4", "bank": 5, "attribute": LXPPacket.p_pv_4, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 5 (Live)", "unique": "lux_current_solar_output_5", "bank": 5, "attribute": LXPPacket.p_pv_5, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Output Array 6 (Live)", "unique": "lux_current_solar_output_6", "bank": 5, "attribute": LXPPacket.p_pv_6, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Energy Array 4 (Daily)", "unique": "lux_solar_energy_array_4_daily", "bank": 5, "attribute": LXPPacket.e_pv_4_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Energy Array 5 (Daily)", "unique": "lux_solar_energy_array_5_daily", "bank": 5, "attribute": LXPPacket.e_pv_5_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Energy Array 6 (Daily)", "unique": "lux_solar_energy_array_6_daily", "bank": 5, "attribute": LXPPacket.e_pv_6_day, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Energy Array 4 (Total)", "unique": "lux_solar_energy_array_4_total", "bank": 5, "attribute": LXPPacket.e_pv_4_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Energy Array 5 (Total)", "unique": "lux_solar_energy_array_5_total", "bank": 5, "attribute": LXPPacket.e_pv_5_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Solar Energy Array 6 (Total)", "unique": "lux_solar_energy_array_6_total", "bank": 5, "attribute": LXPPacket.e_pv_6_all, "device_class": SensorDeviceClass.ENERGY, "unit_of_measurement": UnitOfEnergy.KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING, "enabled": False},
        
        # NEW 2025.03.05 Protocol: Enhanced Monitoring
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Remaining Charge Time", "unique": "lux_remaining_charge_time", "bank": 5, "attribute": LXPPacket.remaining_charge_seconds, "device_class": SensorDeviceClass.DURATION, "unit_of_measurement": "s", "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Power", "unique": "lux_smart_load_power", "bank": 5, "attribute": LXPPacket.smart_load_power, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # NEW 2025.03.05 Protocol: Updated Temperature Sensors (Input 214-216)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Internal Temperature (NTC)", "unique": "lux_internal_temperature_ntc", "bank": 5, "attribute": LXPPacket.t_internal_ntc, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} DCDC Temperature Low", "unique": "lux_dcdc_temperature_low", "bank": 5, "attribute": LXPPacket.t_dcdc_low, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} DCDC Temperature High", "unique": "lux_dcdc_temperature_high", "bank": 5, "attribute": LXPPacket.t_dcdc_high, "device_class": SensorDeviceClass.TEMPERATURE, "unit_of_measurement": UnitOfTemperature.CELSIUS, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # NEW 2025.03.05 Protocol: Historical Exception Sensors (Input 176-178)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Exception Reason 1", "unique": "lux_exception_reason_1", "bank": 4, "attribute": LXPPacket.exception_reason_1, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Exception Reason 2", "unique": "lux_exception_reason_2", "bank": 4, "attribute": LXPPacket.exception_reason_2, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Charge Discharge Disable Reason", "unique": "lux_charge_discharge_disable_reason", "bank": 4, "attribute": LXPPacket.charge_discharge_disable_reason, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        
        # AC Coupling Sensors (Registers 220-223)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Coupling Start Voltage", "unique": "lux_ac_couple_start_volt", "bank": 4, "attribute": LXPPacket.ac_couple_start_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Coupling End Voltage", "unique": "lux_ac_couple_end_volt", "bank": 4, "attribute": LXPPacket.ac_couple_end_volt, "device_class": SensorDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # Advanced System Configuration Sensors (Registers 176-180)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Max System Power 12K", "unique": "lux_max_sys_power_12k", "bank": 4, "attribute": LXPPacket.max_sys_power_12k, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} System Configuration 12K", "unique": "lux_sys_config_12k", "bank": 4, "attribute": LXPPacket.sys_config_12k, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Power Limit", "unique": "lux_power_limit", "bank": 4, "attribute": LXPPacket.power_limit, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # Peak Shaving Effectiveness Sensor (Register 282)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Effectiveness", "unique": "lux_peak_shaving_effectiveness", "bank": 5, "attribute": LXPPacket.peak_shaving_effectiveness, "device_class": None, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # Demand Response Capability Sensor (Register 281)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Demand Response Capability", "unique": "lux_demand_response_capability", "bank": 5, "attribute": LXPPacket.demand_response_capability, "device_class": None, "unit_of_measurement": None, "state_class": None, "enabled": False},
        
        # Load Balancing Score Sensor (Register 283)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Load Balancing Score", "unique": "lux_load_balancing_score", "bank": 5, "attribute": LXPPacket.load_balancing_score, "device_class": None, "unit_of_measurement": PERCENTAGE, "state_class": SensorStateClass.MEASUREMENT, "enabled": False},
        
        # AFCI Arc Detection Sensors (Registers 140-152)
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Current Channel 1", "unique": "lux_afci_current_ch1", "bank": 3, "attribute": LXPPacket.afci_curr_ch1, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": "mA", "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Current Channel 2", "unique": "lux_afci_current_ch2", "bank": 3, "attribute": LXPPacket.afci_curr_ch2, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": "mA", "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Current Channel 3", "unique": "lux_afci_current_ch3", "bank": 3, "attribute": LXPPacket.afci_curr_ch3, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": "mA", "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Current Channel 4", "unique": "lux_afci_current_ch4", "bank": 3, "attribute": LXPPacket.afci_curr_ch4, "device_class": SensorDeviceClass.CURRENT, "unit_of_measurement": "mA", "state_class": SensorStateClass.MEASUREMENT},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Flags", "unique": "lux_afci_flags", "bank": 3, "attribute": LXPPacket.afci_flags, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Arc Channel 1", "unique": "lux_afci_arc_ch1", "bank": 3, "attribute": LXPPacket.afci_arc_ch1, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Arc Channel 2", "unique": "lux_afci_arc_ch2", "bank": 3, "attribute": LXPPacket.afci_arc_ch2, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Arc Channel 3", "unique": "lux_afci_arc_ch3", "bank": 3, "attribute": LXPPacket.afci_arc_ch3, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Arc Channel 4", "unique": "lux_afci_arc_ch4", "bank": 3, "attribute": LXPPacket.afci_arc_ch4, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Max Arc Channel 1", "unique": "lux_afci_max_arc_ch1", "bank": 3, "attribute": LXPPacket.afci_max_arc_ch1, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Max Arc Channel 2", "unique": "lux_afci_max_arc_ch2", "bank": 3, "attribute": LXPPacket.afci_max_arc_ch2, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Max Arc Channel 3", "unique": "lux_afci_max_arc_ch3", "bank": 3, "attribute": LXPPacket.afci_max_arc_ch3, "device_class": None, "unit_of_measurement": None, "state_class": None},
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI Max Arc Channel 4", "unique": "lux_afci_max_arc_ch4", "bank": 3, "attribute": LXPPacket.afci_max_arc_ch4, "device_class": None, "unit_of_measurement": None, "state_class": None},
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


        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Status", "unique": "lux_status", "bank": 0, "attribute": LXPPacket.status, "device_class": None, "state_class": None, "unit_of_measurement": None},

        # 4. Setup Data Received Timestamp sensor
        {"etype": "LPDR", "name": "Lux {replaceID_midfix}{hyphen} Data Received Time", "unique": "lux_data_last_received_time", "bank": 0, "attribute": LXPPacket.status, "device_class": None, "state_class": None, "unit_of_measurement": None},

        # 5. Setup State Text sensor
        {"etype": "LPST", "name": "Lux {replaceID_midfix}{hyphen} Status (Text)", "unique": "lux_status_text", "bank": 0, "attribute": LXPPacket.status, "device_class": None, "state_class": None, "unit_of_measurement": None},

        # Multiple Attribute Calculated Sensors
        # 6. Battery Flow Live
        {"etype": "LPBF", "name": "Lux {replaceID_midfix}{hyphen} Battery Flow (Live)", "unique": "lux_battery_flow", "bank": 0, "attribute": LXPPacket.p_discharge, "attribute1": LXPPacket.p_discharge, "attribute2": LXPPacket.p_charge, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT},

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

        # 11. Load Percentage Sensor (calculated using rated power)
        {
            "etype": "LPLS", "name": "Lux {replaceID_midfix}{hyphen} Load Percentage", "unique": "lux_load_percentage",
            "bank": 0, "attribute": LXPPacket.p_load, "rated_power_key": "rated_power",
            "device_class": SensorDeviceClass.POWER_FACTOR, "unit_of_measurement": PERCENTAGE,
            "state_class": SensorStateClass.MEASUREMENT, "enabled": True
        },

        # 12. Adaptive Polling Sensors
        {"etype": "LPAP", "name": "Lux {replaceID_midfix}{hyphen} Polling Interval", "unique": "lux_polling_interval", "bank": 0, "attribute": LXPPacket.status, "device_class": SensorDeviceClass.DURATION, "unit_of_measurement": "s", "enabled": True},
        {"etype": "LPAC", "name": "Lux {replaceID_midfix}{hyphen} Connection Quality", "unique": "lux_connection_quality", "bank": 0, "attribute": LXPPacket.status, "device_class": None, "unit_of_measurement": PERCENTAGE, "enabled": True},

        # 13. Calculated Power Flow Sensors
        {"etype": "LPSC", "name": "Lux {replaceID_midfix}{hyphen} Self Consumption Rate", "unique": "lux_self_consumption_rate", "bank": 0, "attribute": LXPPacket.p_load, "attribute1": LXPPacket.p_to_user, "attribute2": LXPPacket.p_to_grid, "device_class": SensorDeviceClass.POWER_FACTOR, "unit_of_measurement": PERCENTAGE, "enabled": True},
        {"etype": "LPSG", "name": "Lux {replaceID_midfix}{hyphen} Grid Dependency", "unique": "lux_grid_dependency", "bank": 0, "attribute": LXPPacket.p_to_user, "attribute1": LXPPacket.p_load, "device_class": SensorDeviceClass.POWER_FACTOR, "unit_of_measurement": PERCENTAGE, "enabled": True},

        # 14. Read-Only Mode Sensors (only created when read_only_mode is True)
        # These are read-only versions of control entities for monitoring only
        *([
            # Number entities as sensors
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Charge Power Rate", "unique": "lux_system_charge_power_rate", "bank": 0, "register": 64, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": read_only_mode},
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Discharge Power Rate", "unique": "lux_system_discharge_power_rate", "bank": 0, "register": 65, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": read_only_mode},
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Power Rate", "unique": "lux_ac_charge_power_rate", "bank": 0, "register": 66, "device_class": SensorDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": read_only_mode},
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Battery Charge Level", "unique": "lux_ac_battery_charge_level", "bank": 0, "register": 67, "device_class": SensorDeviceClass.BATTERY, "unit_of_measurement": PERCENTAGE, "enabled": read_only_mode},
            # Add more number entities as needed...

            # Switch entities as binary sensors
            {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Normal/Standby", "unique": "lux_normal_standby", "bank": 0, "register": 21, "bitmask": LXPPacket.SET_TO_STANDBY, "enabled": read_only_mode},
            {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Power Backup Enable", "unique": "lux_power_backup_enable", "bank": 0, "register": 21, "bitmask": LXPPacket.POWER_BACKUP_ENABLE, "enabled": read_only_mode},
            {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Feed-In Grid", "unique": "lux_feed_in_grid", "bank": 0, "register": 21, "bitmask": LXPPacket.FEED_IN_GRID, "enabled": read_only_mode},
            # Add more switch entities as needed...

            # Time entities as sensors (simplified - just show current values)
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start Time 1", "unique": "lux_ac_charge_start_time_1", "bank": 0, "register": 68, "device_class": None, "unit_of_measurement": None, "enabled": read_only_mode},
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End Time 1", "unique": "lux_ac_charge_end_time_1", "bank": 0, "register": 69, "device_class": None, "unit_of_measurement": None, "enabled": read_only_mode},
            # Add more time entities as needed...

            # Select entities as sensors (show current selected option)
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Type", "unique": "lux_ac_charge_type_readonly", "bank": 0, "register": 20, "device_class": None, "unit_of_measurement": None, "enabled": read_only_mode},
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Output Priority", "unique": "lux_output_priority_readonly", "bank": 0, "register": 21, "device_class": None, "unit_of_measurement": None, "enabled": read_only_mode},
            {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Work Mode", "unique": "lux_work_mode_readonly", "bank": 0, "register": 110, "device_class": None, "unit_of_measurement": None, "enabled": read_only_mode},
        ] if read_only_mode else []),

        # 15. Test Sensor
        # {"etype": "LPTS", "name": "Lux {replaceID_midfix}{hyphen} Testing", "unique": "lux_testing", "bank": 0, "register": 5},

        # Configuration Diagnostic Sensors (Disabled by Default)
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Capacity (Ah)", "unique": "lux_battery_capacity_ah_register", "bank": 0, "register": 38, "enabled": False},
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
        # Smart Load Control (12K-specific registers 181-186) - Using LPSE entities instead of LPRS
        
        # Enhanced Peak Shaving (12K-specific registers 206-208) - Using LPSE entities instead of LPRS

        # AC Coupling (12K-specific registers 220-223) - Using LPSE entities instead of LPRS

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
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Fault Code", "unique": "lux_fault_code_register", "bank": 0, "register": 0, "enabled": False},  # Current fault code
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Warning Code", "unique": "lux_warning_code_register", "bank": 0, "register": 0, "enabled": False},  # Current warning code
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} System Status Code", "unique": "lux_system_status", "bank": 0, "register": 0, "enabled": False},  # System status code
        
        # Power Flow Sensors (calculated from existing power values) - Register 0 valid for system status
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV to Battery Power", "unique": "lux_pv_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # PV charging battery
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV to Load Power", "unique": "lux_pv_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # PV directly powering loads
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} PV to Grid Power", "unique": "lux_pv_to_grid", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # PV exporting to grid
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery to Load Power", "unique": "lux_battery_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Battery discharging to loads
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Battery to Grid Power", "unique": "lux_battery_to_grid", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Battery exporting to grid
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid to Battery Power", "unique": "lux_grid_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Grid charging battery
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Grid to Load Power", "unique": "lux_grid_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Grid powering loads
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator to Battery Power", "unique": "lux_generator_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Generator charging
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} Generator to Load Power", "unique": "lux_generator_to_load", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # Generator powering loads
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple to Battery Power", "unique": "lux_ac_couple_to_battery", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # AC-coupled PV to battery
        {"etype": "LPSE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple to Grid Power", "unique": "lux_ac_couple_to_grid", "bank": 0, "register": 0, "enabled": False, "calculated": True},  # AC-coupled PV to grid
        
        # Battery Management System (BMS) Integration - Register 0 valid for system status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} BMS Status", "unique": "lux_bms_status", "bank": 0, "register": 0, "enabled": False},  # BMS communication status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} BMS Fault Code", "unique": "lux_bms_fault", "bank": 0, "register": 0, "enabled": False},  # BMS fault code
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Health %", "unique": "lux_battery_health", "bank": 0, "register": 0, "enabled": False},  # Battery health percentage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Battery Cycle Count", "unique": "lux_battery_cycles", "bank": 0, "register": 0, "enabled": False},  # Battery cycle count
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Voltage Max", "unique": "lux_cell_voltage_max", "bank": 0, "register": 0, "enabled": False},  # Maximum cell voltage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Voltage Min", "unique": "lux_cell_voltage_min", "bank": 0, "register": 0, "enabled": False},  # Minimum cell voltage
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Temperature Max", "unique": "lux_cell_temp_max", "bank": 0, "register": 0, "enabled": False},  # Maximum cell temperature
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Cell Temperature Min", "unique": "lux_cell_temp_min", "bank": 0, "register": 0, "enabled": False},  # Minimum cell temperature
        
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
        
        # Integration Health Diagnostics
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Integration Status", "unique": "lux_integration_status", "bank": 0, "register": 0, "enabled": False},  # Integration health status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Model Detection Status", "unique": "lux_model_detection_status", "bank": 0, "register": 0, "enabled": False},  # Model detection status
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Register Read Success Rate", "unique": "lux_register_success_rate", "bank": 0, "register": 0, "enabled": False},  # Register read success rate
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Error Count", "unique": "lux_error_count", "bank": 0, "register": 0, "enabled": False},  # Total error count
        {"etype": "LPRS", "name": "Lux {replaceID_midfix}{hyphen} Last Error", "unique": "lux_last_error", "bank": 0, "register": 0, "enabled": False},  # Last error message
        
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
            sensorEntities.append(LuxStateSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPSE":
            sensorEntities.append(LuxPowerSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPRS":
            sensorEntities.append(LuxPowerRegisterSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPFW":
            sensorEntities.append(LuxPowerFirmwareSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPMD":
            sensorEntities.append(LuxPowerModelSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPSN":
            _LOGGER.debug(f"🔍 CREATING SERIAL SENSOR - Adding LuxPowerSerialNumberSensor for dongle: {DONGLE}")
            sensorEntities.append(LuxPowerSerialNumberSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPDR":
            sensorEntities.append(LuxPowerDataReceivedTimestampSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPST":
            sensorEntities.append(LuxPowerStatusTextSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPFS":
            sensorEntities.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPBF":
            sensorEntities.append(LuxPowerBatteryFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPHC":
            sensorEntities.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPPF":
            sensorEntities.append(LuxPowerFactorSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPBS":
            sensorEntities.append(LuxPowerBatteryStatusSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPLS":
            sensorEntities.append(LuxPowerLoadPercentageSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPAP":
            sensorEntities.append(LuxPowerAdaptivePollingSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPAC":
            sensorEntities.append(LuxPowerConnectionQualitySensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPSC":
            sensorEntities.append(LuxPowerSelfConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPSG":
            sensorEntities.append(LuxPowerGridDependencySensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))
        elif etype == "LPTS":
            sensorEntities.append(LuxPowerTestSensor(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event, model_code))

    # fmt: on

    _LOGGER.debug(f"🔍 ADDING SENSORS - Adding {len(sensorEntities)} sensors to platform")
    async_add_devices(sensorEntities, True)

    _LOGGER.info("LuxPower sensor async_setup_platform sensor done %s", DONGLE)


class LuxPowerSensorEntity(SensorEntity):
    """Representation of a general numeric LUXpower sensor."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
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
        # Set device class before using it
        self._attr_device_class = entity_definition.get("device_class", None)
        self._attr_state_class = entity_definition.get("state_class", None)
        self._attr_native_unit_of_measurement = entity_definition.get(
            "unit_of_measurement", None
        )
        self._attr_should_poll = False
        
        # Initialize with appropriate unavailable value based on device class
        if self._attr_device_class in [
            SensorDeviceClass.ENERGY,
            SensorDeviceClass.POWER,
            SensorDeviceClass.CURRENT,
            SensorDeviceClass.VOLTAGE,
            SensorDeviceClass.FREQUENCY,
            SensorDeviceClass.TEMPERATURE,
            SensorDeviceClass.BATTERY,
            SensorDeviceClass.POWER_FACTOR,
        ]:
            self._attr_native_value = None
        else:
            self._attr_native_value = "Unavailable"
        self._attr_available = False
        
        # Apply model-based enablement logic
        default_enabled = entity_definition.get("enabled", True)
        
        # Always enable main power sensors regardless of model
        main_power_attributes = {
            LXPPacket.p_discharge, LXPPacket.p_charge, LXPPacket.soc,
            LXPPacket.p_pv_total, LXPPacket.p_to_grid, LXPPacket.p_load,
            LXPPacket.e_dischg_day, LXPPacket.e_chg_day, LXPPacket.e_pv_total
        }
        
        # Always enable energy dashboard sensors regardless of model
        energy_dashboard_attributes = {
            LXPPacket.e_dischg_day, LXPPacket.e_chg_day, LXPPacket.e_pv_total,
            LXPPacket.e_to_grid_day, LXPPacket.e_to_user_day, LXPPacket.e_eps_day,
            LXPPacket.e_inv_day, LXPPacket.e_rec_day, LXPPacket.e_pv_1_day,
            LXPPacket.e_pv_2_day, LXPPacket.e_pv_3_day, LXPPacket.gen_power_day
        }
        
        if model_code:
            is_12k = is_12k_model(model_code)
            attribute = entity_definition.get("attribute", "")
            
            # Always enable main power sensors and energy dashboard sensors
            if attribute in main_power_attributes or attribute in energy_dashboard_attributes:
                default_enabled = True
                _LOGGER.debug(f"Force enabling essential sensor: {entity_definition['name']} (attribute: {attribute})")
            # Check if this is a 12K-specific sensor
            elif attribute in TWELVE_K_ATTRIBUTES:
                # Enable only for 12K models
                default_enabled = is_12k
                if is_12k:
                    _LOGGER.debug(f"Enabling 12K-specific sensor: {entity_definition['name']}")
                else:
                    _LOGGER.debug(f"Disabling 12K-specific sensor for non-12K model: {entity_definition['name']}")
        else:
            # If no model code, ensure main power sensors and energy dashboard sensors are enabled
            attribute = entity_definition.get("attribute", "")
            if attribute in main_power_attributes or attribute in energy_dashboard_attributes:
                default_enabled = True
                _LOGGER.debug(f"Force enabling essential sensor (no model): {entity_definition['name']} (attribute: {attribute})")
        
        self._attr_entity_registry_enabled_default = default_enabled

        # Hidden Class Extended Instance Attributes
        self._entity_definition = entity_definition
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
        
        # Debug logging for main power sensors
        if self._device_attribute in [LXPPacket.p_discharge, LXPPacket.p_charge, LXPPacket.soc]:
            _LOGGER.info(f"Power sensor update: {self._attr_name} - Data: {self._data.get(self._device_attribute, 'None')}")
        
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
            self._attr_native_value = value
            self._attr_available = True
        else:
            # For numeric sensors, set to None instead of "unavailable" string
            # to avoid Home Assistant validation errors
            if self._attr_device_class in [
                SensorDeviceClass.ENERGY,
                SensorDeviceClass.POWER,
                SensorDeviceClass.CURRENT,
                SensorDeviceClass.VOLTAGE,
                SensorDeviceClass.FREQUENCY,
                SensorDeviceClass.TEMPERATURE,
                SensorDeviceClass.BATTERY,
                SensorDeviceClass.POWER_FACTOR,
            ]:
                self._attr_native_value = None
            else:
                value = UA
                self._attr_native_value = f"{value}"
            self._attr_available = False
        self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        _LOGGER.warning(f"Sensor: gone_unavailable event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._attr_available = False

        # For numeric sensors, set value to None instead of keeping old value
        # to avoid Home Assistant validation errors
        if self._attr_device_class in [
            SensorDeviceClass.ENERGY,
            SensorDeviceClass.POWER,
            SensorDeviceClass.CURRENT,
            SensorDeviceClass.VOLTAGE,
            SensorDeviceClass.FREQUENCY,
            SensorDeviceClass.TEMPERATURE,
            SensorDeviceClass.BATTERY,
            SensorDeviceClass.POWER_FACTOR,
        ]:
            self._attr_native_value = None

        self.schedule_update_ha_state()

    @property
    def entity_category(self):
        """Return entity category."""
        # Diagnostic entities for status and connection info
        if self._device_attribute in [
            "lux_battery_status", "lux_status", "lux_data_last_received_time", 
            "lux_status_text", "lux_system_status", "lux_bms_status", 
            "lux_charge_mode", "lux_eps_status", "lux_parallel_comm_status",
            "lux_battery_wear_level", "lux_zero_export_mode", "lux_reactive_power_control",
            "lux_peak_schedule_active", "lux_integration_status", "lux_model_detection_status",
            "lux_firmware_update_status", "lux_parallel_system_status", "lux_parallel_system_sync",
            "lux_parallel_system_comm", "lux_battery_firmware_update_status",
            "lux_battery_firmware_compatibility", "lux_battery_firmware_validation"
        ]:
            return EntityCategory.DIAGNOSTIC
        return None

    @property
    def suggested_display_precision(self) -> int:
        """Return suggested decimal places."""
        if self._attr_native_unit_of_measurement in ["W", "kW", "V", "A"]:
            return 1
        elif self._attr_native_unit_of_measurement == "%":
            return 0
        elif self._attr_native_unit_of_measurement is None:
            return None  # No precision for text sensors
        return 2

    @property
    def available(self) -> bool:
        """Return if entity is available based on connection state."""
        # Get the client from hass data
        try:
            for entry_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    client = data.get("client")
                    if client and hasattr(client, '_connected'):
                        return client._connected
            return False
        except Exception:
            return False

    @property
    def device_info(self):
        """Return device info for the appropriate device group."""
        # Get the device group for this entity
        device_group = get_entity_device_group(self._entity_definition, self.hass)
        
        # Return device group info if available, otherwise fall back to main device
        if device_group:
            return get_device_group_info(self.hass, self.dongle, device_group)
        else:
            return get_comprehensive_device_info(self.hass, self.dongle, self.serial)


class LuxPowerFlowSensor(LuxPowerSensorEntity):
    """
    Representation of a Numeric LUXpower Flow sensor.

    Template equation state = -1*attribute1 if attribute1 > 0 else attribute2
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._device_attribute1 = entity_definition["attribute1"]
        self._device_attribute2 = entity_definition["attribute2"]

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})

        # For grid flow: attribute1 = p_to_user (grid to user), attribute2 = p_to_grid (user to grid)
        grid_to_user = float(self._data.get(self._device_attribute1, 0.0))  # Power from grid to user
        user_to_grid = float(self._data.get(self._device_attribute2, 0.0))   # Power from user to grid
        
        # Debug logging for grid flow values
        _LOGGER.debug(f"Grid Flow Debug - grid_to_user: {grid_to_user}, user_to_grid: {user_to_grid}")
        
        # Calculate net grid flow: positive = importing from grid, negative = exporting to grid
        if grid_to_user > 0:
            # We're importing from grid
            flow_value = grid_to_user
        elif user_to_grid > 0:
            # We're exporting to grid
            flow_value = -user_to_grid
        else:
            # No grid flow
            flow_value = 0.0
            
        self._attr_native_value = f"{round(flow_value, 1)}"

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerBatteryFlowSensor(LuxPowerSensorEntity):
    """
    Representation of a Battery Flow sensor for a LUXPower Inverter.
    
    Handles positive and negative power flow correctly:
    - Positive = Battery discharging (power flowing out of battery)
    - Negative = Battery charging (power flowing into battery)
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._device_attribute1 = entity_definition["attribute1"]  # p_discharge
        self._device_attribute2 = entity_definition["attribute2"]  # p_charge

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})

        # For battery flow: attribute1 = p_discharge, attribute2 = p_charge
        battery_discharge = float(self._data.get(self._device_attribute1, 0.0))  # Power discharging from battery
        battery_charge = float(self._data.get(self._device_attribute2, 0.0))     # Power charging battery
        
        # Debug logging for battery flow values
        _LOGGER.debug(f"Battery Flow Debug - discharge: {battery_discharge}, charge: {battery_charge}")
        
        # Calculate net battery flow: positive = discharging, negative = charging
        if battery_discharge > 0:
            # Battery is discharging
            flow_value = battery_discharge
        elif battery_charge > 0:
            # Battery is charging
            flow_value = -battery_charge
        else:
            # No battery flow
            flow_value = 0.0
            
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
        self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
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
        
        # Debug logging for power values
        _LOGGER.debug(f"Home Consumption Debug - grid: {grid}, to_inverter: {to_inverter}, from_inverter: {from_inverter}, to_grid: {to_grid}")
        
        consumption_value = grid - to_inverter + from_inverter - to_grid
        self._attr_native_value = f"{round(consumption_value, 1)}"

        self._attr_available = True
        self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerFactorSensor(LuxPowerSensorEntity):
    """
    Used for power factor calculation.
    
    Template equation power_factor = watts / va
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._device_attribute_watts = entity_definition["attribute_watts"]
        self._device_attribute_va = entity_definition["attribute_va"]

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip
        self._data = event.data.get("data", {})

        watts = float(self._data.get(self._device_attribute_watts, 0.0))
        va = float(self._data.get(self._device_attribute_va, 0.0))
        
        # Handle division by zero and invalid values
        if va == 0.0 or watts is None or va is None:
            self._attr_native_value = None
            self._attr_available = False
        else:
            power_factor = watts / va
            # Clamp power factor between 0 and 1
            power_factor = max(0.0, min(1.0, power_factor))
            self._attr_native_value = f"{round(power_factor, 2)}"
            self._attr_available = True

        self.schedule_update_ha_state()
        return self._attr_native_value


class LuxPowerRegisterSensor(LuxPowerSensorEntity):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
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


class LuxPowerFirmwareSensor(LuxPowerSensorEntity):
    """
    Used for firmware version display.
    """

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._register_address = entity_definition.get("register", 7)
        self._bank = entity_definition.get("bank", 0)
        
        # Set initial value to indicate waiting for data
        self._attr_native_value = "Waiting for data..."
        self._attr_available = False

    @property
    def suggested_display_precision(self) -> Optional[int]:
        """Return suggested decimal places for firmware version (text sensor)."""
        return None  # No precision for text values
    
    @property
    def native_value(self) -> Optional[str]:
        """Return the native value as string for text sensors."""
        return self._attr_native_value

    async def async_added_to_hass(self) -> None:
        """Register event listeners for firmware sensor."""
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass firmware sensor %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(
                self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable
            )
            # Listen to BANK0 events since registers 7, 8, 9, 10 are in bank 0
            self.hass.bus.async_listen(
                self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update
            )
            _LOGGER.info(f"Firmware sensor {self._attr_name} listening to EVENT_REGISTER_BANK0_RECEIVED")

    def push_update(self, event):
        _LOGGER.debug(f"Sensor: register event received Bank: {self._bank} FIRMWARE Register: {self._register_address} Name: {self._attr_name}")  # fmt: skip
        registers = event.data.get("registers", {})
        self._data = registers
        
        # Debug logging for firmware sensor
        _LOGGER.debug(f"🔍 FIRMWARE SENSOR DEBUG - Available registers: {list(registers.keys())}")
        _LOGGER.debug(f"🔍 FIRMWARE SENSOR DEBUG - Register 7: {registers.get(7)}, Register 8: {registers.get(8)}, Register 9: {registers.get(9)}, Register 10: {registers.get(10)}")

        # Check if we have all required registers for firmware version
        required_registers = [7, 8, 9, 10]
        if all(reg in registers.keys() for reg in required_registers):
            _LOGGER.debug(
                f"All required firmware registers found: {required_registers}"
            )
            reg07_val = registers.get(7, None)
            reg08_val = registers.get(8, None)
            reg09_val = registers.get(9, None)
            reg10_val = registers.get(10, None)
            
            # Check if all values are valid
            if any(val is None for val in [reg07_val, reg08_val, reg09_val, reg10_val]):
                _LOGGER.debug(
                    f"ABORTING: Missing firmware register values - reg07: {reg07_val}, reg08: {reg08_val}, reg09: {reg09_val}, reg10: {reg10_val}"
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
                # Check if firmware version has changed
                current_firmware = self.hass.data[DOMAIN].get(entry_id, {}).get("lux_firmware_version")
                if current_firmware != firmware:
                    self.hass.data[DOMAIN].setdefault(entry_id, {})[
                        "lux_firmware_version"
                    ] = firmware
                    _LOGGER.info(f"🔍 FIRMWARE SENSOR - Firmware changed from '{current_firmware}' to '{firmware}', updating device info")
                    
                    # Trigger device info update only if firmware changed
                    self._update_device_info()
                    
                    # Also update device groups with new firmware info
                    from .helpers import update_device_info_when_available
                    update_device_info_when_available(self.hass, self.dongle)
                else:
                    _LOGGER.debug(f"🔍 FIRMWARE SENSOR - Firmware unchanged: '{firmware}'")
            else:
                _LOGGER.debug(f"🔍 FIRMWARE SENSOR - No entry_id found for dongle {self.dongle}")
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.info(f"Firmware sensor updated: {oldstate} -> {self._attr_native_value}")
                self.schedule_update_ha_state()
        else:
            # Log when firmware registers are not available
            missing_registers = [reg for reg in required_registers if reg not in registers.keys()]
            if missing_registers:
                _LOGGER.debug(f"Firmware sensor: Missing registers {missing_registers}, available: {list(registers.keys())}")
            else:
                _LOGGER.debug(f"Firmware sensor: All registers present but values are None")
            
            # Set fallback value if no firmware data is available
            if self._attr_native_value == "Waiting for data..." or self._attr_native_value == "Unavailable":
                self._attr_native_value = "Firmware data not available"
                self._attr_available = True
                _LOGGER.warning(f"Firmware sensor: No firmware data available, setting fallback value")
                self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        """Handle unavailable event for firmware sensor."""
        _LOGGER.debug(f"🔍 FIRMWARE SENSOR - gone_unavailable event received for {self._attr_name}")
        _LOGGER.debug(f"🔍 FIRMWARE SENSOR - Current value before unavailable: '{self._attr_native_value}'")
        # Don't set unavailable - keep the last known firmware version
        # self._attr_available = False
        # self.schedule_update_ha_state()

    def _update_device_info(self):
        """Update device info in the device registry."""
        try:
            from homeassistant.helpers import device_registry as dr
            from .const import DEVICE_GROUP_PV, DEVICE_GROUP_GRID, DEVICE_GROUP_EPS, DEVICE_GROUP_GENERATOR, DEVICE_GROUP_BATTERY, DEVICE_GROUP_INVERTER, DEVICE_GROUP_TEMPERATURES, DEVICE_GROUP_SETTINGS
            
            # Get the device registry
            device_registry = dr.async_get(self.hass)
            
            # Device groups to update
            device_groups = [
                (DEVICE_GROUP_PV, f"{self.dongle}_pv"),
                (DEVICE_GROUP_GRID, f"{self.dongle}_grid"),
                (DEVICE_GROUP_EPS, f"{self.dongle}_eps"),
                (DEVICE_GROUP_GENERATOR, f"{self.dongle}_generator"),
                (DEVICE_GROUP_BATTERY, f"{self.dongle}_battery"),
                (DEVICE_GROUP_INVERTER, f"{self.dongle}_inverter"),
                (DEVICE_GROUP_TEMPERATURES, f"{self.dongle}_temperatures"),
                (DEVICE_GROUP_SETTINGS, f"{self.dongle}_settings"),
            ]
            
            # Find main device and all device groups
            main_device = device_registry.async_get_device(identifiers={(DOMAIN, self.dongle)})
            group_devices = {}
            
            for group_name, group_id in device_groups:
                device = device_registry.async_get_device(identifiers={(DOMAIN, group_id)})
                if device:
                    group_devices[group_name] = device
            
            # Schedule device info update to run in event loop
            def update_device_info():
                try:
                    # Update main device
                    if main_device:
                        device_registry.async_update_device(
                            main_device.id,
                            sw_version=self._attr_native_value
                        )
                        _LOGGER.info(f"🔍 DEVICE INFO UPDATE - Updated main device firmware to: {self._attr_native_value}")
                    
                    # Update all device groups
                    for group_name, device in group_devices.items():
                        device_registry.async_update_device(
                            device.id,
                            sw_version=self._attr_native_value
                        )
                        _LOGGER.info(f"🔍 DEVICE INFO UPDATE - Updated {group_name} device firmware to: {self._attr_native_value}")
                        
                except Exception as e:
                    _LOGGER.error(f"🔍 DEVICE INFO UPDATE - Failed to update device info: {e}")
            
            # Schedule the update to run in the event loop
            self.hass.loop.call_soon_threadsafe(update_device_info)
                
        except Exception as e:
            _LOGGER.error(f"🔍 DEVICE INFO UPDATE - Failed to update device info: {e}")


class LuxPowerModelSensor(LuxPowerSensorEntity):
    """Sensor that exposes the inverter model based on firmware code."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._register_address = entity_definition.get("register", [7, 8])
        self._bank = entity_definition.get("bank", 0)
        
        # Set initial value to indicate waiting for data
        self._attr_native_value = "Waiting for data..."
        self._attr_available = False
        
        # Try to get existing model from hass.data on initialization
        _LOGGER.debug(f"🔍 MODEL SENSOR - Creating model sensor for dongle: {self.dongle}")
        self._load_existing_model()

    def _load_existing_model(self):
        """Load existing model from hass.data on initialization."""
        try:
            entry_id = None
            for e_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    entry_id = e_id
                    break
            
            if entry_id is not None:
                existing_model = self.hass.data[DOMAIN].get(entry_id, {}).get("model")
                if existing_model and existing_model != "LuxPower Inverter":
                    self._attr_native_value = existing_model
                    self._attr_available = True
                    _LOGGER.debug(f"🔍 MODEL SENSOR - Loaded existing model: '{existing_model}'")
                    # Trigger device info update on initialization
                    self._update_device_info()
        except Exception as e:
            _LOGGER.error(f"🔍 MODEL SENSOR - Failed to load existing model: {e}")

    @property
    def suggested_display_precision(self) -> Optional[int]:
        """Return suggested decimal places for model sensor (text sensor)."""
        return None  # No precision for text values
    
    @property
    def native_value(self) -> Optional[str]:
        """Return the native value as string for text sensors."""
        return self._attr_native_value

    async def async_added_to_hass(self) -> None:
        """Register event listeners for model sensor."""
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass model sensor %s", self._attr_name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(
                self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable
            )
            # Listen to BANK0 events since registers 7, 8 are in bank 0
            self.hass.bus.async_listen(
                self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update
            )
            _LOGGER.info(f"Model sensor {self._attr_name} listening to EVENT_REGISTER_BANK0_RECEIVED")

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} MODEL Register: {self._register_address} Name: {self._attr_name}"
        )
        registers = event.data.get("registers", {})
        self._data = registers
        
        # Debug logging for model sensor
        _LOGGER.debug(f"🔍 MODEL SENSOR DEBUG - Available registers: {list(registers.keys())}")
        _LOGGER.debug(f"🔍 MODEL SENSOR DEBUG - Register 7: {registers.get(7)}, Register 8: {registers.get(8)}")

        # Check if we have both required registers for model detection
        required_registers = [7, 8]
        if all(reg in registers.keys() for reg in required_registers):
            _LOGGER.debug(f"All required model registers found: {required_registers}")
            reg07_val = registers.get(7, None)
            reg08_val = registers.get(8, None)
            
            # Check if all values are valid and not None
            if any(val is None for val in [reg07_val, reg08_val]):
                _LOGGER.debug(f"ABORTING: Missing model register values - reg07: {reg07_val}, reg08: {reg08_val}")
                return
            
            # Additional validation: check if values are reasonable (not 0 or negative)
            if any(val <= 0 for val in [reg07_val, reg08_val]):
                _LOGGER.debug(f"ABORTING: Invalid model register values - reg07: {reg07_val}, reg08: {reg08_val}")
                return
            reg07_str = int(reg07_val).to_bytes(2, "little").decode()
            reg08_str = int(reg08_val).to_bytes(2, "little").decode()
            code = reg07_str + reg08_str
            model_code = code.upper()
            model_info = MODEL_MAP.get(model_code, {"name": "Unknown", "rated_power": 6000})
            model = model_info.get("name", "Unknown") if isinstance(model_info, dict) else str(model_info)

            # Save both model and model_code into hass.data for device_info usage
            entry_id = None
            for e_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    entry_id = e_id
                    break
            if entry_id is not None:
                # Check if model has changed
                current_model = self.hass.data[DOMAIN].get(entry_id, {}).get("model")
                current_model_code = self.hass.data[DOMAIN].get(entry_id, {}).get("model_code")
                if current_model != model or current_model_code != model_code:
                    self.hass.data[DOMAIN].setdefault(entry_id, {})["model"] = model
                    self.hass.data[DOMAIN][entry_id]["model_code"] = model_code
                    self.hass.data[DOMAIN][entry_id]["model_info"] = model_info
                    _LOGGER.debug(f"🔍 MODEL SENSOR - Saved model '{model}' and model_code '{model_code}' to hass.data[{DOMAIN}][{entry_id}]")
                    
                    # Trigger device info update only if model changed
                    self._update_device_info()
                    
                    # Also update device groups with new model info
                    from .helpers import update_device_info_when_available
                    update_device_info_when_available(self.hass, self.dongle)
                else:
                    _LOGGER.debug(f"🔍 MODEL SENSOR - Model unchanged: '{model}' (code: '{model_code}')")
            else:
                _LOGGER.debug(f"🔍 MODEL SENSOR - No entry_id found for dongle {self.dongle}")
                
                # Also persist to config entry options for restart persistence
                config_entry = self.hass.config_entries.async_get_entry(entry_id)
                if config_entry and "model_code" not in config_entry.options:
                    new_options = dict(config_entry.options)
                    new_options["model_code"] = model_code
                    # Schedule the async call to run in the event loop
                    self.hass.async_create_task(
                        self.hass.config_entries.async_update_entry(config_entry, options=new_options)
                    )
                    _LOGGER.info(f"Cached model code {model_code} to config entry for persistence")

            oldstate = self._attr_native_value
            self._attr_native_value = model
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.info(f"Model sensor updated: {oldstate} -> {self._attr_native_value}")
                _LOGGER.info(f"Model sensor debug - model: {model}, model_code: {model_code}, type: {type(model)}")
                self.schedule_update_ha_state()
        else:
            # Log when model registers are not available
            missing_registers = [reg for reg in required_registers if reg not in registers.keys()]
            if missing_registers:
                _LOGGER.debug(f"Model sensor: Missing registers {missing_registers}, available: {list(registers.keys())}")
            else:
                _LOGGER.debug(f"Model sensor: All registers present but values are None")
            
            # Set fallback value if no model data is available
            if self._attr_native_value in ["Unavailable", "Unknown", "Waiting for data..."]:
                self._attr_native_value = "Model detection pending"
                self._attr_available = True
                _LOGGER.warning(f"Model sensor: No model data available, setting fallback value")
                self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        """Handle unavailable event for model sensor."""
        _LOGGER.debug(f"🔍 MODEL SENSOR - gone_unavailable event received for {self._attr_name}")
        _LOGGER.debug(f"🔍 MODEL SENSOR - Current value before unavailable: '{self._attr_native_value}'")
        # Don't set unavailable - keep the last known model
        # self._attr_available = False
        # self.schedule_update_ha_state()

    def _update_device_info(self):
        """Update device info in the device registry."""
        try:
            from homeassistant.helpers import device_registry as dr
            from .const import DEVICE_GROUP_PV, DEVICE_GROUP_GRID, DEVICE_GROUP_EPS, DEVICE_GROUP_GENERATOR, DEVICE_GROUP_BATTERY, DEVICE_GROUP_INVERTER, DEVICE_GROUP_TEMPERATURES, DEVICE_GROUP_SETTINGS
            
            # Get the device registry
            device_registry = dr.async_get(self.hass)
            
            # Device groups to update
            device_groups = [
                (DEVICE_GROUP_PV, f"{self.dongle}_pv"),
                (DEVICE_GROUP_GRID, f"{self.dongle}_grid"),
                (DEVICE_GROUP_EPS, f"{self.dongle}_eps"),
                (DEVICE_GROUP_GENERATOR, f"{self.dongle}_generator"),
                (DEVICE_GROUP_BATTERY, f"{self.dongle}_battery"),
                (DEVICE_GROUP_INVERTER, f"{self.dongle}_inverter"),
                (DEVICE_GROUP_TEMPERATURES, f"{self.dongle}_temperatures"),
                (DEVICE_GROUP_SETTINGS, f"{self.dongle}_settings"),
            ]
            
            # Find main device and all device groups
            main_device = device_registry.async_get_device(identifiers={(DOMAIN, self.dongle)})
            group_devices = {}
            
            for group_name, group_id in device_groups:
                device = device_registry.async_get_device(identifiers={(DOMAIN, group_id)})
                if device:
                    group_devices[group_name] = device
            
            # Schedule device info update to run in event loop
            def update_device_info():
                try:
                    # Update main device
                    if main_device:
                        device_registry.async_update_device(
                            main_device.id,
                            model=self._attr_native_value,
                            hw_version=self._attr_native_value
                        )
                        _LOGGER.info(f"🔍 DEVICE INFO UPDATE - Updated main device model to: {self._attr_native_value}")
                    
                    # Update all device groups
                    for group_name, device in group_devices.items():
                        device_registry.async_update_device(
                            device.id,
                            model=self._attr_native_value,
                            hw_version=self._attr_native_value
                        )
                        _LOGGER.info(f"🔍 DEVICE INFO UPDATE - Updated {group_name} device model to: {self._attr_native_value}")
                        
                except Exception as e:
                    _LOGGER.error(f"🔍 DEVICE INFO UPDATE - Failed to update device info: {e}")
            
            # Schedule the update to run in the event loop
            self.hass.loop.call_soon_threadsafe(update_device_info)
                
        except Exception as e:
            _LOGGER.error(f"🔍 DEVICE INFO UPDATE - Failed to update device info: {e}")


class LuxPowerSerialNumberSensor(LuxPowerSensorEntity):
    """Sensor that exposes the inverter serial number from communication packets."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._register_address = entity_definition.get("register", 0)
        self._bank = entity_definition.get("bank", 0)
        
        # Set initial value to indicate waiting for data
        self._attr_native_value = "Waiting for data..."
        self._attr_available = False
        
        # Try to get existing serial number from hass.data on initialization
        _LOGGER.debug(f"🔍 SERIAL SENSOR - Creating serial number sensor for dongle: {self.dongle}")
        self._load_existing_serial_number()

    def _load_existing_serial_number(self):
        """Load existing serial number from hass.data on initialization."""
        try:
            entry_id = None
            for e_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    entry_id = e_id
                    break
            
            if entry_id is not None:
                existing_serial = self.hass.data[DOMAIN].get(entry_id, {}).get("inverter_serial_number")
                if existing_serial and existing_serial != "0000000000":
                    self._attr_native_value = existing_serial
                    self._attr_available = True
                    _LOGGER.debug(f"🔍 SERIAL SENSOR - Loaded existing serial number: '{existing_serial}'")
                    # Trigger device info update on initialization
                    self._update_device_info()
        except Exception as e:
            _LOGGER.error(f"🔍 SERIAL SENSOR - Failed to load existing serial number: {e}")

    @property
    def suggested_display_precision(self) -> Optional[int]:
        """Return suggested decimal places for serial number sensor (text sensor)."""
        return None  # No precision for text values
    
    @property
    def native_value(self) -> Optional[str]:
        """Return the native value as string for text sensors."""
        return self._attr_native_value

    async def async_added_to_hass(self) -> None:
        """Register event listeners for serial number sensor."""
        await super().async_added_to_hass()
        _LOGGER.debug(f"🔍 SERIAL SENSOR - async_added_to_hass called for {self._attr_name}")
        self.is_added_to_hass = True
        if self.hass is not None:
            _LOGGER.debug(f"🔍 SERIAL SENSOR - hass is not None, registering event listeners")
            self.hass.bus.async_listen(
                self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable
            )
            # Listen to data events for serial number (serial number is passed in data events)
            self.hass.bus.async_listen(
                self.event.EVENT_DATA_BANK0_RECEIVED, self.push_update
            )
            self.hass.bus.async_listen(
                self.event.EVENT_DATA_BANK1_RECEIVED, self.push_update
            )
            self.hass.bus.async_listen(
                self.event.EVENT_DATA_BANK2_RECEIVED, self.push_update
            )
            self.hass.bus.async_listen(
                self.event.EVENT_DATA_BANK3_RECEIVED, self.push_update
            )
            _LOGGER.debug(f"🔍 SERIAL SENSOR - Event listeners registered for {self._attr_name}")
            _LOGGER.debug(f"🔍 SERIAL SENSOR - Listening to events: {self.event.EVENT_DATA_BANK0_RECEIVED}, {self.event.EVENT_DATA_BANK1_RECEIVED}, {self.event.EVENT_DATA_BANK2_RECEIVED}, {self.event.EVENT_DATA_BANK3_RECEIVED}")
            _LOGGER.info(f"Serial number sensor {self._attr_name} listening to data events")
        else:
            _LOGGER.error(f"🔍 SERIAL SENSOR - hass is None for {self._attr_name}")

    def push_update(self, event):
        _LOGGER.debug(f"🔍 SERIAL SENSOR - Event received! Bank: {self._bank} Register: {self._register_address} Name: {self._attr_name}")
        
        # Get the serial number from the event data
        serial_str = event.data.get("serial_number", "")
        _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Received serial_number: '{serial_str}'")
        _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Event data keys: {list(event.data.keys())}")
        _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Current sensor value: '{self._attr_native_value}'")
        _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Event type: {type(event).__name__}")
        _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Full event data: {event.data}")
        if serial_str and serial_str.strip() and serial_str != "0000000000":
            _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Found serial number: '{serial_str}'")
            
            oldstate = self._attr_native_value
            self._attr_native_value = serial_str
            
            # Save serial number into hass.data for device_info usage
            entry_id = None
            for e_id, data in self.hass.data.get(DOMAIN, {}).items():
                if data.get("DONGLE") == self.dongle:
                    entry_id = e_id
                    break
            if entry_id is not None:
                # Always save serial number to hass.data to ensure persistence
                current_serial = self.hass.data[DOMAIN].get(entry_id, {}).get("inverter_serial_number")
                _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - Current serial in hass.data: '{current_serial}'")
                _LOGGER.debug(f"🔍 SERIAL SENSOR DEBUG - New serial from event: '{serial_str}'")
                
                # Save serial number to hass.data (always save to ensure persistence)
                self.hass.data[DOMAIN].setdefault(entry_id, {})["inverter_serial_number"] = serial_str
                _LOGGER.debug(f"🔍 SERIAL SENSOR - Saved serial number '{serial_str}' to hass.data[{DOMAIN}][{entry_id}]")
                
                # Trigger device info update if serial changed or if not previously set
                if current_serial != serial_str or not current_serial:
                    _LOGGER.info(f"🔍 SERIAL SENSOR - Serial number changed from '{current_serial}' to '{serial_str}', updating device info")
                    self._update_device_info()
                else:
                    _LOGGER.debug(f"🔍 SERIAL SENSOR - Serial number unchanged: '{serial_str}'")
            else:
                _LOGGER.debug(f"🔍 SERIAL SENSOR - No entry_id found for dongle {self.dongle}")
            
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.info(f"Serial number sensor updated: {oldstate} -> {self._attr_native_value}")
                self.schedule_update_ha_state()
        else:
            _LOGGER.debug(f"Serial number sensor: No valid serial number in event data: '{serial_str}'")
        
        return self._attr_native_value

    def gone_unavailable(self, event):
        """Handle unavailable event for serial number sensor."""
        _LOGGER.debug(f"🔍 SERIAL SENSOR - gone_unavailable event received for {self._attr_name}")
        _LOGGER.debug(f"🔍 SERIAL SENSOR - Current value before unavailable: '{self._attr_native_value}'")
        # Don't set unavailable - keep the last known serial number
        # self._attr_available = False
        # self.schedule_update_ha_state()

    def _update_device_info(self):
        """Update device info in the device registry."""
        try:
            from homeassistant.helpers import device_registry as dr
            from .const import DEVICE_GROUP_PV, DEVICE_GROUP_GRID, DEVICE_GROUP_EPS, DEVICE_GROUP_GENERATOR, DEVICE_GROUP_BATTERY, DEVICE_GROUP_INVERTER, DEVICE_GROUP_TEMPERATURES, DEVICE_GROUP_SETTINGS
            
            # Get the device registry
            device_registry = dr.async_get(self.hass)
            
            # Device groups to update
            device_groups = [
                (DEVICE_GROUP_PV, f"{self.dongle}_pv"),
                (DEVICE_GROUP_GRID, f"{self.dongle}_grid"),
                (DEVICE_GROUP_EPS, f"{self.dongle}_eps"),
                (DEVICE_GROUP_GENERATOR, f"{self.dongle}_generator"),
                (DEVICE_GROUP_BATTERY, f"{self.dongle}_battery"),
                (DEVICE_GROUP_INVERTER, f"{self.dongle}_inverter"),
                (DEVICE_GROUP_TEMPERATURES, f"{self.dongle}_temperatures"),
                (DEVICE_GROUP_SETTINGS, f"{self.dongle}_settings"),
            ]
            
            # Find main device and all device groups
            main_device = device_registry.async_get_device(identifiers={(DOMAIN, self.dongle)})
            group_devices = {}
            
            for group_name, group_id in device_groups:
                device = device_registry.async_get_device(identifiers={(DOMAIN, group_id)})
                if device:
                    group_devices[group_name] = device
            
            # Schedule device info update to run in event loop
            def update_device_info():
                try:
                    # Update main device
                    if main_device:
                        device_registry.async_update_device(
                            main_device.id,
                            serial_number=self._attr_native_value
                        )
                        _LOGGER.info(f"🔍 DEVICE INFO UPDATE - Updated main device serial number to: {self._attr_native_value}")
                    
                    # Update all device groups
                    for group_name, device in group_devices.items():
                        device_registry.async_update_device(
                            device.id,
                            serial_number=self._attr_native_value
                        )
                        _LOGGER.info(f"🔍 DEVICE INFO UPDATE - Updated {group_name} device serial number to: {self._attr_native_value}")
                        
                except Exception as e:
                    _LOGGER.error(f"🔍 DEVICE INFO UPDATE - Failed to update device info: {e}")
            
            # Schedule the update to run in the event loop
            _LOGGER.debug(f"🔍 SERIAL SENSOR - Scheduling device info update for serial: {self._attr_native_value}")
            self.hass.loop.call_soon_threadsafe(update_device_info)
                
        except Exception as e:
            _LOGGER.error(f"🔍 DEVICE INFO UPDATE - Failed to update device info: {e}")


class LuxPowerTestSensor(LuxPowerRegisterSensor):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(
        self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None
    ):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._register_address = entity_definition["register"]
        self.entity_id = "sensor.{}_{}_{}".format(
            "lux", serial, entity_definition["unique"]
        )


class LuxPowerStatusTextSensor(LuxPowerSensorEntity):
    """Representation of a Status sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)

    @property
    def suggested_display_precision(self) -> Optional[int]:
        """Return suggested decimal places for status text sensor (text sensor)."""
        return None  # No precision for text values

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

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)

    @property
    def suggested_display_precision(self) -> Optional[int]:
        """Return suggested decimal places for battery status sensor (text sensor)."""
        return None  # No precision for text values

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

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self.datetime_last_received = None

    @property
    def suggested_display_precision(self) -> Optional[int]:
        """Return suggested decimal places for timestamp sensor (text sensor)."""
        return None  # No precision for text values

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


class LuxPowerLoadPercentageSensor(LuxPowerSensorEntity):
    """Representation of a Load Percentage sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)
        self._rated_power = 6000  # Default value

    def push_update(self, event):
        """Handle data updates and calculate load percentage."""
        _LOGGER.debug(f"Load Percentage Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip

        # Get current load power
        self._data = event.data.get("data", {})
        current_load = self._data.get(LXPPacket.p_load, 0)

        # Get rated power from hass.data
        entry_id = None
        for e_id, data in self.hass.data.get(DOMAIN, {}).items():
            if data.get("DONGLE") == self.dongle:
                entry_id = e_id
                break

        if entry_id:
            rated_power = self.hass.data[DOMAIN][entry_id].get("rated_power", 6000)
            if rated_power and rated_power > 0:
                # Calculate load percentage: (current_load / rated_power) * 100
                load_percentage = (current_load / rated_power) * 100
                self._attr_native_value = round(load_percentage, 1)
                _LOGGER.debug(f"Load Percentage: {current_load}W / {rated_power}W = {self._attr_native_value}%")
            else:
                self._attr_native_value = 0.0
                _LOGGER.debug(f"No rated power available for load percentage calculation")
        else:
            self._attr_native_value = 0.0
            _LOGGER.debug(f"No entry found for dongle {self.dongle}")

        self._attr_available = True
        self.lastupdated_time = time.time()

        # Update Home Assistant state (thread-safe)
        self.schedule_update_ha_state()


class LuxPowerAdaptivePollingSensor(LuxPowerSensorEntity):
    """Representation of an Adaptive Polling sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)

    def push_update(self, event):
        """Handle data updates and show current polling interval."""
        _LOGGER.debug(f"Adaptive Polling Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip

        # Get polling interval from client
        client = None
        for entry_id, data in self.hass.data.get(DOMAIN, {}).items():
            if data.get("DONGLE") == self.dongle:
                client = data.get("client")
                break

        if client and hasattr(client, 'get_current_polling_interval'):
            polling_interval = client.get_current_polling_interval()
            self._attr_native_value = polling_interval
            _LOGGER.debug(f"Polling Interval: {polling_interval}s")
        else:
            self._attr_native_value = 120  # Default fallback
            _LOGGER.debug(f"No client available for polling interval")

        self._attr_available = True
        self.lastupdated_time = time.time()

        # Update Home Assistant state (thread-safe)
        self.schedule_update_ha_state()


class LuxPowerConnectionQualitySensor(LuxPowerSensorEntity):
    """Representation of a Connection Quality sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)

    def push_update(self, event):
        """Handle data updates and show connection quality."""
        _LOGGER.debug(f"Connection Quality Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip

        # Get connection quality from client
        client = None
        for entry_id, data in self.hass.data.get(DOMAIN, {}).items():
            if data.get("DONGLE") == self.dongle:
                client = data.get("client")
                break

        if client and hasattr(client, '_connection_quality'):
            connection_quality = client._connection_quality
            # Convert to percentage (0-100)
            self._attr_native_value = round(connection_quality * 100, 1)
            _LOGGER.debug(f"Connection Quality: {self._attr_native_value}%")
        else:
            self._attr_native_value = 100.0  # Default to good connection
            _LOGGER.debug(f"No client available for connection quality")

        self._attr_available = True
        self.lastupdated_time = time.time()

        # Update Home Assistant state (thread-safe)
        self.schedule_update_ha_state()


class LuxPowerSelfConsumptionSensor(LuxPowerSensorEntity):
    """Representation of a Self-Consumption Rate sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)

    def push_update(self, event):
        """Handle data updates and calculate self-consumption rate."""
        _LOGGER.debug(f"Self Consumption Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip

        # Get power flow data
        self._data = event.data.get("data", {})
        load_power = self._data.get(LXPPacket.p_load, 0)
        grid_import = self._data.get(LXPPacket.p_to_user, 0)  # Power from grid to loads
        grid_export = self._data.get(LXPPacket.p_to_grid, 0)  # Power to grid (export)

        # Self-consumption = power from PV/battery to loads / total load power
        # Grid import is power from grid, so self-consumption = 1 - (grid_import / load_power)
        if load_power > 0:
            grid_dependency = min(grid_import / load_power, 1.0)  # Cap at 100%
            self_consumption_rate = (1.0 - grid_dependency) * 100
            self._attr_native_value = round(self_consumption_rate, 1)
            _LOGGER.debug(f"Self-Consumption: {self._attr_native_value}% (Grid: {grid_import}W, Load: {load_power}W)")
        else:
            self._attr_native_value = 0.0
            _LOGGER.debug("No load power, self-consumption rate = 0%")

        self._attr_available = True
        self.lastupdated_time = time.time()

        # Update Home Assistant state (thread-safe)
        self.schedule_update_ha_state()


class LuxPowerGridDependencySensor(LuxPowerSensorEntity):
    """Representation of a Grid Dependency sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, entity_definition, event, model_code)

    def push_update(self, event):
        """Handle data updates and calculate grid dependency percentage."""
        _LOGGER.debug(f"Grid Dependency Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._attr_name}")  # fmt: skip

        # Get power flow data
        self._data = event.data.get("data", {})
        grid_import = self._data.get(LXPPacket.p_to_user, 0)  # Power from grid to loads
        load_power = self._data.get(LXPPacket.p_load, 0)

        # Grid dependency = grid power / total load power
        if load_power > 0:
            grid_dependency = min(grid_import / load_power, 1.0) * 100  # Cap at 100%
            self._attr_native_value = round(grid_dependency, 1)
            _LOGGER.debug(f"Grid Dependency: {self._attr_native_value}% (Grid: {grid_import}W, Load: {load_power}W)")
        else:
            self._attr_native_value = 0.0
            _LOGGER.debug("No load power, grid dependency = 0%")

        self._attr_available = True
        self.lastupdated_time = time.time()

        # Update Home Assistant state (thread-safe)
        self.schedule_update_ha_state()


class LuxStateSensorEntity(SensorEntity):
    """Representation of an overall sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event, model_code: str = None):  # fmt: skip
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
        self._entity_definition = entity_definition
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
        """Return device info for the appropriate device group."""
        # Get the device group for this entity
        device_group = get_entity_device_group(self._entity_definition, self.hass)
        
        # Return device group info if available, otherwise fall back to main device
        if device_group:
            return get_device_group_info(self.hass, self.dongle, device_group)
        else:
            return get_comprehensive_device_info(self.hass, self.dongle, self.serial)
