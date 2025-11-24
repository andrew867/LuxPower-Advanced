package com.lux.luxcloud.global.custom.mpChart.marker;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.tool.InvTool;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class CustomLineMarkerView extends MarkerView {
    private TextView batteryTextView;
    private TextView consumptionTextView;
    private Map<LineDataSet, TextView> dataSetTextViewMap;
    private TextView gridTextView;
    private LineChart lineChart;
    private TextView socTextView;
    private TextView solarPvTextView;
    private TextView xValueTextView;

    public CustomLineMarkerView(Context context, LineChart lineChart, int i) {
        super(context, i);
        this.lineChart = lineChart;
        try {
            JSONObject chartColors = GlobalInfo.getInstance().getUserData().getChartColorValues().getChartColors();
            this.xValueTextView = (TextView) findViewById(R.id.xValueTextView);
            TextView textView = (TextView) findViewById(R.id.solarPvTextView);
            this.solarPvTextView = textView;
            textView.setTextColor(Color.parseColor(chartColors.getString("Solar PV")));
            TextView textView2 = (TextView) findViewById(R.id.batteryTextView);
            this.batteryTextView = textView2;
            textView2.setTextColor(Color.parseColor(chartColors.getString("Battery")));
            TextView textView3 = (TextView) findViewById(R.id.gridTextView);
            this.gridTextView = textView3;
            textView3.setTextColor(Color.parseColor(chartColors.getString("Grid")));
            TextView textView4 = (TextView) findViewById(R.id.consumptionTextView);
            this.consumptionTextView = textView4;
            textView4.setTextColor(Color.parseColor(chartColors.getString("Consumption")));
            TextView textView5 = (TextView) findViewById(R.id.socTextView);
            this.socTextView = textView5;
            textView5.setTextColor(Color.parseColor(chartColors.getString("SOC")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void bindDataSets(Map<LineDataSet, TextView> map) {
        this.dataSetTextViewMap = map;
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
        if (numValueOf != null && this.dataSetTextViewMap != null) {
            System.out.println("dataSetTextViewMap.size() == " + this.dataSetTextViewMap.size());
            for (Map.Entry<LineDataSet, TextView> entry2 : this.dataSetTextViewMap.entrySet()) {
                LineDataSet key = entry2.getKey();
                TextView value = entry2.getValue();
                List<T> values = key.getValues();
                if (!values.isEmpty() && values.size() > numValueOf.intValue()) {
                    value.setText(String.valueOf(((Entry) values.get(numValueOf.intValue())).getY()));
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