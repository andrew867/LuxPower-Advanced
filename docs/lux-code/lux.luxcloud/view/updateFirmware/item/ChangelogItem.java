package com.lux.luxcloud.view.updateFirmware.item;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ChangelogItem {
    private String createTime;
    private String description;
    private String fwCode;

    public void setFwCode(String str) {
        this.fwCode = str;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getFwCode() {
        return this.fwCode;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public String getDescription() {
        return this.description;
    }
}