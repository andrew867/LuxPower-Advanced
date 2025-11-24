package com.lux.luxcloud.view.plant;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class AddDatalogActivity extends Activity {
    public static AddDatalogActivity instance;
    private Button addDatalogButton;
    private long currentPlantId;
    private EditText datalogCheckCodeEditText;
    private EditText datalogSnEditText;
    private boolean isDarkTheme;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_datalog);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.AddDatalogActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AddDatalogActivity.instance.finish();
            }
        });
        if (!GlobalInfo.getInstance().getUserData().needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        this.currentPlantId = getIntent().getLongExtra("plantId", 0L);
        this.datalogSnEditText = (EditText) findViewById(R.id.register_datalogSnEditText);
        this.datalogCheckCodeEditText = (EditText) findViewById(R.id.register_datalogCheckCodeEditText);
        this.addDatalogButton = (Button) findViewById(R.id.register_addDatalogButton);
        ((ImageView) findViewById(R.id.scanImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.AddDatalogActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AddDatalogActivity.this.datalogSnEditText.setText("");
                AddDatalogActivity.this.datalogCheckCodeEditText.setText("");
                new IntentIntegrator(AddDatalogActivity.this).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).setPrompt(AddDatalogActivity.this.getString(R.string.warranty_scan_tip_text)).setCameraId(0).setBeepEnabled(true).setBarcodeImageEnabled(false).initiateScan();
            }
        });
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        IntentResult activityResult = IntentIntegrator.parseActivityResult(i, i2, intent);
        if (activityResult != null) {
            if (activityResult.getContents() == null) {
                return;
            }
            String contents = activityResult.getContents();
            if (contents.length() == 10 && contents.matches("^[A-Za-z]{2}\\d{8}$")) {
                this.datalogSnEditText.setText(contents);
                return;
            }
            if (contents.length() == 16) {
                String[] strArrSplit = contents.split("_");
                if (strArrSplit[0].matches("^[A-Za-z]{2}\\d{8}$")) {
                    this.datalogSnEditText.setText(contents);
                    if (strArrSplit.length <= 1 || Tool.isEmpty(strArrSplit[1])) {
                        return;
                    }
                    this.datalogCheckCodeEditText.setText(strArrSplit[1]);
                    return;
                }
                return;
            }
            Toast.makeText(this, R.string.page_maintain_scan_result_not_valid, 0).show();
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    public void clickAddDatalogButton(View view) {
        String string = this.datalogSnEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.page_register_error_datalogSn_empty);
            this.datalogSnEditText.requestFocus();
            return;
        }
        if (string.length() != 10) {
            Tool.alert(this, R.string.page_register_error_datalogSn_length);
            this.datalogSnEditText.requestFocus();
            return;
        }
        String string2 = this.datalogCheckCodeEditText.getText().toString();
        if (Tool.isEmpty(string2)) {
            Tool.alert(this, R.string.page_register_error_check_code_empty);
            this.datalogCheckCodeEditText.requestFocus();
        } else {
            if (string2.length() != 5) {
                Tool.alert(this, R.string.page_register_error_check_code_length);
                this.datalogCheckCodeEditText.requestFocus();
                return;
            }
            HashMap map = new HashMap();
            map.put("serialNum", string);
            map.put("verifyCode", string2);
            map.put("plantId", String.valueOf(this.currentPlantId));
            this.addDatalogButton.setEnabled(false);
            new AddDatalogTask().execute(map);
        }
    }

    private static class AddDatalogTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private AddDatalogTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/datalog/add", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0051  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0073 A[Catch: all -> 0x009a, Exception -> 0x009c, TryCatch #0 {Exception -> 0x009c, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x0018, B:27:0x0058, B:28:0x0061, B:29:0x006a, B:30:0x0073, B:14:0x0033, B:17:0x003d, B:20:0x0047, B:31:0x007c), top: B:53:0x0004, outer: #1 }] */
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
                if (r5 == 0) goto L7c
                java.lang.String r1 = "success"
                boolean r1 = r5.getBoolean(r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                if (r1 == 0) goto L18
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r1 = 2131886514(0x7f1201b2, float:1.940761E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                goto L84
            L18:
                java.lang.String r1 = "msg"
                java.lang.String r5 = r5.getString(r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                int r1 = r5.hashCode()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r2 = -1334946316(0xffffffffb06e55f4, float:-8.670604E-10)
                r3 = 2
                if (r1 == r2) goto L47
                r2 = -79698962(0xfffffffffb3fe3ee, float:-9.963517E35)
                if (r1 == r2) goto L3d
                r2 = 254378608(0xf298270, float:8.357465E-30)
                if (r1 == r2) goto L33
                goto L51
            L33:
                java.lang.String r1 = "serialNumLengthError"
                boolean r5 = r5.equals(r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                if (r5 == 0) goto L51
                r5 = 0
                goto L52
            L3d:
                java.lang.String r1 = "serialNumExists"
                boolean r5 = r5.equals(r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                if (r5 == 0) goto L51
                r5 = r3
                goto L52
            L47:
                java.lang.String r1 = "verifyCodeMismatch"
                boolean r5 = r5.equals(r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                if (r5 == 0) goto L51
                r5 = r0
                goto L52
            L51:
                r5 = -1
            L52:
                if (r5 == 0) goto L73
                if (r5 == r0) goto L6a
                if (r5 == r3) goto L61
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r1 = 2131887255(0x7f120497, float:1.9409112E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                goto L84
            L61:
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r1 = 2131886600(0x7f120208, float:1.9407783E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                goto L84
            L6a:
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r1 = 2131886594(0x7f120202, float:1.9407771E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                goto L84
            L73:
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r1 = 2131886601(0x7f120209, float:1.9407785E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                goto L84
            L7c:
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
                r1 = 2131887250(0x7f120492, float:1.9409102E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9c
            L84:
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                if (r5 == 0) goto Lad
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto Lad
            L90:
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                android.widget.Button r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.access$300(r5)
                r5.setEnabled(r0)
                goto Lad
            L9a:
                r5 = move-exception
                goto Lae
            L9c:
                r5 = move-exception
                r5.printStackTrace()     // Catch: java.lang.Throwable -> L9a
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                if (r5 == 0) goto Lad
                com.lux.luxcloud.view.plant.AddDatalogActivity r5 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto Lad
                goto L90
            Lad:
                return
            Lae:
                com.lux.luxcloud.view.plant.AddDatalogActivity r1 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                if (r1 == 0) goto Lc3
                com.lux.luxcloud.view.plant.AddDatalogActivity r1 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                boolean r1 = r1.isDestroyed()
                if (r1 != 0) goto Lc3
                com.lux.luxcloud.view.plant.AddDatalogActivity r1 = com.lux.luxcloud.view.plant.AddDatalogActivity.instance
                android.widget.Button r1 = com.lux.luxcloud.view.plant.AddDatalogActivity.access$300(r1)
                r1.setEnabled(r0)
            Lc3:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.plant.AddDatalogActivity.AddDatalogTask.onPostExecute(org.json.JSONObject):void");
        }
    }
}