package com.lux.luxcloud.view.userCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.i18n.TextBundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class EditUserActivity extends Activity {
    public static EditUserActivity instance;
    private EditText addressEditText;
    private ConstraintLayout allowRemoteSupportLayout;
    private ToggleButton allowRemoteSupportToggleButton;
    private Spinner continentSpinner;
    private Spinner countrySpinner;
    private List<Property> countrys;
    private Button editUserButton;
    private EditText emailEditText;
    private boolean firstTimeLoadCountryBySelect;
    private boolean firstTimeLoadRegionBySelect;
    private boolean isDarkTheme;
    private EditText realNameEditText;
    private Spinner regionSpinner;
    private List<Property> regions;
    private EditText telNumberEditText;
    private Spinner timezoneSpinner;
    private EditText usernameEditText;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_edit_user);
        instance = this;
        int i = 0;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.EditUserActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditUserActivity.instance.finish();
            }
        });
        UserData userData = GlobalInfo.getInstance().getUserData();
        if (!userData.needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        this.firstTimeLoadRegionBySelect = true;
        this.firstTimeLoadCountryBySelect = true;
        EditText editText = (EditText) findViewById(R.id.usernameEditText);
        this.usernameEditText = editText;
        editText.setText(userData.getUsername());
        EditText editText2 = (EditText) findViewById(R.id.realNameEditText);
        this.realNameEditText = editText2;
        editText2.setText(userData.getRealName());
        EditText editText3 = (EditText) findViewById(R.id.emailEditText);
        this.emailEditText = editText3;
        editText3.setText(userData.getEmail());
        this.continentSpinner = (Spinner) findViewById(R.id.register_continentSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getContinents(this));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.continentSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        System.out.println("LuxPowerCurrentContinentIndex = " + userData.getCurrentContinentIndex());
        this.continentSpinner.setSelection(userData.getCurrentContinentIndex());
        this.continentSpinner.invalidate();
        List<Property> regions = userData.getRegions();
        this.regions = regions;
        if (regions == null) {
            this.regions = new ArrayList();
        }
        this.regionSpinner = (Spinner) findViewById(R.id.register_regionSpinner);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, this.regions);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.regionSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        int currentRegionIndex = userData.getCurrentRegionIndex();
        if (currentRegionIndex >= 0 && currentRegionIndex < this.regions.size()) {
            this.regionSpinner.setSelection(currentRegionIndex);
        }
        System.out.println("LuxPowerCurrentRegionIndex = " + currentRegionIndex);
        this.regionSpinner.invalidate();
        this.continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.userCenter.EditUserActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                if (!EditUserActivity.instance.firstTimeLoadRegionBySelect) {
                    new ReloadRegionSpinnerDataTask().execute(((Property) EditUserActivity.instance.continentSpinner.getSelectedItem()).getName());
                } else {
                    EditUserActivity.instance.firstTimeLoadRegionBySelect = false;
                }
            }
        });
        List<Property> countrys = userData.getCountrys();
        this.countrys = countrys;
        if (countrys == null) {
            this.countrys = new ArrayList();
        }
        this.countrySpinner = (Spinner) findViewById(R.id.register_countrySpinner);
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(instance, android.R.layout.simple_spinner_item, this.countrys);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.countrySpinner.setAdapter((SpinnerAdapter) arrayAdapter3);
        int currentCountryIndex = userData.getCurrentCountryIndex();
        if (currentCountryIndex >= 0 && currentCountryIndex < this.countrys.size()) {
            this.countrySpinner.setSelection(currentCountryIndex);
        }
        System.out.println("LuxPowerCurrentCountryIndex = " + currentCountryIndex);
        this.countrySpinner.invalidate();
        this.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.userCenter.EditUserActivity.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                if (!EditUserActivity.instance.firstTimeLoadCountryBySelect) {
                    new ReloadCountrySpinnerDataTask().execute(((Property) EditUserActivity.instance.regionSpinner.getSelectedItem()).getName());
                } else {
                    EditUserActivity.instance.firstTimeLoadCountryBySelect = false;
                }
            }
        });
        this.timezoneSpinner = (Spinner) findViewById(R.id.register_timezoneSpinner);
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getTimezones());
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.timezoneSpinner.setAdapter((SpinnerAdapter) arrayAdapter4);
        String timezone = userData.getTimezone();
        List<Property> timezones = GlobalInfo.getInstance().getTimezones();
        int i2 = 0;
        while (true) {
            if (i2 >= timezones.size()) {
                break;
            }
            if (timezones.get(i2).getName().equals(timezone)) {
                i = i2;
                break;
            }
            i2++;
        }
        this.timezoneSpinner.setSelection(i);
        this.timezoneSpinner.invalidate();
        EditText editText4 = (EditText) findViewById(R.id.telNumberEditText);
        this.telNumberEditText = editText4;
        editText4.setText(userData.getTelNumber());
        EditText editText5 = (EditText) findViewById(R.id.addressEditText);
        this.addressEditText = editText5;
        editText5.setText(userData.getAddress());
        this.allowRemoteSupportLayout = (ConstraintLayout) findViewById(R.id.edit_allowRemoteSupportLayout);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.edit_allowRemoteSupportToggleButton);
        this.allowRemoteSupportToggleButton = toggleButton;
        toggleButton.setChecked(userData.isAllowRemoteSupport());
        this.editUserButton = (Button) findViewById(R.id.editUserButton);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (ROLE.VIEWER.equals(GlobalInfo.getInstance().getUserData().getRole())) {
            this.allowRemoteSupportLayout.setVisibility(0);
        } else {
            this.allowRemoteSupportLayout.setVisibility(8);
        }
    }

    public void clickUpdateButton(View view) {
        UserData userData = GlobalInfo.getInstance().getUserData();
        String string = this.usernameEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Toast.makeText(this, R.string.page_register_error_account_empty, 1).show();
            this.usernameEditText.requestFocus();
            return;
        }
        if (string.length() < 4) {
            Toast.makeText(this, R.string.page_register_error_account_minLength, 1).show();
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
        } else if (Tool.containInvalidCharacter(string)) {
            Tool.alert(this, R.string.phrase_username_error_char_invalid);
            this.usernameEditText.requestFocus();
        } else if (string.equals(userData.getUsername())) {
            execEditUser();
        } else {
            new CheckAccountExistTask().execute(string);
        }
    }

    private void execEditUser() {
        execEditUser(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execEditUser(String str) {
        UserData userData = GlobalInfo.getInstance().getUserData();
        String string = this.emailEditText.getText().toString();
        if (Tool.isEmpty(string)) {
            Tool.alert(this, R.string.page_register_error_email_empty);
            this.emailEditText.requestFocus();
            return;
        }
        if (!Tool.isEmail(string)) {
            Tool.alert(this, R.string.page_register_error_email_format);
            this.emailEditText.requestFocus();
            return;
        }
        HashMap map = new HashMap();
        map.put("userId", String.valueOf(userData.getUserId()));
        if (!Tool.isEmpty(str)) {
            map.put("account", str);
        }
        map.put("email", string);
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
        map.put("language", userData.getLanguage());
        String string2 = this.telNumberEditText.getText().toString();
        if (string2.length() > 30) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.page_register_text_tel_number), 30}));
            this.telNumberEditText.requestFocus();
            return;
        }
        map.put("telNumber", string2);
        map.put("address", this.addressEditText.getText().toString());
        String string3 = this.realNameEditText.getText().toString();
        if (string3.length() > 30) {
            Tool.alert(this, getString(R.string.page_register_error_param_maxLength, new Object[]{getString(R.string.page_register_text_realName), 30}));
            this.realNameEditText.requestFocus();
        } else {
            map.put("realName", string3);
            map.put("allowRemoteSupport", String.valueOf(this.allowRemoteSupportToggleButton.isChecked()));
            this.editUserButton.setEnabled(false);
            new EditUserTask().execute(map);
        }
    }

    private static class CheckAccountExistTask extends AsyncTask<String, Object, String> {
        private String account;

        private CheckAccountExistTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public String doInBackground(String[] strArr) {
            try {
                this.account = strArr[0];
                HashMap map = new HashMap();
                map.put("account", this.account);
                return HttpTool.postString(GlobalInfo.getInstance().getBaseUrl() + "web/register/isAccountExist", map, (Map<String, String>) null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            super.onPostExecute((CheckAccountExistTask) str);
            try {
                if (str != null) {
                    if (!"true".equals(str)) {
                        EditUserActivity.instance.execEditUser(this.account);
                    } else {
                        Tool.alert(EditUserActivity.instance, R.string.page_register_error_account_exists);
                    }
                } else {
                    Tool.alert(EditUserActivity.instance, R.string.phrase_toast_network_error);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class EditUserTask extends AsyncTask<Map<String, String>, Object, JSONObject> {
        private Map<String, String> paramIns;

        private EditUserTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Map<String, String>[] mapArr) {
            try {
                this.paramIns = mapArr[0];
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/system/user/editJson", this.paramIns);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            super.onPostExecute((EditUserTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.instance);
                            builder.setTitle(R.string.phrase_message).setIcon(android.R.drawable.ic_dialog_info).setMessage(R.string.page_edit_user_success).setNegativeButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.userCenter.EditUserActivity.EditUserTask.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UserData userData = GlobalInfo.getInstance().getUserData();
                                    String str = (String) EditUserTask.this.paramIns.get("account");
                                    if (!Tool.isEmpty(str)) {
                                        userData.setUsername(str);
                                    }
                                    userData.setEmail((String) EditUserTask.this.paramIns.get("email"));
                                    userData.setCurrentContinentIndex(EditUserActivity.instance.continentSpinner.getSelectedItemPosition());
                                    userData.setCurrentRegionIndex(EditUserActivity.instance.regionSpinner.getSelectedItemPosition());
                                    userData.setRegions(EditUserActivity.instance.regions);
                                    userData.setCurrentCountryIndex(EditUserActivity.instance.countrySpinner.getSelectedItemPosition());
                                    userData.setCountrys(EditUserActivity.instance.countrys);
                                    if (EditUserActivity.instance.countrys.size() > userData.getCurrentCountryIndex()) {
                                        userData.setCountryText(((Property) EditUserActivity.instance.countrys.get(userData.getCurrentCountryIndex())).getValue());
                                    }
                                    String str2 = (String) EditUserTask.this.paramIns.get("timezone");
                                    userData.setTimezone(str2);
                                    userData.setTimezoneText(Tool.getTimezoneText(str2));
                                    userData.setTelNumber((String) EditUserTask.this.paramIns.get("telNumber"));
                                    userData.setAddress((String) EditUserTask.this.paramIns.get("address"));
                                    userData.setRealName((String) EditUserTask.this.paramIns.get("realName"));
                                    userData.setAllowRemoteSupport(Boolean.parseBoolean((String) EditUserTask.this.paramIns.get("allowRemoteSupport")));
                                    EditUserActivity.instance.finish();
                                }
                            });
                            builder.show();
                        } else {
                            Tool.alert(EditUserActivity.instance, R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(EditUserActivity.instance, R.string.phrase_toast_network_error);
                    }
                    if (EditUserActivity.instance == null || EditUserActivity.instance.isDestroyed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (EditUserActivity.instance == null || EditUserActivity.instance.isDestroyed()) {
                        return;
                    }
                }
                EditUserActivity.instance.editUserButton.setEnabled(true);
            } catch (Throwable th) {
                if (EditUserActivity.instance != null && !EditUserActivity.instance.isDestroyed()) {
                    EditUserActivity.instance.editUserButton.setEnabled(true);
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
            if (EditUserActivity.instance == null || EditUserActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(EditUserActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            EditUserActivity.instance.regionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            EditUserActivity.instance.regions = arrayList;
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
            if (EditUserActivity.instance == null || EditUserActivity.instance.isDestroyed()) {
                return;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(EditUserActivity.instance, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            EditUserActivity.instance.countrySpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            EditUserActivity.instance.countrys = arrayList;
        }
    }
}