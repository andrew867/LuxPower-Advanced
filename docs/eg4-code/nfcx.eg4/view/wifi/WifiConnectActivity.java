package com.nfcx.eg4.view.wifi;

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
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE;
import com.nfcx.eg4.global.bean.set.RemoteWriteInfo;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;
import com.nfcx.eg4.tcp.TcpManager;
import com.nfcx.eg4.tool.DonglePskUtil;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.ble.BleConnectActivity;
import com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.i18n.LocalizedMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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
        return (i == 5 || i == 11) ? 15 : 10;
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
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity.1
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
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity.3
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
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(WifiConnectActivity.instance, (Class<?>) FirmwareChangeLogActivity.class);
                intent.putExtra("fromWifiConnect", true);
                WifiConnectActivity.this.startActivity(intent);
            }
        });
        Button button4 = (Button) findViewById(R.id.Dongle_Connect_Params_button);
        this.dongleConnectParamsButton = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity.5
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
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m698lambda$onCreate$7$comnfcxeg4viewwifiWifiConnectActivity();
            }
        }).start();
    }

    /* renamed from: com.nfcx.eg4.view.wifi.WifiConnectActivity$2, reason: invalid class name */
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
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m703lambda$onClick$3$comnfcxeg4viewwifiWifiConnectActivity$2(str);
                    }
                }).start();
                return;
            }
            Tool.alert(WifiConnectActivity.this, R.string.local_set_result_failed);
        }

        /* renamed from: lambda$onClick$3$com-nfcx-eg4-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m703lambda$onClick$3$comnfcxeg4viewwifiWifiConnectActivity$2(String str) {
            WifiConnectActivity wifiConnectActivity;
            Runnable runnable;
            try {
                try {
                    WifiConnectActivity.this.writeDatalogParam(6, str.getBytes(LocalizedMessage.DEFAULT_ENCODING));
                    WifiConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m700lambda$onClick$0$comnfcxeg4viewwifiWifiConnectActivity$2();
                        }
                    });
                    wifiConnectActivity = WifiConnectActivity.this;
                    runnable = new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m702lambda$onClick$2$comnfcxeg4viewwifiWifiConnectActivity$2();
                        }
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                    WifiConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m701lambda$onClick$1$comnfcxeg4viewwifiWifiConnectActivity$2();
                        }
                    });
                    wifiConnectActivity = WifiConnectActivity.this;
                    runnable = new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m702lambda$onClick$2$comnfcxeg4viewwifiWifiConnectActivity$2();
                        }
                    };
                }
                wifiConnectActivity.runOnUiThread(runnable);
            } catch (Throwable th) {
                WifiConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m702lambda$onClick$2$comnfcxeg4viewwifiWifiConnectActivity$2();
                    }
                });
                throw th;
            }
        }

        /* renamed from: lambda$onClick$0$com-nfcx-eg4-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m700lambda$onClick$0$comnfcxeg4viewwifiWifiConnectActivity$2() {
            Tool.alert(WifiConnectActivity.this, R.string.local_set_result_success);
        }

        /* renamed from: lambda$onClick$1$com-nfcx-eg4-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m701lambda$onClick$1$comnfcxeg4viewwifiWifiConnectActivity$2() {
            Tool.alert(WifiConnectActivity.this, R.string.local_set_result_failed);
        }

        /* renamed from: lambda$onClick$2$com-nfcx-eg4-view-wifi-WifiConnectActivity$2, reason: not valid java name */
        /* synthetic */ void m702lambda$onClick$2$comnfcxeg4viewwifiWifiConnectActivity$2() {
            WifiConnectActivity.this.setServerIpButton.setEnabled(true);
        }
    }

    /* renamed from: com.nfcx.eg4.view.wifi.WifiConnectActivity$6, reason: invalid class name */
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
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m704lambda$onClick$0$comnfcxeg4viewwifiWifiConnectActivity$6(ssid, strTrim);
                }
            }).start();
        }

        /* renamed from: lambda$onClick$0$com-nfcx-eg4-view-wifi-WifiConnectActivity$6, reason: not valid java name */
        /* synthetic */ void m704lambda$onClick$0$comnfcxeg4viewwifiWifiConnectActivity$6(String str, String str2) {
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

    /* renamed from: lambda$onCreate$7$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m698lambda$onCreate$7$comnfcxeg4viewwifiWifiConnectActivity() {
        Runnable runnable;
        String strTrim;
        String strFetchDongleSnFromWifiSsid = DonglePskUtil.fetchDongleSnFromWifiSsid(this, 5);
        Log.i("Eg4::wifiConnectActivity", "dongleSn: " + strFetchDongleSnFromWifiSsid);
        this.tcpManager.setDatalogSn(strFetchDongleSnFromWifiSsid);
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m691lambda$onCreate$0$comnfcxeg4viewwifiWifiConnectActivity();
            }
        });
        try {
            if (this.tcpManager.initialize(true)) {
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
                        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m692lambda$onCreate$1$comnfcxeg4viewwifiWifiConnectActivity();
                            }
                        });
                        this.datalogParamData = readDatalogParam(11);
                        System.out.println("datalogParamData2 == " + ProTool.showData(String.valueOf(this.datalogParamData.charAt(0))));
                        if (!Tool.isEmptyNoTrim(this.datalogParamData) && (this.datalogParamData.charAt(0) == 5 || this.datalogParamData.charAt(0) == 6 || this.datalogParamData.charAt(0) == 7 || this.datalogParamData.charAt(0) == '\b' || this.datalogParamData.charAt(0) == '\t')) {
                            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m693lambda$onCreate$2$comnfcxeg4viewwifiWifiConnectActivity();
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
                            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m694lambda$onCreate$3$comnfcxeg4viewwifiWifiConnectActivity(arrayList);
                                }
                            });
                        }
                        final String datalogParam2 = readDatalogParam(6);
                        System.out.println("serverIpAndPort == " + datalogParam2);
                        if (Tool.isEmpty(datalogParam2)) {
                            showFirstClusterServersWithUnknown();
                        } else {
                            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m695lambda$onCreate$4$comnfcxeg4viewwifiWifiConnectActivity(datalogParam2);
                                }
                            });
                        }
                        this.firmwareVersion = readDatalogParam(7);
                        this.datalogParamData = readDatalogParam(11);
                        System.out.println("datalogParamData == " + this.datalogParamData.charAt(0));
                        if (!Tool.isEmptyNoTrim(this.datalogParamData) && (this.datalogParamData.charAt(0) == 5 || this.datalogParamData.charAt(0) == 6 || this.datalogParamData.charAt(0) == 7 || this.datalogParamData.charAt(0) == '\b' || this.datalogParamData.charAt(0) == '\t')) {
                            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m696lambda$onCreate$5$comnfcxeg4viewwifiWifiConnectActivity();
                                }
                            });
                        }
                    }
                    runnable = new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m697lambda$onCreate$6$comnfcxeg4viewwifiWifiConnectActivity();
                        }
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                    runnable = new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m697lambda$onCreate$6$comnfcxeg4viewwifiWifiConnectActivity();
                        }
                    };
                }
                runOnUiThread(runnable);
            }
        } catch (Throwable th) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m697lambda$onCreate$6$comnfcxeg4viewwifiWifiConnectActivity();
                }
            });
            throw th;
        }
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m691lambda$onCreate$0$comnfcxeg4viewwifiWifiConnectActivity() {
        this.searchSsidProgressBar.setVisibility(0);
    }

    /* renamed from: lambda$onCreate$1$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m692lambda$onCreate$1$comnfcxeg4viewwifiWifiConnectActivity() {
        Tool.alertNotInUiThread(instance, getString(R.string.wifi_connect_dongle_first_used));
    }

    /* renamed from: lambda$onCreate$2$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m693lambda$onCreate$2$comnfcxeg4viewwifiWifiConnectActivity() {
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

    /* renamed from: lambda$onCreate$3$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m694lambda$onCreate$3$comnfcxeg4viewwifiWifiConnectActivity(List list) {
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

    /* renamed from: lambda$onCreate$4$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m695lambda$onCreate$4$comnfcxeg4viewwifiWifiConnectActivity(String str) {
        this.serverIpLayout.setVisibility(0);
        ArrayAdapter arrayAdapter = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getSecondClusterServers());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Integer indexByHost = Constants.getIndexByHost(str);
        if (indexByHost != null) {
            this.serverIpSpinner.setSelection(indexByHost.intValue());
        } else {
            this.serverIpSpinner.setSelection(r0.size() - 1);
        }
    }

    /* renamed from: lambda$onCreate$5$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m696lambda$onCreate$5$comnfcxeg4viewwifiWifiConnectActivity() {
        this.dongleConnectParamsButton.setVisibility(0);
        this.eWifiRemoteUpdateButton.setVisibility(0);
        this.eWifiRemoteUpdateButton.setText(instance.getString(R.string._1_s_update_firmware, new Object[]{this.firmwareVersion + " - "}));
        this.eWifiChangeLogButton.setVisibility(0);
    }

    /* renamed from: lambda$onCreate$6$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m697lambda$onCreate$6$comnfcxeg4viewwifiWifiConnectActivity() {
        this.searchSsidProgressBar.setVisibility(4);
    }

    private void showFirstClusterServersWithUnknown() {
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m699x95ec7dfd();
            }
        });
    }

    /* renamed from: lambda$showFirstClusterServersWithUnknown$8$com-nfcx-eg4-view-wifi-WifiConnectActivity, reason: not valid java name */
    /* synthetic */ void m699x95ec7dfd() {
        this.serverIpLayout.setVisibility(0);
        List<Property> secondClusterServers = GlobalInfo.getInstance().getSecondClusterServers();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, secondClusterServers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.serverIpSpinner.setSelection(secondClusterServers.size() - 1);
        this.setServerIpButton.setEnabled(true);
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
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.WifiConnectActivity.7
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

    public void clickBleConnectButton(View view) {
        Intent intent = new Intent(this, (Class<?>) BleConnectActivity.class);
        intent.putExtra(TypedValues.AttributesType.S_TARGET, Constants.BLUETOOTH_TARGET_WIFI_CONFIG);
        intent.putExtra("ssid", getSsid());
        intent.putExtra("password", this.passwordEditView.getText());
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
                if (AnonymousClass8.$SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()] == 1) {
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
                com.nfcx.eg4.view.wifi.WifiConnectActivity r4 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                r1 = 2131886279(0x7f1200c7, float:1.9407132E38)
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
                com.nfcx.eg4.view.wifi.WifiConnectActivity r1 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.nfcx.eg4.view.wifi.WifiConnectActivity.access$1100(r1, r4)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            L46:
                com.nfcx.eg4.view.wifi.WifiConnectActivity r4 = r3.instance
                android.widget.Button r4 = com.nfcx.eg4.view.wifi.WifiConnectActivity.access$100(r4)
                r4.setEnabled(r0)
                goto L64
            L50:
                r4 = move-exception
                goto L65
            L52:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                com.nfcx.eg4.view.wifi.WifiConnectActivity r4 = r3.instance     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L5f
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
                com.nfcx.eg4.view.wifi.WifiConnectActivity r1 = r3.instance
                android.widget.Button r1 = com.nfcx.eg4.view.wifi.WifiConnectActivity.access$100(r1)
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.wifi.WifiConnectActivity.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.nfcx.eg4.view.wifi.WifiConnectActivity$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
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
}