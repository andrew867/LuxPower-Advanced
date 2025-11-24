package com.nfcx.eg4.view.main.fragment.lv1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.messaging.Constants;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.global.custom.mpChart.CustomLargeValueFormatter;
import com.nfcx.eg4.global.custom.mpChart.marker.CustomBarMarkerView;
import com.nfcx.eg4.global.custom.mpChart.marker.CustomLineMarkerView;
import com.nfcx.eg4.tool.API;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.InvTool;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Lv1DataFragment extends AbstractItemFragment {
    private static final int ENERGY_TYPE_MONTH = 0;
    private static final int ENERGY_TYPE_TOTAL = 2;
    private static final int ENERGY_TYPE_YEAR = 1;
    private LineDataSet batteryDataSet;
    private BarDataSet batteryDischargingBarDataSet;
    private BarDataSet consumptionBarDataSet;
    private LineDataSet consumptionDataSet;
    private boolean created;
    private int currentEnergyType;
    private BarChart energyBarChart;
    private ToggleButton energyChartMonthButton;
    private ConstraintLayout energyChartNextButton;
    private ConstraintLayout energyChartPreviousButton;
    private EditText energyChartTimeEditText;
    private RelativeLayout energyChartTimeLayout;
    private ToggleButton energyChartTotalButton;
    private ToggleButton energyChartYearButton;
    private BarDataSet export2GridBarDataSet;
    private Fragment fragment;
    private LineDataSet gridDataSet;
    private boolean inited;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private ConstraintLayout powerChartNextButton;
    private ConstraintLayout powerChartPreviousButton;
    private LineChart powerLineChart;
    private EditText powerLineChartTimeEditText;
    private BarDataSet solarProductionBarDataSet;
    private LineDataSet solarPvDataSet;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int waitRequestCount;

    static /* synthetic */ int access$1306(Lv1DataFragment lv1DataFragment) {
        int i = lv1DataFragment.waitRequestCount - 1;
        lv1DataFragment.waitRequestCount = i;
        return i;
    }

    public Lv1DataFragment() {
        super(2L);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        List<Inverter> invertersByPlant;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lv1_data, viewGroup, false);
        this.fragment = this;
        final UserData userData = GlobalInfo.getInstance().getUserData();
        if (!PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
            viewInflate.findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                MainActivity.instance.finish();
            }
        });
        ((ImageView) viewInflate.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
            }
        });
        this.inverterList = new ArrayList();
        if (userData.getCurrentPlant() != null && (invertersByPlant = userData.getInvertersByPlant(userData.getCurrentPlant().getPlantId())) != null) {
            Iterator<Inverter> it = invertersByPlant.iterator();
            while (it.hasNext()) {
                this.inverterList.add(it.next());
            }
        }
        if (userData.getCurrentPlant() != null && !userData.getCurrentPlant().getParallelGroups().isEmpty()) {
            Map<String, String> parallelGroups = userData.getCurrentPlant().getParallelGroups();
            int i = 0;
            for (String str : parallelGroups.keySet()) {
                Inverter inverter = new Inverter();
                inverter.setSerialNum(getString(R.string.phrase_parallel) + "-" + str);
                inverter.setPlantId(Long.valueOf(userData.getCurrentPlant().getPlantId()));
                inverter.setParallelGroup(str);
                inverter.setParallelFirstDeviceSn(parallelGroups.get(str));
                this.inverterList.add(i, inverter);
                i++;
            }
        }
        this.inverterSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_data_inverter_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        LineChart lineChart = (LineChart) viewInflate.findViewById(R.id.fragment_overview_powerChart);
        this.powerLineChart = lineChart;
        lineChart.getAxisRight().setEnabled(false);
        this.powerLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.powerLineChart.getXAxis().setGranularity(60.0f);
        this.powerLineChart.getXAxis().setValueFormatter(GlobalInfo.getInstance().getTimeAxisValueFormatter());
        this.powerLineChart.getAxisLeft().setGranularity(100.0f);
        this.powerLineChart.getAxisLeft().enableGridDashedLine(10.0f, 10.0f, 0.0f);
        this.powerLineChart.getAxisLeft().setValueFormatter(new CustomLargeValueFormatter());
        this.powerLineChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        this.powerLineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        Description description = new Description();
        description.setText("");
        this.powerLineChart.setDescription(description);
        this.powerLineChart.setPinchZoom(false);
        this.powerLineChart.setDoubleTapToZoomEnabled(false);
        this.powerLineChart.setTouchEnabled(true);
        this.powerLineChart.setMarker(new CustomLineMarkerView(getActivity(), this.powerLineChart, R.layout.custom_marker_view_layout));
        BarChart barChart = (BarChart) viewInflate.findViewById(R.id.fragment_overview_energyChart);
        this.energyBarChart = barChart;
        barChart.getAxisRight().setEnabled(false);
        this.energyBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.energyBarChart.getXAxis().setGranularity(1.0f);
        this.energyBarChart.getXAxis().setValueFormatter(GlobalInfo.getInstance().getNumberAxisValueFormatter());
        this.energyBarChart.getAxisLeft().enableGridDashedLine(10.0f, 10.0f, 0.0f);
        this.energyBarChart.getAxisLeft().setValueFormatter(new CustomLargeValueFormatter());
        this.energyBarChart.getAxisLeft().setGranularity(1.0f);
        this.energyBarChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        this.energyBarChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        Description description2 = new Description();
        description2.setEnabled(false);
        this.energyBarChart.setDescription(description2);
        this.energyBarChart.setPinchZoom(false);
        this.energyBarChart.setDoubleTapToZoomEnabled(false);
        this.energyBarChart.setTouchEnabled(true);
        this.energyBarChart.setMarker(new CustomBarMarkerView(getActivity(), this.energyBarChart, R.layout.custom_marker_view_layout, this.currentEnergyType));
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) viewInflate.findViewById(R.id.fragment_data_swipe_refresh_layout);
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m548xab74b59e();
            }
        });
        this.created = true;
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$0$com-nfcx-eg4-view-main-fragment-lv1-Lv1DataFragment, reason: not valid java name */
    /* synthetic */ void m548xab74b59e() {
        this.waitRequestCount = 2;
        reloadFragmentData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ConstraintLayout constraintLayout = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_powerChart_previousButtonLayout);
        this.powerChartPreviousButton = constraintLayout;
        constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changePowerLineChartTimeEditTextByButton(-1);
            }
        });
        ConstraintLayout constraintLayout2 = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_powerChart_nextButtonLayout);
        this.powerChartNextButton = constraintLayout2;
        constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changePowerLineChartTimeEditTextByButton(1);
            }
        });
        EditText editText = (EditText) getView().findViewById(R.id.fragment_overview_powerChart_timeEditText);
        this.powerLineChartTimeEditText = editText;
        editText.setText(InvTool.formatDate(new Date()));
        this.powerLineChartTimeEditText.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.getActivity().showDialog(0);
            }
        });
        this.powerLineChartTimeEditText.addTextChangedListener(new TextWatcher() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.6
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Lv1DataFragment.this.refreshPowerLineChart();
            }
        });
        ConstraintLayout constraintLayout3 = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_energyChart_previousButtonLayout);
        this.energyChartPreviousButton = constraintLayout3;
        constraintLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changeEnergyChartTimeEditTextByButton(-1);
            }
        });
        ConstraintLayout constraintLayout4 = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_energyChart_nextButtonLayout);
        this.energyChartNextButton = constraintLayout4;
        constraintLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changeEnergyChartTimeEditTextByButton(1);
            }
        });
        ToggleButton toggleButton = (ToggleButton) getView().findViewById(R.id.fragment_overview_energyChart_monthButton);
        this.energyChartMonthButton = toggleButton;
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z || Lv1DataFragment.this.currentEnergyType == 0) {
                    if (Lv1DataFragment.this.currentEnergyType == 0) {
                        compoundButton.setChecked(true);
                    }
                } else {
                    Lv1DataFragment.this.currentEnergyType = 0;
                    Lv1DataFragment.this.energyChartYearButton.setChecked(false);
                    Lv1DataFragment.this.energyChartTotalButton.setChecked(false);
                    Lv1DataFragment.this.initEnergyChartTimeEditText();
                    Lv1DataFragment.this.energyChartTimeLayout.setVisibility(0);
                }
            }
        });
        ToggleButton toggleButton2 = (ToggleButton) getView().findViewById(R.id.fragment_overview_energyChart_yearButton);
        this.energyChartYearButton = toggleButton2;
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z || Lv1DataFragment.this.currentEnergyType == 1) {
                    if (Lv1DataFragment.this.currentEnergyType == 1) {
                        compoundButton.setChecked(true);
                    }
                } else {
                    Lv1DataFragment.this.currentEnergyType = 1;
                    Lv1DataFragment.this.energyChartMonthButton.setChecked(false);
                    Lv1DataFragment.this.energyChartTotalButton.setChecked(false);
                    Lv1DataFragment.this.initEnergyChartTimeEditText();
                    Lv1DataFragment.this.energyChartTimeLayout.setVisibility(0);
                }
            }
        });
        ToggleButton toggleButton3 = (ToggleButton) getView().findViewById(R.id.fragment_overview_energyChart_totalButton);
        this.energyChartTotalButton = toggleButton3;
        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.11
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z || Lv1DataFragment.this.currentEnergyType == 2) {
                    if (Lv1DataFragment.this.currentEnergyType == 2) {
                        compoundButton.setChecked(true);
                    }
                } else {
                    Lv1DataFragment.this.currentEnergyType = 2;
                    Lv1DataFragment.this.energyChartMonthButton.setChecked(false);
                    Lv1DataFragment.this.energyChartYearButton.setChecked(false);
                    Lv1DataFragment.this.energyChartTimeLayout.setVisibility(4);
                    Lv1DataFragment.this.refreshEnergyBarChart();
                    Lv1DataFragment.this.initEnergyChartTimeEditText();
                }
            }
        });
        EditText editText2 = (EditText) getView().findViewById(R.id.fragment_overview_energyChart_timeEditText);
        this.energyChartTimeEditText = editText2;
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1DataFragment.this.currentEnergyType != 0) {
                    if (Lv1DataFragment.this.currentEnergyType == 1) {
                        Lv1DataFragment.this.getActivity().showDialog(2);
                        return;
                    }
                    return;
                }
                Lv1DataFragment.this.getActivity().showDialog(1);
            }
        });
        this.energyChartTimeEditText.addTextChangedListener(new TextWatcher() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.13
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Lv1DataFragment.this.refreshEnergyBarChart();
            }
        });
        this.currentEnergyType = 0;
        initEnergyChartTimeEditText();
        this.energyChartTimeLayout = (RelativeLayout) getView().findViewById(R.id.fragment_overview_energyChart_timeLayout);
    }

    private void initFirstTimeVisible() {
        System.out.println("Eg4 - DataFragment initFirstTimeVisible...");
        this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.14
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Lv1DataFragment.this.updateSelectInverter((Inverter) Lv1DataFragment.this.inverterSpinner.getSelectedItem());
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (Lv1DataFragment.this.inverter != null) {
                    UserData userData = GlobalInfo.getInstance().getUserData();
                    if (Lv1DataFragment.this.inverter.isParallelGroup()) {
                        userData.setCurrentParallelGroup(null);
                    } else {
                        userData.setCurrentInverter(null, true);
                    }
                    Lv1DataFragment.this.inverter = null;
                    Lv1DataFragment.this.reloadFragmentData();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectInverter(Inverter inverter) {
        System.out.println("Eg4 - DataFragment selectInverter = " + inverter.getSerialNum() + ", inverter = " + this.inverter);
        Inverter inverter2 = this.inverter;
        if (inverter2 == null || !inverter2.getSerialNum().equals(inverter.getSerialNum())) {
            this.inverter = inverter;
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (this.inverter.isParallelGroup()) {
                userData.setCurrentParallelGroup(this.inverter);
            } else {
                userData.setCurrentInverter(this.inverter, true);
            }
            reloadFragmentData();
            MainActivity mainActivity = (MainActivity) this.fragment.getActivity();
            if (mainActivity != null) {
                mainActivity.switchRemoteSetFragment(this.inverter.getDeviceTypeValue());
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("Eg4 - DataFragment onResume...");
        if (!this.inited) {
            this.inited = true;
            initFirstTimeVisible();
        }
        refreshFragmentParams();
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        if (this.created) {
            System.out.println("Eg4 - DATA refreshFragmentParams...");
            UserData userData = GlobalInfo.getInstance().getUserData();
            Inverter currentParallelGroup = userData.getCurrentParallelGroup();
            if (currentParallelGroup == null) {
                currentParallelGroup = userData.getCurrentInverter();
            }
            if (currentParallelGroup == null || this.inverterList == null) {
                return;
            }
            for (int i = 0; i < this.inverterList.size(); i++) {
                Inverter inverter2 = this.inverterList.get(i);
                if (inverter2.getSerialNum().equals(currentParallelGroup.getSerialNum()) && ((inverter = this.inverter) == null || !inverter.getSerialNum().equals(currentParallelGroup.getSerialNum()))) {
                    System.out.println("Eg4 - inverterSpinner.setSelection(" + i + ")");
                    if (this.inverterSpinner.getSelectedItemPosition() != i) {
                        this.inverterSpinner.setSelection(i);
                    } else {
                        updateSelectInverter(inverter2);
                    }
                }
            }
        }
    }

    public synchronized void reloadFragmentData() {
        if (this.created) {
            refreshPowerLineChart();
            refreshEnergyBarChart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePowerLineChartTimeEditTextByButton(int i) {
        Calendar calendar = Calendar.getInstance();
        String string = this.powerLineChartTimeEditText.getText().toString();
        calendar.set(1, Integer.parseInt(string.substring(0, 4)));
        calendar.set(2, Integer.parseInt(string.substring(5, 7)) - 1);
        calendar.set(5, Integer.parseInt(string.substring(8, 10)));
        calendar.add(5, i);
        this.powerLineChartTimeEditText.setText(InvTool.formatDate(calendar.getTime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPowerLineChart() {
        LineChart lineChart = this.powerLineChart;
        if (lineChart != null) {
            if (this.inverter != null) {
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m550x9e77df45();
                    }
                }).start();
                return;
            }
            lineChart.clear();
            this.powerLineChart.animateX(2000, Easing.EasingOption.EaseInCubic);
            this.powerLineChart.invalidate();
        }
    }

    /* renamed from: lambda$refreshPowerLineChart$1$com-nfcx-eg4-view-main-fragment-lv1-Lv1DataFragment, reason: not valid java name */
    /* synthetic */ void m550x9e77df45() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
        map.put("dateText", this.powerLineChartTimeEditText.getText().toString());
        System.out.println("Eg4 - Query serialNum = " + ((String) map.get("serialNum")));
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/analyze/chart/dayMultiLine" + (this.inverter.isParallelGroup() ? "Parallel" : ""), map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.15
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    try {
                        if (Lv1DataFragment.this.waitRequestCount > 0 && Lv1DataFragment.access$1306(Lv1DataFragment.this) == 0) {
                            Lv1DataFragment.this.swipeRefreshLayout.setRefreshing(false);
                        }
                        JSONObject jSONObject = jSONObjectPostJson;
                        if (jSONObject == null || !jSONObject.getBoolean("success")) {
                            Lv1DataFragment.this.toastByResult(activity, jSONObjectPostJson);
                            return;
                        }
                        JSONArray jSONArray = jSONObjectPostJson.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                        if (jSONArray.length() <= 0) {
                            Lv1DataFragment.this.powerLineChart.clear();
                        } else {
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            ArrayList arrayList3 = new ArrayList();
                            ArrayList arrayList4 = new ArrayList();
                            for (int i = 0; i < jSONArray.length(); i++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                float f = (jSONObject2.getInt("hour") * 3600) + (jSONObject2.getInt("minute") * 60) + jSONObject2.getInt("second");
                                arrayList.add(new Entry(f, jSONObject2.getInt("solarPv")));
                                arrayList2.add(new Entry(f, jSONObject2.getInt("gridPower")));
                                arrayList3.add(new Entry(f, jSONObject2.getInt("batteryDischarging")));
                                arrayList4.add(new Entry(f, jSONObject2.getInt("consumption")));
                            }
                            if (Lv1DataFragment.this.solarPvDataSet != null) {
                                Lv1DataFragment.this.solarPvDataSet.setValues(arrayList);
                            } else {
                                Lv1DataFragment lv1DataFragment = Lv1DataFragment.this;
                                lv1DataFragment.solarPvDataSet = lv1DataFragment.createLineDataSet(arrayList, lv1DataFragment.getString(R.string.main_overview_chart_solar_pv), Color.rgb(112, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 70));
                            }
                            if (Lv1DataFragment.this.batteryDataSet != null) {
                                Lv1DataFragment.this.batteryDataSet.setValues(arrayList3);
                            } else {
                                Lv1DataFragment lv1DataFragment2 = Lv1DataFragment.this;
                                lv1DataFragment2.batteryDataSet = lv1DataFragment2.createLineDataSet(arrayList3, lv1DataFragment2.getString(R.string.main_overview_chart_battery_discharging), Color.rgb(90, 155, 213));
                            }
                            if (Lv1DataFragment.this.gridDataSet != null) {
                                Lv1DataFragment.this.gridDataSet.setValues(arrayList2);
                            } else {
                                Lv1DataFragment lv1DataFragment3 = Lv1DataFragment.this;
                                lv1DataFragment3.gridDataSet = lv1DataFragment3.createLineDataSet(arrayList2, lv1DataFragment3.getString(R.string.main_overview_chart_grid_power), Color.rgb(246, 104, 103));
                            }
                            if (Lv1DataFragment.this.consumptionDataSet != null) {
                                Lv1DataFragment.this.consumptionDataSet.setValues(arrayList4);
                            } else {
                                Lv1DataFragment lv1DataFragment4 = Lv1DataFragment.this;
                                lv1DataFragment4.consumptionDataSet = lv1DataFragment4.createLineDataSet(arrayList4, lv1DataFragment4.getString(R.string.main_overview_chart_consumption), Color.rgb(255, 164, 97));
                            }
                            ArrayList arrayList5 = new ArrayList();
                            arrayList5.add(Lv1DataFragment.this.solarPvDataSet);
                            arrayList5.add(Lv1DataFragment.this.batteryDataSet);
                            arrayList5.add(Lv1DataFragment.this.gridDataSet);
                            arrayList5.add(Lv1DataFragment.this.consumptionDataSet);
                            Lv1DataFragment.this.powerLineChart.setData(new LineData(arrayList5));
                        }
                        Lv1DataFragment.this.powerLineChart.animateX(2000, Easing.EasingOption.EaseInCubic);
                        Lv1DataFragment.this.powerLineChart.invalidate();
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplicationContext(), R.string.phrase_toast_response_error, 1).show();
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LineDataSet createLineDataSet(List<Entry> list, String str, int i) {
        LineDataSet lineDataSet = new LineDataSet(list, str);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(i);
        lineDataSet.setCircleColor(i);
        lineDataSet.setLineWidth(1.0f);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCubicIntensity(0.1f);
        lineDataSet.setCircleRadius(1.0f);
        return lineDataSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEnergyChartTimeEditText() {
        int i = this.currentEnergyType;
        if (i == 0) {
            this.energyChartTimeEditText.setText(InvTool.formatDate(new Date()).substring(0, 7));
        } else if (i == 1) {
            this.energyChartTimeEditText.setText(InvTool.formatDate(new Date()).substring(0, 4));
        }
        this.energyBarChart.clear();
        this.energyBarChart.setMarker(new CustomBarMarkerView(getActivity(), this.energyBarChart, R.layout.custom_marker_view_layout, this.currentEnergyType));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEnergyChartTimeEditTextByButton(int i) {
        Calendar calendar = Calendar.getInstance();
        String string = this.energyChartTimeEditText.getText().toString();
        calendar.set(1, Integer.parseInt(string.substring(0, 4)));
        int i2 = this.currentEnergyType;
        if (i2 == 0) {
            calendar.set(2, Integer.parseInt(string.substring(5, 7)) - 1);
            calendar.add(2, i);
            this.energyChartTimeEditText.setText(InvTool.formatDate(calendar.getTime()).substring(0, 7));
        } else if (i2 == 1) {
            calendar.add(1, i);
            this.energyChartTimeEditText.setText(InvTool.formatDate(calendar.getTime()).substring(0, 4));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshEnergyBarChart() {
        BarChart barChart = this.energyBarChart;
        if (barChart != null) {
            if (this.inverter != null) {
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m549xb55d03b4();
                    }
                }).start();
                return;
            }
            barChart.clear();
            this.energyBarChart.animateX(2000, Easing.EasingOption.EaseInCubic);
            this.energyBarChart.invalidate();
        }
    }

    /* renamed from: lambda$refreshEnergyBarChart$2$com-nfcx-eg4-view-main-fragment-lv1-Lv1DataFragment, reason: not valid java name */
    /* synthetic */ void m549xb55d03b4() {
        String strConcat;
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
        int i = this.currentEnergyType;
        if (i == 0 || i == 1) {
            map.put("year", this.energyChartTimeEditText.getText().toString().substring(0, 4));
        }
        if (this.currentEnergyType == 0) {
            map.put("month", this.energyChartTimeEditText.getText().toString().substring(5, 7));
        }
        int i2 = this.currentEnergyType;
        if (i2 == 0) {
            strConcat = "monthColumn".concat(this.inverter.isParallelGroup() ? "Parallel" : "");
        } else if (i2 == 1) {
            strConcat = "yearColumn".concat(this.inverter.isParallelGroup() ? "Parallel" : "");
        } else if (i2 == 2) {
            strConcat = "totalColumn".concat(this.inverter.isParallelGroup() ? "Parallel" : "");
        } else {
            strConcat = null;
        }
        if (Tool.isEmpty(strConcat)) {
            return;
        }
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverterChart/" + strConcat, map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1DataFragment.16
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    try {
                        if (Lv1DataFragment.this.waitRequestCount > 0 && Lv1DataFragment.access$1306(Lv1DataFragment.this) == 0) {
                            Lv1DataFragment.this.swipeRefreshLayout.setRefreshing(false);
                        }
                        JSONObject jSONObject = jSONObjectPostJson;
                        if (jSONObject == null || !jSONObject.getBoolean("success")) {
                            Lv1DataFragment.this.toastByResult(activity, jSONObjectPostJson);
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        ArrayList arrayList4 = new ArrayList();
                        JSONArray jSONArray = jSONObjectPostJson.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                        if (jSONArray.length() <= 0) {
                            Lv1DataFragment.this.energyBarChart.clear();
                        } else {
                            int i3 = 0;
                            for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                                int i5 = Lv1DataFragment.this.currentEnergyType == 2 ? jSONArray.getJSONObject(i4).getInt("year") : i4 + 1;
                                if (i3 <= 0) {
                                    i3 = i5;
                                }
                                float f = 0.0f;
                                if (Lv1DataFragment.this.inverter.isParallelGroup()) {
                                    float f2 = i5;
                                    arrayList.add(new BarEntry(f2, r10.getInt("ePvDay") / 10.0f));
                                    arrayList2.add(new BarEntry(f2, r10.getInt("eDisChgDay") / 10.0f));
                                    arrayList3.add(new BarEntry(f2, r10.getInt("eExportDay") / 10.0f));
                                    float f3 = r10.getInt("eConsumptionDay") / 10.0f;
                                    if (f3 >= 0.0f) {
                                        f = f3;
                                    }
                                    arrayList4.add(new BarEntry(f2, f));
                                } else {
                                    float f4 = ((r10.getInt("ePv1Day") + r10.getInt("ePv2Day")) + r10.getInt("ePv3Day")) / 10.0f;
                                    float f5 = r10.getInt("eConsumptionDay") / 10.0f;
                                    if (f5 >= 0.0f) {
                                        f = f5;
                                    }
                                    float f6 = i5;
                                    arrayList.add(new BarEntry(f6, f4));
                                    arrayList2.add(new BarEntry(f6, r10.getInt("eDisChgDay") / 10.0f));
                                    arrayList3.add(new BarEntry(f6, r10.getInt("eToGridDay") / 10.0f));
                                    arrayList4.add(new BarEntry(f6, f));
                                }
                            }
                            if (Lv1DataFragment.this.solarProductionBarDataSet != null) {
                                Lv1DataFragment.this.solarProductionBarDataSet.setValues(arrayList);
                            } else {
                                Lv1DataFragment lv1DataFragment = Lv1DataFragment.this;
                                lv1DataFragment.solarProductionBarDataSet = lv1DataFragment.createBarDataSet(arrayList, lv1DataFragment.getString(R.string.main_overview_chart_solar_production), Color.rgb(112, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 70));
                                Lv1DataFragment.this.solarProductionBarDataSet.setValueFormatter(GlobalInfo.getInstance().getNoZeroValueFormatter());
                            }
                            if (Lv1DataFragment.this.batteryDischargingBarDataSet != null) {
                                Lv1DataFragment.this.batteryDischargingBarDataSet.setValues(arrayList2);
                            } else {
                                Lv1DataFragment lv1DataFragment2 = Lv1DataFragment.this;
                                lv1DataFragment2.batteryDischargingBarDataSet = lv1DataFragment2.createBarDataSet(arrayList2, lv1DataFragment2.getString(R.string.main_overview_chart_battery_discharging), Color.rgb(90, 155, 213));
                                Lv1DataFragment.this.batteryDischargingBarDataSet.setValueFormatter(GlobalInfo.getInstance().getNoZeroValueFormatter());
                            }
                            if (Lv1DataFragment.this.export2GridBarDataSet != null) {
                                Lv1DataFragment.this.export2GridBarDataSet.setValues(arrayList3);
                            } else {
                                Lv1DataFragment lv1DataFragment3 = Lv1DataFragment.this;
                                lv1DataFragment3.export2GridBarDataSet = lv1DataFragment3.createBarDataSet(arrayList3, lv1DataFragment3.getString(R.string.main_overview_chart_export_2_grid), Color.rgb(246, 104, 103));
                                Lv1DataFragment.this.export2GridBarDataSet.setValueFormatter(GlobalInfo.getInstance().getNoZeroValueFormatter());
                            }
                            if (Lv1DataFragment.this.consumptionBarDataSet != null) {
                                Lv1DataFragment.this.consumptionBarDataSet.setValues(arrayList4);
                            } else {
                                Lv1DataFragment lv1DataFragment4 = Lv1DataFragment.this;
                                lv1DataFragment4.consumptionBarDataSet = lv1DataFragment4.createBarDataSet(arrayList4, lv1DataFragment4.getString(R.string.main_overview_chart_consumption), Color.rgb(255, 164, 97));
                                Lv1DataFragment.this.consumptionBarDataSet.setValueFormatter(GlobalInfo.getInstance().getNoZeroValueFormatter());
                            }
                            BarData barData = new BarData(Lv1DataFragment.this.solarProductionBarDataSet, Lv1DataFragment.this.batteryDischargingBarDataSet, Lv1DataFragment.this.export2GridBarDataSet, Lv1DataFragment.this.consumptionBarDataSet);
                            Lv1DataFragment.this.energyBarChart.setData(barData);
                            barData.setBarWidth(0.2f);
                            float f7 = i3 - 0.5f;
                            Lv1DataFragment.this.energyBarChart.getXAxis().setAxisMinimum(f7);
                            Lv1DataFragment.this.energyBarChart.groupBars(f7, 0.08f, 0.03f);
                        }
                        Lv1DataFragment.this.energyBarChart.animateX(2000, Easing.EasingOption.EaseInCubic);
                        Lv1DataFragment.this.energyBarChart.invalidate();
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplicationContext(), R.string.phrase_toast_response_error, 1).show();
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BarDataSet createBarDataSet(List<BarEntry> list, String str, int i) {
        BarDataSet barDataSet = new BarDataSet(list, str);
        barDataSet.setColor(i);
        return barDataSet;
    }

    public EditText getPowerLineChartTimeEditText() {
        return this.powerLineChartTimeEditText;
    }

    public EditText getEnergyChartTimeEditText() {
        return this.energyChartTimeEditText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toastByResult(FragmentActivity fragmentActivity, JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            Toast.makeText(fragmentActivity.getApplicationContext(), R.string.phrase_toast_network_error, 1).show();
            return;
        }
        if (jSONObject.getInt(API.MSG_CODE) == 150) {
            Toast.makeText(fragmentActivity.getApplicationContext(), R.string.plant_toast_not_find_device, 1).show();
            return;
        }
        if (jSONObject.getInt(API.MSG_CODE) == 151) {
            Toast.makeText(fragmentActivity.getApplicationContext(), R.string.plant_toast_device_user_dismatch, 1).show();
            return;
        }
        if (jSONObject.getInt(API.MSG_CODE) == 102) {
            Toast.makeText(fragmentActivity.getApplicationContext(), R.string.phrase_toast_unlogin_error, 1).show();
        } else if (jSONObject.getInt(API.MSG_CODE) == 103) {
            Toast.makeText(fragmentActivity.getApplicationContext(), String.format(getString(R.string.phrase_toast_param_error), jSONObject.getString("errorParamIndex")), 1).show();
        }
    }
}