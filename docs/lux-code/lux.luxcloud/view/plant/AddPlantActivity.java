package com.lux.luxcloud.view.plant;

import android.app.Activity;
import android.app.UiModeManager;
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
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.i18n.TextBundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class AddPlantActivity extends Activity {
    public static AddPlantActivity instance;
    private Button addPlantButton;
    private Spinner continentSpinner;
    private Spinner countrySpinner;
    private ToggleButton daylightSavingTimeToggleButton;
    private boolean isDarkTheme;
    private EditText plantNameEditText;
    private Spinner regionSpinner;
    private Spinner timezoneSpinner;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_plant);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.plant.AddPlantActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AddPlantActivity.instance.finish();
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
        this.continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.plant.AddPlantActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new ReloadRegionSpinnerDataTask().execute(((Property) AddPlantActivity.this.continentSpinner.getSelectedItem()).getName());
            }
        });
        this.continentSpinner.setSelection(Custom.DEFAULT_CONTINENT.ordinal());
        Spinner spinner = (Spinner) findViewById(R.id.register_regionSpinner);
        this.regionSpinner = spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.plant.AddPlantActivity.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new ReloadCountrySpinnerDataTask().execute(((Property) AddPlantActivity.this.regionSpinner.getSelectedItem()).getName());
            }
        });
        this.countrySpinner = (Spinner) findViewById(R.id.register_countrySpinner);
        this.timezoneSpinner = (Spinner) findViewById(R.id.register_timezoneSpinner);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getTimezones());
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.timezoneSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        this.timezoneSpinner.setSelection(GlobalInfo.getInstance().getDefaultTimezoneIndex().intValue());
        this.daylightSavingTimeToggleButton = (ToggleButton) findViewById(R.id.register_daylightSavingTimeToggleButton);
        this.addPlantButton = (Button) findViewById(R.id.register_addPlantButton);
    }

    public void clickAddPlantButton(View view) {
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
        this.addPlantButton.setEnabled(false);
        new AddPlantTask().execute(map);
    }

    private static class AddPlantTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private AddPlantTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/plant/add", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((AddPlantTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            Tool.alert(AddPlantActivity.instance, R.string.page_add_plant_success);
                        } else {
                            Tool.alert(AddPlantActivity.instance, R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(AddPlantActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (AddPlantActivity.instance == null || AddPlantActivity.instance.isDestroyed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (AddPlantActivity.instance == null || AddPlantActivity.instance.isDestroyed()) {
                        return;
                    }
                }
                AddPlantActivity.instance.addPlantButton.setEnabled(true);
            } catch (Throwable th) {
                if (AddPlantActivity.instance != null && !AddPlantActivity.instance.isDestroyed()) {
                    AddPlantActivity.instance.addPlantButton.setEnabled(true);
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
            Integer numValueOf = null;
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        String string = jSONObject.getString("value");
                        arrayList.add(new Property(string, jSONObject.getString(TextBundle.TEXT_ENTRY)));
                        if (Custom.DEFAULT_REGION.equals(string)) {
                            numValueOf = Integer.valueOf(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (AddPlantActivity.instance == null || AddPlantActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(AddPlantActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AddPlantActivity.instance.regionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            if (numValueOf != null) {
                AddPlantActivity.instance.regionSpinner.setSelection(numValueOf.intValue());
            }
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
            Integer numValueOf = null;
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        String string = jSONObject.getString("value");
                        arrayList.add(new Property(string, jSONObject.getString(TextBundle.TEXT_ENTRY)));
                        if (Custom.DEFAULT_COUNTRY.equals(string)) {
                            numValueOf = Integer.valueOf(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (AddPlantActivity.instance == null || AddPlantActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(AddPlantActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AddPlantActivity.instance.countrySpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            if (numValueOf != null) {
                AddPlantActivity.instance.countrySpinner.setSelection(numValueOf.intValue());
            }
        }
    }
}