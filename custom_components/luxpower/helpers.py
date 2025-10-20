"""
Helper classes and utilities for LuxPower integration.

This module provides the Event class for managing LuxPower-specific events
and event naming conventions used throughout the integration.
"""

from .const import (
    CLIENT_DAEMON_FORMAT,
    DOMAIN,
    EVENT_DATA_FORMAT,
    EVENT_REGISTER_FORMAT,
    EVENT_UNAVAILABLE_FORMAT,
    VERSION,
    MODEL_MAP,
)

import logging

_LOGGER = logging.getLogger(__name__)

# fmt: off


class Event:
    """
    Event management for LuxPower integration.
    
    This class manages all event names and formats used by the LuxPower integration
    for data updates, register changes, and connection status events.
    """

    def __init__(self, dongle: str) -> None:
        """
        Initialize Event class with dongle serial.
        
        Args:
            dongle: The dongle serial number for this inverter
        """
        self.INVERTER_ID = dongle
        self.EVENT_DATA_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="all")
        self.EVENT_DATA_BANK0_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank0")
        self.EVENT_DATA_BANK1_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank1")
        self.EVENT_DATA_BANK2_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank2")
        self.EVENT_DATA_BANK3_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank3")
        self.EVENT_DATA_BANK4_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank4")
        self.EVENT_DATA_BANK5_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank5")
        self.EVENT_DATA_BANK6_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank6")
        self.EVENT_DATA_BANKX_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bankX")
        self.EVENT_REGISTER_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="all")
        self.EVENT_REGISTER_21_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="21")
        self.EVENT_REGISTER_BANK0_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank0")
        self.EVENT_REGISTER_BANK1_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank1")
        self.EVENT_REGISTER_BANK2_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank2")
        self.EVENT_REGISTER_BANK3_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank3")
        self.EVENT_REGISTER_BANK4_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank4")
        self.EVENT_REGISTER_BANK5_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank5")
        self.EVENT_UNAVAILABLE_RECEIVED = EVENT_UNAVAILABLE_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID)
        self.CLIENT_DAEMON = CLIENT_DAEMON_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID)

# fmt: on


def get_comprehensive_device_info(hass, dongle: str, serial: str = None) -> dict:
    """
    Get comprehensive device information for LuxPower inverter.
    
    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number
        serial: Inverter serial number (optional)
        
    Returns:
        dict: Comprehensive device information
    """
    from homeassistant.helpers.entity import DeviceInfo
    
    # Find the entry_id for this dongle
    entry_id = None
    device_data = {}
    
    for e_id, data in hass.data.get(DOMAIN, {}).items():
        if data.get("DONGLE") == dongle:
            entry_id = e_id
            device_data = data
            break
    
    # Get device information from cached data
    model = device_data.get("model", "LuxPower Inverter")
    model_code = device_data.get("model_code", "Unknown")
    firmware_version = device_data.get("lux_firmware_version", "Unknown")
    inverter_serial = device_data.get("inverter_serial_number", None)
    
    # Debug logging for device info
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Dongle: {dongle}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Entry ID: {entry_id}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Device Data: {device_data}")
    _LOGGER.debug(f"🔍 DEVICE INFO DEBUG - Model: {model}, Model Code: {model_code}, Firmware: {firmware_version}, Serial: {inverter_serial}")
    
    # Get model name from model code
    model_name = MODEL_MAP.get(model_code, model)
    
    # Use register-based serial number if available, otherwise fall back to configuration
    serial_number = inverter_serial or serial or dongle
    
    # Create comprehensive device info
    device_info = DeviceInfo(
        identifiers={(DOMAIN, dongle)},
        name=f"LuxPower {model_name}",
        manufacturer="LuxPower",
        model=model_name,
        sw_version=firmware_version,
        hw_version=model_code if model_code != "Unknown" else "Unknown",
        serial_number=serial_number,
        configuration_url=f"https://github.com/guybw/LuxPython_DEV",
    )
    
    return device_info
