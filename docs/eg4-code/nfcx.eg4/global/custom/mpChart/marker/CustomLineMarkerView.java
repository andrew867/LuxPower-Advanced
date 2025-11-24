package com.nfcx.eg4.global.custom.mpChart.marker;

import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.nfcx.eg4.R;
import com.nfcx.eg4.tool.InvTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class CustomLineMarkerView extends MarkerView {
    private TextView batteryTextView;
    private TextView consumptionTextView;
    private TextView gridTextView;
    private LineChart lineChart;
    private TextView solarPvTextView;
    private TextView xValueTextView;

    public CustomLineMarkerView(Context context, LineChart lineChart, int i) {
        super(context, i);
        this.lineChart = lineChart;
        this.xValueTextView = (TextView) findViewById(R.id.xValueTextView);
        this.solarPvTextView = (TextView) findViewById(R.id.solarPvTextView);
        this.batteryTextView = (TextView) findViewById(R.id.batteryTextView);
        this.gridTextView = (TextView) findViewById(R.id.gridTextView);
        this.consumptionTextView = (TextView) findViewById(R.id.consumptionTextView);
    }

    @Override // com.github.mikephil.charting.components.MarkerView, com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
        Integer numValueOf;
        int iRound = Math.round(entry.getX());
        this.xValueTextView.setText(InvTool.fillZero(iRound / 3600) + ":" + InvTool.fillZero((iRound % 3600) / 60) + ":" + InvTool.fillZero(iRound % 60));
        LineDataSet lineDataSet = (LineDataSet) ((LineData) this.lineChart.getData()).getDataSets().get(highlight.getDataSetIndex());
        int i = 0;
        while (true) {
            if (i >= lineDataSet.getValues().size()) {
                numValueOf = null;
                break;
            } else {
                if (((Entry) lineDataSet.getValues().get(i)).getX() == entry.getX()) {
                    numValueOf = Integer.valueOf(i);
                    break;
                }
                i++;
            }
        }
        if (numValueOf != null) {
            for (int i2 = 0; i2 < ((LineData) this.lineChart.getData()).getDataSets().size(); i2++) {
                Entry entry2 = (Entry) ((LineDataSet) ((IDataSet) ((LineData) this.lineChart.getData()).getDataSets().get(i2))).getValues().get(numValueOf.intValue());
                if (i2 == 0) {
                    this.solarPvTextView.setText("" + entry2.getY());
                } else if (i2 == 1) {
                    this.batteryTextView.setText("" + entry2.getY());
                } else if (i2 == 2) {
                    this.gridTextView.setText("" + entry2.getY());
                } else if (i2 == 3) {
                    this.consumptionTextView.setText("" + entry2.getY());
                }
            }
        }
        super.refreshContent(entry, highlight);
    }

    @Override // com.github.mikephil.charting.components.MarkerView, com.github.mikephil.charting.components.IMarker
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}