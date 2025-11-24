package com.lux.luxcloud.view.local;

import android.app.Dialog;
import android.app.UiModeManager;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnect;
import com.lux.luxcloud.connect.LocalConnectManager;
import com.lux.luxcloud.connect.ble.BleAction;
import com.lux.luxcloud.connect.ble.BluetoothLocalConnect;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.custom.android.dialog.CustomTimePickerDialog;
import com.lux.luxcloud.global.custom.android.dialog.DayDatePickerDialog;
import com.lux.luxcloud.global.custom.android.dialog.DayOnDateSetListener;
import com.lux.luxcloud.global.custom.android.dialog.TimePickerSetListener;
import com.lux.luxcloud.tool.DonglePskUtil;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.view.local.fragment.Local12KSetFragment;
import com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment;
import com.lux.luxcloud.view.local.fragment.LocalOverviewFragment;
import com.lux.luxcloud.view.local.fragment.LocalSetFragment;
import com.lux.luxcloud.view.login.LoginActivity;
import java.util.ArrayList;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LocalActivity extends FragmentActivity implements BleAction {
    private static final int REQUEST_WIFI_PERMISSION = 8;
    public static LocalActivity instance;
    private LocalViewPagerAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private int deviceType;
    private boolean isDarkTheme;
    private Local12KSetFragment local12KSetFragment;
    private LocalConnect localConnect;
    private String localConnectType;
    private LocalOffGridSetFragment localOffGridSetFragment;
    private LocalOverviewFragment localOverviewFragment;
    private LocalSetFragment localSetFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: com.lux.luxcloud.view.local.LocalActivity.1
        @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_overview /* 2131231814 */:
                    LocalActivity.this.viewPager2.setCurrentItem(0);
                    return true;
                case R.id.navigation_set /* 2131231815 */:
                    LocalActivity.this.viewPager2.setCurrentItem(1);
                    return true;
                default:
                    return false;
            }
        }
    };
    private MenuItem menuItem;
    private ViewPager2 viewPager2;

    @Override // com.lux.luxcloud.connect.ble.BleAction
    public void bleConnectClosed() {
    }

    @Override // com.lux.luxcloud.connect.ble.BleAction
    public void bleConnected() {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_local);
        Intent intent = getIntent();
        this.localConnectType = intent.getStringExtra(Constants.LOCAL_CONNECT_TYPE);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().addFlags(128);
        setRequestedOrientation(1);
        if (Constants.LOCAL_CONNECT_TYPE_BLUETOOTH.equals(this.localConnectType)) {
            LocalConnectManager.updateBluetoothLocalConnect(new BluetoothLocalConnect(this, (BluetoothDevice) intent.getParcelableExtra(Constants.KEY_BLE_DEVICE)));
            this.localConnect = LocalConnectManager.getLocalConnect(this.localConnectType);
        } else {
            LocalConnectManager.setupDongleSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 8));
            this.localConnect = LocalConnectManager.getLocalConnect(this.localConnectType);
        }
        this.viewPager2 = (ViewPager2) findViewById(R.id.viewpager);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        this.bottomNavigationView = bottomNavigationView;
        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
        this.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.lux.luxcloud.view.local.LocalActivity.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                if (LocalActivity.this.menuItem != null) {
                    LocalActivity.this.menuItem.setChecked(false);
                } else {
                    LocalActivity.this.bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                LocalActivity localActivity = LocalActivity.this;
                localActivity.menuItem = localActivity.bottomNavigationView.getMenu().getItem(i);
                LocalActivity.this.menuItem.setChecked(true);
            }
        });
        setupViewPager(this.viewPager2);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        System.out.println("LuxPowerLocalActivity onDestroy...");
        this.localConnect.close();
        this.localConnect = null;
    }

    private void setupViewPager(ViewPager2 viewPager2) {
        ArrayList arrayList = new ArrayList();
        LocalConnectManager.setupDongleSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 8));
        LocalOverviewFragment localOverviewFragment = new LocalOverviewFragment(this.localConnect);
        this.localOverviewFragment = localOverviewFragment;
        arrayList.add(localOverviewFragment);
        LocalSetFragment localSetFragment = new LocalSetFragment(this.localConnect);
        this.localSetFragment = localSetFragment;
        arrayList.add(localSetFragment);
        this.localOffGridSetFragment = new LocalOffGridSetFragment(this.localConnect);
        this.local12KSetFragment = new Local12KSetFragment(this.localConnect);
        LocalViewPagerAdapter localViewPagerAdapter = new LocalViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), arrayList, this.localSetFragment, this.localOffGridSetFragment, this.local12KSetFragment);
        this.adapter = localViewPagerAdapter;
        viewPager2.setAdapter(localViewPagerAdapter);
    }

    public void switchLocalSetFragment(int i) {
        this.deviceType = i;
        this.adapter.switchLocalSetFragment(i);
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int i) {
        EditText timeDateEditText;
        EditText timeTimeEditText;
        if (i == 0) {
            int i2 = this.deviceType;
            if (i2 == 6) {
                timeDateEditText = this.local12KSetFragment.getTimeDateEditText();
            } else if (i2 == 3) {
                timeDateEditText = this.localOffGridSetFragment.getTimeDateEditText();
            } else {
                timeDateEditText = this.localSetFragment.getTimeDateEditText();
            }
            if (timeDateEditText != null) {
                return new DayDatePickerDialog(this, new DayOnDateSetListener(timeDateEditText), timeDateEditText.getText().toString());
            }
            return null;
        }
        if (i != 1) {
            return null;
        }
        int i3 = this.deviceType;
        if (i3 == 6) {
            timeTimeEditText = this.local12KSetFragment.getTimeTimeEditText();
        } else if (i3 == 3) {
            timeTimeEditText = this.localOffGridSetFragment.getTimeTimeEditText();
        } else {
            timeTimeEditText = this.localSetFragment.getTimeTimeEditText();
        }
        if (timeTimeEditText != null) {
            return new CustomTimePickerDialog(this, new TimePickerSetListener(timeTimeEditText), timeTimeEditText.getText().toString());
        }
        return null;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            GlobalInfo.getInstance().getUserData();
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }
}