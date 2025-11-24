package com.nfcx.eg4.global.custom.android.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class YearDatePickerDialog extends AbstractDatePickerDialog {
    public YearDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener, String str) {
        super(context, onDateSetListener, Integer.parseInt(str.substring(0, 4)), 0, 1);
        setTitle(str.substring(0, 4));
    }

    @Override // android.app.DatePickerDialog, android.widget.DatePicker.OnDateChangedListener
    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        super.onDateChanged(datePicker, i, i2, i3);
        setTitle(String.valueOf(i));
    }
}