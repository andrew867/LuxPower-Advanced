"""

This is a docstring placeholder.

This is where we will describe what this module does

"""

import asyncio
import logging
import re

from .const import (
    CLIENT_DAEMON_FORMAT,
    DOMAIN,
    EVENT_DATA_FORMAT,
    EVENT_REGISTER_FORMAT,
    EVENT_UNAVAILABLE_FORMAT,
)

_LOGGER = logging.getLogger(__name__)

# fmt: off


class Event:
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    def __init__(self, dongle) -> None:
        """

        This is a docstring placeholder.

        This is where we will describe what this __init__ does

        """
        self.INVERTER_ID = dongle
        self.EVENT_DATA_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="all")
        self.EVENT_DATA_BANK0_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank0")
        self.EVENT_DATA_BANK1_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank1")
        self.EVENT_DATA_BANK2_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank2")
        self.EVENT_DATA_BANK3_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank3")
        self.EVENT_DATA_BANK4_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank4")
        self.EVENT_DATA_BANKX_RECEIVED = EVENT_DATA_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bankX")
        self.EVENT_REGISTER_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="all")
        self.EVENT_REGISTER_21_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="21")
        self.EVENT_REGISTER_BANK0_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank0")
        self.EVENT_REGISTER_BANK1_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank1")
        self.EVENT_REGISTER_BANK2_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank2")
        self.EVENT_REGISTER_BANK3_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank3")
        self.EVENT_REGISTER_BANK4_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank4")
        self.EVENT_REGISTER_BANK5_RECEIVED = EVENT_REGISTER_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID, GROUP="bank5")
        self.EVENT_UNAVAILABLE_RECEIVED = EVENT_UNAVAILABLE_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID)
        self.CLIENT_DAEMON = CLIENT_DAEMON_FORMAT.format(DOMAIN=DOMAIN, DONGLE=self.INVERTER_ID)

# fmt: on


def _crc16_modbus(data: bytes) -> int:
    """Compute MODBUS CRC16."""
    crc = 0xFFFF
    for byte in data:
        crc ^= byte
        for _ in range(8):
            if crc & 1:
                crc >>= 1
                crc ^= 0xA001
            else:
                crc >>= 1
    return crc


async def read_serial_number(host: str, port: int) -> str:
    """Retrieve inverter serial number using a raw Modbus request."""
    command = bytearray(18)
    command[0] = 0x01  # address
    command[1] = 0x04  # function code
    # bytes 2-11 are all zeros as in the C example
    command[12] = 0x00  # initial register low byte
    command[13] = 0x00  # initial register high byte
    command[14] = 0x01  # number of registers low byte
    command[15] = 0x00  # number of registers high byte

    crc = _crc16_modbus(command[:16])
    command[16] = crc & 0xFF
    command[17] = (crc >> 8) & 0xFF

    try:
        reader, writer = await asyncio.open_connection(host, port)
        writer.write(command)
        await writer.drain()
        data = await reader.read(64)
        writer.close()
        await writer.wait_closed()
    except Exception as exc:  # pragma: no cover - network dependent
        _LOGGER.error("Failed to read serial number: %s", exc)
        return ""

    match = re.search(rb"(\d{10})", data)
    if match:
        return match.group(1).decode()
    return ""

