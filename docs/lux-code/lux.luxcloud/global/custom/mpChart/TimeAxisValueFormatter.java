package com.lux.luxcloud.global.custom.mpChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.lux.luxcloud.tool.InvTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class TimeAxisValueFormatter implements IAxisValueFormatter {
    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public String getFormattedValue(float f, AxisBase axisBase) {
        int i = (int) f;
        return InvTool.fillZero(i / 3600) + ":" + InvTool.fillZero((i % 3600) / 60);
    }
}