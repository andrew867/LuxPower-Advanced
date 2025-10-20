#!/usr/bin/env python3
"""
LuxPower Entity Manager

This script manages LuxPower entities based on detected model,
handles entity recreation, and ensures energy dashboard compatibility.

Features:
- Automatic entity enablement based on detected model
- Entity recreation with proper model-based filtering
- Energy dashboard auto-configuration
- Model detection and validation

Usage:
    python luxpower_entity_manager.py [--recreate-entities] [--configure-energy] [--model MODEL_CODE]
"""

import os
import json
import requests
import logging
import argparse
from typing import Dict, List, Optional, Set
from dataclasses import dataclass

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

@dataclass
class EntityInfo:
    entity_id: str
    name: str
    device_class: Optional[str]
    state_class: Optional[str]
    unit_of_measurement: Optional[str]
    enabled: bool
    unique_id: str
    device_id: Optional[str] = None

class LuxPowerEntityManager:
    def __init__(self, hass_url: str, token: str):
        self.hass_url = hass_url.rstrip('/')
        self.token = token
        self.headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
        
        # Model-specific entity patterns
        self.model_patterns = {
            '12K': [
                '12k', '12_k', 'peak_shaving', 'grid_peak', 'ac_couple',
                'force_charge', 'force_discharge', 'generator'
            ],
            'standard': [
                'battery', 'solar', 'grid', 'inverter', 'power', 'energy',
                'charge', 'discharge', 'soc', 'voltage', 'current'
            ]
        }
        
        # Essential energy dashboard entities
        self.energy_entities = {
            'solar_production': ['solar', 'pv', 'array'],
            'battery_charge': ['battery_charge', 'charge'],
            'battery_discharge': ['battery_discharge', 'discharge'],
            'grid_consumption': ['power_from_grid', 'grid_consumption'],
            'grid_feed_in': ['power_to_grid', 'grid_feed'],
            'home_consumption': ['power_from_inverter', 'home_consumption']
        }
    
    def get_entities(self) -> List[EntityInfo]:
        """Get all LuxPower entities."""
        try:
            response = requests.get(f"{self.hass_url}/api/states", headers=self.headers)
            response.raise_for_status()
            states = response.json()
            
            # Get entity registry for additional info
            registry_response = requests.get(f"{self.hass_url}/api/config/entity_registry/list", headers=self.headers)
            registry_data = registry_response.json() if registry_response.status_code == 200 else {}
            registry_entities = {e['entity_id']: e for e in registry_data.get('entities', [])}
            
            entities = []
            for state in states:
                entity_id = state.get('entity_id', '')
                if entity_id.startswith(('sensor.lux_', 'switch.lux_', 'number.lux_', 'binary_sensor.lux_', 'time.lux_', 'button.lux_')):
                    registry_info = registry_entities.get(entity_id, {})
                    entities.append(EntityInfo(
                        entity_id=entity_id,
                        name=state.get('attributes', {}).get('friendly_name', entity_id),
                        device_class=state.get('attributes', {}).get('device_class'),
                        state_class=state.get('attributes', {}).get('state_class'),
                        unit_of_measurement=state.get('attributes', {}).get('unit_of_measurement'),
                        enabled=registry_info.get('disabled', True) == False,
                        unique_id=registry_info.get('unique_id', ''),
                        device_id=registry_info.get('device_id')
                    ))
            
            return entities
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get entities: {e}")
            return []
    
    def detect_model_from_entities(self, entities: List[EntityInfo]) -> Optional[str]:
        """Detect model from entity names and firmware version."""
        try:
            # Try to get firmware version from sensor
            response = requests.get(f"{self.hass_url}/api/states/sensor.lux_firmware_version", headers=self.headers)
            if response.status_code == 200:
                firmware = response.json().get('state', '')
                if firmware and firmware != 'unavailable':
                    logger.info(f"Detected firmware version: {firmware}")
                    # Extract model code from firmware (first 4 characters)
                    if len(firmware) >= 4:
                        model_code = firmware[:4].upper()
                        logger.info(f"Extracted model code: {model_code}")
                        return model_code
        except Exception as e:
            logger.debug(f"Could not detect model from firmware: {e}")
        
        # Fallback: detect from entity names
        entity_names = [e.name.lower() for e in entities]
        if any('12k' in name for name in entity_names):
            return '12K'
        
        return 'standard'
    
    def is_12k_model(self, model_code: str) -> bool:
        """Check if model is 12K series."""
        return model_code and ('12K' in model_code.upper() or '12' in model_code)
    
    def should_enable_entity(self, entity: EntityInfo, model_code: Optional[str]) -> bool:
        """Determine if entity should be enabled based on model."""
        entity_name = entity.name.lower()
        entity_id = entity.entity_id.lower()
        
        # Always enable essential energy entities
        for category, keywords in self.energy_entities.items():
            if any(keyword in entity_name or keyword in entity_id for keyword in keywords):
                return True
        
        # Always enable basic power/energy sensors
        essential_keywords = [
            'battery', 'solar', 'grid', 'power', 'energy', 'charge', 'discharge',
            'soc', 'voltage', 'current', 'frequency', 'temperature'
        ]
        if any(keyword in entity_name or keyword in entity_id for keyword in essential_keywords):
            return True
        
        # Model-specific enablement
        if model_code:
            is_12k = self.is_12k_model(model_code)
            
            # 12K-specific entities
            if is_12k:
                if any(keyword in entity_name for keyword in self.model_patterns['12K']):
                    return True
            else:
                # Disable 12K-specific entities for non-12K models
                if any(keyword in entity_name for keyword in self.model_patterns['12K']):
                    return False
        
        # Default to enabled for standard entities
        return True
    
    def update_entity_enabled_state(self, entity_id: str, enabled: bool) -> bool:
        """Update entity enabled state."""
        try:
            data = {'disabled': not enabled}
            response = requests.post(
                f"{self.hass_url}/api/config/entity_registry/update_entity",
                headers=self.headers,
                json={'entity_id': entity_id, **data}
            )
            response.raise_for_status()
            return True
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to update entity {entity_id}: {e}")
            return False
    
    def recreate_entities(self, model_code: Optional[str] = None) -> bool:
        """Recreate entities with proper model-based enablement."""
        logger.info("Starting entity recreation...")
        
        entities = self.get_entities()
        if not entities:
            logger.error("No LuxPower entities found")
            return False
        
        # Detect model if not provided
        if not model_code:
            model_code = self.detect_model_from_entities(entities)
            logger.info(f"Detected model: {model_code}")
        
        # Process entities
        updated_count = 0
        enabled_count = 0
        disabled_count = 0
        
        for entity in entities:
            should_enable = self.should_enable_entity(entity, model_code)
            
            if entity.enabled != should_enable:
                success = self.update_entity_enabled_state(entity.entity_id, should_enable)
                if success:
                    updated_count += 1
                    if should_enable:
                        enabled_count += 1
                        logger.info(f"Enabled: {entity.entity_id}")
                    else:
                        disabled_count += 1
                        logger.info(f"Disabled: {entity.entity_id}")
        
        logger.info(f"Entity recreation completed: {updated_count} updated, {enabled_count} enabled, {disabled_count} disabled")
        return True
    
    def configure_energy_dashboard(self) -> bool:
        """Configure energy dashboard with LuxPower entities."""
        try:
            entities = self.get_entities()
            luxpower_entities = self.find_energy_entities(entities)
            
            if not any(luxpower_entities.values()):
                logger.error("No energy entities found for dashboard configuration")
                return False
            
            # Create energy dashboard configuration
            config = self.create_energy_config(luxpower_entities)
            
            # Update configuration
            response = requests.post(f"{self.hass_url}/api/config/energy", 
                                   headers=self.headers, 
                                   json=config)
            response.raise_for_status()
            
            logger.info("Energy dashboard configured successfully!")
            return True
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to configure energy dashboard: {e}")
            return False
    
    def find_energy_entities(self, entities: List[EntityInfo]) -> Dict[str, List[str]]:
        """Find energy-related entities."""
        energy_entities = {category: [] for category in self.energy_entities.keys()}
        
        for entity in entities:
            if not entity.enabled:
                continue
                
            entity_name = entity.name.lower()
            entity_id = entity.entity_id.lower()
            
            for category, keywords in self.energy_entities.items():
                if any(keyword in entity_name or keyword in entity_id for keyword in keywords):
                    energy_entities[category].append(entity.entity_id)
        
        return energy_entities
    
    def create_energy_config(self, energy_entities: Dict[str, List[str]]) -> Dict:
        """Create energy dashboard configuration."""
        config = {
            "energy_sources": [],
            "device_consumption": []
        }
        
        # Add solar production
        if energy_entities['solar_production']:
            solar_entity = energy_entities['solar_production'][0]
            config["energy_sources"].append({
                "type": "solar",
                "stat_energy_from": solar_entity,
                "name": "LuxPower Solar"
            })
            logger.info(f"Added solar: {solar_entity}")
        
        # Add battery
        if energy_entities['battery_charge'] and energy_entities['battery_discharge']:
            charge_entity = energy_entities['battery_charge'][0]
            discharge_entity = energy_entities['battery_discharge'][0]
            config["energy_sources"].append({
                "type": "battery",
                "stat_energy_from": charge_entity,
                "stat_energy_to": discharge_entity,
                "name": "LuxPower Battery"
            })
            logger.info(f"Added battery: {charge_entity} -> {discharge_entity}")
        
        # Add grid
        if energy_entities['grid_consumption']:
            grid_consumption = energy_entities['grid_consumption'][0]
            grid_feed_in = energy_entities['grid_feed_in'][0] if energy_entities['grid_feed_in'] else None
            
            grid_config = {
                "type": "grid",
                "flow_from": [grid_consumption],
                "name": "LuxPower Grid"
            }
            if grid_feed_in:
                grid_config["flow_to"] = [grid_feed_in]
            
            config["energy_sources"].append(grid_config)
            logger.info(f"Added grid: {grid_consumption}")
        
        # Add home consumption
        if energy_entities['home_consumption']:
            home_entity = energy_entities['home_consumption'][0]
            config["device_consumption"].append({
                "stat_energy_from": home_entity,
                "name": "LuxPower Home"
            })
            logger.info(f"Added home consumption: {home_entity}")
        
        return config
    
    def run(self, recreate_entities: bool = False, configure_energy: bool = False, model_code: Optional[str] = None):
        """Run the entity manager."""
        logger.info("Starting LuxPower Entity Manager...")
        
        if recreate_entities:
            success = self.recreate_entities(model_code)
            if not success:
                logger.error("Entity recreation failed")
                return False
        
        if configure_energy:
            success = self.configure_energy_dashboard()
            if not success:
                logger.error("Energy dashboard configuration failed")
                return False
        
        logger.info("LuxPower Entity Manager completed successfully!")
        return True

def main():
    """Main function."""
    parser = argparse.ArgumentParser(description='LuxPower Entity Manager')
    parser.add_argument('--recreate-entities', action='store_true', 
                       help='Recreate entities with model-based enablement')
    parser.add_argument('--configure-energy', action='store_true',
                       help='Configure energy dashboard')
    parser.add_argument('--model', type=str, help='Model code (e.g., 12K, S6)')
    parser.add_argument('--url', type=str, default='http://homeassistant.local:8123',
                       help='Home Assistant URL')
    
    args = parser.parse_args()
    
    # Get token from environment
    token = os.getenv('HASS_TOKEN')
    if not token:
        logger.error("HASS_TOKEN environment variable not set")
        logger.error("Set it with: export HASS_TOKEN='your_long_lived_access_token'")
        return False
    
    # Create manager and run
    manager = LuxPowerEntityManager(args.url, token)
    return manager.run(
        recreate_entities=args.recreate_entities,
        configure_energy=args.configure_energy,
        model_code=args.model
    )

if __name__ == "__main__":
    success = main()
    exit(0 if success else 1)
