package com.nfcx.eg4.view.overview.inverter;

import android.app.Activity;
import android.app.UiModeManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class InverterBatteryParamsActivity extends Activity {
    public static InverterBatteryParamsActivity instance;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private boolean isDarkTheme;
    private JSONObject jsonFromRemote;
    private boolean pageFinished;
    private TextView titleTextView;
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_battery_params);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        UserData userData = GlobalInfo.getInstance().getUserData();
        Inverter currentInverter = userData.getCurrentInverter();
        this.inverter = currentInverter;
        if (currentInverter != null) {
            getBatteryInfo(currentInverter.getSerialNum());
        }
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.inverter.InverterBatteryParamsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m582xcc430470(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.titleTextView);
        this.titleTextView = textView;
        textView.setText(getString(R.string.battery_params));
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
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        this.webView.setWebChromeClient(new WebChromeClient());
        settings.setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.nfcx.eg4.view.overview.inverter.InverterBatteryParamsActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
                InverterBatteryParamsActivity.this.inverterSpinner.setEnabled(false);
                webView2.setVisibility(0);
                InverterBatteryParamsActivity.this.pageFinished = true;
                InverterBatteryParamsActivity.this.webView.loadUrl("javascript:getBatteryInfo(" + InverterBatteryParamsActivity.this.jsonFromRemote + ")");
            }
        });
        this.webView.loadUrl("file:///android_asset/module/batteryDetail/batteryDetail_index.html");
        this.webView.addJavascriptInterface(new WebAppInterfaceBattery(this), "WebAppInterfaceBattery");
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-overview-inverter-InverterBatteryParamsActivity, reason: not valid java name */
    /* synthetic */ void m582xcc430470(View view) {
        finish();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        refreshFragmentParams();
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (userData.getCurrentInverter() == null || this.inverterList == null) {
            return;
        }
        for (int i = 0; i < this.inverterList.size(); i++) {
            if (this.inverterList.get(i).getSerialNum().equals(userData.getCurrentInverter().getSerialNum()) && (((inverter = this.inverter) == null || !inverter.getSerialNum().equals(userData.getCurrentInverter().getSerialNum())) && this.inverterSpinner.getSelectedItemPosition() != i)) {
                this.inverterSpinner.setSelection(i);
            }
        }
    }

    private void getBatteryInfo(final String str) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.overview.inverter.InverterBatteryParamsActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m581x30076c9c(str);
            }
        }).start();
    }

    /* renamed from: lambda$getBatteryInfo$2$com-nfcx-eg4-view-overview-inverter-InverterBatteryParamsActivity, reason: not valid java name */
    /* synthetic */ void m581x30076c9c(String str) {
        HashMap map = new HashMap();
        map.put("serialNum", str);
        try {
            JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/battery/getBatteryInfo", map);
            this.jsonFromRemote = jSONObjectPostJson;
            if (!this.pageFinished || jSONObjectPostJson == null) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.overview.inverter.InverterBatteryParamsActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m580x5445f0db();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$getBatteryInfo$1$com-nfcx-eg4-view-overview-inverter-InverterBatteryParamsActivity, reason: not valid java name */
    /* synthetic */ void m580x5445f0db() {
        this.webView.loadUrl("javascript:getBatteryInfo(" + this.jsonFromRemote + ")");
    }

    private class WebAppInterfaceBattery {
        private InverterBatteryParamsActivity activity;

        WebAppInterfaceBattery(InverterBatteryParamsActivity inverterBatteryParamsActivity) {
            this.activity = inverterBatteryParamsActivity;
        }

        @JavascriptInterface
        public String getBatteryInfo() throws JSONException {
            if (InverterBatteryParamsActivity.this.jsonFromRemote != null) {
                return InverterBatteryParamsActivity.this.jsonFromRemote.toString();
            }
            return null;
        }
    }
}