package com.lux.luxcloud.global.custom.android.dialog;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import com.lux.luxcloud.tool.InvTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
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