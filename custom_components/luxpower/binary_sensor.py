"""
LuxPower binary sensor platform for Home Assistant.

This module provides binary sensor entities for monitoring LuxPower inverter
status indicators including charging state, grid connection, and system alerts.

Copyright (C) 2025 Guy Wells
https://github.com/guybw/LuxPython_DEV
"""

import logging
from typing import Any, Dict, List, Optional

from homeassistant.components.binary_sensor import (
    BinarySensorDeviceClass,
    BinarySensorEntity,
)
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import CONF_MODE
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo, Entity
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
)
from .helpers import Event
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(
    hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices
):
    """Set up the binary sensor platform."""
    _LOGGER.info("Loading the Lux binary sensor platform")

    global hyphen
    global nameID_midfix
    global entityID_midfix
    
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
        from .sensor import is_12k_model, MODEL_MAP
        is_12k = is_12k_model(model_code)
        model_name = MODEL_MAP.get(model_code, "Unknown")
        _LOGGER.info(f"Model detected: {model_name} ({model_code}) - {'12K' if is_12k else 'non-12K'}")
    else:
        _LOGGER.info("No model code available - using default entity enablement")

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, DEFAULT_DONGLE_SERIAL)
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, DEFAULT_SERIAL_NUMBER)
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    # Options For Name Midfix Based Upon Serial Number - Suggest Last Two Digits
    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""

    # Options For Entity Midfix Based Upon Serial Number - Suggest Full Serial Number
    entityID_midfix = SERIAL if USE_SERIAL else ""

    hyphen = "-" if nameID_midfix else ""

    event = Event(hass, HOST, PORT, DONGLE, SERIAL)

    binarySensorEntities = []

    # Enhanced Diagnostics Binary Sensors (Phase 1A-B)
    binary_sensors = [
        # Fault & Warning Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Fault Active", "unique": "lux_fault_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Warning Active", "unique": "lux_warning_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} System Alarm", "unique": "lux_system_alarm", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        
        # BMS Status Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} BMS Connected", "unique": "lux_bms_connected", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} BMS Fault", "unique": "lux_bms_fault", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Battery Protection Active", "unique": "lux_battery_protection", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        
        # Inverter Health Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Over Temperature", "unique": "lux_over_temp", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.HEAT, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Fan Failure", "unique": "lux_fan_failure", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Communication Error", "unique": "lux_comm_error", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        
        # Operating Mode Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Grid Connected", "unique": "lux_grid_connected", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} EPS Mode Active", "unique": "lux_eps_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Connected", "unique": "lux_generator_connected", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Active", "unique": "lux_ac_couple_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        
        # Parallel System Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Master", "unique": "lux_parallel_master", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Slave", "unique": "lux_parallel_slave", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Sync", "unique": "lux_parallel_sync", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Parallel System Active", "unique": "lux_parallel_system_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Parallel Communication Error", "unique": "lux_parallel_comm_error", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        
        # Generator Integration Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Running", "unique": "lux_generator_running", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Auto Start", "unique": "lux_generator_auto_start", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Quick Start", "unique": "lux_generator_quick_start", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Dry Contact", "unique": "lux_generator_dry_contact", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Fault", "unique": "lux_generator_fault", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Generator Low Fuel", "unique": "lux_generator_low_fuel", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        
        # Battery Management Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Battery Over Temperature", "unique": "lux_battery_over_temp", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.HEAT, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Battery Under Temperature", "unique": "lux_battery_under_temp", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.COLD, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Cell Imbalance", "unique": "lux_cell_imbalance", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Equalization Active", "unique": "lux_equalization_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Battery Protection Active", "unique": "lux_battery_protection_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        
        # Grid Management Binary Sensors
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Grid Export Limited", "unique": "lux_grid_export_limited", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Zero Export Active", "unique": "lux_zero_export_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Reactive Power Control Active", "unique": "lux_reactive_power_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Grid Fault Detected", "unique": "lux_grid_fault_detected", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Anti Islanding Active", "unique": "lux_anti_islanding_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        
        # Enhanced Peak Shaving Binary Sensors (Phase 5B)
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Active", "unique": "lux_peak_shaving_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Schedule Active", "unique": "lux_peak_schedule_active", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Bit 15 Active", "unique": "lux_peak_bit_15_active", "bank": 4, "register": 179, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Bit 14 Active", "unique": "lux_peak_bit_14_active", "bank": 4, "register": 179, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Bit 12 Active", "unique": "lux_peak_bit_12_active", "bank": 4, "register": 179, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Bit 8 Active", "unique": "lux_peak_bit_8_active", "bank": 4, "register": 179, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Bit 7 Active", "unique": "lux_peak_bit_7_active", "bank": 4, "register": 179, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Bit 6 Active", "unique": "lux_peak_bit_6_active", "bank": 4, "register": 179, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
        
        # UI Enhancements & Diagnostic Binary Sensors (Phase 8)
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Communication Healthy", "unique": "lux_comm_healthy", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.CONNECTIVITY, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Data Stale", "unique": "lux_data_stale", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} High Packet Loss", "unique": "lux_high_packet_loss", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} Low Efficiency", "unique": "lux_low_efficiency", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} High Energy Cost", "unique": "lux_high_energy_cost", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.PROBLEM, "enabled": False},
        {"etype": "LPBS", "name": "Lux {replaceID_midfix}{hyphen} System Optimized", "unique": "lux_system_optimized", "bank": 0, "register": 0, "device_class": BinarySensorDeviceClass.POWER, "enabled": False},
    ]

    for entity_definition in binary_sensors:
        etype = entity_definition["etype"]
        if etype == "LPBS":
            binarySensorEntities.append(LuxBinarySensorEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))

    async_add_devices(binarySensorEntities, True)


class LuxBinarySensorEntity(BinarySensorEntity):
    """LuxPower Binary Sensor Entity."""

    def __init__(self, hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event):
        """Initialize the binary sensor."""
        self._hass = hass
        self._host = HOST
        self._port = PORT
        self._dongle = DONGLE
        self._serial = SERIAL
        self._entity_definition = entity_definition
        self._event = event
        self._state = None
        self._attributes = {}

        # Extract entity properties
        self._name = entity_definition["name"].format(
            replaceID_midfix=nameID_midfix, hyphen=hyphen
        )
        self._unique_id = entity_definition["unique"]
        self._bank = entity_definition.get("bank", 0)
        self._register = entity_definition.get("register", 0)
        self._device_class = entity_definition.get("device_class")
        self._enabled = entity_definition.get("enabled", True)

        # Set up event listener
        self._event.add_listener(self._update_callback)

    @property
    def name(self):
        """Return the name of the binary sensor."""
        return self._name

    @property
    def unique_id(self):
        """Return the unique ID of the binary sensor."""
        return f"{self._unique_id}_{self._serial}"

    @property
    def device_info(self):
        """Return device information."""
        return DeviceInfo(
            identifiers={(DOMAIN, self._serial)},
            name=f"LuxPower Inverter {self._serial}",
            manufacturer="LuxPowerTek",
            model="LuxPower Inverter",
            sw_version=VERSION,
        )

    @property
    def is_on(self):
        """Return the state of the binary sensor."""
        return self._state

    @property
    def device_class(self):
        """Return the device class of the binary sensor."""
        return self._device_class

    @property
    def extra_state_attributes(self):
        """Return the state attributes."""
        return self._attributes

    @property
    def available(self):
        """Return if the binary sensor is available."""
        return self._state is not None

    def _update_callback(self, data):
        """Handle updated data from the inverter."""
        try:
            if self._bank == 0 and self._register == 0:
                # Placeholder for future register mapping
                # For now, set to False (no fault/warning)
                self._state = False
            else:
                # Future implementation will read from actual registers
                self._state = False
                
            self.schedule_update_ha_state()
        except Exception as e:
            _LOGGER.error(f"Error updating binary sensor {self._name}: {e}")

    async def async_added_to_hass(self):
        """Called when entity is added to hass."""
        if not self._enabled:
            self.entity_registry.async_get_or_create(
                self.entity_id,
                suggested_object_id=self._unique_id,
                disabled_by="integration",
            )
