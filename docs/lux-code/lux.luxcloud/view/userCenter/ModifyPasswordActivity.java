package com.lux.luxcloud.view.userCenter;

import android.app.Activity;
import android.app.UiModeManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.alibaba.fastjson2.internal.asm.Opcodes;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ModifyPasswordActivity extends Activity {
    public static ModifyPasswordActivity instance;
    private boolean isDarkTheme;
    private Button modifyPasswordButton;
    private EditText newPasswordEditText;
    private EditText oldPasswordEditText;
    private EditText repeatPasswordEditText;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_modify_password);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ModifyPasswordActivity.instance.finish();
            }
        });
        if (!GlobalInfo.getInstance().getUserData().needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        this.oldPasswordEditText = (EditText) findViewById(R.id.oldPasswordEditText);
        final ImageView imageView = (ImageView) findViewById(R.id.oldPassword_toggle);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m738x201e1607(imageView, view);
            }
        });
        this.newPasswordEditText = (EditText) findViewById(R.id.newPasswordEditText);
        final ImageView imageView2 = (ImageView) findViewById(R.id.newPassword_toggle);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m739x45b21f08(imageView2, view);
            }
        });
        this.repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
        final ImageView imageView3 = (ImageView) findViewById(R.id.repeatPassword_toggle);
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m740x6b462809(imageView3, view);
            }
        });
        this.modifyPasswordButton = (Button) findViewById(R.id.modifyPasswordButton);
    }

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-userCenter-ModifyPasswordActivity, reason: not valid java name */
    /* synthetic */ void m738x201e1607(ImageView imageView, View view) {
        if (this.oldPasswordEditText.getInputType() == 129) {
            this.oldPasswordEditText.setInputType(145);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.oldPasswordEditText.setInputType(Opcodes.LOR);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
    }

    /* renamed from: lambda$onCreate$2$com-lux-luxcloud-view-userCenter-ModifyPasswordActivity, reason: not valid java name */
    /* synthetic */ void m739x45b21f08(ImageView imageView, View view) {
        if (this.newPasswordEditText.getInputType() == 129) {
            this.newPasswordEditText.setInputType(145);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.newPasswordEditText.setInputType(Opcodes.LOR);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
    }

    /* renamed from: lambda$onCreate$3$com-lux-luxcloud-view-userCenter-ModifyPasswordActivity, reason: not valid java name */
    /* synthetic */ void m740x6b462809(ImageView imageView, View view) {
        if (this.repeatPasswordEditText.getInputType() == 129) {
            this.repeatPasswordEditText.setInputType(145);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.repeatPasswordEditText.setInputType(Opcodes.LOR);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
    }

    public void clickModifyPasswordButton(View view) {
        String string = this.oldPasswordEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.page_register_error_old_password_empty);
            this.oldPasswordEditText.requestFocus();
            return;
        }
        if (string.length() > 50) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.login_edit_text_password_hint), 50}));
            this.oldPasswordEditText.requestFocus();
            return;
        }
        String string2 = this.newPasswordEditText.getText().toString();
        if (Tool.isEmpty(string2)) {
            Tool.alert(this, R.string.page_register_error_new_password_empty);
            this.newPasswordEditText.requestFocus();
            return;
        }
        if (string2.length() < 8) {
            Tool.alert(this, R.string.page_register_error_new_password_minLength);
            this.newPasswordEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidPassword(string2)) {
            Tool.alert(this, R.string.phase_register_password_num_str);
            this.newPasswordEditText.requestFocus();
            return;
        }
        if (string2.length() > 50) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.login_edit_text_password_hint), 50}));
            this.newPasswordEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidCharacter(string2)) {
            Tool.alert(this, R.string.phrase_password_error_char_invalid);
            this.newPasswordEditText.requestFocus();
            return;
        }
        if (!string2.equals(this.repeatPasswordEditText.getText().toString())) {
            Tool.alert(this, R.string.page_register_error_repeat_password_different);
            this.repeatPasswordEditText.requestFocus();
            return;
        }
        UserData userData = GlobalInfo.getInstance().getUserData();
        HashMap map = new HashMap();
        map.put("userId", String.valueOf(userData.getUserId()));
        map.put("oldPassword", string);
        map.put("newPassword", string2);
        map.put("clientType", "APP");
        this.modifyPasswordButton.setEnabled(false);
        new ModifyPasswordTask().execute(map);
    }

    private static class ModifyPasswordTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private ModifyPasswordTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/system/user/modifyPasswordJson", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0073  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0095 A[Catch: all -> 0x00bc, Exception -> 0x00be, TryCatch #0 {Exception -> 0x00be, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x003a, B:27:0x007a, B:28:0x0083, B:29:0x008c, B:30:0x0095, B:14:0x0055, B:17:0x005f, B:20:0x0069, B:31:0x009e), top: B:53:0x0004, outer: #1 }] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r5) {
            /*
                r4 = this;
                super.onPostExecute(r5)
                r0 = 1
                if (r5 == 0) goto L9e
                java.lang.String r1 = "success"
                boolean r1 = r5.getBoolean(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                if (r1 == 0) goto L3a
                android.app.AlertDialog$Builder r5 = new android.app.AlertDialog$Builder     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r1 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r5.<init>(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886955(0x7f12036b, float:1.9408503E38)
                android.app.AlertDialog$Builder r1 = r5.setTitle(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r2 = 17301659(0x108009b, float:2.497969E-38)
                android.app.AlertDialog$Builder r1 = r1.setIcon(r2)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r2 = 2131886586(0x7f1201fa, float:1.9407755E38)
                android.app.AlertDialog$Builder r1 = r1.setMessage(r2)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity$ModifyPasswordTask$1 r2 = new com.lux.luxcloud.view.userCenter.ModifyPasswordActivity$ModifyPasswordTask$1     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r2.<init>()     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r3 = 2131886842(0x7f1202fa, float:1.9408274E38)
                r1.setNegativeButton(r3, r2)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r5.show()     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L3a:
                java.lang.String r1 = "msg"
                java.lang.String r5 = r5.getString(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                int r1 = r5.hashCode()     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r2 = -634377898(0xffffffffda302956, float:-1.2396261E16)
                r3 = 2
                if (r1 == r2) goto L69
                r2 = 1701307993(0x6567e659, float:6.844474E22)
                if (r1 == r2) goto L5f
                r2 = 1809121698(0x6bd501a2, float:5.1501782E26)
                if (r1 == r2) goto L55
                goto L73
            L55:
                java.lang.String r1 = "passwordLengthInvalid8"
                boolean r5 = r5.equals(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                if (r5 == 0) goto L73
                r5 = r0
                goto L74
            L5f:
                java.lang.String r1 = "passwordInvalidDataCharactor"
                boolean r5 = r5.equals(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                if (r5 == 0) goto L73
                r5 = r3
                goto L74
            L69:
                java.lang.String r1 = "passwordLengthInvalid"
                boolean r5 = r5.equals(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                if (r5 == 0) goto L73
                r5 = 0
                goto L74
            L73:
                r5 = -1
            L74:
                if (r5 == 0) goto L95
                if (r5 == r0) goto L8c
                if (r5 == r3) goto L83
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886585(0x7f1201f9, float:1.9407753E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L83:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131887225(0x7f120479, float:1.9409051E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L8c:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886613(0x7f120215, float:1.940781E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L95:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886614(0x7f120216, float:1.9407812E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L9e:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131887250(0x7f120492, float:1.9409102E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
            La6:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                if (r5 == 0) goto Lcf
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto Lcf
            Lb2:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                android.widget.Button r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.access$100(r5)
                r5.setEnabled(r0)
                goto Lcf
            Lbc:
                r5 = move-exception
                goto Ld0
            Lbe:
                r5 = move-exception
                r5.printStackTrace()     // Catch: java.lang.Throwable -> Lbc
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                if (r5 == 0) goto Lcf
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r5 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto Lcf
                goto Lb2
            Lcf:
                return
            Ld0:
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r1 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                if (r1 == 0) goto Le5
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r1 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                boolean r1 = r1.isDestroyed()
                if (r1 != 0) goto Le5
                com.lux.luxcloud.view.userCenter.ModifyPasswordActivity r1 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.instance
                android.widget.Button r1 = com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.access$100(r1)
                r1.setEnabled(r0)
            Le5:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.userCenter.ModifyPasswordActivity.ModifyPasswordTask.onPostExecute(org.json.JSONObject):void");
        }
    }
}