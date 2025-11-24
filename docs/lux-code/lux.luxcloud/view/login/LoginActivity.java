package com.lux.luxcloud.view.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson2.internal.asm.Opcodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.cluster.Cluster;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.tool.DisplayUtil;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.LocalConnectTool;
import com.lux.luxcloud.tool.LogUtils;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import com.lux.luxcloud.version.Version;
import com.lux.luxcloud.view.forgetPassword.ForgetPasswordActivity;
import com.lux.luxcloud.view.main.Lv2MainActivity;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.register.Lv2RegisterActivity;
import com.lux.luxcloud.view.register.RegisterActivity;
import com.lux.luxcloud.view.updateFirmware.DownloadFirmwareActivity;
import com.lux.luxcloud.view.warranty.WarrantyActivity;
import com.lux.luxcloud.view.wifi.WifiConnectActivity;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LoginActivity extends Activity {
    private static final String AUTO_LOGIN_CHECKED = "autoLoginChecked";
    private static final String CLUSTER_HOST = "CLUSTER_HOST";
    public static final String CLUSTER_ID = "clusterId";
    public static final String CLUSTER_URL = "CLUSTER_URL";
    public static final String IGNORE_VERSION = "ignoredVersion";
    private static final String LAST_LOGIN_DATE = "lastLoginDate";
    private static final String PASSWORD = "password";
    private static final String REMEMBER_USERNAME_CHECKED = "rememberUsernameChecked";
    private static final int REQUEST_CODE = 2;
    private static final int REQUEST_WIFI_PERMISSION = 99;
    private static final String USERNAME = "username";
    public static final String USER_INFO = "userInfo";
    public static final String isNotification = "isNotification";
    public static boolean isUserLoggedIn = false;
    public static boolean obtainDeviceToken = false;
    public static String passwordForLogin;
    public static String usernameForLogin;
    private EditText accountEditText;
    private CheckBox agreeCheckBox;
    private String appVersion;
    private String autoAgreePrivacyAndTermChecked;
    private boolean autoLoginChecked;
    private String fcmToken;
    private boolean fromLogout;
    private boolean isDarkTheme;
    private Button loginButton;
    private Spinner loginServerSelectSpinner;
    private long loginSuccessTime;
    private String newVersion;
    private long obtainTokenTime;
    private String packageName;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private boolean useHtmlSettingPage;
    private boolean triedUpdate = false;
    JSONObject userInfoMap = new JSONObject();
    private String loginBaseUrl = "http://eu.luxpowertek.com/";

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws PackageManager.NameNotFoundException {
        super.onCreate(bundle);
        if ((getIntent().getFlags() & 4194304) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.login_container);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        this.useHtmlSettingPage = sharedPreferences.getBoolean(Constants.useHtmlSettingPage, false);
        String country = Locale.getDefault().getCountry();
        System.out.println("LuxPower - Country 1: " + country);
        getApplicationContext();
        String networkCountryIso = ((TelephonyManager) getSystemService("phone")).getNetworkCountryIso();
        System.out.println("LuxPower - Country 2: " + networkCountryIso);
        if ((Constants.INDIA_COUNTRY_CODE.equalsIgnoreCase(country) || Constants.INDIA_COUNTRY_CODE.equalsIgnoreCase(networkCountryIso)) && (PLATFORM.LUX_POWER.equals(Custom.APP_USER_PLATFORM) || PLATFORM.MID.equals(Custom.APP_USER_PLATFORM))) {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_INDIA);
        } else {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_FIRST);
        }
        Constants.initValidServerIndexMap();
        GlobalInfo.getInstance().getUserData().setClusterId(sharedPreferences.getLong(CLUSTER_ID, GlobalInfo.getDefaultClusterId()));
        Locale locale = getResources().getConfiguration().locale;
        GlobalInfo.getInstance().initializeGlobalInfo(this, getResources().getConfiguration().locale);
        if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.POST_NOTIFICATIONS"}, 1);
        }
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        System.out.println("LuxPowerdisplayWidth = " + width + ", displayHeight = " + height);
        float f = width;
        float f2 = height;
        System.out.println("LuxPowerdpWidth = " + DisplayUtil.px2dip(this, f) + ", dpHeight = " + DisplayUtil.px2dip(this, f2));
        System.out.println("LuxPowerspWidth = " + DisplayUtil.px2sp(this, f) + ", spHeight = " + DisplayUtil.px2sp(this, f2));
        getAllFirmwares(getBaseContext());
        this.accountEditText = (EditText) findViewById(R.id.login_accountEditText);
        EditText editText = (EditText) findViewById(R.id.login_passwordEditText);
        this.passwordEditText = editText;
        editText.setTypeface(Typeface.DEFAULT);
        final ImageView imageView = (ImageView) findViewById(R.id.password_toggle);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m595lambda$onCreate$0$comluxluxcloudviewloginLoginActivity(imageView, view);
            }
        });
        this.progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
            TextView textView = (TextView) findViewById(R.id.login_versionTextView);
            String str = getString(R.string.phrase_version) + " " + packageInfo.versionName;
            if (!PLATFORM.LUX_POWER.equals(Custom.APP_USER_PLATFORM)) {
                str = str + " - " + getString(R.string.phrase_privacy_policy);
            }
            textView.setText(str);
            this.appVersion = packageInfo.versionName;
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m596lambda$onCreate$1$comluxluxcloudviewloginLoginActivity(view);
                }
            });
            this.packageName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CheckBox checkBox = (CheckBox) findViewById(R.id.login_agreeCheckBox);
        this.agreeCheckBox = checkBox;
        checkBox.setText(getAgreeToTermsText());
        this.agreeCheckBox.setMovementMethod(LinkMovementMethod.getInstance());
        if (PLATFORM.LUX_POWER.equals(Custom.APP_USER_PLATFORM)) {
            this.agreeCheckBox.setVisibility(0);
        } else {
            this.agreeCheckBox.setChecked(true);
            this.agreeCheckBox.setVisibility(8);
        }
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.login_rememberAccountCheckBox);
        boolean z = sharedPreferences.getBoolean(REMEMBER_USERNAME_CHECKED, true);
        checkBox2.setChecked(z);
        if (z) {
            this.accountEditText.setText(sharedPreferences.getString(USERNAME, ""));
        }
        final CheckBox checkBox3 = (CheckBox) findViewById(R.id.login_autoLoginCheckBox);
        boolean z2 = sharedPreferences.getBoolean(AUTO_LOGIN_CHECKED, false);
        this.autoLoginChecked = z2;
        checkBox3.setChecked(z2);
        String string = sharedPreferences.getString(LAST_LOGIN_DATE, "");
        this.autoAgreePrivacyAndTermChecked = string;
        if (!Tool.isEmpty(string)) {
            Date date = Tool.parseDate(this.autoAgreePrivacyAndTermChecked);
            if (date != null) {
                if (TimeUnit.MILLISECONDS.toDays(new Date().getTime() - date.getTime()) > 5) {
                    this.autoLoginChecked = false;
                } else if (this.autoLoginChecked) {
                    this.agreeCheckBox.setChecked(true);
                } else {
                    this.agreeCheckBox.setChecked(false);
                }
            }
        } else {
            this.autoLoginChecked = false;
        }
        if (this.autoLoginChecked) {
            this.passwordEditText.setText(sharedPreferences.getString(PASSWORD, ""));
        }
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.lux.luxcloud.view.login.LoginActivity.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                if (z3) {
                    return;
                }
                checkBox3.setChecked(false);
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.lux.luxcloud.view.login.LoginActivity.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                if (z3) {
                    checkBox2.setChecked(true);
                }
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.login_serverSelectSpinner);
        this.loginServerSelectSpinner = spinner;
        setupClusterSpinnerWithRestore(spinner);
        Button button = (Button) findViewById(R.id.login_loginButton);
        this.loginButton = button;
        button.setOnClickListener(new AnonymousClass3(sharedPreferences, checkBox2, checkBox3));
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda12
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                this.f$0.m597lambda$onCreate$2$comluxluxcloudviewloginLoginActivity(task);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m595lambda$onCreate$0$comluxluxcloudviewloginLoginActivity(ImageView imageView, View view) {
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

    /* renamed from: lambda$onCreate$1$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m596lambda$onCreate$1$comluxluxcloudviewloginLoginActivity(View view) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(GlobalInfo.getInstance().getCustomDoname() + Custom.PRIVACY_POLICY_URL_SUFFIX));
        intent.setAction("android.intent.action.VIEW");
        startActivity(intent);
    }

    /* renamed from: com.lux.luxcloud.view.login.LoginActivity$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnClickListener {
        final /* synthetic */ CheckBox val$autoLoginCheckBox;
        final /* synthetic */ CheckBox val$rememberUsernameCheckBox;
        final /* synthetic */ SharedPreferences val$sharedPreferences;

        AnonymousClass3(SharedPreferences sharedPreferences, CheckBox checkBox, CheckBox checkBox2) {
            this.val$sharedPreferences = sharedPreferences;
            this.val$rememberUsernameCheckBox = checkBox;
            this.val$autoLoginCheckBox = checkBox2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            final String strTrim = LoginActivity.this.accountEditText.getText().toString().trim();
            final String strTrim2 = LoginActivity.this.passwordEditText.getText().toString().trim();
            SharedPreferences.Editor editorEdit = this.val$sharedPreferences.edit();
            editorEdit.putString(LoginActivity.USERNAME, this.val$rememberUsernameCheckBox.isChecked() ? strTrim : "");
            editorEdit.putString(LoginActivity.PASSWORD, this.val$autoLoginCheckBox.isChecked() ? strTrim2 : "");
            editorEdit.putBoolean(LoginActivity.REMEMBER_USERNAME_CHECKED, this.val$rememberUsernameCheckBox.isChecked());
            editorEdit.putBoolean(LoginActivity.AUTO_LOGIN_CHECKED, this.val$autoLoginCheckBox.isChecked());
            editorEdit.commit();
            if (Tool.isEmpty(strTrim)) {
                Tool.alert(LoginActivity.this, R.string.login_toast_account_empty);
                return;
            }
            if (!Tool.isEmpty(strTrim2)) {
                if (!LoginActivity.this.agreeCheckBox.isChecked()) {
                    LoginActivity.this.agreePrivacyAndTermChecked();
                    return;
                }
                LoginActivity.this.progressBar.setVisibility(0);
                LoginActivity.this.loginButton.setEnabled(false);
                final CheckBox checkBox = this.val$autoLoginCheckBox;
                final SharedPreferences sharedPreferences = this.val$sharedPreferences;
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m600lambda$onClick$1$comluxluxcloudviewloginLoginActivity$3(strTrim, strTrim2, checkBox, sharedPreferences);
                    }
                }).start();
                return;
            }
            Tool.alert(LoginActivity.this, R.string.login_toast_password_empty);
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-login-LoginActivity$3, reason: not valid java name */
        /* synthetic */ void m600lambda$onClick$1$comluxluxcloudviewloginLoginActivity$3(String str, String str2, CheckBox checkBox, SharedPreferences sharedPreferences) {
            boolean z;
            GlobalInfo.getInstance().getUserData().clear();
            LogUtils.writeLog("Do login for user: " + str);
            HashMap map = new HashMap();
            map.put("account", str);
            map.put(LoginActivity.PASSWORD, str2);
            map.put("language", GlobalInfo.getInstance().getLanguage());
            map.put("userPlatForm", Custom.APP_USER_PLATFORM.name());
            map.put("withSatoken", "true");
            JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/login", map);
            System.out.println("LuxPower - resultFromRemote for login " + str + ": " + (jSONObjectPostJson != null ? jSONObjectPostJson.toString() : "null..."));
            LogUtils.writeLog("resultFromRemote for login " + str + ": " + (jSONObjectPostJson != null ? jSONObjectPostJson.toString() : "null..."));
            boolean z2 = true;
            if (jSONObjectPostJson != null) {
                z = false;
                try {
                    try {
                        if (jSONObjectPostJson.getBoolean("success")) {
                            LoginActivity.usernameForLogin = str;
                            LoginActivity.passwordForLogin = str2;
                            if (checkBox.isChecked()) {
                                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                                editorEdit.putString(LoginActivity.LAST_LOGIN_DATE, Tool.formatDate(new Date()));
                                editorEdit.apply();
                            }
                            if (jSONObjectPostJson.has("token")) {
                                System.out.println("LuxPower - token = " + jSONObjectPostJson.getString("token"));
                            }
                            if (jSONObjectPostJson.has("needReloginCluster") && jSONObjectPostJson.getBoolean("needReloginCluster")) {
                                String string = jSONObjectPostJson.getString("reloginClusterUrl");
                                if (Tool.isEmpty(string)) {
                                    Tool.alertNotInUiThread(LoginActivity.this, "Cluster exception");
                                    z2 = false;
                                } else {
                                    long j = jSONObjectPostJson.getLong("reloginClusterId");
                                    SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
                                    editorEdit2.putLong(LoginActivity.CLUSTER_ID, j);
                                    editorEdit2.commit();
                                    String strCanonicalizeUrl = LoginActivity.this.canonicalizeUrl(string);
                                    if (strCanonicalizeUrl.contains("server.luxpowertek.com")) {
                                        strCanonicalizeUrl = strCanonicalizeUrl.replaceAll("server.luxpowertek.com", "as.luxpowertek.com");
                                    }
                                    System.out.println(strCanonicalizeUrl);
                                    jSONObjectPostJson = HttpTool.postJson(strCanonicalizeUrl, map);
                                }
                            }
                        }
                        LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$3$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m599lambda$onClick$0$comluxluxcloudviewloginLoginActivity$3();
                            }
                        });
                        z = z2;
                    } catch (Exception e) {
                        Tool.alertNotInUiThread(LoginActivity.this, R.string.phrase_toast_response_error);
                        e.printStackTrace();
                        LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$3$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m599lambda$onClick$0$comluxluxcloudviewloginLoginActivity$3();
                            }
                        });
                    }
                } catch (Throwable th) {
                    LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m599lambda$onClick$0$comluxluxcloudviewloginLoginActivity$3();
                        }
                    });
                    throw th;
                }
            } else {
                LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m599lambda$onClick$0$comluxluxcloudviewloginLoginActivity$3();
                    }
                });
                z = z2;
            }
            LoginActivity.this.runOnUiThread(new AnonymousClass1(z, jSONObjectPostJson, str2));
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-login-LoginActivity$3, reason: not valid java name */
        /* synthetic */ void m599lambda$onClick$0$comluxluxcloudviewloginLoginActivity$3() {
            LoginActivity.this.progressBar.setVisibility(4);
            LoginActivity.this.loginButton.setEnabled(true);
        }

        /* renamed from: com.lux.luxcloud.view.login.LoginActivity$3$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ boolean val$clusterCheckResultInThread;
            final /* synthetic */ String val$passwordValue;
            final /* synthetic */ JSONObject val$result;

            AnonymousClass1(boolean z, JSONObject jSONObject, String str) {
                this.val$clusterCheckResultInThread = z;
                this.val$result = jSONObject;
                this.val$passwordValue = str;
            }

            /* JADX WARN: Removed duplicated region for block: B:108:0x040e A[Catch: Exception -> 0x0495, TryCatch #3 {Exception -> 0x0495, blocks: (B:15:0x00ae, B:17:0x00bf, B:18:0x00cc, B:20:0x00ef, B:21:0x00f8, B:24:0x016c, B:26:0x01fc, B:60:0x02b3, B:61:0x02b6, B:63:0x0313, B:68:0x0335, B:70:0x033f, B:71:0x034a, B:74:0x0356, B:76:0x036a, B:78:0x0374, B:80:0x037d, B:82:0x0383, B:84:0x038c, B:86:0x0392, B:88:0x039c, B:90:0x03a5, B:92:0x03af, B:94:0x03ba, B:96:0x03c4, B:98:0x03cf, B:100:0x03d9, B:102:0x03e4, B:103:0x03ee, B:104:0x03f1, B:105:0x03f4, B:106:0x0408, B:108:0x040e, B:109:0x0448, B:111:0x044e, B:112:0x045f, B:114:0x046e, B:115:0x0475, B:117:0x047b, B:67:0x032d, B:25:0x0177, B:64:0x031e), top: B:156:0x00ae, outer: #5, inners: #1 }] */
            /* JADX WARN: Removed duplicated region for block: B:124:0x04a3 A[Catch: Exception -> 0x0563, TryCatch #5 {Exception -> 0x0563, blocks: (B:5:0x001d, B:7:0x0021, B:9:0x0029, B:13:0x009b, B:122:0x0499, B:124:0x04a3, B:125:0x04bc, B:127:0x04cf, B:131:0x04f1, B:132:0x0503, B:136:0x051a, B:121:0x0496, B:12:0x0095, B:137:0x052c, B:139:0x0530, B:140:0x053b, B:142:0x0543, B:143:0x054e, B:145:0x0558, B:15:0x00ae, B:17:0x00bf, B:18:0x00cc, B:20:0x00ef, B:21:0x00f8, B:24:0x016c, B:26:0x01fc, B:60:0x02b3, B:61:0x02b6, B:63:0x0313, B:68:0x0335, B:70:0x033f, B:71:0x034a, B:74:0x0356, B:76:0x036a, B:78:0x0374, B:80:0x037d, B:82:0x0383, B:84:0x038c, B:86:0x0392, B:88:0x039c, B:90:0x03a5, B:92:0x03af, B:94:0x03ba, B:96:0x03c4, B:98:0x03cf, B:100:0x03d9, B:102:0x03e4, B:103:0x03ee, B:104:0x03f1, B:105:0x03f4, B:106:0x0408, B:108:0x040e, B:109:0x0448, B:111:0x044e, B:112:0x045f, B:114:0x046e, B:115:0x0475, B:117:0x047b, B:67:0x032d, B:25:0x0177, B:64:0x031e), top: B:160:0x001d, inners: #3 }] */
            /* JADX WARN: Removed duplicated region for block: B:125:0x04bc A[Catch: Exception -> 0x0563, TryCatch #5 {Exception -> 0x0563, blocks: (B:5:0x001d, B:7:0x0021, B:9:0x0029, B:13:0x009b, B:122:0x0499, B:124:0x04a3, B:125:0x04bc, B:127:0x04cf, B:131:0x04f1, B:132:0x0503, B:136:0x051a, B:121:0x0496, B:12:0x0095, B:137:0x052c, B:139:0x0530, B:140:0x053b, B:142:0x0543, B:143:0x054e, B:145:0x0558, B:15:0x00ae, B:17:0x00bf, B:18:0x00cc, B:20:0x00ef, B:21:0x00f8, B:24:0x016c, B:26:0x01fc, B:60:0x02b3, B:61:0x02b6, B:63:0x0313, B:68:0x0335, B:70:0x033f, B:71:0x034a, B:74:0x0356, B:76:0x036a, B:78:0x0374, B:80:0x037d, B:82:0x0383, B:84:0x038c, B:86:0x0392, B:88:0x039c, B:90:0x03a5, B:92:0x03af, B:94:0x03ba, B:96:0x03c4, B:98:0x03cf, B:100:0x03d9, B:102:0x03e4, B:103:0x03ee, B:104:0x03f1, B:105:0x03f4, B:106:0x0408, B:108:0x040e, B:109:0x0448, B:111:0x044e, B:112:0x045f, B:114:0x046e, B:115:0x0475, B:117:0x047b, B:67:0x032d, B:25:0x0177, B:64:0x031e), top: B:160:0x001d, inners: #3 }] */
            /* JADX WARN: Removed duplicated region for block: B:63:0x0313 A[Catch: Exception -> 0x0495, TRY_LEAVE, TryCatch #3 {Exception -> 0x0495, blocks: (B:15:0x00ae, B:17:0x00bf, B:18:0x00cc, B:20:0x00ef, B:21:0x00f8, B:24:0x016c, B:26:0x01fc, B:60:0x02b3, B:61:0x02b6, B:63:0x0313, B:68:0x0335, B:70:0x033f, B:71:0x034a, B:74:0x0356, B:76:0x036a, B:78:0x0374, B:80:0x037d, B:82:0x0383, B:84:0x038c, B:86:0x0392, B:88:0x039c, B:90:0x03a5, B:92:0x03af, B:94:0x03ba, B:96:0x03c4, B:98:0x03cf, B:100:0x03d9, B:102:0x03e4, B:103:0x03ee, B:104:0x03f1, B:105:0x03f4, B:106:0x0408, B:108:0x040e, B:109:0x0448, B:111:0x044e, B:112:0x045f, B:114:0x046e, B:115:0x0475, B:117:0x047b, B:67:0x032d, B:25:0x0177, B:64:0x031e), top: B:156:0x00ae, outer: #5, inners: #1 }] */
            /* JADX WARN: Removed duplicated region for block: B:70:0x033f A[Catch: Exception -> 0x0495, TryCatch #3 {Exception -> 0x0495, blocks: (B:15:0x00ae, B:17:0x00bf, B:18:0x00cc, B:20:0x00ef, B:21:0x00f8, B:24:0x016c, B:26:0x01fc, B:60:0x02b3, B:61:0x02b6, B:63:0x0313, B:68:0x0335, B:70:0x033f, B:71:0x034a, B:74:0x0356, B:76:0x036a, B:78:0x0374, B:80:0x037d, B:82:0x0383, B:84:0x038c, B:86:0x0392, B:88:0x039c, B:90:0x03a5, B:92:0x03af, B:94:0x03ba, B:96:0x03c4, B:98:0x03cf, B:100:0x03d9, B:102:0x03e4, B:103:0x03ee, B:104:0x03f1, B:105:0x03f4, B:106:0x0408, B:108:0x040e, B:109:0x0448, B:111:0x044e, B:112:0x045f, B:114:0x046e, B:115:0x0475, B:117:0x047b, B:67:0x032d, B:25:0x0177, B:64:0x031e), top: B:156:0x00ae, outer: #5, inners: #1 }] */
            /* JADX WARN: Removed duplicated region for block: B:74:0x0356 A[Catch: Exception -> 0x0495, TRY_ENTER, TryCatch #3 {Exception -> 0x0495, blocks: (B:15:0x00ae, B:17:0x00bf, B:18:0x00cc, B:20:0x00ef, B:21:0x00f8, B:24:0x016c, B:26:0x01fc, B:60:0x02b3, B:61:0x02b6, B:63:0x0313, B:68:0x0335, B:70:0x033f, B:71:0x034a, B:74:0x0356, B:76:0x036a, B:78:0x0374, B:80:0x037d, B:82:0x0383, B:84:0x038c, B:86:0x0392, B:88:0x039c, B:90:0x03a5, B:92:0x03af, B:94:0x03ba, B:96:0x03c4, B:98:0x03cf, B:100:0x03d9, B:102:0x03e4, B:103:0x03ee, B:104:0x03f1, B:105:0x03f4, B:106:0x0408, B:108:0x040e, B:109:0x0448, B:111:0x044e, B:112:0x045f, B:114:0x046e, B:115:0x0475, B:117:0x047b, B:67:0x032d, B:25:0x0177, B:64:0x031e), top: B:156:0x00ae, outer: #5, inners: #1 }] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws org.json.JSONException {
                /*
                    Method dump skipped, instructions count: 1394
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.login.LoginActivity.AnonymousClass3.AnonymousClass1.run():void");
            }

            /* renamed from: lambda$run$0$com-lux-luxcloud-view-login-LoginActivity$3$1, reason: not valid java name */
            /* synthetic */ void m601lambda$run$0$comluxluxcloudviewloginLoginActivity$3$1(UserData userData, DialogInterface dialogInterface) {
                userData.setCurrentInverter(userData.getUserVisitInverter(), false);
                if (userData.getPlants().size() == 1) {
                    userData.setCurrentPlant(userData.getPlants().get(0));
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, (Class<?>) (LoginActivity.this.useHtmlSettingPage ? Lv2MainActivity.class : MainActivity.class)));
                    LoginActivity.this.finish();
                } else {
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                    LoginActivity.this.finish();
                }
            }
        }
    }

    /* renamed from: lambda$onCreate$2$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m597lambda$onCreate$2$comluxluxcloudviewloginLoginActivity(Task task) {
        if (!task.isSuccessful()) {
            Log.w(Version.TAG, "Fetching FCM registration token failed", task.getException());
            return;
        }
        String str = (String) task.getResult();
        System.out.println("fcmToken == " + str);
        this.obtainTokenTime = System.currentTimeMillis();
        if (isUserLoggedIn) {
            sendTokenToServer();
        } else {
            this.fcmToken = str;
        }
    }

    private void setupClusterSpinnerWithRestore(final Spinner spinner) {
        PreferenceManager.getDefaultSharedPreferences(this);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                spinner.setVisibility(8);
            }
        });
    }

    /* renamed from: com.lux.luxcloud.view.login.LoginActivity$4, reason: invalid class name */
    class AnonymousClass4 implements AdapterView.OnItemSelectedListener {
        final /* synthetic */ Map val$clusterMap;
        final /* synthetic */ boolean[] val$first;
        final /* synthetic */ SharedPreferences val$sp;

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        AnonymousClass4(boolean[] zArr, Map map, SharedPreferences sharedPreferences) {
            this.val$first = zArr;
            this.val$clusterMap = map;
            this.val$sp = sharedPreferences;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) throws NumberFormatException {
            boolean[] zArr = this.val$first;
            if (zArr[0]) {
                zArr[0] = false;
                return;
            }
            long j2 = Long.parseLong(((Property) adapterView.getItemAtPosition(i)).getName());
            Cluster cluster = (Cluster) this.val$clusterMap.get(Long.valueOf(j2));
            LoginActivity.this.applyClusterSelection(j2, cluster);
            String clusterPrefixUrl = (cluster == null || Tool.isEmpty(cluster.getClusterPrefixUrl())) ? LoginActivity.this.loginBaseUrl : cluster.getClusterPrefixUrl();
            this.val$sp.edit().putLong(LoginActivity.CLUSTER_ID, j2).putString(LoginActivity.CLUSTER_URL, clusterPrefixUrl).putString(LoginActivity.CLUSTER_HOST, LoginActivity.canonicalHost(clusterPrefixUrl)).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyClusterSelection(long j, Cluster cluster) {
        if (cluster == null || 2 == j) {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_FIRST);
        } else if (200 == j) {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_INDIA);
        } else if (500 == j) {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_EU2);
        } else if (300 == j) {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_PHILIPPINES);
        } else if (400 == j) {
            GlobalInfo.getInstance().setCurrentClusterGroup(Constants.CLUSTER_GROUP_VIETNAM);
        }
        Constants.initValidServerIndexMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String canonicalizeUrl(String str) {
        if (Tool.isEmpty(str)) {
            return null;
        }
        String strTrim = str.trim();
        return strTrim.contains("server.luxpowertek.com") ? strTrim.replace("server.luxpowertek.com", "as.luxpowertek.com") : strTrim;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String canonicalHost(String str) {
        if (Tool.isEmpty(str)) {
            return null;
        }
        String strReplace = str.trim().replace("server.luxpowertek.com", "as.luxpowertek.com");
        if (!strReplace.startsWith("http://") && !strReplace.startsWith("https://")) {
            strReplace = "http://" + strReplace;
        }
        try {
            String host = new URI(strReplace).getHost();
            if (host == null) {
                String strReplaceFirst = strReplace.replaceFirst("^[a-zA-Z]+://", "");
                int iIndexOf = strReplaceFirst.indexOf(47);
                if (iIndexOf >= 0) {
                    strReplaceFirst = strReplaceFirst.substring(0, iIndexOf);
                }
                host = strReplaceFirst;
            }
            return host.toLowerCase(Locale.US);
        } catch (Exception unused) {
            return null;
        }
    }

    private void checkAutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        this.autoLoginChecked = sharedPreferences.getBoolean(AUTO_LOGIN_CHECKED, false);
        boolean z = sharedPreferences.getBoolean(isNotification, true);
        if (this.autoLoginChecked && !this.fromLogout && z) {
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m589x611cd184();
                }
            });
        }
    }

    /* renamed from: lambda$checkAutoLogin$4$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m589x611cd184() {
        this.loginButton.performClick();
    }

    private void getAddressFromLocation(double d, double d2) throws IOException {
        try {
            List<Address> fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(d, d2, 1);
            if (fromLocation.isEmpty()) {
                return;
            }
            Address address = fromLocation.get(0);
            String str = "Country: " + address.getCountryName() + " (" + address.getCountryCode() + ")\nAdmin Area: " + address.getAdminArea() + "\nSub Admin Area: " + address.getSubAdminArea() + "\nLocality: " + address.getLocality() + "\nSub Locality: " + address.getSubLocality() + "\nThoroughfare: " + address.getThoroughfare() + "\nSub Thoroughfare: " + address.getSubThoroughfare();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(HttpHeaders.LOCATION);
            builder.setMessage(str).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to get street address", 0).show();
        }
    }

    private void getAllFirmwares(final Context context) {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m594x79b78686(context);
            }
        }).start();
    }

    /* renamed from: lambda$getAllFirmwares$5$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m594x79b78686(Context context) {
        try {
            if (!hasInternet(context)) {
                System.out.println("No internet, using cached data");
                return;
            }
            SharedPreferences.Editor editorEdit = getSharedPreferences("userInfo", 0).edit();
            HashMap map = new HashMap();
            if (WarrantyActivity.testModeEnable) {
                map.put("isTest", String.valueOf(true));
            }
            map.put("encrypted", String.valueOf(true));
            map.put("platform", Custom.APP_USER_PLATFORM.name());
            System.out.println("params == " + map);
            JSONObject jSONObjectPostJson = HttpTool.postJson("https://res.solarcloudsystem.com:8443/resource/getAllFirmware", map);
            if (jSONObjectPostJson != null) {
                editorEdit.putString("firmwares", jSONObjectPostJson.toString());
                editorEdit.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTokenToServer() {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f$0.m598x2818c5a9();
            }
        }).start();
    }

    /* renamed from: lambda$sendTokenToServer$6$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m598x2818c5a9() throws JSONException {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void findNewVersion() {
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m593xf7faf4e7();
            }
        }).start();
    }

    /* renamed from: lambda$findNewVersion$8$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m593xf7faf4e7() {
        try {
            HashMap map = new HashMap();
            map.put("envPlatform", "ANDROID");
            map.put("versionCode", this.appVersion);
            map.put("appType", "MONITOR");
            map.put("userPlatform", Custom.APP_USER_PLATFORM.name());
            JSONObject jSONObjectPostJson = HttpTool.postJson("https://res.solarcloudsystem.com:8443/resource/appVersion/findNew", map);
            if (jSONObjectPostJson != null) {
                this.newVersion = jSONObjectPostJson.getString("msg");
                if (jSONObjectPostJson.getBoolean(Constants.ScionAnalytics.MessageType.DATA_MESSAGE)) {
                    runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m592x12b98626();
                        }
                    });
                } else {
                    checkAutoLogin();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$findNewVersion$7$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m592x12b98626() {
        checkNewVersion(this.newVersion);
    }

    private void checkNewVersion(final String str) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        final SharedPreferences preferences = getPreferences(0);
        String string = preferences.getString(IGNORE_VERSION, "");
        if (Tool.isEmpty(string) || !string.equals(str)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.new_app_version_released));
            builder.setMessage(getString(R.string.new_app_version_released_message, new Object[]{str, getString(R.string.app_name)}));
            builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m590x5e5e35d(dialogInterface, i);
                }
            });
            builder.setNegativeButton(getString(R.string.skip), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences.Editor editorEdit = preferences.edit();
                    editorEdit.putString(LoginActivity.IGNORE_VERSION, str);
                    editorEdit.apply();
                }
            });
            builder.setNeutralButton(getString(R.string.later), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity.6
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

    /* renamed from: lambda$checkNewVersion$9$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m590x5e5e35d(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + this.packageName));
        intent.setAction("android.intent.action.VIEW");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void agreePrivacyAndTermChecked() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pharse_agree_to_terms);
        AppCompatTextView appCompatTextView = new AppCompatTextView(this);
        appCompatTextView.setText(getAgreeToTermsText());
        appCompatTextView.setTextSize(14.0f);
        int i = (int) (getResources().getDisplayMetrics().density * 16.0f);
        int i2 = (int) (getResources().getDisplayMetrics().density * 12.0f);
        appCompatTextView.setPadding(i, i2, i, i2);
        appCompatTextView.setLinksClickable(true);
        appCompatTextView.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setView(appCompatTextView);
        builder.setPositiveButton(getString(R.string.phrase_button_agree_and_login), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                this.f$0.m588xe72294ec(dialogInterface, i3);
            }
        });
        builder.setNegativeButton(getString(R.string.phase_cancel), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i3) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.show();
        alertDialogCreate.getButton(-1).setTextColor(ContextCompat.getColor(this, R.color.main_green));
        alertDialogCreate.getButton(-2).setTextColor(-7829368);
    }

    /* renamed from: lambda$agreePrivacyAndTermChecked$11$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m588xe72294ec(DialogInterface dialogInterface, int i) {
        this.agreeCheckBox.setChecked(true);
        runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m587x1e1262b();
            }
        });
    }

    /* renamed from: lambda$agreePrivacyAndTermChecked$10$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m587x1e1262b() {
        this.loginButton.performClick();
    }

    private SpannableString getAgreeToTermsText() {
        String string = getString(R.string.phrase_privacy_policy);
        String string2 = getString(R.string.phrase_user_terms);
        String string3 = getString(R.string.login_agree_text, new Object[]{string, string2});
        SpannableString spannableString = new SpannableString(string3);
        final int color = ContextCompat.getColor(this, R.color.main_green);
        int iIndexOf = string3.indexOf(string);
        spannableString.setSpan(new ClickableSpan() { // from class: com.lux.luxcloud.view.login.LoginActivity.8
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.solarcloudsystem.com/PrivacyPolicy")));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(color);
            }
        }, iIndexOf, string.length() + iIndexOf, 33);
        int iIndexOf2 = string3.indexOf(string2);
        spannableString.setSpan(new ClickableSpan() { // from class: com.lux.luxcloud.view.login.LoginActivity.9
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.solarcloudsystem.com/TermsOfService")));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(color);
            }
        }, iIndexOf2, string2.length() + iIndexOf2, 33);
        return spannableString;
    }

    public void clickForgetPasswordButton(View view) {
        startActivity(new Intent(this, (Class<?>) ForgetPasswordActivity.class));
    }

    public void clickRegisterButton2(View view) {
        if (GlobalInfo.getInstance().isInited()) {
            Intent intent = new Intent(this, (Class<?>) Lv2RegisterActivity.class);
            intent.putExtra("fromLogin", true);
            startActivity(intent);
        } else {
            Tool.alert(this, R.string.phrase_please_wait_seconds);
            GlobalInfo.getInstance().isIniting();
        }
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
        Intent intent = new Intent(this, (Class<?>) WifiConnectActivity.class);
        intent.putExtra("newVersionCode", this.newVersion);
        intent.putExtra("fromLogin", true);
        startActivity(intent);
    }

    public void clickWarrantyButton(View view) {
        startActivity(new Intent(this, (Class<?>) WarrantyActivity.class));
    }

    public void clickLocalConnectButton(View view) {
        LocalConnectTool.go2LocalActivity(this, com.lux.luxcloud.global.Constants.TARGET_LOCAL_CONNECT, 99, (Button) view.findViewById(R.id.login_localButton), this.progressBar);
    }

    public void clickGoToRegisterButton(View view) {
        if (PLATFORM.LUX_POWER.equals(Custom.APP_USER_PLATFORM) || PLATFORM.MID.equals(Custom.APP_USER_PLATFORM)) {
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.dialog_title_subtitle, (ViewGroup) null);
            ((TextView) viewInflate.findViewById(R.id.tvTitle)).setText(getString(R.string.select_registration_version));
            ((TextView) viewInflate.findViewById(R.id.tvSubtitle)).setText(getString(R.string.phase_choose_register_version));
            new AlertDialog.Builder(this).setCustomTitle(viewInflate).setItems(new String[]{getString(R.string.register_version_old), getString(R.string.register_version_new)}, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m591x183d0c27(dialogInterface, i);
                }
            }).setNegativeButton(getString(R.string.phrase_button_cancel), (DialogInterface.OnClickListener) null).show();
            return;
        }
        if (GlobalInfo.getInstance().isInited()) {
            startActivity(new Intent(this, (Class<?>) RegisterActivity.class));
        } else {
            Tool.alert(this, R.string.phrase_please_wait_seconds);
            GlobalInfo.getInstance().isIniting();
        }
    }

    /* renamed from: lambda$clickGoToRegisterButton$12$com-lux-luxcloud-view-login-LoginActivity, reason: not valid java name */
    /* synthetic */ void m591x183d0c27(DialogInterface dialogInterface, int i) {
        if (i == 1) {
            goToRegister(Lv2RegisterActivity.class, true);
        } else {
            goToRegister(RegisterActivity.class, true);
        }
    }

    private void goToRegister(Class<?> cls, boolean z) {
        if (GlobalInfo.getInstance().isInited()) {
            Intent intent = new Intent(this, cls);
            if (z) {
                intent.putExtra("fromLogin", true);
            }
            startActivity(intent);
            return;
        }
        Tool.alert(this, R.string.phrase_please_wait_seconds);
        GlobalInfo.getInstance().isIniting();
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
        if (!this.autoLoginChecked) {
            findNewVersion();
        } else {
            new Handler().postDelayed(new Runnable() { // from class: com.lux.luxcloud.view.login.LoginActivity$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.findNewVersion();
                }
            }, 10L);
        }
        handleIntent(getIntent(), getBaseContext());
    }

    private boolean hasInternet(Context context) {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())) == null || !networkCapabilities.hasCapability(12)) ? false : true;
    }

    private void handleIntent(Intent intent, Context context) {
        SharedPreferences preferences = getPreferences(0);
        if (WarrantyActivity.testModeEnable) {
            getAllFirmwares(context);
        }
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