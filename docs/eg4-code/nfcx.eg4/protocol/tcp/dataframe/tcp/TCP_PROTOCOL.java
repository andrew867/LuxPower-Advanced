package com.nfcx.eg4.protocol.tcp.dataframe.tcp;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public enum TCP_PROTOCOL {
    _01 { // from class: com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL.1
        @Override // com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL
        public int getPrefixData() {
            return 1;
        }
    },
    _02 { // from class: com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL.2
        @Override // com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL
        public int getPrefixData() {
            return 2;
        }
    };

    public abstract int getPrefixData();
}