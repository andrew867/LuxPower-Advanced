"""
Diagnostics support for LuxPower integration.

This module provides diagnostic information for troubleshooting
LuxPower integration issues and monitoring system health.
"""

import logging
from typing import Any, Dict

from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers import device_registry as dr

from .const import DOMAIN, VERSION

_LOGGER = logging.getLogger(__name__)


async def async_get_config_entry_diagnostics(
    hass: HomeAssistant, config_entry: ConfigEntry
) -> Dict[str, Any]:
    """
    Return diagnostics for a config entry.
    
    Args:
        hass: Home Assistant instance
        config_entry: Configuration entry to get diagnostics for
        
    Returns:
        Dictionary containing diagnostic information
    """
    if config_entry.entry_id not in hass.data[DOMAIN]:
        return {"error": "Integration not loaded"}

    entry_data = hass.data[DOMAIN][config_entry.entry_id]
    client = entry_data.get("client")
    
    diagnostics = {
        "integration": {
            "version": VERSION,
            "domain": DOMAIN,
            "entry_id": config_entry.entry_id,
        },
        "configuration": {
            "host": config_entry.data.get("lux_host", "unknown"),
            "dongle_serial": config_entry.data.get("lux_dongle_serial", "unknown"),
            "use_serial": config_entry.data.get("lux_use_serial", False),
            "auto_refresh": config_entry.data.get("lux_auto_refresh", False),
            "refresh_interval": config_entry.data.get("lux_refresh_interval", 120),
            "refresh_bank_count": config_entry.data.get("lux_refresh_bank_count", 2),
            "respond_to_heartbeat": config_entry.data.get("lux_respond_to_heartbeat", False),
        },
        "client_status": {
            "connected": client.is_connected() if client and hasattr(client, 'is_connected') else False,
            "last_update": getattr(client, 'last_update', None) if client else None,
        },
        "entities": {
            "sensors": len([e for e in hass.states.async_all() if e.entity_id.startswith("sensor.lux")]),
            "switches": len([e for e in hass.states.async_all() if e.entity_id.startswith("switch.lux")]),
            "numbers": len([e for e in hass.states.async_all() if e.entity_id.startswith("number.lux")]),
            "buttons": len([e for e in hass.states.async_all() if e.entity_id.startswith("button.lux")]),
            "binary_sensors": len([e for e in hass.states.async_all() if e.entity_id.startswith("binary_sensor.lux")]),
            "time": len([e for e in hass.states.async_all() if e.entity_id.startswith("time.lux")]),
        },
    }
    
    # Add device information if available
    device_registry = dr.async_get(hass)
    devices = dr.async_entries_for_config_entry(device_registry, config_entry.entry_id)
    if devices:
        diagnostics["devices"] = [
            {
                "id": device.id,
                "name": device.name,
                "model": device.model,
                "manufacturer": device.manufacturer,
                "sw_version": device.sw_version,
                "hw_version": device.hw_version,
            }
            for device in devices
        ]
    
    # Add recent log entries (last 10)
    try:
        log_entries = []
        for entry in hass.config.logger.handlers:
            if hasattr(entry, 'records'):
                for record in list(entry.records)[-10:]:
                    if DOMAIN in record.getMessage():
                        log_entries.append({
                            "level": record.levelname,
                            "message": record.getMessage(),
                            "time": record.created,
                        })
        diagnostics["recent_logs"] = log_entries
    except Exception:
        diagnostics["recent_logs"] = "Unable to retrieve logs"
    
    # Add service call information
    diagnostics["services"] = {
        "registered": [
            service for service in hass.services.async_services().get(DOMAIN, {}).keys()
        ],
        "recent_calls": [],  # Could be enhanced to track recent service calls
    }
    
    return diagnostics
