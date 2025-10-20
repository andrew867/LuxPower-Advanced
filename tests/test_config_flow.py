"""Test configuration flow for LuxPower integration."""

import pytest
from homeassistant import config_entries
from homeassistant.core import HomeAssistant
from homeassistant.data_entry_flow import FlowResultType

from custom_components.luxpower.config_flow import LuxConfigFlow, OptionsFlowHandler
from custom_components.luxpower.const import DOMAIN


class TestLuxConfigFlow:
    """Test LuxPower configuration flow."""

    async def test_user_flow_success(self, hass: HomeAssistant):
        """Test successful user flow."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        assert result["type"] == FlowResultType.FORM
        assert result["step_id"] == "user"
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.100",
                "lux_dongle_serial": "BA12345678",
                "lux_use_serial": False,
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 120,
            },
        )
        
        assert result["type"] == FlowResultType.CREATE_ENTRY
        assert result["title"] == "LuxPower - (BA12345678)"
        assert result["data"]["lux_host"] == "192.168.1.100"
        assert result["data"]["lux_dongle_serial"] == "BA12345678"

    async def test_user_flow_with_serial(self, hass: HomeAssistant):
        """Test user flow with serial number."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.100",
                "lux_dongle_serial": "BA12345678",
                "lux_use_serial": True,
                "lux_serial_number": "SN12345678",
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 120,
            },
        )
        
        assert result["type"] == FlowResultType.CREATE_ENTRY
        assert result["data"]["lux_serial_number"] == "SN12345678"

    async def test_user_flow_invalid_host(self, hass: HomeAssistant):
        """Test user flow with invalid host."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "invalid-host",
                "lux_dongle_serial": "BA12345678",
                "lux_use_serial": False,
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 120,
            },
        )
        
        assert result["type"] == FlowResultType.FORM
        assert result["errors"]["lux_host"] == "host_error"

    async def test_user_flow_invalid_dongle_serial(self, hass: HomeAssistant):
        """Test user flow with invalid dongle serial."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.100",
                "lux_dongle_serial": "INVALID",
                "lux_use_serial": False,
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 120,
            },
        )
        
        assert result["type"] == FlowResultType.FORM
        assert result["errors"]["lux_dongle_serial"] == "dongle_error"

    async def test_user_flow_invalid_serial_number(self, hass: HomeAssistant):
        """Test user flow with invalid serial number."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.100",
                "lux_dongle_serial": "BA12345678",
                "lux_use_serial": True,
                "lux_serial_number": "INVALID",
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 120,
            },
        )
        
        assert result["type"] == FlowResultType.FORM
        assert "lux_serial_number" in result["errors"]
        assert "lux_use_serial" in result["errors"]

    async def test_user_flow_invalid_refresh_interval(self, hass: HomeAssistant):
        """Test user flow with invalid refresh interval."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.100",
                "lux_dongle_serial": "BA12345678",
                "lux_use_serial": False,
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 10,  # Too low
            },
        )
        
        assert result["type"] == FlowResultType.FORM
        assert result["errors"]["lux_refresh_interval"] == "refresh_interval_error"

    async def test_duplicate_dongle_serial(self, hass: HomeAssistant, mock_config_entry):
        """Test duplicate dongle serial detection."""
        # Add existing entry
        hass.data[DOMAIN] = {mock_config_entry.entry_id: {"DONGLE": "BA12345678"}}
        
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.101",
                "lux_dongle_serial": "BA12345678",  # Same as existing
                "lux_use_serial": False,
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 120,
            },
        )
        
        assert result["type"] == FlowResultType.FORM
        assert result["errors"]["lux_dongle_serial"] == "exist_error"


class TestOptionsFlowHandler:
    """Test LuxPower options flow handler."""

    async def test_options_flow_success(self, hass: HomeAssistant, mock_config_entry):
        """Test successful options flow."""
        handler = OptionsFlowHandler(mock_config_entry)
        
        result = await handler.async_step_init()
        assert result["type"] == FlowResultType.FORM
        
        result = await handler.async_step_user(
            {
                "lux_host": "192.168.1.200",
                "lux_dongle_serial": "BA87654321",
                "lux_use_serial": True,
                "lux_serial_number": "SN87654321",
                "lux_respond_to_heartbeat": True,
                "lux_auto_refresh": False,
                "lux_refresh_interval": 60,
                "lux_refresh_bank_count": 3,
            }
        )
        
        assert result["type"] == FlowResultType.CREATE_ENTRY
        assert result["data"]["lux_host"] == "192.168.1.200"
        assert result["data"]["lux_refresh_interval"] == 60

    async def test_options_flow_validation_errors(self, hass: HomeAssistant, mock_config_entry):
        """Test options flow validation errors."""
        handler = OptionsFlowHandler(mock_config_entry)
        
        result = await handler.async_step_user(
            {
                "lux_host": "invalid-host",
                "lux_dongle_serial": "INVALID",
                "lux_use_serial": True,
                "lux_serial_number": "INVALID",
                "lux_respond_to_heartbeat": False,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 10,
                "lux_refresh_bank_count": 0,
            }
        )
        
        assert result["type"] == FlowResultType.FORM
        assert "lux_host" in result["errors"]
        assert "lux_dongle_serial" in result["errors"]
        assert "lux_serial_number" in result["errors"]
        assert "lux_refresh_interval" in result["errors"]
        assert "lux_refresh_bank_count" in result["errors"]

    async def test_options_flow_reconfiguration_scenarios(self, hass: HomeAssistant, mock_config_entry):
        """Test various reconfiguration scenarios."""
        handler = OptionsFlowHandler(mock_config_entry)
        
        # Test changing IP address
        result = await handler.async_step_user({
            "lux_host": "192.168.1.200",
            "lux_dongle_serial": "BA12345678",
            "lux_use_serial": False,
            "lux_respond_to_heartbeat": False,
            "lux_auto_refresh": True,
            "lux_refresh_interval": 60,
            "lux_refresh_bank_count": 3,
        })
        assert result["type"] == FlowResultType.CREATE_ENTRY
        assert result["data"]["lux_host"] == "192.168.1.200"
        assert result["data"]["lux_refresh_interval"] == 60

    async def test_options_flow_edge_cases(self, hass: HomeAssistant, mock_config_entry):
        """Test edge cases in options flow."""
        handler = OptionsFlowHandler(mock_config_entry)
        
        # Test minimum refresh interval
        result = await handler.async_step_user({
            "lux_host": "192.168.1.100",
            "lux_dongle_serial": "BA12345678",
            "lux_use_serial": False,
            "lux_respond_to_heartbeat": False,
            "lux_auto_refresh": True,
            "lux_refresh_interval": 30,  # Minimum value
            "lux_refresh_bank_count": 1,  # Minimum value
        })
        assert result["type"] == FlowResultType.CREATE_ENTRY
        
        # Test maximum values
        result = await handler.async_step_user({
            "lux_host": "192.168.1.100",
            "lux_dongle_serial": "BA12345678",
            "lux_use_serial": False,
            "lux_respond_to_heartbeat": False,
            "lux_auto_refresh": True,
            "lux_refresh_interval": 120,  # Maximum value
            "lux_refresh_bank_count": 6,  # Maximum value
        })
        assert result["type"] == FlowResultType.CREATE_ENTRY

    async def test_config_flow_with_serial_number_validation(self, hass: HomeAssistant):
        """Test config flow with serial number validation."""
        result = await hass.config_entries.flow.async_init(
            DOMAIN, context={"source": config_entries.SOURCE_USER}
        )
        
        # Test with valid serial number
        result = await hass.config_entries.flow.async_configure(
            result["flow_id"],
            {
                "lux_host": "192.168.1.100",
                "lux_dongle_serial": "BA12345678",
                "lux_use_serial": True,
                "lux_serial_number": "SN12345678",
                "lux_respond_to_heartbeat": True,
                "lux_auto_refresh": True,
                "lux_refresh_interval": 90,
            },
        )
        assert result["type"] == FlowResultType.CREATE_ENTRY
        assert result["data"]["lux_serial_number"] == "SN12345678"
        assert result["data"]["lux_respond_to_heartbeat"] is True
