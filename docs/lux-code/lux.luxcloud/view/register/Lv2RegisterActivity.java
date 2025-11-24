package com.lux.luxcloud.view.register;

import android.app.UiModeManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.webView.CommonJsBridge;
import java.util.Date;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Lv2RegisterActivity extends AppCompatActivity {
    public static Lv2RegisterActivity instance;
    private boolean isDarkTheme;
    private ProgressBar loadProgressBar;
    private long mExitTime;
    private WebView webView;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(1);
        setContentView(R.layout.activity_lv2_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.layoutInDisplayCutoutMode = 1;
                getWindow().setAttributes(attributes);
                new StatusBarUtil(this).setupStatusBar(0, this.isDarkTheme, R.id.container);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        instance = this;
        checkAndRequestPermissions();
        initView();
    }

    private void applyStatusBarIconColor(String str) {
        getWindow().getDecorView().setSystemUiVisibility("light".equals(str) ? 9472 : 1280);
    }

    private void checkAndRequestPermissions() {
        String[] requiredPermissions = getRequiredPermissions();
        int length = requiredPermissions.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            } else if (ContextCompat.checkSelfPermission(this, requiredPermissions[i]) != 0) {
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return;
        }
        ActivityCompat.requestPermissions(this, requiredPermissions, Constants.FILECHOOSER_RESULTCODE);
    }

    private String[] getRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= 33) {
            return new String[]{"android.permission.READ_MEDIA_IMAGES"};
        }
        return new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    }

    private void initView() {
        String queryParameter;
        boolean z;
        String queryParameter2;
        String queryParameter3;
        String str;
        boolean booleanExtra = getIntent().getBooleanExtra("fromLogin", false);
        Uri data = getIntent().getData();
        if (data != null) {
            queryParameter = data.getQueryParameter("registerId");
            queryParameter3 = data.getQueryParameter("params");
            queryParameter2 = data.getQueryParameter("platform");
            Log.d("AppLink", "registerId = " + queryParameter + ", platform = " + queryParameter2 + ", params empty = " + Tool.isEmpty(queryParameter3));
            if (Tool.isEmpty(queryParameter) || Tool.isEmpty(queryParameter3) || Tool.isEmpty(queryParameter2)) {
                z = false;
            } else if (isRegisterIdExpired(queryParameter, 3)) {
                Log.d("RegisterIdCheck", "registerId 已过期");
                Tool.alertNotInUiThread(this, "The link has expired, please reacquire it");
                z = false;
            } else {
                z = true;
            }
        } else {
            queryParameter = null;
            z = false;
            queryParameter2 = null;
            queryParameter3 = null;
        }
        this.loadProgressBar = (ProgressBar) findViewById(R.id.load_progressBar);
        WebView webView = (WebView) findViewById(R.id.activity_lv2_main_webview);
        this.webView = webView;
        webView.setBackgroundColor(0);
        WebSettings settings = this.webView.getSettings();
        settings.setCacheMode(2);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(true);
        settings.setMediaPlaybackRequiresUserGesture(true);
        settings.setGeolocationEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(false);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= 28) {
            this.webView.getSettings().setMixedContentMode(0);
        }
        this.webView.clearCache(true);
        this.webView.clearHistory();
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.lux.luxcloud.view.register.Lv2RegisterActivity.1
            @Override // android.webkit.WebChromeClient
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                permissionRequest.grant(permissionRequest.getResources());
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str2, GeolocationPermissions.Callback callback) {
                if (ContextCompat.checkSelfPermission(Lv2RegisterActivity.instance, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                    ActivityCompat.requestPermissions(Lv2RegisterActivity.instance, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    callback.invoke(str2, true, false);
                    super.onGeolocationPermissionsShowPrompt(str2, callback);
                }
            }
        });
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.lux.luxcloud.view.register.Lv2RegisterActivity.2
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str2, Bitmap bitmap) {
                super.onPageStarted(webView2, str2, bitmap);
                System.out.println("LuxPower - http start time: " + System.currentTimeMillis());
                Lv2RegisterActivity.this.loadProgressBar.setVisibility(0);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str2) {
                super.onPageFinished(webView2, str2);
                Lv2RegisterActivity.this.loadProgressBar.setVisibility(4);
                Lv2RegisterActivity.this.webView.loadUrl("javascript:delInfo()");
                System.out.println("LuxPower - http onPageFinished url == " + str2);
                System.out.println("LuxPower - http end time: " + System.currentTimeMillis());
            }
        });
        this.webView.addJavascriptInterface(new lv2RegWebAppInterface(this), "lv2RegWebAppInterface");
        this.webView.addJavascriptInterface(new CommonJsBridge(this), "AppBridge");
        if (!z) {
            str = "https://app_test.solarcloudsystem.com:8083/register?platform=" + getPlatformByCustomPlatForm();
        } else {
            str = booleanExtra ? "https://app_test.solarcloudsystem.com:8083/register?platform=" + getPlatformByCustomPlatForm() : "https://app_test.solarcloudsystem.com:8083/registerStep";
            if (!queryParameter.isEmpty() && !queryParameter3.isEmpty() && !queryParameter2.isEmpty()) {
                str = str + "?params=" + Uri.encode(queryParameter3) + "&platform=" + queryParameter2 + "&registerId=" + Uri.encode(queryParameter);
            }
        }
        String str2 = str + "?v=" + System.currentTimeMillis();
        Log.d("FinalURL", str2);
        this.webView.loadUrl(str2);
    }

    /* renamed from: com.lux.luxcloud.view.register.Lv2RegisterActivity$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$lux$luxcloud$global$bean$user$PLATFORM;

        static {
            int[] iArr = new int[PLATFORM.values().length];
            $SwitchMap$com$lux$luxcloud$global$bean$user$PLATFORM = iArr;
            try {
                iArr[PLATFORM.MID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$user$PLATFORM[PLATFORM.LUX_POWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public String getPlatformByCustomPlatForm() {
        return AnonymousClass3.$SwitchMap$com$lux$luxcloud$global$bean$user$PLATFORM[Custom.APP_USER_PLATFORM.ordinal()] != 1 ? "luxcloud" : "pownova";
    }

    private boolean isRegisterIdExpired(String str, int i) {
        if (str != null && str.contains("T")) {
            try {
                String[] strArrSplit = str.split("-");
                if (strArrSplit.length < 3) {
                    return true;
                }
                return new Date().getTime() - Tool.parseRegisterIdTime(strArrSplit[2]).getTime() > (((((long) i) * 24) * 60) * 60) * 1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean canGoBackInWebView() {
        WebView webView = this.webView;
        if (webView != null) {
            return webView.canGoBack();
        }
        return false;
    }

    public void goBackInWebView() {
        this.webView.goBack();
    }

    public class lv2RegWebAppInterface {
        private Lv2RegisterActivity activity;

        lv2RegWebAppInterface(Lv2RegisterActivity lv2RegisterActivity) {
            this.activity = lv2RegisterActivity;
        }

        @JavascriptInterface
        public void toLoginPage(String str, String str2) {
            Intent intent = new Intent(Lv2RegisterActivity.instance, (Class<?>) LoginActivity.class);
            intent.putExtra("account", str);
            intent.putExtra("password", str2);
            Lv2RegisterActivity.this.startActivity(intent);
        }

        @JavascriptInterface
        public void back() {
            Lv2RegisterActivity.this.startActivity(new Intent(Lv2RegisterActivity.instance, (Class<?>) LoginActivity.class));
            Lv2RegisterActivity.instance.finish();
        }

        @JavascriptInterface
        public void exit() {
            Lv2RegisterActivity.this.finishAffinity();
            System.exit(0);
            Lv2RegisterActivity.instance.finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void toast(org.json.JSONObject r6) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 626
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.register.Lv2RegisterActivity.toast(org.json.JSONObject):void");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
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