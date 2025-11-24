package com.lux.luxcloud.view.local.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alibaba.fastjson2.internal.asm.Opcodes;
import com.google.common.base.Ascii;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnect;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.bean.inverter.BATTERY_TYPE;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE;
import com.lux.luxcloud.global.bean.set.RemoteReadInfo;
import com.lux.luxcloud.global.bean.set.RemoteWriteInfo;
import com.lux.luxcloud.global.custom.view.GifView;
import com.lux.luxcloud.protocol.tcp.DataFrameFactory;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.lux.luxcloud.tool.FrameTool;
import com.lux.luxcloud.tool.InvTool;
import com.lux.luxcloud.tool.ProTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.local.LocalActivity;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.view.main.fragment.lv1.AbstractItemFragment;
import java.util.Calendar;
import java.util.Date;
import kotlin.text.Typography;
import org.bouncycastle.math.Primes;
import org.bouncycastle.pqc.legacy.math.linearalgebra.Matrix;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LocalOverviewFragment extends AbstractItemFragment {
    private String[] _110Functions;
    private String[] _179Functions;
    private String[] _21Functions;
    private String[] _226Functions;
    private TextView battCapacityLabel;
    private TextView battCapacityValueLabel;
    private TextView battParallelNumLabel;
    private TextView battParallelNumValueLabel;
    private TextView bmsLimitChargeLabel;
    private TextView bmsLimitChargeValueLabel;
    private TextView bmsLimitDischargeLabel;
    private TextView bmsLimitDischargeValueLabel;
    private TextView datalogSnTextView;
    private boolean firstRead04;
    private GifView flowAcPvPowerGifView1;
    private GifView flowAcPvPowerGifView2;
    private ImageView flowAcPvPowerImageView;
    private TextView flowAcPvPowerLabelTextView;
    private TextView flowAcPvPowerTextView;
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
    private ConstraintLayout genExerciseLayout;
    private Button genExerciseStartButton;
    private TextView inverterSnTextView;
    private LocalConnect localConnect;
    private TextView localTimeTextView;
    private boolean paused;
    private boolean read03Success;
    private boolean read04_3_Success;
    private boolean readData;
    private ImageView statusImageView;
    private Button stopGenExerciseStartButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView todayDischargingTextView;
    private TextView todayExportTextView;
    private TextView todayUsageTextView;
    private TextView todayYieldingTextView;
    private TextView totalDischargingTextView;
    private TextView totalExportTextView;
    private TextView totalUsageTextView;
    private TextView totalYieldingTextView;

    public LocalOverviewFragment(LocalConnect localConnect) {
        super(1L);
        this.read03Success = false;
        this.firstRead04 = true;
        this.read04_3_Success = false;
        this._21Functions = new String[]{"FUNC_DRMS_EN", "FUNC_AC_CHARGE", "FUNC_FORCED_CHG_EN", "FUNC_FORCED_DISCHG_EN", "FUNC_SET_TO_STANDBY", "FUNC_EPS_EN", "FUNC_FEED_IN_GRID_EN"};
        this._110Functions = new String[]{"FUNC_PV_GRID_OFF_EN", "FUNC_RUN_WITHOUT_GRID", "FUNC_MICRO_GRID_EN", "FUNC_BAT_SHARED", "FUNC_CHARGE_LAST", "FUNC_BUZZER_EN", "FUNC_TAKE_LOAD_TOGETHER", "FUNC_GREEN_EN"};
        this._179Functions = new String[]{"FUNC_CT_DIRECTION_REVERSED", "FUNC_PV_ARC_FAULT_CLEAR", "FUNC_PV_SELL_TO_GRID_EN", "FUNC_WATT_VOLT_EN", "FUNC_TRIP_TIME_UNIT", "FUNC_ACTIVE_POWER_LIMIT_MODE", "FUNC_GRID_PEAK_SHAVING", "FUNC_GEN_PEAK_SHAVING", "FUNC_BAT_CHARGE_CONTROL", "FUNC_BAT_DISCHARGE_CONTROL", "FUNC_PV_ARC", "FUNC_ON_GRID_ALWAYS_ON"};
        this._226Functions = new String[]{"FUNC_RUN_WITHOUT_GRID_12K", "FUNC_GEN_CTRL"};
        this.localConnect = localConnect;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.readData = true;
        this.paused = false;
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m570xd98a7644();
            }
        }).start();
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m570xd98a7644() throws InterruptedException {
        this.localConnect.initialize(true);
        while (this.readData) {
            if (!this.paused) {
                if (!this.read03Success) {
                    sendRead03Command1();
                } else {
                    sendRead04Command1();
                }
            }
            int i = 0;
            if (this.read03Success) {
                if (this.firstRead04) {
                    Tool.sleep(1000L);
                    this.firstRead04 = false;
                } else {
                    while (i < 20 && this.readData) {
                        Tool.sleep(500L);
                        i++;
                    }
                }
            } else {
                while (i < 6 && this.readData) {
                    Tool.sleep(500L);
                    i++;
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_local_overview, viewGroup, false);
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOverviewFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) LoginActivity.class));
                LocalActivity.instance.finish();
            }
        });
        this.datalogSnTextView = (TextView) viewInflate.findViewById(R.id.fragment_overview_datalogSn_TextView);
        this.inverterSnTextView = (TextView) viewInflate.findViewById(R.id.fragment_overview_inverterSn_TextView);
        this.todayYieldingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_yielding);
        this.totalYieldingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_yielding);
        this.todayDischargingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_discharging);
        this.totalDischargingTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_discharging);
        this.todayExportTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_export);
        this.totalExportTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_export);
        this.todayUsageTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_today_usage);
        this.totalUsageTextView = (TextView) viewInflate.findViewById(R.id.main_overview_info_total_usage);
        this.statusImageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_statusImageView);
        this.flowBatteryPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_batteryPower_textView);
        this.flowBatteryPowerLabelTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_batteryPowerLabel_textView);
        this.flowBatteryPowerGifView1 = (GifView) viewInflate.findViewById(R.id.fragment_flow_batteryPower_gifView1);
        this.flowBatteryPowerGifView2 = (GifView) viewInflate.findViewById(R.id.fragment_flow_batteryPower_gifView2);
        this.flowSocPowerTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_socPower_textView);
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
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.fragment_flow_inverter_imageView);
        this.flowInverterImageView = imageView;
        imageView.setBackgroundResource(R.drawable.icon_dt_inverter_mid);
        this.genExerciseLayout = (ConstraintLayout) viewInflate.findViewById(R.id.gen_exercise_layout);
        this.genExerciseStartButton = (Button) viewInflate.findViewById(R.id.start_GEN_Exercise_Button);
        String string = getString(R.string.start_gen_exercise);
        SpannableString spannableString = new SpannableString(string + " ↗");
        spannableString.setSpan(new StyleSpan(1), 0, string.length(), 33);
        spannableString.setSpan(new StyleSpan(1), string.length(), string.length() + 2, 33);
        spannableString.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString.length(), 18);
        this.genExerciseStartButton.setText(spannableString);
        this.genExerciseStartButton.setTextSize(1, 12.0f);
        this.genExerciseStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOverviewFragment localOverviewFragment = LocalOverviewFragment.this;
                localOverviewFragment.runFuncWriteWrite(localOverviewFragment.getGenExerciseCtrlFunctionParam(localOverviewFragment.localConnect.getInverter()), true);
            }
        });
        this.stopGenExerciseStartButton = (Button) viewInflate.findViewById(R.id.stop_GEN_Exercise_Button);
        String string2 = getString(R.string.stop_gen_exercise);
        SpannableString spannableString2 = new SpannableString(string2 + " ↗");
        spannableString2.setSpan(new StyleSpan(1), 0, string2.length(), 33);
        spannableString2.setSpan(new StyleSpan(1), string2.length(), string2.length() + 2, 33);
        spannableString2.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString2.length(), 18);
        this.stopGenExerciseStartButton.setText(spannableString2);
        this.stopGenExerciseStartButton.setTextSize(1, 12.0f);
        this.stopGenExerciseStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m571x52cf74fe(view);
            }
        });
        this.localTimeTextView = (TextView) viewInflate.findViewById(R.id.fragment_flow_localTime_textView);
        this.battParallelNumLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_battParallelNumLabel);
        this.battParallelNumValueLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_battParallelNumValueLabel);
        this.battCapacityLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_battCapacityLabel);
        this.battCapacityValueLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_battCapacityValueLabel);
        this.bmsLimitChargeLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_bmsLimitChargeLabel);
        this.bmsLimitChargeValueLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_bmsLimitChargeValueLabel);
        this.bmsLimitDischargeLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_bmsLimitDischargeLabel);
        this.bmsLimitDischargeValueLabel = (TextView) viewInflate.findViewById(R.id.fragment_flow_bmsLimitDischargeValueLabel);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) viewInflate.findViewById(R.id.fragment_overview_swipe_refresh_layout);
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setEnabled(false);
        initFlowChartByDeviceType();
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$1$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m571x52cf74fe(View view) {
        runFuncWriteWrite(getGenExerciseCtrlFunctionParam(this.localConnect.getInverter()), false);
    }

    public String getGenExerciseCtrlFunctionParam(Inverter inverter) {
        if (inverter.isType6() || inverter.isTrip12K()) {
            return "FUNC_BUZZER_EN";
        }
        if (inverter.isSnaSeries()) {
            return "FUNC_GEN_CTRL";
        }
        return null;
    }

    private void initFlowChartByDeviceType() {
        Inverter inverter = this.localConnect.getInverter();
        if (inverter != null && inverter.isAcCharger()) {
            this.flowPvPowerImageView.setVisibility(4);
            this.flowPvPowerTextView.setVisibility(4);
            this.flowPvPowerLabelTextView.setVisibility(4);
            this.flowPvPowerGifView.setVisibility(4);
            this.flowAcPvPowerImageView.setVisibility(0);
            this.flowAcPvPowerTextView.setVisibility(0);
            this.flowAcPvPowerLabelTextView.setVisibility(0);
            this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_ac_charger_mid);
            return;
        }
        this.flowAcPvPowerImageView.setVisibility(4);
        this.flowAcPvPowerTextView.setVisibility(4);
        this.flowAcPvPowerLabelTextView.setVisibility(4);
        this.flowAcPvPowerGifView1.setVisibility(4);
        this.flowAcPvPowerGifView2.setVisibility(4);
        this.flowPvPowerImageView.setVisibility(0);
        this.flowPvPowerTextView.setVisibility(0);
        this.flowPvPowerLabelTextView.setVisibility(0);
        if (inverter != null && inverter.checkAllowGenExercise()) {
            this.genExerciseLayout.setVisibility(0);
        } else {
            this.genExerciseLayout.setVisibility(8);
        }
        if (inverter != null && inverter.isAcCharger()) {
            this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_ac_charger_mid);
        } else if (inverter != null && inverter.isSnaSeries()) {
            if (inverter.isEcoBeast6k()) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_eco_beast_6000_mid);
            } else if (inverter.isSna6kUsAio()) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.inverter_us_aio);
            } else if (inverter.getSubDeviceTypeValue() == 131) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_sna_us_6k_mid);
            } else if (inverter.getSubDeviceTypeValue() == 1111) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_sna_12k_us_mid);
            } else if (inverter.getSubDeviceTypeValue() == 1110) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_sna_12k_eu_mid);
            } else {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_inverter_off_grid_mid);
            }
        } else if (inverter != null && inverter.isType6Series()) {
            if (inverter.getSubDeviceTypeValue() == 161) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_type6_8_10k_eu_mid);
            } else if (inverter.getSubDeviceTypeValue() == 162) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_type6_12k_eu_mid);
            } else if (inverter.getSubDeviceTypeValue() == 163) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_type6_8_10k_us_mid);
            } else if (inverter.getSubDeviceTypeValue() == 164) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_type6_12k_us_mid);
            }
            if (inverter.getMachineType() == 1) {
                this.flowInverterImageView.setBackgroundResource(R.drawable.icon_inverter_minus);
            }
        }
        if (inverter == null) {
            this.flowInverterImageView.setBackgroundResource(R.drawable.icon_dt_inverter_mid);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        System.out.println("LuxPowerLocal Overview Fragment onStart...");
        Inverter inverter = this.localConnect.getInverter();
        this.datalogSnTextView.setText(inverter != null ? inverter.getDatalogSn() : "");
        this.inverterSnTextView.setText(inverter != null ? inverter.getSerialNum() : "");
        initFlowChartByDeviceType();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("LuxPower - ble - onResume >>>>>>>>>>>");
        this.paused = false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        System.out.println("LuxPower - ble - onPause >>>>>>>>>>>");
        this.readData = false;
        this.paused = true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        System.out.println("LuxPowerLocalOverviewFragment onDestroy...");
        this.readData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runFuncWriteWrite(String str, boolean z) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.FUNCTION_PARAM);
        remoteWriteInfo.setFunctionParam(str);
        remoteWriteInfo.setFunctionEnable(z);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    private class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private LocalOverviewFragment fragment;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(LocalOverviewFragment localOverviewFragment) {
            this.fragment = localOverviewFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
            this.remoteWriteInfo.setEnabled(false);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(RemoteWriteInfo... remoteWriteInfoArr) throws NumberFormatException {
            Integer numValueOf;
            Integer bitByFunction;
            Integer numValueOf2;
            Integer bitByFunction2;
            Integer numValueOf3;
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                publishProgress(new Void[0]);
                switch (AnonymousClass3.$SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()]) {
                    case 1:
                        String valueText = remoteWriteInfo.getValueText();
                        if (Tool.isEmpty(valueText)) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        String holdParam = remoteWriteInfo.getHoldParam();
                        Integer startRegisterByParam = this.fragment.getStartRegisterByParam(holdParam);
                        if (!"HOLD_TIME".equals(holdParam)) {
                            Integer valueByParam = this.fragment.getValueByParam(holdParam, valueText);
                            if (valueByParam == null) {
                                return this.fragment.createFailureJSONObject("PARAM_ERROR");
                            }
                            return this.fragment.localConnect.writeSingle(startRegisterByParam.intValue(), valueByParam.intValue()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                        }
                        Date dateTime = InvTool.parseDateTime(valueText);
                        if (dateTime == null) {
                            return this.fragment.createFailureJSONObject("PARAM_ERROR");
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateTime);
                        return this.fragment.localConnect.writeMulti(startRegisterByParam.intValue(), 3, new byte[]{(byte) (calendar.get(1) + (-2000)), (byte) (calendar.get(2) + 1), (byte) calendar.get(5), (byte) calendar.get(11), (byte) calendar.get(12), (byte) calendar.get(13)}) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                    case 2:
                        Integer registerByBitParam = this.fragment.getRegisterByBitParam(remoteWriteInfo.getBitParam());
                        Integer single03 = this.fragment.localConnect.readSingle03(registerByBitParam.intValue());
                        if (single03 == null) {
                            return this.fragment.createFailureJSONObject("FAILED");
                        }
                        Integer bitByBitParam = this.fragment.getBitByBitParam(remoteWriteInfo.getBitParam());
                        Integer bitSizeByBitParam = this.fragment.getBitSizeByBitParam(remoteWriteInfo.getBitParam());
                        try {
                            numValueOf = Integer.valueOf(Integer.parseInt(remoteWriteInfo.getValueText()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            numValueOf = null;
                        }
                        if (bitByBitParam == null || bitSizeByBitParam == null || numValueOf == null) {
                            return this.fragment.createFailureJSONObject("FAILED");
                        }
                        int iIntValue = bitByBitParam.intValue() + bitSizeByBitParam.intValue();
                        return this.fragment.localConnect.writeSingle(registerByBitParam.intValue(), ((((single03.intValue() >> iIntValue) << iIntValue) + (numValueOf.intValue() << bitByBitParam.intValue())) + (single03.intValue() & ((1 << bitByBitParam.intValue()) - 1))) & 65535) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                    case 3:
                        Integer registerByFunction = this.fragment.getRegisterByFunction(remoteWriteInfo.getFunctionParam());
                        Integer single032 = this.fragment.localConnect.readSingle03(registerByFunction.intValue());
                        if (single032 != null && (bitByFunction = this.fragment.getBitByFunction(remoteWriteInfo.getFunctionParam())) != null) {
                            if (remoteWriteInfo.isFunctionEnable()) {
                                numValueOf2 = Integer.valueOf(single032.intValue() | (1 << bitByFunction.intValue()));
                            } else {
                                numValueOf2 = Integer.valueOf(single032.intValue() & (~(1 << bitByFunction.intValue())) & 65535);
                            }
                            return this.fragment.localConnect.writeSingle(registerByFunction.intValue(), Integer.valueOf(numValueOf2.intValue() & 65535).intValue()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                        }
                        return this.fragment.createFailureJSONObject("FAILED");
                    case 4:
                        String hourText = remoteWriteInfo.getHourText();
                        String minuteText = remoteWriteInfo.getMinuteText();
                        if (Tool.isEmpty(hourText) || Tool.isEmpty(minuteText)) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        try {
                            int i = Integer.parseInt(hourText);
                            int i2 = Integer.parseInt(minuteText);
                            if (i < 0 || i > 23) {
                                return this.fragment.createFailureJSONObject("OUT_RANGE_ERROR");
                            }
                            if (i2 < 0 || i2 > 59) {
                                return this.fragment.createFailureJSONObject("OUT_RANGE_ERROR");
                            }
                            return this.fragment.localConnect.writeSingle(this.fragment.getStartRegisterByParam(remoteWriteInfo.getTimeParam()).intValue(), ProTool.count(i2, i)) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return this.fragment.createFailureJSONObject("INTEGER_FORMAT_ERROR");
                        }
                    case 5:
                        Integer registerByFunction2 = this.fragment.getRegisterByFunction(remoteWriteInfo.getFunctionParam());
                        Integer single033 = this.fragment.localConnect.readSingle03(registerByFunction2.intValue());
                        if (single033 != null && (bitByFunction2 = this.fragment.getBitByFunction(remoteWriteInfo.getFunctionParam())) != null) {
                            if (remoteWriteInfo.isFunctionToggleButtonChecked()) {
                                numValueOf3 = Integer.valueOf(single033.intValue() | (1 << bitByFunction2.intValue()));
                            } else {
                                numValueOf3 = Integer.valueOf(single033.intValue() & (~(1 << bitByFunction2.intValue())) & 65535);
                            }
                            return this.fragment.localConnect.writeSingle(registerByFunction2.intValue(), Integer.valueOf(numValueOf3.intValue() & 65535).intValue()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                        }
                        return this.fragment.createFailureJSONObject("FAILED");
                    case 6:
                        if (remoteWriteInfo.getDatalogParamIndex() == null || remoteWriteInfo.getDatalogParamValues() == null) {
                            return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                        }
                        return this.fragment.localConnect.writeDatalogParam(remoteWriteInfo.getDatalogParamIndex().intValue(), remoteWriteInfo.getDatalogParamValues()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                }
            }
            return this.fragment.createFailureJSONObject("UNKNOWN_ERROR");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0085 A[Catch: all -> 0x0090, Exception -> 0x0092, TRY_LEAVE, TryCatch #2 {Exception -> 0x0092, blocks: (B:4:0x0006, B:6:0x000e, B:8:0x002d, B:9:0x0051, B:11:0x0061, B:12:0x0085), top: B:31:0x0006, outer: #0 }] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r6) {
            /*
                r5 = this;
                super.onPostExecute(r6)
                r0 = 1
                if (r6 == 0) goto L85
                java.lang.String r1 = "success"
                boolean r1 = r6.getBoolean(r1)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                if (r1 == 0) goto L85
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r6 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                androidx.fragment.app.FragmentActivity r6 = r6.getActivity()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r1 = 2131886305(0x7f1200e1, float:1.9407185E38)
                com.lux.luxcloud.tool.Tool.alert(r6, r1)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r6 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.connect.LocalConnect r6 = com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.access$000(r6)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.global.bean.inverter.Inverter r6 = r6.getInverter()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                boolean r6 = r6.isType6()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r1 = 0
                r2 = 40
                if (r6 == 0) goto L51
                com.lux.luxcloud.global.bean.set.RemoteReadInfo r6 = new com.lux.luxcloud.global.bean.set.RemoteReadInfo     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r3 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.connect.LocalConnect r3 = com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.access$000(r3)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.global.bean.inverter.Inverter r3 = r3.getInverter()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                java.lang.String r3 = r3.getSerialNum()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r4 = 80
                r6.<init>(r3, r4, r2)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$ReadMultiParamTask r2 = new com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$ReadMultiParamTask     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r3 = com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.this     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r2.<init>(r3)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.global.bean.set.RemoteReadInfo[] r3 = new com.lux.luxcloud.global.bean.set.RemoteReadInfo[r0]     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r3[r1] = r6     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r2.execute(r3)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                goto L8a
            L51:
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r6 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.connect.LocalConnect r6 = com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.access$000(r6)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.global.bean.inverter.Inverter r6 = r6.getInverter()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                boolean r6 = r6.isSnaSeries()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                if (r6 == 0) goto L8a
                com.lux.luxcloud.global.bean.set.RemoteReadInfo r6 = new com.lux.luxcloud.global.bean.set.RemoteReadInfo     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r3 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.connect.LocalConnect r3 = com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.access$000(r3)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.global.bean.inverter.Inverter r3 = r3.getInverter()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                java.lang.String r3 = r3.getSerialNum()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r4 = 200(0xc8, float:2.8E-43)
                r6.<init>(r3, r4, r2)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$ReadMultiParamTask r2 = new com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$ReadMultiParamTask     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r3 = com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.this     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r2.<init>(r3)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.global.bean.set.RemoteReadInfo[] r3 = new com.lux.luxcloud.global.bean.set.RemoteReadInfo[r0]     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r3[r1] = r6     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                r2.execute(r3)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                goto L8a
            L85:
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r1 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.access$1100(r1, r6)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
            L8a:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r6 = r5.remoteWriteInfo
                r6.setEnabled(r0)
                goto La8
            L90:
                r6 = move-exception
                goto La9
            L92:
                r6 = move-exception
                r6.printStackTrace()     // Catch: java.lang.Throwable -> L90
                com.lux.luxcloud.view.local.fragment.LocalOverviewFragment r6 = r5.fragment     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> La3
                androidx.fragment.app.FragmentActivity r6 = r6.getActivity()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> La3
                r1 = 2131886563(0x7f1201e3, float:1.9407708E38)
                com.lux.luxcloud.tool.Tool.alert(r6, r1)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> La3
                goto L8a
            La3:
                r6 = move-exception
                r6.printStackTrace()     // Catch: java.lang.Throwable -> L90
                goto L8a
            La8:
                return
            La9:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r5.remoteWriteInfo
                r1.setEnabled(r0)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE;

        static {
            int[] iArr = new int[REMOTE_WRITE_TYPE.values().length];
            $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE = iArr;
            try {
                iArr[REMOTE_WRITE_TYPE.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.BIT_PARAM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.FUNCTION_PARAM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.TIME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.CONTROL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.DATALOG_PARAM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static class ReadMultiParamTask extends AsyncTask<RemoteReadInfo, JSONObject, Void> {
        private LocalOverviewFragment fragment;

        public ReadMultiParamTask(LocalOverviewFragment localOverviewFragment) {
            this.fragment = localOverviewFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(RemoteReadInfo... remoteReadInfoArr) throws JSONException {
            for (RemoteReadInfo remoteReadInfo : remoteReadInfoArr) {
                int startRegister = remoteReadInfo.getStartRegister();
                int pointNumber = remoteReadInfo.getPointNumber();
                String strSendCommand = this.fragment.localConnect.sendCommand("read_multi_03_" + startRegister + "_" + (pointNumber * 2), DataFrameFactory.createReadMultiHoldDataFrame(this.fragment.localConnect.getTcpProtocol(), this.fragment.localConnect.getDatalogSn(), startRegister, pointNumber));
                JSONObject jSONObjectCreateSuccessJSONObject = this.fragment.createSuccessJSONObject();
                try {
                    if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 36) {
                        for (int i = startRegister; i < startRegister + pointNumber; i++) {
                            if (i == 110) {
                                int i2 = ((110 - startRegister) * 2) + 35;
                                int iCount = ProTool.count(strSendCommand.charAt(i2 + 1), strSendCommand.charAt(i2));
                                for (String str : this.fragment._110Functions) {
                                    jSONObjectCreateSuccessJSONObject.put(str, ((1 << this.fragment.getBitByFunction(str).intValue()) & iCount) > 0);
                                }
                            }
                            if (i == 226) {
                                int i3 = ((226 - startRegister) * 2) + 35;
                                int iCount2 = ProTool.count(strSendCommand.charAt(i3 + 1), strSendCommand.charAt(i3));
                                for (String str2 : this.fragment._226Functions) {
                                    jSONObjectCreateSuccessJSONObject.put(str2, ((1 << this.fragment.getBitByFunction(str2).intValue()) & iCount2) > 0);
                                }
                            }
                        }
                    }
                    JSONObject[] jSONObjectArr = new JSONObject[1];
                    try {
                        jSONObjectArr[0] = jSONObjectCreateSuccessJSONObject;
                        publishProgress(jSONObjectArr);
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(JSONObject... jSONObjectArr) {
            super.onProgressUpdate((Object[]) jSONObjectArr);
            for (JSONObject jSONObject : jSONObjectArr) {
                if (jSONObject != null) {
                    try {
                        if (jSONObject.getBoolean("success")) {
                            if (this.fragment.localConnect.getInverter().checkAllowGenExercise()) {
                                this.fragment.genExerciseLayout.setVisibility(0);
                                this.fragment.updateGenExerciseButtons(jSONObject);
                            } else {
                                this.fragment.genExerciseLayout.setVisibility(View.generateViewId());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Toast.makeText(this.fragment.getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_unknown_error, 1).show();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r1) {
            super.onPostExecute((ReadMultiParamTask) r1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGenExerciseButtons(JSONObject jSONObject) {
        try {
            boolean z = true;
            boolean z2 = this.localConnect.getInverter().isType6() && jSONObject.getBoolean("FUNC_BUZZER_EN");
            if (!this.localConnect.getInverter().isSnaSeries() || !jSONObject.getBoolean("FUNC_GEN_CTRL")) {
                z = z2;
            }
            if (z) {
                this.genExerciseStartButton.setVisibility(4);
                this.stopGenExerciseStartButton.setVisibility(0);
            } else {
                this.genExerciseStartButton.setVisibility(0);
                this.stopGenExerciseStartButton.setVisibility(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRead03Command1() {
        try {
            if (this.localConnect.initialize(true)) {
                if (Tool.isEmpty(this.localConnect.getDatalogSn())) {
                    return;
                }
                if (Constants.DEFAULT_DATALOG_SN.equals(this.localConnect.getDatalogSn())) {
                    String commandWaitResult = this.localConnect.getCommandWaitResult("heart_beat");
                    if (!Tool.isEmpty(commandWaitResult) && commandWaitResult.length() == 19) {
                        this.localConnect.setTcpProtocol(commandWaitResult.charAt(2) == 2 ? TCP_PROTOCOL._02 : TCP_PROTOCOL._01);
                        this.localConnect.setDatalogSn(commandWaitResult.substring(8, 18));
                    }
                }
                if (this.localConnect.read03AndInitDevice()) {
                    this.read03Success = true;
                    if (this.localConnect.getInverter().isType6()) {
                        new ReadMultiParamTask(this).execute(new RemoteReadInfo(this.localConnect.getInverter().getSerialNum(), 80, 40));
                    } else if (this.localConnect.getInverter().isSnaSeries()) {
                        new ReadMultiParamTask(this).execute(new RemoteReadInfo(this.localConnect.getInverter().getSerialNum(), 200, 40));
                    }
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m572xcd3bd756();
                            }
                        });
                        return;
                    }
                    return;
                }
                this.localConnect.setTcpProtocol(TCP_PROTOCOL._01.equals(this.localConnect.getTcpProtocol()) ? TCP_PROTOCOL._02 : TCP_PROTOCOL._01);
                return;
            }
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                activity2.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m573xd33fa2b5();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$sendRead03Command1$2$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m572xcd3bd756() {
        initFlowChartByDeviceType();
        this.datalogSnTextView.setText(this.localConnect.getInverter().getDatalogSn());
        this.inverterSnTextView.setText(this.localConnect.getInverter().getSerialNum());
    }

    /* renamed from: lambda$sendRead03Command1$3$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m573xd33fa2b5() {
        clearFragmentData();
        Toast.makeText(getActivity().getApplicationContext(), R.string.local_regular_set_toast_tcp_init_fail, 1).show();
    }

    public void sendRead04Command1() {
        try {
            if (this.localConnect.initialize(true)) {
                if (Tool.isEmpty(this.localConnect.getDatalogSn())) {
                    return;
                }
                final String strSendCommand = this.localConnect.sendCommand("read_04_1", DataFrameFactory.createReadMultiInputDataFrame(this.localConnect.getTcpProtocol(), this.localConnect.getDatalogSn(), 0, 40));
                if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 60) {
                    return;
                }
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m574x1223ceb3(strSendCommand);
                        }
                    });
                }
                sendRead04Command2();
                return;
            }
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                activity2.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m575x18279a12();
                    }
                });
            }
            this.read03Success = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$sendRead04Command1$4$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m574x1223ceb3(String str) {
        Inverter inverter = this.localConnect.getInverter();
        if (inverter == null) {
            this.read03Success = false;
            return;
        }
        int register2 = FrameTool.getRegister2(str, 28);
        int register22 = register2 + 0;
        if (!inverter.isAcCharger()) {
            register22 = register22 + FrameTool.getRegister2(str, 29) + FrameTool.getRegister2(str, 30);
        }
        this.todayYieldingTextView.setText(InvTool.formatDataText10(register22) + " kWh");
        this.todayDischargingTextView.setText(InvTool.formatDataText10(FrameTool.getRegister2(str, 34)) + " kWh");
        int register23 = FrameTool.getRegister2(str, 36);
        this.todayExportTextView.setText(InvTool.formatDataText10(register23) + " kWh");
        int register24 = FrameTool.getRegister2(str, 35) + (FrameTool.getRegister2(str, 37) - register23) + (FrameTool.getRegister2(str, 31) - FrameTool.getRegister2(str, 32));
        if (!inverter.isAcCharger()) {
            register2 = 0;
        }
        this.todayUsageTextView.setText(InvTool.formatDataText10(register24 + register2) + " kWh");
        int register25 = FrameTool.getRegister2(str, 10);
        int register26 = FrameTool.getRegister2(str, 11);
        if (register25 > 0) {
            this.flowBatteryPowerLabelTextView.setText(R.string.main_flow_charge_power);
            this.flowBatteryPowerTextView.setText(register25 + "W");
            this.flowBatteryPowerGifView1.setMovieResource(R.drawable.arrow_left);
            this.flowBatteryPowerGifView2.setMovieResource(R.drawable.arrow_left);
            this.flowBatteryPowerGifView1.setVisibility(0);
            this.flowBatteryPowerGifView2.setVisibility(0);
        } else if (register26 > 0) {
            this.flowBatteryPowerLabelTextView.setText(R.string.main_flow_discharge_power);
            this.flowBatteryPowerTextView.setText(register26 + "W");
            this.flowBatteryPowerGifView1.setMovieResource(R.drawable.arrow_right);
            this.flowBatteryPowerGifView2.setMovieResource(R.drawable.arrow_right);
            this.flowBatteryPowerGifView1.setVisibility(0);
            this.flowBatteryPowerGifView2.setVisibility(0);
        } else {
            this.flowBatteryPowerLabelTextView.setText(R.string.main_flow_battery_power);
            this.flowBatteryPowerTextView.setText("0 W");
            this.flowBatteryPowerGifView1.setVisibility(4);
            this.flowBatteryPowerGifView2.setVisibility(4);
        }
        int regLowInt = FrameTool.getRegLowInt(str, 5);
        this.flowSocPowerTextView.setText(regLowInt + "%");
        if (regLowInt >= 90) {
            this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_5_green);
        } else if (regLowInt >= 70) {
            this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_4_green);
        } else if (regLowInt >= 50) {
            this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_3_green);
        } else if (regLowInt >= 30) {
            this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_2_green);
        } else if (regLowInt >= 10) {
            this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_1_green);
        } else {
            this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_0_green);
        }
        int register27 = FrameTool.getRegister2(str, 7);
        if (inverter.isAcCharger()) {
            this.flowPvPowerGifView.setVisibility(4);
            this.flowAcPvPowerTextView.setText(register27 + "W");
            if (register27 < 30) {
                this.flowAcPvPowerGifView1.setVisibility(4);
                this.flowAcPvPowerGifView2.setVisibility(4);
            } else {
                this.flowAcPvPowerGifView1.setVisibility(0);
                this.flowAcPvPowerGifView2.setVisibility(0);
            }
        } else {
            this.flowAcPvPowerGifView1.setVisibility(4);
            this.flowAcPvPowerGifView2.setVisibility(4);
            int register28 = FrameTool.getRegister2(str, 8) + register27 + FrameTool.getRegister2(str, 9);
            this.flowPvPowerTextView.setText(register28 + "W");
            if (register28 > 0) {
                this.flowPvPowerGifView.setVisibility(0);
            } else {
                this.flowPvPowerGifView.setVisibility(4);
            }
        }
        int register29 = FrameTool.getRegister2(str, 26);
        int register210 = FrameTool.getRegister2(str, 27);
        int i = register29 - register210;
        this.flowGridPowerTextView.setText(Math.abs(i) + "W");
        if (i > 0) {
            this.flowGridPowerLabelTextView.setText(R.string.main_flow_export_power);
            this.flowGridPowerGifView1.setMovieResource(R.drawable.arrow_right);
            this.flowGridPowerGifView2.setMovieResource(R.drawable.arrow_right);
            this.flowGridPowerGifView1.setVisibility(0);
            this.flowGridPowerGifView2.setVisibility(0);
        } else if (i < 0) {
            this.flowGridPowerLabelTextView.setText(R.string.main_flow_import_power);
            this.flowGridPowerGifView1.setMovieResource(R.drawable.arrow_left);
            this.flowGridPowerGifView2.setMovieResource(R.drawable.arrow_left);
            this.flowGridPowerGifView1.setVisibility(0);
            this.flowGridPowerGifView2.setVisibility(0);
        } else {
            this.flowGridPowerLabelTextView.setText("");
            this.flowGridPowerGifView1.setVisibility(4);
            this.flowGridPowerGifView2.setVisibility(4);
        }
        int register211 = FrameTool.getRegister2(str, 16);
        int register212 = FrameTool.getRegister2(str, 17);
        if (register211 > register212) {
            this.flowInverterArrowGifView1.setMovieResource(R.drawable.arrow_right);
            this.flowInverterArrowGifView2.setMovieResource(R.drawable.arrow_right);
            this.flowInverterArrowGifView3.setMovieResource(R.drawable.arrow_right);
            this.flowInverterArrowGifView1.setVisibility(0);
            this.flowInverterArrowGifView2.setVisibility(0);
            this.flowInverterArrowGifView3.setVisibility(0);
        } else if (register211 < register212) {
            this.flowInverterArrowGifView1.setMovieResource(R.drawable.arrow_left);
            this.flowInverterArrowGifView2.setMovieResource(R.drawable.arrow_left);
            this.flowInverterArrowGifView3.setMovieResource(R.drawable.arrow_left);
            this.flowInverterArrowGifView1.setVisibility(0);
            this.flowInverterArrowGifView2.setVisibility(0);
            this.flowInverterArrowGifView3.setVisibility(0);
        } else {
            this.flowInverterArrowGifView1.setVisibility(4);
            this.flowInverterArrowGifView2.setVisibility(4);
            this.flowInverterArrowGifView3.setVisibility(4);
        }
        if (!inverter.isAcCharger()) {
            register27 = 0;
        }
        int i2 = register27 + (register211 - register212) + (register210 - register29);
        if (i2 < 0) {
            i2 = 0;
        }
        this.flowConsumptionPowerTextView.setText(i2 + "W");
        if (i2 > 0) {
            this.flowConsumptionPowerGifView1.setVisibility(0);
            this.flowConsumptionPowerGifView2.setVisibility(0);
        } else {
            this.flowConsumptionPowerGifView1.setVisibility(4);
            this.flowConsumptionPowerGifView2.setVisibility(4);
        }
        if (FrameTool.getRegister2(str, 0) >= 64) {
            int register213 = FrameTool.getRegister2(str, 24);
            this.flowEpsPowerTextView.setText(register213 + "W");
            if (register213 > 0) {
                this.flowEpsPowerGifView.setVisibility(0);
                return;
            } else {
                this.flowEpsPowerGifView.setVisibility(4);
                return;
            }
        }
        this.flowEpsPowerTextView.setText(R.string.main_flow_standby);
        this.flowEpsPowerGifView.setVisibility(4);
    }

    /* renamed from: lambda$sendRead04Command1$5$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m575x18279a12() {
        clearFragmentData();
        Toast.makeText(getActivity().getApplicationContext(), R.string.local_regular_set_toast_tcp_init_fail, 1).show();
    }

    public void sendRead04Command2() {
        try {
            if (this.localConnect.initialize(true)) {
                if (Tool.isEmpty(this.localConnect.getDatalogSn())) {
                    return;
                }
                final String strSendCommand = this.localConnect.sendCommand("read_04_2", DataFrameFactory.createReadMultiInputDataFrame(this.localConnect.getTcpProtocol(), this.localConnect.getDatalogSn(), 40, 40));
                if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 60) {
                    return;
                }
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m576xb269d510(strSendCommand);
                        }
                    });
                }
                if (this.read04_3_Success) {
                    return;
                }
                sendRead04Command3();
                return;
            }
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                activity2.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m577xb86da06f();
                    }
                });
            }
            this.read03Success = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$sendRead04Command2$6$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m576xb269d510(String str) {
        Inverter inverter = this.localConnect.getInverter();
        if (inverter == null) {
            this.read03Success = false;
            return;
        }
        long register4 = FrameTool.getRegister4(str, 0);
        long register2 = register4 + 0;
        if (!inverter.isAcCharger()) {
            register2 = register2 + FrameTool.getRegister2(str, 2) + FrameTool.getRegister2(str, 4);
        }
        this.totalYieldingTextView.setText(InvTool.formatDataText10(register2) + " kWh");
        this.totalDischargingTextView.setText(InvTool.formatDataText10(FrameTool.getRegister4(str, 12)) + " kWh");
        long register42 = FrameTool.getRegister4(str, 16);
        this.totalExportTextView.setText(InvTool.formatDataText10(register42) + " kWh");
        long register43 = FrameTool.getRegister4(str, 14) + (FrameTool.getRegister4(str, 18) - register42) + (FrameTool.getRegister4(str, 6) - FrameTool.getRegister4(str, 8));
        if (!inverter.isAcCharger()) {
            register4 = 0;
        }
        this.totalUsageTextView.setText(InvTool.formatDataText10(register43 + register4) + " kWh");
        this.statusImageView.setImageResource(getStatusResourceId(FrameTool.getRegister4(str, 20) > 0 ? com.google.firebase.messaging.Constants.IPC_BUNDLE_KEY_SEND_ERROR : FrameTool.getRegister4(str, 22) > 0 ? "warning" : "normal"));
        this.localTimeTextView.setText(InvTool.formatDateTime(new Date()));
    }

    /* renamed from: lambda$sendRead04Command2$7$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m577xb86da06f() {
        clearFragmentData();
        Toast.makeText(getActivity().getApplicationContext(), R.string.local_regular_set_toast_tcp_init_fail, 1).show();
    }

    public void sendRead04Command3() {
        FragmentActivity activity;
        try {
            if (this.localConnect.initialize(true)) {
                if (Tool.isEmpty(this.localConnect.getDatalogSn())) {
                    return;
                }
                final String strSendCommand = this.localConnect.sendCommand("read_04_3", DataFrameFactory.createReadMultiInputDataFrame(this.localConnect.getTcpProtocol(), this.localConnect.getDatalogSn(), 80, 40));
                if (Tool.isEmpty(strSendCommand) || strSendCommand.length() <= 60 || (activity = getActivity()) == null) {
                    return;
                }
                activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m578x52afdb6d(strSendCommand);
                    }
                });
                return;
            }
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                activity2.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m579x58b3a6cc();
                    }
                });
            }
            this.read03Success = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$sendRead04Command3$8$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m578x52afdb6d(String str) {
        Integer numValueOf;
        Integer numValueOf2;
        Integer numValueOf3;
        Integer numValueOf4;
        Inverter inverter = this.localConnect.getInverter();
        if (inverter == null || inverter.getModel() == null) {
            this.read03Success = false;
            return;
        }
        BATTERY_TYPE batteryTypeFromModel = inverter.getBatteryTypeFromModel();
        int register2 = FrameTool.getRegister2(str, 0);
        inverter.setBatCurrUnit(Integer.valueOf((register2 >> 10) & 1));
        Integer leadAcidCapacity = null;
        if (BATTERY_TYPE.LITHIUM.equals(BATTERY_TYPE.getBatteryTypeByCode(register2 & 3)) || register2 == 0) {
            numValueOf = Integer.valueOf(FrameTool.getRegister2(str, 1));
            numValueOf2 = Integer.valueOf(FrameTool.getRegister2(str, 2));
            numValueOf3 = Integer.valueOf(FrameTool.getRegister2(str, 16));
            numValueOf4 = Integer.valueOf(FrameTool.getRegister2(str, 17));
        } else {
            numValueOf4 = null;
            numValueOf = null;
            numValueOf2 = null;
            numValueOf3 = null;
        }
        if (BATTERY_TYPE.LITHIUM.equals(batteryTypeFromModel)) {
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(inverter.isBatCurrUnit10() ? 660 : 6600);
            }
            if (numValueOf2 == null) {
                numValueOf2 = Integer.valueOf(inverter.isBatCurrUnit10() ? 660 : 6600);
            }
        }
        if (BATTERY_TYPE.LITHIUM.equals(batteryTypeFromModel) && numValueOf4 != null) {
            leadAcidCapacity = numValueOf4;
        } else if (inverter.getLeadAcidCapacity() != null) {
            leadAcidCapacity = inverter.getLeadAcidCapacity();
        }
        if (numValueOf3 != null) {
            this.battParallelNumLabel.setVisibility(0);
            this.battParallelNumValueLabel.setVisibility(0);
            this.battParallelNumValueLabel.setText(String.valueOf(numValueOf3));
        }
        if (leadAcidCapacity != null) {
            this.battCapacityLabel.setVisibility(0);
            this.battCapacityValueLabel.setVisibility(0);
            this.battCapacityValueLabel.setText(leadAcidCapacity + " Ah");
        }
        if (BATTERY_TYPE.LITHIUM.equals(batteryTypeFromModel)) {
            if (numValueOf != null) {
                this.bmsLimitChargeLabel.setVisibility(0);
                this.bmsLimitChargeValueLabel.setVisibility(0);
                this.bmsLimitChargeValueLabel.setText((numValueOf.intValue() / (inverter.isBatCurrUnit10() ? 10 : 100)) + " A");
            }
            if (numValueOf2 != null) {
                this.bmsLimitDischargeLabel.setVisibility(0);
                this.bmsLimitDischargeValueLabel.setVisibility(0);
                this.bmsLimitDischargeValueLabel.setText((numValueOf2.intValue() / (inverter.isBatCurrUnit10() ? 10 : 100)) + " A");
            }
        }
        this.read04_3_Success = true;
    }

    /* renamed from: lambda$sendRead04Command3$9$com-lux-luxcloud-view-local-fragment-LocalOverviewFragment, reason: not valid java name */
    /* synthetic */ void m579x58b3a6cc() {
        clearFragmentData();
        Toast.makeText(getActivity().getApplicationContext(), R.string.local_regular_set_toast_tcp_init_fail, 1).show();
    }

    private void clearFragmentData() {
        this.todayYieldingTextView.setText("-- kWh");
        this.totalYieldingTextView.setText("-- kWh");
        this.todayDischargingTextView.setText("-- kWh");
        this.totalDischargingTextView.setText("-- kWh");
        this.todayExportTextView.setText("-- kWh");
        this.totalExportTextView.setText("-- kWh");
        this.todayUsageTextView.setText("-- kWh");
        this.totalUsageTextView.setText("-- kWh");
        this.statusImageView.setImageResource(R.drawable.status_offline);
        this.flowBatteryPowerLabelTextView.setText(R.string.main_flow_battery_power);
        this.flowBatteryPowerTextView.setText("");
        this.flowBatteryImageView.setBackgroundResource(R.drawable.flow_icon_battery_0_green);
        this.flowSocPowerTextView.setText("");
        this.flowPvPowerTextView.setText("");
        this.flowAcPvPowerTextView.setText("");
        this.flowGridPowerTextView.setText("");
        this.flowGridPowerLabelTextView.setText("");
        this.flowConsumptionPowerTextView.setText("");
        this.flowEpsPowerTextView.setText("");
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

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getRegisterByBitParam(String str) {
        str.hashCode();
        switch (str) {
            case "BIT_PVCT_SAMPLE_RATIO":
            case "BIT_WORKING_MODE":
            case "BIT_PVCT_SAMPLE_TYPE":
            case "BIT_CT_SAMPLE_RATIO":
                return 110;
            case "BIT_DISCHG_CONTROL_TYPE":
            case "BIT_ON_GRID_EOD_TYPE":
            case "BIT_AC_CHARGE_TYPE":
                return 120;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getBitByBitParam(String str) {
        str.hashCode();
        switch (str) {
            case "BIT_PVCT_SAMPLE_RATIO":
                return 12;
            case "BIT_WORKING_MODE":
                return 11;
            case "BIT_DISCHG_CONTROL_TYPE":
                return 4;
            case "BIT_PVCT_SAMPLE_TYPE":
                return 8;
            case "BIT_CT_SAMPLE_RATIO":
                return 5;
            case "BIT_ON_GRID_EOD_TYPE":
                return 6;
            case "BIT_AC_CHARGE_TYPE":
                return 1;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getBitSizeByBitParam(java.lang.String r8) {
        /*
            r7 = this;
            r8.hashCode()
            int r0 = r8.hashCode()
            r1 = 3
            r2 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            r4 = 2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r6 = -1
            switch(r0) {
                case -1236111220: goto L58;
                case -503707613: goto L4f;
                case 189011893: goto L44;
                case 1068586617: goto L39;
                case 1073806034: goto L2e;
                case 1642564362: goto L23;
                case 1775981274: goto L18;
                default: goto L16;
            }
        L16:
            r2 = r6
            goto L62
        L18:
            java.lang.String r0 = "BIT_AC_CHARGE_TYPE"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L21
            goto L16
        L21:
            r2 = 6
            goto L62
        L23:
            java.lang.String r0 = "BIT_ON_GRID_EOD_TYPE"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L2c
            goto L16
        L2c:
            r2 = 5
            goto L62
        L2e:
            java.lang.String r0 = "BIT_CT_SAMPLE_RATIO"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L37
            goto L16
        L37:
            r2 = 4
            goto L62
        L39:
            java.lang.String r0 = "BIT_PVCT_SAMPLE_TYPE"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L42
            goto L16
        L42:
            r2 = r1
            goto L62
        L44:
            java.lang.String r0 = "BIT_DISCHG_CONTROL_TYPE"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L4d
            goto L16
        L4d:
            r2 = r4
            goto L62
        L4f:
            java.lang.String r0 = "BIT_WORKING_MODE"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L62
            goto L16
        L58:
            java.lang.String r0 = "BIT_PVCT_SAMPLE_RATIO"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L61
            goto L16
        L61:
            r2 = 0
        L62:
            switch(r2) {
                case 0: goto L6f;
                case 1: goto L6e;
                case 2: goto L6d;
                case 3: goto L6d;
                case 4: goto L6d;
                case 5: goto L6c;
                case 6: goto L67;
                default: goto L65;
            }
        L65:
            r8 = 0
            return r8
        L67:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)
            return r8
        L6c:
            return r3
        L6d:
            return r5
        L6e:
            return r3
        L6f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.getBitSizeByBitParam(java.lang.String):java.lang.Integer");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public Integer getStartRegisterByParam(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2146680827:
                if (str.equals("HOLD_UVF_DROOP_KUF")) {
                    c = 0;
                    break;
                }
                break;
            case -2110570028:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE")) {
                    c = 1;
                    break;
                }
                break;
            case -2106433585:
                if (str.equals("HOLD_GRID_FREQ_CONN_LOW")) {
                    c = 2;
                    break;
                }
                break;
            case -2092080778:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR")) {
                    c = 3;
                    break;
                }
                break;
            case -2091729313:
                if (str.equals("HOLD_AC_CHARGE_END_TIME")) {
                    c = 4;
                    break;
                }
                break;
            case -2070600516:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW_TIME")) {
                    c = 5;
                    break;
                }
                break;
            case -2064663285:
                if (str.equals("HOLD_SET_COMPOSED_PHASE")) {
                    c = 6;
                    break;
                }
                break;
            case -2051169280:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG")) {
                    c = 7;
                    break;
                }
                break;
            case -2012582261:
                if (str.equals("HOLD_FEED_IN_GRID_POWER_PERCENT")) {
                    c = '\b';
                    break;
                }
                break;
            case -1922746271:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_VOLT_REF")) {
                    c = '\t';
                    break;
                }
                break;
            case -1917271147:
                if (str.equals("_12K_HOLD_START_PV_POWER")) {
                    c = '\n';
                    break;
                }
                break;
            case -1910210817:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_1")) {
                    c = 11;
                    break;
                }
                break;
            case -1910210816:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_2")) {
                    c = '\f';
                    break;
                }
                break;
            case -1907123448:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_END_VOLT")) {
                    c = '\r';
                    break;
                }
                break;
            case -1905186084:
                if (str.equals("_12K_HOLD_SMART_LOAD_END_VOLT")) {
                    c = 14;
                    break;
                }
                break;
            case -1876698434:
                if (str.equals("HOLD_FORCED_DISCHG_SOC_LIMIT")) {
                    c = 15;
                    break;
                }
                break;
            case -1862575828:
                if (str.equals("_12K_HOLD_SMART_LOAD_END_SOC")) {
                    c = 16;
                    break;
                }
                break;
            case -1854323434:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG")) {
                    c = 17;
                    break;
                }
                break;
            case -1790606454:
                if (str.equals("HOLD_PEAK_SHAVING_END_TIME_1")) {
                    c = 18;
                    break;
                }
                break;
            case -1790606453:
                if (str.equals("HOLD_PEAK_SHAVING_END_TIME_2")) {
                    c = 19;
                    break;
                }
                break;
            case -1784648912:
                if (str.equals("HOLD_P_TO_USER_START_DISCHG")) {
                    c = 20;
                    break;
                }
                break;
            case -1774171999:
                if (str.equals("_12K_HOLD_OVF_DERATE_START_POINT")) {
                    c = 21;
                    break;
                }
                break;
            case -1738912721:
                if (str.equals("HOLD_EQUALIZATION_VOLTAGE")) {
                    c = 22;
                    break;
                }
                break;
            case -1724289777:
                if (str.equals("HOLD_UVF_DERATE_START_POINT")) {
                    c = 23;
                    break;
                }
                break;
            case -1670871462:
                if (str.equals("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR")) {
                    c = 24;
                    break;
                }
                break;
            case -1662706451:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_1")) {
                    c = 25;
                    break;
                }
                break;
            case -1662706450:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_2")) {
                    c = 26;
                    break;
                }
                break;
            case -1657690225:
                if (str.equals("HOLD_COM_ADDR")) {
                    c = 27;
                    break;
                }
                break;
            case -1612429665:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_1")) {
                    c = 28;
                    break;
                }
                break;
            case -1612429664:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_2")) {
                    c = 29;
                    break;
                }
                break;
            case -1605958379:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG")) {
                    c = 30;
                    break;
                }
                break;
            case -1567068683:
                if (str.equals("_12K_HOLD_SMART_LOAD_START_VOLT")) {
                    c = 31;
                    break;
                }
                break;
            case -1563900533:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR")) {
                    c = ' ';
                    break;
                }
                break;
            case -1563549068:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME")) {
                    c = '!';
                    break;
                }
                break;
            case -1551617594:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR")) {
                    c = '\"';
                    break;
                }
                break;
            case -1551266129:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME")) {
                    c = '#';
                    break;
                }
                break;
            case -1536953061:
                if (str.equals("_12K_HOLD_STOP_DISCHG_VOLT")) {
                    c = '$';
                    break;
                }
                break;
            case -1524255375:
                if (str.equals("HOLD_PV_INPUT_MODE")) {
                    c = '%';
                    break;
                }
                break;
            case -1475031011:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE")) {
                    c = '&';
                    break;
                }
                break;
            case -1474656009:
                if (str.equals("_12K_HOLD_GRID_REGULATION")) {
                    c = '\'';
                    break;
                }
                break;
            case -1448232788:
                if (str.equals("_12K_HOLD_AC_COUPLE_START_VOLT")) {
                    c = '(';
                    break;
                }
                break;
            case -1433471711:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
                    c = ')';
                    break;
                }
                break;
            case -1413838822:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
                    c = '*';
                    break;
                }
                break;
            case -1376342106:
                if (str.equals("HOLD_VREF_ADJUSTMENT_TIME_CONSTANT")) {
                    c = '+';
                    break;
                }
                break;
            case -1367869989:
                if (str.equals("ALL_TO_DEFAULT")) {
                    c = ',';
                    break;
                }
                break;
            case -1352201891:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
                    c = '-';
                    break;
                }
                break;
            case -1345314128:
                if (str.equals("HOLD_EQUALIZATION_PERIOD")) {
                    c = '.';
                    break;
                }
                break;
            case -1323572740:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
                    c = '/';
                    break;
                }
                break;
            case -1294943589:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
                    c = '0';
                    break;
                }
                break;
            case -1288210873:
                if (str.equals("_12K_HOLD_GRID_TYPE")) {
                    c = '1';
                    break;
                }
                break;
            case -1282654362:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
                    c = '2';
                    break;
                }
                break;
            case -1274671800:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_1")) {
                    c = '3';
                    break;
                }
                break;
            case -1274671799:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_2")) {
                    c = '4';
                    break;
                }
                break;
            case -1254025211:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
                    c = '5';
                    break;
                }
                break;
            case -1225396060:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
                    c = '6';
                    break;
                }
                break;
            case -1119226968:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_1")) {
                    c = '7';
                    break;
                }
                break;
            case -1119226967:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_2")) {
                    c = '8';
                    break;
                }
                break;
            case -1033230202:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_1")) {
                    c = '9';
                    break;
                }
                break;
            case -1033230201:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_2")) {
                    c = ':';
                    break;
                }
                break;
            case -1028461968:
                if (str.equals("HOLD_MIN_Q_PERCENT_FOR_QV")) {
                    c = ';';
                    break;
                }
                break;
            case -917268805:
                if (str.equals("HOLD_FORCED_DISCHG_POWER_CMD")) {
                    c = Typography.less;
                    break;
                }
                break;
            case -875057049:
                if (str.equals("HOLD_GRID_FREQ_CONN_HIGH")) {
                    c = '=';
                    break;
                }
                break;
            case -823881906:
                if (str.equals("HOLD_MAX_AC_INPUT_POWER")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case -750853128:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_1")) {
                    c = '?';
                    break;
                }
                break;
            case -750853127:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_2")) {
                    c = '@';
                    break;
                }
                break;
            case -659862417:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH_TIME")) {
                    c = 'A';
                    break;
                }
                break;
            case -659327994:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH_TIME")) {
                    c = 'B';
                    break;
                }
                break;
            case -625075712:
                if (str.equals("HOLD_VOLT_WATT_DELAY_TIME")) {
                    c = 'C';
                    break;
                }
                break;
            case -613454474:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE")) {
                    c = 'D';
                    break;
                }
                break;
            case -595561232:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW")) {
                    c = 'E';
                    break;
                }
                break;
            case -594637711:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW")) {
                    c = 'F';
                    break;
                }
                break;
            case -593714190:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW")) {
                    c = 'G';
                    break;
                }
                break;
            case -573009974:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
                    c = 'H';
                    break;
                }
                break;
            case -550997124:
                if (str.equals("HOLD_EQUALIZATION_TIME")) {
                    c = 'I';
                    break;
                }
                break;
            case -535537138:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2")) {
                    c = 'J';
                    break;
                }
                break;
            case -444930136:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_1")) {
                    c = 'K';
                    break;
                }
                break;
            case -444930135:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_2")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_LT;
                    break;
                }
                break;
            case -431364128:
                if (str.equals("HOLD_FORCED_CHG_SOC_LIMIT")) {
                    c = 'M';
                    break;
                }
                break;
            case -413095263:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_1")) {
                    c = 'N';
                    break;
                }
                break;
            case -413095262:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_2")) {
                    c = 'O';
                    break;
                }
                break;
            case -386262971:
                if (str.equals("HOLD_OVF_DROOP_KOF")) {
                    c = 'P';
                    break;
                }
                break;
            case -384118065:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR")) {
                    c = 'Q';
                    break;
                }
                break;
            case -383766600:
                if (str.equals("HOLD_AC_CHARGE_START_TIME")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                    break;
                }
                break;
            case -307531482:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE")) {
                    c = 'S';
                    break;
                }
                break;
            case -276744627:
                if (str.equals("HOLD_TIME")) {
                    c = 'T';
                    break;
                }
                break;
            case -276676643:
                if (str.equals("HOLD_VREF")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_UT;
                    break;
                }
                break;
            case -233655236:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_RATE")) {
                    c = 'V';
                    break;
                }
                break;
            case -214155815:
                if (str.equals("HOLD_DELAY_TIME_FOR_OVER_F_DERATE")) {
                    c = 'W';
                    break;
                }
                break;
            case -213248243:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_SOC")) {
                    c = 'X';
                    break;
                }
                break;
            case -165590897:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_1")) {
                    c = 'Y';
                    break;
                }
                break;
            case -165590896:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_2")) {
                    c = Matrix.MATRIX_TYPE_ZERO;
                    break;
                }
                break;
            case -135057669:
                if (str.equals("HOLD_DISCHG_POWER_PERCENT_CMD")) {
                    c = '[';
                    break;
                }
                break;
            case -107172271:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_1")) {
                    c = '\\';
                    break;
                }
                break;
            case -107172270:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_2")) {
                    c = ']';
                    break;
                }
                break;
            case -26098721:
                if (str.equals("HOLD_REACTIVE_POWER_CMD_TYPE")) {
                    c = '^';
                    break;
                }
                break;
            case 41202161:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH_TIME")) {
                    c = '_';
                    break;
                }
                break;
            case 41736584:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH_TIME")) {
                    c = '`';
                    break;
                }
                break;
            case 51556766:
                if (str.equals("HOLD_MAX_Q_PERCENT_FOR_QV")) {
                    c = 'a';
                    break;
                }
                break;
            case 106114747:
                if (str.equals("HOLD_CT_POWER_OFFSET")) {
                    c = 'b';
                    break;
                }
                break;
            case 124739648:
                if (str.equals("HOLD_ON_GRID_EOD_VOLTAGE")) {
                    c = 'c';
                    break;
                }
                break;
            case 127930925:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR")) {
                    c = 'd';
                    break;
                }
                break;
            case 128282390:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME")) {
                    c = 'e';
                    break;
                }
                break;
            case 159565091:
                if (str.equals("HOLD_MAINTENANCE_COUNT")) {
                    c = 'f';
                    break;
                }
                break;
            case 168754545:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW_TIME")) {
                    c = 'g';
                    break;
                }
                break;
            case 180157537:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG")) {
                    c = 'h';
                    break;
                }
                break;
            case 219635409:
                if (str.equals("HOLD_PF_CMD")) {
                    c = 'i';
                    break;
                }
                break;
            case 221415899:
                if (str.equals("HOLD_EPS_VOLT_SET")) {
                    c = 'j';
                    break;
                }
                break;
            case 229729985:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_1")) {
                    c = 'k';
                    break;
                }
                break;
            case 229729986:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_2")) {
                    c = 'l';
                    break;
                }
                break;
            case 265183773:
                if (str.equals("HOLD_START_PV_VOLT")) {
                    c = 'm';
                    break;
                }
                break;
            case 268168589:
                if (str.equals("HOLD_V1H")) {
                    c = 'n';
                    break;
                }
                break;
            case 268168593:
                if (str.equals("HOLD_V1L")) {
                    c = 'o';
                    break;
                }
                break;
            case 268168620:
                if (str.equals("HOLD_V2H")) {
                    c = 'p';
                    break;
                }
                break;
            case 268168624:
                if (str.equals("HOLD_V2L")) {
                    c = 'q';
                    break;
                }
                break;
            case 305030215:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_START_SOC")) {
                    c = 'r';
                    break;
                }
                break;
            case 313840816:
                if (str.equals("HOLD_GRID_VOLT_CONN_HIGH")) {
                    c = 's';
                    break;
                }
                break;
            case 330144381:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_1")) {
                    c = 't';
                    break;
                }
                break;
            case 330144382:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_2")) {
                    c = 'u';
                    break;
                }
                break;
            case 365088499:
                if (str.equals("_12K_HOLD_SMART_LOAD_START_SOC")) {
                    c = 'v';
                    break;
                }
                break;
            case 365268050:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW_TIME")) {
                    c = 'w';
                    break;
                }
                break;
            case 367128639:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE")) {
                    c = 'x';
                    break;
                }
                break;
            case 390956452:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR")) {
                    c = 'y';
                    break;
                }
                break;
            case 391307917:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME")) {
                    c = 'z';
                    break;
                }
                break;
            case 467543035:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE")) {
                    c = '{';
                    break;
                }
                break;
            case 485996371:
                if (str.equals("_12K_HOLD_AC_COUPLE_END_VOLT")) {
                    c = '|';
                    break;
                }
                break;
            case 528065501:
                if (str.equals("HOLD_FORCED_CHG_POWER_CMD")) {
                    c = '}';
                    break;
                }
                break;
            case 561781555:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW_TIME")) {
                    c = '~';
                    break;
                }
                break;
            case 565590738:
                if (str.equals("HOLD_EPS_FREQ_SET")) {
                    c = Ascii.MAX;
                    break;
                }
                break;
            case 567487850:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_1")) {
                    c = 128;
                    break;
                }
                break;
            case 567487851:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_2")) {
                    c = 129;
                    break;
                }
                break;
            case 574683163:
                if (str.equals("HOLD_SET_MASTER_OR_SLAVE")) {
                    c = 130;
                    break;
                }
                break;
            case 614838895:
                if (str.equals("_12K_HOLD_CHARGE_FIRST_VOLT")) {
                    c = 131;
                    break;
                }
                break;
            case 623306801:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_1")) {
                    c = 132;
                    break;
                }
                break;
            case 623306802:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_2")) {
                    c = 133;
                    break;
                }
                break;
            case 667902246:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_1")) {
                    c = 134;
                    break;
                }
                break;
            case 667902247:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_2")) {
                    c = 135;
                    break;
                }
                break;
            case 677731373:
                if (str.equals("HOLD_ACTIVE_POWER_PERCENT_CMD")) {
                    c = 136;
                    break;
                }
                break;
            case 695812793:
                if (str.equals("HOLD_VOLT_WATT_P2")) {
                    c = 137;
                    break;
                }
                break;
            case 695812978:
                if (str.equals("HOLD_VOLT_WATT_V1")) {
                    c = 138;
                    break;
                }
                break;
            case 695812979:
                if (str.equals("HOLD_VOLT_WATT_V2")) {
                    c = 139;
                    break;
                }
                break;
            case 767099658:
                if (str.equals("HOLD_DISCHG_CUT_OFF_SOC_EOD")) {
                    c = 140;
                    break;
                }
                break;
            case 814992216:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_1")) {
                    c = 141;
                    break;
                }
                break;
            case 814992217:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_2")) {
                    c = 142;
                    break;
                }
                break;
            case 866091809:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_START_VOLT")) {
                    c = 143;
                    break;
                }
                break;
            case 894260725:
                if (str.equals("HOLD_AC_CHARGE_SOC_LIMIT")) {
                    c = 144;
                    break;
                }
                break;
            case 946846866:
                if (str.equals("HOLD_SOC_LOW_LIMIT_EPS_DISCHG")) {
                    c = 145;
                    break;
                }
                break;
            case 1039780741:
                if (str.equals("HOLD_FLOATING_VOLTAGE")) {
                    c = 146;
                    break;
                }
                break;
            case 1118506598:
                if (str.equals("HOLD_GRID_VOLT_CONN_LOW")) {
                    c = 147;
                    break;
                }
                break;
            case 1203310617:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW")) {
                    c = 148;
                    break;
                }
                break;
            case 1204234138:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW")) {
                    c = 149;
                    break;
                }
                break;
            case 1205157659:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW")) {
                    c = 150;
                    break;
                }
                break;
            case 1224119563:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_POWER")) {
                    c = 151;
                    break;
                }
                break;
            case 1226871680:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2")) {
                    c = 152;
                    break;
                }
                break;
            case 1457278549:
                if (str.equals("HOLD_RECONNECT_TIME")) {
                    c = 153;
                    break;
                }
                break;
            case 1477300572:
                if (str.equals("_12K_HOLD_AC_COUPLE_START_SOC")) {
                    c = 154;
                    break;
                }
                break;
            case 1504508315:
                if (str.equals("HOLD_VBAT_START_DERATING")) {
                    c = 155;
                    break;
                }
                break;
            case 1527620681:
                if (str.equals("HOLD_MAX_GENERATOR_INPUT_POWER")) {
                    c = 156;
                    break;
                }
                break;
            case 1539695061:
                if (str.equals("_12K_HOLD_AC_COUPLE_END_SOC")) {
                    c = 157;
                    break;
                }
                break;
            case 1600103487:
                if (str.equals("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
                    c = 158;
                    break;
                }
                break;
            case 1613415905:
                if (str.equals("HOLD_DELAY_TIME_FOR_QV_CURVE")) {
                    c = 159;
                    break;
                }
                break;
            case 1809765729:
                if (str.equals("HOLD_P1")) {
                    c = Typography.nbsp;
                    break;
                }
                break;
            case 1809765730:
                if (str.equals("HOLD_P2")) {
                    c = 161;
                    break;
                }
                break;
            case 1809765731:
                if (str.equals("HOLD_P3")) {
                    c = Typography.cent;
                    break;
                }
                break;
            case 1809765762:
                if (str.equals("HOLD_Q3")) {
                    c = Typography.pound;
                    break;
                }
                break;
            case 1809765763:
                if (str.equals("HOLD_Q4")) {
                    c = 164;
                    break;
                }
                break;
            case 1831339770:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW_TIME")) {
                    c = 165;
                    break;
                }
                break;
            case 1838153520:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH_TIME")) {
                    c = 166;
                    break;
                }
                break;
            case 1838687943:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH_TIME")) {
                    c = Typography.section;
                    break;
                }
                break;
            case 1853690354:
                if (str.equals("HOLD_AC_CHARGE_POWER_CMD")) {
                    c = 168;
                    break;
                }
                break;
            case 1890826442:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_SOC")) {
                    c = Typography.copyright;
                    break;
                }
                break;
            case 1910924292:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_37")) {
                    c = 170;
                    break;
                }
                break;
            case 1910924293:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_38")) {
                    c = 171;
                    break;
                }
                break;
            case 1910924294:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_39")) {
                    c = 172;
                    break;
                }
                break;
            case 1910924316:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_40")) {
                    c = 173;
                    break;
                }
                break;
            case 1910924317:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_41")) {
                    c = Typography.registered;
                    break;
                }
                break;
            case 1910924318:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_42")) {
                    c = 175;
                    break;
                }
                break;
            case 1910924319:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_43")) {
                    c = Typography.degree;
                    break;
                }
                break;
            case 1910924320:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_44")) {
                    c = Typography.plusMinus;
                    break;
                }
                break;
            case 1925850636:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_RATE")) {
                    c = 178;
                    break;
                }
                break;
            case 1979328795:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_VOLT")) {
                    c = 179;
                    break;
                }
                break;
            case 1985523803:
                if (str.equals("HOLD_CHARGE_POWER_PERCENT_CMD")) {
                    c = 180;
                    break;
                }
                break;
            case 1989908579:
                if (str.equals("HOLD_PEAK_SHAVING_START_TIME_1")) {
                    c = 181;
                    break;
                }
                break;
            case 1989908580:
                if (str.equals("HOLD_PEAK_SHAVING_START_TIME_2")) {
                    c = Typography.paragraph;
                    break;
                }
                break;
            case 2016686976:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_END_SOC")) {
                    c = Typography.middleDot;
                    break;
                }
                break;
            case 2020464802:
                if (str.equals("HOLD_CONNECT_TIME")) {
                    c = 184;
                    break;
                }
                break;
            case 2027853275:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW_TIME")) {
                    c = 185;
                    break;
                }
                break;
            case 2036981088:
                if (str.equals("HOLD_REACTIVE_POWER_PERCENT_CMD")) {
                    c = 186;
                    break;
                }
                break;
            case 2046998614:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_1")) {
                    c = 187;
                    break;
                }
                break;
            case 2046998615:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_2")) {
                    c = 188;
                    break;
                }
                break;
            case 2069975635:
                if (str.equals("HOLD_POWER_SOFT_START_SLOPE")) {
                    c = Typography.half;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 193;
            case 1:
            case 'y':
            case 'z':
                return 84;
            case 2:
                return 27;
            case 3:
            case 4:
            case 'S':
                return 69;
            case 5:
                return 31;
            case 6:
                return 113;
            case 7:
                return 108;
            case '\b':
                return 103;
            case '\t':
                return 99;
            case '\n':
                return 217;
            case 11:
            case '9':
            case 187:
                return 86;
            case '\f':
            case ':':
            case 188:
                return 88;
            case '\r':
                return 195;
            case 14:
                return 214;
            case 15:
                return 83;
            case 16:
                return 216;
            case 17:
                return 106;
            case 18:
            case 172:
            case 173:
                return 210;
            case 19:
            case 176:
            case 177:
                return 212;
            case 20:
                return 116;
            case 21:
                return 115;
            case 22:
                return 149;
            case 23:
                return Integer.valueOf(CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA);
            case 24:
                return 198;
            case 25:
            case 't':
            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA /* 134 */:
                return 87;
            case 26:
            case 'u':
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA /* 135 */:
                return 89;
            case 27:
                return 15;
            case 28:
            case '3':
            case 'Y':
                return 79;
            case 29:
            case '4':
            case 'Z':
                return 81;
            case 30:
                return 107;
            case 31:
                return 213;
            case ' ':
            case '!':
            case '{':
                return 85;
            case '\"':
            case '#':
            case 'D':
                return 76;
            case '$':
                return 202;
            case '%':
                return 20;
            case '&':
            case 'd':
            case 'e':
                return 77;
            case '\'':
                return 203;
            case '(':
                return 222;
            case ')':
                return 158;
            case '*':
                return 159;
            case '+':
                return Integer.valueOf(CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256);
            case ',':
                return 11;
            case '-':
                return 43;
            case '.':
                return 150;
            case '/':
                return 47;
            case '0':
                return 51;
            case '1':
                return 205;
            case '2':
                return 30;
            case '5':
                return 34;
            case '6':
                return 38;
            case '7':
            case '?':
            case 'N':
                return 78;
            case '8':
            case '@':
            case 'O':
                return 80;
            case ';':
                return 121;
            case '<':
                return 82;
            case '=':
                return 28;
            case '>':
                return 176;
            case 'A':
                return 45;
            case 'B':
                return 32;
            case 'C':
                return 183;
            case 'E':
                return 29;
            case 'F':
                return 33;
            case 'G':
                return 37;
            case 'H':
                return 100;
            case 'I':
                return 151;
            case 'J':
                return 219;
            case 'K':
            case '\\':
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /* 141 */:
                return 71;
            case 'L':
            case ']':
            case 142:
                return 73;
            case 'M':
                return 75;
            case 'P':
                return 136;
            case 'Q':
            case 'R':
            case 'x':
                return 68;
            case 'T':
                return 12;
            case 'U':
                return 185;
            case 'V':
                return 102;
            case 'W':
                return 97;
            case 'X':
                return 207;
            case '[':
                return 65;
            case '^':
                return 59;
            case '_':
                return 53;
            case '`':
                return 40;
            case 'a':
                return 54;
            case 'b':
                return 119;
            case 'c':
                return 169;
            case 'f':
                return 122;
            case 'g':
                return 52;
            case 'h':
                return 109;
            case 'i':
                return 62;
            case 'j':
                return 90;
            case 'k':
            case 128:
            case 132:
                return 70;
            case 'l':
            case Opcodes.LOR /* 129 */:
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA /* 133 */:
                return 72;
            case 'm':
                return 22;
            case 'n':
                return 57;
            case 'o':
                return 55;
            case 'p':
                return 58;
            case 'q':
                return 56;
            case 'r':
                return Integer.valueOf(CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256);
            case 's':
                return 26;
            case 'v':
                return 215;
            case 'w':
                return 48;
            case '|':
                return 223;
            case '}':
                return 74;
            case '~':
                return 44;
            case 127:
                return 91;
            case Opcodes.IXOR /* 130 */:
                return 112;
            case 131:
                return 201;
            case 136:
                return 60;
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA /* 137 */:
                return 184;
            case CipherSuite.TLS_PSK_WITH_RC4_128_SHA /* 138 */:
                return 181;
            case 139:
                return 182;
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /* 140 */:
                return 105;
            case 143:
                return 194;
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /* 144 */:
                return 67;
            case 145:
                return 125;
            case 146:
                return Integer.valueOf(CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA);
            case 147:
                return 25;
            case 148:
                return 42;
            case 149:
                return 46;
            case 150:
                return 50;
            case 151:
                return 206;
            case 152:
                return 218;
            case 153:
                return 24;
            case 154:
                return 220;
            case 155:
                return 118;
            case 156:
                return 177;
            case 157:
                return 221;
            case 158:
                return 41;
            case 159:
                return 96;
            case 160:
                return Integer.valueOf(CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256);
            case 161:
                return 190;
            case 162:
                return 191;
            case 163:
                return 187;
            case 164:
                return 188;
            case 165:
                return 39;
            case 166:
                return 49;
            case 167:
                return 36;
            case CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256 /* 168 */:
                return 66;
            case 169:
                return 160;
            case 170:
            case 171:
            case 181:
                return 209;
            case 174:
            case 175:
            case 182:
                return Integer.valueOf(Primes.SMALL_FACTOR_LIMIT);
            case 178:
                return 101;
            case 179:
                return 208;
            case 180:
                return 64;
            case 183:
                return Integer.valueOf(CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256);
            case 184:
                return 23;
            case 185:
                return 35;
            case CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 186 */:
                return 61;
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 189 */:
                return 63;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:265:0x03a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getValueByParam(java.lang.String r5, java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 1352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.getValueByParam(java.lang.String, java.lang.String):java.lang.Integer");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getRegisterByFunction(String str) {
        str.hashCode();
        switch (str) {
            case "FUNC_PV_GRID_OFF_EN":
            case "FUNC_TAKE_LOAD_TOGETHER":
            case "FUNC_BUZZER_EN":
            case "FUNC_RUN_WITHOUT_GRID":
            case "FUNC_MICRO_GRID_EN":
            case "FUNC_GREEN_EN":
            case "FUNC_CHARGE_LAST":
            case "FUNC_BAT_SHARED":
                return 110;
            case "FUNC_ON_GRID_ALWAYS_ON":
            case "FUNC_GEN_PEAK_SHAVING":
            case "FUNC_BAT_CHARGE_CONTROL":
            case "FUNC_CT_DIRECTION_REVERSED":
            case "FUNC_TRIP_TIME_UNIT":
            case "FUNC_PV_SELL_TO_GRID_EN":
            case "FUNC_WATT_VOLT_EN":
            case "FUNC_PV_ARC_FAULT_CLEAR":
            case "FUNC_GRID_PEAK_SHAVING":
            case "FUNC_PV_ARC":
            case "FUNC_BAT_DISCHARGE_CONTROL":
            case "FUNC_ACTIVE_POWER_LIMIT_MODE":
                return 179;
            case "FUNC_GEN_CTRL":
            case "FUNC_RUN_WITHOUT_GRID_12K":
                return 226;
            case "FUNC_AC_CHARGE":
            case "FUNC_DRMS_EN":
            case "FUNC_SET_TO_STANDBY":
            case "FUNC_FEED_IN_GRID_EN":
            case "FUNC_EPS_EN":
            case "FUNC_FORCED_CHG_EN":
            case "FUNC_FORCED_DISCHG_EN":
                return 21;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getBitByFunction(java.lang.String r27) {
        /*
            Method dump skipped, instructions count: 690
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOverviewFragment.getBitByFunction(java.lang.String):java.lang.Integer");
    }

    private static Integer toInt(String str) {
        try {
            return Integer.valueOf(str.toLowerCase().contains("0x") ? Integer.parseInt(str.substring(2), 16) : Integer.parseInt(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Double toDouble(String str) {
        try {
            return Double.valueOf(str.toLowerCase().contains("0x") ? Integer.parseInt(str.substring(2), 16) : Double.parseDouble(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int checkNegativeValue(String str, int i) {
        str.hashCode();
        return (str.equals("HOLD_CT_POWER_OFFSET") && i < 0) ? 65535 & i : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject createSuccessJSONObject() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("success", true);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject createFailureJSONObject(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("success", false);
            jSONObject.put("msg", str);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toast(JSONObject jSONObject) throws Exception {
        if (jSONObject == null) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.phrase_toast_network_error, 1).show();
            return;
        }
        String string = jSONObject.getString("msg");
        string.hashCode();
        switch (string) {
            case "DEVICE_OFFLINE":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_device_offline, 1).show();
                break;
            case "ACTION_ERROR_UNDONE":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_set_undo, 1).show();
                break;
            case "DATAFRAME_TIMEOUT":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_timeout, 1).show();
                break;
            case "INTEGER_FORMAT_ERROR":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_alert_param_should_int, 1).show();
                break;
            case "PARAM_EMPTY":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_alert_param_empty, 1).show();
                break;
            case "PARAM_ERROR":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_param_error, 1).show();
                break;
            case "DATAFRAME_UNSEND":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_command_not_send, 1).show();
                break;
            case "REMOTE_READ_ERROR":
            case "REMOTE_SET_ERROR":
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.page_maintain_remote_set_result_failed) + " " + jSONObject.getInt("errorCode"), 1).show();
                break;
            case "SERVER_HTTP_EXCEPTION":
                Toast.makeText(getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_server_exception, 1).show();
                break;
            case "OUT_RANGE_ERROR":
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.page_maintain_remote_set_alert_param_out_range), 1).show();
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), R.string.local_set_result_failed, 1).show();
                break;
        }
    }
}