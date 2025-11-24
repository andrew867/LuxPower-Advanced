package com.nfcx.eg4.view.userCenter;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.user.CHART_COLOR;
import com.nfcx.eg4.global.bean.user.DATE_FORMAT;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.global.bean.user.TEMP_UNIT;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        setupListeners();
    }

    private void setupListeners() {
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m660x528a49bd(view);
            }
        });
        WebView webView = (WebView) findViewById(R.id.chart_Color_setting);
        this.webView = webView;
        webView.setWebChromeClient(new WebChromeClient());
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity.1
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
                } else if (str.contains("manageColumn_index.html")) {
                    webView2.loadUrl("javascript:getForm()");
                }
                webView2.evaluateJavascript("changeEleColor('" + String.format("#%06X", Integer.valueOf(NormalSettingActivity.instance.getColor(R.color.themeColor) & ViewCompat.MEASURED_SIZE_MASK)) + "');", null);
            }
        });
        this.webView.addJavascriptInterface(new WebAppInterfaceNormalSetting(), "WebAppInterfaceNormalSetting");
        configureWebSettings(this.webView.getSettings());
        this.webView.loadUrl("file:///android_asset/module/normalSetting/normalSetting_index.html");
    }

    /* renamed from: lambda$setupListeners$0$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m660x528a49bd(View view) {
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
        public void saveForm(String str) {
            NormalSettingActivity.this.savePreference(str);
        }

        @JavascriptInterface
        public boolean getUseNewSettingPage() {
            return NormalSettingActivity.this.adaptUseSetting();
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
    }

    private String getUserChartRecord() {
        final AtomicReference atomicReference = new AtomicReference(new JSONObject());
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m654x82ec05ef(atomicReference);
            }
        }).start();
        return atomicReference.toString();
    }

    /* renamed from: lambda$getUserChartRecord$2$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m654x82ec05ef(AtomicReference atomicReference) {
        try {
            String serialNum = this.userData.getCurrentInverter().getSerialNum();
            long userId = this.userData.getUserId();
            HashMap map = new HashMap();
            map.put("serialNum", serialNum);
            map.put("userId", String.valueOf(userId));
            atomicReference.set(HttpTool.postJson("https://as.luxpowertek.com/WManage/api/userChartRecord/getUserChartRecord", map));
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m653x55136b90();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$getUserChartRecord$1$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m653x55136b90() {
        Tool.alert(this, getString(R.string.error_processing_request));
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
            if (!Tool.isEmpty(this.userData.getChartColor().toString())) {
                this.jsonFromRemote.put("chartColor", this.userData.getChartColor().getName());
                this.jsonFromRemote.put("dateFormat", this.userData.getDateFormat().name());
                this.jsonFromRemote.put("tempUnit", this.userData.getTempUnit().name());
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
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m661xce8643ae(string);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m662xfc5ede0d();
                }
            });
        }
    }

    /* renamed from: lambda$updateWebContent$3$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m661xce8643ae(String str) {
        this.webView.loadUrl("javascript:getForm(" + str + ")");
    }

    /* renamed from: lambda$updateWebContent$4$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m662xfc5ede0d() {
        this.webView.loadUrl("javascript:getForm(null)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePreference(final String str) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m659x4192220(str);
            }
        }).start();
    }

    /* renamed from: lambda$savePreference$7$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m659x4192220(String str) throws JSONException {
        try {
            final Map<String, String> mapJsonStringToMap = jsonStringToMap(str);
            final boolean z = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userChartRecord/saveOrUpdatePreferences", mapJsonStringToMap).getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m657xa867ed62(z, mapJsonStringToMap);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m658xd64087c1();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$savePreference$5$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m657xa867ed62(boolean z, Map map) {
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
        Tool.alert(this, getString(R.string.saved), new DialogInterface.OnDismissListener() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                NormalSettingActivity.this.startActivity(new Intent(NormalSettingActivity.instance, (Class<?>) (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                if (MainActivity.instance != null) {
                    MainActivity.instance.finish();
                }
            }
        });
    }

    /* renamed from: lambda$savePreference$6$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m658xd64087c1() {
        Tool.alert(this, getString(R.string.error_processing_request));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNewPageSetting(final Boolean bool) {
        final UserData userData = GlobalInfo.getInstance().getUserData();
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m656x8e9fad78(sharedPreferences, bool, userData);
            }
        });
    }

    /* renamed from: lambda$saveNewPageSetting$9$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m656x8e9fad78(SharedPreferences sharedPreferences, Boolean bool, final UserData userData) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putBoolean(Constants.useNewSettingPage, bool.booleanValue());
        editorEdit.apply();
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m655x60c71319(userData);
            }
        });
    }

    /* renamed from: lambda$saveNewPageSetting$8$com-nfcx-eg4-view-userCenter-NormalSettingActivity, reason: not valid java name */
    /* synthetic */ void m655x60c71319(final UserData userData) {
        Tool.alert(instance, getString(R.string.wifi_connect_tcp_set_success), new DialogInterface.OnDismissListener() { // from class: com.nfcx.eg4.view.userCenter.NormalSettingActivity.3
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
    public boolean adaptUseSetting() {
        return getSharedPreferences("userInfo", 0).getBoolean(Constants.useNewSettingPage, true);
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