package com.lux.luxcloud.view.shave;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class PeakShavingActivity extends Activity {
    public static PeakShavingActivity instance;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_local_12k_peak_shaving_set);
        instance = this;
        if (!GlobalInfo.getInstance().getUserData().needShowCompanyLogo()) {
            findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.shave.PeakShavingActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PeakShavingActivity.instance.finish();
            }
        });
    }
}