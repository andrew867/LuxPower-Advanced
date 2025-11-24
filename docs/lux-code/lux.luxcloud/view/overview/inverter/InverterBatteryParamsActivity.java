package com.lux.luxcloud.view.overview.inverter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
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
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class InverterBatteryParamsActivity extends Activity {
    public static InverterBatteryParamsActivity instance;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private boolean isDarkTheme;
    private JSONObject jsonFromRemote;
    private boolean pageFinished;
    private int selectedItemPosition;
    private TextView titleTextView;
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_battery_params);
        instance = this;
        int i = 0;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        UserData userData = GlobalInfo.getInstance().getUserData();
        Intent intent = getIntent();
        if (intent != null) {
            this.selectedItemPosition = intent.getIntExtra("selectedItemPosition", 0);
        }
        Inverter currentInverter = userData.getCurrentInverter();
        this.inverter = currentInverter;
        if (currentInverter != null) {
            getBatteryInfo(currentInverter.getSerialNum());
        }
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.inverter.InverterBatteryParamsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m667x1275085e(view);
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
        if (userData.getCurrentPlant() != null && !userData.getCurrentPlant().getParallelGroups().isEmpty()) {
            Map<String, String> parallelGroups = userData.getCurrentPlant().getParallelGroups();
            for (String str : parallelGroups.keySet()) {
                Inverter inverter = new Inverter();
                inverter.setSerialNum(getString(R.string.phrase_parallel) + "-" + str);
                inverter.setPlantId(Long.valueOf(userData.getCurrentPlant().getPlantId()));
                inverter.setParallelGroup(str);
                inverter.setParallelFirstDeviceSn(parallelGroups.get(str));
                Inverter inverter2 = userData.getInverter(inverter.getParallelFirstDeviceSn());
                if (inverter2 != null) {
                    inverter.setDeviceType(Integer.valueOf(inverter2.getDeviceTypeValue()));
                    inverter.setSubDeviceType(Integer.valueOf(inverter2.getSubDeviceTypeValue()));
                    inverter.setDtc(inverter2.getDtcValue());
                    inverter.setPhase(inverter2.getPhaseValue());
                }
                this.inverterList.add(i, inverter);
                i++;
            }
        }
        this.inverterSpinner = (Spinner) findViewById(R.id.activity_battery_params_inverter_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.inverterSpinner.setSelection(this.selectedItemPosition);
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
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.overview.inverter.InverterBatteryParamsActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str2, Bitmap bitmap) {
                super.onPageStarted(webView2, str2, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str2) {
                super.onPageFinished(webView2, str2);
                InverterBatteryParamsActivity.this.inverterSpinner.setEnabled(false);
                webView2.setVisibility(0);
                InverterBatteryParamsActivity.this.pageFinished = true;
                InverterBatteryParamsActivity.this.webView.loadUrl("javascript:getBatteryInfo(" + InverterBatteryParamsActivity.this.jsonFromRemote + ")");
            }
        });
        this.webView.loadUrl("file:///android_asset/module/batteryDetail/batteryDetail_index.html");
        this.webView.addJavascriptInterface(new WebAppInterfaceBattery(this), "WebAppInterfaceBattery");
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-overview-inverter-InverterBatteryParamsActivity, reason: not valid java name */
    /* synthetic */ void m667x1275085e(View view) {
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
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.overview.inverter.InverterBatteryParamsActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m666xb0317a8a(str);
            }
        }).start();
    }

    /* renamed from: lambda$getBatteryInfo$2$com-lux-luxcloud-view-overview-inverter-InverterBatteryParamsActivity, reason: not valid java name */
    /* synthetic */ void m666xb0317a8a(String str) {
        HashMap map = new HashMap();
        map.put("serialNum", str);
        try {
            JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/battery/getBatteryInfo", map);
            this.jsonFromRemote = jSONObjectPostJson;
            if (!this.pageFinished || jSONObjectPostJson == null) {
                return;
            }
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.overview.inverter.InverterBatteryParamsActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m665xcd05c749();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$getBatteryInfo$1$com-lux-luxcloud-view-overview-inverter-InverterBatteryParamsActivity, reason: not valid java name */
    /* synthetic */ void m665xcd05c749() {
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