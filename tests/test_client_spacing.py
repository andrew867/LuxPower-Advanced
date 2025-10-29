import asyncio
import os
import pytest

from custom_components.luxpower.lxp.client_new import LuxPowerClient


class DummyBus:
    def fire(self, *_args, **_kwargs):
        return None

    def async_fire(self, *_args, **_kwargs):
        return None


class DummyHass:
    def __init__(self):
        self.bus = DummyBus()


class DummyEvents:
    EVENT_DATA_BANK0_RECEIVED = "e0"
    EVENT_DATA_BANK1_RECEIVED = "e1"
    EVENT_DATA_BANK2_RECEIVED = "e2"
    EVENT_DATA_BANK3_RECEIVED = "e3"
    EVENT_DATA_BANK4_RECEIVED = "e4"
    EVENT_DATA_BANK5_RECEIVED = "e5"
    EVENT_DATA_BANK6_RECEIVED = "e6"
    EVENT_REGISTER_BANK7_RECEIVED = "e7"
    EVENT_REGISTER_BANK8_RECEIVED = "e8"
    EVENT_REGISTER_BANK9_RECEIVED = "e9"
    EVENT_DATA_BANKX_RECEIVED = "ex"
    EVENT_UNAVAILABLE_RECEIVED = "eu"


class DummyTransport:
    def __init__(self):
        self._callback = None

    async def connect(self):
        return True

    async def start_listening(self):
        return None

    async def stop_listening(self):
        return None

    async def disconnect(self):
        return None

    async def send_packet(self, _):
        return True

    def register_data_received_callback(self, cb):
        self._callback = cb

    def __str__(self):
        return "DummyTransport"


@pytest.mark.asyncio
async def test_inter_bank_delay(monkeypatch):
    hass = DummyHass()
    transport = DummyTransport()
    client = LuxPowerClient(
        hass=hass,
        transport=transport,
        dongle_serial="D",
        serial_number="S",
        events=DummyEvents(),
        respond_to_heartbeat=False,
    )

    # Force connected so the refresh path runs
    client._connected = True

    # Record sleeps
    sleeps = {"calls": 0}

    async def fake_sleep(t):
        # count only small inter-bank sleeps
        if 0 <= t <= 1:
            sleeps["calls"] += 1
        return None

    monkeypatch.setenv("LUXPOWER_INTER_BANK_DELAY", "0.2")
    monkeypatch.setattr(asyncio, "sleep", fake_sleep)

    # Request 3 banks; expect 2 inter-bank sleeps
    await client.do_refresh_data_registers(3)
    assert sleeps["calls"] >= 2


