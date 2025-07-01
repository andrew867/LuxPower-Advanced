"""

This is a docstring placeholder.

This is where we will describe what this module does

"""

import ipaddress
import logging
import re

import voluptuous as vol
from homeassistant import config_entries
from homeassistant.core import callback

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_RESPOND_TO_HEARTBEAT,
    ATTR_LUX_AUTO_REFRESH,
    ATTR_LUX_REFRESH_INTERVAL,
    ATTR_LUX_REFRESH_BANK_COUNT,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    PLACEHOLDER_LUX_DONGLE_SERIAL,
    PLACEHOLDER_LUX_HOST,
    PLACEHOLDER_LUX_PORT,
    PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT,
    PLACEHOLDER_LUX_AUTO_REFRESH,
    PLACEHOLDER_LUX_REFRESH_INTERVAL,
    PLACEHOLDER_LUX_REFRESH_BANK_COUNT,
    PLACEHOLDER_LUX_USE_SERIAL,
)

_LOGGER = logging.getLogger(__name__)

# Security constants
MAX_HOST_LENGTH = 253  # RFC compliant max domain name length
MIN_REFRESH_INTERVAL = 30
MAX_REFRESH_INTERVAL = 300
MIN_BANK_COUNT = 1
MAX_BANK_COUNT = 6
DONGLE_SERIAL_LENGTH = 10


def validate_ip_address(host):
    """Validate IP address or hostname with security checks."""
    if not host or not isinstance(host, str):
        return False
    
    # Length check
    if len(host) > MAX_HOST_LENGTH:
        return False
    
    # Remove any whitespace that could be used for injection
    host = host.strip()
    if not host:
        return False
    
    try:
        # Try as IP address first
        addr = ipaddress.ip_address(host)
        
        # Block dangerous IP ranges
        if addr.is_loopback and str(addr) != "127.0.0.1":
            return False
        if addr.is_multicast or addr.is_reserved:
            return False
        if addr.is_link_local or addr.is_unspecified:
            return False
        
        return True
    except ValueError:
        # Not an IP, try as hostname
        return validate_hostname(host)


def validate_hostname(hostname):
    """Validate hostname format."""
    if not hostname or len(hostname) > MAX_HOST_LENGTH:
        return False
    
    # Check for valid hostname characters and structure
    if hostname.startswith('-') or hostname.endswith('-'):
        return False
    
    # Allow only valid hostname characters
    allowed_chars = re.compile(r"^[a-zA-Z0-9.-]+$")
    if not allowed_chars.match(hostname):
        return False
    
    # Check each label in the hostname
    labels = hostname.split('.')
    for label in labels:
        if not label:  # Empty label
            return False
        if len(label) > 63:  # Max label length
            return False
        if label.startswith('-') or label.endswith('-'):
            return False
        # Label must start and end with alphanumeric
        if not (label[0].isalnum() and label[-1].isalnum()):
            return False
    
    return True


def validate_dongle_serial(serial):
    """Validate dongle serial with security checks."""
    if not serial or not isinstance(serial, str):
        return False
    
    # Remove whitespace
    serial = serial.strip()
    
    # Check length
    if len(serial) != DONGLE_SERIAL_LENGTH:
        return False
    
    # Only allow alphanumeric characters
    if not serial.isalnum():
        return False
    
    # Additional security: check for common attack patterns
    if serial.lower() in ['0000000000', '1111111111', 'aaaaaaaaaa']:
        return False
    
    return True


def validate_refresh_interval(interval):
    """Validate refresh interval."""
    try:
        interval = int(interval)
        return MIN_REFRESH_INTERVAL <= interval <= MAX_REFRESH_INTERVAL
    except (ValueError, TypeError):
        return False


def validate_bank_count(count):
    """Validate bank count."""
    try:
        count = int(count)
        return MIN_BANK_COUNT <= count <= MAX_BANK_COUNT
    except (ValueError, TypeError):
        return False


def sanitize_input(value, max_length=100):
    """Sanitize user input."""
    if not isinstance(value, str):
        return ""
    
    # Remove dangerous characters
    value = value.strip()
    
    # Limit length
    if len(value) > max_length:
        value = value[:max_length]
    
    return value


class LuxConfigFlow(config_entries.ConfigFlow, domain=DOMAIN):  # type:ignore
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    VERSION = 1
    CONNECTION_CLASS = config_entries.CONN_CLASS_LOCAL_PUSH

    async def async_step_user(self, user_input=None):
        """Handle user input with comprehensive validation."""
        errors = {}

        if user_input is not None:
            try:
                # Sanitize inputs
                user_input[ATTR_LUX_HOST] = sanitize_input(user_input.get(ATTR_LUX_HOST, ""), MAX_HOST_LENGTH)
                user_input[ATTR_LUX_DONGLE_SERIAL] = sanitize_input(user_input.get(ATTR_LUX_DONGLE_SERIAL, ""), 20)
                
                # Set secure defaults
                user_input[ATTR_LUX_PORT] = PLACEHOLDER_LUX_PORT
                user_input[ATTR_LUX_REFRESH_BANK_COUNT] = PLACEHOLDER_LUX_REFRESH_BANK_COUNT
                
                # Validate inputs
                errors = self._validate_user_input(user_input)
                
                if not errors:
                    _LOGGER.info("LuxConfigFlow: saving options with validation passed")
                    return self.async_create_entry(
                        title=f"LuxPower - ({user_input[ATTR_LUX_DONGLE_SERIAL]})",
                        data=user_input,
                    )
            except Exception as e:
                _LOGGER.error(f"Error processing user input: {e}")
                errors["base"] = "unknown_error"

        config_entry = user_input if user_input else {}

        # Check if luxpower device already exists to use sn in entities
        if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
            placeholder_use_serial = True
        else:
            placeholder_use_serial = PLACEHOLDER_LUX_USE_SERIAL

        # Specify items in the order they are to be displayed in the UI
        try:
            schema = {
                vol.Required(
                    ATTR_LUX_HOST, 
                    default=config_entry.get(ATTR_LUX_HOST, PLACEHOLDER_LUX_HOST)
                ): vol.All(str, vol.Length(min=1, max=MAX_HOST_LENGTH)),
                vol.Required(
                    ATTR_LUX_DONGLE_SERIAL, 
                    default=config_entry.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)
                ): vol.All(str, vol.Length(min=DONGLE_SERIAL_LENGTH, max=DONGLE_SERIAL_LENGTH)),
            }

            schema.update({
                vol.Optional(
                    ATTR_LUX_USE_SERIAL, 
                    default=config_entry.get(ATTR_LUX_USE_SERIAL, placeholder_use_serial)
                ): bool,
                vol.Optional(
                    ATTR_LUX_RESPOND_TO_HEARTBEAT, 
                    default=config_entry.get(ATTR_LUX_RESPOND_TO_HEARTBEAT, PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)
                ): bool,
                vol.Optional(
                    ATTR_LUX_AUTO_REFRESH, 
                    default=config_entry.get(ATTR_LUX_AUTO_REFRESH, PLACEHOLDER_LUX_AUTO_REFRESH)
                ): bool,
                vol.Optional(
                    ATTR_LUX_REFRESH_INTERVAL, 
                    default=config_entry.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
                ): vol.All(int, vol.Range(min=MIN_REFRESH_INTERVAL, max=MAX_REFRESH_INTERVAL)),
            })
            
            data_schema = vol.Schema(schema)
            
        except Exception as e:
            _LOGGER.error(f"Error creating schema: {e}")
            errors["base"] = "schema_error"
            data_schema = vol.Schema({})
            
        return self.async_show_form(
            step_id="user",
            data_schema=data_schema,
            errors=errors,
        )
    
    def _validate_user_input(self, user_input):
        """Validate user input with comprehensive security checks."""
        errors = {}
        
        try:
            # Validate host
            host = user_input.get(ATTR_LUX_HOST, "")
            if not validate_ip_address(host):
                errors[ATTR_LUX_HOST] = "host_error"
            
            # Validate dongle serial
            dongle = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            if not validate_dongle_serial(dongle):
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
            
            # Validate refresh interval
            ri = user_input.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
            if not validate_refresh_interval(ri):
                errors[ATTR_LUX_REFRESH_INTERVAL] = "refresh_interval_error"
            
            # Check for duplicate dongles
            if self.hass.data.get(DOMAIN, None) is not None and self.hass.data[DOMAIN].__len__() > 0:
                for entry in self.hass.data[DOMAIN]:
                    entry_data = self.hass.data[DOMAIN][entry]
                    if entry_data.get("DONGLE") == dongle:
                        errors[ATTR_LUX_DONGLE_SERIAL] = "exist_error"
                        break
            
            # Additional security validations
            self._validate_security_constraints(user_input, errors)
            
        except Exception as e:
            _LOGGER.error(f"Error in validation: {e}")
            errors["base"] = "validation_error"
            
        return errors
    
    def _validate_security_constraints(self, user_input, errors):
        """Additional security constraint validation."""
        try:
            # Check for suspicious patterns
            host = user_input.get(ATTR_LUX_HOST, "")
            
            # Block localhost variants that might bypass security
            dangerous_hosts = [
                "localhost", "127.0.0.1", "0.0.0.0", 
                "::1", "[::1]", "0:0:0:0:0:0:0:1"
            ]
            if host.lower() in dangerous_hosts and host != "127.0.0.1":
                errors[ATTR_LUX_HOST] = "host_error"
            
            # Check for potential injection patterns in dongle serial
            dongle = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            suspicious_patterns = ["'", '"', "<", ">", "&", "%", "\\", "/"]
            if any(pattern in dongle for pattern in suspicious_patterns):
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
            
            # Validate boolean inputs are actually booleans
            bool_fields = [ATTR_LUX_USE_SERIAL, ATTR_LUX_RESPOND_TO_HEARTBEAT, ATTR_LUX_AUTO_REFRESH]
            for field in bool_fields:
                if field in user_input and not isinstance(user_input[field], bool):
                    errors[field] = "invalid_type"
                    
        except Exception as e:
            _LOGGER.error(f"Error in security validation: {e}")
            errors["base"] = "security_validation_error"

    @staticmethod
    @callback
    def async_get_options_flow(config_entry):
        """Get options flow handler."""
        return OptionsFlowHandler(config_entry)


class OptionsFlowHandler(config_entries.OptionsFlow):
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    def __init__(self, config_entry):
        """Initialize options flow."""
        self.config_entry = config_entry

    async def async_step_init(self, user_input=None):
        """Manage the options."""
        return await self.async_step_user(user_input)

    async def async_step_user(self, user_input):
        """Handle options with validation."""
        errors = {}

        if user_input is not None:
            try:
                # Sanitize inputs
                user_input[ATTR_LUX_HOST] = sanitize_input(user_input.get(ATTR_LUX_HOST, ""), MAX_HOST_LENGTH)
                user_input[ATTR_LUX_DONGLE_SERIAL] = sanitize_input(user_input.get(ATTR_LUX_DONGLE_SERIAL, ""), 20)
                
                # Set secure defaults
                user_input[ATTR_LUX_PORT] = PLACEHOLDER_LUX_PORT
                
                # Validate inputs
                errors = self._validate_user_input(user_input)
                
                if not errors:
                    _LOGGER.info("OptionsFlowHandler: saving options with validation passed")
                    return self.async_create_entry(title="LuxPower ()", data=user_input)
                    
            except Exception as e:
                _LOGGER.error(f"Error processing options: {e}")
                errors["base"] = "unknown_error"

        config_entry = self.config_entry.data
        if len(self.config_entry.options) > 0:
            config_entry = self.config_entry.options
        if user_input:
            config_entry = user_input

        try:
            schema = {
                vol.Required(
                    ATTR_LUX_HOST, 
                    default=config_entry.get(ATTR_LUX_HOST, PLACEHOLDER_LUX_HOST)
                ): vol.All(str, vol.Length(min=1, max=MAX_HOST_LENGTH)),
                vol.Required(
                    ATTR_LUX_DONGLE_SERIAL, 
                    default=config_entry.get(ATTR_LUX_DONGLE_SERIAL, PLACEHOLDER_LUX_DONGLE_SERIAL)
                ): vol.All(str, vol.Length(min=DONGLE_SERIAL_LENGTH, max=DONGLE_SERIAL_LENGTH)),
            }

            schema.update({
                vol.Optional(
                    ATTR_LUX_USE_SERIAL, 
                    default=config_entry.get(ATTR_LUX_USE_SERIAL, PLACEHOLDER_LUX_USE_SERIAL)
                ): bool,
                vol.Optional(
                    ATTR_LUX_RESPOND_TO_HEARTBEAT, 
                    default=config_entry.get(ATTR_LUX_RESPOND_TO_HEARTBEAT, PLACEHOLDER_LUX_RESPOND_TO_HEARTBEAT)
                ): bool,
                vol.Optional(
                    ATTR_LUX_AUTO_REFRESH, 
                    default=config_entry.get(ATTR_LUX_AUTO_REFRESH, PLACEHOLDER_LUX_AUTO_REFRESH)
                ): bool,
                vol.Optional(
                    ATTR_LUX_REFRESH_INTERVAL, 
                    default=config_entry.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
                ): vol.All(int, vol.Range(min=MIN_REFRESH_INTERVAL, max=MAX_REFRESH_INTERVAL)),
                vol.Optional(
                    ATTR_LUX_REFRESH_BANK_COUNT, 
                    default=config_entry.get(ATTR_LUX_REFRESH_BANK_COUNT, PLACEHOLDER_LUX_REFRESH_BANK_COUNT)
                ): vol.All(int, vol.Range(min=MIN_BANK_COUNT, max=MAX_BANK_COUNT)),
            })

            data_schema = vol.Schema(schema)
            
        except Exception as e:
            _LOGGER.error(f"Error creating options schema: {e}")
            errors["base"] = "schema_error"
            data_schema = vol.Schema({})

        return self.async_show_form(
            step_id="user",
            data_schema=data_schema,
            errors=errors,
        )
    
    def _validate_user_input(self, user_input):
        """Validate options input with security checks."""
        errors = {}
        
        try:
            # Validate host
            host = user_input.get(ATTR_LUX_HOST, "")
            if not validate_ip_address(host):
                errors[ATTR_LUX_HOST] = "host_error"
            
            # Validate dongle serial
            dongle = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            if not validate_dongle_serial(dongle):
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
            
            # Validate refresh interval
            ri = user_input.get(ATTR_LUX_REFRESH_INTERVAL, PLACEHOLDER_LUX_REFRESH_INTERVAL)
            if not validate_refresh_interval(ri):
                errors[ATTR_LUX_REFRESH_INTERVAL] = "refresh_interval_error"
            
            # Validate bank count
            bc = user_input.get(ATTR_LUX_REFRESH_BANK_COUNT, PLACEHOLDER_LUX_REFRESH_BANK_COUNT)
            if not validate_bank_count(bc):
                errors[ATTR_LUX_REFRESH_BANK_COUNT] = "refresh_bank_count_error"
            
            # Additional security validations
            self._validate_security_constraints(user_input, errors)
            
        except Exception as e:
            _LOGGER.error(f"Error in options validation: {e}")
            errors["base"] = "validation_error"

        return errors
    
    def _validate_security_constraints(self, user_input, errors):
        """Additional security constraint validation for options."""
        try:
            # Same security checks as main config flow
            host = user_input.get(ATTR_LUX_HOST, "")
            
            # Block localhost variants that might bypass security
            dangerous_hosts = [
                "localhost", "127.0.0.1", "0.0.0.0", 
                "::1", "[::1]", "0:0:0:0:0:0:0:1"
            ]
            if host.lower() in dangerous_hosts and host != "127.0.0.1":
                errors[ATTR_LUX_HOST] = "host_error"
            
            # Check for potential injection patterns
            dongle = user_input.get(ATTR_LUX_DONGLE_SERIAL, "")
            suspicious_patterns = ["'", '"', "<", ">", "&", "%", "\\", "/"]
            if any(pattern in dongle for pattern in suspicious_patterns):
                errors[ATTR_LUX_DONGLE_SERIAL] = "dongle_error"
            
            # Validate boolean inputs
            bool_fields = [ATTR_LUX_USE_SERIAL, ATTR_LUX_RESPOND_TO_HEARTBEAT, ATTR_LUX_AUTO_REFRESH]
            for field in bool_fields:
                if field in user_input and not isinstance(user_input[field], bool):
                    errors[field] = "invalid_type"
            
            # Validate integer inputs
            int_fields = [ATTR_LUX_REFRESH_INTERVAL, ATTR_LUX_REFRESH_BANK_COUNT]
            for field in int_fields:
                if field in user_input:
                    try:
                        int(user_input[field])
                    except (ValueError, TypeError):
                        errors[field] = "invalid_type"
                        
        except Exception as e:
            _LOGGER.error(f"Error in options security validation: {e}")
            errors["base"] = "security_validation_error"