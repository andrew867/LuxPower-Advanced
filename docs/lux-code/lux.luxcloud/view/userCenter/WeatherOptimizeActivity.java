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
public class WeatherOptimizeActivity extends Activity {
    private static WeatherOptimizeActivity instance;
    private boolean isDarkTheme;
    UserData userData = GlobalInfo.getInstance().getUserData();
    private JSONObject weatherOptimizeFromRemote;
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_weather_optimize);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        setupUI();
    }

    private void setupUI() {
        setTitleText(getString(R.string.weather_optimize));
        setupWebView();
        setupBackButton();
        loadWebContent();
    }

    private void setTitleText(String str) {
        ((TextView) findViewById(R.id.titleTextView)).setText(str);
    }

    private void setupWebView() {
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webView = webView;
        webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                if ("file:///android_asset/module/weatherOptimize/weatherOptimize_index.html".equals(str)) {
                    WeatherOptimizeActivity.this.updateWebContent();
                }
            }
        });
        configureWebSettings(this.webView.getSettings());
        this.webView.addJavascriptInterface(new WebAppInterfaceWeatherOptimize(), "WebAppInterfaceWeatherOptimize");
    }

    private void configureWebSettings(WebSettings webSettings) {
        webSettings.setCacheMode(2);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
    }

    private void setupBackButton() {
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m794x7aa9c4dd(view);
            }
        });
    }

    /* renamed from: lambda$setupBackButton$0$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m794x7aa9c4dd(View view) {
        handleBackPress();
    }

    private void handleBackPress() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
        }
    }

    private void loadWebContent() {
        this.webView.loadUrl("file:///android_asset/module/weatherOptimize/weatherOptimize_index.html");
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        fetchWeatherData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateWebContent();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchWeatherData(final Runnable runnable) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m789xac2b39d2(runnable);
            }
        }).start();
    }

    /* renamed from: lambda$fetchWeatherData$3$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m789xac2b39d2(final Runnable runnable) {
        HashMap map = new HashMap();
        map.put("page", "1");
        map.put("rows", "6");
        try {
            this.weatherOptimizeFromRemote = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/weatherSet/inverter/list", map);
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    WeatherOptimizeActivity.lambda$fetchWeatherData$1(runnable);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m788x1f3e22b3();
                }
            });
            e.printStackTrace();
        }
    }

    static /* synthetic */ void lambda$fetchWeatherData$1(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    /* renamed from: lambda$fetchWeatherData$2$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m788x1f3e22b3() {
        Tool.alert(this, getString(R.string.error_fetching_data));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWebContent() {
        JSONObject jSONObject = this.weatherOptimizeFromRemote;
        if (jSONObject != null) {
            final String string = jSONObject.toString();
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m795x424d9527(string);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m796xcf3aac46();
                }
            });
        }
    }

    /* renamed from: lambda$updateWebContent$4$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m795x424d9527(String str) {
        this.webView.loadUrl("javascript:getList(" + str + ")");
    }

    /* renamed from: lambda$updateWebContent$5$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m796xcf3aac46() {
        this.webView.loadUrl("javascript:getList(null)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WebAppInterfaceWeatherOptimize {
        private WebAppInterfaceWeatherOptimize() {
        }

        @JavascriptInterface
        public String getList() throws JSONException {
            if (WeatherOptimizeActivity.this.weatherOptimizeFromRemote != null) {
                return WeatherOptimizeActivity.this.weatherOptimizeFromRemote.toString();
            }
            return null;
        }

        @JavascriptInterface
        public void getTableList(String str, String str2) throws JSONException {
            WeatherOptimizeActivity.this.getSetDetailList(str, str2, new ValueCallback() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda2
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    this.f$0.m800xd3846828((JSONObject) obj);
                }
            });
        }

        /* renamed from: lambda$getTableList$0$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize, reason: not valid java name */
        /* synthetic */ void m798x174de26(JSONObject jSONObject) {
            WeatherOptimizeActivity.this.webView.evaluateJavascript("handleFormResult('" + jSONObject + "')", null);
        }

        /* renamed from: lambda$getTableList$2$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize, reason: not valid java name */
        /* synthetic */ void m800xd3846828(final JSONObject jSONObject) {
            if (jSONObject != null) {
                WeatherOptimizeActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m798x174de26(jSONObject);
                    }
                });
            } else {
                WeatherOptimizeActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m799xea7ca327();
                    }
                });
            }
        }

        /* renamed from: lambda$getTableList$1$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize, reason: not valid java name */
        /* synthetic */ void m799xea7ca327() {
            WeatherOptimizeActivity.this.webView.evaluateJavascript("handleFormResult(null)", null);
        }

        @JavascriptInterface
        public void saveForm(String str) throws JSONException {
            WeatherOptimizeActivity.this.saveWeatherSet(WeatherOptimizeActivity.jsonStringToMap(str), new Consumer() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m801x12aed9f5((Boolean) obj);
                }
            });
        }

        /* renamed from: lambda$saveForm$4$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize, reason: not valid java name */
        /* synthetic */ void m801x12aed9f5(Boolean bool) {
            if (bool.booleanValue()) {
                final WeatherOptimizeActivity weatherOptimizeActivity = WeatherOptimizeActivity.this;
                weatherOptimizeActivity.fetchWeatherData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        weatherOptimizeActivity.updateWebContent();
                    }
                });
            }
        }

        @JavascriptInterface
        public void editForm(String str) throws JSONException {
            WeatherOptimizeActivity.this.editWeatherSet(WeatherOptimizeActivity.jsonStringToMap(str), new Consumer() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda3
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m797x84d473c4((Boolean) obj);
                }
            });
        }

        /* renamed from: lambda$editForm$6$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize, reason: not valid java name */
        /* synthetic */ void m797x84d473c4(Boolean bool) {
            if (bool.booleanValue()) {
                final WeatherOptimizeActivity weatherOptimizeActivity = WeatherOptimizeActivity.this;
                weatherOptimizeActivity.fetchWeatherData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        weatherOptimizeActivity.updateWebContent();
                    }
                });
            }
        }

        @JavascriptInterface
        public void setEnableStatus(String str, String str2) {
            WeatherOptimizeActivity.this.enableDevice(str, str2, new Consumer() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda4
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m802x2dda78d5((Boolean) obj);
                }
            });
        }

        /* renamed from: lambda$setEnableStatus$8$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize, reason: not valid java name */
        /* synthetic */ void m802x2dda78d5(Boolean bool) {
            if (bool.booleanValue()) {
                final WeatherOptimizeActivity weatherOptimizeActivity = WeatherOptimizeActivity.this;
                weatherOptimizeActivity.fetchWeatherData(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$WebAppInterfaceWeatherOptimize$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        weatherOptimizeActivity.updateWebContent();
                    }
                });
            }
        }

        @JavascriptInterface
        public String getLocalLanguageResources() throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
            JSONObject jSONObject = new JSONObject();
            Resources resources = WeatherOptimizeActivity.instance.getResources();
            for (Field field : R.string.class.getFields()) {
                try {
                    jSONObject.put(field.getName(), resources.getString(field.getInt(null)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return jSONObject.toString();
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public void getSetDetailList(final String str, final String str2, final ValueCallback<JSONObject> valueCallback) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m790x5cabe3b7(str, str2, valueCallback);
            }
        }).start();
    }

    /* renamed from: lambda$getSetDetailList$7$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m790x5cabe3b7(String str, String str2, final ValueCallback valueCallback) {
        final JSONObject jSONObjectPostJson;
        HashMap map = new HashMap();
        map.put("serialNum", str);
        map.put("dateText", str2);
        try {
            jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/weatherSet/setDetail/list", map);
        } catch (Exception e) {
            e.printStackTrace();
            jSONObjectPostJson = null;
        }
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WeatherOptimizeActivity.lambda$getSetDetailList$6(valueCallback, jSONObjectPostJson);
            }
        });
    }

    static /* synthetic */ void lambda$getSetDetailList$6(ValueCallback valueCallback, JSONObject jSONObject) {
        if (valueCallback != null) {
            valueCallback.onReceiveValue(jSONObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveWeatherSet(final Map<String, String> map, final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m791xcb2cb1c7(map, consumer);
            }
        }).start();
    }

    /* renamed from: lambda$saveWeatherSet$10$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m791xcb2cb1c7(Map map, final Consumer consumer) throws JSONException {
        try {
            final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/weatherSet/addDevice", map);
            final boolean z = jSONObjectPostJson.getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m792xc8aa4746(z, jSONObjectPostJson, consumer);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m793x55975e65(consumer);
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$saveWeatherSet$8$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m792xc8aa4746(boolean z, JSONObject jSONObject, Consumer consumer) {
        if (z) {
            Tool.alert(this, getString(R.string.saved));
        } else {
            try {
                Tool.alert(this, getString(R.string.save_failed) + ", " + jSONObject.getString("msg"));
            } catch (JSONException e) {
                Tool.alert(this, getString(R.string.error_processing_request));
                throw new RuntimeException(e);
            }
        }
        consumer.accept(Boolean.valueOf(z));
    }

    /* renamed from: lambda$saveWeatherSet$9$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m793x55975e65(Consumer consumer) {
        Tool.alert(this, getString(R.string.error_processing_request));
        consumer.accept(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void editWeatherSet(final Map<String, String> map, final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m784x6ae5df1(map, consumer);
            }
        }).start();
    }

    /* renamed from: lambda$editWeatherSet$13$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m784x6ae5df1(Map map, final Consumer consumer) throws JSONException {
        try {
            final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/weatherSet/edit", map);
            final boolean z = jSONObjectPostJson.getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m782xecd42fb3(z, jSONObjectPostJson, consumer);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m783x79c146d2(consumer);
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$editWeatherSet$11$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m782xecd42fb3(boolean z, JSONObject jSONObject, Consumer consumer) {
        if (z) {
            Tool.alert(this, getString(R.string.saved));
        } else {
            try {
                Tool.alert(this, getString(R.string.save_failed) + ", " + jSONObject.getString("msg"));
            } catch (JSONException e) {
                Tool.alert(this, getString(R.string.error_processing_request));
                throw new RuntimeException(e);
            }
        }
        consumer.accept(Boolean.valueOf(z));
    }

    /* renamed from: lambda$editWeatherSet$12$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m783x79c146d2(Consumer consumer) {
        Tool.alert(this, getString(R.string.error_processing_request));
        consumer.accept(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDevice(final String str, final String str2, final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m787x8dd2e34f(str, str2, consumer);
            }
        }).start();
    }

    /* renamed from: lambda$enableDevice$16$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m787x8dd2e34f(String str, String str2, final Consumer consumer) throws JSONException {
        JSONObject jSONObjectPostJson;
        try {
            HashMap map = new HashMap();
            map.put("serialNum", str);
            if (str2.equals("enable")) {
                jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/weatherSet/enableDevice", map);
            } else {
                jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/weatherSet/disableDevice", map);
            }
            final boolean z = jSONObjectPostJson.getBoolean("success");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m785x73f8b511(z, consumer);
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.userCenter.WeatherOptimizeActivity$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m786xe5cc30(consumer);
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$enableDevice$14$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m785x73f8b511(boolean z, Consumer consumer) {
        if (z) {
            Tool.alert(this, getString(R.string.saved));
        } else {
            Tool.alert(this, getString(R.string.save_failed));
        }
        consumer.accept(Boolean.valueOf(z));
    }

    /* renamed from: lambda$enableDevice$15$com-lux-luxcloud-view-userCenter-WeatherOptimizeActivity, reason: not valid java name */
    /* synthetic */ void m786xe5cc30(Consumer consumer) {
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
}