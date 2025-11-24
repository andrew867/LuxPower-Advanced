package com.lux.luxcloud.view.ble;

import android.app.Activity;
import android.app.UiModeManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnectManager;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.local.LocalActivity;
import com.lux.luxcloud.view.updateFirmware.UpdateExtFirmwareActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class BleConnectActivity extends Activity {
    private static final int REQUEST_BLUFI = 16;
    private static final int REQUEST_PERMISSION = 1;
    private static final long TIMEOUT_SCAN = 4000;
    public static BleConnectActivity instance;
    private ListView bleConnectListView;
    private String dongleSn;
    private boolean isDarkTheme;
    private List<ScanResult> mBleList;
    private Map<Long, ScanResult> mDeviceMap;
    private ScanCallback mScanCallback;
    private volatile long mScanStartTime;
    private ExecutorService mThreadPool;
    private Future<Boolean> mUpdateFuture;
    private String password;
    private String ssid;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String target;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ble_connect);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.login_container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(Constants.TARGET);
        this.target = stringExtra;
        if (Constants.TARGET_WIFI_CONFIG.equals(stringExtra)) {
            this.ssid = intent.getStringExtra("ssid");
            this.password = intent.getStringExtra("password");
            this.dongleSn = intent.getStringExtra("dongleSn");
        }
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BleConnectActivity.instance.finish();
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        this.mThreadPool = Executors.newSingleThreadExecutor();
        this.mBleList = new LinkedList();
        this.mDeviceMap = new HashMap();
        this.mScanCallback = new ScanCallback();
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m546lambda$onCreate$1$comluxluxcloudviewbleBleConnectActivity();
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        if (Build.VERSION.SDK_INT >= 31) {
            arrayList.add("android.permission.BLUETOOTH_SCAN");
            arrayList.add("android.permission.BLUETOOTH_CONNECT");
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[0]), 1);
        ListView listView = (ListView) findViewById(R.id.ble_connect_bleList);
        this.bleConnectListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BleConnectActivity.this.stopScan();
                ScanResult scanResult = (ScanResult) BleConnectActivity.this.mDeviceMap.get(Long.valueOf(j));
                if (scanResult != null) {
                    if (!Constants.TARGET_WIFI_CONFIG.equals(BleConnectActivity.this.target)) {
                        if (Constants.TARGET_UPDATE_FIRMWARE.equals(BleConnectActivity.this.target)) {
                            Intent intent2 = new Intent(BleConnectActivity.this, (Class<?>) UpdateExtFirmwareActivity.class);
                            intent2.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_BLUETOOTH);
                            intent2.putExtra(Constants.KEY_BLE_DEVICE, scanResult.getDevice());
                            BleConnectActivity.this.startActivity(intent2);
                        } else {
                            Intent intent3 = new Intent(BleConnectActivity.this, (Class<?>) LocalActivity.class);
                            intent3.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_BLUETOOTH);
                            intent3.putExtra(Constants.KEY_BLE_DEVICE, scanResult.getDevice());
                            BleConnectActivity.this.startActivity(intent3);
                        }
                    } else {
                        Intent intent4 = new Intent(BleConnectActivity.this, (Class<?>) BlufiActivity.class);
                        intent4.putExtra(Constants.KEY_BLE_DEVICE, scanResult.getDevice());
                        intent4.putExtra("ssid", !Tool.isEmpty(BleConnectActivity.this.ssid) ? BleConnectActivity.this.ssid : "");
                        intent4.putExtra("password", Tool.isEmpty(BleConnectActivity.this.password) ? "" : BleConnectActivity.this.password);
                        BleConnectActivity.this.startActivityForResult(intent4, 16);
                    }
                    BleConnectActivity.this.mDeviceMap.clear();
                    BleConnectActivity.this.mBleList.clear();
                    ListView listView2 = BleConnectActivity.this.bleConnectListView;
                    BleConnectActivity bleConnectActivity = BleConnectActivity.this;
                    listView2.setAdapter((ListAdapter) new BleConnectAdapter(bleConnectActivity, bleConnectActivity.mBleList));
                }
            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity.2
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                BleConnectActivity.this.scan();
            }
        });
    }

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-ble-BleConnectActivity, reason: not valid java name */
    /* synthetic */ void m546lambda$onCreate$1$comluxluxcloudviewbleBleConnectActivity() {
        Tool.alert(instance, getString(R.string.bleconnect_turn_on_ble_tip));
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        stopScan();
        this.mThreadPool.shutdownNow();
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        System.out.println("BLE - onRequestPermissionsResult...");
        super.onRequestPermissionsResult(i, strArr, iArr);
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = strArr[i2];
            int i3 = iArr[i2];
            System.out.println("BLE - permission = " + str);
            if (str.equals("android.permission.ACCESS_FINE_LOCATION")) {
                System.out.println("BLE - grant = " + i3);
                if (i3 == 0) {
                    this.swipeRefreshLayout.setRefreshing(true);
                    System.out.println("BLE - start scan 1...");
                    scan();
                }
            }
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        System.out.println("BLE - onActivityResult requestCode = " + i + ", resultCode = " + i2);
        if (i == 16) {
            System.out.println("BLE - start scan 2...");
            this.swipeRefreshLayout.setRefreshing(true);
            scan();
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scan() {
        System.out.println("LuxPower - Ready to start scan ble");
        LocalConnectManager.updateBluetoothLocalConnect(null);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        if (!defaultAdapter.isEnabled() || bluetoothLeScanner == null) {
            Toast.makeText(this, R.string.main_bt_disable_msg, 0).show();
            this.swipeRefreshLayout.setRefreshing(false);
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(FirebaseAnalytics.Param.LOCATION);
        if (!(locationManager != null && LocationManagerCompat.isLocationEnabled(locationManager))) {
            Toast.makeText(this, R.string.main_location_disable_msg, 0).show();
            this.swipeRefreshLayout.setRefreshing(false);
            return;
        }
        this.mDeviceMap.clear();
        this.mBleList.clear();
        this.mScanStartTime = SystemClock.elapsedRealtime();
        System.out.println("LuxPower - BLE - mScanStartTime = " + this.mScanStartTime);
        System.out.println("LuxPower - Start scan ble");
        bluetoothLeScanner.startScan((List<ScanFilter>) null, new ScanSettings.Builder().setScanMode(2).build(), this.mScanCallback);
        this.mUpdateFuture = this.mThreadPool.submit(new Callable() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m547lambda$scan$2$comluxluxcloudviewbleBleConnectActivity();
            }
        });
    }

    /* renamed from: lambda$scan$2$com-lux-luxcloud-view-ble-BleConnectActivity, reason: not valid java name */
    /* synthetic */ Boolean m547lambda$scan$2$comluxluxcloudviewbleBleConnectActivity() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000L);
                if (SystemClock.elapsedRealtime() - this.mScanStartTime > TIMEOUT_SCAN) {
                    break;
                }
                onIntervalScanUpdate(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallback);
        }
        onIntervalScanUpdate(true);
        System.out.println("BLE - Scan ble thread is interrupted...");
        System.out.println("LuxPower - Scan ble thread is interrupted");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopScan() {
        BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallback);
        }
        Future<Boolean> future = this.mUpdateFuture;
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("LuxPower - Stop scan ble");
        System.out.println("BLE - Stop scan ble...");
    }

    private void onIntervalScanUpdate(final boolean z) {
        System.out.println("BLE - onIntervalScanUpdate over = " + z + ", mDeviceMap.size = " + this.mDeviceMap.size());
        final ArrayList arrayList = new ArrayList(this.mDeviceMap.values());
        Collections.sort(arrayList, new Comparator<ScanResult>() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity.3
            @Override // java.util.Comparator
            public int compare(ScanResult scanResult, ScanResult scanResult2) {
                return scanResult2.getDevice().getName().compareTo(scanResult.getDevice().getName());
            }
        });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            System.out.println("BLE - device = " + ((ScanResult) it.next()).getDevice());
        }
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.ble.BleConnectActivity.4
            @Override // java.lang.Runnable
            public void run() {
                BleConnectActivity.this.mBleList.clear();
                BleConnectActivity.this.mBleList.addAll(arrayList);
                ListView listView = BleConnectActivity.this.bleConnectListView;
                BleConnectActivity bleConnectActivity = BleConnectActivity.this;
                listView.setAdapter((ListAdapter) new BleConnectAdapter(bleConnectActivity, bleConnectActivity.mBleList));
                if (z) {
                    BleConnectActivity.this.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private class ScanCallback extends android.bluetooth.le.ScanCallback {
        private ScanCallback() {
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanFailed(int i) {
            super.onScanFailed(i);
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onBatchScanResults(List<ScanResult> list) {
            Iterator<ScanResult> it = list.iterator();
            while (it.hasNext()) {
                onLeScan(it.next());
            }
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanResult(int i, ScanResult scanResult) {
            onLeScan(scanResult);
        }

        private void onLeScan(ScanResult scanResult) {
            String name = scanResult.getDevice().getName();
            System.out.println("BLE - onLeScan = " + scanResult.getDevice() + ", name = " + name);
            if (name == null || name.length() != 10) {
                return;
            }
            BleConnectActivity.this.mDeviceMap.put(Long.valueOf(Tool.mac2Long(scanResult.getDevice().getAddress())), scanResult);
        }
    }
}