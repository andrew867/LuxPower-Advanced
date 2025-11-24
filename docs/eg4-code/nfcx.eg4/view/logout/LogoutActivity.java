package com.nfcx.eg4.view.logout;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.nfcx.eg4.R;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.view.login.LoginActivity;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import com.nfcx.eg4.view.userCenter.EditUserActivity;
import com.nfcx.eg4.view.userCenter.ModifyPasswordActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import com.nfcx.eg4.view.userCenter.UserCenterActivity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class LogoutActivity extends Activity {
    private boolean isDarkTheme;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_logout);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        finish();
        return true;
    }

    public void exitButtonYes(View view) {
        doLogout(this);
    }

    public static void doLogout(Activity activity) {
        Intent intent = new Intent(activity, (Class<?>) LoginActivity.class);
        intent.putExtra("fromLogout", true);
        activity.startActivity(intent);
        activity.finish();
        if (PlantOverviewActivity.instance != null) {
            PlantOverviewActivity.instance.finish();
        }
        if (PlantListActivity.instance != null) {
            PlantListActivity.instance.finish();
        }
        if (MainActivity.instance != null) {
            MainActivity.instance.finish();
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

    public void exitButtonNo(View view) {
        finish();
    }
}