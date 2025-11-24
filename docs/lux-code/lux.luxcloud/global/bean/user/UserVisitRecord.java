package com.lux.luxcloud.global.bean.user;

import com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE;
import java.io.Serializable;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class UserVisitRecord implements Serializable {
    private BATTERY_TYPE batteryTypeFromModel;
    private Integer deviceType;
    private Integer dtc;
    private Integer phase;
    private Long plantId;
    private String serialNum;
    private Integer subDeviceType;

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
        return -1;
    }

    public void setDeviceType(Integer num) {
        this.deviceType = num;
    }

    public Integer getSubDeviceType() {
        return this.subDeviceType;
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

    public boolean isDtcAmerica() {
        Integer num = this.dtc;
        return num != null && num.intValue() == 1;
    }

    public Integer getDtc() {
        return this.dtc;
    }

    public Integer getDtcValue() {
        Integer num = this.dtc;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setDtc(Integer num) {
        this.dtc = num;
    }

    public BATTERY_TYPE getBatteryTypeFromModel() {
        return this.batteryTypeFromModel;
    }

    public void setBatteryTypeFromModel(BATTERY_TYPE battery_type) {
        this.batteryTypeFromModel = battery_type;
    }
}