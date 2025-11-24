package com.lux.luxcloud.global;

import android.os.AsyncTask;
import com.lux.luxcloud.global.bean.dongle.Datalog;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.plant.Plant;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.user.CHART_COLOR;
import com.lux.luxcloud.global.bean.user.DATE_FORMAT;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.global.bean.user.TEMP_UNIT;
import com.lux.luxcloud.global.bean.user.UserVisitRecord;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class UserData implements Serializable {
    private String address;
    private boolean allowRemoteSupport;
    private boolean allowViewerVisitOptimalSet;
    private boolean allowViewerVisitWeatherSet;
    private CHART_COLOR chartColor;
    private long clusterId;
    private String countryText;
    private List<Property> countrys;
    private Date createTime;
    private int currentContinentIndex;
    private int currentCountryIndex;
    private Inverter currentInverter;
    private Inverter currentParallelGroup;
    private Plant currentPlant;
    private int currentRegionIndex;
    private String customerCode;
    private Datalog datalog;
    private DATE_FORMAT dateFormat;
    private String email;
    private String language;
    private Long parentUserId;
    private String password;
    private PLATFORM platform;
    private boolean readonly;
    private String realName;
    private List<Property> regions;
    private ROLE role;
    private JSONObject techInfo;
    private String telNumber;
    private TEMP_UNIT tempUnit;
    private String timezone;
    private String timezoneText;
    private String token;
    private String userChartRecord;
    private long userCreatedDays;
    private long userId;
    private UserVisitRecord userVisitRecord;
    private String username;
    private List<Plant> plants = new ArrayList();
    private Map<String, Inverter> inverterMap = new HashMap();
    private Map<Long, List<Inverter>> plantInverterMap = new HashMap();

    public void clear() {
        this.userVisitRecord = null;
        this.plants.clear();
        this.inverterMap.clear();
        this.plantInverterMap.clear();
    }

    public boolean needShowCompanyLogo() {
        return (this.platform == null || PLATFORM.MID.equals(this.platform) || !this.platform.equals(Custom.APP_USER_PLATFORM)) ? false : true;
    }

    public boolean isInstallerLevel() {
        ROLE role = this.role;
        return role != null && role.getInstallerLevelCheck();
    }

    public boolean isGigabiz1User() {
        if (this.userId == 13) {
            return true;
        }
        Long l = this.parentUserId;
        return l != null && l.longValue() == 13;
    }

    public long getUserVisitPlantId() {
        if (this.userVisitRecord == null) {
            return -1L;
        }
        Inverter userVisitInverter = getUserVisitInverter();
        return ((userVisitInverter == null || userVisitInverter.getPlantId() == null) ? this.userVisitRecord.getPlantId() : userVisitInverter.getPlantId()).longValue();
    }

    public Plant getPlant(long j) {
        for (Plant plant : this.plants) {
            if (plant.getPlantId() == j) {
                return plant;
            }
        }
        return null;
    }

    public Inverter getUserVisitInverter() {
        UserVisitRecord userVisitRecord = this.userVisitRecord;
        if (userVisitRecord == null || Tool.isEmpty(userVisitRecord.getSerialNum())) {
            return null;
        }
        return this.inverterMap.get(this.userVisitRecord.getSerialNum());
    }

    public UserVisitRecord getUserVisitRecord() {
        return this.userVisitRecord;
    }

    public void setUserVisitRecord(UserVisitRecord userVisitRecord) {
        this.userVisitRecord = userVisitRecord;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long j) {
        this.userId = j;
    }

    public Long getParentUserId() {
        return this.parentUserId;
    }

    public void setParentUserId(Long l) {
        this.parentUserId = l;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public ROLE getRole() {
        return this.role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String str) {
        this.realName = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getCustomerCode() {
        return this.customerCode;
    }

    public void setCustomerCode(String str) {
        this.customerCode = str;
    }

    public String getCountryText() {
        return this.countryText;
    }

    public void setCountryText(String str) {
        this.countryText = str;
    }

    public int getCurrentContinentIndex() {
        return this.currentContinentIndex;
    }

    public void setCurrentContinentIndex(int i) {
        this.currentContinentIndex = i;
    }

    public int getCurrentRegionIndex() {
        return this.currentRegionIndex;
    }

    public void setCurrentRegionIndex(int i) {
        this.currentRegionIndex = i;
    }

    public List<Property> getRegions() {
        return this.regions;
    }

    public void setRegions(List<Property> list) {
        this.regions = list;
    }

    public int getCurrentCountryIndex() {
        return this.currentCountryIndex;
    }

    public void setCurrentCountryIndex(int i) {
        this.currentCountryIndex = i;
    }

    public List<Property> getCountrys() {
        return this.countrys;
    }

    public void setCountrys(List<Property> list) {
        this.countrys = list;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public String getTimezoneText() {
        return this.timezoneText;
    }

    public void setTimezoneText(String str) {
        this.timezoneText = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getTelNumber() {
        return this.telNumber;
    }

    public void setTelNumber(String str) {
        this.telNumber = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public long getUserCreatedDays() {
        return this.userCreatedDays;
    }

    public void setUserCreatedDays(long j) {
        this.userCreatedDays = j;
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean z) {
        this.readonly = z;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date date) {
        this.createTime = date;
    }

    public PLATFORM getPlatform() {
        return this.platform;
    }

    public void setPlatform(PLATFORM platform) {
        this.platform = platform;
    }

    public CHART_COLOR getChartColorValues() {
        CHART_COLOR chart_color = this.chartColor;
        return chart_color == null ? CHART_COLOR.STANDARD : chart_color;
    }

    public CHART_COLOR getChartColor() {
        return this.chartColor;
    }

    public void setChartColor(CHART_COLOR chart_color) {
        this.chartColor = chart_color;
    }

    public TEMP_UNIT getTempUnit() {
        return this.tempUnit;
    }

    public TEMP_UNIT getTempUnitValues() {
        TEMP_UNIT temp_unit = this.tempUnit;
        return temp_unit == null ? TEMP_UNIT.C : temp_unit;
    }

    public void setTempUnit(TEMP_UNIT temp_unit) {
        this.tempUnit = temp_unit;
    }

    public DATE_FORMAT getDateFormat() {
        return this.dateFormat;
    }

    public DATE_FORMAT getDateFormatValues() {
        DATE_FORMAT date_format = this.dateFormat;
        return date_format == null ? DATE_FORMAT.MM_DD_YYYY : date_format;
    }

    public void setDateFormat(DATE_FORMAT date_format) {
        this.dateFormat = date_format;
    }

    public String getUserChartRecord() {
        return this.userChartRecord;
    }

    public void setUserChartRecord(String str) {
        this.userChartRecord = str;
    }

    public JSONObject getTechInfo() {
        return this.techInfo;
    }

    public void setTechInfo(JSONObject jSONObject) {
        this.techInfo = jSONObject;
    }

    public Plant getCurrentPlant() {
        return this.currentPlant;
    }

    public void setCurrentPlant(Plant plant) {
        this.currentPlant = plant;
    }

    public Inverter getCurrentParallelGroup() {
        return this.currentParallelGroup;
    }

    public void setCurrentParallelGroup(Inverter inverter) {
        Inverter inverter2;
        if (inverter != null && (inverter2 = getInverter(inverter.getParallelFirstDeviceSn())) != null) {
            setCurrentInverter(inverter2, true);
        }
        this.currentParallelGroup = inverter;
    }

    public Inverter getCurrentInverter() {
        return this.currentInverter;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setCurrentInverter(com.lux.luxcloud.global.bean.inverter.Inverter r8, boolean r9) {
        /*
            r7 = this;
            r0 = 0
            r7.currentParallelGroup = r0
            r7.currentInverter = r8
            if (r9 == 0) goto L9c
            com.lux.luxcloud.global.bean.user.UserVisitRecord r8 = new com.lux.luxcloud.global.bean.user.UserVisitRecord
            r8.<init>()
            com.lux.luxcloud.global.bean.inverter.Inverter r9 = r7.currentInverter
            r1 = 0
            r2 = 1
            if (r9 == 0) goto L54
            com.lux.luxcloud.global.bean.user.UserVisitRecord r9 = r7.userVisitRecord
            if (r9 == 0) goto L2e
            java.lang.String r9 = r9.getSerialNum()
            if (r9 == 0) goto L2e
            com.lux.luxcloud.global.bean.user.UserVisitRecord r9 = r7.userVisitRecord
            java.lang.String r9 = r9.getSerialNum()
            com.lux.luxcloud.global.bean.inverter.Inverter r3 = r7.currentInverter
            java.lang.String r3 = r3.getSerialNum()
            boolean r9 = r9.equals(r3)
            if (r9 != 0) goto L54
        L2e:
            com.lux.luxcloud.global.bean.inverter.Inverter r9 = r7.currentInverter
            java.lang.Long r9 = r9.getPlantId()
            r8.setPlantId(r9)
            com.lux.luxcloud.global.bean.inverter.Inverter r9 = r7.currentInverter
            boolean r9 = r9.isParallelGroup()
            if (r9 == 0) goto L46
            com.lux.luxcloud.global.bean.inverter.Inverter r9 = r7.currentInverter
            java.lang.String r9 = r9.getParallelFirstDeviceSn()
            goto L4e
        L46:
            com.lux.luxcloud.global.bean.inverter.Inverter r9 = r7.getCurrentInverter()
            java.lang.String r9 = r9.getSerialNum()
        L4e:
            r8.setSerialNum(r9)
            r7.userVisitRecord = r8
            goto L8b
        L54:
            com.lux.luxcloud.global.bean.plant.Plant r9 = r7.getCurrentPlant()
            if (r9 == 0) goto L8d
            com.lux.luxcloud.global.bean.user.UserVisitRecord r9 = r7.userVisitRecord
            if (r9 == 0) goto L7a
            java.lang.Long r9 = r9.getPlantId()
            if (r9 == 0) goto L7a
            com.lux.luxcloud.global.bean.user.UserVisitRecord r9 = r7.userVisitRecord
            java.lang.Long r9 = r9.getPlantId()
            long r3 = r9.longValue()
            com.lux.luxcloud.global.bean.plant.Plant r9 = r7.getCurrentPlant()
            long r5 = r9.getPlantId()
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 == 0) goto L8d
        L7a:
            com.lux.luxcloud.global.bean.plant.Plant r9 = r7.getCurrentPlant()
            long r3 = r9.getPlantId()
            java.lang.Long r9 = java.lang.Long.valueOf(r3)
            r8.setPlantId(r9)
            r7.userVisitRecord = r8
        L8b:
            r9 = r2
            goto L8e
        L8d:
            r9 = r1
        L8e:
            if (r9 == 0) goto L9c
            com.lux.luxcloud.global.UserData$UpdateUserVisitRecordTask r9 = new com.lux.luxcloud.global.UserData$UpdateUserVisitRecordTask
            r9.<init>()
            com.lux.luxcloud.global.bean.user.UserVisitRecord[] r0 = new com.lux.luxcloud.global.bean.user.UserVisitRecord[r2]
            r0[r1] = r8
            r9.execute(r0)
        L9c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.global.UserData.setCurrentInverter(com.lux.luxcloud.global.bean.inverter.Inverter, boolean):void");
    }

    private static class UpdateUserVisitRecordTask extends AsyncTask<UserVisitRecord, Object, Boolean> {
        private UpdateUserVisitRecordTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(UserVisitRecord... userVisitRecordArr) {
            HashMap map = new HashMap();
            map.put("plantId", String.valueOf(userVisitRecordArr[0].getPlantId()));
            if (!Tool.isEmpty(userVisitRecordArr[0].getSerialNum())) {
                map.put("serialNum", userVisitRecordArr[0].getSerialNum());
            }
            try {
                if (HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userVisit/update", map).getBoolean("success")) {
                    GlobalInfo.getInstance().getUserData().setUserVisitRecord(userVisitRecordArr[0]);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public void clearPlantsAndInverters() {
        this.plants.clear();
        this.inverterMap.clear();
        this.plantInverterMap.clear();
    }

    public List<Plant> getPlants() {
        return this.plants;
    }

    public void addPlant(Plant plant) {
        this.plants.add(plant);
    }

    public void addInverter(Inverter inverter) {
        this.inverterMap.put(inverter.getSerialNum(), inverter);
    }

    public void put(long j, List<Inverter> list) {
        this.plantInverterMap.put(Long.valueOf(j), list);
    }

    public List<Inverter> getInvertersByPlant(long j) {
        return this.plantInverterMap.get(Long.valueOf(j));
    }

    public Inverter getInverter(String str) {
        if (Tool.isEmpty(str)) {
            return null;
        }
        return this.inverterMap.get(str);
    }

    public boolean isAllowViewerVisitOptimalSet() {
        return this.allowViewerVisitOptimalSet;
    }

    public void setAllowViewerVisitOptimalSet(boolean z) {
        this.allowViewerVisitOptimalSet = z;
    }

    public boolean isAllowViewerVisitWeatherSet() {
        return this.allowViewerVisitWeatherSet;
    }

    public void setAllowViewerVisitWeatherSet(boolean z) {
        this.allowViewerVisitWeatherSet = z;
    }

    public boolean isAllowRemoteSupport() {
        return this.allowRemoteSupport;
    }

    public void setAllowRemoteSupport(boolean z) {
        this.allowRemoteSupport = z;
    }

    public long getClusterId() {
        return this.clusterId;
    }

    public void setClusterId(long j) {
        this.clusterId = j;
    }
}