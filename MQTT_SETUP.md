# MQTT Setup Guide for LuxPower Integration

## Overview

This guide explains how to set up MQTT communication for the LuxPower integration using an ESPHome Modbus bridge. The MQTT transport allows you to connect to LuxPower inverters through a wireless bridge instead of direct TCP connection.

## Prerequisites

- Home Assistant with MQTT broker (Mosquitto add-on recommended)
- ESPHome Modbus bridge configured and running
- LuxPower inverter with RS485 connection
- Network connectivity between all components

## MQTT Broker Requirements

### Home Assistant Mosquitto Add-on

1. **Install Mosquitto Add-on**:
   - Go to Settings → Add-ons → Add-on Store
   - Search for "Mosquitto broker"
   - Install and start the add-on

2. **Configure Mosquitto**:
   ```yaml
   # mosquitto.conf
   listener 1883
   allow_anonymous false
   password_file /config/mqtt/passwd
   ```

3. **Create MQTT User**:
   ```bash
   # In Home Assistant terminal
   mosquitto_passwd -c /config/mqtt/passwd your_username
   ```

### External MQTT Broker

If using an external MQTT broker:

- **Broker**: Any MQTT broker (Mosquitto, EMQX, etc.)
- **Port**: 1883 (standard) or 8883 (TLS)
- **Authentication**: Username/password required
- **QoS**: Level 1 recommended for reliability

## ESPHome Bridge Setup

### 1. Hardware Configuration

Follow the ESPHome documentation to:
- Configure the Waveshare ESP32-S3-Relay-6CH board
- Connect RS485 to the LuxPower inverter
- Set up WiFi and MQTT credentials

### 2. MQTT Topic Configuration

The bridge publishes to these topics:

```
modbus_bridge/{dongle_serial}/data/bank0    # Input registers 0-39
modbus_bridge/{dongle_serial}/data/bank1    # Input registers 40-79
modbus_bridge/{dongle_serial}/data/bank2    # Input registers 80-119
modbus_bridge/{dongle_serial}/data/bank3    # Input registers 120-159
modbus_bridge/{dongle_serial}/data/bank4    # Input registers 160-199
modbus_bridge/{dongle_serial}/data/bank5    # Input registers 200-253
modbus_bridge/{dongle_serial}/data/bank6    # Input registers 254-380
modbus_bridge/{dongle_serial}/hold/bank0    # Holding registers 0-39
modbus_bridge/{dongle_serial}/hold/bank1    # Holding registers 40-79
modbus_bridge/{dongle_serial}/hold/bank2    # Holding registers 80-119
modbus_bridge/{dongle_serial}/hold/bank3    # Holding registers 120-159
modbus_bridge/{dongle_serial}/hold/bank4    # Holding registers 160-199
modbus_bridge/{dongle_serial}/hold/bank5    # Holding registers 200-239
modbus_bridge/{dongle_serial}/write         # Write commands (input)
modbus_bridge/{dongle_serial}/status        # Bridge status
```

**Note**: The integration also supports simplified topic formats without the dongle serial:
- `modbus_bridge/data/bank0` (instead of `modbus_bridge/{dongle_serial}/data/bank0`)
- `modbus_bridge/hold/bank0` (instead of `modbus_bridge/{dongle_serial}/hold/bank0`)
- `modbus_bridge/status` (instead of `modbus_bridge/{dongle_serial}/status`)

The integration automatically subscribes to both formats for maximum compatibility.

### 3. JSON Data Format

Each data message contains:

```json
{
  "timestamp": 1234567890,
  "register_start": 0,
  "register_count": 40,
  "values": [1234, 5678, 9012, ...],
  "raw_hex": "04d2162e..."
}
```

## LuxPower Integration Configuration

### 1. Add Integration

1. Go to **Settings** → **Devices & Services**
2. Click **Add Integration**
3. Search for **LuxPower Inverter**
4. Select **MQTT Bridge** as connection type

### 2. Configuration Fields

- **Connection Type**: MQTT Bridge
- **MQTT Topic Prefix**: `modbus_bridge` (default)
- **Dongle Serial**: Your inverter's dongle serial number
- **Serial Number**: Optional, for entity naming
- **Use Serial in Names**: Enable for unique entity names

### 3. Advanced Options

- **Auto Refresh**: Enable automatic data updates
- **Refresh Interval**: 30-120 seconds (default: 60)
- **Device Grouping**: Group entities by function
- **Rated Power**: Inverter power rating (0 = auto-detect)
- **Adaptive Polling**: Dynamic polling based on connection quality
- **Read Only Mode**: Disable control functions

## Testing the Setup

### 1. Verify MQTT Communication

1. **Check Bridge Status**:
   ```bash
   mosquitto_sub -h localhost -t "modbus_bridge/+/status"
   ```

2. **Monitor Data Flow**:
   ```bash
   mosquitto_sub -h localhost -t "modbus_bridge/+/data/+"
   ```

3. **Test Write Commands**:
   ```bash
   mosquitto_pub -h localhost -t "modbus_bridge/YOUR_DONGLE/write" -m '{"function":"write_single","address":1,"register":21,"value":1234}'
   ```

### 2. Verify Integration

1. **Check Entity Creation**:
   - Go to **Settings** → **Entities**
   - Filter by `luxpower` domain
   - Verify all expected entities are created

2. **Test Data Updates**:
   - Monitor sensor values in real-time
   - Verify data matches inverter display
   - Check for missing or stale data

3. **Test Control Functions**:
   - Toggle switches
   - Adjust number entities
   - Use button entities

## Troubleshooting

### Common Issues

#### No Entities Created
- **Cause**: MQTT topics not receiving data
- **Solution**: Check bridge configuration and RS485 connection

#### Stale Data
- **Cause**: Bridge not polling inverter
- **Solution**: Check bridge logs and Modbus communication

#### Control Not Working
- **Cause**: Write commands not reaching inverter
- **Solution**: Verify MQTT write topic and bridge processing

#### Connection Errors
- **Cause**: MQTT broker connectivity issues
- **Solution**: Check broker status and network connectivity

### Debugging Steps

1. **Check MQTT Broker**:
   ```bash
   # Test broker connectivity
   mosquitto_pub -h localhost -t "test" -m "hello"
   mosquitto_sub -h localhost -t "test"
   ```

2. **Monitor Bridge Logs**:
   - Use ESPHome logs to check Modbus communication
   - Look for RS485 errors or timeouts
   - Verify register reading success

3. **Check Integration Logs**:
   - Enable debug logging in Home Assistant
   - Look for MQTT subscription errors
   - Verify JSON parsing success

4. **Test Direct MQTT**:
   ```bash
   # Subscribe to all bridge topics
   mosquitto_sub -h localhost -t "modbus_bridge/#"
   ```

### Performance Optimization

#### Reduce Polling Frequency
- Increase bridge polling interval
- Use selective register reading
- Implement smart polling based on changes

#### Optimize MQTT Settings
- Use QoS 1 for reliability
- Enable retained messages for status
- Compress large payloads if needed

#### Network Optimization
- Use wired Ethernet for bridge if possible
- Ensure stable WiFi signal
- Monitor network latency

## Migration from TCP

### Switching Connection Types

1. **Backup Configuration**:
   - Note current entity IDs and names
   - Save automation and dashboard configurations

2. **Add MQTT Integration**:
   - Add new integration with MQTT transport
   - Use same dongle serial number
   - Verify entity creation

3. **Update References**:
   - Update automations to use new entity IDs
   - Modify dashboard cards
   - Test all control functions

4. **Remove TCP Integration**:
   - Only after confirming MQTT works correctly
   - Remove old integration
   - Clean up unused entities

### Maintaining Compatibility

- **Entity IDs**: Remain the same across connection types
- **Unique IDs**: Preserved for seamless migration
- **Entity Names**: Consistent naming scheme
- **Device Groups**: Maintained across transports

## Security Considerations

### MQTT Security
- Use strong authentication
- Enable TLS encryption if possible
- Restrict topic access with ACLs
- Regular password updates

### Network Security
- Isolate MQTT broker if possible
- Use VPN for remote access
- Monitor for unauthorized connections
- Regular security updates

### Bridge Security
- Secure WiFi credentials
- Use unique API keys
- Enable OTA password protection
- Regular firmware updates

## Advanced Configuration

### Custom Topic Structure

Modify bridge configuration for custom topics:

```yaml
mqtt:
  topic_prefix: "custom_prefix"
```

### Multiple Bridges

Support multiple inverters:

```yaml
# Bridge 1
mqtt:
  topic_prefix: "bridge1"

# Bridge 2  
mqtt:
  topic_prefix: "bridge2"
```

### Integration with Other Systems

The MQTT bridge can be used with:
- Other Home Assistant integrations
- External monitoring systems
- Custom applications
- Data logging systems

## Support and Resources

- **ESPHome Documentation**: [esphome.io](https://esphome.io)
- **MQTT Specification**: [mqtt.org](https://mqtt.org)
- **LuxPower Integration**: GitHub repository
- **Community Forums**: Home Assistant community
