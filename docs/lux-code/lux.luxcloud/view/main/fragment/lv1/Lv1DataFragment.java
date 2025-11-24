package com.lux.luxcloud.view.main.fragment.lv1;

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
import android.widget.TextView;
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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.messaging.Constants;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.global.custom.mpChart.CustomLargeValueFormatter;
import com.lux.luxcloud.global.custom.mpChart.marker.CustomBarMarkerView;
import com.lux.luxcloud.global.custom.mpChart.marker.CustomLineMarkerView;
import com.lux.luxcloud.tool.API;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.InvTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.userCenter.NewUserCenterActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Lv1DataFragment extends AbstractItemFragment {
    private static final int ENERGY_TYPE_MONTH = 0;
    private static final int ENERGY_TYPE_TOTAL = 2;
    private static final int ENERGY_TYPE_YEAR = 1;
    private BarDataSet batteryChargingBarDataSet;
    private LineDataSet batteryDataSet;
    private BarDataSet batteryDischargingBarDataSet;
    private BarDataSet consumptionBarDataSet;
    private LineDataSet consumptionDataSet;
    private boolean created;
    private int currentEnergyType;
    private CustomBarMarkerView customBarMarkerView;
    CustomLineMarkerView customLineMarkerView;
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
    private BarDataSet importOffgridBarDataSet;
    private boolean inited;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private ConstraintLayout powerChartNextButton;
    private ConstraintLayout powerChartPreviousButton;
    private LineChart powerLineChart;
    private EditText powerLineChartTimeEditText;
    private LineDataSet socDataSet;
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
        if (!userData.needShowCompanyLogo()) {
            viewInflate.findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                MainActivity.instance.finish();
            }
        });
        ((ImageView) viewInflate.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.2
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
                Inverter inverter2 = userData.getInverter(inverter.getParallelFirstDeviceSn());
                if (inverter2 != null) {
                    inverter.setDeviceType(Integer.valueOf(inverter2.getDeviceTypeValue()));
                    inverter.setSubDeviceType(Integer.valueOf(inverter2.getSubDeviceTypeValue()));
                    inverter.setDtc(inverter2.getDtcValue());
                    inverter.setPhase(inverter2.getPhaseValue());
                }
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
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.powerLineChart.getXAxis().setGranularity(60.0f);
        this.powerLineChart.getXAxis().setValueFormatter(GlobalInfo.getInstance().getTimeAxisValueFormatter());
        this.powerLineChart.getAxisLeft().setGranularity(100.0f);
        this.powerLineChart.getAxisLeft().enableGridDashedLine(10.0f, 10.0f, 0.0f);
        this.powerLineChart.getAxisLeft().setValueFormatter(new CustomLargeValueFormatter());
        this.powerLineChart.getAxisRight().setAxisMinimum(0.0f);
        this.powerLineChart.getAxisRight().setAxisMaximum(110.0f);
        this.powerLineChart.getAxisRight().setGranularity(20.0f);
        this.powerLineChart.getAxisRight().setValueFormatter(GlobalInfo.getInstance().getNumberAxisSOCValueFormatter());
        this.powerLineChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        this.powerLineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        Description description = new Description();
        description.setText("");
        this.powerLineChart.setDescription(description);
        this.powerLineChart.setPinchZoom(false);
        this.powerLineChart.setDoubleTapToZoomEnabled(false);
        this.powerLineChart.setTouchEnabled(true);
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
        this.energyBarChart.setMarker(new CustomBarMarkerView(getActivity(), this.energyBarChart, R.layout.custom_bar_marker_view_layout, this.currentEnergyType));
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) viewInflate.findViewById(R.id.fragment_data_swipe_refresh_layout);
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment$$ExternalSyntheticLambda2
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m622xafe8078c();
            }
        });
        this.created = true;
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1DataFragment, reason: not valid java name */
    /* synthetic */ void m622xafe8078c() {
        this.waitRequestCount = 2;
        reloadFragmentData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ConstraintLayout constraintLayout = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_powerChart_previousButtonLayout);
        this.powerChartPreviousButton = constraintLayout;
        constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changePowerLineChartTimeEditTextByButton(-1);
            }
        });
        ConstraintLayout constraintLayout2 = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_powerChart_nextButtonLayout);
        this.powerChartNextButton = constraintLayout2;
        constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changePowerLineChartTimeEditTextByButton(1);
            }
        });
        EditText editText = (EditText) getView().findViewById(R.id.fragment_overview_powerChart_timeEditText);
        this.powerLineChartTimeEditText = editText;
        editText.setText(InvTool.formatDate(new Date()));
        this.powerLineChartTimeEditText.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.getActivity().showDialog(0);
            }
        });
        this.powerLineChartTimeEditText.addTextChangedListener(new TextWatcher() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.6
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
        constraintLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changeEnergyChartTimeEditTextByButton(-1);
            }
        });
        ConstraintLayout constraintLayout4 = (ConstraintLayout) getView().findViewById(R.id.fragment_overview_energyChart_nextButtonLayout);
        this.energyChartNextButton = constraintLayout4;
        constraintLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1DataFragment.this.changeEnergyChartTimeEditTextByButton(1);
            }
        });
        ToggleButton toggleButton = (ToggleButton) getView().findViewById(R.id.fragment_overview_energyChart_monthButton);
        this.energyChartMonthButton = toggleButton;
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.9
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
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.10
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
        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.11
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
                    Lv1DataFragment.this.initEnergyChartTimeEditText();
                    Lv1DataFragment.this.refreshEnergyBarChart();
                }
            }
        });
        EditText editText2 = (EditText) getView().findViewById(R.id.fragment_overview_energyChart_timeEditText);
        this.energyChartTimeEditText = editText2;
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.12
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
        this.energyChartTimeEditText.addTextChangedListener(new TextWatcher() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.13
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
        System.out.println("LuxPower - DataFragment initFirstTimeVisible...");
        this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.14
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
        System.out.println("LuxPower - DataFragment selectInverter = " + inverter.getSerialNum() + ", inverter = " + this.inverter);
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
        System.out.println("LuxPower - DataFragment onResume...");
        if (!this.inited) {
            this.inited = true;
            initFirstTimeVisible();
        }
        CustomLineMarkerView customLineMarkerView = new CustomLineMarkerView(getActivity(), this.powerLineChart, R.layout.custom_line_marker_view_layout);
        this.customLineMarkerView = customLineMarkerView;
        this.powerLineChart.setMarker(customLineMarkerView);
        refreshPowerLineChart();
        refreshEnergyBarChart();
        initEnergyChartTimeEditText();
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        if (this.created) {
            System.out.println("LuxPower - DATA refreshFragmentParams...");
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
                    System.out.println("LuxPower - inverterSpinner.setSelection(" + i + ")");
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
            lineChart.clear();
            if (this.inverter != null) {
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m624xb46005b3();
                    }
                }).start();
                return;
            }
            this.powerLineChart.clear();
            this.powerLineChart.animateX(2000, Easing.EasingOption.EaseInCubic);
            this.powerLineChart.invalidate();
        }
    }

    /* renamed from: lambda$refreshPowerLineChart$1$com-lux-luxcloud-view-main-fragment-lv1-Lv1DataFragment, reason: not valid java name */
    /* synthetic */ void m624xb46005b3() {
        final UserData userData = GlobalInfo.getInstance().getUserData();
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
        map.put("dateText", this.powerLineChartTimeEditText.getText().toString());
        System.out.println("LuxPower - Query serialNum = " + ((String) map.get("serialNum")));
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/analyze/chart/dayMultiLine" + (this.inverter.isParallelGroup() ? "Parallel" : ""), map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.15
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    boolean z;
                    boolean z2;
                    boolean z3;
                    boolean z4;
                    try {
                        if (Lv1DataFragment.this.waitRequestCount > 0 && Lv1DataFragment.access$1306(Lv1DataFragment.this) == 0) {
                            Lv1DataFragment.this.swipeRefreshLayout.setRefreshing(false);
                        }
                        JSONObject jSONObject = jSONObjectPostJson;
                        if (jSONObject == null || !jSONObject.getBoolean("success")) {
                            Lv1DataFragment.this.toastByResult(activity, jSONObjectPostJson);
                            return;
                        }
                        JSONObject chartColors = userData.getChartColorValues().getChartColors();
                        JSONArray jSONArray = jSONObjectPostJson.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                        if (jSONArray.length() <= 0) {
                            Lv1DataFragment.this.powerLineChart.clear();
                        } else {
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            ArrayList arrayList3 = new ArrayList();
                            ArrayList arrayList4 = new ArrayList();
                            ArrayList arrayList5 = new ArrayList();
                            for (int i = 0; i < jSONArray.length(); i++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                float f = (jSONObject2.getInt("hour") * 3600) + (jSONObject2.getInt("minute") * 60) + jSONObject2.getInt("second");
                                arrayList.add(new Entry(f, jSONObject2.getInt("solarPv")));
                                arrayList2.add(new Entry(f, jSONObject2.getInt("gridPower")));
                                arrayList3.add(new Entry(f, jSONObject2.getInt("batteryDischarging")));
                                arrayList4.add(new Entry(f, jSONObject2.getInt("consumption")));
                                if (jSONObject2.has("soc")) {
                                    arrayList5.add(new Entry(f, jSONObject2.getInt("soc")));
                                }
                            }
                            LinkedHashMap linkedHashMap = new LinkedHashMap();
                            if (Lv1DataFragment.this.solarPvDataSet != null) {
                                Lv1DataFragment.this.solarPvDataSet.setValues(arrayList);
                            } else {
                                Lv1DataFragment lv1DataFragment = Lv1DataFragment.this;
                                lv1DataFragment.solarPvDataSet = lv1DataFragment.createLineDataSet(arrayList, lv1DataFragment.getString(R.string.main_overview_chart_solar_pv), Color.parseColor(chartColors.getString("Solar PV")));
                            }
                            if (Lv1DataFragment.this.batteryDataSet != null) {
                                Lv1DataFragment.this.batteryDataSet.setValues(arrayList3);
                            } else {
                                Lv1DataFragment lv1DataFragment2 = Lv1DataFragment.this;
                                lv1DataFragment2.batteryDataSet = lv1DataFragment2.createLineDataSet(arrayList3, lv1DataFragment2.getString(R.string.main_overview_chart_battery_discharging), Color.parseColor(chartColors.getString("Battery")));
                            }
                            if (Lv1DataFragment.this.gridDataSet != null) {
                                Lv1DataFragment.this.gridDataSet.setValues(arrayList2);
                            } else {
                                Lv1DataFragment lv1DataFragment3 = Lv1DataFragment.this;
                                lv1DataFragment3.gridDataSet = lv1DataFragment3.createLineDataSet(arrayList2, lv1DataFragment3.getString(R.string.main_overview_chart_grid_power), Color.parseColor(chartColors.getString("Grid")));
                            }
                            if (Lv1DataFragment.this.consumptionDataSet != null) {
                                Lv1DataFragment.this.consumptionDataSet.setValues(arrayList4);
                            } else {
                                Lv1DataFragment lv1DataFragment4 = Lv1DataFragment.this;
                                lv1DataFragment4.consumptionDataSet = lv1DataFragment4.createLineDataSet(arrayList4, lv1DataFragment4.getString(R.string.main_overview_chart_consumption), Color.parseColor(chartColors.getString("Consumption")));
                            }
                            if (Lv1DataFragment.this.socDataSet != null) {
                                Lv1DataFragment.this.socDataSet.setValues(arrayList5);
                            } else {
                                Lv1DataFragment lv1DataFragment5 = Lv1DataFragment.this;
                                lv1DataFragment5.socDataSet = lv1DataFragment5.createLineDataSet(arrayList5, "SOC", Color.parseColor(chartColors.getString("SOC")));
                                Lv1DataFragment.this.socDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                            }
                            ArrayList arrayList6 = new ArrayList(0);
                            String userChartRecord = userData.getUserChartRecord();
                            if (Tool.isEmpty(userChartRecord)) {
                                arrayList6.add(Lv1DataFragment.this.solarPvDataSet);
                                arrayList6.add(Lv1DataFragment.this.batteryDataSet);
                                arrayList6.add(Lv1DataFragment.this.gridDataSet);
                                arrayList6.add(Lv1DataFragment.this.consumptionDataSet);
                                arrayList6.add(Lv1DataFragment.this.socDataSet);
                            } else {
                                JSONObject jSONObject3 = new JSONObject(userChartRecord);
                                String str = Lv1DataFragment.this.inverter.getDeviceTypeValue() + "_" + Lv1DataFragment.this.inverter.getSubDeviceTypeValue() + "_" + Lv1DataFragment.this.inverter.getPhaseValue() + "_" + Lv1DataFragment.this.inverter.getDtcValue();
                                if (!jSONObject3.has(str)) {
                                    arrayList6.add(Lv1DataFragment.this.solarPvDataSet);
                                    arrayList6.add(Lv1DataFragment.this.batteryDataSet);
                                    arrayList6.add(Lv1DataFragment.this.gridDataSet);
                                    arrayList6.add(Lv1DataFragment.this.consumptionDataSet);
                                    arrayList6.add(Lv1DataFragment.this.socDataSet);
                                } else {
                                    JSONObject jSONObject4 = jSONObject3.getJSONObject(str);
                                    z2 = jSONObject4.getBoolean("lineSolarPV");
                                    z3 = jSONObject4.getBoolean("lineBattery");
                                    z4 = jSONObject4.getBoolean("lineGrid");
                                    z = jSONObject4.getBoolean("lineSOC");
                                    if (z2) {
                                        arrayList6.add(Lv1DataFragment.this.solarPvDataSet);
                                    }
                                    if (z3) {
                                        arrayList6.add(Lv1DataFragment.this.batteryDataSet);
                                    }
                                    if (z4) {
                                        arrayList6.add(Lv1DataFragment.this.gridDataSet);
                                    }
                                    arrayList6.add(Lv1DataFragment.this.consumptionDataSet);
                                    if (z) {
                                        arrayList6.add(Lv1DataFragment.this.socDataSet);
                                    }
                                    if (z2 && Lv1DataFragment.this.solarPvDataSet != null) {
                                        linkedHashMap.put(Lv1DataFragment.this.solarPvDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.solarPvTextView));
                                    }
                                    if (z3 && Lv1DataFragment.this.batteryDataSet != null) {
                                        linkedHashMap.put(Lv1DataFragment.this.batteryDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.batteryTextView));
                                    }
                                    if (z4 && Lv1DataFragment.this.gridDataSet != null) {
                                        linkedHashMap.put(Lv1DataFragment.this.gridDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.gridTextView));
                                    }
                                    linkedHashMap.put(Lv1DataFragment.this.consumptionDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.consumptionTextView));
                                    if (z && Lv1DataFragment.this.socDataSet != null) {
                                        linkedHashMap.put(Lv1DataFragment.this.socDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.socTextView));
                                    }
                                    Lv1DataFragment.this.customLineMarkerView.bindDataSets(linkedHashMap);
                                    Lv1DataFragment.this.powerLineChart.setData(new LineData((ILineDataSet[]) arrayList6.toArray(new ILineDataSet[0])));
                                }
                            }
                            z = true;
                            z2 = true;
                            z3 = true;
                            z4 = true;
                            if (z2) {
                                linkedHashMap.put(Lv1DataFragment.this.solarPvDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.solarPvTextView));
                            }
                            if (z3) {
                                linkedHashMap.put(Lv1DataFragment.this.batteryDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.batteryTextView));
                            }
                            if (z4) {
                                linkedHashMap.put(Lv1DataFragment.this.gridDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.gridTextView));
                            }
                            linkedHashMap.put(Lv1DataFragment.this.consumptionDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.consumptionTextView));
                            if (z) {
                                linkedHashMap.put(Lv1DataFragment.this.socDataSet, (TextView) Lv1DataFragment.this.customLineMarkerView.findViewById(R.id.socTextView));
                            }
                            Lv1DataFragment.this.customLineMarkerView.bindDataSets(linkedHashMap);
                            Lv1DataFragment.this.powerLineChart.setData(new LineData((ILineDataSet[]) arrayList6.toArray(new ILineDataSet[0])));
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
        CustomBarMarkerView customBarMarkerView = new CustomBarMarkerView(getActivity(), this.energyBarChart, R.layout.custom_bar_marker_view_layout, this.currentEnergyType);
        this.customBarMarkerView = customBarMarkerView;
        this.energyBarChart.setMarker(customBarMarkerView);
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
            barChart.clear();
            if (this.inverter != null) {
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m623xd22f5aa2();
                    }
                }).start();
                return;
            }
            this.energyBarChart.clear();
            this.energyBarChart.animateX(2000, Easing.EasingOption.EaseInCubic);
            this.energyBarChart.invalidate();
        }
    }

    /* renamed from: lambda$refreshEnergyBarChart$2$com-lux-luxcloud-view-main-fragment-lv1-Lv1DataFragment, reason: not valid java name */
    /* synthetic */ void m623xd22f5aa2() {
        String strConcat;
        final UserData userData = GlobalInfo.getInstance().getUserData();
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
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.16
                /* JADX WARN: Removed duplicated region for block: B:100:0x0369 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:101:0x0384 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:104:0x03a0 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:107:0x03c0 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:108:0x03db A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:111:0x03f7 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:114:0x0417 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:115:0x0432 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:118:0x044e A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:121:0x046e A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:122:0x0489 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:126:0x04c2 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:172:0x05b4  */
                /* JADX WARN: Removed duplicated region for block: B:183:0x0126 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:186:0x02a2 A[EDGE_INSN: B:186:0x02a2->B:82:0x02a2 BREAK  A[LOOP:0: B:32:0x011e->B:81:0x0293], SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:21:0x00d3 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:22:0x00f4 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:86:0x02b3 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:87:0x02d2 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:90:0x02f2 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:93:0x0312 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:94:0x032d A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:97:0x0349 A[Catch: Exception -> 0x0614, TryCatch #1 {Exception -> 0x0614, blocks: (B:3:0x0008, B:5:0x0011, B:7:0x0019, B:8:0x0022, B:10:0x0026, B:12:0x002e, B:14:0x0064, B:16:0x0072, B:18:0x00ca, B:21:0x00d3, B:32:0x011e, B:38:0x0135, B:84:0x02ab, B:86:0x02b3, B:88:0x02df, B:90:0x02f2, B:91:0x030a, B:93:0x0312, B:95:0x0336, B:97:0x0349, B:98:0x0361, B:100:0x0369, B:102:0x038d, B:104:0x03a0, B:105:0x03b8, B:107:0x03c0, B:109:0x03e4, B:111:0x03f7, B:112:0x040f, B:114:0x0417, B:116:0x043b, B:118:0x044e, B:119:0x0466, B:121:0x046e, B:123:0x0492, B:124:0x04bb, B:126:0x04c2, B:127:0x04cc, B:129:0x04d2, B:132:0x04ec, B:133:0x04ef, B:134:0x04f6, B:136:0x0507, B:138:0x050f, B:140:0x0515, B:142:0x0520, B:144:0x0528, B:146:0x052e, B:148:0x0539, B:150:0x0541, B:152:0x0547, B:154:0x0552, B:156:0x055a, B:158:0x0560, B:160:0x056b, B:162:0x0573, B:164:0x0579, B:165:0x0582, B:167:0x058a, B:169:0x0590, B:170:0x0599, B:173:0x05b5, B:177:0x05f3, B:122:0x0489, B:115:0x0432, B:108:0x03db, B:101:0x0384, B:94:0x032d, B:87:0x02d2, B:22:0x00f4, B:27:0x0107, B:176:0x05ea, B:178:0x060a), top: B:185:0x0008 }] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void run() throws org.json.JSONException {
                    /*
                        Method dump skipped, instructions count: 1578
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1DataFragment.AnonymousClass16.run():void");
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