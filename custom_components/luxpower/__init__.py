"""Example Load Platform integration."""
from distutils.log import info
from typing import Optional

from homeassistant.core import HomeAssistant
from homeassistant.helpers import discovery
from homeassistant.config_entries import ConfigEntry
import voluptuous as vol
from datetime import timedelta
import logging
import asyncio
from .LXPPacket import LXPPacket
from .const import DOMAIN, ATTR_LUX_PORT, ATTR_LUX_HOST, ATTR_LUX_DONGLE_SERIAL, ATTR_LUX_SERIAL_NUMBER, \
    ATTR_LUX_USE_SERIAL
from .helpers import Event
from .connector import LuxPowerClient, ServiceHelper

_LOGGER = logging.getLogger(__name__)

PLATFORMS = ["sensor", "switch", "number"]
# PLATFORMS = ["sensor", "switch"]

SCHEME_REGISTER_BANK = vol.Schema({
    vol.Required("dongle"): vol.Coerce(str),
    vol.Required("address_bank"): vol.Coerce(int),
})

SCHEME_REGISTERS_COUNT = vol.Schema({
    vol.Required("dongle"): vol.Coerce(str),
    vol.Optional("bank_count", default=2): vol.Coerce(int),
})

SCHEME_REGISTERS = vol.Schema({
    vol.Required("dongle"): vol.Coerce(str),
})

SCHEME_RECONNECT = vol.Schema({
    vol.Required("dongle"): vol.Coerce(str),
})

SCHEME_SETTIME = vol.Schema({
    vol.Required("dongle"): vol.Coerce(str),
})


async def refreshALLPlatforms(hass: HomeAssistant, dongle):

    await asyncio.sleep(10)
    status = await hass.services.async_call(DOMAIN, 'luxpower_refresh_registers', {'dongle': dongle, 'bank_count': 3}, blocking=True)

    status = await hass.services.async_call(DOMAIN, 'luxpower_refresh_holdings', {'dongle': dongle}, blocking=True)


async def async_setup(hass: HomeAssistant, config: dict):
    """Set up the BOM component."""
    hass.data.setdefault(DOMAIN, {})

    service_helper = ServiceHelper(hass=hass)

    async def handle_refresh_register_bank(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        address_bank = call.data.get("address_bank")
        _LOGGER.debug("handle_refresh_register_bank service: %s %s %s", DOMAIN, dongle, address_bank)
        await service_helper.send_refresh_register_bank(dongle=dongle, address_bank=int(address_bank))

    async def handle_refresh_registers(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        bank_count = call.data.get("bank_count")
        if int(bank_count)==0:
            bank_count = 2    
        _LOGGER.debug("handle_refresh_registers service: %s %s", DOMAIN, dongle)
        await service_helper.send_refresh_registers(dongle=dongle, bank_count=int(bank_count))
        #await service_helper.send_refresh_registers(dongle=dongle)

    async def handle_holding_registers(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug("handle_holding_registers service: %s %s", DOMAIN, dongle)
        await service_helper.send_holding_registers(dongle=dongle)

    async def handle_reconnect(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug("handle_reconnect service: %s %s", DOMAIN, dongle)
        await service_helper.send_reconnect(dongle=dongle)

    async def handle_synctime(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug("handle_synctime service: %s %s", DOMAIN, dongle)
        await service_helper.send_synctime(dongle=dongle)
   
    hass.services.async_register(
        DOMAIN, "luxpower_refresh_register_bank",
        handle_refresh_register_bank,
        schema=SCHEME_REGISTER_BANK
    )

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_registers",
        handle_refresh_registers,
        schema=SCHEME_REGISTERS_COUNT
    )

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_holdings",
        handle_holding_registers,
        schema=SCHEME_REGISTERS
    )

    hass.services.async_register(
        DOMAIN, "luxpower_reconnect",
        handle_reconnect,
        schema=SCHEME_RECONNECT
    )

    hass.services.async_register(
        DOMAIN, "luxpower_synctime",
        handle_synctime,
        schema=SCHEME_SETTIME
    )
    return True


async def async_setup_entry(hass: HomeAssistant, entry: ConfigEntry) -> bool:
    """LuxPower integration platform load."""

    _LOGGER.info("async_setup_entry: LuxPower integration platform load")
    _LOGGER.debug("platform config: ", entry.data)
    _LOGGER.debug("platform entry_id: ", entry.entry_id)
    """Your controller/hub specific code."""
    # Data that you want to share with your platforms
    config = entry.data or {}
    if len(entry.options) > 0:
        config = entry.options
    _LOGGER.debug(config)
    # Read the config values entered by the user
    HOST = config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = config.get(ATTR_LUX_PORT, 8000)
    DONGLE_SERIAL = config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL_NUMBER = config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = config.get(ATTR_LUX_USE_SERIAL, False)

    events = Event(dongle=DONGLE_SERIAL)
    luxpower_client = LuxPowerClient(hass, server=HOST, port=PORT, dongle_serial=str.encode(str(DONGLE_SERIAL)),
                                     serial_number=str.encode(str(SERIAL_NUMBER)), events=events)
    # _server = await hass.loop.create_connection(luxpower_client.factory, HOST, PORT)
    hass.loop.create_task(luxpower_client.start_luxpower_client_daemon())
    # await hass.async_add_job(luxpower_client.start_luxpower_client_daemon())

    hass.data[events.CLIENT_DAEMON] = luxpower_client

    hass_data = hass.data.setdefault(DOMAIN, {})
    hass_data[entry.entry_id] = {'DONGLE': DONGLE_SERIAL, 'client': luxpower_client}   # Used for avoiding duplication of config entries

    for component in PLATFORMS:
        hass.async_create_task(
            hass.config_entries.async_forward_entry_setup(entry, component)
        )
        _LOGGER.debug(f"async_setup_entry: loading: {component}")

    # Refresh ALL Platforms By Issuing Service Call, After Suitable Delay To Give The Platforms Time To Initialise
    hass.async_create_task(refreshALLPlatforms(hass, dongle=DONGLE_SERIAL))
 
    _LOGGER.info("LuxPower init async_setup done")
    return True


async def async_unload_entry(hass: HomeAssistant, entry: ConfigEntry):
    """Unload a config entry."""
    _LOGGER.info("async_unload_entry: unloading...")
    unload_ok = all(
        await asyncio.gather(
            *[
                hass.config_entries.async_forward_entry_unload(entry, component)
                for component in PLATFORMS
            ]
        )
    )

    if unload_ok:
        entry_data = hass.data[DOMAIN].pop(entry.entry_id)
        if entry_data.get('client') is not None:
            luxpower_client = entry_data.get('client')
            luxpower_client.stop_client()
        hass.data.setdefault(DOMAIN, {})
        _LOGGER.info("async_unload_entry: unloaded...")

    return unload_ok
