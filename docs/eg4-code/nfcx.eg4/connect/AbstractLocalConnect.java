package com.nfcx.eg4.connect;

import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.bean.inverter.BATTERY_TYPE;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.nfcx.eg4.tool.FrameTool;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.Tool;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public abstract class AbstractLocalConnect implements LocalConnect {
    protected String connectType;
    protected boolean connected;
    protected String datalogSn;
    protected Inverter inverter;
    protected TCP_PROTOCOL tcpProtocol = TCP_PROTOCOL._02;

    public AbstractLocalConnect(String str) {
        this.connectType = str;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean read03AndInitDevice() {
        String strSendCommand = sendCommand("read_03_1", DataFrameFactory.createReadMultiHoldDataFrame(getTcpProtocol(), getDatalogSn(), 0, 40));
        System.out.println("Eg4 - read03AndInitDevice result = " + strSendCommand);
        if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 60) {
            return false;
        }
        Inverter inverter = new Inverter();
        inverter.setDatalogSn(FrameTool.getDatalogSn(strSendCommand));
        inverter.setSerialNum(FrameTool.getInverterSn(strSendCommand));
        long register4 = FrameTool.getRegister4(strSendCommand, 0);
        inverter.setModel(Long.valueOf(register4));
        inverter.setBatteryTypeFromModel(BATTERY_TYPE.getBatteryTypeByCode((int) ((register4 >> 8) & 3)));
        if (BATTERY_TYPE.LEAD_ACID.equals(inverter.getBatteryTypeFromModel())) {
            inverter.setLeadAcidType(Integer.valueOf((int) ((register4 >> 10) & 31)));
        }
        int register2 = FrameTool.getRegister2(strSendCommand, 19);
        inverter.setDtc(Integer.valueOf((register2 >> 5) & 7));
        inverter.setMachineType((register2 >> 13) & 15);
        inverter.setUsVersion(Integer.valueOf((int) ((register4 >> 19) & 1)));
        inverter.setPowerRating(Integer.valueOf((int) ((register4 >> 5) & 7)));
        inverter.setDeviceType(Integer.valueOf((register2 >> 1) & 15));
        int regHighInt = FrameTool.getRegHighInt(strSendCommand, 9);
        int regLowInt = FrameTool.getRegLowInt(strSendCommand, 9);
        int regLowInt2 = FrameTool.getRegLowInt(strSendCommand, 10);
        inverter.setSlaveVersion(Integer.valueOf(regHighInt));
        inverter.setFwVersion(Integer.valueOf(regLowInt2));
        if (inverter.isSnaSeries() || inverter.isLsp()) {
            inverter.setFwCode(FrameTool.getRegisterText(strSendCommand, 7, 2) + "-" + ProTool.showHex(regLowInt) + ProTool.showHex(regHighInt) + ProTool.showHex(regLowInt2));
        } else {
            inverter.setFwCode(FrameTool.getRegisterText(strSendCommand, 7, 2) + "-" + ProTool.showHex(regHighInt) + ProTool.showHex(regLowInt2));
        }
        setInverter(inverter);
        return true;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public Integer readSingle03(int i) {
        if (!initialize(true) || Tool.isEmpty(this.datalogSn)) {
            return null;
        }
        String strSendCommand = sendCommand("read_single_03_" + i, DataFrameFactory.createReadSingleHoldDataFrame(this.tcpProtocol, this.datalogSn, i));
        if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 36) {
            return null;
        }
        return Integer.valueOf(ProTool.count(strSendCommand.charAt(36), strSendCommand.charAt(35)));
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public JSONObject readMultiHold(int i, int i2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            if (initialize(true)) {
                DataFrame dataFrameCreateReadMultiHoldDataFrame = DataFrameFactory.createReadMultiHoldDataFrame(this.tcpProtocol, this.datalogSn, i, i2);
                int i3 = i2 * 2;
                String strSendCommand = sendCommand("read_multi_03_" + i + "_" + i3, dataFrameCreateReadMultiHoldDataFrame);
                if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 36) {
                    jSONObject.put("success", true);
                    jSONObject.put("valueFrame", strSendCommand.substring(35, i3 + 35));
                    return jSONObject;
                }
            }
            jSONObject.put("success", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean writeSingle(int i, int i2) {
        if (initialize(true) && !Tool.isEmpty(this.datalogSn)) {
            String strSendCommand = sendCommand("write_single_06", DataFrameFactory.createWriteSingleDataFrame(this.tcpProtocol, this.datalogSn, i, i2));
            return !Tool.isEmpty(strSendCommand) && strSendCommand.length() > 33 && i == ProTool.count(strSendCommand.charAt(33), strSendCommand.charAt(32));
        }
        return false;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean writeMulti(int i, int i2, byte[] bArr) {
        if (initialize(true) && !Tool.isEmpty(this.datalogSn)) {
            String strSendCommand = sendCommand("write_multi_06_" + i + "_" + i2, DataFrameFactory.createWriteMultiDataFrame(this.tcpProtocol, this.datalogSn, i, i2, bArr));
            if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 33) {
                return i == ProTool.count(strSendCommand.charAt(33), strSendCommand.charAt(32)) && i2 == ProTool.count(strSendCommand.charAt(35), strSendCommand.charAt(34));
            }
        }
        return false;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean functionControl(int i, int i2, boolean z) {
        Integer single03;
        if (!initialize(true) || (single03 = readSingle03(i)) == null) {
            return false;
        }
        return writeSingle(i, Integer.valueOf((z ? Integer.valueOf((1 << i2) | single03.intValue()) : Integer.valueOf((~(1 << i2)) & 65535 & single03.intValue())).intValue() & 65535).intValue());
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public String readDatalogParam(int i) {
        if (!initialize(true) || Tool.isEmpty(this.datalogSn)) {
            return null;
        }
        String strSendCommand = sendCommand("read_datalog_" + i, DataFrameFactory.createReadDatalogParamDataFrame(this.tcpProtocol, this.datalogSn, i));
        if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 22) {
            return null;
        }
        return strSendCommand.substring(22);
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean writeDatalogParam(int i, String str) {
        if (Tool.isEmpty(str)) {
            return false;
        }
        return writeDatalogParam(i, str.getBytes(StandardCharsets.ISO_8859_1));
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean writeDatalogParam(int i, byte[] bArr) {
        if (initialize(true) && !Tool.isEmpty(this.datalogSn)) {
            String strSendCommand = sendCommand("write_datalog_" + i, DataFrameFactory.createWriteDatalogParamDataFrame(this.tcpProtocol, this.datalogSn, i, bArr));
            return !Tool.isEmpty(strSendCommand) && strSendCommand.length() == 21 && strSendCommand.charAt(20) == 0;
        }
        return false;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public String getConnectType() {
        return this.connectType;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public Inverter getInverter() {
        return this.inverter;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public void setInverter(Inverter inverter) {
        this.inverter = inverter;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public void initDongleSn() {
        this.datalogSn = Constants.DEFAULT_DATALOG_SN;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public String getDatalogSn() {
        return this.datalogSn;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public void setDatalogSn(String str) {
        this.datalogSn = str;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public TCP_PROTOCOL getTcpProtocol() {
        return this.tcpProtocol;
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public void setTcpProtocol(TCP_PROTOCOL tcp_protocol) {
        this.tcpProtocol = tcp_protocol;
    }
}