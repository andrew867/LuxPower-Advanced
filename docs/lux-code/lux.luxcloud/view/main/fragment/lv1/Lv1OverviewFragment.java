package com.lux.luxcloud.view.main.fragment.lv1;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.location.DeviceOrientationRequest;
import com.google.firebase.messaging.Constants;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.global.custom.view.CircleView;
import com.lux.luxcloud.global.custom.view.GifView;
import com.lux.luxcloud.tool.AESUtil;
import com.lux.luxcloud.tool.API;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Custom;
import com.lux.luxcloud.view.main.MainActivity;
import com.lux.luxcloud.view.overview.inverter.InverterBatteryParamsActivity;
import com.lux.luxcloud.view.overview.inverter.InverterMidBoxRuntimeDataActivity;
import com.lux.luxcloud.view.overview.plant.PlantOverviewActivity;
import com.lux.luxcloud.view.plant.PlantListActivity;
import com.lux.luxcloud.view.userCenter.NewUserCenterActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Lv1OverviewFragment extends AbstractItemFragment {
    private static List<Integer> pvPieColors;
    private Button battCapacityButton;
    private Button battParallelNumButton;
    private Button batteryDataButton;
    private TextView batteryEnergyTitleLabel;
    private ConstraintLayout batteryEnergyViewLayout;
    private LinearLayout batteryEnergyViewLayout1;
    private LinearLayout batteryEnergyViewLayout2;
    private ImageView batteryParamImageView;
    private Button bmsLimitChargeButton;
    private Button bmsLimitDischargeButton;
    private ConstraintLayout centered1Layout;
    private ConstraintLayout centered2Layout;
    private ConstraintLayout centeredLayout;
    private TextView consumptionPowerValue1TextView;
    private TextView consumptionPowerValue2TextView;
    private CountDownTimer countDownTimer;
    private boolean created;
    private int currentBatteryEnergyViewIndex;
    private int currentFeedinEnergyViewIndex;
    private int currentPvEnergyViewIndex;
    private Button epsL1nButton;
    private Button epsL2nButton;
    private ConstraintLayout epsLayout;
    private ImageView epsParamImageView;
    private TextView epsValue1TextView;
    private TextView epsValue2TextView;
    private TextView feedinEnergyTitleLabel;
    private ConstraintLayout feedinEnergyViewLayout;
    private LinearLayout feedinEnergyViewLayout1;
    private LinearLayout feedinEnergyViewLayout2;
    private Button firmwareButton;
    private GifView flowAcPvPowerGifView1;
    private GifView flowAcPvPowerGifView2;
    private ImageView flowAcPvPowerImageView;
    private TextView flowAcPvPowerLabelTextView;
    private TextView flowAcPvPowerTextView;
    private TextView flowBatVoltTextView;
    private ImageView flowBatteryImageView;
    private GifView flowBatteryPowerGifView1;
    private GifView flowBatteryPowerGifView2;
    private TextView flowBatteryPowerLabelTextView;
    private TextView flowBatteryPowerTextView;
    private GifView flowConsumptionPowerGifView1;
    private GifView flowConsumptionPowerGifView2;
    private TextView flowConsumptionPowerTextView;
    private TextView flowEpsOutPutTextView;
    private GifView flowEpsPowerGifView;
    private TextView flowEpsPowerTextView;
    private TextView flowFacTextView;
    private GifView flowGridPowerGifView1;
    private GifView flowGridPowerGifView2;
    private TextView flowGridPowerLabelTextView;
    private TextView flowGridPowerTextView;
    private GifView flowInverterArrowGifView1;
    private GifView flowInverterArrowGifView2;
    private GifView flowInverterArrowGifView3;
    private ImageView flowInverterImageView;
    private GifView flowPvPowerGifView;
    private ImageView flowPvPowerImageView;
    private TextView flowPvPowerLabelTextView;
    private TextView flowPvPowerTextView;
    private TextView flowSocPowerTextView;
    private TextView flowVacTextView;
    private Fragment fragment;
    private Button genExerciseStartButton;
    private TextView generatorAutoStopTextView;
    private ImageView generatorImageView;
    private ImageView gridImageView;
    private TextView gridPowerValue1TextView;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private double is12TripAndPowerGH50_negative;
    private double is12TripAndPowerGH50_positive;
    private boolean isSpecificLanguage;
    private long lastRefreshDataTime;
    private Inverter lastReloadInverter;
    private long lastReloadTime;
    private TextView localTimeTextView;
    private Button midboxRuntimeButton;
    private Button midboxRuntimeButton2;
    private ConstraintLayout newButton2Layout;
    private ConstraintLayout newButton3Layout;
    private ConstraintLayout newButtonLayout;
    private Button powerRateButton;
    private TextView pvChargeToday;
    private TextView pvChargeTotal;
    private ConstraintLayout pvEnergyViewLayout;
    private LinearLayout pvEnergyViewLayout1;
    private ConstraintLayout pvEnergyViewLayout2;
    private ConstraintLayout pvEnergyViewLayout3;
    private TextView pvExportToday;
    private TextView pvExportTotal;
    private TextView pvLoadToday;
    private TextView pvLoadTotal;
    private Button quickDisChargeButton;
    private Button quickGridChargeButton;
    private Button refreshButton;
    private int selectedItemPosition;
    private boolean shouldShowTodayCircleView;
    private boolean shouldShowTotalCircleView;
    private String status;
    private ImageView statusImageView;
    private Button stopGenExerciseStartButton;
    private Button stopQuickDisChargeButton;
    private Button stopQuickGridChargeButton;
    private TextView todayAllPvEnergy;
    private TextView todayChargingTextView;
    private CircleView todayCircleView;
    private TextView todayDischargingTextView;
    private TextView todayExportTextView;
    private TextView todayImportTextView;
    private PieChart todayPvPieChart;
    private PieDataSet todayPvPieDataSet;
    private TextView todayUsageTextView;
    private TextView todayYieldingTextView;
    private TextView totalAllPvEnergy;
    private TextView totalChargingTextView;
    private CircleView totalCircleView;
    private TextView totalDischargingTextView;
    private TextView totalExportTextView;
    private TextView totalImportTextView;
    private PieChart totalPvPieChart;
    private PieDataSet totalPvPieDataSet;
    private TextView totalUsageTextView;
    private TextView totalYieldingTextView;
    private int waitRequestCount;

    public Lv1OverviewFragment() {
        super(1L);
        this.currentPvEnergyViewIndex = 1;
        this.currentBatteryEnergyViewIndex = 1;
        this.currentFeedinEnergyViewIndex = 1;
        this.is12TripAndPowerGH50_positive = 0.0d;
        this.is12TripAndPowerGH50_negative = 0.0d;
    }

    static {
        ArrayList arrayList = new ArrayList();
        pvPieColors = arrayList;
        arrayList.add(Integer.valueOf(Color.rgb(255, 113, 143)));
        pvPieColors.add(Integer.valueOf(Color.rgb(92, 201, 160)));
        pvPieColors.add(Integer.valueOf(Color.rgb(242, 164, 116)));
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        List<Inverter> invertersByPlant;
        System.out.println("LuxPower - Lv1OverviewFragment.onCreateView...");
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lv1_overview, viewGroup, false);
        this.fragment = this;
        final UserData userData = GlobalInfo.getInstance().getUserData();
        if (!userData.needShowCompanyLogo()) {
            viewInflate.findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1OverviewFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                MainActivity.instance.finish();
            }
        });
        ((ImageView) viewInflate.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1OverviewFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
            }
        });
        this.inverterSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_overview_inverter_spinner);
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
        ArrayAdapter arrayAdapter = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                Inverter inverter3 = (Inverter) Lv1OverviewFragment.this.inverterSpinner.getSelectedItem();
                System.out.println("LuxPower - onItemSelected selectInverter = " + inverter3);
                if (Lv1OverviewFragment.this.inverter == null || !Lv1OverviewFragment.this.inverter.getSerialNum().equals(inverter3.getSerialNum())) {
                    Lv1OverviewFragment.this.inverter = inverter3;
                    Lv1OverviewFragment.this.initFlowChartByDeviceType();
                    UserData userData2 = GlobalInfo.getInstance().getUserData();
                    if (Lv1OverviewFragment.this.inverter.isParallelGroup()) {
                        userData2.setCurrentParallelGroup(Lv1OverviewFragment.this.inverter);
                    } else {
                        userData2.setCurrentInverter(Lv1OverviewFragment.this.inverter, true);
                    }
                    if (Lv1OverviewFragment.this.inverter.getWithbatteryData() == null || !Lv1OverviewFragment.this.inverter.getWithbatteryData().booleanValue()) {
                        Lv1OverviewFragment.this.newButton2Layout.setVisibility(8);
                    } else {
                        Lv1OverviewFragment.this.newButton2Layout.setVisibility(0);
                    }
                    if (Lv1OverviewFragment.this.inverter.isMidBox()) {
                        Lv1OverviewFragment.this.newButton3Layout.setVisibility(0);
                        Lv1OverviewFragment.this.midboxRuntimeButton.setVisibility(0);
                    } else {
                        Lv1OverviewFragment.this.newButton3Layout.setVisibility(8);
                        Lv1OverviewFragment.this.midboxRuntimeButton.setVisibility(4);
                    }
                    if (Lv1OverviewFragment.this.inverter.isParallelGroup()) {
                        Lv1OverviewFragment.this.newButton3Layout.setVisibility(8);
                    }
                    if (Lv1OverviewFragment.this.inverter.isMidBox()) {
                        Lv1OverviewFragment.this.powerRateButton.setVisibility(4);
                    }
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    lv1OverviewFragment.is12TripAndPowerGH50_positive = lv1OverviewFragment.inverter.isTrip12K() ? 99.99d : 0.0d;
                    Lv1OverviewFragment lv1OverviewFragment2 = Lv1OverviewFragment.this;
                    lv1OverviewFragment2.is12TripAndPowerGH50_negative = lv1OverviewFragment2.inverter.isTrip12K() ? -99.99d : 0.0d;
                    Lv1OverviewFragment.this.m631x6c825f2a();
                    Lv1OverviewFragment lv1OverviewFragment3 = Lv1OverviewFragment.this;
                    lv1OverviewFragment3.is12TripAndPowerGH50_positive = lv1OverviewFragment3.inverter.isTrip12K() ? 99.99d : 0.0d;
                    Lv1OverviewFragment lv1OverviewFragment4 = Lv1OverviewFragment.this;
                    lv1OverviewFragment4.is12TripAndPowerGH50_negative = lv1OverviewFragment4.inverter.isTrip12K() ? -99.99d : 0.0d;
                    MainActivity mainActivity = (MainActivity) Lv1OverviewFragment.this.fragment.getActivity();
                    if (mainActivity != null) {
                        mainActivity.switchRemoteSetFragment(Lv1OverviewFragment.this.inverter.getDeviceTypeValue());
                    }
                    Lv1OverviewFragment lv1OverviewFragment5 = Lv1OverviewFragment.this;
                    lv1OverviewFragment5.selectedItemPosition = lv1OverviewFragment5.inverterSpinner.getSelectedItemPosition();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    UserData userData2 = GlobalInfo.getInstance().getUserData();
                    if (Lv1OverviewFragment.this.inverter.isParallelGroup()) {
                        userData2.setCurrentParallelGroup(null);
                    } else {
                        userData2.setCurrentInverter(null, true);
                    }
                    if (Lv1OverviewFragment.this.inverter != null && !Lv1OverviewFragment.this.inverter.isAllowGenExercise()) {
                        Lv1OverviewFragment.this.genExerciseStartButton.setVisibility(4);
                        Lv1OverviewFragment.this.stopGenExerciseStartButton.setVisibility(4);
                    }
                    if (Lv1OverviewFragment.this.inverter != null && Lv1OverviewFragment.this.inverter.isParallelGroup()) {
                        Lv1OverviewFragment.this.centeredLayout.setVisibility(0);
                        Lv1OverviewFragment.this.epsLayout.setVisibility(0);
                    }
                    Lv1OverviewFragment.this.inverter = null;
                    Lv1OverviewFragment.this.m631x6c825f2a();
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    lv1OverviewFragment.is12TripAndPowerGH50_positive = lv1OverviewFragment.inverter.isTrip12K() ? 99.99d : 0.0d;
                    Lv1OverviewFragment lv1OverviewFragment2 = Lv1OverviewFragment.this;
                    lv1OverviewFragment2.is12TripAndPowerGH50_negative = lv1OverviewFragment2.inverter.isTrip12K() ? -99.99d : 0.0d;
                }
            }
        });
        this.pvEnergyViewLayout1 = (LinearLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout1);
        this.pvEnergyViewLayout2 = (ConstraintLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout2);
        this.pvEnergyViewLayout3 = (ConstraintLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout3);
        ConstraintLayout constraintLayout = (ConstraintLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout);
        this.pvEnergyViewLayout = constraintLayout;
        constraintLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Lv1OverviewFragment.this.currentPvEnergyViewIndex == 1) {
                    Lv1OverviewFragment.this.pvEnergyViewLayout1.setVisibility(8);
                    Lv1OverviewFragment.this.pvEnergyViewLayout3.setVisibility(8);
                    Lv1OverviewFragment.this.todayCircleView.setVisibility(Lv1OverviewFragment.this.shouldShowTodayCircleView ? 0 : 8);
                    Lv1OverviewFragment.this.totalCircleView.setVisibility(8);
                    Lv1OverviewFragment.this.pvEnergyViewLayout2.setVisibility(0);
                    Lv1OverviewFragment.this.currentPvEnergyViewIndex = 2;
                } else if (Lv1OverviewFragment.this.currentPvEnergyViewIndex == 2) {
                    Lv1OverviewFragment.this.pvEnergyViewLayout1.setVisibility(8);
                    Lv1OverviewFragment.this.pvEnergyViewLayout2.setVisibility(8);
                    Lv1OverviewFragment.this.todayCircleView.setVisibility(8);
                    Lv1OverviewFragment.this.totalCircleView.setVisibility(Lv1OverviewFragment.this.shouldShowTotalCircleView ? 0 : 8);
                    Lv1OverviewFragment.this.pvEnergyViewLayout3.setVisibility(0);
                    Lv1OverviewFragment.this.currentPvEnergyViewIndex = 3;
                } else {
                    Lv1OverviewFragment.this.pvEnergyViewLayout2.setVisibility(8);
                    Lv1OverviewFragment.this.pvEnergyViewLayout3.setVisibility(8);
                    Lv1OverviewFragment.this.todayCircleView.setVisibility(8);
                    Lv1OverviewFragment.this.totalCircleView.setVisibility(8);
                    Lv1OverviewFragment.this.pvEnergyViewLayout1.setVisibility(0);
                    Lv1OverviewFragment.this.currentPvEnergyViewIndex = 1;
                }
                return false;
            }
        });
        this.batteryEnergyTitleLabel = (TextView) viewInflate.findViewById(R.id.batteryEnergyTitleLabel);
        this.batteryEnergyViewLayout1 = (LinearLayout) viewInflate.findViewById(R.id.batteryEnergyViewLayout1);
        this.batteryEnergyViewLayout2 = (LinearLayout) viewInflate.findViewById(R.id.batteryEnergyViewLayout2);
        this.batteryEnergyViewLayout = (ConstraintLayout) viewInflate.findViewById(R.id.batteryEnergyViewLayout);
        if (userData.isGigabiz1User()) {
            this.batteryEnergyTitleLabel.setText(R.string.main_overview_info_box_battery_charging);
            this.batteryEnergyViewLayout1.setVisibility(8);
            this.batteryEnergyViewLayout2.setVisibility(0);
            this.currentBatteryEnergyViewIndex = 2;
        } else {
            this.batteryEnergyViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda8
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.m628xa19bbd5b(view, motionEvent);
                }
            });
        }
        this.feedinEnergyViewLayout1 = (LinearLayout) viewInflate.findViewById(R.id.feedinEnergyViewLayout1);
        this.feedinEnergyViewLayout2 = (LinearLayout) viewInflate.findViewById(R.id.feedinEnergyViewLayout2);
        ConstraintLayout constraintLayout2 = (ConstraintLayout) viewInflate.findViewById(R.id.feedinEnergyViewLayout);
        this.feedinEnergyViewLayout = constraintLayout2;
        constraintLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda9
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.m629x5c115ddc(view, motionEvent);
            }
        });
        this.pvLoadToday = (TextView) viewInflate.findViewById(R.id.main_overview_info_pvLoadToday);
        this.pvChargeToday = (TextView) viewInflate.findViewById(R.id.main_overview_info_pvChargeToday);
        this.pvExportToday = (TextView) viewInflate.findViewById(R.id.main_overview_info_pvExportToday);
        this.todayAllPvEnergy = (TextView) viewInflate.findViewById(R.id.main_overview_info_todayAllPvEnergy);
        PieChart pieChart = (PieChart) viewInflate.findViewById(R.id.main_overview_info_todayPvPieChart);
        this.todayPvPieChart = pieChart;
        pieChart.getLegend().setEnabled(false);
        this.todayPvPieChart.getDescription().setEnabled(false);
        this.todayPvPieChart.setDrawHoleEnabled(false);
        this.shouldShowTodayCircleView = this.inverterList.isEmpty();
        this.todayCircleView = (CircleView) viewInflate.findViewById(R.id.main_overview_info_todayPvPieChartMask);
        this.pvLoadTotal = (TextView) viewInflate.findViewById(R.id.main_overview_info_pvLoadTotal);
        this.pvChargeTotal = (TextView) viewInflate.findViewById(R.id.main_overview_info_pvChargeTotal);
        this.pvExportTotal = (TextView) viewInflate.findViewById(R.id.main_overview_info_pvExportTotal);
        this.totalAllPvEnergy = (TextView) viewInflate.findViewById(R.id.main_overview_info_totalAllPvEnergy);
        PieChart pieChart2 = (PieChart) viewInflate.findViewById(R.id.main_overview_info_totalPvPieChart);
        this.totalPvPieChart = pieChart2;
        pieChart2.getLegend().setEnabled(false);
        this.totalPvPieChart.getDescription().setEnabled(false);
        this.totalPvPieChart.setDrawHoleEnabled(false);
        this.shouldShowTotalCircleView = this.inverterList.isEmpty();
        this.totalCircleView = (CircleView) viewInflate.findViewById(R.id.main_overview_info_totalPvPieChartMask);
        this.todayYieldingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_yielding);
        this.totalYieldingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_yielding);
        this.todayDischargingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_discharging);
        this.totalDischargingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_discharging);
        this.todayChargingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_charging);
        this.totalChargingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_charging);
        this.feedinEnergyTitleLabel = (TextView) viewInflate.findViewById(R.id.feedinEnergyTitleLabel);
        this.todayExportTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_export);
        this.totalExportTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_export);
        this.todayImportTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_import);
        this.totalImportTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_import);
        this.todayUsageTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_usage);
        this.totalUsageTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_usage);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_statusImageView);
        this.statusImageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Constants.IPC_BUNDLE_KEY_SEND_ERROR.equals(Lv1OverviewFragment.this.status)) {
                    return;
                }
                "warning".equals(Lv1OverviewFragment.this.status);
            }
        });
        this.flowBatteryPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_batteryPower_textView);
        this.flowBatteryPowerLabelTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_batteryPowerLabel_textView);
        this.flowBatteryPowerGifView1 = (GifView) viewInflate.findViewById(R.id.fragment_flow_batteryPower_gifView1);
        this.flowBatteryPowerGifView2 = (GifView) viewInflate.findViewById(R.id.fragment_flow_batteryPower_gifView2);
        this.flowSocPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_socPower_textView);
        this.flowBatVoltTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_batVoltTextView);
        this.flowPvPowerImageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_pvPower_imageView);
        this.flowPvPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_pvPower_textView);
        this.flowEpsOutPutTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_epsOutput_textView);
        this.flowPvPowerLabelTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_pvPowerLabel_textView);
        GifView gifView = (GifView) viewInflate.findViewById(R.id.fragment_flow_pvPower_gifView);
        this.flowPvPowerGifView = gifView;
        gifView.setMovieResource(R.drawable.arrow_down);
        this.flowPvPowerGifView.setVisibility(4);
        this.flowAcPvPowerImageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_acPvPower_imageView);
        this.flowAcPvPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_acPvPower_textView);
        this.flowAcPvPowerLabelTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_acPvPowerLabel_textView);
        GifView gifView2 = (GifView) viewInflate.findViewById(R.id.fragment_flow_acPvPower_gifView1);
        this.flowAcPvPowerGifView1 = gifView2;
        gifView2.setMovieResource(R.drawable.arrow_down);
        this.flowAcPvPowerGifView1.setVisibility(4);
        GifView gifView3 = (GifView) viewInflate.findViewById(R.id.fragment_flow_acPvPower_gifView2);
        this.flowAcPvPowerGifView2 = gifView3;
        gifView3.setMovieResource(R.drawable.arrow_down);
        this.flowAcPvPowerGifView2.setVisibility(4);
        this.flowInverterArrowGifView1 = (GifView) viewInflate.findViewById(R.id.fragment_flow_inverterArrow_gifView1);
        this.flowInverterArrowGifView2 = (GifView) viewInflate.findViewById(R.id.fragment_flow_inverterArrow_gifView2);
        this.flowInverterArrowGifView3 = (GifView) viewInflate.findViewById(R.id.fragment_flow_inverterArrow_gifView3);
        this.flowGridPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_gridPower_textView);
        this.flowGridPowerLabelTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_gridPowerLabel_textView);
        this.flowGridPowerGifView1 = (GifView) viewInflate.findViewById(R.id.fragment_flow_gridPower_gifView1);
        this.flowGridPowerGifView2 = (GifView) viewInflate.findViewById(R.id.fragment_flow_gridPower_gifView2);
        this.flowVacTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_vacLabel_textView);
        this.flowFacTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_facLabel_textView);
        this.flowConsumptionPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_consumptionPower_textView);
        GifView gifView4 = (GifView) viewInflate.findViewById(R.id.fragment_flow_consumptionPower_gifView1);
        this.flowConsumptionPowerGifView1 = gifView4;
        gifView4.setMovieResource(R.drawable.arrow_down);
        this.flowConsumptionPowerGifView1.setVisibility(4);
        GifView gifView5 = (GifView) viewInflate.findViewById(R.id.fragment_flow_consumptionPower_gifView2);
        this.flowConsumptionPowerGifView2 = gifView5;
        gifView5.setMovieResource(R.drawable.arrow_down);
        this.flowConsumptionPowerGifView2.setVisibility(4);
        this.flowEpsPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_epsPower_textView);
        GifView gifView6 = (GifView) viewInflate.findViewById(R.id.fragment_flow_epsPower_gifView);
        this.flowEpsPowerGifView = gifView6;
        gifView6.setMovieResource(R.drawable.arrow_down);
        this.flowEpsPowerGifView.setVisibility(4);
        this.flowBatteryImageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_battery_imageView);
        this.gridPowerValue1TextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_gridPowerValue1_textView);
        this.epsValue1TextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_epsValue1_textView);
        this.epsValue2TextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_epsValue2_textView);
        this.consumptionPowerValue1TextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_consumptionPowerValue1_textView);
        this.consumptionPowerValue2TextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_consumptionPowerValue2_textView);
        this.gridImageView = (ImageView) viewInflate.findViewById(R.id.gridImageView);
        this.generatorImageView = (ImageView) viewInflate.findViewById(R.id.generatorImageView);
        this.flowInverterImageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_inverter_imageView);
        this.localTimeTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_localTime_textView);
        this.powerRateButton = (Button) viewInflate.findViewById(R.id.power_rate_button);
        this.firmwareButton = (Button) viewInflate.findViewById(R.id.firmware_button);
        this.battParallelNumButton = (Button) viewInflate.findViewById(R.id.batt_parallel_button);
        this.battCapacityButton = (Button) viewInflate.findViewById(R.id.batt_capacity_button);
        this.bmsLimitChargeButton = (Button) viewInflate.findViewById(R.id.bms_limit_charge_button);
        this.bmsLimitDischargeButton = (Button) viewInflate.findViewById(R.id.bms_limit_discharge_button);
        this.epsL1nButton = (Button) viewInflate.findViewById(R.id.EPS_L1N_BUTTON);
        this.epsL2nButton = (Button) viewInflate.findViewById(R.id.EPS_L2N_BUTTON);
        this.centeredLayout = (ConstraintLayout) viewInflate.findViewById(R.id.centered_Layout);
        this.centered1Layout = (ConstraintLayout) viewInflate.findViewById(R.id.centered1_Layout);
        this.centered2Layout = (ConstraintLayout) viewInflate.findViewById(R.id.centered2_Layout);
        this.generatorAutoStopTextView = (TextView) viewInflate.findViewById(R.id.generator_auto_stopTextView);
        this.newButtonLayout = (ConstraintLayout) viewInflate.findViewById(R.id.new_button_layout);
        this.quickGridChargeButton = (Button) viewInflate.findViewById(R.id.quick_Grid_Charge_Button);
        String string = getString(R.string.start_quick_charge);
        SpannableString spannableString = new SpannableString(string + " ↗");
        spannableString.setSpan(new StyleSpan(1), 0, string.length(), 33);
        spannableString.setSpan(new StyleSpan(1), string.length(), string.length() + 2, 33);
        spannableString.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString.length(), 18);
        this.quickGridChargeButton.setText(spannableString);
        this.quickGridChargeButton.setTextSize(1, 12.0f);
        this.quickGridChargeButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.quickGridChargeButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new QuickChargeCtrlTask(lv1OverviewFragment, lv1OverviewFragment.quickGridChargeButton).execute(true);
                }
            }
        });
        this.stopQuickGridChargeButton = (Button) viewInflate.findViewById(R.id.stop_quick_Grid_Charge_Button);
        String string2 = getString(R.string.stop_quick_charge);
        SpannableString spannableString2 = new SpannableString(string2 + " ↗");
        spannableString2.setSpan(new StyleSpan(1), 0, string2.length(), 33);
        spannableString2.setSpan(new StyleSpan(1), string2.length(), string2.length() + 2, 33);
        spannableString2.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString2.length(), 18);
        this.stopQuickGridChargeButton.setText(spannableString2);
        this.stopQuickGridChargeButton.setTextSize(1, 12.0f);
        this.stopQuickGridChargeButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.stopQuickGridChargeButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new QuickChargeCtrlTask(lv1OverviewFragment, lv1OverviewFragment.stopQuickGridChargeButton).execute(false);
                }
            }
        });
        this.genExerciseStartButton = (Button) viewInflate.findViewById(R.id.start_GEN_Exercise_Button);
        String string3 = getString(R.string.start_gen_exercise);
        SpannableString spannableString3 = new SpannableString(string3 + " ↗");
        spannableString3.setSpan(new StyleSpan(1), 0, string3.length(), 33);
        spannableString3.setSpan(new StyleSpan(1), string3.length(), string3.length() + 2, 33);
        spannableString3.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString3.length(), 18);
        this.genExerciseStartButton.setText(spannableString3);
        this.genExerciseStartButton.setTextSize(1, 12.0f);
        this.genExerciseStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.genExerciseStartButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new GenExerciseCtrlTask(lv1OverviewFragment, lv1OverviewFragment.genExerciseStartButton).execute(true);
                }
            }
        });
        this.stopGenExerciseStartButton = (Button) viewInflate.findViewById(R.id.stop_GEN_Exercise_Button);
        String string4 = getString(R.string.stop_gen_exercise);
        SpannableString spannableString4 = new SpannableString(string4 + " ↗");
        spannableString4.setSpan(new StyleSpan(1), 0, string4.length(), 33);
        spannableString4.setSpan(new StyleSpan(1), string4.length(), string4.length() + 2, 33);
        spannableString4.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString4.length(), 18);
        this.stopGenExerciseStartButton.setText(spannableString4);
        this.stopGenExerciseStartButton.setTextSize(1, 12.0f);
        this.stopGenExerciseStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.stopGenExerciseStartButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new GenExerciseCtrlTask(lv1OverviewFragment, lv1OverviewFragment.stopGenExerciseStartButton).execute(false);
                }
            }
        });
        this.quickDisChargeButton = (Button) viewInflate.findViewById(R.id.start_quick_discharge_Charge_Button);
        String string5 = getString(R.string.start_quick_discharge);
        SpannableString spannableString5 = new SpannableString(string5 + " ↗");
        spannableString5.setSpan(new StyleSpan(1), 0, string5.length(), 33);
        spannableString5.setSpan(new StyleSpan(1), string5.length(), string5.length() + 2, 33);
        spannableString5.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString5.length(), 18);
        this.quickDisChargeButton.setText(spannableString5);
        this.quickDisChargeButton.setTextSize(1, 12.0f);
        this.quickDisChargeButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.quickDisChargeButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new QuickDisChargeCtrlTask(lv1OverviewFragment, lv1OverviewFragment.quickDisChargeButton).execute(true);
                }
            }
        });
        this.stopQuickDisChargeButton = (Button) viewInflate.findViewById(R.id.stop_quick_discharge_Charge_Button);
        String string6 = getString(R.string.stop_quick_discharge);
        SpannableString spannableString6 = new SpannableString(string6 + " ↗");
        spannableString6.setSpan(new StyleSpan(1), 0, string6.length(), 33);
        spannableString6.setSpan(new StyleSpan(1), string6.length(), string6.length() + 2, 33);
        spannableString6.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString6.length(), 18);
        this.stopQuickDisChargeButton.setText(spannableString6);
        this.stopQuickDisChargeButton.setTextSize(1, 12.0f);
        this.stopQuickDisChargeButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.stopQuickDisChargeButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new QuickDisChargeCtrlTask(lv1OverviewFragment, lv1OverviewFragment.stopQuickDisChargeButton).execute(false);
                }
            }
        });
        this.newButton2Layout = (ConstraintLayout) viewInflate.findViewById(R.id.new_button2_layout);
        this.batteryDataButton = (Button) viewInflate.findViewById(R.id.with_battery_Data_Button);
        String string7 = getString(R.string.battery_params);
        SpannableString spannableString7 = new SpannableString(string7 + " ↗");
        spannableString7.setSpan(new StyleSpan(1), 0, string7.length(), 33);
        spannableString7.setSpan(new StyleSpan(1), string7.length(), string7.length() + 2, 33);
        spannableString7.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString7.length(), 18);
        this.batteryDataButton.setText(spannableString7);
        this.batteryDataButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Lv1OverviewFragment.this.getActivity(), (Class<?>) InverterBatteryParamsActivity.class);
                intent.putExtra("selectedItemPosition", Lv1OverviewFragment.this.selectedItemPosition);
                Lv1OverviewFragment.this.startActivity(intent);
            }
        });
        this.newButton3Layout = (ConstraintLayout) viewInflate.findViewById(R.id.new_button3_layout);
        this.midboxRuntimeButton = (Button) viewInflate.findViewById(R.id.midBox_runtime_button);
        this.midboxRuntimeButton2 = (Button) viewInflate.findViewById(R.id.midBox_runtime_button2);
        SpannableString spannableString8 = new SpannableString("MidBox RunTime Data ↗");
        spannableString8.setSpan(new StyleSpan(1), 0, 19, 33);
        spannableString8.setSpan(new StyleSpan(1), 19, 21, 33);
        spannableString8.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString8.length(), 18);
        this.midboxRuntimeButton.setText(spannableString8);
        this.midboxRuntimeButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Lv1OverviewFragment.this.getActivity(), (Class<?>) InverterMidBoxRuntimeDataActivity.class);
                intent.putExtra("inverterSn", Lv1OverviewFragment.this.inverter.getSerialNum());
                Lv1OverviewFragment.this.startActivity(intent);
            }
        });
        this.epsLayout = (ConstraintLayout) viewInflate.findViewById(R.id.eps_Layout);
        this.batteryParamImageView = (ImageView) viewInflate.findViewById(R.id.batteryParamImageView);
        this.epsParamImageView = (ImageView) viewInflate.findViewById(R.id.epsParamImageView);
        Button button = (Button) viewInflate.findViewById(R.id.fragment_flow_refreshButton);
        this.refreshButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1OverviewFragment.this.refreshData();
            }
        });
        this.isSpecificLanguage = GlobalInfo.getInstance().isSpecificLanguage(getResources().getConfiguration().locale);
        this.created = true;
        if (this.inverterList.isEmpty()) {
            initFlowChartByDeviceType();
        }
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ boolean m628xa19bbd5b(View view, MotionEvent motionEvent) {
        if (this.currentBatteryEnergyViewIndex == 1) {
            this.batteryEnergyTitleLabel.setText(R.string.main_overview_info_box_battery_charging);
            this.batteryEnergyViewLayout1.setVisibility(8);
            this.batteryEnergyViewLayout2.setVisibility(0);
            this.currentBatteryEnergyViewIndex = 2;
        } else {
            this.batteryEnergyTitleLabel.setText(R.string.main_overview_info_box_battery_discharging);
            this.batteryEnergyViewLayout2.setVisibility(8);
            this.batteryEnergyViewLayout1.setVisibility(0);
            this.currentBatteryEnergyViewIndex = 1;
        }
        return false;
    }

    /* renamed from: lambda$onCreateView$1$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ boolean m629x5c115ddc(View view, MotionEvent motionEvent) {
        if (this.currentFeedinEnergyViewIndex == 1) {
            this.feedinEnergyTitleLabel.setText(R.string.main_overview_info_box_import);
            this.feedinEnergyViewLayout1.setVisibility(8);
            this.feedinEnergyViewLayout2.setVisibility(0);
            this.currentFeedinEnergyViewIndex = 2;
        } else {
            this.feedinEnergyTitleLabel.setText(R.string.main_overview_info_box_feed_in_discharging);
            this.feedinEnergyViewLayout2.setVisibility(8);
            this.feedinEnergyViewLayout1.setVisibility(0);
            this.currentFeedinEnergyViewIndex = 1;
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("LuxPower - Lv1OverviewFragment.onResume...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    public long getLastRefreshDataTime() {
        return this.lastRefreshDataTime;
    }

    public void refreshData() {
        if (this.created) {
            if (this.inverter != null) {
                this.lastRefreshDataTime = System.currentTimeMillis();
                this.refreshButton.setEnabled(false);
                CountDownTimer countDownTimer = this.countDownTimer;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m632x26f7ffab();
                    }
                }).start();
                return;
            }
            this.waitRequestCount = 2;
            m631x6c825f2a();
        }
    }

    /* renamed from: lambda$refreshData$5$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m632x26f7ffab() {
        boolean z;
        try {
            HashMap map = new HashMap();
            map.put("inverterSn", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
            UserData userData = GlobalInfo.getInstance().getUserData();
            HashMap map2 = new HashMap();
            map2.put("Encrypted-Data", AESUtil.encrypt(System.currentTimeMillis() + "&" + userData.getUserId()));
            final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteTransfer/refreshInputData", map, map2);
            if (jSONObjectPostJson != null && jSONObjectPostJson.getBoolean("success")) {
                Tool.sleep(3000L);
                z = true;
            } else {
                final FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() throws JSONException {
                            this.f$0.m630xf7971e28(jSONObjectPostJson, activity);
                        }
                    });
                }
                z = false;
            }
            if (z) {
                this.waitRequestCount = 2;
                FragmentActivity activity2 = getActivity();
                if (activity2 != null) {
                    activity2.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m631x6c825f2a();
                        }
                    });
                }
            }
        } catch (Exception e) {
            final FragmentActivity activity3 = getActivity();
            if (activity3 != null) {
                activity3.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(activity3.getApplicationContext(), R.string.phrase_toast_response_error, 1).show();
                    }
                });
            }
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$refreshData$2$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m630xf7971e28(JSONObject jSONObject, FragmentActivity fragmentActivity) throws JSONException {
        String string;
        if (jSONObject == null) {
            string = getString(R.string.page_maintain_remote_set_result_server_exception);
        } else {
            try {
                string = jSONObject.getString("msg");
            } catch (JSONException unused) {
                string = getString(R.string.phrase_toast_unknown_error);
            }
        }
        Toast.makeText(fragmentActivity.getApplicationContext(), "Error: " + string, 0).show();
        this.refreshButton.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFlowChartByDeviceType() {
        if (this.created) {
            ImageView imageView = this.generatorImageView;
            if (imageView != null) {
                imageView.setVisibility(4);
            }
            ImageView imageView2 = this.gridImageView;
            boolean z = false;
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            clearBattInfo();
            clearEpsLnInfo();
            Inverter inverter = this.inverter;
            if (inverter != null && inverter.isTrip12K()) {
                this.flowEpsOutPutTextView.setText(R.string.main_flow_ups_output);
            }
            Button button = this.powerRateButton;
            if (button != null) {
                button.setText("");
            }
            Button button2 = this.firmwareButton;
            if (button2 != null) {
                button2.setText("");
            }
            this.centeredLayout.setVisibility(0);
            Inverter inverter2 = this.inverter;
            if (inverter2 != null && inverter2.isParallelGroup()) {
                this.centered1Layout.setVisibility(8);
            } else {
                this.centered1Layout.setVisibility(0);
            }
            this.centered2Layout.setVisibility(0);
            Inverter inverter3 = this.inverter;
            if (inverter3 != null && !inverter3.isAllowGenExercise()) {
                this.genExerciseStartButton.setVisibility(4);
                this.stopGenExerciseStartButton.setVisibility(4);
            }
            Inverter inverter4 = this.inverter;
            if (inverter4 != null && inverter4.isAcCharger()) {
                z = true;
            }
            initFlowChartByDeviceExceptDeviceImg(z);
            adjustGetaDeviceHideView();
            refreshDeviceImageSource();
        }
    }

    private void adjustGetaDeviceHideView() {
        Inverter inverter = this.inverter;
        if (inverter == null || !inverter.isGeta()) {
            return;
        }
        this.flowGridPowerTextView.setVisibility(4);
        this.flowGridPowerLabelTextView.setVisibility(4);
        this.flowVacTextView.setVisibility(4);
        this.flowFacTextView.setVisibility(4);
        this.gridPowerValue1TextView.setVisibility(4);
        this.flowConsumptionPowerTextView.setVisibility(4);
    }

    private void refreshDeviceImageSource() {
        System.out.println("LuxPower - refreshDeviceImageSource...");
        if (!this.created || this.flowInverterImageView == null) {
            return;
        }
        Inverter inverter = this.inverter;
        if (inverter != null && inverter.isParallelGroup()) {
            this.flowInverterImageView.setBackgroundResource(R.drawable.icon_inverter_parallel);
            return;
        }
        Integer numValueOf = null;
        if (PLATFORM.LUX_POWER.equals(GlobalInfo.getInstance().getUserData().getPlatform()) && "LUX_POWER".equals(Custom.APP_USER_PLATFORM.name())) {
            Inverter inverter2 = this.inverter;
            if (inverter2 != null && inverter2.isAcCharger()) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_ac_charger);
            } else {
                Inverter inverter3 = this.inverter;
                if (inverter3 != null && inverter3.isSnaSeries()) {
                    if (this.inverter.isEcoBeast6k()) {
                        numValueOf = Integer.valueOf(R.drawable.inverter_eco_beast_6000);
                    } else if (this.inverter.isSna6kUsAio()) {
                        numValueOf = Integer.valueOf(R.drawable.inverter_us_aio);
                    } else if (this.inverter.isSna6kEneleageZero()) {
                        numValueOf = Integer.valueOf(R.drawable.icon_sna_3_6k_volta);
                    } else if (this.inverter.getSubDeviceTypeValue() == 131) {
                        numValueOf = Integer.valueOf(R.drawable.icon_dt_sna_us_6k);
                    } else if (this.inverter.getSubDeviceTypeValue() == 1111) {
                        numValueOf = Integer.valueOf(R.drawable.icon_sna_12k_us);
                    } else if (this.inverter.getSubDeviceTypeValue() == 1110) {
                        numValueOf = Integer.valueOf(R.drawable.icon_sna_12k_eu);
                    } else {
                        numValueOf = Integer.valueOf(R.drawable.icon_dt_inverter_off_grid);
                    }
                } else {
                    Inverter inverter4 = this.inverter;
                    if (inverter4 != null && inverter4.isType6Series()) {
                        if (this.inverter.getSubDeviceTypeValue() == 161) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_8_10k_eu);
                        } else if (this.inverter.getSubDeviceTypeValue() == 162) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_12k_eu);
                        } else if (this.inverter.getSubDeviceTypeValue() == 163) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_8_10k_us);
                        } else if (this.inverter.getSubDeviceTypeValue() == 164) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_12k_us);
                        }
                        if (this.inverter.getMachineType() == 1) {
                            numValueOf = Integer.valueOf(R.drawable.icon_inverter_minus);
                        }
                    }
                }
            }
            Inverter inverter5 = this.inverter;
            if (inverter5 != null && inverter5.isAllInOne()) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_all_in_one);
            }
            Inverter inverter6 = this.inverter;
            if (inverter6 != null && inverter6.isTrip12K()) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_trip);
            }
            Inverter inverter7 = this.inverter;
            if (inverter7 != null && inverter7.is7_10KDevice()) {
                numValueOf = Integer.valueOf(R.drawable.icon_gen_7_10k);
            }
            Inverter inverter8 = this.inverter;
            if (inverter8 != null && inverter8.isGen3_6K()) {
                numValueOf = Integer.valueOf(R.drawable.icon_gen_3_6k);
            }
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_inverter);
            }
        } else {
            Inverter inverter9 = this.inverter;
            if (inverter9 != null && inverter9.isAcCharger()) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_ac_charger_mid);
            } else {
                Inverter inverter10 = this.inverter;
                if (inverter10 != null && inverter10.isSnaSeries()) {
                    if (this.inverter.isEcoBeast6k()) {
                        numValueOf = Integer.valueOf(R.drawable.inverter_eco_beast_6000_mid);
                    } else if (this.inverter.isSna6kUsAio()) {
                        numValueOf = Integer.valueOf(R.drawable.inverter_us_aio);
                    } else if (this.inverter.isSna6kEneleageZero()) {
                        numValueOf = Integer.valueOf(R.drawable.icon_sna_3_6k_volta);
                    } else if (this.inverter.getSubDeviceTypeValue() == 131) {
                        numValueOf = Integer.valueOf(R.drawable.icon_dt_sna_us_6k_mid);
                    } else if (this.inverter.getSubDeviceTypeValue() == 1111) {
                        numValueOf = Integer.valueOf(R.drawable.icon_sna_12k_us_mid);
                    } else if (this.inverter.getSubDeviceTypeValue() == 1110) {
                        numValueOf = Integer.valueOf(R.drawable.icon_sna_12k_eu_mid);
                    } else {
                        numValueOf = Integer.valueOf(R.drawable.icon_dt_inverter_off_grid_mid);
                    }
                } else {
                    Inverter inverter11 = this.inverter;
                    if (inverter11 != null && inverter11.isType6Series()) {
                        if (this.inverter.getSubDeviceTypeValue() == 161) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_8_10k_eu_mid);
                        } else if (this.inverter.getSubDeviceTypeValue() == 162) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_12k_eu_mid);
                        } else if (this.inverter.getSubDeviceTypeValue() == 163) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_8_10k_us_mid);
                        } else if (this.inverter.getSubDeviceTypeValue() == 164) {
                            numValueOf = Integer.valueOf(R.drawable.icon_dt_type6_12k_us_mid);
                        }
                        if (this.inverter.getMachineType() == 1) {
                            numValueOf = Integer.valueOf(R.drawable.icon_inverter_minus_mid);
                        }
                    }
                }
            }
            Inverter inverter12 = this.inverter;
            if (inverter12 != null && inverter12.isAllInOne()) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_all_in_one_mid);
            }
            Inverter inverter13 = this.inverter;
            if (inverter13 != null && inverter13.isTrip12K()) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_trip_mid);
            }
            Inverter inverter14 = this.inverter;
            if (inverter14 != null && inverter14.is7_10KDevice()) {
                numValueOf = Integer.valueOf(R.drawable.icon_gen_7_10k_mid);
            }
            Inverter inverter15 = this.inverter;
            if (inverter15 != null && inverter15.isGen3_6K()) {
                numValueOf = Integer.valueOf(R.drawable.icon_gen_3_6k_mid);
            }
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(R.drawable.icon_dt_inverter_mid);
            }
        }
        this.flowInverterImageView.setBackgroundResource(numValueOf.intValue());
    }

    private void initFlowChartByDeviceExceptDeviceImg(boolean z) {
        if (z) {
            this.flowPvPowerImageView.setVisibility(4);
            this.flowPvPowerTextView.setVisibility(4);
            this.flowPvPowerLabelTextView.setVisibility(4);
            this.flowPvPowerGifView.setVisibility(4);
            this.flowAcPvPowerImageView.setVisibility(0);
            this.flowAcPvPowerTextView.setVisibility(0);
            this.flowAcPvPowerLabelTextView.setVisibility(0);
            return;
        }
        ImageView imageView = this.flowAcPvPowerImageView;
        if (imageView != null) {
            imageView.setVisibility(4);
        }
        TextView textView = this.flowAcPvPowerTextView;
        if (textView != null) {
            textView.setVisibility(4);
        }
        TextView textView2 = this.flowAcPvPowerLabelTextView;
        if (textView2 != null) {
            textView2.setVisibility(4);
        }
        GifView gifView = this.flowAcPvPowerGifView1;
        if (gifView != null) {
            gifView.setVisibility(4);
        }
        GifView gifView2 = this.flowAcPvPowerGifView2;
        if (gifView2 != null) {
            gifView2.setVisibility(4);
        }
        ImageView imageView2 = this.flowPvPowerImageView;
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        TextView textView3 = this.flowPvPowerTextView;
        if (textView3 != null) {
            textView3.setVisibility(0);
        }
        TextView textView4 = this.flowPvPowerLabelTextView;
        if (textView4 != null) {
            textView4.setVisibility(0);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        refreshFragmentParams();
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        UserData userData = GlobalInfo.getInstance().getUserData();
        Inverter currentParallelGroup = userData.getCurrentParallelGroup();
        if (currentParallelGroup == null) {
            currentParallelGroup = userData.getCurrentInverter();
        }
        if (currentParallelGroup != null && this.inverterList != null) {
            for (int i = 0; i < this.inverterList.size(); i++) {
                if (this.inverterList.get(i).getSerialNum().equals(currentParallelGroup.getSerialNum()) && ((inverter = this.inverter) == null || !inverter.getSerialNum().equals(currentParallelGroup.getSerialNum()))) {
                    this.inverterSpinner.setSelection(i);
                }
            }
        }
        initFlowChartByDeviceType();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0032  */
    /* renamed from: reloadFragmentData, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void m631x6c825f2a() {
        /*
            r8 = this;
            monitor-enter(r8)
            boolean r0 = r8.created     // Catch: java.lang.Throwable -> L4d
            if (r0 != 0) goto L7
            monitor-exit(r8)
            return
        L7:
            long r0 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L4d
            com.lux.luxcloud.global.bean.inverter.Inverter r2 = r8.inverter     // Catch: java.lang.Throwable -> L4d
            r3 = 1
            if (r2 != 0) goto L15
            com.lux.luxcloud.global.bean.inverter.Inverter r2 = r8.lastReloadInverter     // Catch: java.lang.Throwable -> L4d
            if (r2 == 0) goto L32
            goto L33
        L15:
            com.lux.luxcloud.global.bean.inverter.Inverter r4 = r8.lastReloadInverter     // Catch: java.lang.Throwable -> L4d
            if (r4 == 0) goto L33
            java.lang.String r2 = r2.getSerialNum()     // Catch: java.lang.Throwable -> L4d
            if (r2 == 0) goto L32
            com.lux.luxcloud.global.bean.inverter.Inverter r2 = r8.inverter     // Catch: java.lang.Throwable -> L4d
            java.lang.String r2 = r2.getSerialNum()     // Catch: java.lang.Throwable -> L4d
            com.lux.luxcloud.global.bean.inverter.Inverter r4 = r8.lastReloadInverter     // Catch: java.lang.Throwable -> L4d
            java.lang.String r4 = r4.getSerialNum()     // Catch: java.lang.Throwable -> L4d
            boolean r2 = r2.equals(r4)     // Catch: java.lang.Throwable -> L4d
            if (r2 != 0) goto L32
            goto L33
        L32:
            r3 = 0
        L33:
            long r4 = r8.lastReloadTime     // Catch: java.lang.Throwable -> L4d
            long r4 = r0 - r4
            r6 = 1000(0x3e8, double:4.94E-321)
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 > 0) goto L3f
            if (r3 == 0) goto L4b
        L3f:
            com.lux.luxcloud.global.bean.inverter.Inverter r2 = r8.inverter     // Catch: java.lang.Throwable -> L4d
            r8.lastReloadInverter = r2     // Catch: java.lang.Throwable -> L4d
            r8.lastReloadTime = r0     // Catch: java.lang.Throwable -> L4d
            r8.refreshEnergyInfo()     // Catch: java.lang.Throwable -> L4d
            r8.refreshFlowChart()     // Catch: java.lang.Throwable -> L4d
        L4b:
            monitor-exit(r8)
            return
        L4d:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.m631x6c825f2a():void");
    }

    private void refreshEnergyInfo() {
        if (this.inverter != null) {
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m634xb26ac4d9();
                }
            }).start();
        } else {
            clearEnergyInfo();
        }
    }

    /* renamed from: lambda$refreshEnergyInfo$7$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m634xb26ac4d9() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverter/getInverterEnergyInfo" + (this.inverter.isParallelGroup() ? "Parallel" : ""), map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f$0.m633xf7f52458(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* renamed from: lambda$refreshEnergyInfo$6$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m633xf7f52458(JSONObject jSONObject, FragmentActivity fragmentActivity) throws JSONException {
        try {
            int i = this.waitRequestCount;
            if (i > 0) {
                int i2 = i - 1;
                this.waitRequestCount = i2;
                if (i2 == 0) {
                    this.refreshButton.setEnabled(true);
                }
            }
            if (jSONObject == null || !jSONObject.getBoolean("success")) {
                try {
                    toastByResult(fragmentActivity, jSONObject);
                    return;
                } catch (JSONException e) {
                    e = e;
                    Toast.makeText(fragmentActivity.getApplicationContext(), R.string.phrase_toast_response_error, 1).show();
                    e.printStackTrace();
                    return;
                }
            }
            if (!jSONObject.getBoolean("hasRuntimeData")) {
                clearEnergyInfo();
                return;
            }
            this.todayYieldingTextView.setText(jSONObject.getString("todayYieldingText") + " kWh");
            this.totalYieldingTextView.setText(jSONObject.getString("totalYieldingText") + " kWh");
            this.todayDischargingTextView.setText(jSONObject.getString("todayDischargingText") + " kWh");
            this.totalDischargingTextView.setText(jSONObject.getString("totalDischargingText") + " kWh");
            this.todayChargingTextView.setText(jSONObject.getString("todayChargingText") + " kWh");
            this.totalChargingTextView.setText(jSONObject.getString("totalChargingText") + " kWh");
            this.todayExportTextView.setText(jSONObject.getString("todayExportText") + " kWh");
            this.totalExportTextView.setText(jSONObject.getString("totalExportText") + " kWh");
            this.todayImportTextView.setText(jSONObject.getString("todayImportText") + " kWh");
            this.totalImportTextView.setText(jSONObject.getString("totalImportText") + " kWh");
            this.todayUsageTextView.setText(jSONObject.getString("todayUsageText") + " kWh");
            this.totalUsageTextView.setText(jSONObject.getString("totalUsageText") + " kWh");
            this.pvLoadToday.setText((jSONObject.getInt("pvPieUsageTodayRate") / 10.0d) + "%");
            this.pvChargeToday.setText((jSONObject.getInt("pvPieChargeTodayRate") / 10.0d) + "%");
            this.pvExportToday.setText((jSONObject.getInt("pvPieExportTodayRate") / 10.0d) + "%");
            this.todayAllPvEnergy.setText(jSONObject.getString("todayYieldingText") + " kWh");
            this.pvLoadTotal.setText((jSONObject.getInt("pvPieUsageTotalRate") / 10.0d) + "%");
            this.pvChargeTotal.setText((jSONObject.getInt("pvPieChargeTotalRate") / 10.0d) + "%");
            this.pvExportTotal.setText((jSONObject.getInt("pvPieExportTotalRate") / 10.0d) + "%");
            this.totalAllPvEnergy.setText(jSONObject.getString("totalYieldingText") + " kWh");
            int i3 = jSONObject.getInt("pvPieUsageTodayRate");
            int i4 = jSONObject.getInt("pvPieChargeTodayRate");
            int i5 = jSONObject.getInt("pvPieExportTodayRate");
            if (i3 == 0 && i4 == 0 && i5 == 0) {
                this.shouldShowTodayCircleView = true;
                if (this.currentPvEnergyViewIndex == 2) {
                    this.todayCircleView.setVisibility(0);
                }
            } else {
                this.shouldShowTodayCircleView = false;
                this.todayCircleView.setVisibility(8);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new PieEntry((float) (i3 / 10.0d)));
            arrayList.add(new PieEntry((float) (i4 / 10.0d)));
            arrayList.add(new PieEntry((float) (i5 / 10.0d)));
            PieDataSet pieDataSet = this.todayPvPieDataSet;
            if (pieDataSet == null) {
                PieDataSet pieDataSet2 = new PieDataSet(arrayList, "Today Pv Pie");
                this.todayPvPieDataSet = pieDataSet2;
                pieDataSet2.setColors(pvPieColors);
            } else {
                pieDataSet.setValues(arrayList);
            }
            this.todayPvPieChart.setData(new PieData(this.todayPvPieDataSet));
            this.todayPvPieChart.invalidate();
            int i6 = jSONObject.getInt("pvPieUsageTotalRate");
            int i7 = jSONObject.getInt("pvPieChargeTotalRate");
            int i8 = jSONObject.getInt("pvPieExportTotalRate");
            if (i6 == 0 && i7 == 0 && i8 == 0) {
                this.shouldShowTotalCircleView = true;
                if (this.currentPvEnergyViewIndex == 3) {
                    this.totalCircleView.setVisibility(0);
                }
            } else {
                this.shouldShowTotalCircleView = false;
                this.totalCircleView.setVisibility(8);
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new PieEntry((float) (i6 / 10.0d)));
            arrayList2.add(new PieEntry((float) (i7 / 10.0d)));
            arrayList2.add(new PieEntry((float) (i8 / 10.0d)));
            PieDataSet pieDataSet3 = this.totalPvPieDataSet;
            if (pieDataSet3 == null) {
                PieDataSet pieDataSet4 = new PieDataSet(arrayList2, "Total Pv Pie");
                this.totalPvPieDataSet = pieDataSet4;
                pieDataSet4.setColors(pvPieColors);
            } else {
                pieDataSet3.setValues(arrayList2);
            }
            this.totalPvPieChart.setData(new PieData(this.totalPvPieDataSet));
            this.totalPvPieChart.invalidate();
        } catch (JSONException e2) {
            e = e2;
        }
    }

    private void clearEnergyInfo() {
        this.todayYieldingTextView.setText("-- kWh");
        this.totalYieldingTextView.setText("-- kWh");
        this.todayDischargingTextView.setText("-- kWh");
        this.totalDischargingTextView.setText("-- kWh");
        this.todayChargingTextView.setText("-- kWh");
        this.totalChargingTextView.setText("-- kWh");
        this.todayExportTextView.setText("-- kWh");
        this.totalExportTextView.setText("-- kWh");
        this.todayImportTextView.setText("-- kWh");
        this.totalImportTextView.setText("-- kWh");
        this.todayUsageTextView.setText("-- kWh");
        this.totalUsageTextView.setText("-- kWh");
        this.pvLoadToday.setText("--%");
        this.pvChargeToday.setText("--%");
        this.pvExportToday.setText("--%");
        this.todayAllPvEnergy.setText("-- kWh");
        this.todayPvPieChart.clear();
        this.pvLoadTotal.setText("--%");
        this.pvChargeTotal.setText("--%");
        this.pvExportTotal.setText("--%");
        this.totalAllPvEnergy.setText("-- kWh");
        this.totalPvPieChart.clear();
        this.shouldShowTodayCircleView = true;
        this.shouldShowTotalCircleView = true;
        int i = this.currentPvEnergyViewIndex;
        if (i == 2) {
            this.todayCircleView.setVisibility(0);
            this.totalCircleView.setVisibility(8);
        } else if (i == 3) {
            this.todayCircleView.setVisibility(8);
            this.totalCircleView.setVisibility(0);
        } else {
            this.todayCircleView.setVisibility(8);
            this.totalCircleView.setVisibility(8);
        }
    }

    private void refreshFlowChart() {
        if (this.inverter != null) {
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.getCurrentPlant() != null && !userData.getCurrentPlant().getParallelGroups().isEmpty()) {
                refreshDeviceImageSource();
                if (this.inverter.isParallelGroup()) {
                    this.flowBatVoltTextView.setVisibility(8);
                } else {
                    this.flowBatVoltTextView.setVisibility(0);
                }
            }
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m635xb44af1d8();
                }
            }).start();
            return;
        }
        clearFlowChart();
    }

    /* renamed from: lambda$refreshFlowChart$8$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m635xb44af1d8() {
        if (this.inverter.isParallelGroup()) {
            refreshFlowChartForParallelAtThread();
            if (this.inverter.isMidBox()) {
                refreshFlowChartForMidBoxParallelAtThread();
                return;
            }
            return;
        }
        refreshFlowChartForSingleAtThread();
    }

    private void refreshFlowChartForMidBoxParallelAtThread() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.getParallelFirstDeviceSn());
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/midbox/getMidboxRuntime", map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m636xb240b185(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* renamed from: lambda$refreshFlowChartForMidBoxParallelAtThread$9$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m636xb240b185(JSONObject jSONObject, FragmentActivity fragmentActivity) {
        try {
            try {
                int i = this.waitRequestCount;
                if (i > 0) {
                    int i2 = i - 1;
                    this.waitRequestCount = i2;
                    if (i2 == 0) {
                        this.refreshButton.setEnabled(true);
                    }
                }
                if (jSONObject == null || !jSONObject.getBoolean("success")) {
                    toastByResult(fragmentActivity, jSONObject);
                } else if (!jSONObject.getBoolean("lost") && jSONObject.getBoolean("hasRuntimeData")) {
                    if (!jSONObject.has("hasUnclosedQuickChargeTask")) {
                        this.quickGridChargeButton.setVisibility(4);
                        this.stopQuickGridChargeButton.setVisibility(4);
                    } else if (jSONObject.getBoolean("hasUnclosedQuickChargeTask")) {
                        this.quickGridChargeButton.setVisibility(4);
                        this.stopQuickGridChargeButton.setVisibility(0);
                    } else {
                        this.stopQuickGridChargeButton.setVisibility(4);
                        this.quickGridChargeButton.setVisibility(0);
                    }
                    if (!jSONObject.has("hasUnclosedQuickDischargeTask") || !this.inverter.checkSupportQuickDisCharge()) {
                        this.newButton3Layout.setVisibility(8);
                        this.quickDisChargeButton.setVisibility(4);
                        this.stopQuickDisChargeButton.setVisibility(4);
                    } else if (jSONObject.getBoolean("hasUnclosedQuickDischargeTask")) {
                        this.newButton3Layout.setVisibility(0);
                        this.quickDisChargeButton.setVisibility(4);
                        this.stopQuickDisChargeButton.setVisibility(0);
                    } else {
                        this.stopQuickDisChargeButton.setVisibility(4);
                        this.quickDisChargeButton.setVisibility(0);
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(fragmentActivity.getApplicationContext(), R.string.phrase_toast_response_error, 1).show();
                e.printStackTrace();
            }
        } finally {
            adJustHideButton();
        }
    }

    private void refreshFlowChartForParallelAtThread() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.getParallelFirstDeviceSn());
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverter/getInverterRuntimeParallel", map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m637xfeffa15a(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:267:0x08b4 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0b81  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0b8a A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0bc3 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0c10 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0c50 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0cbf A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0cdd A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:403:0x0ce8 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TRY_LEAVE, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01f7 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TRY_ENTER, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x020b A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0213 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x025d A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x02af A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0340 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0353 A[Catch: all -> 0x0cf4, JSONException -> 0x0cf7, TRY_LEAVE, TryCatch #1 {all -> 0x0cf4, blocks: (B:3:0x0017, B:5:0x001b, B:7:0x0020, B:8:0x0025, B:10:0x007a, B:12:0x0082, B:14:0x0099, B:16:0x00a6, B:18:0x00b7, B:20:0x00cf, B:22:0x00d7, B:24:0x00ef, B:27:0x00fd, B:29:0x0105, B:31:0x010f, B:32:0x0120, B:34:0x0128, B:35:0x0138, B:37:0x0140, B:39:0x0156, B:41:0x015c, B:47:0x01a7, B:50:0x01f7, B:52:0x01ff, B:109:0x048f, B:111:0x0497, B:113:0x049f, B:115:0x04a7, B:116:0x04b2, B:118:0x04ba, B:120:0x04c2, B:122:0x04ca, B:124:0x04d5, B:126:0x04db, B:128:0x04e3, B:130:0x04ed, B:146:0x0569, B:148:0x056f, B:150:0x057a, B:154:0x05ab, B:158:0x05bb, B:171:0x0641, B:173:0x0649, B:175:0x0651, B:178:0x0683, B:212:0x0750, B:215:0x075a, B:217:0x077b, B:249:0x085b, B:251:0x0863, B:253:0x086b, B:256:0x087b, B:258:0x0889, B:261:0x0893, B:267:0x08b4, B:272:0x08c2, B:276:0x08cd, B:279:0x0915, B:281:0x0922, B:283:0x093f, B:285:0x0962, B:328:0x0b83, B:330:0x0b8a, B:332:0x0b8e, B:339:0x0b9e, B:341:0x0ba5, B:348:0x0bb5, B:361:0x0bfe, B:363:0x0c10, B:367:0x0c1d, B:371:0x0c2d, B:375:0x0c3d, B:392:0x0ca7, B:394:0x0caf, B:396:0x0cb7, B:398:0x0cbf, B:400:0x0cc7, B:401:0x0cd2, B:402:0x0cdd, B:376:0x0c50, B:378:0x0c58, B:382:0x0c65, B:386:0x0c75, B:390:0x0c85, B:391:0x0c98, B:344:0x0baa, B:335:0x0b93, B:349:0x0bc3, B:351:0x0bc9, B:355:0x0bd6, B:359:0x0be6, B:360:0x0bf4, B:286:0x0968, B:287:0x096e, B:290:0x0984, B:292:0x09a1, B:293:0x09ac, B:280:0x091c, B:295:0x09b9, B:297:0x09c0, B:300:0x09ca, B:301:0x0a2a, B:303:0x0a8e, B:305:0x0aeb, B:307:0x0af0, B:310:0x0af6, B:314:0x0b05, B:317:0x0b0e, B:320:0x0b2d, B:323:0x0b4c, B:325:0x0b6b, B:326:0x0b76, B:311:0x0afc, B:302:0x0a2d, B:264:0x08a7, B:218:0x0787, B:219:0x0793, B:221:0x079b, B:223:0x07a3, B:225:0x07ab, B:229:0x07c0, B:230:0x07ca, B:232:0x07d2, B:234:0x07da, B:236:0x07e2, B:240:0x0813, B:241:0x0822, B:243:0x082d, B:245:0x0833, B:247:0x0850, B:248:0x0856, B:239:0x07ec, B:228:0x07b5, B:180:0x068f, B:182:0x069b, B:184:0x06a7, B:186:0x06b3, B:187:0x06bd, B:188:0x06c7, B:191:0x06d1, B:193:0x06dd, B:195:0x06e9, B:197:0x06f4, B:199:0x06ff, B:200:0x0708, B:202:0x0713, B:204:0x071e, B:206:0x0729, B:208:0x0734, B:210:0x073f, B:211:0x0748, B:159:0x05ca, B:161:0x05d5, B:165:0x060a, B:169:0x061a, B:170:0x0628, B:131:0x04fe, B:133:0x0507, B:134:0x0517, B:136:0x051f, B:138:0x0537, B:140:0x053d, B:141:0x055b, B:143:0x055f, B:144:0x0562, B:403:0x0ce8, B:56:0x020b, B:58:0x0213, B:60:0x021f, B:63:0x0255, B:65:0x025d, B:67:0x0269, B:68:0x029c, B:69:0x02a1, B:71:0x02af, B:73:0x02b7, B:75:0x02bf, B:77:0x02f7, B:79:0x02ff, B:81:0x0307, B:88:0x0353, B:91:0x035e, B:93:0x0366, B:95:0x03c9, B:97:0x03d1, B:98:0x03db, B:99:0x03e0, B:101:0x03e7, B:103:0x0401, B:105:0x047b, B:107:0x0483, B:108:0x048c, B:82:0x033a, B:76:0x02f2, B:83:0x0340, B:85:0x0348, B:86:0x034e, B:61:0x024d, B:42:0x0188, B:44:0x0195, B:45:0x0198, B:23:0x00ea, B:19:0x00ca, B:406:0x0cee, B:415:0x0cfb), top: B:422:0x0017 }] */
    /* JADX WARN: Type inference failed for: r10v37, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$16] */
    /* JADX WARN: Type inference failed for: r21v1, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$15] */
    /* renamed from: lambda$refreshFlowChartForParallelAtThread$10$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    /* synthetic */ void m637xfeffa15a(org.json.JSONObject r28, androidx.fragment.app.FragmentActivity r29) {
        /*
            Method dump skipped, instructions count: 3349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.m637xfeffa15a(org.json.JSONObject, androidx.fragment.app.FragmentActivity):void");
    }

    private void refreshFlowChartForSingleAtThread() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.getSerialNum());
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverter/getInverterRuntime", map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m638x4fd1e6ba(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x03b4 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:101:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x03be A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0439 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0596  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x05dc A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x066d A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x06aa A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x077b A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0782  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x07af A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x07b7 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x082f A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:231:0x08fe A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0946 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x09a9 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:281:0x09ea A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0a4a  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0a68 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0a74 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0a86 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:320:0x0afa A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0b41 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0b61 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0b73 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0ba0 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0bb0 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TRY_LEAVE, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0265 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0286 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x028e A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02cc A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0316 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0393 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x039a A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x03a0 A[Catch: all -> 0x0bbc, JSONException -> 0x0bbf, TryCatch #1 {all -> 0x0bbc, blocks: (B:3:0x002c, B:5:0x0062, B:7:0x0068, B:8:0x006d, B:10:0x007b, B:12:0x0083, B:14:0x008a, B:16:0x0090, B:18:0x0098, B:19:0x009e, B:21:0x00a5, B:23:0x00c0, B:25:0x00c8, B:27:0x00e5, B:29:0x00ee, B:31:0x00f6, B:33:0x0102, B:34:0x0113, B:36:0x011b, B:37:0x012e, B:39:0x0136, B:41:0x014c, B:43:0x0152, B:51:0x01f3, B:55:0x0267, B:57:0x026f, B:59:0x0277, B:61:0x027f, B:109:0x043c, B:111:0x0444, B:113:0x044c, B:115:0x0475, B:119:0x04a6, B:123:0x04b6, B:137:0x053e, B:140:0x0598, B:174:0x0665, B:176:0x066d, B:178:0x0690, B:207:0x0773, B:209:0x077b, B:211:0x0783, B:214:0x079a, B:218:0x07d3, B:222:0x07e3, B:229:0x08dd, B:231:0x08fe, B:235:0x0909, B:237:0x0910, B:244:0x0920, B:246:0x0927, B:253:0x0937, B:266:0x098f, B:268:0x09a9, B:272:0x09b6, B:276:0x09c6, B:280:0x09d6, B:297:0x0a42, B:300:0x0a4b, B:302:0x0a68, B:304:0x0a7e, B:306:0x0a86, B:308:0x0a9f, B:310:0x0aa4, B:315:0x0aad, B:319:0x0ab4, B:328:0x0b39, B:330:0x0b41, B:332:0x0b49, B:335:0x0b6b, B:337:0x0b73, B:339:0x0b7b, B:341:0x0b83, B:342:0x0b94, B:343:0x0ba0, B:333:0x0b55, B:334:0x0b61, B:320:0x0afa, B:322:0x0b00, B:326:0x0b28, B:327:0x0b2c, B:303:0x0a74, B:281:0x09ea, B:283:0x09f2, B:287:0x09ff, B:291:0x0a0f, B:295:0x0a1f, B:296:0x0a33, B:249:0x092c, B:240:0x0915, B:254:0x0946, B:256:0x094c, B:260:0x0961, B:264:0x0971, B:265:0x0980, B:223:0x082f, B:226:0x0839, B:227:0x0892, B:215:0x07af, B:217:0x07b7, B:179:0x069b, B:181:0x06aa, B:183:0x06b2, B:185:0x06ba, B:187:0x06c2, B:191:0x06d8, B:192:0x06e2, B:194:0x06ea, B:196:0x06f2, B:198:0x06fa, B:202:0x072e, B:203:0x073f, B:205:0x0767, B:206:0x076e, B:201:0x0704, B:190:0x06cc, B:142:0x05a4, B:144:0x05b0, B:146:0x05bc, B:148:0x05c8, B:149:0x05d2, B:150:0x05dc, B:153:0x05e6, B:155:0x05f2, B:157:0x05fe, B:159:0x0609, B:161:0x0614, B:162:0x061d, B:164:0x0628, B:166:0x0633, B:168:0x063e, B:170:0x0649, B:172:0x0654, B:173:0x065d, B:125:0x04c7, B:127:0x04d4, B:131:0x0505, B:135:0x0515, B:136:0x0524, B:344:0x0bb0, B:62:0x0286, B:64:0x028e, B:66:0x029a, B:67:0x02bf, B:68:0x02c4, B:70:0x02cc, B:72:0x02d8, B:73:0x0303, B:74:0x0308, B:76:0x0316, B:78:0x031e, B:80:0x0326, B:84:0x0354, B:86:0x035c, B:88:0x0364, B:92:0x0393, B:98:0x03ac, B:100:0x03b4, B:103:0x03be, B:105:0x0428, B:107:0x0430, B:108:0x0439, B:93:0x039a, B:89:0x0386, B:90:0x038c, B:81:0x0348, B:82:0x034e, B:94:0x03a0, B:97:0x03a9, B:44:0x0196, B:46:0x01b8, B:47:0x01bb, B:26:0x00e0, B:22:0x00bb, B:347:0x0bb6, B:356:0x0bc3), top: B:363:0x002c }] */
    /* JADX WARN: Type inference failed for: r26v0, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$17] */
    /* renamed from: lambda$refreshFlowChartForSingleAtThread$11$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    /* synthetic */ void m638x4fd1e6ba(org.json.JSONObject r32, androidx.fragment.app.FragmentActivity r33) {
        /*
            Method dump skipped, instructions count: 3037
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.m638x4fd1e6ba(org.json.JSONObject, androidx.fragment.app.FragmentActivity):void");
    }

    public void adJustHideButton() {
        boolean z = this.quickGridChargeButton.getVisibility() == 4;
        boolean z2 = this.stopQuickGridChargeButton.getVisibility() == 4;
        boolean z3 = this.genExerciseStartButton.getVisibility() == 4;
        boolean z4 = this.stopGenExerciseStartButton.getVisibility() == 4;
        if (z && z2 && z3 && z4) {
            this.newButtonLayout.setVisibility(8);
        } else {
            this.newButtonLayout.setVisibility(0);
        }
        boolean z5 = this.quickDisChargeButton.getVisibility() == 4;
        boolean z6 = this.stopQuickDisChargeButton.getVisibility() == 4;
        boolean z7 = this.midboxRuntimeButton.getVisibility() == 4;
        boolean z8 = this.midboxRuntimeButton2.getVisibility() == 4;
        if (z5 && z6 && z7 && z8) {
            this.newButton3Layout.setVisibility(8);
        } else {
            this.newButton3Layout.setVisibility(0);
        }
    }

    private int getStatusResourceId(String str) {
        if (Tool.isEmpty(str)) {
            return R.drawable.status_offline;
        }
        str.hashCode();
        switch (str) {
            case "normal":
                return R.drawable.status_normal;
            case "error":
                return R.drawable.status_error;
            case "warning":
                return R.drawable.status_warning;
            default:
                return R.drawable.status_offline;
        }
    }

    private void clearFlowChart() {
        this.statusImageView.setImageResource(R.drawable.status_offline);
        this.flowBatteryPowerLabelTextView.setText(R.string.main_flow_battery_power);
        this.flowBatteryPowerTextView.setText("");
        this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_0_green);
        this.flowSocPowerTextView.setText("");
        this.flowBatVoltTextView.setText("");
        this.flowPvPowerTextView.setText("");
        this.flowAcPvPowerTextView.setText("");
        this.flowGridPowerTextView.setText("");
        this.flowGridPowerLabelTextView.setText("");
        this.flowConsumptionPowerTextView.setText("");
        this.flowEpsPowerTextView.setText("");
        this.flowVacTextView.setText("");
        this.flowFacTextView.setText("");
        this.gridPowerValue1TextView.setText("");
        this.epsValue1TextView.setText("");
        this.epsValue2TextView.setText("");
        this.consumptionPowerValue1TextView.setText("");
        this.consumptionPowerValue2TextView.setText("");
        this.localTimeTextView.setText("");
        this.flowBatteryPowerGifView1.setVisibility(4);
        this.flowBatteryPowerGifView2.setVisibility(4);
        this.flowPvPowerGifView.setVisibility(4);
        this.flowAcPvPowerGifView1.setVisibility(4);
        this.flowAcPvPowerGifView2.setVisibility(4);
        this.flowInverterArrowGifView1.setVisibility(4);
        this.flowInverterArrowGifView2.setVisibility(4);
        this.flowInverterArrowGifView3.setVisibility(4);
        this.flowGridPowerGifView1.setVisibility(4);
        this.flowGridPowerGifView2.setVisibility(4);
        this.flowConsumptionPowerGifView1.setVisibility(4);
        this.flowConsumptionPowerGifView2.setVisibility(4);
        this.flowEpsPowerGifView.setVisibility(4);
        this.quickGridChargeButton.setVisibility(4);
        this.stopQuickGridChargeButton.setVisibility(4);
        this.genExerciseStartButton.setVisibility(4);
        this.stopGenExerciseStartButton.setVisibility(4);
    }

    private void clearBattInfo() {
        Button button = this.battParallelNumButton;
        if (button != null) {
            button.setVisibility(8);
            this.battParallelNumButton.setText("");
        }
        Button button2 = this.battCapacityButton;
        if (button2 != null) {
            button2.setVisibility(8);
            this.battCapacityButton.setText("");
        }
        Button button3 = this.bmsLimitChargeButton;
        if (button3 != null) {
            button3.setVisibility(8);
            this.bmsLimitChargeButton.setText("");
        }
        Button button4 = this.bmsLimitDischargeButton;
        if (button4 != null) {
            button4.setVisibility(8);
            this.bmsLimitDischargeButton.setText("");
        }
        this.batteryParamImageView.setVisibility(8);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.centered2Layout.getLayoutParams();
        layoutParams.bottomMargin = 8;
        this.centered2Layout.setLayoutParams(layoutParams);
        this.centered2Layout.setVisibility(8);
    }

    private void clearEpsLnInfo() {
        Button button = this.epsL1nButton;
        if (button != null) {
            button.setText("");
        }
        Button button2 = this.epsL2nButton;
        if (button2 != null) {
            button2.setText("");
        }
        ConstraintLayout constraintLayout = this.epsLayout;
        if (constraintLayout != null) {
            constraintLayout.setVisibility(8);
        }
        this.epsParamImageView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class QuickChargeCtrlTask extends AsyncTask<Boolean, Object, JSONObject> {
        private Button ctrlButton;
        private Lv1OverviewFragment fragment;

        public QuickChargeCtrlTask(Lv1OverviewFragment lv1OverviewFragment, Button button) {
            this.fragment = lv1OverviewFragment;
            this.ctrlButton = button;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Boolean[] boolArr) {
            try {
                String str = boolArr[0].booleanValue() ? "start" : "stop";
                HashMap map = new HashMap();
                if (this.fragment.inverter.isParallelGroup()) {
                    map.put("inverterSn", this.fragment.inverter.getParallelFirstDeviceSn());
                } else {
                    map.put("inverterSn", this.fragment.inverter.getSerialNum());
                }
                map.put("clientType", "APP");
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/quickCharge/" + str, map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0 */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1, java.lang.Runnable] */
        /* JADX WARN: Type inference failed for: r0v9 */
        /* JADX WARN: Type inference failed for: r1v0 */
        /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
        /* JADX WARN: Type inference failed for: r1v6, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment] */
        /* JADX WARN: Type inference failed for: r1v7 */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            Thread thread;
            super.onPostExecute((QuickChargeCtrlTask) jSONObject);
            int i = 0;
            i = 0;
            boolean z = 1;
            z = 1;
            try {
                try {
                    if (jSONObject != null) {
                        if (!jSONObject.getBoolean("success")) {
                            Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_network_error);
                    }
                    this.ctrlButton.setVisibility(8);
                    this.ctrlButton.setEnabled(true);
                    z = this.fragment;
                    new ReadQuickChargeTask(z).execute(new Void[0]);
                    i = new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m642x8814ad45();
                        }
                    };
                    thread = new Thread((Runnable) i);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.ctrlButton.setVisibility(8);
                    this.ctrlButton.setEnabled(z);
                    new ReadQuickChargeTask(this.fragment).execute(new Void[i]);
                    thread = new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m642x8814ad45();
                        }
                    });
                }
                thread.start();
            } catch (Throwable th) {
                this.ctrlButton.setVisibility(8);
                this.ctrlButton.setEnabled(z);
                new ReadQuickChargeTask(this.fragment).execute(new Void[i]);
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws InterruptedException {
                        this.f$0.m642x8814ad45();
                    }
                }).start();
                throw th;
            }
        }

        /* renamed from: lambda$onPostExecute$1$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment$QuickChargeCtrlTask, reason: not valid java name */
        /* synthetic */ void m642x8814ad45() throws InterruptedException {
            Tool.sleep(DeviceOrientationRequest.OUTPUT_PERIOD_MEDIUM);
            FragmentActivity activity = this.fragment.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m641xa79b5744();
                    }
                });
            }
        }

        /* renamed from: lambda$onPostExecute$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment$QuickChargeCtrlTask, reason: not valid java name */
        /* synthetic */ void m641xa79b5744() {
            this.fragment.refreshData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class QuickDisChargeCtrlTask extends AsyncTask<Boolean, Object, JSONObject> {
        private Button ctrlButton;
        private Lv1OverviewFragment fragment;

        public QuickDisChargeCtrlTask(Lv1OverviewFragment lv1OverviewFragment, Button button) {
            this.fragment = lv1OverviewFragment;
            this.ctrlButton = button;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Boolean[] boolArr) {
            try {
                String str = boolArr[0].booleanValue() ? "start" : "stop";
                HashMap map = new HashMap();
                if (this.fragment.inverter.isParallelGroup()) {
                    map.put("inverterSn", this.fragment.inverter.getParallelFirstDeviceSn());
                } else {
                    map.put("inverterSn", this.fragment.inverter.getSerialNum());
                }
                map.put("clientType", "APP");
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/quickDischarge/" + str, map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0 */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickDisChargeCtrlTask$$ExternalSyntheticLambda0, java.lang.Runnable] */
        /* JADX WARN: Type inference failed for: r0v9 */
        /* JADX WARN: Type inference failed for: r1v0 */
        /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
        /* JADX WARN: Type inference failed for: r1v6, types: [com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment] */
        /* JADX WARN: Type inference failed for: r1v7 */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            Thread thread;
            super.onPostExecute((QuickDisChargeCtrlTask) jSONObject);
            int i = 0;
            i = 0;
            boolean z = 1;
            z = 1;
            try {
                try {
                    if (jSONObject != null) {
                        if (!jSONObject.getBoolean("success")) {
                            Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_network_error);
                    }
                    this.ctrlButton.setVisibility(8);
                    this.ctrlButton.setEnabled(true);
                    z = this.fragment;
                    new ReadQuickDisChargeTask(z).execute(new Void[0]);
                    i = new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickDisChargeCtrlTask$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m644x36f39e11();
                        }
                    };
                    thread = new Thread((Runnable) i);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.ctrlButton.setVisibility(8);
                    this.ctrlButton.setEnabled(z);
                    new ReadQuickDisChargeTask(this.fragment).execute(new Void[i]);
                    thread = new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickDisChargeCtrlTask$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m644x36f39e11();
                        }
                    });
                }
                thread.start();
            } catch (Throwable th) {
                this.ctrlButton.setVisibility(8);
                this.ctrlButton.setEnabled(z);
                new ReadQuickDisChargeTask(this.fragment).execute(new Void[i]);
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickDisChargeCtrlTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws InterruptedException {
                        this.f$0.m644x36f39e11();
                    }
                }).start();
                throw th;
            }
        }

        /* renamed from: lambda$onPostExecute$1$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment$QuickDisChargeCtrlTask, reason: not valid java name */
        /* synthetic */ void m644x36f39e11() throws InterruptedException {
            Tool.sleep(DeviceOrientationRequest.OUTPUT_PERIOD_MEDIUM);
            FragmentActivity activity = this.fragment.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$QuickDisChargeCtrlTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m643xeef43fb2();
                    }
                });
            }
        }

        /* renamed from: lambda$onPostExecute$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment$QuickDisChargeCtrlTask, reason: not valid java name */
        /* synthetic */ void m643xeef43fb2() {
            this.fragment.refreshData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GenExerciseCtrlTask extends AsyncTask<Boolean, Object, JSONObject> {
        private Button ctrlButton;
        private Lv1OverviewFragment fragment;

        public GenExerciseCtrlTask(Lv1OverviewFragment lv1OverviewFragment, Button button) {
            this.fragment = lv1OverviewFragment;
            this.ctrlButton = button;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Boolean[] boolArr) {
            try {
                boolean zBooleanValue = boolArr[0].booleanValue();
                HashMap map = new HashMap();
                map.put("inverterSn", this.fragment.inverter.isParallelGroup() ? this.fragment.inverter.getParallelFirstDeviceSn() : this.fragment.inverter.getSerialNum());
                map.put("enable", String.valueOf(zBooleanValue));
                map.put("clientType", "APP");
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverter/ctrlGenExercise", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(JSONObject jSONObject) {
            Thread thread;
            super.onPostExecute((GenExerciseCtrlTask) jSONObject);
            try {
                try {
                    if (jSONObject != null) {
                        if (jSONObject.getBoolean("success")) {
                            Tool.alert(this.fragment.getActivity(), R.string.page_maintain_remote_set_result_success);
                        } else {
                            Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_network_error);
                    }
                    thread = new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m640x8209cc8c();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    thread = new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m640x8209cc8c();
                        }
                    });
                }
                thread.start();
            } catch (Throwable th) {
                new Thread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws InterruptedException {
                        this.f$0.m640x8209cc8c();
                    }
                }).start();
                throw th;
            }
        }

        /* renamed from: lambda$onPostExecute$1$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment$GenExerciseCtrlTask, reason: not valid java name */
        /* synthetic */ void m640x8209cc8c() throws InterruptedException {
            Tool.sleep(3000L);
            FragmentActivity activity = this.fragment.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m639xa190768b();
                    }
                });
            }
        }

        /* renamed from: lambda$onPostExecute$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1OverviewFragment$GenExerciseCtrlTask, reason: not valid java name */
        /* synthetic */ void m639xa190768b() {
            this.fragment.refreshData();
        }
    }

    private static class ReadQuickChargeTask extends AsyncTask<Void, Object, JSONObject> {
        private Lv1OverviewFragment fragment;

        public ReadQuickChargeTask(Lv1OverviewFragment lv1OverviewFragment) {
            this.fragment = lv1OverviewFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Void[] voidArr) {
            try {
                HashMap map = new HashMap();
                if (this.fragment.inverter.isParallelGroup()) {
                    map.put("inverterSn", this.fragment.inverter.getParallelFirstDeviceSn());
                } else {
                    map.put("inverterSn", this.fragment.inverter.getSerialNum());
                }
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "/web/config/quickCharge/getStatusInfo", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r5) {
            /*
                r4 = this;
                java.lang.String r0 = "hasUnclosedQuickChargeTask"
                super.onPostExecute(r5)
                r1 = 4
                if (r5 == 0) goto L4e
                java.lang.String r2 = "success"
                boolean r2 = r5.getBoolean(r2)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
                if (r2 == 0) goto L4e
                boolean r2 = r5.has(r0)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
                if (r2 == 0) goto L4e
                boolean r5 = r5.getBoolean(r0)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
                r0 = 0
                if (r5 == 0) goto L1e
                goto L4f
            L1e:
                r3 = r1
                r1 = r0
                r0 = r3
                goto L4f
            L22:
                r5 = move-exception
                goto L3b
            L24:
                r5 = move-exception
                r5.printStackTrace()     // Catch: java.lang.Throwable -> L22
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2400(r5)
                r5.setVisibility(r1)
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2500(r5)
                r5.setVisibility(r1)
                goto L61
            L3b:
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r0 = r4.fragment
                android.widget.Button r0 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2400(r0)
                r0.setVisibility(r1)
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r0 = r4.fragment
                android.widget.Button r0 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2500(r0)
                r0.setVisibility(r1)
                throw r5
            L4e:
                r0 = r1
            L4f:
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2400(r5)
                r5.setVisibility(r1)
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2500(r5)
                r5.setVisibility(r0)
            L61:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.ReadQuickChargeTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    private static class ReadQuickDisChargeTask extends AsyncTask<Void, Object, JSONObject> {
        private Lv1OverviewFragment fragment;

        public ReadQuickDisChargeTask(Lv1OverviewFragment lv1OverviewFragment) {
            this.fragment = lv1OverviewFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(Void[] voidArr) {
            try {
                HashMap map = new HashMap();
                if (this.fragment.inverter.isParallelGroup()) {
                    map.put("inverterSn", this.fragment.inverter.getParallelFirstDeviceSn());
                } else {
                    map.put("inverterSn", this.fragment.inverter.getSerialNum());
                }
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "/web/config/quickCharge/getStatusInfo", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:22:0x005a  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r5) {
            /*
                r4 = this;
                java.lang.String r0 = "hasUnclosedQuickDischargeTask"
                super.onPostExecute(r5)
                r1 = 4
                if (r5 == 0) goto L5a
                java.lang.String r2 = "success"
                boolean r2 = r5.getBoolean(r2)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
                if (r2 == 0) goto L5a
                boolean r2 = r5.has(r0)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
                if (r2 == 0) goto L5a
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r2 = r4.fragment     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
                com.lux.luxcloud.global.bean.inverter.Inverter r2 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$100(r2)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
                boolean r2 = r2.checkSupportQuickDisCharge()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
                if (r2 == 0) goto L5a
                boolean r5 = r5.getBoolean(r0)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
                r0 = 0
                if (r5 == 0) goto L2a
                goto L5b
            L2a:
                r3 = r1
                r1 = r0
                r0 = r3
                goto L5b
            L2e:
                r5 = move-exception
                goto L47
            L30:
                r5 = move-exception
                r5.printStackTrace()     // Catch: java.lang.Throwable -> L2e
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2600(r5)
                r5.setVisibility(r1)
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2700(r5)
                r5.setVisibility(r1)
                goto L6d
            L47:
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r0 = r4.fragment
                android.widget.Button r0 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2600(r0)
                r0.setVisibility(r1)
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r0 = r4.fragment
                android.widget.Button r0 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2700(r0)
                r0.setVisibility(r1)
                throw r5
            L5a:
                r0 = r1
            L5b:
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2600(r5)
                r5.setVisibility(r1)
                com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.access$2700(r5)
                r5.setVisibility(r0)
            L6d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1OverviewFragment.ReadQuickDisChargeTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    private void toastByResult(FragmentActivity fragmentActivity, JSONObject jSONObject) throws JSONException {
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

    private SpannableStringBuilder buildSpannableString(int i, String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(1.4f), 0, str.length(), 33);
        spannableStringBuilder.append((CharSequence) "\n ");
        spannableStringBuilder.append((CharSequence) getString(i));
        spannableStringBuilder.setSpan(new RelativeSizeSpan(0.8f), spannableStringBuilder.length() - getString(i).length(), spannableStringBuilder.length(), 33);
        return spannableStringBuilder;
    }
}