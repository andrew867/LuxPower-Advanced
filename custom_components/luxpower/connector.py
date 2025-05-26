"""

This is a docstring placeholder.

This is where we will describe what this module does

"""

import asyncio
import logging

from homeassistant.core import HomeAssistant

from .lxp.client import LuxPowerClient
from .const import DOMAIN

_LOGGER = logging.getLogger(__name__)


async def refreshALLPlatforms(hass: HomeAssistant, dongle):
    """

    This is a docstring placeholder.

    This is where we will describe what this function does

    """
    await asyncio.sleep(20)
    # fmt: skip
    await hass.services.async_call(DOMAIN, "luxpower_refresh_holdings", {"dongle": dongle}, blocking=True)
    await asyncio.sleep(10)
    # fmt: skip
    await hass.services.async_call(DOMAIN, "luxpower_refresh_registers", {"dongle": dongle, "bank_count": 3}, blocking=True)


class ServiceHelper:
    def __init__(self, hass) -> None:
        self.hass = hass

    def _lux_client(self, dongle: str) -> LuxPowerClient:
        for entry_id in self.hass.data[DOMAIN]:
            entry_data = self.hass.data[DOMAIN][entry_id]
            if dongle == entry_data["DONGLE"]:
                return entry_data.get("client")

        raise Exception(f"Couldn't find luxpower client dongle {dongle}")

    async def service_reconnect(self, dongle):
        luxpower_client = self._lux_client(dongle)
        await luxpower_client.reconnect()
        await asyncio.sleep(1)
        _LOGGER.debug("service_reconnect done")

    async def service_restart(self, dongle):
        luxpower_client = self._lux_client(dongle)
        await luxpower_client.restart()
        await asyncio.sleep(1)
        _LOGGER.warning("service_restart done")

    async def service_reset_settings(self, dongle):
        luxpower_client = self._lux_client(dongle)
        await luxpower_client.reset_all_settings()
        await asyncio.sleep(1)
        _LOGGER.warning("service_reset_settings done")

    async def service_synctime(self, dongle, do_set_time: bool):
        luxpower_client = self._lux_client(dongle)
        await luxpower_client.synctime(do_set_time)
        _LOGGER.info("service_synctime done")

    async def service_refresh_data_registers(self, dongle, bank_count):
        _LOGGER.info(
            f"service_refresh_data_registers start - Count: {bank_count}")
        luxpower_client = self._lux_client(dongle)
        await luxpower_client.do_refresh_data_registers(bank_count)

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
        luxpower_client = self._lux_client(dongle)
        await luxpower_client.do_refresh_hold_registers()

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
        luxpower_client = self._lux_client(dongle)
        _LOGGER.debug(
            "service_refresh_register for address_bank: %s", address_bank)
        await luxpower_client.request_data_bank(address_bank)
        _LOGGER.debug("service_refresh_data_register_bank done")
