package com.nfcx.eg4.protocol.tcp.dataframe.tcp.response;

import com.nfcx.eg4.protocol.tcp.TCP_FUNCTION;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.AbstractTcpDataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ResponseHeartBeatDataFrame extends AbstractTcpDataFrame {
    public ResponseHeartBeatDataFrame(TCP_PROTOCOL tcp_protocol) {
        super(TCP_FUNCTION.HEART_BEAT, tcp_protocol);
    }
}