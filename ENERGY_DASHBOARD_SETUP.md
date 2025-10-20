# LuxPower Energy Dashboard Setup Guide

This guide helps you automatically configure your LuxPower integration for optimal Home Assistant energy dashboard functionality.

## 🚀 Quick Start

### Prerequisites
1. **Home Assistant running** with LuxPower integration installed
2. **Long-lived access token** from Home Assistant
3. **Python 3** installed on your system

### One-Command Setup

#### Linux/macOS:
```bash
export HASS_TOKEN='your_long_lived_access_token'
./setup_luxpower_energy.sh
```

#### Windows:
```cmd
set HASS_TOKEN=your_long_lived_access_token
setup_luxpower_energy.bat
```

## 📋 Manual Setup Steps

### Step 1: Get Home Assistant Token
1. Go to Home Assistant → Profile (bottom left)
2. Scroll down to "Long-lived access tokens"
3. Click "Create token"
4. Give it a name like "LuxPower Energy Setup"
5. Copy the token

### Step 2: Set Environment Variable

#### Linux/macOS:
```bash
export HASS_TOKEN='your_token_here'
export HASS_URL='http://your-homeassistant-url:8123'  # Optional, defaults to homeassistant.local:8123
```

#### Windows:
```cmd
set HASS_TOKEN=your_token_here
set HASS_URL=http://your-homeassistant-url:8123
```

### Step 3: Run Setup Scripts

#### Option A: Automatic Setup (Recommended)
```bash
# Linux/macOS
./setup_luxpower_energy.sh

# Windows
setup_luxpower_energy.bat
```

#### Option B: Manual Steps
```bash
# 1. Recreate entities with model-based enablement
python luxpower_entity_manager.py --recreate-entities

# 2. Configure energy dashboard
python luxpower_entity_manager.py --configure-energy

# 3. Basic energy dashboard setup
python setup_energy_dashboard.py
```

## 🔧 Advanced Configuration

### Entity Manager Options

The `luxpower_entity_manager.py` script provides several options:

```bash
python luxpower_entity_manager.py [OPTIONS]

Options:
  --recreate-entities    Recreate entities with model-based enablement
  --configure-energy     Configure energy dashboard
  --model MODEL_CODE     Specify model code (e.g., 12K, S6)
  --url URL             Home Assistant URL (default: http://homeassistant.local:8123)
```

### Examples:

```bash
# Recreate entities for a specific model
python luxpower_entity_manager.py --recreate-entities --model 12K

# Configure energy dashboard only
python luxpower_entity_manager.py --configure-energy

# Full setup with custom URL
python luxpower_entity_manager.py --recreate-entities --configure-energy --url http://192.168.1.100:8123
```

## 📊 Energy Dashboard Entities

The setup automatically configures these essential entities:

### Solar Production
- **Daily Solar Output**: `sensor.lux_daily_solar`
- **Total Solar Output**: `sensor.lux_total_solar`
- **Solar Array 1-3**: Individual array outputs

### Battery Management
- **Battery Charge (Daily)**: `sensor.lux_daily_battery_charge`
- **Battery Discharge (Daily)**: `sensor.lux_daily_battery_discharge`
- **Battery SOC**: `sensor.lux_battery_percent`

### Grid Interaction
- **Grid Consumption**: `sensor.lux_power_from_grid_daily`
- **Grid Feed-in**: `sensor.lux_power_to_grid_daily`

### Home Consumption
- **Home Consumption**: `sensor.lux_power_from_inverter_daily`

## 🎯 Model-Based Entity Enablement

The system automatically enables/disables entities based on your inverter model:

### 12K Models
- ✅ **Enabled**: All standard entities + 12K-specific features
- ✅ **12K Features**: Peak shaving, AC coupling, generator integration
- ✅ **Advanced Controls**: Force charge/discharge, grid peak shaving

### Standard Models
- ✅ **Enabled**: Core power and energy entities
- ❌ **Disabled**: 12K-specific features (not applicable)

### Essential Entities (Always Enabled)
These entities are always enabled regardless of model:
- Battery charge/discharge power and energy
- Solar production (daily and total)
- Grid consumption and feed-in
- Home consumption
- System status and diagnostics

## 🔍 Verification

After setup, verify your configuration:

1. **Check Energy Dashboard**: Go to `http://your-ha-url/energy`
2. **Verify Entities**: All energy entities should show data
3. **Check Model Detection**: Look for model-specific entities enabled/disabled correctly
4. **Test Data Flow**: Ensure solar, battery, and grid data is flowing

## 🛠️ Troubleshooting

### Common Issues

#### "No entities found"
- Ensure LuxPower integration is installed and configured
- Check that entities are created in Home Assistant
- Verify HASS_TOKEN has proper permissions

#### "Failed to configure energy dashboard"
- Check Home Assistant logs for errors
- Ensure energy dashboard is available in your HA version
- Verify entity IDs are correct

#### "Model detection failed"
- Manually specify model with `--model` parameter
- Check firmware version sensor for model code
- Review entity names for model indicators

### Debug Mode

Enable debug logging:
```bash
export PYTHONPATH=.
python -c "
import logging
logging.basicConfig(level=logging.DEBUG)
# Run your script here
"
```

## 📈 Energy Dashboard Features

Once configured, your energy dashboard will show:

### Solar Production
- Daily solar generation
- Total solar production
- Individual array performance

### Battery Management
- Battery charge/discharge cycles
- State of charge (SOC)
- Battery energy flows

### Grid Interaction
- Grid consumption (when solar/battery insufficient)
- Grid feed-in (when excess solar)
- Net grid usage

### Home Consumption
- Total home energy consumption
- Solar self-consumption
- Battery utilization

## 🔄 Maintenance

### Regular Updates
- Run setup script after LuxPower integration updates
- Recreate entities if new model-specific features are added
- Update energy dashboard configuration as needed

### Monitoring
- Check energy dashboard for data accuracy
- Monitor entity states in Home Assistant
- Review logs for any configuration issues

## 📞 Support

If you encounter issues:

1. **Check Home Assistant logs** for error messages
2. **Verify entity states** in Home Assistant
3. **Test with manual configuration** if automatic setup fails
4. **Review model detection** and entity enablement logic

## 🎉 Success!

Once configured, you'll have:
- ✅ **Automatic entity enablement** based on your inverter model
- ✅ **Complete energy dashboard** with solar, battery, and grid data
- ✅ **Optimized entity configuration** for your specific LuxPower model
- ✅ **Real-time energy monitoring** and historical data

Your LuxPower integration is now fully optimized for Home Assistant's energy dashboard! 🚀
