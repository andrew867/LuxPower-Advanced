import logging
import ipaddress
import re

from homeassistant import config_entries, data_entry_flow
from homeassistant.components.mqtt import MqttServiceInfo
from homeassistant.core import callback

from .const import DOMAIN, ATTR_LUX_HOST, ATTR_LUX_PORT, ATTR_LUX_DONGLE_SERIAL, ATTR_LUX_SERIAL_NUMBER, \
    PLACEHOLDER_LUX_HOST, PLACEHOLDER_LUX_PORT, PLACEHOLDER_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_SERIAL_NUMBER

import voluptuous as vol
_LOGGER = logging.getLogger(__name__)


def host_valid(host):
    """Return True if hostname or IP address is valid."""
    try:
        if ipaddress.ip_address(host).version == (4 or 6):
            return True
    except ValueError:
        disallowed = re.compile(r"[^a-zA-Z\d\-]")
        return all(x and not disallowed.search(x) for x in host.split("."))


class LuxConfigFlow(config_entries.ConfigFlow, domain=DOMAIN):
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
            }
        )

        if user_input is None:
            return self.async_show_form(
                step_id="user", data_schema=data_schema, errors=errors
            )

        self.data = user_input

        is_valid = host_valid(self.data[ATTR_LUX_HOST])
        if not is_valid:
            errors["base"] = "host_error"
            return self.async_show_form(
                step_id="user", data_schema=data_schema, errors=errors
            )
        is_valid = len(self.data[ATTR_LUX_DONGLE_SERIAL]) == 10
        if not is_valid:
            errors["base"] = "dongle_error"
            return self.async_show_form(
                step_id="user", data_schema=data_schema, errors=errors
            )
        is_valid = len(self.data[ATTR_LUX_SERIAL_NUMBER]) == 10
        if not is_valid:
            errors["base"] = "serial_error"
            return self.async_show_form(
                step_id="user", data_schema=data_schema, errors=errors
            )
        if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
            errors["base"] = "exist_error"
            return self.async_show_form(
                step_id="user", data_schema=data_schema, errors=errors
            )

        return self.async_create_entry(
            title="LuxPower",
            data=self.data,
        )

    # async def async_step_mqtt(self, discovery_info: MqttServiceInfo) -> data_entry_flow.FlowResult:
    #     _LOGGER.debug(f"async_step_mqtt: {discovery_info}")
    #     if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
    #         return self.async_abort(reason="already_configured")
    #     self.data = {}
    #     return self.async_create_entry(
    #                 title="Wiring Central",
    #                 data=self.data,
    #             )

    @staticmethod
    @callback
    def async_get_options_flow(config_entry):
        return OptionsFlowHandler(config_entry)


class OptionsFlowHandler(config_entries.OptionsFlow):
    def __init__(self, config_entry):
        """Initialize options flow."""
        self.config_entry = config_entry

    async def async_step_init(self, user_input=None):
        """Manage the options."""
        return await self.async_step_user(user_input=None)

    async def async_step_user(
            self, user_input
    ):
        if user_input is not None:
            _LOGGER.info("OptionsFlowHandler: saving options ")
            return self.async_create_entry(title="LuxPower", data=user_input)
        data_schema = vol.Schema(
            {
                vol.Required(ATTR_LUX_HOST, default=PLACEHOLDER_LUX_HOST): str,
                vol.Required(ATTR_LUX_PORT, default=PLACEHOLDER_LUX_PORT): vol.All(int, vol.Range(min=1001, max=60001)),
                vol.Required(ATTR_LUX_DONGLE_SERIAL, default=PLACEHOLDER_LUX_DONGLE_SERIAL): str,
                vol.Required(ATTR_LUX_SERIAL_NUMBER, default=PLACEHOLDER_LUX_SERIAL_NUMBER): str,
            }
        )
        return self.async_show_form(
            step_id="user",
            data_schema=data_schema,
        )