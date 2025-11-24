package com.nfcx.eg4.global.bean.inverter;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Model {
    private int batteryType;
    private int leadAcidType;
    private int lithiumType;
    private int measurement;
    private int meterBrand;
    private int meterType;
    private int powerRating;
    private int powerRatingBit3;
    private int rule;
    private int ruleMask;
    private int usVersion;
    private int wirelessMeter;

    public Model() {
    }

    public Model(long j) {
        this.rule = (int) (j & 31);
        long j2 = j >> 5;
        this.powerRating = (int) (7 & j2);
        long j3 = j2 >> 3;
        int i = (int) (j3 & 3);
        this.batteryType = i;
        long j4 = j3 >> 2;
        if (i == 1) {
            this.leadAcidType = (int) (31 & j4);
        } else if (i == 2) {
            this.lithiumType = (int) (31 & j4);
        }
        long j5 = j4 >> 5;
        this.ruleMask = (int) (j5 & 1);
        long j6 = j5 >> 1;
        this.measurement = (int) (j6 & 1);
        long j7 = j6 >> 1;
        this.meterBrand = (int) (3 & j7);
        long j8 = j7 >> 2;
        this.usVersion = (int) (j8 & 1);
        long j9 = j8 >> 1;
        this.meterType = (int) (j9 & 1);
        long j10 = (j9 >> 1) >> 3;
        this.powerRatingBit3 = (int) (j10 & 1);
        this.wirelessMeter = (int) ((j10 >> 1) & 1);
    }

    public int getWirelessMeter() {
        return this.wirelessMeter;
    }

    public String getWirelessMeterText() {
        int i = this.wirelessMeter;
        return i != 0 ? i != 1 ? "" : "Enable" : "Disable";
    }

    public void setWirelessMeter(int i) {
        this.wirelessMeter = i;
    }

    public int getMeterType() {
        return this.meterType;
    }

    public String getMeterTypeText() {
        int i = this.meterType;
        return i != 0 ? i != 1 ? "" : "3 Phase Meter" : "1 Phase Meter";
    }

    public void setMeterType(int i) {
        this.meterType = i;
    }

    public int getUsVersion() {
        return this.usVersion;
    }

    public String getUsVersionText() {
        int i = this.usVersion;
        return i != 0 ? i != 1 ? "" : "US Version" : "Standard Version";
    }

    public void setUsVersion(int i) {
        this.usVersion = i;
    }

    public int getMeterBrand() {
        return this.meterBrand;
    }

    public String getMeterBrandText() {
        int i = this.meterBrand;
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "" : "Chint" : "Rsvd" : "WattNode/Rsvd" : "Eastron";
    }

    public void setMeterBrand(int i) {
        this.meterBrand = i;
    }

    public int getMeasurement() {
        return this.measurement;
    }

    public String getMeasurementText() {
        int i = this.measurement;
        return i != 0 ? i != 1 ? "" : "CT" : "Meter";
    }

    public void setMeasurement(int i) {
        this.measurement = i;
    }

    public int getRuleMask() {
        return this.ruleMask;
    }

    public String getRuleMaskText() {
        int i = this.ruleMask;
        return i != 0 ? i != 1 ? "" : "Easy" : "Strict";
    }

    public void setRuleMask(int i) {
        this.ruleMask = i;
    }

    public int getBatteryType() {
        return this.batteryType;
    }

    public String getBatteryTypeText() {
        int i = this.batteryType;
        return i != 0 ? i != 1 ? i != 2 ? "" : "Lithium" : "Lead-acid" : "No battery";
    }

    public void setBatteryType(int i) {
        this.batteryType = i;
    }

    public int getLeadAcidType() {
        return this.leadAcidType;
    }

    public String getLeadAcidTypeText() {
        switch (this.leadAcidType) {
            case 0:
                return "50Ah";
            case 1:
                return "100Ah";
            case 2:
                return "150Ah";
            case 3:
                return "200Ah";
            case 4:
                return "250Ah";
            case 5:
                return "300Ah";
            case 6:
                return "350Ah";
            case 7:
                return "400Ah";
            case 8:
                return "450Ah";
            case 9:
                return "500Ah";
            case 10:
                return "550Ah";
            case 11:
                return "600Ah";
            case 12:
                return "650Ah";
            default:
                return "";
        }
    }

    public void setLeadAcidType(int i) {
        this.leadAcidType = i;
    }

    public int getLithiumType() {
        return this.lithiumType;
    }

    public void setLithiumType(int i) {
        this.lithiumType = i;
    }

    public int getWholePowerRating() {
        return (this.powerRatingBit3 << 3) + this.powerRating;
    }

    public void setWholePowerRating(int i) {
        this.powerRating = i & 7;
        this.powerRatingBit3 = (i >> 3) & 1;
    }

    public int getRule() {
        return this.rule;
    }

    public void setRule(int i) {
        this.rule = i;
    }

    public long getModel(long j) {
        int i;
        int i2 = this.batteryType;
        long j2 = this.rule + (this.powerRating << 5) + (i2 << 8);
        if (i2 == 1) {
            i = this.leadAcidType;
        } else {
            if (i2 == 2) {
                i = this.lithiumType;
            }
            return j2 + (this.ruleMask << 15) + (this.measurement << 16) + (this.meterBrand << 17) + (this.usVersion << 19) + (this.meterType << 20) + (((j >> 21) & 7) << 21) + (this.powerRatingBit3 << 24) + (this.wirelessMeter << 25) + (j & (-67108864));
        }
        j2 += i << 10;
        return j2 + (this.ruleMask << 15) + (this.measurement << 16) + (this.meterBrand << 17) + (this.usVersion << 19) + (this.meterType << 20) + (((j >> 21) & 7) << 21) + (this.powerRatingBit3 << 24) + (this.wirelessMeter << 25) + (j & (-67108864));
    }
}