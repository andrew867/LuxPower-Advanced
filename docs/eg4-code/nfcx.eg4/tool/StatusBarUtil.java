package com.nfcx.eg4.tool;

import android.app.Activity;
import android.view.View;
import android.view.Window;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class StatusBarUtil {
    private final Activity activity;

    public StatusBarUtil(Activity activity) {
        this.activity = activity;
    }

    public void setColor(int i) {
        Window window = this.activity.getWindow();
        window.getDecorView().setSystemUiVisibility(1280);
        window.setStatusBarColor(i);
    }

    public void setTextColor(boolean z) {
        View decorView = this.activity.getWindow().getDecorView();
        if (z) {
            decorView.setSystemUiVisibility(1280);
        } else {
            decorView.setSystemUiVisibility(9216);
        }
    }

    public int getStatusBarHeight() {
        int identifier = this.activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return this.activity.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public void setupStatusBar(int i, boolean z, int i2) {
        setColor(i);
        setTextColor(z);
    }
}