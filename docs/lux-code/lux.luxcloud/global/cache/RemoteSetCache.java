package com.lux.luxcloud.global.cache;

import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class RemoteSetCache {
    private JSONObject result;
    private long time;

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public JSONObject getResult() {
        return this.result;
    }

    public void setResult(JSONObject jSONObject) {
        this.result = jSONObject;
    }
}