#!/usr/bin/env python3
"""
LuxPower Device Info Test Script

This script helps verify that the enhanced device information is properly
displayed in Home Assistant's device configuration pages.

Usage:
    python test_device_info.py
"""

import os
import requests
import logging
from typing import Dict, List, Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class DeviceInfoTester:
    def __init__(self, hass_url: str, token: str):
        self.hass_url = hass_url.rstrip('/')
        self.token = token
        self.headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
    
    def get_devices(self) -> List[Dict]:
        """Get all devices from Home Assistant."""
        try:
            response = requests.get(f"{self.hass_url}/api/config/device_registry/list", headers=self.headers)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get devices: {e}")
            return []
    
    def find_luxpower_devices(self, devices: List[Dict]) -> List[Dict]:
        """Find LuxPower devices."""
        luxpower_devices = []
        for device in devices:
            if device.get('manufacturer') == 'LuxPower':
                luxpower_devices.append(device)
        return luxpower_devices
    
    def get_device_entities(self, device_id: str) -> List[Dict]:
        """Get entities for a specific device."""
        try:
            response = requests.get(f"{self.hass_url}/api/states", headers=self.headers)
            response.raise_for_status()
            states = response.json()
            
            # Filter entities for this device
            device_entities = []
            for state in states:
                entity_id = state.get('entity_id', '')
                if entity_id.startswith(('sensor.lux_', 'switch.lux_', 'number.lux_', 'binary_sensor.lux_', 'time.lux_', 'button.lux_')):
                    # Check if this entity belongs to the device
                    # This is a simplified check - in reality, you'd need to check the device_id
                    device_entities.append(state)
            
            return device_entities
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get device entities: {e}")
            return []
    
    def test_device_info(self) -> bool:
        """Test device information display."""
        logger.info("Testing LuxPower device information...")
        
        # Get all devices
        devices = self.get_devices()
        if not devices:
            logger.error("No devices found")
            return False
        
        # Find LuxPower devices
        luxpower_devices = self.find_luxpower_devices(devices)
        if not luxpower_devices:
            logger.error("No LuxPower devices found")
            return False
        
        logger.info(f"Found {len(luxpower_devices)} LuxPower device(s)")
        
        # Test each device
        all_good = True
        for device in luxpower_devices:
            device_id = device.get('id', 'unknown')
            name = device.get('name', 'Unknown')
            manufacturer = device.get('manufacturer', 'Unknown')
            model = device.get('model', 'Unknown')
            sw_version = device.get('sw_version', 'Unknown')
            hw_version = device.get('hw_version', 'Unknown')
            serial_number = device.get('serial_number', 'Unknown')
            configuration_url = device.get('configuration_url', 'None')
            
            logger.info(f"Device: {name}")
            logger.info(f"  ID: {device_id}")
            logger.info(f"  Manufacturer: {manufacturer}")
            logger.info(f"  Model: {model}")
            logger.info(f"  Software Version: {sw_version}")
            logger.info(f"  Hardware Version: {hw_version}")
            logger.info(f"  Serial Number: {serial_number}")
            logger.info(f"  Configuration URL: {configuration_url}")
            
            # Check if device info is comprehensive
            if manufacturer != 'LuxPower':
                logger.warning(f"⚠️  Manufacturer not set to 'LuxPower': {manufacturer}")
                all_good = False
            
            if model == 'Unknown' or model == 'LuxPower Inverter':
                logger.warning(f"⚠️  Model not properly detected: {model}")
                all_good = False
            
            if sw_version == 'Unknown' or sw_version == '1.0.0':
                logger.warning(f"⚠️  Software version not properly detected: {sw_version}")
                all_good = False
            
            if hw_version == 'Unknown':
                logger.warning(f"⚠️  Hardware version not detected: {hw_version}")
                all_good = False
            
            if serial_number == 'Unknown':
                logger.warning(f"⚠️  Serial number not detected: {serial_number}")
                all_good = False
            
            if configuration_url == 'None':
                logger.warning(f"⚠️  Configuration URL not set: {configuration_url}")
                all_good = False
            
            # Get device entities
            entities = self.get_device_entities(device_id)
            logger.info(f"  Entities: {len(entities)}")
            
            logger.info("")
        
        return all_good
    
    def run_tests(self) -> bool:
        """Run all device info tests."""
        logger.info("Starting LuxPower Device Info Tests...")
        
        success = self.test_device_info()
        
        if success:
            logger.info("🎉 All device info tests passed!")
            logger.info("Your LuxPower devices should now show comprehensive information in Home Assistant!")
        else:
            logger.warning("⚠️  Some device info tests failed!")
            logger.info("You may need to restart the LuxPower integration to see the enhanced device info.")
        
        return success

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
    tester = DeviceInfoTester(HASS_URL, HASS_TOKEN)
    return tester.run_tests()

if __name__ == "__main__":
    success = main()
    exit(0 if success else 1)
