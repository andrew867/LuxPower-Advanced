# LuxPower Dashboard Collection

A comprehensive Home Assistant dashboard collection that replicates the LuxPower cloud interface (na.luxpowertek.com) with enhanced features and model-specific capabilities.

## 🚀 Features

### ✨ **Core Features**
- **Energy Flow Visualization** - Animated SVG energy flow diagram with directional arrows
- **Real-time Monitoring** - Live power, energy, and system status data
- **Model Detection** - Automatic detection of inverter models with appropriate features
- **Responsive Design** - Works on desktop, tablet, and mobile devices
- **Custom Cards** - HACS-installable custom Lovelace cards

### 🎯 **Model Support**
- **6K Models**: AAAA, AAAB, CBAA, CCAA, BAAA, BAAB
- **12K Models**: CFAA, CEAA, FAAB (with enhanced features)
- **7-10K Models**: HAAA, EAAB, ACAB
- **Auto-detection** of model capabilities and features

### 📊 **Dashboard Types**
1. **Main Dashboard** - Complete overview with summary cards
2. **Energy Flow** - Interactive energy flow visualization
3. **Graphs** - Power and energy charts with historical data
4. **Controls** - Inverter settings and configuration
5. **12K Features** - Advanced features for 12K models

## 📁 File Structure

```
dashboards/
├── README.md                           # This file
├── luxpower_main_dashboard.yaml        # Main dashboard
├── luxpower_energy_flow.yaml           # Energy flow visualization
├── luxpower_graphs.yaml                # Power and energy graphs
├── luxpower_controls.yaml              # Control panel
└── luxpower_12k_features.yaml          # 12K-specific features

packages/
└── luxpower_helpers.yaml               # Helper template sensors

www/
├── luxpower-dashboard/
│   └── images/                         # SVG assets
│       ├── solar-panel.svg
│       ├── battery-*.svg
│       ├── inverter.svg
│       ├── grid.svg
│       ├── home.svg
│       ├── eps-backup.svg
│       ├── generator.svg
│       └── arrow-*.svg
└── luxpower-energy-flow-card/          # Custom card
    ├── luxpower-energy-flow-card.js
    ├── luxpower-energy-flow-card-editor.js
    ├── hacs.json
    └── README.md
```

## 🛠️ Installation

### Prerequisites
- Home Assistant with LuxPower integration installed
- HACS (Home Assistant Community Store)
- ApexCharts Card (for graphs)
- Button Card (for custom buttons)

### Step 1: Install Dependencies
```bash
# Install via HACS
- ApexCharts Card
- Button Card
- Mini Graph Card (optional)
```

### Step 2: Copy Files
1. Copy the `dashboards/` folder to your Home Assistant config
2. Copy the `packages/` folder to your Home Assistant config
3. Copy the `www/` folder to your Home Assistant config

### Step 3: Enable Packages
Add to your `configuration.yaml`:
```yaml
homeassistant:
  packages: !include_dir_named packages/
```

### Step 4: Install Custom Card
1. Copy `www/luxpower-energy-flow-card/` to your `www/` directory
2. Add to HACS or install manually
3. Restart Home Assistant

### Step 5: Add Dashboards
1. Go to Configuration → Dashboards
2. Add new dashboard
3. Import the YAML files from the `dashboards/` folder

## 🎨 Customization

### Custom Card Configuration
The LuxPower Energy Flow Card supports extensive configuration:

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

### Helper Template Sensors
The `packages/luxpower_helpers.yaml` file provides additional template sensors:

- **Energy Flow Calculations**: PV to load, PV to battery, PV to grid, etc.
- **Model Detection**: Binary sensors for 12K models, PV3, EPS, generator
- **Enhanced Status**: System status, model names, power ratings
- **Energy Statistics**: Daily and total energy calculations

## 📱 Responsive Design

### Desktop (>1200px)
- Full layout with all sections
- Side-by-side cards
- Complete energy flow diagram

### Tablet (768-1200px)
- Stacked layout
- Smaller cards
- Simplified flow diagram

### Mobile (<768px)
- Single column layout
- Compact cards
- Touch-friendly controls

## 🔧 Troubleshooting

### Common Issues

1. **Custom Card Not Loading**
   - Ensure the card files are in the correct location
   - Check browser console for JavaScript errors
   - Verify HACS installation

2. **Missing Entities**
   - Check that the LuxPower integration is working
   - Verify entity names match your configuration
   - Check helper template sensors are loaded

3. **Graphs Not Displaying**
   - Install ApexCharts Card via HACS
   - Check entity names and data availability
   - Verify time range settings

4. **Model Detection Not Working**
   - Check `sensor.lux_inverter_model` entity
   - Verify helper template sensors are loaded
   - Check binary sensor states

### Debug Mode
Enable debug logging in your `configuration.yaml`:
```yaml
logger:
  default: warning
  logs:
    custom_components.luxpower: debug
    custom_components.luxpower_energy_flow_card: debug
```

## 🎯 Model-Specific Features

### 6K Models (Standard)
- 2 PV inputs (PV1, PV2)
- Basic energy monitoring
- Standard controls
- Grid import/export

### 12K Models (Enhanced)
- 3 PV inputs (PV1, PV2, PV3)
- Smart Load control
- AC Coupling support
- Advanced peak shaving
- Higher power ratings
- Generator integration

### 7-10K Models
- 3 PV inputs capability
- Enhanced power ratings
- Advanced monitoring

## 📊 Dashboard Overview

### Main Dashboard
- System status and model information
- Energy summary cards (Solar, Battery, Grid, Consumption)
- Energy flow visualization
- Power flow summary
- Quick actions and controls

### Energy Flow Dashboard
- Interactive energy flow diagram
- Real-time power values
- Animated arrows showing energy direction
- Component tooltips with detailed information

### Graphs Dashboard
- Power flow line charts
- Energy overview bar charts
- Battery SOC and voltage
- Grid status monitoring
- Monthly energy overview
- System status timeline

### Controls Dashboard
- Quick control switches
- Battery settings
- Grid settings
- Time settings
- System configuration
- Advanced settings
- Service calls and actions

### 12K Features Dashboard
- Smart Load control
- AC Coupling features
- Advanced power management
- Generator integration
- Enhanced monitoring
- 12K-specific controls

## 🔄 Updates and Maintenance

### Regular Updates
- Check for LuxPower integration updates
- Update custom cards via HACS
- Monitor for new entity additions
- Review dashboard performance

### Backup
- Backup your dashboard configurations
- Save custom modifications
- Document any customizations

## 🤝 Contributing

### Adding New Features
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

### Reporting Issues
1. Check existing issues
2. Provide detailed information
3. Include logs and screenshots
4. Specify your model and setup

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- LuxPower for the original cloud interface design
- Home Assistant community for inspiration
- Contributors and testers
- HACS for the custom card distribution

## 📞 Support

For support and questions:
- Check the troubleshooting section
- Review the documentation
- Open an issue on GitHub
- Join the Home Assistant community

---

**Note**: This dashboard collection is designed to work with the LuxPower Home Assistant integration. Ensure you have the latest version installed and properly configured.
