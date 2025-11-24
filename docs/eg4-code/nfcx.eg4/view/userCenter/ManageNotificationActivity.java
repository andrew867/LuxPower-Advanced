package com.nfcx.eg4.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ManageNotificationActivity extends Activity {
    private EditText BattLowNoticeSocEditText;
    private ConstraintLayout BattLowNoticeSocNotificationLayout;
    private ConstraintLayout BattSocLowNotificationLayout;
    private ConstraintLayout allowNotificationsLayout;
    private ToggleButton allowNotificationsToggleButton;
    private Button battLowNoticeSocButton;
    private ToggleButton battSocLowNotificationsToggleButton;
    private ConstraintLayout gridOutageNotificationLayout;
    private ToggleButton gridOutageNotificationToggleButton;
    private boolean isDarkTheme;
    private TextView notificationTypeText;
    private ConstraintLayout octopusTOUNotificationsLayout;
    private ToggleButton octopusTOUNotificationsToggleButton;
    private ConstraintLayout updatesNotificationLayout;
    private ToggleButton updatesNotificationToggleButton;
    private ConstraintLayout weatherUpdatesNotificationLayout;
    private ToggleButton weatherUpdatesNotificationToggleButton;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_manage_notifications);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        initializeViews();
        setupListeners();
        updateVisibilityBasedOnRole();
    }

    private void initializeViews() {
        this.allowNotificationsLayout = (ConstraintLayout) findViewById(R.id.activity_Allow_NotificationsLayout);
        this.notificationTypeText = (TextView) findViewById(R.id.notification_type);
        this.octopusTOUNotificationsLayout = (ConstraintLayout) findViewById(R.id.activity_Octopus_TOU_NotificationsLayout);
        this.weatherUpdatesNotificationLayout = (ConstraintLayout) findViewById(R.id.activity_Weather_Updates_NotificationLayout);
        this.gridOutageNotificationLayout = (ConstraintLayout) findViewById(R.id.activity_Grid_Outage_NotificationLayout);
        this.BattSocLowNotificationLayout = (ConstraintLayout) findViewById(R.id.activity_BattSocLow_NotificationLayout);
        this.BattLowNoticeSocNotificationLayout = (ConstraintLayout) findViewById(R.id.activity_BattLowNoticeSoc_NotificationLayout);
        this.updatesNotificationLayout = (ConstraintLayout) findViewById(R.id.activity_Updates_NotificationLayout);
        this.allowNotificationsToggleButton = (ToggleButton) findViewById(R.id.activity_Allow_Notifications_toggleButton);
        this.octopusTOUNotificationsToggleButton = (ToggleButton) findViewById(R.id.activity_Octopus_TOU_Notifications_toggleButton);
        this.weatherUpdatesNotificationToggleButton = (ToggleButton) findViewById(R.id.activity_Weather_Updates_Notification_toggleButton);
        this.gridOutageNotificationToggleButton = (ToggleButton) findViewById(R.id.activity_Grid_Outage_Notification_toggleButton);
        this.battSocLowNotificationsToggleButton = (ToggleButton) findViewById(R.id.activity_NoticeBattSocLow_toggleButton);
        this.updatesNotificationToggleButton = (ToggleButton) findViewById(R.id.activity_Updates_NotificationLayout_toggleButton);
        this.BattLowNoticeSocEditText = (EditText) findViewById(R.id.activity_BattLowNoticeSoc_NotificationEditText);
        Button button = (Button) findViewById(R.id.activity_BattLowNoticeSoc_Notification_Button);
        this.battLowNoticeSocButton = button;
        button.setEnabled(false);
    }

    private void setupListeners() {
        findViewById(R.id.backImageViewLayout).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m640x11c43d1a(view);
            }
        });
        this.allowNotificationsLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.openNotificationSettings(view);
            }
        });
        this.allowNotificationsToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.openNotificationSettings(view);
            }
        });
        this.BattLowNoticeSocEditText.addTextChangedListener(new AnonymousClass1());
        this.battSocLowNotificationsToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m641x3758461b(view);
            }
        });
        this.battLowNoticeSocButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m642x5cec4f1c(view);
            }
        });
        this.gridOutageNotificationToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m643x8280581d(view);
            }
        });
        this.octopusTOUNotificationsToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m644xa814611e(view);
            }
        });
        this.weatherUpdatesNotificationToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m645xcda86a1f(view);
            }
        });
        this.updatesNotificationToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m646xf33c7320(view);
            }
        });
    }

    /* renamed from: lambda$setupListeners$0$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m640x11c43d1a(View view) {
        finish();
    }

    /* renamed from: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$1, reason: invalid class name */
    class AnonymousClass1 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        AnonymousClass1() {
        }

        /* renamed from: lambda$onTextChanged$0$com-nfcx-eg4-view-userCenter-ManageNotificationActivity$1, reason: not valid java name */
        /* synthetic */ void m649x701cc5b3(CharSequence charSequence) {
            ManageNotificationActivity.this.battLowNoticeSocButton.setEnabled(charSequence.length() > 0);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(final CharSequence charSequence, int i, int i2, int i3) {
            ManageNotificationActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m649x701cc5b3(charSequence);
                }
            });
        }
    }

    /* renamed from: lambda$setupListeners$1$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m641x3758461b(View view) {
        saveNotificationSettings();
    }

    /* renamed from: lambda$setupListeners$2$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m642x5cec4f1c(View view) {
        saveNotificationSettings();
    }

    /* renamed from: lambda$setupListeners$3$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m643x8280581d(View view) {
        saveNotificationSettings();
    }

    /* renamed from: lambda$setupListeners$4$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m644xa814611e(View view) {
        saveNotificationSettings();
    }

    /* renamed from: lambda$setupListeners$5$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m645xcda86a1f(View view) {
        saveNotificationSettings();
    }

    /* renamed from: lambda$setupListeners$6$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m646xf33c7320(View view) {
        Tool.alert(this, "UpdatesNotificationToggleButton");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openNotificationSettings(View view) {
        Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        startActivity(intent);
    }

    private void updateVisibilityBasedOnRole() {
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (!ROLE.VIEWER.equals(userData.getRole())) {
            this.notificationTypeText.setVisibility(8);
            this.BattSocLowNotificationLayout.setVisibility(8);
            this.BattLowNoticeSocNotificationLayout.setVisibility(8);
            this.octopusTOUNotificationsLayout.setVisibility(8);
            this.weatherUpdatesNotificationLayout.setVisibility(8);
            this.gridOutageNotificationLayout.setVisibility(8);
            return;
        }
        if (userData.isAllowViewerVisitWeatherSet() || userData.getClusterId() == 4) {
            return;
        }
        this.weatherUpdatesNotificationLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchNotificationSettings() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m635x9fd113cd();
            }
        }).start();
    }

    /* renamed from: lambda$fetchNotificationSettings$7$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m635x9fd113cd() {
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateNotificationToggleButtons();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNotificationToggleButtons() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m648xd6c94de0();
            }
        }).start();
    }

    /* renamed from: lambda$updateNotificationToggleButtons$9$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m648xd6c94de0() throws JSONException {
        UserData userData = GlobalInfo.getInstance().getUserData();
        HashMap map = new HashMap();
        map.put("userId", String.valueOf(userData.getUserId()));
        JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/user/getUserAppNoticeInfo", map);
        if (jSONObjectPostJson != null) {
            try {
                if (jSONObjectPostJson.getBoolean("success")) {
                    boolean z = jSONObjectPostJson.getBoolean("noticeAppWarning");
                    boolean z2 = jSONObjectPostJson.getBoolean("noticeOctopusSet");
                    boolean z3 = jSONObjectPostJson.getBoolean("noticeWeatherSet");
                    boolean z4 = jSONObjectPostJson.getBoolean("noticeBattSocLow");
                    int i = jSONObjectPostJson.getInt("battLowNoticeSoc");
                    this.battSocLowNotificationsToggleButton.setChecked(z4);
                    this.gridOutageNotificationToggleButton.setChecked(z);
                    this.octopusTOUNotificationsToggleButton.setChecked(z2);
                    this.weatherUpdatesNotificationToggleButton.setChecked(z3);
                    this.BattLowNoticeSocEditText.setText(i + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m647xb13544df();
            }
        });
    }

    /* renamed from: lambda$updateNotificationToggleButtons$8$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m647xb13544df() {
        this.battLowNoticeSocButton.setEnabled(false);
    }

    private void saveNotificationSettings() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m639x78cd104a();
            }
        }).start();
    }

    /* renamed from: lambda$saveNotificationSettings$10$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m639x78cd104a() {
        UserData userData = GlobalInfo.getInstance().getUserData();
        HashMap map = new HashMap();
        map.put("userId", String.valueOf(userData.getUserId()));
        map.put("noticeAppFault", "false");
        map.put("noticeBattSocLow", String.valueOf(this.battSocLowNotificationsToggleButton.isChecked()));
        map.put("battLowNoticeSoc", this.BattLowNoticeSocEditText.getText().toString().trim());
        map.put("noticeAppWarning", String.valueOf(this.gridOutageNotificationToggleButton.isChecked()));
        map.put("noticeOctopusSet", String.valueOf(this.octopusTOUNotificationsToggleButton.isChecked()));
        map.put("noticeWeatherSet", String.valueOf(this.weatherUpdatesNotificationToggleButton.isChecked()));
        handleSaveResponse(HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/user/saveUserAppNoticeInfo", map));
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0013 A[Catch: JSONException -> 0x0024, TRY_LEAVE, TryCatch #0 {JSONException -> 0x0024, blocks: (B:3:0x0002, B:5:0x000a, B:6:0x0013), top: B:11:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleSaveResponse(org.json.JSONObject r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L13
            java.lang.String r0 = "success"
            boolean r2 = r2.getBoolean(r0)     // Catch: org.json.JSONException -> L24
            if (r2 == 0) goto L13
            com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda8 r2 = new com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda8     // Catch: org.json.JSONException -> L24
            r2.<init>()     // Catch: org.json.JSONException -> L24
            r1.runOnUiThread(r2)     // Catch: org.json.JSONException -> L24
            goto L2c
        L13:
            com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda9 r2 = new com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda9     // Catch: org.json.JSONException -> L24
            r2.<init>()     // Catch: org.json.JSONException -> L24
            r1.runOnUiThread(r2)     // Catch: org.json.JSONException -> L24
            com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda10 r2 = new com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda10     // Catch: org.json.JSONException -> L24
            r2.<init>()     // Catch: org.json.JSONException -> L24
            r1.runOnUiThread(r2)     // Catch: org.json.JSONException -> L24
            goto L2c
        L24:
            com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda11 r2 = new com.nfcx.eg4.view.userCenter.ManageNotificationActivity$$ExternalSyntheticLambda11
            r2.<init>()
            r1.runOnUiThread(r2)
        L2c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.userCenter.ManageNotificationActivity.handleSaveResponse(org.json.JSONObject):void");
    }

    /* renamed from: lambda$handleSaveResponse$11$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m636x28319ad0() {
        Tool.alert(this, getString(R.string.local_set_result_success));
        this.battLowNoticeSocButton.setEnabled(false);
    }

    /* renamed from: lambda$handleSaveResponse$12$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m637x4dc5a3d1() {
        Tool.alert(this, getString(R.string.local_set_result_failed));
    }

    /* renamed from: lambda$handleSaveResponse$13$com-nfcx-eg4-view-userCenter-ManageNotificationActivity, reason: not valid java name */
    /* synthetic */ void m638x7359acd2() {
        Toast.makeText(this, R.string.phrase_toast_network_error, 0).show();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        fetchNotificationSettings();
    }
}