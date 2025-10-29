import pytest

from custom_components.luxpower.LXPPacket import LXPPacket


def test_prepare_packet_for_read_clamps_to_40():
    pkt = LXPPacket(dongle_serial=b"1234567890", serial_number=b"ABCDEFGHIJ")
    p = pkt.prepare_packet_for_read(0, 127, type=LK := pkt.READ_INPUT)
    # Read length is embedded as little-endian in the data frame; the last struct before CRC
    # We validate that we requested 40, not 127
    # Extract register and count from data frame
    data_frame = p[20:-2]
    reg = int.from_bytes(data_frame[12:14], "little")
    count = int.from_bytes(data_frame[14:16], "little")
    assert reg == 0
    assert count == 40


def test_parse_packet_ignores_127_len_response():
    pkt = LXPPacket(dongle_serial=b"1234567890", serial_number=b"ABCDEFGHIJ")

    # Build a fake data_frame with value_length = 254 bytes (127 registers)
    data_frame = bytearray()
    data_frame.append(pkt.ACTION_WRITE)
    data_frame.append(pkt.READ_INPUT)
    data_frame += b"ABCDEFGHIJ"  # serial_number 10 bytes
    data_frame += (0).to_bytes(2, "little")  # register 0
    value_len = (127 * 2)
    data_frame.append(value_len)  # value_length byte present for protocol 2
    values = bytes([0] * value_len)
    data_frame += values

    # Assemble full packet
    protocol = 2
    frame_length = 32  # header spec; not used strictly here
    header = bytearray()
    header += pkt.prefix
    header += protocol.to_bytes(2, "little")
    # frame_length = 20(header to len) + len(data_frame) + 2(crc)
    calc_frame_len = 20 + len(data_frame) + 2 - 6  # adjust like implementation
    header += calc_frame_len.to_bytes(2, "little")
    header += b"\x01"
    header += bytes([pkt.TRANSLATED_DATA])
    header += b"1234567890"
    header += len(data_frame).to_bytes(2, "little")

    crc = pkt.computeCRC(bytes(data_frame))
    packet = bytes(header) + bytes(data_frame) + crc.to_bytes(2, "little")

    result = pkt.parse_packet(packet)
    # Parser should drop this packet; return None
    assert result is None

from custom_components.luxpower.transport.serial_transport import SerialTransport


def test_modbus_crc_verify_and_extract():
    st = SerialTransport(port="COM1")

    # Build a simple Modbus RTU response: addr=1, func=0x04, bytecount=2, data=0x00 0x2A, CRC appended
    frame = bytearray([0x01, 0x04, 0x02, 0x00, 0x2A])
    crc = st._calculate_crc16(frame)
    frame.extend(crc.to_bytes(2, "little"))

    # Buffer emulates incoming stream
    st._buffer.clear()
    st._buffer.extend(frame)

    pkt = st._extract_packet()
    assert pkt is not None
    assert st._verify_crc(pkt) is True


