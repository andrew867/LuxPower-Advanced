"""

This is a docstring placeholder.

This is where we will describe what this module does

"""
import logging
from typing import Any, Dict, List, Optional

import voluptuous as vol
from homeassistant.components.number import NumberEntity, NumberMode
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import (
    DEVICE_CLASS_CURRENT,
    DEVICE_CLASS_POWER,
    DEVICE_CLASS_VOLTAGE,
    ELECTRIC_CURRENT_AMPERE,
    ELECTRIC_POTENTIAL_VOLT,
    POWER_KILO_WATT,
    POWER_WATT,
)
from homeassistant.helpers.entity import DeviceInfo
from homeassistant.util import slugify

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    VERSION,
)
from .helpers import Event
from .LXPPacket import LXPPacket

"""
Setup some options from this page in Home-assistant and allow times and % to be set.

Examples would be AC Charge enable / disable
AC Charge start Time 1 allow to set time via GUI
AC Charge Power Rate % allow to pick 1-100 ?

"""

_LOGGER = logging.getLogger(__name__)


def floatzero(incoming):
    """

    This is a docstring placeholder.

    This is where we will describe what this function does

    """
    try:
        value_we_got = float(incoming)
    except Exception:
        value_we_got = 0
    return value_we_got


hyphen = "test"
nameID_midfix = "mid"
entityID_midfix = "mid"


async def async_setup_entry(hass, config_entry: ConfigEntry, async_add_devices):
    """Set up the number platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux number platform")
    _LOGGER.info("Options %s", len(config_entry.options))

    global hyphen
    global nameID_midfix
    global entityID_midfix

    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    # Options For Name Midfix Based Upon Serial Number - Suggest Last Two Digits
    # nameID_midfix = SERIAL if USE_SERIAL else ""
    nameID_midfix = SERIAL[-2:] if USE_SERIAL else ""

    # Options For Entity Midfix Based Upon Serial Number - Suggest Full Serial Number
    entityID_midfix = SERIAL if USE_SERIAL else ""

    # Options For Hyphen Use Before Entity Description - Suggest No Hyphen As Of 15/02/23
    # hyphen = " -" if USE_SERIAL else "-"
    hyphen = ""

    event = Event(dongle=DONGLE)
    # luxpower_client = hass.data[event.CLIENT_DAEMON]

    _LOGGER.info(f"Lux number platform_config: {platform_config}")

    minnumb = 0.0

    maxperc = 100.0
    maxbyte = 255.0
    # maxtime = 65000.0
    maxtime = 15127.0
    maxnumb = 65535.0

    # fmt: off

    numberEntities: List[LuxNormalNumberEntity] = []

    numbers = [
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} System Charge Power Rate(%)", "register_address": 64, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} System Discharge Power Rate(%)", "register_address": 65, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Power Rate(%)", "register_address": 66, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Battery Charge Level(%)", "register_address": 67, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start1", "register_address": 68, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End1", "register_address": 69, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start2", "register_address": 70, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End2", "register_address": 71, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start3", "register_address": 72, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End3", "register_address": 73, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Priority Charge Rate(%)", "register_address": 74, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Priority Charge Level(%)", "register_address": 75, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start1", "register_address": 76, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End1", "register_address": 77, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start2", "register_address": 78, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End2", "register_address": 79, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start3", "register_address": 80, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End3", "register_address": 81, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Forced Discharge Power Rate(%)", "register_address": 82, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Forced Discharge Battery Level(%)", "register_address": 83, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start1", "register_address": 84, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End1", "register_address": 85, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start2", "register_address": 86, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End2", "register_address": 87, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start3", "register_address": 88, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End3", "register_address": 89, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": True},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} EPS Voltage Target", "register_address": 90, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "icon": "mdi:car-turbocharger", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} EPS Frequency Target", "register_address": 91, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Charge Voltage", "register_address": 99, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Discharge Cut-off Voltage", "register_address": 100, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Charge Current Limit", "register_address": 101, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": DEVICE_CLASS_CURRENT, "unit_of_measurement": ELECTRIC_CURRENT_AMPERE, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Discharge Current Limit", "register_address": 102, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": DEVICE_CLASS_CURRENT, "unit_of_measurement": ELECTRIC_CURRENT_AMPERE, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Feed-in Grid Power(%)", "register_address": 103, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} On-grid Discharge Cut-off SOC", "register_address": 105, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} CT Clamp Offset Amount", "register_address": 119, "def_val": 42.0, "min_val": minnumb, "max_val": 90, "step": 0.1, "mode": NumberMode.BOX, "device_class": DEVICE_CLASS_POWER, "unit_of_measurement": POWER_WATT, "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Off-grid Discharge Cut-off SOC", "register_address": 125, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Floating Voltage", "register_address": 144, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Voltage", "register_address": 149, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Period(Days)", "register_address": 150, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Time(Hours)", "register_address": 151, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start1", "register_address": 152, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First End1", "register_address": 153, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start2", "register_address": 154, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First End2", "register_address": 155, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start3", "register_address": 156, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First End3", "register_address": 157, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start Battery Voltage", "register_address": 158, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End Battery Voltage", "register_address": 159, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start Battery SOC(%)", "register_address": 160, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-20", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End Battery SOC(%)", "register_address": 161, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-100", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning Voltage", "register_address": 162, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning Recovery Voltage", "register_address": 163, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning SOC(%)", "register_address": 164, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-10", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning Recovery SOC(%)", "register_address": 165, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-10", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} On Grid EOD Voltage", "register_address": 169, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Max Generator Input Power", "register_address": 177, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "device_class": DEVICE_CLASS_POWER, "unit_of_measurement": POWER_WATT, "enabled": False},
        {"etype": "LFPE", "name": "Lux {replaceID_midfix}{hyphen} Fan 1 Max Speed(%)", "register_address": 178, "bitmask": 0x00FF, "def_val": 42.0, "min_val": 50.0, "max_val": maxperc, "icon": "mdi:fan", "enabled": False},
        {"etype": "LFPE", "name": "Lux {replaceID_midfix}{hyphen} Fan 2 Max Speed(%)", "register_address": 178, "bitmask": 0xFF00, "def_val": 42.0, "min_val": 50.0, "max_val": maxperc, "icon": "mdi:fan", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start Battery Voltage", "register_address": 194, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End Battery Voltage", "register_address": 195, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start Battery SOC(%)", "register_address": 196, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-20", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End Battery SOC(%)", "register_address": 197, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-80", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Battery Current", "register_address": 198, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": DEVICE_CLASS_CURRENT, "unit_of_measurement": ELECTRIC_CURRENT_AMPERE, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Grid Peak-Shaving Power", "register_address": 206, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "step": 0.1, "mode": NumberMode.BOX, "device_class": DEVICE_CLASS_POWER, "unit_of_measurement": POWER_KILO_WATT, "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving SOC 1(%)", "register_address": 207, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-80", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving Volt 1", "register_address": 208, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving Start1", "register_address": 209, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving End1", "register_address": 210, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving Start2", "register_address": 211, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving End2", "register_address": 212, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving SOC 2(%)", "register_address": 218, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-80", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving Volt 2", "register_address": 219, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Start SOC(%)", "register_address": 220, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-20", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple End SOC(%)", "register_address": 221, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-100", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Start Voltage", "register_address": 222, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple End Voltage", "register_address": 223, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": DEVICE_CLASS_VOLTAGE, "unit_of_measurement": ELECTRIC_POTENTIAL_VOLT, "enabled": False},
    ]

    for entity_definition in numbers:
        etype = entity_definition["etype"]
        if etype == "LNNE":
            numberEntities.append(LuxNormalNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPNE":
            numberEntities.append(LuxPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LTNE":
            numberEntities.append(LuxTimeNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LDTE":
            numberEntities.append(LuxVoltageDivideByTenEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LFPE":
            numberEntities.append(LuxFanPercentageNumberEntity(hass, HOST, PORT, DONGLE, SERIAL, entity_definition, event))

    async_add_devices(numberEntities, True)

    _LOGGER.info("LuxPower number async_setup_platform number done")

    # fmt: on


class LuxNormalNumberEntity(NumberEntity):
    """Representation of a Normal Number entity."""

    def __init__(self, hass, host, port, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"number.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.event = event

        # Hidden Inherited Instance Attributes
        self._attr_entity_registry_enabled_default = entity_definition.get("enabled", False)

        # Hidden Class Extended Instance Attributes
        self._host = host
        self._port = port
        self._register_address = entity_definition["register_address"]
        self._register_value = None
        self._bitmask = entity_definition.get("bitmask", None)
        self._name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        self._state = entity_definition.get("def_val", None)
        self._attr_assumed_state = entity_definition.get("assumed", False)
        self._attr_available = False
        self._attr_device_class = entity_definition.get("device_class", None)
        self._attr_icon = entity_definition.get("icon", None)
        self._attr_mode = entity_definition.get("mode", NumberMode.AUTO)
        self._attr_native_unit_of_measurement = entity_definition.get("unit_of_measurement", None)
        self._attr_native_min_value = entity_definition.get("min_val", None)
        self._attr_native_max_value = entity_definition.get("max_val", None)
        self._attr_native_step = entity_definition.get("step", 1.0)
        self._attr_should_poll = False
        self._read_value = 0
        self.registers: Dict[int, str] = {}
        self.hour_val = -1
        self.minute_val = -1

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug(f"async_added_to_hass {self._name},  {self.entity_id},  {self.unique_id}")
        if self.hass is not None:
            if self._register_address == 21:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_21_RECEIVED, self.push_update)
            elif 0 <= self._register_address <= 39:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update)
            elif 40 <= self._register_address <= 79:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK1_RECEIVED, self.push_update)
            elif 80 <= self._register_address <= 119:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK2_RECEIVED, self.push_update)
            elif 120 <= self._register_address <= 159:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK3_RECEIVED, self.push_update)
            elif 160 <= self._register_address <= 199:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK4_RECEIVED, self.push_update)
            elif 200 <= self._register_address <= 239:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK5_RECEIVED, self.push_update)

    def convert_to_time(self, value):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    def push_update(self, event):
        _LOGGER.debug(
            f"Register Event Received Lux****NumberEntity: {self._name} - Register Address: {self._register_address}"
        )

        registers = event.data.get("registers", {})
        self.registers = registers
        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self._register_address} is in register.keys")
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            oldstate = self._state
            if self.is_divbyten_entity:
                self._state = float(register_val) / 10
            else:
                self._state = float(register_val)
            if oldstate != self._state or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(f"Changing the number from {oldstate} to {self._state}")
                if self.is_time_entity:
                    self.hour_val, self.minute_val = self.convert_to_time(register_val)
                    _LOGGER.debug(f"Translating To Time {self.hour_val}:{self.minute_val}")
                self.schedule_update_ha_state()
        return self._state

    @property
    def device_info(self):
        """Return device info."""
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model="LUXPower Inverter",
            name=self.dongle,
            sw_version=VERSION,
        )

    @property
    def is_time_entity(self):
        return False

    @property
    def is_divbyten_entity(self):
        return False

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_numbernormal_{self._register_address}"

    @property
    def name(self):
        """Return the name of the device if any."""
        return self._name

    @property
    def native_value(self):
        """Return the current value."""
        return self._state

    def set_register(self, new_value=0):
        _LOGGER.debug("Started set_register")

        lxpPacket = LXPPacket(
            debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
        )

        self._read_value = lxpPacket.register_io_with_retry(
            self._host, self._port, self._register_address, value=new_value, iotype=lxpPacket.WRITE_SINGLE
        )

        if self._read_value is not None:
            # Write has been successful
            _LOGGER.info(
                f"WRITE Register OK - Setting INVERTER Register: {self._register_address} Value: {self._read_value}"
            )
        else:
            # Write has been UNsuccessful
            _LOGGER.warning(f"Cannot WRITE Register: {self._register_address} Value: {new_value}")

        return self._read_value

    def get_register(self):
        _LOGGER.debug("Started get_register")

        lxpPacket = LXPPacket(
            debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
        )

        self._read_value = lxpPacket.register_io_with_retry(
            self._host, self._port, self._register_address, value=1, iotype=lxpPacket.READ_HOLD
        )

        if self._read_value is not None:
            # Read has been successful - use read value
            _LOGGER.info(
                f"READ Register OK - Using INVERTER Register: {self._register_address} Value: {self._read_value}"
            )
        else:
            # Read has been UNsuccessful
            _LOGGER.warning(f"Cannot READ Register: {self._register_address}")

        return self._read_value

    def set_native_value(self, value):
        """Update the current value."""
        if value != self._state:
            _LOGGER.debug(f"Started set_value {value}")
            if value < self.min_value or value > self.max_value:
                raise vol.Invalid(
                    f"Invalid value for {self.entity_id}: {value} (range {self.min_value} - {self.max_value})"
                )

            if self.is_divbyten_entity:
                num_value = float(value) * 10
            else:
                num_value = float(value)

            self._read_value = self.set_register(int(num_value))
            if self._read_value is not None:
                _LOGGER.info(
                    f"CAN confirm successful WRITE of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                )
                self._read_value = self.get_register()
                if self._read_value is not None:
                    _LOGGER.info(
                        f"CAN confirm successful READ_BACK of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                    if self._read_value == int(num_value):
                        _LOGGER.info(
                            f"CAN confirm READ_BACK value is same as that sent to SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                        )
                        if self.is_divbyten_entity:
                            self._state = float(self._read_value) / 10
                        else:
                            self._state = self._read_value
                            if self.is_time_entity:
                                self.hour_val, self.minute_val = self.convert_to_time(int(self._state))
                                _LOGGER.debug(f"Translating To Time {self.hour_val}:{self.minute_val}")
                        self.schedule_update_ha_state()
                    else:
                        _LOGGER.warning(
                            f"CanNOT confirm READ_BACK value is same as that sent to SET Register: {self._register_address} ValueSENT: {num_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                        )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm successful READ_BACK of SET Register: {self._register_address} Entity: {self.entity_id}"
                    )
            else:
                _LOGGER.warning(
                    f"CanNOT confirm successful WRITE of SET Register: {self._register_address} Entity: {self.entity_id}"
                )


class LuxPercentageNumberEntity(LuxNormalNumberEntity):
    """Representation of a Percentage Number entity."""

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_numberpercent_{self._register_address}"

    @property
    def native_unit_of_measurement(self) -> Optional[str]:
        return "%"


class LuxTimeNumberEntity(LuxNormalNumberEntity):
    """Representation of a Time Number entity."""

    @property
    def is_time_entity(self):
        return True

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_hour_{self._register_address}"

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes["hour"] = self.hour_val
        state_attributes["minute"] = self.minute_val
        return state_attributes


class LuxVoltageDivideByTenEntity(LuxNormalNumberEntity):
    """Representation of a Divide By Ten Number entity."""

    @property
    def is_divbyten_entity(self):
        return True

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_numberdivbyten_{self._register_address}"


class LuxFanPercentageNumberEntity(LuxPercentageNumberEntity):
    """Representation of a Percentage Number entity."""

    @property
    def unique_id(self) -> Optional[str]:
        return f"{DOMAIN}_{self.dongle}_numberpercent_{self._register_address}_{self._bitmask}"

    def push_update(self, event):
        _LOGGER.debug(
            f"Register Event Received Lux****NumberEntity: {self._name} - Register Address: {self._register_address}"
        )

        registers = event.data.get("registers", {})
        self.registers = registers
        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self._register_address} is in register.keys")
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            # Save current register int value
            self._register_value = register_val
            oldstate = self._state
            if self._bitmask == 0xFF00:
                self._state = ((register_val & 0xFF00) >> 8) / 2
            else:
                self._state = (register_val & 0x00FF) / 2
            if oldstate != self._state or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(f"Changing the number from {oldstate} to {self._state}")
                self.schedule_update_ha_state()
        return self._state

    def set_native_value(self, value):
        """Update the current value."""
        if value != self._state:
            _LOGGER.debug(f"Started set_value {value}")
            if value < self.min_value or value > self.max_value:
                raise vol.Invalid(
                    f"Invalid value for {self.entity_id}: {value} (range {self.min_value} - {self.max_value})"
                )

            lxpPacket = LXPPacket(
                debug=True, dongle_serial=str.encode(str(self.dongle)), serial_number=str.encode(str(self.serial))
            )

            self._read_value = lxpPacket.register_io_with_retry(
                self._host, self._port, self._register_address, value=1, iotype=lxpPacket.READ_HOLD
            )

            if self._read_value is not None:
                # Read has been successful - use read value
                _LOGGER.info(
                    f"Read Register OK - Using INVERTER Register {self._register_address} value of {self._read_value}"
                )
                old_value = int(self._read_value)
            else:
                # Read has been UNsuccessful - use LAST KNOWN register value
                _LOGGER.warning(
                    f"Cannot read Register - Using LAST KNOWN Register {self._register_address} value of {self._register_value}"
                )
                old_value = int(self._register_value)

            if self._bitmask == 0xFF00:
                lsb_val = old_value & 0x00FF
                msb_val = int(float(value) * 2)
            else:
                lsb_val = int(float(value) * 2)
                msb_val = (old_value & 0xFF00) >> 8

            new_value = msb_val * 256 + lsb_val

            if new_value != old_value:
                _LOGGER.info(
                    f"Writing: OLD: {old_value} REGISTER: {self._register_address} MASK: {self._bitmask} NEW {new_value}"
                )
                self._read_value = lxpPacket.register_io_with_retry(
                    self._host, self._port, self._register_address, value=new_value, iotype=lxpPacket.WRITE_SINGLE
                )

                if self._read_value is not None:
                    _LOGGER.info(
                        f"CAN confirm successful WRITE of SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                    if self._read_value == new_value:
                        _LOGGER.info(
                            f"CAN confirm WRITTEN value is same as that sent to SET Register: {self._register_address} Value: {self._read_value} Entity: {self.entity_id}"
                        )
                    else:
                        _LOGGER.warning(
                            f"CanNOT confirm WRITTEN value is same as that sent to SET Register: {self._register_address} ValueSENT: {new_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                        )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm successful WRITE of SET Register: {self._register_address} Entity: {self.entity_id}"
                    )

            _LOGGER.debug("set_native_value done")
