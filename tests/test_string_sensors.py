#!/usr/bin/env python3
"""
LuxPower String Sensor Test Script

This script helps verify that string-based sensors (firmware version, model, etc.)
are working correctly and not showing as 'unavailable'.

Usage:
    python test_string_sensors.py
"""

import os
import requests
import logging
from typing import Dict, List, Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class StringSensorTester:
    def __init__(self, hass_url: str, token: str):
        self.hass_url = hass_url.rstrip('/')
        self.token = token
        self.headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
        
        # String-based sensors to test
        self.string_sensors = [
            'sensor.lux_firmware_version',
            'sensor.lux_inverter_model',
            'sensor.lux_states',  # Overall state sensor
        ]
        
        # Text-based sensors
        self.text_sensors = [
            'sensor.lux_status_text',
            'sensor.lux_data_received_timestamp',
        ]
    
    def get_entity_state(self, entity_id: str) -> Optional[Dict]:
        """Get the state of a specific entity."""
        try:
            response = requests.get(f"{self.hass_url}/api/states/{entity_id}", headers=self.headers)
            if response.status_code == 200:
                return response.json()
            else:
                logger.warning(f"Entity {entity_id} not found (status: {response.status_code})")
                return None
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get entity {entity_id}: {e}")
            return None
    
    def test_string_sensors(self) -> bool:
        """Test string-based sensors."""
        logger.info("Testing string-based sensors...")
        all_good = True
        
        for sensor_id in self.string_sensors:
            state = self.get_entity_state(sensor_id)
            if state:
                value = state.get('state', 'unknown')
                if value == 'unavailable':
                    logger.error(f"❌ {sensor_id}: {value}")
                    all_good = False
                elif value in ['unknown', 'Unavailable', 'None']:
                    logger.warning(f"⚠️  {sensor_id}: {value}")
                else:
                    logger.info(f"✅ {sensor_id}: {value}")
            else:
                logger.error(f"❌ {sensor_id}: Entity not found")
                all_good = False
        
        return all_good
    
    def test_text_sensors(self) -> bool:
        """Test text-based sensors."""
        logger.info("Testing text-based sensors...")
        all_good = True
        
        for sensor_id in self.text_sensors:
            state = self.get_entity_state(sensor_id)
            if state:
                value = state.get('state', 'unknown')
                if value == 'unavailable':
                    logger.error(f"❌ {sensor_id}: {value}")
                    all_good = False
                elif value in ['unknown', 'Unavailable', 'None']:
                    logger.warning(f"⚠️  {sensor_id}: {value}")
                else:
                    logger.info(f"✅ {sensor_id}: {value}")
            else:
                logger.warning(f"⚠️  {sensor_id}: Entity not found (may be disabled)")
        
        return all_good
    
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
            'unknown': 0
        }
        
        for entity in entities:
            state = entity.get('state', 'unknown')
            if state == 'unavailable':
                stats['unavailable'] += 1
            elif state in ['unknown', 'Unavailable', 'None']:
                stats['unknown'] += 1
            else:
                stats['available'] += 1
        
        return stats
    
    def run_tests(self) -> bool:
        """Run all tests."""
        logger.info("Starting LuxPower String Sensor Tests...")
        
        # Test string sensors
        string_ok = self.test_string_sensors()
        
        # Test text sensors
        text_ok = self.test_text_sensors()
        
        # Check overall entity availability
        logger.info("Checking overall entity availability...")
        stats = self.check_entity_availability()
        
        logger.info(f"Entity Statistics:")
        logger.info(f"  Total entities: {stats['total']}")
        logger.info(f"  Available: {stats['available']}")
        logger.info(f"  Unavailable: {stats['unavailable']}")
        logger.info(f"  Unknown: {stats['unknown']}")
        
        # Overall result
        all_tests_passed = string_ok and text_ok
        
        if all_tests_passed:
            logger.info("🎉 All string sensor tests passed!")
        else:
            logger.error("❌ Some string sensor tests failed!")
        
        return all_tests_passed

def main():
    """Main function."""
    # Configuration
    HASS_URL = os.getenv('HASS_URL', 'http://homeassistant.local:8123')
    HASS_TOKEN = os.getenv('HASS_TOKEN')
    
    if not HASS_TOKEN:
        logger.error("HASS_TOKEN environment variable not set.")
        logger.error("Please set it with: export HASS_TOKEN='your_long_lived_access_token'")
        return False
    
    # Create tester and run tests
    tester = StringSensorTester(HASS_URL, HASS_TOKEN)
    return tester.run_tests()

if __name__ == "__main__":
    success = main()
    exit(0 if success else 1)
