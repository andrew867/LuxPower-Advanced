"""

This is a docstring placeholder.

This is where we will describe what this module does

"""

from .const import (
    CLIENT_DAEMON_FORMAT,
    DOMAIN,
    EVENT_DATA_FORMAT,
    EVENT_REGISTER_FORMAT,
    EVENT_UNAVAILABLE_FORMAT,
)

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
