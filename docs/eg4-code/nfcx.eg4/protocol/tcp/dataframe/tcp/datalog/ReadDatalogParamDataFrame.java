package com.nfcx.eg4.protocol.tcp.dataframe.tcp.datalog;

import com.nfcx.eg4.protocol.tcp.TCP_FUNCTION;
import com.nfcx.eg4.protocol.tcp.data.datalog.ReadDatalogParamData;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.AbstractTcpDataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ReadDatalogParamDataFrame extends AbstractTcpDataFrame {
    public ReadDatalogParamDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i) {
        super(TCP_FUNCTION.R_PARAM, tcp_protocol);
        setData(new ReadDatalogParamData(str, i));
    }
}