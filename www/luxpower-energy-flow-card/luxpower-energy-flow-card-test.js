class LuxPowerEnergyFlowCard extends HTMLElement {
  constructor() {
    super();
    console.log('LuxPowerEnergyFlowCard constructor called');
    this.attachShadow({ mode: 'open' });
    this._config = {};
    this._hass = {};
    this._entities = {};
  }

  static getConfigElement() {
    return document.createElement('luxpower-energy-flow-card-editor');
  }

  static getStubConfig() {
    return {
      type: 'custom:luxpower-energy-flow-card'
    };
  }

  setConfig(config) {
    console.log('setConfig called with:', config);
    this._config = config || {};
  }

  set hass(hass) {
    console.log('hass setter called');
    this._hass = hass;
    this._render();
  }

  _render() {
    console.log('_render called');
    this.shadowRoot.innerHTML = `
      <style>
        :host {
          display: block;
          padding: 20px;
          background: #f0f0f0;
          border-radius: 8px;
        }
      </style>
      <div>
        <h3>LuxPower Energy Flow Card</h3>
        <p>This is a test version. If you see this, the custom element is working!</p>
        <p>Config: ${JSON.stringify(this._config)}</p>
      </div>
    `;
  }

  getCardSize() {
    return 3;
  }
}

console.log('About to define custom element');
customElements.define('luxpower-energy-flow-card', LuxPowerEnergyFlowCard);
console.log('Custom element defined successfully');
