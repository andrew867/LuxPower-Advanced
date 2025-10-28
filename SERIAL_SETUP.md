# Serial/RS485 Setup Guide for LuxPower Integration

## Overview

This guide explains how to set up direct USB serial/RS485 communication for the LuxPower integration. This method connects directly to the inverter's RS485 port using a USB-to-RS485 adapter, bypassing the need for WiFi dongles or MQTT bridges.

## Prerequisites

- USB-to-RS485 adapter (FTDI, CH340, or similar)
- RS485 termination resistors (120Ω)
- Appropriate RS485 cable
- LuxPower inverter with RS485 port
- Computer running Home Assistant

## Hardware Requirements

### USB-to-RS485 Adapter

**Recommended Models**:
- **FTDI FT232RL**: High quality, reliable
- **CH340G**: Cost-effective, widely available
- **CP2102**: Good performance, USB-C options
- **PL2303**: Budget option, basic functionality

**Specifications**:
- **Voltage**: 3.3V or 5V compatible
- **Baud Rate**: Up to 115200 bps
- **Connector**: Screw terminals or DB9
- **Driver**: Automatic or manual installation

### RS485 Cable

**Cable Requirements**:
- **Type**: Shielded twisted pair
- **Gauge**: 22-24 AWG
- **Length**: Up to 1200m (4000ft)
- **Termination**: 120Ω resistors at both ends

**Wiring**:
```
USB-RS485 Adapter    LuxPower Inverter
A+ (Data+)      -->   A+ (Data+)
B- (Data-)      -->   B- (Data-)
GND             -->   GND
```

## Software Setup

### Linux (Home Assistant OS, Ubuntu, etc.)

#### 1. Install Serial Drivers

Most adapters are automatically recognized:

```bash
# Check if device is recognized
lsusb

# Check for serial devices
ls /dev/ttyUSB* /dev/ttyACM*
```

#### 2. Set Permissions

Add user to dialout group:

```bash
# Add current user to dialout group
sudo usermod -a -G dialout $USER

# For Home Assistant OS, add to dialout group
sudo usermod -a -G dialout homeassistant

# Reboot or logout/login to apply changes
sudo reboot
```

#### 3. Verify Device Access

```bash
# Check device permissions
ls -l /dev/ttyUSB0

# Test device access
sudo chmod 666 /dev/ttyUSB0
```

#### 4. Test Serial Communication

```bash
# Install minicom for testing
sudo apt install minicom

# Configure minicom
sudo minicom -s

# Set serial device: /dev/ttyUSB0
# Set baud rate: 19200
# Set data bits: 8
# Set stop bits: 1
# Set parity: None
```

### Windows

#### 1. Install Drivers

Download and install drivers for your adapter:

- **FTDI**: [FTDI Drivers](https://ftdichip.com/drivers/vcp-drivers/)
- **CH340**: [CH340 Drivers](https://www.wch.cn/downloads/CH341SER_ZIP.html)
- **CP2102**: [Silicon Labs Drivers](https://www.silabs.com/developers/usb-to-uart-bridge-vcp-drivers)

#### 1. Find COM Port

1. Open **Device Manager**
2. Look under **Ports (COM & LPT)**
3. Note the COM port number (e.g., COM3)

#### 2. Test with PuTTY

1. Download and install PuTTY
2. Select **Serial** connection type
3. Set **Serial line**: COM3 (your port)
4. Set **Speed**: 19200
5. Configure data bits: 8, stop bits: 1, parity: None

### macOS

#### 1. Install Drivers

Most adapters work with built-in drivers, but some may need:

- **FTDI**: Built-in support
- **CH340**: [CH340 Drivers](https://github.com/adrianmihalko/ch340g-ch34g-ch34x-mac-os-x-driver)
- **CP2102**: [Silicon Labs Drivers](https://www.silabs.com/developers/usb-to-uart-bridge-vcp-drivers)

#### 2. Find Device

```bash
# List serial devices
ls /dev/cu.usbserial* /dev/cu.usbmodem*

# Check device info
system_profiler SPUSBDataType
```

## LuxPower Integration Configuration

### 1. Add Integration

1. Go to **Settings** → **Devices & Services**
2. Click **Add Integration**
3. Search for **LuxPower Inverter**
4. Select **USB Serial/RS485** as connection type

### 2. Configuration Fields

- **Connection Type**: USB Serial/RS485
- **Serial Port**: `/dev/ttyUSB0` (Linux) or `COM3` (Windows)
- **Baud Rate**: 19200 (default)
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

### 1. Hardware Verification

1. **Check Connections**:
   - Verify RS485 wiring (A+, B-, GND)
   - Ensure proper termination resistors
   - Check adapter power LED

2. **Test Continuity**:
   - Use multimeter to check cable continuity
   - Verify no shorts between conductors
   - Check ground connection

### 2. Serial Communication Test

#### Linux
```bash
# Monitor serial data
cat /dev/ttyUSB0 | hexdump -C

# Send test data
echo -e "\x01\x04\x00\x00\x00\x28\xC5\xF1" > /dev/ttyUSB0
```

#### Windows
Use PuTTY or similar terminal program to monitor data.

#### macOS
```bash
# Monitor serial data
cat /dev/cu.usbserial-* | hexdump -C
```

### 3. Integration Test

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

#### Device Not Found
- **Cause**: Driver not installed or device not recognized
- **Solution**: Install correct drivers, check USB connection

#### Permission Denied
- **Cause**: User not in dialout group (Linux)
- **Solution**: Add user to dialout group and reboot

#### No Data Received
- **Cause**: Incorrect wiring or baud rate
- **Solution**: Check RS485 connections and settings

#### Communication Errors
- **Cause**: Termination resistors missing or wrong polarity
- **Solution**: Add 120Ω resistors and verify A+/B- wiring

### Debugging Steps

1. **Check Device Recognition**:
   ```bash
   # Linux
   lsusb
   dmesg | grep tty
   
   # Windows
   Device Manager → Ports
   ```

2. **Test Serial Communication**:
   ```bash
   # Linux - check if data is received
   timeout 10 cat /dev/ttyUSB0 | hexdump -C
   
   # Send Modbus test command
   echo -e "\x01\x04\x00\x00\x00\x28\xC5\xF1" > /dev/ttyUSB0
   ```

3. **Check Integration Logs**:
   - Enable debug logging in Home Assistant
   - Look for serial communication errors
   - Verify Modbus protocol parsing

4. **Hardware Verification**:
   - Use oscilloscope to check RS485 signals
   - Verify voltage levels (3.3V or 5V)
   - Check for signal noise or interference

### Performance Optimization

#### Serial Settings
- Use appropriate baud rate (19200 for LuxPower)
- Enable hardware flow control if available
- Use interrupt-driven I/O for better performance

#### Cable Optimization
- Use shortest cable length possible
- Ensure proper shielding and grounding
- Avoid running near power cables

#### Software Optimization
- Reduce polling frequency if needed
- Use efficient Modbus command sequences
- Implement error recovery mechanisms

## Advanced Configuration

### Multiple Serial Devices

Support multiple inverters:

```yaml
# First inverter
luxpower:
  connection_type: serial
  serial_port: /dev/ttyUSB0
  dongle_serial: "INV001"

# Second inverter  
luxpower:
  connection_type: serial
  serial_port: /dev/ttyUSB1
  dongle_serial: "INV002"
```

### Custom Serial Settings

Modify serial parameters if needed:

```yaml
luxpower:
  connection_type: serial
  serial_port: /dev/ttyUSB0
  serial_baudrate: 38400  # Higher baud rate
  dongle_serial: "INV001"
```

### Integration with Other Systems

The serial connection can be used with:
- Custom Modbus applications
- Data logging systems
- SCADA systems
- Other automation platforms

## Security Considerations

### Physical Security
- Secure USB adapter connections
- Protect RS485 cable from tampering
- Use locked enclosures if possible

### Network Security
- Serial connection is inherently local
- No network exposure unlike TCP/MQTT
- Consider encryption for sensitive data

### Access Control
- Limit physical access to serial devices
- Use proper user permissions
- Monitor for unauthorized access

## Migration from Other Methods

### From TCP Connection

1. **Backup Configuration**:
   - Note current entity IDs and names
   - Save automation and dashboard configurations

2. **Add Serial Integration**:
   - Add new integration with serial transport
   - Use same dongle serial number
   - Verify entity creation

3. **Update References**:
   - Update automations to use new entity IDs
   - Modify dashboard cards
   - Test all control functions

4. **Remove TCP Integration**:
   - Only after confirming serial works correctly
   - Remove old integration
   - Clean up unused entities

### From MQTT Bridge

Similar migration process as TCP, but:
- Remove ESPHome bridge configuration
- Disconnect RS485 from bridge
- Connect RS485 directly to USB adapter

## Support and Resources

- **Modbus Specification**: [modbus.org](https://modbus.org)
- **RS485 Standard**: EIA-485 specification
- **USB-to-Serial Adapters**: Manufacturer documentation
- **LuxPower Integration**: GitHub repository
- **Community Forums**: Home Assistant community

## Hardware Recommendations

### Budget Setup
- **Adapter**: CH340G USB-to-RS485
- **Cable**: CAT5e with RS485 converter
- **Termination**: 120Ω resistors

### Professional Setup
- **Adapter**: FTDI FT232RL USB-to-RS485
- **Cable**: Shielded RS485 cable
- **Termination**: Professional RS485 terminators
- **Enclosure**: Weatherproof junction box
