package com.lux.luxcloud.view.plant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.overview.dongle.DongleManageActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.i18n.TextBundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class EditPlantActivity extends Activity {
    public static EditPlantActivity instance;
    private Spinner continentSpinner;
    private Spinner countrySpinner;
    private long currentPlantId;
    private ToggleButton daylightSavingTimeToggleButton;
    private Button editPlantButton;
    private ConstraintLayout favoritePlantLayout;
    private ToggleButton favoritePlantToggleButton;
    private boolean firstTimeLoadCountryBySelect;
    private boolean firstTimeLoadRegionBySelect;
    private boolean isDarkTheme;
    private EditText plantNameEditText;
    private Spinner regionSpinner;
    private Button removeStationButton;
    private Spinner timezoneSpinner;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_edit_plant);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        this.firstTimeLoadRegionBySelect = true;
        this.firstTimeLoadCountryBySelect = true;
        this.currentPlantId = getIntent().getLongExtra("plantId", 0L);
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditPlantActivity.instance.finish();
            }
        });
        if (!GlobalInfo.getInstance().getUserData().needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        this.plantNameEditText = (EditText) findViewById(R.id.register_plantNameEditText);
        this.continentSpinner = (Spinner) findViewById(R.id.register_continentSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getContinents(this));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.continentSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.regionSpinner = (Spinner) findViewById(R.id.register_regionSpinner);
        this.countrySpinner = (Spinner) findViewById(R.id.register_countrySpinner);
        this.timezoneSpinner = (Spinner) findViewById(R.id.register_timezoneSpinner);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getTimezones());
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.timezoneSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        this.daylightSavingTimeToggleButton = (ToggleButton) findViewById(R.id.register_daylightSavingTimeToggleButton);
        this.editPlantButton = (Button) findViewById(R.id.register_editPlantButton);
        this.removeStationButton = (Button) findViewById(R.id.removeStationButton);
        this.favoritePlantLayout = (ConstraintLayout) findViewById(R.id.register_favoritePlantLayout);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.register_favoritePlantToggleButton);
        this.favoritePlantToggleButton = toggleButton;
        toggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EditPlantActivity.this.favoritePlantToggleButton.isChecked()) {
                    EditPlantActivity.this.favoritePlantButton();
                } else {
                    EditPlantActivity.this.unFavoritePlantButton();
                }
            }
        });
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        new GetPlantInfoTask().execute(Long.valueOf(this.currentPlantId));
        if (GlobalInfo.getInstance().getUserData().getRole().equals(ROLE.VIEWER)) {
            this.favoritePlantLayout.setVisibility(8);
        }
        updateFavoritePlantButtons();
    }

    public void clickEditPlantButton(View view) {
        String string = this.plantNameEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.page_setting_plant_error_name_empty);
            this.plantNameEditText.requestFocus();
            return;
        }
        if (string.length() < 2) {
            Tool.alert(this, R.string.page_setting_plant_error_name_minLength);
            this.plantNameEditText.requestFocus();
            return;
        }
        if (string.length() > 50) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.page_register_text_plant_name), 50}));
            this.plantNameEditText.requestFocus();
            return;
        }
        HashMap map = new HashMap();
        map.put("plantId", String.valueOf(this.currentPlantId));
        map.put(AppMeasurementSdk.ConditionalUserProperty.NAME, string);
        map.put("daylightSavingTime", String.valueOf(this.daylightSavingTimeToggleButton.isChecked()));
        Property property = (Property) this.countrySpinner.getSelectedItem();
        if (property == null) {
            return;
        }
        map.put("country", property.getName());
        Property property2 = (Property) this.timezoneSpinner.getSelectedItem();
        if (property2 == null) {
            return;
        }
        map.put("timezone", property2.getName());
        this.editPlantButton.setEnabled(false);
        new EditPlantTask().execute(map);
    }

    public void clickManagePlantButton(View view) {
        Intent intent = new Intent(this, (Class<?>) DongleManageActivity.class);
        intent.putExtra("stationName", this.plantNameEditText.getText().toString());
        intent.putExtra("plantId", this.currentPlantId);
        startActivity(intent);
    }

    public void favoritePlantButton() {
        HashMap map = new HashMap();
        map.put("plantId", String.valueOf(this.currentPlantId));
        new FavoritePlantTask().execute(map);
    }

    public void unFavoritePlantButton() {
        HashMap map = new HashMap();
        map.put("plantId", String.valueOf(this.currentPlantId));
        new unFavoritePlantTask().execute(map);
    }

    private void updateFavoritePlantButtons() {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m682xaff42505();
            }
        }).start();
    }

    /* renamed from: lambda$updateFavoritePlantButtons$2$com-lux-luxcloud-view-plant-EditPlantActivity, reason: not valid java name */
    /* synthetic */ void m682xaff42505() throws JSONException {
        HashMap map = new HashMap();
        map.put("plantId", String.valueOf(this.currentPlantId));
        JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userFav/getUserFavPlantRecord", map);
        if (jSONObjectPostJson != null) {
            try {
                if (jSONObjectPostJson.getBoolean("success")) {
                    final boolean z = jSONObjectPostJson.getBoolean(NotificationCompat.CATEGORY_STATUS);
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m681x3a79fec4(z);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: lambda$updateFavoritePlantButtons$1$com-lux-luxcloud-view-plant-EditPlantActivity, reason: not valid java name */
    /* synthetic */ void m681x3a79fec4(boolean z) {
        this.favoritePlantToggleButton.setChecked(z);
    }

    private static class GetPlantInfoTask extends AsyncTask<Long, Object, JSONObject> {
        private GetPlantInfoTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Long... lArr) {
            HashMap map = new HashMap();
            map.put("plantId", String.valueOf(lArr[0]));
            map.put("language", GlobalInfo.getInstance().getLanguage());
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/plant/getPlantInfo", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) throws JSONException {
            super.onPostExecute((GetPlantInfoTask) jSONObject);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (jSONObject != null) {
                try {
                    EditPlantActivity.instance.plantNameEditText.setText(jSONObject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                    String string = jSONObject.getString("continent");
                    List<Property> continents = GlobalInfo.getInstance().getContinents(EditPlantActivity.instance);
                    int i = 0;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= continents.size()) {
                            i2 = 0;
                            break;
                        } else if (continents.get(i2).getName().equals(string)) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    EditPlantActivity.instance.continentSpinner.setSelection(i2);
                    EditPlantActivity.instance.continentSpinner.invalidate();
                    JSONArray jSONArray = jSONObject.getJSONArray("regions");
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i3);
                        arrayList.add(new Property(jSONObject2.getString("value"), jSONObject2.getString(TextBundle.TEXT_ENTRY)));
                    }
                    JSONArray jSONArray2 = jSONObject.getJSONArray("countrys");
                    for (int i4 = 0; i4 < jSONArray2.length(); i4++) {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i4);
                        arrayList2.add(new Property(jSONObject3.getString("value"), jSONObject3.getString(TextBundle.TEXT_ENTRY)));
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(EditPlantActivity.instance, android.R.layout.simple_spinner_item, arrayList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    EditPlantActivity.instance.regionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
                    int i5 = jSONObject.getInt("currentRegionIndex");
                    Spinner spinner = EditPlantActivity.instance.regionSpinner;
                    if (i5 <= 0) {
                        i5 = 0;
                    }
                    spinner.setSelection(i5);
                    EditPlantActivity.instance.regionSpinner.invalidate();
                    ArrayAdapter arrayAdapter2 = new ArrayAdapter(EditPlantActivity.instance, android.R.layout.simple_spinner_item, arrayList2);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    EditPlantActivity.instance.countrySpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
                    int i6 = jSONObject.getInt("currentCountryIndex");
                    Spinner spinner2 = EditPlantActivity.instance.countrySpinner;
                    if (i6 <= 0) {
                        i6 = 0;
                    }
                    spinner2.setSelection(i6);
                    EditPlantActivity.instance.countrySpinner.invalidate();
                    String string2 = jSONObject.getString("timezone");
                    List<Property> timezones = GlobalInfo.getInstance().getTimezones();
                    int i7 = 0;
                    while (true) {
                        if (i7 >= timezones.size()) {
                            break;
                        }
                        if (timezones.get(i7).getName().equals(string2)) {
                            i = i7;
                            break;
                        }
                        i7++;
                    }
                    EditPlantActivity.instance.timezoneSpinner.setSelection(i);
                    EditPlantActivity.instance.timezoneSpinner.invalidate();
                    EditPlantActivity.instance.daylightSavingTimeToggleButton.setChecked(jSONObject.getBoolean("daylightSavingTime"));
                    EditPlantActivity.instance.continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity.GetPlantInfoTask.1
                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i8, long j) {
                            if (!EditPlantActivity.instance.firstTimeLoadRegionBySelect) {
                                new ReloadRegionSpinnerDataTask().execute(((Property) EditPlantActivity.instance.continentSpinner.getSelectedItem()).getName());
                            } else {
                                EditPlantActivity.instance.firstTimeLoadRegionBySelect = false;
                            }
                        }
                    });
                    EditPlantActivity.instance.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity.GetPlantInfoTask.2
                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i8, long j) {
                            if (!EditPlantActivity.instance.firstTimeLoadCountryBySelect) {
                                new ReloadCountrySpinnerDataTask().execute(((Property) EditPlantActivity.instance.regionSpinner.getSelectedItem()).getName());
                            } else {
                                EditPlantActivity.instance.firstTimeLoadCountryBySelect = false;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class FavoritePlantTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private FavoritePlantTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userFav/saveUserFavPlantRecord", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((FavoritePlantTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            Tool.alert(EditPlantActivity.instance, R.string.page_favorite_plant_success);
                        } else {
                            Tool.alert(EditPlantActivity.instance, R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(EditPlantActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (EditPlantActivity.instance == null) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (EditPlantActivity.instance == null) {
                        return;
                    }
                }
                EditPlantActivity.instance.isDestroyed();
            } catch (Throwable th) {
                if (EditPlantActivity.instance != null) {
                    EditPlantActivity.instance.isDestroyed();
                }
                throw th;
            }
        }
    }

    private static class unFavoritePlantTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private unFavoritePlantTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/userFav/removeUserFavPlantRecord", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((unFavoritePlantTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            Tool.alert(EditPlantActivity.instance, R.string.page_unfavorite_plant_success);
                        } else {
                            Tool.alert(EditPlantActivity.instance, R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(EditPlantActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (EditPlantActivity.instance == null) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (EditPlantActivity.instance == null) {
                        return;
                    }
                }
                EditPlantActivity.instance.isDestroyed();
            } catch (Throwable th) {
                if (EditPlantActivity.instance != null) {
                    EditPlantActivity.instance.isDestroyed();
                }
                throw th;
            }
        }
    }

    private static class EditPlantTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private EditPlantTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/plant/edit", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((EditPlantTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            Tool.alert(EditPlantActivity.instance, R.string.page_edit_plant_success);
                        } else {
                            Tool.alert(EditPlantActivity.instance, R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(EditPlantActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (EditPlantActivity.instance == null || EditPlantActivity.instance.isDestroyed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (EditPlantActivity.instance == null || EditPlantActivity.instance.isDestroyed()) {
                        return;
                    }
                }
                EditPlantActivity.instance.editPlantButton.setEnabled(true);
            } catch (Throwable th) {
                if (EditPlantActivity.instance != null && !EditPlantActivity.instance.isDestroyed()) {
                    EditPlantActivity.instance.editPlantButton.setEnabled(true);
                }
                throw th;
            }
        }
    }

    private static class ReloadRegionSpinnerDataTask extends AsyncTask<String, Object, JSONArray> {
        private ReloadRegionSpinnerDataTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONArray doInBackground(String... strArr) {
            HashMap map = new HashMap();
            map.put("continent", String.valueOf(strArr[0]));
            map.put("language", GlobalInfo.getInstance().getLanguage());
            try {
                return HttpTool.postJsonArray(GlobalInfo.getInstance().getBaseUrl() + "locale/region", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONArray jSONArray) throws JSONException {
            super.onPostExecute((ReloadRegionSpinnerDataTask) jSONArray);
            ArrayList arrayList = new ArrayList();
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        arrayList.add(new Property(jSONObject.getString("value"), jSONObject.getString(TextBundle.TEXT_ENTRY)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (EditPlantActivity.instance == null || EditPlantActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(EditPlantActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            EditPlantActivity.instance.regionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        }
    }

    private static class ReloadCountrySpinnerDataTask extends AsyncTask<String, Object, JSONArray> {
        private ReloadCountrySpinnerDataTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONArray doInBackground(String... strArr) {
            HashMap map = new HashMap();
            map.put("region", String.valueOf(strArr[0]));
            map.put("language", GlobalInfo.getInstance().getLanguage());
            try {
                return HttpTool.postJsonArray(GlobalInfo.getInstance().getBaseUrl() + "locale/country", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONArray jSONArray) throws JSONException {
            super.onPostExecute((ReloadCountrySpinnerDataTask) jSONArray);
            ArrayList arrayList = new ArrayList();
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        arrayList.add(new Property(jSONObject.getString("value"), jSONObject.getString(TextBundle.TEXT_ENTRY)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (EditPlantActivity.instance == null || EditPlantActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(EditPlantActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            EditPlantActivity.instance.countrySpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        }
    }

    public void clickRemoveStationButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.phrase_remove_station).setIcon(android.R.drawable.ic_dialog_info).setMessage(R.string.phrase_remove_station_text).setPositiveButton(R.string.phrase_button_ok, new AnonymousClass2()).setNegativeButton(R.string.phrase_button_cancel, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    /* renamed from: com.lux.luxcloud.view.plant.EditPlantActivity$2, reason: invalid class name */
    class AnonymousClass2 implements DialogInterface.OnClickListener {
        AnonymousClass2() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            EditPlantActivity.this.removeStationButton.setEnabled(false);
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m684lambda$onClick$1$comluxluxcloudviewplantEditPlantActivity$2();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-plant-EditPlantActivity$2, reason: not valid java name */
        /* synthetic */ void m684lambda$onClick$1$comluxluxcloudviewplantEditPlantActivity$2() {
            HashMap map = new HashMap();
            map.put("plantId", String.valueOf(EditPlantActivity.this.currentPlantId));
            final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/plant/remove", map);
            EditPlantActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.plant.EditPlantActivity$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m683lambda$onClick$0$comluxluxcloudviewplantEditPlantActivity$2(jSONObjectPostJson);
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0036 A[Catch: all -> 0x0062, Exception -> 0x0064, TryCatch #1 {Exception -> 0x0064, blocks: (B:4:0x0003, B:6:0x000b, B:7:0x0036, B:9:0x0044, B:10:0x004d), top: B:33:0x0003, outer: #0 }] */
        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-plant-EditPlantActivity$2, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        /* synthetic */ void m683lambda$onClick$0$comluxluxcloudviewplantEditPlantActivity$2(org.json.JSONObject r5) {
            /*
                r4 = this;
                r0 = 1
                if (r5 == 0) goto L36
                java.lang.String r1 = "success"
                boolean r1 = r5.getBoolean(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                if (r1 == 0) goto L36
                android.app.AlertDialog$Builder r5 = new android.app.AlertDialog$Builder     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                com.lux.luxcloud.view.plant.EditPlantActivity r1 = com.lux.luxcloud.view.plant.EditPlantActivity.this     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r5.<init>(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r1 = 2131886955(0x7f12036b, float:1.9408503E38)
                android.app.AlertDialog$Builder r1 = r5.setTitle(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r2 = 17301659(0x108009b, float:2.497969E-38)
                android.app.AlertDialog$Builder r1 = r1.setIcon(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r2 = 2131887232(0x7f120480, float:1.9409065E38)
                android.app.AlertDialog$Builder r1 = r1.setMessage(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                com.lux.luxcloud.view.plant.EditPlantActivity$2$1 r2 = new com.lux.luxcloud.view.plant.EditPlantActivity$2$1     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r2.<init>()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r3 = 2131886842(0x7f1202fa, float:1.9408274E38)
                r1.setNegativeButton(r3, r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r5.show()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                goto L55
            L36:
                java.lang.String r1 = "stillHasDatalog"
                java.lang.String r2 = "msg"
                java.lang.String r5 = r5.getString(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                boolean r5 = r1.equals(r5)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                if (r5 == 0) goto L4d
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.this     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r1 = 2131886659(0x7f120243, float:1.9407903E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                goto L55
            L4d:
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.this     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
                r1 = 2131887255(0x7f120497, float:1.9409112E38)
                com.lux.luxcloud.tool.Tool.alert(r5, r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            L55:
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                if (r5 == 0) goto L85
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto L85
                goto L7c
            L62:
                r5 = move-exception
                goto L86
            L64:
                r5 = move-exception
                com.lux.luxcloud.view.plant.EditPlantActivity r1 = com.lux.luxcloud.view.plant.EditPlantActivity.this     // Catch: java.lang.Throwable -> L62
                r2 = 2131887253(0x7f120495, float:1.9409108E38)
                com.lux.luxcloud.tool.Tool.alert(r1, r2)     // Catch: java.lang.Throwable -> L62
                r5.printStackTrace()     // Catch: java.lang.Throwable -> L62
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                if (r5 == 0) goto L85
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                boolean r5 = r5.isDestroyed()
                if (r5 != 0) goto L85
            L7c:
                com.lux.luxcloud.view.plant.EditPlantActivity r5 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                android.widget.Button r5 = com.lux.luxcloud.view.plant.EditPlantActivity.access$1600(r5)
                r5.setEnabled(r0)
            L85:
                return
            L86:
                com.lux.luxcloud.view.plant.EditPlantActivity r1 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                if (r1 == 0) goto L9b
                com.lux.luxcloud.view.plant.EditPlantActivity r1 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                boolean r1 = r1.isDestroyed()
                if (r1 != 0) goto L9b
                com.lux.luxcloud.view.plant.EditPlantActivity r1 = com.lux.luxcloud.view.plant.EditPlantActivity.instance
                android.widget.Button r1 = com.lux.luxcloud.view.plant.EditPlantActivity.access$1600(r1)
                r1.setEnabled(r0)
            L9b:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.plant.EditPlantActivity.AnonymousClass2.m683lambda$onClick$0$comluxluxcloudviewplantEditPlantActivity$2(org.json.JSONObject):void");
        }
    }
}