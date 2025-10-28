# ESPHome Modbus RTU Bridge Documentation

## Overview

This ESPHome configuration provides a generic Modbus RTU to MQTT bridge using the Waveshare ESP32-S3-Relay-6CH board. It converts Modbus RTU binary data to JSON format and publishes it to MQTT topics, making it compatible with any Modbus RTU device, not just LuxPower inverters.

## Hardware Setup

### Required Components

- **Waveshare ESP32-S3-Relay-6CH** board
- **RS485 to TTL converter** (if not built into the board)
- **RS485 termination resistors** (120Ω)
- **Power supply** (5V/3A recommended)

### Pin Configuration

| Function | GPIO Pin | Description |
|----------|----------|-------------|
| RS485 TX | GPIO17 | Transmit data to RS485 |
| RS485 RX | GPIO16 | Receive data from RS485 |
| RS485 DE/RE | GPIO18 | Direction control (optional) |
| Relay 1 | GPIO1 | Relay channel 1 |
| Relay 2 | GPIO2 | Relay channel 2 |
| Relay 3 | GPIO3 | Relay channel 3 |
| Relay 4 | GPIO4 | Relay channel 4 |
| Relay 5 | GPIO5 | Relay channel 5 |
| Relay 6 | GPIO6 | Relay channel 6 |
| RGB LED | GPIO8 | Status LED (WS2812) |
| Buzzer | GPIO7 | Buzzer control |

### RS485 Wiring

```
ESP32-S3          RS485 Device
GPIO17 (TX)  -->  DI (Data In)
GPIO16 (RX)  <--  RO (Receive Out)
GPIO18 (DE)  -->  DE/RE (Direction Enable)
GND          -->  GND
3.3V         -->  VCC (if needed)
```

**Important Notes:**
- Use proper RS485 termination resistors (120Ω) at both ends
- Ensure correct polarity (A/B lines)
- Use shielded cable for long distances
- Check voltage levels (3.3V vs 5V)

## Configuration

### 1. Basic Setup

1. Copy the YAML configuration to your ESPHome device
2. Update the following secrets in your `secrets.yaml`:

```yaml
# WiFi Configuration
wifi_ssid: "your_wifi_ssid"
wifi_password: "your_wifi_password"

# MQTT Configuration
mqtt_broker: "your_mqtt_broker_ip"
mqtt_username: "your_mqtt_username"
mqtt_password: "your_mqtt_password"

# ESPHome API Key
api_key: "your_api_key"

# OTA Password
ota_password: "your_ota_password"
```

### 2. Device-Specific Configuration

#### Modbus Settings

The configuration uses ESPHome's built-in `modbus_controller` component with `text_sensor` entities and `raw_encode: HEXBYTES` for efficient data handling:

1. **Device Address**: Change `address: 0x01` to match your device
2. **Baud Rate**: Modify `baud_rate: 19200` if needed
3. **Register Ranges**: Pre-configured for LuxPower inverters (banks 0-6 for input, 0-5 for holding)

#### MQTT Topics

The bridge publishes to these topics:

```
modbus_bridge/{device_id}/data/bank0    # Input registers 0-39
modbus_bridge/{device_id}/data/bank1    # Input registers 40-79
modbus_bridge/{device_id}/data/bank2    # Input registers 80-119
modbus_bridge/{device_id}/data/bank3    # Input registers 120-159
modbus_bridge/{device_id}/data/bank4    # Input registers 160-199
modbus_bridge/{device_id}/data/bank5    # Input registers 200-253
modbus_bridge/{device_id}/data/bank6    # Input registers 254-380
modbus_bridge/{device_id}/hold/bank0    # Holding registers 0-39
modbus_bridge/{device_id}/hold/bank1    # Holding registers 40-79
modbus_bridge/{device_id}/hold/bank2    # Holding registers 80-119
modbus_bridge/{device_id}/hold/bank3    # Holding registers 120-159
modbus_bridge/{device_id}/hold/bank4    # Holding registers 160-199
modbus_bridge/{device_id}/hold/bank5    # Holding registers 200-239
modbus_bridge/{device_id}/write         # Write commands (input)
modbus_bridge/{device_id}/status        # Bridge status
```

#### JSON Data Format

Each data message contains:

```json
{
  "timestamp": 1234567890,
  "register_start": 0,
  "register_count": 40,
  "values": [1234, 5678, 9012, ...]
}
```

**Key Features:**
- **Efficient Parsing**: Uses `raw_encode: HEXBYTES` for optimal performance
- **Automatic Conversion**: Lambda functions convert hex to uint16 arrays
- **Sequential Reading**: 150ms delays between banks prevent Modbus collisions
- **Dual Register Types**: Both input and holding registers supported

### 3. Advanced Configuration

#### Polling Intervals

Modify the polling interval in the `interval` section:

```yaml
interval:
  - interval: 5s  # Change this value
    then:
      - script.execute: read_modbus_data
```

#### Register Customization

To read different register ranges, modify the `read_modbus_data` script:

```yaml
script:
  - id: read_modbus_data
    then:
      - modbus.register.read:
          id: modbus_rtu
          address: 0x01
          register: 0      # Start register
          count: 40       # Number of registers
          register_type: input
```

## Testing

### 1. Hardware Test

1. Power on the ESP32-S3 board
2. Check WiFi connection in ESPHome logs
3. Verify MQTT connection status
4. Test relay outputs manually

### 2. Modbus Communication Test

1. Connect RS485 device
2. Monitor ESPHome logs for Modbus errors
3. Check MQTT topics for data flow
4. Verify register values match device specifications

### 3. Integration Test

1. Configure LuxPower integration with MQTT transport
2. Set topic prefix to match bridge configuration
3. Verify entity creation and data updates
4. Test control functions (switches, numbers)

## Troubleshooting

### Common Issues

#### No Data Received
- Check RS485 wiring and polarity
- Verify device address matches configuration
- Ensure proper termination resistors
- Check baud rate settings

#### MQTT Connection Failed
- Verify broker IP and credentials
- Check network connectivity
- Ensure MQTT broker is running
- Check firewall settings

#### ESP32 Not Connecting
- Verify WiFi credentials
- Check signal strength
- Ensure correct board configuration
- Check power supply adequacy

#### Relay Not Working
- Verify GPIO pin assignments
- Check relay board power
- Test with manual GPIO control
- Verify relay board compatibility

### Debugging

Enable debug logging:

```yaml
logger:
  level: DEBUG
```

Monitor logs for:
- Modbus communication errors
- MQTT publish failures
- WiFi connection issues
- Memory usage warnings

### Performance Optimization

- Reduce polling frequency if needed
- Limit register count per read
- Use selective bank reading
- Monitor memory usage

## Customization

### Adding More Relays

To add additional relay channels:

```yaml
switch:
  - platform: gpio
    name: "Relay 7"
    pin: GPIO9
    id: relay7
```

### Custom LED Effects

Modify LED behavior:

```yaml
light:
  - platform: fastled_clockless
    chipset: WS2812B
    pin: GPIO8
    num_leds: 1
    name: "Bridge LED"
    effects:
      - strobe:
          name: "Strobe"
          colors: [red, blue, green]
```

### Additional Sensors

Add temperature or other sensors:

```yaml
sensor:
  - platform: dht
    pin: GPIO10
    temperature:
      name: "Temperature"
    humidity:
      name: "Humidity"
```

## Security Considerations

- Use strong WiFi passwords
- Enable MQTT authentication
- Use unique API keys
- Regular firmware updates
- Network isolation if possible

## Support

For issues and questions:
- Check ESPHome documentation
- Review Modbus RTU specifications
- Test with known working devices
- Verify hardware connections
