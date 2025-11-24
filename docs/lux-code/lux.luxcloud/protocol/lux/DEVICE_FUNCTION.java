package com.lux.luxcloud.protocol.lux;

import org.bouncycastle.tls.CipherSuite;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum DEVICE_FUNCTION {
    R_HOLD { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.1
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 3;
        }
    },
    R_HOLD_ERROR { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.2
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 131;
        }
    },
    R_INPUT { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.3
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 4;
        }
    },
    R_INPUT_ERROR { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.4
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 132;
        }
    },
    W_SINGLE { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.5
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 6;
        }
    },
    W_SINGLE_ERROR { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.6
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA;
        }
    },
    W_MULTI { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.7
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 16;
        }
    },
    W_MULTI_ERROR { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.8
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA;
        }
    },
    UPDATE_PREPARE { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.9
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 33;
        }
    },
    UPDATE_SEND_DATA { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.10
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 34;
        }
    },
    UPDATE_RESET { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.11
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 35;
        }
    },
    LUX_UPDATE_PREPARE { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.12
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 49;
        }
    },
    LUX_UPDATE_SEND_DATA { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.13
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 50;
        }
    },
    LUX_UPDATE_RESET { // from class: com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION.14
        @Override // com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION
        public int getFunctionCode() {
            return 51;
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