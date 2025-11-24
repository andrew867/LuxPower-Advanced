package com.lux.luxcloud.view.wifi;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE;
import com.lux.luxcloud.global.bean.set.RemoteWriteInfo;
import com.lux.luxcloud.protocol.tcp.DataFrameFactory;
import com.lux.luxcloud.protocol.tcp.dataframe.DataFrame;
import com.lux.luxcloud.tcp.TcpManager;
import com.lux.luxcloud.tool.DonglePskUtil;
import com.lux.luxcloud.tool.ProTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Version;
import com.lux.luxcloud.view.ble.BleConnectActivity;
import com.lux.luxcloud.view.updateFirmware.FirmwareChangeLogActivity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.i18n.LocalizedMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class WifiConnectActivity extends Activity {
    private static final int REQUEST_WIFI_PERMISSION = 5;
    public static WifiConnectActivity instance;
    String datalogParamData;
    private Button dongleConnectParamsButton;
    private Button eWifiChangeLogButton;
    private Button eWifiRemoteUpdateButton;
    String firmwareVersion;
    private boolean firstUseDongle;
    private boolean fromLogin;
    private boolean isDarkTheme;
    private String newVersionCode;
    private EditText passwordEditView;
    private ProgressBar searchSsidProgressBar;
    private ConstraintLayout serverIpLayout;
    private Spinner serverIpSpinner;
    private Button setServerIpButton;
    private EditText ssidEditView;
    private Spinner ssidSpinner;
    private Button switchSsidModeButton;
    private Button tcpActionButton;
    private TcpManager tcpManager = TcpManager.getInstance();
    private TextView tcpResultTextView;
    private Button wifiBluetoothButton;

    public int getTimeOutSecondsWithParamIndex(int i) {
        return (i == 5 || i == 6 || i == 11) ? 15 : 10;
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        DonglePskUtil.getFineLocationPermission(this, 5, i, strArr, iArr);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_wifi_connect);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.login_container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WifiConnectActivity.instance.finish();
            }
        });
        this.switchSsidModeButton = (Button) findViewById(R.id.wifi_connect_switchSsidModeButton);
        this.searchSsidProgressBar = (ProgressBar) findViewById(R.id.search_ssid_progressBar);
        this.ssidEditView = (EditText) findViewById(R.id.wifi_connect_ssid_editView);
        this.ssidSpinner = (Spinner) findViewById(R.id.wifi_connect_ssid_spinner);
        this.passwordEditView = (EditText) findViewById(R.id.wifi_connect_password_editView);
        this.tcpResultTextView = (TextView) findViewById(R.id.wifi_tcp_result_textView);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.wifi_connect_serverIpLayout);
        this.serverIpLayout = constraintLayout;
        constraintLayout.setVisibility(8);
        this.serverIpSpinner = (Spinner) findViewById(R.id.wifi_connect_serverIpSpinner);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ArrayList()));
        Button button = (Button) findViewById(R.id.wifi_connect_setServerIpButton);
        this.setServerIpButton = button;
        button.setOnClickListener(new AnonymousClass2());
        this.wifiBluetoothButton = (Button) findViewById(R.id.wifi_bluetooth_button);
        Button button2 = (Button) findViewById(R.id.eWifi_remoteUpdate_button);
        this.eWifiRemoteUpdateButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(WifiConnectActivity.instance, (Class<?>) EWifiRemoteUpdateActivity.class);
                intent.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_TCP);
                intent.putExtra("firmwareVersion", WifiConnectActivity.this.firmwareVersion);
                intent.putExtra("datalogParam", WifiConnectActivity.this.datalogParamData);
                WifiConnectActivity.this.startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.eWifi_change_log_button);
        this.eWifiChangeLogButton = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(WifiConnectActivity.instance, (Class<?>) FirmwareChangeLogActivity.class);
                intent.putExtra("fromWifiConnect", true);
                WifiConnectActivity.this.startActivity(intent);
            }
        });
        Button button4 = (Button) findViewById(R.id.Dongle_Connect_Params_button);
        this.dongleConnectParamsButton = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(WifiConnectActivity.instance, (Class<?>) DongleConnectActivity.class);
                intent.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_TCP);
                intent.putExtra("dongleType", Integer.parseInt(ProTool.showData(String.valueOf(WifiConnectActivity.this.datalogParamData.charAt(0))).trim()));
                intent.putExtra("fromWifiConnect", true);
                intent.putExtra("firstUseDongle", WifiConnectActivity.this.firstUseDongle);
                WifiConnectActivity.this.startActivity(intent);
            }
        });
        Button button5 = (Button) findViewById(R.id.wifi_tcp_action_button);
        this.tcpActionButton = button5;
        button5.setOnClickListener(new AnonymousClass6());
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m837lambda$onCreate$8$comluxluxcloudviewwifiWifiConnectActivity();
            }
        }).start();
    }

    /* renamed from: com.lux.luxcloud.view.wifi.WifiConnectActivity$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {
        AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            final String str;
            Property property = (Property) WifiConnectActivity.this.serverIpSpinner.getSelectedItem();
            if (property != null && !Tool.isEmpty(property.getName())) {
                WifiConnectActivity.this.setServerIpButton.setEnabled(false);
                String strExtractHost = Tool.extractHost(property.getName());
                if (TcpManager.isTlsConnection()) {
                    str = strExtractHost + ",4348";
                } else {
                    str = strExtractHost + ",4346";
                }
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m842x85f0048e(str);
                    }
                }).start();
                return;
            }
            Tool.alert(WifiConnectActivity.this, R.string.local_set_result_failed);
        }

        /* renamed from: lambda$onClick$3$com-lux-luxcloud-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m842x85f0048e(String str) {
            WifiConnectActivity wifiConnectActivity;
            Runnable runnable;
            try {
                try {
                    WifiConnectActivity.this.writeDatalogParam(6, str.getBytes(LocalizedMessage.DEFAULT_ENCODING));
                    WifiConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m839xb0f31231();
                        }
                    });
                    wifiConnectActivity = WifiConnectActivity.this;
                    runnable = new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m841x94465e6f();
                        }
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                    WifiConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m840xa29cb850();
                        }
                    });
                    wifiConnectActivity = WifiConnectActivity.this;
                    runnable = new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m841x94465e6f();
                        }
                    };
                }
                wifiConnectActivity.runOnUiThread(runnable);
            } catch (Throwable th) {
                WifiConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m841x94465e6f();
                    }
                });
                throw th;
            }
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m839xb0f31231() {
            Tool.alert(WifiConnectActivity.this, R.string.local_set_result_success);
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m840xa29cb850() {
            Tool.alert(WifiConnectActivity.this, R.string.local_set_result_failed);
        }

        /* renamed from: lambda$onClick$2$com-lux-luxcloud-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m841x94465e6f() {
            WifiConnectActivity.this.setServerIpButton.setEnabled(true);
        }
    }

    /* renamed from: com.lux.luxcloud.view.wifi.WifiConnectActivity$6, reason: invalid class name */
    class AnonymousClass6 implements View.OnClickListener {
        AnonymousClass6() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            WifiConnectActivity.this.tcpResultTextView.setVisibility(4);
            final String ssid = WifiConnectActivity.this.getSsid();
            if (Tool.isEmpty(ssid) || "unknown ssid".equals(ssid)) {
                WifiConnectActivity.this.tcpResultTextView.setVisibility(0);
                WifiConnectActivity.this.tcpResultTextView.setText(R.string.wifi_connect_ssid_invalid);
                return;
            }
            final String strTrim = WifiConnectActivity.this.passwordEditView.getText().toString().trim();
            if (Tool.isEmpty(strTrim)) {
                strTrim = "";
            }
            WifiConnectActivity.this.tcpActionButton.setText(R.string.wifi_connect_btn_sending);
            WifiConnectActivity.this.tcpActionButton.setEnabled(false);
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m843xb0f31235(ssid, strTrim);
                }
            }).start();
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-wifi-WifiConnectActivity$6, reason: not valid java name */
        /* synthetic */ void m843xb0f31235(String str, String str2) {
            DataFrame dataFrameCreateWriteDatalogParamDataFrame;
            if (WifiConnectActivity.this.tcpManager.initialize(true)) {
                try {
                    dataFrameCreateWriteDatalogParamDataFrame = DataFrameFactory.createWriteDatalogParamDataFrame(WifiConnectActivity.this.tcpManager.getTcpProtocol(), WifiConnectActivity.this.tcpManager.getDatalogSn(), 4, (str + "," + str2).getBytes(LocalizedMessage.DEFAULT_ENCODING));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    dataFrameCreateWriteDatalogParamDataFrame = null;
                }
                String strSendCommand = WifiConnectActivity.this.tcpManager.sendCommand("write_datalog_4", dataFrameCreateWriteDatalogParamDataFrame);
                if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() == 21 && strSendCommand.charAt(20) == 0) {
                    WifiConnectActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_tcp_set_success_reboot);
                    return;
                } else {
                    WifiConnectActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_write_tcp_failed);
                    return;
                }
            }
            WifiConnectActivity.this.closeTcpActionAtUiThread(R.string.phrase_toast_local_connect_error);
        }
    }

    /* renamed from: lambda$onCreate$8$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m837lambda$onCreate$8$comluxluxcloudviewwifiWifiConnectActivity() {
        Runnable runnable;
        String strTrim;
        String strFetchDongleSnFromWifiSsid = DonglePskUtil.fetchDongleSnFromWifiSsid(this, 5);
        Log.i("LuxPower::wifiConnectActivity", "dongleSn: " + strFetchDongleSnFromWifiSsid);
        this.tcpManager.setDatalogSn(strFetchDongleSnFromWifiSsid);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m829lambda$onCreate$0$comluxluxcloudviewwifiWifiConnectActivity();
            }
        });
        try {
            if (!this.tcpManager.initialize(true)) {
                Log.i(Version.TAG, "finished");
                runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m836lambda$onCreate$7$comluxluxcloudviewwifiWifiConnectActivity();
                    }
                });
                return;
            }
            try {
                if (TcpManager.isTlsConnection()) {
                    strTrim = ProTool.showData(readDatalogParam(19)).trim();
                    this.firstUseDongle = "00".equals(strTrim);
                } else {
                    strTrim = "";
                    this.firstUseDongle = false;
                }
                System.out.println("DONGLE_QUERY_FIRST_USE == " + strTrim);
                if ("00".equals(strTrim)) {
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m830lambda$onCreate$1$comluxluxcloudviewwifiWifiConnectActivity();
                        }
                    });
                    this.datalogParamData = readDatalogParam(11);
                    System.out.println("datalogParamData2 == " + ProTool.showData(String.valueOf(this.datalogParamData.charAt(0))));
                    if (!Tool.isEmptyNoTrim(ProTool.showData(String.valueOf(this.datalogParamData.charAt(0)))) && (this.datalogParamData.charAt(0) == 5 || this.datalogParamData.charAt(0) == 6 || this.datalogParamData.charAt(0) == 7 || this.datalogParamData.charAt(0) == '\b' || this.datalogParamData.charAt(0) == '\t')) {
                        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m831lambda$onCreate$2$comluxluxcloudviewwifiWifiConnectActivity();
                            }
                        });
                    }
                } else {
                    String datalogParam = readDatalogParam(5);
                    if (!Tool.isEmpty(datalogParam)) {
                        final ArrayList arrayList = new ArrayList();
                        for (String str : datalogParam.split(";")) {
                            if (!Tool.isEmpty(str)) {
                                arrayList.add(new Property(str, str));
                            }
                        }
                        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m832lambda$onCreate$3$comluxluxcloudviewwifiWifiConnectActivity(arrayList);
                            }
                        });
                    }
                    if (!Tool.isAloneClusterGroup()) {
                        final String datalogParam2 = readDatalogParam(6);
                        System.out.println("serverIpAndPort == " + datalogParam2);
                        if (Tool.isEmpty(datalogParam2)) {
                            showFirstClusterServersWithUnknown();
                        } else {
                            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m833lambda$onCreate$4$comluxluxcloudviewwifiWifiConnectActivity(datalogParam2);
                                }
                            });
                        }
                    }
                    this.firmwareVersion = readDatalogParam(7);
                    this.datalogParamData = readDatalogParam(11);
                    System.out.println("datalogParamData1 == " + this.datalogParamData.charAt(0));
                    if (!Tool.isEmptyNoTrim(this.datalogParamData) && (this.datalogParamData.charAt(0) == 5 || this.datalogParamData.charAt(0) == 6 || this.datalogParamData.charAt(0) == 7 || this.datalogParamData.charAt(0) == '\b' || this.datalogParamData.charAt(0) == '\t')) {
                        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m834lambda$onCreate$5$comluxluxcloudviewwifiWifiConnectActivity();
                            }
                        });
                    }
                }
                Log.i(Version.TAG, "finished");
                runnable = new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m835lambda$onCreate$6$comluxluxcloudviewwifiWifiConnectActivity();
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(Version.TAG, "finished");
                runnable = new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m835lambda$onCreate$6$comluxluxcloudviewwifiWifiConnectActivity();
                    }
                };
            }
            runOnUiThread(runnable);
        } catch (Throwable th) {
            Log.i(Version.TAG, "finished");
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m835lambda$onCreate$6$comluxluxcloudviewwifiWifiConnectActivity();
                }
            });
            throw th;
        }
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m829lambda$onCreate$0$comluxluxcloudviewwifiWifiConnectActivity() {
        this.searchSsidProgressBar.setVisibility(0);
    }

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m830lambda$onCreate$1$comluxluxcloudviewwifiWifiConnectActivity() {
        Tool.alertNotInUiThread(instance, getString(R.string.wifi_connect_dongle_first_used));
    }

    /* renamed from: lambda$onCreate$2$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m831lambda$onCreate$2$comluxluxcloudviewwifiWifiConnectActivity() {
        this.dongleConnectParamsButton.setVisibility(0);
        this.eWifiRemoteUpdateButton.setVisibility(0);
        this.eWifiRemoteUpdateButton.setText(instance.getString(R.string._1_s_update_firmware, new Object[]{""}));
        this.switchSsidModeButton.setEnabled(false);
        this.ssidEditView.setEnabled(false);
        this.ssidSpinner.setEnabled(false);
        this.tcpActionButton.setEnabled(false);
        this.eWifiRemoteUpdateButton.setEnabled(false);
        this.wifiBluetoothButton.setEnabled(false);
        this.setServerIpButton.setEnabled(false);
        this.serverIpSpinner.setEnabled(false);
        this.passwordEditView.setEnabled(false);
    }

    /* renamed from: lambda$onCreate$3$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m832lambda$onCreate$3$comluxluxcloudviewwifiWifiConnectActivity(List list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ssidSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        if (list.size() > 0) {
            this.ssidEditView.setVisibility(8);
            this.ssidSpinner.setVisibility(0);
        } else {
            this.ssidSpinner.setVisibility(8);
            this.ssidEditView.setVisibility(0);
        }
    }

    /* renamed from: lambda$onCreate$4$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m833lambda$onCreate$4$comluxluxcloudviewwifiWifiConnectActivity(String str) {
        this.serverIpLayout.setVisibility(0);
        ArrayAdapter arrayAdapter = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getFirstClusterServers(this));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Integer indexByHost = Constants.getIndexByHost(str);
        if (indexByHost != null) {
            this.serverIpSpinner.setSelection(indexByHost.intValue());
        } else {
            this.serverIpSpinner.setSelection(r0.size() - 1);
        }
    }

    /* renamed from: lambda$onCreate$5$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m834lambda$onCreate$5$comluxluxcloudviewwifiWifiConnectActivity() {
        this.dongleConnectParamsButton.setVisibility(0);
        this.eWifiRemoteUpdateButton.setVisibility(0);
        this.eWifiRemoteUpdateButton.setText(instance.getString(R.string._1_s_update_firmware, new Object[]{this.firmwareVersion + " - "}));
    }

    /* renamed from: lambda$onCreate$6$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m835lambda$onCreate$6$comluxluxcloudviewwifiWifiConnectActivity() {
        this.searchSsidProgressBar.setVisibility(4);
    }

    /* renamed from: lambda$onCreate$7$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m836lambda$onCreate$7$comluxluxcloudviewwifiWifiConnectActivity() {
        this.searchSsidProgressBar.setVisibility(4);
    }

    private void showFirstClusterServersWithUnknown() {
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m838x8dcc43ae();
            }
        });
    }

    /* renamed from: lambda$showFirstClusterServersWithUnknown$9$com-lux-luxcloud-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m838x8dcc43ae() {
        this.serverIpLayout.setVisibility(0);
        List<Property> firstClusterServersWithUnknown = GlobalInfo.getInstance().getFirstClusterServersWithUnknown(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, firstClusterServersWithUnknown);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.serverIpSpinner.setSelection(firstClusterServersWithUnknown.size() - 1);
        this.setServerIpButton.setEnabled(true);
    }

    private double getVersionNumber(String str) {
        try {
            return Double.parseDouble(str.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    public String readDatalogParam(int i) {
        if (!this.tcpManager.initialize(true) || Tool.isEmpty(this.tcpManager.getDatalogSn())) {
            return null;
        }
        String strSendCommand = this.tcpManager.sendCommand("read_datalog_" + i, DataFrameFactory.createReadDatalogParamDataFrame(this.tcpManager.getTcpProtocol(), this.tcpManager.getDatalogSn(), i), getTimeOutSecondsWithParamIndex(i));
        if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 22) {
            return null;
        }
        return strSendCommand.substring(22);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSsid() {
        if (this.ssidEditView.getVisibility() == 0) {
            return this.ssidEditView.getText().toString().trim();
        }
        Property property = (Property) this.ssidSpinner.getSelectedItem();
        return property != null ? property.getName() : "unknown ssid";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeTcpActionAtUiThread(final int i) {
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.WifiConnectActivity.7
            @Override // java.lang.Runnable
            public void run() {
                WifiConnectActivity.this.tcpResultTextView.setVisibility(0);
                WifiConnectActivity.this.tcpResultTextView.setText(i);
                WifiConnectActivity.this.tcpActionButton.setText(R.string.wifi_tcp_btn_connect);
                WifiConnectActivity.this.tcpActionButton.setEnabled(true);
            }
        });
    }

    public void clickConnectFromBrowserButton(View view) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("http://10.10.10.1"));
        intent.setAction("android.intent.action.VIEW");
        startActivity(intent);
    }

    public void clickSwitchSsidModeButton(View view) {
        if (this.ssidSpinner.getVisibility() == 8) {
            this.ssidEditView.setVisibility(8);
            this.ssidSpinner.setVisibility(0);
        } else {
            this.ssidSpinner.setVisibility(8);
            this.ssidEditView.setVisibility(0);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.tcpManager.close();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            boolean booleanExtra = intent.getBooleanExtra("fromLogin", false);
            this.fromLogin = booleanExtra;
            if (booleanExtra) {
                this.newVersionCode = intent.getStringExtra("newVersionCode");
                System.out.println("newVersionCode == " + this.newVersionCode);
            }
        }
    }

    public void clickBleConnectButton(View view) {
        Intent intent = new Intent(this, (Class<?>) BleConnectActivity.class);
        intent.putExtra(Constants.TARGET, Constants.TARGET_WIFI_CONFIG);
        intent.putExtra("ssid", getSsid());
        intent.putExtra("password", this.passwordEditView.getText().toString());
        startActivity(intent);
    }

    private void runDatalogParamWrite(int i, byte[] bArr) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.DATALOG_PARAM);
        remoteWriteInfo.setDatalogParamIndex(Integer.valueOf(i));
        remoteWriteInfo.setDatalogParamValues(bArr);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private WifiConnectActivity instance;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(WifiConnectActivity wifiConnectActivity) {
            this.instance = wifiConnectActivity;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(RemoteWriteInfo... remoteWriteInfoArr) {
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                if (AnonymousClass8.$SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()] == 1) {
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
                com.lux.luxcloud.view.wifi.WifiConnectActivity r4 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                r1 = 2131886305(0x7f1200e1, float:1.9407185E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                goto L46
            L17:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L41
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r1 = com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r2 = r2.getRemoteWriteType()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L41
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
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
                com.lux.luxcloud.view.wifi.WifiConnectActivity r1 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.view.wifi.WifiConnectActivity.access$1100(r1, r4)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            L46:
                com.lux.luxcloud.view.wifi.WifiConnectActivity r4 = r3.instance
                android.widget.Button r4 = com.lux.luxcloud.view.wifi.WifiConnectActivity.access$100(r4)
                r4.setEnabled(r0)
                goto L64
            L50:
                r4 = move-exception
                goto L65
            L52:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                com.lux.luxcloud.view.wifi.WifiConnectActivity r4 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L5f
                r1 = 2131886563(0x7f1201e3, float:1.9407708E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L5f
                goto L46
            L5f:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                goto L46
            L64:
                return
            L65:
                com.lux.luxcloud.view.wifi.WifiConnectActivity r1 = r3.instance
                android.widget.Button r1 = com.lux.luxcloud.view.wifi.WifiConnectActivity.access$100(r1)
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.wifi.WifiConnectActivity.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.lux.luxcloud.view.wifi.WifiConnectActivity$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE;

        static {
            int[] iArr = new int[REMOTE_WRITE_TYPE.values().length];
            $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE = iArr;
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
}