from custom_components.luxpower.transport.serial_transport import SerialTransport


def test_serial_bridge_ignores_127_read(monkeypatch):
    st = object.__new__(SerialTransport)
    # monkeypatch logger
    class Dummy:
        def warning(self, *args, **kwargs):
            pass
    st._logger = Dummy()

    # Build a fake LuxPower packet bytes where a register/count pair decodes to count=127
    # _luxpower_to_modbus_rtu scans for two little-endian words; we inject 0x0000 and 127
    lux = bytearray(b"\x00\x00\x7f\x00")
    modbus, meta = SerialTransport._luxpower_to_modbus_rtu(st, bytes(lux))
    assert modbus is None
    assert meta == {}

from custom_components.luxpower.transport.serial_transport import SerialTransport


def test_modbus_crc_detects_failure():
    st = SerialTransport(port="COM1")
    # Valid base frame
    base = bytearray([0x01, 0x04, 0x02, 0x00, 0x2A])
    crc = st._calculate_crc16(base)
    bad = bytearray(base)
    # Corrupt one byte
    bad[3] = 0xFF
    bad.extend(crc.to_bytes(2, "little"))

    assert st._verify_crc(bytes(bad)) is False


