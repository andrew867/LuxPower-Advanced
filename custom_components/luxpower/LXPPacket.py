import struct
import logging

_LOGGER = logging.getLogger(__name__)

class LXPPacket:

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

    NULL_DONGLE = b'\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff'
    NULL_SERIAL = b'\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00'

    TCP_FUNCTION = {
        193: 'HEARTBEAT',
        194: 'TRANSLATED_DATA',
        195: 'READ_PARAM',
        196: 'WRITE_PARAM'
    }

    ACTION_WRITE = 0
    ACTION_READ = 1
    ADDRESS_ACTION = {
        0: 'writing',
        1: 'reading'
    }

    DEVICE_FUNCTION = {
        3: 'READ_HOLD',
        4: 'READ_INPUT',
        6: 'WRITE_SINGLE',
        16: 'WRITE_MULTI',

        131: 'READ_HOLD_ERROR',
        132: 'READ_INPUT_ERROR',
        133: 'WRITE_SINGLE_ERROR',
        134: 'WRITE_MULTI_ERROR',
    }

    ###
    ### Register 21, Most Significant Byte
    ###
    FEED_IN_GRID = 1 << 15
    DCI_ENABLE = 1 << 14
    GFCI_ENABLE = 1 << 13
    R21_UNKNOWN_BIT_12 = 1 << 12
    CHARGE_PRIORITY = 1 << 11
    FORCED_DISCHARGE_ENABLE = 1 << 10
    NORMAL_OR_STANDBY = 1 << 9
    SEAMLESS_EPS_SWITCHING = 1 << 8

    ### Register 21, Least Significant Byte
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
    R21_DEFAULTS = FEED_IN_GRID | DCI_ENABLE | GFCI_ENABLE | R21_UNKNOWN_BIT_12 | NORMAL_OR_STANDBY | \
                   SEAMLESS_EPS_SWITCHING | GRID_ON_POWER_SS | ANTI_ISLAND_ENABLE | DRMS_ENABLE

    ###
    ### Register 105, Least Significant Byte
    ###
    MICRO_GRID_ENABLE = 1 << 2
    FAST_ZERO_EXPORT_ENABLE = 1 << 1

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
    p_rec = "p_rec"
    pf = "pf"
    v_eps_r = "v_eps_r"
    v_eps_s = "v_eps_s"
    v_eps_t = "v_eps_t"
    f_eps = "f_eps"
    p_to_grid = "p_to_grid"
    p_to_user = "p_to_user"
    p_load = "p_load"
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
    t_inner = "t_inner"
    t_rad_1 = "t_rad_1"
    t_rad_2 = "t_rad_2"
    t_bat = "t_bat"
    uptime = "uptime"
    max_chg_curr = "max_chg_curr"
    max_dischg_curr = "max_dischg_curr"
    charge_volt_ref = "charge_volt_ref"
    dischg_cut_volt = "dischg_cut_volt"
    bat_count = "bat_count"

    def __init__(self, packet=b'', dongle_serial=b'', serial_number=b'', debug=True):
        self.packet = packet
        self.packet_length = 0
        self.packet_length_calced = 0
        self.packet_error = True
        self.prefix = b'\xa1\x1a'
        self.protocol_number = 0
        self.frame_length = 0
        self.tcp_function = 0
        self.dongle_serial = dongle_serial
        self.data_length = 0
        self.data_frame = b''
        self.crc_modbus = 0
        self.address_action = b''
        self.device_function = b''
        self.serial_number = serial_number
        self.register = 0
        self.value_length_byte_present = False
        self.value_length = 0
        self.value = b''
        self.regValues = {}
        self.regValuesHex = {}
        self.regValuesInt = {}
        self.readValues = {}
        self.readValuesHex = {}
        self.readValuesInt = {}
        self.inputRead1 = False
        self.inputRead2 = False
        self.inputRead3 = False

        self.data = {}
        self.debug = True

    def set_packet(self, packet):
        self.packet = packet

    def parse(self):
        self.parse_packet(self.packet)

    def parse_packet(self, packet):
        self.packet_error = True
        if self.debug:
            _LOGGER.info("*********************** PARSING PACKET *************************************")
        self.packet_length = len(packet)

        #Check if packet contains only serial number
        if self.packet_length == 19 or self.packet_length == 21:
            _LOGGER.info(f"Packet received. Serial number number: {packet}. No other data.")
            return
        #Check if packet contains data
        elif self.packet_length < 37:
            _LOGGER.error("Recevied packet not sufficient")
            return

        prefix = packet[0:2]
        self.protocol_number = struct.unpack('H', packet[2:4])[0]
        self.frame_length = struct.unpack('H', packet[4:6])[0]
        self.packet_length_calced = self.frame_length + 6

        unknown_byte = packet[6]
        self.tcp_function = packet[7]
        self.dongle_serial = packet[8:18]
        self.data_length = struct.unpack('H', packet[18:20])[0]

        self.data_frame = packet[20:self.packet_length_calced - 2]
        self.crc_modbus = struct.unpack('H', packet[self.packet_length_calced - 2: self.packet_length_calced])[0]

        if self.debug:
            _LOGGER.debug("prefix: %s", prefix)
        if prefix != self.prefix:
            _LOGGER.debug('invalid packet')
            return

        if self.debug:
            _LOGGER.debug("protocol_number: %s", self.protocol_number)
            _LOGGER.debug("frame_length : %s", self.frame_length)
            _LOGGER.debug("tcp_function : %s", self.tcp_function, self.TCP_FUNCTION[self.tcp_function])
            _LOGGER.debug("dongle_serial : %s", self.dongle_serial)
            _LOGGER.debug("data_length : %s", self.data_length)

        if self.tcp_function == self.HEARTBEAT:
            _LOGGER.debug("HEARTBEAT ")
            return

        if self.data_length != len(self.data_frame) + 2:
            _LOGGER.debug('bad data length %s', len(self.data_frame))
            return

        if self.debug:
            _LOGGER.debug("data_frame : %s", self.data_frame)

            _LOGGER.debug("crc_modbus : %s", self.crc_modbus)
        crc16 = self.computeCRC(self.data_frame)

        if self.debug:
            _LOGGER.debug("CRC data: %s", crc16)

        if crc16 != self.crc_modbus:
            _LOGGER.debug("CRC error")
            return

        self.address_action = self.data_frame[0]
        self.device_function = self.data_frame[1]
        self.serial_number = self.data_frame[2:12]
        self.register = struct.unpack('H', self.data_frame[12:14])[0]
        self.value_length_byte_present = (self.protocol_number == 2 or self.protocol_number == 5) and self.device_function != self.WRITE_SINGLE
        self.value_length = 2
        if self.value_length_byte_present:
            self.value_length = self.data_frame[14]
            self.value = self.data_frame[15: 15 + self.value_length]
        else:
            self.value = self.data_frame[14:16]

        if self.debug:
            #_LOGGER.debug("address_action : %s %s", self.address_action, self.ADDRESS_ACTION[self.address_action])
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

        return {"tcp_function": self.TCP_FUNCTION[self.tcp_function],
                "device_function": self.DEVICE_FUNCTION[self.device_function],
                "register": self.register, "value": self.value, "data": self.data, "registers": self.regValuesInt}

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
            _LOGGER.info("--------------PROCESS PACKET---------------")
        if self.device_function == self.READ_HOLD or self.device_function == self.WRITE_SINGLE:
            for i in range(0, int(len(self.value) / 2)):
                self.regValues[self.register + i] = self.get_read_value(self.register + i)
                self.regValuesInt[self.register + i] = self.get_read_value_int(self.register + i)
                self.regValuesHex[self.register + i] = ''.join(format(x, '02X')
                                                                for x in self.get_read_value(self.register + i))
            if self.debug:
                _LOGGER.debug(self.regValues)
                _LOGGER.debug(self.regValuesInt)
                _LOGGER.debug(self.regValuesHex)

        # elif self.device_function == self.WRITE_SINGLE:
        #     self.regValues[self.register] = self.get_read_value(self.register)
        #     self.regValuesInt[self.register] = self.get_read_value_int(self.register)
        #     self.regValuesHex[self.register] = ''.join(format(x, '02X')
        #                                                 for x in self.get_read_value(self.register))
        #     if self.debug:
        #         print(self.regValues)
        #         print(self.regValuesInt)
        #         print(self.regValuesHex)

        elif self.device_function == self.READ_INPUT:
            for i in range(0,int(len(self.value)/2)):
                self.readValues[self.register + i] = self.get_read_value(self.register + i)
                self.readValuesInt[self.register + i] = self.get_read_value_int(self.register + i)
                self.readValuesHex[self.register + i] = ''.join(format(x, '02X')
                                                                for x in self.get_read_value(self.register + i))
            if self.register == 0:
                self.inputRead1 = True
            elif self.register == 40:
                self.inputRead2 = True
            elif self.register == 80:
                self.inputRead3 = True

            if self.debug:
                _LOGGER.debug(self.readValues)
                _LOGGER.debug(self.readValuesInt)
                _LOGGER.debug(self.readValuesHex)

            self.get_device_values()

    def prepare_packet_for_write(self, register, value):
        _LOGGER.debug(f"Started Creating Packet For Write Register {register} With Value {value} ")
        # if len(value) != 2:
        #     print("value length is not 2")
        #     return
        protocol = 2
        frame_length = 32
        data_length = 18

        packet = self.prefix
        _LOGGER.debug(f"Created Packet With Prefix {packet} , {len(packet)}")
        packet = packet + struct.pack('H', protocol)
        _LOGGER.debug(f"Created Packet Inc Protocol {packet} , {len(packet)}")
        packet = packet + struct.pack('H', frame_length)
        _LOGGER.debug(f"Created Packet Inc Frame Length {packet} , {len(packet)}")
        packet = packet + b'\x01'
        packet = packet + struct.pack('B', self.TRANSLATED_DATA)
        _LOGGER.debug(f"Created Packet Inc Translated Data {packet} , {len(packet)}")
        packet = packet + self.dongle_serial
        _LOGGER.debug(f"Created Packet Inc Dongle Serial {packet} , {len(packet)}")
        packet = packet + struct.pack('H', data_length)

        _LOGGER.debug(f"Created Packet Header {packet} , {len(packet)}")

        data_frame = struct.pack('B', self.ACTION_WRITE)
        data_frame = data_frame + struct.pack('B', self.WRITE_SINGLE)
        data_frame = data_frame + self.serial_number
        data_frame = data_frame + struct.pack('H', register)
        data_frame = data_frame + struct.pack('H', value)

        _LOGGER.debug(f"Created Data Frame {data_frame} , {len(data_frame)}")

        crc_modbus = self.computeCRC(data_frame)
        packet = packet + data_frame + struct.pack('H', crc_modbus)

        _LOGGER.debug(f"Created Packet {packet} , {len(packet)}")
        return packet

    def prepare_packet_for_read(self, register, value=1, type=READ_HOLD):
        # if len(value) != 2:
        #     print("value length is not 2")
        #     return

        _LOGGER.info("Entering prepare_packet_for_read %s %s", register, value)

        protocol = 2
        frame_length = 32
        data_length = 18

        packet = self.prefix
        packet = packet + struct.pack('H', protocol)
        packet = packet + struct.pack('H', frame_length)
        packet = packet + b'\x01'
        packet = packet + struct.pack('B', self.TRANSLATED_DATA)
        packet = packet + self.dongle_serial
        #packet = packet + self.NULL_DONGLE
        packet = packet + struct.pack('H', data_length)

        # This Change Makes Packets Same as App in Local Mode
        # And Solves issue Of Slow Connect on 2nd Parallel Inverter
        # data_frame = struct.pack('B', self.ACTION_READ)
        data_frame = struct.pack('B', self.ACTION_WRITE)

        data_frame = data_frame + struct.pack('B', type)
        data_frame = data_frame + self.serial_number
        #data_frame = data_frame + self.NULL_SERIAL
        data_frame = data_frame + struct.pack('H', register)
        data_frame = data_frame + struct.pack('H', value)
        crc_modbus = self.computeCRC(data_frame)
        packet = packet + data_frame + struct.pack('H', crc_modbus)

        _LOGGER.debug(f"{packet} LEN: %s ",len(packet))
        #_LOGGER.debug(packet, len(packet))
        return packet

    def get_read_value_int(self, reg):
        offset = (reg - self.register) * 2
        if offset < 0 or offset > self.data_length:
            return None
        else:
            return self.get_value_int(offset)

    def get_value_int(self, offset=0):
        return struct.unpack('H', self.value[offset:2 + offset])[0]

    def get_read_value(self, reg):
        offset = (reg - self.register) * 2
        if offset < 0 or offset > self.data_length:
            return None
        else:
            return self.get_value(offset)

    def get_value(self, offset=0):
        return self.value[offset:2 + offset]

    def get_read_value_combined(self, reg1, reg2):
        return self.readValues.get(reg1, b'\x00\x00') + self.readValues.get(reg2, b'\x00\x00')

    def get_read_value_combined_int(self, reg1, reg2):
        raw_value = self.readValues.get(reg1, b'\x00\x00') + self.readValues.get(reg2, b'\x00\x00')
        return struct.unpack('I', raw_value)[0]

    def convert_to_int(self, value):
        return struct.unpack('H', value)[0]

    def print_register_values(self):
        pass

    def convert_to_time(self, value):
        # Has To Be Integer Type value Coming In - NOT BYTE ARRAY
        return value & 0x00ff, (value & 0xff00) >> 8

    def get_device_values(self):
        if self.inputRead1:
            if self.debug:
                _LOGGER.info("***********INPUT 1 registers************")
            status = self.readValuesInt.get(0)
            if self.debug:
                print("status ", status)
            self.data[LXPPacket.status] = status

            v_pv_1 = self.readValuesInt.get(1, 0) / 10
            v_pv_2 = self.readValuesInt.get(2, 0) / 10
            v_pv_3 = self.readValuesInt.get(3, 0) / 10

            if self.debug:
                _LOGGER.debug("v_pv(Volts - v_pv_1, v_pv_2, v_pv_3)  ", v_pv_1, v_pv_2, v_pv_3)
            self.data[LXPPacket.v_pv_1] = v_pv_1
            self.data[LXPPacket.v_pv_2] = v_pv_2
            self.data[LXPPacket.v_pv_3] = v_pv_3

            v_bat = self.readValuesInt.get(4, 0) / 10
            if self.debug:
                _LOGGER.debug("v_bat(Volts) ", v_bat)
            self.data[LXPPacket.v_bat] = v_bat

            soc = self.readValues.get(5)[0] or 0 if self.readValues.get(5) is not None else 0
            if self.debug:
                _LOGGER.debug("soc(%) ", soc)
            self.data[LXPPacket.soc] = soc

            p_pv_1 = self.readValuesInt.get(7, 0)
            p_pv_2 = self.readValuesInt.get(8, 0)
            p_pv_3 = self.readValuesInt.get(9, 0)
            if self.debug:
                _LOGGER.debug("p_pv(Watts - p_pv_1, p_pv_2, p_pv_3) ", p_pv_1, p_pv_2, p_pv_3)
                _LOGGER.debug("p_pv_total(Watts) ", p_pv_1 + p_pv_2 + p_pv_3)
            self.data[LXPPacket.p_pv_1] = p_pv_1
            self.data[LXPPacket.p_pv_2] = p_pv_2
            self.data[LXPPacket.p_pv_3] = p_pv_3
            self.data[LXPPacket.p_pv_total] = p_pv_1 + p_pv_2 + p_pv_3

            p_charge = self.readValuesInt.get(10, 0)
            p_discharge = self.readValuesInt.get(11, 0)
            if self.debug:
                _LOGGER.debug("p_charge(Watts) ", p_charge)
                _LOGGER.debug("p_discharge(Watts) ", p_discharge)
            self.data[LXPPacket.p_charge] = p_charge
            self.data[LXPPacket.p_discharge] = p_discharge

            v_ac_r = self.readValuesInt.get(12, 0) / 10
            v_ac_s = self.readValuesInt.get(13, 0) / 10
            v_ac_t = self.readValuesInt.get(14, 0) / 10
            if self.debug:
                _LOGGER.debug("v_ac(Volts - v_ac_r, v_ac_s, v_ac_t) ", v_ac_r, v_ac_s, v_ac_t)
            self.data[LXPPacket.v_ac_r] = v_ac_r
            self.data[LXPPacket.v_ac_s] = v_ac_s
            self.data[LXPPacket.v_ac_t] = v_ac_t

            f_ac = self.readValuesInt.get(15, 0) / 100
            if self.debug:
                _LOGGER.debug("f_ac(Hz)", f_ac)
            self.data[LXPPacket.f_ac] = f_ac

            p_inv = self.readValuesInt.get(16, 0)
            p_rec = self.readValuesInt.get(17, 0)
            if self.debug:
                _LOGGER.debug("p_inv(Watts)", p_inv)
                _LOGGER.debug("p_rec(Watts)", p_rec)
            self.data[LXPPacket.p_inv] = p_inv
            self.data[LXPPacket.p_rec] = p_rec

            pf = self.readValuesInt.get(19, 0) / 1000
            if self.debug:
                _LOGGER.debug("pf ", pf)
            self.data[LXPPacket.pf] = pf

            v_eps_r = self.readValuesInt.get(20, 0) / 10
            v_eps_s = self.readValuesInt.get(21, 0) / 10
            v_eps_t = self.readValuesInt.get(22, 0) / 10
            if self.debug:
                _LOGGER.debug("v_pv(Volts - v_eps_r, v_eps_s, v_eps_t)  ", v_eps_r, v_eps_s, v_eps_t)
            self.data[LXPPacket.v_eps_r] = v_eps_r
            self.data[LXPPacket.v_eps_s] = v_eps_s
            self.data[LXPPacket.v_eps_t] = v_eps_t

            f_eps = self.readValuesInt.get(23, 0) / 100
            if self.debug:
                _LOGGER.debug("f_ac(Hz)", f_eps)
            self.data[LXPPacket.f_eps] = f_eps
            
            p_to_eps = self.readValuesInt.get(24, 0)
            p_to_grid = self.readValuesInt.get(26, 0)
            p_to_user = self.readValuesInt.get(27, 0)
            p_load =  p_to_user - p_rec
            if p_load < 0:
                p_load = 0
            if self.debug:
                _LOGGER.debug("p_to_grid(Watts)", p_to_grid)
                _LOGGER.debug("p_to_user(Watts)", p_to_user)
                _LOGGER.debug("p_load(Watts)", p_load)
            self.data[LXPPacket.p_to_grid] = p_to_grid
            self.data[LXPPacket.p_to_user] = p_to_user
            self.data[LXPPacket.p_to_eps] = p_to_eps
            self.data[LXPPacket.p_load] = p_load

            e_pv_1_day = self.readValuesInt.get(28, 0) / 10
            e_pv_2_day = self.readValuesInt.get(29, 0) / 10
            e_pv_3_day = self.readValuesInt.get(30, 0) / 10
            if self.debug:
                _LOGGER.debug("e_pv_1(kWh) e_pv_1_day, e_pv_2_day, e_pv_3_day", e_pv_1_day, e_pv_2_day, e_pv_3_day)
                _LOGGER.debug("e_pv_total(kWh) e_pv_1_day + e_pv_2_day + e_pv_3_day", e_pv_1_day + e_pv_2_day + e_pv_3_day)
            self.data[LXPPacket.e_pv_1_day] = e_pv_1_day
            self.data[LXPPacket.e_pv_2_day] = e_pv_2_day
            self.data[LXPPacket.e_pv_3_day] = e_pv_3_day
            self.data[LXPPacket.e_pv_total] = e_pv_1_day + e_pv_2_day + e_pv_3_day

            e_inv_day = self.readValuesInt.get(31, 0) / 10
            e_rec_day = self.readValuesInt.get(32, 0) / 10
            e_chg_day = self.readValuesInt.get(33, 0) / 10
            e_dischg_day = self.readValuesInt.get(34, 0) / 10
            if self.debug:
                _LOGGER.debug("e_inv_day(kWh) ", e_inv_day)
                _LOGGER.debug("e_rec_day(kWh) ", e_rec_day)
                _LOGGER.debug("e_chg_day(kWh) ", e_chg_day)
                _LOGGER.debug("e_dischg_day(kWh) ", e_dischg_day)
            self.data[LXPPacket.e_inv_day] = e_inv_day
            self.data[LXPPacket.e_rec_day] = e_rec_day
            self.data[LXPPacket.e_chg_day] = e_chg_day
            self.data[LXPPacket.e_dischg_day] = e_dischg_day

            e_eps_day = self.readValuesInt.get(35, 0) / 10
            e_to_grid_day = self.readValuesInt.get(36, 0) / 10
            e_to_user_day = self.readValuesInt.get(37, 0) / 10
            if self.debug:
                _LOGGER.debug("e_eps_day(kWh) ", e_eps_day)
                _LOGGER.debug("e_to_grid_day(kWh) ", e_to_grid_day)
                _LOGGER.debug("e_to_user_day(kWh) ", e_to_user_day)
            self.data[LXPPacket.e_eps_day] = e_eps_day
            self.data[LXPPacket.e_to_grid_day] = e_to_grid_day
            self.data[LXPPacket.e_to_user_day] = e_to_user_day

            v_bus_1 = self.readValuesInt.get(38, 0) / 10
            v_bus_2 = self.readValuesInt.get(39, 0) / 10
            if self.debug:
                _LOGGER.debug("v_bus_1(Volts) ", v_bus_1)
                _LOGGER.debug("v_bus_2(Volts) ", v_bus_2)
            self.data[LXPPacket.v_bus_1] = v_bus_1
            self.data[LXPPacket.v_bus_2] = v_bus_2

        if self.inputRead2:
            if self.debug:
                _LOGGER.debug("*********INPUT 2 registers **************")

            e_pv_1_all = self.get_read_value_combined_int(40, 41) / 10
            e_pv_2_all = self.get_read_value_combined_int(42, 43) / 10
            e_pv_3_all = self.get_read_value_combined_int(44, 45) / 10
            if self.debug:
                _LOGGER.debug("e_pv_1_all(kWh) ", e_pv_1_all)
                _LOGGER.debug("e_pv_2_all(kWh) ", e_pv_2_all)
                _LOGGER.debug("e_pv_3_all(kWh) ", e_pv_3_all)
                _LOGGER.debug("e_pv_all(kWh) e_pv_1_all + e_pv_2_all + e_pv_3_all", e_pv_1_all + e_pv_2_all + e_pv_3_all)
            self.data[LXPPacket.e_pv_1_all] = e_pv_1_all
            self.data[LXPPacket.e_pv_2_all] = e_pv_2_all
            self.data[LXPPacket.e_pv_3_all] = e_pv_3_all
            self.data[LXPPacket.e_pv_all] = e_pv_1_all + e_pv_2_all + e_pv_3_all

            e_inv_all = self.get_read_value_combined_int(46, 47) / 10
            e_rec_all = self.get_read_value_combined_int(48, 49) / 10
            e_chg_all = self.get_read_value_combined_int(50, 51) / 10
            e_dischg_all = self.get_read_value_combined_int(52, 53) / 10
            e_eps_all = self.get_read_value_combined_int(54, 55) / 10
            e_to_grid_all = self.get_read_value_combined_int(56, 57) / 10
            e_to_user_all = self.get_read_value_combined_int(58, 59) / 10
            if self.debug:
                _LOGGER.debug("e_inv_all(kWh) ", e_inv_all)
                _LOGGER.debug("e_rec_all(kWh) ", e_rec_all)
                _LOGGER.debug("e_chg_all(kWh) ", e_chg_all)
                _LOGGER.debug("e_dischg_all(kWh) ", e_dischg_all)
                _LOGGER.debug("e_eps_all(kWh) ", e_eps_all)
                _LOGGER.debug("e_to_grid_all(kWh) ", e_to_grid_all)
                _LOGGER.debug("e_to_user_all(kWh) ", e_to_user_all)
            self.data[LXPPacket.e_inv_all] = e_inv_all
            self.data[LXPPacket.e_rec_all] = e_rec_all
            self.data[LXPPacket.e_chg_all] = e_chg_all
            self.data[LXPPacket.e_dischg_all] = e_dischg_all
            self.data[LXPPacket.e_eps_all] = e_eps_all
            self.data[LXPPacket.e_to_grid_all] = e_to_grid_all
            self.data[LXPPacket.e_to_user_all] = e_to_user_all

            t_inner = self.readValuesInt.get(64, 0)
            t_rad_1 = self.readValuesInt.get(65, 0)
            t_rad_2 = self.readValuesInt.get(66, 0)
            t_bat = self.readValuesInt.get(67, 0)
            if self.debug:
                _LOGGER.debug("t_inner ", t_inner)
                _LOGGER.debug("t_rad_1 ", t_rad_1)
                _LOGGER.debug("t_rad_2 ", t_rad_2)
                _LOGGER.debug("t_bat ", t_bat)
            self.data[LXPPacket.t_inner] = t_inner
            self.data[LXPPacket.t_rad_1] = t_rad_1
            self.data[LXPPacket.t_rad_2] = t_rad_2
            self.data[LXPPacket.t_bat] = t_bat

            uptime = self.get_read_value_combined_int(69, 70)
            if self.debug:
                _LOGGER.debug("uptime(seconds) ", uptime)
            self.data[LXPPacket.uptime] = uptime

        if self.inputRead3:
            if self.debug:
                _LOGGER.debug("***********INPUT 3 registers************")

            max_chg_curr = self.readValuesInt.get(81, 0) / 100
            max_dischg_curr = self.readValuesInt.get(82, 0) / 100
            if self.debug:
                _LOGGER.debug("max_chg_curr(Ampere) ", max_chg_curr)
                _LOGGER.debug("max_dischg_curr(Ampere) ", max_dischg_curr)
            self.data[LXPPacket.max_chg_curr] = max_chg_curr
            self.data[LXPPacket.max_dischg_curr] = max_dischg_curr

            charge_volt_ref = self.readValuesInt.get(83, 0) / 10
            dischg_cut_volt = self.readValuesInt.get(84, 0) / 10
            if self.debug:
                _LOGGER.debug("charge_volt_ref(Volts) ", charge_volt_ref)
                _LOGGER.debug("dischg_cut_volt(Volts) ", dischg_cut_volt)
            self.data[LXPPacket.charge_volt_ref] = charge_volt_ref
            self.data[LXPPacket.dischg_cut_volt] = dischg_cut_volt

            bat_count = self.readValuesInt.get(96, 0)
            if self.debug:
                _LOGGER.debug("bat_count ", bat_count)
            self.data[LXPPacket.bat_count] = bat_count

    def update_value(self, oldvalue, mask, enable=True):
        return oldvalue | mask if enable else oldvalue & (65535 - mask)

if __name__ == "__main__":
    ## WriteSingle
    packet = b'\xa1\x1a\x02\x00\x20\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33\x39\x33\x12\x00\x01\x06\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30\x15\x00\x01\xd1\x19\x6c'

    ## WriteSingle
    # packet = b"\xa1\x1a\x02\x00\x20\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #          b"\x39\x33\x12\x00\x01\x06\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #          b"\x15\x00\x01\xd3\x98\xad"

    ## ReadHold
    # packet = b"\xa1\x1a\x02\x00\x21\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #          b"\x39\x33\x13\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #          b"\x15\x00\x02\x01\xd1\x03\x05"

    ## Read Input (0 to 39)
    read_input_packet1 = b"\xa1\x1a\x02\x00\x6f\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33\x39\x33\x61\x00\x01\x04\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30\x00\x00\x50\x0c\x00\x14\x08\xe8\x07\x02\x00\xfa\x01\x54\x64\x00\x2c\xa5\x02\x99\x02\x00\x00\x27\x03\x00\x00\x6c\x09\x01\x05\x00\x00\x83\x13\xfc\x01\x00\x00\xe6\x00\xe8\x03\x6c\x09\xd9\xcc\x0a\x00\x83\x13\x00\x00\x00\x00\x00\x00\x00\x00\x1d\x00\x1d\x00\x00\x00\x2a\x00\x00\x00\x24\x00\x16\x00\x00\x00\x02\x00\x05\x00\x83\x0e\xfe\x0b\x69\x7b"

    ## ReadHold
    # packet = b"\xa1\x1a\x02\x00\x4d\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x3f\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #         b"\x00\x00\x2e\xa6\x0a\x01\x00\x30\x31\x30\x32\x30\x30\x35\x30\x35" \
    #         b"\x30\x41\x41\x41\x41\x02\x15\x17\x01\x00\x00\x14\x07\x1d\x0b\x23" \
    #         b"\x1a\x01\x00\x01\x00\x00\x00\x00\x00\x00\x00\x04\x00\x01\xd3\xe8" \
    #         b"\x03\xa7\x08"

    ## Read Hold
    # packet = b"\xa1\x1a\x02\x00\x45\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x37\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #         b"\x15\x00\x26\x01\xd3\xe8\x03\x1e\x00\x0a\x00\xd1\x07\x3e\x0a\x94" \
    #         b"\x11\x64\x19\xd1\x07\x3e\x0a\x7e\x00\x33\x00\x30\x07\xb1\x0a\x1a" \
    #         b"\x00\x1a\x00\x30\x07\xb1\x0a\x1a\x00\x00\x10"

    # packet = b"\xa1\x1a\x02\x00\x23\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x15\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #         b"\x7c\x00\x04\x00\x00\x05\x00\xe3\x60"

    # packet = b"\xa1\x1a\x02\x00\x21\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x13\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #         b"\x15\x00\x02\x01\xd1\x03\x05"

    # WriteRegister
    # packet = b"\xa1\x1a\x02\x00\x20\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x12\x00\x01\x06\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #         b"\x15\x00\x01\xd3\x98\xad"

    ## CRC error
    # packet = b"\xa1\x1a\x02\x00\x3f\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x31\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x6c" \
    #         b"\x5b\xa9\xad\x08\x00\x64\x00\x64\x00\x5f\x00\x0e\x00\x0f\x3b\x00" \
    #         b"\x00\xcc\x04\x00\x00\x00\x00\x64\x00\x5f\x00\x05\x01\x0f\x1c\x00" \
    #         b"\x00\x00\x00\x1d\xec"

    ## READ_HOLD ( 40 to 63)
    packet2 = b"\xa1\x1a\x02\x00\x4f\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
                b"\x39\x33\x41\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
                b"\x28\x00\x30\x1a\x00\x3e\x0a\x94\x11\x64\x19\xb7\x03\x36\x12\x94" \
                b"\x11\x64\x19\x19\x00\x1a\x00\x94\x11\x64\x19\x19\x00\x1a\x00\x1e" \
                b"\x00\x16\x08\x80\x07\xd8\x09\x28\x0a\x00\x00\x64\x00\x32\x00\xe8" \
                b"\x03\xe8\x03\x87\xf6"

    ## READ_HOLD ( 80 to 113 )
    packet3 = b"\xa1\x1a\x02\x00\x63\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
            b"\x39\x33\x55\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
            b"\x50\x00\x44\x00\x00\x00\x00\x64\x00\x1e\x00\x10\x00\x17\x00\x15" \
            b"\x00\x17\x3b\x00\x00\x00\x00\xe6\x00\x32\x00\x6f\x09\xfc\x08\x14" \
            b"\x00\x05\x00\x00\x00\x00\x00\x00\x00\x30\x02\x90\x01\x42\x00\x42" \
            b"\x00\x64\x00\x00\x00\x0a\x00\x38\xff\x26\x02\x00\x00\x90\x01\x00" \
            b"\x00\x00\x00\x00\x00\x00\x00\xbc\xcc"


    # packet = b"\xa1\x1a\x02\x00\x4d\x00\x01\xc2\x42\x41\x31\x39\x35\x32\x30\x33" \
    #         b"\x39\x33\x3f\x00\x01\x03\x30\x31\x30\x32\x30\x30\x35\x30\x35\x30" \
    #         b"\x00\x00\x2e\xa6\x0a\x01\x00\x30\x31\x30\x32\x30\x30\x35\x30\x35" \
    #         b"\x30\x41\x41\x41\x41\x02\x15\x17\x01\x00\x00\x14\x07\x1d\x0b\x23" \
    #         b"\x1a\x01\x00\x01\x00\x00\x00\x00\x00\x00\x00\x04\x00\x01\xd3\xe8" \
    #         b"\x03\xa7\x08"


    lxpPacket = LXPPacket()

    lxpPacket.set_packet(packet)
    lxpPacket.parse()
    if not lxpPacket.packet_error:
        _LOGGER.debug("Protocol: ", lxpPacket.protocol_number, 'action: ', lxpPacket.ADDRESS_ACTION[lxpPacket.address_action])
        _LOGGER.debug("function: ", lxpPacket.DEVICE_FUNCTION[lxpPacket.device_function], 'register: ', lxpPacket.register)
        _LOGGER.debug(lxpPacket.regValues)
        _LOGGER.debug(lxpPacket.regValuesInt)
        _LOGGER.debug(lxpPacket.regValuesHex)

        lxpPacket.get_device_values()

    lxpPacket.set_packet(packet2)
    lxpPacket.parse()
    if not lxpPacket.packet_error:
        _LOGGER.debug("Protocol: ", lxpPacket.protocol_number, 'action: ', lxpPacket.ADDRESS_ACTION[lxpPacket.address_action])
        _LOGGER.debug("function: ", lxpPacket.DEVICE_FUNCTION[lxpPacket.device_function], 'register: ', lxpPacket.register)
        _LOGGER.debug(lxpPacket.regValues)
        _LOGGER.debug(lxpPacket.regValuesInt)
        _LOGGER.debug(lxpPacket.regValuesHex)

        lxpPacket.get_device_values()

    lxpPacket.set_packet(packet3)
    lxpPacket.parse()
    if not lxpPacket.packet_error:
        _LOGGER.debug("Protocol: ", lxpPacket.protocol_number, 'action: ', lxpPacket.ADDRESS_ACTION[lxpPacket.address_action])
        _LOGGER.debug("function: ", lxpPacket.DEVICE_FUNCTION[lxpPacket.device_function], 'register: ', lxpPacket.register)
        _LOGGER.debug(lxpPacket.regValues)
        _LOGGER.debug(lxpPacket.regValuesInt)
        _LOGGER.debug(lxpPacket.regValuesHex)

        lxpPacket.get_device_values()

    lxpPacket.set_packet(read_input_packet1)
    lxpPacket.parse()
    if not lxpPacket.packet_error:
        _LOGGER.debug("Protocol: ", lxpPacket.protocol_number, 'action: ', lxpPacket.ADDRESS_ACTION[lxpPacket.address_action])
        _LOGGER.debug("function: ", lxpPacket.DEVICE_FUNCTION[lxpPacket.device_function], 'register: ', lxpPacket.register)
        _LOGGER.debug(lxpPacket.readValues)
        _LOGGER.debug(lxpPacket.readValuesInt)
        _LOGGER.debug(lxpPacket.readValuesHex)


    new_packet = lxpPacket.prepare_packet_for_write(21, 53505)
    _LOGGER.debug(packet == new_packet)
