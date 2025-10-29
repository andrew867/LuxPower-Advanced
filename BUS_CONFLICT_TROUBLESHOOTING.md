# Modbus Bus Conflict Troubleshooting Guide

## Problem Description
When connecting the ESP32 Modbus bridge alongside the WiFi dongle, you may experience:
- CRC check failures
- Device crashes (dongle/inverter)
- Zero readings on registers
- Bus instability

## Root Causes

### 1. **Timing Conflicts (Primary Issue)**
- **ESP32 Bridge**: Polls every 30s (updated from 10s)
- **WiFi Dongle**: Polls every 15s
- **Conflict**: Both devices try to communicate simultaneously

### 2. **Bus Master Conflicts**
- Modbus RTU is a **single-master protocol**
- Two masters on the same bus cause collisions
- Results in packet corruption and device instability

### 3. **Polarity Issues**
- RS485 A+ and B- connections may be reversed
- Causes signal inversion and communication failures

## Solutions

### **Solution 1: Timing Adjustments (Implemented)**
The ESPHome configuration has been updated with conservative timing:

```yaml
modbus:
  send_wait_time: 2000ms  # Increased from 500ms

modbus_controller:
  update_interval: 30000ms  # Increased from 10000ms
  command_throttle: 1000ms  # Increased from 250ms
```

### **Solution 2: Bus Arbitration (Implemented)**
Added random delays and bus activity checking:

```yaml
script:
  - id: check_bus_activity
    then:
      - lambda: |-
          // Add random delay to avoid synchronized polling
          uint32_t random_delay = (esphome::millis() % 5000) + 1000;
          delay(random_delay);
```

### **Solution 3: Physical Wiring Check**

#### **RS485 Connection Verification**
1. **Check A+ and B- connections**:
   - A+ should connect to A+ on inverter
   - B- should connect to B- on inverter
   - **Try swapping A+ and B-** if you get CRC errors

2. **Grounding**:
   - Ensure proper ground connection
   - Use shielded cable if possible

3. **Termination**:
   - Add 120Ω termination resistor at the end of the bus
   - Only terminate at the physical end of the bus

#### **Waveshare ESP32-S3-Relay-6CH Wiring**
```
ESP32 GPIO17 (TX) → RS485 DI (Data In)
ESP32 GPIO18 (RX) ← RS485 RO (Receive Out)
ESP32 GND → RS485 GND
```

### **Solution 4: Alternative Connection Methods**

#### **Option A: Use Only ESP32 Bridge**
1. Disconnect WiFi dongle from RS485 bus
2. Use ESP32 bridge exclusively
3. Configure Home Assistant for MQTT connection

#### **Option B: Use Only WiFi Dongle**
1. Disconnect ESP32 from RS485 bus
2. Use WiFi dongle exclusively
3. Configure Home Assistant for TCP connection

#### **Option C: Separate RS485 Ports**
1. Use different RS485 ports on inverter
2. Connect each device to separate ports
3. Configure different Modbus addresses if needed

### **Solution 5: Advanced Bus Management**

#### **Enable Bus Arbitration Mode**
Add this to your ESPHome configuration for even more conservative polling:

```yaml
modbus_controller:
  - id: lux
    modbus_id: mbus
    address: 0x01
    update_interval: 60000ms  # 60 seconds - very conservative
    command_throttle: 2000ms  # 2 seconds between commands
    offline_skip_updates: 0
```

#### **Monitor Bus Activity**
The ESP32 will now log bus activity and conflicts:

```
🔍 Checking bus activity before polling...
⏱️  Random delay: 3247 ms
✅ Bank0: Good data [0..5]: 1234, 5678, 9012, 3456, 7890, 1234
```

## Testing Steps

### **Step 1: Test ESP32 Bridge Alone**
1. Disconnect WiFi dongle from RS485
2. Connect only ESP32 bridge
3. Monitor logs for successful communication
4. Verify MQTT data is being published

### **Step 2: Test WiFi Dongle Alone**
1. Disconnect ESP32 from RS485
2. Connect only WiFi dongle
3. Monitor Home Assistant logs
4. Verify TCP connection works

### **Step 3: Test Both Together (Conservative Mode)**
1. Connect both devices
2. Use updated ESPHome configuration (30s polling)
3. Monitor for conflicts in logs
4. Check for CRC errors

### **Step 4: Physical Troubleshooting**
1. **Swap A+ and B- connections**
2. **Check grounding**
3. **Add termination resistor**
4. **Use shielded cable**

## Log Monitoring

### **ESP32 Logs (Look for these patterns)**
```
✅ Bank0: Good data [0..5]: 1234, 5678, 9012, 3456, 7890, 1234
⚠️  Bank0: Empty response received - possible bus conflict
⚠️  Bank0: Incomplete response (40 bytes, expected 80) - possible bus conflict
```

### **Home Assistant Logs (Look for these patterns)**
```
ERROR: Modbus CRC Check failed!
ERROR: Duplicate modbus command found
ERROR: Packet too small
ERROR: Cannot send packet: not connected
```

## Performance Impact

### **Conservative Timing Impact**
- **ESP32 Polling**: 30s interval (was 10s)
- **Command Throttle**: 1000ms (was 250ms)
- **Send Wait**: 2000ms (was 500ms)
- **Trade-off**: Slower data updates but stable communication

### **Expected Results**
- Reduced bus conflicts
- More stable communication
- Fewer CRC errors
- Better device stability

## Emergency Recovery

If devices crash or become unresponsive:

1. **Power cycle the inverter**
2. **Disconnect ESP32 from RS485**
3. **Wait 30 seconds**
4. **Reconnect ESP32**
5. **Monitor logs for stability**

## Next Steps

1. **Deploy updated ESPHome configuration**
2. **Test with conservative timing**
3. **Monitor logs for 24 hours**
4. **Adjust timing if needed**
5. **Consider physical wiring changes if issues persist**

## Support

If issues persist after implementing these solutions:
1. Check physical RS485 connections
2. Verify inverter RS485 port configuration
3. Consider using separate RS485 ports
4. Contact support with detailed logs
