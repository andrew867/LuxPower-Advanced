package com.nfcx.eg4.global;

import android.content.Context;
import android.os.AsyncTask;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.bean.cluster.Cluster;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.custom.mpChart.NoZeroValueFormatter;
import com.nfcx.eg4.global.custom.mpChart.NumberAxisSOCValueFormatter;
import com.nfcx.eg4.global.custom.mpChart.NumberAxisValueFormatter;
import com.nfcx.eg4.global.custom.mpChart.TimeAxisValueFormatter;
import com.nfcx.eg4.global.locale.CONTINENT;
import com.nfcx.eg4.global.locale.TIMEZONE;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.version.Custom;
import com.nfcx.eg4.version.Version;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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
    private boolean inited;
    private boolean initing;
    private String language;
    private List<Property> secondClusterServerIdProperties;
    private List<Property> secondClusterServerProperties;
    private List<Property> secondClusterServerPropertiesWithKnown;
    private List<Property> timezones;
    private UserData userData = new UserData();
    private Integer defaultTimezoneIndex = Integer.valueOf(TIMEZONE.ZERO.ordinal());
    private Map<Long, Cluster> firstClusterMap = new HashMap();
    private Map<Long, Cluster> secondClusterMap = new HashMap();
    private IAxisValueFormatter timeAxisValueFormatter = new TimeAxisValueFormatter();
    private IAxisValueFormatter numberAxisValueFormatter = new NumberAxisValueFormatter();
    private IAxisValueFormatter numberAxisSOCValueFormatter = new NumberAxisSOCValueFormatter();
    private IValueFormatter noZeroValueFormatter = new NoZeroValueFormatter();

    public static GlobalInfo getInstance() {
        return instance;
    }

    public String getCustomDoname() {
        return !Tool.isEmpty(Custom.CUSTOM_DONAME) ? "http://monitor.eg4electronics.com/WManage/" : getBaseUrl();
    }

    public String getBaseUrl() {
        if (Constants.CLUSTER_GROUP_SECOND.equals(this.currentClusterGroup)) {
            Cluster cluster = this.secondClusterMap.get(Long.valueOf(this.userData.getClusterId()));
            return cluster != null ? cluster.getClusterPrefixUrl() + "/WManage/" : "https://us.luxpowertek.com/WManage/";
        }
        Cluster cluster2 = this.firstClusterMap.get(Long.valueOf(this.userData.getClusterId()));
        return cluster2 != null ? cluster2.getClusterPrefixUrl() + "/WManage/" : "https://us.luxpowertek.com/WManage/";
    }

    public String getMajorUrl() {
        return Constants.CLUSTER_GROUP_SECOND.equals(this.currentClusterGroup) ? "https://us.luxpowertek.com/WManage/" : "https://as.luxpowertek.com/WManage/";
    }

    public void initializeGlobalInfo(Context context, Locale locale) {
        if (this.inited) {
            return;
        }
        if (locale.getLanguage().indexOf("zh") >= 0) {
            this.language = "zh_CN";
        } else {
            this.language = "en";
        }
        initializeFirstClusterServerPropertiesWithUnknown4EG4(context);
        Cluster cluster = new Cluster();
        cluster.setClusterId(1L);
        cluster.setClusterName("Asia");
        cluster.setClusterPrefixUrl("http://server.luxpowertek.com");
        cluster.setMajor(true);
        this.firstClusterMap.put(Long.valueOf(cluster.getClusterId()), cluster);
        Cluster cluster2 = new Cluster();
        cluster2.setClusterId(2L);
        cluster2.setClusterName("Europe");
        cluster2.setClusterPrefixUrl("http://eu.luxpowertek.com");
        cluster2.setMajor(false);
        this.firstClusterMap.put(Long.valueOf(cluster2.getClusterId()), cluster2);
        Cluster cluster3 = new Cluster();
        cluster3.setClusterId(3L);
        cluster3.setClusterName("Africa");
        cluster3.setClusterPrefixUrl("http://af.luxpowertek.com");
        cluster3.setMajor(false);
        this.firstClusterMap.put(Long.valueOf(cluster3.getClusterId()), cluster3);
        Cluster cluster4 = new Cluster();
        cluster4.setClusterId(4L);
        cluster4.setClusterName("America");
        cluster4.setClusterPrefixUrl("http://na.luxpowertek.com");
        cluster4.setMajor(true);
        this.firstClusterMap.put(Long.valueOf(cluster4.getClusterId()), cluster4);
        ArrayList arrayList = new ArrayList();
        this.firstClusterServerIdProperties = arrayList;
        arrayList.add(new Property(String.valueOf(1), context.getString(R.string.continent_asia)));
        this.firstClusterServerIdProperties.add(new Property(String.valueOf(2), context.getString(R.string.continent_europe)));
        this.firstClusterServerIdProperties.add(new Property(String.valueOf(3), context.getString(R.string.continent_africa)));
        this.firstClusterServerIdProperties.add(new Property(String.valueOf(4), context.getString(R.string.continent_north_america)));
        ArrayList arrayList2 = new ArrayList();
        this.firstClusterServerProperties = arrayList2;
        arrayList2.add(new Property(Constants.DONGLE_SERVER_ASIA, context.getString(R.string.continent_asia)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_EUROPE, context.getString(R.string.continent_europe)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_AFRICA, context.getString(R.string.continent_africa)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_USA, context.getString(R.string.continent_north_america)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_SOUTHEAST_ASIA, context.getString(R.string.continent_southeast_asia)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_LOCAL, context.getString(R.string.continent_local_server)));
        this.firstClusterServerProperties.add(new Property(Constants.DONGLE_SERVER_LOCAL_SSL, context.getString(R.string.continent_local_server_ssl)));
        ArrayList arrayList3 = new ArrayList();
        this.secondClusterServerIdProperties = arrayList3;
        arrayList3.add(new Property(String.valueOf(4), context.getString(R.string.continent_north_america)));
        ArrayList arrayList4 = new ArrayList();
        this.secondClusterServerProperties = arrayList4;
        arrayList4.add(new Property(Constants.DONGLE_SERVER_NORTH_AMERICA, context.getString(R.string.continent_north_america)));
        Cluster cluster5 = new Cluster();
        cluster5.setClusterId(100L);
        cluster5.setClusterName("America (Stand-alone)");
        cluster5.setClusterPrefixUrl("http://us.luxpowertek.com");
        cluster5.setMajor(true);
        this.secondClusterMap.put(Long.valueOf(cluster5.getClusterId()), cluster5);
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
        initializeBatControlList(context);
        this.inited = true;
    }

    public void execGlobalInfoTask() {
        new GlobalInfoTask().execute("ASIA");
        new GlobalInfoTask().execute("AF");
        new GlobalInfoTask().execute("NA");
    }

    public List<Property> getFirstClusterServerIds() {
        return this.firstClusterServerIdProperties;
    }

    public List<Property> getSecondClusterServerIds() {
        return this.secondClusterServerIdProperties;
    }

    private void initializeFirstClusterServerPropertiesWithUnknown4EG4(Context context) {
        ArrayList arrayList = new ArrayList();
        this.secondClusterServerPropertiesWithKnown = arrayList;
        arrayList.add(new Property(Constants.DONGLE_SERVER_NORTH_AMERICA, context.getString(R.string.continent_north_america)));
        this.secondClusterServerPropertiesWithKnown.add(new Property("", context.getString(R.string.string_unknown_domain_name)));
    }

    public List<Property> getSecondClusterServers() {
        return this.secondClusterServerPropertiesWithKnown;
    }

    public List<Property> getFirstClusterServers() {
        return this.firstClusterServerProperties;
    }

    public Map<Long, Cluster> getClusterMap() {
        if (Constants.CLUSTER_GROUP_SECOND.equals(this.currentClusterGroup)) {
            return this.secondClusterMap;
        }
        return this.firstClusterMap;
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

    private static void initializeBatControlList(Context context) {
        ArrayList arrayList = new ArrayList();
        batControlList = arrayList;
        arrayList.add(new Property("-1", context.getString(R.string.phrase_please_select)));
        batControlList.add(new Property(Boolean.TRUE.toString(), "Volt"));
        batControlList.add(new Property(Boolean.FALSE.toString(), "SOC"));
    }

    public static List<Property> getBatControlList(Context context) {
        if (batControlList == null) {
            initializeBatControlList(context);
        }
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