package com.nfcx.eg4.global.cache;

import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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