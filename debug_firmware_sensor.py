#!/usr/bin/env python3
"""
LuxPower Firmware Sensor Debug Script

This script helps debug why the firmware sensor might be showing as 'unavailable'
and provides detailed information about register data and sensor status.

Usage:
    python debug_firmware_sensor.py
"""

import os
import requests
import logging
from typing import Dict, List, Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class FirmwareSensorDebugger:
    def __init__(self, hass_url: str, token: str):
        self.hass_url = hass_url.rstrip('/')
        self.token = token
        self.headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
    
    def get_firmware_sensor_state(self) -> Optional[Dict]:
        """Get the current state of the firmware sensor."""
        try:
            response = requests.get(f"{self.hass_url}/api/states/sensor.lux_firmware_version", headers=self.headers)
            if response.status_code == 200:
                return response.json()
            else:
                logger.error(f"Firmware sensor not found (status: {response.status_code})")
                return None
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get firmware sensor state: {e}")
            return None
    
    def get_model_sensor_state(self) -> Optional[Dict]:
        """Get the current state of the model sensor."""
        try:
            response = requests.get(f"{self.hass_url}/api/states/sensor.lux_inverter_model", headers=self.headers)
            if response.status_code == 200:
                return response.json()
            else:
                logger.error(f"Model sensor not found (status: {response.status_code})")
                return None
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get model sensor state: {e}")
            return None
    
    def get_all_luxpower_entities(self) -> List[Dict]:
        """Get all LuxPower entities."""
        try:
            response = requests.get(f"{self.hass_url}/api/states", headers=self.headers)
            response.raise_for_status()
            states = response.json()
            
            luxpower_entities = []
            for state in states:
                entity_id = state.get('entity_id', '')
                if entity_id.startswith(('sensor.lux_', 'switch.lux_', 'number.lux_', 'binary_sensor.lux_', 'time.lux_', 'button.lux_')):
                    luxpower_entities.append(state)
            
            return luxpower_entities
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get entities: {e}")
            return []
    
    def check_entity_availability(self) -> Dict[str, int]:
        """Check availability of LuxPower entities."""
        entities = self.get_all_luxpower_entities()
        
        stats = {
            'total': len(entities),
            'available': 0,
            'unavailable': 0,
            'waiting_for_data': 0,
            'firmware_data_not_available': 0,
            'other': 0
        }
        
        for entity in entities:
            state = entity.get('state', 'unknown')
            if state == 'unavailable':
                stats['unavailable'] += 1
            elif state == 'Waiting for data...':
                stats['waiting_for_data'] += 1
            elif state == 'Firmware data not available':
                stats['firmware_data_not_available'] += 1
            elif state in ['unknown', 'Unavailable', 'None']:
                stats['other'] += 1
            else:
                stats['available'] += 1
        
        return stats
    
    def debug_firmware_sensor(self) -> bool:
        """Debug the firmware sensor."""
        logger.info("Debugging LuxPower string sensors...")
        
        # Check firmware sensor
        firmware_state = self.get_firmware_sensor_state()
        if firmware_state:
            state = firmware_state.get('state', 'unknown')
            attributes = firmware_state.get('attributes', {})
            
            logger.info(f"Firmware Sensor State: {state}")
            logger.info(f"Firmware Sensor Attributes:")
            for key, value in attributes.items():
                logger.info(f"  {key}: {value}")
            
            if state == 'unavailable':
                logger.warning("❌ Firmware sensor is unavailable")
                return False
            elif state == 'Waiting for data...':
                logger.warning("⚠️  Firmware sensor is waiting for data")
                return False
            elif state == 'Firmware data not available':
                logger.warning("⚠️  Firmware sensor reports no firmware data available")
                return False
            else:
                logger.info(f"✅ Firmware sensor working: {state}")
        else:
            logger.error("❌ Firmware sensor not found")
            return False
        
        # Check model sensor
        model_state = self.get_model_sensor_state()
        if model_state:
            state = model_state.get('state', 'unknown')
            logger.info(f"Model Sensor State: {state}")
            
            if state == 'unavailable':
                logger.warning("❌ Model sensor is unavailable")
            else:
                logger.info(f"✅ Model sensor working: {state}")
        else:
            logger.warning("⚠️  Model sensor not found")
        
        return True
    
    def check_entity_statistics(self) -> None:
        """Check overall entity statistics."""
        logger.info("Checking LuxPower entity statistics...")
        
        stats = self.check_entity_availability()
        
        logger.info(f"Entity Statistics:")
        logger.info(f"  Total entities: {stats['total']}")
        logger.info(f"  Available: {stats['available']}")
        logger.info(f"  Unavailable: {stats['unavailable']}")
        logger.info(f"  Waiting for data: {stats['waiting_for_data']}")
        logger.info(f"  Firmware data not available: {stats['firmware_data_not_available']}")
        logger.info(f"  Other: {stats['other']}")
        
        if stats['unavailable'] > 0:
            logger.warning(f"⚠️  {stats['unavailable']} entities are unavailable")
        
        if stats['waiting_for_data'] > 0:
            logger.info(f"ℹ️  {stats['waiting_for_data']} entities are waiting for data")
        
        if stats['firmware_data_not_available'] > 0:
            logger.warning(f"⚠️  {stats['firmware_data_not_available']} entities report firmware data not available")
    
    def run_debug(self) -> bool:
        """Run firmware sensor debugging."""
        logger.info("Starting LuxPower Firmware Sensor Debug...")
        
        # Debug firmware sensor
        firmware_ok = self.debug_firmware_sensor()
        
        # Check entity statistics
        self.check_entity_statistics()
        
        if firmware_ok:
            logger.info("🎉 Firmware sensor debugging completed successfully!")
        else:
            logger.warning("⚠️  Firmware sensor debugging found issues!")
            logger.info("Check Home Assistant logs for more details about register data.")
        
        return firmware_ok

def main():
    """Main function."""
    # Configuration
    HASS_URL = os.getenv('HASS_URL', 'http://homeassistant.local:8123')
    HASS_TOKEN = os.getenv('HASS_TOKEN')
    
    if not HASS_TOKEN:
        logger.error("HASS_TOKEN environment variable not set.")
        logger.error("Please set it with: export HASS_TOKEN='your_long_lived_access_token'")
        return False
    
    # Create debugger and run
    debugger = FirmwareSensorDebugger(HASS_URL, HASS_TOKEN)
    return debugger.run_debug()

if __name__ == "__main__":
    success = main()
    exit(0 if success else 1)
