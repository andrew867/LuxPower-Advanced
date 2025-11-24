package com.lux.luxcloud.view.overview.plant;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.cluster.Cluster;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.plant.Plant;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.API;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Version;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.view.main.Lv2MainActivity;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.dongle.DongleManageAdapter;
import com.lux.luxcloud.view.plant.AddPlantActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.userCenter.NewUserCenterActivity;
import com.lux.luxcloud.view.userCenter.UserCenterActivity;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class PlantOverviewActivity extends Activity {
    public static final String USER_INFO = "userInfo";
    public static int currentPage = 1;
    public static PlantOverviewActivity instance;
    private Spinner clusterSpinner;
    private ImageView datalogOverviewUserFavImg;
    private Button goToHtmlButton;
    private boolean isDarkTheme;
    private long mExitTime;
    private ConstraintLayout nextButton;
    private EditText pageEditText;
    private JSONArray plantArray;
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
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.plant_overview_container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (!userData.needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ImageView) findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m671x497036e8(view);
            }
        });
        Button button = (Button) findViewById(R.id.plant_overview_goToNewPageButton);
        this.goToHtmlButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m673x5b7b9905(view);
            }
        });
        this.clusterSpinner = (Spinner) findViewById(R.id.clusterSpinner);
        if (Constants.CLUSTER_GROUP_FIRST.equals(GlobalInfo.getInstance().getCurrentClusterGroup())) {
            this.clusterSpinner.setVisibility(0);
            ArrayList arrayList = new ArrayList();
            Map<Long, Cluster> clusterMap = GlobalInfo.getInstance().getClusterMap();
            Iterator<Long> it = clusterMap.keySet().iterator();
            Property property = null;
            while (it.hasNext()) {
                long jLongValue = it.next().longValue();
                Cluster cluster = clusterMap.get(Long.valueOf(jLongValue));
                String str = getString(R.string.phrase_cluster) + ": " + cluster.getClusterName();
                if (cluster.getClusterId() == 1) {
                    property = new Property(String.valueOf(jLongValue), str);
                } else {
                    arrayList.add(new Property(String.valueOf(jLongValue), str));
                }
            }
            if (property != null) {
                arrayList.add(property);
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.custom_spinner_text_view, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            this.clusterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            HashMap map = new HashMap();
            map.put(2, 0);
            map.put(3, 1);
            map.put(4, 2);
            map.put(5, 3);
            map.put(1, 4);
            this.clusterSpinner.setSelection(((Integer) map.getOrDefault(Integer.valueOf((int) GlobalInfo.getInstance().getUserData().getClusterId()), 0)).intValue());
            this.clusterSpinner.setOnItemSelectedListener(new AnonymousClass2(userData));
        }
        EditText editText = (EditText) findViewById(R.id.plant_overview_list_item_plantTitleEditText);
        this.plantTitleEditText = editText;
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda2
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.m674x617f6464(textView, i, keyEvent);
            }
        });
        EditText editText2 = (EditText) findViewById(R.id.plant_overview_list_item_userVisitRecord);
        this.userVisitRecordEditText = editText2;
        editText2.setFocusable(false);
        this.userVisitRecordEditText.setClickable(true);
        this.userVisitRecordEditText.setEnabled(true);
        this.userVisitRecordEditText.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m675x67832fc3(view);
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.plant_overview_list_swipe_refresh_layout);
        ListView listView = (ListView) findViewById(R.id.plant_overview_list_plantList);
        this.plantListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(PlantOverviewActivity.this, (Class<?>) MainActivity.class);
                GlobalInfo.getInstance().getUserData().setCurrentPlant(GlobalInfo.getInstance().getUserData().getPlant(j));
                PlantOverviewActivity.this.startActivity(intent);
                PlantOverviewActivity.this.finish();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.datalog_overview_userFavImg);
        this.datalogOverviewUserFavImg = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m676x6d86fb22(view);
            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda5
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m677x738ac681();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.searchPlantImageView);
        this.searchPlantImageView = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m678x798e91e0(view);
            }
        });
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.plant_overview_previousButtonLayout);
        this.previousButton = constraintLayout;
        constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.changePageEditTextByButton(-1);
            }
        });
        ConstraintLayout constraintLayout2 = (ConstraintLayout) findViewById(R.id.plant_overview_nextButtonLayout);
        this.nextButton = constraintLayout2;
        constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlantOverviewActivity.this.changePageEditTextByButton(1);
            }
        });
        EditText editText3 = (EditText) findViewById(R.id.plant_overview_pageEditText);
        this.pageEditText = editText3;
        editText3.setText(String.valueOf(currentPage));
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ void m671x497036e8(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
    }

    /* renamed from: lambda$onCreate$3$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ void m673x5b7b9905(View view) {
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m672x5577cda6();
            }
        });
    }

    /* renamed from: lambda$onCreate$2$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ void m672x5577cda6() throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (instance.plantArray != null) {
            for (int i = 0; i < instance.plantArray.length(); i++) {
                try {
                    arrayList.add((Map) new Gson().fromJson(instance.plantArray.getJSONObject(i).toString(), new TypeToken<Map<String, Object>>() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity.1
                    }.getType()));
                } catch (JSONException unused) {
                }
            }
        }
        final String json = new Gson().toJson(arrayList);
        Log.d("IntentPayload", "Async plantListArray size = " + json.getBytes(StandardCharsets.UTF_8).length + " bytes");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PlantOverviewActivity.lambda$onCreate$1(json);
            }
        });
    }

    static /* synthetic */ void lambda$onCreate$1(String str) {
        Intent intent = new Intent(instance, (Class<?>) Lv2MainActivity.class);
        intent.putExtra("formPlantList", true);
        if (Tool.isEmpty(str)) {
            str = "{}";
        }
        intent.putExtra("plantListArray", str);
        instance.startActivity(intent);
    }

    /* renamed from: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$2, reason: invalid class name */
    class AnonymousClass2 implements AdapterView.OnItemSelectedListener {
        final /* synthetic */ UserData val$userData;

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        AnonymousClass2(UserData userData) {
            this.val$userData = userData;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) throws NumberFormatException {
            long j2 = Long.parseLong(((Property) PlantOverviewActivity.this.clusterSpinner.getSelectedItem()).getName());
            if (j2 == GlobalInfo.getInstance().getUserData().getClusterId() || GlobalInfo.getInstance().getClusterMap().get(Long.valueOf(j2)) == null) {
                return;
            }
            PlantOverviewActivity.instance.swipeRefreshLayout.setRefreshing(true);
            PlantOverviewActivity.instance.plantArray = null;
            PlantOverviewActivity.this.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(PlantOverviewActivity.this, new JSONArray()));
            GlobalInfo.getInstance().getUserData().setClusterId(j2);
            SharedPreferences.Editor editorEdit = PlantOverviewActivity.this.getSharedPreferences("userInfo", 0).edit();
            editorEdit.putLong(LoginActivity.CLUSTER_ID, j2);
            editorEdit.commit();
            final UserData userData = this.val$userData;
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m680x2baeda2(userData);
                }
            }).start();
        }

        /* renamed from: lambda$onItemSelected$2$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity$2, reason: not valid java name */
        /* synthetic */ void m680x2baeda2(UserData userData) {
            PlantOverviewActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PlantOverviewActivity.instance.goToHtmlButton.setEnabled(false);
                }
            });
            final boolean z = false;
            try {
                HashMap map = new HashMap();
                map.put("account", LoginActivity.usernameForLogin);
                map.put("password", LoginActivity.passwordForLogin);
                map.put("language", GlobalInfo.getInstance().getLanguage());
                map.put("changeCluster", Boolean.TRUE.toString());
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/login", map);
                boolean z2 = jSONObjectPostJson != null && jSONObjectPostJson.getBoolean("success");
                if (jSONObjectPostJson != null && jSONObjectPostJson.has(LoginActivity.CLUSTER_ID)) {
                    userData.setClusterId(jSONObjectPostJson.getLong(LoginActivity.CLUSTER_ID));
                }
                z = z2;
            } catch (Exception unused) {
            }
            PlantOverviewActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.overview.plant.PlantOverviewActivity$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m679x6e7c7e03(z);
                }
            });
        }

        /* renamed from: lambda$onItemSelected$1$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity$2, reason: not valid java name */
        /* synthetic */ void m679x6e7c7e03(boolean z) {
            PlantOverviewActivity.instance.swipeRefreshLayout.setRefreshing(false);
            PlantOverviewActivity.instance.goToHtmlButton.setEnabled(true);
            if (z) {
                PlantOverviewActivity.this.m677x738ac681();
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

    /* renamed from: lambda$onCreate$4$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ boolean m674x617f6464(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 3 && (keyEvent == null || keyEvent.getKeyCode() != 66)) {
            return false;
        }
        m677x738ac681();
        return true;
    }

    /* renamed from: lambda$onCreate$5$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ void m675x67832fc3(View view) {
        this.plantTitleEditText.setText(this.userVisitRecordEditText.getText());
        m677x738ac681();
    }

    /* renamed from: lambda$onCreate$6$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ void m676x6d86fb22(View view) {
        this.plantTitleEditText.setText("");
        this.plantListView.setAdapter((ListAdapter) new DongleManageAdapter(this, new JSONArray()));
        userFavList();
    }

    /* renamed from: lambda$onCreate$8$com-lux-luxcloud-view-overview-plant-PlantOverviewActivity, reason: not valid java name */
    /* synthetic */ void m678x798e91e0(View view) {
        m677x738ac681();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePageEditTextByButton(int i) {
        int currentPage2 = getCurrentPage() + i;
        currentPage = currentPage2;
        if (currentPage2 < 1) {
            currentPage = 1;
        }
        this.pageEditText.setText(String.valueOf(currentPage));
        m677x738ac681();
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
        m677x738ac681();
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (userData.getUserVisitRecord() != null) {
            instance.userVisitRecordEditText.setText(userData.getUserVisitRecord().getSerialNum());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshPlantList, reason: merged with bridge method [inline-methods] */
    public void m677x738ac681() {
        instance.goToHtmlButton.setEnabled(true);
        HashMap map = new HashMap();
        String string = this.plantTitleEditText.getText().toString();
        if (!Tool.isEmpty(string)) {
            map.put("searchText", string);
        }
        map.put("page", String.valueOf(getCurrentPage()));
        map.put("rows", "20");
        map.put("language", GlobalInfo.getInstance().getLanguage());
        map.put("clientType", "V2APP");
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
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/plantOverview/list4AllUser", mapArr[0]);
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
                        UserData userData = GlobalInfo.getInstance().getUserData();
                        JSONArray jSONArray = jSONObject.getJSONArray("rows");
                        PlantOverviewActivity.instance.plantArray = jSONArray;
                        PlantOverviewActivity.instance.plantListView.setAdapter((ListAdapter) new PlantOverviewAdapter(PlantOverviewActivity.instance, jSONArray));
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

    public void userFavList() {
        instance.swipeRefreshLayout.setRefreshing(true);
        this.plantListView.setAdapter((ListAdapter) new DongleManageAdapter(this, new JSONArray()));
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
                Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_network_error, 1).show();
            } catch (JSONException e) {
                Toast.makeText(PlantOverviewActivity.instance, R.string.phrase_toast_response_error, 1).show();
                e.printStackTrace();
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