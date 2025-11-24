package com.lux.luxcloud.global.bean.user;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum CHART_COLOR {
    STANDARD { // from class: com.lux.luxcloud.global.bean.user.CHART_COLOR.1
        @Override // com.lux.luxcloud.global.bean.user.CHART_COLOR
        public JSONObject getChartColors() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("Solar PV", "#70AD46");
            jSONObject.put("Battery", "#5A9BD5");
            jSONObject.put("Grid", "#F66867");
            jSONObject.put("Consumption", "#FFA461");
            jSONObject.put("SOC", "#800080");
            jSONObject.put("AC Couple/Generator", "#3B31F5");
            return jSONObject;
        }

        @Override // com.lux.luxcloud.global.bean.user.CHART_COLOR
        public JSONObject getEnergyChartColors() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("Solar Production", "#70AD46");
            jSONObject.put("Battery", "#5A9BD5");
            jSONObject.put("BattCharge", "#4026c6");
            jSONObject.put("Export to Grid", "#F66867");
            jSONObject.put("Import to User", "#996B1F");
            jSONObject.put("Consumption", "#FFA461");
            jSONObject.put("AC Couple/Generator", "#3B31F5");
            return jSONObject;
        }
    },
    DARK { // from class: com.lux.luxcloud.global.bean.user.CHART_COLOR.2
        @Override // com.lux.luxcloud.global.bean.user.CHART_COLOR
        public JSONObject getChartColors() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("Solar PV", "#3BAEDA");
            jSONObject.put("Battery", "#36BB99");
            jSONObject.put("Grid", "#BE0013");
            jSONObject.put("Consumption", "#F8BA42");
            jSONObject.put("SOC", "#D971AC");
            jSONObject.put("AC Couple/Generator", "#AC92ED");
            return jSONObject;
        }

        @Override // com.lux.luxcloud.global.bean.user.CHART_COLOR
        public JSONObject getEnergyChartColors() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("Solar Production", "#3BAEDA");
            jSONObject.put("Battery", "#36BB99");
            jSONObject.put("BattCharge", "#8CC051");
            jSONObject.put("Export to Grid", "#BE0013");
            jSONObject.put("Import to User", "#E2593A");
            jSONObject.put("Consumption", "#F8BA42");
            jSONObject.put("AC Couple/Generator", "#AC92ED");
            return jSONObject;
        }
    },
    LIGHT { // from class: com.lux.luxcloud.global.bean.user.CHART_COLOR.3
        @Override // com.lux.luxcloud.global.bean.user.CHART_COLOR
        public JSONObject getChartColors() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("Solar PV", "#6ABCEA");
            jSONObject.put("Battery", "#02B44D");
            jSONObject.put("Grid", "#D5A900");
            jSONObject.put("Consumption", "#FB8650");
            jSONObject.put("SOC", "#EB3937");
            jSONObject.put("AC Couple/Generator", "#4280F0");
            return jSONObject;
        }

        @Override // com.lux.luxcloud.global.bean.user.CHART_COLOR
        public JSONObject getEnergyChartColors() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("Solar Production", "#6ABCEA");
            jSONObject.put("Battery", "#02B44D");
            jSONObject.put("BattCharge", "#5AD38D");
            jSONObject.put("Export to Grid", "#D5A900");
            jSONObject.put("Import to User", "#FED402");
            jSONObject.put("Consumption", "#ED8450");
            jSONObject.put("AC Couple/Generator", "#4280F0");
            return jSONObject;
        }
    };

    public abstract JSONObject getChartColors() throws JSONException;

    public abstract JSONObject getEnergyChartColors() throws JSONException;

    public String getName() {
        return name();
    }
}