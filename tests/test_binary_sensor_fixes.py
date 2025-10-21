"""
Test binary sensor fixes for LuxPower integration.
"""

import pytest
from unittest.mock import Mock, patch
from custom_components.luxpower.binary_sensor import LuxBinarySensorEntity


class TestBinarySensorFixes:
    """Test binary sensor event listener fixes."""

    def test_binary_sensor_initialization(self):
        """Test that binary sensor initializes without AttributeError."""
        # Mock Home Assistant objects
        mock_hass = Mock()
        mock_entry = Mock()
        mock_entry.entry_id = "test_entry"
        
        entity_definition = {
            "name": "Test Binary Sensor",
            "register": 0,
            "bank": 0,
            "enabled": True
        }
        
        # This should not raise AttributeError
        entity = LuxBinarySensorEntity(
            hass=mock_hass,
            entry=mock_entry,
            entity_definition=entity_definition,
            dongle_serial="BA12345678"
        )
        
        assert entity is not None
        assert entity._name == "Test Binary Sensor"

    def test_binary_sensor_event_listeners(self):
        """Test that event listeners are set up correctly."""
        mock_hass = Mock()
        mock_entry = Mock()
        mock_entry.entry_id = "test_entry"
        
        entity_definition = {
            "name": "Test Binary Sensor",
            "register": 0,
            "bank": 0,
            "enabled": True
        }
        
        entity = LuxBinarySensorEntity(
            hass=mock_hass,
            entry=mock_entry,
            entity_definition=entity_definition,
            dongle_serial="BA12345678"
        )
        
        # Mock the event object
        mock_event = Mock()
        mock_event.EVENT_DATA_BANK0_RECEIVED = "luxpower_data_bank0_BA12345678"
        mock_event.EVENT_UNAVAILABLE_RECEIVED = "luxpower_unavailable_BA12345678"
        entity._event = mock_event
        
        # Test async_added_to_hass
        entity.async_added_to_hass()
        
        # Should have called async_listen for the appropriate bank
        mock_hass.bus.async_listen.assert_called()

    def test_binary_sensor_unavailable_handling(self):
        """Test that unavailable events are handled correctly."""
        mock_hass = Mock()
        mock_entry = Mock()
        mock_entry.entry_id = "test_entry"
        
        entity_definition = {
            "name": "Test Binary Sensor",
            "register": 0,
            "bank": 0,
            "enabled": True
        }
        
        entity = LuxBinarySensorEntity(
            hass=mock_hass,
            entry=mock_entry,
            entity_definition=entity_definition,
            dongle_serial="BA12345678"
        )
        
        # Test _gone_unavailable method
        entity._gone_unavailable(None)
        
        # State should be set to None
        assert entity._state is None

    def test_binary_sensor_update_callback(self):
        """Test that update callback works without errors."""
        mock_hass = Mock()
        mock_entry = Mock()
        mock_entry.entry_id = "test_entry"
        
        entity_definition = {
            "name": "Test Binary Sensor",
            "register": 0,
            "bank": 0,
            "enabled": True
        }
        
        entity = LuxBinarySensorEntity(
            hass=mock_hass,
            entry=mock_entry,
            entity_definition=entity_definition,
            dongle_serial="BA12345678"
        )
        
        # Test update callback with mock event data
        mock_event_data = {
            "data": {
                "0": [1, 2, 3, 4]  # Mock register data
            }
        }
        
        # This should not raise an exception
        entity._update_callback(mock_event_data)

    def test_binary_sensor_bank_specific_listeners(self):
        """Test that correct event listeners are set up for different banks."""
        mock_hass = Mock()
        mock_entry = Mock()
        mock_entry.entry_id = "test_entry"
        
        # Test bank 0
        entity_definition_bank0 = {
            "name": "Test Binary Sensor Bank 0",
            "register": 0,
            "bank": 0,
            "enabled": True
        }
        
        entity_bank0 = LuxBinarySensorEntity(
            hass=mock_hass,
            entry=mock_entry,
            entity_definition=entity_definition_bank0,
            dongle_serial="BA12345678"
        )
        
        # Mock the event object
        mock_event = Mock()
        mock_event.EVENT_DATA_BANK0_RECEIVED = "luxpower_data_bank0_BA12345678"
        mock_event.EVENT_DATA_BANK1_RECEIVED = "luxpower_data_bank1_BA12345678"
        mock_event.EVENT_UNAVAILABLE_RECEIVED = "luxpower_unavailable_BA12345678"
        entity_bank0._event = mock_event
        
        # Reset mock to track calls
        mock_hass.bus.async_listen.reset_mock()
        
        entity_bank0.async_added_to_hass()
        
        # Should listen to bank 0 events
        calls = mock_hass.bus.async_listen.call_args_list
        bank0_called = any("bank0" in str(call) for call in calls)
        assert bank0_called

    def test_binary_sensor_error_handling(self):
        """Test that binary sensor handles errors gracefully."""
        mock_hass = Mock()
        mock_entry = Mock()
        mock_entry.entry_id = "test_entry"
        
        entity_definition = {
            "name": "Test Binary Sensor",
            "register": 0,
            "bank": 0,
            "enabled": True
        }
        
        entity = LuxBinarySensorEntity(
            hass=mock_hass,
            entry=mock_entry,
            entity_definition=entity_definition,
            dongle_serial="BA12345678"
        )
        
        # Test update callback with invalid data
        invalid_event_data = {
            "data": None  # Invalid data
        }
        
        # Should not raise exception
        entity._update_callback(invalid_event_data)
        
        # Test with missing data key
        missing_data = {}
        
        # Should not raise exception
        entity._update_callback(missing_data)
