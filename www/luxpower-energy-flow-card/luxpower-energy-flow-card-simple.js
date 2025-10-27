// Simple test version of LuxPower Energy Flow Card
console.log('Loading LuxPower Energy Flow Card - Simple Version');

class LuxPowerEnergyFlowCardSimple extends HTMLElement {
  constructor() {
    super();
    console.log('LuxPowerEnergyFlowCardSimple: Constructor called');
    this.attachShadow({ mode: 'open' });
    this._config = {};
    this._hass = {};
  }

  static getConfigElement() {
    console.log('LuxPowerEnergyFlowCardSimple: getConfigElement called');
    return document.createElement('luxpower-energy-flow-card-editor');
  }

  static getStubConfig() {
    console.log('LuxPowerEnergyFlowCardSimple: getStubConfig called');
    return {
      type: 'custom:luxpower-energy-flow-card-simple'
    };
  }

  setConfig(config) {
    console.log('LuxPowerEnergyFlowCardSimple: setConfig called with:', config);
    this._config = config || {};
  }

  set hass(hass) {
    console.log('LuxPowerEnergyFlowCardSimple: hass setter called');
    this._hass = hass;
    this._render();
  }

  _render() {
    console.log('LuxPowerEnergyFlowCardSimple: _render called');
    this.shadowRoot.innerHTML = `
      <style>
        :host {
          display: block;
          font-family: 'Roboto', sans-serif;
          padding: 20px;
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          border-radius: 12px;
          color: white;
          text-align: center;
        }
        .success {
          font-size: 24px;
          font-weight: bold;
          margin-bottom: 20px;
        }
        .info {
          font-size: 16px;
          margin-bottom: 10px;
        }
        .config {
          background: rgba(255, 255, 255, 0.1);
          padding: 15px;
          border-radius: 8px;
          margin-top: 20px;
          text-align: left;
        }
      </style>
      
      <div class="success">✅ LuxPower Energy Flow Card Working!</div>
      <div class="info">🎉 The card is loading successfully!</div>
      <div class="info">📊 This is a simplified test version</div>
      
      <div class="config">
        <h3>Configuration:</h3>
        <pre>${JSON.stringify(this._config, null, 2)}</pre>
      </div>
      
      <div class="info">
        <p>If you see this message, the JavaScript is loading correctly!</p>
        <p>Next step: Install the full version and configure your LuxPower entities.</p>
      </div>
    `;
  }

  getCardSize() {
    return 3;
  }
}

// Register the custom element
console.log('LuxPowerEnergyFlowCardSimple: Registering custom element');
customElements.define('luxpower-energy-flow-card-simple', LuxPowerEnergyFlowCardSimple);
console.log('LuxPowerEnergyFlowCardSimple: Custom element registered successfully');
