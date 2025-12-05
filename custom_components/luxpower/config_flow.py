"""
Configuration flow for LuxPower integration.

This module handles the user interface for setting up and configuring
LuxPower inverter connections, including validation of connection parameters
and options flow for reconfiguration.
"""

import ipaddress
import asyncio
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

    Two-step wizard:
    - Step 1: choose connection type and minimal inputs
    - Validate: probe device and extract identifiers
    - Step 2: show detected info and options
    """

    VERSION = 1

    def __init__(self) -> None:
        self._data: dict = {}
        self._detected: dict = {}

    # ------------------------
    # Step 1 - basic connection
    # ------------------------
    async def async_step_user(self, user_input=None):
        errors: dict[str, str] = {}

        if user_input is not None:
            # Persist interim inputs, basic validation only
            connection_type = user_input.get("connection_type", "tcp")
            errors = self._validate_step1(user_input)
            if not errors:
                self._data.update(user_input)
                return await self.async_step_validate()

        # default values
        defaults = {**self._data}

        # Build schema dynamically based on selected connection type (if any)
        connection_type = (user_input or {}).get("connection_type", self._data.get("connection_type", "tcp"))
        schema = self._schema_for_type(connection_type, defaults)
        return self.async_show_form(step_id="user", data_schema=schema, errors=errors)

    def _schema_for_type(self, connection_type: str, defaults: dict) -> vol.Schema:
        base = {
            vol.Required("connection_type", default=connection_type): vol.In({
                "tcp": "TCP Direct",
                "mqtt": "MQTT Bridge",
                "serial": "USB Serial/RS485",
            })
        }

        if connection_type == "tcp":
            base.update(
                {
                    vol.Required(ATTR_LUX_HOST, default=defaults.get(ATTR_LUX_HOST, PLACEHOLDER_LUX_HOST)): str,
                    vol.Optional(ATTR_LUX_PORT, default=defaults.get(ATTR_LUX_PORT, PLACEHOLDER_LUX_PORT)): int,
                    vol.Required(ATTR_LUX_DONGLE_SERIAL, default=defaults.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
                    vol.Optional(ATTR_LUX_USE_SERIAL, default=defaults.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)): bool,
                    vol.Optional(ATTR_LUX_SERIAL_NUMBER, default=defaults.get(ATTR_LUX_SERIAL_NUMBER, "")): str,
                }
            )
        elif connection_type == "mqtt":
            base.update(
                {
                    vol.Required("broker_host", default=defaults.get("broker_host", "")): str,
                    vol.Optional("broker_port", default=defaults.get("broker_port", 1883)): int,
                    vol.Optional("username", default=defaults.get("username", "")): str,
                    vol.Optional("password", default=defaults.get("password", "")): str,
                    vol.Optional("topic_prefix", default=defaults.get("topic_prefix", "luxpower")): str,
                    vol.Required(ATTR_LUX_DONGLE_SERIAL, default=defaults.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
                }
            )
        else:  # serial
            base.update(
                {
                    vol.Required("serial_port", default=defaults.get("serial_port", "/dev/ttyUSB0")): str,
                    vol.Required(ATTR_LUX_DONGLE_SERIAL, default=defaults.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
                }
            )

        return vol.Schema(base)

    def _validate_step1(self, user_input: dict) -> dict:
        errors: dict[str, str] = {}
        ctype = user_input.get("connection_type", "tcp")

        # Basic per-type validation
        if ctype == "tcp":
            if not host_valid(user_input.get(ATTR_LUX_HOST, "")):
                errors[ATTR_LUX_HOST] = "host_error"
            dongle_serial = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            if len(dongle_serial) < 8 or len(dongle_serial) > 12:
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
            use_sn = user_input.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)
            if use_sn:
                sn = user_input.get(ATTR_LUX_SERIAL_NUMBER, PLACEHOLDER_LUX_SERIAL_NUMBER)
                if len(sn) != 10 or sn == PLACEHOLDER_LUX_SERIAL_NUMBER:
                    errors[ATTR_LUX_SERIAL_NUMBER] = "serial_error"
                    errors[ATTR_LUX_USE_SERIAL] = "use_serial_error"

            # Duplicate dongle check (existing entries)
            if self.hass.data.get(DOMAIN):
                for entry in self.hass.data[DOMAIN].values():
                    if entry.get("DONGLE") == dongle_serial:
                        errors[ATTR_LUX_DONGLE_SERIAL] = "exist_error"

        elif ctype == "mqtt":
            if not user_input.get("broker_host"):
                errors["broker_host"] = "required"
            dongle_serial = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            if len(dongle_serial) < 8 or len(dongle_serial) > 12:
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
        else:  # serial
            if not user_input.get("serial_port"):
                errors["serial_port"] = "required"
            dongle_serial = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            if len(dongle_serial) < 8 or len(dongle_serial) > 12:
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"

        return errors

    # ------------------------
    # Validate - probe device
    # ------------------------
    async def async_step_validate(self, user_input=None):
        errors: dict[str, str] = {}
        try:
            self._detected = await self._probe_connection(self._data)
        except asyncio.TimeoutError:
            errors["base"] = "cannot_connect"
        except Exception:
            errors["base"] = "cannot_connect"

        if errors:
            # Go back to user step with prior inputs preserved
            schema = self._schema_for_type(self._data.get("connection_type", "tcp"), self._data)
            return self.async_show_form(step_id="user", data_schema=schema, errors=errors)

        return await self.async_step_options()

    async def _probe_connection(self, data: dict) -> dict:
        """Attempt a lightweight probe of the device and return identifiers.

        Returns a dict with keys: serial_number, model, model_code (best-effort).
        """
        ctype = data.get("connection_type", "tcp")
        result = {"serial_number": "", "model": "", "model_code": ""}

        if ctype == "tcp":
            # Try to establish a TCP connection and close immediately
            host = data.get(ATTR_LUX_HOST)
            port = int(data.get(ATTR_LUX_PORT, PLACEHOLDER_LUX_PORT))
            import asyncio as _asyncio
            conn = await _asyncio.wait_for(_asyncio.open_connection(host, port), timeout=8)
            reader, writer = conn
            writer.close()
            try:
                await writer.wait_closed()
            except Exception:
                pass
            # Best effort; detailed model/serial extraction requires protocol exchange
            result["serial_number"] = data.get(ATTR_LUX_SERIAL_NUMBER, "")
        else:
            # For MQTT/Serial we don't actively probe here
            await asyncio.sleep(0)

        return result

    # ------------------------
    # Step 2 - options
    # ------------------------
    async def async_step_options(self, user_input=None):
        errors: dict[str, str] = {}

        if user_input is not None:
            # Merge and finalize
            final = {**self._data, **self._detected, **user_input}
            # Normalize defaults
            if final.get(ATTR_LUX_PORT) is None:
                final[ATTR_LUX_PORT] = PLACEHOLDER_LUX_PORT
            title_sn = self._data.get(ATTR_LUX_DONGLE_SERIAL, "?")
            return self.async_create_entry(title=f"LuxPower - ({title_sn})", data=final)

        ctype = self._data.get("connection_type", "tcp")
        schema = self._options_schema(ctype, {**self._data, **self._detected})
        return self.async_show_form(step_id="options", data_schema=schema, errors=errors, description_placeholders={
            "serial": self._detected.get("serial_number", ""),
            "model": self._detected.get("model", ""),
        })

    def _options_schema(self, connection_type: str, defaults: dict) -> vol.Schema:
        # TCP/Serial: full options; MQTT: only read_only_mode
        if connection_type in ("tcp", "serial"):
            schema = {
                vol.Optional(ATTR_LUX_USE_SERIAL, default=defaults.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)): bool,
                vol.Optional(ATTR_LUX_RESPOND_TO_HEARTBEAT, default=defaults.get(ATTR_LUX_RESPOND_TO_HEARTBEAT, PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)): bool,
                vol.Optional(ATTR_LUX_AUTO_REFRESH, default=defaults.get(ATTR_LUX_AUTO_REFRESH, PLACEHOLDER_LUX_AUTO_REFRESH)): bool,
                vol.Optional(ATTR_LUX_REFRESH_INTERVAL, default=defaults.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)): vol.All(int, vol.Range(min=30, max=120)),
                vol.Optional(ATTR_LUX_DEVICE_GROUPING, default=defaults.get(ATTR_LUX_DEVICE_GROUPING, PLACEHOLDER_LUX_DEVICE_GROUPING)): bool,
                vol.Optional(ATTR_LUX_ADAPTIVE_POLLING, default=defaults.get(ATTR_LUX_ADAPTIVE_POLLING, PLACEHOLDER_LUX_ADAPTIVE_POLLING)): bool,
                vol.Optional(ATTR_LUX_READ_ONLY_MODE, default=defaults.get(ATTR_LUX_READ_ONLY_MODE, PLACEHOLDER_LUX_READ_ONLY_MODE)): bool,
            }
            # Conditionally show reconnection_delay when adaptive polling disabled
            adaptive_enabled = defaults.get(ATTR_LUX_ADAPTIVE_POLLING, PLACEHOLDER_LUX_ADAPTIVE_POLLING)
            if not adaptive_enabled:
                schema[vol.Optional(ATTR_LUX_RECONNECTION_DELAY, default=defaults.get(ATTR_LUX_RECONNECTION_DELAY, PLACEHOLDER_LUX_RECONNECTION_DELAY))] = vol.All(int, vol.Range(min=MIN_RECONNECTION_DELAY, max=MAX_RECONNECTION_DELAY))  # fmt: skip
            return vol.Schema(schema)
        else:
            return vol.Schema({
                vol.Optional(ATTR_LUX_READ_ONLY_MODE, default=defaults.get(ATTR_LUX_READ_ONLY_MODE, PLACEHOLDER_LUX_READ_ONLY_MODE)): bool,
            })

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
        sn = user_input.get(ATTR_LUX_SERIAL_NUMBER, PLACEHOLDER_LUX_SERIAL_NUMBER)
        use_sn = user_input.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)
        if use_sn:
            if len(sn) != 10 or sn == PLACEHOLDER_LUX_SERIAL_NUMBER:
                errors[ATTR_LUX_SERIAL_NUMBER] = "serial_error"
                errors[ATTR_LUX_USE_SERIAL] = "use_serial_error"

        return errors
