package com.nfcx.eg4.global.custom.mpChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.nfcx.eg4.tool.InvTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class TimeAxisValueFormatter implements IAxisValueFormatter {
    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public String getFormattedValue(float f, AxisBase axisBase) {
        int i = (int) f;
        return InvTool.fillZero(i / 3600) + ":" + InvTool.fillZero((i % 3600) / 60);
    }
}