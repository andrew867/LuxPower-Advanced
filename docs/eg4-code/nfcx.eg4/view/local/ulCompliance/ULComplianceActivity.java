package com.nfcx.eg4.view.local.ulCompliance;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ULComplianceActivity extends Activity {
    public static ULComplianceActivity instance;
    private JSONObject cacheReadAllResult;
    private TextView complianceModelText;
    private TextView complianceParallelNumberText;
    private boolean isDarkTheme;
    private TextView rpcNumberText;
    private TextView usernameText;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ul_compliance);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        String stringExtra = getIntent().getStringExtra("cacheReadAllResultText");
        if (!Tool.isEmpty(stringExtra)) {
            try {
                this.cacheReadAllResult = (JSONObject) new JSONTokener(stringExtra).nextValue();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.ulCompliance.ULComplianceActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ULComplianceActivity.instance.finish();
            }
        });
        this.usernameText = (TextView) findViewById(R.id.activity_ul_compliance_user_nameEditText);
        this.rpcNumberText = (TextView) findViewById(R.id.activity_ul_compliance_rpc_numberEditText);
        this.complianceModelText = (TextView) findViewById(R.id.activity_ul_compliance_modelEditText);
        this.complianceParallelNumberText = (TextView) findViewById(R.id.activity_ul_compliance_compliance_parallel_numberEditText);
    }

    public void clickExportPdfButton(View view) {
        if (this.cacheReadAllResult == null) {
            Tool.alert(this, R.string.ul_toast_local_data_empty);
            return;
        }
        final String string = this.usernameText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.login_toast_account_empty);
            return;
        }
        final String string2 = this.rpcNumberText.getText().toString();
        if (Tool.isEmpty(string2)) {
            Tool.alert(this, R.string.ul_toast_rcp_number_empty);
            return;
        }
        final String string3 = this.complianceModelText.getText().toString();
        if (Tool.isEmpty(string3)) {
            Tool.alert(this, R.string.ul_toast_model_empty);
            return;
        }
        final String string4 = this.complianceParallelNumberText.getText().toString();
        if (Tool.isEmpty(string4)) {
            Tool.alert(this, R.string.ul_toast_parallel_number_empty);
        } else {
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.local.ulCompliance.ULComplianceActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f$0.m538x53a7182f(string, string2, string3, string4);
                }
            }).start();
        }
    }

    /* renamed from: lambda$clickExportPdfButton$1$com-nfcx-eg4-view-local-ulCompliance-ULComplianceActivity, reason: not valid java name */
    /* synthetic */ void m538x53a7182f(String str, String str2, String str3, String str4) throws JSONException {
        try {
            String string = UUID.randomUUID().toString();
            this.cacheReadAllResult.put("exportId", string);
            this.cacheReadAllResult.put("username", str);
            this.cacheReadAllResult.put("rpcNumber", str2);
            this.cacheReadAllResult.put("model", str3);
            this.cacheReadAllResult.put("parallelNumber", str4);
            JSONObject jSONObjectMultiPartPostJson = HttpTool.multiPartPostJson(GlobalInfo.getInstance().getCustomDoname() + "web/maintain/local/ulCompliance/postExportData", this.cacheReadAllResult.toString());
            if (jSONObjectMultiPartPostJson == null || !jSONObjectMultiPartPostJson.getBoolean("success")) {
                return;
            }
            Intent intent = new Intent();
            intent.setData(Uri.parse(GlobalInfo.getInstance().getCustomDoname() + "web/maintain/local/ulCompliance/" + string));
            intent.setAction("android.intent.action.VIEW");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        clearFlowChart();
    }

    private void clearFlowChart() {
        this.cacheReadAllResult = null;
        this.usernameText.setText("");
        this.rpcNumberText.setText("");
        this.complianceModelText.setText("");
        this.complianceParallelNumberText.setText("");
    }
}