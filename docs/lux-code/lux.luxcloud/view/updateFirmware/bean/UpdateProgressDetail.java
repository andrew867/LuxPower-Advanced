package com.lux.luxcloud.view.updateFirmware.bean;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class UpdateProgressDetail {
    private String datalogSn;
    private int errorCount;
    private String inverterSn;
    private long lastTimeSendPackage;
    private int packageIndex;
    private boolean sendUpdateReset_0x23;
    private boolean sendUpdateStart_0x21;
    private Boolean totallyStandardUpdate;
    private UPDATE_STATUS updateStatus;

    public int getCurrentProgress() {
        boolean z = this.sendUpdateStart_0x21;
        return (z ? this.packageIndex - 1 : 0) + (this.sendUpdateReset_0x23 ? 1 : 0) + (z ? 1 : 0);
    }

    public String getInverterSn() {
        return this.inverterSn;
    }

    public void setInverterSn(String str) {
        this.inverterSn = str;
    }

    public String getDatalogSn() {
        return this.datalogSn;
    }

    public void setDatalogSn(String str) {
        this.datalogSn = str;
    }

    public boolean isSendUpdateStart_0x21() {
        return this.sendUpdateStart_0x21;
    }

    public void setSendUpdateStart_0x21(boolean z) {
        this.sendUpdateStart_0x21 = z;
    }

    public boolean isSendUpdateReset_0x23() {
        return this.sendUpdateReset_0x23;
    }

    public void setSendUpdateReset_0x23(boolean z) {
        this.sendUpdateReset_0x23 = z;
    }

    public Boolean getTotallyStandardUpdate() {
        return this.totallyStandardUpdate;
    }

    public boolean getTotallyStandardUpdateValue() {
        Boolean bool = this.totallyStandardUpdate;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public void setTotallyStandardUpdate(Boolean bool) {
        this.totallyStandardUpdate = bool;
    }

    public int getPackageIndex() {
        return this.packageIndex;
    }

    public void setPackageIndex(int i) {
        this.packageIndex = i;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(int i) {
        this.errorCount = i;
    }

    public UPDATE_STATUS getUpdateStatus() {
        return this.updateStatus;
    }

    public void setUpdateStatus(UPDATE_STATUS update_status) {
        this.updateStatus = update_status;
    }

    public long getLastTimeSendPackage() {
        return this.lastTimeSendPackage;
    }

    public void setLastTimeSendPackage(long j) {
        this.lastTimeSendPackage = j;
    }
}