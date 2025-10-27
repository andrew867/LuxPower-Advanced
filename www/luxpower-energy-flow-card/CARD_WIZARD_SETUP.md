# LuxPower Energy Flow Card - Card Wizard Setup

This guide explains how to add the LuxPower Energy Flow Card to the Home Assistant card wizard for easy auto-configuration.

## 🚀 Quick Setup

### Method 1: Automatic Setup (Recommended)

1. **Add the wizard configuration to your Lovelace resources:**
   ```yaml
   # In your configuration.yaml or Lovelace resources
   resources:
     - url: /hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card.js
       type: module
     - url: /hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card-editor.js
       type: module
     - url: /hacsfiles/luxpower-energy-flow-card/card-wizard-config.js
       type: module
     - url: /hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card-wizard.js
       type: module
   ```

2. **Restart Home Assistant**

3. **Add the card through the wizard:**
   - Go to your dashboard
   - Click "Add Card"
   - Search for "LuxPower Energy Flow Card"
   - Click "Add Card"
   - The card will auto-configure itself!

### Method 2: Manual Setup

1. **Add the card files to your resources:**
   ```yaml
   resources:
     - url: /hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card.js
       type: module
     - url: /hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card-editor.js
       type: module
   ```

2. **Add the card manually:**
   ```yaml
   type: custom:luxpower-energy-flow-card
   ```

## 🔧 Auto-Configuration Features

The card wizard will automatically:

### ✅ **Entity Detection**
- Automatically detect available LuxPower entities
- Configure the card with the correct entity names
- Handle different inverter models (6K, 12K, 7-10K)

### ✅ **Model-Specific Features**
- **6K Models**: 2 PV inputs, standard features
- **12K Models**: 3 PV inputs, smart load controls
- **7-10K Models**: Enhanced power ratings, advanced features

### ✅ **Power Flow Entities**
- Uses the fixed sign conventions we implemented
- Automatically detects `sensor.lux_grid_flow` for grid power
- Configures battery charge/discharge entities
- Sets up PV power entities

### ✅ **Display Options**
- Automatically shows/hides features based on model
- Configures EPS/backup power if available
- Sets up generator power if available

## 📋 Entity Mapping

The wizard automatically maps these entities:

| Function | Entity | Description |
|----------|--------|-------------|
| **Model Detection** | `sensor.lux_inverter_model` | Inverter model |
| **PV Power** | `sensor.lux_pv_power` | Total PV power |
| **Battery SOC** | `sensor.lux_battery_soc` | Battery state of charge |
| **Battery Voltage** | `sensor.lux_battery_voltage` | Battery voltage |
| **Grid Power** | `sensor.lux_grid_flow` | Grid power (import/export) |
| **Load Power** | `sensor.lux_load_power` | Load power |
| **Home Consumption** | `sensor.lux_home_consumption_live` | Home consumption |
| **System Status** | `sensor.lux_status_text` | System status |

## 🎯 Advanced Configuration

### Custom Entity Names
If you have custom entity names, you can override them:

```yaml
type: custom:luxpower-energy-flow-card
pv_power_entity: sensor.my_custom_pv_power
battery_soc_entity: sensor.my_custom_battery_soc
grid_import_entity: sensor.my_custom_grid_import
```

### Display Options
```yaml
type: custom:luxpower-energy-flow-card
show_pv3: true          # Show 3rd PV input (12K models)
show_eps: true          # Show EPS/backup section
show_generator: false   # Show generator section
show_12k_features: false # Show 12K-specific features
```

## 🔍 Troubleshooting

### Card Not Appearing in Wizard
1. Check that the wizard configuration file is in your resources
2. Restart Home Assistant
3. Clear browser cache
4. Check the browser console for errors

### Auto-Configuration Not Working
1. Ensure LuxPower integration is installed and working
2. Check that entities are available in Home Assistant
3. Verify entity names match the expected patterns
4. Check the browser console for configuration errors

### Missing Data
1. Verify that the LuxPower integration is receiving data
2. Check that entities are updating with real values
3. Review the entity names in the card configuration
4. Check the browser console for entity errors

## 📱 Usage

### Adding the Card
1. **Through Wizard**: Search for "LuxPower Energy Flow Card" in the card wizard
2. **Manual**: Add `type: custom:luxpower-energy-flow-card` to your dashboard

### Configuration
1. **Auto-Configuration**: The card will automatically detect and configure entities
2. **Manual Configuration**: Use the built-in configuration editor
3. **YAML Configuration**: Edit the card configuration directly

### Features
- **Animated Energy Flow**: SVG-based diagram with animated arrows
- **Real-time Data**: Live power values and system status
- **Responsive Design**: Works on desktop, tablet, and mobile
- **Interactive Elements**: Click components for detailed information

## 🎨 Customization

### Visual Customization
- The card automatically adapts to different screen sizes
- Supports light and dark themes
- Responsive layout for all devices

### Entity Customization
- Override any entity name
- Add custom entities
- Configure display options

### Model-Specific Features
- Automatically detects inverter model
- Shows/hides features based on capabilities
- Configures power flow calculations

## 📞 Support

If you encounter issues:

1. **Check the troubleshooting section above**
2. **Review the browser console for errors**
3. **Verify entity names and availability**
4. **Check the LuxPower integration status**
5. **Review the card documentation**

## 🔄 Updates

### Automatic Updates (HACS)
- HACS will notify you of updates
- Click "Update" to install new versions
- Restart Home Assistant after updates

### Manual Updates
1. Download the latest version
2. Replace existing files
3. Clear browser cache
4. Restart Home Assistant

---

**Note**: This card is designed to work with the LuxPower Home Assistant integration. Ensure you have the latest version installed and properly configured.
