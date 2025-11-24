package com.lux.luxcloud.global.locale;

import com.lux.luxcloud.R;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum CONTINENT {
    ASIA { // from class: com.lux.luxcloud.global.locale.CONTINENT.1
        @Override // com.lux.luxcloud.global.locale.CONTINENT
        public int getTextResourceId() {
            return R.string.continent_asia;
        }
    },
    EUROPE { // from class: com.lux.luxcloud.global.locale.CONTINENT.2
        @Override // com.lux.luxcloud.global.locale.CONTINENT
        public int getTextResourceId() {
            return R.string.continent_europe;
        }
    },
    AFRICA { // from class: com.lux.luxcloud.global.locale.CONTINENT.3
        @Override // com.lux.luxcloud.global.locale.CONTINENT
        public int getTextResourceId() {
            return R.string.continent_africa;
        }
    },
    OCEANIA { // from class: com.lux.luxcloud.global.locale.CONTINENT.4
        @Override // com.lux.luxcloud.global.locale.CONTINENT
        public int getTextResourceId() {
            return R.string.continent_oceania;
        }
    },
    NORTH_AMERICA { // from class: com.lux.luxcloud.global.locale.CONTINENT.5
        @Override // com.lux.luxcloud.global.locale.CONTINENT
        public int getTextResourceId() {
            return R.string.continent_north_america;
        }
    },
    SOUTH_AMERICA { // from class: com.lux.luxcloud.global.locale.CONTINENT.6
        @Override // com.lux.luxcloud.global.locale.CONTINENT
        public int getTextResourceId() {
            return R.string.continent_south_america;
        }
    };

    public abstract int getTextResourceId();

    public String getName() {
        return name();
    }
}