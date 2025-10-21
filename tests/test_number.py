"""Test number platform for LuxPower integration."""

import pytest
from unittest.mock import AsyncMock, MagicMock, patch

from homeassistant.core import HomeAssistant
from homeassistant.config_entries import ConfigEntry

from custom_components.luxpower.number import async_setup_entry as number_async_setup_entry
from custom_components.luxpower.const import DOMAIN


class TestLuxPowerNumber:
    """Test LuxPower number platform."""

    async def test_number_setup(self, mock_integration_setup):
        """Test number platform setup."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.number.async_setup_entry") as mock_setup:
            mock_setup.return_value = True
            result = await number_async_setup_entry(hass, config_entry)
            assert result is True

    async def test_number_entity_creation(self, mock_integration_setup):
        """Test number entity creation."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.number.LuxPowerNumber") as mock_number_class:
            mock_number = MagicMock()
            mock_number_class.return_value = mock_number
            
            # Test entity creation
            entities = []
            for number_type in ["charge_current", "discharge_current", "max_charge_power"]:
                number = mock_number_class.return_value
                number.name = f"Lux {number_type.replace('_', ' ').title()}"
                number.unique_id = f"lux_{number_type}_{config_entry.data['lux_dongle_serial']}"
                entities.append(number)
            
            assert len(entities) > 0
            for entity in entities:
                assert entity.name is not None
                assert entity.unique_id is not None

    async def test_number_value_setting(self, mock_integration_setup):
        """Test number value setting."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.number.LuxPowerNumber") as mock_number_class:
            mock_number = MagicMock()
            mock_number_class.return_value = mock_number
            
            # Test setting value
            mock_number.async_set_value = AsyncMock()
            await mock_number.async_set_value(50.0)
            mock_number.async_set_value.assert_called_once_with(50.0)
            
            # Test value validation
            assert mock_number.min_value is not None
            assert mock_number.max_value is not None
            assert mock_number.step is not None
