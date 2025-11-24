package com.nfcx.eg4.protocol.tcp;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public enum TCP_FUNCTION {
    HEART_BEAT { // from class: com.nfcx.eg4.protocol.tcp.TCP_FUNCTION.1
        @Override // com.nfcx.eg4.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -63;
        }
    },
    TRANSLATE { // from class: com.nfcx.eg4.protocol.tcp.TCP_FUNCTION.2
        @Override // com.nfcx.eg4.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -62;
        }
    },
    R_PARAM { // from class: com.nfcx.eg4.protocol.tcp.TCP_FUNCTION.3
        @Override // com.nfcx.eg4.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -61;
        }
    },
    W_PARAM { // from class: com.nfcx.eg4.protocol.tcp.TCP_FUNCTION.4
        @Override // com.nfcx.eg4.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -60;
        }
    };

    public abstract byte getFunctionCode();

    public static TCP_FUNCTION getFunctionByCode(byte b) {
        for (TCP_FUNCTION tcp_function : values()) {
            if (tcp_function.getFunctionCode() == b) {
                return tcp_function;
            }
        }
        return null;
    }
}