package com.lux.luxcloud.view.forgetPassword;

import android.app.Activity;
import android.app.UiModeManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ForgetPasswordActivity extends Activity {
    public static ForgetPasswordActivity instance;
    private EditText datalogSnEditText;
    private ConstraintLayout datalogSnLayout;
    private EditText emailEditText;
    private ToggleButton forgetUsernameButton;
    private Button getVerifyCodeButton;
    private boolean isDarkTheme;
    private EditText passwordEditText;
    private ConstraintLayout passwordLayout;
    private EditText repeatPasswordEditText;
    private ConstraintLayout repeatPasswordLayout;
    private Button resetPasswordButton;
    private EditText usernameEditText;
    private EditText verifyCodeEditText;
    private ConstraintLayout verifyCodeLayout;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forget_password);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForgetPasswordActivity.instance.finish();
            }
        });
        this.datalogSnLayout = (ConstraintLayout) findViewById(R.id.datalogSnLayout);
        this.datalogSnEditText = (EditText) findViewById(R.id.datalogSnEditText);
        this.usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.forgetUsernameButton);
        this.forgetUsernameButton = toggleButton;
        toggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ForgetPasswordActivity.this.datalogSnLayout.setVisibility(ForgetPasswordActivity.this.forgetUsernameButton.isChecked() ? 0 : 8);
                ForgetPasswordActivity.this.usernameEditText.setEnabled(!ForgetPasswordActivity.this.forgetUsernameButton.isChecked());
            }
        });
        this.emailEditText = (EditText) findViewById(R.id.emailEditText);
        this.getVerifyCodeButton = (Button) findViewById(R.id.getVerifyCodeButton);
        this.verifyCodeLayout = (ConstraintLayout) findViewById(R.id.verifyCodeLayout);
        this.verifyCodeEditText = (EditText) findViewById(R.id.verifyCodeEditText);
        this.passwordLayout = (ConstraintLayout) findViewById(R.id.passwordLayout);
        this.passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        this.repeatPasswordLayout = (ConstraintLayout) findViewById(R.id.repeatPasswordLayout);
        this.repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
        this.resetPasswordButton = (Button) findViewById(R.id.resetPasswordButton);
    }

    private Map<String, String> buildParamMapForRequest() {
        boolean zIsChecked = this.forgetUsernameButton.isChecked();
        String string = this.usernameEditText.getText().toString();
        String string2 = this.datalogSnEditText.getText().toString();
        HashMap map = new HashMap();
        map.put("useDatalogSn", String.valueOf(zIsChecked));
        if (zIsChecked) {
            if (Tool.isEmpty(string2)) {
                Tool.alert(this, R.string.page_register_error_datalogSn_empty);
                this.datalogSnEditText.requestFocus();
                return null;
            }
            if (string2.length() != 10) {
                Tool.alert(this, R.string.page_register_error_datalogSn_length);
                this.datalogSnEditText.requestFocus();
                return null;
            }
            map.put("datalogSn", string2);
        } else {
            if (Tool.isEmpty(string)) {
                Tool.alert(this, R.string.page_register_error_account_empty);
                this.usernameEditText.requestFocus();
                return null;
            }
            if (Tool.hasDoubleSpace(string)) {
                Tool.alert(this, R.string.phrase_username_error_double_space);
                this.usernameEditText.requestFocus();
                return null;
            }
            map.put("username", string);
        }
        return map;
    }

    public void clickGetVerifyCodeButton(View view) {
        Map<String, String> mapBuildParamMapForRequest = buildParamMapForRequest();
        if (mapBuildParamMapForRequest != null) {
            this.forgetUsernameButton.setEnabled(false);
            this.getVerifyCodeButton.setEnabled(false);
            new GetVerifyCodeTask().execute(mapBuildParamMapForRequest);
        }
    }

    private static class GetVerifyCodeTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private GetVerifyCodeTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/forgetPassword/find", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00dc  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r6) {
            /*
                Method dump skipped, instructions count: 382
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.GetVerifyCodeTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    public void clickResetPasswordButton(View view) {
        String string = this.verifyCodeEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.page_register_error_verify_code_empty);
            this.verifyCodeEditText.requestFocus();
            return;
        }
        String string2 = this.passwordEditText.getText().toString();
        if (Tool.isEmpty(string2)) {
            Tool.alert(this, R.string.page_register_error_password_empty);
            this.passwordEditText.requestFocus();
            return;
        }
        if (string2.length() < 8) {
            Tool.alert(this, R.string.page_register_error_password_minLength);
            this.passwordEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidPassword(string2)) {
            Tool.alert(this, R.string.phase_register_password_num_str);
            this.passwordEditText.requestFocus();
            return;
        }
        if (!string2.equals(this.repeatPasswordEditText.getText().toString())) {
            Tool.alert(this, R.string.page_register_error_repeat_password_different);
            this.repeatPasswordEditText.requestFocus();
            return;
        }
        Map<String, String> mapBuildParamMapForRequest = buildParamMapForRequest();
        if (mapBuildParamMapForRequest != null) {
            mapBuildParamMapForRequest.put("verifyCode", string);
            mapBuildParamMapForRequest.put("password", string2);
            mapBuildParamMapForRequest.put("clientType", "APP");
            this.resetPasswordButton.setEnabled(false);
            new ResetPasswordTask().execute(mapBuildParamMapForRequest);
        }
    }

    private static class ResetPasswordTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private ResetPasswordTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                System.out.println("params = " + Arrays.toString(mapArr));
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/forgetPassword/reset", mapArr[0]);
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
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r1 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r5.<init>(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886955(0x7f12036b, float:1.9408503E38)
                android.app.AlertDialog$Builder r1 = r5.setTitle(r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r2 = 17301659(0x108009b, float:2.497969E-38)
                android.app.AlertDialog$Builder r1 = r1.setIcon(r2)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r2 = 2131886525(0x7f1201bd, float:1.9407631E38)
                android.app.AlertDialog$Builder r1 = r1.setMessage(r2)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity$ResetPasswordTask$1 r2 = new com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity$ResetPasswordTask$1     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
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
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886524(0x7f1201bc, float:1.940763E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L83:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131887225(0x7f120479, float:1.9409051E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L8c:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886613(0x7f120215, float:1.940781E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L95:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131886614(0x7f120216, float:1.9407812E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                goto La6
            L9e:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
                r1 = 2131887250(0x7f120492, float:1.9409102E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> Lbc java.lang.Exception -> Lbe
            La6:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                if (r5 == 0) goto Lcf
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto Lcf
            Lb2:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                android.widget.Button r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.access$1000(r5)
                r5.setEnabled(r0)
                goto Lcf
            Lbc:
                r5 = move-exception
                goto Ld0
            Lbe:
                r5 = move-exception
                r5.printStackTrace()     // Catch: java.lang.Throwable -> Lbc
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                if (r5 == 0) goto Lcf
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r5 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto Lcf
                goto Lb2
            Lcf:
                return
            Ld0:
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r1 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                if (r1 == 0) goto Le5
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r1 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                boolean r1 = r1.isDestroyed()
                if (r1 != 0) goto Le5
                com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity r1 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.instance
                android.widget.Button r1 = com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.access$1000(r1)
                r1.setEnabled(r0)
            Le5:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity.ResetPasswordTask.onPostExecute(org.json.JSONObject):void");
        }
    }
}