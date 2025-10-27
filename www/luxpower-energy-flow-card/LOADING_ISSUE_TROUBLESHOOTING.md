# Card Loading Issue - Troubleshooting Guide

## 🔍 "Card shows loading gif" - Debug Steps

If the LuxPower Energy Flow Card shows a loading gif but never loads, follow these steps:

### **Step 1: Browser Console Debugging**

1. **Open browser console** (F12)
2. **Go to Console tab**
3. **Look for these specific errors:**

#### **Common Error Messages:**
- `TypeError: Cannot read property 'states' of undefined`
- `ReferenceError: hass is not defined`
- `SyntaxError: Unexpected token`
- `Failed to load resource`

### **Step 2: Test with Debug Version**

1. **Add the debug resource:**
   - Go to Settings → Dashboards → Resources
   - Add: `/local/www/luxpower-energy-flow-card/luxpower-energy-flow-card-debug.js`
   - Type: `JavaScript Module`

2. **Test the debug card:**
   - Add card: `type: custom:luxpower-energy-flow-card-debug`
   - This will show detailed error information

### **Step 3: Check LuxPower Integration**

The card needs LuxPower entities to work. Check if you have:

1. **Go to Settings → Devices & Services → Entities**
2. **Search for "lux"**
3. **You should see entities like:**
   - `sensor.lux_pv_power`
   - `sensor.lux_battery_soc`
   - `sensor.lux_inverter_model`
   - `binary_sensor.luxpower_is_12k_model`

### **Step 4: Common Issues and Solutions**

#### **Issue A: No LuxPower Entities**
**Symptoms:** Card loads but shows "No entities found"
**Solution:** 
1. Install LuxPower integration first
2. Configure your inverter
3. Wait for entities to be created

#### **Issue B: JavaScript Error**
**Symptoms:** Console shows JavaScript errors
**Solution:**
1. Check browser console for specific error
2. Clear browser cache (Ctrl+F5)
3. Restart Home Assistant

#### **Issue C: Resource Loading Error**
**Symptoms:** Console shows "Failed to load resource"
**Solution:**
1. Check resource URLs are correct
2. Verify files exist in the directory
3. Check file permissions

#### **Issue D: Custom Element Not Defined**
**Symptoms:** Console shows "Custom element not defined"
**Solution:**
1. Make sure all 4 JavaScript files are loaded
2. Check the main card file is loaded first
3. Restart Home Assistant

### **Step 5: Manual Configuration Test**

If auto-configuration isn't working, try manual configuration:

```yaml
type: custom:luxpower-energy-flow-card
# Basic configuration
model_entity: sensor.lux_inverter_model
pv_power_entity: sensor.lux_pv_power
battery_soc_entity: sensor.lux_battery_soc
battery_voltage_entity: sensor.lux_battery_voltage
grid_import_entity: sensor.lux_grid_import_power
grid_export_entity: sensor.lux_grid_export_power
load_power_entity: sensor.lux_load_power
status_entity: sensor.lux_status_text
```

### **Step 6: Network Tab Debugging**

1. **Open browser console (F12)**
2. **Go to Network tab**
3. **Refresh the page**
4. **Look for the JavaScript files:**
   - All should show status `200`
   - If any show `404`, the file path is wrong
   - If any show `500`, there's a server error

### **Step 7: File Path Verification**

Make sure your resource URLs are correct:

**✅ Correct URLs:**
```
/local/www/luxpower-energy-flow-card/luxpower-energy-flow-card.js
/local/www/luxpower-energy-flow-card/luxpower-energy-flow-card-editor.js
/local/www/luxpower-energy-flow-card/card-wizard-config.js
/local/www/luxpower-energy-flow-card/luxpower-energy-flow-card-wizard.js
```

**❌ Wrong URLs:**
```
/local/luxpower-energy-flow-card/luxpower-energy-flow-card.js
/hacsfiles/luxpower-energy-flow-card/luxpower-energy-flow-card.js
```

### **Step 8: Quick Fixes**

#### **Fix A: Clear Everything and Start Over**
1. Delete all LuxPower resources
2. Restart Home Assistant
3. Add resources one by one
4. Test after each addition

#### **Fix B: Use Debug Version**
1. Add the debug resource
2. Use `type: custom:luxpower-energy-flow-card-debug`
3. Check what errors it shows

#### **Fix C: Check File Permissions**
1. Make sure Home Assistant can read the files
2. Check file permissions in the directory
3. Verify files are not corrupted

### **Step 9: Still Having Issues?**

If you're still stuck:

1. **Check Home Assistant logs** for JavaScript errors
2. **Try a different browser** to rule out browser issues
3. **Check if other custom cards work** (to rule out system issues)
4. **Use the debug version** to get detailed error information

### **Step 10: Success Indicators**

You'll know it's working when:
- ✅ Card shows energy flow diagram (not loading gif)
- ✅ Real-time data is displayed
- ✅ No errors in browser console
- ✅ Card editor works when you click edit
- ✅ All 4 resources show as "Loaded" in Lovelace resources
