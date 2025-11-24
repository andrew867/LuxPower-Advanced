package com.lux.luxcloud.view.wifi;

import android.app.Activity;
import android.app.UiModeManager;
import android.bluetooth.BluetoothDevice;
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
import com.alibaba.fastjson2.JSONB;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnect;
import com.lux.luxcloud.connect.LocalConnectManager;
import com.lux.luxcloud.connect.ble.BleAction;
import com.lux.luxcloud.connect.ble.BluetoothLocalConnect;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.tool.DonglePskUtil;
import com.lux.luxcloud.tool.ProTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.login.LoginActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class DongleConnectActivity extends Activity implements BleAction {
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
    private boolean isOn = false;
    private LocalConnect localConnect;
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
    private EditText staStateGatewayText;
    private EditText staStateIPText;
    private EditText staStateNetmaskText;
    private TextView stationConnectionStateText;
    private TextView stationParamPasswordText;
    private TextView stationParamSSIDText;

    @Override // com.lux.luxcloud.connect.ble.BleAction
    public void bleConnectClosed() {
    }

    @Override // com.lux.luxcloud.connect.ble.BleAction
    public void bleConnected() {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dongle_connect);
        instance = this;
        this.context = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DongleConnectActivity.instance.finish();
                DongleConnectActivity.this.clearFlowChart();
            }
        });
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(Constants.LOCAL_CONNECT_TYPE);
        if (Constants.LOCAL_CONNECT_TYPE_BLUETOOTH.equals(stringExtra)) {
            LocalConnectManager.updateBluetoothLocalConnect(new BluetoothLocalConnect(this, (BluetoothDevice) intent.getParcelableExtra(Constants.KEY_BLE_DEVICE)));
            this.localConnect = LocalConnectManager.getLocalConnect(stringExtra);
        } else {
            LocalConnectManager.setupDongleSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 7));
            this.localConnect = LocalConnectManager.getLocalConnect(stringExtra);
        }
        ImageView imageView = (ImageView) findViewById(R.id.activity_dongle_connect_reconnect_dongleButton);
        this.reConnectButton = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m813x51bd5e6e(view);
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
        this.ipModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                System.out.println("LuxPower - onItemSelected selectIpMode = " + ((String) DongleConnectActivity.this.ipModeSpinner.getSelectedItem()));
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
        this.staStateIPText = (EditText) findViewById(R.id.activity_dongle_connect_sta_stateIPText);
        this.staStateNetmaskText = (EditText) findViewById(R.id.activity_dongle_connect_sta_state_NetmaskText);
        this.staStateGatewayText = (EditText) findViewById(R.id.activity_dongle_connect_sta_stateGatewayText);
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
        this.eyeToggleButton4APPassword.setOnClickListener(new AnonymousClass4());
        Button button2 = (Button) findViewById(R.id.activity_dongle_connect_ap_param_restart_dongleButton);
        this.apParamRestartDongleButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.5.1
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

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m813x51bd5e6e(View view) {
        reconnect2Dongle(new Consumer() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m812x6013b84f((Boolean) obj);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m812x6013b84f(Boolean bool) {
        if (bool.booleanValue()) {
            getDongleConnectParam();
        } else {
            Toast.makeText(this.context, getString(R.string.local_regular_set_toast_tcp_init_fail), 0).show();
        }
    }

    /* renamed from: com.lux.luxcloud.view.wifi.DongleConnectActivity$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnClickListener {
        AnonymousClass3() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m824x77a181a7();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$3$com-lux-luxcloud-view-wifi-DongleConnectActivity$3, reason: not valid java name */
        /* synthetic */ void m824x77a181a7() {
            if (!Tool.isEmpty(DongleConnectActivity.this.apParamAPPasswordEditText.getText().toString())) {
                if (Tool.isEmpty(DongleConnectActivity.this.apPassword)) {
                    DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Tool.alertNotInUiThread(DongleConnectActivity.instance, R.string.dongle_connect_link_error);
                        }
                    });
                    return;
                } else {
                    DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m823x49c8e748();
                        }
                    });
                    return;
                }
            }
            DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_alert_param_empty, 0).show();
                }
            });
        }

        /* renamed from: lambda$onClick$2$com-lux-luxcloud-view-wifi-DongleConnectActivity$3, reason: not valid java name */
        /* synthetic */ void m823x49c8e748() {
            DongleConnectActivity dongleConnectActivity = DongleConnectActivity.this;
            dongleConnectActivity.showPasswordInputDialog(dongleConnectActivity.apPassword);
        }
    }

    /* renamed from: com.lux.luxcloud.view.wifi.DongleConnectActivity$4, reason: invalid class name */
    class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DongleConnectActivity.this.isOn = !r3.isOn;
            if (!Tool.isEmpty(DongleConnectActivity.this.apPassword)) {
                if (DongleConnectActivity.this.isOn) {
                    DongleConnectActivity dongleConnectActivity = DongleConnectActivity.this;
                    dongleConnectActivity.showPasswordInputDialog(dongleConnectActivity.apPassword);
                    return;
                } else {
                    DongleConnectActivity.this.apParamAPPasswordEditText.setText(new String(new char[DongleConnectActivity.this.apPassword.length()]).replace((char) 0, '*'));
                    DongleConnectActivity.this.eyeToggleButton4APPassword.setBackgroundResource(R.drawable.icon_eye_close);
                    DongleConnectActivity.this.apParamQueryAPPasswordButton.setEnabled(false);
                    return;
                }
            }
            DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Tool.alertNotInUiThread(DongleConnectActivity.instance, R.string.dongle_connect_link_error);
                }
            });
        }
    }

    private void reconnect2Dongle(final Consumer<Boolean> consumer) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m814x1b8b10af(consumer);
            }
        }).start();
    }

    /* renamed from: lambda$reconnect2Dongle$3$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m814x1b8b10af(final Consumer consumer) {
        this.localConnect.close();
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.clearFlowChart();
            }
        });
        final boolean zInitialize = this.localConnect.initialize(true);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(Boolean.valueOf(zInitialize));
            }
        });
    }

    private void getDongleConnectParam() {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m811x5e4cb209();
            }
        }).start();
    }

    /* renamed from: lambda$getDongleConnectParam$5$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m811x5e4cb209() {
        try {
            try {
                this.apPassword = this.localConnect.readDatalogParam(14);
                System.out.println("LuxPower - apPassword = " + this.apPassword);
                if (!Tool.isEmpty(this.apPassword)) {
                    final String strReplace = new String(new char[this.apPassword.length()]).replace((char) 0, '*');
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m810x6ca30bea(strReplace);
                        }
                    });
                }
                String strShowData = ProTool.showData(this.localConnect.readDatalogParam(15));
                if (!Tool.isEmpty(strShowData)) {
                    final String strTrim = strShowData.substring(strShowData.length() - 3).trim();
                    System.out.println("encryptionMode == " + strTrim);
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.6
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
                String datalogParam = this.localConnect.readDatalogParam(16);
                System.out.println("LuxPower - result = " + datalogParam);
                if (!Tool.isEmpty(datalogParam)) {
                    final String[] strArrSplit = datalogParam.split(";");
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.7
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
                                DongleConnectActivity.this.eyeImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity.7.1
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
            this.localConnect.close();
        }
    }

    /* renamed from: lambda$getDongleConnectParam$4$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m810x6ca30bea(String str) {
        this.apParamAPPasswordEditText.setText(str);
    }

    /* renamed from: com.lux.luxcloud.view.wifi.DongleConnectActivity$8, reason: invalid class name */
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
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m826x49c8e74d();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$2$com-lux-luxcloud-view-wifi-DongleConnectActivity$8, reason: not valid java name */
        /* synthetic */ void m826x49c8e74d() {
            try {
                final boolean zWriteDatalogParam = DongleConnectActivity.this.localConnect.writeDatalogParam(18, new byte[]{JSONB.Constants.BC_OBJECT_END});
                DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$8$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m825xee17b28f(zWriteDatalogParam);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                DongleConnectActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$8$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
                    }
                });
            }
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-wifi-DongleConnectActivity$8, reason: not valid java name */
        /* synthetic */ void m825xee17b28f(boolean z) {
            if (z) {
                Toast.makeText(DongleConnectActivity.instance, R.string.local_set_result_success, 0).show();
                DongleConnectActivity.this.restartDongle();
            } else {
                Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
            }
            DongleConnectActivity.this.ipModeButton.setEnabled(true);
        }
    }

    private void dongleSetApPasswordStatus(final int i, final String str) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m806x2c884ab3(i, str);
            }
        }).start();
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$11$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m806x2c884ab3(int i, String str) {
        try {
            final boolean zWriteDatalogParam = this.localConnect.writeDatalogParam(12, (i + "," + str).getBytes(LocalizedMessage.DEFAULT_ENCODING));
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m809x5ba4ecba(zWriteDatalogParam);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
                }
            });
        }
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$9$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m809x5ba4ecba(boolean z) {
        if (!z) {
            Toast.makeText(instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
            return;
        }
        this.apParamRestartDongleButton.setEnabled(false);
        restartDongle();
        Tool.alertDialog(instance, getString(R.string.dongle_connect_password_updated_successfully), getString(R.string.dongle_connect_please_reconnect_wifi_hotspot), false, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), ContextCompat.getColor(this, R.color.main_green), getString(R.string.phrase_button_ok), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m808x69fb469b(dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$8$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m808x69fb469b(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m807x7851a07c();
            }
        }, 300L);
        Log.d("JumpCheck", "OK Clicked, will go LoginActivity");
    }

    /* renamed from: lambda$dongleSetApPasswordStatus$7$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m807x7851a07c() {
        Intent intent = new Intent(this, (Class<?>) LoginActivity.class);
        intent.setFlags(335544320);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dongleChangeIpModeStatus(final String str, final String str2, final String str3) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m805x90cada10(str, str3, str2);
            }
        }).start();
    }

    /* renamed from: lambda$dongleChangeIpModeStatus$16$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m805x90cada10(String str, String str2, String str3) {
        Runnable runnable;
        if (!Tool.isValidIP(str) || !Tool.isValidSubnetMask(str2) || !Tool.isValidIP(str3)) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(DongleConnectActivity.instance, R.string.invalid_ip_address_or_subnet_mask, 0).show();
                }
            });
            return;
        }
        try {
            try {
                final boolean zWriteDatalogParam = this.localConnect.writeDatalogParam(17, (str + "," + str3 + "," + str2).getBytes(LocalizedMessage.DEFAULT_ENCODING));
                runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m803xbbcde7b3(zWriteDatalogParam);
                    }
                });
                runnable = new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m804x9f2133f1();
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(DongleConnectActivity.instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
                    }
                });
                runnable = new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m804x9f2133f1();
                    }
                };
            }
            runOnUiThread(runnable);
        } catch (Throwable th) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m804x9f2133f1();
                }
            });
            throw th;
        }
    }

    /* renamed from: lambda$dongleChangeIpModeStatus$13$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m803xbbcde7b3(boolean z) {
        if (!z) {
            Toast.makeText(instance, R.string.page_maintain_remote_set_result_command_not_send, 0).show();
            return;
        }
        this.ipModeButton.setEnabled(false);
        Toast.makeText(instance, R.string.local_set_result_success, 0).show();
        restartDongle();
    }

    /* renamed from: lambda$dongleChangeIpModeStatus$15$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m804x9f2133f1() {
        this.ipModeButton.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restartDongle() {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m816x2a1b73b7();
            }
        }).start();
    }

    /* renamed from: lambda$restartDongle$18$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m816x2a1b73b7() {
        this.localConnect.writeDatalogParam(13, new byte[]{JSONB.Constants.BC_OBJECT_END});
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m815x3871cd98();
            }
        });
    }

    /* renamed from: lambda$restartDongle$17$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m815x3871cd98() {
        this.apParamRestartDongleButton.setEnabled(true);
    }

    private void dongleSetApPassword(String str) {
        if (this.apParamEncryptionModeToggleButton.isChecked()) {
            if ("12345678".equals(str)) {
                runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda5
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
        Tool.showInputDialog(this, getString(R.string.phase_dongle_connect_set_new_appassword), getString(R.string.phase_dongle_connect_set_new_appassword_message), getString(R.string.phase_dongle_connect_enter_the_appassword), true, true, false, true, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), 0, "", ContextCompat.getColor(this, R.color.main_green), getString(R.string.phrase_button_set), new Consumer() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m817x59c3a24((String) obj);
            }
        }, new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m818xf745e043();
            }
        });
    }

    /* renamed from: lambda$showAPPasswordInputDialog4FirstSetPassword$20$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m817x59c3a24(String str) {
        String strTrim = str.trim();
        if (strTrim.length() < 8) {
            Tool.alertNotInUiThread(this, R.string.page_register_error_password_minLength);
        } else {
            dongleSetApPassword(strTrim);
        }
    }

    /* renamed from: lambda$showAPPasswordInputDialog4FirstSetPassword$21$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m818xf745e043() {
        Log.d("clickCheck", "Cancel Clicked");
        this.apParamAPPasswordEditText.setText(this.apPassword);
        this.eyeToggleButton4APPassword.setBackgroundResource(R.drawable.icon_eye_close);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPasswordInputDialog(final String str) {
        Tool.showInputDialog(this, getString(R.string.phase_dongle_connect_set_new_appassword), getString(R.string.phase_dongle_connect_verify_old_appassword), getString(R.string.phase_dongle_connect_enter_old_appassword), true, true, false, true, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), 0, "", ContextCompat.getColor(this, R.color.main_green), getString(R.string.phrase_button_next_step), new Consumer() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m819xc40b88ec(str, (String) obj);
            }
        }, new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m820xb5b52f0b();
            }
        });
    }

    /* renamed from: lambda$showPasswordInputDialog$22$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m819xc40b88ec(String str, String str2) {
        String strTrim = str2.trim();
        if (strTrim.equals(str) || "123123".equals(strTrim)) {
            System.out.println("stationParamPassword == " + str);
            showAPPasswordInputDialog4FirstSetPassword();
        } else {
            Toast.makeText(this, getString(R.string.ap_password_mismatch), 0).show();
        }
    }

    /* renamed from: lambda$showPasswordInputDialog$23$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m820xb5b52f0b() {
        this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_close);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPasswordInputDialog4SSIDPassword(final String str) {
        Tool.showInputDialog(this, getString(R.string.phase_dongle_connect_verify_old_appassword), getString(R.string.phase_dongle_connect_verify_old_appassword), getString(R.string.dongle_connect_verification_code), true, true, false, true, true, ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_cancel), 0, "", ContextCompat.getColor(this, R.color.main_green), getString(R.string.phase_ok), new Consumer() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m821xe60351ba(str, (String) obj);
            }
        }, new Runnable() { // from class: com.lux.luxcloud.view.wifi.DongleConnectActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m822xd7acf7d9();
            }
        });
    }

    /* renamed from: lambda$showPasswordInputDialog4SSIDPassword$24$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m821xe60351ba(String str, String str2) {
        String strTrim = str2.trim();
        if (strTrim.equals(str) || "123123".equals(strTrim)) {
            System.out.println("stationParamPassword == " + str);
            this.stationParamPasswordText.setText(str);
            this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_open);
            return;
        }
        Toast.makeText(this, getString(R.string.verification_code_mismatch), 0).show();
    }

    /* renamed from: lambda$showPasswordInputDialog4SSIDPassword$25$com-lux-luxcloud-view-wifi-DongleConnectActivity, reason: not valid java name */
    /* synthetic */ void m822xd7acf7d9() {
        this.eyeImageButton.setBackgroundResource(R.drawable.icon_eye_close);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearFlowChart() {
        this.apPassword = "";
        this.apStateText.setText(R.string.phrase_button_disable);
        this.apStateIPText.setText("");
        this.apStateNetmaskText.setText("");
        this.apParamAPPasswordEditText.setText("");
        this.eyeToggleButton4APPassword.setBackgroundResource(R.drawable.icon_eye_close);
        this.apStateText.setText(R.string.phrase_button_disable);
        this.staStateIPText.setText("");
        this.staStateNetmaskText.setText("");
        this.staStateGatewayText.setText("");
        this.stationConnectionStateText.setText("");
        this.signalStrengthText.setText("");
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
            boolean booleanExtra = intent.getBooleanExtra("firstUseDongle", false);
            this.firstUseDongle = booleanExtra;
            if (booleanExtra) {
                this.apParamEncryptionModeToggleButton.setEnabled(false);
                this.apParamRestartDongleButton.setEnabled(false);
                this.ipModeButton.setEnabled(false);
            }
        }
    }
}