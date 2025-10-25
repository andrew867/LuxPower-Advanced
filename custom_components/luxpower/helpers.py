"""
Helper classes and utilities for LuxPower integration.

This module provides the Event class for managing LuxPower-specific events
and event naming conventions used throughout the integration.
"""

from .const import (
    CLIENT_DAEMON_FORMAT,
    DOMAIN,
    EVENT_DATA_FORMAT,
    EVENT_REGISTER_FORMAT,
    EVENT_UNAVAILABLE_FORMAT,
    VERSION,
    MODEL_MAP,
    DEVICE_GROUP_PV,
    DEVICE_GROUP_GRID,
    DEVICE_GROUP_EPS,
    DEVICE_GROUP_GENERATOR,
    DEVICE_GROUP_BATTERY,
    DEVICE_GROUP_TEMPERATURES,
    DEVICE_GROUP_SETTINGS,
    DEVICE_GROUP_INVERTER,
)

import logging

_LOGGER = logging.getLogger(__name__)

# fmt: off


class Event:
    """
    Event management for LuxPower integration.
    
    This class manages all event names and formats used by the LuxPower integration
    for data updates, register changes, and connection status events.
    """

    def __init__(self, dongle: str) -> None:
        """
        Initialize Event class with dongle serial.
        
        Args:
            dongle: The dongle serial number for this inverter
        """
        self.INVERTER_ID = dongle
        self.EVENT_DATA_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="all")
        self.EVENT_DATA_BANK0_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank0")
        self.EVENT_DATA_BANK1_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank1")
        self.EVENT_DATA_BANK2_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank2")
        self.EVENT_DATA_BANK3_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank3")
        self.EVENT_DATA_BANK4_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank4")
        self.EVENT_DATA_BANK5_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank5")
        self.EVENT_DATA_BANK6_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank6")
        self.EVENT_DATA_BANKX_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bankX")
        self.EVENT_REGISTER_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="all")
        self.EVENT_REGISTER_21_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="21")
        self.EVENT_REGISTER_BANK0_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank0")
        self.EVENT_REGISTER_BANK1_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank1")
        self.EVENT_REGISTER_BANK2_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank2")
        self.EVENT_REGISTER_BANK3_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank3")
        self.EVENT_REGISTER_BANK4_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank4")
        self.EVENT_REGISTER_BANK5_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank5")
        self.EVENT_UNAVAILABLE_RECEIVED = EVENT_UNAVAILABLE_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID)
        self.CLIENT_DAEMON = CLIENT_DAEMON_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID)

# fmt: on


def get_device_group_icon(device_group: str) -> str:
    """
    Get appropriate icon for device group.
    
    Args:
        device_group: Device group identifier
        
    Returns:
        str: Local icon URL for the device group
    """
    from .const import DEVICE_GROUP_PV, DEVICE_GROUP_GRID, DEVICE_GROUP_EPS, DEVICE_GROUP_GENERATOR, DEVICE_GROUP_BATTERY, DEVICE_GROUP_INVERTER, DEVICE_GROUP_TEMPERATURES, DEVICE_GROUP_SETTINGS
    
    # Device group icon mapping
    group_icons = {
        DEVICE_GROUP_PV: "/local/luxpower-images/icon-pv.svg",
        DEVICE_GROUP_GRID: "/local/luxpower-images/icon-grid.svg",
        DEVICE_GROUP_EPS: "/local/luxpower-images/icon-eps.svg",
        DEVICE_GROUP_GENERATOR: "/local/luxpower-images/icon-generator.svg",
        DEVICE_GROUP_BATTERY: "/local/luxpower-images/icon-battery.svg",
        DEVICE_GROUP_INVERTER: "/local/luxpower-images/icon-inverter.svg",
        DEVICE_GROUP_TEMPERATURES: "/local/luxpower-images/icon-temperatures.svg",
        DEVICE_GROUP_SETTINGS: "/local/luxpower-images/icon-settings.svg",
    }
    
    return group_icons.get(device_group, "/local/luxpower-images/icon-inverter.svg")


def get_device_image_url(model_code: str, model_name: str) -> str:
    """
    Get appropriate device image URL based on model.
    
    Args:
        model_code: Model code from inverter
        model_name: Human-readable model name
        
    Returns:
        str: Local image URL for the device
    """
    # Default LuxPower logo (local image)
    default_logo = "/local/luxpower-images/logo_new_website.webp"
    
    # Model-specific image mapping (using local images)
    model_images = {
        # 12K models - EU 12K hybrid inverter
        "CFAA": "/local/luxpower-images/EU-12K-263x300.webp",
        "CEAA": "/local/luxpower-images/SNA-US-12K-300x225.webp",  # SNA 12K-US
        "CCAA": "/local/luxpower-images/SNA-US-12K-300x225.webp",  # SNA-US 6000
        
        # 6K models - Hybrid inverter
        "CFAB": "/local/luxpower-images/SNA-3-6K-510x306.webp",
        "CEAB": "/local/luxpower-images/SNA-3-6K-510x306.webp",
        "CCAB": "/local/luxpower-images/SNA-US-6K-300x225.webp",
        
        # 3K models - ECO series
        "CFAC": "/local/luxpower-images/ACS-3600-510x287.webp",
        "CEAC": "/local/luxpower-images/ACS-3600-510x287.webp",
        "CCAC": "/local/luxpower-images/ACS-3600-510x287.webp",
        
        # Additional model codes from MODEL_MAP
        "AAAA": "/local/luxpower-images/SNA-3-6K-510x306.webp",  # LXP 3-6K Hybrid (Standard)
        "AAAB": "/local/luxpower-images/SNA-3-6K-510x306.webp",  # LXP 3-6K Hybrid (Parallel)
        "BAAA": "/local/luxpower-images/ACS-3600-510x287.webp",  # LXP-3600 ACS (Standard)
        "BAAB": "/local/luxpower-images/ACS-3600-510x287.webp",  # LXP-3600 ACS (Parallel)
        "CBAA": "/local/luxpower-images/SNA-3-6K-510x306.webp",  # SNA 3000-6000
        "EAAB": "/local/luxpower-images/SNA-3-6K-510x306.webp",  # LXP-LB-EU 7K
        "FAAB": "/local/luxpower-images/US-8-10K-510x383.webp",  # LXP-LB-8-12K
        "ACAB": "/local/luxpower-images/GEN-3-6K-scaled.webp",   # GEN-LB-EU 3-6K
        "HAAA": "/local/luxpower-images/GEN-7-10K-scaled.webp",   # GEB-LB-EU 7-10K
        "BEAA": "/local/luxpower-images/SNA-3-6K-510x306.webp",  # LXP Variant
        "DAAA": "/local/luxpower-images/SNA-3-6K-510x306.webp",  # LXP Variant
    }
    
    # Return model-specific image if available, otherwise default logo
    return model_images.get(model_code, default_logo)


def get_comprehensive_device_info(hass, dongle: str, serial: str = None) -> dict:
    """
    Get comprehensive device information for LuxPower inverter.
    
    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number
        serial: Inverter serial number (optional)
        
    Returns:
        dict: Comprehensive device information
    """
    from homeassistant.helpers.entity import DeviceInfo
    
    # Find the entry_id for this dongle
    entry_id = None
    device_data = {}
    
    for e_id, data in hass.data.get(DOMAIN, {}).items():
        if data.get("DONGLE") == dongle:
            entry_id = e_id
            device_data = data
            break
    
    # Get device information from cached data
    model = device_data.get("model", "LuxPower Inverter")
    model_code = device_data.get("model_code", "Unknown")
    firmware_version = device_data.get("lux_firmware_version", "Unknown")
    inverter_serial = device_data.get("inverter_serial_number", None)
    
    # Debug logging for device info
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Dongle: {dongle}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Entry ID: {entry_id}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Device Data: {device_data}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Model: {model}, Model Code: {model_code}, Firmware: {firmware_version}, Serial: {inverter_serial}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Firmware from device_data: '{firmware_version}'")
    
    # Get model name from model code - ensure we get the string name, not the dict
    from .const import get_model_name
    model_name = get_model_name(model_code) if model_code != "Unknown" else model
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Model name before check: {model_name}, type: {type(model_name)}")
    if isinstance(model_name, dict):
        model_name = model_name.get("name", str(model_name))
        _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Model name after dict conversion: {model_name}, type: {type(model_name)}")
    
    # Use register-based serial number if available, otherwise fall back to configuration
    serial_number = inverter_serial or serial or dongle
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Serial number selection: inverter_serial='{inverter_serial}', serial='{serial}', dongle='{dongle}', final='{serial_number}'")
    
    # Get appropriate device image
    device_image_url = get_device_image_url(model_code, model_name)
    
    # Create comprehensive device info
    device_info = DeviceInfo(
        identifiers={(DOMAIN, dongle)},
        name=f"LuxPower {model_name}",
        manufacturer="LuxPower",
        model=model_name,
        sw_version=firmware_version,
        hw_version=model_code if model_code != "Unknown" else "Unknown",
        serial_number=serial_number,
        configuration_url=None,
        suggested_area="Solar",
        icon="/local/luxpower-images/e-wifi-dongle-510x383.webp",
    )
    
    return device_info


def get_device_group_info(hass, dongle: str, device_group: str) -> dict:
    """
    Get device information for a specific device group.
    
    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number
        device_group: Device group identifier
        
    Returns:
        dict: Device information for the group
    """
    from homeassistant.helpers.entity import DeviceInfo
    
    # Find the entry_id for this dongle
    entry_id = None
    device_data = {}
    
    for e_id, data in hass.data.get(DOMAIN, {}).items():
        if data.get("DONGLE") == dongle:
            entry_id = e_id
            device_data = data
            break
    
    # Get device information from cached data
    model = device_data.get("model", "LuxPower Inverter")
    model_code = device_data.get("model_code", "Unknown")
    firmware_version = device_data.get("lux_firmware_version", "Unknown")
    inverter_serial = device_data.get("inverter_serial_number", None)
    
    # Debug logging for device group info
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Group: {device_group}, Dongle: {dongle}")
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Entry ID: {entry_id}")
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Device Data: {device_data}")
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Model: {model}, Model Code: {model_code}, Firmware: {firmware_version}, Serial: {inverter_serial}")
    
    # Get model name from model code - ensure we get the string name, not the dict
    from .const import get_model_name
    model_name = get_model_name(model_code) if model_code != "Unknown" else model
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Model name before check: {model_name}, type: {type(model_name)}")
    if isinstance(model_name, dict):
        model_name = model_name.get("name", str(model_name))
        _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Model name after dict conversion: {model_name}, type: {type(model_name)}")
    
    # Use register-based serial number if available, otherwise fall back to dongle
    serial_number = inverter_serial or dongle
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Serial number selection: inverter_serial='{inverter_serial}', dongle='{dongle}', final='{serial_number}'")
    _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Firmware from device_data: '{firmware_version}'")
    
    # Get appropriate device image and group icon
    device_image_url = get_device_image_url(model_code, model_name)
    group_icon = get_device_group_icon(device_group)
    
    # Device group names and identifiers
    device_group_info = {
        DEVICE_GROUP_PV: {
            "name": "PV System",
            "identifiers": {(DOMAIN, f"{dongle}_pv")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_GRID: {
            "name": "Grid",
            "identifiers": {(DOMAIN, f"{dongle}_grid")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_EPS: {
            "name": "EPS/Backup",
            "identifiers": {(DOMAIN, f"{dongle}_eps")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_GENERATOR: {
            "name": "Generator",
            "identifiers": {(DOMAIN, f"{dongle}_generator")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_BATTERY: {
            "name": "Battery",
            "identifiers": {(DOMAIN, f"{dongle}_battery")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_INVERTER: {
            "name": "Inverter",
            "identifiers": {(DOMAIN, f"{dongle}_inverter")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_TEMPERATURES: {
            "name": "Temperatures & Diagnostics",
            "identifiers": {(DOMAIN, f"{dongle}_temperatures")},
            "via_device": (DOMAIN, dongle),
        },
        DEVICE_GROUP_SETTINGS: {
            "name": "Settings & Schedules",
            "identifiers": {(DOMAIN, f"{dongle}_settings")},
            "via_device": (DOMAIN, dongle),
        },
    }
    
    if device_group not in device_group_info:
        _LOGGER.warning(f"Unknown device group: {device_group}")
        return None
    
    group_info = device_group_info[device_group]
    
    # For the "Inverter" group, use the inverter serial number (5xxx) instead of dongle serial
    if device_group == DEVICE_GROUP_INVERTER:
        group_serial_number = inverter_serial or dongle
        _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Using inverter serial for INVERTER group: '{group_serial_number}'")
    else:
        group_serial_number = serial_number
        _LOGGER.debug(f"🔍 DEVICE GROUP INFO DEBUG - Using standard serial for {device_group} group: '{group_serial_number}'")
    
    # Create device info for the group
    device_info = DeviceInfo(
        identifiers=group_info["identifiers"],
        name=group_info["name"],
        manufacturer="LuxPower",
        model=model_name,
        sw_version=firmware_version,
        hw_version=model_code if model_code != "Unknown" else "Unknown",
        serial_number=group_serial_number,
        via_device=group_info["via_device"],
        icon=group_icon,
    )
    
    return device_info


def should_enable_entity_for_model(entity_definition: dict, model_code: str) -> bool:
    """
    Determine if an entity should be enabled based on the model.
    
    Args:
        entity_definition: Entity definition dictionary
        model_code: Model code from inverter
        
    Returns:
        bool: True if entity should be enabled for this model
    """
    from .const import is_12k_model
    
    # Get the default enabled status
    default_enabled = entity_definition.get("enabled", True)
    
    # If explicitly disabled, don't enable
    if not default_enabled:
        return False
    
    # Check for 12K-specific entities
    name = entity_definition.get("name", "").lower()
    unique = entity_definition.get("unique", "").lower()
    register = entity_definition.get("register_address", 0)
    
    # 12K-specific features that should only be enabled for 12K models
    if is_12k_model(model_code):
        # Enable all 12K-specific features
        if any(keyword in name or keyword in unique for keyword in [
            "afci", "generator", "smart load", "peak shaving", "ac coupling", 
            "system power", "demand response", "load balancing"
        ]):
            return True
        
        # Enable entities with 12K-specific register addresses (including smart load 181-186 and generator 194-198)
        if register in [176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 198, 206, 207, 208, 220, 221, 222, 223]:
            return True
    
    # For non-12K models, disable 12K-specific features
    if not is_12k_model(model_code):
        if any(keyword in name or keyword in unique for keyword in [
            "afci", "generator", "smart load", "peak shaving", "ac coupling", 
            "system power", "demand response", "load balancing"
        ]):
            return False
        
        # Disable entities with 12K-specific register addresses (including smart load 181-186 and generator 194-198)
        if register in [176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 198, 206, 207, 208, 220, 221, 222, 223]:
            return False
    
    # For all other entities, use the default enabled status
    return default_enabled


def get_model_filtered_entities(entity_list: list, model_code: str) -> list:
    """
    Filter entity list based on model capabilities.
    
    Args:
        entity_list: List of entity definitions
        model_code: Model code from inverter
        
    Returns:
        list: Filtered entity list with appropriate entities enabled/disabled
    """
    from .const import is_12k_model
    
    filtered_entities = []
    
    for entity_definition in entity_list:
        # Create a copy of the entity definition
        filtered_entity = entity_definition.copy()
        
        # Check if this entity should be enabled for this model
        should_enable = should_enable_entity_for_model(entity_definition, model_code)
        
        # Update the enabled status
        filtered_entity["enabled"] = should_enable
        
        # Add to filtered list
        filtered_entities.append(filtered_entity)
    
    return filtered_entities


def get_current_model_code(hass, dongle: str) -> str:
    """
    Get the current model code for a dongle.
    
    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number
        
    Returns:
        str: Model code or "Unknown" if not found
    """
    from .const import DOMAIN
    
    for entry_id, data in hass.data.get(DOMAIN, {}).items():
        if data.get("DONGLE") == dongle:
            return data.get("model_code", "Unknown")
    
    return "Unknown"


def should_create_entity_for_model(entity_definition: dict, hass, dongle: str) -> bool:
    """
    Check if an entity should be created based on the detected model.
    
    Args:
        entity_definition: Entity definition dictionary
        hass: Home Assistant instance
        dongle: Dongle serial number
        
    Returns:
        bool: True if entity should be created for this model
    """
    # Get the current model code
    model_code = get_current_model_code(hass, dongle)
    
    # If model is unknown, create all entities (fallback)
    if model_code == "Unknown":
        return entity_definition.get("enabled", True)
    
    # Check if this entity should be enabled for this model
    return should_enable_entity_for_model(entity_definition, model_code)


def get_filtered_entity_definitions(entity_definitions: list, hass, dongle: str) -> list:
    """
    Get filtered entity definitions based on the detected model.
    
    Args:
        entity_definitions: List of entity definitions
        hass: Home Assistant instance
        dongle: Dongle serial number
        
    Returns:
        list: Filtered entity definitions
    """
    filtered_entities = []
    
    for entity_definition in entity_definitions:
        # Check if this entity should be created for this model
        if should_create_entity_for_model(entity_definition, hass, dongle):
            filtered_entities.append(entity_definition)
    
    return filtered_entities


def update_model_info_and_filter_entities(hass, dongle: str, model_code: str, entity_lists: dict) -> dict:
    """
    Update model information in hass data and filter entities based on model.
    
    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number
        model_code: Detected model code
        entity_lists: Dictionary of entity lists by platform
        
    Returns:
        dict: Filtered entity lists
    """
    from .const import DOMAIN, MODEL_MAP
    
    # Update model information in hass data
    for entry_id, data in hass.data.get(DOMAIN, {}).items():
        if data.get("DONGLE") == dongle:
            # Update model information
            from .const import get_model_name
            data["model_code"] = model_code
            data["model"] = get_model_name(model_code)
            _LOGGER.info(f"Updated model information: {model_code} -> {data['model']}")
            break
    
    # Filter entities based on model
    filtered_entity_lists = {}
    for platform, entity_list in entity_lists.items():
        filtered_entity_lists[platform] = get_model_filtered_entities(entity_list, model_code)
        _LOGGER.info(f"Filtered {platform} entities for model {model_code}: {len([e for e in filtered_entity_lists[platform] if e.get('enabled', True)])} enabled, {len([e for e in filtered_entity_lists[platform] if not e.get('enabled', True)])} disabled")
    
    return filtered_entity_lists


def get_entity_device_group(entity_definition: dict, hass=None) -> str:
    """
    Determine the device group for an entity based on its definition.
    
    Args:
        entity_definition: Entity definition dictionary
        hass: Home Assistant instance (optional, for checking config)
        
    Returns:
        str: Device group identifier
    """
    # Check if device grouping is enabled in configuration
    if hass:
        try:
            from .const import ATTR_LUX_DEVICE_GROUPING, PLACEHOLDER_LUX_DEVICE_GROUPING
            
            # Get the config entry for this integration
            config_entries = hass.config_entries.async_entries(DOMAIN)
            if config_entries:
                config_entry = config_entries[0]  # Use first entry
                device_grouping_enabled = config_entry.options.get(
                    ATTR_LUX_DEVICE_GROUPING, 
                    config_entry.data.get(ATTR_LUX_DEVICE_GROUPING, PLACEHOLDER_LUX_DEVICE_GROUPING)
                )
                
                # If device grouping is disabled, return the main inverter group
                if not device_grouping_enabled:
                    return DEVICE_GROUP_INVERTER
        except Exception as e:
            # If there's any error checking config, default to device grouping enabled
            _LOGGER.debug(f"Error checking device grouping config: {e}")
            pass
    # Get entity attributes for grouping
    attribute = entity_definition.get("attribute", "")
    name = entity_definition.get("name", "").lower()
    unique = entity_definition.get("unique", "").lower()
    
    # Debug logging for entity grouping
    _LOGGER.debug(f"Entity grouping - attribute: {attribute}, name: {name}, unique: {unique}")
    
    # PV System entities
    pv_attributes = {
        "v_pv_1", "v_pv_2", "v_pv_3", "p_pv_1", "p_pv_2", "p_pv_3", "p_pv_total",
        "e_pv_1_day", "e_pv_2_day", "e_pv_3_day", "e_pv_total", "e_pv_1_all", 
        "e_pv_2_all", "e_pv_3_all", "e_pv_all"
    }
    
    # Grid entities
    grid_attributes = {
        "v_ac_r", "v_ac_s", "v_ac_t", "f_ac", "p_to_grid", "p_to_user", "p_load", "p_load2", "p_load_ongrid",
        "e_to_grid_day", "e_to_user_day", "e_to_grid_all", "e_to_user_all", "e_load_day", "e_load_all_l",
        "pf", "rms_current", "self_consumption_rate", "grid_dependency"
    }
    
    # EPS/Backup entities
    eps_attributes = {
        "v_eps_r", "v_eps_s", "v_eps_t", "f_eps", "p_to_eps", "e_eps_day", "e_eps_all",
        "eps_L1_volt", "eps_L2_volt", "eps_L1_watt", "eps_L2_watt", "eps_L1_va", "eps_L2_va",
        "eps_L1_day", "eps_L2_day", "eps_L1_all", "eps_L2_all", "eps_L1_all_l", "eps_L1_all_h",
        "eps_L2_all_l", "eps_L2_all_h"
    }
    
    # Generator entities (12K only)
    generator_attributes = {
        "gen_input_volt", "gen_input_freq", "gen_power_watt", "gen_power_day", "gen_power_all",
        "gen_power_s", "gen_power_t", "gen_rated_power", "gen_chg_start_volt", "gen_chg_end_volt",
        "gen_chg_start_soc", "gen_chg_end_soc", "max_gen_chg_current", "gen_charge_type", "ac_input_type"
    }
    
    # Battery entities
    battery_attributes = {
        "v_bat", "soc", "p_charge", "p_discharge", "e_chg_day", "e_dischg_day", "e_chg_all", "e_dischg_all",
        "max_chg_curr", "max_dischg_curr", "bat_status_inv", "bat_count", "bat_capacity", "bat_current",
        "bat_cycle_count", "max_cell_temp", "min_cell_temp", "max_cell_volt", "min_cell_volt",
        "charge_volt_ref", "dischg_cut_volt"
    }
    
    # Temperature and diagnostic entities
    temperature_attributes = {
        "t_inner", "t_rad_1", "t_rad_2", "t_bat", "max_cell_temp", "min_cell_temp",
        "internal_fault", "fault_code", "warning_code", "uptime"
    }
    
    # Inverter power entities
    inverter_attributes = {
        "p_inv", "p_rec", "e_inv_day", "e_rec_day", "e_inv_all", "e_rec_all", "status",
        "v_bus_1", "v_bus_2"
    }
    
    # Advanced features (12K only) - moved to settings group
    advanced_attributes = {
        "afci_curr_ch1", "afci_curr_ch2", "afci_curr_ch3", "afci_curr_ch4", "afci_flags",
        "afci_arc_ch1", "afci_arc_ch2", "afci_arc_ch3", "afci_arc_ch4", "afci_max_arc_ch1",
        "afci_max_arc_ch2", "afci_max_arc_ch3", "afci_max_arc_ch4", "smart_load_start_soc",
        "smart_load_end_soc", "smart_load_start_volt", "smart_load_end_volt", "smart_load_soc_hysteresis",
        "smart_load_volt_hysteresis", "peak_shaving_power", "peak_shaving_soc", "peak_shaving_volt",
        "ac_couple_start_soc", "ac_couple_end_soc", "ac_couple_start_volt", "ac_couple_end_volt",
        "max_sys_power_12k", "sys_config_12k", "power_limit", "peak_shaving_effectiveness",
        "demand_response_capability", "load_balancing_score", "ac_charge_type", "output_priority", "work_mode", "ac_charge_mode",
        "polling_interval", "connection_quality"
    }
    
    # Check by attribute
    if attribute in pv_attributes:
        _LOGGER.debug(f"Entity {name} assigned to PV group by attribute {attribute}")
        return DEVICE_GROUP_PV
    elif attribute in grid_attributes:
        _LOGGER.debug(f"Entity {name} assigned to GRID group by attribute {attribute}")
        return DEVICE_GROUP_GRID
    elif attribute in eps_attributes:
        _LOGGER.debug(f"Entity {name} assigned to EPS group by attribute {attribute}")
        return DEVICE_GROUP_EPS
    elif attribute in generator_attributes:
        _LOGGER.debug(f"Entity {name} assigned to GENERATOR group by attribute {attribute}")
        return DEVICE_GROUP_GENERATOR
    elif attribute in battery_attributes:
        _LOGGER.debug(f"Entity {name} assigned to BATTERY group by attribute {attribute}")
        return DEVICE_GROUP_BATTERY
    elif attribute in temperature_attributes:
        _LOGGER.debug(f"Entity {name} assigned to TEMPERATURES group by attribute {attribute}")
        return DEVICE_GROUP_TEMPERATURES
    elif attribute in inverter_attributes:
        _LOGGER.debug(f"Entity {name} assigned to INVERTER group by attribute {attribute}")
        return DEVICE_GROUP_INVERTER
    elif attribute in advanced_attributes:
        _LOGGER.debug(f"Entity {name} assigned to SETTINGS group by attribute {attribute}")
        return DEVICE_GROUP_SETTINGS
    
    # Check by name patterns
    if "solar" in name or "pv" in name or "array" in name:
        _LOGGER.debug(f"Entity {name} assigned to PV group by name pattern")
        return DEVICE_GROUP_PV
    elif "grid" in name or "import" in name or "export" in name:
        _LOGGER.debug(f"Entity {name} assigned to GRID group by name pattern")
        return DEVICE_GROUP_GRID
    elif "eps" in name or "backup" in name:
        _LOGGER.debug(f"Entity {name} assigned to EPS group by name pattern")
        return DEVICE_GROUP_EPS
    elif "generator" in name or "gen" in name:
        _LOGGER.debug(f"Entity {name} assigned to GENERATOR group by name pattern")
        return DEVICE_GROUP_GENERATOR
    elif "battery" in name or "soc" in name or "charge" in name or "discharge" in name:
        _LOGGER.debug(f"Entity {name} assigned to BATTERY group by name pattern")
        return DEVICE_GROUP_BATTERY
    elif "temp" in name or "fault" in name or "warning" in name or "uptime" in name or "diagnostic" in name:
        _LOGGER.debug(f"Entity {name} assigned to TEMPERATURES group by name pattern")
        return DEVICE_GROUP_TEMPERATURES
    elif "afci" in name or "smart" in name or "peak" in name or "coupling" in name or "setting" in name or "config" in name:
        _LOGGER.debug(f"Entity {name} assigned to SETTINGS group by name pattern")
        return DEVICE_GROUP_SETTINGS
    
    # Check by unique ID patterns
    if "solar" in unique or "pv" in unique:
        return DEVICE_GROUP_PV
    elif "grid" in unique or "import" in unique or "export" in unique:
        return DEVICE_GROUP_GRID
    elif "eps" in unique or "backup" in unique:
        return DEVICE_GROUP_EPS
    elif "generator" in unique or "gen" in unique:
        return DEVICE_GROUP_GENERATOR
    elif "battery" in unique or "soc" in unique or "charge" in unique or "discharge" in unique:
        return DEVICE_GROUP_BATTERY
    elif "temp" in unique or "fault" in unique or "warning" in unique or "uptime" in unique or "diagnostic" in unique:
        return DEVICE_GROUP_TEMPERATURES
    elif "afci" in unique or "smart" in unique or "peak" in unique or "coupling" in unique or "setting" in unique or "config" in unique:
        return DEVICE_GROUP_SETTINGS
    
    # Default to settings for control entities (numbers, switches, times, buttons)
    etype = entity_definition.get("etype", "")
    if etype in ["LPNE", "LVSE", "LTTE", "LPBE"]:
        _LOGGER.debug(f"Entity {name} assigned to SETTINGS group by entity type {etype}")
        return DEVICE_GROUP_SETTINGS
    
    # Default to inverter for unknown entities
    _LOGGER.debug(f"Entity {name} assigned to INVERTER group as fallback")
    return DEVICE_GROUP_INVERTER
