package com.lux.luxcloud.view.updateFirmware.bean;

import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.tool.Tool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class StandardUploadCheck {
    private boolean _12KNoPowerOffUpdateAllowed;
    private Integer bootloaderVersion;
    protected Integer deviceType;
    private Integer lastV1;
    private Integer lastV2;
    private Integer m3Version;
    private Integer machineType;
    private boolean needRunStep2;
    private boolean needRunStep3;
    private boolean needRunStep4;
    private boolean needRunStep5;
    private boolean pcs1ForM3Upldate;
    private boolean pcs1UpdateMatch;
    private boolean pcs2UpdateMatch;
    private Integer phase;
    private boolean runStep2024Wait;
    private String standard;
    private UpdateFileCache step5StandardUploadRecord;
    private String stepFirmwareTypePrefix;
    private Integer v1;
    private Integer v2;
    private Integer v3;

    public void fillByInverter(Inverter inverter) throws NumberFormatException {
        setPhase(inverter.getPhase());
        setDeviceType(inverter.getDeviceType());
        setMachineType(Integer.valueOf(inverter.getMachineType()));
        String fwCode = inverter.getFwCode();
        boolean z = false;
        setStandard(fwCode.substring(0, 4));
        if (inverter.isLsp()) {
            setV1(Integer.valueOf(Integer.parseInt(fwCode.substring(9, 11), 16)));
            setV2(Integer.valueOf(Integer.parseInt(fwCode.substring(7, 9), 16)));
            setV3(Integer.valueOf(Integer.parseInt(fwCode.substring(5, 7), 16)));
        } else if (inverter.isOffGrid()) {
            setV1(Integer.valueOf(Integer.parseInt(fwCode.substring(7, 9), 16)));
            setV2(Integer.valueOf(Integer.parseInt(fwCode.substring(9, 11), 16)));
            setV3(Integer.valueOf(Integer.parseInt(fwCode.substring(5, 7), 16)));
        } else if (inverter.isType6()) {
            int i = Integer.parseInt(fwCode.substring(5, 7), 16);
            int i2 = Integer.parseInt(fwCode.substring(7, 9), 16);
            if (!Tool.isEmpty(this.standard) && this.standard.endsWith("B") && i >= 24 && i2 >= 24) {
                z = true;
            }
            this._12KNoPowerOffUpdateAllowed = z;
            setV1(Integer.valueOf(z ? i : i2));
            if (this._12KNoPowerOffUpdateAllowed) {
                i = i2;
            }
            setV2(Integer.valueOf(i));
        } else {
            setV1(Integer.valueOf(Integer.parseInt(fwCode.substring(5, 7), 16)));
            setV2(Integer.valueOf(Integer.parseInt(fwCode.substring(7, 9), 16)));
        }
        if (inverter.isType6()) {
            this.m3Version = check12KNoPowerOffUpdateAllowed() ? this.v1 : this.v2;
        } else if (inverter.isLsp() || inverter.isGen3_6K() || inverter.isTrip12K()) {
            this.m3Version = this.v2;
        } else {
            this.m3Version = this.v1;
        }
    }

    public boolean needRunStep2024() {
        Integer num = this.deviceType;
        if (num == null) {
            return false;
        }
        if (num.intValue() == 6 || this.deviceType.intValue() == 7 || this.deviceType.intValue() == 8) {
            return this.needRunStep2 || this.needRunStep3 || this.needRunStep4 || this.needRunStep5;
        }
        return false;
    }

    public boolean check12KNoPowerOffUpdateAllowed() {
        return this._12KNoPowerOffUpdateAllowed;
    }

    public boolean isAllowTotallyUpdate() {
        return this.pcs1ForM3Upldate && this.pcs2UpdateMatch;
    }

    public Integer getM3Version() {
        return this.m3Version;
    }

    public Integer getPhase() {
        return this.phase;
    }

    public void setPhase(Integer num) {
        this.phase = num;
    }

    public Integer getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(Integer num) {
        this.deviceType = num;
    }

    public Integer getMachineType() {
        return this.machineType;
    }

    public void setMachineType(Integer num) {
        this.machineType = num;
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

    public boolean isPcs1UpdateMatch() {
        return this.pcs1UpdateMatch;
    }

    public void setPcs1UpdateMatch(boolean z) {
        this.pcs1UpdateMatch = z;
    }

    public Integer getLastV1() {
        return this.lastV1;
    }

    public void setLastV1(Integer num) {
        this.lastV1 = num;
    }

    public boolean isPcs1ForM3Upldate() {
        return this.pcs1ForM3Upldate;
    }

    public void setPcs1ForM3Upldate(boolean z) {
        this.pcs1ForM3Upldate = z;
    }

    public boolean isPcs2UpdateMatch() {
        return this.pcs2UpdateMatch;
    }

    public void setPcs2UpdateMatch(boolean z) {
        this.pcs2UpdateMatch = z;
    }

    public Integer getLastV2() {
        return this.lastV2;
    }

    public void setLastV2(Integer num) {
        this.lastV2 = num;
    }

    public String getStepFirmwareTypePrefix() {
        return this.stepFirmwareTypePrefix;
    }

    public void setStepFirmwareTypePrefix(String str) {
        this.stepFirmwareTypePrefix = str;
    }

    public boolean isRunStep2024Wait() {
        return this.runStep2024Wait;
    }

    public void setRunStep2024Wait(boolean z) {
        this.runStep2024Wait = z;
    }

    public Integer getBootloaderVersion() {
        return this.bootloaderVersion;
    }

    public void setBootloaderVersion(Integer num) {
        this.bootloaderVersion = num;
    }

    public boolean isNeedRunStep2() {
        return this.needRunStep2;
    }

    public void setNeedRunStep2(boolean z) {
        this.needRunStep2 = z;
    }

    public boolean isNeedRunStep3() {
        return this.needRunStep3;
    }

    public void setNeedRunStep3(boolean z) {
        this.needRunStep3 = z;
    }

    public boolean isNeedRunStep4() {
        return this.needRunStep4;
    }

    public void setNeedRunStep4(boolean z) {
        this.needRunStep4 = z;
    }

    public boolean isNeedRunStep5() {
        return this.needRunStep5;
    }

    public void setNeedRunStep5(boolean z) {
        this.needRunStep5 = z;
    }

    public UpdateFileCache getStep5StandardUploadRecord() {
        return this.step5StandardUploadRecord;
    }

    public void setStep5StandardUploadRecord(UpdateFileCache updateFileCache) {
        this.step5StandardUploadRecord = updateFileCache;
    }
}