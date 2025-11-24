package com.nfcx.eg4.view.main.fragment.lv2;

import android.app.UiModeManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.param.BattChgPARAM;
import com.nfcx.eg4.global.bean.param.PARAM;
import com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE;
import com.nfcx.eg4.global.bean.set.RemoteReadInfo;
import com.nfcx.eg4.global.bean.set.RemoteWriteInfo;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.global.cache.RemoteSetCacheManager;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.InvTool;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.login.LoginActivity;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment;
import com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.text.Typography;
import org.bouncycastle.pqc.legacy.math.linearalgebra.Matrix;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Lv2RemoteSetFragment extends AbstractItemFragment {
    private boolean created;
    private String currentUrl;
    private Fragment fragment;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private boolean isDarkTheme;
    private Button readAllButton;
    private ProgressBar readProgressBar;
    private String titleText;
    private TextView titleTextView;
    private WebView webView;

    static /* synthetic */ boolean lambda$InitView$0(View view, MotionEvent motionEvent) {
        return true;
    }

    public Lv2RemoteSetFragment() {
        super(51L);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lv2_remote_set, viewGroup, false);
        this.fragment = this;
        this.isDarkTheme = ((UiModeManager) getActivity().getSystemService("uimode")).getNightMode() == 2;
        InitView(viewInflate);
        this.created = true;
        return viewInflate;
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

    private void InitView(final View view) {
        final UserData userData = GlobalInfo.getInstance().getUserData();
        this.titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        this.inverterSpinner = (Spinner) view.findViewById(R.id.new_remote_set_inverter_spinner);
        this.readAllButton = (Button) view.findViewById(R.id.new_remote_set_readAllButton);
        this.readProgressBar = (ProgressBar) view.findViewById(R.id.read_progress_bar);
        this.webView = (WebView) view.findViewById(R.id.webView);
        ((ConstraintLayout) view.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (Lv2RemoteSetFragment.this.webView.canGoBack()) {
                    Lv2RemoteSetFragment.this.webView.goBack();
                } else {
                    Lv2RemoteSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                    MainActivity.instance.finish();
                }
            }
        });
        ((ImageView) view.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Lv2RemoteSetFragment.this.startActivity(new Intent(view2.getContext(), (Class<?>) NewUserCenterActivity.class));
            }
        });
        WebSettings settings = this.webView.getSettings();
        settings.setCacheMode(2);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        this.webView.setWebChromeClient(new WebChromeClient());
        settings.setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.3
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                Lv2RemoteSetFragment.this.inverterSpinner.setClickable(false);
                Lv2RemoteSetFragment.this.inverterSpinner.setEnabled(false);
                Lv2RemoteSetFragment.this.readAllButton.setVisibility(4);
                Lv2RemoteSetFragment.this.titleTextView.setText(Lv2RemoteSetFragment.this.titleText);
                Lv2RemoteSetFragment.this.currentUrl = str;
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (str.equals("file:///android_asset/module/settingContent/userCenterDetail.html")) {
                    Lv2RemoteSetFragment.this.inverterSpinner.setClickable(true);
                    Lv2RemoteSetFragment.this.inverterSpinner.setEnabled(true);
                    Lv2RemoteSetFragment.this.readAllButton.setVisibility(0);
                } else {
                    Lv2RemoteSetFragment.this.inverterSpinner.setClickable(false);
                    Lv2RemoteSetFragment.this.inverterSpinner.setEnabled(false);
                    Lv2RemoteSetFragment.this.readAllButton.setVisibility(8);
                }
                webView.setVisibility(0);
                Lv2RemoteSetFragment.this.titleTextView.setText(Lv2RemoteSetFragment.this.titleText);
                webView.evaluateJavascript("changeEleColor('" + String.format("#%06X", Integer.valueOf(Lv2RemoteSetFragment.this.getActivity().getColor(R.color.themeColor) & ViewCompat.MEASURED_SIZE_MASK)) + "');", null);
            }
        });
        this.webView.loadUrl("file:///android_asset/module/settingContent/userCenterDetail.html");
        this.webView.addJavascriptInterface(new WebAppInterface(this), "WebAppInterface");
        this.webView.addJavascriptInterface(new WebAppSysInterface(this.isDarkTheme), "Android");
        if (userData.getCurrentPlant() != null) {
            this.inverterList = userData.getInvertersByPlant(userData.getCurrentPlant().getPlantId());
        }
        if (this.inverterList == null) {
            this.inverterList = new ArrayList();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.readAllButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m572xce472f0(userData, view2);
            }
        });
    }

    /* renamed from: lambda$InitView$1$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m572xce472f0(UserData userData, View view) {
        if (this.inverter != null) {
            int unused = ReadMultiParamTask.readCount = 0;
            JSONObject unused2 = ReadMultiParamTask.resultsList = new JSONObject();
            RemoteSetCacheManager.getInstance().clearCache(this.inverter.getSerialNum());
            this.readProgressBar.setVisibility(0);
            this.readAllButton.setEnabled(false);
            this.webView.setOnTouchListener(new View.OnTouchListener() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return Lv2RemoteSetFragment.lambda$InitView$0(view2, motionEvent);
                }
            });
            if (this.inverter.supportRead127Register()) {
                RemoteReadInfo remoteReadInfo = new RemoteReadInfo(this.inverter.getSerialNum(), 0, 127);
                RemoteReadInfo remoteReadInfo2 = new RemoteReadInfo(this.inverter.getSerialNum(), 127, 127);
                if (!this.inverter.isMidBox()) {
                    new ReadMultiParamTask(this).execute(remoteReadInfo, remoteReadInfo2);
                    return;
                } else {
                    new ReadMultiParamTask(this).execute(remoteReadInfo, remoteReadInfo2, new RemoteReadInfo(this.inverter.getSerialNum(), 254, 127), new RemoteReadInfo(this.inverter.getSerialNum(), 2032, 127));
                    return;
                }
            }
            RemoteReadInfo remoteReadInfo3 = new RemoteReadInfo(this.inverter.getSerialNum(), 0, 40);
            RemoteReadInfo remoteReadInfo4 = new RemoteReadInfo(this.inverter.getSerialNum(), 40, 40);
            RemoteReadInfo remoteReadInfo5 = new RemoteReadInfo(this.inverter.getSerialNum(), 80, 40);
            RemoteReadInfo remoteReadInfo6 = new RemoteReadInfo(this.inverter.getSerialNum(), 120, 40);
            RemoteReadInfo remoteReadInfo7 = new RemoteReadInfo(this.inverter.getSerialNum(), CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 40);
            RemoteReadInfo remoteReadInfo8 = new RemoteReadInfo(this.inverter.getSerialNum(), 200, 40);
            RemoteReadInfo remoteReadInfo9 = new RemoteReadInfo(this.inverter.getSerialNum(), 240, 40);
            RemoteReadInfo remoteReadInfo10 = new RemoteReadInfo(this.inverter.getSerialNum(), 600, 40);
            if (this.inverter.isAcCharger()) {
                new ReadMultiParamTask(this).execute(remoteReadInfo3, remoteReadInfo4, remoteReadInfo5, remoteReadInfo6, remoteReadInfo7, remoteReadInfo8);
                return;
            }
            boolean z = ROLE.ADMIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole()) || ROLE.P_ASSISTANT.equals(userData.getRole()) || "SolarVietD".equals(userData.getUsername());
            if (this.inverter.isSnaSeries() || this.inverter.isType6Series()) {
                new ReadMultiParamTask(this).execute(remoteReadInfo3, remoteReadInfo4, remoteReadInfo5, remoteReadInfo6, remoteReadInfo7, remoteReadInfo8, remoteReadInfo9);
            } else if (this.inverter.isSnaSeries() && z) {
                new ReadMultiParamTask(this).execute(remoteReadInfo3, remoteReadInfo4, remoteReadInfo5, remoteReadInfo6, remoteReadInfo7, remoteReadInfo8, remoteReadInfo9, remoteReadInfo10);
            } else {
                new ReadMultiParamTask(this).execute(remoteReadInfo3, remoteReadInfo4, remoteReadInfo5, remoteReadInfo6, remoteReadInfo7, remoteReadInfo8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMasterInverterForMidboxAsync(String str) {
        RemoteSetCacheManager.getInstance().clearCache(str);
        new ReadMultiParamTask(this).execute(new RemoteReadInfo(str, 0, 127), new RemoteReadInfo(str, 127, 127));
    }

    public JSONArray getParamInfos(List<PARAM> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (list != null && !list.isEmpty()) {
            for (PARAM param : list) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("device", this.inverter.getDeviceType());
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("holdParam", param.name());
                    jSONObject2.put("paramName", getString(param.getResourceId(this.inverter)));
                    jSONObject2.put("visible", !param.checkInvisible(userData));
                    jSONObject2.put("deviceType", this.inverter.getDeviceType());
                    jSONObject2.put("platformSpecificData", param.getPlatformSpecificData(userData));
                    jSONArray.put(jSONObject2);
                    jSONArray.put(jSONObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    public JSONArray getBattChgParamInfos(List<BattChgPARAM> list, String str) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (list != null && !list.isEmpty()) {
            for (BattChgPARAM battChgPARAM : list) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("holdParam", battChgPARAM.name());
                    jSONObject.put("paramName", getString(battChgPARAM.getResourceId(this.inverter)));
                    jSONObject.put("visible", !battChgPARAM.checkInvisible(userData));
                    jSONObject.put("enabled", battChgPARAM.checkEnabled(str));
                    jSONObject.put("deviceType", this.inverter.getDeviceType());
                    jSONArray.put(jSONObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ReadMultiParamTask extends AsyncTask<RemoteReadInfo, JSONObject, Void> {
        private Lv2RemoteSetFragment fragment;
        private boolean hasMasterInverterSn = false;
        private Set<String> keysToRetain;
        private static JSONObject resultsList = new JSONObject();
        private static int readCount = 0;

        static /* synthetic */ boolean lambda$onPostExecute$1(View view, MotionEvent motionEvent) {
            return true;
        }

        public ReadMultiParamTask(Lv2RemoteSetFragment lv2RemoteSetFragment) {
            this.fragment = lv2RemoteSetFragment;
            HashSet hashSet = new HashSet();
            this.keysToRetain = hashSet;
            hashSet.add("FUNC_BAT_CHARGE_CONTROL");
            this.keysToRetain.add("FUNC_BAT_DISCHARGE_CONTROL");
            this.keysToRetain.add("FUNC_SMART_LOAD_EN_1");
            this.keysToRetain.add("FUNC_SMART_LOAD_EN_2");
            this.keysToRetain.add("FUNC_SMART_LOAD_EN_3");
            this.keysToRetain.add("FUNC_SMART_LOAD_EN_4");
            this.keysToRetain.add("FUNC_AC_COUPLE_EN_1");
            this.keysToRetain.add("FUNC_AC_COUPLE_EN_2");
            this.keysToRetain.add("FUNC_AC_COUPLE_EN_3");
            this.keysToRetain.add("FUNC_AC_COUPLE_EN_4");
            this.keysToRetain.add("FUNC_SHEDDING_MODE_EN_1");
            this.keysToRetain.add("FUNC_SHEDDING_MODE_EN_2");
            this.keysToRetain.add("FUNC_SHEDDING_MODE_EN_3");
            this.keysToRetain.add("FUNC_SHEDDING_MODE_EN_4");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT1_LOW");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT2_LOW");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT3_LOW");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT1_LOW_TIME");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT2_LOW_TIME");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT3_LOW_TIME");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT1_HIGH");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT2_HIGH");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT3_HIGH");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT1_HIGH_TIME");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT2_HIGH_TIME");
            this.keysToRetain.add("HOLD_GRID_VOLT_LIMIT3_HIGH_TIME");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT1_LOW");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT2_LOW");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT3_LOW");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT1_LOW_TIME");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT2_LOW_TIME");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT3_LOW_TIME");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT1_HIGH");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT2_HIGH");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT3_HIGH");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT1_HIGH_TIME");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT2_HIGH_TIME");
            this.keysToRetain.add("HOLD_GRID_FREQ_LIMIT3_HIGH_TIME");
            this.keysToRetain.add("MIDBOX_HOLD_GEN_REMOTE_TURN_OFF_TIME");
            this.keysToRetain.add("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR");
            this.keysToRetain.add("MIDBOX_HOLD_GEN_WARN_UP_TIME");
            this.keysToRetain.add("HOLD_MAX_GENERATOR_INPUT_POWER");
            this.keysToRetain.add("OFF_GRID_HOLD_GEN_CHG_START_SOC");
            this.keysToRetain.add("OFF_GRID_HOLD_GEN_CHG_END_SOC");
            this.keysToRetain.add("OFF_GRID_HOLD_GEN_CHG_START_VOLT");
            this.keysToRetain.add("OFF_GRID_HOLD_GEN_CHG_END_VOLT");
            this.keysToRetain.add("inverterSn");
            this.keysToRetain.add("HOLD_FW_CODE");
            this.keysToRetain.add("HOLD_TIME");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(RemoteReadInfo... remoteReadInfoArr) throws JSONException {
            final JSONObject jSONObjectPostJson;
            readCount++;
            System.out.println("readCount == " + readCount);
            if (readCount > 2) {
                readCount = 0;
            }
            System.out.println("readCount2 == " + readCount);
            if (!this.fragment.inverter.isMidBox()) {
                resultsList = new JSONObject();
            }
            for (RemoteReadInfo remoteReadInfo : remoteReadInfoArr) {
                HashMap map = new HashMap();
                map.put("inverterSn", remoteReadInfo.getSerialNum());
                map.put("startRegister", String.valueOf(remoteReadInfo.getStartRegister()));
                map.put("pointNumber", String.valueOf(remoteReadInfo.getPointNumber()));
                map.put("autoRetry", String.valueOf(true));
                try {
                    System.out.println("inverterSn == " + remoteReadInfo.getSerialNum());
                    System.out.println("startRegister == " + remoteReadInfo.getStartRegister());
                    System.out.println("pointNumber == " + remoteReadInfo.getPointNumber());
                    jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteRead/read", map);
                    resultsList.put("deviceType", this.fragment.inverter.getDeviceType());
                    resultsList.put("fwVersion", this.fragment.inverter.getFwVersionValue());
                    resultsList.put("slaveVersion", this.fragment.inverter.getSlaveVersionValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jSONObjectPostJson != null && jSONObjectPostJson.getBoolean("success")) {
                    if (jSONObjectPostJson.has("masterInverterSn")) {
                        this.hasMasterInverterSn = true;
                    }
                    mergeResults(resultsList, jSONObjectPostJson, readCount);
                }
                FragmentActivity activity = this.fragment.getActivity();
                if (activity == null) {
                    return null;
                }
                activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$ReadMultiParamTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws JSONException {
                        this.f$0.m579x3503006b(jSONObjectPostJson);
                    }
                });
                return null;
            }
            return null;
        }

        /* renamed from: lambda$doInBackground$0$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment$ReadMultiParamTask, reason: not valid java name */
        /* synthetic */ void m579x3503006b(JSONObject jSONObject) throws JSONException {
            this.fragment.m577x6fee619b(jSONObject);
        }

        private void mergeResults(JSONObject jSONObject, JSONObject jSONObject2, int i) throws JSONException {
            try {
                if (this.fragment.inverter.isMidBox()) {
                    String[] strArr = {"inverterSn", "HOLD_FW_CODE", "HOLD_TIME"};
                    for (int i2 = 0; i2 < 3; i2++) {
                        String str = strArr[i2];
                        String str2 = str + "_MIDBOX";
                        String str3 = str + "_MASTER";
                        if (!jSONObject.has(str) || "N/A".equals(jSONObject.optString(str, "N/A"))) {
                            jSONObject.put(str, jSONObject2.optString(str, "N/A"));
                        }
                        if (i == 1) {
                            if (!jSONObject.has(str2) || "N/A".equals(jSONObject.optString(str2, "N/A"))) {
                                jSONObject.put(str2, jSONObject2.optString(str, "N/A"));
                            }
                        } else if (i == 2 && (!jSONObject.has(str3) || "N/A".equals(jSONObject.optString(str3, "N/A")))) {
                            jSONObject.put(str3, jSONObject2.optString(str, "N/A"));
                        }
                    }
                }
                compareAndMerge(jSONObject, jSONObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void compareAndMerge(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
            try {
                Iterator<String> itKeys = jSONObject2.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (this.fragment.inverter.isMidBox()) {
                        if (!jSONObject.has(next) || !this.keysToRetain.contains(next)) {
                            jSONObject.put(next, jSONObject2.get(next));
                        }
                    } else {
                        jSONObject.put(next, jSONObject2.get(next));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(JSONObject... jSONObjectArr) {
            super.onProgressUpdate((Object[]) jSONObjectArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r11) throws JSONException {
            super.onPostExecute((ReadMultiParamTask) r11);
            this.fragment.readAllButton.setEnabled(true);
            this.fragment.webView.setOnTouchListener(null);
            if (this.fragment.inverter != null) {
                RemoteSetCacheManager.getInstance().setCache(this.fragment.inverter.getSerialNum(), resultsList);
            }
            if (this.hasMasterInverterSn) {
                String strOptString = resultsList.optString("masterInverterSn", null);
                this.fragment.readAllButton.setEnabled(false);
                this.fragment.webView.setOnTouchListener(new View.OnTouchListener() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$ReadMultiParamTask$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        return Lv2RemoteSetFragment.ReadMultiParamTask.lambda$onPostExecute$1(view, motionEvent);
                    }
                });
                RemoteSetCacheManager.getInstance().setCache(this.fragment.inverter.getSerialNum(), resultsList);
                this.fragment.inverter.setMasterInverterSn(strOptString);
                this.fragment.getMasterInverterForMidboxAsync(strOptString);
            }
            try {
                String strOptString2 = resultsList.optString("inverterSn", "N/A");
                String strOptString3 = resultsList.optString("inverterSn_MIDBOX", "N/A");
                String strOptString4 = resultsList.optString("HOLD_FW_CODE", "N/A");
                String strOptString5 = resultsList.optString("HOLD_FW_CODE_MIDBOX", "N/A");
                String strOptString6 = resultsList.optString("HOLD_TIME", "N/A");
                String strOptString7 = resultsList.optString("HOLD_TIME_MIDBOX", "N/A");
                if (!"N/A".equals(strOptString3) && !Tool.isEmpty(strOptString3)) {
                    strOptString2 = strOptString3;
                }
                if (!"N/A".equals(strOptString5) && !Tool.isEmpty(strOptString5)) {
                    strOptString4 = strOptString5;
                }
                if (!"N/A".equals(strOptString7) && !Tool.isEmpty(strOptString7)) {
                    strOptString6 = strOptString7;
                }
                resultsList.put("inverterSn", strOptString2);
                resultsList.put("HOLD_FW_CODE", strOptString4);
                resultsList.put("HOLD_TIME", strOptString6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.fragment.readProgressBar.setVisibility(4);
            this.fragment.fillDataFromCache();
        }
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private Lv2RemoteSetFragment fragment;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(Lv2RemoteSetFragment lv2RemoteSetFragment) {
            this.fragment = lv2RemoteSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
            this.remoteWriteInfo.setEnabled(false);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(RemoteWriteInfo... remoteWriteInfoArr) throws JSONException, NumberFormatException {
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                publishProgress(new Void[0]);
                System.out.println("remoteWriteInfo.getSerialNum() == " + remoteWriteInfo.getSerialNum());
                System.out.println("remoteWriteInfo.getHoldParam() == " + remoteWriteInfo.getHoldParam());
                switch (AnonymousClass6.$SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()]) {
                    case 1:
                        if (Tool.isEmpty(remoteWriteInfo.getValueText())) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        return postWriteParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getHoldParam(), remoteWriteInfo.getValueText());
                    case 2:
                        String valueText = remoteWriteInfo.getValueText();
                        if (Tool.isEmpty(valueText)) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        return postWriteBitParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getBitParam(), valueText);
                    case 3:
                        String valueText2 = remoteWriteInfo.getValueText();
                        if (Tool.isEmpty(valueText2)) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        return postWriteBitModelParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getHoldParam(), valueText2);
                    case 4:
                        return postResetParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getResetParam());
                    case 5:
                        String hourText = remoteWriteInfo.getHourText();
                        String minuteText = remoteWriteInfo.getMinuteText();
                        if (Tool.isEmpty(hourText) || Tool.isEmpty(minuteText)) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        try {
                            int i = Integer.parseInt(hourText);
                            int i2 = Integer.parseInt(minuteText);
                            if (i < 0 || i > 23) {
                                return this.fragment.createOutRangeJSONObject("0", "23");
                            }
                            if (i2 < 0 || i2 > 59) {
                                return this.fragment.createOutRangeJSONObject("0", "59");
                            }
                            return postWriteTimeParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getTimeParam(), hourText, minuteText);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return this.fragment.createFailureJSONObject("INTEGER_FORMAT_ERROR");
                        }
                    case 6:
                        HashMap map = new HashMap();
                        map.put("inverterSn", remoteWriteInfo.getSerialNum());
                        map.put("functionParam", remoteWriteInfo.getFunctionParam());
                        map.put("enable", String.valueOf(remoteWriteInfo.isFunctionToggleButtonChecked()));
                        map.put("clientType", "APP");
                        map.put("remoteSetType", InvTool.STATUS_NORMAL);
                        try {
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put(remoteWriteInfo.getFunctionParam(), remoteWriteInfo.isFunctionToggleButtonChecked());
                                RemoteSetCacheManager.getInstance().setCache(this.fragment.inverter.getSerialNum(), jSONObject);
                                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/functionControl", map);
                            } catch (JSONException e2) {
                                throw new RuntimeException(e2);
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            break;
                        }
                }
            }
            return this.fragment.createFailureJSONObject("UNKNOWN_ERROR");
        }

        private JSONObject postWriteParam(String str, String str2, String str3) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("holdParam", str2);
            map.put("valueText", str3);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/write", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postResetParam(String str, String str2) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("resetParam", str2);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/reset", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postWriteBitParam(String str, String str2, String str3) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("bitParam", str2);
            map.put("value", str3);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/bitParamControl", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postWriteBitModelParam(String str, String str2, String str3) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("modelBitParam", str2);
            map.put("value", str3);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/bitModelParamControl", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postWriteTimeParam(String str, String str2, String str3, String str4) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("timeParam", str2);
            map.put("hour", str3);
            map.put("minute", str4);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/writeTime", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0172 A[Catch: all -> 0x01ad, Exception -> 0x01af, TryCatch #2 {Exception -> 0x01af, blocks: (B:4:0x0007, B:6:0x000f, B:24:0x0068, B:25:0x006d, B:26:0x007e, B:28:0x008e, B:29:0x0094, B:30:0x00c4, B:32:0x00d8, B:33:0x00dd, B:34:0x00de, B:35:0x00e3, B:37:0x00f5, B:38:0x00fe, B:40:0x0112, B:41:0x0117, B:42:0x0118, B:43:0x011d, B:44:0x012c, B:46:0x013f, B:47:0x0144, B:48:0x0145, B:49:0x014a, B:50:0x0159, B:52:0x016c, B:53:0x0171, B:54:0x0172, B:56:0x0176, B:58:0x0184, B:60:0x018c, B:63:0x019f, B:64:0x01a2), top: B:82:0x0007, outer: #7 }] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r8) {
            /*
                Method dump skipped, instructions count: 460
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE;

        static {
            int[] iArr = new int[REMOTE_WRITE_TYPE.values().length];
            $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE = iArr;
            try {
                iArr[REMOTE_WRITE_TYPE.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.BIT_PARAM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.BIT_MODEL_PARAM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.RESET.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.TIME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.CONTROL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject createOutRangeJSONObject(String str, String str2) throws JSONException {
        try {
            JSONObject jSONObjectCreateFailureJSONObject = createFailureJSONObject("OUT_RANGE_ERROR");
            if (jSONObjectCreateFailureJSONObject == null) {
                return null;
            }
            jSONObjectCreateFailureJSONObject.put("minValue", str);
            jSONObjectCreateFailureJSONObject.put("maxValue", str2);
            return jSONObjectCreateFailureJSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject createFailureJSONObject(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("success", false);
            jSONObject.put("msg", str);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initInverterSpinnerOnItemSelectedListener() {
        if (this.inverterSpinner.getOnItemSelectedListener() == null) {
            System.out.println("Eg4 - Lv2RemoteSetFragment initInverterSpinnerOnItemSelectedListener...");
            this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.4
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    Inverter inverter = (Inverter) Lv2RemoteSetFragment.this.inverterSpinner.getSelectedItem();
                    if (Lv2RemoteSetFragment.this.webView != null) {
                        int unused = ReadMultiParamTask.readCount = 0;
                        RemoteSetCacheManager.getInstance().clearCache(inverter.getSerialNum());
                    }
                    Lv2RemoteSetFragment.this.updateSelectInverter(inverter);
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                    if (Lv2RemoteSetFragment.this.inverter != null) {
                        Lv2RemoteSetFragment.this.inverter = null;
                        GlobalInfo.getInstance().getUserData().setCurrentInverter(Lv2RemoteSetFragment.this.inverter, true);
                        Lv2RemoteSetFragment.this.readAllButton.performClick();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getModelParam(String str) {
        str.hashCode();
        switch (str) {
            case "MODEL_BIT_LEAD_ACID_TYPE":
                return "HOLD_MODEL_leadAcidType";
            case "MODEL_BIT_METER_BRAND":
                return "HOLD_MODEL_meterBrand";
            case "MODEL_BIT_BATTERY_TYPE":
                return "HOLD_MODEL_batteryType";
            case "MODEL_BIT_MEASUREMENT":
                return "HOLD_MODEL_measurement";
            case "MODEL_BIT_LITHIUM_TYPE":
                return "HOLD_MODEL_lithiumType";
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public String getReadParams(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2091729313:
                if (str.equals("HOLD_AC_CHARGE_END_TIME")) {
                    c = 0;
                    break;
                }
                break;
            case -1910210817:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_1")) {
                    c = 1;
                    break;
                }
                break;
            case -1910210816:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_2")) {
                    c = 2;
                    break;
                }
                break;
            case -1855381647:
                if (str.equals("HOLD_MIDBOX_SL_4_START_TIME_1")) {
                    c = 3;
                    break;
                }
                break;
            case -1855381646:
                if (str.equals("HOLD_MIDBOX_SL_4_START_TIME_2")) {
                    c = 4;
                    break;
                }
                break;
            case -1855381645:
                if (str.equals("HOLD_MIDBOX_SL_4_START_TIME_3")) {
                    c = 5;
                    break;
                }
                break;
            case -1790606454:
                if (str.equals("HOLD_PEAK_SHAVING_END_TIME_1")) {
                    c = 6;
                    break;
                }
                break;
            case -1790606453:
                if (str.equals("HOLD_PEAK_SHAVING_END_TIME_2")) {
                    c = 7;
                    break;
                }
                break;
            case -1563549068:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME")) {
                    c = '\b';
                    break;
                }
                break;
            case -1551266129:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME")) {
                    c = '\t';
                    break;
                }
                break;
            case -1349823022:
                if (str.equals("HOLD_MIDBOX_SL_3_START_TIME_1")) {
                    c = '\n';
                    break;
                }
                break;
            case -1349823021:
                if (str.equals("HOLD_MIDBOX_SL_3_START_TIME_2")) {
                    c = 11;
                    break;
                }
                break;
            case -1349823020:
                if (str.equals("HOLD_MIDBOX_SL_3_START_TIME_3")) {
                    c = '\f';
                    break;
                }
                break;
            case -1274671800:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_1")) {
                    c = '\r';
                    break;
                }
                break;
            case -1274671799:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_2")) {
                    c = 14;
                    break;
                }
                break;
            case -902075313:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_1_END_TIME_1")) {
                    c = 15;
                    break;
                }
                break;
            case -902075312:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_1_END_TIME_2")) {
                    c = 16;
                    break;
                }
                break;
            case -902075311:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_1_END_TIME_3")) {
                    c = 17;
                    break;
                }
                break;
            case -844264397:
                if (str.equals("HOLD_MIDBOX_SL_2_START_TIME_1")) {
                    c = 18;
                    break;
                }
                break;
            case -844264396:
                if (str.equals("HOLD_MIDBOX_SL_2_START_TIME_2")) {
                    c = 19;
                    break;
                }
                break;
            case -844264395:
                if (str.equals("HOLD_MIDBOX_SL_2_START_TIME_3")) {
                    c = 20;
                    break;
                }
                break;
            case -783053080:
                if (str.equals("HOLD_AC_FIRST_START_TIME_1")) {
                    c = 21;
                    break;
                }
                break;
            case -783053079:
                if (str.equals("HOLD_AC_FIRST_START_TIME_2")) {
                    c = 22;
                    break;
                }
                break;
            case -772992594:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_2_END_TIME_1")) {
                    c = 23;
                    break;
                }
                break;
            case -772992593:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_2_END_TIME_2")) {
                    c = 24;
                    break;
                }
                break;
            case -772992592:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_2_END_TIME_3")) {
                    c = 25;
                    break;
                }
                break;
            case -643909875:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_3_END_TIME_1")) {
                    c = 26;
                    break;
                }
                break;
            case -643909874:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_3_END_TIME_2")) {
                    c = 27;
                    break;
                }
                break;
            case -643909873:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_3_END_TIME_3")) {
                    c = 28;
                    break;
                }
                break;
            case -514827156:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_4_END_TIME_1")) {
                    c = 29;
                    break;
                }
                break;
            case -514827155:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_4_END_TIME_2")) {
                    c = 30;
                    break;
                }
                break;
            case -514827154:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_4_END_TIME_3")) {
                    c = 31;
                    break;
                }
                break;
            case -413095263:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_1")) {
                    c = ' ';
                    break;
                }
                break;
            case -413095262:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_2")) {
                    c = '!';
                    break;
                }
                break;
            case -383766600:
                if (str.equals("HOLD_AC_CHARGE_START_TIME")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case -346832699:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_4_START_TIME_1")) {
                    c = '#';
                    break;
                }
                break;
            case -346832698:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_4_START_TIME_2")) {
                    c = Typography.dollar;
                    break;
                }
                break;
            case -346832697:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_4_START_TIME_3")) {
                    c = '%';
                    break;
                }
                break;
            case -338705772:
                if (str.equals("HOLD_MIDBOX_SL_1_START_TIME_1")) {
                    c = Typography.amp;
                    break;
                }
                break;
            case -338705771:
                if (str.equals("HOLD_MIDBOX_SL_1_START_TIME_2")) {
                    c = '\'';
                    break;
                }
                break;
            case -338705770:
                if (str.equals("HOLD_MIDBOX_SL_1_START_TIME_3")) {
                    c = '(';
                    break;
                }
                break;
            case -107172271:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_1")) {
                    c = ')';
                    break;
                }
                break;
            case -107172270:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_2")) {
                    c = '*';
                    break;
                }
                break;
            case 128282390:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME")) {
                    c = '+';
                    break;
                }
                break;
            case 158725926:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_3_START_TIME_1")) {
                    c = ',';
                    break;
                }
                break;
            case 158725927:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_3_START_TIME_2")) {
                    c = '-';
                    break;
                }
                break;
            case 158725928:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_3_START_TIME_3")) {
                    c = '.';
                    break;
                }
                break;
            case 326077381:
                if (str.equals("HOLD_OFF_GRID_START_TIME_1")) {
                    c = '/';
                    break;
                }
                break;
            case 326077382:
                if (str.equals("HOLD_OFF_GRID_START_TIME_2")) {
                    c = '0';
                    break;
                }
                break;
            case 326077383:
                if (str.equals("HOLD_OFF_GRID_START_TIME_3")) {
                    c = '1';
                    break;
                }
                break;
            case 391307917:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME")) {
                    c = '2';
                    break;
                }
                break;
            case 531028150:
                if (str.equals("HOLD_AC_FIRST_START_TIME")) {
                    c = '3';
                    break;
                }
                break;
            case 567487850:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_1")) {
                    c = '4';
                    break;
                }
                break;
            case 567487851:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_2")) {
                    c = '5';
                    break;
                }
                break;
            case 664284551:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_2_START_TIME_1")) {
                    c = '6';
                    break;
                }
                break;
            case 664284552:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_2_START_TIME_2")) {
                    c = '7';
                    break;
                }
                break;
            case 664284553:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_2_START_TIME_3")) {
                    c = '8';
                    break;
                }
                break;
            case 667902246:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_1")) {
                    c = '9';
                    break;
                }
                break;
            case 667902247:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_2")) {
                    c = ':';
                    break;
                }
                break;
            case 807622479:
                if (str.equals("HOLD_AC_FIRST_END_TIME_1")) {
                    c = ';';
                    break;
                }
                break;
            case 807622480:
                if (str.equals("HOLD_AC_FIRST_END_TIME_2")) {
                    c = Typography.less;
                    break;
                }
                break;
            case 885755613:
                if (str.equals("HOLD_AC_FIRST_END_TIME")) {
                    c = '=';
                    break;
                }
                break;
            case 1169843176:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_1_START_TIME_1")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case 1169843177:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_1_START_TIME_2")) {
                    c = '?';
                    break;
                }
                break;
            case 1169843178:
                if (str.equals("HOLD_MIDBOX_AC_COUPLE_1_START_TIME_3")) {
                    c = '@';
                    break;
                }
                break;
            case 1496352251:
                if (str.equals("HOLD_MIDBOX_SL_1_END_TIME_1")) {
                    c = 'A';
                    break;
                }
                break;
            case 1496352252:
                if (str.equals("HOLD_MIDBOX_SL_1_END_TIME_2")) {
                    c = 'B';
                    break;
                }
                break;
            case 1496352253:
                if (str.equals("HOLD_MIDBOX_SL_1_END_TIME_3")) {
                    c = 'C';
                    break;
                }
                break;
            case 1625434970:
                if (str.equals("HOLD_MIDBOX_SL_2_END_TIME_1")) {
                    c = 'D';
                    break;
                }
                break;
            case 1625434971:
                if (str.equals("HOLD_MIDBOX_SL_2_END_TIME_2")) {
                    c = 'E';
                    break;
                }
                break;
            case 1625434972:
                if (str.equals("HOLD_MIDBOX_SL_2_END_TIME_3")) {
                    c = 'F';
                    break;
                }
                break;
            case 1675814764:
                if (str.equals("HOLD_OFF_GRID_END_TIME_1")) {
                    c = 'G';
                    break;
                }
                break;
            case 1675814765:
                if (str.equals("HOLD_OFF_GRID_END_TIME_2")) {
                    c = 'H';
                    break;
                }
                break;
            case 1675814766:
                if (str.equals("HOLD_OFF_GRID_END_TIME_3")) {
                    c = 'I';
                    break;
                }
                break;
            case 1754517689:
                if (str.equals("HOLD_MIDBOX_SL_3_END_TIME_1")) {
                    c = 'J';
                    break;
                }
                break;
            case 1754517690:
                if (str.equals("HOLD_MIDBOX_SL_3_END_TIME_2")) {
                    c = 'K';
                    break;
                }
                break;
            case 1754517691:
                if (str.equals("HOLD_MIDBOX_SL_3_END_TIME_3")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_LT;
                    break;
                }
                break;
            case 1883600408:
                if (str.equals("HOLD_MIDBOX_SL_4_END_TIME_1")) {
                    c = 'M';
                    break;
                }
                break;
            case 1883600409:
                if (str.equals("HOLD_MIDBOX_SL_4_END_TIME_2")) {
                    c = 'N';
                    break;
                }
                break;
            case 1883600410:
                if (str.equals("HOLD_MIDBOX_SL_4_END_TIME_3")) {
                    c = 'O';
                    break;
                }
                break;
            case 1989908579:
                if (str.equals("HOLD_PEAK_SHAVING_START_TIME_1")) {
                    c = 'P';
                    break;
                }
                break;
            case 1989908580:
                if (str.equals("HOLD_PEAK_SHAVING_START_TIME_2")) {
                    c = 'Q';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return "HOLD_AC_CHARGE_END_HOUR,HOLD_AC_CHARGE_END_MINUTE";
            case 1:
                return "HOLD_FORCED_DISCHARGE_START_HOUR_1,HOLD_FORCED_DISCHARGE_START_MINUTE_1";
            case 2:
                return "HOLD_FORCED_DISCHARGE_START_HOUR_2,HOLD_FORCED_DISCHARGE_START_MINUTE_2";
            case 3:
                return "HOLD_MIDBOX_SL_4_START_HOUR_1,HOLD_MIDBOX_SL_4_START_MINUTE_1";
            case 4:
                return "HOLD_MIDBOX_SL_4_START_HOUR_2,HOLD_MIDBOX_SL_4_START_MINUTE_2";
            case 5:
                return "HOLD_MIDBOX_SL_4_START_HOUR_3,HOLD_MIDBOX_SL_4_START_MINUTE_3";
            case 6:
                return "LSP_HOLD_DIS_CHG_POWER_TIME_39,LSP_HOLD_DIS_CHG_POWER_TIME_40";
            case 7:
                return "LSP_HOLD_DIS_CHG_POWER_TIME_43,LSP_HOLD_DIS_CHG_POWER_TIME_44";
            case '\b':
                return "HOLD_FORCED_DISCHARGE_END_HOUR,HOLD_FORCED_DISCHARGE_END_MINUTE";
            case '\t':
                return "HOLD_FORCED_CHARGE_START_HOUR,HOLD_FORCED_CHARGE_START_MINUTE";
            case '\n':
                return "HOLD_MIDBOX_SL_3_START_HOUR_1,HOLD_MIDBOX_SL_3_START_MINUTE_1";
            case 11:
                return "HOLD_MIDBOX_SL_3_START_HOUR_2,HOLD_MIDBOX_SL_3_START_MINUTE_2";
            case '\f':
                return "HOLD_MIDBOX_SL_3_START_HOUR_3,HOLD_MIDBOX_SL_3_START_MINUTE_3";
            case '\r':
                return "HOLD_FORCED_CHARGE_END_HOUR_1,HOLD_FORCED_CHARGE_END_MINUTE_1";
            case 14:
                return "HOLD_FORCED_CHARGE_END_HOUR_2,HOLD_FORCED_CHARGE_END_MINUTE_2";
            case 15:
                return "HOLD_MIDBOX_AC_COUPLE_1_END_HOUR_1,HOLD_MIDBOX_AC_COUPLE_1_END_MINUTE_1";
            case 16:
                return "HOLD_MIDBOX_AC_COUPLE_1_END_HOUR_2,HOLD_MIDBOX_AC_COUPLE_1_END_MINUTE_2";
            case 17:
                return "HOLD_MIDBOX_AC_COUPLE_1_END_HOUR_3,HOLD_MIDBOX_AC_COUPLE_1_END_MINUTE_3";
            case 18:
                return "HOLD_MIDBOX_SL_2_START_HOUR_1,HOLD_MIDBOX_SL_2_START_MINUTE_1";
            case 19:
                return "HOLD_MIDBOX_SL_2_START_HOUR_2,HOLD_MIDBOX_SL_2_START_MINUTE_2";
            case 20:
                return "HOLD_MIDBOX_SL_2_START_HOUR_3,HOLD_MIDBOX_SL_2_START_MINUTE_3";
            case 21:
                return "HOLD_AC_FIRST_START_HOUR_1,HOLD_AC_FIRST_START_MINUTE_1";
            case 22:
                return "HOLD_AC_FIRST_START_HOUR_2,HOLD_AC_FIRST_START_MINUTE_2";
            case 23:
                return "HOLD_MIDBOX_AC_COUPLE_2_END_HOUR_1,HOLD_MIDBOX_AC_COUPLE_2_END_MINUTE_1";
            case 24:
                return "HOLD_MIDBOX_AC_COUPLE_2_END_HOUR_2,HOLD_MIDBOX_AC_COUPLE_2_END_MINUTE_2";
            case 25:
                return "HOLD_MIDBOX_AC_COUPLE_2_END_HOUR_3,HOLD_MIDBOX_AC_COUPLE_2_END_MINUTE_3";
            case 26:
                return "HOLD_MIDBOX_AC_COUPLE_3_END_HOUR_1,HOLD_MIDBOX_AC_COUPLE_3_END_MINUTE_1";
            case 27:
                return "HOLD_MIDBOX_AC_COUPLE_3_END_HOUR_2,HOLD_MIDBOX_AC_COUPLE_3_END_MINUTE_2";
            case 28:
                return "HOLD_MIDBOX_AC_COUPLE_3_END_HOUR_3,HOLD_MIDBOX_AC_COUPLE_3_END_MINUTE_3";
            case 29:
                return "HOLD_MIDBOX_AC_COUPLE_4_END_HOUR_1,HOLD_MIDBOX_AC_COUPLE_4_END_MINUTE_1";
            case 30:
                return "HOLD_MIDBOX_AC_COUPLE_4_END_HOUR_2,HOLD_MIDBOX_AC_COUPLE_4_END_MINUTE_2";
            case 31:
                return "HOLD_MIDBOX_AC_COUPLE_4_END_HOUR_3,HOLD_MIDBOX_AC_COUPLE_4_END_MINUTE_3";
            case ' ':
                return "HOLD_FORCED_CHARGE_START_HOUR_1,HOLD_FORCED_CHARGE_START_MINUTE_1";
            case '!':
                return "HOLD_FORCED_CHARGE_START_HOUR_2,HOLD_FORCED_CHARGE_START_MINUTE_2";
            case '\"':
                return "HOLD_AC_CHARGE_START_HOUR,HOLD_AC_CHARGE_START_MINUTE";
            case '#':
                return "HOLD_MIDBOX_AC_COUPLE_4_START_HOUR_1,HOLD_MIDBOX_AC_COUPLE_4_START_MINUTE_1";
            case '$':
                return "HOLD_MIDBOX_AC_COUPLE_4_START_HOUR_2,HOLD_MIDBOX_AC_COUPLE_4_START_MINUTE_2";
            case '%':
                return "HOLD_MIDBOX_AC_COUPLE_4_START_HOUR_3,HOLD_MIDBOX_AC_COUPLE_4_START_MINUTE_3";
            case '&':
                return "HOLD_MIDBOX_SL_1_START_HOUR_1,HOLD_MIDBOX_SL_1_START_MINUTE_1";
            case '\'':
                return "HOLD_MIDBOX_SL_1_START_HOUR_2,HOLD_MIDBOX_SL_1_START_MINUTE_2";
            case '(':
                return "HOLD_MIDBOX_SL_1_START_HOUR_3,HOLD_MIDBOX_SL_1_START_MINUTE_3";
            case ')':
                return "HOLD_AC_CHARGE_END_HOUR_1,HOLD_AC_CHARGE_END_MINUTE_1";
            case '*':
                return "HOLD_AC_CHARGE_END_HOUR_2,HOLD_AC_CHARGE_END_MINUTE_2";
            case '+':
                return "HOLD_FORCED_CHARGE_END_HOUR,HOLD_FORCED_CHARGE_END_MINUTE";
            case ',':
                return "HOLD_MIDBOX_AC_COUPLE_3_START_HOUR_1,HOLD_MIDBOX_AC_COUPLE_3_START_MINUTE_1";
            case '-':
                return "HOLD_MIDBOX_AC_COUPLE_3_START_HOUR_2,HOLD_MIDBOX_AC_COUPLE_3_START_MINUTE_2";
            case '.':
                return "HOLD_MIDBOX_AC_COUPLE_3_START_HOUR_3,HOLD_MIDBOX_AC_COUPLE_3_START_MINUTE_3";
            case '/':
                return "HOLD_OFF_GRID_START_HOUR_1,HOLD_OFF_GRID_START_MINUTE_1";
            case '0':
                return "HOLD_OFF_GRID_START_HOUR_1,HOLD_OFF_GRID_START_MINUTE_2";
            case '1':
                return "HOLD_OFF_GRID_START_HOUR_3,HOLD_OFF_GRID_START_MINUTE_3";
            case '2':
                return "HOLD_FORCED_DISCHARGE_START_HOUR,HOLD_FORCED_DISCHARGE_START_MINUTE";
            case '3':
                return "HOLD_AC_FIRST_START_HOUR,HOLD_AC_FIRST_START_MINUTE";
            case '4':
                return "HOLD_AC_CHARGE_START_HOUR_1,HOLD_AC_CHARGE_START_MINUTE_1";
            case '5':
                return "HOLD_AC_CHARGE_START_HOUR_2,HOLD_AC_CHARGE_START_MINUTE_2";
            case '6':
                return "HOLD_MIDBOX_AC_COUPLE_2_START_HOUR_1,HOLD_MIDBOX_AC_COUPLE_2_START_MINUTE_1";
            case '7':
                return "HOLD_MIDBOX_AC_COUPLE_2_START_HOUR_2,HOLD_MIDBOX_AC_COUPLE_2_START_MINUTE_2";
            case '8':
                return "HOLD_MIDBOX_AC_COUPLE_2_START_HOUR_3,HOLD_MIDBOX_AC_COUPLE_2_START_MINUTE_3";
            case '9':
                return "HOLD_FORCED_DISCHARGE_END_HOUR_1,HOLD_FORCED_DISCHARGE_END_MINUTE_1";
            case ':':
                return "HOLD_FORCED_DISCHARGE_END_HOUR_2,HOLD_FORCED_DISCHARGE_END_MINUTE_2";
            case ';':
                return "HOLD_AC_FIRST_START_END_HOUR_1,HOLD_AC_FIRST_START_END_MINUTE_1";
            case '<':
                return "HOLD_AC_FIRST_START_END_HOUR_2,HOLD_AC_FIRST_START_END_MINUTE_2";
            case '=':
                return "HOLD_AC_FIRST_START_END_HOUR,HOLD_AC_FIRST_START_END_MINUTE";
            case '>':
                return "HOLD_MIDBOX_AC_COUPLE_1_START_HOUR_1,HOLD_MIDBOX_AC_COUPLE_1_START_MINUTE_1";
            case '?':
                return "HOLD_MIDBOX_AC_COUPLE_1_START_HOUR_2,HOLD_MIDBOX_AC_COUPLE_1_START_MINUTE_2";
            case '@':
                return "HOLD_MIDBOX_AC_COUPLE_1_START_HOUR_3,HOLD_MIDBOX_AC_COUPLE_1_START_MINUTE_3";
            case 'A':
                return "HOLD_MIDBOX_SL_1_END_HOUR_1,HOLD_MIDBOX_SL_1_END_MINUTE_1";
            case 'B':
                return "HOLD_MIDBOX_SL_1_END_HOUR_2,HOLD_MIDBOX_SL_1_END_MINUTE_2";
            case 'C':
                return "HOLD_MIDBOX_SL_1_END_HOUR_3,HOLD_MIDBOX_SL_1_END_MINUTE_3";
            case 'D':
                return "HOLD_MIDBOX_SL_2_END_HOUR_1,HOLD_MIDBOX_SL_2_END_MINUTE_1";
            case 'E':
                return "HOLD_MIDBOX_SL_2_END_HOUR_2,HOLD_MIDBOX_SL_2_END_MINUTE_2";
            case 'F':
                return "HOLD_MIDBOX_SL_2_END_HOUR_3,HOLD_MIDBOX_SL_2_END_MINUTE_3";
            case 'G':
                return "HOLD_OFF_GRID_END_HOUR_1,HOLD_OFF_GRID_END_MINUTE_1";
            case 'H':
                return "HOLD_OFF_GRID_END_HOUR_2,HOLD_OFF_GRID_END_MINUTE_2";
            case 'I':
                return "HOLD_OFF_GRID_END_HOUR_3,HOLD_OFF_GRID_END_MINUTE_3";
            case 'J':
                return "HOLD_MIDBOX_SL_3_END_HOUR_1,HOLD_MIDBOX_SL_3_END_MINUTE_1";
            case 'K':
                return "HOLD_MIDBOX_SL_3_END_HOUR_2,HOLD_MIDBOX_SL_3_END_MINUTE_2";
            case 'L':
                return "HOLD_MIDBOX_SL_3_END_HOUR_3,HOLD_MIDBOX_SL_3_END_MINUTE_3";
            case 'M':
                return "HOLD_MIDBOX_SL_4_END_HOUR_1,HOLD_MIDBOX_SL_4_END_MINUTE_1";
            case 'N':
                return "HOLD_MIDBOX_SL_4_END_HOUR_2,HOLD_MIDBOX_SL_4_END_MINUTE_2";
            case 'O':
                return "HOLD_MIDBOX_SL_4_END_HOUR_3,HOLD_MIDBOX_SL_4_END_MINUTE_3";
            case 'P':
                return "LSP_HOLD_DIS_CHG_POWER_TIME_37,LSP_HOLD_DIS_CHG_POWER_TIME_38";
            case 'Q':
                return "LSP_HOLD_DIS_CHG_POWER_TIME_41,LSP_HOLD_DIS_CHG_POWER_TIME_42";
            default:
                return null;
        }
    }

    public void fillDataFromCache() {
        if (this.created && GlobalInfo.getInstance().getUserData().getCurrentInverter() != null) {
            JSONObject cache = RemoteSetCacheManager.getInstance().getCache(this.inverter.getSerialNum());
            if (cache != null) {
                WebView webView = this.webView;
                if (webView != null) {
                    webView.loadUrl("javascript:getData(" + cache + ")");
                    return;
                }
                return;
            }
            WebView webView2 = this.webView;
            if (webView2 != null) {
                webView2.loadUrl("javascript:getData()");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectInverter(Inverter inverter) {
        System.out.println("Eg4 - Lv2RemoteSetFragment selectInverter = " + inverter.getSerialNum() + ", inverter = " + this.inverter);
        Inverter inverter2 = this.inverter;
        if (inverter2 == null || !inverter2.getSerialNum().equals(inverter.getSerialNum())) {
            this.inverter = inverter;
            this.readAllButton.performClick();
            GlobalInfo.getInstance().getUserData().setCurrentInverter(this.inverter, true);
        }
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        if (this.created) {
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.getCurrentInverter() == null || this.inverterList == null) {
                return;
            }
            for (int i = 0; i < this.inverterList.size(); i++) {
                Inverter inverter2 = this.inverterList.get(i);
                if (inverter2.getSerialNum().equals(userData.getCurrentInverter().getSerialNum()) && ((inverter = this.inverter) == null || !inverter.getSerialNum().equals(userData.getCurrentInverter().getSerialNum()))) {
                    if (this.inverterSpinner.getSelectedItemPosition() != i) {
                        this.inverterSpinner.setSelection(i);
                    } else {
                        updateSelectInverter(inverter2);
                    }
                }
            }
        }
    }

    private class WebAppSysInterface {
        private boolean isDarkTheme;

        WebAppSysInterface(boolean z) {
            this.isDarkTheme = z;
        }

        @JavascriptInterface
        public boolean getDarkTheme() {
            return this.isDarkTheme;
        }

        @JavascriptInterface
        public boolean checkHasCustomPlatform() {
            UserData userData = GlobalInfo.getInstance().getUserData();
            return PLATFORM.SUNBEAT.equals(userData.getPlatform()) || PLATFORM.FORTRESS.equals(userData.getPlatform()) || PLATFORM.BOER.equals(userData.getPlatform()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.INCCO.equals(userData.getPlatform());
        }

        @JavascriptInterface
        public boolean isEG4() {
            return PLATFORM.EG4.name().equals("EG4");
        }

        @JavascriptInterface
        public String getCustomPlatform() {
            return GlobalInfo.getInstance().getUserData().getPlatform().name();
        }

        @JavascriptInterface
        public void logout() {
            Lv2RemoteSetFragment.this.startActivity(new Intent(Lv2RemoteSetFragment.this.fragment.getActivity(), (Class<?>) LoginActivity.class));
            if (Lv2RemoteSetFragment.this.fragment == null || Lv2RemoteSetFragment.this.fragment.getActivity() == null) {
                return;
            }
            FragmentTransaction fragmentTransactionBeginTransaction = Lv2RemoteSetFragment.this.fragment.getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.remove(Lv2RemoteSetFragment.this.fragment);
            fragmentTransactionBeginTransaction.commit();
        }
    }

    private class WebAppInterface {
        private Lv2RemoteSetFragment fragment;

        WebAppInterface(Lv2RemoteSetFragment lv2RemoteSetFragment) {
            this.fragment = lv2RemoteSetFragment;
        }

        @JavascriptInterface
        public String getData() throws JSONException {
            JSONObject cache = RemoteSetCacheManager.getInstance().getCache(Lv2RemoteSetFragment.this.inverter.getSerialNum());
            if (cache == null) {
                cache = new JSONObject();
            }
            return cache.toString();
        }

        @JavascriptInterface
        public String getLocalLanguageResources() throws IllegalAccessException, JSONException, SecurityException, IllegalArgumentException {
            JSONObject jSONObject = new JSONObject();
            Resources resources = this.fragment.getResources();
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
        public void adJustTitle(String str) {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                this.fragment.titleText = str;
            }
        }

        @JavascriptInterface
        public String getParamVisible(String str, String str2) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter == null) {
                return null;
            }
            List<String> list = (List) new Gson().fromJson(str, new TypeToken<List<String>>() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.WebAppInterface.1
            }.getType());
            if (!Tool.isEmpty(str2)) {
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(BattChgPARAM.valueOf((String) it.next()));
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("paramInfos", this.fragment.getBattChgParamInfos(arrayList, str2));
                return jSONObject.toString();
            }
            ArrayList arrayList2 = new ArrayList();
            for (String str3 : list) {
                if ("file:///android_asset/module/settingContent/acChargeSetting.html".equals(Lv2RemoteSetFragment.this.currentUrl)) {
                    if ("HOLD_AC_CHARGE_SOC_LIMIT".equals(str3)) {
                        arrayList2.add(PARAM.valueOf("HOLD_BACK_UP_SOC_LIMIT"));
                    } else if ("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE".equals(str3)) {
                        arrayList2.add(PARAM.valueOf("HOLD_BACK_UP_END_BATTERY_VOLTAGE"));
                    } else {
                        arrayList2.add(PARAM.valueOf(str3));
                    }
                } else {
                    arrayList2.add(PARAM.valueOf(str3));
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            JSONArray paramInfos = this.fragment.getParamInfos(arrayList2);
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
        public String wattNodeRead() {
            String strFetchWattNodeResult = Lv2RemoteSetFragment.this.inverter != null ? Lv2RemoteSetFragment.this.fetchWattNodeResult() : null;
            return Tool.isEmpty(strFetchWattNodeResult) ? "" : strFetchWattNodeResult;
        }

        @JavascriptInterface
        public void wattNodeWrite(String str) {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                Lv2RemoteSetFragment.this.WriteWattNode(Tool.jsonStringToMap(str));
            }
        }

        @JavascriptInterface
        public void runNormalRemoteWrite(String str, String str2) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
                remoteWriteInfo.setSerialNum(Lv2RemoteSetFragment.this.inverter.getSerialNum());
                remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.NORMAL);
                remoteWriteInfo.setHoldParam(str);
                remoteWriteInfo.setValueText(str2);
                new WriteParamTask(Lv2RemoteSetFragment.this).execute(Lv2RemoteSetFragment.this.adjustMidBoxMasterInverter(remoteWriteInfo, str));
            }
        }

        @JavascriptInterface
        public void runResetRemoteWrite(String str) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
                remoteWriteInfo.setSerialNum(Lv2RemoteSetFragment.this.inverter.getSerialNum());
                remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.RESET);
                remoteWriteInfo.setResetParam(str);
                new WriteParamTask(Lv2RemoteSetFragment.this).execute(Lv2RemoteSetFragment.this.adjustMidBoxMasterInverter(remoteWriteInfo, str));
            }
        }

        @JavascriptInterface
        public void runBitRemoteWrite(String str, String str2) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
                remoteWriteInfo.setSerialNum(Lv2RemoteSetFragment.this.inverter.getSerialNum());
                remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.BIT_PARAM);
                remoteWriteInfo.setBitParam(str);
                remoteWriteInfo.setValueText(str2);
                new WriteParamTask(Lv2RemoteSetFragment.this).execute(Lv2RemoteSetFragment.this.adjustMidBoxMasterInverter(remoteWriteInfo, str));
            }
        }

        @JavascriptInterface
        public void runBitModelRemoteWrite(String str, String str2) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
                remoteWriteInfo.setSerialNum(Lv2RemoteSetFragment.this.inverter.getSerialNum());
                remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.BIT_MODEL_PARAM);
                remoteWriteInfo.setHoldParam(str);
                remoteWriteInfo.setValueText(str2);
                new WriteParamTask(Lv2RemoteSetFragment.this).execute(Lv2RemoteSetFragment.this.adjustMidBoxMasterInverter(remoteWriteInfo, str));
            }
        }

        @JavascriptInterface
        public void runTimeRemoteWrite(String str, String str2, String str3) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
                remoteWriteInfo.setSerialNum(Lv2RemoteSetFragment.this.inverter.getSerialNum());
                remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.TIME);
                remoteWriteInfo.setTimeParam(str);
                remoteWriteInfo.setHourText(str2.trim());
                remoteWriteInfo.setMinuteText(str3.trim());
                new WriteParamTask(Lv2RemoteSetFragment.this).execute(Lv2RemoteSetFragment.this.adjustMidBoxMasterInverter(remoteWriteInfo, str));
            }
        }

        @JavascriptInterface
        public void runControlRemoteWrite(String str, boolean z) throws JSONException {
            if (Lv2RemoteSetFragment.this.inverter != null) {
                RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
                remoteWriteInfo.setSerialNum(Lv2RemoteSetFragment.this.inverter.getSerialNum());
                remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
                remoteWriteInfo.setFunctionParam(str);
                remoteWriteInfo.setFunctionToggleButtonChecked(z);
                new WriteParamTask(Lv2RemoteSetFragment.this).execute(Lv2RemoteSetFragment.this.adjustMidBoxMasterInverter(remoteWriteInfo, str));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RemoteWriteInfo adjustMidBoxMasterInverter(RemoteWriteInfo remoteWriteInfo, String str) throws JSONException {
        String string;
        Set<String> setLoadValidParams = loadValidParams();
        JSONObject cache = RemoteSetCacheManager.getInstance().getCache(this.inverter.getSerialNum());
        if (cache.has("masterInverterSn")) {
            try {
                string = cache.getString("masterInverterSn");
            } catch (Exception unused) {
                return remoteWriteInfo;
            }
        } else {
            string = null;
        }
        if (setLoadValidParams.contains(str) && this.inverter.isMidBox() && !Tool.isEmpty(string)) {
            remoteWriteInfo.setSerialNum(string);
        }
        return remoteWriteInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String fetchWattNodeResult() throws InterruptedException {
        final AtomicReference atomicReference = new AtomicReference(null);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m578xfcdb78ba(atomicReference, countDownLatch);
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

    /* renamed from: lambda$fetchWattNodeResult$3$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m578xfcdb78ba(AtomicReference atomicReference, CountDownLatch countDownLatch) {
        try {
            try {
                HashMap map = new HashMap();
                map.put("inverterSn", this.inverter.getSerialNum());
                System.out.println("params == " + map);
                final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/wattNode/read", map);
                if (jSONObjectPostJson.getBoolean("success")) {
                    atomicReference.set(jSONObjectPostJson.toString());
                } else {
                    FragmentActivity activity = this.fragment.getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() throws JSONException {
                                this.f$0.m577x6fee619b(jSONObjectPostJson);
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
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m576x88a765ae(map);
            }
        }).start();
    }

    /* renamed from: lambda$WriteWattNode$7$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m576x88a765ae(HashMap map) throws JSONException {
        try {
            if (Tool.isEmpty((String) map.get("ctAmpsA")) || Tool.isEmpty((String) map.get("ctAmpsB")) || Tool.isEmpty((String) map.get("ctAmpsC"))) {
                FragmentActivity activity = this.fragment.getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m573xe1e02051();
                        }
                    });
                }
            } else {
                map.put("inverterSn", this.inverter.getSerialNum());
                System.out.println("params == " + map);
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/wattNode/write", map);
                if (jSONObjectPostJson.getBoolean("success")) {
                    this.fragment.getActivity().runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m574x6ecd3770();
                        }
                    });
                } else {
                    final String string = jSONObjectPostJson.getString("msg");
                    final FragmentActivity activity2 = this.fragment.getActivity();
                    if (activity2 != null) {
                        activity2.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.5
                            @Override // java.lang.Runnable
                            public void run() {
                                if ("invalidUpdateFrequency".equals(string)) {
                                    Tool.alert(activity2, Lv2RemoteSetFragment.this.getString(R.string.page_maintain_remote_set_result_invalidUpdateFrequency));
                                } else {
                                    Tool.alert(activity2, Lv2RemoteSetFragment.this.getString(R.string.local_set_result_failed));
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            this.fragment.getActivity().runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m575xfbba4e8f();
                }
            });
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$WriteWattNode$4$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m573xe1e02051() {
        Tool.alert(getActivity(), R.string.page_maintain_remote_set_alert_param_empty);
    }

    /* renamed from: lambda$WriteWattNode$5$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m574x6ecd3770() {
        Tool.alert(getActivity(), R.string.local_set_result_success);
    }

    /* renamed from: lambda$WriteWattNode$6$com-nfcx-eg4-view-main-fragment-lv2-Lv2RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m575xfbba4e8f() {
        Tool.alert(getActivity(), getString(R.string.error_processing_request));
    }

    private Set<String> loadValidParams() {
        HashSet hashSet = new HashSet();
        hashSet.add("FUNC_SET_TO_STANDBY");
        hashSet.add("INV_REBOOT");
        hashSet.add("FUNC_GREEN_EN");
        hashSet.add("FUNC_RUN_WITHOUT_GRID_12K");
        hashSet.add("HOLD_MAX_AC_INPUT_POWER");
        hashSet.add("FUNC_EPS_EN");
        hashSet.add("FUNC_SW_SEAMLESSLY_EN");
        hashSet.add("FUNC_PV_GRID_OFF_EN");
        hashSet.add("HOLD_EPS_VOLT_SET");
        hashSet.add("FUNC_FEED_IN_GRID_EN");
        hashSet.add("HOLD_FEED_IN_GRID_POWER_PERCENT");
        hashSet.add("FUNC_RUN_WITHOUT_GRID");
        hashSet.add("FUNC_DCI_EN");
        hashSet.add("FUNC_ANTI_ISLAND_EN");
        hashSet.add("FUNC_ISO_EN");
        hashSet.add("FUNC_NEUTRAL_DETECT_EN");
        hashSet.add("FUNC_GFCI_EN");
        hashSet.add("HOLD_PV_INPUT_MODE");
        hashSet.add("HOLD_START_PV_VOLT");
        hashSet.add("FUNC_BATTERY_BACKUP_CTRL");
        hashSet.add("HOLD_AC_CHARGE_POWER_CMD");
        hashSet.add("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE");
        hashSet.add("FUNC_GRID_PEAK_SHAVING");
        hashSet.add("_12K_HOLD_GRID_PEAK_SHAVING_POWER");
        hashSet.add("_12K_HOLD_GRID_PEAK_SHAVING_POWER_2");
        hashSet.add("_12K_HOLD_GRID_PEAK_SHAVING_VOLT");
        hashSet.add("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2");
        hashSet.add("_12K_HOLD_GRID_PEAK_SHAVING_SOC");
        hashSet.add("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2");
        hashSet.add("HOLD_PEAK_SHAVING_START_TIME_1");
        hashSet.add("HOLD_PEAK_SHAVING_END_TIME_1");
        hashSet.add("HOLD_PEAK_SHAVING_START_TIME_2");
        hashSet.add("HOLD_PEAK_SHAVING_END_TIME_2");
        hashSet.add("FUNC_AC_CHARGE");
        hashSet.add("BIT_AC_CHARGE_TYPE");
        hashSet.add("HOLD_AC_CHARGE_START_BATTERY_SOC");
        hashSet.add("HOLD_AC_CHARGE_SOC_LIMIT");
        hashSet.add("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE");
        hashSet.add("HOLD_AC_CHARGE_START_TIME");
        hashSet.add("HOLD_AC_CHARGE_END_TIME");
        hashSet.add("HOLD_AC_CHARGE_START_TIME_1");
        hashSet.add("HOLD_AC_CHARGE_END_TIME_1");
        hashSet.add("HOLD_AC_CHARGE_START_TIME_2");
        hashSet.add("HOLD_AC_CHARGE_END_TIME_2");
        hashSet.add("FUNC_FORCED_CHG_EN");
        hashSet.add("HOLD_FORCED_CHG_POWER_CMD");
        hashSet.add("HOLD_FORCED_CHG_SOC_LIMIT");
        hashSet.add("_12K_HOLD_CHARGE_FIRST_VOLT");
        hashSet.add("HOLD_FORCED_CHARGE_START_TIME");
        hashSet.add("HOLD_FORCED_CHARGE_END_TIME");
        hashSet.add("HOLD_FORCED_CHARGE_START_TIME_1");
        hashSet.add("HOLD_FORCED_CHARGE_END_TIME_1");
        hashSet.add("HOLD_FORCED_CHARGE_START_TIME_2");
        hashSet.add("HOLD_FORCED_CHARGE_END_TIME_2");
        hashSet.add("FUNC_FORCED_DISCHG_EN");
        hashSet.add("FUNC_PV_SELL_TO_GRID_EN");
        hashSet.add("HOLD_FORCED_DISCHG_POWER_CMD");
        hashSet.add("HOLD_FORCED_DISCHG_SOC_LIMIT");
        hashSet.add("_12K_HOLD_STOP_DISCHG_VOLT");
        hashSet.add("HOLD_FORCED_DISCHARGE_START_TIME");
        hashSet.add("HOLD_FORCED_DISCHARGE_END_TIME");
        hashSet.add("HOLD_FORCED_DISCHARGE_START_TIME_1");
        hashSet.add("HOLD_FORCED_DISCHARGE_END_TIME_1");
        hashSet.add("HOLD_FORCED_DISCHARGE_START_TIME_2");
        hashSet.add("HOLD_FORCED_DISCHARGE_END_TIME_2");
        hashSet.add("MODEL_BIT_BATTERY_TYPE");
        hashSet.add("FUNC_BAT_SHARED");
        hashSet.add("HOLD_SYSTEM_CHARGE_SOC_LIMIT");
        hashSet.add("HOLD_SYSTEM_CHARGE_VOLT_LIMIT");
        hashSet.add("HOLD_LEAD_ACID_CHARGE_RATE");
        hashSet.add("FUNC_CHARGE_LAST");
        hashSet.add("HOLD_LEAD_ACID_DISCHARGE_RATE");
        hashSet.add("HOLD_P_TO_USER_START_DISCHG");
        hashSet.add("HOLD_SOC_LOW_LIMIT_EPS_DISCHG");
        hashSet.add("HOLD_DISCHG_CUT_OFF_SOC_EOD");
        hashSet.add("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT");
        hashSet.add("HOLD_ON_GRID_EOD_VOLTAGE");
        return hashSet;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("Eg4 - Lv2RemoteSetFragment onResume...");
        initInverterSpinnerOnItemSelectedListener();
        refreshFragmentParams();
        this.webView.setWebChromeClient(new WebChromeClient());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b2  */
    /* renamed from: toast, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void m577x6fee619b(org.json.JSONObject r6) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment.m577x6fee619b(org.json.JSONObject):void");
    }
}