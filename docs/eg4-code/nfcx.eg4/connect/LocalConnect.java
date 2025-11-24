package com.nfcx.eg4.connect;

import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public interface LocalConnect {
    void close();

    boolean functionControl(int i, int i2, boolean z);

    String getCommandWaitResult(String str);

    String getConnectType();

    String getDatalogSn();

    Inverter getInverter();

    TCP_PROTOCOL getTcpProtocol();

    void initDongleSn();

    boolean initialize(boolean z);

    boolean read03AndInitDevice();

    String readDatalogParam(int i);

    JSONObject readMultiHold(int i, int i2);

    Integer readSingle03(int i);

    String sendCommand(String str, DataFrame dataFrame);

    String sendCommand(String str, DataFrame dataFrame, int i);

    boolean sendPureCommand(DataFrame dataFrame);

    void setDatalogSn(String str);

    void setInverter(Inverter inverter);

    void setTcpProtocol(TCP_PROTOCOL tcp_protocol);

    boolean writeDatalogParam(int i, String str);

    boolean writeDatalogParam(int i, byte[] bArr);

    boolean writeMulti(int i, int i2, byte[] bArr);

    boolean writeSingle(int i, int i2);
}