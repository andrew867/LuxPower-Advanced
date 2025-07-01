"""

This is a docstring placeholder.

This is where we will describe what this module does

"""

import asyncio
import logging

import voluptuous as vol
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant

from .connector import ServiceHelper
from .lxp.client import LuxPowerClient
from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_AUTO_REFRESH,
    ATTR_LUX_REFRESH_INTERVAL,
    ATTR_LUX_REFRESH_BANK_COUNT,
    ATTR_LUX_RESPOND_TO_HEARTBEAT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    PLACEHOLDER_LUX_AUTO_REFRESH,
    PLACEHOLDER_LUX_REFRESH_INTERVAL,
    PLACEHOLDER_LUX_REFRESH_BANK_COUNT,
    PLACEHOLDER_LUX_SERIAL_NUMBER,
    PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT,
    VERSION,
)
from .helpers import Event, read_serial_number

_LOGGER = logging.getLogger(__name__)

PLATFORMS = ["sensor", "switch", "number", "time", "button"]

SCHEME_REGISTER_BANK = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
        vol.Required("address_bank"): vol.Coerce(int),
    }
)

SCHEME_REGISTERS_COUNT = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
        vol.Optional("bank_count", default=2): vol.Coerce(int),
    }
)

SCHEME_REGISTERS = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
    }
)

SCHEME_RECONNECT = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
    }
)

SCHEME_RESTART = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
    }
)

SCHEME_RESET_SETTINGS = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
    }
)

SCHEME_SETTIME = vol.Schema(
    {
        vol.Required("dongle"): vol.Coerce(str),
        vol.Optional("do_set_time", default="False"): vol.Coerce(str),
    }
)


async def refreshALLPlatforms(hass: HomeAssistant, dongle):
    """

    This is a docstring placeholder.

    This is where we will describe what this function does

    """
    await asyncio.sleep(20)
    # fmt: skip
    await hass.services.async_call(DOMAIN, "luxpower_refresh_registers", {"dongle": dongle, "bank_count": 3}, blocking=True)
    # fmt: skip
    await hass.services.async_call(DOMAIN, "luxpower_refresh_holdings", {"dongle": dongle}, blocking=True)


async def async_setup(hass: HomeAssistant, config: dict):
    """Set up the BOM component."""
    hass.data.setdefault(DOMAIN, {})

    service_helper = ServiceHelper(hass=hass)

    async def handle_refresh_data_register_bank(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        address_bank = call.data.get("address_bank")
        _LOGGER.debug("handle_refresh_data_register_bank service: %s %s %s",
                      DOMAIN, dongle, address_bank)
        await service_helper.service_refresh_data_register_bank(dongle=dongle, address_bank=int(address_bank))

    async def handle_refresh_data_registers(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        bank_count = call.data.get("bank_count")
        if int(bank_count) == 0:
            bank_count = 2
        _LOGGER.debug(
            "handle_refresh_data_registers service: %s %s", DOMAIN, dongle)
        await service_helper.service_refresh_data_registers(dongle=dongle, bank_count=int(bank_count))

    async def handle_refresh_hold_registers(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug(
            "handle_refresh_hold_registers service: %s %s", DOMAIN, dongle)
        await service_helper.service_refresh_hold_registers(dongle=dongle)

    async def handle_reconnect(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug("handle_reconnect service: %s %s", DOMAIN, dongle)
        await service_helper.service_reconnect(dongle=dongle)

    async def handle_restart(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug("handle_restart service: %s %s", DOMAIN, dongle)
        await service_helper.service_restart(dongle=dongle)

    async def handle_reset_settings(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        _LOGGER.debug("handle_reset_settings service: %s %s", DOMAIN, dongle)
        await service_helper.service_reset_settings(dongle=dongle)

    async def handle_synctime(call):
        """Handle the service call."""
        dongle = call.data.get("dongle")
        do_set_time = call.data.get(
            "do_set_time", "False").lower() in ("yes", "true", "t", "1")
        _LOGGER.debug("handle_synctime service: %s %s", DOMAIN, dongle)
        await service_helper.service_synctime(dongle=dongle, do_set_time=do_set_time)

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_register_bank", handle_refresh_data_register_bank, schema=SCHEME_REGISTER_BANK
    )

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_registers", handle_refresh_data_registers, schema=SCHEME_REGISTERS_COUNT
    )

    hass.services.async_register(
        DOMAIN, "luxpower_refresh_holdings", handle_refresh_hold_registers, schema=SCHEME_REGISTERS
    )

    hass.services.async_register(
        DOMAIN, "luxpower_reconnect", handle_reconnect, schema=SCHEME_RECONNECT
    )  # fmt: skip

    hass.services.async_register(
        DOMAIN, "luxpower_restart", handle_restart, schema=SCHEME_RESTART
    )  # fmt: skip

    hass.services.async_register(
        DOMAIN, "luxpower_reset_settings", handle_reset_settings, schema=SCHEME_RESET_SETTINGS
    )  # fmt: skip

    hass.services.async_register(
        DOMAIN, "luxpower_synctime", handle_synctime, schema=SCHEME_SETTIME
    )  # fmt: skip

    return True


async def async_setup_entry(hass: HomeAssistant, entry: ConfigEntry) -> bool:
    """
    The LUXPower integration platform load with proper error handling.
    """
    _LOGGER.info(f"async_setup_entry: LuxPower integration Version {VERSION} platform load")
    
    try:
        # Validate entry parameter
        if not isinstance(entry, ConfigEntry):
            _LOGGER.error("Invalid entry parameter - not a ConfigEntry object")
            return False
            
        _LOGGER.debug("platform config: %s", entry.data)
        _LOGGER.debug("platform entry_id: %s", entry.entry_id)
        
        # Get configuration with proper error handling
        config = {}
        try:
            config = entry.data or {}
            if len(entry.options) > 0:
                config = entry.options
        except Exception as e:
            _LOGGER.error(f"Error accessing entry configuration: {e}")
            return False
            
        _LOGGER.debug("Final config: %s", config)
        
        # Read the config values with validation
        HOST = config.get(ATTR_LUX_HOST, "127.0.0.1")
        PORT = config.get(ATTR_LUX_PORT, 8000)
        DONGLE_SERIAL = config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
        SERIAL_NUMBER = config.get(ATTR_LUX_SERIAL_NUMBER, "")
        USE_SERIAL = config.get(ATTR_LUX_USE_SERIAL, False)
        AUTO_REFRESH = config.get(ATTR_LUX_AUTO_REFRESH, PLACEHOLDER_LUX_AUTO_REFRESH)
        REFRESH_INTERVAL = int(config.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL))
        BANK_COUNT = int(config.get(ATTR_LUX_REFRESH_BANK_COUNT, PLACEHOLDER_LUX_REFRESH_BANK_COUNT))

        # Validate critical parameters
        if not HOST or HOST == "":
            _LOGGER.error("Invalid host configuration")
            return False
            
        if not DONGLE_SERIAL or DONGLE_SERIAL == "XXXXXXXXXX":
            _LOGGER.error("Invalid dongle serial configuration")
            return False

        # Handle serial number retrieval with proper error handling
        if USE_SERIAL and (not SERIAL_NUMBER or SERIAL_NUMBER == "" or SERIAL_NUMBER == PLACEHOLDER_LUX_SERIAL_NUMBER):
            try:
                _LOGGER.info("Attempting to retrieve inverter serial number from %s:%s", HOST, PORT)
                
                # Properly await the serial number retrieval
                retrieved_serial = await read_serial_number(HOST, PORT)
                
                if retrieved_serial and isinstance(retrieved_serial, str) and len(retrieved_serial) > 0:
                    SERIAL_NUMBER = retrieved_serial
                    _LOGGER.info("Retrieved inverter serial number: %s", SERIAL_NUMBER)
                    
                    try:
                        # Safely update entry data
                        if hasattr(entry, 'data') and entry.data is not None:
                            new_data = dict(entry.data)
                            new_data[ATTR_LUX_SERIAL_NUMBER] = SERIAL_NUMBER
                            hass.config_entries.async_update_entry(entry, data=new_data)
                            _LOGGER.debug("Updated entry with serial number")
                        else:
                            _LOGGER.warning("Cannot update entry - entry.data is None or missing")
                    except Exception as e:
                        _LOGGER.error(f"Error updating entry with serial number: {e}")
                        # Continue without updating entry
                else:
                    _LOGGER.warning("Could not retrieve valid serial number, using default")
                    SERIAL_NUMBER = PLACEHOLDER_LUX_SERIAL_NUMBER
                    
            except Exception as e:
                _LOGGER.error(f"Error retrieving serial number: {e}")
                _LOGGER.warning("Using placeholder serial number due to retrieval error")
                SERIAL_NUMBER = PLACEHOLDER_LUX_SERIAL_NUMBER

        # Create event handler with validation
        try:
            events = Event(dongle=DONGLE_SERIAL)
        except Exception as e:
            _LOGGER.error(f"Error creating event handler: {e}")
            return False
            
        # Create LuxPower client with comprehensive error handling
        try:
            luxpower_client = LuxPowerClient(
                hass,
                server=HOST,
                port=PORT,
                dongle_serial=str.encode(str(DONGLE_SERIAL)),
                serial_number=str.encode(str(SERIAL_NUMBER)),
                events=events,
                respond_to_heartbeat=config.get(
                    ATTR_LUX_RESPOND_TO_HEARTBEAT,
                    PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT
                )
            )
        except Exception as e:
            _LOGGER.error(f"Error creating LuxPower client: {e}")
            return False

        # Store client in hass data
        try:
            hass.data[events.CLIENT_DAEMON] = luxpower_client

            hass_data = hass.data.setdefault(DOMAIN, {})
            hass_data[entry.entry_id] = {
                "DONGLE": DONGLE_SERIAL,
                "client": luxpower_client,
                "model": "LUXPower Inverter",
            }
        except Exception as e:
            _LOGGER.error(f"Error storing client data: {e}")
            return False

        # Set up platforms with error handling
        try:
            await hass.config_entries.async_forward_entry_setups(entry, PLATFORMS)
            for component in PLATFORMS:
                _LOGGER.debug(f"async_setup_entry: loading: {component}")
        except Exception as e:
            _LOGGER.error(f"Error setting up platforms: {e}")
            return False

        # Set up auto refresh if enabled
        refresh_remove = None
        if AUTO_REFRESH:
            try:
                from datetime import timedelta
                from homeassistant.helpers.event import async_track_time_interval

                async def _scheduled_refresh(_now):
                    try:
                        await hass.services.async_call(
                            DOMAIN,
                            "luxpower_refresh_registers",
                            {"dongle": DONGLE_SERIAL, "bank_count": BANK_COUNT},
                            blocking=True,
                        )
                    except Exception as e:
                        _LOGGER.error(f"Error in scheduled refresh: {e}")

                refresh_remove = async_track_time_interval(
                    hass, _scheduled_refresh, timedelta(seconds=REFRESH_INTERVAL)
                )
                hass_data[entry.entry_id]["refresh_remove"] = refresh_remove
                _LOGGER.info(f"Auto refresh enabled with {REFRESH_INTERVAL}s interval")
                
            except Exception as e:
                _LOGGER.error(f"Error setting up auto refresh: {e}")
                # Continue without auto refresh

        # Initialize client connection
        try:
            # Wait to make sure all entities have been initialised
            await asyncio.sleep(20)

            # Start the main Inverter Polling asyncio loop
            connect_to_inverter = hass.loop.create_task(luxpower_client.start_luxpower_client_daemon())
            
        except Exception as e:
            _LOGGER.error(f"Error starting client daemon: {e}")
            return False

        _LOGGER.info("LuxPower init async_setup done")

        # Set up reload handler
        async def reload_config_entry(hass: HomeAssistant, entry: ConfigEntry):
            try:
                if not connect_to_inverter.done():
                    connect_to_inverter.cancel()
                await hass.config_entries.async_reload(entry.entry_id)
            except Exception as e:
                _LOGGER.error(f"Error in reload handler: {e}")

        entry.async_on_unload(
            entry.add_update_listener(reload_config_entry)
        )

        return True
        
    except Exception as e:
        _LOGGER.error(f"Unexpected error in async_setup_entry: {e}")
        return False


async def async_unload_entry(hass: HomeAssistant, entry: ConfigEntry):
    """Unload a config entry with proper error handling."""
    _LOGGER.info("async_unload_entry: unloading...")
    
    try:
        # Unload platforms
        unload_ok = all(
            await asyncio.gather(
                *[hass.config_entries.async_forward_entry_unload(entry, component) for component in PLATFORMS]
            )
        )

        if unload_ok:
            try:
                # Clean up entry data
                entry_data = hass.data[DOMAIN].pop(entry.entry_id, {})
                
                # Stop client if it exists
                if entry_data.get("client") is not None:
                    luxpower_client = entry_data.get("client")
                    try:
                        luxpower_client.stop_client()
                    except Exception as e:
                        _LOGGER.error(f"Error stopping client: {e}")
                
                # Stop refresh timer if it exists
                if entry_data.get("refresh_remove"):
                    try:
                        entry_data.get("refresh_remove")()
                    except Exception as e:
                        _LOGGER.error(f"Error stopping refresh timer: {e}")
                
                # Ensure domain data structure exists
                hass.data.setdefault(DOMAIN, {})
                
                _LOGGER.info("async_unload_entry: unloaded successfully")
                
            except Exception as e:
                _LOGGER.error(f"Error during cleanup: {e}")
                # Still return True as platforms were unloaded successfully

        return unload_ok
        
    except Exception as e:
        _LOGGER.error(f"Error in async_unload_entry: {e}")
        return False