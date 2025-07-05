"""

Defines constants used throughout the Luxpower integration.


"""

UA = "unavailable"

DOMAIN = "luxpower"
VERSION = "2025.6.0-beta"

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

# Placeholder values
PLACEHOLDER_LUX_HOST = ""
PLACEHOLDER_LUX_PORT = 8000
PLACEHOLDER_LUX_DONGLE_SERIAL = ""
PLACEHOLDER_LUX_SERIAL_NUMBER = "0000000000"
PLACEHOLDER_LUX_USE_SERIAL = False
PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT = False
PLACEHOLDER_LUX_AUTO_REFRESH = False
PLACEHOLDER_LUX_REFRESH_INTERVAL = 120
PLACEHOLDER_LUX_REFRESH_BANK_COUNT = 2

EVENT_DATA_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_{GROUP}_event"
EVENT_REGISTER_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_{GROUP}_event"
EVENT_UNAVAILABLE_FORMAT = "{DOMAIN}_{DONGLE}_unavailable_event"
CLIENT_DAEMON_FORMAT = "{DOMAIN}_{DONGLE}_client_daemon"
