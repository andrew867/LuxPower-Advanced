package com.nfcx.eg4.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.nfcx.eg4.R;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class NewVersionDialogActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_version_dialog);
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