package com.nfcx.eg4.view.overview.inverter;

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
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        UserData userData = GlobalInfo.getInstance().getUserData();
        Inverter currentInverter = userData.getCurrentInverter();
        this.inverter = currentInverter;
        if (currentInverter != null) {
            getMidBoxRuntime(currentInverter.getSerialNum());
        }
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.inverter.InverterMidBoxRuntimeDataActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m585x10794bc(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.titleTextView);
        this.titleTextView = textView;
        textView.setText("Energy Flow");
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
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.nfcx.eg4.view.overview.inverter.InverterMidBoxRuntimeDataActivity.1
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
                InverterMidBoxRuntimeDataActivity.this.inverterSpinner.setEnabled(false);
                InverterMidBoxRuntimeDataActivity.this.linearLayout3.setVisibility(8);
                InverterMidBoxRuntimeDataActivity.this.pageFinished = true;
                if (InverterMidBoxRuntimeDataActivity.this.inverter == null) {
                    if (InverterMidBoxRuntimeDataActivity.this.webView != null) {
                        InverterMidBoxRuntimeDataActivity.this.webView.loadUrl("javascript:getData()");
                    }
                } else {
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
                InverterMidBoxRuntimeDataActivity.this.webView.loadUrl("javascript:getMidBoxData(" + InverterMidBoxRuntimeDataActivity.this.jsonFromRemote + ")");
            }
        });
        this.webView.loadUrl("file:///android_asset/module/flowDiagram/midbox_index.html");
        this.webView.addJavascriptInterface(new WebAppInterfaceMidBoxRuntime(this), "WebAppInterfaceMidBoxRuntime");
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-overview-inverter-InverterMidBoxRuntimeDataActivity, reason: not valid java name */
    /* synthetic */ void m585x10794bc(View view) {
        finish();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMidBoxRuntime(final String str) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.overview.inverter.InverterMidBoxRuntimeDataActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m584xc9c5f6c2(str);
            }
        }).start();
    }

    /* renamed from: lambda$getMidBoxRuntime$2$com-nfcx-eg4-view-overview-inverter-InverterMidBoxRuntimeDataActivity, reason: not valid java name */
    /* synthetic */ void m584xc9c5f6c2(String str) {
        try {
            HashMap map = new HashMap();
            map.put("serialNum", str);
            System.out.println("params == " + map);
            JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/midbox/getMidboxRuntime", map);
            this.jsonFromRemote = jSONObjectPostJson;
            if (!this.pageFinished || jSONObjectPostJson == null) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.overview.inverter.InverterMidBoxRuntimeDataActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m583xe69a4381();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$getMidBoxRuntime$1$com-nfcx-eg4-view-overview-inverter-InverterMidBoxRuntimeDataActivity, reason: not valid java name */
    /* synthetic */ void m583xe69a4381() {
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
        public String getMidBoxRuntime() throws JSONException {
            if (InverterMidBoxRuntimeDataActivity.this.jsonFromRemote != null) {
                return InverterMidBoxRuntimeDataActivity.this.jsonFromRemote.toString();
            }
            return null;
        }

        @JavascriptInterface
        public void refreshData() {
            InverterMidBoxRuntimeDataActivity.instance.getMidBoxRuntime(InverterMidBoxRuntimeDataActivity.this.inverter.getSerialNum());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHasCustomPlatform() {
        GlobalInfo.getInstance().getUserData();
        return PLATFORM.SUNBEAT.name().equals("EG4") || PLATFORM.FORTRESS.name().equals("EG4") || PLATFORM.BOER.name().equals("EG4") || PLATFORM.EG4.name().equals("EG4") || PLATFORM.INCCO.name().equals("EG4");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCustomPlatform() {
        GlobalInfo.getInstance().getUserData();
        return "EG4";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEG4() {
        return PLATFORM.EG4.name().equals("EG4");
    }
}