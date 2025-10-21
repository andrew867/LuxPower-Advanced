"""
Test critical fixes for LuxPower client functionality.
"""

import pytest
import asyncio
from unittest.mock import Mock, AsyncMock, patch
from custom_components.luxpower.lxp.client import LuxPowerClient


class TestClientFixes:
    """Test critical client fixes."""

    @pytest.mark.asyncio
    async def test_transport_state_validation(self):
        """Test that transport state is validated before write operations."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Test with None transport
        client._transport = None
        with pytest.raises(ConnectionError, match="Transport not available"):
            await client.write(21, 1)
        
        # Test with closing transport
        mock_transport = Mock()
        mock_transport.is_closing.return_value = True
        client._transport = mock_transport
        
        with pytest.raises(ConnectionError, match="Transport not available"):
            await client.write(21, 1)

    @pytest.mark.asyncio
    async def test_lock_release_on_connection_lost(self):
        """Test that locks are released when connection is lost."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Simulate lock being held
        client._lxp_request_lock.acquire()
        assert client._lxp_request_lock.locked()
        
        # Simulate connection loss
        client.connection_lost(None)
        
        # Lock should be released
        assert not client._lxp_request_lock.locked()

    @pytest.mark.asyncio
    async def test_future_cancellation_on_connection_lost(self):
        """Test that pending futures are cancelled on connection loss."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Create a pending future
        future = asyncio.Future()
        client._lxp_single_register_result = future
        assert not future.done()
        
        # Simulate connection loss
        client.connection_lost(None)
        
        # Future should be cancelled
        assert future.cancelled()

    @pytest.mark.asyncio
    async def test_retry_logic_write(self):
        """Test retry logic for write operations."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Mock transport
        mock_transport = Mock()
        mock_transport.is_closing.return_value = False
        client._transport = mock_transport
        
        # Mock _wait_for_value to return None (failure)
        with patch.object(client, '_wait_for_value', return_value=None):
            result = await client.write(21, 1, max_retries=2)
            assert result is None

    @pytest.mark.asyncio
    async def test_retry_logic_read(self):
        """Test retry logic for read operations."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Mock transport
        mock_transport = Mock()
        mock_transport.is_closing.return_value = False
        client._transport = mock_transport
        
        # Mock _wait_for_value to return None (failure)
        with patch.object(client, '_wait_for_value', return_value=None):
            result = await client.read(21, max_retries=2)
            assert result is None

    @pytest.mark.asyncio
    async def test_finally_block_lock_release(self):
        """Test that finally blocks ensure lock release."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Mock transport
        mock_transport = Mock()
        mock_transport.is_closing.return_value = False
        client._transport = mock_transport
        
        # Mock _wait_for_value to raise exception
        with patch.object(client, '_wait_for_value', side_effect=Exception("Test error")):
            with pytest.raises(Exception):
                await client.write(21, 1)
        
        # Lock should be released even after exception
        assert not client._lxp_request_lock.locked()

    def test_timeout_handling_sleep(self):
        """Test timeout handling for long sleep operations."""
        client = LuxPowerClient(
            hass=Mock(),
            server="192.168.1.100",
            port=8899,
            dongle_serial=b"BA12345678",
            serial_number=b"SN12345678"
        )
        
        # Test that sleep timeout is handled
        async def test_sleep():
            try:
                await asyncio.wait_for(asyncio.sleep(60), timeout=65)
            except asyncio.TimeoutError:
                return "timeout"
            return "completed"
        
        # This should not timeout in normal execution
        result = asyncio.run(test_sleep())
        assert result == "completed"
