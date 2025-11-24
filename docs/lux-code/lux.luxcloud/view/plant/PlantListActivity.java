package com.lux.luxcloud.view.plant;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.plant.Plant;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.API;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.main.Lv2MainActivity;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.userCenter.NewUserCenterActivity;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class PlantListActivity extends Activity {
    public static final String USER_INFO = "userInfo";
    public static PlantListActivity instance;
    private Button goToHtmlButton;
    private boolean isDarkTheme;
    private long mExitTime;
    private JSONArray plantArray;
    private ListView plantListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_plant_list);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        if (!GlobalInfo.getInstance().getUserData().needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ImageView) findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m685lambda$onCreate$0$comluxluxcloudviewplantPlantListActivity(view);
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.plant_list_swipe_refresh_layout);
        ListView listView = (ListView) findViewById(R.id.plant_list_plantList);
        this.plantListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lux.luxcloud.view.plant.PlantListActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(PlantListActivity.this, (Class<?>) MainActivity.class);
                GlobalInfo.getInstance().getUserData().setCurrentPlant(GlobalInfo.getInstance().getUserData().getPlant(j));
                PlantListActivity.this.startActivity(intent);
                PlantListActivity.this.finish();
            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m686lambda$onCreate$1$comluxluxcloudviewplantPlantListActivity();
            }
        });
        Button button = (Button) findViewById(R.id.plant_overview_goToNewPageButton);
        this.goToHtmlButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        PlantListActivity.lambda$onCreate$3();
                    }
                });
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-plant-PlantListActivity, reason: not valid java name */
    /* synthetic */ void m685lambda$onCreate$0$comluxluxcloudviewplantPlantListActivity(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
    }

    static /* synthetic */ void lambda$onCreate$3() {
        JSONArray jSONArray = instance.plantArray;
        final String string = jSONArray != null ? jSONArray.toString() : "{}";
        Log.d("IntentPayload", "Async plantListArray size = " + string.getBytes(StandardCharsets.UTF_8).length + " bytes");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PlantListActivity.lambda$onCreate$2(string);
            }
        });
    }

    static /* synthetic */ void lambda$onCreate$2(String str) {
        Intent intent = new Intent(instance, (Class<?>) Lv2MainActivity.class);
        intent.putExtra("formPlantList", true);
        if (Tool.isEmpty(str)) {
            str = "{}";
        }
        intent.putExtra("plantListArray", str);
        instance.startActivity(intent);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        m686lambda$onCreate$1$comluxluxcloudviewplantPlantListActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshPlantList, reason: merged with bridge method [inline-methods] */
    public void m686lambda$onCreate$1$comluxluxcloudviewplantPlantListActivity() {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m688x4a806fc4();
            }
        }).start();
    }

    /* renamed from: lambda$refreshPlantList$6$com-lux-luxcloud-view-plant-PlantListActivity, reason: not valid java name */
    /* synthetic */ void m688x4a806fc4() {
        HashMap map = new HashMap();
        map.put("showPlantImage", String.valueOf(true));
        map.put("showParallelGroups", String.valueOf(true));
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/plant/getPlantList", map);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.plant.PlantListActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m687xd5064983(jSONObjectPostJson);
            }
        });
    }

    /* renamed from: lambda$refreshPlantList$5$com-lux-luxcloud-view-plant-PlantListActivity, reason: not valid java name */
    /* synthetic */ void m687xd5064983(JSONObject jSONObject) throws JSONException {
        this.swipeRefreshLayout.setRefreshing(false);
        if (jSONObject != null) {
            try {
                if (jSONObject.getBoolean("success")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("rows");
                    instance.plantArray = jSONArray;
                    this.plantListView.setAdapter((ListAdapter) new PlantListAdapter(this, jSONArray));
                    UserData userData = GlobalInfo.getInstance().getUserData();
                    if (ROLE.VIEWER.equals(userData.getRole())) {
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
                            System.out.println("LuxPower - Plant List inverters = " + arrayList.size());
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
                    return;
                }
            } catch (JSONException e) {
                Toast.makeText(this, R.string.phrase_toast_response_error, 1).show();
                e.printStackTrace();
                return;
            }
        }
        if (jSONObject == null) {
            Toast.makeText(this, R.string.phrase_toast_network_error, 1).show();
            return;
        }
        if (jSONObject.getInt(API.MSG_CODE) == 110) {
            this.plantListView.setAdapter((ListAdapter) new PlantListAdapter(this, new JSONArray()));
            Toast.makeText(this, R.string.plant_toast_no_plant, 1).show();
        } else if (jSONObject.getInt(API.MSG_CODE) == 102) {
            Toast.makeText(this, R.string.phrase_toast_unlogin_error, 1).show();
        }
    }

    public void clickAddPlantButton(View view) {
        if (GlobalInfo.getInstance().isInited()) {
            startActivity(new Intent(this, (Class<?>) AddPlantActivity.class));
        } else {
            Toast.makeText(this, R.string.phrase_please_wait_seconds, 1).show();
            GlobalInfo.getInstance().isIniting();
        }
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