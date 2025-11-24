package com.nfcx.eg4.view.ble;

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
import com.nfcx.eg4.R;
import com.nfcx.eg4.connect.LocalConnect;
import com.nfcx.eg4.connect.LocalConnectManager;
import com.nfcx.eg4.connect.ble.BleAction;
import com.nfcx.eg4.connect.ble.BluetoothLocalConnect;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.nfcx.eg4.tcp.TcpManager;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.wifi.EWifiRemoteUpdateActivity;
import com.nfcx.eg4.view.wifi.WifiConnectActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class BlufiActivity extends Activity implements BleAction {
    public static BlufiActivity instance;
    private byte[] dataReceived;
    private String datalogParam;
    String datalogParamData;
    private String datalogSn;
    String firmwareVersion;
    private boolean isDarkTheme;
    private LocalConnect localConnect;
    private EditText passwordEditView;
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
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        this.titleTextView = (TextView) findViewById(R.id.titleTextView);
        BluetoothDevice bluetoothDevice = (BluetoothDevice) getIntent().getParcelableExtra(Constants.KEY_BLE_DEVICE);
        this.titleTextView.setText(bluetoothDevice.getName() == null ? getString(R.string.string_unknown) : bluetoothDevice.getName());
        String strTrim = this.titleTextView.getText().toString().trim();
        this.datalogSn = strTrim;
        if (strTrim == null || strTrim.length() != 10) {
            this.datalogSn = Constants.DEFAULT_DATALOG_SN;
        }
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda3
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
        Button button2 = (Button) findViewById(R.id.wifi_connect_setServerIpButton);
        this.setServerIpButton = button2;
        button2.setEnabled(false);
        this.setServerIpButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m508lambda$onCreate$3$comnfcxeg4viewbleBlufiActivity(view);
            }
        });
        Button button3 = (Button) findViewById(R.id.ble_update_firmware_button);
        this.updateFirmwareButton = button3;
        button3.setEnabled(false);
        this.updateFirmwareButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.ble.BlufiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent2 = new Intent(WifiConnectActivity.instance, (Class<?>) EWifiRemoteUpdateActivity.class);
                intent2.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_TCP);
                intent2.putExtra("firmwareVersion", BlufiActivity.this.firmwareVersion);
                intent2.putExtra("datalogParam", BlufiActivity.this.datalogParamData);
                BlufiActivity.this.startActivity(intent2);
            }
        });
        Button button4 = (Button) findViewById(R.id.ble_test_04_button);
        this.test04Button = button4;
        button4.setEnabled(false);
        this.test04Button.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m509lambda$onCreate$4$comnfcxeg4viewbleBlufiActivity(view);
            }
        });
        LocalConnectManager.updateBluetoothLocalConnect(new BluetoothLocalConnect(this, bluetoothDevice));
        LocalConnect bluetoothLocalConnect = LocalConnectManager.getBluetoothLocalConnect();
        this.localConnect = bluetoothLocalConnect;
        bluetoothLocalConnect.initialize(false);
    }

    /* renamed from: com.nfcx.eg4.view.ble.BlufiActivity$1, reason: invalid class name */
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
            if (Tool.isEmpty(strTrim2) || strTrim2.length() < 8) {
                BlufiActivity.this.tcpResultTextView.setVisibility(0);
                BlufiActivity.this.tcpResultTextView.setText(R.string.page_register_error_wifi_password_minLength);
            } else {
                BlufiActivity.this.tcpActionButton.setText(R.string.wifi_connect_btn_sending);
                BlufiActivity.this.tcpActionButton.setEnabled(false);
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m511lambda$onClick$0$comnfcxeg4viewbleBlufiActivity$1(strTrim, strTrim2);
                    }
                }).start();
            }
        }

        /* renamed from: lambda$onClick$0$com-nfcx-eg4-view-ble-BlufiActivity$1, reason: not valid java name */
        /* synthetic */ void m511lambda$onClick$0$comnfcxeg4viewbleBlufiActivity$1(String str, String str2) {
            if (BlufiActivity.this.localConnect.writeDatalogParam(4, str + "," + str2)) {
                BlufiActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_tcp_set_success_reboot);
            } else {
                BlufiActivity.this.closeTcpActionAtUiThread(R.string.wifi_connect_write_tcp_failed);
            }
        }
    }

    /* renamed from: lambda$onCreate$3$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m508lambda$onCreate$3$comnfcxeg4viewbleBlufiActivity(View view) {
        final String str;
        Property property = (Property) this.serverIpSpinner.getSelectedItem();
        if (property == null || Tool.isEmpty(property.getName())) {
            Tool.alert(this, R.string.local_set_result_failed);
            return;
        }
        this.setServerIpButton.setEnabled(false);
        String strExtractHost = Tool.extractHost(property.getName());
        if (TcpManager.isTlsConnection()) {
            str = strExtractHost + ",4348";
        } else {
            str = strExtractHost + ",4346";
        }
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m507lambda$onCreate$2$comnfcxeg4viewbleBlufiActivity(str);
            }
        }).start();
    }

    /* renamed from: lambda$onCreate$2$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m507lambda$onCreate$2$comnfcxeg4viewbleBlufiActivity(String str) {
        final boolean zWriteDatalogParam = this.localConnect.writeDatalogParam(6, str);
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m506lambda$onCreate$1$comnfcxeg4viewbleBlufiActivity(zWriteDatalogParam);
            }
        });
    }

    /* renamed from: lambda$onCreate$1$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m506lambda$onCreate$1$comnfcxeg4viewbleBlufiActivity(boolean z) {
        if (z) {
            Tool.alert(this, R.string.local_set_result_success);
        } else {
            Tool.alert(this, R.string.local_set_result_failed);
        }
        this.setServerIpButton.setEnabled(true);
    }

    /* renamed from: lambda$onCreate$4$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m509lambda$onCreate$4$comnfcxeg4viewbleBlufiActivity(View view) {
        if (this.localConnect.initialize(true)) {
            this.localConnect.sendPureCommand(DataFrameFactory.createReadMultiInputDataFrame(TCP_PROTOCOL._02, this.datalogSn, 0, 40));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeTcpActionAtUiThread(final int i) {
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m504xb3e470cc(i);
            }
        });
    }

    /* renamed from: lambda$closeTcpActionAtUiThread$5$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m504xb3e470cc(int i) {
        this.tcpResultTextView.setVisibility(0);
        this.tcpResultTextView.setText(i);
        this.tcpActionButton.setText(R.string.wifi_tcp_btn_connect);
        this.tcpActionButton.setEnabled(true);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
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
                System.out.println("ble - onCharacteristicChanged value: " + ProTool.showData(value));
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
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m510x6070c5da(z);
            }
        });
    }

    /* renamed from: lambda$setButtonStatusAtUiThread$6$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m510x6070c5da(boolean z) {
        this.tcpActionButton.setEnabled(z);
        this.setServerIpButton.setEnabled(z);
        this.updateFirmwareButton.setEnabled(z);
        this.test04Button.setEnabled(z);
    }

    @Override // com.nfcx.eg4.connect.ble.BleAction
    public void bleConnected() {
        setButtonStatusAtUiThread(true);
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m503lambda$bleConnected$8$comnfcxeg4viewbleBlufiActivity();
            }
        }).start();
    }

    /* renamed from: lambda$bleConnected$8$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m503lambda$bleConnected$8$comnfcxeg4viewbleBlufiActivity() throws InterruptedException {
        handleReadServerIpAndPort(this.localConnect.readDatalogParam(6));
        Tool.sleep(200L);
        final String datalogParam = this.localConnect.readDatalogParam(7);
        System.out.println("localFirmwareVersion == " + datalogParam);
        Tool.sleep(200L);
        final String datalogParam2 = this.localConnect.readDatalogParam(11);
        System.out.println("localDatalogParam == " + ProTool.showData(String.valueOf(datalogParam2.charAt(0))));
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m502lambda$bleConnected$7$comnfcxeg4viewbleBlufiActivity(datalogParam, datalogParam2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateData, reason: merged with bridge method [inline-methods] */
    public void m502lambda$bleConnected$7$comnfcxeg4viewbleBlufiActivity(String str, String str2) {
        this.firmwareVersion = str;
        this.datalogParamData = str2;
        instance.updateFirmwareButton.setText(instance.getString(R.string._1_s_update_firmware, new Object[]{this.firmwareVersion + " - "}));
        instance.updateFirmwareButton.setEnabled(true);
    }

    @Override // com.nfcx.eg4.connect.ble.BleAction
    public void bleConnectClosed() {
        setButtonStatusAtUiThread(false);
    }

    public void handleReadServerIpAndPort(final String str) {
        System.out.println("ble - handleReadServerIpAndPort responseValue: " + str);
        if (Tool.isEmpty(str)) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.ble.BlufiActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m505xc2f7d6b6(str);
            }
        });
    }

    /* renamed from: lambda$handleReadServerIpAndPort$9$com-nfcx-eg4-view-ble-BlufiActivity, reason: not valid java name */
    /* synthetic */ void m505xc2f7d6b6(String str) {
        List<Property> secondClusterServers = GlobalInfo.getInstance().getSecondClusterServers();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, secondClusterServers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Integer indexByHost = Constants.getIndexByHost(str);
        if (indexByHost != null) {
            this.serverIpSpinner.setSelection(indexByHost.intValue());
        } else {
            this.serverIpSpinner.setSelection(secondClusterServers.size() - 1);
        }
        this.setServerIpButton.setEnabled(true);
    }
}