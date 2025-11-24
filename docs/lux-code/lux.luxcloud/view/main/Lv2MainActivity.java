package com.lux.luxcloud.view.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.savedstate.serialization.ClassDiscriminatorModeKt;
import com.alibaba.fastjson2.JSON;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.net.HttpHeaders;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.param.BattChgPARAM;
import com.lux.luxcloud.global.bean.param.PARAM;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.warranty.WarrantyActivity;
import com.lux.luxcloud.webView.CommonJsBridge;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Lv2MainActivity extends AppCompatActivity {
    public static Lv2MainActivity instance;
    private String appVersion;
    private CameraManager cameraManager;
    private LinearLayout deviceLinearLayout;
    private FusedLocationProviderClient fusedLocationClient;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private boolean isDarkTheme;
    private boolean isFlashOn = false;
    private ProgressBar loadProgressBar;
    private lv2WebAppSysInterface sysInterface;
    private ValueCallback<Uri[]> uploadMessage;
    private WebView webView;

    public interface LocationCallback {
        void onLocationReceived(double d, double d2);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(1);
        setContentView(R.layout.activity_lv2_main);
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.layoutInDisplayCutoutMode = 1;
                getWindow().setAttributes(attributes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        try {
            this.appVersion = "V" + getPackageManager().getPackageInfo(getApplication().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        instance = this;
        this.sysInterface = new lv2WebAppSysInterface(this, this.isDarkTheme);
        this.cameraManager = (CameraManager) getSystemService("camera");
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        initView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyStatusBarIconColor(String str) {
        getWindow().getDecorView().setSystemUiVisibility("light".equals(str) ? 9472 : 1280);
    }

    private void initView() {
        this.loadProgressBar = (ProgressBar) findViewById(R.id.load_progressBar);
        WebView webView = (WebView) findViewById(R.id.activity_lv2_main_webview);
        this.webView = webView;
        webView.setBackgroundColor(0);
        WebSettings settings = this.webView.getSettings();
        settings.setCacheMode(2);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setGeolocationEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(false);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= 28) {
            this.webView.getSettings().setMixedContentMode(0);
        }
        this.webView.clearCache(true);
        this.webView.clearHistory();
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity.1
            @Override // android.webkit.WebChromeClient
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                permissionRequest.grant(permissionRequest.getResources());
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                if (ContextCompat.checkSelfPermission(Lv2MainActivity.instance, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                    ActivityCompat.requestPermissions(Lv2MainActivity.instance, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    callback.invoke(str, true, false);
                    super.onGeolocationPermissionsShowPrompt(str, callback);
                }
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView2, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (Lv2MainActivity.this.uploadMessage != null) {
                    Lv2MainActivity.this.uploadMessage.onReceiveValue(null);
                }
                Lv2MainActivity.this.uploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                Lv2MainActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.FILECHOOSER_RESULTCODE);
                return true;
            }
        });
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity.2
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
                System.out.println("LuxPower - http start time: " + System.currentTimeMillis());
                Lv2MainActivity.this.loadProgressBar.setVisibility(0);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
                Lv2MainActivity.this.loadProgressBar.setVisibility(4);
                System.out.println("LuxPower - http onPageFinished url == " + str);
                System.out.println("LuxPower - http end time: " + System.currentTimeMillis());
            }
        });
        this.webView.addJavascriptInterface(new lv2WebAppInterface(this), "lv2WebAppInterface");
        this.webView.addJavascriptInterface(new lv2WebAppSysInterface(instance, this.isDarkTheme), "lv2Android");
        this.webView.addJavascriptInterface(new CommonJsBridge(this), "AppBridge");
        if (WarrantyActivity.testModeEnable) {
            this.webView.loadUrl("https://app_test.solarcloudsystem.com:8083/?v=" + System.currentTimeMillis());
        } else {
            this.webView.loadUrl("https://app.solarcloudsystem.com?v=" + System.currentTimeMillis());
        }
    }

    public boolean canGoBackInWebView() {
        WebView webView = this.webView;
        if (webView != null) {
            return webView.canGoBack();
        }
        return false;
    }

    public void goBackInWebView() {
        this.webView.goBack();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFlashAvailable() {
        try {
            return this.cameraManager.getCameraCharacteristics("0").get(CameraCharacteristics.FLASH_INFO_AVAILABLE) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFlashMode(boolean z) throws CameraAccessException {
        try {
            if (this.isFlashOn == z) {
                return;
            }
            this.cameraManager.setTorchMode("0", z);
            this.isFlashOn = z;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLastLocation(final LocationCallback locationCallback) {
        if (ActivityCompat.checkSelfPermission(instance, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(instance, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.fusedLocationClient.getLastLocation().addOnSuccessListener(instance, new OnSuccessListener() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    Lv2MainActivity.lambda$getLastLocation$0(locationCallback, (Location) obj);
                }
            });
        } else {
            ActivityCompat.requestPermissions(instance, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    static /* synthetic */ void lambda$getLastLocation$0(LocationCallback locationCallback, Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            System.out.println("latitude == " + latitude);
            System.out.println("longitude == " + longitude);
            locationCallback.onLocationReceived(latitude, longitude);
            return;
        }
        locationCallback.onLocationReceived(0.0d, 0.0d);
    }

    public JSONArray getParamInfos(List<PARAM> list, String str) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (list != null && !list.isEmpty()) {
            for (PARAM param : list) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("device", instance.inverter.getDeviceType());
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("holdParam", param.name());
                    jSONObject2.put("paramName", getLocalizedString(instance.getApplicationContext(), param.getResourceId(instance.inverter), str));
                    jSONObject2.put("visible", !param.checkInvisible(userData));
                    jSONObject2.put("deviceType", instance.inverter.getDeviceType());
                    jSONObject2.put("platformSpecificData", param.getPlatformSpecificData(userData));
                    if (param.getDescription(userData) != -1) {
                        jSONObject2.put("description", getString(param.getDescription(userData)));
                    }
                    jSONArray.put(jSONObject2);
                    jSONArray.put(jSONObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    public JSONArray getBattChgParamInfos(List<BattChgPARAM> list, String str, String str2) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (list != null && !list.isEmpty()) {
            for (BattChgPARAM battChgPARAM : list) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("holdParam", battChgPARAM.name());
                    jSONObject.put("paramName", getLocalizedString(instance.getApplicationContext(), battChgPARAM.getResourceId(instance.inverter), str2));
                    jSONObject.put("visible", !battChgPARAM.checkInvisible(userData));
                    jSONObject.put("enabled", battChgPARAM.checkEnabled(str));
                    jSONObject.put("platformSpecificData", battChgPARAM.getPlatformSpecificData(userData));
                    if (battChgPARAM.getDescription(userData) != -1) {
                        jSONObject.put("description", getString(battChgPARAM.getDescription(userData)));
                    }
                    jSONObject.put("deviceType", instance.inverter.getDeviceType());
                    jSONArray.put(jSONObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    public static String getLocalizedString(Context context, int i, String str) {
        Locale locale;
        if (str.contains("_")) {
            String[] strArrSplit = str.split("_");
            locale = new Locale(strArrSplit[0], strArrSplit[1]);
        } else {
            locale = new Locale(str);
        }
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration).getString(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String fetchWattNodeResult() throws InterruptedException {
        final AtomicReference atomicReference = new AtomicReference(null);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                Lv2MainActivity.lambda$fetchWattNodeResult$2(atomicReference, countDownLatch);
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

    static /* synthetic */ void lambda$fetchWattNodeResult$2(AtomicReference atomicReference, CountDownLatch countDownLatch) {
        try {
            try {
                HashMap map = new HashMap();
                map.put("inverterSn", instance.inverter.getSerialNum());
                System.out.println("params == " + map);
                final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/wattNode/read", map);
                if (jSONObjectPostJson.getBoolean("success")) {
                    atomicReference.set(jSONObjectPostJson.toString());
                } else {
                    atomicReference.set(null);
                    Lv2MainActivity lv2MainActivity = instance;
                    if (lv2MainActivity != null) {
                        lv2MainActivity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() throws JSONException {
                                Lv2MainActivity.instance.toast(jSONObjectPostJson);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void WriteWattNode(final HashMap<String, String> map) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m603xd0197a0b(map);
            }
        }).start();
    }

    /* renamed from: lambda$WriteWattNode$6$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m603xd0197a0b(HashMap map) throws JSONException {
        try {
            if (Tool.isEmpty((String) map.get("ctAmpsA")) || Tool.isEmpty((String) map.get("ctAmpsB")) || Tool.isEmpty((String) map.get("ctAmpsC"))) {
                Lv2MainActivity lv2MainActivity = instance;
                if (lv2MainActivity != null) {
                    lv2MainActivity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            Tool.alert(Lv2MainActivity.instance, R.string.page_maintain_remote_set_alert_param_empty);
                        }
                    });
                }
            } else {
                map.put("inverterSn", instance.inverter.getSerialNum());
                System.out.println("params == " + map);
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/wattNode/write", map);
                if (jSONObjectPostJson.getBoolean("success")) {
                    instance.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            Tool.alert(Lv2MainActivity.instance, R.string.local_set_result_success);
                        }
                    });
                } else {
                    final String string = jSONObjectPostJson.getString("msg");
                    Lv2MainActivity lv2MainActivity2 = instance;
                    if (lv2MainActivity2 != null) {
                        lv2MainActivity2.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity.3
                            @Override // java.lang.Runnable
                            public void run() {
                                if ("invalidUpdateFrequency".equals(string)) {
                                    Tool.alert(Lv2MainActivity.instance, Lv2MainActivity.this.getString(R.string.page_maintain_remote_set_result_invalidUpdateFrequency));
                                } else {
                                    Tool.alert(Lv2MainActivity.instance, Lv2MainActivity.this.getString(R.string.local_set_result_failed));
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            instance.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m602xd2d10ac();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$WriteWattNode$5$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m602xd2d10ac() {
        Tool.alert(instance, getString(R.string.error_processing_request));
    }

    public class lv2WebAppInterface {
        private Lv2MainActivity activity;

        lv2WebAppInterface(Lv2MainActivity lv2MainActivity) {
            this.activity = lv2MainActivity;
        }

        @JavascriptInterface
        public String getLocalLanguageResources() throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
            JSONObject jSONObject = new JSONObject();
            Resources resources = this.activity.getResources();
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
        public String getAppVersion() {
            return !Tool.isEmpty(Lv2MainActivity.instance.appVersion) ? Lv2MainActivity.this.appVersion : "";
        }

        @JavascriptInterface
        public String getParamVisible(String str, String str2, String str3) throws JSONException {
            List<String> list = (List) new Gson().fromJson(str, new TypeToken<List<String>>() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity.lv2WebAppInterface.1
            }.getType());
            if (!Tool.isEmpty(str2)) {
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(BattChgPARAM.valueOf((String) it.next()));
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("paramInfos", Lv2MainActivity.instance.getBattChgParamInfos(arrayList, str2, str3));
                return jSONObject.toString();
            }
            ArrayList arrayList2 = new ArrayList();
            for (String str4 : list) {
                if ("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE".equals(str4)) {
                    arrayList2.add(PARAM.valueOf("HOLD_BACK_UP_END_BATTERY_VOLTAGE"));
                } else {
                    arrayList2.add(PARAM.valueOf(str4));
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            JSONArray paramInfos = Lv2MainActivity.instance.getParamInfos(arrayList2, str3);
            for (int i = 0; i < paramInfos.length(); i++) {
                JSONObject jSONObject3 = paramInfos.getJSONObject(i);
                if (jSONObject3.has("holdParam")) {
                    String string = jSONObject3.getString("holdParam");
                    if ("HOLD_BACK_UP_SOC_LIMIT".equals(string)) {
                        jSONObject3.put("holdParam", "HOLD_AC_CHARGE_SOC_LIMIT");
                    } else if ("HOLD_BACK_UP_END_BATTERY_VOLTAGE".equals(string)) {
                        jSONObject3.put("holdParam", "HOLD_AC_CHARGE_END_BATTERY_VOLTAGE");
                    }
                }
            }
            jSONObject2.put("paramInfos", paramInfos);
            return jSONObject2.toString();
        }

        @JavascriptInterface
        public void onUrlChanged(String str) {
            System.out.println("isHomePage == " + str.equals("Home"));
        }

        @JavascriptInterface
        public void setCurrentInverter(String str) {
            if (Tool.isEmpty(str)) {
                return;
            }
            com.alibaba.fastjson2.JSONObject object = JSON.parseObject(str);
            Inverter inverter = new Inverter();
            inverter.setSerialNum(object.getString("serialNum"));
            inverter.setDatalogSn(object.getString("datalogSn"));
            inverter.setPlantId(Long.valueOf(object.getLongValue("plantId")));
            inverter.setDeviceType(Integer.valueOf(object.getIntValue("deviceType")));
            inverter.setSubDeviceType(Integer.valueOf(object.getIntValue("subDeviceType")));
            inverter.setPhase(Integer.valueOf(object.getIntValue(TypedValues.CycleType.S_WAVE_PHASE)));
            inverter.setOdm(Integer.valueOf(object.getIntValue("odm")));
            inverter.setDtc(Integer.valueOf(object.getIntValue("dtc")));
            inverter.setBatteryTypeFromModel(BATTERY_TYPE.valueOf(object.getString("batteryType")));
            inverter.setMachineType(object.getIntValue("machineType"));
            inverter.setModel(object.getLong("model"));
            inverter.setPowerRating(Integer.valueOf(object.getIntValue("powerRating")));
            inverter.setSlaveVersion(Integer.valueOf(object.getIntValue("slaveVersion")));
            inverter.setStandard(object.getString("standard"));
            inverter.setHardwareVersion(object.getIntValue("hardwareVersion"));
            inverter.setFwVersion(Integer.valueOf(object.getIntValue("fwVersion")));
            GlobalInfo.getInstance().getUserData().setCurrentInverter(inverter, true);
            Lv2MainActivity.instance.inverter = inverter;
        }

        @JavascriptInterface
        public String getCurrentUserInfo() {
            UserData userData = GlobalInfo.getInstance().getUserData();
            String token = userData.getToken();
            long clusterId = userData.getClusterId();
            Locale locale = Locale.getDefault();
            Intent intent = Lv2MainActivity.this.getIntent();
            String stringExtra = (intent == null || !intent.getBooleanExtra("formPlantList", false)) ? null : intent.getStringExtra("plantListArray");
            System.out.println("Lv2Main token ==" + token);
            return token + "&" + locale + (Tool.isEmpty(stringExtra) ? "" : "&" + stringExtra) + "&" + clusterId;
        }

        @JavascriptInterface
        public String wattNodeRead() {
            String strFetchWattNodeResult = Lv2MainActivity.instance.inverter != null ? Lv2MainActivity.this.fetchWattNodeResult() : null;
            return Tool.isEmpty(strFetchWattNodeResult) ? "" : strFetchWattNodeResult;
        }

        @JavascriptInterface
        public void wattNodeWrite(String str) {
            if (Lv2MainActivity.instance.inverter != null) {
                Lv2MainActivity.this.WriteWattNode(Tool.jsonStringToMap(str));
            }
        }

        @JavascriptInterface
        public String generateRegisterId() {
            return String.format("%s-%s-%s-%s", "RG", GlobalInfo.getInstance().getUserData().getRole().getOwnerLevelCheck() ? "DIS" : "INS", Tool.formatRegisterIdTime(new Date()), generateRandomAlphanumeric(6));
        }

        private String generateRandomAlphanumeric(int i) {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i2 = 0; i2 < i; i2++) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(36)));
            }
            return sb.toString();
        }

        @JavascriptInterface
        public void exit() {
            Lv2MainActivity.this.startActivity(new Intent(Lv2MainActivity.instance, (Class<?>) (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
            Lv2MainActivity.instance.finish();
        }
    }

    public class lv2WebAppSysInterface {
        private String actionType;
        private Context context;
        private boolean isDarkTheme;

        public lv2WebAppSysInterface(Context context, boolean z) {
            this.context = context;
            this.isDarkTheme = z;
        }

        @JavascriptInterface
        public boolean getDarkTheme() {
            return this.isDarkTheme;
        }

        @JavascriptInterface
        public void manageFlashLight(boolean z) throws CameraAccessException {
            if (Lv2MainActivity.this.isFlashAvailable()) {
                Lv2MainActivity.this.setFlashMode(z);
            }
        }

        @JavascriptInterface
        public void onThemeChanged(final String str) {
            Lv2MainActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$lv2WebAppSysInterface$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m617x7a429095(str);
                }
            });
        }

        /* renamed from: lambda$onThemeChanged$0$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m617x7a429095(String str) {
            Lv2MainActivity.this.applyStatusBarIconColor(str);
        }

        @JavascriptInterface
        public void checkAndRequestPermissions() {
            Lv2MainActivity.this.checkAndRequestPermissionsIfNeeded();
        }

        @JavascriptInterface
        public void downloadFile(final String str, final String str2) {
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$lv2WebAppSysInterface$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.m614x425dc9b6(str, str2);
                }
            }).start();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0124  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x013d  */
        /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v1 */
        /* JADX WARN: Type inference failed for: r11v2 */
        /* JADX WARN: Type inference failed for: r11v3, types: [java.net.HttpURLConnection] */
        /* JADX WARN: Type inference failed for: r11v5, types: [java.net.HttpURLConnection] */
        /* JADX WARN: Type inference failed for: r11v9, types: [java.net.HttpURLConnection] */
        /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r1v1 */
        /* JADX WARN: Type inference failed for: r1v10, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r1v2 */
        /* JADX WARN: Type inference failed for: r1v3, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r1v5 */
        /* JADX WARN: Type inference failed for: r1v6 */
        /* renamed from: lambda$downloadFile$5$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        /* synthetic */ void m614x425dc9b6(java.lang.String r11, java.lang.String r12) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 329
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.Lv2MainActivity.lv2WebAppSysInterface.m614x425dc9b6(java.lang.String, java.lang.String):void");
        }

        /* renamed from: lambda$downloadFile$1$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m610x999fa93a() {
            Lv2MainActivity.this.loadProgressBar.setVisibility(0);
        }

        /* renamed from: lambda$downloadFile$2$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m611x3cf3159(File file) {
            Toast.makeText(this.context, Lv2MainActivity.this.getString(R.string.saved) + " " + file.getAbsolutePath(), 1).show();
        }

        /* renamed from: lambda$downloadFile$3$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m612x6dfeb978() {
            Toast.makeText(this.context, Lv2MainActivity.this.getString(R.string.save_failed), 0).show();
        }

        /* renamed from: lambda$downloadFile$4$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m613xd82e4197() {
            Lv2MainActivity.this.loadProgressBar.setVisibility(4);
        }

        @JavascriptInterface
        public void saveBase64Image(String str, String str2) throws IOException {
            try {
                byte[] bArrDecode = Base64.decode(str.substring(str.indexOf(",") + 1), 0);
                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(externalStoragePublicDirectory, str2 + ".png");
                int i = 1;
                while (file.exists()) {
                    file = new File(externalStoragePublicDirectory, str2 + "_" + i + ".png");
                    i++;
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bArrDecode);
                fileOutputStream.flush();
                fileOutputStream.close();
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file));
                this.context.sendBroadcast(intent);
                Toast.makeText(this.context, Lv2MainActivity.this.getString(R.string.saved) + " " + file.getAbsolutePath(), 1).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this.context, Lv2MainActivity.this.getString(R.string.save_failed), 0).show();
            }
        }

        @JavascriptInterface
        public void openCamera(String str) {
            this.actionType = str;
            Lv2MainActivity.this.sysInterface.setActionType(str);
            new IntentIntegrator((Activity) this.context).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).setPrompt(Lv2MainActivity.this.getString(R.string.warranty_scan_tip_text)).setCameraId(0).setBeepEnabled(true).setBarcodeImageEnabled(false).initiateScan();
        }

        public String getActionType() {
            return this.actionType;
        }

        public void setActionType(String str) {
            this.actionType = str;
            System.out.println("actionType set to == " + this.actionType);
        }

        @JavascriptInterface
        public void getLocation() {
            Lv2MainActivity.this.getLastLocation(new LocationCallback() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity.lv2WebAppSysInterface.1
                @Override // com.lux.luxcloud.view.main.Lv2MainActivity.LocationCallback
                public void onLocationReceived(double d, double d2) {
                    System.out.println("latitude == " + d);
                    System.out.println("longitude == " + d2);
                    Lv2MainActivity.this.webView.evaluateJavascript("javascript:onLocationReceived(" + d + ", " + d2 + ")", null);
                }
            });
        }

        @JavascriptInterface
        public boolean checkHasCustomPlatform() {
            UserData userData = GlobalInfo.getInstance().getUserData();
            return PLATFORM.SUNBEAT.equals(userData.getPlatform()) || PLATFORM.FORTRESS.equals(userData.getPlatform()) || PLATFORM.BOER.equals(userData.getPlatform()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.INCCO.equals(userData.getPlatform());
        }

        @JavascriptInterface
        public boolean isEG4() {
            return PLATFORM.EG4.name().equals(Custom.APP_USER_PLATFORM);
        }

        @JavascriptInterface
        public void checkUpdates() throws PackageManager.NameNotFoundException {
            try {
                PackageInfo packageInfo = Lv2MainActivity.this.getPackageManager().getPackageInfo(Lv2MainActivity.this.getApplication().getPackageName(), 0);
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageInfo.packageName));
                intent.setAction("android.intent.action.VIEW");
                Lv2MainActivity.this.startActivity(intent);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Tool.alert(Lv2MainActivity.instance, Lv2MainActivity.this.getString(R.string.check_new_version_fail_tip));
            }
        }

        @JavascriptInterface
        public void logout() {
            Lv2MainActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$lv2WebAppSysInterface$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m616xe82af70e();
                }
            });
        }

        /* renamed from: lambda$logout$7$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m616xe82af70e() {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$lv2WebAppSysInterface$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m615x7dfb6eef();
                }
            }, 300L);
        }

        /* renamed from: lambda$logout$6$com-lux-luxcloud-view-main-Lv2MainActivity$lv2WebAppSysInterface, reason: not valid java name */
        /* synthetic */ void m615x7dfb6eef() {
            Intent intent = new Intent(Lv2MainActivity.instance, (Class<?>) LoginActivity.class);
            intent.putExtra("fromLogout", true);
            Lv2MainActivity.this.startActivity(intent);
            Lv2MainActivity.this.finish();
            if (Lv2MainActivity.instance != null) {
                Lv2MainActivity.instance.finish();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        if (i == Constants.FILECHOOSER_RESULTCODE) {
            if (this.uploadMessage == null) {
                return;
            }
            this.uploadMessage.onReceiveValue((i2 != -1 || intent == null || (data = intent.getData()) == null) ? null : new Uri[]{data});
            this.uploadMessage = null;
            return;
        }
        IntentResult activityResult = IntentIntegrator.parseActivityResult(i, i2, intent);
        if (activityResult != null) {
            String contents = activityResult.getContents();
            try {
                String actionType = this.sysInterface.getActionType();
                if (Tool.isEmpty(contents)) {
                    return;
                }
                parseQRCode(contents, actionType);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m605xfafd50();
                    }
                });
                return;
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    /* renamed from: lambda$onActivityResult$7$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m605xfafd50() {
        Tool.alert(instance, getString(R.string.unsupported_qr_code_tip));
    }

    private void parseQRCode(String str, String str2) throws JSONException {
        if ("addDongle".equals(str2)) {
            String[] strArrSplit = str.split("_");
            if (strArrSplit[0].matches("^[A-Za-z]{2}\\d{8}$")) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("scanContent", strArrSplit[0]);
                if (strArrSplit.length > 1 && !Tool.isEmpty(strArrSplit[1])) {
                    jSONObject.put("PIN", strArrSplit[1]);
                }
                jSONObject.put(ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "addDongle");
                this.webView.loadUrl("javascript:getAction('" + jSONObject + "')");
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m608lambda$parseQRCode$8$comluxluxcloudviewmainLv2MainActivity();
                }
            });
            return;
        }
        if ("getInverterWarranty".equals(str2)) {
            if (str.length() == 10) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("scanContent", str);
                jSONObject2.put(ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "getInverterWarranty");
                this.webView.loadUrl("javascript:getAction('" + jSONObject2 + "')");
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m609lambda$parseQRCode$9$comluxluxcloudviewmainLv2MainActivity();
                }
            });
            return;
        }
        if ("scanUserManual".equals(str2)) {
            if (str.matches("^http://res\\.solarcloudsystem\\.com:8083/.+$")) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(str));
                intent.setAction("android.intent.action.VIEW");
                startActivity(intent);
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m606lambda$parseQRCode$10$comluxluxcloudviewmainLv2MainActivity();
                }
            });
            return;
        }
        if ("register".equals(str2)) {
            JSONObject jSONObject3 = new JSONObject(str);
            if (jSONObject3.has("customerCode")) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("customerCode", jSONObject3.getString("customerCode"));
                jSONObject4.put(ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "register");
                if (jSONObject3.has("continent")) {
                    jSONObject4.put("continent", jSONObject3.getString("continent"));
                }
                if (jSONObject3.has("region")) {
                    jSONObject4.put("region", jSONObject3.getString("region"));
                }
                if (jSONObject3.has("country")) {
                    jSONObject4.put("country", jSONObject3.getString("country"));
                }
                if (jSONObject3.has("timezone")) {
                    jSONObject4.put("timezone", jSONObject3.getString("timezone"));
                }
                this.webView.loadUrl("javascript:getAction('" + jSONObject4 + "')");
                return;
            }
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m607lambda$parseQRCode$11$comluxluxcloudviewmainLv2MainActivity();
            }
        });
    }

    /* renamed from: lambda$parseQRCode$8$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m608lambda$parseQRCode$8$comluxluxcloudviewmainLv2MainActivity() {
        Tool.alert(instance, getString(R.string.phase_qr_code_not_meet_require_tip));
    }

    /* renamed from: lambda$parseQRCode$9$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m609lambda$parseQRCode$9$comluxluxcloudviewmainLv2MainActivity() {
        Tool.alert(instance, getString(R.string.phase_qr_code_not_meet_require_tip_4_inverter_warranty));
    }

    /* renamed from: lambda$parseQRCode$10$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m606lambda$parseQRCode$10$comluxluxcloudviewmainLv2MainActivity() {
        Tool.alert(instance, getString(R.string.phase_qr_code_incorrect_tip));
    }

    /* renamed from: lambda$parseQRCode$11$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m607lambda$parseQRCode$11$comluxluxcloudviewmainLv2MainActivity() {
        Tool.alert(instance, getString(R.string.unsupported_qr_code_tip));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == Constants.FILECHOOSER_RESULTCODE) {
            int length = iArr.length;
            for (int i2 = 0; i2 < length && iArr[i2] == 0; i2++) {
            }
            return;
        }
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndRequestPermissionsIfNeeded() {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") != 0) {
                arrayList.add("android.permission.READ_MEDIA_IMAGES");
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
            }
            if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
        }
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            arrayList.add("android.permission.POST_NOTIFICATIONS");
        }
        if (arrayList.isEmpty()) {
            return;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[0]), Constants.FILECHOOSER_RESULTCODE);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener() { // from class: com.lux.luxcloud.view.main.Lv2MainActivity$$ExternalSyntheticLambda3
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) throws IOException {
                    this.f$0.m604xb2cbe46a((Location) obj);
                }
            });
        }
    }

    /* renamed from: lambda$getLastLocation$12$com-lux-luxcloud-view-main-Lv2MainActivity, reason: not valid java name */
    /* synthetic */ void m604xb2cbe46a(Location location) throws IOException {
        if (location == null) {
            Toast.makeText(this, "Location not available", 0).show();
            return;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        System.out.println("经度 longitude == " + longitude);
        System.out.println("纬度 latitude ==" + latitude);
        getAddressFromLocation(latitude, longitude);
    }

    private void getAddressFromLocation(double d, double d2) throws IOException {
        try {
            List<Address> fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(d, d2, 1);
            if (fromLocation.isEmpty()) {
                return;
            }
            Address address = fromLocation.get(0);
            String str = "Country: " + address.getCountryName() + " (" + address.getCountryCode() + ")\nAdmin Area: " + address.getAdminArea() + "\nSub Admin Area: " + address.getSubAdminArea() + "\nLocality: " + address.getLocality() + "\nSub Locality: " + address.getSubLocality() + "\nThoroughfare: " + address.getThoroughfare() + "\nSub Thoroughfare: " + address.getSubThoroughfare();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(HttpHeaders.LOCATION);
            builder.setMessage(str).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to get street address", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void toast(org.json.JSONObject r6) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 626
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.Lv2MainActivity.toast(org.json.JSONObject):void");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    public static void deleteWebViewCacheDir(Context context) {
        File file = new File(context.getCacheDir(), "webview");
        if (file.exists()) {
            deleteDirectory(file);
        }
    }

    public static void deleteDirectory(File file) {
        String[] list;
        if (file != null && file.isDirectory() && (list = file.list()) != null) {
            for (String str : list) {
                deleteDirectory(new File(file, str));
            }
        }
        file.delete();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (instance.canGoBackInWebView()) {
            instance.goBackInWebView();
            return;
        }
        startActivity(new Intent(this, (Class<?>) (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
        finish();
        Lv2MainActivity lv2MainActivity = instance;
        if (lv2MainActivity != null) {
            lv2MainActivity.finish();
        }
    }
}