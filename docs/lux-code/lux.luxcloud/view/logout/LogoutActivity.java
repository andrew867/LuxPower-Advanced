package com.lux.luxcloud.view.logout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.lux.luxcloud.R;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.userCenter.EditUserActivity;
import com.lux.luxcloud.view.userCenter.ModifyPasswordActivity;
import com.lux.luxcloud.view.userCenter.NewUserCenterActivity;
import com.lux.luxcloud.view.userCenter.UserCenterActivity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LogoutActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_logout);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
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