package com.nfcx.eg4.view.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.global.custom.android.dialog.CustomTimePickerDialog;
import com.nfcx.eg4.global.custom.android.dialog.DayDatePickerDialog;
import com.nfcx.eg4.global.custom.android.dialog.DayOnDateSetListener;
import com.nfcx.eg4.global.custom.android.dialog.MonthDatePickerDialog;
import com.nfcx.eg4.global.custom.android.dialog.MonthOnDateSetListener;
import com.nfcx.eg4.global.custom.android.dialog.TimePickerSetListener;
import com.nfcx.eg4.global.custom.android.dialog.YearDatePickerDialog;
import com.nfcx.eg4.global.custom.android.dialog.YearOnDateSetListener;
import com.nfcx.eg4.tool.StatusBarFontUtil;
import com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment;
import com.nfcx.eg4.view.main.fragment.lv1.Lv112KRemoteSetFragment;
import com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment;
import com.nfcx.eg4.view.main.fragment.lv1.Lv1EventFragment;
import com.nfcx.eg4.view.main.fragment.lv1.Lv1OffGridRemoteSetFragment;
import com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment;
import com.nfcx.eg4.view.main.fragment.lv1.Lv1RemoteSetFragment;
import com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class MainActivity extends AppCompatActivity {
    public static final String USER_INFO = "userInfo";
    public static MainActivity instance;
    private int deviceType;
    private AbstractItemFragment[] fragmentList;
    private FragmentManager fragmentManager;
    private boolean isDarkTheme;
    private Lv112KRemoteSetFragment lv112KRemoteSetFragment;
    private Lv1DataFragment lv1DataFragment;
    private Lv1EventFragment lv1EventFragment;
    private Lv1OffGridRemoteSetFragment lv1OffGridRemoteSetFragment;
    private Lv1OverviewFragment lv1OverviewFragment;
    private Lv1RemoteSetFragment lv1RemoteSetFragment;
    private Lv2RemoteSetFragment lv2RemoteSetFragment;
    private int mFragment = 0;
    private int toFragment = 0;
    private Boolean useNewSettingPage;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        StatusBarFontUtil.setStatusBarTextColor(this, !((getResources().getConfiguration().uiMode & 48) == 32));
        instance = this;
        getSupportActionBar().hide();
        this.lv1OverviewFragment = new Lv1OverviewFragment();
        this.lv1DataFragment = new Lv1DataFragment();
        this.lv1EventFragment = new Lv1EventFragment();
        this.lv1RemoteSetFragment = new Lv1RemoteSetFragment();
        this.lv2RemoteSetFragment = new Lv2RemoteSetFragment();
        this.lv1OffGridRemoteSetFragment = new Lv1OffGridRemoteSetFragment();
        this.lv112KRemoteSetFragment = new Lv112KRemoteSetFragment();
        Boolean boolValueOf = Boolean.valueOf(getSharedPreferences("userInfo", 0).getBoolean(Constants.useNewSettingPage, true));
        this.useNewSettingPage = boolValueOf;
        AbstractItemFragment[] abstractItemFragmentArr = new AbstractItemFragment[4];
        abstractItemFragmentArr[0] = this.lv1OverviewFragment;
        abstractItemFragmentArr[1] = this.lv1DataFragment;
        abstractItemFragmentArr[2] = this.lv1EventFragment;
        abstractItemFragmentArr[3] = boolValueOf.booleanValue() ? this.lv2RemoteSetFragment : this.lv1RemoteSetFragment;
        this.fragmentList = abstractItemFragmentArr;
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        if (GlobalInfo.getInstance().getUserData().isReadonly()) {
            bottomNavigationView.getMenu().removeItem(R.id.navigation_set);
        }
        this.fragmentManager = getSupportFragmentManager();
        NavigationUI.setupWithNavController(bottomNavigationView, Navigation.findNavController(this, R.id.nav_host_fragment_activity_main));
        this.lv1OverviewFragment.isAdded = true;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.main.MainActivity.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                FragmentTransaction fragmentTransactionBeginTransaction = MainActivity.this.fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_data /* 2131231842 */:
                        MainActivity.this.toFragment = 1;
                        MainActivity.this.switchFragment(fragmentTransactionBeginTransaction);
                        MainActivity.this.mFragment = 1;
                        return true;
                    case R.id.navigation_header_container /* 2131231843 */:
                    case R.id.navigation_menu_subheader_label /* 2131231844 */:
                    default:
                        return true;
                    case R.id.navigation_monitor /* 2131231845 */:
                        MainActivity.this.toFragment = 2;
                        MainActivity.this.switchFragment(fragmentTransactionBeginTransaction);
                        MainActivity.this.mFragment = 2;
                        return true;
                    case R.id.navigation_overview /* 2131231846 */:
                        MainActivity.this.toFragment = 0;
                        MainActivity.this.switchFragment(fragmentTransactionBeginTransaction);
                        MainActivity.this.mFragment = 0;
                        MainActivity.this.lv1OverviewFragment.refreshFragmentParams();
                        MainActivity.this.lv1OverviewFragment.m557x84f0eabb();
                        return true;
                    case R.id.navigation_set /* 2131231847 */:
                        MainActivity.this.toFragment = 3;
                        MainActivity.this.switchFragment(fragmentTransactionBeginTransaction);
                        MainActivity.this.mFragment = 3;
                        return true;
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        System.out.println("Eg4 - MainActivity onResume...");
        if (this.lv1OverviewFragment != null && this.mFragment == 0 && System.currentTimeMillis() - this.lv1OverviewFragment.getLastRefreshDataTime() > 30000) {
            this.lv1OverviewFragment.refreshData();
        }
        this.useNewSettingPage = Boolean.valueOf(getSharedPreferences("userInfo", 0).getBoolean(Constants.useNewSettingPage, true));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void switchRemoteSetFragment(int r6) {
        /*
            r5 = this;
            int r0 = r5.deviceType
            if (r0 == r6) goto L56
            r5.deviceType = r6
            int r0 = r5.mFragment
            r1 = 3
            if (r0 != r1) goto L10
            com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment[] r0 = r5.fragmentList
            r0 = r0[r1]
            goto L11
        L10:
            r0 = 0
        L11:
            java.lang.String r2 = "userInfo"
            r3 = 0
            android.content.SharedPreferences r2 = r5.getSharedPreferences(r2, r3)
            java.lang.String r3 = com.nfcx.eg4.global.Constants.useNewSettingPage
            r4 = 1
            boolean r2 = r2.getBoolean(r3, r4)
            if (r2 == 0) goto L28
            com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment[] r6 = r5.fragmentList
            com.nfcx.eg4.view.main.fragment.lv2.Lv2RemoteSetFragment r2 = r5.lv2RemoteSetFragment
            r6[r1] = r2
            goto L45
        L28:
            if (r6 == r1) goto L3f
            r2 = 6
            if (r6 == r2) goto L38
            r2 = 11
            if (r6 == r2) goto L3f
            com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment[] r6 = r5.fragmentList
            com.nfcx.eg4.view.main.fragment.lv1.Lv1RemoteSetFragment r2 = r5.lv1RemoteSetFragment
            r6[r1] = r2
            goto L45
        L38:
            com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment[] r6 = r5.fragmentList
            com.nfcx.eg4.view.main.fragment.lv1.Lv112KRemoteSetFragment r2 = r5.lv112KRemoteSetFragment
            r6[r1] = r2
            goto L45
        L3f:
            com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment[] r6 = r5.fragmentList
            com.nfcx.eg4.view.main.fragment.lv1.Lv1OffGridRemoteSetFragment r2 = r5.lv1OffGridRemoteSetFragment
            r6[r1] = r2
        L45:
            int r6 = r5.mFragment
            if (r6 != r1) goto L56
            com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment[] r6 = r5.fragmentList
            r6 = r6[r1]
            androidx.fragment.app.FragmentManager r1 = r5.fragmentManager
            androidx.fragment.app.FragmentTransaction r1 = r1.beginTransaction()
            r5.switchFragment(r1, r0, r6)
        L56:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.MainActivity.switchRemoteSetFragment(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchFragment(FragmentTransaction fragmentTransaction) {
        AbstractItemFragment[] abstractItemFragmentArr = this.fragmentList;
        int i = this.mFragment;
        AbstractItemFragment abstractItemFragment = abstractItemFragmentArr[i];
        int i2 = this.toFragment;
        AbstractItemFragment abstractItemFragment2 = abstractItemFragmentArr[i2];
        if (i == i2) {
            return;
        }
        switchFragment(fragmentTransaction, abstractItemFragment, abstractItemFragment2);
    }

    private void switchFragment(FragmentTransaction fragmentTransaction, AbstractItemFragment abstractItemFragment, AbstractItemFragment abstractItemFragment2) {
        if (abstractItemFragment.isAdded) {
            if (abstractItemFragment2.isAdded) {
                fragmentTransaction.hide(abstractItemFragment).show(abstractItemFragment2).commit();
                return;
            } else {
                fragmentTransaction.add(R.id.nav_host_fragment_activity_main, abstractItemFragment2).hide(abstractItemFragment).show(abstractItemFragment2).commit();
                abstractItemFragment2.isAdded = true;
                return;
            }
        }
        if (abstractItemFragment2.isAdded) {
            fragmentTransaction.add(R.id.nav_host_fragment_activity_main, abstractItemFragment).hide(abstractItemFragment).show(abstractItemFragment2).commit();
        } else {
            fragmentTransaction.add(R.id.nav_host_fragment_activity_main, abstractItemFragment).add(R.id.nav_host_fragment_activity_main, abstractItemFragment2).hide(abstractItemFragment).show(abstractItemFragment2).commit();
            abstractItemFragment2.isAdded = true;
        }
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int i) {
        EditText timeDateEditText;
        EditText timeTimeEditText;
        if (i == 0) {
            EditText powerLineChartTimeEditText = this.lv1DataFragment.getPowerLineChartTimeEditText();
            if (powerLineChartTimeEditText != null) {
                return new DayDatePickerDialog(this, new DayOnDateSetListener(powerLineChartTimeEditText), powerLineChartTimeEditText.getText().toString());
            }
            return null;
        }
        if (i == 1) {
            EditText energyChartTimeEditText = this.lv1DataFragment.getEnergyChartTimeEditText();
            if (energyChartTimeEditText != null) {
                return new MonthDatePickerDialog(this, new MonthOnDateSetListener(energyChartTimeEditText), energyChartTimeEditText.getText().toString());
            }
            return null;
        }
        if (i == 2) {
            EditText energyChartTimeEditText2 = this.lv1DataFragment.getEnergyChartTimeEditText();
            if (energyChartTimeEditText2 != null) {
                return new YearDatePickerDialog(this, new YearOnDateSetListener(energyChartTimeEditText2), energyChartTimeEditText2.getText().toString());
            }
            return null;
        }
        if (i == 6) {
            int i2 = this.deviceType;
            if (i2 == 6) {
                timeDateEditText = this.lv112KRemoteSetFragment.getTimeDateEditText();
            } else if (i2 == 3) {
                timeDateEditText = this.lv1OffGridRemoteSetFragment.getTimeDateEditText();
            } else {
                timeDateEditText = this.lv1RemoteSetFragment.getTimeDateEditText();
            }
            if (timeDateEditText != null) {
                return new DayDatePickerDialog(this, new DayOnDateSetListener(timeDateEditText), timeDateEditText.getText().toString());
            }
            return null;
        }
        if (i != 7) {
            return null;
        }
        int i3 = this.deviceType;
        if (i3 == 6) {
            timeTimeEditText = this.lv112KRemoteSetFragment.getTimeTimeEditText();
        } else if (i3 == 3) {
            timeTimeEditText = this.lv1OffGridRemoteSetFragment.getTimeTimeEditText();
        } else {
            timeTimeEditText = this.lv1RemoteSetFragment.getTimeTimeEditText();
        }
        if (timeTimeEditText != null) {
            return new CustomTimePickerDialog(this, new TimePickerSetListener(timeTimeEditText), timeTimeEditText.getText().toString());
        }
        return null;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.lv2RemoteSetFragment.canGoBackInWebView()) {
            this.lv2RemoteSetFragment.goBackInWebView();
            return;
        }
        startActivity(new Intent(this, (Class<?>) (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
        finish();
        MainActivity mainActivity = instance;
        if (mainActivity != null) {
            mainActivity.finish();
        }
    }
}