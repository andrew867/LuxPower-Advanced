package com.lux.luxcloud.protocol.tcp;

import com.alibaba.fastjson2.JSONB;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum TCP_FUNCTION {
    HEART_BEAT { // from class: com.lux.luxcloud.protocol.tcp.TCP_FUNCTION.1
        @Override // com.lux.luxcloud.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -63;
        }
    },
    TRANSLATE { // from class: com.lux.luxcloud.protocol.tcp.TCP_FUNCTION.2
        @Override // com.lux.luxcloud.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -62;
        }
    },
    R_PARAM { // from class: com.lux.luxcloud.protocol.tcp.TCP_FUNCTION.3
        @Override // com.lux.luxcloud.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return (byte) -61;
        }
    },
    W_PARAM { // from class: com.lux.luxcloud.protocol.tcp.TCP_FUNCTION.4
        @Override // com.lux.luxcloud.protocol.tcp.TCP_FUNCTION
        public byte getFunctionCode() {
            return JSONB.Constants.BC_INT64_SHORT_ZERO;
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