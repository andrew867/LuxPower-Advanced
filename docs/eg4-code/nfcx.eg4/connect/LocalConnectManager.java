package com.nfcx.eg4.connect;

import com.nfcx.eg4.connect.ble.BluetoothLocalConnect;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.tcp.TcpManager;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class LocalConnectManager {
    private static BluetoothLocalConnect bluetoothLocalConnect;
    private static String dongleSn;

    public static LocalConnect getLocalConnect(String str) {
        if (Constants.LOCAL_CONNECT_TYPE_BLUETOOTH.equals(str)) {
            return bluetoothLocalConnect;
        }
        if (Constants.LOCAL_CONNECT_TYPE_TCP.equals(str)) {
            TcpManager tcpManager = TcpManager.getInstance();
            tcpManager.setDatalogSn(dongleSn);
            return tcpManager;
        }
        throw new RuntimeException("No such localConnectType: " + str);
    }

    public static void setupDongleSn(String str) {
        dongleSn = str;
    }

    public static LocalConnect getBluetoothLocalConnect() {
        return bluetoothLocalConnect;
    }

    public static void updateBluetoothLocalConnect(BluetoothLocalConnect bluetoothLocalConnect2) {
        BluetoothLocalConnect bluetoothLocalConnect3 = bluetoothLocalConnect;
        if (bluetoothLocalConnect3 != null) {
            bluetoothLocalConnect3.close();
        }
        bluetoothLocalConnect = bluetoothLocalConnect2;
    }
}