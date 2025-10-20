#!/bin/bash
# LuxPower Energy Dashboard Setup Script
# This script automatically configures LuxPower entities and energy dashboard

set -e

# Configuration
HASS_URL="${HASS_URL:-http://homeassistant.local:8123}"
HASS_TOKEN="${HASS_TOKEN}"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Logging functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if HASS_TOKEN is set
if [ -z "$HASS_TOKEN" ]; then
    log_error "HASS_TOKEN environment variable not set"
    log_info "Please set it with: export HASS_TOKEN='your_long_lived_access_token'"
    log_info "You can create a token in Home Assistant: Profile -> Long-lived access tokens"
    exit 1
fi

# Check if Python is available
if ! command -v python3 &> /dev/null; then
    log_error "Python 3 is required but not installed"
    exit 1
fi

# Check if requests module is available
if ! python3 -c "import requests" 2>/dev/null; then
    log_warning "requests module not found, installing..."
    pip3 install requests
fi

log_info "Starting LuxPower Energy Dashboard setup..."

# Step 1: Recreate entities with model-based enablement
log_info "Step 1: Recreating entities with model-based enablement..."
if python3 luxpower_entity_manager.py --recreate-entities --url "$HASS_URL"; then
    log_success "Entities recreated successfully"
else
    log_error "Failed to recreate entities"
    exit 1
fi

# Step 2: Configure energy dashboard
log_info "Step 2: Configuring energy dashboard..."
if python3 luxpower_entity_manager.py --configure-energy --url "$HASS_URL"; then
    log_success "Energy dashboard configured successfully"
else
    log_error "Failed to configure energy dashboard"
    exit 1
fi

# Step 3: Run basic energy dashboard setup
log_info "Step 3: Running basic energy dashboard setup..."
if python3 setup_energy_dashboard.py; then
    log_success "Basic energy dashboard setup completed"
else
    log_warning "Basic energy dashboard setup failed (this is optional)"
fi

log_success "LuxPower Energy Dashboard setup completed!"
log_info "You can now view your energy dashboard in Home Assistant"
log_info "Dashboard URL: $HASS_URL/energy"

# Display next steps
echo ""
log_info "Next steps:"
echo "1. Go to $HASS_URL/energy to view your energy dashboard"
echo "2. Check that all energy entities are properly configured"
echo "3. Verify that solar, battery, and grid entities are showing data"
echo "4. If needed, manually adjust entity selections in the energy dashboard"
