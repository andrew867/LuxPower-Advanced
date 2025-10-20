"""Test configuration and fixtures for LuxPower integration."""

import asyncio
from unittest.mock import AsyncMock, MagicMock, patch

import pytest
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.setup import async_setup_component

from custom_components.luxpower.const import DOMAIN


@pytest.fixture
def hass():
    """Return a Home Assistant instance for testing."""
    return HomeAssistant("test")


@pytest.fixture
async def mock_hass():
    """Return a properly initialized Home Assistant instance."""
    hass = HomeAssistant("test")
    await hass.async_start()
    yield hass
    await hass.async_stop()


@pytest.fixture
def mock_config_entry():
    """Return a mock config entry."""
    return ConfigEntry(
        version=1,
        domain=DOMAIN,
        title="LuxPower Test",
        data={
            "lux_host": "192.168.1.100",
            "lux_dongle_serial": "BA12345678",
            "lux_serial_number": "0000000000",
            "lux_use_serial": False,
            "lux_respond_to_heartbeat": False,
            "lux_auto_refresh": True,
            "lux_refresh_interval": 120,
            "lux_refresh_bank_count": 2,
        },
        source="user",
        options={},
        entry_id="test_entry_id",
    )


@pytest.fixture
def mock_luxpower_client():
    """Return a mock LuxPower client."""
    client = MagicMock()
    client.start_luxpower_client_daemon = AsyncMock()
    client.stop_client = MagicMock()
    return client


@pytest.fixture
def mock_events():
    """Return mock events for testing."""
    events = MagicMock()
    events.INVERTER_ID = "BA12345678"
    events.EVENT_DATA_RECEIVED = f"{DOMAIN}_BA12345678_data_receive_all_event"
    events.EVENT_UNAVAILABLE = f"{DOMAIN}_BA12345678_unavailable_event"
    return events


@pytest.fixture
async def mock_integration_setup(mock_hass, mock_config_entry, mock_luxpower_client, mock_events):
    """Set up a mock integration for testing."""
    with patch("custom_components.luxpower.lxp.client.LuxPowerClient") as mock_client_class:
        mock_client_class.return_value = mock_luxpower_client
        
        with patch("custom_components.luxpower.helpers.Event") as mock_event_class:
            mock_event_class.return_value = mock_events
            
            # Set up the integration
            mock_hass.data[DOMAIN] = {}
            mock_hass.data[DOMAIN][mock_config_entry.entry_id] = {
                "DONGLE": "BA12345678",
                "client": mock_luxpower_client,
                "model": "LUXPower Inverter",
            }
            
            yield mock_hass, mock_config_entry, mock_luxpower_client, mock_events


@pytest.fixture
def mock_register_data():
    """Return mock register data for testing."""
    return {
        0: 1234,  # Example register values
        1: 5678,
        2: 9012,
        7: 65,    # Model code part 1
        8: 65,    # Model code part 2
    }


@pytest.fixture
def mock_sensor_data():
    """Return mock sensor data for testing."""
    return {
        "battery_voltage": 48.5,
        "battery_current": 25.0,
        "battery_power": 1212.5,
        "battery_soc": 85,
        "solar_voltage": 45.2,
        "solar_current": 12.5,
        "solar_power": 565.0,
        "grid_voltage": 230.0,
        "grid_current": 5.2,
        "grid_power": 1196.0,
        "inverter_temperature": 35.5,
        "battery_temperature": 28.0,
    }
