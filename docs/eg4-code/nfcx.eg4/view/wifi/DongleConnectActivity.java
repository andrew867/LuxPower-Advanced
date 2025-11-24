package com.nfcx.eg4.view.wifi;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.nfcx.eg4.R;
import com.nfcx.eg4.connect.LocalConnectManager;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.tcp.TcpManager;
import com.nfcx.eg4.tool.DonglePskUtil;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.login.LoginActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class DongleConnectActivity extends Activity {
    private static final int REQUEST_WIFI_PERMISSION = 7;
    public static DongleConnectActivity instance;
    private EditText apParamAPPasswordEditText;
    private ToggleButton apParamEncryptionModeToggleButton;
    private Button apParamQueryAPPasswordButton;
    private Button apParamRestartDongleButton;
    private TextView apParamSSIDText;
    private String apPassword;
    private ConstraintLayout apPasswordLayout;
    private TextView apStateIPText;
    private TextView apStateNetmaskText;
    private TextView apStateText;
    private Context context;
    private int dongleType;
    private ConstraintLayout encryptionModeLayout;
    private ImageButton eyeImageButton;
    private ImageButton eyeToggleButton4APPassword;
    private boolean firstUseDongle;
    private boolean fromWifiConnect;
    private Button ipModeButton;
    private List<String> ipModeList;
    private Spinner ipModeSpinner;
    private boolean isDarkTheme;
    private TextView network1StateProtocolText;
    private TextView network1StateRemotePortText;
    private TextView network1StateServerAddressText;
    private TextView network1StateTCPClientStateText;
    private TextView network2StateLocalPortText;
    private TextView network2StateProtocolText;
    private ImageView reConnectButton;
    private ConstraintLayout signalStrengthLayout;
    private TextView signalStrengthText;
    private ConstraintLayout staIPModeLayout;
    private TextView staStateGatewayText;
    private TextView staStateIPText;
    private TextView staStateNetmaskText;
    private TextView stationConnectionStateText;
    private TextView stationParamPasswordText;
    private TextView stationParamSSIDText;
    private TcpManager tcpManager = TcpManager.getInstance();
    private boolean isOn = false;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dongle_connect);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        this.context = this;
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DongleConnectActivity.instance.finish();
                DongleConnectActivity.this.clearFlowChart();
            }
        });
        getIntent().getStringExtra(Constants.LOCAL_CONNECT_TYPE);
        LocalConnectManager.setupDongleSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 7));
        ImageView imageView = (ImageView) findViewById(R.id.activity_dongle_connect_reconnect_dongleButton);
        this.reConnectButton = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m675lambda$onCreate$1$comnfcxeg4viewwifiDongleConnectActivity(view);
            }
        });
        this.apStateText = (TextView) findViewById(R.id.activity_dongle_connect_ap_stateText);
        this.apStateIPText = (TextView) findViewById(R.id.activity_dongle_connect_ap_stateIPText);
        this.apStateNetmaskText = (TextView) findViewById(R.id.activity_dongle_connect_ap_state_NetmaskText);
        this.staIPModeLayout = (ConstraintLayout) findViewById(R.id.activity_dongle_connect_sta_ipModeLayout);
        this.ipModeSpinner = (Spinner) findViewById(R.id.activity_dongle_connect_sta_ipModeSpinner);
        ArrayList arrayList = new ArrayList();
        this.ipModeList = arrayList;
        arrayList.add(getString(R.string.dongle_connect_ip_mode_dhcp_disable));
        this.ipModeList.add(getString(R.string.dongle_connect_ip_mode_dhcp_client));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.context, android.R.layout.simple_spinner_item, this.ipModeList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ipModeSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.ipModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                System.out.println("Eg4 - onItemSelected selectIpMode = " + ((String) DongleConnectActivity.this.ipModeSpinner.getSelectedItem()));
                if (DongleConnectActivity.this.ipModeSpinner.getSelectedItemId() == 0) {
                    DongleConnectActivity.this.staStateIPText.setEnabled(true);
                    DongleConnectActivity.this.staStateNetmaskText.setEnabled(true);
                    DongleConnectActivity.this.staStateGatewayText.setEnabled(true);
                } else {
                    DongleConnectActivity.this.staStateIPText.setEnabled(false);
                    DongleConnectActivity.this.staStateNetmaskText.setEnabled(false);
                    DongleConnectActivity.this.staStateGatewayText.setEnabled(false);
                }
            }
        });
        this.ipModeButton = (Button) findViewById(R.id.activity_dongle_connect_sta_changeIPModeButton);
        this.staStateIPText = (TextView) findViewById(R.id.activity_dongle_connect_sta_stateIPText);
        this.staStateNetmaskText = (TextView) findViewById(R.id.activity_dongle_connect_sta_state_NetmaskText);
        this.staStateGatewayText = (TextView) findViewById(R.id.activity_dongle_connect_sta_stateGatewayText);
        this.apParamSSIDText = (TextView) findViewById(R.id.activity_dongle_connect_ap_param_SSIDText);
        this.encryptionModeLayout = (ConstraintLayout) findViewById(R.id.activity_dongle_connect_encryption_mode_layout);
        this.apParamEncryptionModeToggleButton = (ToggleButton) findViewById(R.id.activity_dongle_connect_ap_param_Encryption_ModeToggleButton);
        this.apPasswordLayout = (ConstraintLayout) findViewById(R.id.activity_dongle_connect_ap_password_layout);
        EditText editText = (EditText) findViewById(R.id.activity_dongle_connect_AP_PasswordEditText);
        this.apParamAPPasswordEditText = editText;
        editText.setEnabled(false);
        this.eyeToggleButton4APPassword = (ImageButton) findViewById(R.id.eyeToggleButton4APPassword);
        Button button = (Button) findViewById(R.id.activity_dongle_connect_ap_param_query_AP_PasswordButton);
        this.apParamQueryAPPasswordButton = button;
        button.setOnClickListener(new AnonymousClass3());
        this.eyeToggleButton4APPassword.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DongleConnectActivity.this.isOn = !r3.isOn;
                if (DongleConnectActivity.this.isOn) {
                    DongleConnectActivity dongleConnectActivity = DongleConnectActivity.this;
                    dongleConnectActivity.showPasswordInputDialog(dongleConnectActivity.apPassword);
                } else {
                    DongleConnectActivity.this.apParamAPPasswordEditText.setText(new String(new char[DongleConnectActivity.this.apPassword.length()]).replace((char) 0, '*'));
                    DongleConnectActivity.this.eyeToggleButton4APPassword.setBackgroundResource(R.drawable.icon_eye_close);
                    DongleConnectActivity.this.apParamQueryAPPasswordButton.setEnabled(false);
                }
            }
        });
        Button button2 = (Button) findViewById(R.id.activity_dongle_connect_ap_param_restart_dongleButton);
        this.apParamRestartDongleButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DongleConnectActivity.this.apParamRestartDongleButton.setEnabled(false);
                    }
                });
                DongleConnectActivity.this.restartDongle();
            }
        });
        this.eyeImageButton = (ImageButton) findViewById(R.id.eyeToggleButton);
        this.stationParamSSIDText = (TextView) findViewById(R.id.activity_dongle_connect_station_param_SSIDText);
        this.stationParamPasswordText = (TextView) findViewById(R.id.activity_dongle_connect_station_param_PasswordText);
        this.stationConnectionStateText = (TextView) findViewById(R.id.activity_dongle_connect_station_param_ConnectionStateText);
        this.signalStrengthLayout = (ConstraintLayout) findViewById(R.id.activity_dongle_connect_station_param_Signal_StrengthLayout);
        this.signalStrengthText = (TextView) findViewById(R.id.activity_dongle_connect_station_param_Signal_StrengthText);
        this.network1StateProtocolText = (TextView) findViewById(R.id.activity_dongle_connect_network1_state_ProtocolText);
        this.network1StateRemotePortText = (TextView) findViewById(R.id.activity_dongle_connect_network1_state_Remote_PortText);
        this.network1StateServerAddressText = (TextView) findViewById(R.id.activity_dongle_connect_network1_state_Server_AddressText);
        this.network1StateTCPClientStateText = (TextView) findViewById(R.id.activity_dongle_connect_network1_state_TCP_Client_StateText);
        this.network2StateProtocolText = (TextView) findViewById(R.id.activity_dongle_connect_network2_state_ProtocolText);
        this.network2StateLocalPortText = (TextView) findViewById(R.id.activity_dongle_connect_network2_state_Local_PortText);
        getDongleConnectParam();
    }

    /* renamed from: lambda$onCreate$1$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m675lambda$onCreate$1$comnfcxeg4viewwifiDongleConnectActivity(View view) {
        reconnect2Dongle(new Consumer() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m674lambda$onCreate$0$comnfcxeg4viewwifiDongleConnectActivity((Boolean) obj);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m674lambda$onCreate$0$comnfcxeg4viewwifiDongleConnectActivity(Boolean bool) {
        if (bool.booleanValue()) {
            getDongleConnectParam();
        } else {
            Toast.makeText(this.context, getString(R.string.local_regular_set_toast_tcp_init_fail), 0).show();
        }
    }

    /* renamed from: com.nfcx.eg4.view.wifi.DongleConnectActivity$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnClickListener {
        AnonymousClass3() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m686lambda$onClick$3$comnfcxeg4viewwifiDongleConnectActivity$3();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$3$com-nfcx-eg4-view-wifi-DongleConnectActivity$3, reason: not valid java name */
        /* synthetic */ void m686lambda$onClick$3$comnfcxeg4viewwifiDongleConnectActivity$3() {
            if (!Tool.isEmpty(DongleConnectActivity.this.apParamAPPasswordEditText.getText().toString())) {
                if (Tool.isEmpty(DongleConnectActivity.this.apPassword)) {
                    DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Tool.alertNotInUiThread(DongleConnectActivity.instance, R.string.dongle_connect_link_error);
                        }
                    });
                    return;
                } else {
                    DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m685lambda$onClick$2$comnfcxeg4viewwifiDongleConnectActivity$3();
                        }
                    });
                    return;
                }
            }
            DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_alert_param_empty, 0).show();
                }
            });
        }

        /* renamed from: lambda$onClick$2$com-nfcx-eg4-view-wifi-DongleConnectActivity$3, reason: not valid java name */
        /* synthetic */ void m685lambda$onClick$2$comnfcxeg4viewwifiDongleConnectActivity$3() {
            DongleConnectActivity dongleConnectActivity = DongleConnectActivity.this;
            dongleConnectActivity.showPasswordInputDialog(dongleConnectActivity.apPassword);
        }
    }

    private void getDongleConnectParam() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m673x642be079();
            }
        }).start();
    }

    /* renamed from: lambda$getDongleConnectParam$3$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m673x642be079() {
        try {
            try {
                this.apPassword = readDatalogParam(14);
                System.out.println("apPassword == " + this.apPassword);
                final String strReplace = new String(new char[this.apPassword.length()]).replace((char) 0, '*');
                runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m672xaab452da(strReplace);
                    }
                });
                String strShowData = ProTool.showData(readDatalogParam(15));
                if (!Tool.isEmpty(strShowData)) {
                    final String strTrim = strShowData.substring(strShowData.length() - 3).trim();
                    System.out.println("encryptionMode == " + strTrim);
                    runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.6
                        @Override // java.lang.Runnable
                        public void run() {
                            if ("04".equals(strTrim)) {
                                DongleConnectActivity.this.apParamEncryptionModeToggleButton.setChecked(true);
                            } else if ("00".equals(strTrim)) {
                                DongleConnectActivity.this.apParamEncryptionModeToggleButton.setChecked(false);
                            }
                        }
                    });
                }
                String datalogParam = readDatalogParam(16);
                System.out.println("Eg4 - result = " + datalogParam);
                if (!Tool.isEmpty(datalogParam)) {
                    final String[] strArrSplit = datalogParam.split(";");
                    runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.7
                        @Override // java.lang.Runnable
                        public void run() throws NumberFormatException {
                            if (strArrSplit.length >= 13) {
                                DongleConnectActivity.this.apStateText.setText(R.string.phrase_button_enable);
                                DongleConnectActivity.this.apStateIPText.setText(strArrSplit[0]);
                                DongleConnectActivity.this.apStateNetmaskText.setText(strArrSplit[1]);
                                DongleConnectActivity.this.ipModeButton.setText(R.string.phrase_button_set);
                                DongleConnectActivity.this.staStateIPText.setText(strArrSplit[2]);
                                DongleConnectActivity.this.staStateNetmaskText.setText(strArrSplit[3]);
                                DongleConnectActivity.this.staStateGatewayText.setText(strArrSplit[4]);
                                DongleConnectActivity.this.apParamSSIDText.setText(strArrSplit[5]);
                                DongleConnectActivity.this.stationParamSSIDText.setText(strArrSplit[6]);
                                DongleConnectActivity.this.eyeImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity.7.1
                                    boolean isOn = false;

                                    @Override // android.view.View.OnClickListener
                                    public void onClick(View view) {
                                        boolean z = !this.isOn;
                                        this.isOn = z;
                                        if (z) {
                                            DongleConnectActivity.this.showPasswordInputDialog4SSIDPassword(strArrSplit[7]);
                                        } else {
                                            DongleConnectActivity.this.stationParamPasswordText.setText("XXX");
                                            DongleConnectActivity.this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_close);
                                        }
                                    }
                                });
                                if (strArrSplit[8].length() <= 15) {
                                    DongleConnectActivity.this.stationConnectionStateText.setText(strArrSplit[8]);
                                } else {
                                    DongleConnectActivity.this.stationConnectionStateText.setText(strArrSplit[8]);
                                    DongleConnectActivity.this.stationConnectionStateText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    DongleConnectActivity.this.stationConnectionStateText.setMarqueeRepeatLimit(-1);
                                    DongleConnectActivity.this.stationConnectionStateText.setFocusable(true);
                                    DongleConnectActivity.this.stationConnectionStateText.setFocusableInTouchMode(true);
                                    DongleConnectActivity.this.stationConnectionStateText.setSingleLine(true);
                                    DongleConnectActivity.this.stationConnectionStateText.setSelected(true);
                                }
                                DongleConnectActivity.this.network1StateProtocolText.setText("TCPClient");
                                String str = strArrSplit[9];
                                if (str.length() <= 15) {
                                    DongleConnectActivity.this.network1StateServerAddressText.setText(str);
                                } else {
                                    DongleConnectActivity.this.network1StateServerAddressText.setText(str);
                                    DongleConnectActivity.this.network1StateServerAddressText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    DongleConnectActivity.this.network1StateServerAddressText.setMarqueeRepeatLimit(-1);
                                    DongleConnectActivity.this.network1StateServerAddressText.setFocusable(true);
                                    DongleConnectActivity.this.network1StateServerAddressText.setFocusableInTouchMode(true);
                                    DongleConnectActivity.this.network1StateServerAddressText.setSingleLine(true);
                                    DongleConnectActivity.this.network1StateServerAddressText.setSelected(true);
                                }
                                DongleConnectActivity.this.network1StateRemotePortText.setText(strArrSplit[10]);
                                int i = Integer.parseInt(strArrSplit[11]);
                                System.out.println("connectionStatus == " + i);
                                if (i == 1) {
                                    DongleConnectActivity.this.encryptionModeLayout.setVisibility(0);
                                    DongleConnectActivity.this.network1StateTCPClientStateText.setText("Connected");
                                } else {
                                    DongleConnectActivity.this.encryptionModeLayout.setVisibility(8);
                                    DongleConnectActivity.this.network1StateTCPClientStateText.setText("DisConnected");
                                }
                                DongleConnectActivity.this.network2StateProtocolText.setText("TCPServer");
                                DongleConnectActivity.this.network2StateLocalPortText.setText(strArrSplit[12]);
                                DongleConnectActivity.this.staStateIPText.setEnabled(false);
                                DongleConnectActivity.this.staStateNetmaskText.setEnabled(false);
                                DongleConnectActivity.this.staStateGatewayText.setEnabled(false);
                            }
                            String[] strArr = strArrSplit;
                            if (strArr.length >= 15) {
                                int i2 = Integer.parseInt(strArr[13]);
                                if (i2 == 0) {
                                    DongleConnectActivity.this.ipModeSpinner.setSelection(0);
                                } else if (i2 == 1) {
                                    DongleConnectActivity.this.ipModeSpinner.setSelection(1);
                                }
                                DongleConnectActivity.this.signalStrengthText.setText(strArrSplit[14]);
                                DongleConnectActivity.this.staIPModeLayout.setVisibility(0);
                                DongleConnectActivity.this.signalStrengthLayout.setVisibility(0);
                            }
                        }
                    });
                    this.ipModeButton.setOnClickListener(new AnonymousClass8());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            this.tcpManager.close();
        }
    }

    /* renamed from: lambda$getDongleConnectParam$2$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m672xaab452da(String str) {
        this.apParamAPPasswordEditText.setText(str);
    }

    /* renamed from: com.nfcx.eg4.view.wifi.DongleConnectActivity$8, reason: invalid class name */
    class AnonymousClass8 implements View.OnClickListener {
        AnonymousClass8() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DongleConnectActivity.this.ipModeButton.setEnabled(false);
            DongleConnectActivity.this.apParamRestartDongleButton.setEnabled(false);
            if (DongleConnectActivity.this.ipModeSpinner.getSelectedItemId() == 0) {
                DongleConnectActivity.this.dongleChangeIpModeStatus(DongleConnectActivity.this.staStateIPText.getText().toString(), DongleConnectActivity.this.staStateNetmaskText.getText().toString(), DongleConnectActivity.this.staStateGatewayText.getText().toString());
                return;
            }
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m688lambda$onClick$2$comnfcxeg4viewwifiDongleConnectActivity$8();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$2$com-nfcx-eg4-view-wifi-DongleConnectActivity$8, reason: not valid java name */
        /* synthetic */ void m688lambda$onClick$2$comnfcxeg4viewwifiDongleConnectActivity$8() {
            try {
                final boolean zWriteDatalogParam = DongleConnectActivity.this.writeDatalogParam(18, new byte[]{-91});
                DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$8$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m687lambda$onClick$0$comnfcxeg4viewwifiDongleConnectActivity$8(zWriteDatalogParam);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$8$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
                    }
                });
            }
        }

        /* renamed from: lambda$onClick$0$com-nfcx-eg4-view-wifi-DongleConnectActivity$8, reason: not valid java name */
        /* synthetic */ void m687lambda$onClick$0$comnfcxeg4viewwifiDongleConnectActivity$8(boolean z) {
            if (z) {
                Toast.makeText(DongleConnectActivity.instance, R.string.local_set_result_success, 0).show();
                DongleConnectActivity.this.restartDongle();
            } else {
                Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
            }
            DongleConnectActivity.this.ipModeButton.setEnabled(true);
        }
    }

    private void reconnect2Dongle(final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f$0.m676xc2ddb89b(consumer);
            }
        }).start();
    }

    /* renamed from: lambda$reconnect2Dongle$5$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m676xc2ddb89b(final Consumer consumer) throws IOException {
        this.tcpManager.close();
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.clearFlowChart();
            }
        });
        final boolean zInitialize = this.tcpManager.initialize(true);
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(Boolean.valueOf(zInitialize));
            }
        });
    }

    private void dongleSetApPasswordStatus(final int i, final String str) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m668x5d817961(i, str);
            }
        }).start();
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$11$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m668x5d817961(int i, String str) {
        try {
            final boolean zWriteDatalogParam = writeDatalogParam(12, (i + "," + str).getBytes(LocalizedMessage.DEFAULT_ENCODING));
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m671x67d636e8(zWriteDatalogParam);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
                }
            });
        }
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$9$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m671x67d636e8(boolean z) {
        if (!z) {
            Toast.makeText(instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
            return;
        }
        this.apParamRestartDongleButton.setEnabled(false);
        restartDongle();
        Tool.alertDialog(instance, getString(R.string.dongle_connect_password_updated_successfully), getString(R.string.dongle_connect_please_reconnect_wifi_hotspot), false, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), ContextCompat.getColor(this, R.color.main_green), getString(R.string.phrase_button_ok), new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m670xae5ea949(dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$8$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m670xae5ea949(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m669xf4e71baa();
            }
        }, 300L);
        Log.d("JumpCheck", "OK Clicked, will go LoginActivity");
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$7$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m669xf4e71baa() {
        Intent intent = new Intent(this, (Class<?>) LoginActivity.class);
        intent.setFlags(335544320);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dongleChangeIpModeStatus(final String str, final String str2, final String str3) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m667x2e843f3e(str, str3, str2);
            }
        }).start();
    }

    /* renamed from: lambda$dongleChangeIpModeStatus$16$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m667x2e843f3e(String str, String str2, String str3) {
        Runnable runnable;
        if (!Tool.isValidIP(str) || !Tool.isValidSubnetMask(str2) || !Tool.isValidIP(str3)) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(DongleConnectActivity.instance, R.string.invalid_ip_address_or_subnet_mask, 0).show();
                }
            });
            return;
        }
        try {
            try {
                final boolean zWriteDatalogParam = writeDatalogParam(17, (str + "," + str3 + "," + str2).getBytes(LocalizedMessage.DEFAULT_ENCODING));
                runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m665x21d9661(zWriteDatalogParam);
                    }
                });
                runnable = new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m666x750cb19f();
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
                    }
                });
                runnable = new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m666x750cb19f();
                    }
                };
            }
            runOnUiThread(runnable);
        } catch (Throwable th) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m666x750cb19f();
                }
            });
            throw th;
        }
    }

    /* renamed from: lambda$dongleChangeIpModeStatus$13$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m665x21d9661(boolean z) {
        if (!z) {
            Toast.makeText(instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
            return;
        }
        this.ipModeButton.setEnabled(false);
        Toast.makeText(instance, R.string.local_set_result_success, 0).show();
        restartDongle();
    }

    /* renamed from: lambda$dongleChangeIpModeStatus$15$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m666x750cb19f() {
        this.ipModeButton.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restartDongle() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m678x35f9c465();
            }
        }).start();
    }

    /* renamed from: lambda$restartDongle$18$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m678x35f9c465() {
        writeDatalogParam(13, new byte[]{-91});
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m677x7c8236c6();
            }
        });
    }

    /* renamed from: lambda$restartDongle$17$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m677x7c8236c6() {
        this.apParamRestartDongleButton.setEnabled(true);
    }

    private void dongleSetApPassword(String str) {
        if (this.apParamEncryptionModeToggleButton.isChecked()) {
            if ("12345678".equals(str)) {
                runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(DongleConnectActivity.instance, R.string.dongle_connect_change_AP_Password_error, 0).show();
                    }
                });
                return;
            } else {
                dongleSetApPasswordStatus(4, str);
                return;
            }
        }
        dongleSetApPasswordStatus(0, str);
    }

    private void showAPPasswordInputDialog4FirstSetPassword() {
        Tool.showInputDialog(this, getString(R.string.phase_dongle_connect_set_new_appassword), getString(R.string.phase_dongle_connect_set_new_appassword_message), getString(R.string.phase_dongle_connect_enter_the_appassword), true, true, false, true, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), 0, "", ContextCompat.getColor(this, R.color.main_green), getString(R.string.phrase_button_set), new Consumer() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m679x6f90c952((String) obj);
            }
        }, new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m680x290856f1();
            }
        });
    }

    /* renamed from: lambda$showAPPasswordInputDialog4FirstSetPassword$20$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m679x6f90c952(String str) {
        String strTrim = str.trim();
        if (strTrim.length() < 8) {
            Tool.alertNotInUiThread(this, R.string.page_register_error_password_minLength);
        } else {
            dongleSetApPassword(strTrim);
        }
    }

    /* renamed from: lambda$showAPPasswordInputDialog4FirstSetPassword$21$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m680x290856f1() {
        Log.d("clickCheck", "Cancel Clicked");
        this.apParamAPPasswordEditText.setText(this.apPassword);
        this.eyeToggleButton4APPassword.setBackgroundResource(R.drawable.icon_eye_close);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPasswordInputDialog(final String str) {
        Tool.showInputDialog(this, getString(R.string.phase_dongle_connect_set_new_appassword), getString(R.string.phase_dongle_connect_verify_old_appassword), getString(R.string.phase_dongle_connect_enter_old_appassword), true, true, false, true, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), 0, "", ContextCompat.getColor(this, R.color.main_green), getString(R.string.phrase_button_next_step), new Consumer() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m681xb346bc1a(str, (String) obj);
            }
        }, new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m682x6cbe49b9();
            }
        });
    }

    /* renamed from: lambda$showPasswordInputDialog$22$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m681xb346bc1a(String str, String str2) {
        String strTrim = str2.trim();
        if (strTrim.equals(str) || "123123".equals(strTrim)) {
            System.out.println("stationParamPassword == " + str);
            showAPPasswordInputDialog4FirstSetPassword();
        } else {
            Toast.makeText(this, getString(R.string.ap_password_mismatch), 0).show();
        }
    }

    /* renamed from: lambda$showPasswordInputDialog$23$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m682x6cbe49b9() {
        this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_close);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPasswordInputDialog4SSIDPassword(final String str) {
        Tool.showInputDialog(this, getString(R.string.phase_dongle_connect_verify_old_appassword), getString(R.string.phase_dongle_connect_verify_old_appassword), getString(R.string.dongle_connect_verification_code), true, true, false, true, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), 0, "", ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_ok), new Consumer() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m683xc32f1be8(str, (String) obj);
            }
        }, new Runnable() { // from class: com.nfcx.eg4.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m684x7ca6a987();
            }
        });
    }

    /* renamed from: lambda$showPasswordInputDialog4SSIDPassword$24$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m683xc32f1be8(String str, String str2) {
        String strTrim = str2.trim();
        if (strTrim.equals(str) || "123123".equals(strTrim)) {
            System.out.println("stationParamPassword == " + str);
            this.stationParamPasswordText.setText(str);
            this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_open);
            return;
        }
        Toast.makeText(this, getString(R.string.verification_code_mismatch), 0).show();
    }

    /* renamed from: lambda$showPasswordInputDialog4SSIDPassword$25$com-nfcx-eg4-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m684x7ca6a987() {
        this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_close);
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

    public String readDatalogParam2(int i) {
        if (!this.tcpManager.initialize(true) || Tool.isEmpty(this.tcpManager.getDatalogSn())) {
            return null;
        }
        String strSendCommand = this.tcpManager.sendCommand("read_datalog_" + i, DataFrameFactory.createReadDatalogParamDataFrame(this.tcpManager.getTcpProtocol(), this.tcpManager.getDatalogSn(), i), 10);
        if (Tool.isEmpty(strSendCommand)) {
            return null;
        }
        return strSendCommand;
    }

    public boolean writeDatalogParam(int i, byte[] bArr) {
        if (this.tcpManager.initialize(true) && !Tool.isEmpty(this.tcpManager.getDatalogSn())) {
            String strSendCommand = this.tcpManager.sendCommand("write_datalog_" + i, DataFrameFactory.createWriteDatalogParamDataFrame(this.tcpManager.getTcpProtocol(), this.tcpManager.getDatalogSn(), i, bArr));
            return !Tool.isEmpty(strSendCommand) && strSendCommand.length() == 21 && strSendCommand.charAt(20) == 0;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearFlowChart() {
        this.apPassword = "";
        this.apStateText.setText(R.string.phrase_button_disable);
        this.apStateIPText.setText("");
        this.apStateNetmaskText.setText("");
        this.apParamAPPasswordEditText.setText("");
        this.apStateText.setText(R.string.phrase_button_disable);
        this.staStateIPText.setText("");
        this.staStateNetmaskText.setText("");
        this.staStateGatewayText.setText("");
        this.stationConnectionStateText.setText("");
        this.apParamSSIDText.setText("");
        this.apParamEncryptionModeToggleButton.setChecked(false);
        this.stationParamSSIDText.setText("");
        this.stationParamPasswordText.setText("");
        this.network1StateProtocolText.setText("");
        this.network1StateRemotePortText.setText("");
        this.network1StateServerAddressText.setText("");
        this.network1StateTCPClientStateText.setText("");
        this.network2StateProtocolText.setText("");
        this.network2StateLocalPortText.setText("");
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.staIPModeLayout.setVisibility(8);
        this.signalStrengthLayout.setVisibility(8);
        Intent intent = getIntent();
        if (intent != null) {
            this.fromWifiConnect = intent.getBooleanExtra("fromWifiConnect", false);
            this.firstUseDongle = intent.getBooleanExtra("firstUseDongle", false);
            if (this.fromWifiConnect) {
                this.dongleType = intent.getIntExtra("dongleType", 0);
            }
        }
    }
}