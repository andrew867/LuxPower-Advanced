package com.lux.luxcloud.protocol.tcp.dataframe.tcp.response;

import com.lux.luxcloud.protocol.tcp.TCP_FUNCTION;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.AbstractTcpDataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ResponseHeartBeatDataFrame extends AbstractTcpDataFrame {
    public ResponseHeartBeatDataFrame(TCP_PROTOCOL tcp_protocol) {
        super(TCP_FUNCTION.HEART_BEAT, tcp_protocol);
    }
}