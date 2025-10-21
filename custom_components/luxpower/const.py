"""

Defines constants used throughout the Luxpower integration.


"""

UA = "unavailable"

DOMAIN = "luxpower"
VERSION = "2025.10.1-dev"

# Config UI Attributes
ATTR_LUX_HOST = "lux_host"
ATTR_LUX_PORT = "lux_port"
ATTR_LUX_DONGLE_SERIAL = "lux_dongle_serial"
ATTR_LUX_SERIAL_NUMBER = "lux_serial_number"
ATTR_LUX_USE_SERIAL = "lux_use_serial"
ATTR_LUX_RESPOND_TO_HEARTBEAT = "lux_respond_to_heartbeat"
ATTR_LUX_AUTO_REFRESH = "lux_auto_refresh"
ATTR_LUX_REFRESH_INTERVAL = "lux_refresh_interval"
ATTR_LUX_REFRESH_BANK_COUNT = "lux_refresh_bank_count"
ATTR_LUX_DEVICE_GROUPING = "lux_device_grouping"
ATTR_LUX_RATED_POWER = "lux_rated_power"

# Placeholder values
PLACEHOLDER_LUX_HOST = ""
PLACEHOLDER_LUX_PORT = 8000
PLACEHOLDER_LUX_DONGLE_SERIAL = ""
PLACEHOLDER_LUX_SERIAL_NUMBER = "0000000000"
PLACEHOLDER_LUX_USE_SERIAL = False
PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT = False
PLACEHOLDER_LUX_AUTO_REFRESH = False
PLACEHOLDER_LUX_REFRESH_INTERVAL = 120
PLACEHOLDER_LUX_REFRESH_BANK_COUNT = 6
PLACEHOLDER_LUX_DEVICE_GROUPING = True
PLACEHOLDER_LUX_RATED_POWER = 0  # 0 = auto-detect from model

# Default values for service calls
DEFAULT_CHARGE_DURATION_MINUTES = 180
DEFAULT_CHARGE_SLOT = 1
DEFAULT_DONGLE_SERIAL = "XXXXXXXXXX"
DEFAULT_SERIAL_NUMBER = "XXXXXXXXXX"

# Service call constants
SERVICE_REFRESH_REGISTER_BANK = "luxpower_refresh_register_bank"
SERVICE_REFRESH_REGISTERS = "luxpower_refresh_registers"
SERVICE_REFRESH_HOLDINGS = "luxpower_refresh_holdings"
SERVICE_RECONNECT = "luxpower_reconnect"
SERVICE_RESTART = "luxpower_restart"
SERVICE_RESET_SETTINGS = "luxpower_reset_settings"
SERVICE_SYNC_TIME = "luxpower_synctime"
SERVICE_START_CHARGING = "luxpower_start_charging"
SERVICE_STOP_CHARGING = "luxpower_stop_charging"

# Entity categories
ENTITY_CATEGORY_DIAGNOSTIC = "diagnostic"
ENTITY_CATEGORY_CONFIG = "config"

# Device grouping constants (v0.2.0+)
DEVICE_GROUP_PV = "pv_system"           # Solar panel monitoring and MPPT controls (25 entities)
DEVICE_GROUP_GRID = "grid"              # Utility grid connection and import/export data (42 entities)
DEVICE_GROUP_EPS = "eps_backup"         # Emergency Power Supply / backup load outputs (18 entities)
DEVICE_GROUP_GENERATOR = "generator"    # Backup generator monitoring and controls (12 entities)
DEVICE_GROUP_BATTERY = "battery"        # BMS data, cell voltages, temperatures, and battery controls (62 entities)
DEVICE_GROUP_TEMPERATURES = "temperatures"  # Temperature sensors, fault codes, and diagnostic data (12 entities)
DEVICE_GROUP_SETTINGS = "settings"      # Configuration settings, timing schedules, and power management (56 entities)
DEVICE_GROUP_INVERTER = "inverter"      # Main inverter device (fallback for ungrouped entities)

# Mapping of firmware model codes to inverter models and rated power
MODEL_MAP = {
    "AAAA": {"name": "LXP 3-6K Hybrid (Standard)", "rated_power": 6000},
    "AAAB": {"name": "LXP 3-6K Hybrid (Parallel)", "rated_power": 6000},
    "BAAA": {"name": "LXP-3600 ACS (Standard)", "rated_power": 3600},
    "BAAB": {"name": "LXP-3600 ACS (Parallel)", "rated_power": 3600},
    "CBAA": {"name": "SNA 3000-6000", "rated_power": 6000},
    "EAAB": {"name": "LXP-LB-EU 7K", "rated_power": 7000},
    "CCAA": {"name": "SNA-US 6000", "rated_power": 6000},
    "FAAB": {"name": "LXP-LB-8-12K", "rated_power": 12000},
    "ACAB": {"name": "GEN-LB-EU 3-6K", "rated_power": 6000},
    "HAAA": {"name": "GEB-LB-EU 7-10K", "rated_power": 10000},
    "CFAA": {"name": "SNA 12K", "rated_power": 12000},
    "CEAA": {"name": "SNA 12K-US", "rated_power": 12000},
    # Additional model codes found in LuxPowerTek cloud UI
    "BEAA": {"name": "LXP Variant", "rated_power": 6000},  # Default to 6K for variants
    "DAAA": {"name": "LXP Variant", "rated_power": 6000},  # Default to 6K for variants
}

def is_12k_model(model_code: str) -> bool:
    """Check if model code is a 12K model."""
    if not model_code:
        return False
    return model_code in ("CFAA", "CEAA", "FAAB")

def get_model_rated_power(model_code: str) -> int:
    """Get the rated power for a model code."""
    if not model_code or model_code not in MODEL_MAP:
        return 6000  # Default to 6K if unknown
    return MODEL_MAP[model_code].get("rated_power", 6000)

def get_model_name(model_code: str) -> str:
    """Get the model name for a model code."""
    if not model_code or model_code not in MODEL_MAP:
        return "Unknown Inverter"
    return MODEL_MAP[model_code].get("name", "Unknown Inverter")

EVENT_DATA_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_{GROUP}_event"
EVENT_REGISTER_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_{GROUP}_event"
EVENT_UNAVAILABLE_FORMAT = "{DOMAIN}_{DONGLE}_unavailable_event"
CLIENT_DAEMON_FORMAT = "{DOMAIN}_{DONGLE}_client_daemon"
