DOMAIN = 'luxpower'

# Config UI Attributes
ATTR_LUX_HOST = "lux_host"
ATTR_LUX_PORT = "lux_port"
ATTR_LUX_DONGLE_SERIAL = "lux_dongle_serial"
ATTR_LUX_SERIAL_NUMBER = "lux_serial_number"
ATTR_LUX_USE_DONGLE = "lux_use_dongle"

# Placeholder values
PLACEHOLDER_LUX_HOST = ""
PLACEHOLDER_LUX_PORT = 8000
PLACEHOLDER_LUX_DONGLE_SERIAL = ""
PLACEHOLDER_LUX_SERIAL_NUMBER = ""
PLACEHOLDER_LUX_USE_DONGLE = False

EVENT_DATA_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_event"
EVENT_REGISTER_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_event"
CLIENT_DAEMON_FORMAT = "{DOMAIN}_{DONGLE}_client_daemon"

