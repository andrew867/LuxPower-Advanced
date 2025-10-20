#!/usr/bin/env python3
"""
Test script to check model sensor status and register data.
"""

import requests
import json
import os
import sys

def test_model_sensor():
    """Test the model sensor status and register data."""
    
    # Get Home Assistant token from environment
    token = os.getenv('HASS_TOKEN')
    if not token:
        print("❌ Please set HASS_TOKEN environment variable")
        print("   export HASS_TOKEN='your_token_here'")
        return False
    
    # Home Assistant URL
    base_url = "http://localhost:8123"
    headers = {
        "Authorization": f"Bearer {token}",
        "Content-Type": "application/json"
    }
    
    try:
        # Get all entities
        response = requests.get(f"{base_url}/api/states", headers=headers)
        response.raise_for_status()
        entities = response.json()
        
        # Find LuxPower model sensor
        model_sensor = None
        for entity in entities:
            if entity.get('entity_id') == 'sensor.lux_inverter_model':
                model_sensor = entity
                break
        
        if not model_sensor:
            print("❌ Model sensor not found")
            return False
        
        print("🔍 Model Sensor Status:")
        print(f"   Entity ID: {model_sensor.get('entity_id')}")
        print(f"   State: {model_sensor.get('state')}")
        print(f"   Available: {model_sensor.get('attributes', {}).get('available', 'unknown')}")
        print(f"   Last Updated: {model_sensor.get('last_updated')}")
        
        # Check if it's still "pending"
        state = model_sensor.get('state', '')
        if state == 'Model detection pending':
            print("⚠️  Model sensor is still pending - needs entity recreation")
            return False
        elif state == 'Waiting for data...':
            print("⚠️  Model sensor is waiting for data - needs entity recreation")
            return False
        elif state in ['Unavailable', 'unknown']:
            print("❌ Model sensor is unavailable")
            return False
        else:
            print(f"✅ Model sensor working: {state}")
            return True
            
    except requests.exceptions.RequestException as e:
        print(f"❌ Error connecting to Home Assistant: {e}")
        return False
    except Exception as e:
        print(f"❌ Error: {e}")
        return False

if __name__ == "__main__":
    print("🧪 Testing LuxPower Model Sensor...")
    success = test_model_sensor()
    
    if not success:
        print("\n💡 Recommendation:")
        print("   1. Go to Settings → Devices & Services → LuxPower")
        print("   2. Click 'Recreate entity IDs'")
        print("   3. Wait 30-60 seconds")
        print("   4. Check if model sensor shows actual model")
    else:
        print("\n🎉 Model sensor is working correctly!")
