"""
LuxPower button platform for Home Assistant.

This module provides button entities for triggering LuxPower inverter actions
including data refresh, reconnection, restart, and charging control.
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
    DEFAULT_DONGLE_SERIAL,
    DEFAULT_SERIAL_NUMBER,
    DOMAIN,
    VERSION,
    MODEL_MAP,
    is_12k_model,
)
from .helpers import Event, get_comprehensive_device_info, get_device_group_info, get_entity_device_group
from .lxp.client import LuxPowerClient

_LOGGER = logging.getLogger(__name__)

hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


async def async_setup_entry(
    hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices
):
    """Set up the button platform."""
    _LOGGER.info("Loading the Lux button platform")

    global hyphen
    global nameID_midfix
    global entityID_midfix

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, DEFAULT_DONGLE_SERIAL)
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, DEFAULT_SERIAL_NUMBER)
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)
    luxpower_client = hass.data[config_entry.domain][config_entry.entry_id]["client"]

    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""
    entityID_midfix = SERIAL if USE_SERIAL else ""
    hyphen = ""
    
    # Retrieve cached model code
    entry_id = config_entry.entry_id
    model_code = None
    
    # Check config entry options first (persists across restarts)
    if "model_code" in config_entry.options:
        model_code = config_entry.options["model_code"]
        _LOGGER.info(f"Using cached model code from config entry: {model_code}")
    
    # Check hass.data second (available after first register read)
    elif entry_id in hass.data.get(DOMAIN, {}):
        model_code = hass.data[DOMAIN][entry_id].get("model_code")
        if model_code:
            _LOGGER.info(f"Using cached model code from hass.data: {model_code}")
    
    # Log model detection status
    if model_code:
        is_12k = is_12k_model(model_code)
        model_name = MODEL_MAP.get(model_code, "Unknown")
        _LOGGER.info(f"Model detected: {model_name} ({model_code}) - {'12K' if is_12k else 'non-12K'}")
    else:
        _LOGGER.info("No model code available - using default entity enablement")

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
        {
            "name": "Lux {replaceID_midfix}{hyphen} AFCI Alarm Clear",
            "unique": "lux_afci_alarm_clear",
            "action": "afci_alarm_clear",
            "enabled": False,
        },
        {
            "name": "Lux {replaceID_midfix}{hyphen} Start Charging Slot 1 (180min)",
            "unique": "lux_start_charging_slot1",
            "action": "start_charging",
            "slot": 1,
            "enabled": True,
        },
        {
            "name": "Lux {replaceID_midfix}{hyphen} Stop Charging Slot 1",
            "unique": "lux_stop_charging_slot1",
            "action": "stop_charging",
            "slot": 1,
            "enabled": True,
        },
    ]

    for entity_definition in buttons:
        # Apply model-based enablement logic
        default_enabled = entity_definition.get("enabled", True)
        
        if model_code:
            is_12k = is_12k_model(model_code)
            # Check if this is a 12K-specific button
            if "12K" in entity_definition.get("name", ""):
                default_enabled = is_12k
                if is_12k:
                    _LOGGER.debug(f"Enabling 12K-specific button: {entity_definition['name']}")
                else:
                    _LOGGER.debug(f"Disabling 12K-specific button for non-12K model: {entity_definition['name']}")
        
        # Update entity definition with model-based enablement
        entity_definition["enabled"] = default_enabled
        
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

    def __init__(
        self, hass, luxpower_client, dongle, serial, entity_definition, event: Event
    ):
        self.entity_id = f"button.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}"
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event
        self._entity_definition = entity_definition
        self._client = luxpower_client
        self._action = entity_definition["action"]
        self._slot = entity_definition.get("slot", 1)

        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_{entity_definition['unique']}"
        self._attr_name = entity_definition["name"].format(
            replaceID_midfix=nameID_midfix, hyphen=hyphen
        )
        self._attr_should_poll = False
        self._attr_entity_registry_enabled_default = entity_definition.get(
            "enabled", False
        )

    async def async_press(self) -> None:
        if self._action == "restart":
            await self._client.restart()
        elif self._action == "reset_all":
            await self._client.reset_all_settings()
        elif self._action == "start_charging":
            # Start charging with default 180 minutes (3 hours) duration in specified slot
            await self.hass.services.async_call(
                DOMAIN, 
                "luxpower_start_charging",
                {"dongle": self.dongle, "duration_minutes": 180, "charge_slot": self._slot},
                blocking=True
            )
        elif self._action == "stop_charging":
            # Stop charging in specified slot
            await self.hass.services.async_call(
                DOMAIN,
                "luxpower_stop_charging",
                {"dongle": self.dongle, "charge_slot": self._slot},
                blocking=True,
            )
        elif self._action == "afci_alarm_clear":
            # Clear AFCI alarm
            await self.hass.services.async_call(
                DOMAIN,
                "luxpower_afci_alarm_clear",
                {"dongle": self.dongle},
                blocking=True,
            )

    @property
    def device_info(self) -> DeviceInfo:
        """Return device info for the appropriate device group."""
        # Get the device group for this entity
        device_group = get_entity_device_group(self._entity_definition, self.hass)
        
        # Return device group info if available, otherwise fall back to main device
        if device_group:
            return get_device_group_info(self.hass, self.dongle, device_group)
        else:
            return get_comprehensive_device_info(self.hass, self.dongle, self.serial)
