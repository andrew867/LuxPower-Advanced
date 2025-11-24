package com.lux.luxcloud.global;

import android.content.Context;
import android.os.AsyncTask;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP;
import com.lux.luxcloud.global.bean.cluster.Cluster;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.custom.mpChart.NoZeroValueFormatter;
import com.lux.luxcloud.global.custom.mpChart.NumberAxisSOCValueFormatter;
import com.lux.luxcloud.global.custom.mpChart.NumberAxisValueFormatter;
import com.lux.luxcloud.global.custom.mpChart.TimeAxisValueFormatter;
import com.lux.luxcloud.global.locale.CONTINENT;
import com.lux.luxcloud.global.locale.TIMEZONE;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import com.lux.luxcloud.version.Version;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class GlobalInfo {
    public static final int DATA_DATE_DAY_PICKER = 3;
    public static final int DATA_DATE_MONTH_PICKER = 4;
    public static final int DATA_DATE_YEAR_PICKER = 5;
    public static final int LOCAL_SET_DATE_DAY_PICKER = 0;
    public static final int LOCAL_SET_TIME_PICKER = 1;
    public static final int OVERVIEW_DATE_DAY_PICKER = 0;
    public static final int OVERVIEW_DATE_MONTH_PICKER = 1;
    public static final int OVERVIEW_DATE_YEAR_PICKER = 2;
    public static final int REMOTE_SET_DATE_DAY_PICKER = 6;
    public static final int REMOTE_SET_TIME_PICKER = 7;
    public static List<Property> batControlList;
    private static final GlobalInfo instance = new GlobalInfo();
    private List<Property> continents;
    private String currentClusterGroup;
    private List<Property> firstClusterServerIdProperties;
    private List<Property> firstClusterServerProperties;
    private List<Property> firstClusterServerPropertiesWithKnown;
    private boolean inited;
    private boolean initing;
    private String language;
    private List<Property> secondClusterServerProperties;
    private List<Property> secondClusterServerPropertiesWithKnown;
    private List<Property> timezones;
    private UserData userData = new UserData();
    private Integer defaultTimezoneIndex = Integer.valueOf(TIMEZONE.ZERO.ordinal());
    private final Map<Long, Cluster> loginClusterMap = new LinkedHashMap();
    private Map<Long, Cluster> firstClusterMap = new HashMap();
    private Map<Long, Cluster> secondClusterMap = new HashMap();
    private Map<Long, Cluster> indiaClusterMap = new HashMap();
    private Map<Long, Cluster> eu2ClusterMap = new HashMap();
    private Map<Long, Cluster> vietnamClusterMap = new HashMap();
    private Map<Long, Cluster> phiClusterMap = new HashMap();
    private IAxisValueFormatter timeAxisValueFormatter = new TimeAxisValueFormatter();
    private IAxisValueFormatter numberAxisValueFormatter = new NumberAxisValueFormatter();
    private IAxisValueFormatter numberAxisSOCValueFormatter = new NumberAxisSOCValueFormatter();
    private IValueFormatter noZeroValueFormatter = new NoZeroValueFormatter();

    public static GlobalInfo getInstance() {
        return instance;
    }

    public String getCustomDoname() {
        return Constants.CLUSTER_GROUP_INDIA.equals(this.currentClusterGroup) ? "http://ind.solarcloudsystem.com/WManage/" : !Tool.isEmpty(Custom.CUSTOM_DONAME) ? "http://na.solarcloudsystem.com/WManage/" : getBaseUrl();
    }

    public String getBaseUrl() {
        CLUSTER_GROUP cluster_groupFromName = CLUSTER_GROUP.fromName(this.currentClusterGroup);
        Cluster cluster = cluster_groupFromName.getClusterMap().get(Long.valueOf(this.userData.getClusterId()));
        return cluster != null ? cluster.getClusterPrefixUrl() + "/WManage/" : cluster_groupFromName.getDefaultUrl();
    }

    public String getMajorUrl() {
        return Constants.CLUSTER_GROUP_SECOND.equals(this.currentClusterGroup) ? "https://us.luxpowertek.com/WManage/" : "https://as.luxpowertek.com/WManage/";
    }

    public String getFirmwareDownloadUrl() {
        return !Tool.isEmpty("") ? "" : getMajorUrl();
    }

    public static boolean getHideClusterAtRegisterPage() {
        return Tool.isAloneClusterGroup();
    }

    public static CLUSTER_GROUP getClusterGroup() {
        String currentClusterGroup = getInstance().getCurrentClusterGroup();
        if (currentClusterGroup == null) {
            return CLUSTER_GROUP.FIRST;
        }
        currentClusterGroup.hashCode();
        switch (currentClusterGroup) {
        }
        return CLUSTER_GROUP.FIRST;
    }

    public static final int getDefaultClusterIdIndex() {
        return Tool.isAloneClusterGroup() ? 0 : 1;
    }

    public static long getDefaultClusterId() {
        return CLUSTER_GROUP.fromName(getInstance().getCurrentClusterGroup()).getDefaultClusterId();
    }

    public boolean isSpecificLanguage(Locale locale) {
        String language = locale.getLanguage();
        return "ar".equals(language) || "ur".equals(language);
    }

    public void initializeGlobalInfo(Context context, Locale locale) {
        if (this.inited) {
            return;
        }
        String language = locale.getLanguage();
        if (language.contains("zh")) {
            this.language = "zh_CN";
        } else if (language.contains("ar")) {
            this.language = "ar";
        } else if (language.contains("es")) {
            this.language = "es_ES";
        } else if (language.contains("fr")) {
            this.language = "fr";
        } else if (language.contains("he")) {
            this.language = "he_IL";
        } else if (language.contains("it")) {
            this.language = "it";
        } else if (language.contains("ja")) {
            this.language = "ja";
        } else if (language.contains("nl")) {
            this.language = "nl_NL";
        } else if (language.contains("pl")) {
            this.language = "pl_PL";
        } else if (language.contains("pt")) {
            this.language = "pt_PT";
        } else if (language.contains("ru")) {
            this.language = "ru_RU";
        } else if (language.contains("uk")) {
            this.language = "uk_UA";
        } else if (language.contains("ur")) {
            this.language = "ur_PK";
        } else {
            this.language = "en";
        }
        Cluster cluster = new Cluster();
        cluster.setClusterId(500L);
        cluster.setClusterName(context.getString(R.string.phase_server_name_eu2));
        cluster.setClusterPrefixUrl("http://eu2.luxpowertek.com");
        cluster.setMajor(false);
        this.eu2ClusterMap.put(Long.valueOf(cluster.getClusterId()), cluster);
        Cluster cluster2 = new Cluster();
        cluster2.setClusterId(400L);
        cluster2.setClusterName(context.getString(R.string.phase_server_name_vn));
        cluster2.setClusterPrefixUrl("http://vn.luxpowertek.com");
        cluster2.setMajor(false);
        this.vietnamClusterMap.put(Long.valueOf(cluster2.getClusterId()), cluster2);
        Cluster cluster3 = new Cluster();
        cluster3.setClusterId(300L);
        cluster3.setClusterName(context.getString(R.string.phase_server_name_phi));
        cluster3.setClusterPrefixUrl("http://phi.luxpowertek.com");
        cluster3.setMajor(false);
        this.phiClusterMap.put(Long.valueOf(cluster3.getClusterId()), cluster3);
        Cluster cluster4 = new Cluster();
        cluster4.setClusterId(2L);
        cluster4.setClusterName("Europe");
        cluster4.setClusterPrefixUrl("http://eu.luxpowertek.com");
        cluster4.setMajor(false);
        this.firstClusterMap.put(Long.valueOf(cluster4.getClusterId()), cluster4);
        Cluster cluster5 = new Cluster();
        cluster5.setClusterId(3L);
        cluster5.setClusterName("Africa");
        cluster5.setClusterPrefixUrl("http://af.luxpowertek.com");
        cluster5.setMajor(false);
        this.firstClusterMap.put(Long.valueOf(cluster5.getClusterId()), cluster5);
        Cluster cluster6 = new Cluster();
        cluster6.setClusterId(4L);
        cluster6.setClusterName("America");
        cluster6.setClusterPrefixUrl("http://na.luxpowertek.com");
        cluster6.setMajor(true);
        this.firstClusterMap.put(Long.valueOf(cluster6.getClusterId()), cluster6);
        Cluster cluster7 = new Cluster();
        cluster7.setClusterId(5L);
        cluster7.setClusterName("Asia(1)");
        cluster7.setClusterPrefixUrl("http://sea.luxpowertek.com");
        cluster7.setMajor(true);
        this.firstClusterMap.put(Long.valueOf(cluster7.getClusterId()), cluster7);
        Cluster cluster8 = new Cluster();
        cluster8.setClusterId(1L);
        cluster8.setClusterName("Asia(China)");
        cluster8.setClusterPrefixUrl("http://as.luxpowertek.com");
        cluster8.setMajor(true);
        this.firstClusterMap.put(Long.valueOf(cluster8.getClusterId()), cluster8);
        initializeFirstClusterServerIdProperties(context);
        initializeFirstClusterServerProperties(context);
        initializeFirstClusterServerPropertiesWithUnknown(context);
        initializeFirstClusterServerPropertiesWithUnknown4EG4(context);
        Cluster cluster9 = new Cluster();
        cluster9.setClusterId(100L);
        cluster9.setClusterName("America (Stand-alone)");
        cluster9.setClusterPrefixUrl("http://us.luxpowertek.com");
        cluster9.setMajor(true);
        this.secondClusterMap.put(Long.valueOf(cluster9.getClusterId()), cluster9);
        Cluster cluster10 = new Cluster();
        cluster10.setClusterId(200L);
        cluster10.setClusterName(context.getString(R.string.phase_server_name_ind));
        cluster10.setClusterPrefixUrl("http://ind.luxpowertek.com");
        cluster10.setMajor(true);
        this.indiaClusterMap.put(Long.valueOf(cluster10.getClusterId()), cluster10);
        Cluster cluster11 = new Cluster();
        cluster11.setClusterId(2L);
        cluster11.setClusterName(context.getString(R.string.phase_server_name_common));
        cluster11.setClusterPrefixUrl("http://eu.luxpowertek.com");
        cluster11.setMajor(false);
        this.loginClusterMap.put(Long.valueOf(cluster11.getClusterId()), cluster11);
        this.loginClusterMap.put(Long.valueOf(cluster10.getClusterId()), cluster10);
        this.loginClusterMap.put(Long.valueOf(cluster3.getClusterId()), cluster3);
        this.loginClusterMap.put(Long.valueOf(cluster2.getClusterId()), cluster2);
        this.loginClusterMap.put(Long.valueOf(cluster.getClusterId()), cluster);
        initializeContinents(context);
        initializeTimezones();
        String displayName = TimeZone.getDefault().getDisplayName(false, 0);
        int iIndexOf = displayName.indexOf(":");
        if (iIndexOf > 3) {
            try {
                TIMEZONE enumByNumber = TIMEZONE.getEnumByNumber(Integer.parseInt(displayName.substring(3, iIndexOf)));
                getInstance().defaultTimezoneIndex = Integer.valueOf(enumByNumber.ordinal());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList arrayList = new ArrayList();
        batControlList = arrayList;
        arrayList.add(new Property("-1", context.getString(R.string.phrase_please_select)));
        batControlList.add(new Property(Boolean.TRUE.toString(), "Volt"));
        batControlList.add(new Property(Boolean.FALSE.toString(), "SOC"));
        this.inited = true;
    }

    public void execGlobalInfoTask() {
        new GlobalInfoTask().execute("ASIA");
        new GlobalInfoTask().execute("AF");
        new GlobalInfoTask().execute("NA");
    }

    private void initializeFirstClusterServerIdProperties(Context context) {
        ArrayList arrayList = new ArrayList();
        this.firstClusterServerIdProperties = arrayList;
        arrayList.add(new Property(String.valueOf(2L), context.getString(R.string.continent_europe)));
        this.firstClusterServerIdProperties.add(new Property(String.valueOf(3L), context.getString(R.string.continent_africa)));
        this.firstClusterServerIdProperties.add(new Property(String.valueOf(4L), context.getString(R.string.continent_north_america)));
        this.firstClusterServerIdProperties.add(new Property(String.valueOf(5L), context.getString(R.string.continent_southeast_asia_custom)));
    }

    public List<Property> getFirstClusterServerIds(Context context) {
        if (this.firstClusterServerIdProperties == null) {
            initializeFirstClusterServerIdProperties(context);
        }
        return this.firstClusterServerIdProperties;
    }

    private void initializeFirstClusterServerProperties(Context context) {
        ArrayList arrayList = new ArrayList();
        this.firstClusterServerProperties = arrayList;
        arrayList.add(new Property(Constants.DONGLE_SERVER_ASIA, context.getString(R.string.continent_asia)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_EUROPE, context.getString(R.string.continent_europe)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_AFRICA, context.getString(R.string.continent_africa)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_USA, context.getString(R.string.continent_north_america)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_SOUTHEAST_ASIA, context.getString(R.string.continent_southeast_asia)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_LOCAL, context.getString(R.string.continent_local_server)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_LOCAL_SSL, context.getString(R.string.continent_local_server_ssl)));
    }

    private void initializeFirstClusterServerProperties4EG4(Context context) {
        this.secondClusterServerProperties = new ArrayList();
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_NORTH_AMERICA, context.getString(R.string.continent_north_america)));
    }

    private void initializeFirstClusterServerPropertiesWithUnknown(Context context) {
        ArrayList arrayList = new ArrayList();
        this.firstClusterServerPropertiesWithKnown = arrayList;
        arrayList.add(new Property(Constants.DONGLE_SERVER_ASIA, context.getString(R.string.continent_asia)));
        this.firstClusterServerPropertiesWithKnown.add(new Property(Constants.DONGLE_SERVER_EUROPE, context.getString(R.string.continent_europe)));
        this.firstClusterServerPropertiesWithKnown.add(new Property(Constants.DONGLE_SERVER_AFRICA, context.getString(R.string.continent_africa)));
        this.firstClusterServerPropertiesWithKnown.add(new Property(Constants.DONGLE_SERVER_USA, context.getString(R.string.continent_north_america)));
        this.firstClusterServerPropertiesWithKnown.add(new Property(Constants.DONGLE_SERVER_SOUTHEAST_ASIA, context.getString(R.string.continent_southeast_asia)));
        this.firstClusterServerPropertiesWithKnown.add(new Property(Constants.DONGLE_SERVER_LOCAL, context.getString(R.string.continent_local_server)));
        this.firstClusterServerPropertiesWithKnown.add(new Property(Constants.DONGLE_SERVER_LOCAL_SSL, context.getString(R.string.continent_local_server_ssl)));
        this.firstClusterServerPropertiesWithKnown.add(new Property("", context.getString(R.string.string_unknown_domain_name)));
    }

    private void initializeFirstClusterServerPropertiesWithUnknown4EG4(Context context) {
        ArrayList arrayList = new ArrayList();
        this.secondClusterServerPropertiesWithKnown = arrayList;
        arrayList.add(new Property(Constants.DONGLE_SERVER_USA, context.getString(R.string.continent_north_america)));
        this.secondClusterServerPropertiesWithKnown.add(new Property("", context.getString(R.string.string_unknown_domain_name)));
    }

    public List<Property> getSecondClusterServers(Context context) {
        if (this.secondClusterServerPropertiesWithKnown == null) {
            initializeFirstClusterServerPropertiesWithUnknown4EG4(context);
        }
        return this.secondClusterServerPropertiesWithKnown;
    }

    public List<Property> getFirstClusterServersWithUnknown(Context context) {
        if (this.firstClusterServerPropertiesWithKnown == null) {
            initializeFirstClusterServerPropertiesWithUnknown(context);
        }
        return this.firstClusterServerPropertiesWithKnown;
    }

    public List<Property> getFirstClusterServers(Context context) {
        if (this.firstClusterServerProperties == null) {
            initializeFirstClusterServerProperties(context);
        }
        return this.firstClusterServerProperties;
    }

    public Map<Long, Cluster> getClusterMap() {
        if (Constants.INDIA_COUNTRY_CODE.equals(this.currentClusterGroup)) {
            return this.indiaClusterMap;
        }
        if (Constants.CLUSTER_GROUP_SECOND.equals(this.currentClusterGroup)) {
            return this.secondClusterMap;
        }
        if (Constants.CLUSTER_GROUP_EU2.equals(this.currentClusterGroup)) {
            return this.eu2ClusterMap;
        }
        if (Constants.CLUSTER_GROUP_VIETNAM.equals(this.currentClusterGroup)) {
            return this.vietnamClusterMap;
        }
        if (Constants.CLUSTER_GROUP_PHILIPPINES.equals(this.currentClusterGroup)) {
            return this.phiClusterMap;
        }
        return this.firstClusterMap;
    }

    public Map<Long, Cluster> getLoginClusterMap() {
        return this.loginClusterMap;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getLanguageEnumName() {
        return "zh_CN".equals(this.language) ? "CHINESE" : "ENGLISH";
    }

    public UserData getUserData() {
        return this.userData;
    }

    public Integer getDefaultTimezoneIndex() {
        return this.defaultTimezoneIndex;
    }

    private void initializeTimezones() {
        this.timezones = new ArrayList();
        for (TIMEZONE timezone : TIMEZONE.values()) {
            this.timezones.add(new Property(timezone.name(), timezone.getText()));
        }
    }

    public List<Property> getTimezones() {
        if (this.timezones == null) {
            initializeTimezones();
        }
        return this.timezones;
    }

    private void initializeContinents(Context context) {
        this.continents = new ArrayList();
        for (CONTINENT continent : CONTINENT.values()) {
            this.continents.add(new Property(continent.name(), context.getString(continent.getTextResourceId())));
        }
    }

    public List<Property> getContinents(Context context) {
        if (this.continents == null) {
            initializeContinents(context);
        }
        return this.continents;
    }

    public static List<Property> getBatControlList() {
        return batControlList;
    }

    public IAxisValueFormatter getTimeAxisValueFormatter() {
        return this.timeAxisValueFormatter;
    }

    public IAxisValueFormatter getNumberAxisValueFormatter() {
        return this.numberAxisValueFormatter;
    }

    public IAxisValueFormatter getNumberAxisSOCValueFormatter() {
        return this.numberAxisSOCValueFormatter;
    }

    public IValueFormatter getNoZeroValueFormatter() {
        return this.noZeroValueFormatter;
    }

    public boolean isIniting() {
        return this.initing;
    }

    public boolean isInited() {
        return this.inited;
    }

    public String getCurrentClusterGroup() {
        return this.currentClusterGroup;
    }

    public void setCurrentClusterGroup(String str) {
        this.currentClusterGroup = str;
    }

    private static class GlobalInfoTask extends AsyncTask<String, Object, Void> {
        private GlobalInfoTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(String[] strArr) {
            try {
                try {
                    GlobalInfo.getInstance().initing = true;
                    HashMap map = new HashMap();
                    map.put("language", GlobalInfo.getInstance().getLanguage());
                    String baseUrl = GlobalInfo.getInstance().getBaseUrl();
                    if ("ASIA".equals(strArr[0])) {
                        baseUrl = "https://as.luxpowertek.com/WManage/";
                    } else if ("AF".equals(strArr[0])) {
                        baseUrl = Version.AF_BASE_URL;
                    } else if ("NA".equals(strArr[0])) {
                        baseUrl = Version.NA_BASE_URL;
                    }
                    if (HttpTool.postJson(baseUrl + "api/global/buildGlobalInfo", map).getBoolean("success") && !GlobalInfo.getInstance().inited) {
                        GlobalInfo.getInstance().inited = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GlobalInfo.getInstance().initing = false;
                return null;
            } catch (Throwable th) {
                GlobalInfo.getInstance().initing = false;
                throw th;
            }
        }
    }
}