# LuxPower Energy Flow Card - Storage Mode Setup

## 🎯 Quick Setup for Storage Mode Users

If you're using Home Assistant in **storage mode** (YAML mode disabled), follow these steps:

### **Step 1: Add Resources via UI**

1. **Go to Settings** → **Dashboards** → **Resources**
2. **Click the "+" button** to add each resource
3. **Add these 4 resources** (one by one):

#### Resource 1: Main Card
- **URL**: `/hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card.js`
- **Type**: `JavaScript Module`

#### Resource 2: Card Editor
- **URL**: `/hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card-editor.js`
- **Type**: `JavaScript Module`

#### Resource 3: Wizard Config
- **URL**: `/hacsfiles/luxpower-energy-flow-card/card-wizard-config.js`
- **Type**: `JavaScript Module`

#### Resource 4: Wizard Interface
- **URL**: `/hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card-wizard.js`
- **Type**: `JavaScript Module`

### **Step 2: Restart Home Assistant**
- Go to **Settings** → **System** → **Restart**
- Wait for Home Assistant to fully restart

### **Step 3: Add the Card**

#### **Method A: Through Card Wizard**
1. Go to your dashboard
2. Click **"Add Card"**
3. Search for **"LuxPower Energy Flow Card"**
4. Click **"Add Card"**
5. The card should auto-configure if it detects LuxPower entities

#### **Method B: Manual Configuration**
1. Go to your dashboard
2. Click **"Add Card"**
3. Scroll down to **"Custom Cards"**
4. Select **"LuxPower Energy Flow Card"**
5. Configure manually if auto-config doesn't work

### **Step 4: Manual Configuration (if auto-config fails)**

If the auto-configuration doesn't work, you can manually configure the card:

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
battery_charge_entity: sensor.lux_power_charging_battery
battery_discharge_entity: sensor.lux_power_discharging_battery

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

### **Troubleshooting**

#### **Card doesn't appear in custom cards list**
- Check that all 4 resources are loaded (green checkmark)
- Restart Home Assistant
- Clear browser cache (Ctrl+F5)

#### **Auto-configuration doesn't work**
- Make sure LuxPower integration is installed and working
- Check that you have LuxPower entities (sensor.lux_*)
- Try manual configuration instead

#### **Editor doesn't work**
- Make sure the editor resource is loaded
- Check browser console for JavaScript errors
- Verify all 4 resources are properly loaded

#### **Card shows but doesn't display data**
- Check entity names in the configuration
- Verify entities exist and have data
- Check browser console for errors

### **Verification Steps**

1. **Check Resources**: Go to Settings → Dashboards → Resources
   - All 4 resources should show as "Loaded" with green checkmarks

2. **Check Entities**: Go to Settings → Devices & Services → Entities
   - Look for LuxPower entities (sensor.lux_*)

3. **Test Card**: Add the card to a dashboard
   - Should display energy flow diagram
   - Should show real-time data

### **Need Help?**

If you're still having issues:
1. Check the browser console (F12) for JavaScript errors
2. Verify all file paths are correct
3. Make sure Home Assistant can access the files
4. Try the manual configuration method
