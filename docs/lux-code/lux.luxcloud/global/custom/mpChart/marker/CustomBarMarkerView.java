package com.lux.luxcloud.global.custom.mpChart.marker;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class CustomBarMarkerView extends MarkerView {
    private static final int ENERGY_TYPE_MONTH = 0;
    private static final int ENERGY_TYPE_TOTAL = 2;
    private static final int ENERGY_TYPE_YEAR = 1;
    private BarChart barChart;
    private TextView batteryChargeTextView;
    private TextView batteryTextView;
    private TextView consumptionTextView;
    private int currentEnergyType;
    private Map<BarDataSet, TextView> dataSetTextViewMap;
    private TextView exportToGridTextView;
    private TextView importToUserTextView;
    private TextView solarPvTextView;
    private TextView xValueTextView;

    public CustomBarMarkerView(Context context, BarChart barChart, int i, int i2) {
        super(context, i);
        this.currentEnergyType = i2;
        this.barChart = barChart;
        try {
            JSONObject energyChartColors = GlobalInfo.getInstance().getUserData().getChartColorValues().getEnergyChartColors();
            this.xValueTextView = (TextView) findViewById(R.id.xValueTextView);
            TextView textView = (TextView) findViewById(R.id.solarPvTextView);
            this.solarPvTextView = textView;
            textView.setTextColor(Color.parseColor(energyChartColors.getString("Solar Production")));
            TextView textView2 = (TextView) findViewById(R.id.batteryTextView);
            this.batteryTextView = textView2;
            textView2.setTextColor(Color.parseColor(energyChartColors.getString("Battery")));
            TextView textView3 = (TextView) findViewById(R.id.battChargeTextView);
            this.batteryChargeTextView = textView3;
            textView3.setTextColor(Color.parseColor(energyChartColors.getString("BattCharge")));
            TextView textView4 = (TextView) findViewById(R.id.exportToGridTextView);
            this.exportToGridTextView = textView4;
            textView4.setTextColor(Color.parseColor(energyChartColors.getString("Export to Grid")));
            TextView textView5 = (TextView) findViewById(R.id.importToUserTextView);
            this.importToUserTextView = textView5;
            textView5.setTextColor(Color.parseColor(energyChartColors.getString("Import to User")));
            TextView textView6 = (TextView) findViewById(R.id.consumptionTextView);
            this.consumptionTextView = textView6;
            textView6.setTextColor(Color.parseColor(energyChartColors.getString("Consumption")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void bindDataSets(Map<BarDataSet, TextView> map) {
        this.dataSetTextViewMap = map;
    }

    @Override // com.github.mikephil.charting.components.MarkerView, com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
        Integer numValueOf;
        Map<BarDataSet, TextView> map;
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
        if (numValueOf != null && (map = this.dataSetTextViewMap) != null) {
            for (Map.Entry<BarDataSet, TextView> entry2 : map.entrySet()) {
                BarDataSet key = entry2.getKey();
                TextView value = entry2.getValue();
                List<T> values = key.getValues();
                if (!values.isEmpty() && values.size() > numValueOf.intValue()) {
                    value.setText(String.valueOf(((BarEntry) values.get(numValueOf.intValue())).getY()));
                    value.setVisibility(0);
                } else {
                    value.setText("");
                    value.setVisibility(8);
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