class LuxPowerEnergyFlowCard extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
    this._config = {};
    this._hass = {};
    this._entities = {};
  }

  setConfig(config) {
    this._config = {
      show_pv3: true,
      show_eps: true,
      show_generator: false,
      show_12k_features: false,
      ...config
    };
  }

  set hass(hass) {
    this._hass = hass;
    this._updateEntities();
    this._render();
  }

  _updateEntities() {
    const hass = this._hass;
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
  }

  _render() {
    const entities = this._entities;
    const config = this._config;
    
    // Get power values
    const pvPower = this._getNumericValue(entities.pv_power);
    const pv1Power = this._getNumericValue(entities.pv1_power);
    const pv2Power = this._getNumericValue(entities.pv2_power);
    const pv3Power = this._getNumericValue(entities.pv3_power);
    const batterySOC = this._getNumericValue(entities.battery_soc);
    const batteryVoltage = this._getNumericValue(entities.battery_voltage);
    const batteryCharge = this._getNumericValue(entities.battery_charge);
    const batteryDischarge = this._getNumericValue(entities.battery_discharge);
    const gridImport = this._getNumericValue(entities.grid_import);
    const gridExport = this._getNumericValue(entities.grid_export);
    const gridVoltage = this._getNumericValue(entities.grid_voltage);
    const gridFrequency = this._getNumericValue(entities.grid_frequency);
    const loadPower = this._getNumericValue(entities.load_power);
    const consumption = this._getNumericValue(entities.consumption);
    const epsPower = this._getNumericValue(entities.eps_power);
    
    // Model detection
    const is12K = entities.is_12k?.state === 'on';
    const hasPV3 = entities.has_pv3?.state === 'on';
    const hasEPS = entities.has_eps?.state === 'on';
    const hasGenerator = entities.has_generator?.state === 'on';
    
    // Calculate energy flows
    const pvToLoad = Math.max(0, pvPower - batteryCharge - gridExport);
    const pvToBattery = batteryCharge;
    const pvToGrid = gridExport;
    const batteryToLoad = batteryDischarge;
    const gridToLoad = gridImport;
    
    // Determine battery icon
    const batteryIcon = this._getBatteryIcon(batterySOC);
    
    // Determine system status
    const systemStatus = entities.system_status?.state || 'Unknown';
    const statusColor = this._getStatusColor(systemStatus);
    
    this.shadowRoot.innerHTML = `
      <style>
        :host {
          display: block;
          font-family: 'Roboto', sans-serif;
        }
        
        .energy-flow-container {
          background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
          border-radius: 12px;
          padding: 20px;
          box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
          position: relative;
          overflow: hidden;
        }
        
        .energy-flow-svg {
          width: 100%;
          height: auto;
          max-width: 800px;
          margin: 0 auto;
          display: block;
        }
        
        .component {
          cursor: pointer;
          transition: all 0.3s ease;
        }
        
        .component:hover {
          transform: scale(1.05);
          filter: brightness(1.1);
        }
        
        .power-value {
          font-family: 'Roboto', sans-serif;
          font-weight: bold;
          text-anchor: middle;
          dominant-baseline: middle;
          font-size: 14px;
          fill: #2c3e50;
        }
        
        .power-label {
          font-family: 'Roboto', sans-serif;
          text-anchor: middle;
          dominant-baseline: middle;
          font-size: 10px;
          fill: #7f8c8d;
        }
        
        .arrow {
          animation: pulse 2s infinite;
        }
        
        @keyframes pulse {
          0% { opacity: 0.6; }
          50% { opacity: 1; }
          100% { opacity: 0.6; }
        }
        
        .status-indicator {
          position: absolute;
          top: 10px;
          right: 10px;
          padding: 5px 10px;
          border-radius: 15px;
          color: white;
          font-size: 12px;
          font-weight: bold;
        }
        
        .model-info {
          position: absolute;
          top: 10px;
          left: 10px;
          padding: 5px 10px;
          background: rgba(255, 255, 255, 0.9);
          border-radius: 15px;
          font-size: 12px;
          color: #2c3e50;
        }
        
        .power-flow {
          position: absolute;
          bottom: 10px;
          left: 50%;
          transform: translateX(-50%);
          background: rgba(255, 255, 255, 0.9);
          padding: 10px 20px;
          border-radius: 20px;
          font-size: 14px;
          color: #2c3e50;
          text-align: center;
        }
        
        .hidden {
          display: none;
        }
        
        .tooltip {
          position: absolute;
          background: rgba(0, 0, 0, 0.8);
          color: white;
          padding: 8px 12px;
          border-radius: 6px;
          font-size: 12px;
          pointer-events: none;
          z-index: 1000;
          opacity: 0;
          transition: opacity 0.3s ease;
        }
        
        .tooltip.show {
          opacity: 1;
        }
      </style>
      
      <div class="energy-flow-container">
        <div class="status-indicator" style="background-color: ${statusColor}">
          ${systemStatus}
        </div>
        
        <div class="model-info">
          ${entities.model?.state || 'Unknown Model'}
        </div>
        
        <svg class="energy-flow-svg" viewBox="0 0 800 600" xmlns="http://www.w3.org/2000/svg">
          <!-- Background grid -->
          <defs>
            <pattern id="grid" width="40" height="40" patternUnits="userSpaceOnUse">
              <path d="M 40 0 L 0 0 0 40" fill="none" stroke="#e0e0e0" stroke-width="1"/>
            </pattern>
          </defs>
          <rect width="100%" height="100%" fill="url(#grid)" opacity="0.3"/>
          
          <!-- Solar Panel 1 -->
          <g class="component" data-component="pv1">
            <image href="/local/luxpower-dashboard/images/solar-panel.svg" x="50" y="50" width="80" height="80"/>
            <text x="90" y="140" class="power-value">${pv1Power}W</text>
            <text x="90" y="155" class="power-label">PV1</text>
          </g>
          
          <!-- Solar Panel 2 -->
          <g class="component" data-component="pv2">
            <image href="/local/luxpower-dashboard/images/solar-panel.svg" x="50" y="200" width="80" height="80"/>
            <text x="90" y="290" class="power-value">${pv2Power}W</text>
            <text x="90" y="305" class="power-label">PV2</text>
          </g>
          
          <!-- Solar Panel 3 (conditional) -->
          ${hasPV3 ? `
          <g class="component" data-component="pv3">
            <image href="/local/luxpower-dashboard/images/solar-panel.svg" x="50" y="350" width="80" height="80"/>
            <text x="90" y="440" class="power-value">${pv3Power}W</text>
            <text x="90" y="455" class="power-label">PV3</text>
          </g>
          ` : ''}
          
          <!-- Battery -->
          <g class="component" data-component="battery">
            <image href="/local/luxpower-dashboard/images/${batteryIcon}" x="200" y="200" width="80" height="80"/>
            <text x="240" y="290" class="power-value">${batterySOC}%</text>
            <text x="240" y="305" class="power-label">${batteryVoltage}V</text>
            <text x="240" y="320" class="power-label">${batteryCharge > 0 ? '+' : ''}${batteryCharge - batteryDischarge}W</text>
          </g>
          
          <!-- Inverter -->
          <g class="component" data-component="inverter">
            <image href="/local/luxpower-dashboard/images/inverter.svg" x="350" y="200" width="80" height="80"/>
            <text x="390" y="290" class="power-value">${pvPower}W</text>
            <text x="390" y="305" class="power-label">INVERTER</text>
          </g>
          
          <!-- Grid -->
          <g class="component" data-component="grid">
            <image href="/local/luxpower-dashboard/images/grid.svg" x="500" y="200" width="80" height="80"/>
            <text x="540" y="290" class="power-value">${gridImport > 0 ? gridImport : gridExport}W</text>
            <text x="540" y="305" class="power-label">${gridImport > 0 ? 'IMPORT' : 'EXPORT'}</text>
            <text x="540" y="320" class="power-label">${gridVoltage}V ${gridFrequency}Hz</text>
          </g>
          
          <!-- Home/Load -->
          <g class="component" data-component="home">
            <image href="/local/luxpower-dashboard/images/home.svg" x="350" y="350" width="80" height="80"/>
            <text x="390" y="440" class="power-value">${consumption}W</text>
            <text x="390" y="455" class="power-label">CONSUMPTION</text>
          </g>
          
          <!-- EPS/Backup (conditional) -->
          ${hasEPS ? `
          <g class="component" data-component="eps">
            <image href="/local/luxpower-dashboard/images/eps-backup.svg" x="200" y="350" width="80" height="80"/>
            <text x="240" y="440" class="power-value">${epsPower}W</text>
            <text x="240" y="455" class="power-label">EPS/BACKUP</text>
          </g>
          ` : ''}
          
          <!-- Generator (conditional) -->
          ${hasGenerator ? `
          <g class="component" data-component="generator">
            <image href="/local/luxpower-dashboard/images/generator.svg" x="500" y="350" width="80" height="80"/>
            <text x="540" y="440" class="power-value">0W</text>
            <text x="540" y="455" class="power-label">GENERATOR</text>
          </g>
          ` : ''}
          
          <!-- Energy Flow Arrows -->
          ${this._renderEnergyFlowArrows(pvToLoad, pvToBattery, pvToGrid, batteryToLoad, gridToLoad)}
        </svg>
        
        <div class="power-flow">
          <strong>Energy Flow:</strong> ${this._getEnergyFlowDescription(pvPower, batteryCharge, batteryDischarge, gridImport, gridExport)}
        </div>
        
        <div class="tooltip" id="tooltip"></div>
      </div>
    `;
    
    this._addEventListeners();
  }

  _renderEnergyFlowArrows(pvToLoad, pvToBattery, pvToGrid, batteryToLoad, gridToLoad) {
    let arrows = '';
    
    // PV to Load (if PV is powering loads directly)
    if (pvToLoad > 50) {
      arrows += `
        <g class="arrow">
          <image href="/local/luxpower-dashboard/images/arrow-right.svg" x="140" y="90" width="40" height="40" opacity="0.8"/>
          <text x="160" y="110" class="power-label">${pvToLoad}W</text>
        </g>
      `;
    }
    
    // PV to Battery (if charging)
    if (pvToBattery > 50) {
      arrows += `
        <g class="arrow">
          <image href="/local/luxpower-dashboard/images/arrow-right.svg" x="140" y="240" width="40" height="40" opacity="0.8"/>
          <text x="160" y="260" class="power-label">${pvToBattery}W</text>
        </g>
      `;
    }
    
    // Battery to Load (if discharging)
    if (batteryToLoad > 50) {
      arrows += `
        <g class="arrow">
          <image href="/local/luxpower-dashboard/images/arrow-right.svg" x="290" y="240" width="40" height="40" opacity="0.8"/>
          <text x="310" y="260" class="power-label">${batteryToLoad}W</text>
        </g>
      `;
    }
    
    // Grid to Load (if importing)
    if (gridToLoad > 50) {
      arrows += `
        <g class="arrow">
          <image href="/local/luxpower-dashboard/images/arrow-left.svg" x="450" y="240" width="40" height="40" opacity="0.8"/>
          <text x="470" y="260" class="power-label">${gridToLoad}W</text>
        </g>
      `;
    }
    
    return arrows;
  }

  _getBatteryIcon(soc) {
    if (soc >= 90) return 'battery-100.svg';
    if (soc >= 80) return 'battery-100.svg';
    if (soc >= 70) return 'battery-100.svg';
    if (soc >= 60) return 'battery-100.svg';
    if (soc >= 50) return 'battery-50.svg';
    if (soc >= 40) return 'battery-50.svg';
    if (soc >= 30) return 'battery-50.svg';
    if (soc >= 20) return 'battery-50.svg';
    if (soc >= 10) return 'battery-50.svg';
    return 'battery-0.svg';
  }

  _getStatusColor(status) {
    switch (status.toLowerCase()) {
      case 'normal': return '#27ae60';
      case 'warning': return '#f39c12';
      case 'fault': return '#e74c3c';
      case 'offline': return '#95a5a6';
      default: return '#3498db';
    }
  }

  _getEnergyFlowDescription(pvPower, batteryCharge, batteryDischarge, gridImport, gridExport) {
    if (pvPower > 0 && batteryCharge > 0 && gridExport > 0) {
      return 'Solar charging battery and exporting to grid';
    } else if (pvPower > 0 && batteryCharge > 0) {
      return 'Solar charging battery';
    } else if (pvPower > 0 && gridExport > 0) {
      return 'Solar exporting to grid';
    } else if (batteryDischarge > 0 && gridImport > 0) {
      return 'Battery and grid powering loads';
    } else if (batteryDischarge > 0) {
      return 'Battery powering loads';
    } else if (gridImport > 0) {
      return 'Grid powering loads';
    } else {
      return 'System idle';
    }
  }

  _getNumericValue(entity) {
    if (!entity || entity.state === 'unavailable' || entity.state === 'unknown') {
      return 0;
    }
    return parseFloat(entity.state) || 0;
  }

  _addEventListeners() {
    const components = this.shadowRoot.querySelectorAll('.component');
    const tooltip = this.shadowRoot.getElementById('tooltip');
    
    components.forEach(component => {
      component.addEventListener('mouseenter', (e) => {
        this._showTooltip(e, component);
      });
      
      component.addEventListener('mouseleave', () => {
        this._hideTooltip();
      });
      
      component.addEventListener('mousemove', (e) => {
        this._updateTooltipPosition(e);
      });
    });
  }

  _showTooltip(event, component) {
    const tooltip = this.shadowRoot.getElementById('tooltip');
    const componentType = component.dataset.component;
    const entities = this._entities;
    
    let content = '';
    switch (componentType) {
      case 'pv1':
        content = `PV1 Power: ${this._getNumericValue(entities.pv1_power)}W`;
        break;
      case 'pv2':
        content = `PV2 Power: ${this._getNumericValue(entities.pv2_power)}W`;
        break;
      case 'pv3':
        content = `PV3 Power: ${this._getNumericValue(entities.pv3_power)}W`;
        break;
      case 'battery':
        content = `SOC: ${this._getNumericValue(entities.battery_soc)}%<br>Voltage: ${this._getNumericValue(entities.battery_voltage)}V<br>Charge: ${this._getNumericValue(entities.battery_charge)}W<br>Discharge: ${this._getNumericValue(entities.battery_discharge)}W`;
        break;
      case 'inverter':
        content = `Total PV: ${this._getNumericValue(entities.pv_power)}W<br>Status: ${entities.system_status?.state || 'Unknown'}`;
        break;
      case 'grid':
        content = `Import: ${this._getNumericValue(entities.grid_import)}W<br>Export: ${this._getNumericValue(entities.grid_export)}W<br>Voltage: ${this._getNumericValue(entities.grid_voltage)}V<br>Frequency: ${this._getNumericValue(entities.grid_frequency)}Hz`;
        break;
      case 'home':
        content = `Consumption: ${this._getNumericValue(entities.consumption)}W<br>Load Power: ${this._getNumericValue(entities.load_power)}W`;
        break;
      case 'eps':
        content = `EPS Power: ${this._getNumericValue(entities.eps_power)}W<br>Backup Mode`;
        break;
      case 'generator':
        content = `Generator: Standby<br>Available for backup`;
        break;
    }
    
    tooltip.innerHTML = content;
    tooltip.classList.add('show');
    this._updateTooltipPosition(event);
  }

  _hideTooltip() {
    const tooltip = this.shadowRoot.getElementById('tooltip');
    tooltip.classList.remove('show');
  }

  _updateTooltipPosition(event) {
    const tooltip = this.shadowRoot.getElementById('tooltip');
    const rect = this.getBoundingClientRect();
    tooltip.style.left = (event.clientX - rect.left + 10) + 'px';
    tooltip.style.top = (event.clientY - rect.top - 10) + 'px';
  }

  getCardSize() {
    return 3;
  }
}

customElements.define('luxpower-energy-flow-card', LuxPowerEnergyFlowCard);
