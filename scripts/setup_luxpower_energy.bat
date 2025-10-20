@echo off
REM LuxPower Energy Dashboard Setup Script for Windows
REM This script automatically configures LuxPower entities and energy dashboard

setlocal enabledelayedexpansion

REM Configuration
if not defined HASS_URL set HASS_URL=http://homeassistant.local:8123
if not defined HASS_TOKEN (
    echo [ERROR] HASS_TOKEN environment variable not set
    echo Please set it with: set HASS_TOKEN=your_long_lived_access_token
    echo You can create a token in Home Assistant: Profile -^> Long-lived access tokens
    exit /b 1
)

REM Check if Python is available
python --version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Python is required but not installed
    exit /b 1
)

REM Check if requests module is available
python -c "import requests" >nul 2>&1
if errorlevel 1 (
    echo [WARNING] requests module not found, installing...
    pip install requests
)

echo [INFO] Starting LuxPower Energy Dashboard setup...

REM Step 1: Recreate entities with model-based enablement
echo [INFO] Step 1: Recreating entities with model-based enablement...
python luxpower_entity_manager.py --recreate-entities --url "%HASS_URL%"
if errorlevel 1 (
    echo [ERROR] Failed to recreate entities
    exit /b 1
)
echo [SUCCESS] Entities recreated successfully

REM Step 2: Configure energy dashboard
echo [INFO] Step 2: Configuring energy dashboard...
python luxpower_entity_manager.py --configure-energy --url "%HASS_URL%"
if errorlevel 1 (
    echo [ERROR] Failed to configure energy dashboard
    exit /b 1
)
echo [SUCCESS] Energy dashboard configured successfully

REM Step 3: Run basic energy dashboard setup
echo [INFO] Step 3: Running basic energy dashboard setup...
python setup_energy_dashboard.py
if errorlevel 1 (
    echo [WARNING] Basic energy dashboard setup failed (this is optional)
) else (
    echo [SUCCESS] Basic energy dashboard setup completed
)

echo [SUCCESS] LuxPower Energy Dashboard setup completed!
echo [INFO] You can now view your energy dashboard in Home Assistant
echo [INFO] Dashboard URL: %HASS_URL%/energy

echo.
echo [INFO] Next steps:
echo 1. Go to %HASS_URL%/energy to view your energy dashboard
echo 2. Check that all energy entities are properly configured
echo 3. Verify that solar, battery, and grid entities are showing data
echo 4. If needed, manually adjust entity selections in the energy dashboard

pause
