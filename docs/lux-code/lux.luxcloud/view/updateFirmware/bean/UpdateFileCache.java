package com.lux.luxcloud.view.updateFirmware.bean;

import com.lux.luxcloud.global.firmware.FIRMWARE_DEVICE_TYPE;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class UpdateFileCache implements Serializable {
    private Integer bmsHeaderId;
    private long crc32;
    private boolean doneDownload;
    private Integer fileHandleType;
    private String fileName;
    private long fileSize;
    private int fileType;
    private FIRMWARE_DEVICE_TYPE firmwareDeviceType;
    private String firmwareLengthArrayEncoded;
    private boolean isLuxVersion;
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

    public FIRMWARE_DEVICE_TYPE getFirmwareDeviceType() {
        return this.firmwareDeviceType;
    }

    public void setFirmwareDeviceType(FIRMWARE_DEVICE_TYPE firmware_device_type) {
        this.firmwareDeviceType = firmware_device_type;
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

    public Integer getBmsHeaderId() {
        return this.bmsHeaderId;
    }

    public void setBmsHeaderId(Integer num) {
        this.bmsHeaderId = num;
    }

    public boolean isLuxVersion() {
        return this.isLuxVersion;
    }

    public void setLuxVersion(boolean z) {
        this.isLuxVersion = z;
    }

    public Integer getFileHandleType() {
        return this.fileHandleType;
    }

    public void setFileHandleType(Integer num) {
        this.fileHandleType = num;
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

    public String getFirmwareLengthArrayEncoded() {
        return this.firmwareLengthArrayEncoded;
    }

    public void setFirmwareLengthArrayEncoded(String str) {
        this.firmwareLengthArrayEncoded = str;
    }

    public boolean isDoneDownload() {
        return this.doneDownload;
    }

    public void setDoneDownload(boolean z) {
        this.doneDownload = z;
    }
}