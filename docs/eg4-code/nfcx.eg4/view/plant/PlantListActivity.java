package com.nfcx.eg4.view.plant;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.plant.Plant;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.tool.API;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class PlantListActivity extends Activity {
    public static PlantListActivity instance;
    private boolean isDarkTheme;
    private long mExitTime;
    private ListView plantListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_plant_list);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        if (!PLATFORM.LUX_POWER.equals(GlobalInfo.getInstance().getUserData().getPlatform())) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ImageView) findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.plant.PlantListActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m593lambda$onCreate$0$comnfcxeg4viewplantPlantListActivity(view);
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.plant_list_swipe_refresh_layout);
        ListView listView = (ListView) findViewById(R.id.plant_list_plantList);
        this.plantListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.nfcx.eg4.view.plant.PlantListActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(PlantListActivity.this, (Class<?>) MainActivity.class);
                GlobalInfo.getInstance().getUserData().setCurrentPlant(GlobalInfo.getInstance().getUserData().getPlant(j));
                PlantListActivity.this.startActivity(intent);
                PlantListActivity.this.finish();
            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.nfcx.eg4.view.plant.PlantListActivity$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m594lambda$onCreate$1$comnfcxeg4viewplantPlantListActivity();
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-plant-PlantListActivity, reason: not valid java name */
    /* synthetic */ void m593lambda$onCreate$0$comnfcxeg4viewplantPlantListActivity(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        m594lambda$onCreate$1$comnfcxeg4viewplantPlantListActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshPlantList, reason: merged with bridge method [inline-methods] */
    public void m594lambda$onCreate$1$comnfcxeg4viewplantPlantListActivity() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.plant.PlantListActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m596xb3188a13();
            }
        }).start();
    }

    /* renamed from: lambda$refreshPlantList$3$com-nfcx-eg4-view-plant-PlantListActivity, reason: not valid java name */
    /* synthetic */ void m596xb3188a13() {
        HashMap map = new HashMap();
        map.put("showPlantImage", String.valueOf(true));
        map.put("showParallelGroups", String.valueOf(true));
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/plant/getPlantList", map);
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.plant.PlantListActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m595xcdd71b52(jSONObjectPostJson);
            }
        });
    }

    /* renamed from: lambda$refreshPlantList$2$com-nfcx-eg4-view-plant-PlantListActivity, reason: not valid java name */
    /* synthetic */ void m595xcdd71b52(JSONObject jSONObject) throws JSONException {
        this.swipeRefreshLayout.setRefreshing(false);
        if (jSONObject != null) {
            try {
                if (jSONObject.getBoolean("success")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("rows");
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