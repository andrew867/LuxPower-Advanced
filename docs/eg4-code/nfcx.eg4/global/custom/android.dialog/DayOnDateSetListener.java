package com.nfcx.eg4.global.custom.android.dialog;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import com.nfcx.eg4.tool.InvTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class DayOnDateSetListener implements DatePickerDialog.OnDateSetListener {
    private EditText editText;

    public DayOnDateSetListener(EditText editText) {
        this.editText = editText;
    }

    @Override // android.app.DatePickerDialog.OnDateSetListener
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        this.editText.setText(i + "-" + InvTool.fillZero(i2 + 1) + "-" + InvTool.fillZero(i3));
    }
}