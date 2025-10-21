"""Test initialization of LuxPower integration."""

import pytest
from unittest.mock import AsyncMock, MagicMock, patch

from homeassistant.core import HomeAssistant
from homeassistant.config_entries import ConfigEntry

from custom_components.luxpower import async_setup, async_setup_entry, async_unload_entry
from custom_components.luxpower.const import DOMAIN


class TestLuxPowerInit:
    """Test LuxPower integration initialization."""

    async def test_async_setup(self, hass: HomeAssistant):
        """Test async_setup function."""
        with patch("custom_components.luxpower.connector.ServiceHelper") as mock_service_helper:
            result = await async_setup(hass, {})
            assert result is True
            mock_service_helper.assert_called_once()

    async def test_async_setup_entry_success(self, hass: HomeAssistant, mock_config_entry, mock_luxpower_client, mock_events):
        """Test successful async_setup_entry."""
        with patch("custom_components.luxpower.lxp.client.LuxPowerClient") as mock_client_class:
            mock_client_class.return_value = mock_luxpower_client
            
            with patch("custom_components.luxpower.helpers.Event") as mock_event_class:
                mock_event_class.return_value = mock_events
                
                with patch("custom_components.luxpower.async_setup") as mock_async_setup:
                    mock_async_setup.return_value = True
                    
                    result = await async_setup_entry(hass, mock_config_entry)
                    assert result is True
                    
                    # Check that data was stored
                    assert DOMAIN in hass.data
                    assert mock_config_entry.entry_id in hass.data[DOMAIN]

    async def test_async_setup_entry_with_auto_refresh(self, hass: HomeAssistant, mock_config_entry, mock_luxpower_client, mock_events):
        """Test async_setup_entry with auto refresh enabled."""
        # Enable auto refresh
        mock_config_entry.data["lux_auto_refresh"] = True
        mock_config_entry.data["lux_refresh_interval"] = 60
        
        with patch("custom_components.luxpower.lxp.client.LuxPowerClient") as mock_client_class:
            mock_client_class.return_value = mock_luxpower_client
            
            with patch("custom_components.luxpower.helpers.Event") as mock_event_class:
                mock_event_class.return_value = mock_events
                
                with patch("custom_components.luxpower.async_setup") as mock_async_setup:
                    mock_async_setup.return_value = True
                    
                    with patch("homeassistant.helpers.event.async_track_time_interval") as mock_track:
                        result = await async_setup_entry(hass, mock_config_entry)
                        assert result is True
                        mock_track.assert_called_once()

    async def test_async_unload_entry_success(self, hass: HomeAssistant, mock_config_entry, mock_luxpower_client):
        """Test successful async_unload_entry."""
        # Set up the integration data
        hass.data[DOMAIN] = {
            mock_config_entry.entry_id: {
                "DONGLE": "BA12345678",
                "client": mock_luxpower_client,
                "model": "LUXPower Inverter",
            }
        }
        
        with patch("custom_components.luxpower.async_setup") as mock_async_setup:
            mock_async_setup.return_value = True
            
            result = await async_unload_entry(hass, mock_config_entry)
            assert result is True
            
            # Check that data was cleaned up
            assert mock_config_entry.entry_id not in hass.data[DOMAIN]

    async def test_async_unload_entry_with_refresh_remove(self, hass: HomeAssistant, mock_config_entry, mock_luxpower_client):
        """Test async_unload_entry with refresh remove callback."""
        # Set up the integration data with refresh remove
        refresh_remove = MagicMock()
        hass.data[DOMAIN] = {
            mock_config_entry.entry_id: {
                "DONGLE": "BA12345678",
                "client": mock_luxpower_client,
                "model": "LUXPower Inverter",
                "refresh_remove": refresh_remove,
            }
        }
        
        with patch("custom_components.luxpower.async_setup") as mock_async_setup:
            mock_async_setup.return_value = True
            
            result = await async_unload_entry(hass, mock_config_entry)
            assert result is True
            
            # Check that refresh remove was called
            refresh_remove.assert_called_once()

    async def test_service_registration(self, hass: HomeAssistant):
        """Test that services are properly registered."""
        with patch("custom_components.luxpower.connector.ServiceHelper") as mock_service_helper:
            await async_setup(hass, {})
            
            # Check that services are registered
            assert hass.services.has_service(DOMAIN, "luxpower_refresh_register_bank")
            assert hass.services.has_service(DOMAIN, "luxpower_refresh_registers")
            assert hass.services.has_service(DOMAIN, "luxpower_refresh_holdings")
            assert hass.services.has_service(DOMAIN, "luxpower_reconnect")
            assert hass.services.has_service(DOMAIN, "luxpower_restart")
            assert hass.services.has_service(DOMAIN, "luxpower_reset_settings")
            assert hass.services.has_service(DOMAIN, "luxpower_synctime")
            assert hass.services.has_service(DOMAIN, "luxpower_start_charging")
            assert hass.services.has_service(DOMAIN, "luxpower_stop_charging")
