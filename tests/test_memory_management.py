"""
Test memory management fixes for LuxPower integration.
"""

import pytest
from custom_components.luxpower.LXPPacket import LXPPacket


class TestMemoryManagement:
    """Test memory leak prevention."""

    def test_cleanup_old_entries(self):
        """Test that old entries are cleaned up to prevent memory leaks."""
        packet = LXPPacket()
        
        # Fill dictionaries with test data
        for i in range(1200):  # Exceed the 1000 limit
            packet.readValuesInt[i] = i
            packet.readValues[i] = f"value_{i}"
            packet.readValuesHex[i] = f"hex_{i}"
        
        # Verify dictionaries are full
        assert len(packet.readValuesInt) == 1200
        assert len(packet.readValues) == 1200
        assert len(packet.readValuesHex) == 1200
        
        # Trigger cleanup
        packet._cleanup_old_entries()
        
        # Should be reduced to 500 entries
        assert len(packet.readValuesInt) == 500
        assert len(packet.readValues) == 500
        assert len(packet.readValuesHex) == 500
        
        # Should keep the most recent entries (highest numbers)
        assert 700 in packet.readValuesInt  # Should be kept
        assert 0 not in packet.readValuesInt  # Should be removed

    def test_cleanup_handles_errors(self):
        """Test that cleanup handles errors gracefully."""
        packet = LXPPacket()
        
        # Mock the dictionaries to raise an error
        with pytest.MonkeyPatch().context() as m:
            m.setattr(packet, 'readValuesInt', Mock(side_effect=KeyError("test")))
            
            # Should not raise exception
            packet._cleanup_old_entries()

    def test_cleanup_with_empty_dictionaries(self):
        """Test cleanup with empty dictionaries."""
        packet = LXPPacket()
        
        # Should not raise exception with empty dictionaries
        packet._cleanup_old_entries()
        
        assert len(packet.readValuesInt) == 0
        assert len(packet.readValues) == 0
        assert len(packet.readValuesHex) == 0

    def test_cleanup_below_threshold(self):
        """Test that cleanup doesn't run when below threshold."""
        packet = LXPPacket()
        
        # Add some data below threshold
        for i in range(500):
            packet.readValuesInt[i] = i
            packet.readValues[i] = f"value_{i}"
            packet.readValuesHex[i] = f"hex_{i}"
        
        original_length = len(packet.readValuesInt)
        
        # Cleanup should not run
        packet._cleanup_old_entries()
        
        # Length should be unchanged
        assert len(packet.readValuesInt) == original_length
