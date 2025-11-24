package com.nfcx.eg4.view.updateFirmware.item;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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