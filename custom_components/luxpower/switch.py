"""
LuxPower switch platform for Home Assistant.

This module provides switch entities for controlling LuxPower inverter functions
including charging control, grid export settings, and other operational modes.
"""

import logging
from typing import Any, Dict, List, Optional

from homeassistant.components.switch import SwitchEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo, EntityCategory
from homeassistant.util import slugify

from .lxp.client import LuxPowerClient

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DEFAULT_DONGLE_SERIAL,
    DEFAULT_SERIAL_NUMBER,
    DOMAIN,
    VERSION,
    MODEL_MAP,
    is_12k_model,
)
from .helpers import Event, get_comprehensive_device_info, get_device_group_info, get_entity_device_group
from .LXPPacket import LXPPacket, prepare_binary_value

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(
    hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices
):
    """Set up the switch platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux switch platform")
    _LOGGER.info("Set up the switch platform %s", config_entry.data)
    _LOGGER.info("Options %s", len(config_entry.options))

    global hyphen
    global nameID_midfix
    global entityID_midfix

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, DEFAULT_DONGLE_SERIAL)
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, DEFAULT_SERIAL_NUMBER)
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)
    luxpower_client = hass.data[config_entry.domain][config_entry.entry_id]["client"]

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

    event = Event(dongle=DONGLE)
    # luxpower_client = hass.data[event.CLIENT_DAEMON]

    _LOGGER.info(f"Lux switch platform_config: {platform_config}")

    # fmt: off

    switchEntities: List[SwitchEntity] = []

    """ Common Switches Displayed In The App/Web """
    switches = [
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Normal/Standby(ON/OFF)", "register_address": 21, "bitmask": LXPPacket.NORMAL_OR_STANDBY, "attribute": "work_mode", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Power Backup Enable", "register_address": 21, "bitmask": LXPPacket.POWER_BACKUP_ENABLE, "attribute": "eps_status", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Feed-In Grid", "register_address": 21, "bitmask": LXPPacket.FEED_IN_GRID, "attribute": "p_to_grid", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} DCI Enable", "register_address": 21, "bitmask": LXPPacket.DCI_ENABLE, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} GFCI Enable", "register_address": 21, "bitmask": LXPPacket.GFCI_ENABLE, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Seamless EPS Switching", "register_address": 21, "bitmask": LXPPacket.SEAMLESS_EPS_SWITCHING, "attribute": "eps_status", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Grid On Power SS", "register_address": 21, "bitmask": LXPPacket.GRID_ON_POWER_SS, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Neutral Detect Enable", "register_address": 21, "bitmask": LXPPacket.NEUTRAL_DETECT_ENABLE, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Anti Island Enable", "register_address": 21, "bitmask": LXPPacket.ANTI_ISLAND_ENABLE, "attribute": "grid_status", "enabled": False},
        
        # AFCI Arc Detection Controls
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} AFCI PV Arc Enable", "register_address": 179, "bitmask": LXPPacket.AFCI_PV_ARC_ENABLE, "attribute": "afci_curr_ch1", "enabled": False},
        
        # Generator Control Switches
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Connected", "register_address": 77, "bitmask": LXPPacket.GENERATOR_CONNECTED, "attribute": "gen_input_volt", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} DRMS Enable", "register_address": 21, "bitmask": LXPPacket.DRMS_ENABLE, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} OVF Load Derate Enable", "register_address": 21, "bitmask": LXPPacket.OVF_LOAD_DERATE_ENABLE, "attribute": "p_load", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} ISO Enabled", "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_12, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Low Voltage Ride Though Enable", "register_address": 21, "bitmask": LXPPacket.R21_UNKNOWN_BIT_3, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Enable", "register_address": 21, "bitmask": LXPPacket.AC_CHARGE_ENABLE, "attribute": "ac_charge_mode", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Charge Priority", "register_address": 21, "bitmask": LXPPacket.CHARGE_PRIORITY, "attribute": "p_charge", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Enable", "register_address": 21, "bitmask": LXPPacket.FORCED_DISCHARGE_ENABLE, "attribute": "p_discharge", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Take Load Together", "register_address": 110, "bitmask": LXPPacket.TAKE_LOAD_TOGETHER, "attribute": "p_load", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Charge Last", "register_address": 110, "bitmask": LXPPacket.CHARGE_LAST, "attribute": "p_charge", "enabled": True},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Grid Peak-Shaving", "register_address": 179, "bitmask": LXPPacket.ENABLE_PEAK_SHAVING, "attribute": "peak_shaving_power", "enabled": False},
        
        # Additional Register 110 Control Switches (Disabled by Default)
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} EPS Mode Enable", "register_address": 110, "bitmask": LXPPacket.EPS_ENABLE, "attribute": "eps_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Alt", "register_address": 110, "bitmask": LXPPacket.FORCED_DISCHG_EN_ALT, "attribute": "p_discharge", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} DRMS Enable Alt", "register_address": 110, "bitmask": LXPPacket.DRMS_ENABLE_ALT, "attribute": "grid_status", "enabled": False},
        
        # 12K-Specific Control Switches (Based on Cloud UI Analysis) - UPDATED FROM CLOUD UI
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Smart Load Inverter Enable", "register_address": 0, "bitmask": 0, "attribute": "smart_load_start_soc", "enabled": False},  # Smart load control enable
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Quick Start", "register_address": 0, "bitmask": 0, "attribute": "gen_charge_type", "enabled": False},  # Generator quick start control
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Backup Mode", "register_address": 0, "bitmask": 0, "attribute": "bat_status_inv", "enabled": False},  # Battery backup mode control

        # Enhanced Peak Shaving Analysis Control Switches
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 1", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_00, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 1
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 2", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_01, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 2
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 3", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_02, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 3
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 4", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_03, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 4
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 5", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_04, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 5
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 6", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_05, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 6
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 7", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_06, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 7
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 8", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_08, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 8
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 9", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_08, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 9
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 10", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_09, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 10
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 11", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_10, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 11
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 12", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_11, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 12
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 13", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_12, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 13
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 14", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_13, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 14
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 15", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_14, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 15
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Peak Shaving Mode 16", "register_address": 179, "bitmask": LXPPacket.R179_UNKNOWN_BIT_15, "attribute": "peak_shaving_power", "enabled": False},  # Peak shaving mode 16

        # UI Enhancements Control Switches
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Auto Configuration Mode", "register_address": 0, "bitmask": 0, "attribute": "sys_config_12k", "enabled": False},  # Auto configuration mode
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Diagnostic Mode", "register_address": 0, "bitmask": 0, "attribute": "connection_quality", "enabled": False},  # Diagnostic mode
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Performance Monitoring", "register_address": 0, "bitmask": 0, "attribute": "self_consumption_rate", "enabled": False},  # Performance monitoring
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Energy Dashboard Integration", "register_address": 0, "bitmask": 0, "attribute": "self_consumption_rate", "enabled": False},  # Energy dashboard integration
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Cost Tracking", "register_address": 0, "bitmask": 0, "attribute": "self_consumption_rate", "enabled": False},  # Cost tracking
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Environmental Impact Tracking", "register_address": 0, "bitmask": 0, "attribute": "self_consumption_rate", "enabled": False},  # Environmental impact tracking

        # Generator Integration Control Switches (Phase 3)
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Auto Start Enable", "register_address": 0, "bitmask": 0, "attribute": "gen_charge_type", "enabled": False},  # Generator auto start enable
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Priority", "register_address": 0, "bitmask": 0, "attribute": "gen_charge_type", "enabled": False},  # Generator charge priority
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Dry Contact Control", "register_address": 0, "bitmask": 0, "attribute": "gen_input_volt", "enabled": False},  # Generator dry contact control
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Generator Cooldown Timer", "register_address": 0, "bitmask": 0, "attribute": "gen_charge_type", "enabled": False},  # Generator cooldown timer
        
        # Battery Management Control Switches (Phase 6)
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Protection Enable", "register_address": 0, "bitmask": 0, "attribute": "bat_status_inv", "enabled": False},  # Battery protection enable
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Enable", "register_address": 0, "bitmask": 0, "attribute": "charge_volt_ref", "enabled": False},  # Equalization enable
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Temperature Compensation Enable", "register_address": 0, "bitmask": 0, "attribute": "t_bat", "enabled": False},  # Temperature compensation enable
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Aging Compensation Enable", "register_address": 0, "bitmask": 0, "attribute": "bat_cycle_count", "enabled": False},  # Aging compensation enable
        
        # Grid Management Control Switches (Phase 7)
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Zero Export Mode", "register_address": 0, "bitmask": 0, "attribute": "p_to_grid", "enabled": False},  # Zero export mode
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Reactive Power Control", "register_address": 0, "bitmask": 0, "attribute": "pf", "enabled": False},  # Reactive power control
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Voltage Support Mode", "register_address": 0, "bitmask": 0, "attribute": "v_ac_r", "enabled": False},  # Voltage support mode
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Dynamic Export Control", "register_address": 0, "bitmask": 0, "attribute": "p_to_grid", "enabled": False},  # Dynamic export control
        
        # NEW 2025.03.05 Protocol: Missing Switch Entities
        # Hold 110 - Function Enable 1 (12K Models)
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} PV Grid Off", "register_address": 110, "bitmask": LXPPacket.PV_GRID_OFF, "attribute": "p_pv_total", "enabled": False},  # 12K models only
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Fast Zero Export", "register_address": 110, "bitmask": LXPPacket.FAST_ZERO_EXPORT, "attribute": "p_to_grid", "enabled": False},  # 12K models only
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Micro Grid", "register_address": 110, "bitmask": LXPPacket.MICRO_GRID, "attribute": "grid_status", "enabled": False},  # 12K models only
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Shared", "register_address": 110, "bitmask": LXPPacket.BATTERY_SHARED, "attribute": "bat_status_inv", "enabled": False},  # 12K models only
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Buzzer/Dry Contactor", "register_address": 110, "bitmask": LXPPacket.BUZZER_DRY_CONTACTOR, "attribute": "grid_status", "enabled": False},  # 12K models only
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Green/Absolute Zero Export", "register_address": 110, "bitmask": LXPPacket.GREEN_ABSOLUTE_ZERO_EXPORT, "attribute": "p_to_grid", "enabled": False},  # 12K models only
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Eco Mode/EPS RY", "register_address": 110, "bitmask": LXPPacket.ECO_MODE_EPS_RY, "attribute": "eps_status", "enabled": False},  # 12K models only
        
        # Hold 179 - Function Enable 2
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Wakeup", "register_address": 179, "bitmask": LXPPacket.BATTERY_WAKEUP, "attribute": "bat_status_inv", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} PV Sell First", "register_address": 179, "bitmask": LXPPacket.PV_SELL_FIRST, "attribute": "p_pv_total", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Volt-Watt", "register_address": 179, "bitmask": LXPPacket.VOLT_WATT, "attribute": "p_pv_total", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Trip Time Unit", "register_address": 179, "bitmask": LXPPacket.TRIP_TIME_UNIT, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Active Power CMD", "register_address": 179, "bitmask": LXPPacket.ACTIVE_POWER_CMD, "attribute": "p_inv", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Gen Peak Shaving", "register_address": 179, "bitmask": LXPPacket.GEN_PEAK_SHAVING, "attribute": "gen_power_watt", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Charge Control", "register_address": 179, "bitmask": LXPPacket.BATTERY_CHARGE_CONTROL, "attribute": "p_charge", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Discharge Control", "register_address": 179, "bitmask": LXPPacket.BATTERY_DISCHARGE_CONTROL, "attribute": "p_discharge", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} AC Coupling", "register_address": 179, "bitmask": LXPPacket.AC_COUPLING, "attribute": "ac_couple_start_soc", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} RSD Disable", "register_address": 179, "bitmask": LXPPacket.RSD_DISABLE, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} On Grid Always On", "register_address": 179, "bitmask": LXPPacket.ON_GRID_ALWAYS_ON, "attribute": "grid_status", "enabled": False},
        
        # Hold 233 - Function Enable 4
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Quick Charge Start", "register_address": 233, "bitmask": LXPPacket.QUICK_CHARGE_START, "attribute": "p_charge", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Battery Backup", "register_address": 233, "bitmask": LXPPacket.BATTERY_BACKUP, "attribute": "bat_status_inv", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Maintenance Enable", "register_address": 233, "bitmask": LXPPacket.MAINTENANCE_ENABLE, "attribute": "grid_status", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} Working Mode", "register_address": 233, "bitmask": LXPPacket.WORKING_MODE, "attribute": "work_mode", "enabled": False},
        {"etype": "LVSE", "name": "Lux {replaceID_midfix}{hyphen} EN50549 F-stop", "register_address": 233, "bitmask": LXPPacket.EN50549_F_STOP, "attribute": "grid_status", "enabled": False},
    ]

    for entity_definition in switches:
        etype = entity_definition["etype"]
        if etype == "LVSE":
            # Apply model-based enablement logic
            default_enabled = entity_definition.get("enabled", True)
            
            if model_code:
                is_12k = is_12k_model(model_code)
                # Check if this is a 12K-specific switch
                if "12K" in entity_definition.get("name", ""):
                    default_enabled = is_12k
                    if is_12k:
                        _LOGGER.debug(f"Enabling 12K-specific switch: {entity_definition['name']}")
                    else:
                        _LOGGER.debug(f"Disabling 12K-specific switch for non-12K model: {entity_definition['name']}")
            
            # Update entity definition with model-based enablement
            entity_definition["enabled"] = default_enabled
            switchEntities.append(LuxPowerRegisterValueSwitchEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))

    # fmt: on

    async_add_devices(switchEntities, True)

    _LOGGER.info("LuxPower switch async_setup_platform switch done")


class LuxPowerRegisterValueSwitchEntity(SwitchEntity):
    """Represent a LUX binary switch sensor."""

    _client: LuxPowerClient

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"switch.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event

        # Hidden Inherited Instance Attributes
        self._attr_entity_registry_enabled_default = entity_definition.get(
            "enabled", False
        )

        # Hidden Class Extended Instance Attributes
        self._entity_definition = entity_definition
        self._client = luxpower_client
        self._register_address = entity_definition["register_address"]
        self._register_value = None
        self._bitmask = entity_definition["bitmask"]
        self._attr_name = entity_definition["name"].format(
            replaceID_midfix=nameID_midfix, hyphen=hyphen
        )
        self._attr_available = False
        # self._attr_device_class = DEVICE_CLASS_OPENING
        self._attr_should_poll = False
        self._state = False
        self._read_value = 0
        # self.lxppacket = luxpower_client.lxpPacket
        self.registers: Dict[int, int] = {}
        self.totalregs: Dict[int, int] = {}

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass %s", self._attr_name)
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
        registers = event.data.get("registers", {})
        self.registers = registers
        if self._register_address in registers.keys():
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            # Save current register int value
            self._register_value = register_val
            _LOGGER.debug(
                "switch: register event received - register: %s bitmask: %s",
                self._register_address,
                self._bitmask,
            )
            self.totalregs = self._client.lxpPacket.regValuesInt
            # _LOGGER.debug("totalregs: %s" , self.totalregs)
            oldstate = self._state
            self._state = register_val & self._bitmask == self._bitmask
            if oldstate != self._state or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(
                    f"Reading: {self._register_address} {self._bitmask} Old State {oldstate} Updating state to {self._state} - {self._attr_name}"
                )
                self.schedule_update_ha_state()
            if (
                self._register_address == 21
                and self._bitmask == LXPPacket.AC_CHARGE_ENABLE
            ):
                if 68 in self.totalregs.keys():
                    self.schedule_update_ha_state()
            if (
                self._register_address == 21
                and self._bitmask == LXPPacket.CHARGE_PRIORITY
            ):
                if 76 in self.totalregs.keys():
                    self.schedule_update_ha_state()
            if (
                self._register_address == 21
                and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE
            ):
                if 84 in self.totalregs.keys():
                    self.schedule_update_ha_state()
        return self._state

    def gone_unavailable(self, event):
        _LOGGER.warning(f"Register: gone_unavailable event received Name: {self._attr_name} - Register Address: {self._register_address}")  # fmt: skip
        self._attr_available = False
        self.schedule_update_ha_state()

    def update(self):
        _LOGGER.debug(
            f"{self._register_address} {self._bitmask} updating state to {self._state}"
        )
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_{self._register_address}_{self._bitmask}"

    @property
    def is_on(self):
        """Return true if the binary sensor is on."""
        return self._state

    async def async_turn_on(self) -> None:
        _LOGGER.debug("turn on called ")
        await self.set_register_bit(True)

    async def async_turn_off(self) -> None:
        _LOGGER.debug("turn off called ")
        await self.set_register_bit(False)

    @property
    def entity_category(self):
        """Return entity category."""
        # Configuration entities for settings and controls
        if self._register_address in [21, 22, 23, 24, 25]:  # Common config registers
            return EntityCategory.CONFIG
        return None

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

    async def set_register_bit(self, bit_polarity=False):
        self._read_value = await self._client.read(self._register_address)

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

        new_value = prepare_binary_value(old_value, self._bitmask, bit_polarity)

        if new_value != old_value:
            _LOGGER.info(
                f"Writing: OLD: {old_value} REGISTER: {self._register_address} MASK: {self._bitmask} NEW {new_value}"
            )
            self._read_value = await self._client.write(
                self._register_address, new_value
            )

            if self._read_value is not None:
                self._state = self._read_value & self._bitmask == self._bitmask
                self.async_write_ha_state()
                _LOGGER.info(
                    f"CAN confirm successful WRITE of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                )
                if self._read_value == new_value:
                    _LOGGER.info(
                        f"CAN confirm WRITTEN value is same as that sent to SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm WRITTEN value is same as that sent to SET Register: {self._register_address} ValueSENT: {new_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                    )
            else:
                _LOGGER.warning(
                    f"CanNOT confirm successful WRITE of SET Register: {self._register_address} Entity: {self.entity_id}"
                )

        self._attr_available = True
        _LOGGER.debug("set_register_bit done")

    def convert_to_time(self, value: int):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    # fmt: off

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        if self._register_address == 21 and self._bitmask == LXPPacket.AC_CHARGE_ENABLE:

            _LOGGER.debug("Attrib totalregs: %s", self.totalregs)
            _LOGGER.debug("Attrib registers: %s", self.registers)

            state_attributes["AC_CHARGE_START_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(68, 0)))
            state_attributes["AC_CHARGE_END_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(69, 0)))
            state_attributes["AC_CHARGE_START_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(70, 0)))
            state_attributes["AC_CHARGE_END_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(71, 0)))
            state_attributes["AC_CHARGE_START_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(72, 0)))
            state_attributes["AC_CHARGE_END_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(73, 0)))

        if self._register_address == 21 and self._bitmask == LXPPacket.CHARGE_PRIORITY:

            state_attributes["PRIORITY_CHARGE_START_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(76, 0)))
            state_attributes["PRIORITY_CHARGE_END_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(77, 0)))
            state_attributes["PRIORITY_CHARGE_START_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(78, 0)))
            state_attributes["PRIORITY_CHARGE_END_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(79, 0)))
            state_attributes["PRIORITY_CHARGE_START_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(80, 0)))
            state_attributes["PRIORITY_CHARGE_END_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(81, 0)))

        if self._register_address == 21 and self._bitmask == LXPPacket.FORCED_DISCHARGE_ENABLE:

            state_attributes["FORCED_DISCHARGE_START_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(84, 0)))
            state_attributes["FORCED_DISCHARGE_END_1"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(85, 0)))
            state_attributes["FORCED_DISCHARGE_START_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(86, 0)))
            state_attributes["FORCED_DISCHARGE_END_2"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(87, 0)))
            state_attributes["FORCED_DISCHARGE_START_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(88, 0)))
            state_attributes["FORCED_DISCHARGE_END_3"] = "{0[0]}:{0[1]}".format(self.convert_to_time(self.totalregs.get(89, 0)))

        return state_attributes

    # fmt: on
