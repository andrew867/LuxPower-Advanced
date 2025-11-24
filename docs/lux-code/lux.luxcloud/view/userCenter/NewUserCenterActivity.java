package com.lux.luxcloud.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class NewUserCenterActivity extends Activity {
    public static NewUserCenterActivity instance;
    private TextView activityOnlineDays;
    private TextView customerCode;
    private TextView customerCodeValue;
    private boolean isDarkTheme;
    private ConstraintLayout newSettingPageLayout;
    private ConstraintLayout notificationLayout;
    private ConstraintLayout octopusChargePageLayout;
    private ConstraintLayout settingsLayout;
    private ConstraintLayout userAccountLayout;
    private TextView userName;
    private ConstraintLayout weatherOptimizePageLayout;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_user_center);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.login_container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.instance.finish();
            }
        });
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_userAccount_Layout);
        this.userAccountLayout = constraintLayout;
        constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) UserCenterActivity.class));
            }
        });
        ConstraintLayout constraintLayout2 = (ConstraintLayout) findViewById(R.id.activity_setting_layout);
        this.settingsLayout = constraintLayout2;
        constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) UserCenterActivity.class));
            }
        });
        ConstraintLayout constraintLayout3 = (ConstraintLayout) findViewById(R.id.activity_notification_layout);
        this.notificationLayout = constraintLayout3;
        constraintLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) ManageNotificationActivity.class));
            }
        });
        ConstraintLayout constraintLayout4 = (ConstraintLayout) findViewById(R.id.use_newSettingPage_layout);
        this.newSettingPageLayout = constraintLayout4;
        constraintLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) NormalSettingActivity.class));
            }
        });
        ConstraintLayout constraintLayout5 = (ConstraintLayout) findViewById(R.id.weather_optimizePage_layout);
        this.weatherOptimizePageLayout = constraintLayout5;
        constraintLayout5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) WeatherOptimizeActivity.class));
            }
        });
        ConstraintLayout constraintLayout6 = (ConstraintLayout) findViewById(R.id.octopus_chargePage_layout);
        this.octopusChargePageLayout = constraintLayout6;
        constraintLayout6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.NewUserCenterActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) OctopusChargeActivity.class));
            }
        });
    }

    private void updateVisibilityBasedOnRole(UserData userData) {
        if (userData != null && ROLE.VIEWER.equals(userData.getRole())) {
            this.notificationLayout.setVisibility(0);
            if (!userData.isAllowViewerVisitOptimalSet()) {
                this.octopusChargePageLayout.setVisibility(8);
            }
            if (userData.isAllowViewerVisitWeatherSet() || userData.getClusterId() == 4) {
                return;
            }
            this.weatherOptimizePageLayout.setVisibility(8);
            return;
        }
        this.notificationLayout.setVisibility(8);
        this.octopusChargePageLayout.setVisibility(8);
        this.weatherOptimizePageLayout.setVisibility(8);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        UserData userData = GlobalInfo.getInstance().getUserData();
        TextView textView = (TextView) findViewById(R.id.activity_userName);
        this.userName = textView;
        textView.setText(userData.getUsername());
        this.customerCodeValue = (TextView) findViewById(R.id.activity_customer_code_value);
        this.customerCode = (TextView) findViewById(R.id.activity_customer_code);
        if (!Tool.isEmpty(userData.getCustomerCode()) && (userData.getRole().getOwnerLevelCheck() || userData.getRole().getInstallerLevelCheck())) {
            this.customerCode.setVisibility(0);
            this.customerCodeValue.setVisibility(0);
            this.customerCodeValue.setText(!Tool.isEmpty(userData.getCustomerCode()) ? userData.getCustomerCode() : "");
        }
        this.activityOnlineDays = (TextView) findViewById(R.id.activity_onlineDays);
        this.activityOnlineDays.setText(instance.getString(R.string.online_for_days, new Object[]{String.valueOf(userData.getUserCreatedDays())}));
        updateVisibilityBasedOnRole(userData);
    }
}