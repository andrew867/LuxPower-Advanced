"""

This is a docstring placeholder.

This is where we will describe what this module does

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

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    VERSION = 1
    CONNECTION_CLASS = config_entries.CONN_CLASS_LOCAL_PUSH

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
        if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
            placeholder_use_serial = True
        else:
            placeholder_use_serial = PLACEHOLDER_LUX_USE_SERIAL

        # Specify items in the order they are to be displayed in the UI
        schema = {
            vol.Required(ATTR_LUX_HOST, default=config_entry.get(ATTR_LUX_HOST, PLACEHOLDER_LUX_HOST)): str,
            vol.Required(ATTR_LUX_DONGLE_SERIAL, default=config_entry.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
        }  # fmt: skip

        if config_entry.get(ATTR_LUX_USE_SERIAL, placeholder_use_serial):
            schema[vol.Optional(ATTR_LUX_SERIAL_NUMBER, default=config_entry.get(ATTR_LUX_SERIAL_NUMBER, ""))] = str  # fmt: skip

        schema.update({
            vol.Optional(ATTR_LUX_USE_SERIAL, default=config_entry.get(ATTR_LUX_USE_SERIAL, placeholder_use_serial)): bool,
            vol.Optional(ATTR_LUX_RESPOND_TO_HEARTBEAT, default=config_entry.get(ATTR_LUX_RESPOND_TO_HEARTBEAT, PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)): bool,
            vol.Optional(ATTR_LUX_AUTO_REFRESH, default=config_entry.get(ATTR_LUX_AUTO_REFRESH, PLACEHOLDER_LUX_AUTO_REFRESH)): bool,
            vol.Optional(ATTR_LUX_REFRESH_INTERVAL, default=config_entry.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)): vol.All(int, vol.Range(min=30, max=120)),
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
        if len(user_input[ATTR_LUX_DONGLE_SERIAL]) != 10:
            errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
        ri = user_input.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
        if type(ri) is not int or ri < 30 or ri > 120:
            errors[ATTR_LUX_REFRESH_INTERVAL] = "refresh_interval_error"
        # Check if the dongle serial already exists in the configuration
        if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
            for entry in self.hass.data[DOMAIN]:
                entry_data = self.hass.data[DOMAIN][entry]
                if entry_data["DONGLE"] == user_input[ATTR_LUX_DONGLE_SERIAL]:
                    errors[ATTR_LUX_DONGLE_SERIAL] = "exist_error"
        use_sn = user_input.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)
        if use_sn:
            sn = user_input.get(ATTR_LUX_SERIAL_NUMBER, PLACEHOLDER_LUX_SERIAL_NUMBER)
            if len(sn) != 10 or sn == PLACEHOLDER_LUX_SERIAL_NUMBER:
                errors[ATTR_LUX_SERIAL_NUMBER] = "serial_error"
                errors[ATTR_LUX_USE_SERIAL] = "use_serial_error"

        return errors

    @staticmethod
    @callback
    def async_get_options_flow(config_entry):
        return OptionsFlowHandler(config_entry)


class OptionsFlowHandler(config_entries.OptionsFlow):
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    def __init__(self, config_entry):
        """Initialize options flow."""
        self.config_entry = config_entry

    async def async_step_init(self, user_input=None):
        """Manage the options."""
        return await self.async_step_user(user_input)

    async def async_step_user(self, user_input):
        errors = {}

        if user_input is not None:
            user_input[ATTR_LUX_PORT] = PLACEHOLDER_LUX_PORT
            if not user_input[ATTR_LUX_USE_SERIAL]:
                user_input[ATTR_LUX_SERIAL_NUMBER] = PLACEHOLDER_LUX_SERIAL_NUMBER
            errors = self._validate_user_input(user_input)
            if not errors:
                _LOGGER.info("OptionsFlowHandler: saving options ")
                return self.async_create_entry(title="LuxPower ()", data=user_input)

        config_entry = self.config_entry.data
        if len(self.config_entry.options) > 0:
            config_entry = self.config_entry.options
        if user_input:
            config_entry = user_input

        schema = {
            vol.Required(ATTR_LUX_HOST, default=config_entry.get(ATTR_LUX_HOST, PLACEHOLDER_LUX_HOST)): str,
            vol.Required(ATTR_LUX_DONGLE_SERIAL, default=config_entry.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)): str,
        }  # fmt: skip

        if config_entry.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL):
            schema[vol.Optional(ATTR_LUX_SERIAL_NUMBER, default=config_entry.get(ATTR_LUX_SERIAL_NUMBER, ""))] = str  # fmt: skip

        schema.update({
            vol.Optional(ATTR_LUX_USE_SERIAL, default=config_entry.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)): bool,
            vol.Optional(ATTR_LUX_RESPOND_TO_HEARTBEAT, default=config_entry.get(ATTR_LUX_RESPOND_TO_HEARTBEAT, PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)): bool,
            vol.Optional(ATTR_LUX_AUTO_REFRESH, default=config_entry.get(ATTR_LUX_AUTO_REFRESH, PLACEHOLDER_LUX_AUTO_REFRESH)): bool,
            vol.Optional(ATTR_LUX_REFRESH_INTERVAL, default=config_entry.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)): vol.All(int, vol.Range(min=30, max=120)),
            vol.Optional(ATTR_LUX_REFRESH_BANK_COUNT, default=config_entry.get(ATTR_LUX_REFRESH_BANK_COUNT, PLACEHOLDER_LUX_REFRESH_BANK_COUNT)): vol.All(int, vol.Range(min=1, max=6)),
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
        if len(user_input[ATTR_LUX_DONGLE_SERIAL]) != 10:
            errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
        ri = user_input.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
        if type(ri) is not int or ri < 30 or ri > 120:
            errors[ATTR_LUX_REFRESH_INTERVAL] = "refresh_interval_error"
        bc = user_input.get(ATTR_LUX_REFRESH_BANK_COUNT, PLACEHOLDER_LUX_REFRESH_BANK_COUNT)
        if type(bc) is not int or bc < 1 or bc > 6:
            errors[ATTR_LUX_REFRESH_BANK_COUNT] = "refresh_bank_count_error"
        sn = user_input.get(ATTR_LUX_SERIAL_NUMBER, PLACEHOLDER_LUX_SERIAL_NUMBER)
        use_sn = user_input.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)
        if use_sn:
            if len(sn) != 10 or sn == PLACEHOLDER_LUX_SERIAL_NUMBER:
                errors[ATTR_LUX_SERIAL_NUMBER] = "serial_error"
                errors[ATTR_LUX_USE_SERIAL] = "use_serial_error"

        return errors
