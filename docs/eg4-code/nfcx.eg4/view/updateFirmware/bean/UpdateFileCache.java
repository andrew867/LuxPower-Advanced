package com.nfcx.eg4.view.updateFirmware.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class UpdateFileCache implements Serializable {
    private long crc32;
    private boolean doneDownload;
    private String fileName;
    private long fileSize;
    private int fileType;
    private String recordId;
    private String tailEncoded;
    private String standard = "";
    private Integer v1 = -1;
    private Integer v2 = -1;
    private Integer v3 = -1;
    private Map<Integer, Integer> physicalAddr = new HashMap();
    private Map<Integer, String> firmware = new HashMap();

    public Integer getV(int i) {
        if (i == 1) {
            return this.v1;
        }
        if (i == 2) {
            return this.v2;
        }
        if (i != 3) {
            return null;
        }
        return this.v3;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String str) {
        this.recordId = str;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public int getFileType() {
        return this.fileType;
    }

    public void setFileType(int i) {
        this.fileType = i;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long j) {
        this.fileSize = j;
    }

    public String getStandard() {
        return this.standard;
    }

    public void setStandard(String str) {
        this.standard = str;
    }

    public Integer getV1() {
        return this.v1;
    }

    public void setV1(Integer num) {
        this.v1 = num;
    }

    public Integer getV2() {
        return this.v2;
    }

    public void setV2(Integer num) {
        this.v2 = num;
    }

    public Integer getV3() {
        return this.v3;
    }

    public void setV3(Integer num) {
        this.v3 = num;
    }

    public long getCrc32() {
        return this.crc32;
    }

    public void setCrc32(long j) {
        this.crc32 = j;
    }

    public Map<Integer, Integer> getPhysicalAddr() {
        return this.physicalAddr;
    }

    public Map<Integer, String> getFirmware() {
        return this.firmware;
    }

    public String getTailEncoded() {
        return this.tailEncoded;
    }

    public void setTailEncoded(String str) {
        this.tailEncoded = str;
    }

    public boolean isDoneDownload() {
        return this.doneDownload;
    }

    public void setDoneDownload(boolean z) {
        this.doneDownload = z;
    }
}