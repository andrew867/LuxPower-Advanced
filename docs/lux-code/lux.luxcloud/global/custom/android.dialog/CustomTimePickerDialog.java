package com.lux.luxcloud.global.custom.android.dialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class CustomTimePickerDialog extends TimePickerDialog {
    public CustomTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener, String str) {
        super(context, onTimeSetListener, Integer.parseInt(str.substring(0, 2)), Integer.parseInt(str.substring(3, 5)), true);
    }

    @Override // android.app.TimePickerDialog, android.widget.TimePicker.OnTimeChangedListener
    public void onTimeChanged(TimePicker timePicker, int i, int i2) {
        super.onTimeChanged(timePicker, i, i2);
    }
}