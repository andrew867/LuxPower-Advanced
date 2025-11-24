package com.lux.luxcloud.view.register;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.alibaba.fastjson2.internal.asm.Opcodes;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import com.lux.luxcloud.view.login.LoginActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.i18n.TextBundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class RegisterActivity extends Activity {
    public static RegisterActivity instance;
    private static String scannedCountry;
    private static String scannedRegion;
    private EditText addressEditText;
    private ToggleButton allowRemoteSupportToggleButton;
    private ConstraintLayout clusterConstraintLayout;
    private Spinner clusterSpinner;
    private TextView clusterTextView;
    private Spinner continentSpinner;
    private ArrayAdapter<Property> continentSpinnerAdapter;
    private Spinner countrySpinner;
    private EditText customerCodeEditText;
    private EditText datalogCheckCodeEditText;
    private EditText datalogSnEditText;
    private ToggleButton daylightSavingTimeToggleButton;
    private EditText emailEditText;
    private boolean isDarkTheme;
    private EditText passwordEditText;
    private EditText plantNameEditText;
    private EditText realNameEditText;
    private Spinner regionSpinner;
    private Button registerButton;
    private EditText repeatPasswordEditText;
    private EditText telNumberEditText;
    private Spinner timezoneSpinner;
    private ArrayAdapter<Property> timezoneSpinnerAdapter;
    private EditText usernameEditText;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RegisterActivity.instance.finish();
            }
        });
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.clusterConstraintLayout);
        this.clusterConstraintLayout = constraintLayout;
        constraintLayout.setVisibility(GlobalInfo.getHideClusterAtRegisterPage() ? 8 : 0);
        TextView textView = (TextView) findViewById(R.id.clusterTextView);
        this.clusterTextView = textView;
        textView.setText("* " + getString(R.string.phrase_cluster));
        this.clusterSpinner = (Spinner) findViewById(R.id.register_clusterSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getFirstClusterServerIds(this));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.clusterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.clusterSpinner.setSelection(GlobalInfo.getDefaultClusterIdIndex());
        this.usernameEditText = (EditText) findViewById(R.id.register_usernameEditText);
        this.passwordEditText = (EditText) findViewById(R.id.register_passwordEditText);
        final ImageView imageView = (ImageView) findViewById(R.id.register_password_toggle);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m689x3a1d2eb1(imageView, view);
            }
        });
        this.repeatPasswordEditText = (EditText) findViewById(R.id.register_repeatPasswordEditText);
        final ImageView imageView2 = (ImageView) findViewById(R.id.register_repeatPassword_toggle);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m690x39a6c8b2(imageView2, view);
            }
        });
        this.realNameEditText = (EditText) findViewById(R.id.register_realNameEditText);
        this.emailEditText = (EditText) findViewById(R.id.register_emailEditText);
        this.telNumberEditText = (EditText) findViewById(R.id.register_telNumberEditText);
        this.plantNameEditText = (EditText) findViewById(R.id.register_plantNameEditText);
        this.daylightSavingTimeToggleButton = (ToggleButton) findViewById(R.id.register_daylightSavingTimeToggleButton);
        this.continentSpinner = (Spinner) findViewById(R.id.register_continentSpinner);
        ArrayAdapter<Property> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getContinents(this));
        this.continentSpinnerAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.continentSpinner.setAdapter((SpinnerAdapter) this.continentSpinnerAdapter);
        this.continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new ReloadRegionSpinnerDataTask().execute(((Property) RegisterActivity.this.continentSpinner.getSelectedItem()).getName());
            }
        });
        this.continentSpinner.setSelection(Custom.DEFAULT_CONTINENT.ordinal());
        Spinner spinner = (Spinner) findViewById(R.id.register_regionSpinner);
        this.regionSpinner = spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                new ReloadCountrySpinnerDataTask().execute(((Property) RegisterActivity.this.regionSpinner.getSelectedItem()).getName());
            }
        });
        this.countrySpinner = (Spinner) findViewById(R.id.register_countrySpinner);
        this.timezoneSpinner = (Spinner) findViewById(R.id.register_timezoneSpinner);
        ArrayAdapter<Property> arrayAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getTimezones());
        this.timezoneSpinnerAdapter = arrayAdapter3;
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.timezoneSpinner.setAdapter((SpinnerAdapter) this.timezoneSpinnerAdapter);
        this.timezoneSpinner.setSelection(GlobalInfo.getInstance().getDefaultTimezoneIndex().intValue());
        this.addressEditText = (EditText) findViewById(R.id.register_addressEditText);
        this.allowRemoteSupportToggleButton = (ToggleButton) findViewById(R.id.register_allowRemoteSupportToggleButton);
        this.customerCodeEditText = (EditText) findViewById(R.id.register_customerCodeEditText);
        ((ImageView) findViewById(R.id.register_customerCodescanImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RegisterActivity.this.customerCodeEditText.setText("");
                new IntentIntegrator(RegisterActivity.this).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).setPrompt(RegisterActivity.this.getString(R.string.warranty_scan_tip_text)).setCameraId(0).setBeepEnabled(true).setBarcodeImageEnabled(false).initiateScan();
            }
        });
        this.datalogSnEditText = (EditText) findViewById(R.id.register_datalogSnEditText);
        this.datalogCheckCodeEditText = (EditText) findViewById(R.id.register_datalogCheckCodeEditText);
        this.registerButton = (Button) findViewById(R.id.register_registerButton);
        ((ImageView) findViewById(R.id.scanImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.register.RegisterActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RegisterActivity.this.datalogSnEditText.setText("");
                RegisterActivity.this.datalogCheckCodeEditText.setText("");
                new IntentIntegrator(RegisterActivity.this).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).setPrompt(RegisterActivity.this.getString(R.string.warranty_scan_tip_text)).setCameraId(0).setBeepEnabled(true).setBarcodeImageEnabled(false).initiateScan();
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-register-RegisterActivity, reason: not valid java name */
    /* synthetic */ void m689x3a1d2eb1(ImageView imageView, View view) {
        if (this.passwordEditText.getInputType() == 129) {
            this.passwordEditText.setInputType(145);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.passwordEditText.setInputType(Opcodes.LOR);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
        EditText editText = this.passwordEditText;
        editText.setSelection(editText.getText().length());
    }

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-register-RegisterActivity, reason: not valid java name */
    /* synthetic */ void m690x39a6c8b2(ImageView imageView, View view) {
        if (this.repeatPasswordEditText.getInputType() == 129) {
            this.repeatPasswordEditText.setInputType(145);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.repeatPasswordEditText.setInputType(Opcodes.LOR);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
        EditText editText = this.repeatPasswordEditText;
        editText.setSelection(editText.getText().length());
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) throws JSONException {
        boolean z;
        IntentResult activityResult = IntentIntegrator.parseActivityResult(i, i2, intent);
        if (activityResult != null) {
            if (activityResult.getContents() == null) {
                return;
            }
            String contents = activityResult.getContents();
            try {
                JSONObject jSONObject = new JSONObject(activityResult.getContents());
                if (jSONObject.has("customerCode")) {
                    if (jSONObject.has("continent")) {
                        String string = jSONObject.getString("continent");
                        scannedRegion = jSONObject.getString("region");
                        scannedCountry = jSONObject.getString("country");
                        long selectedItemId = this.continentSpinner.getSelectedItemId();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= this.continentSpinnerAdapter.getCount()) {
                                i3 = 0;
                                break;
                            }
                            Property item = this.continentSpinnerAdapter.getItem(i3);
                            if (item != null && item.getName().equals(string)) {
                                System.out.println("LuxPower - continentSpinner select: " + i3);
                                break;
                            }
                            i3++;
                        }
                        if (i3 != selectedItemId) {
                            this.continentSpinner.setSelection(i3);
                        } else {
                            new ReloadRegionSpinnerDataTask().execute(string);
                        }
                    }
                    if (jSONObject.has("timezone")) {
                        String string2 = jSONObject.getString("timezone");
                        int i4 = 0;
                        while (true) {
                            if (i4 >= this.timezoneSpinnerAdapter.getCount()) {
                                z = false;
                                break;
                            }
                            Property item2 = this.timezoneSpinnerAdapter.getItem(i4);
                            if (item2 != null && item2.getName().equals(string2)) {
                                this.timezoneSpinner.setSelection(i4);
                                z = true;
                                break;
                            }
                            i4++;
                        }
                        if (!z) {
                            this.timezoneSpinner.setSelection(GlobalInfo.getInstance().getDefaultTimezoneIndex().intValue());
                        }
                    }
                    this.customerCodeEditText.setText(jSONObject.getString("customerCode"));
                    return;
                }
                return;
            } catch (JSONException unused) {
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
        }
        super.onActivityResult(i, i2, intent);
    }

    public void clickRegisterButton(View view) {
        Property property;
        String strValueOf = String.valueOf(GlobalInfo.getDefaultClusterId());
        if (!Tool.isAloneClusterGroup() && (property = (Property) this.clusterSpinner.getSelectedItem()) != null) {
            strValueOf = property.getName();
        }
        String string = this.usernameEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.page_register_error_account_empty);
            this.usernameEditText.requestFocus();
            return;
        }
        if (string.length() < 6) {
            Tool.alert(this, R.string.page_register_error_account_minLength);
            this.usernameEditText.requestFocus();
            return;
        }
        if (string.length() > 30) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.login_edit_text_account_hint), 30}));
            this.usernameEditText.requestFocus();
            return;
        }
        if (Tool.hasDoubleSpace(string)) {
            Tool.alert(this, R.string.phrase_username_error_double_space);
            this.usernameEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidCharacter(string)) {
            Tool.alert(this, R.string.phrase_username_error_char_invalid);
            this.usernameEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidCharacter(string)) {
            Tool.alert(this, R.string.phrase_username_error_char_invalid);
            this.usernameEditText.requestFocus();
            return;
        }
        String string2 = this.passwordEditText.getText().toString();
        if (Tool.isEmpty(string2)) {
            Tool.alert(this, R.string.page_register_error_password_empty);
            this.passwordEditText.requestFocus();
            return;
        }
        if (string2.length() < 8) {
            Tool.alert(this, R.string.page_register_error_password_minLength);
            this.passwordEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidPassword(string2)) {
            Tool.alert(this, R.string.phase_register_password_num_str);
            this.passwordEditText.requestFocus();
            return;
        }
        if (string2.length() > 50) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.login_edit_text_password_hint), 50}));
            this.passwordEditText.requestFocus();
            return;
        }
        if (Tool.containInvalidCharacter(string2)) {
            Tool.alert(this, R.string.phrase_password_error_char_invalid);
            this.passwordEditText.requestFocus();
            return;
        }
        if (!string2.equals(this.repeatPasswordEditText.getText().toString())) {
            Tool.alert(this, R.string.page_register_error_repeat_password_different);
            this.repeatPasswordEditText.requestFocus();
            return;
        }
        String string3 = this.realNameEditText.getText().toString();
        if (string3.length() > 30) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.page_register_text_realName), 30}));
            this.realNameEditText.requestFocus();
            return;
        }
        String string4 = this.emailEditText.getText().toString();
        if (Tool.isEmpty(string4)) {
            Tool.alert(this, R.string.page_register_error_email_empty);
            this.emailEditText.requestFocus();
            return;
        }
        if (!Tool.isEmail(string4)) {
            Tool.alert(this, R.string.page_register_error_email_format);
            this.emailEditText.requestFocus();
            return;
        }
        String string5 = this.telNumberEditText.getText().toString();
        if (string5.length() > 30) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.page_register_text_tel_number), 30}));
            this.telNumberEditText.requestFocus();
            return;
        }
        String string6 = this.plantNameEditText.getText().toString();
        if (Tool.isEmpty(string6)) {
            Tool.alert(this, R.string.page_setting_plant_error_name_empty);
            this.plantNameEditText.requestFocus();
            return;
        }
        if (string6.length() < 2) {
            Tool.alert(this, R.string.page_setting_plant_error_name_minLength);
            this.plantNameEditText.requestFocus();
            return;
        }
        if (string6.length() > 50) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.page_register_text_plant_name), 50}));
            this.plantNameEditText.requestFocus();
            return;
        }
        String string7 = this.addressEditText.getText().toString();
        String string8 = this.customerCodeEditText.getText().toString();
        if (Tool.isEmpty(string8)) {
            Tool.alert(this, R.string.page_register_error_customer_code_empty);
            this.customerCodeEditText.requestFocus();
            return;
        }
        String string9 = this.datalogSnEditText.getText().toString();
        if (Tool.isEmpty(string9)) {
            Tool.alert(this, R.string.page_register_error_datalogSn_empty);
            this.datalogSnEditText.requestFocus();
            return;
        }
        if (string9.length() != 10) {
            Tool.alert(this, R.string.page_register_error_datalogSn_length);
            this.datalogSnEditText.requestFocus();
            return;
        }
        String string10 = this.datalogCheckCodeEditText.getText().toString();
        if (Tool.isEmpty(string10)) {
            Tool.alert(this, R.string.page_register_error_check_code_empty);
            this.datalogCheckCodeEditText.requestFocus();
            return;
        }
        if (string10.length() != 5) {
            Tool.alert(this, R.string.page_register_error_check_code_length);
            this.datalogCheckCodeEditText.requestFocus();
            return;
        }
        HashMap map = new HashMap();
        if (Custom.TARGET_PLATFORM != null) {
            map.put("targetPlatform", Custom.TARGET_PLATFORM.name());
        }
        map.put("targetClusterId", strValueOf);
        map.put("account", string);
        map.put("password", string2);
        map.put("realName", string3);
        map.put("email", string4);
        map.put("language", GlobalInfo.getInstance().getLanguageEnumName());
        map.put("telNumber", string5);
        map.put(AppMeasurementSdk.ConditionalUserProperty.NAME, string6);
        map.put("daylightSavingTime", String.valueOf(this.daylightSavingTimeToggleButton.isChecked()));
        map.put("allowRemoteSupport", String.valueOf(this.allowRemoteSupportToggleButton.isChecked()));
        Property property2 = (Property) this.countrySpinner.getSelectedItem();
        if (property2 == null) {
            return;
        }
        map.put("country", property2.getName());
        Property property3 = (Property) this.timezoneSpinner.getSelectedItem();
        if (property3 == null) {
            return;
        }
        map.put("timezone", property3.getName());
        map.put("address", string7);
        map.put("customerCode", string8);
        map.put("datalogSn", string9);
        map.put("checkCode", string10);
        map.put("clientType", "APP");
        this.registerButton.setEnabled(false);
        new RegisterTask().execute(map);
    }

    public void clickButton() {
        Intent intent = new Intent(instance, (Class<?>) LoginActivity.class);
        intent.putExtra("account", this.usernameEditText.getText().toString());
        intent.putExtra("password", this.passwordEditText.getText().toString());
        startActivity(intent);
        finish();
    }

    private static class RegisterTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private RegisterTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/register/viewer", mapArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00de  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r4) {
            /*
                Method dump skipped, instructions count: 542
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.register.RegisterActivity.RegisterTask.onPostExecute(org.json.JSONObject):void");
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
            System.out.println("LuxPower - param = " + map);
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
            Integer numValueOf;
            super.onPostExecute((ReloadRegionSpinnerDataTask) jSONArray);
            ArrayList arrayList = new ArrayList();
            if (jSONArray != null) {
                numValueOf = null;
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
            } else {
                numValueOf = null;
            }
            if (RegisterActivity.instance == null || RegisterActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RegisterActivity.instance.regionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            if (RegisterActivity.scannedRegion == null) {
                if (numValueOf != null) {
                    RegisterActivity.instance.regionSpinner.setSelection(numValueOf.intValue());
                }
            } else {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (((Property) arrayList.get(i2)).getName().equals(RegisterActivity.scannedRegion)) {
                        RegisterActivity.instance.regionSpinner.setSelection(i2);
                        String unused = RegisterActivity.scannedRegion = null;
                        return;
                    }
                }
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
            Integer numValueOf;
            super.onPostExecute((ReloadCountrySpinnerDataTask) jSONArray);
            ArrayList arrayList = new ArrayList();
            if (jSONArray != null) {
                numValueOf = null;
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
            } else {
                numValueOf = null;
            }
            if (RegisterActivity.instance == null || RegisterActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RegisterActivity.instance.countrySpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            if (RegisterActivity.scannedCountry == null) {
                if (numValueOf != null) {
                    RegisterActivity.instance.countrySpinner.setSelection(numValueOf.intValue());
                }
            } else {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (((Property) arrayList.get(i2)).getName().equals(RegisterActivity.scannedCountry)) {
                        RegisterActivity.instance.countrySpinner.setSelection(i2);
                        String unused = RegisterActivity.scannedCountry = null;
                        return;
                    }
                }
            }
        }
    }
}