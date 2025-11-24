package com.lux.luxcloud.global.bean.inverter;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum BATTERY_TYPE {
    NO_BATTERY { // from class: com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE.1
        @Override // com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE
        public int getCode() {
            return 0;
        }

        @Override // com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE
        public String getResourceId() {
            return "phrase.battery.type.no.battery";
        }
    },
    LEAD_ACID { // from class: com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE.2
        @Override // com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE
        public int getCode() {
            return 1;
        }

        @Override // com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE
        public String getResourceId() {
            return "phrase.battery.type.lead.acid";
        }
    },
    LITHIUM { // from class: com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE.3
        @Override // com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE
        public int getCode() {
            return 2;
        }

        @Override // com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE
        public String getResourceId() {
            return "phrase.battery.type.lithium";
        }
    };

    public abstract int getCode();

    public abstract String getResourceId();

    public static BATTERY_TYPE getBatteryTypeByCode(int i) {
        for (BATTERY_TYPE battery_type : values()) {
            if (battery_type.getCode() == i) {
                return battery_type;
            }
        }
        return null;
    }
}