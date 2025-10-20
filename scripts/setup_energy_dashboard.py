#!/usr/bin/env python3
"""
LuxPower Energy Dashboard Auto-Configuration Script

This script automatically configures the Home Assistant energy dashboard
with LuxPower inverter sensors for optimal energy monitoring.

Usage:
    python setup_energy_dashboard.py

Requirements:
    - Home Assistant running
    - LuxPower integration installed and configured
    - HASS_TOKEN environment variable set (or edit script to use API key)
"""

import os
import json
import requests
import logging
from typing import Dict, List, Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class EnergyDashboardConfigurator:
    def __init__(self, hass_url: str, token: str):
        self.hass_url = hass_url.rstrip('/')
        self.token = token
        self.headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
        
    def get_entities(self) -> List[Dict]:
        """Get all entities from Home Assistant."""
        try:
            response = requests.get(f"{self.hass_url}/api/states", headers=self.headers)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get entities: {e}")
            return []
    
    def find_luxpower_entities(self, entities: List[Dict]) -> Dict[str, List[str]]:
        """Find LuxPower entities by type."""
        luxpower_entities = {
            'solar_production': [],
            'battery_charge': [],
            'battery_discharge': [],
            'grid_consumption': [],
            'grid_feed_in': [],
            'home_consumption': []
        }
        
        for entity in entities:
            entity_id = entity.get('entity_id', '')
            if not entity_id.startswith('sensor.lux_'):
                continue
                
            # Solar production sensors
            if any(keyword in entity_id.lower() for keyword in ['solar', 'pv', 'array']):
                if 'daily' in entity_id.lower() or 'total' in entity_id.lower():
                    luxpower_entities['solar_production'].append(entity_id)
            
            # Battery charge sensors
            elif 'battery_charge' in entity_id.lower() and ('daily' in entity_id.lower() or 'total' in entity_id.lower()):
                luxpower_entities['battery_charge'].append(entity_id)
            
            # Battery discharge sensors
            elif 'battery_discharge' in entity_id.lower() and ('daily' in entity_id.lower() or 'total' in entity_id.lower()):
                luxpower_entities['battery_discharge'].append(entity_id)
            
            # Grid consumption sensors
            elif 'power_from_grid' in entity_id.lower() and ('daily' in entity_id.lower() or 'total' in entity_id.lower()):
                luxpower_entities['grid_consumption'].append(entity_id)
            
            # Grid feed-in sensors
            elif 'power_to_grid' in entity_id.lower() and ('daily' in entity_id.lower() or 'total' in entity_id.lower()):
                luxpower_entities['grid_feed_in'].append(entity_id)
            
            # Home consumption sensors
            elif 'power_from_inverter' in entity_id.lower() and ('daily' in entity_id.lower() or 'total' in entity_id.lower()):
                luxpower_entities['home_consumption'].append(entity_id)
        
        return luxpower_entities
    
    def get_energy_dashboard_config(self) -> Optional[Dict]:
        """Get current energy dashboard configuration."""
        try:
            response = requests.get(f"{self.hass_url}/api/config/energy", headers=self.headers)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get energy dashboard config: {e}")
            return None
    
    def configure_energy_dashboard(self, luxpower_entities: Dict[str, List[str]]) -> bool:
        """Configure the energy dashboard with LuxPower entities."""
        try:
            # Get current config
            current_config = self.get_energy_dashboard_config()
            if not current_config:
                logger.error("Could not retrieve current energy dashboard configuration")
                return False
            
            # Create new configuration
            new_config = {
                "energy_sources": [],
                "device_consumption": []
            }
            
            # Add solar production
            if luxpower_entities['solar_production']:
                # Prefer daily solar production
                solar_entity = next((e for e in luxpower_entities['solar_production'] if 'daily' in e), 
                                 luxpower_entities['solar_production'][0])
                new_config["energy_sources"].append({
                    "type": "solar",
                    "stat_energy_from": solar_entity,
                    "name": "LuxPower Solar Production"
                })
                logger.info(f"Added solar production: {solar_entity}")
            
            # Add battery charge/discharge
            if luxpower_entities['battery_charge'] and luxpower_entities['battery_discharge']:
                charge_entity = next((e for e in luxpower_entities['battery_charge'] if 'daily' in e), 
                                  luxpower_entities['battery_charge'][0])
                discharge_entity = next((e for e in luxpower_entities['battery_discharge'] if 'daily' in e), 
                                     luxpower_entities['battery_discharge'][0])
                
                new_config["energy_sources"].append({
                    "type": "battery",
                    "stat_energy_from": charge_entity,
                    "stat_energy_to": discharge_entity,
                    "name": "LuxPower Battery"
                })
                logger.info(f"Added battery: charge={charge_entity}, discharge={discharge_entity}")
            
            # Add grid consumption
            if luxpower_entities['grid_consumption']:
                grid_entity = next((e for e in luxpower_entities['grid_consumption'] if 'daily' in e), 
                                luxpower_entities['grid_consumption'][0])
                new_config["energy_sources"].append({
                    "type": "grid",
                    "flow_from": [grid_entity],
                    "flow_to": luxpower_entities['grid_feed_in'] if luxpower_entities['grid_feed_in'] else [],
                    "name": "LuxPower Grid"
                })
                logger.info(f"Added grid consumption: {grid_entity}")
            
            # Add home consumption
            if luxpower_entities['home_consumption']:
                home_entity = next((e for e in luxpower_entities['home_consumption'] if 'daily' in e), 
                                 luxpower_entities['home_consumption'][0])
                new_config["device_consumption"].append({
                    "stat_energy_from": home_entity,
                    "name": "LuxPower Home Consumption"
                })
                logger.info(f"Added home consumption: {home_entity}")
            
            # Update the configuration
            response = requests.post(f"{self.hass_url}/api/config/energy", 
                                   headers=self.headers, 
                                   json=new_config)
            response.raise_for_status()
            
            logger.info("Energy dashboard configured successfully!")
            return True
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to configure energy dashboard: {e}")
            return False
    
    def run(self):
        """Run the energy dashboard configuration."""
        logger.info("Starting LuxPower Energy Dashboard configuration...")
        
        # Get all entities
        entities = self.get_entities()
        if not entities:
            logger.error("No entities found. Make sure Home Assistant is running and accessible.")
            return False
        
        # Find LuxPower entities
        luxpower_entities = self.find_luxpower_entities(entities)
        
        # Log found entities
        for category, entity_list in luxpower_entities.items():
            if entity_list:
                logger.info(f"Found {len(entity_list)} {category} entities: {entity_list}")
        
        # Check if we have enough entities
        if not any(luxpower_entities.values()):
            logger.error("No LuxPower entities found. Make sure the LuxPower integration is installed and configured.")
            return False
        
        # Configure energy dashboard
        success = self.configure_energy_dashboard(luxpower_entities)
        
        if success:
            logger.info("Energy dashboard configuration completed successfully!")
            logger.info("You can now view your energy dashboard in Home Assistant.")
        else:
            logger.error("Failed to configure energy dashboard.")
        
        return success

def main():
    """Main function."""
    # Configuration
    HASS_URL = os.getenv('HASS_URL', 'http://homeassistant.local:8123')
    HASS_TOKEN = os.getenv('HASS_TOKEN')
    
    if not HASS_TOKEN:
        logger.error("HASS_TOKEN environment variable not set.")
        logger.error("Please set it with: export HASS_TOKEN='your_long_lived_access_token'")
        logger.error("Or edit this script to include your token directly.")
        return False
    
    # Create configurator and run
    configurator = EnergyDashboardConfigurator(HASS_URL, HASS_TOKEN)
    return configurator.run()

if __name__ == "__main__":
    success = main()
    exit(0 if success else 1)
