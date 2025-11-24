package com.nfcx.eg4.global.firmware;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public enum FIRMWARE_DEVICE_TYPE {
    LXP_LB_8_12K { // from class: com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE.1
        @Override // com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "18KPV-12LV";
        }
    },
    SNA_US_6000 { // from class: com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE.2
        @Override // com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "6000XP";
        }
    },
    SNA_US_12K { // from class: com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE.3
        @Override // com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "12000XP";
        }
    },
    POWER_HUB { // from class: com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE.4
        @Override // com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Grid Boss";
        }
    },
    DONGLE_E_WIFI_DONGLE { // from class: com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE.5
        @Override // com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "E-WiFi Dongle";
        }
    };

    public static String getEnumWithoutExt(String str) {
        return str;
    }

    public abstract String getText();

    public static FIRMWARE_DEVICE_TYPE getEnumByName(String str) {
        for (FIRMWARE_DEVICE_TYPE firmware_device_type : values()) {
            if (firmware_device_type.name().equals(str)) {
                return firmware_device_type;
            }
        }
        return null;
    }

    public static String getEnumByText(String str) {
        for (FIRMWARE_DEVICE_TYPE firmware_device_type : values()) {
            if (firmware_device_type.getText().equals(str)) {
                return firmware_device_type.toString();
            }
        }
        return null;
    }
}