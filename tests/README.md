# LuxPower Integration Tests

This directory contains tests for the LuxPower Home Assistant integration.

## Test Structure

- `conftest.py` - Common test fixtures and setup
- `test_config_flow.py` - Configuration flow tests
- `test_init.py` - Integration initialization tests
- `test_sensor.py` - Sensor platform tests
- `test_switch.py` - Switch platform tests
- `test_number.py` - Number platform tests

## Running Tests

To run the tests, use pytest:

```bash
pytest tests/
```

## Test Coverage

The tests cover:
- Configuration flow validation
- Integration setup and teardown
- Entity creation and updates
- Service call handling
- Error conditions

## Fixtures

- `mock_hass` - Home Assistant instance
- `mock_config_entry` - Configuration entry
- `mock_luxpower_client` - Mock LuxPower client
- `mock_events` - Mock event system
- `mock_integration_setup` - Complete integration setup
