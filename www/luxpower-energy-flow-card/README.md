# LuxPower Energy Flow Card

A custom Lovelace card that provides an interactive energy flow visualization for LuxPower inverters, replicating the cloud interface experience in Home Assistant.

## ✨ Features

- **Animated Energy Flow** - SVG-based energy flow diagram with animated arrows
- **Real-time Data** - Live power values and system status
- **Model Detection** - Automatic detection of inverter models and capabilities
- **Responsive Design** - Works on desktop, tablet, and mobile
- **Interactive Elements** - Click components for detailed information
- **Customizable** - Extensive configuration options

## 🚀 Installation

### Via HACS (Recommended)
1. Open HACS
2. Go to Frontend
3. Click "Explore & Download Repositories"
4. Search for "LuxPower Energy Flow Card"
5. Click "Download this repository with HACS"
6. Restart Home Assistant

### Manual Installation
1. Download the card files
2. Copy to `www/luxpower-energy-flow-card/` in your Home Assistant config
3. Add to your Lovelace resources:
   ```yaml
   resources:
     - url: /local/luxpower-energy-flow-card/luxpower-energy-flow-card.js
       type: module
   ```
4. Restart Home Assistant

## 📖 Usage

### Basic Configuration
```yaml
type: custom:luxpower-energy-flow-card
```

### Advanced Configuration
```yaml
type: custom:luxpower-energy-flow-card
# Model Detection
model_entity: sensor.lux_inverter_model
is_12k_entity: binary_sensor.luxpower_is_12k_model
has_pv3_entity: binary_sensor.luxpower_has_pv3
has_eps_entity: binary_sensor.luxpower_has_eps
has_generator_entity: binary_sensor.luxpower_has_generator

# Power Sensors
pv_power_entity: sensor.lux_pv_power
pv1_power_entity: sensor.lux_current_solar_output_1
pv2_power_entity: sensor.lux_current_solar_output_2
pv3_power_entity: sensor.lux_current_solar_output_3
battery_soc_entity: sensor.lux_battery_soc
battery_voltage_entity: sensor.lux_battery_voltage
battery_charge_entity: sensor.lux_battery_charge
battery_discharge_entity: sensor.lux_battery_discharge

# Grid Sensors
grid_import_entity: sensor.lux_grid_import_power
grid_export_entity: sensor.lux_grid_export_power
grid_voltage_entity: sensor.lux_grid_voltage_live
grid_frequency_entity: sensor.lux_grid_frequency_live

# Load & Consumption
load_power_entity: sensor.lux_load_power
consumption_entity: sensor.luxpower_total_consumption
eps_power_entity: sensor.lux_power_to_eps

# Status
status_entity: sensor.lux_status_text
system_status_entity: sensor.luxpower_system_status
```

## 🎨 Customization

### Display Options
```yaml
type: custom:luxpower-energy-flow-card
show_pv3: true          # Show 3rd PV input (12K models)
show_eps: true          # Show EPS/backup section
show_generator: false   # Show generator section
show_12k_features: false # Show 12K-specific features
```

### Entity Configuration
All entities are optional and will use defaults if not specified:

- **Model Detection**: Uses `sensor.lux_inverter_model` by default
- **Power Sensors**: Uses standard LuxPower entity names
- **Status**: Uses `sensor.lux_status_text` by default

## 🔧 Configuration Editor

The card includes a built-in configuration editor accessible through the Lovelace UI:

1. Add the card to your dashboard
2. Click the three dots menu
3. Select "Configure UI"
4. Use the visual editor to configure entities and options

## 📱 Responsive Design

### Desktop (>1200px)
- Full energy flow diagram
- All components visible
- Detailed tooltips

### Tablet (768-1200px)
- Compact layout
- Essential components
- Touch-friendly interface

### Mobile (<768px)
- Single column layout
- Simplified diagram
- Optimized for touch

## 🎯 Model Support

### 6K Models (AAAA, AAAB, CBAA, CCAA, BAAA, BAAB)
- 2 PV inputs (PV1, PV2)
- Standard features
- Basic energy flow

### 12K Models (CFAA, CEAA, FAAB)
- 3 PV inputs (PV1, PV2, PV3)
- Smart Load controls
- AC Coupling indicators
- Enhanced monitoring

### 7-10K Models (HAAA, EAAB, ACAB)
- 3 PV inputs capability
- Enhanced power ratings
- Advanced features

## 🔄 Energy Flow Logic

The card automatically calculates and displays energy flows:

- **PV → Load**: Direct solar power to loads
- **PV → Battery**: Solar charging battery
- **PV → Grid**: Solar export to grid
- **Battery → Load**: Battery powering loads
- **Grid → Load**: Grid powering loads

## 🎨 Visual Elements

### Components
- **Solar Panels**: Animated solar panel icons
- **Battery**: Dynamic battery icon based on SOC
- **Inverter**: Inverter status and power
- **Grid**: Grid connection and power flow
- **Home**: Load consumption
- **EPS**: Emergency power supply (if available)
- **Generator**: Generator status (if available)

### Arrows
- **Animated**: Pulsing arrows showing energy direction
- **Color-coded**: Different colors for different energy sources
- **Power-based**: Arrow speed proportional to power level

## 🔧 Troubleshooting

### Common Issues

1. **Card Not Loading**
   - Check file paths and permissions
   - Verify JavaScript console for errors
   - Ensure proper HACS installation

2. **Missing Data**
   - Verify entity names and availability
   - Check LuxPower integration status
   - Review helper template sensors

3. **Model Detection Issues**
   - Check `sensor.lux_inverter_model` entity
   - Verify binary sensor states
   - Review helper template configuration

4. **Performance Issues**
   - Reduce update frequency
   - Check for entity polling issues
   - Review browser performance

### Debug Mode
Enable debug logging:
```yaml
logger:
  default: warning
  logs:
    custom_components.luxpower_energy_flow_card: debug
```

## 📊 Data Requirements

### Required Entities
- `sensor.lux_inverter_model` - Inverter model detection
- `sensor.lux_pv_power` - Total PV power
- `sensor.lux_battery_soc` - Battery state of charge
- `sensor.lux_battery_voltage` - Battery voltage
- `sensor.lux_grid_import_power` - Grid import power
- `sensor.lux_grid_export_power` - Grid export power
- `sensor.lux_load_power` - Load power

### Optional Entities
- `sensor.lux_current_solar_output_1` - PV1 power
- `sensor.lux_current_solar_output_2` - PV2 power
- `sensor.lux_current_solar_output_3` - PV3 power (12K models)
- `sensor.lux_battery_charge` - Battery charge power
- `sensor.lux_battery_discharge` - Battery discharge power
- `sensor.lux_power_to_eps` - EPS power
- `sensor.lux_status_text` - System status

## 🔄 Updates

### Automatic Updates (HACS)
- HACS will notify you of updates
- Click "Update" to install new versions
- Restart Home Assistant after updates

### Manual Updates
1. Download latest version
2. Replace existing files
3. Clear browser cache
4. Restart Home Assistant

## 🤝 Contributing

### Development
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

### Testing
- Test on different screen sizes
- Verify with different inverter models
- Check performance and responsiveness
- Validate entity configurations

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- LuxPower for the original cloud interface
- Home Assistant community
- Contributors and testers
- HACS for distribution

## 📞 Support

For support and questions:
- Check the troubleshooting section
- Review the documentation
- Open an issue on GitHub
- Join the Home Assistant community

---

**Note**: This card is designed to work with the LuxPower Home Assistant integration. Ensure you have the latest version installed and properly configured.
