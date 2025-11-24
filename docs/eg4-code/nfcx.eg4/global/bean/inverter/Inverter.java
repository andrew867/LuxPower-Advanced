package com.nfcx.eg4.global.bean.inverter;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.material.card.MaterialCardViewHelper;
import com.google.firebase.messaging.ServiceStarter;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.tool.Tool;
import java.io.Serializable;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Inverter implements Serializable {
    private boolean allowExport2Grid;
    private boolean allowGenExercise;
    private Integer batCurrUnit;
    private BATTERY_TYPE batteryTypeFromModel;
    private String datalogSn;
    private Integer deviceType;
    private Integer dtc;
    private String fwCode;
    private Integer fwVersion;
    private int hardwareVersion;
    private Integer leadAcidType;
    private int machineType;
    private String masterInverterSn;
    private Long model;
    private String parallelFirstDeviceSn;
    private String parallelGroup;
    private Integer phase;
    private Long plantId;
    private Integer powerRating;
    private Integer protocolVersion;
    private String serialNum;
    private Integer slaveVersion;
    private String standard;
    private Integer subDeviceType;
    private Integer usVersion;
    private Boolean withbatteryData;

    public boolean needCheckHoldBeforeStandardUpdate() {
        return isType6();
    }

    public boolean supportRead127Register() {
        Integer num = this.protocolVersion;
        return num != null && num.intValue() >= 4;
    }

    public boolean isBatCurrUnit10() {
        if (isAcCharger() || isTrip12K()) {
            return false;
        }
        if (isType6() || isAllInOne() || is7_10KDevice()) {
            return true;
        }
        Integer num = this.batCurrUnit;
        return num != null && num.intValue() == 1;
    }

    public void checkSlaveVersion() {
        if (this.slaveVersion != null || Tool.isEmpty(this.fwCode) || this.fwCode.length() < 9) {
            return;
        }
        try {
            this.slaveVersion = Integer.valueOf(Integer.parseInt(this.fwCode.substring(5, 7), 16));
        } catch (Exception unused) {
        }
    }

    public void checkFwVersion() {
        if (this.fwVersion != null || Tool.isEmpty(this.fwCode) || this.fwCode.length() < 9) {
            return;
        }
        try {
            this.fwVersion = Integer.valueOf(Integer.parseInt(this.fwCode.substring(7, 9), 16));
        } catch (Exception unused) {
        }
    }

    public boolean checkAllowGenExercise() {
        Integer num;
        if (isType6() && !Tool.isEmpty(this.standard) && (this.standard.equals("eAAB") || this.standard.equals("EAAB") || this.standard.equals("fAAB") || this.standard.equals("FAAB"))) {
            checkSlaveVersion();
            checkFwVersion();
            Integer num2 = this.slaveVersion;
            if (num2 != null && num2.intValue() >= 24 && (num = this.fwVersion) != null && num.intValue() >= 24) {
                return true;
            }
        }
        if (isTrip12K() || isHybird()) {
            return true;
        }
        int iIntValue = getDtcValue().intValue();
        if (isOffGrid() && (("cbaa".equalsIgnoreCase(this.standard) && this.slaveVersion.intValue() >= 36) || ((("cBaa".equals(this.standard) || "cBAA".equals(this.standard)) && this.slaveVersion.intValue() >= 141) || (iIntValue == 1 && "ccaa".equalsIgnoreCase(this.standard) && this.slaveVersion.intValue() >= 18)))) {
            return true;
        }
        if (isSna12K()) {
            return (iIntValue == 1 && "ceaa".equalsIgnoreCase(this.standard) && this.slaveVersion.intValue() >= 6) || ("cfaa".equalsIgnoreCase(this.standard) && this.slaveVersion.intValue() >= 6);
        }
        return false;
    }

    public boolean isPhase3() {
        Integer num = this.phase;
        return num != null && num.intValue() == 3;
    }

    public boolean isHybird() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 0;
    }

    public boolean isSna12K() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 11;
    }

    public boolean isSnaSeries() {
        return isOffGrid() || isSna12K();
    }

    public boolean isType6Series() {
        return isType6() || isAllInOne() || is7_10KDevice() || isGen3_6K() || isTrip12K();
    }

    public boolean isType6SeriesWithoutTrip12K() {
        return isType6() || isAllInOne() || is7_10KDevice() || isGen3_6K();
    }

    public boolean isGen3_6K() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 10;
    }

    public boolean is7_10KDevice() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 8;
    }

    public boolean isAllInOne() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 7;
    }

    public boolean isTrip12K() {
        Integer num;
        Integer num2 = this.phase;
        return num2 != null && num2.intValue() == 3 && (num = this.deviceType) != null && num.intValue() == 0;
    }

    public boolean is12KUsVersion() {
        Integer num;
        return isType6() && (num = this.usVersion) != null && num.intValue() == Constants.MODEL_US_VERSION_US;
    }

    public boolean isAcCharger() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 2;
    }

    public boolean isOffGrid() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 3;
    }

    public boolean isEcoBeast6k() {
        return isOffGrid() && !isDtcAmerica() && this.machineType == 4;
    }

    public boolean isSna6kUsAio() {
        return isOffGrid() && isDtcAmerica() && this.machineType == 4;
    }

    public boolean isMidBox() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 9;
    }

    public boolean isLsp() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 5;
    }

    public boolean isType6() {
        Integer num = this.deviceType;
        return num != null && num.intValue() == 6;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }

    public String getDatalogSn() {
        return this.datalogSn;
    }

    public void setDatalogSn(String str) {
        this.datalogSn = str;
    }

    public Long getPlantId() {
        return this.plantId;
    }

    public void setPlantId(Long l) {
        this.plantId = l;
    }

    public Integer getPhase() {
        return this.phase;
    }

    public Integer getPhaseValue() {
        Integer num = this.phase;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setPhase(Integer num) {
        this.phase = num;
    }

    public Integer getDeviceType() {
        return this.deviceType;
    }

    public int getDeviceTypeValue() {
        Integer num = this.deviceType;
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public int getSubDeviceTypeValue() {
        Integer num = this.subDeviceType;
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public void setSubDeviceType(Integer num) {
        this.subDeviceType = num;
    }

    public Integer getDtc() {
        return this.dtc;
    }

    public boolean isDtcAmerica() {
        Integer num = this.dtc;
        return num != null && num.intValue() == 1;
    }

    public Integer getDtcValue() {
        Integer num = this.dtc;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setDtc(Integer num) {
        this.dtc = num;
    }

    public Integer getPowerRating() {
        return this.powerRating;
    }

    public void setPowerRating(Integer num) {
        this.powerRating = num;
    }

    public Integer getUsVersion() {
        return this.usVersion;
    }

    public void setUsVersion(Integer num) {
        this.usVersion = num;
    }

    public boolean isAllowExport2Grid() {
        return this.allowExport2Grid;
    }

    public void setAllowExport2Grid(boolean z) {
        this.allowExport2Grid = z;
    }

    public boolean isAllowGenExercise() {
        return this.allowGenExercise;
    }

    public void setAllowGenExercise(boolean z) {
        this.allowGenExercise = z;
    }

    public Boolean getWithbatteryData() {
        return this.withbatteryData;
    }

    public void setWithbatteryData(Boolean bool) {
        this.withbatteryData = bool;
    }

    public int getHardwareVersion() {
        return this.hardwareVersion;
    }

    public void setHardwareVersion(int i) {
        this.hardwareVersion = i;
    }

    public int getMachineType() {
        return this.machineType;
    }

    public void setMachineType(int i) {
        this.machineType = i;
    }

    public void setDeviceType(Integer num) {
        this.deviceType = num;
    }

    public String getFwCode() {
        return this.fwCode;
    }

    public void setFwCode(String str) {
        this.fwCode = str;
    }

    public Long getModel() {
        return this.model;
    }

    public void setModel(Long l) {
        this.model = l;
    }

    public BATTERY_TYPE getBatteryTypeFromModel() {
        return this.batteryTypeFromModel;
    }

    public void setBatteryTypeFromModel(BATTERY_TYPE battery_type) {
        this.batteryTypeFromModel = battery_type;
    }

    public Integer getLeadAcidType() {
        return this.leadAcidType;
    }

    public void setLeadAcidType(Integer num) {
        this.leadAcidType = num;
    }

    public Integer getLeadAcidCapacity() {
        Integer num = this.leadAcidType;
        if (num == null) {
            return null;
        }
        switch (num.intValue()) {
            case 0:
                return 50;
            case 1:
                return 100;
            case 2:
                return Integer.valueOf(CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA);
            case 3:
                return 200;
            case 4:
                return Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            case 5:
                return Integer.valueOf(MaterialCardViewHelper.DEFAULT_FADE_ANIM_DURATION);
            case 6:
                return 350;
            case 7:
                return 400;
            case 8:
                return 450;
            case 9:
                return Integer.valueOf(ServiceStarter.ERROR_UNKNOWN);
            case 10:
                return 550;
            case 11:
                return 600;
            case 12:
                return 650;
            default:
                return null;
        }
    }

    public boolean isParallelGroup() {
        return !Tool.isEmpty(this.parallelGroup);
    }

    public void setParallelGroup(String str) {
        this.parallelGroup = str;
    }

    public String getParallelFirstDeviceSn() {
        return this.parallelFirstDeviceSn;
    }

    public void setParallelFirstDeviceSn(String str) {
        this.parallelFirstDeviceSn = str;
    }

    public Integer getBatCurrUnit() {
        return this.batCurrUnit;
    }

    public void setBatCurrUnit(Integer num) {
        this.batCurrUnit = num;
    }

    public Integer getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(Integer num) {
        this.protocolVersion = num;
    }

    public String getStandard() {
        return this.standard;
    }

    public void setStandard(String str) {
        this.standard = str;
    }

    public Integer getSlaveVersion() {
        return this.slaveVersion;
    }

    public int getSlaveVersionValue() {
        Integer num = this.slaveVersion;
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public void setSlaveVersion(Integer num) {
        this.slaveVersion = num;
    }

    public Integer getFwVersion() {
        return this.fwVersion;
    }

    public int getFwVersionValue() {
        Integer num = this.fwVersion;
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public void setFwVersion(Integer num) {
        this.fwVersion = num;
    }

    public String getMasterInverterSn() {
        return this.masterInverterSn;
    }

    public void setMasterInverterSn(String str) {
        this.masterInverterSn = str;
    }

    public String toString() {
        return this.serialNum;
    }
}