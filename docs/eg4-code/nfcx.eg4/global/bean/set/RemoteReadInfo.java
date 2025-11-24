package com.nfcx.eg4.global.bean.set;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class RemoteReadInfo {
    private int pointNumber;
    private String serialNum;
    private int startRegister;

    public RemoteReadInfo(String str, int i, int i2) {
        this.serialNum = str;
        this.startRegister = i;
        this.pointNumber = i2;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }

    public int getStartRegister() {
        return this.startRegister;
    }

    public void setStartRegister(int i) {
        this.startRegister = i;
    }

    public int getPointNumber() {
        return this.pointNumber;
    }

    public void setPointNumber(int i) {
        this.pointNumber = i;
    }
}