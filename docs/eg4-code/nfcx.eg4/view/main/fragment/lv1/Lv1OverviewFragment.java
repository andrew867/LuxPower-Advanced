package com.nfcx.eg4.view.main.fragment.lv1;

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
import com.google.firebase.messaging.Constants;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import com.nfcx.eg4.global.custom.view.CircleView;
import com.nfcx.eg4.global.custom.view.GifView;
import com.nfcx.eg4.tool.API;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.main.MainActivity;
import com.nfcx.eg4.view.overview.inverter.InverterBatteryParamsActivity;
import com.nfcx.eg4.view.overview.inverter.InverterMidBoxRuntimeDataActivity;
import com.nfcx.eg4.view.overview.plant.PlantOverviewActivity;
import com.nfcx.eg4.view.plant.PlantListActivity;
import com.nfcx.eg4.view.userCenter.NewUserCenterActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Lv1OverviewFragment extends AbstractItemFragment {
    private static List<Integer> pvPieColors;
    private Button battCapacityButton;
    private Button battParallelNumButton;
    private Button batteryDataButton;
    private TextView batteryEnergyTitleLabel;
    private ConstraintLayout batteryEnergyViewLayout;
    private LinearLayout batteryEnergyViewLayout1;
    private LinearLayout batteryEnergyViewLayout2;
    private TextView batteryLevelTextView;
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
    private long lastRefreshDataTime;
    private Inverter lastReloadInverter;
    private long lastReloadTime;
    private TextView localTimeTextView;
    private Button midboxRuntimeButton;
    private ConstraintLayout newButton2Layout;
    private ConstraintLayout newButton3Layout;
    private ConstraintLayout newButtonLayout;
    private boolean paused;
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
    private Button quickGridChargeButton;
    private boolean readData;
    private Button refreshButton;
    private boolean refreshDataSuccess;
    private boolean shouldShowTodayCircleView;
    private boolean shouldShowTotalCircleView;
    private String status;
    private ImageView statusImageView;
    private Button stopGenExerciseStartButton;
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
        this.refreshDataSuccess = false;
    }

    static {
        ArrayList arrayList = new ArrayList();
        pvPieColors = arrayList;
        arrayList.add(Integer.valueOf(Color.rgb(255, 113, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA)));
        pvPieColors.add(Integer.valueOf(Color.rgb(92, 201, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256)));
        pvPieColors.add(Integer.valueOf(Color.rgb(242, 164, 116)));
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.readData = true;
        this.paused = false;
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m554x68bb0fe8();
            }
        }).start();
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m554x68bb0fe8() throws InterruptedException {
        while (this.readData) {
            if (!this.paused && !this.refreshDataSuccess) {
                for (int i = 0; i < 180 && this.readData && !this.paused; i++) {
                    Tool.sleep(1000L);
                }
                if (getActivity() != null && this.readData && !this.paused) {
                    getActivity().runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m557x84f0eabb();
                        }
                    });
                }
            }
            if (this.refreshDataSuccess) {
                for (int i2 = 0; i2 < 6 && this.readData; i2++) {
                    Tool.sleep(500L);
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        List<Inverter> invertersByPlant;
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lv1_overview, viewGroup, false);
        this.fragment = this;
        final UserData userData = GlobalInfo.getInstance().getUserData();
        PLATFORM.LUX_POWER.equals(userData.getPlatform());
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1OverviewFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                MainActivity.instance.finish();
            }
        });
        ((ImageView) viewInflate.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.2
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
                String str2 = parallelGroups.get(str);
                inverter.setParallelFirstDeviceSn(str2);
                Inverter inverter2 = userData.getInverter(str2);
                if (inverter2 != null) {
                    inverter.setDeviceType(inverter2.getDeviceType());
                }
                this.inverterList.add(i, inverter);
                i++;
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                Inverter inverter3 = (Inverter) Lv1OverviewFragment.this.inverterSpinner.getSelectedItem();
                System.out.println("Eg4 - onItemSelected selectInverter = " + inverter3);
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
                    } else {
                        Lv1OverviewFragment.this.newButton3Layout.setVisibility(8);
                    }
                    if (Lv1OverviewFragment.this.inverter.isMidBox()) {
                        Lv1OverviewFragment.this.powerRateButton.setVisibility(4);
                    }
                    Lv1OverviewFragment.this.m557x84f0eabb();
                    MainActivity mainActivity = (MainActivity) Lv1OverviewFragment.this.fragment.getActivity();
                    if (mainActivity != null) {
                        mainActivity.switchRemoteSetFragment(Lv1OverviewFragment.this.inverter.getDeviceTypeValue());
                    }
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
                    Lv1OverviewFragment.this.m557x84f0eabb();
                }
            }
        });
        this.pvEnergyViewLayout1 = (LinearLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout1);
        this.pvEnergyViewLayout2 = (ConstraintLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout2);
        this.pvEnergyViewLayout3 = (ConstraintLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout3);
        ConstraintLayout constraintLayout = (ConstraintLayout) viewInflate.findViewById(R.id.pvEnergyViewLayout);
        this.pvEnergyViewLayout = constraintLayout;
        constraintLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.4
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
            this.batteryEnergyViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda7
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.m555xb1b31b6e(view, motionEvent);
                }
            });
        }
        this.feedinEnergyViewLayout1 = (LinearLayout) viewInflate.findViewById(R.id.feedinEnergyViewLayout1);
        this.feedinEnergyViewLayout2 = (LinearLayout) viewInflate.findViewById(R.id.feedinEnergyViewLayout2);
        ConstraintLayout constraintLayout2 = (ConstraintLayout) viewInflate.findViewById(R.id.feedinEnergyViewLayout);
        this.feedinEnergyViewLayout = constraintLayout2;
        constraintLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda8
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.m556xd747246f(view, motionEvent);
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
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.5
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
        this.batteryLevelTextView = (TextView) viewInflate.findViewById(R.id.textView66);
        this.flowBatVoltTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_batVoltTextView);
        this.flowPvPowerImageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_pvPower_imageView);
        this.flowPvPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_pvPower_textView);
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
        this.quickGridChargeButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.6
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
        this.stopQuickGridChargeButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.7
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
        this.genExerciseStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.8
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
        String string4 = getString(R.string.start_gen_exercise);
        SpannableString spannableString4 = new SpannableString(string4 + " ↗");
        spannableString4.setSpan(new StyleSpan(1), 0, string4.length(), 33);
        spannableString4.setSpan(new StyleSpan(1), string4.length(), string4.length() + 2, 33);
        spannableString4.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString4.length(), 18);
        this.stopGenExerciseStartButton.setText(spannableString4);
        this.stopGenExerciseStartButton.setTextSize(1, 12.0f);
        this.stopGenExerciseStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1OverviewFragment.this.inverter != null) {
                    Lv1OverviewFragment.this.stopGenExerciseStartButton.setEnabled(false);
                    Lv1OverviewFragment lv1OverviewFragment = Lv1OverviewFragment.this;
                    new GenExerciseCtrlTask(lv1OverviewFragment, lv1OverviewFragment.stopGenExerciseStartButton).execute(false);
                }
            }
        });
        this.newButton2Layout = (ConstraintLayout) viewInflate.findViewById(R.id.new_button2_layout);
        this.batteryDataButton = (Button) viewInflate.findViewById(R.id.with_battery_Data_Button);
        String string5 = getString(R.string.battery_params);
        SpannableString spannableString5 = new SpannableString(string5 + " ↗");
        spannableString5.setSpan(new StyleSpan(1), 0, string5.length(), 33);
        spannableString5.setSpan(new StyleSpan(1), string5.length(), string5.length() + 2, 33);
        spannableString5.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString5.length(), 18);
        this.batteryDataButton.setText(spannableString5);
        this.batteryDataButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1OverviewFragment.this.startActivity(new Intent(Lv1OverviewFragment.this.getActivity(), (Class<?>) InverterBatteryParamsActivity.class));
            }
        });
        this.newButton3Layout = (ConstraintLayout) viewInflate.findViewById(R.id.new_button3_layout);
        this.midboxRuntimeButton = (Button) viewInflate.findViewById(R.id.midBox_runtime_button);
        SpannableString spannableString6 = new SpannableString("Energy Flow ↗");
        spannableString6.setSpan(new StyleSpan(1), 0, 11, 33);
        spannableString6.setSpan(new StyleSpan(1), 11, 13, 33);
        spannableString6.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString6.length(), 18);
        this.midboxRuntimeButton.setText(spannableString6);
        this.midboxRuntimeButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.11
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
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1OverviewFragment.this.refreshData();
            }
        });
        this.created = true;
        if (this.inverterList.isEmpty()) {
            initFlowChartByDeviceType();
        }
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$1$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ boolean m555xb1b31b6e(View view, MotionEvent motionEvent) {
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

    /* renamed from: lambda$onCreateView$2$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ boolean m556xd747246f(View view, MotionEvent motionEvent) {
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

    public void refreshData() {
        if (this.created) {
            if (this.inverter != null) {
                this.lastRefreshDataTime = System.currentTimeMillis();
                this.refreshButton.setEnabled(false);
                CountDownTimer countDownTimer = this.countDownTimer;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m558xaa84f3bc();
                    }
                }).start();
                return;
            }
            this.waitRequestCount = 2;
            m557x84f0eabb();
        }
    }

    /* renamed from: lambda$refreshData$4$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m558xaa84f3bc() {
        FragmentActivity activity;
        Runnable runnable;
        try {
            try {
                HashMap map = new HashMap();
                map.put("inverterSn", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteTransfer/refreshInputData", map);
                if (jSONObjectPostJson != null && jSONObjectPostJson.getBoolean("success")) {
                    Tool.sleep(3000L);
                }
                this.waitRequestCount = 2;
                activity = getActivity();
            } catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.phrase_toast_response_error, 1).show();
                e.printStackTrace();
                this.waitRequestCount = 2;
                activity = getActivity();
                if (activity == null) {
                    return;
                } else {
                    runnable = new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m557x84f0eabb();
                        }
                    };
                }
            }
            if (activity != null) {
                runnable = new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m557x84f0eabb();
                    }
                };
                activity.runOnUiThread(runnable);
            }
        } catch (Throwable th) {
            this.waitRequestCount = 2;
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                activity2.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m557x84f0eabb();
                    }
                });
            }
            throw th;
        }
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
            Button button = this.powerRateButton;
            if (button != null) {
                button.setText("");
            }
            Button button2 = this.firmwareButton;
            if (button2 != null) {
                button2.setText("");
            }
            this.centeredLayout.setVisibility(0);
            Inverter inverter = this.inverter;
            if (inverter != null && inverter.isParallelGroup()) {
                this.centered1Layout.setVisibility(8);
            } else {
                this.centered1Layout.setVisibility(0);
            }
            this.centered2Layout.setVisibility(0);
            Inverter inverter2 = this.inverter;
            if (inverter2 != null && !inverter2.isAllowGenExercise()) {
                this.genExerciseStartButton.setVisibility(4);
                this.stopGenExerciseStartButton.setVisibility(4);
            }
            Inverter inverter3 = this.inverter;
            if (inverter3 != null && inverter3.isAcCharger()) {
                z = true;
            }
            initFlowChartByDeviceExceptDeviceImg(z);
            refreshDeviceImageSource();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.paused = false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.paused = true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.readData = false;
    }

    public long getLastRefreshDataTime() {
        return this.lastRefreshDataTime;
    }

    private void refreshDeviceImageSource() {
        if (!this.created || this.flowInverterImageView == null) {
            return;
        }
        Inverter inverter = this.inverter;
        if (inverter != null && inverter.isParallelGroup()) {
            this.flowInverterImageView.setBackgroundResource(R.drawable.icon_inverter_parallel);
            return;
        }
        if (PLATFORM.LUX_POWER.equals(GlobalInfo.getInstance().getUserData().getPlatform())) {
            Inverter inverter2 = this.inverter;
            if (inverter2 != null && inverter2.isAcCharger()) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_ac_charger);
                return;
            }
            Inverter inverter3 = this.inverter;
            if (inverter3 != null && inverter3.isSnaSeries()) {
                if (this.inverter.isEcoBeast6k()) {
                    this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_eco_beast_6000);
                    return;
                }
                if (this.inverter.isSna6kUsAio()) {
                    this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_us_aio);
                    return;
                } else if (this.inverter.getSubDeviceTypeValue() == 1111) {
                    this.flowInverterImageView.setBackgroundResource(R.drawable.icon_sna_12k_us);
                    return;
                } else {
                    this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_off_grid);
                    return;
                }
            }
            Inverter inverter4 = this.inverter;
            if (inverter4 != null && inverter4.isType6()) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.flow_icon_inverter);
                if (this.inverter.getSubDeviceTypeValue() == 161 || this.inverter.getSubDeviceTypeValue() == 163) {
                    this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_type6_8_10k);
                }
                if (this.inverter.getMachineType() == 1) {
                    this.flowInverterImageView.setBackgroundResource(R.drawable.icon_inverter_minus);
                    return;
                }
                return;
            }
            this.flowInverterImageView.setBackgroundResource(R.drawable.flow_icon_inverter);
            return;
        }
        Inverter inverter5 = this.inverter;
        if (inverter5 != null && inverter5.isAcCharger()) {
            this.flowInverterImageView.setBackgroundResource(R.drawable.icon_ac_charger_mid);
            return;
        }
        Inverter inverter6 = this.inverter;
        if (inverter6 != null && inverter6.isSnaSeries()) {
            if (this.inverter.isEcoBeast6k()) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_eco_beast_6000);
                return;
            }
            if (this.inverter.isSna6kUsAio()) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_us_aio);
                return;
            } else if (this.inverter.getSubDeviceTypeValue() == 1111) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_sna_12k_us_mid);
                return;
            } else {
                this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_off_grid_mid);
                return;
            }
        }
        Inverter inverter7 = this.inverter;
        if (inverter7 != null && inverter7.isType6()) {
            this.flowInverterImageView.setBackgroundResource(R.drawable.flow_icon_inverter_mid);
            if (this.inverter.getSubDeviceTypeValue() == 161 || this.inverter.getSubDeviceTypeValue() == 163) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_type6_8_10k);
            }
            if (this.inverter.getMachineType() == 1) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_inverter_minus_mid);
                return;
            }
            return;
        }
        this.flowInverterImageView.setBackgroundResource(R.drawable.flow_icon_inverter_mid);
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
    public synchronized void m557x84f0eabb() {
        /*
            r8 = this;
            monitor-enter(r8)
            boolean r0 = r8.created     // Catch: java.lang.Throwable -> L4d
            if (r0 != 0) goto L7
            monitor-exit(r8)
            return
        L7:
            long r0 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L4d
            com.nfcx.eg4.global.bean.inverter.Inverter r2 = r8.inverter     // Catch: java.lang.Throwable -> L4d
            r3 = 1
            if (r2 != 0) goto L15
            com.nfcx.eg4.global.bean.inverter.Inverter r2 = r8.lastReloadInverter     // Catch: java.lang.Throwable -> L4d
            if (r2 == 0) goto L32
            goto L33
        L15:
            com.nfcx.eg4.global.bean.inverter.Inverter r4 = r8.lastReloadInverter     // Catch: java.lang.Throwable -> L4d
            if (r4 == 0) goto L33
            java.lang.String r2 = r2.getSerialNum()     // Catch: java.lang.Throwable -> L4d
            if (r2 == 0) goto L32
            com.nfcx.eg4.global.bean.inverter.Inverter r2 = r8.inverter     // Catch: java.lang.Throwable -> L4d
            java.lang.String r2 = r2.getSerialNum()     // Catch: java.lang.Throwable -> L4d
            com.nfcx.eg4.global.bean.inverter.Inverter r4 = r8.lastReloadInverter     // Catch: java.lang.Throwable -> L4d
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
            com.nfcx.eg4.global.bean.inverter.Inverter r2 = r8.inverter     // Catch: java.lang.Throwable -> L4d
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
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.m557x84f0eabb():void");
    }

    private void refreshEnergyInfo() {
        if (this.inverter != null) {
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m560x6957fea();
                }
            }).start();
        } else {
            clearEnergyInfo();
        }
    }

    /* renamed from: lambda$refreshEnergyInfo$6$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m560x6957fea() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.isParallelGroup() ? this.inverter.getParallelFirstDeviceSn() : this.inverter.getSerialNum());
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverter/getInverterEnergyInfo" + (this.inverter.isParallelGroup() ? "Parallel" : ""), map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f$0.m559xe10176e9(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* renamed from: lambda$refreshEnergyInfo$5$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m559xe10176e9(JSONObject jSONObject, FragmentActivity fragmentActivity) throws JSONException {
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
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m561xbf5a0469();
                }
            }).start();
            return;
        }
        clearFlowChart();
    }

    /* renamed from: lambda$refreshFlowChart$7$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m561xbf5a0469() {
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
            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m562x9554a296(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* renamed from: lambda$refreshFlowChartForMidBoxParallelAtThread$8$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /* synthetic */ void m562x9554a296(JSONObject jSONObject, FragmentActivity fragmentActivity) {
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
            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m563x35fbaa14(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x04b0 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x04e8 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x057d A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0587 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x05b8  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x05fe A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x068f A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x06cf A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x07c5 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x07cc  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x07cf A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x07e5 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x07f4 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0ac7  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0aca A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0aea  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0b1b A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:293:0x0b3b  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0b7a A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0b9c A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0243 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TRY_ENTER, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x024b A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0253 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x028f A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02d5 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0352 A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x035e A[Catch: all -> 0x0bb4, JSONException -> 0x0bb7, TRY_LEAVE, TryCatch #1 {all -> 0x0bb4, blocks: (B:3:0x0020, B:5:0x0026, B:7:0x002c, B:8:0x0031, B:10:0x0088, B:12:0x0090, B:14:0x00b0, B:16:0x00b8, B:18:0x00be, B:19:0x00c7, B:21:0x00cd, B:23:0x00d7, B:25:0x00e1, B:27:0x00f9, B:29:0x0101, B:31:0x0119, B:33:0x0123, B:35:0x012b, B:37:0x013c, B:38:0x014d, B:40:0x0155, B:41:0x0168, B:43:0x0170, B:45:0x0186, B:47:0x018c, B:55:0x0205, B:58:0x0243, B:112:0x04a2, B:114:0x04b0, B:119:0x0544, B:121:0x0568, B:123:0x0572, B:127:0x0592, B:131:0x05a8, B:134:0x05ba, B:168:0x0687, B:170:0x068f, B:172:0x06b3, B:201:0x0797, B:203:0x079f, B:205:0x07a7, B:208:0x07b7, B:210:0x07c5, B:213:0x07cf, B:219:0x07f4, B:224:0x0802, B:228:0x080d, B:231:0x0855, B:233:0x0862, B:235:0x087e, B:237:0x089c, B:278:0x0aca, B:282:0x0ad1, B:286:0x0adb, B:290:0x0b0d, B:292:0x0b1b, B:299:0x0b72, B:301:0x0b7a, B:303:0x0b82, B:304:0x0b8f, B:305:0x0b9c, B:297:0x0b42, B:298:0x0b53, B:288:0x0aec, B:289:0x0b02, B:238:0x08a3, B:239:0x08aa, B:242:0x08c1, B:244:0x08d9, B:245:0x08e5, B:232:0x085c, B:247:0x08f3, B:249:0x08fa, B:252:0x0904, B:254:0x09c5, B:256:0x09c9, B:261:0x0a24, B:262:0x0a2c, B:263:0x0a34, B:266:0x0a55, B:269:0x0a74, B:272:0x0a93, B:274:0x0aad, B:275:0x0aba, B:253:0x0965, B:216:0x07e5, B:173:0x06bf, B:175:0x06cf, B:177:0x06d7, B:179:0x06df, B:181:0x06e7, B:185:0x06fd, B:186:0x0708, B:188:0x0710, B:190:0x0718, B:192:0x0720, B:196:0x0755, B:197:0x0767, B:199:0x078a, B:200:0x0791, B:195:0x072a, B:184:0x06f1, B:136:0x05c6, B:138:0x05d2, B:140:0x05de, B:142:0x05ea, B:143:0x05f4, B:144:0x05fe, B:147:0x0608, B:149:0x0614, B:151:0x0620, B:153:0x062b, B:155:0x0636, B:156:0x063f, B:158:0x064a, B:160:0x0655, B:162:0x0660, B:164:0x066b, B:166:0x0676, B:167:0x067f, B:124:0x057d, B:126:0x0587, B:115:0x04e8, B:117:0x04ee, B:118:0x052a, B:60:0x024b, B:62:0x0253, B:64:0x025f, B:65:0x0282, B:66:0x0287, B:68:0x028f, B:70:0x029b, B:71:0x02c4, B:72:0x02c9, B:74:0x02d5, B:76:0x02dd, B:78:0x02e5, B:80:0x0313, B:82:0x031b, B:84:0x0323, B:91:0x035e, B:94:0x0369, B:96:0x0371, B:98:0x03d7, B:100:0x03df, B:101:0x03e9, B:102:0x03ee, B:104:0x03f5, B:106:0x0411, B:108:0x048e, B:110:0x0496, B:111:0x049f, B:85:0x034c, B:79:0x030e, B:86:0x0352, B:89:0x0359, B:48:0x01c4, B:50:0x01db, B:51:0x01de, B:30:0x0114, B:26:0x00f4, B:306:0x0ba8, B:309:0x0bae, B:318:0x0bbb), top: B:325:0x0020 }] */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$13] */
    /* renamed from: lambda$refreshFlowChartForParallelAtThread$9$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    /* synthetic */ void m563x35fbaa14(org.json.JSONObject r29, androidx.fragment.app.FragmentActivity r30) {
        /*
            Method dump skipped, instructions count: 3029
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.m563x35fbaa14(org.json.JSONObject, androidx.fragment.app.FragmentActivity):void");
    }

    private void refreshFlowChartForSingleAtThread() {
        HashMap map = new HashMap();
        map.put("serialNum", this.inverter.getSerialNum());
        final JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "api/inverter/getInverterRuntime", map);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m564x53d2ba4b(jSONObjectPostJson, activity);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x04f7 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0501 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0532  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0578 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0609 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0648 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0714 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x071b  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0749 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0751 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x07d1 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x089e A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:232:0x08d4  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x091b A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x093e  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0982 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0989  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0997  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x09ab  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x09c4 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:261:0x09d0 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x09df A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0a62 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0a78 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0a9a A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0aa6 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TRY_LEAVE, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0204 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x021d A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0225 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0261 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02a7 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0324 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x032b A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0331 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0343 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x034d A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03c8 A[Catch: all -> 0x0ab2, JSONException -> 0x0ab5, TryCatch #0 {all -> 0x0ab2, blocks: (B:3:0x0020, B:5:0x0056, B:7:0x005c, B:8:0x0061, B:10:0x006f, B:12:0x0077, B:14:0x007d, B:16:0x0093, B:18:0x009b, B:20:0x00b3, B:22:0x00bd, B:24:0x00c5, B:26:0x00d6, B:27:0x00e7, B:29:0x00ef, B:30:0x0102, B:32:0x010a, B:34:0x0120, B:36:0x0126, B:44:0x019c, B:48:0x0206, B:50:0x020e, B:52:0x0216, B:100:0x03cb, B:102:0x03d3, B:104:0x03db, B:106:0x0405, B:112:0x049c, B:114:0x04e2, B:116:0x04ec, B:120:0x050c, B:124:0x0522, B:127:0x0534, B:161:0x0601, B:163:0x0609, B:165:0x062c, B:194:0x070c, B:196:0x0714, B:198:0x071c, B:202:0x0736, B:206:0x076d, B:210:0x077d, B:217:0x0882, B:219:0x089e, B:223:0x08a9, B:227:0x08b7, B:231:0x08c5, B:235:0x0907, B:237:0x091b, B:245:0x097a, B:247:0x0982, B:249:0x098a, B:252:0x0999, B:255:0x09a2, B:258:0x09ac, B:260:0x09c4, B:264:0x09df, B:266:0x09ed, B:272:0x0a70, B:274:0x0a78, B:276:0x0a80, B:277:0x0a8d, B:278:0x0a9a, B:267:0x0a3c, B:269:0x0a54, B:270:0x0a5b, B:271:0x0a62, B:261:0x09d0, B:243:0x0947, B:244:0x0958, B:233:0x08d6, B:234:0x08f7, B:211:0x07d1, B:214:0x07e1, B:215:0x0833, B:203:0x0749, B:205:0x0751, B:166:0x0637, B:168:0x0648, B:170:0x0650, B:172:0x0658, B:174:0x0660, B:178:0x0676, B:179:0x0680, B:181:0x0688, B:183:0x0690, B:185:0x0698, B:189:0x06cc, B:190:0x06dd, B:192:0x0700, B:193:0x0707, B:188:0x06a2, B:177:0x066a, B:129:0x0540, B:131:0x054c, B:133:0x0558, B:135:0x0564, B:136:0x056e, B:137:0x0578, B:140:0x0582, B:142:0x058e, B:144:0x059a, B:146:0x05a5, B:148:0x05b0, B:149:0x05b9, B:151:0x05c4, B:153:0x05cf, B:155:0x05da, B:157:0x05e5, B:159:0x05f0, B:160:0x05f9, B:117:0x04f7, B:119:0x0501, B:108:0x0440, B:110:0x0448, B:111:0x0482, B:279:0x0aa6, B:53:0x021d, B:55:0x0225, B:57:0x0231, B:58:0x0254, B:59:0x0259, B:61:0x0261, B:63:0x026d, B:64:0x0296, B:65:0x029b, B:67:0x02a7, B:69:0x02af, B:71:0x02b7, B:75:0x02e5, B:77:0x02ed, B:79:0x02f5, B:83:0x0324, B:89:0x033b, B:91:0x0343, B:94:0x034d, B:96:0x03b7, B:98:0x03bf, B:99:0x03c8, B:84:0x032b, B:80:0x0317, B:81:0x031d, B:72:0x02d9, B:73:0x02df, B:85:0x0331, B:88:0x0338, B:37:0x015d, B:39:0x0174, B:40:0x0177, B:19:0x00ae, B:15:0x008e, B:282:0x0aac, B:291:0x0ab9), top: B:296:0x0020 }] */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$14] */
    /* renamed from: lambda$refreshFlowChartForSingleAtThread$10$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    /* synthetic */ void m564x53d2ba4b(org.json.JSONObject r27, androidx.fragment.app.FragmentActivity r28) {
        /*
            Method dump skipped, instructions count: 2771
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.m564x53d2ba4b(org.json.JSONObject, androidx.fragment.app.FragmentActivity):void");
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
        /* JADX WARN: Type inference failed for: r0v8, types: [com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1, java.lang.Runnable] */
        /* JADX WARN: Type inference failed for: r0v9 */
        /* JADX WARN: Type inference failed for: r1v0 */
        /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
        /* JADX WARN: Type inference failed for: r1v6, types: [com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment] */
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
                    i = new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m568xf7b90bd7();
                        }
                    };
                    thread = new Thread((Runnable) i);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.ctrlButton.setVisibility(8);
                    this.ctrlButton.setEnabled(z);
                    new ReadQuickChargeTask(this.fragment).execute(new Void[i]);
                    thread = new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m568xf7b90bd7();
                        }
                    });
                }
                thread.start();
            } catch (Throwable th) {
                this.ctrlButton.setVisibility(8);
                this.ctrlButton.setEnabled(z);
                new ReadQuickChargeTask(this.fragment).execute(new Void[i]);
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws InterruptedException {
                        this.f$0.m568xf7b90bd7();
                    }
                }).start();
                throw th;
            }
        }

        /* renamed from: lambda$onPostExecute$1$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment$QuickChargeCtrlTask, reason: not valid java name */
        /* synthetic */ void m568xf7b90bd7() throws InterruptedException {
            Tool.sleep(10000L);
            FragmentActivity activity = this.fragment.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$QuickChargeCtrlTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m567xf6ea8d56();
                    }
                });
            }
        }

        /* renamed from: lambda$onPostExecute$0$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment$QuickChargeCtrlTask, reason: not valid java name */
        /* synthetic */ void m567xf6ea8d56() {
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
                        if (!jSONObject.getBoolean("success")) {
                            Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_unknown_error);
                        }
                    } else {
                        Tool.alert(this.fragment.getActivity(), R.string.phrase_toast_network_error);
                    }
                    thread = new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m566xf1ae2b1e();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    thread = new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            this.f$0.m566xf1ae2b1e();
                        }
                    });
                }
                thread.start();
            } catch (Throwable th) {
                new Thread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws InterruptedException {
                        this.f$0.m566xf1ae2b1e();
                    }
                }).start();
                throw th;
            }
        }

        /* renamed from: lambda$onPostExecute$1$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment$GenExerciseCtrlTask, reason: not valid java name */
        /* synthetic */ void m566xf1ae2b1e() throws InterruptedException {
            Tool.sleep(3000L);
            FragmentActivity activity = this.fragment.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment$GenExerciseCtrlTask$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m565xf0dfac9d();
                    }
                });
            }
        }

        /* renamed from: lambda$onPostExecute$0$com-nfcx-eg4-view-main-fragment-lv1-Lv1OverviewFragment$GenExerciseCtrlTask, reason: not valid java name */
        /* synthetic */ void m565xf0dfac9d() {
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
                com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.access$2000(r5)
                r5.setVisibility(r1)
                com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.access$2100(r5)
                r5.setVisibility(r1)
                goto L61
            L3b:
                com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment r0 = r4.fragment
                android.widget.Button r0 = com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.access$2000(r0)
                r0.setVisibility(r1)
                com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment r0 = r4.fragment
                android.widget.Button r0 = com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.access$2100(r0)
                r0.setVisibility(r1)
                throw r5
            L4e:
                r0 = r1
            L4f:
                com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.access$2000(r5)
                r5.setVisibility(r1)
                com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment r5 = r4.fragment
                android.widget.Button r5 = com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.access$2100(r5)
                r5.setVisibility(r0)
            L61:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.main.fragment.lv1.Lv1OverviewFragment.ReadQuickChargeTask.onPostExecute(org.json.JSONObject):void");
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