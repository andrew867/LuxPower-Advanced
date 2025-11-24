package com.lux.luxcloud.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Consumer;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class OctopusChargeActivity extends Activity {
    public static OctopusChargeActivity instance;
    private boolean isDarkTheme;
    private JSONObject octopusChargeFromRemote;
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_octopus_charge);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        initUI();
    }

    private void initUI() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m766x470b0877(view);
            }
        });
        ((TextView) findViewById(R.id.titleTextView)).setText(getString(R.string.octopus_charge));
        setupWebView();
    }

    /* renamed from: lambda$initUI$0$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m766x470b0877(View view) {
        handleBack();
    }

    private void setupWebView() {
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webView = webView;
        webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                if ("file:///android_asset/module/octopusCharge/octopusCharge_index.html".equals(str)) {
                    OctopusChargeActivity.this.updateWebContent();
                }
            }
        });
        this.webView.addJavascriptInterface(new WebAppInterfaceOctopusCharge(), "WebAppInterfaceOctopusCharge");
        configureWebSettings(this.webView.getSettings());
        this.webView.loadUrl("file:///android_asset/module/octopusCharge/octopusCharge_index.html");
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
    public void updateWebContent() {
        JSONObject jSONObject = this.octopusChargeFromRemote;
        if (jSONObject != null) {
            final String string = jSONObject.toString();
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m770x3e48a64c(string);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m771x3f7ef92b();
                }
            });
        }
    }

    /* renamed from: lambda$updateWebContent$1$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m770x3e48a64c(String str) {
        this.webView.loadUrl("javascript:getList(" + str + ")");
    }

    /* renamed from: lambda$updateWebContent$2$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m771x3f7ef92b() {
        this.webView.loadUrl("javascript:getList(null)");
    }

    private void handleBack() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        fetchOctopusChargeData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateWebContent();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchOctopusChargeData(final Runnable runnable) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m763xa3b34db3(runnable);
            }
        }).start();
    }

    /* renamed from: lambda$fetchOctopusChargeData$5$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m763xa3b34db3(final Runnable runnable) {
        HashMap map = new HashMap();
        map.put("page", "1");
        map.put("rows", "6");
        try {
            this.octopusChargeFromRemote = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/octopusCharge/inverter/list", map);
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    OctopusChargeActivity.lambda$fetchOctopusChargeData$3(runnable);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m762xa27cfad4();
                }
            });
            e.printStackTrace();
        }
    }

    static /* synthetic */ void lambda$fetchOctopusChargeData$3(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    /* renamed from: lambda$fetchOctopusChargeData$4$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m762xa27cfad4() {
        Tool.alert(this, getString(R.string.error_fetching_data));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WebAppInterfaceOctopusCharge {
        private WebAppInterfaceOctopusCharge() {
        }

        @JavascriptInterface
        public String getList() throws JSONException {
            if (OctopusChargeActivity.this.octopusChargeFromRemote != null) {
                return OctopusChargeActivity.this.octopusChargeFromRemote.toString();
            }
            return null;
        }

        @JavascriptInterface
        public void saveForm(String str) {
            OctopusChargeActivity.this.saveData(str, new Consumer() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda4
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m778x24d04eb2((Boolean) obj);
                }
            });
        }

        /* renamed from: lambda$saveForm$1$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m778x24d04eb2(Boolean bool) {
            if (bool.booleanValue()) {
                final OctopusChargeActivity octopusChargeActivity = OctopusChargeActivity.this;
                octopusChargeActivity.fetchOctopusChargeData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        octopusChargeActivity.updateWebContent();
                    }
                });
            }
        }

        @JavascriptInterface
        public void getForm(String str) {
            OctopusChargeActivity.this.getOptimalSet(str, new ValueCallback() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda5
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    this.f$0.m774x93a2a334((JSONObject) obj);
                }
            });
        }

        /* renamed from: lambda$getForm$2$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m772x23c9c832(JSONObject jSONObject) {
            OctopusChargeActivity.this.webView.evaluateJavascript("handleFormResult('" + jSONObject + "')", null);
        }

        /* renamed from: lambda$getForm$4$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m774x93a2a334(final JSONObject jSONObject) {
            if (jSONObject != null) {
                OctopusChargeActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m772x23c9c832(jSONObject);
                    }
                });
            } else {
                OctopusChargeActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m773xdbb635b3();
                    }
                });
            }
        }

        /* renamed from: lambda$getForm$3$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m773xdbb635b3() {
            OctopusChargeActivity.this.webView.evaluateJavascript("handleFormResult(null)", null);
        }

        @JavascriptInterface
        public void getTableList(String str, String str2) {
            OctopusChargeActivity.this.getDetails(str, str2, new ValueCallback() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda2
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    this.f$0.m777x5b965a6d((JSONObject) obj);
                }
            });
        }

        /* renamed from: lambda$getTableList$5$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m775xebbd7f6b(JSONObject jSONObject) {
            OctopusChargeActivity.this.webView.evaluateJavascript("handleFormResult('" + jSONObject + "')", null);
        }

        /* renamed from: lambda$getTableList$7$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m777x5b965a6d(final JSONObject jSONObject) {
            if (jSONObject != null) {
                OctopusChargeActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m775xebbd7f6b(jSONObject);
                    }
                });
            } else {
                OctopusChargeActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m776xa3a9ecec();
                    }
                });
            }
        }

        /* renamed from: lambda$getTableList$6$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m776xa3a9ecec() {
            OctopusChargeActivity.this.webView.evaluateJavascript("handleFormResult(null)", null);
        }

        @JavascriptInterface
        public String getLocalLanguageResources() throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
            JSONObject jSONObject = new JSONObject();
            Resources resources = OctopusChargeActivity.instance.getResources();
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
        public void setEnableStatus(String str, String str2) {
            OctopusChargeActivity.this.enableDevice(str, str2, new Consumer() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda3
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m779x45101396((Boolean) obj);
                }
            });
        }

        /* renamed from: lambda$setEnableStatus$9$com-lux-luxcloud-view-userCenter-OctopusChargeActivity$WebAppInterfaceOctopusCharge, reason: not valid java name */
        /* synthetic */ void m779x45101396(Boolean bool) {
            if (bool.booleanValue()) {
                final OctopusChargeActivity octopusChargeActivity = OctopusChargeActivity.this;
                octopusChargeActivity.fetchOctopusChargeData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$WebAppInterfaceOctopusCharge$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        octopusChargeActivity.updateWebContent();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveData(final String str, final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m769x322338ec(str, consumer);
            }
        }).start();
    }

    /* renamed from: lambda$saveData$8$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m769x322338ec(String str, final Consumer consumer) throws JSONException {
        try {
            final JSONObject jSONObjectMultiPartPostJson = HttpTool.multiPartPostJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/octopusDeviceAdvancedSet/saveOrUpdate", str);
            final boolean z = jSONObjectMultiPartPostJson.getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f$0.m767x2fb6932e(z, jSONObjectMultiPartPostJson, consumer);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m768x30ece60d(consumer);
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$saveData$6$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m767x2fb6932e(boolean z, JSONObject jSONObject, Consumer consumer) throws JSONException {
        if (z) {
            Tool.alert(this, getString(R.string.saved));
        } else {
            toast(jSONObject);
        }
        consumer.accept(Boolean.valueOf(z));
    }

    /* renamed from: lambda$saveData$7$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m768x30ece60d(Consumer consumer) {
        Tool.alert(this, getString(R.string.error_processing_request));
        consumer.accept(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getOptimalSet(final String str, final ValueCallback<JSONObject> valueCallback) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m765x174d3dfa(str, valueCallback);
            }
        }).start();
    }

    /* renamed from: lambda$getOptimalSet$10$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m765x174d3dfa(String str, final ValueCallback valueCallback) {
        final JSONObject jSONObjectPostJson;
        HashMap map = new HashMap();
        map.put("serialNum", str);
        try {
            jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "/web/maintain/octopusDeviceAdvancedSet/getSet", map);
        } catch (Exception e) {
            e.printStackTrace();
            jSONObjectPostJson = null;
        }
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                OctopusChargeActivity.lambda$getOptimalSet$9(valueCallback, jSONObjectPostJson);
            }
        });
    }

    static /* synthetic */ void lambda$getOptimalSet$9(ValueCallback valueCallback, JSONObject jSONObject) {
        if (valueCallback != null) {
            valueCallback.onReceiveValue(jSONObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDetails(final String str, final String str2, final ValueCallback<JSONObject> valueCallback) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m764x9a729568(str, str2, valueCallback);
            }
        }).start();
    }

    /* renamed from: lambda$getDetails$12$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m764x9a729568(String str, String str2, final ValueCallback valueCallback) {
        final JSONObject jSONObjectPostJson;
        HashMap map = new HashMap();
        map.put("serialNum", str);
        map.put("dateText", str2);
        try {
            jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/octopusCharge/setDetail/list", map);
        } catch (Exception e) {
            e.printStackTrace();
            jSONObjectPostJson = null;
        }
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                OctopusChargeActivity.lambda$getDetails$11(valueCallback, jSONObjectPostJson);
            }
        });
    }

    static /* synthetic */ void lambda$getDetails$11(ValueCallback valueCallback, JSONObject jSONObject) {
        if (valueCallback != null) {
            valueCallback.onReceiveValue(jSONObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDevice(final String str, final String str2, final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m761x96080432(str, str2, consumer);
            }
        }).start();
    }

    /* renamed from: lambda$enableDevice$15$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m761x96080432(String str, String str2, final Consumer consumer) throws JSONException {
        JSONObject jSONObjectPostJson;
        try {
            HashMap map = new HashMap();
            map.put("serialNum", str);
            if (str2.equals("enable")) {
                jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/optimalSet/enableDevice", map);
            } else {
                jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/optimalSet/disableDevice", map);
            }
            final boolean z = jSONObjectPostJson.getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m759x939b5e74(z, consumer);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.OctopusChargeActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m760x94d1b153(consumer);
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$enableDevice$13$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m759x939b5e74(boolean z, Consumer consumer) {
        if (z) {
            Tool.alert(this, getString(R.string.saved));
        } else {
            Tool.alert(this, getString(R.string.save_failed));
        }
        consumer.accept(Boolean.valueOf(z));
    }

    /* renamed from: lambda$enableDevice$14$com-lux-luxcloud-view-userCenter-OctopusChargeActivity, reason: not valid java name */
    /* synthetic */ void m760x94d1b153(Consumer consumer) {
        Tool.alert(this, getString(R.string.error_processing_request));
        consumer.accept(false);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            startActivity(new Intent(this, (Class<?>) NewUserCenterActivity.class));
            finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void toast(org.json.JSONObject r5) throws org.json.JSONException {
        /*
            r4 = this;
            com.lux.luxcloud.view.userCenter.OctopusChargeActivity r0 = com.lux.luxcloud.view.userCenter.OctopusChargeActivity.instance     // Catch: java.lang.Exception -> Lb7
            if (r0 == 0) goto Lbb
            r1 = 1
            if (r5 != 0) goto L17
            android.content.Context r5 = r0.getApplicationContext()     // Catch: java.lang.Exception -> Lb7
            r0 = 2131887250(0x7f120492, float:1.9409102E38)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r0, r1)     // Catch: java.lang.Exception -> Lb7
            r5.show()     // Catch: java.lang.Exception -> Lb7
            goto Lbb
        L17:
            java.lang.String r0 = "msg"
            java.lang.String r5 = r5.getString(r0)     // Catch: java.lang.Exception -> Lb7
            int r0 = r5.hashCode()     // Catch: java.lang.Exception -> Lb7
            r2 = 3
            r3 = 2
            switch(r0) {
                case -1814651686: goto L4f;
                case -75196522: goto L45;
                case 1099156916: goto L3b;
                case 1258055325: goto L31;
                case 1705806578: goto L27;
                default: goto L26;
            }     // Catch: java.lang.Exception -> Lb7
        L26:
            goto L59
        L27:
            java.lang.String r0 = "REMOTE_SET_ERROR"
            boolean r5 = r5.equals(r0)     // Catch: java.lang.Exception -> Lb7
            if (r5 == 0) goto L59
            r5 = 4
            goto L5a
        L31:
            java.lang.String r0 = "notAllowRemoteTechSupport"
            boolean r5 = r5.equals(r0)     // Catch: java.lang.Exception -> Lb7
            if (r5 == 0) goto L59
            r5 = r2
            goto L5a
        L3b:
            java.lang.String r0 = "SERVER_HTTP_EXCEPTION"
            boolean r5 = r5.equals(r0)     // Catch: java.lang.Exception -> Lb7
            if (r5 == 0) goto L59
            r5 = r3
            goto L5a
        L45:
            java.lang.String r0 = "PARAM_ERROR"
            boolean r5 = r5.equals(r0)     // Catch: java.lang.Exception -> Lb7
            if (r5 == 0) goto L59
            r5 = 0
            goto L5a
        L4f:
            java.lang.String r0 = "DEVICE_OFFLINE"
            boolean r5 = r5.equals(r0)     // Catch: java.lang.Exception -> Lb7
            if (r5 == 0) goto L59
            r5 = r1
            goto L5a
        L59:
            r5 = -1
        L5a:
            if (r5 == 0) goto La6
            if (r5 == r1) goto L95
            if (r5 == r3) goto L84
            if (r5 == r2) goto L73
            com.lux.luxcloud.view.userCenter.OctopusChargeActivity r5 = com.lux.luxcloud.view.userCenter.OctopusChargeActivity.instance     // Catch: java.lang.Exception -> Lb7
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> Lb7
            r0 = 2131886563(0x7f1201e3, float:1.9407708E38)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r0, r1)     // Catch: java.lang.Exception -> Lb7
            r5.show()     // Catch: java.lang.Exception -> Lb7
            goto Lbb
        L73:
            com.lux.luxcloud.view.userCenter.OctopusChargeActivity r5 = com.lux.luxcloud.view.userCenter.OctopusChargeActivity.instance     // Catch: java.lang.Exception -> Lb7
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> Lb7
            r0 = 2131886557(0x7f1201dd, float:1.9407696E38)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r0, r1)     // Catch: java.lang.Exception -> Lb7
            r5.show()     // Catch: java.lang.Exception -> Lb7
            goto Lbb
        L84:
            com.lux.luxcloud.view.userCenter.OctopusChargeActivity r5 = com.lux.luxcloud.view.userCenter.OctopusChargeActivity.instance     // Catch: java.lang.Exception -> Lb7
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> Lb7
            r0 = 2131886559(0x7f1201df, float:1.94077E38)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r0, r1)     // Catch: java.lang.Exception -> Lb7
            r5.show()     // Catch: java.lang.Exception -> Lb7
            goto Lbb
        L95:
            com.lux.luxcloud.view.userCenter.OctopusChargeActivity r5 = com.lux.luxcloud.view.userCenter.OctopusChargeActivity.instance     // Catch: java.lang.Exception -> Lb7
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> Lb7
            r0 = 2131886554(0x7f1201da, float:1.940769E38)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r0, r1)     // Catch: java.lang.Exception -> Lb7
            r5.show()     // Catch: java.lang.Exception -> Lb7
            goto Lbb
        La6:
            com.lux.luxcloud.view.userCenter.OctopusChargeActivity r5 = com.lux.luxcloud.view.userCenter.OctopusChargeActivity.instance     // Catch: java.lang.Exception -> Lb7
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> Lb7
            r0 = 2131886558(0x7f1201de, float:1.9407698E38)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r0, r1)     // Catch: java.lang.Exception -> Lb7
            r5.show()     // Catch: java.lang.Exception -> Lb7
            goto Lbb
        Lb7:
            r5 = move-exception
            r5.printStackTrace()
        Lbb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.userCenter.OctopusChargeActivity.toast(org.json.JSONObject):void");
    }
}