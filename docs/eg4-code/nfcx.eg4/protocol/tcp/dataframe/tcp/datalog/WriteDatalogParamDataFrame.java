package com.nfcx.eg4.protocol.tcp.dataframe.tcp.datalog;

import com.nfcx.eg4.protocol.tcp.TCP_FUNCTION;
import com.nfcx.eg4.protocol.tcp.data.Data;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.AbstractTcpDataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class WriteDatalogParamDataFrame extends AbstractTcpDataFrame {
    public WriteDatalogParamDataFrame(TCP_PROTOCOL tcp_protocol, Data data) {
        super(TCP_FUNCTION.W_PARAM, tcp_protocol);
        setData(data);
    }
}