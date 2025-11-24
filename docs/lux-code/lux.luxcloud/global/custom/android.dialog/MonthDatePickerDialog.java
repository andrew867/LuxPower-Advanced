package com.lux.luxcloud.global.custom.android.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import com.lux.luxcloud.tool.InvTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class MonthDatePickerDialog extends AbstractDatePickerDialog {
    public MonthDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener, String str) {
        super(context, onDateSetListener, Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(5, 7)) - 1, 1);
        setTitle(str.substring(0, 7));
    }

    @Override // android.app.DatePickerDialog, android.widget.DatePicker.OnDateChangedListener
    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        super.onDateChanged(datePicker, i, i2, i3);
        setTitle(i + "-" + InvTool.fillZero(i2 + 1));
    }
}