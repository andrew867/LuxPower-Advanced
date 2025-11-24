package com.nfcx.eg4.view.forgetPassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.forgetPassword.ForgetPasswordActivity$$ExternalSyntheticLambda0
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
        toggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.forgetPassword.ForgetPasswordActivity.1
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
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.forgetPassword.ForgetPasswordActivity.GetVerifyCodeTask.onPostExecute(org.json.JSONObject):void");
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
        if (Tool.containInvalidCharacter(string2)) {
            Tool.alert(this, R.string.phrase_password_error_char_invalid);
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
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/forgetPassword/reset", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((ResetPasswordTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPasswordActivity.instance);
                            builder.setTitle(R.string.phrase_message).setIcon(android.R.drawable.ic_dialog_info).setMessage(R.string.page_find_password_reset_success).setNegativeButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.forgetPassword.ForgetPasswordActivity.ResetPasswordTask.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ForgetPasswordActivity.instance.finish();
                                }
                            });
                            builder.show();
                        } else {
                            Tool.alert(ForgetPasswordActivity.instance, R.string.page_find_password_reset_failure);
                        }
                    } else {
                        Tool.alert(ForgetPasswordActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (ForgetPasswordActivity.instance == null || ForgetPasswordActivity.instance.isDestroyed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ForgetPasswordActivity.instance == null || ForgetPasswordActivity.instance.isDestroyed()) {
                        return;
                    }
                }
                ForgetPasswordActivity.instance.resetPasswordButton.setEnabled(true);
            } catch (Throwable th) {
                if (ForgetPasswordActivity.instance != null && !ForgetPasswordActivity.instance.isDestroyed()) {
                    ForgetPasswordActivity.instance.resetPasswordButton.setEnabled(true);
                }
                throw th;
            }
        }
    }
}