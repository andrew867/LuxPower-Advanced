package com.lux.luxcloud.view.ble;

import android.app.Activity;
import android.app.UiModeManager;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnect;
import com.lux.luxcloud.connect.LocalConnectManager;
import com.lux.luxcloud.connect.ble.BleAction;
import com.lux.luxcloud.connect.ble.BluetoothLocalConnect;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.protocol.tcp.DataFrameFactory;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.lux.luxcloud.tool.ProTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.wifi.EWifiRemoteUpdateActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class BlufiActivity extends Activity implements BleAction {
    public static BlufiActivity instance;
    private byte[] dataReceived;
    private String datalogParam;
    private String datalogSn;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private String firmwareVersion;
    private boolean isDarkTheme;
    private LocalConnect localConnect;
    private EditText passwordEditView;
    private ConstraintLayout serverIpLayout;
    private Spinner serverIpSpinner;
    private Button setServerIpButton;
    private EditText ssidEditView;
    private Button tcpActionButton;
    private TextView tcpResultTextView;
    private Button test04Button;
    private TextView titleTextView;
    private Button updateFirmwareButton;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ble_fi);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        this.titleTextView = (TextView) findViewById(R.id.titleTextView);
        BluetoothDevice bluetoothDevice = (BluetoothDevice) getIntent().getParcelableExtra(Constants.KEY_BLE_DEVICE);
        this.titleTextView.setText(bluetoothDevice.getName() == null ? getString(R.string.string_unknown) : bluetoothDevice.getName());
        String strTrim = this.titleTextView.getText().toString().trim();
        this.datalogSn = strTrim;
        if (strTrim == null || strTrim.length() != 10) {
            this.datalogSn = Constants.DEFAULT_DATALOG_SN;
        }
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BlufiActivity.instance.finish();
            }
        });
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("ssid");
        String stringExtra2 = intent.getStringExtra("password");
        EditText editText = (EditText) findViewById(R.id.wifi_connect_ssid_editView);
        this.ssidEditView = editText;
        if (Tool.isEmpty(stringExtra)) {
            stringExtra = "";
        }
        editText.setText(stringExtra);
        EditText editText2 = (EditText) findViewById(R.id.wifi_connect_password_editView);
        this.passwordEditView = editText2;
        if (Tool.isEmpty(stringExtra2)) {
            stringExtra2 = "";
        }
        editText2.setText(stringExtra2);
        this.tcpResultTextView = (TextView) findViewById(R.id.wifi_tcp_result_textView);
        Button button = (Button) findViewById(R.id.wifi_tcp_action_button);
        this.tcpActionButton = button;
        button.setEnabled(false);
        this.tcpActionButton.setOnClickListener(new AnonymousClass1());
        this.serverIpSpinner = (Spinner) findViewById(R.id.wifi_connect_serverIpSpinner);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ArrayList()));
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.wifi_connect_serverIpLayout);
        this.serverIpLayout = constraintLayout;
        constraintLayout.setVisibility(8);
        this.setServerIpButton = (Button) findViewById(R.id.wifi_connect_setServerIpButton);
        Button button2 = (Button) findViewById(R.id.ble_update_firmware_button);
        this.updateFirmwareButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m556lambda$onCreate$5$comluxluxcloudviewbleBlufiActivity(view);
            }
        });
        Button button3 = (Button) findViewById(R.id.ble_test_04_button);
        this.test04Button = button3;
        button3.setEnabled(false);
        this.test04Button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m557lambda$onCreate$6$comluxluxcloudviewbleBlufiActivity(view);
            }
        });
        LocalConnectManager.updateBluetoothLocalConnect(new BluetoothLocalConnect(this, bluetoothDevice));
        LocalConnect bluetoothLocalConnect = LocalConnectManager.getBluetoothLocalConnect();
        this.localConnect = bluetoothLocalConnect;
        bluetoothLocalConnect.initialize(false);
    }

    /* renamed from: com.lux.luxcloud.view.ble.BlufiActivity$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BlufiActivity.this.tcpResultTextView.setVisibility(4);
            final String strTrim = BlufiActivity.this.ssidEditView.getText().toString().trim();
            if (Tool.isEmpty(strTrim) || "unknown ssid".equals(strTrim)) {
                BlufiActivity.this.tcpResultTextView.setVisibility(0);
                BlufiActivity.this.tcpResultTextView.setText(R.string.wifi_connect_ssid_invalid);
                return;
            }
            final String strTrim2 = BlufiActivity.this.passwordEditView.getText().toString().trim();
            if (Tool.isEmpty(strTrim2)) {
                strTrim2 = "";
            }
            BlufiActivity.this.tcpActionButton.setText(R.string.wifi_connect_btn_sending);
            BlufiActivity.this.tcpActionButton.setEnabled(false);
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m559lambda$onClick$0$comluxluxcloudviewbleBlufiActivity$1(strTrim, strTrim2);
                }
            }).start();
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-ble-BlufiActivity$1, reason: not valid java name */
        /* synthetic */ void m559lambda$onClick$0$comluxluxcloudviewbleBlufiActivity$1(String str, String str2) {
            if (BlufiActivity.this.localConnect.writeDatalogParam(4, str + "," + str2)) {
                BlufiActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_tcp_set_success_reboot);
            } else {
                BlufiActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_write_tcp_failed);
            }
        }
    }

    private /* synthetic */ void lambda$onCreate$4(View view) {
        final Property property = (Property) this.serverIpSpinner.getSelectedItem();
        if (property != null) {
            this.setServerIpButton.setEnabled(false);
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m555lambda$onCreate$3$comluxluxcloudviewbleBlufiActivity(property);
                }
            }).start();
        }
    }

    /* renamed from: lambda$onCreate$3$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m555lambda$onCreate$3$comluxluxcloudviewbleBlufiActivity(Property property) {
        if (!Tool.isEmpty(property.getName())) {
            final boolean zWriteDatalogParam = this.localConnect.writeDatalogParam(6, property.getName());
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m553lambda$onCreate$1$comluxluxcloudviewbleBlufiActivity(zWriteDatalogParam);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m554lambda$onCreate$2$comluxluxcloudviewbleBlufiActivity();
                }
            });
        }
    }

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m553lambda$onCreate$1$comluxluxcloudviewbleBlufiActivity(boolean z) {
        if (z) {
            Tool.alert(this, R.string.local_set_result_success);
        } else {
            Tool.alert(this, R.string.local_set_result_failed);
        }
        this.setServerIpButton.setEnabled(true);
    }

    /* renamed from: lambda$onCreate$2$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m554lambda$onCreate$2$comluxluxcloudviewbleBlufiActivity() {
        Tool.alert(this, R.string.local_set_result_failed);
        this.setServerIpButton.setEnabled(true);
    }

    /* renamed from: lambda$onCreate$5$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m556lambda$onCreate$5$comluxluxcloudviewbleBlufiActivity(View view) {
        Intent intent = new Intent(instance, (Class<?>) EWifiRemoteUpdateActivity.class);
        intent.putExtra("datalogParam", this.datalogParam);
        intent.putExtra("firmwareVersion", this.firmwareVersion);
        intent.putExtra(Constants.LOCAL_CONNECT_TYPE, this.localConnect.getConnectType());
        startActivity(intent);
    }

    /* renamed from: lambda$onCreate$6$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m557lambda$onCreate$6$comluxluxcloudviewbleBlufiActivity(View view) {
        if (this.localConnect.initialize(true)) {
            this.localConnect.sendPureCommand(DataFrameFactory.createReadMultiInputDataFrame(TCP_PROTOCOL._02, this.datalogSn, 0, 40));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeTcpActionAtUiThread(final int i) {
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m550xee57c23c(i);
            }
        });
    }

    /* renamed from: lambda$closeTcpActionAtUiThread$7$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m550xee57c23c(int i) {
        this.tcpResultTextView.setVisibility(0);
        this.tcpResultTextView.setText(i);
        this.tcpActionButton.setText(R.string.wifi_tcp_btn_connect);
        this.tcpActionButton.setEnabled(true);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("LuxPower - BlufiActivity onDestroy...");
        this.executor.shutdown();
        LocalConnectManager.updateBluetoothLocalConnect(null);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    private class GattCallback extends BluetoothGattCallback {
        private GattCallback() {
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i != 0) {
                bluetoothGatt.disconnect();
                System.out.println("ble - " + String.format(Locale.ENGLISH, "WriteChar error status %d", Integer.valueOf(i)));
            } else {
                System.out.println("ble - 发送成功");
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            int iCount2Low;
            int iCount2Low2;
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (value != null) {
                if (BlufiActivity.this.dataReceived == null || BlufiActivity.this.dataReceived.length == 0 || (value.length >= 2 && value[0] == -95 && value[1] == 26)) {
                    BlufiActivity.this.dataReceived = new byte[value.length];
                    System.arraycopy(value, 0, BlufiActivity.this.dataReceived, 0, value.length);
                } else {
                    byte[] bArr = new byte[BlufiActivity.this.dataReceived.length + value.length];
                    System.arraycopy(BlufiActivity.this.dataReceived, 0, bArr, 0, BlufiActivity.this.dataReceived.length);
                    System.arraycopy(value, 0, bArr, BlufiActivity.this.dataReceived.length, value.length);
                    BlufiActivity.this.dataReceived = bArr;
                }
                if (BlufiActivity.this.dataReceived == null || BlufiActivity.this.dataReceived.length < 6 || BlufiActivity.this.dataReceived.length < (iCount2Low2 = (iCount2Low = ProTool.count2Low(BlufiActivity.this.dataReceived, 4)) + 6)) {
                    return;
                }
                int i = ProTool.getInt(BlufiActivity.this.dataReceived, 7);
                if (i != 195) {
                    if (i == 196 && BlufiActivity.this.dataReceived.length >= 21) {
                        int iCount2Low3 = ProTool.count2Low(BlufiActivity.this.dataReceived, 18);
                        byte b = BlufiActivity.this.dataReceived[20];
                        if (iCount2Low3 != 4) {
                            if (iCount2Low3 == 6) {
                                if (b == 0) {
                                    Tool.alertNotInUiThread(BlufiActivity.this, R.string.local_set_result_success);
                                } else {
                                    Tool.alertNotInUiThread(BlufiActivity.this, R.string.local_set_result_failed);
                                }
                            }
                        } else if (b == 0) {
                            BlufiActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_tcp_set_success_reboot);
                        } else {
                            BlufiActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_write_tcp_failed);
                        }
                    }
                } else if (BlufiActivity.this.dataReceived.length > 22) {
                    int iCount2Low4 = ProTool.count2Low(BlufiActivity.this.dataReceived, 18);
                    String strSubstring = new String(BlufiActivity.this.dataReceived).substring(22);
                    if (iCount2Low4 == 6) {
                        BlufiActivity.this.handleReadServerIpAndPort(strSubstring);
                    } else if (iCount2Low4 == 7) {
                        Tool.alertNotInUiThread(BlufiActivity.this, strSubstring);
                    }
                }
                int i2 = iCount2Low + 7;
                if (BlufiActivity.this.dataReceived.length <= i2 || BlufiActivity.this.dataReceived[iCount2Low2] != -95 || BlufiActivity.this.dataReceived[i2] != 26) {
                    BlufiActivity.this.dataReceived = null;
                    return;
                }
                int length = (BlufiActivity.this.dataReceived.length - iCount2Low) - 6;
                byte[] bArr2 = new byte[length];
                System.arraycopy(BlufiActivity.this.dataReceived, iCount2Low2, bArr2, 0, length);
                BlufiActivity.this.dataReceived = bArr2;
            }
        }
    }

    private void setButtonStatusAtUiThread(final boolean z) {
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m558x2376e04a(z);
            }
        });
    }

    /* renamed from: lambda$setButtonStatusAtUiThread$8$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m558x2376e04a(boolean z) {
        this.tcpActionButton.setEnabled(z);
        this.setServerIpButton.setEnabled(z);
        this.test04Button.setEnabled(z);
    }

    @Override // com.lux.luxcloud.connect.ble.BleAction
    public void bleConnected() {
        setButtonStatusAtUiThread(true);
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m548lambda$bleConnected$10$comluxluxcloudviewbleBlufiActivity();
            }
        }).start();
    }

    /* renamed from: lambda$bleConnected$10$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m548lambda$bleConnected$10$comluxluxcloudviewbleBlufiActivity() throws InterruptedException {
        handleReadServerIpAndPort(this.localConnect.readDatalogParam(6));
        Tool.sleep(200L);
        final String datalogParam = this.localConnect.readDatalogParam(7);
        System.out.println("localFirmwareVersion == " + datalogParam);
        Tool.sleep(200L);
        final String datalogParam2 = this.localConnect.readDatalogParam(11);
        System.out.println("localDatalogParam == " + datalogParam2);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m549lambda$bleConnected$9$comluxluxcloudviewbleBlufiActivity(datalogParam, datalogParam2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateData, reason: merged with bridge method [inline-methods] */
    public void m549lambda$bleConnected$9$comluxluxcloudviewbleBlufiActivity(String str, String str2) {
        this.firmwareVersion = str;
        this.datalogParam = str2;
        instance.updateFirmwareButton.setText(instance.getString(R.string._1_s_update_firmware, new Object[]{this.firmwareVersion + " - "}));
        instance.updateFirmwareButton.setEnabled(true);
    }

    @Override // com.lux.luxcloud.connect.ble.BleAction
    public void bleConnectClosed() {
        setButtonStatusAtUiThread(false);
    }

    public void handleReadServerIpAndPort(final String str) {
        System.out.println("ble - handleReadServerIpAndPort serverIpAndPort: " + str);
        if (!Tool.isEmpty(str) && Constants.validServerIndexMap.containsKey(str)) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m551x8013cdcd(str);
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BlufiActivity$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m552xcb3f8ce();
                }
            });
        }
    }

    /* renamed from: lambda$handleReadServerIpAndPort$11$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m551x8013cdcd(String str) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getFirstClusterServers(this));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Integer num = Constants.validServerIndexMap.get(str);
        if (num != null) {
            this.serverIpSpinner.setSelection(num.intValue());
        }
        this.setServerIpButton.setEnabled(true);
    }

    /* renamed from: lambda$handleReadServerIpAndPort$12$com-lux-luxcloud-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m552xcb3f8ce() {
        List<Property> firstClusterServersWithUnknown = GlobalInfo.getInstance().getFirstClusterServersWithUnknown(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, firstClusterServersWithUnknown);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.serverIpSpinner.setSelection(firstClusterServersWithUnknown.size() - 1);
        this.setServerIpButton.setEnabled(true);
    }
}