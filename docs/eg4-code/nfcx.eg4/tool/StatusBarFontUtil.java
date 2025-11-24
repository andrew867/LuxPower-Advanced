package com.nfcx.eg4.tool;

import android.app.Activity;
import android.view.Window;
import androidx.core.view.WindowInsetsControllerCompat;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class StatusBarFontUtil {
    public static void setStatusBarTextColor(Activity activity, boolean z) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightStatusBars(z);
    }
}