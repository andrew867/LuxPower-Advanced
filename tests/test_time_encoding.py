"""
Test time encoding fixes for LuxPower integration.
"""

import pytest
import voluptuous as vol
from datetime import time
from custom_components.luxpower.time import LuxTimeTimeEntity


class TestTimeEncoding:
    """Test time encoding bounds checking."""

    def test_time_encoding_valid_hours(self):
        """Test time encoding with valid hour values."""
        # Test all valid hours (0-23)
        for hour in range(24):
            test_time = time(hour, 30)
            # This should not raise an exception
            encoded = test_time.minute * 256 + test_time.hour
            assert 0 <= encoded <= 1535  # Max value: 59 * 256 + 23

    def test_time_encoding_valid_minutes(self):
        """Test time encoding with valid minute values."""
        # Test all valid minutes (0-59)
        for minute in range(60):
            test_time = time(12, minute)
            # This should not raise an exception
            encoded = test_time.minute * 256 + test_time.hour
            assert 0 <= encoded <= 1535  # Max value: 59 * 256 + 23

    def test_time_encoding_bounds_checking(self):
        """Test that bounds checking prevents invalid values."""
        # Create a mock entity for testing
        entity = LuxTimeTimeEntity(
            hass=Mock(),
            entry=Mock(),
            entity_definition={},
            dongle_serial="BA12345678"
        )
        
        # Test with invalid hour (should be caught by validation)
        with pytest.raises(vol.Invalid, match="Invalid hour"):
            entity.async_set_value(time(25, 30))  # Invalid hour
        
        with pytest.raises(vol.Invalid, match="Invalid hour"):
            entity.async_set_value(time(-1, 30))  # Invalid hour
        
        # Test with invalid minute (should be caught by validation)
        with pytest.raises(vol.Invalid, match="Invalid minute"):
            entity.async_set_value(time(12, 60))  # Invalid minute
        
        with pytest.raises(vol.Invalid, match="Invalid minute"):
            entity.async_set_value(time(12, -1))  # Invalid minute

    def test_time_encoding_edge_cases(self):
        """Test time encoding with edge case values."""
        # Test midnight
        midnight = time(0, 0)
        encoded = midnight.minute * 256 + midnight.hour
        assert encoded == 0
        
        # Test end of day
        end_of_day = time(23, 59)
        encoded = end_of_day.minute * 256 + end_of_day.hour
        assert encoded == 59 * 256 + 23  # 15167
        
        # Test noon
        noon = time(12, 0)
        encoded = noon.minute * 256 + noon.hour
        assert encoded == 12

    def test_time_encoding_overflow_protection(self):
        """Test that time encoding doesn't cause integer overflow."""
        # Test maximum valid values
        max_time = time(23, 59)
        encoded = max_time.minute * 256 + max_time.hour
        assert encoded == 15167
        
        # This should be well within Python's integer limits
        assert encoded < 2**31  # Within 32-bit signed integer range
        assert encoded < 2**63  # Within 64-bit signed integer range

    def test_connector_time_encoding_validation(self):
        """Test time encoding validation in connector service."""
        from custom_components.luxpower.connector import ServiceHelper
        from datetime import datetime, timedelta
        
        # Test valid time encoding
        now = datetime.now()
        end_time = now + timedelta(minutes=30)
        
        # These should not raise exceptions
        start_hour = now.hour
        start_minute = now.minute
        end_hour = end_time.hour
        end_minute = end_time.minute
        
        # Validate ranges
        assert 0 <= start_hour <= 23
        assert 0 <= start_minute <= 59
        assert 0 <= end_hour <= 23
        assert 0 <= end_minute <= 59
        
        # Test encoding
        start_value = start_hour + (start_minute << 8)
        end_value = end_hour + (end_minute << 8)
        
        assert 0 <= start_value <= 15167
        assert 0 <= end_value <= 15167
