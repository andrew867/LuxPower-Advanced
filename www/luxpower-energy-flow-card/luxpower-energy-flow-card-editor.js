class LuxPowerEnergyFlowCardEditor extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
  }

  setConfig(config) {
    this._config = config || {};
  }

  connectedCallback() {
    this._render();
  }

  _render() {
    this.shadowRoot.innerHTML = `
      <style>
        .card-config {
          padding: 20px;
          font-family: 'Roboto', sans-serif;
        }
        
        .config-section {
          margin-bottom: 20px;
          padding: 15px;
          border: 1px solid #e0e0e0;
          border-radius: 8px;
          background: #f9f9f9;
        }
        
        .config-section h3 {
          margin: 0 0 15px 0;
          color: #2c3e50;
          font-size: 16px;
        }
        
        .config-row {
          display: flex;
          align-items: center;
          margin-bottom: 10px;
        }
        
        .config-row label {
          width: 200px;
          font-weight: 500;
          color: #34495e;
        }
        
        .config-row ha-entity-picker {
          flex: 1;
        }
        
        .config-row ha-switch {
          margin-left: auto;
        }
        
        .config-row input[type="text"] {
          flex: 1;
          padding: 8px;
          border: 1px solid #ddd;
          border-radius: 4px;
          font-size: 14px;
        }
        
        .help-text {
          font-size: 12px;
          color: #7f8c8d;
          margin-top: 5px;
          font-style: italic;
        }
        
        .section-divider {
          height: 1px;
          background: #e0e0e0;
          margin: 20px 0;
        }
      </style>
      
      <div class="card-config">
        <h2>LuxPower Energy Flow Card Configuration</h2>
        
        <div class="config-section">
          <h3>Model Detection</h3>
          <div class="config-row">
            <label>Model Entity:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.model_entity || 'sensor.lux_inverter_model'}
              .configValue=${'model_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          <div class="help-text">Entity that provides the inverter model (e.g., CFAA, CEAA, etc.)</div>
          
          <div class="config-row">
            <label>12K Model Entity:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.is_12k_entity || 'binary_sensor.luxpower_is_12k_model'}
              .configValue=${'is_12k_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          <div class="help-text">Binary sensor indicating if this is a 12K model</div>
          
          <div class="config-row">
            <label>Has PV3 Entity:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.has_pv3_entity || 'binary_sensor.luxpower_has_pv3'}
              .configValue=${'has_pv3_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          <div class="help-text">Binary sensor indicating if 3rd PV input is available</div>
          
          <div class="config-row">
            <label>Has EPS Entity:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.has_eps_entity || 'binary_sensor.luxpower_has_eps'}
              .configValue=${'has_eps_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          <div class="help-text">Binary sensor indicating if EPS/backup is available</div>
          
          <div class="config-row">
            <label>Has Generator Entity:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.has_generator_entity || 'binary_sensor.luxpower_has_generator'}
              .configValue=${'has_generator_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          <div class="help-text">Binary sensor indicating if generator is available</div>
        </div>
        
        <div class="section-divider"></div>
        
        <div class="config-section">
          <h3>Power Sensors</h3>
          <div class="config-row">
            <label>PV Total Power:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.pv_power_entity || 'sensor.lux_pv_power'}
              .configValue=${'pv_power_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>PV1 Power:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.pv1_power_entity || 'sensor.lux_current_solar_output_1'}
              .configValue=${'pv1_power_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>PV2 Power:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.pv2_power_entity || 'sensor.lux_current_solar_output_2'}
              .configValue=${'pv2_power_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>PV3 Power:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.pv3_power_entity || 'sensor.lux_current_solar_output_3'}
              .configValue=${'pv3_power_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Battery SOC:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.battery_soc_entity || 'sensor.lux_battery_soc'}
              .configValue=${'battery_soc_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Battery Voltage:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.battery_voltage_entity || 'sensor.lux_battery_voltage'}
              .configValue=${'battery_voltage_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Battery Charge:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.battery_charge_entity || 'sensor.lux_battery_charge'}
              .configValue=${'battery_charge_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Battery Discharge:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.battery_discharge_entity || 'sensor.lux_battery_discharge'}
              .configValue=${'battery_discharge_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
        </div>
        
        <div class="section-divider"></div>
        
        <div class="config-section">
          <h3>Grid Sensors</h3>
          <div class="config-row">
            <label>Grid Import:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.grid_import_entity || 'sensor.lux_grid_import_power'}
              .configValue=${'grid_import_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Grid Export:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.grid_export_entity || 'sensor.lux_grid_export_power'}
              .configValue=${'grid_export_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Grid Voltage:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.grid_voltage_entity || 'sensor.lux_grid_voltage_live'}
              .configValue=${'grid_voltage_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Grid Frequency:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.grid_frequency_entity || 'sensor.lux_grid_frequency_live'}
              .configValue=${'grid_frequency_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
        </div>
        
        <div class="section-divider"></div>
        
        <div class="config-section">
          <h3>Load & Consumption</h3>
          <div class="config-row">
            <label>Load Power:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.load_power_entity || 'sensor.lux_load_power'}
              .configValue=${'load_power_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>Total Consumption:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.consumption_entity || 'sensor.luxpower_total_consumption'}
              .configValue=${'consumption_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>EPS Power:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.eps_power_entity || 'sensor.lux_power_to_eps'}
              .configValue=${'eps_power_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
        </div>
        
        <div class="section-divider"></div>
        
        <div class="config-section">
          <h3>Status Sensors</h3>
          <div class="config-row">
            <label>Status Text:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.status_entity || 'sensor.lux_status_text'}
              .configValue=${'status_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
          
          <div class="config-row">
            <label>System Status:</label>
            <ha-entity-picker
              .hass=${this.hass}
              .value=${this._config.system_status_entity || 'sensor.luxpower_system_status'}
              .configValue=${'system_status_entity'}
              @change=${this._valueChanged}
            ></ha-entity-picker>
          </div>
        </div>
        
        <div class="section-divider"></div>
        
        <div class="config-section">
          <h3>Display Options</h3>
          <div class="config-row">
            <label>Show PV3:</label>
            <ha-switch
              .checked=${this._config.show_pv3 !== false}
              .configValue=${'show_pv3'}
              @change=${this._valueChanged}
            ></ha-switch>
          </div>
          
          <div class="config-row">
            <label>Show EPS:</label>
            <ha-switch
              .checked=${this._config.show_eps !== false}
              .configValue=${'show_eps'}
              @change=${this._valueChanged}
            ></ha-switch>
          </div>
          
          <div class="config-row">
            <label>Show Generator:</label>
            <ha-switch
              .checked=${this._config.show_generator || false}
              .configValue=${'show_generator'}
              @change=${this._valueChanged}
            ></ha-switch>
          </div>
          
          <div class="config-row">
            <label>Show 12K Features:</label>
            <ha-switch
              .checked=${this._config.show_12k_features || false}
              .configValue=${'show_12k_features'}
              @change=${this._valueChanged}
            ></ha-switch>
          </div>
        </div>
      </div>
    `;
  }

  _valueChanged(ev) {
    if (!this._config) {
      this._config = {};
    }
    
    const target = ev.target;
    const configValue = target.configValue;
    
    if (target.checked !== undefined) {
      this._config[configValue] = target.checked;
    } else if (target.value !== undefined) {
      this._config[configValue] = target.value;
    }
    
    const event = new CustomEvent('config-changed', {
      detail: { config: this._config },
      bubbles: true,
      composed: true
    });
    
    this.dispatchEvent(event);
  }
}

customElements.define('luxpower-energy-flow-card-editor', LuxPowerEnergyFlowCardEditor);
