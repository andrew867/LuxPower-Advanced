package com.lux.luxcloud.protocol.tcp.dataframe.tcp;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum TCP_PROTOCOL {
    _01 { // from class: com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL.1
        @Override // com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL
        public int getPrefixData() {
            return 1;
        }
    },
    _02 { // from class: com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL.2
        @Override // com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL
        public int getPrefixData() {
            return 2;
        }
    };

    public abstract int getPrefixData();
}