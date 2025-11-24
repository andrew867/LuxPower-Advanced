package com.lux.luxcloud.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.lux.luxcloud.R;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class NewVersionDialogActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_version_dialog);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        ((TextView) findViewById(R.id.new_version_dialog_contentTextView)).setText(String.format(getString(R.string.new_version_dialog_text), getIntent().getStringExtra("newVersion")));
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        finish();
        return true;
    }

    public void updateButtonYes(View view) {
        Intent intent = new Intent(this, (Class<?>) LoginActivity.class);
        intent.putExtra("updateNewVersion", true);
        startActivity(intent);
        finish();
    }

    public void updateButtonNo(View view) {
        finish();
    }
}