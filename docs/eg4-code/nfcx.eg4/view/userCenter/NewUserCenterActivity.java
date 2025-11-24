package com.nfcx.eg4.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.tool.StatusBarUtil;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class NewUserCenterActivity extends Activity {
    public static NewUserCenterActivity instance;
    private TextView activityOnlineDays;
    private boolean isDarkTheme;
    private ConstraintLayout newSettingPageLayout;
    private ConstraintLayout notificationLayout;
    private ConstraintLayout settingsLayout;
    private ConstraintLayout userAccountLayout;
    private TextView userName;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_user_center);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.NewUserCenterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.instance.finish();
            }
        });
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_userAccount_Layout);
        this.userAccountLayout = constraintLayout;
        constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.NewUserCenterActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) UserCenterActivity.class));
            }
        });
        ConstraintLayout constraintLayout2 = (ConstraintLayout) findViewById(R.id.activity_setting_layout);
        this.settingsLayout = constraintLayout2;
        constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.NewUserCenterActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) UserCenterActivity.class));
            }
        });
        ConstraintLayout constraintLayout3 = (ConstraintLayout) findViewById(R.id.activity_notification_layout);
        this.notificationLayout = constraintLayout3;
        constraintLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.NewUserCenterActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) ManageNotificationActivity.class));
            }
        });
        ConstraintLayout constraintLayout4 = (ConstraintLayout) findViewById(R.id.use_newSettingPage_layout);
        this.newSettingPageLayout = constraintLayout4;
        constraintLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.NewUserCenterActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NewUserCenterActivity.this.startActivity(new Intent(NewUserCenterActivity.instance, (Class<?>) NormalSettingActivity.class));
            }
        });
    }

    private void updateVisibilityBasedOnRole() {
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (userData != null && ROLE.VIEWER.equals(userData.getRole())) {
            this.notificationLayout.setVisibility(0);
        } else {
            this.notificationLayout.setVisibility(8);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        UserData userData = GlobalInfo.getInstance().getUserData();
        TextView textView = (TextView) findViewById(R.id.activity_userName);
        this.userName = textView;
        textView.setText(userData.getUsername());
        this.activityOnlineDays = (TextView) findViewById(R.id.activity_onlineDays);
        this.activityOnlineDays.setText(instance.getString(R.string.online_for_days, new Object[]{String.valueOf(userData.getUserCreatedDays())}));
        updateVisibilityBasedOnRole();
    }
}