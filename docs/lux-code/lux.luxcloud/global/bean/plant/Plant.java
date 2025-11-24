package com.lux.luxcloud.global.bean.plant;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Plant implements Serializable {
    private String address;
    private String countryText;
    private String createDateText;
    private String name;
    private Map<String, String> parallelGroups = new TreeMap();
    private long plantId;
    private int timezoneHourOffset;
    private int timezoneMinuteOffset;
    private String timezoneText;
    private String todayYieldingText;
    private String totalYieldingText;

    public long getPlantId() {
        return this.plantId;
    }

    public void setPlantId(long j) {
        this.plantId = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getTimezoneHourOffset() {
        return this.timezoneHourOffset;
    }

    public void setTimezoneHourOffset(int i) {
        this.timezoneHourOffset = i;
    }

    public int getTimezoneMinuteOffset() {
        return this.timezoneMinuteOffset;
    }

    public void setTimezoneMinuteOffset(int i) {
        this.timezoneMinuteOffset = i;
    }

    public String getCreateDateText() {
        return this.createDateText;
    }

    public void setCreateDateText(String str) {
        this.createDateText = str;
    }

    public String getCountryText() {
        return this.countryText;
    }

    public void setCountryText(String str) {
        this.countryText = str;
    }

    public String getTimezoneText() {
        return this.timezoneText;
    }

    public void setTimezoneText(String str) {
        this.timezoneText = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getTodayYieldingText() {
        return this.todayYieldingText;
    }

    public void setTodayYieldingText(String str) {
        this.todayYieldingText = str;
    }

    public String getTotalYieldingText() {
        return this.totalYieldingText;
    }

    public void setTotalYieldingText(String str) {
        this.totalYieldingText = str;
    }

    public Map<String, String> getParallelGroups() {
        return this.parallelGroups;
    }

    public void addParallelGroup(String str, String str2) {
        this.parallelGroups.put(str, str2);
    }

    public String toString() {
        return this.name;
    }
}