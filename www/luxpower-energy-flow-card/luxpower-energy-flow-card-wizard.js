// LuxPower Energy Flow Card Wizard Configuration
// This file provides auto-configuration for the card wizard

class LuxPowerEnergyFlowCardWizard extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
  }

  connectedCallback() {
    this._render();
  }

  _render() {
    this.shadowRoot.innerHTML = `
      <style>
        .wizard-container {
          padding: 20px;
          font-family: 'Roboto', sans-serif;
        }
        
        .wizard-title {
          font-size: 24px;
          font-weight: 500;
          color: #2c3e50;
          margin-bottom: 20px;
        }
        
        .wizard-description {
          color: #7f8c8d;
          margin-bottom: 30px;
          line-height: 1.5;
        }
        
        .auto-config-section {
          background: #f8f9fa;
          border: 1px solid #e9ecef;
          border-radius: 8px;
          padding: 20px;
          margin-bottom: 20px;
        }
        
        .auto-config-title {
          font-size: 18px;
          font-weight: 500;
          color: #2c3e50;
          margin-bottom: 15px;
        }
        
        .auto-config-description {
          color: #6c757d;
          margin-bottom: 20px;
        }
        
        .entity-list {
          list-style: none;
          padding: 0;
          margin: 0;
        }
        
        .entity-item {
          display: flex;
          align-items: center;
          padding: 8px 0;
          border-bottom: 1px solid #e9ecef;
        }
        
        .entity-item:last-child {
          border-bottom: none;
        }
        
        .entity-name {
          font-weight: 500;
          color: #495057;
          margin-right: 10px;
        }
        
        .entity-description {
          color: #6c757d;
          font-size: 14px;
        }
        
        .auto-config-button {
          background: #007bff;
          color: white;
          border: none;
          padding: 12px 24px;
          border-radius: 6px;
          font-size: 16px;
          font-weight: 500;
          cursor: pointer;
          transition: background-color 0.2s;
        }
        
        .auto-config-button:hover {
          background: #0056b3;
        }
        
        .manual-config-section {
          background: #fff3cd;
          border: 1px solid #ffeaa7;
          border-radius: 8px;
          padding: 20px;
          margin-bottom: 20px;
        }
        
        .manual-config-title {
          font-size: 18px;
          font-weight: 500;
          color: #856404;
          margin-bottom: 15px;
        }
        
        .manual-config-description {
          color: #856404;
          margin-bottom: 20px;
        }
        
        .manual-config-button {
          background: #ffc107;
          color: #212529;
          border: none;
          padding: 12px 24px;
          border-radius: 6px;
          font-size: 16px;
          font-weight: 500;
          cursor: pointer;
          transition: background-color 0.2s;
        }
        
        .manual-config-button:hover {
          background: #e0a800;
        }
        
        .features-section {
          background: #d1ecf1;
          border: 1px solid #bee5eb;
          border-radius: 8px;
          padding: 20px;
        }
        
        .features-title {
          font-size: 18px;
          font-weight: 500;
          color: #0c5460;
          margin-bottom: 15px;
        }
        
        .features-list {
          list-style: none;
          padding: 0;
          margin: 0;
        }
        
        .feature-item {
          display: flex;
          align-items: center;
          padding: 8px 0;
        }
        
        .feature-icon {
          margin-right: 10px;
          color: #0c5460;
        }
        
        .feature-text {
          color: #0c5460;
        }
      </style>
      
      <div class="wizard-container">
        <div class="wizard-title">LuxPower Energy Flow Card</div>
        <div class="wizard-description">
          This card provides an interactive energy flow visualization for LuxPower inverters, 
          replicating the cloud interface experience in Home Assistant.
        </div>
        
        <div class="auto-config-section">
          <div class="auto-config-title">🚀 Auto-Configuration</div>
          <div class="auto-config-description">
            The card will automatically detect and configure itself using these default entities:
          </div>
          <ul class="entity-list">
            <li class="entity-item">
              <span class="entity-name">sensor.lux_inverter_model</span>
              <span class="entity-description">Inverter model detection</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_pv_power</span>
              <span class="entity-description">Total PV power</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_battery_soc</span>
              <span class="entity-description">Battery state of charge</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_battery_voltage</span>
              <span class="entity-description">Battery voltage</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_grid_import_power</span>
              <span class="entity-description">Grid import power</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_grid_export_power</span>
              <span class="entity-description">Grid export power</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_load_power</span>
              <span class="entity-description">Load power</span>
            </li>
            <li class="entity-item">
              <span class="entity-name">sensor.lux_status_text</span>
              <span class="entity-description">System status</span>
            </li>
          </ul>
          <button class="auto-config-button" onclick="this._autoConfigure()">
            Use Auto-Configuration
          </button>
        </div>
        
        <div class="manual-config-section">
          <div class="manual-config-title">⚙️ Manual Configuration</div>
          <div class="manual-config-description">
            If you need to customize entity names or have specific requirements, 
            you can manually configure the card.
          </div>
          <button class="manual-config-button" onclick="this._manualConfigure()">
            Manual Configuration
          </button>
        </div>
        
        <div class="features-section">
          <div class="features-title">✨ Features</div>
          <ul class="features-list">
            <li class="feature-item">
              <span class="feature-icon">🎨</span>
              <span class="feature-text">Animated energy flow diagram</span>
            </li>
            <li class="feature-item">
              <span class="feature-icon">📱</span>
              <span class="feature-text">Responsive design for all devices</span>
            </li>
            <li class="feature-item">
              <span class="feature-icon">🔍</span>
              <span class="feature-text">Interactive components</span>
            </li>
            <li class="feature-item">
              <span class="feature-icon">⚡</span>
              <span class="feature-text">Real-time power values</span>
            </li>
            <li class="feature-item">
              <span class="feature-icon">🏠</span>
              <span class="feature-text">Model-specific features</span>
            </li>
          </ul>
        </div>
      </div>
    `;
  }

  _autoConfigure() {
    // Create the card with auto-configuration
    const config = {
      type: 'custom:luxpower-energy-flow-card'
    };
    
    // Dispatch event to add the card
    this.dispatchEvent(new CustomEvent('config-changed', {
      detail: { config },
      bubbles: true,
      composed: true
    }));
  }

  _manualConfigure() {
    // Open the manual configuration editor
    this.dispatchEvent(new CustomEvent('config-changed', {
      detail: { 
        config: {
          type: 'custom:luxpower-energy-flow-card',
          // Add manual configuration options here
        }
      },
      bubbles: true,
      composed: true
    }));
  }
}

// Register the wizard
customElements.define('luxpower-energy-flow-card-wizard', LuxPowerEnergyFlowCardWizard);

// Export for use in card wizard
window.customCards = window.customCards || [];
window.customCards.push({
  type: 'custom:luxpower-energy-flow-card',
  name: 'LuxPower Energy Flow Card',
  description: 'Interactive energy flow visualization for LuxPower inverters',
  preview: true,
  documentationURL: 'https://github.com/your-repo/luxpower-energy-flow-card',
  version: '1.0.0',
  wizard: 'luxpower-energy-flow-card-wizard'
});
