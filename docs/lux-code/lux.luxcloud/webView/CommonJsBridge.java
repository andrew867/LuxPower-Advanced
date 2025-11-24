package com.lux.luxcloud.webView;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.view.main.Lv2MainActivity;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.userCenter.EditUserActivity;
import com.lux.luxcloud.view.userCenter.ModifyPasswordActivity;
import com.lux.luxcloud.view.userCenter.NewUserCenterActivity;
import com.lux.luxcloud.view.userCenter.UserCenterActivity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class CommonJsBridge {
    private final Activity activity;
    private boolean isDarkTheme;

    public CommonJsBridge(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void toLoginPage(String str, String str2) {
        Intent intent = new Intent(this.activity, (Class<?>) LoginActivity.class);
        intent.putExtra("account", str);
        intent.putExtra("password", str2);
        this.activity.startActivity(intent);
        if (PlantOverviewActivity.instance != null) {
            PlantOverviewActivity.instance.finish();
        }
        if (PlantListActivity.instance != null) {
            PlantListActivity.instance.finish();
        }
        if (MainActivity.instance != null) {
            MainActivity.instance.finish();
        }
        if (Lv2MainActivity.instance != null) {
            Lv2MainActivity.instance.finish();
        }
        if (UserCenterActivity.instance != null) {
            UserCenterActivity.instance.finish();
        }
        if (NewUserCenterActivity.instance != null) {
            NewUserCenterActivity.instance.finish();
        }
        if (EditUserActivity.instance != null) {
            EditUserActivity.instance.finish();
        }
        if (ModifyPasswordActivity.instance != null) {
            ModifyPasswordActivity.instance.finish();
        }
    }

    @JavascriptInterface
    public boolean getDarkTheme() {
        boolean z = ((UiModeManager) this.activity.getSystemService("uimode")).getNightMode() == 2;
        this.isDarkTheme = z;
        return z;
    }

    @JavascriptInterface
    public void back() {
        this.activity.startActivity(new Intent(this.activity, (Class<?>) LoginActivity.class));
        this.activity.finish();
        if (PlantOverviewActivity.instance != null) {
            PlantOverviewActivity.instance.finish();
        }
        if (PlantListActivity.instance != null) {
            PlantListActivity.instance.finish();
        }
        if (MainActivity.instance != null) {
            MainActivity.instance.finish();
        }
        if (Lv2MainActivity.instance != null) {
            Lv2MainActivity.instance.finish();
        }
        if (UserCenterActivity.instance != null) {
            UserCenterActivity.instance.finish();
        }
        if (NewUserCenterActivity.instance != null) {
            NewUserCenterActivity.instance.finish();
        }
        if (EditUserActivity.instance != null) {
            EditUserActivity.instance.finish();
        }
        if (ModifyPasswordActivity.instance != null) {
            ModifyPasswordActivity.instance.finish();
        }
    }
}