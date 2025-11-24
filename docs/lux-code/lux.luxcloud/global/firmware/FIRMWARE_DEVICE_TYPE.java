package com.lux.luxcloud.global.firmware;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum FIRMWARE_DEVICE_TYPE {
    SNA_3000_6000 { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.1
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "SNA 3000-6000";
        }
    },
    SNA_US_6000 { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.2
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "SNA-US 6000";
        }
    },
    SNA_12K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.3
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "SNA 12K";
        }
    },
    SNA_US_12K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.4
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "SNA-US 12K";
        }
    },
    LXP_3_6K_HYBRID_STANDARD { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.5
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LXP-3-6K Hybrid (Standard)";
        }
    },
    LXP_3_6K_HYBRID_PARALLEL { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.6
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LXP-3-6K Hybrid (Parallel)";
        }
    },
    LXP_3600_ACS_STANDARD { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.7
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LXP_3600 ACS (Standard)";
        }
    },
    LXP_3600_ACS_PARALLEL { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.8
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LXP_3600 ACS (Parallel)";
        }
    },
    LXP_LB_8_12K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.9
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LXP-LB-8-12K";
        }
    },
    LSP_100K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.10
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LSP-100K";
        }
    },
    LXP_HV_6K_HYBRID { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.11
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LXP-HV-6K Hybrid";
        }
    },
    Lite_Stor { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.12
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "LiteStor";
        }
    },
    TRIP_HB_EU_6_20K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.13
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "TRIP 6-30K";
        }
    },
    TRIP_LV_5_20K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.14
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "TRIP-LV 5-20K";
        }
    },
    GEN_LB_EU_3_6K { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.15
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "GEN-LB-EU 3-6K";
        }
    },
    GEN_LB_EU_7_10K_GST { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.16
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "GEN-LB-EU 7-10K";
        }
    },
    POWER_HUB { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.17
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "PowerHub";
        }
    },
    BATT_hi_5_v1 { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.18
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Hi-5 GEN 1";
        }
    },
    BATT_hi_5_v2 { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.19
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Hi-5 GEN 2 / Li 5";
        }
    },
    BATT_power_gem { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.20
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Power GEM / PGEM";
        }
    },
    BATT_power_gem_plus { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.21
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Power GEM Plus / PGEM PRO";
        }
    },
    BATT_j_of_10kWh { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.22
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - J-OF 10kWh / LiteStor";
        }
    },
    BATT_eco_beast { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.23
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Eco Beast";
        }
    },
    BATT_p_shield { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.24
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - P SHIELD";
        }
    },
    BATT_p_shield_max { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.25
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - PowerShield Max / PSHIELD MAX";
        }
    },
    BATT_power_stack { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.26
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Power Stack / PSTACK";
        }
    },
    BATT_c14 { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.27
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - C14";
        }
    },
    BATT_power_gem_max { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.28
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - Powergem Max / PGEMMAX";
        }
    },
    BATT_e0b_Hi_Li { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.29
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "Batt - E0B-100 / Hi-11.8(GEN3) / Li-11.8";
        }
    },
    DONGLE_E_WIFI_DONGLE { // from class: com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE.30
        @Override // com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE
        public String getText() {
            return "E-WiFi Dongle";
        }
    };

    public abstract String getText();

    public static FIRMWARE_DEVICE_TYPE getEnumByName(String str) {
        for (FIRMWARE_DEVICE_TYPE firmware_device_type : values()) {
            if (firmware_device_type.name().equals(str)) {
                return firmware_device_type;
            }
        }
        return null;
    }

    public static String getEnumWithoutExt(String str) {
        return GEN_LB_EU_7_10K_GST.name().equals(str) ? "GEN_LB_EU_7_10K" : str;
    }
}