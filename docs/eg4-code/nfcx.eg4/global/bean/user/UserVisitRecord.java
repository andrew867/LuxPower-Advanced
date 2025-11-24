package com.nfcx.eg4.global.bean.user;

import java.io.Serializable;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class UserVisitRecord implements Serializable {
    private Long plantId;
    private String serialNum;

    public Long getPlantId() {
        return this.plantId;
    }

    public void setPlantId(Long l) {
        this.plantId = l;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }
}