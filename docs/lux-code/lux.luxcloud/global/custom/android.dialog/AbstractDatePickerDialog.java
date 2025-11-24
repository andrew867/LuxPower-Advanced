package com.lux.luxcloud.global.custom.android.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public abstract class AbstractDatePickerDialog extends DatePickerDialog {
    public AbstractDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        super(context, onDateSetListener, i, i2, i3);
    }

    public DatePicker findDatePicker(ViewGroup viewGroup) {
        DatePicker datePickerFindDatePicker;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof DatePicker) {
                return (DatePicker) childAt;
            }
            if ((childAt instanceof ViewGroup) && (datePickerFindDatePicker = findDatePicker((ViewGroup) childAt)) != null) {
                return datePickerFindDatePicker;
            }
        }
        return null;
    }
}