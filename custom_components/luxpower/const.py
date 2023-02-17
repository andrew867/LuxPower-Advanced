DOMAIN = 'luxpower'

# Config UI Attributes
ATTR_LUX_HOST = "lux_host"
ATTR_LUX_PORT = "lux_port"
ATTR_LUX_DONGLE_SERIAL = "lux_dongle_serial"
ATTR_LUX_SERIAL_NUMBER = "lux_serial_number"
ATTR_LUX_USE_SERIAL = "lux_use_serial"

# Placeholder values
PLACEHOLDER_LUX_HOST = ""
PLACEHOLDER_LUX_PORT = 8000
PLACEHOLDER_LUX_DONGLE_SERIAL = ""
PLACEHOLDER_LUX_SERIAL_NUMBER = ""
PLACEHOLDER_LUX_USE_SERIAL = False

EVENT_DATA_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_event"
EVENT_DATA_BANK0_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_bank0_event"
EVENT_DATA_BANK1_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_bank1_event"
EVENT_DATA_BANK2_FORMAT = "{DOMAIN}_{DONGLE}_data_receive_bank2_event"
EVENT_REGISTER_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_event"
EVENT_REGISTER_21_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_21_event"
EVENT_REGISTER_BANK0_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_bank0_event"
EVENT_REGISTER_BANK1_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_bank1_event"
EVENT_REGISTER_BANK2_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_bank2_event"
EVENT_REGISTER_BANK3_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_bank3_event"
EVENT_REGISTER_BANK4_FORMAT = "{DOMAIN}_{DONGLE}_register_receive_bank4_event"
CLIENT_DAEMON_FORMAT = "{DOMAIN}_{DONGLE}_client_daemon"

