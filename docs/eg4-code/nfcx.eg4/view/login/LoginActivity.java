package com.nfcx.eg4.view.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.cluster.CLUSTER_GROUP;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.plant.Plant;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.bean.user.CHART_COLOR;
import com.nfcx.eg4.global.bean.user.DATE_FORMAT;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.global.bean.user.TEMP_UNIT;
import com.nfcx.eg4.global.bean.user.UserVisitRecord;
import com.nfcx.eg4.tool.API;
import com.nfcx.eg4.tool.DisplayUtil;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.LocalConnectTool;
import com.nfcx.eg4.tool.LogUtils;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.version.Custom;
import com.nfcx.eg4.version.Version;
import com.nfcx.eg4.view.forgetPassword.ForgetPasswordActivity;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import com.nfcx.eg4.view.register.RegisterActivity;
import com.nfcx.eg4.view.updateFirmware.DownloadFirmwareActivity;
import com.nfcx.eg4.view.warranty.WarrantyActivity;
import com.nfcx.eg4.view.wifi.WifiConnectActivity;
import java.util.ArrayList;
import java.util.HashMap;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class LoginActivity extends Activity {
    private static final String AUTO_LOGIN_CHECKED = "autoLoginChecked";
    public static final String CLUSTER_ID = "clusterId";
    public static final String IGNORE_VERSION = "ignoredVersion";
    private static final String PASSWORD = "password";
    private static final String REMEMBER_USERNAME_CHECKED = "rememberUsernameChecked";
    private static final int REQUEST_WIFI_PERMISSION = 4;
    private static final String USERNAME = "username";
    public static final String USER_INFO = "userInfo";
    public static final String isNotification = "isNotification";
    public static boolean isUserLoggedIn = false;
    public static String passwordForLogin;
    public static String usernameForLogin;
    private EditText accountEditText;
    private String appVersion;
    private boolean autoLoginChecked;
    private String fcmToken;
    private boolean fromLogout;
    private boolean isDarkTheme;
    private Button loginButton;
    private long loginSuccessTime;
    private String newVersion;
    private long obtainTokenTime;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private boolean triedUpdate = false;
    HashMap<String, String> userInfoMap = new HashMap<>();

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws PackageManager.NameNotFoundException {
        super.onCreate(bundle);
        if ((getIntent().getFlags() & 4194304) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_SECOND);
        Constants.initValidServerIndexMap();
        GlobalInfo.getInstance().getUserData().setClusterId(sharedPreferences.getLong(CLUSTER_ID, 100L));
        GlobalInfo.getInstance().initializeGlobalInfo(this, getResources().getConfiguration().locale);
        if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
        }
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        System.out.println("Eg4displayWidth = " + width + ", displayHeight = " + height);
        float f = width;
        float f2 = height;
        System.out.println("Eg4dpWidth = " + DisplayUtil.px2dip(this, f) + ", dpHeight = " + DisplayUtil.px2dip(this, f2));
        System.out.println("Eg4spWidth = " + DisplayUtil.px2sp(this, f) + ", spHeight = " + DisplayUtil.px2sp(this, f2));
        this.progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        this.loginButton = (Button) findViewById(R.id.login_loginButton);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
            TextView textView = (TextView) findViewById(R.id.login_versionTextView);
            textView.setText(getString(R.string.phrase_version) + " " + packageInfo.versionName + " - Privacy Policy");
            this.appVersion = packageInfo.versionName;
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.login.LoginActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m541lambda$onCreate$0$comnfcxeg4viewloginLoginActivity(view);
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        getAllFirmwares(sharedPreferences);
        findNewVersion();
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() { // from class: com.nfcx.eg4.view.login.LoginActivity.1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public void onComplete(Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(Version.TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                String result = task.getResult();
                System.out.println("fcmToken == " + result);
                LoginActivity.this.obtainTokenTime = System.currentTimeMillis();
                if (LoginActivity.isUserLoggedIn) {
                    LoginActivity.this.sendTokenToServer();
                } else {
                    LoginActivity.this.fcmToken = result;
                }
            }
        });
        this.accountEditText = (EditText) findViewById(R.id.login_accountEditText);
        EditText editText = (EditText) findViewById(R.id.login_passwordEditText);
        this.passwordEditText = editText;
        editText.setTypeface(Typeface.DEFAULT);
        final ImageView imageView = (ImageView) findViewById(R.id.password_toggle);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.login.LoginActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m542lambda$onCreate$1$comnfcxeg4viewloginLoginActivity(imageView, view);
            }
        });
        final CheckBox checkBox = (CheckBox) findViewById(R.id.login_rememberAccountCheckBox);
        boolean z = sharedPreferences.getBoolean(REMEMBER_USERNAME_CHECKED, true);
        checkBox.setChecked(z);
        if (z) {
            this.accountEditText.setText(sharedPreferences.getString(USERNAME, ""));
        }
        handleIntent(getIntent());
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.login_autoLoginCheckBox);
        boolean z2 = sharedPreferences.getBoolean(AUTO_LOGIN_CHECKED, false);
        this.autoLoginChecked = z2;
        checkBox2.setChecked(z2);
        if (this.autoLoginChecked) {
            this.passwordEditText.setText(sharedPreferences.getString(PASSWORD, ""));
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nfcx.eg4.view.login.LoginActivity.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                if (z3) {
                    return;
                }
                checkBox2.setChecked(false);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nfcx.eg4.view.login.LoginActivity.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                if (z3) {
                    checkBox.setChecked(true);
                }
            }
        });
        Button button = (Button) findViewById(R.id.login_loginButton);
        button.setOnClickListener(new AnonymousClass4(sharedPreferences, checkBox, checkBox2, button));
        boolean z3 = sharedPreferences.getBoolean(isNotification, true);
        if (this.autoLoginChecked && !this.fromLogout && z3) {
            button.performClick();
        }
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m541lambda$onCreate$0$comnfcxeg4viewloginLoginActivity(View view) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(Custom.PRIVACY_POLICY_URL));
        intent.setAction("android.intent.action.VIEW");
        startActivity(intent);
    }

    /* renamed from: lambda$onCreate$1$com-nfcx-eg4-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m542lambda$onCreate$1$comnfcxeg4viewloginLoginActivity(ImageView imageView, View view) {
        if (this.passwordEditText.getInputType() == 129) {
            this.passwordEditText.setInputType(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA);
            imageView.setImageResource(R.drawable.icon_eye_open);
        } else {
            this.passwordEditText.setInputType(129);
            imageView.setImageResource(R.drawable.icon_eye_close);
        }
        EditText editText = this.passwordEditText;
        editText.setSelection(editText.getText().length());
    }

    /* renamed from: com.nfcx.eg4.view.login.LoginActivity$4, reason: invalid class name */
    class AnonymousClass4 implements View.OnClickListener {
        final /* synthetic */ CheckBox val$autoLoginCheckBox;
        final /* synthetic */ Button val$loginButton;
        final /* synthetic */ CheckBox val$rememberUsernameCheckBox;
        final /* synthetic */ SharedPreferences val$sharedPreferences;

        AnonymousClass4(SharedPreferences sharedPreferences, CheckBox checkBox, CheckBox checkBox2, Button button) {
            this.val$sharedPreferences = sharedPreferences;
            this.val$rememberUsernameCheckBox = checkBox;
            this.val$autoLoginCheckBox = checkBox2;
            this.val$loginButton = button;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            String strTrim = LoginActivity.this.accountEditText.getText().toString().trim();
            String strTrim2 = LoginActivity.this.passwordEditText.getText().toString().trim();
            SharedPreferences.Editor editorEdit = this.val$sharedPreferences.edit();
            editorEdit.putString(LoginActivity.USERNAME, this.val$rememberUsernameCheckBox.isChecked() ? strTrim : "");
            editorEdit.putString(LoginActivity.PASSWORD, this.val$autoLoginCheckBox.isChecked() ? strTrim2 : "");
            editorEdit.putBoolean(LoginActivity.REMEMBER_USERNAME_CHECKED, this.val$rememberUsernameCheckBox.isChecked());
            editorEdit.putBoolean(LoginActivity.AUTO_LOGIN_CHECKED, this.val$autoLoginCheckBox.isChecked());
            editorEdit.commit();
            if (Tool.isEmpty(strTrim)) {
                Tool.alert(LoginActivity.this, R.string.login_toast_account_empty);
            } else {
                if (!Tool.isEmpty(strTrim2)) {
                    LoginActivity.this.progressBar.setVisibility(0);
                    this.val$loginButton.setEnabled(false);
                    new Thread(new AnonymousClass1(strTrim, strTrim2)).start();
                    return;
                }
                Tool.alert(LoginActivity.this, R.string.login_toast_password_empty);
            }
        }

        /* renamed from: com.nfcx.eg4.view.login.LoginActivity$4$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ String val$accountValue;
            final /* synthetic */ String val$passwordValue;

            AnonymousClass1(String str, String str2) {
                this.val$accountValue = str;
                this.val$passwordValue = str2;
            }

            @Override // java.lang.Runnable
            public void run() {
                final boolean z;
                GlobalInfo.getInstance().getUserData().clear();
                LogUtils.writeLog("Do login for user: " + this.val$accountValue);
                HashMap map = new HashMap();
                map.put("account", this.val$accountValue);
                map.put(LoginActivity.PASSWORD, this.val$passwordValue);
                map.put("language", GlobalInfo.getInstance().getLanguage());
                map.put("userPlatForm", "EG4");
                final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/login", map);
                LogUtils.writeLog("resultFromRemote for login " + this.val$accountValue + ": " + (jSONObjectPostJson != null ? jSONObjectPostJson.toString() : "null..."));
                boolean z2 = true;
                if (jSONObjectPostJson != null) {
                    z = false;
                    try {
                        try {
                            if (jSONObjectPostJson.getBoolean("success")) {
                                LoginActivity.usernameForLogin = this.val$accountValue;
                                LoginActivity.passwordForLogin = this.val$passwordValue;
                                if (jSONObjectPostJson.has("needReloginCluster") && jSONObjectPostJson.getBoolean("needReloginCluster")) {
                                    String string = jSONObjectPostJson.getString("reloginClusterUrl");
                                    if (!Tool.isEmpty(string)) {
                                        long j = jSONObjectPostJson.getLong("reloginClusterId");
                                        SharedPreferences.Editor editorEdit = AnonymousClass4.this.val$sharedPreferences.edit();
                                        editorEdit.putLong(LoginActivity.CLUSTER_ID, j);
                                        editorEdit.commit();
                                        jSONObjectPostJson = HttpTool.postJson(string, map);
                                    } else {
                                        Tool.alertNotInUiThread(LoginActivity.this, "Cluster exception");
                                        z2 = false;
                                    }
                                }
                            }
                            LoginActivity loginActivity = LoginActivity.this;
                            final Button button = AnonymousClass4.this.val$loginButton;
                            loginActivity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$4$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m544lambda$run$0$comnfcxeg4viewloginLoginActivity$4$1(button);
                                }
                            });
                            z = z2;
                        } catch (Exception e) {
                            Tool.alertNotInUiThread(LoginActivity.this, R.string.phrase_toast_response_error);
                            e.printStackTrace();
                            LoginActivity loginActivity2 = LoginActivity.this;
                            final Button button2 = AnonymousClass4.this.val$loginButton;
                            loginActivity2.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$4$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m544lambda$run$0$comnfcxeg4viewloginLoginActivity$4$1(button2);
                                }
                            });
                        }
                    } catch (Throwable th) {
                        LoginActivity loginActivity3 = LoginActivity.this;
                        final Button button3 = AnonymousClass4.this.val$loginButton;
                        loginActivity3.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$4$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m544lambda$run$0$comnfcxeg4viewloginLoginActivity$4$1(button3);
                            }
                        });
                        throw th;
                    }
                } else {
                    LoginActivity loginActivity4 = LoginActivity.this;
                    final Button button4 = AnonymousClass4.this.val$loginButton;
                    loginActivity4.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$4$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m544lambda$run$0$comnfcxeg4viewloginLoginActivity$4$1(button4);
                        }
                    });
                    z = z2;
                }
                LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity.4.1.1
                    @Override // java.lang.Runnable
                    public void run() throws JSONException {
                        JSONArray jSONArray;
                        JSONArray jSONArray2;
                        if (z) {
                            try {
                                JSONObject jSONObject = jSONObjectPostJson;
                                if (jSONObject != null && jSONObject.getBoolean("success")) {
                                    LoginActivity.isUserLoggedIn = true;
                                    LoginActivity.this.loginSuccessTime = System.currentTimeMillis();
                                    LoginActivity.this.userInfoMap.put("userId", String.valueOf(jSONObjectPostJson.getLong("userId")));
                                    LoginActivity.this.userInfoMap.put(LoginActivity.USERNAME, jSONObjectPostJson.getString(LoginActivity.USERNAME));
                                    LoginActivity.this.userInfoMap.put("role", jSONObjectPostJson.getString("role"));
                                    LoginActivity.this.userInfoMap.put("clusterGroup", CLUSTER_GROUP.SECOND.name());
                                    LoginActivity.this.userInfoMap.put("deviceType", "Android");
                                    LoginActivity.this.userInfoMap.put("platform", jSONObjectPostJson.getString("platform"));
                                    LoginActivity.this.sendTokenToServer();
                                    UserData userData = GlobalInfo.getInstance().getUserData();
                                    try {
                                        userData.setUserId(jSONObjectPostJson.getLong("userId"));
                                        if (jSONObjectPostJson.has("parentUserId")) {
                                            userData.setParentUserId(Long.valueOf(jSONObjectPostJson.getLong("parentUserId")));
                                        }
                                        userData.setUsername(jSONObjectPostJson.getString(LoginActivity.USERNAME));
                                        userData.setRole(ROLE.valueOf(jSONObjectPostJson.getString("role")));
                                        userData.setRealName(jSONObjectPostJson.getString("realName"));
                                        userData.setEmail(jSONObjectPostJson.getString("email"));
                                        userData.setAllowViewerVisitWeatherSet(jSONObjectPostJson.getBoolean("allowViewerVisitWeatherSet"));
                                        userData.setAllowViewerVisitWeatherSet(jSONObjectPostJson.getBoolean("allowViewerVisitWeatherSet"));
                                        userData.setAllowRemoteSupport(jSONObjectPostJson.getBoolean("allowRemoteSupport"));
                                        userData.setCountryText(jSONObjectPostJson.optString("countryText", ""));
                                        ArrayList arrayList = new ArrayList();
                                        ArrayList arrayList2 = new ArrayList();
                                        try {
                                            userData.setCurrentContinentIndex(jSONObjectPostJson.optInt("currentContinentIndex", 0));
                                            int iOptInt = jSONObjectPostJson.optInt("currentRegionIndex", 0);
                                            JSONArray jSONArrayOptJSONArray = jSONObjectPostJson.optJSONArray("regions");
                                            if (jSONArrayOptJSONArray != null) {
                                                int i = 0;
                                                while (i < jSONArrayOptJSONArray.length()) {
                                                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                                                    if (jSONObjectOptJSONObject != null) {
                                                        jSONArray2 = jSONArrayOptJSONArray;
                                                        arrayList.add(new Property(jSONObjectOptJSONObject.optString("value", ""), jSONObjectOptJSONObject.optString(TextBundle.TEXT_ENTRY, "")));
                                                    } else {
                                                        jSONArray2 = jSONArrayOptJSONArray;
                                                    }
                                                    i++;
                                                    jSONArrayOptJSONArray = jSONArray2;
                                                }
                                            }
                                            userData.setCurrentRegionIndex(iOptInt);
                                            int iOptInt2 = jSONObjectPostJson.optInt("currentCountryIndex", 0);
                                            JSONArray jSONArrayOptJSONArray2 = jSONObjectPostJson.optJSONArray("countrys");
                                            if (jSONArrayOptJSONArray2 != null) {
                                                int i2 = 0;
                                                while (i2 < jSONArrayOptJSONArray2.length()) {
                                                    JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray2.optJSONObject(i2);
                                                    if (jSONObjectOptJSONObject2 != null) {
                                                        jSONArray = jSONArrayOptJSONArray2;
                                                        arrayList2.add(new Property(jSONObjectOptJSONObject2.optString("value", ""), jSONObjectOptJSONObject2.optString(TextBundle.TEXT_ENTRY, "")));
                                                    } else {
                                                        jSONArray = jSONArrayOptJSONArray2;
                                                    }
                                                    i2++;
                                                    jSONArrayOptJSONArray2 = jSONArray;
                                                }
                                            }
                                            userData.setCurrentCountryIndex(iOptInt2);
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        userData.setRegions(arrayList);
                                        userData.setCountrys(arrayList2);
                                        userData.setTimezone(jSONObjectPostJson.getString("timezone"));
                                        userData.setTimezoneText(jSONObjectPostJson.getString("timezoneText"));
                                        userData.setLanguage(jSONObjectPostJson.getString("language"));
                                        userData.setTelNumber(jSONObjectPostJson.getString("telNumber"));
                                        userData.setAddress(jSONObjectPostJson.getString("address"));
                                        userData.setReadonly(jSONObjectPostJson.getBoolean("readonly"));
                                        userData.setUserCreatedDays(jSONObjectPostJson.getLong("userCreatedDays"));
                                        try {
                                            userData.setPlatform(PLATFORM.valueOf(jSONObjectPostJson.getString("platform")));
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                        if (jSONObjectPostJson.has("techInfo")) {
                                            userData.setTechInfo(jSONObjectPostJson.getJSONObject("techInfo"));
                                        }
                                        userData.setChartColor(CHART_COLOR.valueOf(jSONObjectPostJson.getString("chartColorValues")));
                                        userData.setDateFormat(DATE_FORMAT.valueOf(jSONObjectPostJson.getString("dateFormat")));
                                        userData.setTempUnit(TEMP_UNIT.valueOf(jSONObjectPostJson.getString("tempUnit")));
                                        if (jSONObjectPostJson.has("userVisitRecord")) {
                                            JSONObject jSONObject2 = jSONObjectPostJson.getJSONObject("userVisitRecord");
                                            UserVisitRecord userVisitRecord = new UserVisitRecord();
                                            userVisitRecord.setPlantId(jSONObject2.has("plantId") ? Long.valueOf(jSONObject2.getLong("plantId")) : null);
                                            userVisitRecord.setSerialNum(jSONObject2.has("serialNum") ? jSONObject2.getString("serialNum") : null);
                                            userData.setUserVisitRecord(userVisitRecord);
                                        }
                                        userData.setClusterId(jSONObjectPostJson.getLong(LoginActivity.CLUSTER_ID));
                                        JSONArray jSONArray3 = jSONObjectPostJson.getJSONArray("plants");
                                        for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                                            JSONObject jSONObject3 = jSONArray3.getJSONObject(i3);
                                            Plant plant = new Plant();
                                            plant.setPlantId(jSONObject3.getLong("plantId"));
                                            plant.setName(jSONObject3.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                                            plant.setTimezoneHourOffset(jSONObject3.getInt("timezoneHourOffset"));
                                            plant.setTimezoneMinuteOffset(jSONObject3.getInt("timezoneMinuteOffset"));
                                            userData.addPlant(plant);
                                            ArrayList arrayList3 = new ArrayList();
                                            JSONArray jSONArray4 = jSONObject3.getJSONArray("inverters");
                                            for (int i4 = 0; i4 < jSONArray4.length(); i4++) {
                                                Inverter inverterByJsonObj = Tool.getInverterByJsonObj(jSONArray4.getJSONObject(i4), plant);
                                                arrayList3.add(inverterByJsonObj);
                                                userData.addInverter(inverterByJsonObj);
                                            }
                                            userData.put(plant.getPlantId(), arrayList3);
                                            if (jSONObject3.has("parallelGroups")) {
                                                JSONArray jSONArray5 = jSONObject3.getJSONArray("parallelGroups");
                                                for (int i5 = 0; i5 < jSONArray5.length(); i5++) {
                                                    JSONObject jSONObject4 = jSONArray5.getJSONObject(i5);
                                                    plant.addParallelGroup(jSONObject4.getString("parallelGroup"), jSONObject4.getString("parallelFirstDeviceSn"));
                                                }
                                            }
                                        }
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                    userData.setCurrentInverter(userData.getUserVisitInverter(), false);
                                    if (userData.getPlants().size() == 1) {
                                        userData.setCurrentPlant(userData.getPlants().get(0));
                                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, (Class<?>) MainActivity.class));
                                        LoginActivity.this.finish();
                                        return;
                                    } else {
                                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                                        LoginActivity.this.finish();
                                        return;
                                    }
                                }
                                JSONObject jSONObject5 = jSONObjectPostJson;
                                if (jSONObject5 == null) {
                                    Tool.alert(LoginActivity.this, R.string.phrase_toast_network_error);
                                } else if (jSONObject5.getInt(API.MSG_CODE) == 101) {
                                    Tool.alert(LoginActivity.this, R.string.login_toast_dismatch);
                                } else if (jSONObjectPostJson.getInt(API.MSG_CODE) == 106) {
                                    Tool.alert(LoginActivity.this, R.string.login_toast_retry_limit);
                                }
                            } catch (JSONException e5) {
                                Tool.alert(LoginActivity.this, R.string.phrase_toast_response_error);
                                e5.printStackTrace();
                            }
                        }
                    }
                });
            }

            /* renamed from: lambda$run$0$com-nfcx-eg4-view-login-LoginActivity$4$1, reason: not valid java name */
            /* synthetic */ void m544lambda$run$0$comnfcxeg4viewloginLoginActivity$4$1(Button button) {
                LoginActivity.this.progressBar.setVisibility(4);
                button.setEnabled(true);
            }
        }
    }

    private void getAllFirmwares(final SharedPreferences sharedPreferences) {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LoginActivity.lambda$getAllFirmwares$2(sharedPreferences);
            }
        }).start();
    }

    static /* synthetic */ void lambda$getAllFirmwares$2(SharedPreferences sharedPreferences) {
        try {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            HashMap map = new HashMap();
            map.put("encrypted", String.valueOf(true));
            map.put("platform", "EG4");
            JSONObject jSONObjectPostJson = HttpTool.postJson("https://res.solarcloudsystem.com:8443/resource/getAllFirmware", map);
            if (jSONObjectPostJson != null) {
                editorEdit.putString("firmwares", jSONObjectPostJson.toString());
                editorEdit.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTokenToServer() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m543lambda$sendTokenToServer$3$comnfcxeg4viewloginLoginActivity();
            }
        }).start();
    }

    /* renamed from: lambda$sendTokenToServer$3$com-nfcx-eg4-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m543lambda$sendTokenToServer$3$comnfcxeg4viewloginLoginActivity() {
        try {
            if (Tool.isEmpty(this.fcmToken)) {
                return;
            }
            System.out.println("fcmToken == " + this.fcmToken);
            this.userInfoMap.put("fcmToken", this.fcmToken);
            System.out.println("userInfoMap.toString() == " + this.userInfoMap.toString());
            HttpTool.multiPartPostJson("https://monitor.solarcloudsystem.com:8443/device/updateToken", this.userInfoMap.toString());
            this.userInfoMap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findNewVersion() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m540lambda$findNewVersion$4$comnfcxeg4viewloginLoginActivity();
            }
        }).start();
    }

    /* renamed from: lambda$findNewVersion$4$com-nfcx-eg4-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m540lambda$findNewVersion$4$comnfcxeg4viewloginLoginActivity() throws JSONException {
        try {
            HashMap map = new HashMap();
            map.put("envPlatform", "ANDROID");
            map.put("versionCode", this.appVersion);
            map.put("appType", "MONITOR");
            map.put("userPlatform", "EG4");
            JSONObject jSONObjectPostJson = HttpTool.postJson("https://res.solarcloudsystem.com:8443/resource/appVersion/findNew", map);
            if (jSONObjectPostJson != null && jSONObjectPostJson.has(Constants.ScionAnalytics.MessageType.DATA_MESSAGE) && jSONObjectPostJson.getBoolean(Constants.ScionAnalytics.MessageType.DATA_MESSAGE)) {
                final String string = jSONObjectPostJson.getString("msg");
                runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.login.LoginActivity.5
                    @Override // java.lang.Runnable
                    public void run() {
                        LoginActivity.this.checkNewVersion(string);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNewVersion(final String str) {
        final SharedPreferences preferences = getPreferences(0);
        String string = preferences.getString(IGNORE_VERSION, "");
        if (Tool.isEmpty(string) || !string.equals(str)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.new_app_version_released));
            builder.setMessage(getString(R.string.new_app_version_released_message, new Object[]{str, getString(R.string.app_name)}));
            builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.login.LoginActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m539lambda$checkNewVersion$5$comnfcxeg4viewloginLoginActivity(dialogInterface, i);
                }
            });
            builder.setNegativeButton(getString(R.string.skip), new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.login.LoginActivity.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences.Editor editorEdit = preferences.edit();
                    editorEdit.putString(LoginActivity.IGNORE_VERSION, str);
                    editorEdit.apply();
                }
            });
            builder.setNeutralButton(getString(R.string.later), new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.login.LoginActivity.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialogCreate = builder.create();
            alertDialogCreate.show();
            alertDialogCreate.getButton(-1).setTextColor(ContextCompat.getColor(this, R.color.main_green));
            alertDialogCreate.getButton(-2).setTextColor(-7829368);
            alertDialogCreate.getButton(-3).setTextColor(-7829368);
        }
    }

    /* renamed from: lambda$checkNewVersion$5$com-nfcx-eg4-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m539lambda$checkNewVersion$5$comnfcxeg4viewloginLoginActivity(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.nfcx.eg4"));
        intent.setAction("android.intent.action.VIEW");
        startActivity(intent);
    }

    public void clickForgetPasswordButton(View view) {
        startActivity(new Intent(this, (Class<?>) ForgetPasswordActivity.class));
    }

    public void clickRegisterButton(View view) {
        if (GlobalInfo.getInstance().isInited()) {
            startActivity(new Intent(this, (Class<?>) RegisterActivity.class));
        } else {
            Tool.alert(this, R.string.phrase_please_wait_seconds);
            GlobalInfo.getInstance().isIniting();
        }
    }

    public void clickWifiModuleConnectButton(View view) {
        startActivity(new Intent(this, (Class<?>) WifiConnectActivity.class));
    }

    public void clickWarrantyButton(View view) {
        startActivity(new Intent(this, (Class<?>) WarrantyActivity.class));
    }

    public void clickLocalConnectButton(View view) {
        LocalConnectTool.go2LocalActivity(this, "LocalConnect", 4, (Button) view.findViewById(R.id.login_localButton), this.progressBar);
    }

    public void clickDownloadFirmwareButton(View view) {
        startActivity(new Intent(this, (Class<?>) DownloadFirmwareActivity.class));
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 1 || iArr.length <= 0) {
            return;
        }
        int i2 = iArr[0];
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.triedUpdate) {
            return;
        }
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        SharedPreferences preferences = getPreferences(0);
        if (intent != null) {
            boolean booleanExtra = intent.getBooleanExtra("fromLogout", false);
            this.fromLogout = booleanExtra;
            if (booleanExtra) {
                GlobalInfo.getInstance().getUserData().setClusterId(0L);
                usernameForLogin = null;
                passwordForLogin = null;
            }
            if (intent.hasExtra("account")) {
                String stringExtra = intent.getStringExtra("account");
                String stringExtra2 = intent.getStringExtra(PASSWORD);
                this.accountEditText.setText(stringExtra);
                this.passwordEditText.setText(stringExtra2);
                SharedPreferences.Editor editorEdit = preferences.edit();
                editorEdit.putBoolean(AUTO_LOGIN_CHECKED, false);
                editorEdit.commit();
            }
            if (intent.getBooleanExtra("updateNewVersion", false)) {
                this.triedUpdate = true;
                ProgressDialog progressDialog = new ProgressDialog(this);
                this.progressDialog = progressDialog;
                progressDialog.setTitle(getString(R.string.login_download_dialog_title));
                this.progressDialog.setMessage(getString(R.string.login_download_dialog_text));
                this.progressDialog.setProgressStyle(0);
                this.progressDialog.show();
            }
        }
    }
}