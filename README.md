# LuxPower Home Assistant Integration

A comprehensive Home Assistant integration for LuxPower inverters, providing local monitoring and control without cloud dependency.

## 🚀 Features

### ✅ **Core Functionality**
- **Local Communication**: Direct Modbus TCP communication with inverters
- **Model Detection**: Automatic detection and optimization for different inverter models
- **Real-time Monitoring**: Live power, voltage, current, and energy data
- **Control Interface**: Switches, numbers, and time entities for configuration

### ✅ **Supported Inverter Models**
- **LXP 3-6K Hybrid** (AAAA, AAAB)
- **LXP-3600 ACS** (BAAA, BAAB) 
- **SNA 3000-6000** (CBAA)
- **LXP-LB-EU 7K** (EAAB)
- **SNA-US 6000** (CCAA)
- **LXP-LB-8-12K** (FAAB)
- **GEN-LB-EU 3-6K** (ACAB)
- **GEB-LB-EU 7-10K** (HAAA)
- **SNA 12K** (CFAA)
- **SNA 12K-US** (CEAA) ⭐
- **LXP Variants** (BEAA, DAAA)

### ✅ **Advanced Features**

#### **12K-Specific Features** (CFAA, CEAA, FAAB)
- **Smart Load Control**: Automatic load management based on SOC/voltage
- **AC Coupling**: Integration with AC-coupled PV systems
- **Generator Integration**: Advanced generator control and monitoring
- **Enhanced Peak Shaving**: Time-of-use optimization
- **System Configuration**: 12K-specific power and charging limits

#### **Universal Features** (All Models)
- **Power Flow Visualization**: Real-time energy routing between PV, battery, grid, and loads
- **Battery Management**: BMS integration, health monitoring, cell-level data
- **Grid Management**: Import/export limits, frequency response, anti-islanding
- **Parallel Systems**: Multi-inverter coordination and load balancing
- **Diagnostic Tools**: Fault codes, warning systems, health metrics
- **Firmware Management**: Remote updates, version tracking, validation

### ✅ **Entity Types**
- **Sensors**: 200+ monitoring sensors for all system parameters
- **Binary Sensors**: Status indicators for faults, warnings, and system states
- **Switches**: Control interfaces for system functions
- **Numbers**: Configurable parameters with validation
- **Times**: Schedule settings for charging and discharging
- **Buttons**: Action triggers for system operations

## 📋 Installation

### **Method 1: HACS (Recommended)**
1. Open HACS in Home Assistant
2. Go to Integrations → Custom Repositories
3. Add repository: `https://github.com/guybw/LuxPython_DEV`
4. Install "LuxPower Inverter" integration
5. Restart Home Assistant

### **Method 2: Manual Installation**
1. Download the latest release
2. Copy `custom_components/luxpower/` to your Home Assistant `custom_components/` directory
3. Restart Home Assistant
4. Add integration via Configuration → Integrations

## ⚙️ Configuration

### **Basic Setup**
1. Go to **Configuration** → **Integrations**
2. Click **Add Integration** → Search for **LuxPower Inverter**
3. Enter your inverter details:
   - **IP Address**: Your inverter's local IP address
   - **Port**: Default 8000 (Modbus TCP)
   - **Dongle Serial**: 10-character dongle serial number
   - **Serial Number**: Optional, for entity naming
   - **Use Serial in Names**: Enable for unique entity names

### **Advanced Options**
- **Auto Refresh**: Enable automatic data updates
- **Refresh Interval**: 30-120 seconds (default: 60)
- **Bank Count**: Number of register banks to read (1-6)
- **Heartbeat Response**: Respond to inverter heartbeat requests

## 🔧 Configuration Examples

### **Basic Configuration**
```yaml
# configuration.yaml
luxpower:
  host: "192.168.1.100"
  port: 8000
  dongle_serial: "1234567890"
  serial_number: "INV123456"
  use_serial: true
```

### **Advanced Configuration**
```yaml
# configuration.yaml
luxpower:
  host: "192.168.1.100"
  port: 8000
  dongle_serial: "1234567890"
  serial_number: "INV123456"
  use_serial: true
  auto_refresh: true
  refresh_interval: 30
  bank_count: 6
  respond_to_heartbeat: true
```

## 📊 Monitoring & Control

### **Key Sensors**
- **Power Flow**: PV → Battery, PV → Load, PV → Grid, Battery → Load, etc.
- **System Status**: Operating mode, fault codes, warning codes
- **Battery Data**: SOC, voltage, current, temperature, health
- **Grid Data**: Voltage, frequency, power import/export
- **Energy Statistics**: Daily generation, consumption, export, import

### **Control Entities**
- **Charging Control**: AC charge enable/disable, power rates, schedules
- **Discharge Control**: Force discharge, power limits, time slots
- **Grid Settings**: Import/export limits, frequency response
- **Battery Settings**: Charge/discharge limits, equalization
- **System Settings**: Operating modes, backup settings

## 🏠 Automation Examples

### **Battery Protection**
```yaml
# Prevent over-discharge
automation:
  - alias: "Battery Low SOC Protection"
    trigger:
      platform: numeric_state
      entity_id: sensor.lux_battery_soc
      below: 20
    action:
      - service: switch.turn_off
        entity_id: switch.lux_force_discharge_enable
```

### **Peak Shaving**
```yaml
# Reduce grid consumption during peak hours
automation:
  - alias: "Peak Shaving"
    trigger:
      platform: time
      at: "17:00:00"
    action:
      - service: switch.turn_on
        entity_id: switch.lux_grid_peak_shaving
```

### **Solar Optimization**
```yaml
# Maximize solar self-consumption
automation:
  - alias: "Solar Self-Consumption"
    trigger:
      platform: numeric_state
      entity_id: sensor.lux_pv_power
      above: 1000
    condition:
      - condition: numeric_state
        entity_id: sensor.lux_battery_soc
        below: 80
    action:
      - service: switch.turn_on
        entity_id: switch.lux_ac_charge_enable
```

## 🔍 Troubleshooting

### **Common Issues**

#### **Integration Not Found**
- Ensure `custom_components/luxpower/` is in the correct directory
- Check file permissions and restart Home Assistant
- Verify the integration is not already installed

#### **Connection Failed**
- Verify inverter IP address and port (default: 8000)
- Check network connectivity between Home Assistant and inverter
- Ensure Modbus TCP is enabled on the inverter
- Try increasing the refresh interval

#### **No Data/Entities**
- Check dongle serial number (exactly 10 characters)
- Verify inverter is online and responding
- Check Home Assistant logs for error messages
- Try restarting the integration

#### **Missing 12K Features**
- Ensure your inverter model is supported (CFAA, CEAA, FAAB)
- Check that model detection is working (look for model sensor)
- Verify Bank 4 is enabled in refresh settings

### **Debug Mode**
Enable debug logging:
```yaml
# configuration.yaml
logger:
  logs:
    custom_components.luxpower: debug
```

### **Log Analysis**
Check Home Assistant logs for:
- Connection errors
- Modbus communication issues
- Register reading failures
- Model detection problems

## 📈 Performance Optimization

### **Refresh Settings**
- **High Performance**: 30-second interval, 6 banks
- **Balanced**: 60-second interval, 4 banks (default)
- **Low Impact**: 120-second interval, 2 banks

### **Network Optimization**
- Use wired Ethernet connection
- Ensure stable network connection
- Consider dedicated VLAN for inverter communication

## 🔒 Security

### **Network Security**
- Use dedicated network segment for inverters
- Enable firewall rules to restrict access
- Consider VPN for remote access
- Regular firmware updates

### **Data Privacy**
- **No Cloud Dependency**: All communication is local
- **No Data Collection**: No telemetry or usage tracking
- **Local Storage**: All data stays on your Home Assistant instance

## 📚 Advanced Usage

### **Custom Sensors**
Create custom sensors for specific calculations:
```yaml
# configuration.yaml
template:
  - sensor:
      - name: "Solar Efficiency"
        unit_of_measurement: "%"
        state: >
          {% set pv_power = states('sensor.lux_pv_power') | float %}
          {% set pv_voltage = states('sensor.lux_pv_voltage') | float %}
          {% set pv_current = states('sensor.lux_pv_current') | float %}
          {% if pv_voltage > 0 and pv_current > 0 %}
            {{ (pv_power / (pv_voltage * pv_current) * 100) | round(1) }}
          {% else %}
            0
          {% endif %}
```

### **Custom Automations**
Advanced automation examples:
- **Weather-based charging**: Adjust charging based on weather forecasts
- **Time-of-use optimization**: Minimize grid costs during peak hours
- **Battery health management**: Optimize charging cycles for battery longevity
- **Grid stability support**: Provide grid services during outages

## 🤝 Support

### **Documentation**
- **GitHub Repository**: [LuxPython_DEV](https://github.com/guybw/LuxPython_DEV)
- **Issue Tracker**: [GitHub Issues](https://github.com/guybw/LuxPython_DEV/issues)
- **Discussions**: [GitHub Discussions](https://github.com/guybw/LuxPython_DEV/discussions)

### **Community**
- **Home Assistant Community**: Search for "LuxPower" in the forums
- **Discord**: Join the Home Assistant Discord server
- **Reddit**: r/homeassistant for general Home Assistant discussions

### **Contributing**
- Fork the repository
- Create a feature branch
- Submit a pull request
- Follow the coding standards

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Guy Wells**: Original developer and maintainer
- **Home Assistant Community**: Testing and feedback
- **LuxPowerTek**: For providing Modbus documentation
- **Contributors**: All those who have helped improve this integration

---

**⚠️ Disclaimer**: This integration is not officially affiliated with LuxPowerTek. Use at your own risk. Always backup your configuration before making changes.