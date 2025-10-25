"""
Service connector for LuxPower integration.

This module provides the ServiceHelper class for handling LuxPower service calls
and managing communication with LuxPower inverters through their dongles.
"""

import asyncio
import logging

from homeassistant.core import HomeAssistant

from .lxp.client import LuxPowerClient
from .const import DOMAIN

_LOGGER = logging.getLogger(__name__)


async def refreshALLPlatforms(hass: HomeAssistant, dongle):
    """
    Refresh all LuxPower platform entities after a delay.

    This function waits 20 seconds then triggers a refresh of all register banks
    and holding registers to ensure all entities have the latest data.

    Args:
        hass: Home Assistant instance
        dongle: Dongle serial number for the inverter
    """
    await asyncio.sleep(5)  # Reduced from 20s to 5s for faster startup
    # fmt: skip
    await hass.services.async_call(
        DOMAIN, "luxpower_refresh_holdings", {"dongle": dongle}, blocking=True
    )
    await asyncio.sleep(10)
    # fmt: skip

    # Always use 7 banks to get all data
    bank_count = 7

    await hass.services.async_call(
        DOMAIN,
        "luxpower_refresh_registers",
        {"dongle": dongle, "bank_count": bank_count},
        blocking=True,
    )


class ServiceHelper:
    """
    Service helper for LuxPower integration.
    
    This class provides methods for handling LuxPower service calls
    and managing communication with LuxPower inverters.
    """
    
    def __init__(self, hass: HomeAssistant) -> None:
        """
        Initialize ServiceHelper.
        
        Args:
            hass: Home Assistant instance
        """
        self.hass = hass

    def _lux_client(self, dongle: str) -> LuxPowerClient:
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                return entry_data.get("client")

        raise Exception(f"Couldn't find luxpower client dongle {dongle}")

    async def service_reconnect(self, dongle):
        """Reconnect to the LuxPower inverter."""
        try:
            luxpower_client = self._lux_client(dongle)
            await luxpower_client.reconnect()
            await asyncio.sleep(1)
            _LOGGER.debug("service_reconnect done")
        except Exception as err:
            _LOGGER.error("Error in service_reconnect: %s", err)
            raise

    async def service_restart(self, dongle):
        """Restart the LuxPower inverter connection."""
        try:
            luxpower_client = self._lux_client(dongle)
            await luxpower_client.restart()
            await asyncio.sleep(1)
            _LOGGER.warning("service_restart done")
        except Exception as err:
            _LOGGER.error("Error in service_restart: %s", err)
            raise

    async def service_reset_settings(self, dongle):
        """Reset all LuxPower inverter settings to defaults."""
        try:
            luxpower_client = self._lux_client(dongle)
            await luxpower_client.reset_all_settings()
            await asyncio.sleep(1)
            _LOGGER.warning("service_reset_settings done")
        except Exception as err:
            _LOGGER.error("Error in service_reset_settings: %s", err)
            raise

    async def service_synctime(self, dongle, do_set_time: bool):
        """Synchronize time with the LuxPower inverter."""
        try:
            luxpower_client = self._lux_client(dongle)
            await luxpower_client.synctime(do_set_time)
            _LOGGER.info("service_synctime done")
        except Exception as err:
            _LOGGER.error("Error in service_synctime: %s", err)
            raise

    async def service_afci_alarm_clear(self, dongle):
        """Clear AFCI alarm by setting the alarm clear bit."""
        try:
            luxpower_client = self._lux_client(dongle)
            # Set bit 2 of register 179 to clear AFCI alarm
            await luxpower_client.write_register(179, LXPPacket.AFCI_ALARM_CLEAR, LXPPacket.AFCI_ALARM_CLEAR)
            _LOGGER.info("AFCI alarm clear sent")
        except Exception as err:
            _LOGGER.error("Error in service_afci_alarm_clear: %s", err)
            raise

    async def service_start_charging(
        self, dongle, duration_minutes: int = 180, charge_slot: int = 1
    ):
        """Start the battery charging process using specified time slot.

        Args:
            dongle: The dongle serial
            duration_minutes: The charging duration in minutes
            charge_slot: Which charging slot to use (1, 2, or 3)
        """
        try:
            import datetime
            from homeassistant.util import dt as dt_util

            # Validate charge_slot parameter
            if charge_slot not in [1, 2, 3]:
                _LOGGER.error(f"Invalid charge_slot: {charge_slot}. Must be 1, 2, or 3.")
                return False

            # Validate duration_minutes parameter
            if duration_minutes < 1 or duration_minutes > 1440:  # 1 minute to 24 hours
                _LOGGER.error(f"Invalid duration_minutes: {duration_minutes}. Must be between 1 and 1440 minutes.")
                return False

            luxpower_client = self._lux_client(dongle)

            # Get current time and calculate end time
            now = dt_util.now()
            end_time = now + datetime.timedelta(minutes=duration_minutes)

            _LOGGER.info(
                f"Starting charging in slot {charge_slot} for {duration_minutes} minutes ({duration_minutes/60:.1f} hours) until {end_time}"
            )

            # Enable AC charging first
            current_reg21 = await luxpower_client.read(21)
            if current_reg21 is not None:
                from .LXPPacket import LXPPacket, prepare_binary_value

                new_value = prepare_binary_value(
                    current_reg21, LXPPacket.AC_CHARGE_ENABLE, True
                )
                await luxpower_client.write(21, new_value)
                await asyncio.sleep(1)

                # Calculate register addresses for the selected slot
                # Slot 1: registers 68-69, Slot 2: registers 70-71, Slot 3: registers 72-73
                start_register = 68 + ((charge_slot - 1) * 2)
                end_register = start_register + 1

                # Encode times (hour + minute * 256) with bounds checking
                start_hour = now.hour
                start_minute = now.minute
                
                # Validate hour and minute ranges
                if not (0 <= start_hour <= 23):
                    _LOGGER.error(f"Invalid start hour: {start_hour}")
                    return False
                if not (0 <= start_minute <= 59):
                    _LOGGER.error(f"Invalid start minute: {start_minute}")
                    return False
                    
                start_value = start_hour + (start_minute << 8)

                end_hour = end_time.hour
                end_minute = end_time.minute
                
                # Validate hour and minute ranges
                if not (0 <= end_hour <= 23):
                    _LOGGER.error(f"Invalid end hour: {end_hour}")
                    return False
                if not (0 <= end_minute <= 59):
                    _LOGGER.error(f"Invalid end minute: {end_minute}")
                    return False
                    
                end_value = end_hour + (end_minute << 8)

                # Set charging times for the selected slot
                await luxpower_client.write(start_register, start_value)
                await asyncio.sleep(1)
                await luxpower_client.write(end_register, end_value)
                await asyncio.sleep(1)

                # Refresh registers to ensure changes are applied
                # Get configured bank count from integration data
                bank_count = 7  # Always use 7 banks for complete data
                await self.service_refresh_data_registers(dongle=dongle, bank_count=bank_count)

                _LOGGER.info(
                    f"Charging slot {charge_slot} started successfully. Will run from {start_hour:02d}:{start_minute:02d} to {end_hour:02d}:{end_minute:02d}"
                )
                return True
            else:
                _LOGGER.error("Could not read current register 21 value")
                return False
        except Exception as e:
            _LOGGER.error(f"Error starting charging: {e}")
            return False

    async def service_stop_charging(self, dongle, charge_slot: int = 1):
        """Stop the battery charging process for specified time slot.

        Args:
            dongle: The dongle serial
            charge_slot: Which charging slot to stop (1, 2, or 3)
        """
        try:
            # Validate charge_slot parameter
            if charge_slot not in [1, 2, 3]:
                _LOGGER.error(f"Invalid charge_slot: {charge_slot}. Must be 1, 2, or 3.")
                return False

            luxpower_client = self._lux_client(dongle)
            # Calculate register addresses for the selected slot
            # Slot 1: registers 68-69, Slot 2: registers 70-71, Slot 3: registers 72-73
            start_register = 68 + ((charge_slot - 1) * 2)
            end_register = start_register + 1

            # Set the selected slot times to 0 (effectively disabling the schedule)
            await luxpower_client.write(start_register, 0)  # Start time
            await asyncio.sleep(1)
            await luxpower_client.write(end_register, 0)  # End time
            await asyncio.sleep(1)

            # Check if all charging slots are now disabled
            slot1_start = await luxpower_client.read(68)
            slot2_start = await luxpower_client.read(70)
            slot3_start = await luxpower_client.read(72)

            await asyncio.sleep(1)

            # If all slots are disabled (all start times are 0), disable AC charging
            if slot1_start == 0 and slot2_start == 0 and slot3_start == 0:
                current_reg21 = await luxpower_client.read(21)
                if current_reg21 is not None:
                    from .LXPPacket import LXPPacket, prepare_binary_value

                    new_value = prepare_binary_value(
                        current_reg21, LXPPacket.AC_CHARGE_ENABLE, False
                    )
                    await luxpower_client.write(21, new_value)
                    await asyncio.sleep(1)
                    _LOGGER.info("All charging slots disabled - AC charging turned off")

            # Refresh registers to ensure changes are applied
            # Get configured bank count from integration data
            bank_count = 7  # Always use 7 banks for complete data
            await self.service_refresh_data_registers(dongle=dongle, bank_count=bank_count)

            _LOGGER.info(f"Charging slot {charge_slot} stopped successfully")
            return True

        except Exception as e:
            _LOGGER.error(f"Error stopping charging: {e}")
            return False

    async def service_refresh_data_registers(self, dongle, bank_count):
        _LOGGER.info(f"service_refresh_data_registers start - Count: {bank_count}")
        try:
            luxpower_client = self._lux_client(dongle)
            await luxpower_client.do_refresh_data_registers(bank_count)
        except Exception as e:
            _LOGGER.error(f"Error refreshing data registers: {e}")
            raise

        # await luxpower_client.inverter_is_reachable()
        # if luxpower_client._reachable:
        #    for address_bank in range(0, bank_count):
        #        _LOGGER.info("service_refresh_data_registers for address_bank: %s", address_bank)
        #        await luxpower_client.request_data_bank(address_bank)
        #        await asyncio.sleep(1)
        # else:
        #    _LOGGER.info("Inverter Is Not Reachable - Attempting Reconnect")
        #    await luxpower_client.reconnect()
        #    await asyncio.sleep(1)

        _LOGGER.debug("service_refresh_data_registers done")

    async def service_refresh_hold_registers(self, dongle):
        _LOGGER.debug("service_refresh_hold_registers start")
        try:
            luxpower_client = self._lux_client(dongle)
            await luxpower_client.do_refresh_hold_registers()
        except Exception as e:
            _LOGGER.error(f"Error refreshing hold registers: {e}")
            raise

        # luxpower_client._warn_registers = True
        # await asyncio.sleep(5)
        # for address_bank in range(0, 5):
        #    _LOGGER.debug("service_refresh_hold_registers for address_bank: %s", address_bank)
        #    await luxpower_client.request_hold_bank(address_bank)
        #    await asyncio.sleep(2)
        # if 1 == 1:
        #    # Request registers 200-239
        #    _LOGGER.debug("service_holding_register for EXTENDED address_bank: %s", 5)
        #    self._warn_registers = True
        #    await luxpower_client.request_hold_bank(5)
        #    await asyncio.sleep(2)
        # if 1 == 0:
        #    # Request registers 560-599
        #    _LOGGER.debug("service_refresh_hold_registers for HIGH EXTENDED address_bank: %s", 6)
        #    self._warn_registers = True
        #    await luxpower_client.request_hold_bank(6)
        #    await asyncio.sleep(2)
        # luxpower_client._warn_registers = False

        _LOGGER.debug("service_refresh_hold_registers finish")

    async def service_refresh_data_register_bank(self, dongle, address_bank):
        try:
            luxpower_client = self._lux_client(dongle)
            _LOGGER.debug("service_refresh_register for address_bank: %s", address_bank)
            await luxpower_client.request_data_bank(address_bank)
            _LOGGER.debug("service_refresh_data_register_bank done")
        except Exception as e:
            _LOGGER.error(f"Error refreshing data register bank {address_bank}: {e}")
            raise
