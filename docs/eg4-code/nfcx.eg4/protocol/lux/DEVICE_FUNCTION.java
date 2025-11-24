package com.nfcx.eg4.protocol.lux;

import com.nfcx.eg4.global.Constants;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public enum DEVICE_FUNCTION {
    R_HOLD { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.1
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 3;
        }
    },
    R_HOLD_ERROR { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.2
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return Constants.SUB_DEVICE_TYPE_OFF_GRID_US;
        }
    },
    R_INPUT { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.3
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 4;
        }
    },
    R_INPUT_ERROR { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.4
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA;
        }
    },
    W_SINGLE { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.5
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 6;
        }
    },
    W_SINGLE_ERROR { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.6
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA;
        }
    },
    W_MULTI { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.7
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 16;
        }
    },
    W_MULTI_ERROR { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.8
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA;
        }
    },
    UPDATE_PREPARE { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.9
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 33;
        }
    },
    UPDATE_SEND_DATA { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.10
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 34;
        }
    },
    UPDATE_RESET { // from class: com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION.11
        @Override // com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 35;
        }
    };

    public abstract int getFunctionCode();

    public static DEVICE_FUNCTION getDeviceFunction(int i) {
        for (DEVICE_FUNCTION device_function : values()) {
            if (device_function.getFunctionCode() == i) {
                return device_function;
            }
        }
        return null;
    }
}