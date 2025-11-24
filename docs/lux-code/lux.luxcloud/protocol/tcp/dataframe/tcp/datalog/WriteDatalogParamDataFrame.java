package com.lux.luxcloud.protocol.tcp.dataframe.tcp.datalog;

import com.lux.luxcloud.protocol.tcp.TCP_FUNCTION;
import com.lux.luxcloud.protocol.tcp.data.Data;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.AbstractTcpDataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class WriteDatalogParamDataFrame extends AbstractTcpDataFrame {
    public WriteDatalogParamDataFrame(TCP_PROTOCOL tcp_protocol, Data data) {
        super(TCP_FUNCTION.W_PARAM, tcp_protocol);
        setData(data);
    }
}