package com.lux.luxcloud.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ManageNotificationActivity extends Activity {
    public static ManageNotificationActivity instance;
    private boolean isDarkTheme;
    UserData userData = GlobalInfo.getInstance().getUserData();
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_manage_notifications);
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m733x8a1b2e81(view);
            }
        });
        initializeViews();
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m733x8a1b2e81(View view) {
        handleBack();
    }

    private void initializeViews() {
        GlobalInfo.getInstance().getUserData();
        ((ConstraintLayout) findViewById(R.id.activity_Allow_NotificationsLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.openNotificationSettings(view);
            }
        });
        ((ToggleButton) findViewById(R.id.activity_Allow_Notifications_toggleButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.openNotificationSettings(view);
            }
        });
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webView = webView;
        webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
            }
        });
        WebSettings settings = this.webView.getSettings();
        this.webView.addJavascriptInterface(new WebAppInterfaceNotification(), "WebAppInterfaceNotification");
        configureWebSettings(settings);
        this.webView.loadUrl("file:///android_asset/module/notifications/notifications_index.html");
    }

    private void handleBack() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openNotificationSettings(View view) {
        Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        intent.setFlags(268435456);
        startActivity(intent);
    }

    private void configureWebSettings(WebSettings webSettings) {
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(2);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WebAppInterfaceNotification {
        private WebAppInterfaceNotification() {
        }

        @JavascriptInterface
        public String getLocalLanguageResources() throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
            JSONObject jSONObject = new JSONObject();
            Resources resources = ManageNotificationActivity.instance.getResources();
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
        public void saveNotifications(String str) {
            ManageNotificationActivity.this.saveNotificationSettings(str);
        }

        @JavascriptInterface
        public boolean isShowWeather() {
            return ManageNotificationActivity.this.userData.isAllowViewerVisitWeatherSet() || ManageNotificationActivity.this.userData.getClusterId() == 4;
        }

        @JavascriptInterface
        public boolean isShowNoticeTOU() {
            return ManageNotificationActivity.this.userData.isAllowViewerVisitOptimalSet();
        }

        @JavascriptInterface
        public void getNotifications() {
            ManageNotificationActivity.this.getNotification(new ValueCallback() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$WebAppInterfaceNotification$$ExternalSyntheticLambda0
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    this.f$0.m737x4041408d((JSONObject) obj);
                }
            });
        }

        /* renamed from: lambda$getNotifications$0$com-lux-luxcloud-view-userCenter-ManageNotificationActivity$WebAppInterfaceNotification, reason: not valid java name */
        /* synthetic */ void m735x6e31b68b(JSONObject jSONObject) {
            ManageNotificationActivity.this.webView.evaluateJavascript("handleFormResult('" + jSONObject + "')", null);
        }

        /* renamed from: lambda$getNotifications$2$com-lux-luxcloud-view-userCenter-ManageNotificationActivity$WebAppInterfaceNotification, reason: not valid java name */
        /* synthetic */ void m737x4041408d(final JSONObject jSONObject) {
            if (jSONObject != null) {
                ManageNotificationActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$WebAppInterfaceNotification$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m735x6e31b68b(jSONObject);
                    }
                });
            } else {
                ManageNotificationActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$WebAppInterfaceNotification$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m736x57397b8c();
                    }
                });
            }
        }

        /* renamed from: lambda$getNotifications$1$com-lux-luxcloud-view-userCenter-ManageNotificationActivity$WebAppInterfaceNotification, reason: not valid java name */
        /* synthetic */ void m736x57397b8c() {
            ManageNotificationActivity.this.webView.evaluateJavascript("handleFormResult(null)", null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getNotification(final ValueCallback<JSONObject> valueCallback) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m729x61dde70f(valueCallback);
            }
        }).start();
    }

    /* renamed from: lambda$getNotification$2$com-lux-luxcloud-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m729x61dde70f(final ValueCallback valueCallback) {
        HashMap map = new HashMap();
        map.put("userId", String.valueOf(this.userData.getUserId()));
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/user/getUserAppNoticeInfo", map);
        if (jSONObjectPostJson != null) {
            try {
                if (jSONObjectPostJson.getBoolean("success")) {
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ManageNotificationActivity.lambda$getNotification$1(valueCallback, jSONObjectPostJson);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static /* synthetic */ void lambda$getNotification$1(ValueCallback valueCallback, JSONObject jSONObject) {
        if (valueCallback != null) {
            valueCallback.onReceiveValue(jSONObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNotificationSettings(final String str) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m734x45200594(str);
            }
        }).start();
    }

    /* renamed from: lambda$saveNotificationSettings$3$com-lux-luxcloud-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m734x45200594(String str) {
        handleSaveResponse(HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/user/saveUserAppNoticeInfo", jsonStringToMap(str)));
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
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

    /* JADX WARN: Removed duplicated region for block: B:6:0x0013 A[Catch: Exception -> 0x001c, TRY_LEAVE, TryCatch #0 {Exception -> 0x001c, blocks: (B:3:0x0002, B:5:0x000a, B:6:0x0013), top: B:11:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleSaveResponse(org.json.JSONObject r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L13
            java.lang.String r0 = "success"
            boolean r2 = r2.getBoolean(r0)     // Catch: java.lang.Exception -> L1c
            if (r2 == 0) goto L13
            com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda1 r2 = new com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda1     // Catch: java.lang.Exception -> L1c
            r2.<init>()     // Catch: java.lang.Exception -> L1c
            r1.runOnUiThread(r2)     // Catch: java.lang.Exception -> L1c
            goto L24
        L13:
            com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda2 r2 = new com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda2     // Catch: java.lang.Exception -> L1c
            r2.<init>()     // Catch: java.lang.Exception -> L1c
            r1.runOnUiThread(r2)     // Catch: java.lang.Exception -> L1c
            goto L24
        L1c:
            com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda3 r2 = new com.lux.luxcloud.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda3
            r2.<init>()
            r1.runOnUiThread(r2)
        L24:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.userCenter.ManageNotificationActivity.handleSaveResponse(org.json.JSONObject):void");
    }

    /* renamed from: lambda$handleSaveResponse$4$com-lux-luxcloud-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m730x9ea5fa70() {
        Tool.alert(this, getString(R.string.local_set_result_success));
    }

    /* renamed from: lambda$handleSaveResponse$5$com-lux-luxcloud-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m731x591b9af1() {
        Tool.alert(this, getString(R.string.local_set_result_failed));
    }

    /* renamed from: lambda$handleSaveResponse$6$com-lux-luxcloud-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m732x13913b72() {
        Toast.makeText(this, R.string.phrase_toast_network_error, 0).show();
    }
}