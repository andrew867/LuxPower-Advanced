"""

This is written by Guy Wells (C) 2025 with the help and support of contributors on the Github page.
This code is from https://github.com/guybw/LuxPython_DEV

This LXPPacket.py takes the packet and decodes it to variables.

"""


import logging
import socket
import struct

_LOGGER = logging.getLogger(__name__)

def prepare_binary_value(oldvalue, mask, enable=True):
    return oldvalue | mask if enable else oldvalue & (65535 - mask)

class LXPPacket:
    """

    This is a docstring placeholder.

    This is where we will describe what this class does

    """

    CHARGE_POWER_PERCENT_CMD = 64

    # System Discharge Rate (%)
    DISCHG_POWER_PERCENT_CMD = 65

    # Grid Charge Power Rate (%)
    AC_CHARGE_POWER_CMD = 66

    # Discharge cut-off SOC (%)
    DISCHG_CUT_OFF_SOC_EOD = 105

    HEARTBEAT = 193
    TRANSLATED_DATA = 194
    READ_PARAM = 195
    WRITE_PARAM = 196

    READ_HOLD = 3
    READ_INPUT = 4
    WRITE_SINGLE = 6
    WRITE_MULTI = 16

    NULL_DONGLE = b"\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff"
    NULL_SERIAL = b"\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00"

    # fmt: off

    TCP_FUNCTION = {
        193: "HEARTBEAT",
        194: "TRANSLATED_DATA",
        195: "READ_PARAM",
        196: "WRITE_PARAM"
    }

    # fmt: on

    ACTION_WRITE = 0
    ACTION_READ = 1
    ADDRESS_ACTION = {0: "writing", 1: "reading"}

    DEVICE_FUNCTION = {
        3: "READ_HOLD",
        4: "READ_INPUT",
        6: "WRITE_SINGLE",
        16: "WRITE_MULTI",
        131: "READ_HOLD_ERROR",
        132: "READ_INPUT_ERROR",
        133: "WRITE_SINGLE_ERROR",
        134: "WRITE_MULTI_ERROR",
    }

    #
    # Register 21, Most Significant Byte
    #

    FEED_IN_GRID = 1 << 15
    DCI_ENABLE = 1 << 14
    GFCI_ENABLE = 1 << 13
    R21_UNKNOWN_BIT_12 = 1 << 12
    CHARGE_PRIORITY = 1 << 11
    FORCED_DISCHARGE_ENABLE = 1 << 10
    NORMAL_OR_STANDBY = 1 << 9
    SEAMLESS_EPS_SWITCHING = 1 << 8

    # Register 21, Least Significant Byte
    AC_CHARGE_ENABLE = 1 << 7
    GRID_ON_POWER_SS = 1 << 6
    NEUTRAL_DETECT_ENABLE = 1 << 5
    ANTI_ISLAND_ENABLE = 1 << 4
    R21_UNKNOWN_BIT_3 = 1 << 3
    DRMS_ENABLE = 1 << 2
    OVF_LOAD_DERATE_ENABLE = 1 << 1
    POWER_BACKUP_ENABLE = 1 << 0

    # Not a recommendation, just what my defaults appeared to be when
    # setting up the unit for the first time, so probably sane..?
    R21_DEFAULTS = (
        FEED_IN_GRID
        | DCI_ENABLE
        | GFCI_ENABLE
        | R21_UNKNOWN_BIT_12
        | NORMAL_OR_STANDBY
        | SEAMLESS_EPS_SWITCHING
        | GRID_ON_POWER_SS
        | ANTI_ISLAND_ENABLE
        | DRMS_ENABLE
    )

    #
    # Register 110, Most Significant Byte
    #

    TAKE_LOAD_TOGETHER = 1 << 10

    #
    # Register 110, Least Significant Byte
    #

    CHARGE_LAST = 1 << 4
    MICRO_GRID_ENABLE = 1 << 2
    FAST_ZERO_EXPORT_ENABLE = 1 << 1
    RUN_WITHOUT_GRID = 1 << 1
    PV_GRID_OFF_ENABLE = 1 << 0

    # fmt: off

    #
    # Register 120, Most Significant Byte
    #

    R120_UNKNOWN_BIT_15 = 1 << 15  # = 32768
    R120_UNKNOWN_BIT_14 = 1 << 14  # = 16384
    R120_UNKNOWN_BIT_13 = 1 << 13  # =  8192
    R120_UNKNOWN_BIT_12 = 1 << 12  # =  4096
    R120_UNKNOWN_BIT_11 = 1 << 11  # =  2048
    R120_UNKNOWN_BIT_10 = 1 << 10  # =  1024
    R120_UNKNOWN_BIT_09 = 1 << 9   # =   512
    R120_UNKNOWN_BIT_08 = 1 << 8   # =   256

    #
    # Register 120, Least Significant Byte
    #

    GEN_CHRG_ACC_TO_SOC = 1 << 7   # =   128  As Opposed To According To Voltage
    R120_UNKNOWN_BIT_06 = 1 << 6   # =    64
    R120_UNKNOWN_BIT_05 = 1 << 5   # =    32
    DISCHARG_ACC_TO_SOC = 1 << 4   # =    16  As Opposed To According To Voltage
    R120_UNKNOWN_BIT_03 = 1 << 3   # =     8
    AC_CHARGE_MODE_B_02 = 1 << 2   # =     4  AC CHARGE  - Off Disable 2 On 4 /4 Both On SOC
    AC_CHARGE_MODE_B_01 = 1 << 1   # =     2  AC CHARGE  - Off Disable 4 On Voltage On with 4 Off Time
    R120_UNKNOWN_BIT_00 = 1 << 0   # =     1

    AC_CHARGE_MODE_BITMASK = AC_CHARGE_MODE_B_01 | AC_CHARGE_MODE_B_02

    #
    # Register 179, Most Significant Byte
    #

    R179_UNKNOWN_BIT_15 = 1 << 15  # = 32768
    R179_UNKNOWN_BIT_14 = 1 << 14  # = 16384
    R179_UNKNOWN_BIT_13 = 1 << 13  # =  8192
    R179_UNKNOWN_BIT_12 = 1 << 12  # =  4096   True
    R179_UNKNOWN_BIT_11 = 1 << 11  # =  2048   True
    R179_UNKNOWN_BIT_10 = 1 << 10  # =  1024
    R179_UNKNOWN_BIT_09 = 1 << 9   # =   512
    R179_UNKNOWN_BIT_08 = 1 << 8   # =   256

    #
    # Register 179, Least Significant Byte
    #

    ENABLE_PEAK_SHAVING = 1 << 7   # =   128   True
    R179_UNKNOWN_BIT_06 = 1 << 6   # =    64   True
    R179_UNKNOWN_BIT_05 = 1 << 5   # =    32
    R179_UNKNOWN_BIT_04 = 1 << 4   # =    16
    R179_UNKNOWN_BIT_03 = 1 << 3   # =     8
    R179_UNKNOWN_BIT_02 = 1 << 2   # =     4
    R179_UNKNOWN_BIT_01 = 1 << 1   # =     2
    R179_UNKNOWN_BIT_00 = 1 << 0   # =     1

    # fmt: on

    status = "status"
    v_pv_1 = "v_pv_1"
    v_pv_2 = "v_pv_2"
    v_pv_3 = "v_pv_3"
    v_bat = "v_bat"
    soc = "soc"
    p_pv_1 = "p_pv_1"
    p_pv_2 = "p_pv_2"
    p_pv_3 = "p_pv_3"
    p_pv_total = "p_pv_total"
    p_charge = "p_charge"
    p_discharge = "p_discharge"
    v_ac_r = "v_ac_r"
    v_ac_s = "v_ac_s"
    v_ac_t = "v_ac_t"
    f_ac = "f_ac"
    p_inv = "p_inv"
    rms_current = "rms_current"
    p_rec = "p_rec"
    pf = "pf"
    v_eps_r = "v_eps_r"
    v_eps_s = "v_eps_s"
    v_eps_t = "v_eps_t"
    f_eps = "f_eps"
    p_to_grid = "p_to_grid"
    p_to_user = "p_to_user"
    p_load = "p_load"
    p_load2 = "p_load2"
    p_to_eps = "p_to_eps"
    e_pv_1_day = "e_pv_1_day"
    e_pv_2_day = "e_pv_2_day"
    e_pv_3_day = "e_pv_3_day"
    e_pv_total = "e_pv_total"
    e_inv_day = "e_inv_day"
    e_rec_day = "e_rec_day"
    e_chg_day = "e_chg_day"
    e_dischg_day = "e_dischg_day"
    e_eps_day = "e_eps_day"
    e_to_grid_day = "e_to_grid_day"
    e_to_user_day = "e_to_user_day"
    v_bus_1 = "v_bus_1"
    v_bus_2 = "v_bus_2"
    e_pv_1_all = "e_pv_1_all"
    e_pv_2_all = "e_pv_2_all"
    e_pv_3_all = "e_pv_3_all"
    e_pv_all = "e_pv_all"
    e_inv_all = "e_inv_all"
    e_rec_all = "e_rec_all"
    e_chg_all = "e_chg_all"
    e_dischg_all = "e_dischg_all"
    e_eps_all = "e_eps_all"
    e_to_grid_all = "e_to_grid_all"
    e_to_user_all = "e_to_user_all"
    internal_fault = "internal_fault"
    fault_code = "fault_code"
    warning_code = "warning_code"
    t_inner = "t_inner"
    t_rad_1 = "t_rad_1"
    t_rad_2 = "t_rad_2"
    t_bat = "t_bat"
    uptime = "uptime"
    max_chg_curr = "max_chg_curr"
    max_dischg_curr = "max_dischg_curr"
    max_cell_temp = "max_cell_temp"
    min_cell_temp = "min_cell_temp"
    max_cell_volt = "max_cell_volt"
    min_cell_volt = "min_cell_volt"
    charge_volt_ref = "charge_volt_ref"
    dischg_cut_volt = "dischg_cut_volt"
    bat_status_inv = "bat_status_inv"
    bat_count = "bat_count"
    bat_capacity = "bat_capacity"
    bat_current = "bat_current"
    bat_cycle_count = "bat_cycle_count"
    gen_input_volt ="gen_input_volt"
    gen_input_freq = "gen_input_freq"
    gen_power_watt = "gen_power_watt"
    gen_power_day = "gen_power_day"
    gen_power_all = "gen_power_all"
    eps_L1_volt = "eps_L1_volt"
    eps_L2_volt = "eps_L2_volt"
    eps_L1_watt = "eps_L1_watt"
    eps_L2_watt = "eps_L2_watt"
    p_load_ongrid = "p_load_ongrid"
    e_load_day = "e_load_day"
    e_load_all_l = "e_load_all_l"

    def __init__(self, packet=b"", dongle_serial=b"", serial_number=b"", debug=True):
        """
        Initialises the LXPPacket Class.

        This is where we will describe what this __init__ does

        """
        self.packet = packet
        self.packet_length = 0
        self.packet_length_calced = 0
        self.packet_error = True
        self.prefix = b"\xa1\x1a"
        self.protocol_number = 0
        self.frame_length = 0
        self.tcp_function = 0
        self.dongle_serial = dongle_serial
        self.data_length = 0
        self.data_frame = b""
        self.crc_modbus = 0
        self.address_action = b""
        self.device_function = b""
        self.serial_number = serial_number
        self.register = 0
        self.value_length_byte_present = False
        self.value_length = 0
        self.value = b""
        self.regValues = {}
        self.regValuesHex = {}
        self.regValuesInt = {}
        self.regValuesThis = {}
        self.readValues = {}
        self.readValuesHex = {}
        self.readValuesInt = {}
        self.readValuesThis = {}
        self.inputRead1 = False
        self.inputRead2 = False
        self.inputRead3 = False
        self.inputRead4 = False
        self.inputRead5 = False

        self.data = {}
        self.debug = True

    def set_packet(self, packet):
        self.packet = packet

    def parse(self):
        self.parse_packet(self.packet)

    def parse_packet(self, packet):
        self.packet_error = True
        if self.debug:
            _LOGGER.debug("*********************** PARSING PACKET *************************************")
        self.packet_length = len(packet)
        is_heartbeat = self.packet_length == 19 or self.packet_length == 21

        # Check if packet contains data
        if not is_heartbeat and self.packet_length < 37:
            _LOGGER.error(f"Received packet is TOO SMALL with length {self.packet_length}")
            return

        prefix = packet[0:2]
        self.protocol_number = struct.unpack("H", packet[2:4])[0]
        self.frame_length = struct.unpack("H", packet[4:6])[0]
        self.packet_length_calced = self.frame_length + 6

        _LOGGER.debug("self.packet_length: %s", self.packet_length)
        _LOGGER.debug("self.packet_length_calced: %s", self.packet_length_calced)

        if self.packet_length != self.packet_length_calced:
            if self.packet_length > self.packet_length_calced:
                _LOGGER.warning(
                    "Long Packet - Continuing - packet length (real/calced) %s %s",
                    self.packet_length,
                    self.packet_length_calced,
                )
                _LOGGER.warning("Probably An Unhandled MultiFrame - Report To Devs")
            else:
                _LOGGER.error(
                    "Bad Packet -  Too Short - (real/calced) %s %s", self.packet_length, self.packet_length_calced
                )
                return

        # unknown_byte = packet[6]
        self.tcp_function = packet[7]
        self.dongle_serial = packet[8:18]
        raw_data_length = packet[18:20]
        self.data_length = struct.unpack("H", raw_data_length)[0] if raw_data_length != b"\x00" else 0
        self.data_frame = packet[20:self.packet_length_calced - 2]  # fmt: skip

        if self.debug:
            _LOGGER.debug("prefix: %s", prefix)

        if prefix != self.prefix:
            _LOGGER.error("Invalid packet - Bad Prefix")
            return

        if self.debug:
            _LOGGER.debug("protocol_number: %s", self.protocol_number)
            _LOGGER.debug("frame_length : %s", self.frame_length)
            _LOGGER.debug(
                "tcp_function : %s %s", self.tcp_function, self.TCP_FUNCTION.get(self.tcp_function, "UNKNOWN")
            )
            _LOGGER.debug("dongle_serial : %s", self.dongle_serial)
            _LOGGER.debug("data_length : %s", self.data_length)

        if self.tcp_function == self.HEARTBEAT:
            _LOGGER.debug("HEARTBEAT ")
            self.packet_error = False
            return {
                "tcp_function": self.TCP_FUNCTION[self.tcp_function],
            }

        if self.data_length != len(self.data_frame) + 2:
            _LOGGER.error("Invalid packet - Bad data length %s", len(self.data_frame))
            return

        self.crc_modbus = struct.unpack("H", packet[self.packet_length_calced - 2:self.packet_length_calced])[0]  # fmt: skip

        if self.debug:
            _LOGGER.debug("data_frame : %s", self.data_frame)
            _LOGGER.debug("crc_modbus : %s", self.crc_modbus)

        crc16 = self.computeCRC(self.data_frame)

        if self.debug:
            _LOGGER.debug("CRC data: %s", crc16)

        if crc16 != self.crc_modbus:
            _LOGGER.error("Invalid Packet - CRC error")
            return

        self.address_action = self.data_frame[0]
        self.device_function = self.data_frame[1]
        self.serial_number = self.data_frame[2:12]
        self.register = struct.unpack("H", self.data_frame[12:14])[0]
        self.value_length_byte_present = (
            self.protocol_number == 2 or self.protocol_number == 5
        ) and self.device_function != self.WRITE_SINGLE
        self.value_length = 2
        if self.value_length_byte_present:
            self.value_length = self.data_frame[14]
            self.value = self.data_frame[15:15 + self.value_length]  # fmt: skip
        else:
            self.value = self.data_frame[14:16]

        if self.debug:
            # _LOGGER.debug("address_action : %s %s", self.address_action, self.ADDRESS_ACTION[self.address_action])
            _LOGGER.debug("device_function : %s %s", self.device_function, self.DEVICE_FUNCTION[self.device_function])
            _LOGGER.debug("serial_number : %s", self.serial_number)
            _LOGGER.debug("register : %s", self.register)
            _LOGGER.debug("value_length_byte_present : %s", self.value_length_byte_present)
            _LOGGER.debug("value_length : %s", self.value_length)
            _LOGGER.debug("value : %s", self.value)

        # if self.tcp_function == self.TRANSLATED_DATA and self.device_function == self.READ_INPUT:
        #     if self.frame_length != 111 or self.frame_length != 97:
        #         return
        self.process_packet()
        self.packet_error = False

        return {
            "tcp_function": self.TCP_FUNCTION[self.tcp_function],
            "device_function": self.DEVICE_FUNCTION[self.device_function],
            "register": self.register,
            "value": self.value,
            "data": self.data,
            "thesedata": self.readValuesThis,
            "registers": self.regValuesInt,
            "thesereg": self.regValuesThis,
        }

    def computeCRC(self, data):
        length = len(data)
        crc = 0xFFFF
        if length == 0:
            length = 1
        j = 0
        while length != 0:
            crc ^= data[j]
            # print('j=0x%02x, length=0x%02x, crc=0x%04x' %(j,length,crc))
            for i in range(0, 8):
                if crc & 1:
                    crc >>= 1
                    crc ^= 0xA001
                else:
                    crc >>= 1
            length -= 1
            j += 1
        return crc

    def process_packet(self):
        if self.debug:
            _LOGGER.debug("--------------PROCESS PACKET---------------")

        number_of_registers = int(len(self.value) / 2)

        if self.device_function == self.READ_HOLD or self.device_function == self.WRITE_SINGLE:
            self.regValuesThis = {}
            not_found = True
            for i in range(0, number_of_registers):
                if self.register + i in [68, 69, 70, 71, 72, 73, 76, 77, 78, 79, 80, 81, 84, 85, 86, 87, 88, 89] and not_found:  # fmt: skip
                    _LOGGER.debug("Trying to Add Register 21 to this List if it already Exists")
                    if 21 in self.regValuesInt:
                        self.regValuesThis[21] = self.regValuesInt[21]
                        not_found = False
                self.regValuesThis[self.register + i] = self.get_read_value_int(self.register + i)
                self.regValues[self.register + i] = self.get_read_value(self.register + i)
                self.regValuesInt[self.register + i] = self.get_read_value_int(self.register + i)
                self.regValuesHex[self.register + i] = "".join(
                    format(x, "02X") for x in self.get_read_value(self.register + i)
                )
            if self.debug:
                _LOGGER.debug(self.regValuesThis)
                _LOGGER.debug(self.regValues)
                _LOGGER.debug(self.regValuesInt)
                _LOGGER.debug(self.regValuesHex)

        elif self.device_function == self.READ_INPUT:
            for i in range(0, number_of_registers):
                self.readValues[self.register + i] = self.get_read_value(self.register + i)
                self.readValuesInt[self.register + i] = self.get_read_value_int(self.register + i)
                self.readValuesHex[self.register + i] = "".join(
                    format(x, "02X") for x in self.get_read_value(self.register + i)
                )

            self.readValuesThis = {}

            # Decode Standard Block Registers
            if self.register == 0 and number_of_registers == 40:
                self.inputRead1 = True
                self.get_device_values_bank0()
            elif self.register == 40 and number_of_registers == 40:
                self.inputRead2 = True
                self.get_device_values_bank1()
            elif self.register == 80 and number_of_registers == 40:
                self.inputRead3 = True
                self.get_device_values_bank2()
            elif self.register == 120 and number_of_registers == 40:
                self.inputRead4 = True
                self.get_device_values_bank3()
            elif self.register == 160 and number_of_registers == 40:
                self.inputRead5 = True
                self.get_device_values_bank4()
            elif self.register == 0 and number_of_registers == 127:
                self.inputRead1 = True
                self.inputRead2 = True
                self.inputRead3 = True
                self.get_device_values_bank0()
                self.get_device_values_bank1()
                self.get_device_values_bank2()
            else:
                if number_of_registers == 1:
                    _LOGGER.warning(f"An input packet was received with an unsupported single register: {self.register}")
                    # The below code is a no-op because the methods immediately return without setting inputRead for that bank.
                    # Significant refactoring needs to happen to only set values from registers in the packet if this is to be supported
                    # Decode Single Register
                    if 0 <= self.register <= 39:
                        self.get_device_values_bank0()
                    elif 40 <= self.register <= 79:
                        self.get_device_values_bank1()
                    elif 80 <= self.register <= 119:
                        self.get_device_values_bank2()
                    elif 120 <= self.register <= 159:
                        self.get_device_values_bank3()
                    elif 160 <= self.register <= 199:
                        self.get_device_values_bank4()
                else:
                    _LOGGER.warning(f"An input packet was received with an unsupported register range: {self.register} - {self.register + number_of_registers - 1}")
                    # The below code is a no-op because the methods immediately return without setting inputRead for that bank.
                    # Significant refactoring needs to happen to only set values from registers in the packet if this is to be supported
                    # Decode Series of Registers - Possibly Over Block Boundaries
                    if 0 <= self.register <= 119:
                        self.get_device_values_bank0()
                        self.get_device_values_bank1()
                        self.get_device_values_bank2()

            self.data.update(self.readValuesThis)

            _LOGGER.debug(f"This Packet Data {self.readValuesThis}")
            _LOGGER.debug(f"Total Data {self.data}")

            if self.debug:
                _LOGGER.debug(self.readValues)
                _LOGGER.debug(self.readValuesInt)
                _LOGGER.debug(self.readValuesHex)

    def prepare_packet_for_write(self, register, value):
        _LOGGER.debug(f"Started Creating Packet For Write Register {register} With Value {value} ")

        protocol = 2
        frame_length = 32
        data_length = 18

        packet = self.prefix
        _LOGGER.debug(f"Created Packet With Prefix {packet} , {len(packet)}")
        packet = packet + struct.pack("H", protocol)
        _LOGGER.debug(f"Created Packet Inc Protocol {packet} , {len(packet)}")
        packet = packet + struct.pack("H", frame_length)
        _LOGGER.debug(f"Created Packet Inc Frame Length {packet} , {len(packet)}")
        packet = packet + b"\x01"
        packet = packet + struct.pack("B", self.TRANSLATED_DATA)
        _LOGGER.debug(f"Created Packet Inc Translated Data {packet} , {len(packet)}")
        packet = packet + self.dongle_serial
        _LOGGER.debug(f"Created Packet Inc Dongle Serial {packet} , {len(packet)}")
        packet = packet + struct.pack("H", data_length)

        _LOGGER.debug(f"Created Packet Header {packet} , {len(packet)}")

        data_frame = struct.pack("B", self.ACTION_WRITE)
        data_frame = data_frame + struct.pack("B", self.WRITE_SINGLE)
        data_frame = data_frame + self.serial_number
        data_frame = data_frame + struct.pack("H", register)
        data_frame = data_frame + struct.pack("H", value)

        _LOGGER.debug(f"Created Data Frame {data_frame} , {len(data_frame)}")

        crc_modbus = self.computeCRC(data_frame)
        packet = packet + data_frame + struct.pack("H", crc_modbus)

        _LOGGER.debug(f"Created Packet {packet} , {len(packet)}")
        return packet

    def prepare_packet_for_read(self, register, value=1, type=READ_HOLD):
        _LOGGER.debug("Entering prepare_packet_for_read %s %s", register, value)

        protocol = 2
        frame_length = 32
        data_length = 18

        packet = self.prefix
        packet = packet + struct.pack("H", protocol)
        packet = packet + struct.pack("H", frame_length)
        packet = packet + b"\x01"
        packet = packet + struct.pack("B", self.TRANSLATED_DATA)
        packet = packet + self.dongle_serial
        # packet = packet + self.NULL_DONGLE
        packet = packet + struct.pack("H", data_length)

        # This Change Makes Packets Same as App in Local Mode
        # And Solves issue Of Slow Connect on 2nd Parallel Inverter
        # data_frame = struct.pack('B', self.ACTION_READ)
        data_frame = struct.pack("B", self.ACTION_WRITE)

        data_frame = data_frame + struct.pack("B", type)
        data_frame = data_frame + self.serial_number
        # data_frame = data_frame + self.NULL_SERIAL
        data_frame = data_frame + struct.pack("H", register)
        data_frame = data_frame + struct.pack("H", value)
        crc_modbus = self.computeCRC(data_frame)
        packet = packet + data_frame + struct.pack("H", crc_modbus)

        _LOGGER.debug(f"{packet} LEN: %s ", len(packet))
        # _LOGGER.debug(packet, len(packet))
        return packet

    def get_read_value_int(self, reg):
        offset = (reg - self.register) * 2
        if offset < 0 or offset > self.data_length:
            return None
        else:
            return self.get_value_int(offset)

    def get_value_int(self, offset=0):
        return struct.unpack("H", self.value[offset:2 + offset])[0]  # fmt: skip

    def get_read_value(self, reg):
        offset = (reg - self.register) * 2
        if offset < 0 or offset > self.data_length:
            return None
        else:
            return self.get_value(offset)

    def get_value(self, offset=0):
        return self.value[offset:2 + offset]  # fmt: skip

    def get_read_value_combined(self, reg1, reg2):
        return self.readValues.get(reg1, b"\x00\x00") + self.readValues.get(reg2, b"\x00\x00")

    def get_read_value_combined_int(self, reg1, reg2):
        raw_value = self.readValues.get(reg1, b"\x00\x00") + self.readValues.get(reg2, b"\x00\x00")
        return struct.unpack("I", raw_value)[0]

    def convert_to_int(self, value):
        return struct.unpack("H", value)[0]

    def print_register_values(self):
        pass

    def convert_to_time(self, value):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00FF, (value & 0xFF00) >> 8

    def get_device_values_bank0(self):
        if self.inputRead1:
            if self.debug:
                _LOGGER.debug("***********INPUT 1 registers************")

            status = self.readValuesInt.get(0)

            if self.debug:
                _LOGGER.debug("status %s", status)
            self.readValuesThis[LXPPacket.status] = status

            v_pv_1 = self.readValuesInt.get(1, 0) / 10
            v_pv_2 = self.readValuesInt.get(2, 0) / 10
            v_pv_3 = self.readValuesInt.get(3, 0) / 10

            if self.debug:
                _LOGGER.debug("v_pv(Volts - v_pv_1, v_pv_2, v_pv_3)  %s,%s,%s", v_pv_1, v_pv_2, v_pv_3)
            self.readValuesThis[LXPPacket.v_pv_1] = v_pv_1
            self.readValuesThis[LXPPacket.v_pv_2] = v_pv_2
            self.readValuesThis[LXPPacket.v_pv_3] = v_pv_3

            v_bat = self.readValuesInt.get(4, 0) / 10
            if self.debug:
                _LOGGER.debug("v_bat(Volts) %s", v_bat)
            self.readValuesThis[LXPPacket.v_bat] = v_bat

            soc = self.readValues.get(5)[0] or 0 if self.readValues.get(5) is not None else 0
            if self.debug:
                _LOGGER.debug("soc(%%) %s", soc)
            self.readValuesThis[LXPPacket.soc] = soc

            internal_fault = self.readValuesInt.get(6, 0)
            if self.debug:
                _LOGGER.debug("internal_fault %s", internal_fault)
            self.readValuesThis[LXPPacket.internal_fault] = internal_fault

            p_pv_1 = self.readValuesInt.get(7, 0)
            p_pv_2 = self.readValuesInt.get(8, 0)
            p_pv_3 = self.readValuesInt.get(9, 0)
            if self.debug:
                _LOGGER.debug("p_pv(Watts - p_pv_1, p_pv_2, p_pv_3) %s,%s,%s", p_pv_1, p_pv_2, p_pv_3)
                _LOGGER.debug("p_pv_total(Watts) %s", p_pv_1 + p_pv_2 + p_pv_3)
            self.readValuesThis[LXPPacket.p_pv_1] = p_pv_1
            self.readValuesThis[LXPPacket.p_pv_2] = p_pv_2
            self.readValuesThis[LXPPacket.p_pv_3] = p_pv_3
            self.readValuesThis[LXPPacket.p_pv_total] = p_pv_1 + p_pv_2 + p_pv_3

            p_charge = self.readValuesInt.get(10, 0)
            p_discharge = self.readValuesInt.get(11, 0)
            if self.debug:
                _LOGGER.debug("p_charge(Watts) %s", p_charge)
                _LOGGER.debug("p_discharge(Watts) %s", p_discharge)
            self.readValuesThis[LXPPacket.p_charge] = p_charge
            self.readValuesThis[LXPPacket.p_discharge] = p_discharge

            v_ac_r = self.readValuesInt.get(12, 0) / 10
            v_ac_s = self.readValuesInt.get(13, 0) / 10
            v_ac_t = self.readValuesInt.get(14, 0) / 10
            if self.debug:
                _LOGGER.debug("v_ac(Volts - v_ac_r, v_ac_s, v_ac_t) %s,%s,%s", v_ac_r, v_ac_s, v_ac_t)
            self.readValuesThis[LXPPacket.v_ac_r] = v_ac_r
            self.readValuesThis[LXPPacket.v_ac_s] = v_ac_s
            self.readValuesThis[LXPPacket.v_ac_t] = v_ac_t

            f_ac = self.readValuesInt.get(15, 0) / 100
            if self.debug:
                _LOGGER.debug("f_ac(Hz) %s", f_ac)
            self.readValuesThis[LXPPacket.f_ac] = f_ac

            p_inv = self.readValuesInt.get(16, 0)
            p_rec = self.readValuesInt.get(17, 0)
            if self.debug:
                _LOGGER.debug("p_inv(Watts) %s", p_inv)
                _LOGGER.debug("p_rec(Watts) %s", p_rec)
            self.readValuesThis[LXPPacket.p_inv] = p_inv
            self.readValuesThis[LXPPacket.p_rec] = p_rec

            # Adding rms_current from register 18
            rms_current = self.readValuesInt.get(18, 0) / 100
            if self.debug:
                _LOGGER.debug("rms_current(Amps) %s", rms_current)
            self.readValuesThis[LXPPacket.rms_current] = rms_current

            pf = self.readValuesInt.get(19, 0) / 1000
            if self.debug:
                _LOGGER.debug("pf %s", pf)
            self.readValuesThis[LXPPacket.pf] = pf

            v_eps_r = self.readValuesInt.get(20, 0) / 10
            v_eps_s = self.readValuesInt.get(21, 0) / 10
            v_eps_t = self.readValuesInt.get(22, 0) / 10
            if self.debug:
                _LOGGER.debug("v_pv(Volts - v_eps_r, v_eps_s, v_eps_t)  %s,%s,%s", v_eps_r, v_eps_s, v_eps_t)
            self.readValuesThis[LXPPacket.v_eps_r] = v_eps_r
            self.readValuesThis[LXPPacket.v_eps_s] = v_eps_s
            self.readValuesThis[LXPPacket.v_eps_t] = v_eps_t

            f_eps = self.readValuesInt.get(23, 0) / 100
            if self.debug:
                _LOGGER.debug("f_ac(Hz) %s", f_eps)
            self.readValuesThis[LXPPacket.f_eps] = f_eps

            p_to_eps = self.readValuesInt.get(24, 0)
            p_to_grid = self.readValuesInt.get(26, 0)
            p_to_user = self.readValuesInt.get(27, 0)
            p_load = p_to_user - p_rec
            if p_load < 0:
                p_load = 0
            if self.debug:
                _LOGGER.debug("p_to_grid(Watts) %s", p_to_grid)
                _LOGGER.debug("p_to_user(Watts) %s", p_to_user)
                _LOGGER.debug("p_load(Watts) %s", p_load)
            self.readValuesThis[LXPPacket.p_to_grid] = p_to_grid
            self.readValuesThis[LXPPacket.p_to_user] = p_to_user
            self.readValuesThis[LXPPacket.p_to_eps] = p_to_eps
            self.readValuesThis[LXPPacket.p_load] = p_load

            e_pv_1_day = self.readValuesInt.get(28, 0) / 10
            e_pv_2_day = self.readValuesInt.get(29, 0) / 10
            e_pv_3_day = self.readValuesInt.get(30, 0) / 10
            if self.debug:
                _LOGGER.debug(
                    "e_pv_1(kWh) e_pv_1_day, e_pv_2_day, e_pv_3_day %s,%s,%s", e_pv_1_day, e_pv_2_day, e_pv_3_day
                )
                _LOGGER.debug(
                    "e_pv_total(kWh) e_pv_1_day + e_pv_2_day + e_pv_3_day %s", e_pv_1_day + e_pv_2_day + e_pv_3_day
                )
            self.readValuesThis[LXPPacket.e_pv_1_day] = e_pv_1_day
            self.readValuesThis[LXPPacket.e_pv_2_day] = e_pv_2_day
            self.readValuesThis[LXPPacket.e_pv_3_day] = e_pv_3_day
            self.readValuesThis[LXPPacket.e_pv_total] = e_pv_1_day + e_pv_2_day + e_pv_3_day

            e_inv_day = self.readValuesInt.get(31, 0) / 10
            e_rec_day = self.readValuesInt.get(32, 0) / 10
            e_chg_day = self.readValuesInt.get(33, 0) / 10
            e_dischg_day = self.readValuesInt.get(34, 0) / 10
            if self.debug:
                _LOGGER.debug("e_inv_day(kWh) %s", e_inv_day)
                _LOGGER.debug("e_rec_day(kWh) %s", e_rec_day)
                _LOGGER.debug("e_chg_day(kWh) %s", e_chg_day)
                _LOGGER.debug("e_dischg_day(kWh) %s", e_dischg_day)
            self.readValuesThis[LXPPacket.e_inv_day] = e_inv_day
            self.readValuesThis[LXPPacket.e_rec_day] = e_rec_day
            self.readValuesThis[LXPPacket.e_chg_day] = e_chg_day
            self.readValuesThis[LXPPacket.e_dischg_day] = e_dischg_day

            e_eps_day = self.readValuesInt.get(35, 0) / 10
            e_to_grid_day = self.readValuesInt.get(36, 0) / 10
            e_to_user_day = self.readValuesInt.get(37, 0) / 10
            if self.debug:
                _LOGGER.debug("e_eps_day(kWh) %s", e_eps_day)
                _LOGGER.debug("e_to_grid_day(kWh) %s", e_to_grid_day)
                _LOGGER.debug("e_to_user_day(kWh) %s", e_to_user_day)
            self.readValuesThis[LXPPacket.e_eps_day] = e_eps_day
            self.readValuesThis[LXPPacket.e_to_grid_day] = e_to_grid_day
            self.readValuesThis[LXPPacket.e_to_user_day] = e_to_user_day

            v_bus_1 = self.readValuesInt.get(38, 0) / 10
            v_bus_2 = self.readValuesInt.get(39, 0) / 10
            if self.debug:
                _LOGGER.debug("v_bus_1(Volts) %s", v_bus_1)
                _LOGGER.debug("v_bus_2(Volts) %s", v_bus_2)
            self.readValuesThis[LXPPacket.v_bus_1] = v_bus_1
            self.readValuesThis[LXPPacket.v_bus_2] = v_bus_2

    def get_device_values_bank1(self):
        if self.inputRead2:
            if self.debug:
                _LOGGER.debug("*********INPUT 2 registers **************")

            e_pv_1_all = self.get_read_value_combined_int(40, 41) / 10
            e_pv_2_all = self.get_read_value_combined_int(42, 43) / 10
            e_pv_3_all = self.get_read_value_combined_int(44, 45) / 10
            if self.debug:
                _LOGGER.debug("e_pv_1_all(kWh) %s", e_pv_1_all)
                _LOGGER.debug("e_pv_2_all(kWh) %s", e_pv_2_all)
                _LOGGER.debug("e_pv_3_all(kWh) %s", e_pv_3_all)
                _LOGGER.debug(
                    "e_pv_all(kWh) e_pv_1_all + e_pv_2_all + e_pv_3_all %s", e_pv_1_all + e_pv_2_all + e_pv_3_all
                )
            self.readValuesThis[LXPPacket.e_pv_1_all] = e_pv_1_all
            self.readValuesThis[LXPPacket.e_pv_2_all] = e_pv_2_all
            self.readValuesThis[LXPPacket.e_pv_3_all] = e_pv_3_all
            self.readValuesThis[LXPPacket.e_pv_all] = e_pv_1_all + e_pv_2_all + e_pv_3_all

            e_inv_all = self.get_read_value_combined_int(46, 47) / 10
            e_rec_all = self.get_read_value_combined_int(48, 49) / 10
            e_chg_all = self.get_read_value_combined_int(50, 51) / 10
            e_dischg_all = self.get_read_value_combined_int(52, 53) / 10
            e_eps_all = self.get_read_value_combined_int(54, 55) / 10
            e_to_grid_all = self.get_read_value_combined_int(56, 57) / 10
            e_to_user_all = self.get_read_value_combined_int(58, 59) / 10
            if self.debug:
                _LOGGER.debug("e_inv_all(kWh) %s", e_inv_all)
                _LOGGER.debug("e_rec_all(kWh) %s", e_rec_all)
                _LOGGER.debug("e_chg_all(kWh) %s", e_chg_all)
                _LOGGER.debug("e_dischg_all(kWh) %s", e_dischg_all)
                _LOGGER.debug("e_eps_all(kWh) %s", e_eps_all)
                _LOGGER.debug("e_to_grid_all(kWh) %s", e_to_grid_all)
                _LOGGER.debug("e_to_user_all(kWh) %s", e_to_user_all)
            self.readValuesThis[LXPPacket.e_inv_all] = e_inv_all
            self.readValuesThis[LXPPacket.e_rec_all] = e_rec_all
            self.readValuesThis[LXPPacket.e_chg_all] = e_chg_all
            self.readValuesThis[LXPPacket.e_dischg_all] = e_dischg_all
            self.readValuesThis[LXPPacket.e_eps_all] = e_eps_all
            self.readValuesThis[LXPPacket.e_to_grid_all] = e_to_grid_all
            self.readValuesThis[LXPPacket.e_to_user_all] = e_to_user_all

            fault_code = self.get_read_value_combined_int(60, 61)
            warning_code = self.get_read_value_combined_int(62, 63)
            if self.debug:
                _LOGGER.debug("fault_code %s", fault_code)
                _LOGGER.debug("warning_code %s", warning_code)
            self.readValuesThis[LXPPacket.fault_code] = fault_code
            self.readValuesThis[LXPPacket.warning_code] = warning_code

            t_inner = self.readValuesInt.get(64, 0)
            t_rad_1 = self.readValuesInt.get(65, 0)
            t_rad_2 = self.readValuesInt.get(66, 0)
            t_bat = self.readValuesInt.get(67, 0)
            if self.debug:
                _LOGGER.debug("t_inner %s", t_inner)
                _LOGGER.debug("t_rad_1 %s", t_rad_1)
                _LOGGER.debug("t_rad_2 %s", t_rad_2)
                _LOGGER.debug("t_bat %s", t_bat)
            self.readValuesThis[LXPPacket.t_inner] = t_inner
            self.readValuesThis[LXPPacket.t_rad_1] = t_rad_1
            self.readValuesThis[LXPPacket.t_rad_2] = t_rad_2
            self.readValuesThis[LXPPacket.t_bat] = t_bat

            uptime = self.get_read_value_combined_int(69, 70)
            if self.debug:
                _LOGGER.debug("uptime(seconds) %s", uptime)
            self.readValuesThis[LXPPacket.uptime] = uptime

    def get_device_values_bank2(self):
        if self.inputRead3:
            if self.debug:
                _LOGGER.debug("***********INPUT 3 registers************")

            model_code = None
            try:
                reg07_val = self.regValuesInt.get(7)
                reg08_val = self.regValuesInt.get(8)
                if reg07_val is not None and reg08_val is not None:
                    reg07_str = int(reg07_val).to_bytes(2, "little").decode()
                    reg08_str = int(reg08_val).to_bytes(2, "little").decode()
                    model_code = (reg07_str + reg08_str).upper()
            except Exception:
                model_code = None

            scale = 10 if model_code in ("FAAB", "EAAB", "ACAB", "CFAA", "CCAA") else 100
            max_chg_curr = self.readValuesInt.get(81, 0) / scale
            max_dischg_curr = self.readValuesInt.get(82, 0) / scale
            if self.debug:
                _LOGGER.debug("max_chg_curr(Ampere) %s", max_chg_curr)
                _LOGGER.debug("max_dischg_curr(Ampere) %s", max_dischg_curr)
            self.readValuesThis[LXPPacket.max_chg_curr] = max_chg_curr
            self.readValuesThis[LXPPacket.max_dischg_curr] = max_dischg_curr

            charge_volt_ref = self.readValuesInt.get(83, 0) / 10
            dischg_cut_volt = self.readValuesInt.get(84, 0) / 10
            if self.debug:
                _LOGGER.debug("charge_volt_ref(Volts) %s", charge_volt_ref)
                _LOGGER.debug("dischg_cut_volt(Volts) %s", dischg_cut_volt)
            self.readValuesThis[LXPPacket.charge_volt_ref] = charge_volt_ref
            self.readValuesThis[LXPPacket.dischg_cut_volt] = dischg_cut_volt

            bat_status_inv = self.readValuesInt.get(95, 0)
            if self.debug:
                _LOGGER.debug("bat_status_inv %s", bat_status_inv)
            self.readValuesThis[LXPPacket.bat_status_inv] = bat_status_inv

            bat_count = self.readValuesInt.get(96, 0)
            if self.debug:
                _LOGGER.debug("bat_count %s", bat_count)
            self.readValuesThis[LXPPacket.bat_count] = bat_count

            bat_capacity = self.readValuesInt.get(97, 0)
            if self.debug:
                _LOGGER.debug("bat_capacity %s", bat_capacity)
            self.readValuesThis[LXPPacket.bat_capacity] = bat_capacity

            bat_current = self.readValuesInt.get(98,0)
            if self.debug:
                _LOGGER.debug("bat_current %s", bat_current)

            if bat_current&0x8000:
                bat_current=bat_current-0x10000
            self.readValuesThis[LXPPacket.bat_current] = bat_current / 10

            max_cell_volt = self.readValuesInt.get(101, 0) / 1000
            min_cell_volt = self.readValuesInt.get(102, 0) / 1000
            max_cell_temp = self.readValuesInt.get(103, 0)
            min_cell_temp = self.readValuesInt.get(104, 0)

            if min_cell_temp&0x8000:
                min_cell_temp=min_cell_temp-0x10000
            min_cell_temp=min_cell_temp/10
            if max_cell_temp&0x8000:
                max_cell_temp=max_cell_temp-0x10000
            max_cell_temp=max_cell_temp/10
            
            if self.debug:
                _LOGGER.debug("max_cell_volt %s", max_cell_volt)
                _LOGGER.debug("min_cell_volt %s", min_cell_volt)
                _LOGGER.debug("min_cell_temp %s", min_cell_temp)
                _LOGGER.debug("max_cell_temp %s", max_cell_temp)
            self.readValuesThis[LXPPacket.max_cell_volt] = max_cell_volt
            self.readValuesThis[LXPPacket.min_cell_volt] = min_cell_volt
            self.readValuesThis[LXPPacket.max_cell_temp] = max_cell_temp
            self.readValuesThis[LXPPacket.min_cell_temp] = min_cell_temp

            bat_cycle_count = self.readValuesInt.get(106,0)
            self.readValuesThis[LXPPacket.bat_cycle_count] = bat_cycle_count

            p_load2 = self.readValuesInt.get(114, 0)
            self.readValuesThis[LXPPacket.p_load2] = p_load2

    def get_device_values_bank3(self):
        if self.inputRead4:
            if self.debug:
                _LOGGER.debug("***********INPUT 4 registers************")

            gen_input_volt = self.readValuesInt.get(121, 0) / 10
            gen_input_freq = self.readValuesInt.get(122, 0) / 100
            gen_power_watt = self.readValuesInt.get(123, 0)
            gen_power_day  = self.readValuesInt.get(124, 0) / 10
            gen_power_all  = self.readValuesInt.get(125, 0) / 10
            self.readValuesThis[LXPPacket.gen_input_volt] = gen_input_volt
            self.readValuesThis[LXPPacket.gen_input_freq] = gen_input_freq
            if gen_power_watt < 125:
                gen_power_watt = 0
            self.readValuesThis[LXPPacket.gen_power_watt] = gen_power_watt
            self.readValuesThis[LXPPacket.gen_power_day] = gen_power_day
            self.readValuesThis[LXPPacket.gen_power_all] = gen_power_all

            eps_L1_volt = self.readValuesInt.get(127, 0) / 10
            eps_L2_volt = self.readValuesInt.get(128, 0) / 10
            eps_L1_watt = self.readValuesInt.get(129, 0)
            eps_L2_watt = self.readValuesInt.get(130, 0)
            self.readValuesThis[LXPPacket.eps_L1_volt] = eps_L1_volt
            self.readValuesThis[LXPPacket.eps_L2_volt] = eps_L2_volt
            self.readValuesThis[LXPPacket.eps_L1_watt] = eps_L1_watt
            self.readValuesThis[LXPPacket.eps_L2_watt] = eps_L2_watt

    def get_device_values_bank4(self):
        if self.inputRead5:
            if self.debug:
                _LOGGER.debug("***********INPUT 5 registers************")

            p_load_ongrid = self.readValuesInt.get(170, 0)
            e_load_day = self.readValuesInt.get(171, 0) / 10
            e_load_all_l = self.readValuesInt.get(172, 0) / 10
            self.readValuesThis[LXPPacket.p_load_ongrid] = p_load_ongrid
            self.readValuesThis[LXPPacket.e_load_day] = e_load_day
            self.readValuesThis[LXPPacket.e_load_all_l] = e_load_all_l

            # IMPORTANT!! Registers above 199 must go into a new bank (create a new method for bank 5/bank 6 etc.)


if __name__ == "__main__":
    pass
