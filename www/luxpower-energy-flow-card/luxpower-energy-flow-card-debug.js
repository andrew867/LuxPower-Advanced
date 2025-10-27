// Debug version of LuxPower Energy Flow Card
// This version includes error handling and console logging

class LuxPowerEnergyFlowCardDebug extends HTMLElement {
  constructor() {
    super();
    console.log('LuxPowerEnergyFlowCardDebug: Constructor called');
    this.attachShadow({ mode: 'open' });
    this._config = {};
    this._hass = {};
    this._entities = {};
  }

  static getConfigElement() {
    console.log('LuxPowerEnergyFlowCardDebug: getConfigElement called');
    return document.createElement('luxpower-energy-flow-card-editor');
  }

  static getStubConfig() {
    console.log('LuxPowerEnergyFlowCardDebug: getStubConfig called');
    return {
      type: 'custom:luxpower-energy-flow-card'
    };
  }

  setConfig(config) {
    console.log('LuxPowerEnergyFlowCardDebug: setConfig called with:', config);
    try {
      this._config = {
        show_pv3: true,
        show_eps: true,
        show_generator: false,
        show_12k_features: false,
        ...config
      };
      console.log('LuxPowerEnergyFlowCardDebug: Config set successfully');
    } catch (error) {
      console.error('LuxPowerEnergyFlowCardDebug: Error in setConfig:', error);
    }
  }

  set hass(hass) {
    console.log('LuxPowerEnergyFlowCardDebug: hass setter called');
    try {
      this._hass = hass;
      this._updateEntities();
      this._render();
      console.log('LuxPowerEnergyFlowCardDebug: hass setter completed');
    } catch (error) {
      console.error('LuxPowerEnergyFlowCardDebug: Error in hass setter:', error);
    }
  }

  _updateEntities() {
    console.log('LuxPowerEnergyFlowCardDebug: _updateEntities called');
    try {
      const hass = this._hass;
      if (!hass || !hass.states) {
        console.warn('LuxPowerEnergyFlowCardDebug: No hass or states available');
        return;
      }

      this._entities = {
        // Model detection
        model: hass.states[this._config.model_entity || 'sensor.lux_inverter_model'],
        is_12k: hass.states[this._config.is_12k_entity || 'binary_sensor.luxpower_is_12k_model'],
        has_pv3: hass.states[this._config.has_pv3_entity || 'binary_sensor.luxpower_has_pv3'],
        has_eps: hass.states[this._config.has_eps_entity || 'binary_sensor.luxpower_has_eps'],
        has_generator: hass.states[this._config.has_generator_entity || 'binary_sensor.luxpower_has_generator'],
        
        // Power values
        pv_power: hass.states[this._config.pv_power_entity || 'sensor.lux_pv_power'],
        pv1_power: hass.states[this._config.pv1_power_entity || 'sensor.lux_current_solar_output_1'],
        pv2_power: hass.states[this._config.pv2_power_entity || 'sensor.lux_current_solar_output_2'],
        pv3_power: hass.states[this._config.pv3_power_entity || 'sensor.lux_current_solar_output_3'],
        
        battery_soc: hass.states[this._config.battery_soc_entity || 'sensor.lux_battery_soc'],
        battery_voltage: hass.states[this._config.battery_voltage_entity || 'sensor.lux_battery_voltage'],
        battery_charge: hass.states[this._config.battery_charge_entity || 'sensor.lux_battery_charge'],
        battery_discharge: hass.states[this._config.battery_discharge_entity || 'sensor.lux_battery_discharge'],
        
        grid_import: hass.states[this._config.grid_import_entity || 'sensor.lux_grid_import_power'],
        grid_export: hass.states[this._config.grid_export_entity || 'sensor.lux_grid_export_power'],
        grid_voltage: hass.states[this._config.grid_voltage_entity || 'sensor.lux_grid_voltage_live'],
        grid_frequency: hass.states[this._config.grid_frequency_entity || 'sensor.lux_grid_frequency_live'],
        
        load_power: hass.states[this._config.load_power_entity || 'sensor.lux_load_power'],
        consumption: hass.states[this._config.consumption_entity || 'sensor.luxpower_total_consumption'],
        
        eps_power: hass.states[this._config.eps_power_entity || 'sensor.lux_power_to_eps'],
        
        // Status
        status: hass.states[this._config.status_entity || 'sensor.lux_status_text'],
        system_status: hass.states[this._config.system_status_entity || 'sensor.luxpower_system_status']
      };
      
      console.log('LuxPowerEnergyFlowCardDebug: Entities updated:', this._entities);
    } catch (error) {
      console.error('LuxPowerEnergyFlowCardDebug: Error in _updateEntities:', error);
    }
  }

  _render() {
    console.log('LuxPowerEnergyFlowCardDebug: _render called');
    try {
      const entities = this._entities;
      const config = this._config;
      
      // Simple render for debugging
      this.shadowRoot.innerHTML = `
        <style>
          :host {
            display: block;
            font-family: 'Roboto', sans-serif;
            padding: 20px;
            background: #f0f0f0;
            border-radius: 8px;
            margin: 10px 0;
          }
          .debug-info {
            background: white;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 10px;
          }
          .error {
            color: red;
            font-weight: bold;
          }
          .success {
            color: green;
            font-weight: bold;
          }
        </style>
        
        <div class="debug-info">
          <h3>LuxPower Energy Flow Card - Debug Mode</h3>
          <p><strong>Status:</strong> <span class="success">Card loaded successfully!</span></p>
          <p><strong>Config:</strong> ${JSON.stringify(config, null, 2)}</p>
          <p><strong>Entities Found:</strong> ${Object.keys(entities).length}</p>
          <p><strong>Available Entities:</strong></p>
          <ul>
            ${Object.entries(entities).map(([key, entity]) => 
              `<li>${key}: ${entity ? entity.state : 'unavailable'}</li>`
            ).join('')}
          </ul>
        </div>
        
        <div class="debug-info">
          <h4>Next Steps:</h4>
          <p>1. If you see this message, the card is working!</p>
          <p>2. Check if you have LuxPower entities in your system</p>
          <p>3. If entities are missing, install the LuxPower integration first</p>
          <p>4. Once entities are available, the card will show the energy flow diagram</p>
        </div>
      `;
      
      console.log('LuxPowerEnergyFlowCardDebug: Render completed');
    } catch (error) {
      console.error('LuxPowerEnergyFlowCardDebug: Error in _render:', error);
      this.shadowRoot.innerHTML = `
        <div style="padding: 20px; background: #ffebee; color: #c62828; border-radius: 8px;">
          <h3>Error in LuxPower Energy Flow Card</h3>
          <p><strong>Error:</strong> ${error.message}</p>
          <p><strong>Stack:</strong> ${error.stack}</p>
        </div>
      `;
    }
  }

  getCardSize() {
    return 3;
  }
}

// Register the debug version
customElements.define('luxpower-energy-flow-card-debug', LuxPowerEnergyFlowCardDebug);

console.log('LuxPowerEnergyFlowCardDebug: Custom element registered');
