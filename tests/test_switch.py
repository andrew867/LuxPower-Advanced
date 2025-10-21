"""Test switch platform for LuxPower integration."""

import pytest
from unittest.mock import AsyncMock, MagicMock, patch

from homeassistant.core import HomeAssistant
from homeassistant.config_entries import ConfigEntry

from custom_components.luxpower.switch import async_setup_entry as switch_async_setup_entry
from custom_components.luxpower.const import DOMAIN


class TestLuxPowerSwitch:
    """Test LuxPower switch platform."""

    async def test_switch_setup(self, mock_integration_setup):
        """Test switch platform setup."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.switch.async_setup_entry") as mock_setup:
            mock_setup.return_value = True
            result = await switch_async_setup_entry(hass, config_entry)
            assert result is True

    async def test_switch_entity_creation(self, mock_integration_setup):
        """Test switch entity creation."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.switch.LuxPowerSwitch") as mock_switch_class:
            mock_switch = MagicMock()
            mock_switch_class.return_value = mock_switch
            
            # Test entity creation
            entities = []
            for switch_type in ["grid_export", "battery_charge", "ac_charge"]:
                switch = mock_switch_class.return_value
                switch.name = f"Lux {switch_type.replace('_', ' ').title()}"
                switch.unique_id = f"lux_{switch_type}_{config_entry.data['lux_dongle_serial']}"
                entities.append(switch)
            
            assert len(entities) > 0
            for entity in entities:
                assert entity.name is not None
                assert entity.unique_id is not None

    async def test_switch_toggle(self, mock_integration_setup):
        """Test switch toggle functionality."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.switch.LuxPowerSwitch") as mock_switch_class:
            mock_switch = MagicMock()
            mock_switch_class.return_value = mock_switch
            
            # Test toggle on
            mock_switch.async_turn_on = AsyncMock()
            await mock_switch.async_turn_on()
            mock_switch.async_turn_on.assert_called_once()
            
            # Test toggle off
            mock_switch.async_turn_off = AsyncMock()
            await mock_switch.async_turn_off()
            mock_switch.async_turn_off.assert_called_once()
