package com.nfcx.eg4.connect.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.nfcx.eg4.connect.AbstractLocalConnect;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;
import com.nfcx.eg4.tcp.CommandSender;
import com.nfcx.eg4.tcp.TcpAnalyzer;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.Tool;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class BluetoothLocalConnect extends AbstractLocalConnect implements CommandSender {
    private static final boolean isDebug = false;
    private BleAction bleAction;
    private BluetoothDevice bluetoothDevice;
    private BluetoothGatt bluetoothGatt;
    protected Map<String, String> commandWaitMap;
    private BluetoothGattCharacteristic notifyCharacteristic;
    private TcpAnalyzer tcpAnalyzer;
    private BluetoothGattCharacteristic writeCharacteristic;

    @Override // com.nfcx.eg4.tcp.CommandSender
    public boolean startMonitor() {
        return false;
    }

    public BluetoothLocalConnect(BleAction bleAction, BluetoothDevice bluetoothDevice) {
        super(Constants.LOCAL_CONNECT_TYPE_BLUETOOTH);
        this.commandWaitMap = new ConcurrentSkipListMap();
        this.bleAction = bleAction;
        this.bluetoothDevice = bluetoothDevice;
        if (bluetoothDevice.getName() == null) {
            this.datalogSn = bluetoothDevice.getName();
        } else {
            this.datalogSn = Constants.DEFAULT_DATALOG_SN;
        }
        this.tcpAnalyzer = new TcpAnalyzer(this);
    }

    @Override // com.nfcx.eg4.connect.LocalConnect
    public boolean initialize(boolean z) throws InterruptedException {
        if (!this.connected) {
            connect();
        }
        if (z) {
            for (int i = 0; i < 60 && !this.connected; i++) {
                Tool.sleep(50L);
            }
        }
        return this.connected;
    }

    private void connect() {
        this.bluetoothGatt = this.bluetoothDevice.connectGatt((Context) this.bleAction, true, new GattCallback());
    }

    private class GattCallback extends BluetoothGattCallback {
        private GattCallback() {
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            String address = bluetoothGatt.getDevice().getAddress();
            System.out.println("ble - " + String.format(Locale.ENGLISH, "onConnectionStateChange addr=%s, status=%d, newState=%d", address, Integer.valueOf(i), Integer.valueOf(i2)));
            if (i != 0) {
                BluetoothLocalConnect.this.closeBluetoothCatt();
                System.out.println("ble - " + String.format(Locale.ENGLISH, "Disconnect %s, status=%d", address, Integer.valueOf(i)));
            } else if (i2 == 0) {
                System.out.println("ble - " + String.format("Disconnected %s", address));
                BluetoothLocalConnect.this.closeBluetoothCatt();
            } else {
                if (i2 != 2) {
                    return;
                }
                System.out.println("ble - " + String.format("Connected %s, 设备连接上 开始扫描服务", address));
                BluetoothLocalConnect.this.bluetoothGatt.discoverServices();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            System.out.println("ble - " + String.format(Locale.ENGLISH, "onMtuChanged status=%d, mtu=%d", Integer.valueOf(i2), Integer.valueOf(i)));
            if (i2 == 0) {
                System.out.println("ble - " + String.format(Locale.ENGLISH, "Set mtu complete, mtu=%d ", Integer.valueOf(i)));
            } else {
                System.out.println("ble - " + String.format(Locale.ENGLISH, "Set mtu failed, mtu=%d, status=%d", Integer.valueOf(i), Integer.valueOf(i2)));
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            System.out.println("ble - " + String.format(Locale.ENGLISH, "onServicesDiscovered status=%d", Integer.valueOf(i)));
            if (i != 0) {
                bluetoothGatt.disconnect();
                System.out.println("ble - " + String.format(Locale.ENGLISH, "Discover services error status %d", Integer.valueOf(i)));
                return;
            }
            System.out.println("ble - 开始获取服务列表");
            BluetoothGattService service = BluetoothLocalConnect.this.bluetoothGatt.getService(UUID.fromString("000000ff-0000-1000-8000-00805f9b34fb"));
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ff01-0000-1000-8000-00805f9b34fb"));
                if (characteristic != null) {
                    BluetoothLocalConnect.this.notifyCharacteristic = characteristic;
                    BluetoothLocalConnect.this.writeCharacteristic = characteristic;
                    BluetoothLocalConnect.this.bluetoothGatt.setCharacteristicNotification(BluetoothLocalConnect.this.notifyCharacteristic, true);
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    BluetoothLocalConnect.this.bluetoothGatt.writeDescriptor(descriptor);
                    return;
                }
                System.out.println("ble - BluetoothGattCharacteristic 目标特性未找到");
                return;
            }
            System.out.println("ble - bluetoothGattService 目标服务未找到");
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            System.out.println("ble - " + String.format(Locale.ENGLISH, "onDescriptorWrite status=%d", Integer.valueOf(i)));
            if (i == 0) {
                System.out.println("ble - 开启监听成功");
                BluetoothLocalConnect.this.connected = true;
                BluetoothLocalConnect.this.bleAction.bleConnected();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i != 0) {
                bluetoothGatt.disconnect();
                System.out.println("ble - " + String.format(Locale.ENGLISH, "WriteChar error status %d", Integer.valueOf(i)));
            } else {
                synchronized (BluetoothLocalConnect.this) {
                    BluetoothLocalConnect.this.notifyAll();
                }
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (value != null) {
                System.out.println("ble - onCharacteristicChanged value: " + ProTool.showData(value));
                for (byte b : value) {
                    BluetoothLocalConnect.this.tcpAnalyzer.putChar(ProTool.getInt(b));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeBluetoothCatt() {
        BluetoothGatt bluetoothGatt = this.bluetoothGatt;
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            this.bluetoothGatt.close();
            this.bluetoothGatt = null;
        }
        this.connected = false;
        this.bleAction.bleConnectClosed();
    }

    @Override // com.nfcx.eg4.connect.LocalConnect, com.nfcx.eg4.tcp.CommandSender
    public void close() {
        closeBluetoothCatt();
    }

    @Override // com.nfcx.eg4.tcp.CommandSender
    public void putCommandWaitMap(String str, String str2) {
        this.commandWaitMap.put(str, str2);
    }

    @Override // com.nfcx.eg4.connect.LocalConnect, com.nfcx.eg4.tcp.CommandSender
    public String getCommandWaitResult(String str) {
        return this.commandWaitMap.get(str);
    }

    @Override // com.nfcx.eg4.connect.LocalConnect, com.nfcx.eg4.tcp.CommandSender
    public String sendCommand(String str, DataFrame dataFrame) {
        return sendCommand(str, dataFrame, 4);
    }

    @Override // com.nfcx.eg4.connect.LocalConnect, com.nfcx.eg4.tcp.CommandSender
    public String sendCommand(String str, DataFrame dataFrame, int i) {
        try {
            this.commandWaitMap.put(str, "");
            if (!sendPureCommand(dataFrame)) {
                return null;
            }
            for (int i2 = 0; i2 < i * 5; i2++) {
                Tool.sleep(200L);
                if (!Tool.isEmpty(this.commandWaitMap.get(str))) {
                    String str2 = this.commandWaitMap.get(str);
                    this.commandWaitMap.remove(str);
                    return str2;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.nfcx.eg4.connect.LocalConnect, com.nfcx.eg4.tcp.CommandSender
    public boolean sendPureCommand(DataFrame dataFrame) {
        byte[] frame = dataFrame.getFrame();
        boolean zWriteCharacteristic = true;
        this.writeCharacteristic.setWriteType(1);
        int i = 0;
        while (true) {
            int i2 = i * 20;
            if (i2 < frame.length) {
                int length = i2 + 20 > frame.length ? frame.length - i2 : 20;
                byte[] bArr = new byte[length];
                System.arraycopy(frame, i2, bArr, 0, length);
                this.writeCharacteristic.setValue(bArr);
                zWriteCharacteristic = this.bluetoothGatt.writeCharacteristic(this.writeCharacteristic);
                synchronized (this) {
                    try {
                        wait(300L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i++;
            } else {
                System.out.println("ble - writeCharacteristicValue DONE >>>>>>>>");
                return zWriteCharacteristic;
            }
        }
    }
}