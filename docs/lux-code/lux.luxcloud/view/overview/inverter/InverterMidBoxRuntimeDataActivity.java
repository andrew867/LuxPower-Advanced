package com.lux.luxcloud.view.overview.inverter;

import android.app.Activity;
import android.app.UiModeManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.version.Custom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class InverterMidBoxRuntimeDataActivity extends Activity {
    public static InverterMidBoxRuntimeDataActivity instance;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private boolean isDarkTheme;
    private JSONObject jsonFromRemote;
    private LinearLayout linearLayout3;
    private boolean pageFinished;
    private TextView titleTextView;
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(1);
        setContentView(R.layout.activity_battery_params);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        UserData userData = GlobalInfo.getInstance().getUserData();
        Inverter currentInverter = userData.getCurrentInverter();
        this.inverter = currentInverter;
        if (currentInverter != null) {
            getMidBoxRuntime(currentInverter.getSerialNum());
        }
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.inverter.InverterMidBoxRuntimeDataActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m670x1599f1aa(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.titleTextView);
        this.titleTextView = textView;
        textView.setText("MidBox Runtime Data");
        this.linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        if (userData.getCurrentPlant() != null) {
            this.inverterList = userData.getInvertersByPlant(userData.getCurrentPlant().getPlantId());
        }
        if (this.inverterList == null) {
            this.inverterList = new ArrayList();
        }
        this.inverterSpinner = (Spinner) findViewById(R.id.activity_battery_params_inverter_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webView = webView;
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(2);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setSupportZoom(true);
        settings.setMediaPlaybackRequiresUserGesture(true);
        settings.setGeolocationEnabled(true);
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
        CookieManager.getInstance().setAcceptCookie(true);
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.overview.inverter.InverterMidBoxRuntimeDataActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
                InverterMidBoxRuntimeDataActivity.this.inverterSpinner.setEnabled(false);
                InverterMidBoxRuntimeDataActivity.this.linearLayout3.setVisibility(8);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) throws JSONException {
                super.onPageFinished(webView2, str);
                webView2.setVisibility(0);
                InverterMidBoxRuntimeDataActivity.this.pageFinished = true;
                InverterMidBoxRuntimeDataActivity.this.inverterSpinner.setEnabled(false);
                InverterMidBoxRuntimeDataActivity.this.linearLayout3.setVisibility(8);
                InverterMidBoxRuntimeDataActivity.this.webView.loadUrl("javascript:getMidBoxData(" + InverterMidBoxRuntimeDataActivity.this.jsonFromRemote + ")");
                if (InverterMidBoxRuntimeDataActivity.this.inverter == null) {
                    if (InverterMidBoxRuntimeDataActivity.this.webView != null) {
                        InverterMidBoxRuntimeDataActivity.this.webView.loadUrl("javascript:getData()");
                        return;
                    }
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("hasCustomPlatform", InverterMidBoxRuntimeDataActivity.instance.checkHasCustomPlatform());
                    jSONObject.put("isEG4", InverterMidBoxRuntimeDataActivity.instance.isEG4());
                    jSONObject.put("getCustomPlatform", InverterMidBoxRuntimeDataActivity.instance.getCustomPlatform());
                    if (InverterMidBoxRuntimeDataActivity.this.webView != null) {
                        InverterMidBoxRuntimeDataActivity.this.webView.loadUrl("javascript:getData(" + jSONObject + ")");
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.webView.loadUrl("file:///android_asset/module/flowDiagram/midbox_index.html");
        this.webView.addJavascriptInterface(new WebAppInterfaceMidBoxRuntime(this), "WebAppInterfaceMidBoxRuntime");
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-overview-inverter-InverterMidBoxRuntimeDataActivity, reason: not valid java name */
    /* synthetic */ void m670x1599f1aa(View view) {
        finish();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMidBoxRuntime(final String str) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.overview.inverter.InverterMidBoxRuntimeDataActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m669xb4abe0b0(str);
            }
        }).start();
    }

    /* renamed from: lambda$getMidBoxRuntime$2$com-lux-luxcloud-view-overview-inverter-InverterMidBoxRuntimeDataActivity, reason: not valid java name */
    /* synthetic */ void m669xb4abe0b0(String str) {
        try {
            HashMap map = new HashMap();
            map.put("serialNum", str);
            System.out.println("params == " + map);
            JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/midbox/getMidboxRuntime", map);
            this.jsonFromRemote = jSONObjectPostJson;
            if (!this.pageFinished || jSONObjectPostJson == null) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.overview.inverter.InverterMidBoxRuntimeDataActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m668x80fdb5ef();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$getMidBoxRuntime$1$com-lux-luxcloud-view-overview-inverter-InverterMidBoxRuntimeDataActivity, reason: not valid java name */
    /* synthetic */ void m668x80fdb5ef() {
        this.webView.loadUrl("javascript:getMidBoxData(" + this.jsonFromRemote + ")");
    }

    private class WebAppInterfaceMidBoxRuntime {
        private InverterMidBoxRuntimeDataActivity activity;

        WebAppInterfaceMidBoxRuntime(InverterMidBoxRuntimeDataActivity inverterMidBoxRuntimeDataActivity) {
            this.activity = inverterMidBoxRuntimeDataActivity;
        }

        @JavascriptInterface
        public boolean checkHasCustomPlatform() {
            return InverterMidBoxRuntimeDataActivity.instance.checkHasCustomPlatform();
        }

        @JavascriptInterface
        public String getCustomPlatform() {
            return InverterMidBoxRuntimeDataActivity.instance.getCustomPlatform();
        }

        @JavascriptInterface
        public boolean isEG4() {
            return InverterMidBoxRuntimeDataActivity.instance.isEG4();
        }

        @JavascriptInterface
        public void refreshData() {
            InverterMidBoxRuntimeDataActivity.instance.getMidBoxRuntime(InverterMidBoxRuntimeDataActivity.this.inverter.getSerialNum());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHasCustomPlatform() {
        UserData userData = GlobalInfo.getInstance().getUserData();
        return PLATFORM.SUNBEAT.equals(userData.getPlatform()) || PLATFORM.FORTRESS.equals(userData.getPlatform()) || PLATFORM.BOER.equals(userData.getPlatform()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.INCCO.equals(userData.getPlatform());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCustomPlatform() {
        return GlobalInfo.getInstance().getUserData().getPlatform().name();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEG4() {
        return PLATFORM.EG4.name().equals(Custom.APP_USER_PLATFORM);
    }
}