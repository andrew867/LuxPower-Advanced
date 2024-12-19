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
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    PLACEHOLDER_LUX_DONGLE_SERIAL,
    PLACEHOLDER_LUX_HOST,
    PLACEHOLDER_LUX_PORT,
    PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT,
    PLACEHOLDER_LUX_SERIAL_NUMBER,
    PLACEHOLDER_LUX_USE_SERIAL,
)

_LOGGER = logging.getLogger(__name__)


def host_valid(host):
    """Return True if hostname or IP address is valid."""
    try:
        if ipaddress.ip_address(host).version == (4 or 6):
            return True
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
        _LOGGER.info("LuxConfigFlow: saving options ")
        errors = {}
        # Specify items in the order they are to be displayed in the UI
        data_schema = vol.Schema(
            {
                vol.Required(ATTR_LUX_HOST, default=PLACEHOLDER_LUX_HOST): str,
                vol.Required(ATTR_LUX_PORT, default=PLACEHOLDER_LUX_PORT): vol.All(int, vol.Range(min=1001, max=60001)),
                vol.Required(ATTR_LUX_DONGLE_SERIAL, default=PLACEHOLDER_LUX_DONGLE_SERIAL): str,
                vol.Required(ATTR_LUX_SERIAL_NUMBER, default=PLACEHOLDER_LUX_SERIAL_NUMBER): str,
                vol.Optional(ATTR_LUX_USE_SERIAL, default=PLACEHOLDER_LUX_USE_SERIAL): bool,
                vol.Optional(ATTR_LUX_RESPOND_TO_HEARTBEAT, default=PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT): bool,
            }
        )  # fmt: skip

        if user_input is None:
            return self.async_show_form(step_id="user", data_schema=data_schema, errors=errors)

        self.data = user_input

        is_valid = host_valid(self.data[ATTR_LUX_HOST])
        if not is_valid:
            errors["base"] = "host_error"
            return self.async_show_form(step_id="user", data_schema=data_schema, errors=errors)
        is_valid = len(self.data[ATTR_LUX_DONGLE_SERIAL]) == 10
        if not is_valid:
            errors["base"] = "dongle_error"
            return self.async_show_form(step_id="user", data_schema=data_schema, errors=errors)
        is_valid = len(self.data[ATTR_LUX_SERIAL_NUMBER]) == 10
        if not is_valid:
            errors["base"] = "serial_error"
            return self.async_show_form(step_id="user", data_schema=data_schema, errors=errors)
        if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
            dongle_check = self.data[ATTR_LUX_DONGLE_SERIAL]
            for entry in self.hass.data[DOMAIN]:
                entry_data = self.hass.data[DOMAIN][entry]
                if entry_data["DONGLE"] == dongle_check:
                    errors["base"] = "exist_error"
                    return self.async_show_form(step_id="user", data_schema=data_schema, errors=errors)

        return self.async_create_entry(
            title=f"LuxPower - ({self.data[ATTR_LUX_DONGLE_SERIAL]})",
            data=self.data,
        )

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
        if user_input is not None:
            _LOGGER.info("OptionsFlowHandler: saving options ")
            return self.async_create_entry(title="LuxPower ()", data=user_input)

        config_entry = self.config_entry.data
        if len(self.config_entry.options) > 0:
            config_entry = self.config_entry.options
        data_schema = vol.Schema(
            {
                vol.Required(ATTR_LUX_HOST, default=config_entry.get(ATTR_LUX_HOST, "")): str,
                vol.Required(ATTR_LUX_PORT, default=config_entry.get(ATTR_LUX_PORT, "")): vol.All(int, vol.Range(min=1001, max=60001)),
                vol.Required(ATTR_LUX_DONGLE_SERIAL, default=config_entry.get(ATTR_LUX_DONGLE_SERIAL, "")): str,
                vol.Required(ATTR_LUX_SERIAL_NUMBER, default=config_entry.get(ATTR_LUX_SERIAL_NUMBER, "")): str,
                vol.Optional(ATTR_LUX_USE_SERIAL, default=config_entry.get(ATTR_LUX_USE_SERIAL, False)): bool,
                vol.Optional(ATTR_LUX_RESPOND_TO_HEARTBEAT, default=config_entry.get(ATTR_LUX_RESPOND_TO_HEARTBEAT, PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)): bool,
            }
        )  # fmt: skip
        return self.async_show_form(
            step_id="user",
            data_schema=data_schema,
        )
