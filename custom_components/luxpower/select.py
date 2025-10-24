"""
LuxPower select platform for Home Assistant.

This module provides select entities for configuring LuxPower inverter settings
including AC Charge Type, Output Priority, and Work Mode.
"""

import logging
from typing import Any, Dict, List, Optional

from homeassistant.components.select import SelectEntity
from homeassistant.config_entries import ConfigEntry
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import EntityCategory

from .const import DOMAIN
from .helpers import get_device_group_info, get_entity_device_group

_LOGGER = logging.getLogger(__name__)


class LuxPowerSelectEntity(SelectEntity):
    """
    Base class for LuxPower select entities.

    Provides common functionality for select entities that control
    inverter settings through register writes.
    """

    def __init__(
        self,
        hass: HomeAssistant,
        dongle: str,
        entity_definition: Dict[str, Any],
    ) -> None:
        """Initialize the select entity."""
        self.hass = hass
        self.dongle = dongle
        self._entity_definition = entity_definition
        self._attr_name = entity_definition.get("name", "Unknown Select")
        self._attr_unique_id = f"{dongle}_{entity_definition.get('unique', 'unknown_select')}"

        # Get device group for proper organization
        device_group = get_entity_device_group(entity_definition, hass)
        device_info = get_device_group_info(hass, dongle, device_group)

        self._attr_device_info = device_info

        # Set entity category if specified
        if entity_definition.get("category") == "config":
            self._attr_entity_category = EntityCategory.CONFIG

        # Register and bit information for control
        self._register_address = entity_definition.get("register_address", 0)
        self._bit_mask = entity_definition.get("bit_mask", 0x03)  # Default 2-bit mask
        self._bit_position = entity_definition.get("bit_position", 0)

        # Initialize current option to first option
        self._attr_current_option = entity_definition.get("options", ["Unknown"])[0]

    async def async_added_to_hass(self) -> None:
        """Called when entity is added to Home Assistant."""
        await super().async_added_to_hass()
        # Set up event listener for register updates
        await self._async_setup_event_listener()

    async def _async_setup_event_listener(self) -> None:
        """Set up event listener for register updates."""
        try:
            # Determine which bank contains our register and listen to that bank event
            bank_event = self._get_bank_event_for_register()
            if bank_event:
                self.hass.bus.async_listen(bank_event, self._async_handle_register_update)
                _LOGGER.debug(f"Set up event listener for {self._attr_name} on {bank_event}")
            else:
                _LOGGER.warning(f"Could not determine bank event for register {self._register_address}")
        except Exception as err:
            _LOGGER.error(f"Error setting up event listener for {self._attr_name}: {err}")

    def _get_bank_event_for_register(self) -> Optional[str]:
        """Determine which bank event contains the register."""
        # Register ranges for each bank (based on LXPPacket.py)
        bank_ranges = {
            0: (0, 39),    # bank0: registers 0-39
            1: (40, 79),   # bank1: registers 40-79
            2: (80, 119),  # bank2: registers 80-119
            3: (120, 159), # bank3: registers 120-159
            4: (160, 199), # bank4: registers 160-199
            5: (200, 239), # bank5: registers 200-239
            6: (240, 253), # bank6: registers 240-253
        }

        for bank_num, (start_reg, end_reg) in bank_ranges.items():
            if start_reg <= self._register_address <= end_reg:
                # Return the actual event format used by the client
                return f"{DOMAIN}_{self.dongle}_data_receive_bank{bank_num}_event"

        return None

    async def _async_handle_register_update(self, event) -> None:
        """Handle register update events."""
        try:
            event_data = event.data
            if not isinstance(event_data, dict):
                return

            # Check if this event contains our register
            data = event_data.get("data", {})
            if not isinstance(data, dict):
                return

            # Try to extract our register value from the data
            # Since the data contains register values, check if our register is in there
            if self._register_address in data:
                register_value = data[self._register_address]

                # Update current option based on register value
                if self._attr_name == "AC Charge Mode":
                    # AC Charge Mode: map register values back to options
                    # Register values: 0=Disabled, 2=Voltage-Based, 4=Time-Based, 6=Both
                    option_index = register_value // 2 if register_value in [0, 2, 4, 6] else 0
                else:
                    # Default behavior for other select entities
                    bit_value = (register_value >> self._bit_position) & self._bit_mask
                    option_index = bit_value

                # Update current option if valid
                if 0 <= option_index < len(self.options):
                    self._attr_current_option = self.options[option_index]
                    _LOGGER.debug(f"Updated {self._attr_name} to {self._attr_current_option} from register {self._register_address}={register_value}")

            # Always trigger a state update to ensure UI is refreshed
            self.async_schedule_update_ha_state()

        except Exception as err:
            _LOGGER.error(f"Error handling register update for {self._attr_name}: {err}")

    @property
    def options(self) -> List[str]:
        """Return the list of available options."""
        return self._entity_definition.get("options", ["Unknown"])

    async def async_select_option(self, option: str) -> None:
        """Handle option selection."""
        try:
            # Find the option index and map to register values
            option_index = self.options.index(option)
            if option_index == -1:
                _LOGGER.error(f"Invalid option selected: {option}")
                return

            # Map option indices to register values for AC Charge Mode
            if self._attr_name == "AC Charge Mode":
                # AC Charge Mode: Disabled=0, Voltage-Based=2, Time-Based=4, Both=6
                value = option_index * 2
            else:
                # Default behavior for other select entities
                value = option_index << self._bit_position

            # Get the client and write the register
            client = self.hass.data[DOMAIN].get(self.dongle, {}).get("client")
            if client:
                await client.write_register(self._register_address, value)
                self._attr_current_option = option
                self.async_write_ha_state()
                _LOGGER.debug(f"Set {self._attr_name} to {option}")
            else:
                _LOGGER.error(f"No client available for {self.dongle}")

        except Exception as err:
            _LOGGER.error(f"Error setting {self._attr_name} to {option}: {err}")


class LuxPowerACChargeTypeSelectEntity(LuxPowerSelectEntity):
    """
    Select entity for AC Charge Type configuration.

    Controls register 20-21 bits for AC charging behavior:
    - Stop, Time, SOC, Both
    """

    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        """Initialize AC Charge Type selector."""
        entity_definition = {
            "name": "AC Charge Type",
            "unique": f"{dongle}_ac_charge_type",
            "options": ["Stop", "Time", "SOC", "Both"],
            "register_address": 20,
            "bit_position": 0,
            "bit_mask": 0x03,
            "category": "config",
            "attribute": "ac_charge_type",
        }

        super().__init__(hass, dongle, entity_definition)


class LuxPowerOutputPrioritySelectEntity(LuxPowerSelectEntity):
    """
    Select entity for Output Priority configuration.

    Controls register 21 bits for output priority:
    - Battery First, PV First, Grid First
    """

    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        """Initialize Output Priority selector."""
        entity_definition = {
            "name": "Output Priority",
            "unique": f"{dongle}_output_priority",
            "options": ["Battery First", "PV First", "Grid First"],
            "register_address": 21,
            "bit_position": 0,
            "bit_mask": 0x03,
            "category": "config",
            "attribute": "output_priority",
        }

        super().__init__(hass, dongle, entity_definition)


class LuxPowerWorkModeSelectEntity(LuxPowerSelectEntity):
    """
    Select entity for Work Mode configuration.

    Controls register 110 bits for work mode:
    - Self-Use, Feed-In Priority, Backup, Off-Grid
    """

    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        """Initialize Work Mode selector."""
        entity_definition = {
            "name": "Work Mode",
            "unique": f"{dongle}_work_mode",
            "options": ["Self-Use", "Feed-In Priority", "Backup", "Off-Grid"],
            "register_address": 110,
            "bit_position": 0,
            "bit_mask": 0x03,
            "category": "config",
            "attribute": "work_mode",
        }

        super().__init__(hass, dongle, entity_definition)


class LuxPowerACChargeModeSelectEntity(LuxPowerSelectEntity):
    """
    Select entity for AC Charge Mode configuration.

    Controls register 120 bits 1-2 for AC charging behavior:
    - Disabled, Voltage-Based, Time-Based, Both
    """

    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        """Initialize AC Charge Mode selector."""
        entity_definition = {
            "name": "AC Charge Mode",
            "unique": f"{dongle}_ac_charge_mode",
            "options": ["Disabled", "Voltage-Based", "Time-Based", "Both"],
            "register_address": 120,
            "bit_position": 1,
            "bit_mask": 0x06,  # Bits 1 and 2 (values 2, 4, 6)
            "category": "config",
            "attribute": "ac_charge_mode",
        }

        super().__init__(hass, dongle, entity_definition)


class LuxPowerCTSampleRatioSelectEntity(LuxPowerSelectEntity):
    """CT Sample Ratio select entity for 12K models (Hold 110)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} CT Sample Ratio",
            "unique": "lux_ct_sample_ratio",
            "register_address": 110,
            "category": "config",
            "icon": "mdi:current-ac",
            "enabled": False,  # 12K models only
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "1/1000",
            "1/3000", 
            "1/2000",
            "1/4000",
            "1/6000"
        ]


class LuxPowerACChargeType12KSelectEntity(LuxPowerSelectEntity):
    """AC Charge Type select entity for 12K models (Hold 120)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} AC Charge Type (12K)",
            "unique": "lux_ac_charge_type_12k",
            "register_address": 120,
            "category": "config",
            "icon": "mdi:car-battery",
            "enabled": False,  # 12K models only
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "According to time",
            "According to SOC/Volt",
            "According to time with SOC/Volt"
        ]


class LuxPowerDryContactorMultiplexSelectEntity(LuxPowerSelectEntity):
    """Dry Contactor Multiplex select entity (Hold 233)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} Dry Contactor Multiplex",
            "unique": "lux_dry_contactor_multiplex",
            "register_address": 233,
            "category": "config",
            "icon": "mdi:connection",
            "enabled": False,
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "Null",
            "RSD",
            "Dark Start",
            "Smart Load",
            "Non-critical Load"
        ]


class LuxPowerSystemTypeSelectEntity(LuxPowerSelectEntity):
    """System Type select entity (Hold 112)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} System Type",
            "unique": "lux_system_type",
            "register_address": 112,
            "category": "config",
            "icon": "mdi:settings",
            "enabled": False,
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "Single Unit",
            "Parallel Master",
            "Parallel Slave",
            "Parallel Auto",
            "Parallel Manual"
        ]


class LuxPowerLineModeSelectEntity(LuxPowerSelectEntity):
    """Line Mode select entity (Hold 146)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} Line Mode",
            "unique": "lux_line_mode",
            "register_address": 146,
            "category": "config",
            "icon": "mdi:transmission-tower",
            "enabled": False,
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "APL (Automatic Power Line)",
            "UPS (Uninterruptible Power Supply)",
            "GEN (Generator)"
        ]


class LuxPowerGridRegulationSelectEntity(LuxPowerSelectEntity):
    """Grid Regulation select entity (Hold 203)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} Grid Regulation",
            "unique": "lux_grid_regulation",
            "register_address": 203,
            "category": "config",
            "icon": "mdi:shield-check",
            "enabled": False,
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "VDE-AR-N 4105 (Germany)",
            "VDE-AR-N 4110 (Germany)",
            "VDE-AR-N 4105 (Austria)",
            "VDE-AR-N 4110 (Austria)",
            "VDE-AR-N 4105 (Switzerland)",
            "VDE-AR-N 4110 (Switzerland)",
            "VDE-AR-N 4105 (Netherlands)",
            "VDE-AR-N 4110 (Netherlands)",
            "VDE-AR-N 4105 (Belgium)",
            "VDE-AR-N 4110 (Belgium)",
            "VDE-AR-N 4105 (Luxembourg)",
            "VDE-AR-N 4110 (Luxembourg)",
            "VDE-AR-N 4105 (Denmark)",
            "VDE-AR-N 4110 (Denmark)",
            "VDE-AR-N 4105 (Sweden)",
            "VDE-AR-N 4110 (Sweden)",
            "VDE-AR-N 4105 (Norway)",
            "VDE-AR-N 4110 (Norway)",
            "VDE-AR-N 4105 (Finland)",
            "VDE-AR-N 4110 (Finland)",
            "VDE-AR-N 4105 (Iceland)",
            "VDE-AR-N 4110 (Iceland)",
            "VDE-AR-N 4105 (Ireland)",
            "VDE-AR-N 4110 (Ireland)",
            "VDE-AR-N 4105 (United Kingdom)",
            "VDE-AR-N 4110 (United Kingdom)",
            "VDE-AR-N 4105 (France)",
            "VDE-AR-N 4110 (France)",
            "VDE-AR-N 4105 (Italy)",
            "VDE-AR-N 4110 (Italy)",
            "VDE-AR-N 4105 (Spain)",
            "VDE-AR-N 4110 (Spain)"
        ]


class LuxPowerGridTypeSelectEntity(LuxPowerSelectEntity):
    """Grid Type select entity (Hold 205)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} Grid Type",
            "unique": "lux_grid_type",
            "register_address": 205,
            "category": "config",
            "icon": "mdi:transmission-tower",
            "enabled": False,
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "Single Phase (1P)",
            "Split Phase (2P)",
            "Three Phase (3P)",
            "Three Phase + N (3P+N)",
            "Three Phase + N + PE (3P+N+PE)"
        ]


class LuxPowerPVInputModelSelectEntity(LuxPowerSelectEntity):
    """PV Input Model select entity (Hold 20)."""
    
    def __init__(self, hass: HomeAssistant, dongle: str) -> None:
        entity_definition = {
            "name": f"Lux {dongle[-2:] if dongle else ''} PV Input Model",
            "unique": "lux_pv_input_model",
            "register_address": 20,
            "category": "config",
            "icon": "mdi:solar-power",
            "enabled": False,
        }
        super().__init__(hass, dongle, entity_definition)
    
    @property
    def options(self) -> List[str]:
        """Return the available options."""
        return [
            "Standard PV",
            "High Voltage PV",
            "Low Voltage PV",
            "Flexible PV",
            "Custom PV"
        ]


def get_select_entities(hass: HomeAssistant, dongle: str) -> List[LuxPowerSelectEntity]:
    """
    Get all select entities for a given dongle.

    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number

    Returns:
        List of select entities for the dongle
    """
    return [
        LuxPowerACChargeTypeSelectEntity(hass, dongle),
        LuxPowerOutputPrioritySelectEntity(hass, dongle),
        LuxPowerWorkModeSelectEntity(hass, dongle),
        LuxPowerACChargeModeSelectEntity(hass, dongle),
        # NEW 2025.03.05 Protocol: Enhanced Select Entities
        LuxPowerCTSampleRatioSelectEntity(hass, dongle),  # Hold 110 - CT Sample Ratio (12K models)
        LuxPowerACChargeType12KSelectEntity(hass, dongle),  # Hold 120 - AC Charge Type (12K models)
        LuxPowerDryContactorMultiplexSelectEntity(hass, dongle),  # Hold 233 - Dry Contactor Multiplex
        LuxPowerSystemTypeSelectEntity(hass, dongle),  # Hold 112 - System Type
        LuxPowerLineModeSelectEntity(hass, dongle),  # Hold 146 - Line Mode
        LuxPowerGridRegulationSelectEntity(hass, dongle),  # Hold 203 - Grid Regulation
        LuxPowerGridTypeSelectEntity(hass, dongle),  # Hold 205 - Grid Type
        LuxPowerPVInputModelSelectEntity(hass, dongle),  # Hold 20 - PV Input Model
    ]


async def async_setup_entry(
    hass: HomeAssistant,
    config_entry: ConfigEntry,
    async_add_entities: callable,
) -> None:
    """
    Set up LuxPower select entities.

    Args:
        hass: Home Assistant instance
        config_entry: Configuration entry
        async_add_entities: Callback to add entities
    """
    dongle = config_entry.data.get("lux_dongle_serial")

    if not dongle:
        _LOGGER.error("No dongle serial found in config entry")
        return

    entities = get_select_entities(hass, dongle)
    async_add_entities(entities, update_before_add=True)

    _LOGGER.info(f"Added {len(entities)} select entities for dongle {dongle}")
