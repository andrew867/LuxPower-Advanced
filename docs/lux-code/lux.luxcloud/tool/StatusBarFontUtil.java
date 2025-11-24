package com.lux.luxcloud.tool;

import android.app.Activity;
import android.view.Window;
import androidx.core.view.WindowInsetsControllerCompat;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class StatusBarFontUtil {
    public static void setStatusBarTextColor(Activity activity, boolean z) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightStatusBars(z);
    }
}