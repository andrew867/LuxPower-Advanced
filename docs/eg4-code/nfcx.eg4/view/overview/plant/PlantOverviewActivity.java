package com.nfcx.eg4.view.overview.plant;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.cluster.Cluster;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.plant.Plant;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.tool.API;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.version.Version;
import com.nfcx.eg4.view.login.LoginActivity;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.plant.AddPlantActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import com.nfcx.eg4.view.userCenter.UserCenterActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class PlantOverviewActivity extends Activity {
    public static int currentPage = 1;
    public static PlantOverviewActivity instance;
    private Spinner clusterSpinner;
    private ImageView datalogOverviewUserFavImg;
    private boolean isDarkTheme;
    private long mExitTime;
    private ConstraintLayout nextButton;
    private EditText pageEditText;
    private ListView plantListView;
    private EditText plantTitleEditText;
    private ConstraintLayout previousButton;
    private ImageView searchPlantImageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText userVisitRecordEditText;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_plant_overview_list);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        if (!PLATFORM.LUX_POWER.equals(GlobalInfo.getInstance().getUserData().getPlatform())) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ImageView) findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
            }
        });
        this.clusterSpinner = (Spinner) findViewById(R.id.clusterSpinner);
        if (!Constants.CLUSTER_GROUP_SECOND.equals(GlobalInfo.getInstance().getCurrentClusterGroup())) {
            this.clusterSpinner.setVisibility(0);
            ArrayList arrayList = new ArrayList();
            Map<Long, Cluster> clusterMap = GlobalInfo.getInstance().getClusterMap();
            Iterator<Long> it = clusterMap.keySet().iterator();
            while (it.hasNext()) {
                long jLongValue = it.next().longValue();
                arrayList.add(new Property(String.valueOf(jLongValue), getString(R.string.phrase_cluster) + ": " + clusterMap.get(Long.valueOf(jLongValue)).getClusterName()));
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.custom_spinner_text_view, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            this.clusterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            int clusterId = (int) GlobalInfo.getInstance().getUserData().getClusterId();
            this.clusterSpinner.setSelection((clusterId <= 0 || clusterId > arrayList.size()) ? 0 : clusterId - 1);
            this.clusterSpinner.setOnItemSelectedListener(new AnonymousClass2());
        }
        EditText editText = (EditText) findViewById(R.id.plant_overview_list_item_plantTitleEditText);
        this.plantTitleEditText = editText;
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 3 && (keyEvent == null || keyEvent.getKeyCode() != 66)) {
                    return false;
                }
                PlantOverviewActivity.this.m586x68471216();
                return true;
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.plant_overview_list_swipe_refresh_layout);
        ListView listView = (ListView) findViewById(R.id.plant_overview_list_plantList);
        this.plantListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(PlantOverviewActivity.this, (Class<?>) MainActivity.class);
                GlobalInfo.getInstance().getUserData().setCurrentPlant(GlobalInfo.getInstance().getUserData().getPlant(j));
                PlantOverviewActivity.this.startActivity(intent);
                PlantOverviewActivity.this.finish();
            }
        });
        EditText editText2 = (EditText) findViewById(R.id.plant_overview_list_item_userVisitRecord);
        this.userVisitRecordEditText = editText2;
        editText2.setFocusable(false);
        this.userVisitRecordEditText.setClickable(true);
        this.userVisitRecordEditText.setEnabled(true);
        this.userVisitRecordEditText.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.plantTitleEditText.setText(PlantOverviewActivity.this.userVisitRecordEditText.getText());
                PlantOverviewActivity.this.m586x68471216();
            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda0
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m586x68471216();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.datalog_overview_userFavImg);
        this.datalogOverviewUserFavImg = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.plantTitleEditText.setText("");
                PlantOverviewActivity.this.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(PlantOverviewActivity.this, new JSONArray()));
                PlantOverviewActivity.this.userFavList();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.searchPlantImageView);
        this.searchPlantImageView = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.m586x68471216();
            }
        });
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.plant_overview_previousButtonLayout);
        this.previousButton = constraintLayout;
        constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.changePageEditTextByButton(-1);
            }
        });
        ConstraintLayout constraintLayout2 = (ConstraintLayout) findViewById(R.id.plant_overview_nextButtonLayout);
        this.nextButton = constraintLayout2;
        constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.changePageEditTextByButton(1);
            }
        });
        EditText editText3 = (EditText) findViewById(R.id.plant_overview_pageEditText);
        this.pageEditText = editText3;
        editText3.setText(String.valueOf(currentPage));
        m586x68471216();
    }

    /* renamed from: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity$2, reason: invalid class name */
    class AnonymousClass2 implements AdapterView.OnItemSelectedListener {
        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        AnonymousClass2() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) throws NumberFormatException {
            long j2 = Long.parseLong(((Property) PlantOverviewActivity.this.clusterSpinner.getSelectedItem()).getName());
            if (j2 == GlobalInfo.getInstance().getUserData().getClusterId() || GlobalInfo.getInstance().getClusterMap().get(Long.valueOf(j2)) == null) {
                return;
            }
            PlantOverviewActivity.instance.swipeRefreshLayout.setRefreshing(true);
            PlantOverviewActivity.this.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(PlantOverviewActivity.this, new JSONArray()));
            GlobalInfo.getInstance().getUserData().setClusterId(j2);
            SharedPreferences.Editor editorEdit = PlantOverviewActivity.this.getSharedPreferences("userInfo", 0).edit();
            editorEdit.putLong(LoginActivity.CLUSTER_ID, j2);
            editorEdit.commit();
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m588xd29f9431();
                }
            }).start();
        }

        /* renamed from: lambda$onItemSelected$1$com-nfcx-eg4-view-overview-plant-PlantOverviewActivity$2, reason: not valid java name */
        /* synthetic */ void m588xd29f9431() {
            final boolean z = false;
            try {
                HashMap map = new HashMap();
                map.put("account", LoginActivity.usernameForLogin);
                map.put("password", LoginActivity.passwordForLogin);
                map.put("language", GlobalInfo.getInstance().getLanguage());
                map.put("changeCluster", Boolean.TRUE.toString());
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/login", map);
                if (jSONObjectPostJson != null) {
                    if (jSONObjectPostJson.getBoolean("success")) {
                        z = true;
                    }
                }
            } catch (Exception unused) {
            }
            PlantOverviewActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.overview.plant.PlantOverviewActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m587x45b27d12(z);
                }
            });
        }

        /* renamed from: lambda$onItemSelected$0$com-nfcx-eg4-view-overview-plant-PlantOverviewActivity$2, reason: not valid java name */
        /* synthetic */ void m587x45b27d12(boolean z) {
            PlantOverviewActivity.instance.swipeRefreshLayout.setRefreshing(false);
            if (z) {
                PlantOverviewActivity.this.m586x68471216();
                return;
            }
            Intent intent = new Intent(PlantOverviewActivity.this, (Class<?>) LoginActivity.class);
            intent.putExtra("fromLogout", true);
            PlantOverviewActivity.this.startActivity(intent);
            PlantOverviewActivity.this.finish();
            if (PlantListActivity.instance != null) {
                PlantListActivity.instance.finish();
            }
            if (MainActivity.instance != null) {
                MainActivity.instance.finish();
            }
            if (UserCenterActivity.instance != null) {
                UserCenterActivity.instance.finish();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePageEditTextByButton(int i) {
        int currentPage2 = getCurrentPage() + i;
        currentPage = currentPage2;
        if (currentPage2 < 1) {
            currentPage = 1;
        }
        this.pageEditText.setText(String.valueOf(currentPage));
        m586x68471216();
    }

    private int getCurrentPage() {
        String string = this.pageEditText.getText().toString();
        try {
            if (Tool.isEmpty(string)) {
                return 1;
            }
            return Integer.parseInt(string);
        } catch (Exception e) {
            Log.e(e.getMessage(), Version.TAG, e);
            return 1;
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        m586x68471216();
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (userData.getUserVisitRecord() != null) {
            instance.userVisitRecordEditText.setText(userData.getUserVisitRecord().getSerialNum());
        }
    }

    public void userFavList() {
        instance.swipeRefreshLayout.setRefreshing(true);
        this.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(this, new JSONArray()));
        HashMap map = new HashMap();
        map.put("clientType", "APP");
        new userFavPlantListTask().execute(map);
    }

    private static class userFavPlantListTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private userFavPlantListTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userFav/getUserFavPlantRecordList", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) throws JSONException {
            super.onPostExecute((userFavPlantListTask) jSONObject);
            PlantOverviewActivity.instance.swipeRefreshLayout.setRefreshing(false);
            try {
                if (jSONObject != null) {
                    PlantOverviewActivity.instance.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(PlantOverviewActivity.instance, jSONObject.getJSONArray("rows")));
                } else {
                    Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_network_error, 1).show();
                }
            } catch (JSONException e) {
                Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_response_error, 1).show();
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshPlantList, reason: merged with bridge method [inline-methods] */
    public void m586x68471216() {
        HashMap map = new HashMap();
        String string = this.plantTitleEditText.getText().toString();
        if (!Tool.isEmpty(string)) {
            map.put("searchText", string);
        }
        map.put("page", String.valueOf(getCurrentPage()));
        map.put("rows", "30");
        map.put("language", GlobalInfo.getInstance().getLanguage());
        instance.swipeRefreshLayout.setRefreshing(true);
        this.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(this, new JSONArray()));
        new RefreshPlantListTask().execute(map);
    }

    private static class RefreshPlantListTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private RefreshPlantListTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/plantOverview/list", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) throws JSONException {
            super.onPostExecute((RefreshPlantListTask) jSONObject);
            PlantOverviewActivity.instance.swipeRefreshLayout.setRefreshing(false);
            if (jSONObject != null) {
                try {
                    if (jSONObject.getBoolean("success")) {
                        JSONArray jSONArray = jSONObject.getJSONArray("rows");
                        PlantOverviewActivity.instance.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(PlantOverviewActivity.instance, jSONArray));
                        UserData userData = GlobalInfo.getInstance().getUserData();
                        if (ROLE.VIEWER.equals(userData.getRole())) {
                            return;
                        }
                        userData.clearPlantsAndInverters();
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            Plant plant = new Plant();
                            plant.setPlantId(jSONObject2.getLong("plantId"));
                            plant.setName(jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                            plant.setTimezoneHourOffset(jSONObject2.getInt("timezoneHourOffset"));
                            plant.setTimezoneMinuteOffset(jSONObject2.getInt("timezoneMinuteOffset"));
                            userData.addPlant(plant);
                            ArrayList arrayList = new ArrayList();
                            JSONArray jSONArray2 = jSONObject2.getJSONArray("inverters");
                            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                Inverter inverterByJsonObj = Tool.getInverterByJsonObj(jSONArray2.getJSONObject(i2), plant);
                                arrayList.add(inverterByJsonObj);
                                userData.addInverter(inverterByJsonObj);
                            }
                            userData.put(plant.getPlantId(), arrayList);
                            if (jSONObject2.has("parallelGroups")) {
                                JSONArray jSONArray3 = jSONObject2.getJSONArray("parallelGroups");
                                for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                                    JSONObject jSONObject3 = jSONArray3.getJSONObject(i3);
                                    plant.addParallelGroup(jSONObject3.getString("parallelGroup"), jSONObject3.getString("parallelFirstDeviceSn"));
                                }
                            }
                        }
                        return;
                    }
                } catch (JSONException e) {
                    Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_response_error, 1).show();
                    e.printStackTrace();
                    return;
                }
            }
            if (jSONObject == null) {
                Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_network_error, 1).show();
            } else if (jSONObject.getInt(API.MSG_CODE) == 102) {
                Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_unlogin_error, 1).show();
            }
        }
    }

    public void clickAddPlantButton(View view) {
        startActivity(new Intent(this, (Class<?>) AddPlantActivity.class));
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (System.currentTimeMillis() - this.mExitTime > 2000) {
            Toast.makeText(this, R.string.phrase_toast_double_click_exit, 0).show();
            this.mExitTime = System.currentTimeMillis();
            return true;
        }
        finish();
        return true;
    }
}