"""
Configuration flow for LuxPower integration.

This module handles the user interface for setting up and configuring
LuxPower inverter connections, including validation of connection parameters
and options flow for reconfiguration.
"""

import ipaddress
import logging
import re

import voluptuous as vol
from homeassistant import config_entries
from homeassistant.core import callback

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_RESPOND_TO_HEARTBEAT,
    ATTR_LUX_AUTO_REFRESH,
    ATTR_LUX_REFRESH_INTERVAL,
    ATTR_LUX_REFRESH_BANK_COUNT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    ATTR_LUX_DEVICE_GROUPING,
    ATTR_LUX_RATED_POWER,
    ATTR_LUX_ADAPTIVE_POLLING,
    ATTR_LUX_RECONNECTION_DELAY,
    ATTR_LUX_READ_ONLY_MODE,
    DOMAIN,
    PLACEHOLDER_LUX_DONGLE_SERIAL,
    PLACEHOLDER_LUX_HOST,
    PLACEHOLDER_LUX_PORT,
    PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT,
    PLACEHOLDER_LUX_AUTO_REFRESH,
    PLACEHOLDER_LUX_REFRESH_INTERVAL,
    PLACEHOLDER_LUX_REFRESH_BANK_COUNT,
    PLACEHOLDER_LUX_SERIAL_NUMBER,
    PLACEHOLDER_LUX_USE_SERIAL,
    PLACEHOLDER_LUX_DEVICE_GROUPING,
    PLACEHOLDER_LUX_RATED_POWER,
    PLACEHOLDER_LUX_ADAPTIVE_POLLING,
    PLACEHOLDER_LUX_RECONNECTION_DELAY,
    PLACEHOLDER_LUX_READ_ONLY_MODE,
    MIN_RECONNECTION_DELAY,
    MAX_RECONNECTION_DELAY,
)

_LOGGER = logging.getLogger(__name__)


def host_valid(host):
    """Return True if hostname or IP address is valid."""
    try:
        return ipaddress.ip_address(host).version in (4, 6)
    except ValueError:
        disallowed = re.compile(r"[^a-zA-Z\d\-]")
        return all(x and not disallowed.search(x) for x in host.split("."))


class LuxConfigFlow(config_entries.ConfigFlow, domain=DOMAIN):  # type:ignore
    """
    Handle LuxPower configuration flow.
    
    This class manages the initial setup process for LuxPower inverters,
    including validation of host addresses, dongle serials, and other
    configuration parameters.
    """

    VERSION = 1

    async def async_step_user(self, user_input=None):
        errors = {}

        if user_input is not None:
            user_input[ATTR_LUX_PORT] = PLACEHOLDER_LUX_PORT
            if not user_input[ATTR_LUX_USE_SERIAL]:
                user_input[ATTR_LUX_SERIAL_NUMBER] = PLACEHOLDER_LUX_SERIAL_NUMBER
            # Omitting bank count from initial setup:
            user_input[ATTR_LUX_REFRESH_BANK_COUNT] = PLACEHOLDER_LUX_REFRESH_BANK_COUNT
            errors = self._validate_user_input(user_input)
            if not errors:
                _LOGGER.info("LuxConfigFlow: saving options ")
                return self.async_create_entry(
                    title=f"LuxPower - ({user_input[ATTR_LUX_DONGLE_SERIAL]})",
                    data=user_input,
                )

        config_entry = user_input if user_input else {}

        # Check if luxpower device already exists to use sn in entities
        if (
            self.hass.data.get(DOMAIN, None) is not None
            and self.hass.data[DOMAIN].__len__() > 0
        ):
            placeholder_use_serial = True
        else:
            placeholder_use_serial = PLACEHOLDER_LUX_USE_SERIAL

        # Specify items in the order they are to be displayed in the UI
        schema = {
            vol.Required("lux_host", default=config_entry.get("lux_host", PLACEHOLDER_LUX_HOST)): str,
            vol.Required("lux_dongle_serial", default=config_entry.get("lux_dongle_serial", PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
        }  # fmt: skip

        if config_entry.get("lux_use_serial", placeholder_use_serial):
            schema[vol.Optional("lux_serial_number", default=config_entry.get("lux_serial_number", ""))] = str  # fmt: skip

        schema.update({
            vol.Optional("lux_use_serial", default=config_entry.get("lux_use_serial", placeholder_use_serial)): bool,
            vol.Optional("lux_respond_to_heartbeat", default=config_entry.get("lux_respond_to_heartbeat", PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)): bool,
            vol.Optional("lux_auto_refresh", default=config_entry.get("lux_auto_refresh", PLACEHOLDER_LUX_AUTO_REFRESH)): bool,
            vol.Optional("lux_refresh_interval", default=config_entry.get("lux_refresh_interval", PLACEHOLDER_LUX_REFRESH_INTERVAL)): vol.All(int, vol.Range(min=30, max=120)),
            vol.Optional("lux_refresh_bank_count", default=config_entry.get("lux_refresh_bank_count", 6)): vol.All(int, vol.Range(min=1, max=6)),
            vol.Optional("lux_device_grouping", default=config_entry.get("lux_device_grouping", PLACEHOLDER_LUX_DEVICE_GROUPING)): bool,
            vol.Optional("lux_rated_power", default=config_entry.get("lux_rated_power", PLACEHOLDER_LUX_RATED_POWER)): vol.All(int, vol.Range(min=0, max=15000)),
            vol.Optional("lux_adaptive_polling", default=config_entry.get("lux_adaptive_polling", PLACEHOLDER_LUX_ADAPTIVE_POLLING)): bool,
            vol.Optional("lux_read_only_mode", default=config_entry.get("lux_read_only_mode", PLACEHOLDER_LUX_READ_ONLY_MODE)): bool,
        })

        # Add retry attempts field only if adaptive polling is disabled
        adaptive_polling_enabled = config_entry.get("lux_adaptive_polling", PLACEHOLDER_LUX_ADAPTIVE_POLLING)
        if not adaptive_polling_enabled:
            schema.update({
                vol.Optional("lux_reconnection_delay", default=config_entry.get("lux_reconnection_delay", PLACEHOLDER_LUX_RECONNECTION_DELAY)): vol.All(int, vol.Range(min=MIN_RECONNECTION_DELAY, max=MAX_RECONNECTION_DELAY)),
            })  # fmt: skip
        data_schema = vol.Schema(schema)
        return self.async_show_form(
            step_id="user",
            data_schema=data_schema,
            errors=errors,
        )

    def _validate_user_input(self, user_input):
        errors = {}
        if not host_valid(user_input["lux_host"]):
            errors["lux_host"] = "host_error"
        # Allow dongle serials with 8-12 characters
        dongle_serial = user_input["lux_dongle_serial"]
        if len(dongle_serial) < 8 or len(dongle_serial) > 12:
            errors["lux_dongle_serial"] = "dongle_error"
        ri = user_input.get("lux_refresh_interval", PLACEHOLDER_LUX_REFRESH_INTERVAL)
        if type(ri) is not int or ri < 30 or ri > 120:
            errors["lux_refresh_interval"] = "refresh_interval_error"
        # Check if the dongle serial already exists in the configuration
        if (
            self.hass.data.get(DOMAIN, None) is not None
            and self.hass.data[DOMAIN].__len__() > 0
        ):
            for entry in self.hass.data[DOMAIN]:
                entry_data = self.hass.data[DOMAIN][entry]
                if entry_data["DONGLE"] == user_input["lux_dongle_serial"]:
                    errors["lux_dongle_serial"] = "exist_error"
        use_sn = user_input.get("lux_use_serial", PLACEHOLDER_LUX_USE_SERIAL)
        if use_sn:
            sn = user_input.get("lux_serial_number", PLACEHOLDER_LUX_SERIAL_NUMBER)
            if len(sn) != 10 or sn == PLACEHOLDER_LUX_SERIAL_NUMBER:
                errors["lux_serial_number"] = "serial_error"
                errors["lux_use_serial"] = "use_serial_error"

        return errors


    @staticmethod
    @callback
    def async_get_options_flow(config_entry):
        return OptionsFlowHandler(config_entry)


class OptionsFlowHandler(config_entries.OptionsFlow):
    """
    Handle LuxPower options flow.
    
    This class manages the reconfiguration of existing LuxPower integrations,
    allowing users to modify settings without removing and re-adding the integration.
    """

    def __init__(self, config_entry):
        """Initialize options flow."""
        # Store config entry data instead of the entry itself
        self.config_entry_data = config_entry.data
        self.config_entry_options = config_entry.options

    def _is_valid_ip(self, ip: str) -> bool:
        """Validate IP address or hostname format."""
        import ipaddress
        try:
            # First try to validate as IP address
            ipaddress.ip_address(ip)
            return True
        except ValueError:
            # If not an IP, check if it's a valid hostname
            import re
            # Allow hostnames with dots, hyphens, and alphanumeric characters
            hostname_pattern = r'^[a-zA-Z0-9]([a-zA-Z0-9\-\.]*[a-zA-Z0-9])?$'
            return bool(re.match(hostname_pattern, ip)) and len(ip) <= 253

    def _is_valid_dongle_serial(self, serial: str) -> bool:
        """Validate dongle serial format (alphanumeric, 8-12 characters)."""
        import re
        # Allow alphanumeric dongle serials with 8-12 characters
        return bool(re.match(r'^[A-Za-z0-9]{8,12}$', serial))

    def _is_valid_serial_number(self, serial: str) -> bool:
        """Validate serial number format (SN followed by 8 digits)."""
        import re
        return bool(re.match(r'^SN\d{8}$', serial))

    async def async_step_init(self, user_input=None):
        """Manage the options."""
        return await self.async_step_user(user_input)

    async def async_step_user(self, user_input):
        errors = {}

        if user_input is not None:
            user_input["lux_port"] = PLACEHOLDER_LUX_PORT
            if not user_input["lux_use_serial"]:
                user_input["lux_serial_number"] = PLACEHOLDER_LUX_SERIAL_NUMBER
            
            # Enhanced validation with real-time feedback
            if not user_input.get("lux_host") or not user_input["lux_host"].strip():
                errors["lux_host"] = "required"
            elif not self._is_valid_ip(user_input["lux_host"]):
                errors["lux_host"] = "invalid_ip"
                
            if not user_input.get("lux_dongle_serial") or not user_input["lux_dongle_serial"].strip():
                errors["lux_dongle_serial"] = "required"
            elif not self._is_valid_dongle_serial(user_input["lux_dongle_serial"]):
                errors["lux_dongle_serial"] = "invalid_format"
                
            if user_input.get("lux_use_serial") and not user_input.get("lux_serial_number"):
                errors["lux_serial_number"] = "required_when_serial_enabled"
            elif user_input.get("lux_use_serial") and not self._is_valid_serial_number(user_input.get("lux_serial_number", "")):
                errors["lux_serial_number"] = "invalid_format"
            
            # Also run the original validation
            original_errors = self._validate_user_input(user_input)
            errors.update(original_errors)
            
            if not errors:
                _LOGGER.info("OptionsFlowHandler: saving options ")
                return self.async_create_entry(title="LuxPower ()", data=user_input)

        config_entry = self.config_entry_data
        if len(self.config_entry_options) > 0:
            config_entry = self.config_entry_options
        if user_input:
            config_entry = user_input

        schema = {
            vol.Required("lux_host", default=config_entry.get("lux_host", PLACEHOLDER_LUX_HOST)): str,
            vol.Required("lux_dongle_serial", default=config_entry.get("lux_dongle_serial", PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
        }  # fmt: skip

        if config_entry.get("lux_use_serial", PLACEHOLDER_LUX_USE_SERIAL):
            schema[vol.Optional("lux_serial_number", default=config_entry.get("lux_serial_number", ""))] = str  # fmt: skip

        schema.update({
            vol.Optional("lux_use_serial", default=config_entry.get("lux_use_serial", PLACEHOLDER_LUX_USE_SERIAL)): bool,
            vol.Optional("lux_respond_to_heartbeat", default=config_entry.get("lux_respond_to_heartbeat", PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)): bool,
            vol.Optional("lux_auto_refresh", default=config_entry.get("lux_auto_refresh", PLACEHOLDER_LUX_AUTO_REFRESH)): bool,
            vol.Optional("lux_refresh_interval", default=config_entry.get("lux_refresh_interval", PLACEHOLDER_LUX_REFRESH_INTERVAL)): vol.All(int, vol.Range(min=30, max=120)),
            vol.Optional("lux_refresh_bank_count", default=config_entry.get("lux_refresh_bank_count", PLACEHOLDER_LUX_REFRESH_BANK_COUNT)): vol.All(int, vol.Range(min=1, max=6)),
            vol.Optional("lux_device_grouping", default=config_entry.get("lux_device_grouping", PLACEHOLDER_LUX_DEVICE_GROUPING)): bool,
            vol.Optional("lux_rated_power", default=config_entry.get("lux_rated_power", PLACEHOLDER_LUX_RATED_POWER)): vol.All(int, vol.Range(min=0, max=15000)),
            vol.Optional("lux_adaptive_polling", default=config_entry.get("lux_adaptive_polling", PLACEHOLDER_LUX_ADAPTIVE_POLLING)): bool,
            vol.Optional("lux_read_only_mode", default=config_entry.get("lux_read_only_mode", PLACEHOLDER_LUX_READ_ONLY_MODE)): bool,
        })

        # Add retry attempts field only if adaptive polling is disabled
        adaptive_polling_enabled = config_entry.get("lux_adaptive_polling", PLACEHOLDER_LUX_ADAPTIVE_POLLING)
        if not adaptive_polling_enabled:
            schema.update({
                vol.Optional("lux_reconnection_delay", default=config_entry.get("lux_reconnection_delay", PLACEHOLDER_LUX_RECONNECTION_DELAY)): vol.All(int, vol.Range(min=MIN_RECONNECTION_DELAY, max=MAX_RECONNECTION_DELAY)),
            })  # fmt: skip

        data_schema = vol.Schema(schema)
        return self.async_show_form(
            step_id="user",
            data_schema=data_schema,
            errors=errors,
        )

    def _validate_user_input(self, user_input):
        errors = {}
        if not host_valid(user_input[ATTR_LUX_HOST]):
            errors[ATTR_LUX_HOST] = "host_error"
        # Allow dongle serials with 8-12 characters
        dongle_serial = user_input[ATTR_LUX_DONGLE_SERIAL]
        if len(dongle_serial) < 8 or len(dongle_serial) > 12:
            errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
        ri = user_input.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
        if type(ri) is not int or ri < 30 or ri > 120:
            errors[ATTR_LUX_REFRESH_INTERVAL] = "refresh_interval_error"
        bc = user_input.get(
            ATTR_LUX_REFRESH_BANK_COUNT, PLACEHOLDER_LUX_REFRESH_BANK_COUNT
        )
        if type(bc) is not int or bc < 1 or bc > 6:
            errors[ATTR_LUX_REFRESH_BANK_COUNT] = "refresh_bank_count_error"
        sn = user_input.get(ATTR_LUX_SERIAL_NUMBER, PLACEHOLDER_LUX_SERIAL_NUMBER)
        use_sn = user_input.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)
        if use_sn:
            if len(sn) != 10 or sn == PLACEHOLDER_LUX_SERIAL_NUMBER:
                errors[ATTR_LUX_SERIAL_NUMBER] = "serial_error"
                errors[ATTR_LUX_USE_SERIAL] = "use_serial_error"

        return errors
