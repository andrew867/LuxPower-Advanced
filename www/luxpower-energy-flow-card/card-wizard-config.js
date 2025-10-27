// LuxPower Energy Flow Card Wizard Configuration
// This file provides auto-configuration for the Home Assistant card wizard

// Card wizard configuration
const cardWizardConfig = {
  type: 'custom:luxpower-energy-flow-card',
  name: 'LuxPower Energy Flow Card',
  description: 'Interactive energy flow visualization for LuxPower inverters',
  preview: true,
  documentationURL: 'https://github.com/your-repo/luxpower-energy-flow-card',
  version: '1.0.0',
  
  // Auto-configuration function
  getConfig: (hass) => {
    // Check if LuxPower integration is available
    const luxpowerEntities = Object.keys(hass.states).filter(entityId => 
      entityId.startsWith('sensor.lux_') || 
      entityId.startsWith('binary_sensor.luxpower_')
    );
    
    if (luxpowerEntities.length === 0) {
      return {
        type: 'custom:luxpower-energy-flow-card',
        error: 'No LuxPower entities found. Please ensure the LuxPower integration is installed and configured.'
      };
    }
    
    // Auto-detect available entities
    const config = {
      type: 'custom:luxpower-energy-flow-card'
    };
    
    // Add entities if they exist
    if (hass.states['sensor.lux_inverter_model']) {
      config.model_entity = 'sensor.lux_inverter_model';
    }
    
    if (hass.states['sensor.lux_pv_power']) {
      config.pv_power_entity = 'sensor.lux_pv_power';
    }
    
    if (hass.states['sensor.lux_battery_soc']) {
      config.battery_soc_entity = 'sensor.lux_battery_soc';
    }
    
    if (hass.states['sensor.lux_battery_voltage']) {
      config.battery_voltage_entity = 'sensor.lux_battery_voltage';
    }
    
    if (hass.states['sensor.lux_grid_flow']) {
      config.grid_import_entity = 'sensor.lux_grid_flow';
      config.grid_export_entity = 'sensor.lux_grid_flow';
    } else if (hass.states['sensor.lux_grid_import_power'] && hass.states['sensor.lux_grid_export_power']) {
      config.grid_import_entity = 'sensor.lux_grid_import_power';
      config.grid_export_entity = 'sensor.lux_grid_export_power';
    }
    
    if (hass.states['sensor.lux_load_power']) {
      config.load_power_entity = 'sensor.lux_load_power';
    }
    
    if (hass.states['sensor.lux_home_consumption_live']) {
      config.consumption_entity = 'sensor.lux_home_consumption_live';
    }
    
    if (hass.states['sensor.lux_status_text']) {
      config.status_entity = 'sensor.lux_status_text';
    }
    
    // Check for 12K model features
    if (hass.states['binary_sensor.luxpower_is_12k_model']) {
      config.is_12k_entity = 'binary_sensor.luxpower_is_12k_model';
    }
    
    if (hass.states['binary_sensor.luxpower_has_pv3']) {
      config.has_pv3_entity = 'binary_sensor.luxpower_has_pv3';
    }
    
    if (hass.states['binary_sensor.luxpower_has_eps']) {
      config.has_eps_entity = 'binary_sensor.luxpower_has_eps';
    }
    
    if (hass.states['binary_sensor.luxpower_has_generator']) {
      config.has_generator_entity = 'binary_sensor.luxpower_has_generator';
    }
    
    // Add individual PV arrays if available
    if (hass.states['sensor.lux_current_solar_output_1']) {
      config.pv1_power_entity = 'sensor.lux_current_solar_output_1';
    }
    
    if (hass.states['sensor.lux_current_solar_output_2']) {
      config.pv2_power_entity = 'sensor.lux_current_solar_output_2';
    }
    
    if (hass.states['sensor.lux_current_solar_output_3']) {
      config.pv3_power_entity = 'sensor.lux_current_solar_output_3';
    }
    
    // Add battery charge/discharge entities if available
    if (hass.states['sensor.lux_power_charging_battery']) {
      config.battery_charge_entity = 'sensor.lux_power_charging_battery';
    }
    
    if (hass.states['sensor.lux_power_discharging_battery']) {
      config.battery_discharge_entity = 'sensor.lux_power_discharging_battery';
    }
    
    // Add EPS power if available
    if (hass.states['sensor.lux_power_to_eps']) {
      config.eps_power_entity = 'sensor.lux_power_to_eps';
    }
    
    return config;
  },
  
  // Validation function
  validateConfig: (config) => {
    const errors = [];
    
    if (!config.type || config.type !== 'custom:luxpower-energy-flow-card') {
      errors.push('Invalid card type');
    }
    
    return errors;
  }
};

// Register with Home Assistant card wizard
if (window.customCards) {
  window.customCards.push(cardWizardConfig);
} else {
  window.customCards = [cardWizardConfig];
}

// Export for manual use
window.LuxPowerEnergyFlowCardWizard = cardWizardConfig;
