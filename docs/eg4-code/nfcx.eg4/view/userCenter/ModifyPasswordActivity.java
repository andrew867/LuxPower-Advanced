package com.nfcx.eg4.view.userCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ModifyPasswordActivity.instance.finish();
            }
        });
        if (!PLATFORM.LUX_POWER.equals(GlobalInfo.getInstance().getUserData().getPlatform())) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        this.oldPasswordEditText = (EditText) findViewById(R.id.oldPasswordEditText);
        final ImageView imageView = (ImageView) findViewById(R.id.oldPassword_toggle);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m650x64c96219(imageView, view);
            }
        });
        this.newPasswordEditText = (EditText) findViewById(R.id.newPasswordEditText);
        final ImageView imageView2 = (ImageView) findViewById(R.id.newPassword_toggle);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m651xf204139a(imageView2, view);
            }
        });
        this.repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
        final ImageView imageView3 = (ImageView) findViewById(R.id.repeatPassword_toggle);
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ModifyPasswordActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m652x7f3ec51b(imageView3, view);
            }
        });
        this.modifyPasswordButton = (Button) findViewById(R.id.modifyPasswordButton);
    }

    /* renamed from: lambda$onCreate$1$com-nfcx-eg4-view-userCenter-ModifyPasswordActivity, reason: not valid java name */
    /* synthetic */ void m650x64c96219(ImageView imageView, View view) {
        if (this.oldPasswordEditText.getInputType() == 129) {
            this.oldPasswordEditText.setInputType(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.oldPasswordEditText.setInputType(129);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
    }

    /* renamed from: lambda$onCreate$2$com-nfcx-eg4-view-userCenter-ModifyPasswordActivity, reason: not valid java name */
    /* synthetic */ void m651xf204139a(ImageView imageView, View view) {
        if (this.newPasswordEditText.getInputType() == 129) {
            this.newPasswordEditText.setInputType(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.newPasswordEditText.setInputType(129);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
    }

    /* renamed from: lambda$onCreate$3$com-nfcx-eg4-view-userCenter-ModifyPasswordActivity, reason: not valid java name */
    /* synthetic */ void m652x7f3ec51b(ImageView imageView, View view) {
        if (this.repeatPasswordEditText.getInputType() == 129) {
            this.repeatPasswordEditText.setInputType(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.repeatPasswordEditText.setInputType(129);
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
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((ModifyPasswordTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPasswordActivity.instance);
                            builder.setTitle(R.string.phrase_message).setIcon(android.R.drawable.ic_dialog_info).setMessage(R.string.page_modify_password_success).setNegativeButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.userCenter.ModifyPasswordActivity.ModifyPasswordTask.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ModifyPasswordActivity.instance.finish();
                                }
                            });
                            builder.show();
                        } else {
                            Tool.alert(ModifyPasswordActivity.instance, R.string.page_modify_password_failed);
                        }
                    } else {
                        Tool.alert(ModifyPasswordActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (ModifyPasswordActivity.instance == null || ModifyPasswordActivity.instance.isDestroyed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ModifyPasswordActivity.instance == null || ModifyPasswordActivity.instance.isDestroyed()) {
                        return;
                    }
                }
                ModifyPasswordActivity.instance.modifyPasswordButton.setEnabled(true);
            } catch (Throwable th) {
                if (ModifyPasswordActivity.instance != null && !ModifyPasswordActivity.instance.isDestroyed()) {
                    ModifyPasswordActivity.instance.modifyPasswordButton.setEnabled(true);
                }
                throw th;
            }
        }
    }
}