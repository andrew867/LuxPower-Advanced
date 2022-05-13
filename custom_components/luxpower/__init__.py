"""Example Load Platform integration."""
from typing import Optional

from homeassistant.core import HomeAssistant
from homeassistant.helpers import discovery
from homeassistant.config_entries import ConfigEntry
import voluptuous as vol
from datetime import timedelta
import logging
import asyncio
from .LXPPacket import LXPPacket
from .const import DOMAIN, ATTR_LUX_PORT, ATTR_LUX_HOST, ATTR_LUX_DONGLE_SERIAL, ATTR_LUX_SERIAL_NUMBER
from .helpers import Event
from .connector import LuxPowerClient

_LOGGER = logging.getLogger(__name__)

PLATFORMS = ["sensor", "switch", "number"]
# PLATFORMS = ["sensor", "switch"]

SCHEME_REGISTER_BANK = vol.Schema({
    vol.Required("address_bank"): vol.Coerce(int),
})


async def async_setup(hass: HomeAssistant, config: dict):
    """Set up the BOM component."""
    hass.data.setdefault(DOMAIN, {})
    return True


async def async_setup_entry(hass: HomeAssistant, entry: ConfigEntry) -> bool:
    """LuxPower integration platform load."""

    _LOGGER.info("async_setup_entry: LuxPower integration platform load")
    print("platform config: ", entry.data)
    print("platform entry_id: ", entry.entry_id)
    """Your controller/hub specific code."""
    # Data that you want to share with your platforms
    config = entry.data or {}
    print(config)
    # Read the config values entered by the user
    HOST = config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = config.get(ATTR_LUX_PORT, 8000)
    DONGLE_SERIAL = config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL_NUMBER = config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")

    events = Event(dongle=DONGLE_SERIAL)
    luxpower_client = LuxPowerClient(hass, server=HOST, port=PORT, dongle_serial=str.encode(str(DONGLE_SERIAL)),
                                     serial_number=str.encode(str(SERIAL_NUMBER)), events=events)
    # _server = await hass.loop.create_connection(luxpower_client.factory, HOST, PORT)
    hass.loop.create_task(luxpower_client.start_luxpower_client_daemon())
    # await hass.async_add_job(luxpower_client.start_luxpower_client_daemon())

    hass.data[events.CLIENT_DAEMON] = luxpower_client

    hass_data = hass.data.setdefault(DOMAIN, {})
    hass_data[entry.entry_id] = {'DONGLE': DONGLE_SERIAL, 'client': luxpower_client}   # Used for avoiding duplication of config entries

    # await hass.helpers.discovery.async_load_platform("switch", DOMAIN, {}, config)
    # await hass.helpers.discovery.async_load_platform("sensor", DOMAIN, {}, config)

    async def handle_refresh_register_bank(call):
        """Handle the service call."""
        _LOGGER.info("handle_refresh_register_bank service: %s", DOMAIN)
        address_bank = call.data.get("address_bank")
        print("handle_refresh_register_bank service ", address_bank)
        await luxpower_client.get_register_data(address_bank)

    async def handle_refresh_registers(call):
        """Handle the service call."""
        _LOGGER.info("handle_refresh_registers service: %s", DOMAIN)
        print("handle_refresh_registers service ")
        for address_bank in range(0, 3):
            await luxpower_client.get_register_data(address_bank)
            await asyncio.sleep(1)

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_register_bank",
        handle_refresh_register_bank,
        schema=SCHEME_REGISTER_BANK
    )

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_registers",
        handle_refresh_registers
    )

    for component in PLATFORMS:
        hass.async_create_task(
            hass.config_entries.async_forward_entry_setup(entry, component)
        )
        _LOGGER.debug(f"async_setup_entry: loading: {component}")

    print("LuxPower init async_setup done")
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