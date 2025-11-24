package com.nfcx.eg4.view.wifi;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.firebase.messaging.Constants;
import com.nfcx.eg4.R;
import com.nfcx.eg4.connect.LocalConnectManager;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE;
import com.nfcx.eg4.global.bean.set.RemoteWriteInfo;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.tcp.TcpManager;
import com.nfcx.eg4.tool.DonglePskUtil;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.i18n.LocalizedMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class EWifiRemoteUpdateActivity extends Activity {
    private static final int REQUEST_WIFI_PERMISSION = 9;
    public static EWifiRemoteUpdateActivity instance;
    private static String selectedFirmware;
    private Button eWifiRemoteUpdateButton;
    private Spinner eWifiRemoteUpdateSpinner;
    private boolean isDarkTheme;
    private TcpManager tcpManager = TcpManager.getInstance();
    private BroadcastReceiver wifiStateReceiver;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ewifi_remote_update);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EWifiRemoteUpdateActivity.instance.finish();
            }
        });
        getIntent().getStringExtra(Constants.LOCAL_CONNECT_TYPE);
        LocalConnectManager.setupDongleSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 9));
        selectedFirmware = null;
        Spinner spinner = (Spinner) findViewById(R.id.eWifi_remoteUpdateSpinner);
        this.eWifiRemoteUpdateSpinner = spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                String unused = EWifiRemoteUpdateActivity.selectedFirmware = (String) EWifiRemoteUpdateActivity.this.eWifiRemoteUpdateSpinner.getSelectedItem();
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
                String unused = EWifiRemoteUpdateActivity.selectedFirmware = null;
            }
        });
        this.eWifiRemoteUpdateButton = (Button) findViewById(R.id.eWifi_remoteUpdateButton);
    }

    private void getAllFirmwares(final JSONArray jSONArray, final String str) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m689x88e54121(str, jSONArray);
            }
        }).start();
    }

    /* renamed from: lambda$getAllFirmwares$0$com-nfcx-eg4-view-wifi-EWifiRemoteUpdateActivity, reason: not valid java name */
    /* synthetic */ void m689x88e54121(String str, JSONArray jSONArray) {
        try {
            ArrayList arrayList = new ArrayList();
            if (!Tool.isEmpty(ProTool.showData(str))) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    addFirmwareNameIfMatches(jSONArray.getJSONObject(i), str, arrayList);
                }
            }
            updateUI(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFirmwareNameIfMatches(JSONObject jSONObject, String str, List<String> list) throws JSONException {
        char cCharAt = str.charAt(0);
        String string = jSONObject.getString("datalogType");
        if ((cCharAt == 5 && "ESP_WIFI".equals(string)) || ((cCharAt == 6 && "ESP_WIFI6".equals(string)) || (cCharAt == '\t' && "ESP_WIFI_E".equals(string)))) {
            list.add(jSONObject.getString("sourceName"));
        }
    }

    private void updateUI(final List<String> list) {
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m690x34b74eb6(list);
            }
        });
    }

    /* renamed from: lambda$updateUI$1$com-nfcx-eg4-view-wifi-EWifiRemoteUpdateActivity, reason: not valid java name */
    /* synthetic */ void m690x34b74eb6(List list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.eWifiRemoteUpdateSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
    }

    public void clickUpdateFirmwareButton(View view) {
        if (selectedFirmware != null) {
            try {
                getVersionNumber(getSharedPreferences("userInfo", 0).getString("firmwareVersion", " "));
                getSelectedFirmwareVersionNumber(selectedFirmware);
                this.eWifiRemoteUpdateButton.setEnabled(false);
                runDatalogParamWrite(9, ("http://47.254.33.206:8083/resource/firmware/" + selectedFirmware).getBytes(LocalizedMessage.DEFAULT_ENCODING));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private double getVersionNumber(String str) {
        try {
            return Double.parseDouble(str.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    private double getSelectedFirmwareVersionNumber(String str) {
        try {
            Matcher matcher = Pattern.compile("V(\\d+_\\d+)\\.bin").matcher(str);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1).replace("_", "."));
            }
            return 0.0d;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    public String readDatalogParam(int i) {
        if (!this.tcpManager.initialize(true) || Tool.isEmpty(this.tcpManager.getDatalogSn())) {
            return null;
        }
        String strSendCommand = this.tcpManager.sendCommand("read_datalog_" + i, DataFrameFactory.createReadDatalogParamDataFrame(this.tcpManager.getTcpProtocol(), this.tcpManager.getDatalogSn(), i), 10);
        if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 22) {
            return null;
        }
        return strSendCommand.substring(22);
    }

    private void runDatalogParamWrite(int i, byte[] bArr) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.DATALOG_PARAM);
        remoteWriteInfo.setDatalogParamIndex(Integer.valueOf(i));
        remoteWriteInfo.setDatalogParamValues(bArr);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private EWifiRemoteUpdateActivity instance;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(EWifiRemoteUpdateActivity eWifiRemoteUpdateActivity) {
            this.instance = eWifiRemoteUpdateActivity;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(RemoteWriteInfo... remoteWriteInfoArr) {
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                if (AnonymousClass3.$SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()] == 1) {
                    if (remoteWriteInfo.getDatalogParamIndex() == null || remoteWriteInfo.getDatalogParamValues() == null) {
                        return this.instance.createFailureJSONObject("PARAM_EMPTY");
                    }
                    return this.instance.writeDatalogParam(remoteWriteInfo.getDatalogParamIndex().intValue(), remoteWriteInfo.getDatalogParamValues()) ? this.instance.createSuccessJSONObject() : this.instance.createFailureJSONObject("FAILED");
                }
            }
            return this.instance.createFailureJSONObject("UNKNOWN_ERROR");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0017 A[Catch: all -> 0x0050, Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x0017, B:9:0x001b, B:11:0x0029, B:15:0x003e, B:16:0x0041), top: B:33:0x0006, outer: #0 }] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r4) {
            /*
                r3 = this;
                super.onPostExecute(r4)
                r0 = 1
                if (r4 == 0) goto L17
                java.lang.String r1 = "success"
                boolean r1 = r4.getBoolean(r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L17
                com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity r4 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                r1 = 2131887153(0x7f120431, float:1.9408905E38)
                com.nfcx.eg4.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                goto L46
            L17:
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L41
                com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE r1 = com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE r2 = r2.getRemoteWriteType()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L41
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                android.widget.ToggleButton r2 = r2.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                boolean r2 = r2.isChecked()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r2 != 0) goto L3d
                r2 = r0
                goto L3e
            L3d:
                r2 = 0
            L3e:
                r1.setChecked(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            L41:
                com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity r1 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity.access$400(r1, r4)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            L46:
                com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity r4 = r3.instance
                android.widget.Button r4 = com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity.access$500(r4)
                r4.setEnabled(r0)
                goto L64
            L50:
                r4 = move-exception
                goto L65
            L52:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity r4 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L5f
                r1 = 2131886525(0x7f1201bd, float:1.9407631E38)
                com.nfcx.eg4.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L5f
                goto L46
            L5f:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                goto L46
            L64:
                return
            L65:
                com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity r1 = r3.instance
                android.widget.Button r1 = com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity.access$500(r1)
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE;

        static {
            int[] iArr = new int[REMOTE_WRITE_TYPE.values().length];
            $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE = iArr;
            try {
                iArr[REMOTE_WRITE_TYPE.DATALOG_PARAM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public boolean writeDatalogParam(int i, byte[] bArr) {
        if (this.tcpManager.initialize(true) && !Tool.isEmpty(this.tcpManager.getDatalogSn())) {
            String strSendCommand = this.tcpManager.sendCommand("write_datalog_" + i, DataFrameFactory.createWriteDatalogParamDataFrame(this.tcpManager.getTcpProtocol(), this.tcpManager.getDatalogSn(), i, bArr));
            return !Tool.isEmpty(strSendCommand) && strSendCommand.length() == 21 && strSendCommand.charAt(20) == 0;
        }
        return false;
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

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject createSuccessJSONObject() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("success", true);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toast(JSONObject jSONObject) throws Exception {
        if (jSONObject == null) {
            Toast.makeText(this, R.string.phrase_toast_network_error, 1).show();
            return;
        }
        String string = jSONObject.getString("msg");
        string.hashCode();
        switch (string) {
            case "DEVICE_OFFLINE":
                Toast.makeText(this, R.string.page_maintain_remote_set_result_device_offline, 1).show();
                break;
            case "ACTION_ERROR_UNDONE":
                Toast.makeText(this, R.string.page_maintain_remote_set_result_set_undo, 1).show();
                break;
            case "DATAFRAME_TIMEOUT":
                Toast.makeText(this, R.string.page_maintain_remote_set_result_timeout, 1).show();
                break;
            case "INTEGER_FORMAT_ERROR":
                Toast.makeText(this, R.string.page_maintain_remote_set_alert_param_should_int, 1).show();
                break;
            case "PARAM_EMPTY":
                Toast.makeText(this, R.string.page_maintain_remote_set_alert_param_empty, 1).show();
                break;
            case "PARAM_ERROR":
                Toast.makeText(this, R.string.page_maintain_remote_set_result_param_error, 1).show();
                break;
            case "DATAFRAME_UNSEND":
                Toast.makeText(this, R.string.page_maintain_remote_set_result_command_not_send, 1).show();
                break;
            case "REMOTE_READ_ERROR":
            case "REMOTE_SET_ERROR":
                Toast.makeText(this, getString(R.string.page_maintain_remote_set_result_failed) + " " + jSONObject.getInt("errorCode"), 1).show();
                break;
            case "SERVER_HTTP_EXCEPTION":
                Toast.makeText(this, R.string.page_maintain_remote_set_result_server_exception, 1).show();
                break;
            case "OUT_RANGE_ERROR":
                Toast.makeText(this, getString(R.string.page_maintain_remote_set_alert_param_out_range), 1).show();
                break;
            default:
                Toast.makeText(this, R.string.local_set_result_failed, 1).show();
                break;
        }
    }

    @Override // android.app.Activity
    protected void onResume() throws JSONException {
        super.onResume();
        handleIntent(getIntent(), getSharedPreferences("userInfo", 0).getString("firmwares", " "));
    }

    private void handleIntent(Intent intent, String str) throws JSONException {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("datalogParam");
            this.eWifiRemoteUpdateButton.setText(instance.getString(R.string._1_s_update_firmware, new Object[]{intent.getStringExtra("firmwareVersion") + " - "}));
            try {
                instance.getAllFirmwares(new JSONObject(str).getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE), stringExtra);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.eWifiRemoteUpdateSpinner.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ArrayList()));
    }
}