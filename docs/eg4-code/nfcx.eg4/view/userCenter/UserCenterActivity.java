package com.nfcx.eg4.view.userCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.version.Custom;
import com.nfcx.eg4.view.logout.LogoutActivity;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class UserCenterActivity extends Activity {
    public static UserCenterActivity instance;
    private ConstraintLayout deleteUserActionLayout;
    private TextView deleteUserActionTextView;
    private ToggleButton deleteUserActionToggleButton;
    private Button deleteUserButton;
    private ConstraintLayout deleteUserCtrlLayout;
    private boolean isDarkTheme;
    private TextView techSupport1Label;
    private TextView techSupport1Value;
    private TextView techSupport2Label;
    private ConstraintLayout techSupport2Layout;
    private TextView techSupport2Value;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws JSONException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_user_center);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.UserCenterActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserCenterActivity.instance.finish();
            }
        });
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (!PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        this.deleteUserCtrlLayout = (ConstraintLayout) findViewById(R.id.deleteUserCtrlLayout);
        this.deleteUserButton = (Button) findViewById(R.id.deleteUserButton);
        this.deleteUserActionLayout = (ConstraintLayout) findViewById(R.id.label_deleteUser_set_layout);
        this.deleteUserActionTextView = (TextView) findViewById(R.id.label_deleteUser_set_textView);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.label_deleteUser_set_toggleButton);
        this.deleteUserActionToggleButton = toggleButton;
        toggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.UserCenterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (UserCenterActivity.this.deleteUserActionToggleButton.isChecked()) {
                    UserCenterActivity.this.deleteUserActionTextView.setText(R.string.phrase_button_collapse);
                    UserCenterActivity.this.deleteUserButton.setVisibility(0);
                } else {
                    UserCenterActivity.this.deleteUserActionTextView.setText(R.string.phrase_button_expand);
                    UserCenterActivity.this.deleteUserButton.setVisibility(8);
                }
            }
        });
        this.deleteUserActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.UserCenterActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (UserCenterActivity.this.deleteUserActionToggleButton.isChecked()) {
                    UserCenterActivity.this.deleteUserActionToggleButton.setChecked(false);
                    UserCenterActivity.this.deleteUserActionTextView.setText(R.string.phrase_button_expand);
                    UserCenterActivity.this.deleteUserButton.setVisibility(8);
                } else {
                    UserCenterActivity.this.deleteUserActionToggleButton.setChecked(true);
                    UserCenterActivity.this.deleteUserActionTextView.setText(R.string.phrase_button_collapse);
                    UserCenterActivity.this.deleteUserButton.setVisibility(0);
                }
            }
        });
        if (ROLE.VIEWER.equals(userData.getRole())) {
            this.deleteUserCtrlLayout.setVisibility(0);
        }
        initTechSupportInfo(userData.getTechInfo());
    }

    private void initTechSupportInfo(JSONObject jSONObject) throws JSONException {
        this.techSupport1Label = (TextView) findViewById(R.id.techSupport1Label);
        this.techSupport1Value = (TextView) findViewById(R.id.techSupport1Value);
        this.techSupport2Label = (TextView) findViewById(R.id.techSupport2Label);
        this.techSupport2Value = (TextView) findViewById(R.id.techSupport2Value);
        this.techSupport2Layout = (ConstraintLayout) findViewById(R.id.techSupport2Layout);
        if (jSONObject != null && jSONObject.has("techInfoCount")) {
            try {
                int i = jSONObject.getInt("techInfoCount");
                if (i > 0) {
                    this.techSupport2Label.setText(getString(R.string.phrase_tech_support) + " 2");
                    this.techSupport1Value.setText(Tool.getTechSupportPrefix(instance, jSONObject, 1) + jSONObject.getString("techInfo1"));
                    if (i == 1) {
                        this.techSupport1Label.setText(getString(R.string.phrase_tech_support));
                        this.techSupport2Value.setText("");
                        this.techSupport2Layout.setVisibility(4);
                        return;
                    } else {
                        this.techSupport1Label.setText(getString(R.string.phrase_tech_support) + " 1");
                        this.techSupport2Value.setText(Tool.getTechSupportPrefix(instance, jSONObject, 2) + jSONObject.getString("techInfo2"));
                        this.techSupport2Layout.setVisibility(0);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.techSupport1Label.setText(R.string.user_center_manufacturer_email);
        this.techSupport1Value.setText(Custom.MANUFACTURER_EMAIL);
        this.techSupport2Label.setText(R.string.user_center_manufacturer_tel);
        this.techSupport2Value.setText(Custom.MANUFACTURER_TEL);
        this.techSupport2Layout.setVisibility(0);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        UserData userData = GlobalInfo.getInstance().getUserData();
        ((TextView) findViewById(R.id.user_center_username_textView)).setText(userData.getUsername());
        ((TextView) findViewById(R.id.user_center_realName_textView)).setText(userData.getRealName());
        ((TextView) findViewById(R.id.user_center_email_textView)).setText(userData.getEmail());
        ((TextView) findViewById(R.id.user_center_country_textView)).setText(userData.getCountryText());
        ((TextView) findViewById(R.id.user_center_timezone_textView)).setText(userData.getTimezoneText());
        ((TextView) findViewById(R.id.user_center_telNumber_textView)).setText(userData.getTelNumber());
        ((TextView) findViewById(R.id.user_center_address_textView)).setText(userData.getAddress());
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.user_center_allowRemoteSupportLayout);
        ((ToggleButton) findViewById(R.id.user_center_allowRemoteSupportToggleButton)).setChecked(userData.isAllowRemoteSupport());
        if (ROLE.VIEWER.equals(userData.getRole())) {
            constraintLayout.setVisibility(0);
        } else {
            constraintLayout.setVisibility(8);
        }
    }

    public void clickEditUserButton(View view) {
        startActivity(new Intent(this, (Class<?>) EditUserActivity.class));
    }

    public void clickModifyPasswordButton(View view) {
        startActivity(new Intent(this, (Class<?>) ModifyPasswordActivity.class));
    }

    public void clickLogoutButton(View view) {
        startActivity(new Intent(this, (Class<?>) LogoutActivity.class));
    }

    public void clickDeleteUserButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.phrase_delete_user).setIcon(android.R.drawable.ic_dialog_info).setMessage(R.string.phrase_delete_user_text).setPositiveButton(R.string.phrase_button_delete, new AnonymousClass3()).setNegativeButton(R.string.phrase_button_cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.show();
        Button button = alertDialogCreate.getButton(-1);
        if (button != null) {
            button.setTextColor(getResources().getColor(R.color.mainGray));
        }
    }

    /* renamed from: com.nfcx.eg4.view.userCenter.UserCenterActivity$3, reason: invalid class name */
    class AnonymousClass3 implements DialogInterface.OnClickListener {
        AnonymousClass3() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.UserCenterActivity$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m664xb337acc7();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$1$com-nfcx-eg4-view-userCenter-UserCenterActivity$3, reason: not valid java name */
        /* synthetic */ void m664xb337acc7() {
            HashMap map = new HashMap();
            map.put("userId", String.valueOf(GlobalInfo.getInstance().getUserData().getUserId()));
            final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/system/user/remove", map);
            UserCenterActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.userCenter.UserCenterActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m663x6fac8f06(jSONObjectPostJson);
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x0035 A[Catch: Exception -> 0x003e, TRY_LEAVE, TryCatch #0 {Exception -> 0x003e, blocks: (B:3:0x0002, B:5:0x000a, B:6:0x0035), top: B:12:0x0002 }] */
        /* renamed from: lambda$onClick$0$com-nfcx-eg4-view-userCenter-UserCenterActivity$3, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        /* synthetic */ void m663x6fac8f06(org.json.JSONObject r4) {
            /*
                r3 = this;
                if (r4 == 0) goto L35
                java.lang.String r0 = "success"
                boolean r4 = r4.getBoolean(r0)     // Catch: java.lang.Exception -> L3e
                if (r4 == 0) goto L35
                android.app.AlertDialog$Builder r4 = new android.app.AlertDialog$Builder     // Catch: java.lang.Exception -> L3e
                com.nfcx.eg4.view.userCenter.UserCenterActivity r0 = com.nfcx.eg4.view.userCenter.UserCenterActivity.this     // Catch: java.lang.Exception -> L3e
                r4.<init>(r0)     // Catch: java.lang.Exception -> L3e
                r0 = 2131886774(0x7f1202b6, float:1.9408136E38)
                android.app.AlertDialog$Builder r0 = r4.setTitle(r0)     // Catch: java.lang.Exception -> L3e
                r1 = 17301659(0x108009b, float:2.497969E-38)
                android.app.AlertDialog$Builder r0 = r0.setIcon(r1)     // Catch: java.lang.Exception -> L3e
                r1 = 2131886672(0x7f120250, float:1.940793E38)
                android.app.AlertDialog$Builder r0 = r0.setMessage(r1)     // Catch: java.lang.Exception -> L3e
                com.nfcx.eg4.view.userCenter.UserCenterActivity$3$1 r1 = new com.nfcx.eg4.view.userCenter.UserCenterActivity$3$1     // Catch: java.lang.Exception -> L3e
                r1.<init>()     // Catch: java.lang.Exception -> L3e
                r2 = 2131886660(0x7f120244, float:1.9407905E38)
                r0.setNegativeButton(r2, r1)     // Catch: java.lang.Exception -> L3e
                r4.show()     // Catch: java.lang.Exception -> L3e
                goto L4a
            L35:
                com.nfcx.eg4.view.userCenter.UserCenterActivity r4 = com.nfcx.eg4.view.userCenter.UserCenterActivity.this     // Catch: java.lang.Exception -> L3e
                r0 = 2131887062(0x7f1203d6, float:1.940872E38)
                com.nfcx.eg4.tool.Tool.alert(r4, r0)     // Catch: java.lang.Exception -> L3e
                goto L4a
            L3e:
                r4 = move-exception
                com.nfcx.eg4.view.userCenter.UserCenterActivity r0 = com.nfcx.eg4.view.userCenter.UserCenterActivity.this
                r1 = 2131887060(0x7f1203d4, float:1.9408716E38)
                com.nfcx.eg4.tool.Tool.alert(r0, r1)
                r4.printStackTrace()
            L4a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.userCenter.UserCenterActivity.AnonymousClass3.m663x6fac8f06(org.json.JSONObject):void");
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }
}