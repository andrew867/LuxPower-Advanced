from custom_components.luxpower.transport.mqtt_transport import MQTTTransport


def test_mqtt_direct_path_enforces_40(monkeypatch):
    mt = object.__new__(MQTTTransport)
    # stub logger and client
    class DummyLog:
        def warning(self, *a, **k):
            pass
        def error(self, *a, **k):
            pass
        def debug(self, *a, **k):
            pass
    mt._logger = DummyLog()
    mt._register_cache = {}

    # Helper to call private method
    data = {"register_start": 0, "register_count": 127, "values": [0] * 127}
    out = MQTTTransport._json_to_register_data(mt, data, "input")
    assert out is None

    data2 = {"register_start": 0, "register_count": 39, "values": [0] * 39}
    out2 = MQTTTransport._json_to_register_data(mt, data2, "input")
    assert out2 is None

import json

from custom_components.luxpower.transport.mqtt_transport import MQTTTransport


class DummyHass:
    pass


class DummyMsg:
    def __init__(self, payload):
        self.payload = payload


def test_mqtt_write_ack_handling():
    t = MQTTTransport(hass=DummyHass(), topic_prefix="modbus_bridge", dongle_serial="D")
    # Seed a pending command
    t._seq = 10
    t._pending_acks[11] = {"command": {"seq": 11}, "timestamp": 0.0, "retries": 0}

    ack = {"seq": 11, "status": "ok", "register": 1, "value": 2}
    t._handle_write_ack(DummyMsg(json.dumps(ack)))

    assert 11 not in t._pending_acks


