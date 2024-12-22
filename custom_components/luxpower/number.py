"""

This is a docstring placeholder.

This is where we will describe what this module does

"""
import logging
from typing import Any, Dict, List, Optional

import voluptuous as vol
from homeassistant.components.number import NumberDeviceClass, NumberEntity, NumberMode
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import (
    PERCENTAGE,
    UnitOfElectricCurrent,
    UnitOfElectricPotential,
    UnitOfPower,
)
from homeassistant.helpers.entity import DeviceInfo
from homeassistant.util import slugify

from .lxp.client import LuxPowerClient

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    VERSION,
)
from .helpers import Event
from .LXPPacket import LXPPacket

# from homeassistant.const import EntityCategory


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


hyphen = ""
nameID_midfix = ""
entityID_midfix = ""


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

    luxpower_client = hass.data[config_entry.domain][config_entry.entry_id]['client']

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
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Grid Volt Connect Low", "register_address": 25, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "icon": "mdi:car-turbocharger", "disabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Grid Volt Connect High", "register_address": 26, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "icon": "mdi:car-turbocharger", "disabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Grid Freq Connect Low", "register_address": 27, "divisor": 100, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "icon": "mdi:car-turbocharger", "disabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Grid Freq Connect High", "register_address": 28, "divisor": 100, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "icon": "mdi:car-turbocharger", "disabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} System Charge Power Rate(%)", "register_address": 64, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} System Discharge Power Rate(%)", "register_address": 65, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Power Rate(%)", "register_address": 66, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Battery Charge Level(%)", "register_address": 67, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start1", "register_address": 68, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End1", "register_address": 69, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start2", "register_address": 70, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End2", "register_address": 71, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start3", "register_address": 72, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End3", "register_address": 73, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Priority Charge Rate(%)", "register_address": 74, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Priority Charge Level(%)", "register_address": 75, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start1", "register_address": 76, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End1", "register_address": 77, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start2", "register_address": 78, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End2", "register_address": 79, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge Start3", "register_address": 80, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Charge End3", "register_address": 81, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Forced Discharge Power Rate(%)", "register_address": 82, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Forced Discharge Battery Level(%)", "register_address": 83, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start1", "register_address": 84, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End1", "register_address": 85, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start2", "register_address": 86, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End2", "register_address": 87, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge Start3", "register_address": 88, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Force Discharge End3", "register_address": 89, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} EPS Voltage Target", "register_address": 90, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "icon": "mdi:car-turbocharger", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} EPS Frequency Target", "register_address": 91, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Charge Voltage", "register_address": 99, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Discharge Cut-off Voltage", "register_address": 100, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Charge Current Limit", "register_address": 101, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": NumberDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Discharge Current Limit", "register_address": 102, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": NumberDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Feed-in Grid Power(%)", "register_address": 103, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} On-grid Discharge Cut-off SOC", "register_address": 105, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} CT Clamp Offset Amount", "register_address": 119, "def_val": 42.0, "min_val": -199, "max_val": 199, "step": 0.1, "signed": True, "mode": NumberMode.BOX, "device_class": NumberDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": True},
        {"etype": "LBNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Mode", "register_address": 120, "bitmask": LXPPacket.AC_CHARGE_MODE_BITMASK, "def_val": 42.0, "min_val": 0, "max_val": 6, "step": 2, "mode": NumberMode.BOX, "icon": "mdi:battery-positive", "enabled": False},
        {"etype": "LBNE", "name": "Lux {replaceID_midfix}{hyphen} Discharge Control", "register_address": 120, "bitmask": LXPPacket.DISCHARG_ACC_TO_SOC, "def_val": 42.0, "min_val": 0, "max_val": 16, "step": 16, "mode": NumberMode.BOX, "icon": "mdi:battery-negative", "enabled": False},
        {"etype": "LBNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Type", "register_address": 120, "bitmask": LXPPacket.GEN_CHRG_ACC_TO_SOC, "def_val": 42.0, "min_val": 0, "max_val": 128, "step": 128, "mode": NumberMode.BOX, "icon": "mdi:engine", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Off-grid Discharge Cut-off SOC", "register_address": 125, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:car-turbocharger", "enabled": True},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Floating Voltage", "register_address": 144, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Voltage", "register_address": 149, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Period(Days)", "register_address": 150, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Equalization Time(Hours)", "register_address": 151, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start1", "register_address": 152, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First End1", "register_address": 153, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start2", "register_address": 154, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First End2", "register_address": 155, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First Start3", "register_address": 156, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} AC First End3", "register_address": 157, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start Battery Voltage", "register_address": 158, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End Battery Voltage", "register_address": 159, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Start Battery SOC(%)", "register_address": 160, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-20", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge End Battery SOC(%)", "register_address": 161, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-100", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning Voltage", "register_address": 162, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning Recovery Voltage", "register_address": 163, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning SOC(%)", "register_address": 164, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-10", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Battery Warning Recovery SOC(%)", "register_address": 165, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-10", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} AC Charge Battery Current", "register_address": 168, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": NumberDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} On Grid EOD Voltage", "register_address": 169, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Max Generator Input Power", "register_address": 177, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "device_class": NumberDeviceClass.POWER, "unit_of_measurement": UnitOfPower.WATT, "enabled": False},
        {"etype": "LBNE", "name": "Lux {replaceID_midfix}{hyphen} Fan 1 Max Speed(%)", "register_address": 178, "bitmask": 0x00FF, "bitshift": 0, "divisor": 2, "def_val": 42.0, "min_val": 0.0, "max_val": maxbyte, "step": 0.5, "icon": "mdi:fan", "enabled": False},
        {"etype": "LBNE", "name": "Lux {replaceID_midfix}{hyphen} Fan 2 Max Speed(%)", "register_address": 178, "bitmask": 0xFF00, "bitshift": 8, "divisor": 2, "def_val": 42.0, "min_val": 0.0, "max_val": maxbyte, "step": 0.5, "icon": "mdi:fan", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start Battery Voltage", "register_address": 194, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End Battery Voltage", "register_address": 195, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Start Battery SOC(%)", "register_address": 196, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-20", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge End Battery SOC(%)", "register_address": 197, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-80", "enabled": False},
        {"etype": "LNNE", "name": "Lux {replaceID_midfix}{hyphen} Generator Charge Battery Current", "register_address": 198, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "device_class": NumberDeviceClass.CURRENT, "unit_of_measurement": UnitOfElectricCurrent.AMPERE, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Grid Peak-Shaving Power", "register_address": 206, "def_val": 42.0, "min_val": minnumb, "max_val": maxbyte, "step": 0.1, "mode": NumberMode.BOX, "device_class": NumberDeviceClass.POWER, "unit_of_measurement": UnitOfPower.KILO_WATT, "enabled": True},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving SOC 1(%)", "register_address": 207, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-80", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving Volt 1", "register_address": 208, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving Start1", "register_address": 209, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving End1", "register_address": 210, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving Start2", "register_address": 211, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LTNE", "name": "Lux {replaceID_midfix}{hyphen} Peak-Shaving End2", "register_address": 212, "def_val": 0.0, "min_val": minnumb, "max_val": maxtime, "icon": "mdi:timer-outline", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving SOC 2(%)", "register_address": 218, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-80", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} Start Peak-Shaving Volt 2", "register_address": 219, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Start SOC(%)", "register_address": 220, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-20", "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple End SOC(%)", "register_address": 221, "def_val": 42.0, "min_val": minnumb, "max_val": maxperc, "icon": "mdi:battery-charging-100", "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple Start Voltage", "register_address": 222, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LDTE", "name": "Lux {replaceID_midfix}{hyphen} AC Couple End Voltage", "register_address": 223, "def_val": 42.0, "min_val": minnumb, "max_val": maxnumb, "step": 0.1, "device_class": NumberDeviceClass.VOLTAGE, "unit_of_measurement": UnitOfElectricPotential.VOLT, "enabled": False},
        {"etype": "LPNE", "name": "Lux {replaceID_midfix}{hyphen} System Charge SOC Limit (%)", "register_address": 227, "def_val": 101.0, "min_val": 10, "max_val": 101, "icon": "mdi:battery-charging-100", "enabled": False},
    ]

    for entity_definition in numbers:
        etype = entity_definition["etype"]
        if etype == "LNNE":
            numberEntities.append(LuxNormalNumberEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LPNE":
            numberEntities.append(LuxPercentageNumberEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LTNE":
            numberEntities.append(LuxTimeNumberEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LDTE":
            numberEntities.append(LuxVoltageDivideByTenEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))
        elif etype == "LBNE":
            numberEntities.append(LuxBitmaskNumberEntity(hass, luxpower_client, DONGLE, SERIAL, entity_definition, event))

    async_add_devices(numberEntities, True)

    _LOGGER.info("LuxPower number async_setup_platform number done")

    # fmt: on


class LuxNormalNumberEntity(NumberEntity):
    """Representation of a Normal Number entity."""

    _client: LuxPowerClient

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        # Visible Instance Attributes Outside Class
        self.entity_id = (f"number.{slugify(entity_definition['name'].format(replaceID_midfix=entityID_midfix, hyphen=hyphen))}")  # fmt: skip
        self.hass = hass
        self.dongle = dongle
        self.serial = serial
        self.register_address = entity_definition["register_address"]
        self.event = event
        self._client = luxpower_client

        # Hidden Inherited Instance Attributes
        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_numbernormal_{self.register_address}"
        self._attr_name = entity_definition["name"].format(replaceID_midfix=nameID_midfix, hyphen=hyphen)
        # self._attr_entity_category = entity_definition.get("ent_cat", None)
        self._attr_native_value = entity_definition.get("def_val", None)
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
        self._attr_entity_registry_enabled_default = entity_definition.get("enabled", False)

        # Hidden Class Extended Instance Attributes
        self._register_value = 0
        self._bitmask = entity_definition.get("bitmask", 0xFFFF)
        self._bitshift = entity_definition.get("bitshift", 0)
        self._divisor = entity_definition.get("divisor", 1)
        self._read_value = 0
        self._is_time_entity = False
        self._is_signed = entity_definition.get("signed", False)
        self._hour_value = -1
        self._minute_value = -1

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hass %s, %s, %s", self._attr_name, self.entity_id, self.unique_id)
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_UNAVAILABLE_RECEIVED, self.gone_unavailable)
            if self.register_address == 21:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_21_RECEIVED, self.push_update)
            elif 0 <= self.register_address <= 39:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update)
            elif 40 <= self.register_address <= 79:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK1_RECEIVED, self.push_update)
            elif 80 <= self.register_address <= 119:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK2_RECEIVED, self.push_update)
            elif 120 <= self.register_address <= 159:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK3_RECEIVED, self.push_update)
            elif 160 <= self.register_address <= 199:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK4_RECEIVED, self.push_update)
            elif 200 <= self.register_address <= 239:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK5_RECEIVED, self.push_update)

    def convert_to_time(self, value):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    def unsigned_short_to_signed_short(self, value):
        return -(value & 0x8000) | (value & 0x7FFF)

    def signed_short_to_unsigned_short(self, value):
        return (value + (1 << 16)) & 0xFFFF

    def push_update(self, event):
        _LOGGER.debug(f"Register Event Received Lux****NumberEntity: {self._attr_name} - Register Address: {self.register_address}")  # fmt: skip

        registers = event.data.get("registers", {})
        if self.register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self.register_address} is in register.keys")
            register_val = registers.get(self.register_address, None)
            if register_val is None:
                return
            # Save current register int value
            self._register_value = register_val
            oldstate = self._attr_native_value
            if self._is_signed:
                self._attr_native_value = self.unsigned_short_to_signed_short(register_val) / self._divisor  # fmt: skip
            else:
                self._attr_native_value = ((register_val & self._bitmask) >> self._bitshift) / self._divisor
            if oldstate != self._attr_native_value or not self._attr_available:
                self._attr_available = True
                _LOGGER.debug(f"Changing the number from {oldstate} to {self._attr_native_value}")
                if self._is_time_entity:
                    self._hour_value, self._minute_value = self.convert_to_time(register_val)
                    _LOGGER.debug(f"Translating To Time {self._hour_value}:{self._minute_value}")
                self.schedule_update_ha_state()
        return self._attr_native_value

    def gone_unavailable(self, event):
        _LOGGER.warning(f"Register: gone_unavailable event received Name: {self._attr_name} - Register Address: {self.register_address}")  # fmt: skip
        self._attr_available = False
        self.schedule_update_ha_state()

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

    async def async_set_native_value(self, value):
        """Update the current value."""
        _LOGGER.debug("set_value called")
        if value != self._attr_native_value:
            _LOGGER.debug(f"Started set_value {value}")
            if value < self.min_value or value > self.max_value:
                raise vol.Invalid(
                    f"Invalid value for {self.entity_id}: {value} (range {self.min_value} - {self.max_value})"
                )

            if self._bitmask != 0xFFFF:
                # Not A Full Bitmask 16 bit Integer - Partial Bitmask So READ Register 1st if Possible

                self._read_value = await self._client.read(self.register_address)

                if self._read_value is not None:
                    # Read has been successful - use read value
                    _LOGGER.info(
                        f"Read Register OK - Using INVERTER Register {self.register_address} value of {self._read_value}"
                    )
                    old_value = int(self._read_value)
                else:
                    # Read has been UNsuccessful - use LAST KNOWN register value
                    _LOGGER.warning(
                        f"Cannot read Register - Using LAST KNOWN Register {self.register_address} value of {self._register_value}"
                    )
                    old_value = int(self._register_value)
            else:
                old_value = int(self._register_value)  # Can be anything!!

            if self._is_signed:
                new_value = self.signed_short_to_unsigned_short(int(round(float(value) * self._divisor, 0)))  # fmt: skip
            else:
                new_value = (old_value & ~self._bitmask) | ((int(round(float(value) * self._divisor, 0)) << self._bitshift) & self._bitmask)  # fmt: skip

            _LOGGER.debug(
                f"ENTITY_ID: {self.entity_id} VALUE: {value} OLD: {old_value} REGISTER: {self.register_address} MASK: {self._bitmask:04x} SHIFT: {self._bitshift} DIVISOR: {self._divisor} NEW: {new_value}"
            )

            if new_value != old_value or self._bitmask == 0xFFFF:
                _LOGGER.info(
                    f"Writing: OLD: {old_value} REGISTER: {self.register_address} MASK: {self._bitmask} NEW {new_value}"
                )
                self._read_value = await self._client.write(self.register_address, new_value)

                if self._read_value is not None:
                    _LOGGER.info(
                        f"CAN confirm successful WRITE of SET Register: {self.register_address} Value: {self._read_value} Entity: {self.entity_id}"
                    )
                    if self._read_value == new_value:
                        _LOGGER.info(
                            f"CAN confirm WRITTEN value is same as that sent to SET Register: {self.register_address} Value: {self._read_value} Entity: {self.entity_id}"
                        )
                        self._attr_native_value = value
                        if self._is_time_entity:
                            self._hour_value, self._minute_value = self.convert_to_time(int(self._attr_native_value))
                            _LOGGER.debug(f"Translating To Time {self._hour_value}:{self._minute_value}")
                        self.async_write_ha_state()
                    else:
                        _LOGGER.warning(
                            f"CanNOT confirm WRITTEN value is same as that sent to SET Register: {self.register_address} ValueSENT: {new_value} ValueREAD: {self._read_value} Entity: {self.entity_id}"
                        )
                else:
                    _LOGGER.warning(
                        f"CanNOT confirm successful WRITE of SET Register: {self.register_address} Entity: {self.entity_id}"
                    )

            _LOGGER.debug("set_native_value done")


class LuxPercentageNumberEntity(LuxNormalNumberEntity):
    """Representation of a Percentage Number entity."""

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        super().__init__(hass, luxpower_client, dongle, serial, entity_definition, event)
        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_numberpercent_{self.register_address}"
        self._attr_native_unit_of_measurement = PERCENTAGE


class LuxTimeNumberEntity(LuxNormalNumberEntity):
    """Representation of a Time Number entity."""

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        super().__init__(hass, luxpower_client, dongle, serial, entity_definition, event)
        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_hour_{self.register_address}"
        self._is_time_entity = True

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes["hour"] = self._hour_value
        state_attributes["minute"] = self._minute_value
        return state_attributes


class LuxVoltageDivideByTenEntity(LuxNormalNumberEntity):
    """Representation of a Divide By Ten Number entity."""

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        super().__init__(hass, luxpower_client, dongle, serial, entity_definition, event)
        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_numberdivbyten_{self.register_address}"
        self._divisor = 10


class LuxBitmaskNumberEntity(LuxNormalNumberEntity):
    """Representation of a Percentage Number entity."""

    def __init__(self, hass, luxpower_client, dongle, serial, entity_definition, event: Event):  # fmt: skip
        """Initialize the Lux****Number entity."""
        #
        super().__init__(hass, luxpower_client, dongle, serial, entity_definition, event)
        self._attr_unique_id = f"{DOMAIN}_{self.dongle}_numberbitmask_{self.register_address}_{self._bitmask}"
