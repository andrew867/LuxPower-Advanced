package com.nfcx.eg4.view.splash;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.view.login.LoginActivity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class SplashActivity extends Activity {
    private boolean isDarkTheme;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFormat(1);
        setContentView(R.layout.activity_splash);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        GlobalInfo.getInstance().initializeGlobalInfo(this, getResources().getConfiguration().locale);
        new Handler().postDelayed(new Runnable() { // from class: com.nfcx.eg4.view.splash.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent(SplashActivity.this, (Class<?>) LoginActivity.class);
                intent.putExtra("checkNewVersion", true);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 2000L);
    }
}