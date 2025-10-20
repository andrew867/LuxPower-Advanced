#!/usr/bin/env python3
"""
LuxPower Integration Restart Script

This script helps restart the LuxPower integration to clear cached
sensor configurations and apply the latest fixes.

Usage:
    python restart_luxpower_integration.py
"""

import os
import requests
import logging
import time
from typing import Dict, List, Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class LuxPowerRestarter:
    def __init__(self, hass_url: str, token: str):
        self.hass_url = hass_url.rstrip('/')
        self.token = token
        self.headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
    
    def get_integrations(self) -> List[Dict]:
        """Get all integrations."""
        try:
            response = requests.get(f"{self.hass_url}/api/config/config_entries", headers=self.headers)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to get integrations: {e}")
            return []
    
    def find_luxpower_integration(self, integrations: List[Dict]) -> Optional[Dict]:
        """Find the LuxPower integration."""
        for integration in integrations:
            if integration.get('domain') == 'luxpower':
                return integration
        return None
    
    def reload_integration(self, entry_id: str) -> bool:
        """Reload the LuxPower integration."""
        try:
            response = requests.post(
                f"{self.hass_url}/api/config/config_entries/entry/{entry_id}/reload",
                headers=self.headers
            )
            response.raise_for_status()
            return True
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to reload integration: {e}")
            return False
    
    def restart_home_assistant(self) -> bool:
        """Restart Home Assistant (requires supervisor)."""
        try:
            response = requests.post(
                f"{self.hass_url}/api/services/homeassistant/restart",
                headers=self.headers
            )
            response.raise_for_status()
            return True
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to restart Home Assistant: {e}")
            return False
    
    def check_integration_status(self, entry_id: str) -> bool:
        """Check if integration is working properly."""
        try:
            # Check if entities are available
            response = requests.get(f"{self.hass_url}/api/states", headers=self.headers)
            response.raise_for_status()
            states = response.json()
            
            luxpower_entities = [s for s in states if s.get('entity_id', '').startswith(('sensor.lux_', 'switch.lux_', 'number.lux_', 'binary_sensor.lux_', 'time.lux_', 'button.lux_'))]
            
            logger.info(f"Found {len(luxpower_entities)} LuxPower entities")
            
            # Check for problematic entities
            problematic = []
            for entity in luxpower_entities:
                state = entity.get('state', 'unknown')
                if state == 'unavailable':
                    problematic.append(entity.get('entity_id', 'unknown'))
            
            if problematic:
                logger.warning(f"Found {len(problematic)} unavailable entities: {problematic[:5]}...")
                return False
            else:
                logger.info("All LuxPower entities appear to be working")
                return True
                
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to check integration status: {e}")
            return False
    
    def run(self, restart_ha: bool = False) -> bool:
        """Run the restart process."""
        logger.info("Starting LuxPower integration restart...")
        
        # Get integrations
        integrations = self.get_integrations()
        if not integrations:
            logger.error("Could not retrieve integrations")
            return False
        
        # Find LuxPower integration
        luxpower = self.find_luxpower_integration(integrations)
        if not luxpower:
            logger.error("LuxPower integration not found")
            return False
        
        entry_id = luxpower.get('entry_id')
        logger.info(f"Found LuxPower integration: {entry_id}")
        
        # Reload integration
        logger.info("Reloading LuxPower integration...")
        if self.reload_integration(entry_id):
            logger.info("Integration reloaded successfully")
        else:
            logger.error("Failed to reload integration")
            return False
        
        # Wait a moment for reload to complete
        logger.info("Waiting for integration to reload...")
        time.sleep(5)
        
        # Check status
        if self.check_integration_status(entry_id):
            logger.info("✅ Integration is working properly")
        else:
            logger.warning("⚠️  Some entities may still be unavailable")
        
        # Optional: Restart Home Assistant
        if restart_ha:
            logger.info("Restarting Home Assistant...")
            if self.restart_home_assistant():
                logger.info("Home Assistant restart initiated")
            else:
                logger.warning("Failed to restart Home Assistant (may require supervisor)")
        
        logger.info("LuxPower integration restart completed!")
        return True

def main():
    """Main function."""
    import argparse
    
    parser = argparse.ArgumentParser(description='Restart LuxPower Integration')
    parser.add_argument('--restart-ha', action='store_true',
                       help='Also restart Home Assistant (requires supervisor)')
    parser.add_argument('--url', type=str, default='http://homeassistant.local:8123',
                       help='Home Assistant URL')
    
    args = parser.parse_args()
    
    # Get token from environment
    token = os.getenv('HASS_TOKEN')
    if not token:
        logger.error("HASS_TOKEN environment variable not set")
        logger.error("Set it with: export HASS_TOKEN='your_long_lived_access_token'")
        return False
    
    # Create restarter and run
    restarter = LuxPowerRestarter(args.url, token)
    return restarter.run(restart_ha=args.restart_ha)

if __name__ == "__main__":
    success = main()
    exit(0 if success else 1)
