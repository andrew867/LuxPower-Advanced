"""
Button entities for LuxPower integration.
"""
import logging
from typing import Any, Dict, List, Optional

from homeassistant.components.button import ButtonEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo
from homeassistant.util import slugify

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    VERSION,
)
from .helpers import Event
from .lxp.client import LuxPowerClient

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices):
    """Set up the button platform."""
    _LOGGER.info("Loading the Lux button platform")

    global hyphen
    global nameID_midfix
    global entityID_midfix

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)
    luxpower_client = hass.data[config_entry.domain][config_entry.entry_id]["client"]

    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""
    entityID_midfix = SERIAL if USE_SERIAL else ""
    hyphen = ""

    event = Event(dongle=DONGLE)

    buttonEntities: List[ButtonEntity] = []

    buttons = [
        {
            "name": "Lux {replaceID_midfix}{hyphen} Inverter Restart",
            "unique": "lux_inverter_restart",
            "action": "restart",
            "enabled": False,
        },
        {
            "name": "Lux {replaceID_midfix}{hyphen} Reset All Settings",
            "unique": "lux_reset_all_settings",
            "action": "reset_all",
            "enabled": False,
        },
    ]

    for entity_definition in buttons:
        buttonEntities.append(
            LuxPowerButtonEntity(
                hass,
                luxpower_client,
                DONGLE,
                SERIAL,
                entity_definition,
                event,
            )
        )

    async_add_devices(buttonEntities, True)
    _LOGGER.info("LuxPower button async_setup_platform button done")


class LuxPowerButtonEntity(ButtonEntity):
    """Representation of a LuxPower button."""

    _client: LuxPowerClient

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):
        self.entity_id = f"button.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}"
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event
        self._client = luxpower_client
        self._action = entity_definition["action"]

        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_{entity_definition['unique']}"
        self._attr_name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        self._attr_should_poll = False
        self._attr_entity_registry_enabled_default = entity_definition.get("enabled", False)

    async def async_press(self) -> None:
        if self._action == "restart":
            await self._client.restart()
        elif self._action == "reset_all":
            await self._client.reset_all_settings()

    @property
    def device_info(self) -> DeviceInfo:
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model="LUXPower Inverter",
            name=self.dongle,
            sw_version=VERSION,
        )
