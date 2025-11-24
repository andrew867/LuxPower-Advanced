package com.nfcx.eg4.global.custom.mpChart.marker;

import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.nfcx.eg4.R;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class CustomBarMarkerView extends MarkerView {
    private static final int ENERGY_TYPE_MONTH = 0;
    private static final int ENERGY_TYPE_TOTAL = 2;
    private static final int ENERGY_TYPE_YEAR = 1;
    private BarChart barChart;
    private TextView batteryTextView;
    private TextView consumptionTextView;
    private int currentEnergyType;
    private TextView gridTextView;
    private TextView solarPvTextView;
    private TextView xValueTextView;

    public CustomBarMarkerView(Context context, BarChart barChart, int i, int i2) {
        super(context, i);
        this.currentEnergyType = i2;
        this.barChart = barChart;
        this.xValueTextView = (TextView) findViewById(R.id.xValueTextView);
        this.solarPvTextView = (TextView) findViewById(R.id.solarPvTextView);
        this.batteryTextView = (TextView) findViewById(R.id.batteryTextView);
        this.gridTextView = (TextView) findViewById(R.id.gridTextView);
        this.consumptionTextView = (TextView) findViewById(R.id.consumptionTextView);
    }

    @Override // com.github.mikephil.charting.components.MarkerView, com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
        Integer numValueOf;
        int i = this.currentEnergyType;
        if (i == 0) {
            this.xValueTextView.setText("Day: " + Math.round(entry.getX()));
        } else if (i == 1) {
            this.xValueTextView.setText("Month: " + Math.round(entry.getX()));
        } else if (i == 2) {
            this.xValueTextView.setText("Year: " + Math.round(entry.getX()));
        } else {
            this.xValueTextView.setText("Date: " + Math.round(entry.getX()));
        }
        BarDataSet barDataSet = (BarDataSet) ((BarData) this.barChart.getData()).getDataSets().get(highlight.getDataSetIndex());
        int i2 = 0;
        while (true) {
            if (i2 >= barDataSet.getValues().size()) {
                numValueOf = null;
                break;
            } else {
                if (((Entry) barDataSet.getValues().get(i2)).getX() == entry.getX()) {
                    numValueOf = Integer.valueOf(i2);
                    break;
                }
                i2++;
            }
        }
        if (numValueOf != null) {
            for (int i3 = 0; i3 < ((BarData) this.barChart.getData()).getDataSets().size(); i3++) {
                Entry entry2 = (Entry) ((BarDataSet) ((IDataSet) ((BarData) this.barChart.getData()).getDataSets().get(i3))).getValues().get(numValueOf.intValue());
                if (i3 == 0) {
                    this.solarPvTextView.setText("" + entry2.getY());
                } else if (i3 == 1) {
                    this.batteryTextView.setText("" + entry2.getY());
                } else if (i3 == 2) {
                    this.gridTextView.setText("" + entry2.getY());
                } else if (i3 == 3) {
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