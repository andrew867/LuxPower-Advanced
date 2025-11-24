package com.nfcx.eg4.view.overview.inverter;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class InverterOverviewActivity extends Activity {
    public static InverterOverviewActivity instance;
    private ListView inverterListView;
    private boolean isDarkTheme;
    private long mExitTime;
    private SwipeRefreshLayout swipeRefreshLayout;

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshInverterList() {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_inverter_overview);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        if (!PLATFORM.LUX_POWER.equals(GlobalInfo.getInstance().getUserData().getPlatform())) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ImageView) findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InverterOverviewActivity.this.startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.inverter_overview_swipe_refresh_layout);
        ListView listView = (ListView) findViewById(R.id.inverter_overview_inverterList);
        this.inverterListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(InverterOverviewActivity.this, (Class<?>) MainActivity.class);
                GlobalInfo.getInstance().getUserData().setCurrentPlant(GlobalInfo.getInstance().getUserData().getPlant(j));
                InverterOverviewActivity.this.startActivity(intent);
                InverterOverviewActivity.this.finish();
            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.3
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                InverterOverviewActivity.this.refreshInverterList();
            }
        });
        refreshInverterList();
    }

    private static class RefreshInverterListTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private RefreshInverterListTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverterMonitor/list", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0031  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r5) throws org.json.JSONException {
            /*
                r4 = this;
                super.onPostExecute(r5)
                com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity r0 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.instance
                androidx.swiperefreshlayout.widget.SwipeRefreshLayout r0 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.access$100(r0)
                r1 = 0
                r0.setRefreshing(r1)
                r0 = 1
                if (r5 == 0) goto L31
                java.lang.String r1 = "success"
                boolean r1 = r5.getBoolean(r1)     // Catch: org.json.JSONException -> L2f
                if (r1 == 0) goto L31
                java.lang.String r1 = "rows"
                org.json.JSONArray r5 = r5.getJSONArray(r1)     // Catch: org.json.JSONException -> L2f
                com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity r1 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.instance     // Catch: org.json.JSONException -> L2f
                android.widget.ListView r1 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.access$200(r1)     // Catch: org.json.JSONException -> L2f
                com.nfcx.eg4.view.overview.inverter.InverterOverviewAdapter r2 = new com.nfcx.eg4.view.overview.inverter.InverterOverviewAdapter     // Catch: org.json.JSONException -> L2f
                com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity r3 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.instance     // Catch: org.json.JSONException -> L2f
                r2.<init>(r3, r5)     // Catch: org.json.JSONException -> L2f
                r1.setAdapter(r2)     // Catch: org.json.JSONException -> L2f
                goto L66
            L2f:
                r5 = move-exception
                goto L57
            L31:
                if (r5 != 0) goto L40
                com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity r5 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.instance     // Catch: org.json.JSONException -> L2f
                r1 = 2131887057(0x7f1203d1, float:1.940871E38)
                android.widget.Toast r5 = android.widget.Toast.makeText(r5, r1, r0)     // Catch: org.json.JSONException -> L2f
                r5.show()     // Catch: org.json.JSONException -> L2f
                goto L66
            L40:
                java.lang.String r1 = "msgCode"
                int r5 = r5.getInt(r1)     // Catch: org.json.JSONException -> L2f
                r1 = 102(0x66, float:1.43E-43)
                if (r5 != r1) goto L66
                com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity r5 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.instance     // Catch: org.json.JSONException -> L2f
                r1 = 2131887063(0x7f1203d7, float:1.9408722E38)
                android.widget.Toast r5 = android.widget.Toast.makeText(r5, r1, r0)     // Catch: org.json.JSONException -> L2f
                r5.show()     // Catch: org.json.JSONException -> L2f
                goto L66
            L57:
                com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity r1 = com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.instance
                r2 = 2131887060(0x7f1203d4, float:1.9408716E38)
                android.widget.Toast r0 = android.widget.Toast.makeText(r1, r2, r0)
                r0.show()
                r5.printStackTrace()
            L66:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.overview.inverter.InverterOverviewActivity.RefreshInverterListTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            startActivity(new Intent(this, (Class<?>) PlantOverviewActivity.class));
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }
}