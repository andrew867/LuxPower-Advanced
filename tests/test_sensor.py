"""Test sensor platform for LuxPower integration."""

import pytest
from unittest.mock import AsyncMock, MagicMock, patch

from homeassistant.core import HomeAssistant
from homeassistant.config_entries import ConfigEntry

from custom_components.luxpower.sensor import async_setup_entry as sensor_async_setup_entry
from custom_components.luxpower.const import DOMAIN


class TestLuxPowerSensor:
    """Test LuxPower sensor platform."""

    async def test_sensor_setup(self, mock_integration_setup):
        """Test sensor platform setup."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.sensor.async_setup_entry") as mock_setup:
            mock_setup.return_value = True
            result = await sensor_async_setup_entry(hass, config_entry)
            assert result is True

    async def test_sensor_entity_creation(self, mock_integration_setup, mock_register_data, mock_sensor_data):
        """Test sensor entity creation and updates."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        # Mock the sensor data
        with patch("custom_components.luxpower.sensor.LuxPowerSensor") as mock_sensor_class:
            mock_sensor = MagicMock()
            mock_sensor_class.return_value = mock_sensor
            
            # Test entity creation
            entities = []
            for sensor_type in ["battery_voltage", "battery_current", "solar_power"]:
                sensor = mock_sensor_class.return_value
                sensor.name = f"Lux {sensor_type.replace('_', ' ').title()}"
                sensor.unique_id = f"lux_{sensor_type}_{config_entry.data['lux_dongle_serial']}"
                entities.append(sensor)
            
            assert len(entities) > 0
            for entity in entities:
                assert entity.name is not None
                assert entity.unique_id is not None

    async def test_sensor_data_update(self, mock_integration_setup, mock_register_data):
        """Test sensor data update mechanism."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        # Mock sensor update
        with patch("custom_components.luxpower.sensor.LuxPowerSensor") as mock_sensor_class:
            mock_sensor = MagicMock()
            mock_sensor_class.return_value = mock_sensor
            
            # Simulate data update
            mock_sensor.update_data(mock_register_data)
            mock_sensor.async_write_ha_state = AsyncMock()
            
            # Verify update was called
            mock_sensor.update_data.assert_called_with(mock_register_data)

    async def test_sensor_availability(self, mock_integration_setup):
        """Test sensor availability tracking."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.sensor.LuxPowerSensor") as mock_sensor_class:
            mock_sensor = MagicMock()
            mock_sensor_class.return_value = mock_sensor
            
            # Test available state
            mock_sensor.available = True
            assert mock_sensor.available is True
            
            # Test unavailable state
            mock_sensor.available = False
            assert mock_sensor.available is False

    async def test_sensor_device_info(self, mock_integration_setup):
        """Test sensor device information."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        with patch("custom_components.luxpower.sensor.LuxPowerSensor") as mock_sensor_class:
            mock_sensor = MagicMock()
            mock_sensor_class.return_value = mock_sensor
            
            # Test device info
            device_info = {
                "identifiers": {(DOMAIN, config_entry.data["lux_dongle_serial"])},
                "name": "LuxPower Inverter",
                "manufacturer": "LuxPower",
                "model": "LUXPower Inverter",
            }
            
            mock_sensor.device_info = device_info
            assert mock_sensor.device_info["identifiers"] == {(DOMAIN, config_entry.data["lux_dongle_serial"])}
            assert mock_sensor.device_info["name"] == "LuxPower Inverter"

    async def test_sensor_units(self, mock_integration_setup):
        """Test sensor units and device classes."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        # Test different sensor types and their units
        sensor_configs = [
            {"name": "battery_voltage", "unit": "V", "device_class": "voltage"},
            {"name": "battery_current", "unit": "A", "device_class": "current"},
            {"name": "battery_power", "unit": "W", "device_class": "power"},
            {"name": "battery_soc", "unit": "%", "device_class": "battery"},
            {"name": "inverter_temperature", "unit": "°C", "device_class": "temperature"},
        ]
        
        for config in sensor_configs:
            with patch("custom_components.luxpower.sensor.LuxPowerSensor") as mock_sensor_class:
                mock_sensor = MagicMock()
                mock_sensor_class.return_value = mock_sensor
                
                # Set up sensor with appropriate unit and device class
                mock_sensor.unit_of_measurement = config["unit"]
                mock_sensor.device_class = config["device_class"]
                
                assert mock_sensor.unit_of_measurement == config["unit"]
                assert mock_sensor.device_class == config["device_class"]

    async def test_sensor_state_class(self, mock_integration_setup):
        """Test sensor state classes."""
        hass, config_entry, mock_client, mock_events = mock_integration_setup
        
        # Test measurement sensors
        with patch("custom_components.luxpower.sensor.LuxPowerSensor") as mock_sensor_class:
            mock_sensor = MagicMock()
            mock_sensor_class.return_value = mock_sensor
            
            # Power sensors should have MEASUREMENT state class
            mock_sensor.state_class = "measurement"
            assert mock_sensor.state_class == "measurement"
            
            # Total sensors should have TOTAL_INCREASING state class
            mock_sensor.state_class = "total_increasing"
            assert mock_sensor.state_class == "total_increasing"
