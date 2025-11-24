package com.lux.luxcloud.protocol.tcp.dataframe.tcp.datalog;

import com.lux.luxcloud.protocol.tcp.TCP_FUNCTION;
import com.lux.luxcloud.protocol.tcp.data.datalog.ReadDatalogParamData;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.AbstractTcpDataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ReadDatalogParamDataFrame extends AbstractTcpDataFrame {
    public ReadDatalogParamDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i) {
        super(TCP_FUNCTION.R_PARAM, tcp_protocol);
        setData(new ReadDatalogParamData(str, i));
    }
}