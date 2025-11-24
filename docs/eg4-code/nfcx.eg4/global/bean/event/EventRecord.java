package com.nfcx.eg4.global.bean.event;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class EventRecord {
    private String eventText;
    private String eventTypeText;
    private String plantName;
    private long recordId;
    private String renormalTime;
    private String serialNum;
    private String startTime;

    public long getRecordId() {
        return this.recordId;
    }

    public void setRecordId(long j) {
        this.recordId = j;
    }

    public String getPlantName() {
        return this.plantName;
    }

    public void setPlantName(String str) {
        this.plantName = str;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }

    public String getEventTypeText() {
        return this.eventTypeText;
    }

    public void setEventTypeText(String str) {
        this.eventTypeText = str;
    }

    public String getEventText() {
        return this.eventText;
    }

    public void setEventText(String str) {
        this.eventText = str;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getRenormalTime() {
        return this.renormalTime;
    }

    public void setRenormalTime(String str) {
        this.renormalTime = str;
    }
}