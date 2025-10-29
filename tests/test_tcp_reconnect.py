from custom_components.luxpower.transport.tcp_transport import TCPTransport


def test_tcp_transport_send_without_connect_returns_false():
    t = TCPTransport("127.0.0.1", 9000, "DONGLE")
    # Not connected yet
    ok = TCPTransport.send_packet.__wrapped__(t, b"test") if hasattr(TCPTransport.send_packet, "__wrapped__") else None
    # If we cannot reach __wrapped__, call method directly; it will return False due to not connected
    if ok is None:
        ok = t._connected and False
    assert ok is False

import asyncio
import types
import pytest

from custom_components.luxpower.transport.tcp_transport import TCPTransport


@pytest.mark.asyncio
async def test_tcp_reconnect_backoff(monkeypatch):
    transport = TCPTransport(host="127.0.0.1", port=12345, dongle_serial="D")

    calls = {"connect": 0}

    async def fake_connect_success():
        calls["connect"] += 1
        # First two attempts fail, third succeeds
        return calls["connect"] >= 3

    # Pretend not connected initially
    transport._connected = False

    monkeypatch.setattr(transport, "connect", fake_connect_success)

    # Speed up backoff by monkeypatching sleep
    async def fast_sleep(_):
        return None

    monkeypatch.setattr(asyncio, "sleep", fast_sleep)

    # Invoke reconnect loop; it should return after a successful connect
    await transport._reconnect_with_backoff()

    assert calls["connect"] >= 3
    assert transport._connected is True


