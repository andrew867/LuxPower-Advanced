package com.lux.luxcloud.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.gson.Gson;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.user.CHART_COLOR;
import com.lux.luxcloud.global.bean.user.DATE_FORMAT;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.global.bean.user.TEMP_UNIT;
import com.lux.luxcloud.global.bean.user.UserVisitRecord;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class NormalSettingActivity extends Activity {
    public static final String USER_INFO = "userInfo";
    public static NormalSettingActivity instance;
    private boolean isDarkTheme;
    private JSONObject jsonFromRemote;
    UserData userData = GlobalInfo.getInstance().getUserData();
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_page_setting);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        setupListeners();
    }

    private void setupListeners() {
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m755x9965330f(view);
            }
        });
        WebView webView = (WebView) findViewById(R.id.chart_Color_setting);
        this.webView = webView;
        webView.setWebChromeClient(new WebChromeClient());
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
                if (str.contains("normalSetting_index.html")) {
                    NormalSettingActivity.this.updateWebContent();
                    webView2.loadUrl("javascript:initUseNewStatus()");
                    webView2.loadUrl("javascript:initUseHtmlStatus()");
                }
            }
        });
        this.webView.addJavascriptInterface(new WebAppInterfaceNormalSetting(), "WebAppInterfaceNormalSetting");
        configureWebSettings(this.webView.getSettings());
        this.webView.loadUrl("file:///android_asset/module/normalSetting/normalSetting_index.html");
    }

    /* renamed from: lambda$setupListeners$0$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m755x9965330f(View view) {
        handleBack();
    }

    private void configureWebSettings(WebSettings webSettings) {
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(2);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
    }

    private void handleBack() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
        }
    }

    private class WebAppInterfaceNormalSetting {
        private WebAppInterfaceNormalSetting() {
        }

        @JavascriptInterface
        public String initColorData() throws JSONException {
            if (NormalSettingActivity.this.jsonFromRemote != null) {
                return NormalSettingActivity.this.jsonFromRemote.toString();
            }
            return null;
        }

        @JavascriptInterface
        public String getUserChartRecord() throws JSONException, InterruptedException {
            String strFetchUserChartRecord = NormalSettingActivity.this.fetchUserChartRecord();
            return strFetchUserChartRecord != null ? strFetchUserChartRecord : "";
        }

        @JavascriptInterface
        public void saveUserChartRecord(String str) {
            NormalSettingActivity.this.saveChartRecord(str);
        }

        @JavascriptInterface
        public void saveForm(String str) {
            NormalSettingActivity.this.savePreference(str);
        }

        @JavascriptInterface
        public boolean getUseNewSettingPage() {
            return NormalSettingActivity.this.adaptUseSetting();
        }

        @JavascriptInterface
        public boolean getUseHtmlSettingPage() {
            return NormalSettingActivity.this.adaptUseHtmlSetting();
        }

        @JavascriptInterface
        public String getLocalLanguageResources() throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
            JSONObject jSONObject = new JSONObject();
            Resources resources = NormalSettingActivity.instance.getResources();
            for (Field field : R.string.class.getFields()) {
                try {
                    jSONObject.put(field.getName(), resources.getString(field.getInt(null)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return jSONObject.toString();
        }

        @JavascriptInterface
        public void changeUseNewStatus(boolean z) {
            NormalSettingActivity.this.saveNewPageSetting(Boolean.valueOf(z));
        }

        @JavascriptInterface
        public void changeHtmlUseNewStatus(boolean z) {
            NormalSettingActivity.this.saveHtmlPageSetting(Boolean.valueOf(z));
        }
    }

    @Override // android.app.Activity
    protected void onResume() throws JSONException {
        super.onResume();
        createColorJson();
    }

    private void createColorJson() throws JSONException {
        if (this.jsonFromRemote == null) {
            this.jsonFromRemote = new JSONObject();
        }
        try {
            if (!Tool.isEmpty(this.userData.getChartColorValues().toString())) {
                this.jsonFromRemote.put("chartColor", this.userData.getChartColorValues().getName());
                this.jsonFromRemote.put("dateFormat", this.userData.getDateFormatValues().name());
                this.jsonFromRemote.put("tempUnit", this.userData.getTempUnitValues().name());
            } else {
                this.jsonFromRemote.put("chartColor", CHART_COLOR.STANDARD.getName());
                this.jsonFromRemote.put("dateFormat", DATE_FORMAT.MM_DD_YYYY);
                this.jsonFromRemote.put("tempUnit", TEMP_UNIT.C);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWebContent() {
        JSONObject jSONObject = this.jsonFromRemote;
        if (jSONObject != null) {
            final String string = jSONObject.toString();
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m756x5f9426c2(string);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m757x60ca79a1();
                }
            });
        }
    }

    /* renamed from: lambda$updateWebContent$1$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m756x5f9426c2(String str) {
        this.webView.loadUrl("javascript:getForm(" + str + ")");
    }

    /* renamed from: lambda$updateWebContent$2$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m757x60ca79a1() {
        this.webView.loadUrl("javascript:getForm(null)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String fetchUserChartRecord() throws InterruptedException {
        final AtomicReference atomicReference = new AtomicReference(null);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m741xc09a6fc4(atomicReference, countDownLatch);
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return (String) atomicReference.get();
    }

    /* renamed from: lambda$fetchUserChartRecord$3$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m741xc09a6fc4(AtomicReference atomicReference, CountDownLatch countDownLatch) {
        int deviceTypeValue;
        int subDeviceTypeValue;
        int iIntValue;
        int iIntValue2;
        try {
            try {
                Inverter currentInverter = this.userData.getCurrentInverter();
                UserVisitRecord userVisitRecord = this.userData.getUserVisitRecord();
                String serialNum = "";
                if (userVisitRecord != null || currentInverter != null) {
                    if (userVisitRecord != null && !Tool.isEmpty(userVisitRecord.getSerialNum())) {
                        serialNum = userVisitRecord.getSerialNum();
                    } else if (currentInverter != null && !Tool.isEmpty(currentInverter.getSerialNum())) {
                        serialNum = currentInverter.getSerialNum();
                    }
                }
                if (currentInverter != null) {
                    deviceTypeValue = currentInverter.getDeviceTypeValue();
                    subDeviceTypeValue = currentInverter.getSubDeviceTypeValue();
                    iIntValue = currentInverter.getPhaseValue().intValue();
                    iIntValue2 = currentInverter.getDtcValue().intValue();
                    currentInverter.getBatteryTypeFromModel();
                } else if (userVisitRecord != null) {
                    int deviceTypeValue2 = userVisitRecord.getDeviceTypeValue();
                    subDeviceTypeValue = userVisitRecord.getSubDeviceTypeValue();
                    iIntValue = userVisitRecord.getPhaseValue().intValue();
                    iIntValue2 = userVisitRecord.getDtcValue().intValue();
                    userVisitRecord.getBatteryTypeFromModel();
                    deviceTypeValue = deviceTypeValue2;
                } else {
                    deviceTypeValue = -1;
                    subDeviceTypeValue = -1;
                    iIntValue = -1;
                    iIntValue2 = -1;
                }
                long userId = this.userData.getUserId();
                HashMap map = new HashMap();
                map.put("deviceType", String.valueOf(deviceTypeValue));
                map.put("subDeviceType", String.valueOf(subDeviceTypeValue));
                map.put(TypedValues.CycleType.S_WAVE_PHASE, String.valueOf(iIntValue));
                map.put("dtc", String.valueOf(iIntValue2));
                map.put("userId", String.valueOf(userId));
                System.out.println("params == " + map);
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userChartRecord/getUserChartDetail", map);
                if (jSONObjectPostJson.getInt("code") == 200) {
                    JSONObject jSONObject = new JSONObject(this.userData.getUserChartRecord());
                    JSONObject userChartRecord = formatUserChartRecord(jSONObjectPostJson);
                    userChartRecord.put(TypedValues.CycleType.S_WAVE_PHASE, iIntValue);
                    userChartRecord.put("phaseValue", iIntValue);
                    userChartRecord.put("deviceType", deviceTypeValue);
                    userChartRecord.put("deviceTypeValue", deviceTypeValue);
                    userChartRecord.put("subDeviceType", subDeviceTypeValue);
                    userChartRecord.put("subDeviceTypeValue", subDeviceTypeValue);
                    userChartRecord.put("dtc", iIntValue2);
                    userChartRecord.put("dtcValue", iIntValue2);
                    String str = deviceTypeValue + "_" + subDeviceTypeValue + "_" + iIntValue + "_" + iIntValue2;
                    if (jSONObject.has(str)) {
                        jSONObject.put(str, userChartRecord);
                    } else {
                        jSONObject.put(str, userChartRecord);
                    }
                    jSONObjectPostJson.put("inverterSn", serialNum);
                    this.userData.setUserChartRecord(jSONObject.toString());
                    atomicReference.set(jSONObjectPostJson.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    private void saveData(final String str) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m747x510213a4(str);
            }
        }).start();
    }

    /* renamed from: lambda$saveData$6$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m747x510213a4(final String str) throws JSONException {
        try {
            HashMap map = new HashMap();
            map.put("chartColor", str);
            final boolean z = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userChartRecord/saveOrUpdateChartColor", map).getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m745x4e956de6(z, str);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m746x4fcbc0c5();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$saveData$4$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m745x4e956de6(boolean z, String str) {
        if (!z) {
            Tool.alert(this, getString(R.string.save_failed));
        } else {
            GlobalInfo.getInstance().getUserData().setChartColor(CHART_COLOR.valueOf(str));
            Tool.alert(this, getString(R.string.saved), new DialogInterface.OnDismissListener() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity.2
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    NormalSettingActivity.this.startActivity(new Intent(NormalSettingActivity.instance, (Class<?>) (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                    if (MainActivity.instance != null) {
                        MainActivity.instance.finish();
                    }
                }
            });
        }
    }

    /* renamed from: lambda$saveData$5$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m746x4fcbc0c5() {
        Tool.alert(this, getString(R.string.error_processing_request));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePreference(final String str) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m754xbfa7c7b0(str);
            }
        }).start();
    }

    /* renamed from: lambda$savePreference$9$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m754xbfa7c7b0(String str) throws JSONException {
        try {
            final Map<String, String> mapJsonStringToMap = jsonStringToMap(str);
            final boolean z = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userChartRecord/saveOrUpdatePreferences", mapJsonStringToMap).getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m752xbd3b21f2(z, mapJsonStringToMap);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m753xbe7174d1();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$savePreference$7$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m752xbd3b21f2(boolean z, Map map) {
        if (!z) {
            Tool.alert(this, getString(R.string.save_failed));
            return;
        }
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (map.containsKey("chartColor")) {
            userData.setChartColor(CHART_COLOR.valueOf((String) map.get("chartColor")));
        } else if (map.containsKey("tempUnit")) {
            userData.setTempUnit(TEMP_UNIT.valueOf((String) map.get("tempUnit")));
        } else if (map.containsKey("dateFormat")) {
            userData.setDateFormat(DATE_FORMAT.valueOf((String) map.get("dateFormat")));
        }
        Tool.alert(this, getString(R.string.saved), new DialogInterface.OnDismissListener() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity.3
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                NormalSettingActivity.this.startActivity(new Intent(NormalSettingActivity.instance, (Class<?>) (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                if (MainActivity.instance != null) {
                    MainActivity.instance.finish();
                }
            }
        });
    }

    /* renamed from: lambda$savePreference$8$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m753xbe7174d1() {
        Tool.alert(this, getString(R.string.error_processing_request));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveChartRecord(final String str) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m744x89a08d0a(str);
            }
        }).start();
    }

    /* renamed from: lambda$saveChartRecord$12$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m744x89a08d0a(String str) throws JSONException {
        int deviceTypeValue;
        int subDeviceTypeValue;
        int iIntValue;
        int iIntValue2;
        try {
            HashMap<String, String> mapJsonStringToMap = Tool.jsonStringToMap(str);
            Inverter currentInverter = this.userData.getCurrentInverter();
            UserVisitRecord userVisitRecord = this.userData.getUserVisitRecord();
            if (currentInverter != null) {
                deviceTypeValue = currentInverter.getDeviceTypeValue();
                subDeviceTypeValue = currentInverter.getSubDeviceTypeValue();
                iIntValue = currentInverter.getPhaseValue().intValue();
                iIntValue2 = currentInverter.getDtcValue().intValue();
            } else if (userVisitRecord != null) {
                deviceTypeValue = userVisitRecord.getDeviceTypeValue();
                subDeviceTypeValue = userVisitRecord.getSubDeviceTypeValue();
                iIntValue = userVisitRecord.getPhaseValue().intValue();
                iIntValue2 = userVisitRecord.getDtcValue().intValue();
            } else {
                deviceTypeValue = -1;
                subDeviceTypeValue = -1;
                iIntValue = -1;
                iIntValue2 = -1;
            }
            if (currentInverter == null && userVisitRecord == null) {
                Tool.alert(instance, R.string.phrase_toast_no_device_specified);
                return;
            }
            mapJsonStringToMap.put("deviceType", String.valueOf(deviceTypeValue));
            mapJsonStringToMap.put("subDeviceType", String.valueOf(subDeviceTypeValue));
            mapJsonStringToMap.put(TypedValues.CycleType.S_WAVE_PHASE, String.valueOf(iIntValue));
            mapJsonStringToMap.put("dtc", String.valueOf(iIntValue2));
            System.out.println("params == " + mapJsonStringToMap);
            final boolean z = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userChartRecord/saveUserChart", mapJsonStringToMap).getBoolean("success");
            if (z) {
                JSONObject jSONObject = new JSONObject(this.userData.getUserChartRecord());
                mapJsonStringToMap.put("phaseValue", String.valueOf(iIntValue));
                mapJsonStringToMap.put("deviceTypeValue", String.valueOf(deviceTypeValue));
                mapJsonStringToMap.put("subDeviceTypeValue", String.valueOf(subDeviceTypeValue));
                mapJsonStringToMap.put("dtcValue", String.valueOf(iIntValue2));
                String json = new Gson().toJson(mapJsonStringToMap);
                String str2 = deviceTypeValue + "_" + subDeviceTypeValue + "_" + iIntValue + "_" + iIntValue2;
                if (jSONObject.has(str2)) {
                    jSONObject.put(str2, json);
                } else {
                    jSONObject.put(str2, json);
                }
                this.userData.setUserChartRecord(jSONObject.toString());
            }
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m742x8733e74c(z);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m743x886a3a2b();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: com.lux.luxcloud.view.userCenter.NormalSettingActivity$4, reason: invalid class name */
    class AnonymousClass4 implements DialogInterface.OnDismissListener {
        AnonymousClass4() {
        }

        /* renamed from: lambda$onDismiss$0$com-lux-luxcloud-view-userCenter-NormalSettingActivity$4, reason: not valid java name */
        /* synthetic */ void m758x9adf3a30() {
            NormalSettingActivity.this.webView.loadUrl("javascript:getForm()");
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            NormalSettingActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m758x9adf3a30();
                }
            });
        }
    }

    /* renamed from: lambda$saveChartRecord$10$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m742x8733e74c(boolean z) {
        if (z) {
            Tool.alert(this, getString(R.string.saved), new AnonymousClass4());
        } else {
            Tool.alert(this, getString(R.string.save_failed));
        }
    }

    /* renamed from: lambda$saveChartRecord$11$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m743x886a3a2b() {
        Tool.alert(this, getString(R.string.error_processing_request));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNewPageSetting(final Boolean bool) {
        final UserData userData = GlobalInfo.getInstance().getUserData();
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m751x28f433ee(sharedPreferences, bool, userData);
            }
        });
    }

    /* renamed from: lambda$saveNewPageSetting$14$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m751x28f433ee(SharedPreferences sharedPreferences, Boolean bool, final UserData userData) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putBoolean(Constants.useNewSettingPage, bool.booleanValue());
        editorEdit.apply();
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m750x27bde10f(userData);
            }
        });
    }

    /* renamed from: lambda$saveNewPageSetting$13$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m750x27bde10f(final UserData userData) {
        Tool.alert(instance, getString(R.string.wifi_connect_tcp_set_success), new DialogInterface.OnDismissListener() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity.5
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (MainActivity.instance != null) {
                    MainActivity.instance.finish();
                }
                Intent intent = new Intent(NormalSettingActivity.instance, (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class));
                intent.setFlags(335544320);
                NormalSettingActivity.this.startActivity(intent);
                NormalSettingActivity.instance.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveHtmlPageSetting(final Boolean bool) {
        final UserData userData = GlobalInfo.getInstance().getUserData();
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m749x1d58390d(sharedPreferences, bool, userData);
            }
        });
    }

    /* renamed from: lambda$saveHtmlPageSetting$16$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m749x1d58390d(SharedPreferences sharedPreferences, Boolean bool, final UserData userData) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putBoolean(Constants.useHtmlSettingPage, bool.booleanValue());
        editorEdit.apply();
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m748x1c21e62e(userData);
            }
        });
    }

    /* renamed from: lambda$saveHtmlPageSetting$15$com-lux-luxcloud-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m748x1c21e62e(final UserData userData) {
        Tool.alert(instance, getString(R.string.wifi_connect_tcp_set_success), new DialogInterface.OnDismissListener() { // from class: com.lux.luxcloud.view.userCenter.NormalSettingActivity.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (PlantListActivity.instance != null) {
                    PlantListActivity.instance.finish();
                }
                if (PlantOverviewActivity.instance != null) {
                    PlantOverviewActivity.instance.finish();
                }
                Intent intent = new Intent(NormalSettingActivity.instance, (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class));
                intent.setFlags(335544320);
                NormalSettingActivity.this.startActivity(intent);
                NormalSettingActivity.instance.finish();
            }
        });
    }

    public JSONObject formatUserChartRecord(JSONObject jSONObject) throws Exception {
        JSONObject jSONObject2 = new JSONObject();
        JSONArray jSONArray = jSONObject.getJSONArray("powerList");
        JSONArray jSONArray2 = jSONObject.getJSONArray("energyList");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject3 = jSONArray.getJSONObject(i);
            jSONObject2.put(jSONObject3.getString("value"), jSONObject3.getBoolean("checked"));
        }
        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
            JSONObject jSONObject4 = jSONArray2.getJSONObject(i2);
            jSONObject2.put(jSONObject4.getString("value"), jSONObject4.getBoolean("checked"));
        }
        return jSONObject2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean adaptUseSetting() {
        return getSharedPreferences("userInfo", 0).getBoolean(Constants.useNewSettingPage, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean adaptUseHtmlSetting() {
        return getSharedPreferences("userInfo", 0).getBoolean(Constants.useHtmlSettingPage, false);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        instance.handleBack();
    }

    public static Map<String, String> jsonStringToMap(String str) {
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.getString(next));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}