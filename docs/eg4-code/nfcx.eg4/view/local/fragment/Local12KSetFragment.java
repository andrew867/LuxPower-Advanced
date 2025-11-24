package com.nfcx.eg4.view.local.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import com.google.common.base.Ascii;
import com.nfcx.eg4.R;
import com.nfcx.eg4.connect.LocalConnect;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.inverter.Model;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE;
import com.nfcx.eg4.global.bean.set.RemoteReadInfo;
import com.nfcx.eg4.global.bean.set.RemoteWriteInfo;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.tool.InvTool;
import com.nfcx.eg4.tool.PinTool;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.local.LocalActivity;
import com.nfcx.eg4.view.local.ulCompliance.ULComplianceActivity;
import com.nfcx.eg4.view.login.LoginActivity;
import com.nfcx.eg4.view.main.fragment.lv1.AbstractItemFragment;
import com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity;
import com.nfcx.eg4.view.updateFirmware.UpdateFirmwareActivity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.text.Typography;
import org.bouncycastle.i18n.LocalizedMessage;
import org.bouncycastle.math.Primes;
import org.bouncycastle.pqc.legacy.math.linearalgebra.Matrix;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Local12KSetFragment extends AbstractItemFragment {
    private String[] _110Functions;
    private String[] _179Functions;
    private String[] _21Functions;
    private String[] _226Functions;
    private LinearLayout absorbVoltageLayout;
    private Button absorbVoltageSetButton;
    private EditText absorbVoltageSetEditText;
    private Button acChargeEndBatteryVoltageButton;
    private EditText acChargeEndBatteryVoltageEditText;
    private EditText acChargeEndHour1EditText;
    private EditText acChargeEndHour2EditText;
    private EditText acChargeEndHourEditText;
    private EditText acChargeEndMinute1EditText;
    private EditText acChargeEndMinute2EditText;
    private EditText acChargeEndMinuteEditText;
    private Button acChargeEndTime1Button;
    private Button acChargeEndTime2Button;
    private Button acChargeEndTimeButton;
    private ToggleButton acChargeFunctionButton;
    private Button acChargePowerButton;
    private EditText acChargePowerEditText;
    private Button acChargeSocLimitButton;
    private EditText acChargeSocLimitEditText;
    private EditText acChargeStartHour1EditText;
    private EditText acChargeStartHour2EditText;
    private EditText acChargeStartHourEditText;
    private EditText acChargeStartMinute1EditText;
    private EditText acChargeStartMinute2EditText;
    private EditText acChargeStartMinuteEditText;
    private Button acChargeStartTime1Button;
    private Button acChargeStartTime2Button;
    private Button acChargeStartTimeButton;
    private Button acCoupleEndSocButton;
    private EditText acCoupleEndSocEditText;
    private Button acCoupleEndVoltButton;
    private EditText acCoupleEndVoltEditText;
    private Button acCoupleStartSocButton;
    private EditText acCoupleStartSocEditText;
    private Button acCoupleStartVoltButton;
    private EditText acCoupleStartVoltEditText;
    private ConstraintLayout advancedSetActionLayout;
    private TextView advancedSetActionTextView;
    private ToggleButton advancedSetActionToggleButton;
    private LinearLayout advancedSetParamLayout;
    private Button all2DefaultButton;
    private ConstraintLayout applicationSetActionLayout;
    private TextView applicationSetActionTextView;
    private ToggleButton applicationSetActionToggleButton;
    private LinearLayout applicationSetParamLayout;
    private ConstraintLayout applicationSetTitleLayout;
    private Button batChargeControlButton;
    private Spinner batChargeControlSpinner;
    private Button batChargeCurrentLimitButton;
    private EditText batChargeCurrentLimitEditText;
    private Button batDischargeControlButton;
    private Spinner batDischargeControlSpinner;
    private ConstraintLayout batterySettingActionLayout;
    private TextView batterySettingActionTextView;
    private ToggleButton batterySettingActionToggleButton;
    private LinearLayout batterySettingParamLayout;
    private ConstraintLayout batterySettingTitleLayout;
    private ToggleButton batterySharedFunctionButton;
    private ConstraintLayout batterySharedFunctionLayout;
    private Button batteryTypeButton;
    private Spinner batteryTypeSpinner;
    private JSONObject cacheReadAllResultObj;
    private Button chargeCurrentButton;
    private EditText chargeCurrentEditText;
    private Button chargeEndSocButton;
    private EditText chargeEndSocEditText;
    private Button chargeEndVoltButton;
    private EditText chargeEndVoltEditText;
    private Button chargeFirstVoltButton;
    private EditText chargeFirstVoltEditText;
    private ConstraintLayout chargeSetActionLayout;
    private TextView chargeSetActionTextView;
    private ToggleButton chargeSetActionToggleButton;
    private LinearLayout chargeSetParamLayout;
    private Button chargeStartSocButton;
    private EditText chargeStartSocEditText;
    private Button chargeStartVoltButton;
    private EditText chargeStartVoltEditText;
    private Button composedPhaseButton;
    private ConstraintLayout composedPhaseLayout;
    private boolean created;
    private ToggleButton ctDirectionReversedFunctionButton;
    private ConstraintLayout ctDirectionReversedFunctionLayout;
    private Button ctPowerOffsetButton;
    private EditText ctPowerOffsetEditText;
    private Button ctSampleRatioButton;
    private ConstraintLayout ctSampleRatioLayout;
    private Spinner ctSampleRatioSpinner;
    private Button datalogSnButton;
    private EditText datalogSnEditText;
    private TextView datalogSnTextView;
    private ConstraintLayout dischargeAcCoupleSetActionLayout;
    private TextView dischargeAcCoupleSetActionTextView;
    private ToggleButton dischargeAcCoupleSetActionToggleButton;
    private LinearLayout dischargeAcCoupleSetParamLayout;
    private ConstraintLayout dischargeAcCoupleSetTitleLayout;
    private TextView dischargeAcCoupleSetTitleTextView;
    private ConstraintLayout dischargePeakShavingSetActionLayout;
    private TextView dischargePeakShavingSetActionTextView;
    private ToggleButton dischargePeakShavingSetActionToggleButton;
    private LinearLayout dischargePeakShavingSetParamLayout;
    private ConstraintLayout dischargePeakShavingSetTitleLayout;
    private TextView dischargePeakShavingSetTitleTextView;
    private ConstraintLayout dischargeSetActionLayout;
    private TextView dischargeSetActionTextView;
    private ToggleButton dischargeSetActionToggleButton;
    private LinearLayout dischargeSetParamLayout;
    private ConstraintLayout dischargeSmartLoadSetActionLayout;
    private TextView dischargeSmartLoadSetActionTextView;
    private ToggleButton dischargeSmartLoadSetActionToggleButton;
    private LinearLayout dischargeSmartLoadSetParamLayout;
    private ConstraintLayout dischargeSmartLoadSetTitleLayout;
    private TextView dischargeSmartLoadSetTitleTextView;
    private Button dischgCurrentButton;
    private EditText dischgCurrentEditText;
    private ToggleButton drmsFunctionButton;
    private Button epsFrequencySetButton;
    private Spinner epsFrequencySetSpinner;
    private ToggleButton epsFunctionButton;
    private Button exportULCompliancePDFButton;
    private LinearLayout floatVoltageLayout;
    private Button floatVoltageSetButton;
    private EditText floatVoltageSetEditText;
    private Button floatingVoltageButton;
    private EditText floatingVoltageEditText;
    private EditText forcedChargeEndHour1EditText;
    private EditText forcedChargeEndHour2EditText;
    private EditText forcedChargeEndHourEditText;
    private EditText forcedChargeEndMinute1EditText;
    private EditText forcedChargeEndMinute2EditText;
    private EditText forcedChargeEndMinuteEditText;
    private Button forcedChargeEndTime1Button;
    private Button forcedChargeEndTime2Button;
    private Button forcedChargeEndTimeButton;
    private EditText forcedChargeStartHour1EditText;
    private EditText forcedChargeStartHour2EditText;
    private EditText forcedChargeStartHourEditText;
    private EditText forcedChargeStartMinute1EditText;
    private EditText forcedChargeStartMinute2EditText;
    private EditText forcedChargeStartMinuteEditText;
    private Button forcedChargeStartTime1Button;
    private Button forcedChargeStartTime2Button;
    private Button forcedChargeStartTimeButton;
    private ToggleButton forcedChgFunctionButton;
    private Button forcedChgPowerCmdButton;
    private EditText forcedChgPowerCmdEditText;
    private Button forcedChgSocLimitButton;
    private EditText forcedChgSocLimitEditText;
    private EditText forcedDisChargeEndHour1EditText;
    private EditText forcedDisChargeEndHour2EditText;
    private EditText forcedDisChargeEndHourEditText;
    private EditText forcedDisChargeEndMinute1EditText;
    private EditText forcedDisChargeEndMinute2EditText;
    private EditText forcedDisChargeEndMinuteEditText;
    private Button forcedDisChargeEndTime1Button;
    private Button forcedDisChargeEndTime2Button;
    private Button forcedDisChargeEndTimeButton;
    private EditText forcedDisChargeStartHour1EditText;
    private EditText forcedDisChargeStartHour2EditText;
    private EditText forcedDisChargeStartHourEditText;
    private EditText forcedDisChargeStartMinute1EditText;
    private EditText forcedDisChargeStartMinute2EditText;
    private EditText forcedDisChargeStartMinuteEditText;
    private Button forcedDisChargeStartTime1Button;
    private Button forcedDisChargeStartTime2Button;
    private Button forcedDisChargeStartTimeButton;
    private ToggleButton forcedDisChgFunctionButton;
    private Button forcedDisChgPowerCmdButton;
    private EditText forcedDisChgPowerCmdEditText;
    private Button forcedDisChgSocLimitButton;
    private EditText forcedDisChgSocLimitEditText;
    private Button genRatedPowerButton;
    private EditText genRatedPowerEditText;
    private ConstraintLayout generatorSetActionLayout;
    private TextView generatorSetActionTextView;
    private ToggleButton generatorSetActionToggleButton;
    private LinearLayout generatorSetParamLayout;
    private ToggleButton go2OffGridFunctionButton;
    private ToggleButton gridBossEnabledFunctionButton;
    private ConstraintLayout gridBossFunctionLayout;
    private ConstraintLayout gridConnectSetActionLayout;
    private TextView gridConnectSetActionTextView;
    private ToggleButton gridConnectSetActionToggleButton;
    private LinearLayout gridConnectSetParamLayout;
    private ConstraintLayout gridConnectSetTitleLayout;
    private Button gridFreqConnHighButton;
    private EditText gridFreqConnHighEditText;
    private Button gridFreqConnLowButton;
    private EditText gridFreqConnLowEditText;
    private Button gridFreqLimit1HighButton;
    private EditText gridFreqLimit1HighEditText;
    private Button gridFreqLimit1LowButton;
    private EditText gridFreqLimit1LowEditText;
    private Button gridFreqLimit2HighButton;
    private EditText gridFreqLimit2HighEditText;
    private Button gridFreqLimit2LowButton;
    private EditText gridFreqLimit2LowEditText;
    private Button gridFreqLimit3HighButton;
    private EditText gridFreqLimit3HighEditText;
    private Button gridFreqLimit3LowButton;
    private EditText gridFreqLimit3LowEditText;
    private ToggleButton gridPeakShavingFunctionButton;
    private Button gridPeakShavingPowerButton;
    private EditText gridPeakShavingPowerEditText;
    private Button gridPeakShavingSoc1Button;
    private EditText gridPeakShavingSoc1EditText;
    private Button gridPeakShavingSoc2Button;
    private EditText gridPeakShavingSoc2EditText;
    private Button gridPeakShavingVolt1Button;
    private EditText gridPeakShavingVolt1EditText;
    private Button gridPeakShavingVolt2Button;
    private EditText gridPeakShavingVolt2EditText;
    private Button gridRegulationButton;
    private Spinner gridRegulationSpinner;
    private Button gridTypeButton;
    private Spinner gridTypeSpinner;
    private Button gridVoltConnHighButton;
    private EditText gridVoltConnHighEditText;
    private Button gridVoltConnLowButton;
    private EditText gridVoltConnLowEditText;
    private Button gridVoltLimit1HighButton;
    private EditText gridVoltLimit1HighEditText;
    private Button gridVoltLimit1LowButton;
    private EditText gridVoltLimit1LowEditText;
    private Button gridVoltLimit2HighButton;
    private EditText gridVoltLimit2HighEditText;
    private Button gridVoltLimit2LowButton;
    private EditText gridVoltLimit2LowEditText;
    private Button gridVoltLimit3HighButton;
    private EditText gridVoltLimit3HighEditText;
    private Button gridVoltLimit3LowButton;
    private EditText gridVoltLimit3LowEditText;
    private TextView inverterSnTextView;
    private LinearLayout leadAcidCapacityLayout;
    private Button leadAcidCapacitysetButton;
    private EditText leadAcidCapacitysetEditText;
    private Button leadAcidChargeVoltRefButton;
    private EditText leadAcidChargeVoltRefEditText;
    private Button leadAcidDischargeCutOffVoltButton;
    private EditText leadAcidDischargeCutOffVoltEditText;
    private Button lithiumBrandButton;
    private LinearLayout lithiumBrandLayout;
    private Spinner lithiumBrandSpinner;
    private LocalConnect localConnect;
    private Button masterOrSlaveButton;
    private ConstraintLayout masterOrSlaveLayout;
    private Spinner masterOrSlaveSpinner;
    private Button maxAcInputPowerButton;
    private EditText maxAcInputPowerEditText;
    private ConstraintLayout maxAcInputPowerLayout;
    private Button offGridDischargeCutoffSocButton;
    private EditText offGridDischargeCutoffSocEditText;
    private ToggleButton onGridAlwaysOnFunctionButton;
    private Button onGridDischargeCutoffSocButton;
    private EditText onGridDischargeCutoffSocEditText;
    private LinearLayout onGridDischargeLayout;
    private Button onGridDischargeSetButton;
    private EditText onGridDischargeSetEditText;
    private Button onGridEodVoltageButton;
    private EditText onGridEodVoltageEditText;
    private EditText peakShavingEndHour1EditText;
    private EditText peakShavingEndHour2EditText;
    private EditText peakShavingEndMinute1EditText;
    private EditText peakShavingEndMinute2EditText;
    private Button peakShavingEndTime1Button;
    private Button peakShavingEndTime2Button;
    private EditText peakShavingStartHour1EditText;
    private EditText peakShavingStartHour2EditText;
    private EditText peakShavingStartMinute1EditText;
    private EditText peakShavingStartMinute2EditText;
    private Button peakShavingStartTime1Button;
    private Button peakShavingStartTime2Button;
    private EditText pinEditText;
    private ToggleButton pvArcFunctionButton;
    private ConstraintLayout pvArcFunctionLayout;
    private Button readAllButton;
    private Spinner readComposedPhaseSpinner;
    private Button readServerIpButton;
    private Button reconnectTimeButton;
    private EditText reconnectTimeEditText;
    private Button reset2FactoryButton;
    private ToggleButton runWithoutGridFunctionButton;
    private ConstraintLayout runWithoutGridFunctionLayout;
    private Spinner serverIpSpinner;
    private Spinner setComposedPhaseSpinner;
    private Button setServerIpButton;
    private Button setTimeButton;
    private ToggleButton setToStandbyFunctionButton;
    private Button smartLoadEndSocButton;
    private EditText smartLoadEndSocEditText;
    private Button smartLoadEndVoltButton;
    private EditText smartLoadEndVoltEditText;
    private Button smartLoadStartSocButton;
    private EditText smartLoadStartSocEditText;
    private Button smartLoadStartVoltButton;
    private EditText smartLoadStartVoltEditText;
    private Button startAcChargeSOCButton;
    private EditText startAcChargeSOCEditText;
    private Button startAcChargeVoltButton;
    private EditText startAcChargeVoltEditText;
    private Button startPvPowerButton;
    private EditText startPvPowerEditText;
    private Button stopDischgVoltButton;
    private EditText stopDischgVoltEditText;
    private EditText timeDateEditText;
    private ConstraintLayout timeLayout;
    private EditText timeTimeEditText;
    private Button updateFirmwareButton;
    private ConstraintLayout wifiModuleSetActionLayout;
    private TextView wifiModuleSetActionTextView;
    private ToggleButton wifiModuleSetActionToggleButton;
    private LinearLayout wifiModuleSetParamLayout;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkBitValueValid(int i, int i2) {
        return i2 >= 0 && i2 <= i;
    }

    private static boolean checkIfRangeValid(int i, int i2, int i3) {
        return i3 >= i && i3 <= i2;
    }

    public Local12KSetFragment(LocalConnect localConnect) {
        super(23L);
        this._21Functions = new String[]{"FUNC_DRMS_EN", "FUNC_AC_CHARGE", "FUNC_FORCED_CHG_EN", "FUNC_FORCED_DISCHG_EN", "FUNC_SET_TO_STANDBY", "FUNC_EPS_EN", "FUNC_FEED_IN_GRID_EN"};
        this._110Functions = new String[]{"FUNC_PV_GRID_OFF_EN", "FUNC_RUN_WITHOUT_GRID", "FUNC_MICRO_GRID_EN", "FUNC_BAT_SHARED", "FUNC_CHARGE_LAST", "FUNC_BUZZER_EN", "FUNC_TAKE_LOAD_TOGETHER", "FUNC_GREEN_EN"};
        this._179Functions = new String[]{"FUNC_CT_DIRECTION_REVERSED", "FUNC_PV_ARC_FAULT_CLEAR", "FUNC_PV_SELL_TO_GRID_EN", "FUNC_WATT_VOLT_EN", "FUNC_TRIP_TIME_UNIT", "FUNC_ACTIVE_POWER_LIMIT_MODE", "FUNC_GRID_PEAK_SHAVING", "FUNC_GEN_PEAK_SHAVING", "FUNC_BAT_CHARGE_CONTROL", "FUNC_BAT_DISCHARGE_CONTROL", "FUNC_PV_ARC", "FUNC_ON_GRID_ALWAYS_ON"};
        this._226Functions = new String[]{"FUNC_RUN_WITHOUT_GRID_12K", "FUNC_MIDBOX_EN"};
        this.localConnect = localConnect;
    }

    public synchronized void putReadAllResultObj(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        if (this.cacheReadAllResultObj == null) {
            this.cacheReadAllResultObj = new JSONObject();
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                this.cacheReadAllResultObj.put(next, jSONObject.get(next));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_local_12k_set, viewGroup, false);
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) LoginActivity.class));
                LocalActivity.instance.finish();
            }
        });
        BatteryDict.initIfNeeded(viewInflate.getContext());
        List<Property> batteryTypeList = BatteryDict.getBatteryTypeList();
        List<Property> lithiumBrandList = BatteryDict.getLithiumBrandList();
        this.datalogSnTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_datalogSn_TextView);
        this.inverterSnTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_inverterSn_TextView);
        Button button = (Button) viewInflate.findViewById(R.id.fragment_remote_set_readAllButton);
        this.readAllButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Inverter inverter = Local12KSetFragment.this.localConnect.getInverter();
                if (inverter != null) {
                    Local12KSetFragment.this.cacheReadAllResultObj = null;
                    Local12KSetFragment.this.clearFragmentData();
                    Local12KSetFragment.this.readAllButton.setEnabled(false);
                    Local12KSetFragment.this.exportULCompliancePDFButton.setEnabled(false);
                    new ReadMultiParamTask(Local12KSetFragment.this).execute(new RemoteReadInfo(inverter.getSerialNum(), 0, 40), new RemoteReadInfo(inverter.getSerialNum(), 40, 40), new RemoteReadInfo(inverter.getSerialNum(), 80, 40), new RemoteReadInfo(inverter.getSerialNum(), 120, 40), new RemoteReadInfo(inverter.getSerialNum(), CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 40), new RemoteReadInfo(inverter.getSerialNum(), 200, 40));
                    new ReadDatalogParamTask(Local12KSetFragment.this).execute(1);
                }
            }
        });
        this.timeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_timeLayout);
        EditText editText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeDateEditText);
        this.timeDateEditText = editText;
        editText.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Local12KSetFragment.this.timeDateEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 10) {
                    Local12KSetFragment.this.timeDateEditText.setText(InvTool.formatDate(new Date()));
                }
                Local12KSetFragment.this.getActivity().showDialog(6);
            }
        });
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeTimeEditText);
        this.timeTimeEditText = editText2;
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Local12KSetFragment.this.timeTimeEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 5) {
                    Local12KSetFragment.this.timeTimeEditText.setText(InvTool.formatTime(new Date()).substring(0, 5));
                }
                Local12KSetFragment.this.getActivity().showDialog(7);
            }
        });
        Button button2 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_timeButton);
        this.setTimeButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                Local12KSetFragment.this.runNormalRemoteWrite("HOLD_TIME", Local12KSetFragment.this.timeDateEditText.getText().toString().trim() + " " + Local12KSetFragment.this.timeTimeEditText.getText().toString().trim() + ":" + String.valueOf(calendar.get(13)), Local12KSetFragment.this.setTimeButton);
            }
        });
        this.ctSampleRatioLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioLayout);
        this.ctSampleRatioSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioSpinner);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Property(String.valueOf(-1), getString(R.string.phrase_bit_param_ct_sample_ratio_empty)));
        arrayList.add(new Property(String.valueOf(0), getString(R.string.phrase_bit_param_ct_sample_ratio_0)));
        arrayList.add(new Property(String.valueOf(1), getString(R.string.phrase_bit_param_ct_sample_ratio_1)));
        arrayList.add(new Property(String.valueOf(2), "1/2000"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ctSampleRatioSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Button button3 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioButton);
        this.ctSampleRatioButton = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.ctSampleRatioSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runBitRemoteWrite("BIT_CT_SAMPLE_RATIO", property.getName(), Local12KSetFragment.this.ctSampleRatioButton);
            }
        });
        this.ctPowerOffsetEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_ctPowerOffsetEditText);
        Button button4 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_ctPowerOffsetButton);
        this.ctPowerOffsetButton = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_CT_POWER_OFFSET", local12KSetFragment.ctPowerOffsetEditText.getText().toString().trim(), Local12KSetFragment.this.ctPowerOffsetButton);
            }
        });
        ToggleButton toggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_setToStandbyFunctionButton);
        this.setToStandbyFunctionButton = toggleButton;
        toggleButton.setOnClickListener(new AnonymousClass8());
        ToggleButton toggleButton2 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_epsFunctionButton);
        this.epsFunctionButton = toggleButton2;
        toggleButton2.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_EPS_EN", local12KSetFragment.epsFunctionButton);
            }
        });
        this.epsFrequencySetSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_epsFrequencySetSpinner);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_eps_frequency_set_empty)));
        arrayList2.add(new Property(String.valueOf(50), getString(R.string.phrase_param_eps_frequency_set_50)));
        arrayList2.add(new Property(String.valueOf(60), getString(R.string.phrase_param_eps_frequency_set_60)));
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.epsFrequencySetSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        Button button5 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_epsFrequencySetButton);
        this.epsFrequencySetButton = button5;
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.epsFrequencySetSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runNormalRemoteWrite("HOLD_EPS_FREQ_SET", property.getName(), Local12KSetFragment.this.epsFrequencySetButton);
            }
        });
        ToggleButton toggleButton3 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_go2OffGridFunctionButton);
        this.go2OffGridFunctionButton = toggleButton3;
        toggleButton3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_GREEN_EN", local12KSetFragment.go2OffGridFunctionButton);
            }
        });
        this.runWithoutGridFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_runWithoutGridFunctionLayout);
        ToggleButton toggleButton4 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_runWithoutGridFunctionButton);
        this.runWithoutGridFunctionButton = toggleButton4;
        toggleButton4.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_RUN_WITHOUT_GRID_12K", local12KSetFragment.runWithoutGridFunctionButton);
            }
        });
        this.masterOrSlaveLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveLayout);
        this.masterOrSlaveSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveSpinner);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_master_or_slave_empty)));
        arrayList3.add(new Property(String.valueOf(1), getString(R.string.phrase_param_master_or_slave_hybird_1)));
        arrayList3.add(new Property(String.valueOf(2), getString(R.string.phrase_param_master_or_slave_hybird_2)));
        arrayList3.add(new Property(String.valueOf(3), getString(R.string.phrase_param_master_or_slave_hybird_3)));
        arrayList3.add(new Property(String.valueOf(4), getString(R.string.phrase_param_master_or_slave_hybird_4)));
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.masterOrSlaveSpinner.setAdapter((SpinnerAdapter) arrayAdapter3);
        Button button6 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveButton);
        this.masterOrSlaveButton = button6;
        button6.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.masterOrSlaveSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runNormalRemoteWrite("HOLD_SET_MASTER_OR_SLAVE", property.getName(), Local12KSetFragment.this.masterOrSlaveButton);
            }
        });
        this.composedPhaseLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseLayout);
        this.readComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_readComposedPhaseSpinner);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(new Property(String.valueOf(-1), "--"));
        arrayList4.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList4.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList4.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList4);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.readComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter4);
        this.readComposedPhaseSpinner.setEnabled(false);
        this.setComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_setComposedPhaseSpinner);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_composed_phase_empty)));
        arrayList5.add(new Property(String.valueOf(0), getString(R.string.phrase_param_composed_phase_0)));
        arrayList5.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList5.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList5.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter5 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList5);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter5);
        Button button7 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseButton);
        this.composedPhaseButton = button7;
        button7.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.setComposedPhaseSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runNormalRemoteWrite("HOLD_SET_COMPOSED_PHASE", property.getName(), Local12KSetFragment.this.composedPhaseButton);
            }
        });
        this.batterySharedFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionLayout);
        ToggleButton toggleButton5 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionButton);
        this.batterySharedFunctionButton = toggleButton5;
        toggleButton5.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_BAT_SHARED", local12KSetFragment.batterySharedFunctionButton);
            }
        });
        this.maxAcInputPowerLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerLayout);
        this.maxAcInputPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerEditText);
        Button button8 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerButton);
        this.maxAcInputPowerButton = button8;
        button8.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_MAX_AC_INPUT_POWER", local12KSetFragment.maxAcInputPowerEditText.getText().toString().trim(), Local12KSetFragment.this.maxAcInputPowerButton);
            }
        });
        this.batteryTypeSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_batteryTypeSpinner);
        ArrayAdapter arrayAdapter6 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, batteryTypeList);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.batteryTypeSpinner.setAdapter((SpinnerAdapter) arrayAdapter6);
        Button button9 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batteryTypeButton);
        this.batteryTypeButton = button9;
        button9.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.batteryTypeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runBitModelRemoteWrite("MODEL_BIT_BATTERY_TYPE", property.getName(), Local12KSetFragment.this.batteryTypeButton);
            }
        });
        this.lithiumBrandSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_lithiumBrandSpinner);
        ArrayAdapter arrayAdapter7 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, lithiumBrandList);
        arrayAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lithiumBrandSpinner.setAdapter((SpinnerAdapter) arrayAdapter7);
        Button button10 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_lithiumBrandButton);
        this.lithiumBrandButton = button10;
        button10.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.lithiumBrandSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runBitModelRemoteWrite("MODEL_BIT_LITHIUM_TYPE", property.getName(), Local12KSetFragment.this.lithiumBrandButton);
            }
        });
        this.leadAcidCapacitysetEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidCapacitySetEditText);
        Button button11 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidCapacitySetButton);
        this.leadAcidCapacitysetButton = button11;
        button11.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_LEAD_CAPACITY", local12KSetFragment.leadAcidCapacitysetEditText.getText().toString().trim(), Local12KSetFragment.this.leadAcidCapacitysetButton);
            }
        });
        this.absorbVoltageSetEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_absorbVoltageSetEditText);
        Button button12 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_absorbVoltageSetButton);
        this.absorbVoltageSetButton = button12;
        button12.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_VOLT_REF", local12KSetFragment.absorbVoltageSetEditText.getText().toString().trim(), Local12KSetFragment.this.absorbVoltageSetButton);
            }
        });
        this.floatVoltageSetEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_floatVoltageSetEditText);
        Button button13 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_floatVoltageSetButton);
        this.floatVoltageSetButton = button13;
        button13.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_FLOATING_VOLTAGE", local12KSetFragment.floatVoltageSetEditText.getText().toString().trim(), Local12KSetFragment.this.floatVoltageSetButton);
            }
        });
        this.onGridDischargeSetEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_on_grid_dischargesetEditText);
        Button button14 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_on_grid_dischargesetButton);
        this.onGridDischargeSetButton = button14;
        button14.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_VBAT_START_DERATING", local12KSetFragment.onGridDischargeSetEditText.getText().toString().trim(), Local12KSetFragment.this.onGridDischargeSetButton);
            }
        });
        this.gridRegulationSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_gridRegulationSpinner);
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(new Property(String.valueOf(-1), getString(R.string.phrase_please_select)));
        arrayList6.add(new Property(String.valueOf(0), "USA"));
        arrayList6.add(new Property(String.valueOf(1), "Hawaii"));
        arrayList6.add(new Property(String.valueOf(2), "USA(rule21)"));
        arrayList6.add(new Property(String.valueOf(3), "South Africa"));
        arrayList6.add(new Property(String.valueOf(4), "General"));
        arrayList6.add(new Property(String.valueOf(5), "PR-LUMA"));
        ArrayAdapter arrayAdapter8 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList6);
        arrayAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.gridRegulationSpinner.setAdapter((SpinnerAdapter) arrayAdapter8);
        Button button15 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridRegulationButton);
        this.gridRegulationButton = button15;
        button15.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.gridRegulationSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runNormalRemoteWrite("_12K_HOLD_GRID_REGULATION", property.getName(), Local12KSetFragment.this.gridRegulationButton);
            }
        });
        this.gridTypeSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_gridTypeSpinner);
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add(new Property(String.valueOf(-1), getString(R.string.phrase_please_select)));
        arrayList7.add(new Property(String.valueOf(0), "240V/120V"));
        arrayList7.add(new Property(String.valueOf(1), "208V/120V"));
        arrayList7.add(new Property(String.valueOf(2), "240V"));
        arrayList7.add(new Property(String.valueOf(3), "230V"));
        arrayList7.add(new Property(String.valueOf(4), "200V/100V"));
        ArrayAdapter arrayAdapter9 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList7);
        arrayAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.gridTypeSpinner.setAdapter((SpinnerAdapter) arrayAdapter9);
        Button button16 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridTypeButton);
        this.gridTypeButton = button16;
        button16.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.gridTypeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runNormalRemoteWrite("_12K_HOLD_GRID_TYPE", property.getName(), Local12KSetFragment.this.gridTypeButton);
            }
        });
        ToggleButton toggleButton6 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_drmsFunctionButton);
        this.drmsFunctionButton = toggleButton6;
        toggleButton6.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_DRMS_EN", local12KSetFragment.drmsFunctionButton);
            }
        });
        this.reconnectTimeEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_reconnectTimeEditText);
        Button button17 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_reconnectTimeButton);
        this.reconnectTimeButton = button17;
        button17.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_RECONNECT_TIME", local12KSetFragment.reconnectTimeEditText.getText().toString().trim(), Local12KSetFragment.this.reconnectTimeButton);
            }
        });
        this.gridVoltConnHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnHighEditText);
        Button button18 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnHighButton);
        this.gridVoltConnHighButton = button18;
        button18.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_CONN_HIGH", local12KSetFragment.gridVoltConnHighEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltConnHighButton);
            }
        });
        this.gridVoltConnLowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnLowEditText);
        Button button19 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnLowButton);
        this.gridVoltConnLowButton = button19;
        button19.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_CONN_LOW", local12KSetFragment.gridVoltConnLowEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltConnLowButton);
            }
        });
        this.gridFreqConnHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnHighEditText);
        Button button20 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnHighButton);
        this.gridFreqConnHighButton = button20;
        button20.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_CONN_HIGH", local12KSetFragment.gridFreqConnHighEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqConnHighButton);
            }
        });
        this.gridFreqConnLowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnLowEditText);
        Button button21 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnLowButton);
        this.gridFreqConnLowButton = button21;
        button21.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_CONN_LOW", local12KSetFragment.gridFreqConnLowEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqConnLowButton);
            }
        });
        this.gridVoltLimit1LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1LowEditText);
        Button button22 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1LowButton);
        this.gridVoltLimit1LowButton = button22;
        button22.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT1_LOW", local12KSetFragment.gridVoltLimit1LowEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltLimit1LowButton);
            }
        });
        this.gridVoltLimit1HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1HighEditText);
        Button button23 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1HighButton);
        this.gridVoltLimit1HighButton = button23;
        button23.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT1_HIGH", local12KSetFragment.gridVoltLimit1HighEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltLimit1HighButton);
            }
        });
        this.gridVoltLimit2LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2LowEditText);
        Button button24 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2LowButton);
        this.gridVoltLimit2LowButton = button24;
        button24.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.33
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT2_LOW", local12KSetFragment.gridVoltLimit2LowEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltLimit2LowButton);
            }
        });
        this.gridVoltLimit2HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2HighEditText);
        Button button25 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2HighButton);
        this.gridVoltLimit2HighButton = button25;
        button25.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT2_HIGH", local12KSetFragment.gridVoltLimit2HighEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltLimit2HighButton);
            }
        });
        this.gridVoltLimit3LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3LowEditText);
        Button button26 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3LowButton);
        this.gridVoltLimit3LowButton = button26;
        button26.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.35
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT3_LOW", local12KSetFragment.gridVoltLimit3LowEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltLimit3LowButton);
            }
        });
        this.gridVoltLimit3HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3HighEditText);
        Button button27 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3HighButton);
        this.gridVoltLimit3HighButton = button27;
        button27.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.36
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT3_HIGH", local12KSetFragment.gridVoltLimit3HighEditText.getText().toString().trim(), Local12KSetFragment.this.gridVoltLimit3HighButton);
            }
        });
        this.gridFreqLimit1LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1LowEditText);
        Button button28 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1LowButton);
        this.gridFreqLimit1LowButton = button28;
        button28.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.37
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT1_LOW", local12KSetFragment.gridFreqLimit1LowEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqLimit1LowButton);
            }
        });
        this.gridFreqLimit1HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1HighEditText);
        Button button29 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1HighButton);
        this.gridFreqLimit1HighButton = button29;
        button29.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT1_HIGH", local12KSetFragment.gridFreqLimit1HighEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqLimit1HighButton);
            }
        });
        this.gridFreqLimit2LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2LowEditText);
        Button button30 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2LowButton);
        this.gridFreqLimit2LowButton = button30;
        button30.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.39
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT2_LOW", local12KSetFragment.gridFreqLimit2LowEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqLimit2LowButton);
            }
        });
        this.gridFreqLimit2HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2HighEditText);
        Button button31 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2HighButton);
        this.gridFreqLimit2HighButton = button31;
        button31.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.40
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT2_HIGH", local12KSetFragment.gridFreqLimit2HighEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqLimit2HighButton);
            }
        });
        this.gridFreqLimit3LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3LowEditText);
        Button button32 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3LowButton);
        this.gridFreqLimit3LowButton = button32;
        button32.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.41
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT3_LOW", local12KSetFragment.gridFreqLimit3LowEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqLimit3LowButton);
            }
        });
        this.gridFreqLimit3HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3HighEditText);
        Button button33 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3HighButton);
        this.gridFreqLimit3HighButton = button33;
        button33.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.42
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT3_HIGH", local12KSetFragment.gridFreqLimit3HighEditText.getText().toString().trim(), Local12KSetFragment.this.gridFreqLimit3HighButton);
            }
        });
        ToggleButton toggleButton7 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_acChargeFunctionButton);
        this.acChargeFunctionButton = toggleButton7;
        toggleButton7.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.43
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_AC_CHARGE", local12KSetFragment.acChargeFunctionButton);
            }
        });
        this.acChargePowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargePowerCmdEditText);
        Button button34 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargePowerCmdButton);
        this.acChargePowerButton = button34;
        button34.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.44
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_POWER_CMD", local12KSetFragment.acChargePowerEditText.getText().toString().trim(), Local12KSetFragment.this.acChargePowerButton);
            }
        });
        this.startAcChargeSOCEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_SOCEditText);
        Button button35 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_SOCButton);
        this.startAcChargeSOCButton = button35;
        button35.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.45
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_START_BATTERY_SOC", local12KSetFragment.startAcChargeSOCEditText.getText().toString().trim(), Local12KSetFragment.this.startAcChargeSOCButton);
            }
        });
        this.acChargeSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeSocLimitEditText);
        Button button36 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeSocLimitButton);
        this.acChargeSocLimitButton = button36;
        button36.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.46
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_SOC_LIMIT", local12KSetFragment.acChargeSocLimitEditText.getText().toString().trim(), Local12KSetFragment.this.acChargeSocLimitButton);
            }
        });
        this.startAcChargeVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_VoltEditText);
        Button button37 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_VoltButton);
        this.startAcChargeVoltButton = button37;
        button37.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.47
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE", local12KSetFragment.startAcChargeVoltEditText.getText().toString().trim(), Local12KSetFragment.this.startAcChargeVoltButton);
            }
        });
        this.acChargeEndBatteryVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatteryVoltageEditText);
        Button button38 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatteryVoltageButton);
        this.acChargeEndBatteryVoltageButton = button38;
        button38.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.48
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE", local12KSetFragment.acChargeEndBatteryVoltageEditText.getText().toString().trim(), Local12KSetFragment.this.acChargeEndBatteryVoltageButton);
            }
        });
        this.acChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHourEditText);
        this.acChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinuteEditText);
        Button button39 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTimeButton);
        this.acChargeStartTimeButton = button39;
        button39.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.49
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME", local12KSetFragment.acChargeStartHourEditText, Local12KSetFragment.this.acChargeStartMinuteEditText, Local12KSetFragment.this.acChargeStartTimeButton);
            }
        });
        this.acChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHourEditText);
        this.acChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinuteEditText);
        Button button40 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTimeButton);
        this.acChargeEndTimeButton = button40;
        button40.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.50
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME", local12KSetFragment.acChargeEndHourEditText, Local12KSetFragment.this.acChargeEndMinuteEditText, Local12KSetFragment.this.acChargeEndTimeButton);
            }
        });
        this.acChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour1EditText);
        this.acChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute1EditText);
        Button button41 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime1Button);
        this.acChargeStartTime1Button = button41;
        button41.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.51
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_1", local12KSetFragment.acChargeStartHour1EditText, Local12KSetFragment.this.acChargeStartMinute1EditText, Local12KSetFragment.this.acChargeStartTime1Button);
            }
        });
        this.acChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour1EditText);
        this.acChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute1EditText);
        Button button42 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime1Button);
        this.acChargeEndTime1Button = button42;
        button42.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.52
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_1", local12KSetFragment.acChargeEndHour1EditText, Local12KSetFragment.this.acChargeEndMinute1EditText, Local12KSetFragment.this.acChargeEndTime1Button);
            }
        });
        this.acChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour2EditText);
        this.acChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute2EditText);
        Button button43 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime2Button);
        this.acChargeStartTime2Button = button43;
        button43.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.53
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_2", local12KSetFragment.acChargeStartHour2EditText, Local12KSetFragment.this.acChargeStartMinute2EditText, Local12KSetFragment.this.acChargeStartTime2Button);
            }
        });
        this.acChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour2EditText);
        this.acChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute2EditText);
        Button button44 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime2Button);
        this.acChargeEndTime2Button = button44;
        button44.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.54
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_2", local12KSetFragment.acChargeEndHour2EditText, Local12KSetFragment.this.acChargeEndMinute2EditText, Local12KSetFragment.this.acChargeEndTime2Button);
            }
        });
        ToggleButton toggleButton8 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgFunctionButton);
        this.forcedChgFunctionButton = toggleButton8;
        toggleButton8.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.55
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_FORCED_CHG_EN", local12KSetFragment.forcedChgFunctionButton);
            }
        });
        this.forcedChgPowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdEditText);
        Button button45 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdButton);
        this.forcedChgPowerCmdButton = button45;
        button45.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.56
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_FORCED_CHG_POWER_CMD", local12KSetFragment.forcedChgPowerCmdEditText.getText().toString().trim(), Local12KSetFragment.this.forcedChgPowerCmdButton);
            }
        });
        this.forcedChgSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitEditText);
        Button button46 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitButton);
        this.forcedChgSocLimitButton = button46;
        button46.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.57
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_FORCED_CHG_SOC_LIMIT", local12KSetFragment.forcedChgSocLimitEditText.getText().toString().trim(), Local12KSetFragment.this.forcedChgSocLimitButton);
            }
        });
        this.chargeFirstVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeFirstVoltEditText);
        Button button47 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeFirstVoltButton);
        this.chargeFirstVoltButton = button47;
        button47.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.58
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_CHARGE_FIRST_VOLT", local12KSetFragment.chargeFirstVoltEditText.getText().toString().trim(), Local12KSetFragment.this.chargeFirstVoltButton);
            }
        });
        this.forcedChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHourEditText);
        this.forcedChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinuteEditText);
        Button button48 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTimeButton);
        this.forcedChargeStartTimeButton = button48;
        button48.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.59
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME", local12KSetFragment.forcedChargeStartHourEditText, Local12KSetFragment.this.forcedChargeStartMinuteEditText, Local12KSetFragment.this.forcedChargeStartTimeButton);
            }
        });
        this.forcedChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHourEditText);
        this.forcedChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinuteEditText);
        Button button49 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTimeButton);
        this.forcedChargeEndTimeButton = button49;
        button49.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.60
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME", local12KSetFragment.forcedChargeEndHourEditText, Local12KSetFragment.this.forcedChargeEndMinuteEditText, Local12KSetFragment.this.forcedChargeEndTimeButton);
            }
        });
        this.forcedChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHour1EditText);
        this.forcedChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinute1EditText);
        Button button50 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime1Button);
        this.forcedChargeStartTime1Button = button50;
        button50.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.61
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME_1", local12KSetFragment.forcedChargeStartHour1EditText, Local12KSetFragment.this.forcedChargeStartMinute1EditText, Local12KSetFragment.this.forcedChargeStartTime1Button);
            }
        });
        this.forcedChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHour1EditText);
        this.forcedChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinute1EditText);
        Button button51 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime1Button);
        this.forcedChargeEndTime1Button = button51;
        button51.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.62
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME_1", local12KSetFragment.forcedChargeEndHour1EditText, Local12KSetFragment.this.forcedChargeEndMinute1EditText, Local12KSetFragment.this.forcedChargeEndTime1Button);
            }
        });
        this.forcedChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHour2EditText);
        this.forcedChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinute2EditText);
        Button button52 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime2Button);
        this.forcedChargeStartTime2Button = button52;
        button52.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.63
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME_2", local12KSetFragment.forcedChargeStartHour2EditText, Local12KSetFragment.this.forcedChargeStartMinute2EditText, Local12KSetFragment.this.forcedChargeStartTime2Button);
            }
        });
        this.forcedChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHour2EditText);
        this.forcedChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinute2EditText);
        Button button53 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime2Button);
        this.forcedChargeEndTime2Button = button53;
        button53.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.64
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME_2", local12KSetFragment.forcedChargeEndHour2EditText, Local12KSetFragment.this.forcedChargeEndMinute2EditText, Local12KSetFragment.this.forcedChargeEndTime2Button);
            }
        });
        this.leadAcidChargeVoltRefEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefEditText);
        Button button54 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefButton);
        this.leadAcidChargeVoltRefButton = button54;
        button54.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.65
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_VOLT_REF", local12KSetFragment.leadAcidChargeVoltRefEditText.getText().toString().trim(), Local12KSetFragment.this.leadAcidChargeVoltRefButton);
            }
        });
        this.floatingVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageEditText);
        Button button55 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageButton);
        this.floatingVoltageButton = button55;
        button55.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.66
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_FLOATING_VOLTAGE", local12KSetFragment.floatingVoltageEditText.getText().toString().trim(), Local12KSetFragment.this.floatingVoltageButton);
            }
        });
        this.batChargeCurrentLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_batChargeCurrentLimitEditText);
        Button button56 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batChargeCurrentLimitButton);
        this.batChargeCurrentLimitButton = button56;
        button56.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.67
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR", local12KSetFragment.batChargeCurrentLimitEditText.getText().toString().trim(), Local12KSetFragment.this.batChargeCurrentLimitButton);
            }
        });
        this.genRatedPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_genRatedPowerEditText);
        Button button57 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_genRatedPowerButton);
        this.genRatedPowerButton = button57;
        button57.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.68
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_MAX_GENERATOR_INPUT_POWER", local12KSetFragment.genRatedPowerEditText.getText().toString().trim(), Local12KSetFragment.this.genRatedPowerButton);
            }
        });
        this.chargeStartVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartVoltEditText);
        Button button58 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartVoltButton);
        this.chargeStartVoltButton = button58;
        button58.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.69
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_START_VOLT", local12KSetFragment.chargeStartVoltEditText.getText().toString().trim(), Local12KSetFragment.this.chargeStartVoltButton);
            }
        });
        this.chargeEndVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndVoltEditText);
        Button button59 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndVoltButton);
        this.chargeEndVoltButton = button59;
        button59.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.70
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_END_VOLT", local12KSetFragment.chargeEndVoltEditText.getText().toString().trim(), Local12KSetFragment.this.chargeEndVoltButton);
            }
        });
        this.chargeStartSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartSocEditText);
        Button button60 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartSocButton);
        this.chargeStartSocButton = button60;
        button60.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.71
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_START_SOC", local12KSetFragment.chargeStartSocEditText.getText().toString().trim(), Local12KSetFragment.this.chargeStartSocButton);
            }
        });
        this.chargeEndSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndSocEditText);
        Button button61 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndSocButton);
        this.chargeEndSocButton = button61;
        button61.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.72
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_END_SOC", local12KSetFragment.chargeEndSocEditText.getText().toString().trim(), Local12KSetFragment.this.chargeEndSocButton);
            }
        });
        this.chargeCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentEditText);
        Button button62 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentButton);
        this.chargeCurrentButton = button62;
        button62.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.73
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_RATE", local12KSetFragment.chargeCurrentEditText.getText().toString().trim(), Local12KSetFragment.this.chargeCurrentButton);
            }
        });
        this.batChargeControlSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_batChargeControlSpinner);
        ArrayAdapter arrayAdapter10 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, GlobalInfo.getBatControlList(getActivity()));
        arrayAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.batChargeControlSpinner.setAdapter((SpinnerAdapter) arrayAdapter10);
        Button button63 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batChargeControlButton);
        this.batChargeControlButton = button63;
        button63.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.74
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.batChargeControlSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runControlRemoteWrite("FUNC_BAT_CHARGE_CONTROL", Boolean.parseBoolean(property.getName()), Local12KSetFragment.this.batChargeControlButton);
            }
        });
        this.batDischargeControlSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_batDischargeControlSpinner);
        ArrayAdapter arrayAdapter11 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, GlobalInfo.getBatControlList(getActivity()));
        arrayAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.batDischargeControlSpinner.setAdapter((SpinnerAdapter) arrayAdapter11);
        Button button64 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batDischargeControlButton);
        this.batDischargeControlButton = button64;
        button64.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.75
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.batDischargeControlSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Local12KSetFragment.this.runControlRemoteWrite("FUNC_BAT_DISCHARGE_CONTROL", Boolean.parseBoolean(property.getName()), Local12KSetFragment.this.batDischargeControlButton);
            }
        });
        ToggleButton toggleButton9 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgFunctionButton);
        this.forcedDisChgFunctionButton = toggleButton9;
        toggleButton9.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.76
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_FORCED_DISCHG_EN", local12KSetFragment.forcedDisChgFunctionButton);
            }
        });
        this.forcedDisChgPowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerCmdEditText);
        Button button65 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerCmdButton);
        this.forcedDisChgPowerCmdButton = button65;
        button65.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.77
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_FORCED_DISCHG_POWER_CMD", local12KSetFragment.forcedDisChgPowerCmdEditText.getText().toString().trim(), Local12KSetFragment.this.forcedDisChgPowerCmdButton);
            }
        });
        this.forcedDisChgSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgSocLimitEditText);
        Button button66 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgSocLimitButton);
        this.forcedDisChgSocLimitButton = button66;
        button66.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.78
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_FORCED_DISCHG_SOC_LIMIT", local12KSetFragment.forcedDisChgSocLimitEditText.getText().toString().trim(), Local12KSetFragment.this.forcedDisChgSocLimitButton);
            }
        });
        this.stopDischgVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_stopDischgVoltEditText);
        Button button67 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_stopDischgVoltButton);
        this.stopDischgVoltButton = button67;
        button67.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.79
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_STOP_DISCHG_VOLT", local12KSetFragment.stopDischgVoltEditText.getText().toString().trim(), Local12KSetFragment.this.stopDischgVoltButton);
            }
        });
        this.forcedDisChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHourEditText);
        this.forcedDisChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinuteEditText);
        Button button68 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTimeButton);
        this.forcedDisChargeStartTimeButton = button68;
        button68.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.80
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME", local12KSetFragment.forcedDisChargeStartHourEditText, Local12KSetFragment.this.forcedDisChargeStartMinuteEditText, Local12KSetFragment.this.forcedDisChargeStartTimeButton);
            }
        });
        this.forcedDisChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHourEditText);
        this.forcedDisChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinuteEditText);
        Button button69 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTimeButton);
        this.forcedDisChargeEndTimeButton = button69;
        button69.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.81
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME", local12KSetFragment.forcedDisChargeEndHourEditText, Local12KSetFragment.this.forcedDisChargeEndMinuteEditText, Local12KSetFragment.this.forcedDisChargeEndTimeButton);
            }
        });
        this.forcedDisChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHour1EditText);
        this.forcedDisChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinute1EditText);
        Button button70 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTime1Button);
        this.forcedDisChargeStartTime1Button = button70;
        button70.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.82
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME_1", local12KSetFragment.forcedDisChargeStartHour1EditText, Local12KSetFragment.this.forcedDisChargeStartMinute1EditText, Local12KSetFragment.this.forcedDisChargeStartTime1Button);
            }
        });
        this.forcedDisChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHour1EditText);
        this.forcedDisChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinute1EditText);
        Button button71 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTime1Button);
        this.forcedDisChargeEndTime1Button = button71;
        button71.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.83
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME_1", local12KSetFragment.forcedDisChargeEndHour1EditText, Local12KSetFragment.this.forcedDisChargeEndMinute1EditText, Local12KSetFragment.this.forcedDisChargeEndTime1Button);
            }
        });
        this.forcedDisChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHour2EditText);
        this.forcedDisChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinute2EditText);
        Button button72 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTime2Button);
        this.forcedDisChargeStartTime2Button = button72;
        button72.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.84
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME_2", local12KSetFragment.forcedDisChargeStartHour2EditText, Local12KSetFragment.this.forcedDisChargeStartMinute2EditText, Local12KSetFragment.this.forcedDisChargeStartTime2Button);
            }
        });
        this.forcedDisChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHour2EditText);
        this.forcedDisChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinute2EditText);
        Button button73 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTime2Button);
        this.forcedDisChargeEndTime2Button = button73;
        button73.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.85
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME_2", local12KSetFragment.forcedDisChargeEndHour2EditText, Local12KSetFragment.this.forcedDisChargeEndMinute2EditText, Local12KSetFragment.this.forcedDisChargeEndTime2Button);
            }
        });
        this.leadAcidDischargeCutOffVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltEditText);
        Button button74 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltButton);
        this.leadAcidDischargeCutOffVoltButton = button74;
        button74.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.86
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT", local12KSetFragment.leadAcidDischargeCutOffVoltEditText.getText().toString().trim(), Local12KSetFragment.this.leadAcidDischargeCutOffVoltButton);
            }
        });
        this.dischgCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_dischgCurrentEditText);
        Button button75 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_dischgCurrentButton);
        this.dischgCurrentButton = button75;
        button75.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.87
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_RATE", local12KSetFragment.dischgCurrentEditText.getText().toString().trim(), Local12KSetFragment.this.dischgCurrentButton);
            }
        });
        this.onGridDischargeCutoffSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_onGridDischargeCutoffSocEditText);
        Button button76 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_onGridDischargeCutoffSocButton);
        this.onGridDischargeCutoffSocButton = button76;
        button76.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.88
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_DISCHG_CUT_OFF_SOC_EOD", local12KSetFragment.onGridDischargeCutoffSocEditText.getText().toString().trim(), Local12KSetFragment.this.onGridDischargeCutoffSocButton);
            }
        });
        this.offGridDischargeCutoffSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocEditText);
        Button button77 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocButton);
        this.offGridDischargeCutoffSocButton = button77;
        button77.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.89
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_SOC_LOW_LIMIT_EPS_DISCHG", local12KSetFragment.offGridDischargeCutoffSocEditText.getText().toString().trim(), Local12KSetFragment.this.offGridDischargeCutoffSocButton);
            }
        });
        this.onGridEodVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodVoltageEditText);
        Button button78 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodVoltageButton);
        this.onGridEodVoltageButton = button78;
        button78.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.90
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("HOLD_ON_GRID_EOD_VOLTAGE", local12KSetFragment.onGridEodVoltageEditText.getText().toString().trim(), Local12KSetFragment.this.onGridEodVoltageButton);
            }
        });
        ToggleButton toggleButton10 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingFunctionButton);
        this.gridPeakShavingFunctionButton = toggleButton10;
        toggleButton10.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.91
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_GRID_PEAK_SHAVING", local12KSetFragment.gridPeakShavingFunctionButton);
            }
        });
        this.gridPeakShavingPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingPowerEditText);
        Button button79 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingPowerButton);
        this.gridPeakShavingPowerButton = button79;
        button79.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.92
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_POWER", local12KSetFragment.gridPeakShavingPowerEditText.getText().toString().trim(), Local12KSetFragment.this.gridPeakShavingPowerButton);
            }
        });
        this.gridPeakShavingVolt1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt1EditText);
        Button button80 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt1Button);
        this.gridPeakShavingVolt1Button = button80;
        button80.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.93
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_VOLT", local12KSetFragment.gridPeakShavingVolt1EditText.getText().toString().trim(), Local12KSetFragment.this.gridPeakShavingVolt1Button);
            }
        });
        this.gridPeakShavingSoc1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc1EditText);
        Button button81 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc1Button);
        this.gridPeakShavingSoc1Button = button81;
        button81.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.94
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_SOC", local12KSetFragment.gridPeakShavingSoc1EditText.getText().toString().trim(), Local12KSetFragment.this.gridPeakShavingSoc1Button);
            }
        });
        this.gridPeakShavingVolt2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt2EditText);
        Button button82 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt2Button);
        this.gridPeakShavingVolt2Button = button82;
        button82.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.95
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2", local12KSetFragment.gridPeakShavingVolt2EditText.getText().toString().trim(), Local12KSetFragment.this.gridPeakShavingVolt2Button);
            }
        });
        this.gridPeakShavingSoc2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc2EditText);
        Button button83 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc2Button);
        this.gridPeakShavingSoc2Button = button83;
        button83.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.96
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2", local12KSetFragment.gridPeakShavingSoc2EditText.getText().toString().trim(), Local12KSetFragment.this.gridPeakShavingSoc2Button);
            }
        });
        this.peakShavingStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartHour1EditText);
        this.peakShavingStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartMinute1EditText);
        Button button84 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartTime1Button);
        this.peakShavingStartTime1Button = button84;
        button84.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.97
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_START_TIME_1", local12KSetFragment.peakShavingStartHour1EditText, Local12KSetFragment.this.peakShavingStartMinute1EditText, Local12KSetFragment.this.peakShavingStartTime1Button);
            }
        });
        this.peakShavingEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndHour1EditText);
        this.peakShavingEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndMinute1EditText);
        Button button85 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndTime1Button);
        this.peakShavingEndTime1Button = button85;
        button85.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.98
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_END_TIME_1", local12KSetFragment.peakShavingEndHour1EditText, Local12KSetFragment.this.peakShavingEndMinute1EditText, Local12KSetFragment.this.peakShavingEndTime1Button);
            }
        });
        this.peakShavingStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartHour2EditText);
        this.peakShavingStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartMinute2EditText);
        Button button86 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartTime2Button);
        this.peakShavingStartTime2Button = button86;
        button86.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.99
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_START_TIME_2", local12KSetFragment.peakShavingStartHour2EditText, Local12KSetFragment.this.peakShavingStartMinute2EditText, Local12KSetFragment.this.peakShavingStartTime2Button);
            }
        });
        this.peakShavingEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndHour2EditText);
        this.peakShavingEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndMinute2EditText);
        Button button87 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndTime2Button);
        this.peakShavingEndTime2Button = button87;
        button87.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.100
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_END_TIME_2", local12KSetFragment.peakShavingEndHour2EditText, Local12KSetFragment.this.peakShavingEndMinute2EditText, Local12KSetFragment.this.peakShavingEndTime2Button);
            }
        });
        ToggleButton toggleButton11 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_onGridAlwaysOnFunctionButton);
        this.onGridAlwaysOnFunctionButton = toggleButton11;
        toggleButton11.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.101
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_ON_GRID_ALWAYS_ON", local12KSetFragment.onGridAlwaysOnFunctionButton);
            }
        });
        this.startPvPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_startPvPowerEditText);
        Button button88 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_startPvPowerButton);
        this.startPvPowerButton = button88;
        button88.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.102
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_START_PV_POWER", local12KSetFragment.startPvPowerEditText.getText().toString().trim(), Local12KSetFragment.this.startPvPowerButton);
            }
        });
        this.smartLoadStartVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartVoltEditText);
        Button button89 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartVoltButton);
        this.smartLoadStartVoltButton = button89;
        button89.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.103
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_START_VOLT", local12KSetFragment.smartLoadStartVoltEditText.getText().toString().trim(), Local12KSetFragment.this.smartLoadStartVoltButton);
            }
        });
        this.smartLoadEndVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndVoltEditText);
        Button button90 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndVoltButton);
        this.smartLoadEndVoltButton = button90;
        button90.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.104
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_END_VOLT", local12KSetFragment.smartLoadEndVoltEditText.getText().toString().trim(), Local12KSetFragment.this.smartLoadEndVoltButton);
            }
        });
        this.smartLoadStartSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartSocEditText);
        Button button91 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartSocButton);
        this.smartLoadStartSocButton = button91;
        button91.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.105
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_START_SOC", local12KSetFragment.smartLoadStartSocEditText.getText().toString().trim(), Local12KSetFragment.this.smartLoadStartSocButton);
            }
        });
        this.smartLoadEndSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndSocEditText);
        Button button92 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndSocButton);
        this.smartLoadEndSocButton = button92;
        button92.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.106
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_END_SOC", local12KSetFragment.smartLoadEndSocEditText.getText().toString().trim(), Local12KSetFragment.this.smartLoadEndSocButton);
            }
        });
        this.acCoupleStartVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartVoltEditText);
        Button button93 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartVoltButton);
        this.acCoupleStartVoltButton = button93;
        button93.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.107
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_START_VOLT", local12KSetFragment.acCoupleStartVoltEditText.getText().toString().trim(), Local12KSetFragment.this.acCoupleStartVoltButton);
            }
        });
        this.acCoupleEndVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndVoltEditText);
        Button button94 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndVoltButton);
        this.acCoupleEndVoltButton = button94;
        button94.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.108
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_END_VOLT", local12KSetFragment.acCoupleEndVoltEditText.getText().toString().trim(), Local12KSetFragment.this.acCoupleEndVoltButton);
            }
        });
        this.acCoupleStartSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartSocEditText);
        Button button95 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartSocButton);
        this.acCoupleStartSocButton = button95;
        button95.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.109
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_START_SOC", local12KSetFragment.acCoupleStartSocEditText.getText().toString().trim(), Local12KSetFragment.this.acCoupleStartSocButton);
            }
        });
        this.acCoupleEndSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndSocEditText);
        Button button96 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndSocButton);
        this.acCoupleEndSocButton = button96;
        button96.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.110
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_END_SOC", local12KSetFragment.acCoupleEndSocEditText.getText().toString().trim(), Local12KSetFragment.this.acCoupleEndSocButton);
            }
        });
        ToggleButton toggleButton12 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_gridBossEnabledFunctionButton);
        this.gridBossEnabledFunctionButton = toggleButton12;
        toggleButton12.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.111
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_MIDBOX_EN", local12KSetFragment.gridBossEnabledFunctionButton);
            }
        });
        ToggleButton toggleButton13 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_ctDirectionReversedFunctionButton);
        this.ctDirectionReversedFunctionButton = toggleButton13;
        toggleButton13.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.112
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_CT_DIRECTION_REVERSED", local12KSetFragment.ctDirectionReversedFunctionButton);
            }
        });
        ToggleButton toggleButton14 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_pvArcFunctionButton);
        this.pvArcFunctionButton = toggleButton14;
        toggleButton14.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.113
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runControlRemoteWrite("FUNC_PV_ARC", local12KSetFragment.pvArcFunctionButton);
            }
        });
        Button button97 = (Button) viewInflate.findViewById(R.id.fragment_local_set_all2DefaultButton);
        this.all2DefaultButton = button97;
        button97.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.114
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment.this.runNormalRemoteWrite("ALL_TO_DEFAULT", String.valueOf(2), Local12KSetFragment.this.all2DefaultButton);
            }
        });
        this.datalogSnEditText = (EditText) viewInflate.findViewById(R.id.fragment_local_set_datalogSnEditText);
        this.pinEditText = (EditText) viewInflate.findViewById(R.id.fragment_local_set_pinEditText);
        Button button98 = (Button) viewInflate.findViewById(R.id.fragment_local_set_datalogSnButton);
        this.datalogSnButton = button98;
        button98.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.115
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Local12KSetFragment.this.datalogSnEditText.getText().toString();
                String string2 = Local12KSetFragment.this.pinEditText.getText().toString();
                if (Tool.isEmpty(string) || Tool.isEmpty(string2)) {
                    Toast.makeText(Local12KSetFragment.this.getActivity().getApplicationContext(), R.string.page_maintain_remote_set_alert_param_empty, 1).show();
                    return;
                }
                if (string.length() != 10) {
                    Toast.makeText(Local12KSetFragment.this.getActivity().getApplicationContext(), R.string.page_register_error_datalogSn_length, 1).show();
                    return;
                }
                if (PinTool.verifyDatalog(string, string2)) {
                    try {
                        Local12KSetFragment.this.runDatalogParamWrite(1, string.getBytes(LocalizedMessage.DEFAULT_ENCODING), Local12KSetFragment.this.datalogSnButton);
                        return;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                Toast.makeText(Local12KSetFragment.this.getActivity().getApplicationContext(), R.string.page_register_error_check_code_not_match, 1).show();
            }
        });
        this.serverIpSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_serverIpSpinner);
        Button button99 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_readServerIpButton);
        this.readServerIpButton = button99;
        button99.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.116
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment.this.serverIpSpinner.setAdapter((SpinnerAdapter) new ArrayAdapter(Local12KSetFragment.this.getContext(), android.R.layout.simple_spinner_item, new ArrayList()));
                Local12KSetFragment.this.readServerIpButton.setEnabled(false);
                Local12KSetFragment.this.setServerIpButton.setEnabled(false);
                new ReadDatalogParamTask(Local12KSetFragment.this).execute(6);
            }
        });
        Button button100 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_setServerIpButton);
        this.setServerIpButton = button100;
        button100.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.117
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Local12KSetFragment.this.serverIpSpinner.getSelectedItem();
                if (property != null) {
                    try {
                        Local12KSetFragment.this.runDatalogParamWrite(6, property.getName().getBytes(LocalizedMessage.DEFAULT_ENCODING), Local12KSetFragment.this.setServerIpButton);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Button button101 = (Button) viewInflate.findViewById(R.id.fragment_local_set_reset2FactoryButton);
        this.reset2FactoryButton = button101;
        button101.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.118
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
                local12KSetFragment.runDatalogParamWrite(3, new byte[]{-91}, local12KSetFragment.reset2FactoryButton);
            }
        });
        this.applicationSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_titleLayout);
        this.applicationSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_layout);
        this.applicationSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_textView);
        this.applicationSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_toggleButton);
        this.applicationSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_paramLayout);
        this.applicationSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.119
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.applicationSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.applicationSetParamLayout.setVisibility(8);
                }
            }
        });
        this.applicationSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.120
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.applicationSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.applicationSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.applicationSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.applicationSetParamLayout.setVisibility(0);
                }
            }
        });
        this.batterySettingTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_battery_setting_titleLayout);
        this.batterySettingActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_battery_setting_layout);
        this.batterySettingActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_battery_setting_textView);
        this.batterySettingActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_battery_setting_toggleButton);
        this.batterySettingParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_battery_setting_paramLayout);
        this.batterySettingActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.121
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.batterySettingActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.batterySettingActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.batterySettingParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.batterySettingActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.batterySettingParamLayout.setVisibility(8);
                }
            }
        });
        this.batterySettingActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.122
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.batterySettingActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.batterySettingActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.batterySettingActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.batterySettingParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.batterySettingActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.batterySettingActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.batterySettingParamLayout.setVisibility(0);
                }
            }
        });
        this.gridConnectSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_titleLayout);
        this.gridConnectSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_layout);
        this.gridConnectSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_textView);
        this.gridConnectSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_toggleButton);
        this.gridConnectSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_paramLayout);
        this.gridConnectSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.123
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.gridConnectSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.gridConnectSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.gridConnectSetParamLayout.setVisibility(8);
                }
            }
        });
        this.gridConnectSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.124
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.gridConnectSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.gridConnectSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.gridConnectSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.gridConnectSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.gridConnectSetParamLayout.setVisibility(0);
                }
            }
        });
        this.generatorSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_layout);
        this.generatorSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_textView);
        this.generatorSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_toggleButton);
        this.generatorSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_paramLayout);
        this.generatorSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.125
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.generatorSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.generatorSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.generatorSetParamLayout.setVisibility(8);
                }
            }
        });
        this.generatorSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.126
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.generatorSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.generatorSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.generatorSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.generatorSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.generatorSetParamLayout.setVisibility(0);
                }
            }
        });
        this.chargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_layout);
        this.chargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_textView);
        this.chargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_toggleButton);
        this.chargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_paramLayout);
        this.chargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.127
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.chargeSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.chargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.chargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.128
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.chargeSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.chargeSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.chargeSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.chargeSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_layout);
        this.dischargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_textView);
        this.dischargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_toggleButton);
        this.dischargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_paramLayout);
        this.dischargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.129
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.130
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargeSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.dischargeSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargePeakShavingSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_peak_shaving_set_titleLayout);
        this.dischargePeakShavingSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_peak_shaving_set_layout);
        TextView textView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_peak_shaving_set);
        this.dischargePeakShavingSetTitleTextView = textView;
        textView.setText(getString(R.string.page_maintain_remote_set_label_discharge_set) + " - " + getString(R.string.phrase_param_peak_shaving));
        this.dischargePeakShavingSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_peak_shaving_set_textView);
        this.dischargePeakShavingSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_peak_shaving_set_toggleButton);
        this.dischargePeakShavingSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_peak_shaving_set_paramLayout);
        this.dischargePeakShavingSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.131
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargePeakShavingSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargePeakShavingSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.132
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargePeakShavingSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargePeakShavingSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.dischargePeakShavingSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargeSmartLoadSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_smart_load_set_titleLayout);
        this.dischargeSmartLoadSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_smart_load_set_layout);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_smart_load_set);
        this.dischargeSmartLoadSetTitleTextView = textView2;
        textView2.setText(getString(R.string.page_maintain_remote_set_label_discharge_set) + " - " + getString(R.string.phrase_param_smart_load));
        this.dischargeSmartLoadSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_smart_load_set_textView);
        this.dischargeSmartLoadSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_smart_load_set_toggleButton);
        this.dischargeSmartLoadSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_smart_load_set_paramLayout);
        this.dischargeSmartLoadSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.133
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargeSmartLoadSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeSmartLoadSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.134
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargeSmartLoadSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargeSmartLoadSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.dischargeSmartLoadSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargeAcCoupleSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_ac_couple_set_titleLayout);
        this.dischargeAcCoupleSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_ac_couple_set_layout);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_ac_couple_set);
        this.dischargeAcCoupleSetTitleTextView = textView3;
        textView3.setText(getString(R.string.page_maintain_remote_set_label_discharge_set) + " - " + getString(R.string.phrase_param_ac_couple));
        this.dischargeAcCoupleSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_ac_couple_set_textView);
        this.dischargeAcCoupleSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_ac_couple_set_toggleButton);
        this.dischargeAcCoupleSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_dischg_ac_couple_set_paramLayout);
        this.dischargeAcCoupleSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.135
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargeAcCoupleSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeAcCoupleSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.136
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.dischargeAcCoupleSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.dischargeAcCoupleSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.dischargeAcCoupleSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(0);
                }
            }
        });
        this.advancedSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_layout);
        this.advancedSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_textView);
        this.advancedSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_toggleButton);
        this.advancedSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_paramLayout);
        this.advancedSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.137
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.advancedSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.advancedSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.advancedSetParamLayout.setVisibility(8);
                }
            }
        });
        this.advancedSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.138
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.advancedSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.advancedSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.advancedSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.advancedSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.advancedSetParamLayout.setVisibility(0);
                }
            }
        });
        this.wifiModuleSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_layout);
        this.wifiModuleSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_textView);
        this.wifiModuleSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_toggleButton);
        this.wifiModuleSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_paramLayout);
        this.wifiModuleSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.139
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.wifiModuleSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.wifiModuleSetParamLayout.setVisibility(0);
                } else {
                    Local12KSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.wifiModuleSetParamLayout.setVisibility(8);
                }
            }
        });
        this.wifiModuleSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.140
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Local12KSetFragment.this.wifiModuleSetActionToggleButton.isChecked()) {
                    Local12KSetFragment.this.wifiModuleSetActionToggleButton.setChecked(false);
                    Local12KSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_expand);
                    Local12KSetFragment.this.wifiModuleSetParamLayout.setVisibility(8);
                } else {
                    Local12KSetFragment.this.wifiModuleSetActionToggleButton.setChecked(true);
                    Local12KSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_collapse);
                    Local12KSetFragment.this.wifiModuleSetParamLayout.setVisibility(0);
                }
            }
        });
        Button button102 = (Button) viewInflate.findViewById(R.id.fragment_local_set_updateFirmwareButton);
        this.updateFirmwareButton = button102;
        button102.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m513x6ccdf46c(view);
            }
        });
        Button button103 = (Button) viewInflate.findViewById(R.id.fragment_local_set_export_ul_compliance_pdf);
        this.exportULCompliancePDFButton = button103;
        button103.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m514x86e9730b(view);
            }
        });
        this.created = true;
        return viewInflate;
    }

    /* renamed from: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$8, reason: invalid class name */
    class AnonymousClass8 implements View.OnClickListener {
        AnonymousClass8() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            boolean zIsChecked = Local12KSetFragment.this.setToStandbyFunctionButton.isChecked();
            AlertDialog.Builder builder = new AlertDialog.Builder(LocalActivity.instance);
            builder.setTitle(zIsChecked ? R.string.phrase_func_param_normaly : R.string.phrase_func_param_standby).setIcon(android.R.drawable.ic_dialog_info).setMessage(Local12KSetFragment.this.getString(zIsChecked ? R.string.phrase_func_text_normal : R.string.phrase_func_text_standby) + " " + ((Object) Local12KSetFragment.this.inverterSnTextView.getText())).setPositiveButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$8$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m515xf77ac91(dialogInterface, i);
                }
            }).setNegativeButton(R.string.phrase_button_cancel, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$8$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m516x10adff70(dialogInterface, i);
                }
            });
            builder.show();
        }

        /* renamed from: lambda$onClick$0$com-nfcx-eg4-view-local-fragment-Local12KSetFragment$8, reason: not valid java name */
        /* synthetic */ void m515xf77ac91(DialogInterface dialogInterface, int i) {
            Local12KSetFragment local12KSetFragment = Local12KSetFragment.this;
            local12KSetFragment.runControlRemoteWrite("FUNC_SET_TO_STANDBY", local12KSetFragment.setToStandbyFunctionButton);
        }

        /* renamed from: lambda$onClick$1$com-nfcx-eg4-view-local-fragment-Local12KSetFragment$8, reason: not valid java name */
        /* synthetic */ void m516x10adff70(DialogInterface dialogInterface, int i) {
            Local12KSetFragment.this.setToStandbyFunctionButton.setChecked(!Local12KSetFragment.this.setToStandbyFunctionButton.isChecked());
        }
    }

    /* renamed from: lambda$onCreateView$0$com-nfcx-eg4-view-local-fragment-Local12KSetFragment, reason: not valid java name */
    /* synthetic */ void m513x6ccdf46c(View view) {
        Inverter inverter = this.localConnect.getInverter();
        Intent intent = new Intent(view.getContext(), (Class<?>) ((inverter == null || !inverter.isType6()) ? UpdateFirmwareActivity.class : UpdateExtFirmwareActivity.class));
        intent.putExtra(Constants.LOCAL_CONNECT_TYPE, this.localConnect.getConnectType());
        startActivity(intent);
    }

    /* renamed from: lambda$onCreateView$1$com-nfcx-eg4-view-local-fragment-Local12KSetFragment, reason: not valid java name */
    /* synthetic */ void m514x86e9730b(View view) {
        if (this.cacheReadAllResultObj != null) {
            Intent intent = new Intent(view.getContext(), (Class<?>) ULComplianceActivity.class);
            intent.putExtra("cacheReadAllResultText", this.cacheReadAllResultObj.toString());
            startActivity(intent);
        }
    }

    public void refreshFragmentParams() {
        if (this.created) {
            Inverter inverter = this.localConnect.getInverter();
            this.datalogSnTextView.setText(inverter != null ? inverter.getDatalogSn() : "");
            this.inverterSnTextView.setText(inverter != null ? inverter.getSerialNum() : "");
            this.updateFirmwareButton.setEnabled((this.localConnect.getInverter() == null || Tool.isEmpty(this.localConnect.getInverter().getFwCode())) ? false : true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("Eg4 - Local12KFragment onResume...");
        refreshFragmentParams();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Eg4 - Local12KSetFragment onDestroy...");
    }

    public void clearFragmentData() {
        if (this.created) {
            this.timeDateEditText.setText("");
            this.timeTimeEditText.setText("");
            this.ctSampleRatioSpinner.setSelection(0);
            this.ctPowerOffsetEditText.setText("");
            this.batteryTypeSpinner.setSelection(0);
            this.lithiumBrandSpinner.setSelection(0);
            this.leadAcidCapacitysetEditText.setText("");
            this.absorbVoltageSetEditText.setText("");
            this.floatVoltageSetEditText.setText("");
            this.onGridDischargeSetEditText.setText("");
            this.setToStandbyFunctionButton.setChecked(false);
            this.epsFunctionButton.setChecked(false);
            this.epsFrequencySetSpinner.setSelection(0);
            this.go2OffGridFunctionButton.setChecked(false);
            this.runWithoutGridFunctionButton.setChecked(false);
            this.masterOrSlaveSpinner.setSelection(0);
            this.readComposedPhaseSpinner.setSelection(0);
            this.setComposedPhaseSpinner.setSelection(0);
            this.batterySharedFunctionButton.setChecked(false);
            this.maxAcInputPowerEditText.setText("");
            this.gridRegulationSpinner.setSelection(0);
            this.gridTypeSpinner.setSelection(0);
            this.drmsFunctionButton.setChecked(false);
            this.reconnectTimeEditText.setText("");
            this.gridVoltConnLowEditText.setText("");
            this.gridVoltConnHighEditText.setText("");
            this.gridFreqConnLowEditText.setText("");
            this.gridFreqConnHighEditText.setText("");
            this.gridVoltLimit1LowEditText.setText("");
            this.gridVoltLimit1HighEditText.setText("");
            this.gridVoltLimit2LowEditText.setText("");
            this.gridVoltLimit2HighEditText.setText("");
            this.gridVoltLimit3LowEditText.setText("");
            this.gridVoltLimit3HighEditText.setText("");
            this.gridFreqLimit1LowEditText.setText("");
            this.gridFreqLimit1HighEditText.setText("");
            this.gridFreqLimit2LowEditText.setText("");
            this.gridFreqLimit2HighEditText.setText("");
            this.gridFreqLimit3LowEditText.setText("");
            this.gridFreqLimit3HighEditText.setText("");
            this.batChargeCurrentLimitEditText.setText("");
            this.genRatedPowerEditText.setText("");
            this.chargeStartVoltEditText.setText("");
            this.chargeEndVoltEditText.setText("");
            this.chargeStartSocEditText.setText("");
            this.chargeEndSocEditText.setText("");
            this.batChargeControlSpinner.setSelection(0);
            this.acChargeFunctionButton.setChecked(false);
            this.acChargePowerEditText.setText("");
            this.startAcChargeSOCEditText.setText("");
            this.acChargeSocLimitEditText.setText("");
            this.startAcChargeVoltEditText.setText("");
            this.acChargeEndBatteryVoltageEditText.setText("");
            this.acChargeStartHourEditText.setText("");
            this.acChargeStartMinuteEditText.setText("");
            this.acChargeEndHourEditText.setText("");
            this.acChargeEndMinuteEditText.setText("");
            this.acChargeStartHour1EditText.setText("");
            this.acChargeStartMinute1EditText.setText("");
            this.acChargeEndHour1EditText.setText("");
            this.acChargeEndMinute1EditText.setText("");
            this.acChargeStartHour2EditText.setText("");
            this.acChargeStartMinute2EditText.setText("");
            this.acChargeEndHour2EditText.setText("");
            this.acChargeEndMinute2EditText.setText("");
            this.forcedChgFunctionButton.setChecked(false);
            this.forcedChgPowerCmdEditText.setText("");
            this.forcedChgSocLimitEditText.setText("");
            this.chargeFirstVoltEditText.setText("");
            this.forcedChargeStartHourEditText.setText("");
            this.forcedChargeStartMinuteEditText.setText("");
            this.forcedChargeEndHourEditText.setText("");
            this.forcedChargeEndMinuteEditText.setText("");
            this.forcedChargeStartHour1EditText.setText("");
            this.forcedChargeStartMinute1EditText.setText("");
            this.forcedChargeEndHour1EditText.setText("");
            this.forcedChargeEndMinute1EditText.setText("");
            this.forcedChargeStartHour2EditText.setText("");
            this.forcedChargeStartMinute2EditText.setText("");
            this.forcedChargeEndHour2EditText.setText("");
            this.forcedChargeEndMinute2EditText.setText("");
            this.leadAcidChargeVoltRefEditText.setText("");
            this.floatingVoltageEditText.setText("");
            this.chargeCurrentEditText.setText("");
            this.batDischargeControlSpinner.setSelection(0);
            this.forcedDisChgFunctionButton.setChecked(false);
            this.forcedDisChgPowerCmdEditText.setText("");
            this.forcedDisChgSocLimitEditText.setText("");
            this.forcedDisChargeStartHourEditText.setText("");
            this.forcedDisChargeStartMinuteEditText.setText("");
            this.forcedDisChargeEndHourEditText.setText("");
            this.forcedDisChargeEndMinuteEditText.setText("");
            this.forcedDisChargeStartHour1EditText.setText("");
            this.forcedDisChargeStartMinute1EditText.setText("");
            this.forcedDisChargeEndHour1EditText.setText("");
            this.forcedDisChargeEndMinute1EditText.setText("");
            this.forcedDisChargeStartHour2EditText.setText("");
            this.forcedDisChargeStartMinute2EditText.setText("");
            this.forcedDisChargeEndHour2EditText.setText("");
            this.forcedDisChargeEndMinute2EditText.setText("");
            this.onGridEodVoltageEditText.setText("");
            this.leadAcidDischargeCutOffVoltEditText.setText("");
            this.onGridDischargeCutoffSocEditText.setText("");
            this.offGridDischargeCutoffSocEditText.setText("");
            this.dischgCurrentEditText.setText("");
            this.stopDischgVoltEditText.setText("");
            this.gridPeakShavingFunctionButton.setChecked(false);
            this.gridPeakShavingPowerEditText.setText("");
            this.gridPeakShavingVolt1EditText.setText("");
            this.gridPeakShavingSoc1EditText.setText("");
            this.gridPeakShavingVolt2EditText.setText("");
            this.gridPeakShavingSoc2EditText.setText("");
            this.peakShavingStartHour1EditText.setText("");
            this.peakShavingStartMinute1EditText.setText("");
            this.peakShavingEndHour1EditText.setText("");
            this.peakShavingEndMinute1EditText.setText("");
            this.peakShavingStartHour2EditText.setText("");
            this.peakShavingStartMinute2EditText.setText("");
            this.peakShavingEndHour2EditText.setText("");
            this.peakShavingEndMinute2EditText.setText("");
            this.onGridAlwaysOnFunctionButton.setChecked(false);
            this.startPvPowerEditText.setText("");
            this.smartLoadStartVoltEditText.setText("");
            this.smartLoadEndVoltEditText.setText("");
            this.smartLoadStartSocEditText.setText("");
            this.smartLoadEndSocEditText.setText("");
            this.acCoupleStartVoltEditText.setText("");
            this.acCoupleEndVoltEditText.setText("");
            this.acCoupleStartSocEditText.setText("");
            this.acCoupleEndSocEditText.setText("");
            this.gridBossEnabledFunctionButton.setChecked(false);
            this.ctDirectionReversedFunctionButton.setChecked(false);
            this.pvArcFunctionButton.setChecked(false);
            this.datalogSnEditText.setText("");
            this.pinEditText.setText("");
            if (getContext() != null) {
                this.serverIpSpinner.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList()));
            }
            this.setServerIpButton.setEnabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runNormalRemoteWrite(String str, String str2, Button button) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.NORMAL);
        remoteWriteInfo.setHoldParam(str);
        remoteWriteInfo.setValueText(str2);
        remoteWriteInfo.setSetButton(button);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runBitRemoteWrite(String str, String str2, Button button) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.BIT_PARAM);
        remoteWriteInfo.setBitParam(str);
        remoteWriteInfo.setValueText(str2);
        remoteWriteInfo.setSetButton(button);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runBitModelRemoteWrite(String str, String str2, Button button) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.BIT_MODEL_PARAM);
        remoteWriteInfo.setModelBitParam(str);
        remoteWriteInfo.setValueText(str2);
        remoteWriteInfo.setSetButton(button);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runTimeRemoteWrite(String str, EditText editText, EditText editText2, Button button) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.TIME);
        remoteWriteInfo.setTimeParam(str);
        remoteWriteInfo.setHourText(editText.getText().toString().trim());
        remoteWriteInfo.setMinuteText(editText2.getText().toString().trim());
        remoteWriteInfo.setSetButton(button);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runControlRemoteWrite(String str, boolean z, Button button) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
        remoteWriteInfo.setFunctionParam(str);
        remoteWriteInfo.setFunctionToggleButtonChecked(z);
        remoteWriteInfo.setSetButton(button);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runControlRemoteWrite(String str, ToggleButton toggleButton) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
        remoteWriteInfo.setFunctionParam(str);
        if (toggleButton != null) {
            remoteWriteInfo.setFunctionToggleButtonChecked(toggleButton.isChecked());
            remoteWriteInfo.setFunctionToggleButton(toggleButton);
        }
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runDatalogParamWrite(int i, byte[] bArr, Button button) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.DATALOG_PARAM);
        remoteWriteInfo.setDatalogParamIndex(Integer.valueOf(i));
        remoteWriteInfo.setDatalogParamValues(bArr);
        remoteWriteInfo.setSetButton(button);
        new WriteParamTask(this).execute(remoteWriteInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ReadDatalogParamTask extends AsyncTask<Integer, JSONObject, Void> {
        private Local12KSetFragment fragment;

        public ReadDatalogParamTask(Local12KSetFragment local12KSetFragment) {
            this.fragment = local12KSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Integer... numArr) throws JSONException {
            for (Integer num : numArr) {
                String datalogParam = this.fragment.localConnect.readDatalogParam(num.intValue());
                if (!Tool.isEmpty(datalogParam)) {
                    try {
                        JSONObject jSONObjectCreateSuccessJSONObject = this.fragment.createSuccessJSONObject();
                        jSONObjectCreateSuccessJSONObject.put(String.valueOf(num), datalogParam);
                        publishProgress(jSONObjectCreateSuccessJSONObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                        FragmentActivity activity = this.fragment.getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$ReadDatalogParamTask$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m517xc2bc53a4();
                                }
                            });
                        }
                    }
                } else {
                    FragmentActivity activity2 = this.fragment.getActivity();
                    if (activity2 != null) {
                        activity2.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$ReadDatalogParamTask$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m518xf66a7e65();
                            }
                        });
                    }
                }
            }
            return null;
        }

        /* renamed from: lambda$doInBackground$0$com-nfcx-eg4-view-local-fragment-Local12KSetFragment$ReadDatalogParamTask, reason: not valid java name */
        /* synthetic */ void m517xc2bc53a4() {
            this.fragment.readServerIpButton.setEnabled(false);
        }

        /* renamed from: lambda$doInBackground$1$com-nfcx-eg4-view-local-fragment-Local12KSetFragment$ReadDatalogParamTask, reason: not valid java name */
        /* synthetic */ void m518xf66a7e65() {
            this.fragment.readServerIpButton.setEnabled(false);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(JSONObject... jSONObjectArr) throws JSONException {
            super.onProgressUpdate((Object[]) jSONObjectArr);
            for (JSONObject jSONObject : jSONObjectArr) {
                if (jSONObject != null) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Toast.makeText(this.fragment.getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_unknown_error, 1).show();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (jSONObject.getBoolean("success")) {
                        if (jSONObject.has(String.valueOf(1))) {
                            this.fragment.datalogSnEditText.setText(jSONObject.getString(String.valueOf(1)));
                            this.fragment.pinEditText.setText("");
                        } else if (jSONObject.has(String.valueOf(6))) {
                            String string = jSONObject.getString(String.valueOf(6));
                            System.out.println("Eg4 - serverIpAndPort = " + string);
                            this.fragment.readServerIpButton.setEnabled(true);
                            if (Constants.validServerIndexMap.containsKey(string)) {
                                ArrayAdapter arrayAdapter = new ArrayAdapter(this.fragment.getContext(), android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getFirstClusterServers());
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                this.fragment.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
                                this.fragment.setServerIpButton.setEnabled(true);
                                Integer num = Constants.validServerIndexMap.get(string);
                                if (num != null) {
                                    this.fragment.serverIpSpinner.setSelection(num.intValue());
                                }
                            } else if (Constants.CLUSTER_GROUP_SECOND.equals(string)) {
                                ArrayAdapter arrayAdapter2 = new ArrayAdapter(this.fragment.getContext(), android.R.layout.simple_spinner_item, GlobalInfo.getInstance().getSecondClusterServers());
                                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                this.fragment.serverIpSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
                                this.fragment.setServerIpButton.setEnabled(true);
                            }
                        }
                    }
                }
                this.fragment.toast(jSONObject);
                return;
            }
        }
    }

    private static class ReadMultiParamTask extends AsyncTask<RemoteReadInfo, JSONObject, Void> {
        private Local12KSetFragment fragment;

        public ReadMultiParamTask(Local12KSetFragment local12KSetFragment) {
            this.fragment = local12KSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(RemoteReadInfo... remoteReadInfoArr) throws JSONException {
            int i;
            JSONObject[] jSONObjectArr;
            int i2;
            RemoteReadInfo[] remoteReadInfoArr2 = remoteReadInfoArr;
            int length = remoteReadInfoArr2.length;
            int i3 = 0;
            while (i3 < length) {
                RemoteReadInfo remoteReadInfo = remoteReadInfoArr2[i3];
                int startRegister = remoteReadInfo.getStartRegister();
                int pointNumber = remoteReadInfo.getPointNumber();
                String strSendCommand = this.fragment.localConnect.sendCommand("read_multi_03_" + startRegister + "_" + (pointNumber * 2), DataFrameFactory.createReadMultiHoldDataFrame(this.fragment.localConnect.getTcpProtocol(), this.fragment.localConnect.getDatalogSn(), startRegister, pointNumber));
                JSONObject jSONObjectCreateSuccessJSONObject = this.fragment.createSuccessJSONObject();
                try {
                    if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 36) {
                        int i4 = startRegister;
                        while (i4 < startRegister + pointNumber) {
                            String[] holdParamsByStartRegister = this.fragment.getHoldParamsByStartRegister(i4);
                            if (holdParamsByStartRegister != null) {
                                int length2 = holdParamsByStartRegister.length;
                                int i5 = 0;
                                while (i5 < length2) {
                                    int i6 = pointNumber;
                                    String str = holdParamsByStartRegister[i5];
                                    if (Local12KSetFragment.getStartRegisterByParam(str) != null) {
                                        jSONObjectCreateSuccessJSONObject.put(str, this.fragment.getValueShowText(str, startRegister, strSendCommand));
                                    }
                                    if ("HOLD_MODEL".equals(str)) {
                                        int i7 = length;
                                        i2 = length2;
                                        try {
                                            i = i7;
                                        } catch (Exception e) {
                                            e = e;
                                            i = i7;
                                            e.printStackTrace();
                                            i3++;
                                            remoteReadInfoArr2 = remoteReadInfoArr;
                                            length = i;
                                        }
                                        try {
                                            Model model = new Model(Local12KSetFragment.getLong4ParamValue(str, startRegister, strSendCommand));
                                            jSONObjectCreateSuccessJSONObject.put(str + "_wirelessMeter", model.getWirelessMeter());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_meterType", model.getMeterType());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_usVersion", model.getUsVersion());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_meterBrand", model.getMeterBrand());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_measurement", model.getMeasurement());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_ruleMask", model.getRuleMask());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_batteryType", model.getBatteryType());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_leadAcidType", model.getLeadAcidType());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_lithiumType", model.getLithiumType());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_powerRating", model.getWholePowerRating());
                                            jSONObjectCreateSuccessJSONObject.put(str + "_rule", model.getRule());
                                        } catch (Exception e2) {
                                            e = e2;
                                            e.printStackTrace();
                                            i3++;
                                            remoteReadInfoArr2 = remoteReadInfoArr;
                                            length = i;
                                        }
                                    } else {
                                        i = length;
                                        i2 = length2;
                                    }
                                    i5++;
                                    pointNumber = i6;
                                    length2 = i2;
                                    length = i;
                                }
                            }
                            int i8 = pointNumber;
                            int i9 = length;
                            if (i4 == 21) {
                                int i10 = ((21 - startRegister) * 2) + 35;
                                int iCount = ProTool.count(strSendCommand.charAt(i10 + 1), strSendCommand.charAt(i10));
                                for (String str2 : this.fragment._21Functions) {
                                    jSONObjectCreateSuccessJSONObject.put(str2, ((1 << this.fragment.getBitByFunction(str2).intValue()) & iCount) > 0);
                                }
                            }
                            if (i4 == 110) {
                                int i11 = ((110 - startRegister) * 2) + 35;
                                int iCount2 = ProTool.count(strSendCommand.charAt(i11 + 1), strSendCommand.charAt(i11));
                                for (String str3 : this.fragment._110Functions) {
                                    jSONObjectCreateSuccessJSONObject.put(str3, ((1 << this.fragment.getBitByFunction(str3).intValue()) & iCount2) > 0);
                                }
                                jSONObjectCreateSuccessJSONObject.put("BIT_WORKING_MODE", (iCount2 >> this.fragment.getBitByBitParam("BIT_WORKING_MODE").intValue()) & 1);
                                jSONObjectCreateSuccessJSONObject.put("BIT_CT_SAMPLE_RATIO", (iCount2 >> this.fragment.getBitByBitParam("BIT_CT_SAMPLE_RATIO").intValue()) & 3);
                                jSONObjectCreateSuccessJSONObject.put("BIT_PVCT_SAMPLE_TYPE", (iCount2 >> this.fragment.getBitByBitParam("BIT_PVCT_SAMPLE_TYPE").intValue()) & 3);
                                jSONObjectCreateSuccessJSONObject.put("BIT_PVCT_SAMPLE_RATIO", (iCount2 >> this.fragment.getBitByBitParam("BIT_PVCT_SAMPLE_RATIO").intValue()) & 3);
                            }
                            if (i4 == 179) {
                                int i12 = ((179 - startRegister) * 2) + 35;
                                int iCount3 = ProTool.count(strSendCommand.charAt(i12 + 1), strSendCommand.charAt(i12));
                                for (String str4 : this.fragment._179Functions) {
                                    jSONObjectCreateSuccessJSONObject.put(str4, ((1 << this.fragment.getBitByFunction(str4).intValue()) & iCount3) > 0);
                                }
                            }
                            if (i4 == 226) {
                                int i13 = ((226 - startRegister) * 2) + 35;
                                int iCount4 = ProTool.count(strSendCommand.charAt(i13 + 1), strSendCommand.charAt(i13));
                                for (String str5 : this.fragment._226Functions) {
                                    jSONObjectCreateSuccessJSONObject.put(str5, ((1 << this.fragment.getBitByFunction(str5).intValue()) & iCount4) > 0);
                                }
                            }
                            i4++;
                            pointNumber = i8;
                            length = i9;
                        }
                    }
                    i = length;
                    jSONObjectArr = new JSONObject[1];
                } catch (Exception e3) {
                    e = e3;
                    i = length;
                }
                try {
                    jSONObjectArr[0] = jSONObjectCreateSuccessJSONObject;
                    publishProgress(jSONObjectArr);
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    i3++;
                    remoteReadInfoArr2 = remoteReadInfoArr;
                    length = i;
                }
                i3++;
                remoteReadInfoArr2 = remoteReadInfoArr;
                length = i;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't wrap try/catch for region: R(10:565|6|(48:8|(8:579|10|11|583|12|13|581|14)(1:22)|571|23|24|(2:26|(1:30)(1:29))|34|(1:36)|37|(1:39)|40|(1:42)|43|(3:45|(1:47)(2:48|(1:50)(1:51))|52)|53|(1:55)|56|(1:58)|59|60|(2:62|(1:66)(1:65))|67|(4:69|(1:73)(1:72)|74|(1:78)(1:77))|79|557|80|(6:591|82|83|(3:577|84|(2:86|(2:602|88)(1:89))(2:601|90))|(1:99)(2:95|(1:97)(1:98))|100)(1:107)|108|563|109|(5:553|111|112|(2:113|(2:115|(2:600|117)(1:118))(2:599|119))|120)(1:124)|125|587|126|(1:128)|129|575|130|(1:132)|133|(1:135)|136|(1:138)|139|(2:141|(1:146)(1:145))|147|(3:561|149|(229:155|156|157|161|(1:163)|164|(1:166)|167|(1:169)|170|(1:172)|173|(1:175)|176|(1:178)|179|(1:181)|182|(1:184)|185|(1:187)|188|(1:190)|191|(1:193)|194|(1:196)|197|(1:199)|200|(1:202)|203|(1:205)|206|(1:208)|209|(1:211)|212|(1:214)|215|(1:217)|218|(1:220)|221|(1:223)|224|(1:226)|227|(1:229)|230|(1:232)|233|(1:235)|236|(1:238)|239|(1:241)|242|(1:244)|245|(1:247)|248|(1:250)|251|(1:253)|254|(1:256)|257|(1:259)|260|(1:262)|263|(1:265)|266|(1:268)|269|(1:271)|272|(1:274)|275|(1:277)|278|(1:280)|281|(1:283)|284|(1:286)|287|(1:289)|290|(1:292)|293|(1:295)|296|(1:298)|299|(1:301)|302|(1:304)|305|(1:307)|308|(1:310)|311|(1:313)|314|(1:316)|317|585|318|(5:589|320|321|573|322)(1:329)|330|567|331|(5:551|333|334|569|335)(1:338)|339|(5:341|342|(1:344)(1:345)|555|346)(1:347)|348|(1:350)|351|(1:353)|354|(1:356)|357|(1:359)|360|(1:362)|363|(1:365)|366|(1:368)|369|(3:371|(1:373)(1:374)|375)|376|(1:378)|379|(1:381)|382|(1:384)|385|(1:387)|388|(1:390)|391|(1:393)|394|(1:396)|397|(1:399)|400|(1:402)|403|(1:405)|406|(1:408)|409|(1:411)|412|(1:414)|415|(1:417)|418|(1:420)|421|(1:423)|424|(1:426)|427|(1:429)|430|(1:432)|433|(1:435)|436|(1:438)|439|(1:441)|442|(1:444)|445|(1:447)|448|(1:450)|451|(1:453)|454|(1:456)|457|(1:459)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|(1:474)|475|(1:477)|478|(1:480)|481|(1:483)|484|(1:486)|487|(1:489)|490|(1:492)|493|(1:495)|496|(1:498)|499|(1:501)|502|(1:504)|505|(1:507)|508|(1:510)|511|(1:513)|514|(1:516)|517|(2:519|597)(1:596))(228:153|160|161|(0)|164|(0)|167|(0)|170|(0)|173|(0)|176|(0)|179|(0)|182|(0)|185|(0)|188|(0)|191|(0)|194|(0)|197|(0)|200|(0)|203|(0)|206|(0)|209|(0)|212|(0)|215|(0)|218|(0)|221|(0)|224|(0)|227|(0)|230|(0)|233|(0)|236|(0)|239|(0)|242|(0)|245|(0)|248|(0)|251|(0)|254|(0)|257|(0)|260|(0)|263|(0)|266|(0)|269|(0)|272|(0)|275|(0)|278|(0)|281|(0)|284|(0)|287|(0)|290|(0)|293|(0)|296|(0)|299|(0)|302|(0)|305|(0)|308|(0)|311|(0)|314|(0)|317|585|318|(0)(0)|330|567|331|(0)(0)|339|(0)(0)|348|(0)|351|(0)|354|(0)|357|(0)|360|(0)|363|(0)|366|(0)|369|(0)|376|(0)|379|(0)|382|(0)|385|(0)|388|(0)|391|(0)|394|(0)|397|(0)|400|(0)|403|(0)|406|(0)|409|(0)|412|(0)|415|(0)|418|(0)|421|(0)|424|(0)|427|(0)|430|(0)|433|(0)|436|(0)|439|(0)|442|(0)|445|(0)|448|(0)|451|(0)|454|(0)|457|(0)|460|(0)|463|(0)|466|(0)|469|(0)|472|(0)|475|(0)|478|(0)|481|(0)|484|(0)|487|(0)|490|(0)|493|(0)|496|(0)|499|(0)|502|(0)|505|(0)|508|(0)|511|(0)|514|(0)|517|(0)(0)))(227:160|161|(0)|164|(0)|167|(0)|170|(0)|173|(0)|176|(0)|179|(0)|182|(0)|185|(0)|188|(0)|191|(0)|194|(0)|197|(0)|200|(0)|203|(0)|206|(0)|209|(0)|212|(0)|215|(0)|218|(0)|221|(0)|224|(0)|227|(0)|230|(0)|233|(0)|236|(0)|239|(0)|242|(0)|245|(0)|248|(0)|251|(0)|254|(0)|257|(0)|260|(0)|263|(0)|266|(0)|269|(0)|272|(0)|275|(0)|278|(0)|281|(0)|284|(0)|287|(0)|290|(0)|293|(0)|296|(0)|299|(0)|302|(0)|305|(0)|308|(0)|311|(0)|314|(0)|317|585|318|(0)(0)|330|567|331|(0)(0)|339|(0)(0)|348|(0)|351|(0)|354|(0)|357|(0)|360|(0)|363|(0)|366|(0)|369|(0)|376|(0)|379|(0)|382|(0)|385|(0)|388|(0)|391|(0)|394|(0)|397|(0)|400|(0)|403|(0)|406|(0)|409|(0)|412|(0)|415|(0)|418|(0)|421|(0)|424|(0)|427|(0)|430|(0)|433|(0)|436|(0)|439|(0)|442|(0)|445|(0)|448|(0)|451|(0)|454|(0)|457|(0)|460|(0)|463|(0)|466|(0)|469|(0)|472|(0)|475|(0)|478|(0)|481|(0)|484|(0)|487|(0)|490|(0)|493|(0)|496|(0)|499|(0)|502|(0)|505|(0)|508|(0)|511|(0)|514|(0)|517|(0)(0))|549)|542|543|544|559|545|598|549) */
        /* JADX WARN: Code restructure failed: missing block: B:547:0x0e6a, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:548:0x0e6b, code lost:
        
            r0.printStackTrace();
         */
        /* JADX WARN: Removed duplicated region for block: B:163:0x03d6 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:166:0x03ed A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:169:0x0404 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:172:0x041b A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:175:0x0432 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:178:0x0449 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:181:0x0460 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:184:0x0477 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:187:0x048e A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:190:0x04a5 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:193:0x04bc A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:196:0x04d3 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:199:0x04ea A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:202:0x0501 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:205:0x0518 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:208:0x052f A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:211:0x0546 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:214:0x055d A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:217:0x0574 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:220:0x058b A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:223:0x05a2 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:226:0x05b9 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:229:0x05d0 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:232:0x05e7 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:235:0x05fe A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:238:0x0615 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:241:0x062c A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:244:0x0643 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:247:0x065a A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:250:0x0671 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:253:0x0688 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:256:0x069f A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:259:0x06b6 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:262:0x06cd A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:265:0x06e4 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:268:0x06fb A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:271:0x0712 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:274:0x0729 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:277:0x0740 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:280:0x0757 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:283:0x076e A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:286:0x0785 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:289:0x079c A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:292:0x07b3 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:295:0x07ca A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:298:0x07e1 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:301:0x07f8 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:304:0x080f A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:307:0x0826 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:310:0x083d A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:313:0x0854 A[Catch: Exception -> 0x0e1f, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:316:0x086b A[Catch: Exception -> 0x0e1f, TRY_LEAVE, TryCatch #12 {Exception -> 0x0e1f, blocks: (B:130:0x032d, B:132:0x0333, B:133:0x0340, B:135:0x0348, B:136:0x0357, B:138:0x035f, B:139:0x036e, B:141:0x0376, B:145:0x0385, B:146:0x0391, B:147:0x039b, B:153:0x03b2, B:157:0x03c5, B:161:0x03ce, B:163:0x03d6, B:164:0x03e5, B:166:0x03ed, B:167:0x03fc, B:169:0x0404, B:170:0x0413, B:172:0x041b, B:173:0x042a, B:175:0x0432, B:176:0x0441, B:178:0x0449, B:179:0x0458, B:181:0x0460, B:182:0x046f, B:184:0x0477, B:185:0x0486, B:187:0x048e, B:188:0x049d, B:190:0x04a5, B:191:0x04b4, B:193:0x04bc, B:194:0x04cb, B:196:0x04d3, B:197:0x04e2, B:199:0x04ea, B:200:0x04f9, B:202:0x0501, B:203:0x0510, B:205:0x0518, B:206:0x0527, B:208:0x052f, B:209:0x053e, B:211:0x0546, B:212:0x0555, B:214:0x055d, B:215:0x056c, B:217:0x0574, B:218:0x0583, B:220:0x058b, B:221:0x059a, B:223:0x05a2, B:224:0x05b1, B:226:0x05b9, B:227:0x05c8, B:229:0x05d0, B:230:0x05df, B:232:0x05e7, B:233:0x05f6, B:235:0x05fe, B:236:0x060d, B:238:0x0615, B:239:0x0624, B:241:0x062c, B:242:0x063b, B:244:0x0643, B:245:0x0652, B:247:0x065a, B:248:0x0669, B:250:0x0671, B:251:0x0680, B:253:0x0688, B:254:0x0697, B:256:0x069f, B:257:0x06ae, B:259:0x06b6, B:260:0x06c5, B:262:0x06cd, B:263:0x06dc, B:265:0x06e4, B:266:0x06f3, B:268:0x06fb, B:269:0x070a, B:271:0x0712, B:272:0x0721, B:274:0x0729, B:275:0x0738, B:277:0x0740, B:278:0x074f, B:280:0x0757, B:281:0x0766, B:283:0x076e, B:284:0x077d, B:286:0x0785, B:287:0x0794, B:289:0x079c, B:290:0x07ab, B:292:0x07b3, B:293:0x07c2, B:295:0x07ca, B:296:0x07d9, B:298:0x07e1, B:299:0x07f0, B:301:0x07f8, B:302:0x0807, B:304:0x080f, B:305:0x081e, B:307:0x0826, B:308:0x0835, B:310:0x083d, B:311:0x084c, B:313:0x0854, B:314:0x0863, B:316:0x086b), top: B:575:0x032d }] */
        /* JADX WARN: Removed duplicated region for block: B:329:0x08a9  */
        /* JADX WARN: Removed duplicated region for block: B:338:0x08d5  */
        /* JADX WARN: Removed duplicated region for block: B:341:0x08df A[Catch: Exception -> 0x0e11, TRY_LEAVE, TryCatch #9 {Exception -> 0x0e11, blocks: (B:335:0x08bb, B:339:0x08d7, B:341:0x08df), top: B:569:0x08bb }] */
        /* JADX WARN: Removed duplicated region for block: B:347:0x08fb  */
        /* JADX WARN: Removed duplicated region for block: B:350:0x0905 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:353:0x091c A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:356:0x0933 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:359:0x094a A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:362:0x0961 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:365:0x0978 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:368:0x098f A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:371:0x09a6 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:378:0x09c7 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:381:0x09de A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:384:0x09f5 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:387:0x0a0c A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:390:0x0a23 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:393:0x0a3a A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:396:0x0a51 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:399:0x0a68 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:402:0x0a7f A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:405:0x0a96 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:408:0x0aad A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:411:0x0ac4 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:414:0x0adb A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:417:0x0af2 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:420:0x0b09 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:423:0x0b20 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:426:0x0b37 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:429:0x0b4e A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:432:0x0b65 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:435:0x0b7c A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:438:0x0b93 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:441:0x0baa A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:444:0x0bc1 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:447:0x0bd8 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:450:0x0bef A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:453:0x0c06 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:456:0x0c1d A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:459:0x0c34 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:462:0x0c4b A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:465:0x0c62 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:468:0x0c79 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:471:0x0c90 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:474:0x0ca7 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:477:0x0cbe A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:480:0x0cd5 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:483:0x0cec A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:486:0x0d03 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:489:0x0d1a A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:492:0x0d31 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:495:0x0d48 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:498:0x0d5f A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:501:0x0d76 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:504:0x0d8d A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:507:0x0da4 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:510:0x0dbb A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:513:0x0dd2 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:516:0x0de9 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:519:0x0e00 A[Catch: Exception -> 0x0e4f, TryCatch #2 {Exception -> 0x0e4f, blocks: (B:346:0x08f2, B:348:0x08fd, B:350:0x0905, B:351:0x0914, B:353:0x091c, B:354:0x092b, B:356:0x0933, B:357:0x0942, B:359:0x094a, B:360:0x0959, B:362:0x0961, B:363:0x0970, B:365:0x0978, B:366:0x0987, B:368:0x098f, B:369:0x099e, B:371:0x09a6, B:375:0x09b7, B:376:0x09bf, B:378:0x09c7, B:379:0x09d6, B:381:0x09de, B:382:0x09ed, B:384:0x09f5, B:385:0x0a04, B:387:0x0a0c, B:388:0x0a1b, B:390:0x0a23, B:391:0x0a32, B:393:0x0a3a, B:394:0x0a49, B:396:0x0a51, B:397:0x0a60, B:399:0x0a68, B:400:0x0a77, B:402:0x0a7f, B:403:0x0a8e, B:405:0x0a96, B:406:0x0aa5, B:408:0x0aad, B:409:0x0abc, B:411:0x0ac4, B:412:0x0ad3, B:414:0x0adb, B:415:0x0aea, B:417:0x0af2, B:418:0x0b01, B:420:0x0b09, B:421:0x0b18, B:423:0x0b20, B:424:0x0b2f, B:426:0x0b37, B:427:0x0b46, B:429:0x0b4e, B:430:0x0b5d, B:432:0x0b65, B:433:0x0b74, B:435:0x0b7c, B:436:0x0b8b, B:438:0x0b93, B:439:0x0ba2, B:441:0x0baa, B:442:0x0bb9, B:444:0x0bc1, B:445:0x0bd0, B:447:0x0bd8, B:448:0x0be7, B:450:0x0bef, B:451:0x0bfe, B:453:0x0c06, B:454:0x0c15, B:456:0x0c1d, B:457:0x0c2c, B:459:0x0c34, B:460:0x0c43, B:462:0x0c4b, B:463:0x0c5a, B:465:0x0c62, B:466:0x0c71, B:468:0x0c79, B:469:0x0c88, B:471:0x0c90, B:472:0x0c9f, B:474:0x0ca7, B:475:0x0cb6, B:477:0x0cbe, B:478:0x0ccd, B:480:0x0cd5, B:481:0x0ce4, B:483:0x0cec, B:484:0x0cfb, B:486:0x0d03, B:487:0x0d12, B:489:0x0d1a, B:490:0x0d29, B:492:0x0d31, B:493:0x0d40, B:495:0x0d48, B:496:0x0d57, B:498:0x0d5f, B:499:0x0d6e, B:501:0x0d76, B:502:0x0d85, B:504:0x0d8d, B:505:0x0d9c, B:507:0x0da4, B:508:0x0db3, B:510:0x0dbb, B:511:0x0dca, B:513:0x0dd2, B:514:0x0de1, B:516:0x0de9, B:517:0x0df8, B:519:0x0e00, B:540:0x0e41), top: B:555:0x08f2 }] */
        /* JADX WARN: Removed duplicated region for block: B:551:0x08b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:589:0x0882 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:596:0x0e6f A[SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onProgressUpdate(org.json.JSONObject... r28) throws org.json.JSONException, java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 3711
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.ReadMultiParamTask.onProgressUpdate(org.json.JSONObject[]):void");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r2) {
            super.onPostExecute((ReadMultiParamTask) r2);
            this.fragment.readAllButton.setEnabled(true);
            this.fragment.exportULCompliancePDFButton.setEnabled(true);
        }
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private Local12KSetFragment fragment;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(Local12KSetFragment local12KSetFragment) {
            this.fragment = local12KSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
            this.remoteWriteInfo.setEnabled(false);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(RemoteWriteInfo... remoteWriteInfoArr) throws JSONException, NumberFormatException {
            Integer numValueOf;
            Integer bitByFunction;
            Integer numValueOf2;
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                publishProgress(new Void[0]);
                int i = AnonymousClass141.$SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()];
                if (i == 1) {
                    String valueText = remoteWriteInfo.getValueText();
                    if (Tool.isEmpty(valueText)) {
                        return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                    }
                    String holdParam = remoteWriteInfo.getHoldParam();
                    Integer startRegisterByParam = Local12KSetFragment.getStartRegisterByParam(holdParam);
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
                }
                if (i == 2) {
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
                }
                if (i == 3) {
                    Integer startRegisterByParam2 = Local12KSetFragment.getStartRegisterByParam("HOLD_MODEL");
                    JSONObject multiHold = this.fragment.localConnect.readMultiHold(startRegisterByParam2.intValue(), 2);
                    try {
                        if (!multiHold.getBoolean("success")) {
                            return this.fragment.createFailureJSONObject("FAILED");
                        }
                        System.out.println("readValuesResultText == " + multiHold);
                        String string = multiHold.getString("valueFrame");
                        long jCount4 = ProTool.count4(string.charAt(3), string.charAt(2), string.charAt(1), string.charAt(0));
                        Integer bitByModelBitParam = this.fragment.getBitByModelBitParam(remoteWriteInfo.getModelBitParam());
                        Integer bitSizeByModelBitParam = this.fragment.getBitSizeByModelBitParam(remoteWriteInfo.getModelBitParam());
                        Integer maskValueByModelBitParam = this.fragment.getMaskValueByModelBitParam(remoteWriteInfo.getModelBitParam());
                        if (bitByModelBitParam != null && bitSizeByModelBitParam != null && maskValueByModelBitParam != null) {
                            String valueText2 = remoteWriteInfo.getValueText();
                            if (!Local12KSetFragment.checkBitValueValid(maskValueByModelBitParam.intValue(), Integer.parseInt(valueText2))) {
                                return this.fragment.createFailureJSONObject("PARAM_ERROR");
                            }
                            int iIntValue2 = bitByModelBitParam.intValue() + bitSizeByModelBitParam.intValue();
                            return this.fragment.localConnect.writeSingle(startRegisterByParam2.intValue(), (int) ((((jCount4 >> iIntValue2) << iIntValue2) + (((long) Integer.parseInt(valueText2)) << bitByModelBitParam.intValue())) + (jCount4 & ((long) ((1 << bitByModelBitParam.intValue()) - 1))))) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                        }
                        return this.fragment.createFailureJSONObject("FAILED");
                    } catch (JSONException unused) {
                        return this.fragment.createFailureJSONObject("FAILED");
                    }
                }
                if (i == 4) {
                    String hourText = remoteWriteInfo.getHourText();
                    String minuteText = remoteWriteInfo.getMinuteText();
                    if (Tool.isEmpty(hourText) || Tool.isEmpty(minuteText)) {
                        return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                    }
                    try {
                        int i2 = Integer.parseInt(hourText);
                        int i3 = Integer.parseInt(minuteText);
                        if (i2 < 0 || i2 > 23) {
                            return this.fragment.createFailureJSONObject("OUT_RANGE_ERROR");
                        }
                        if (i3 < 0 || i3 > 59) {
                            return this.fragment.createFailureJSONObject("OUT_RANGE_ERROR");
                        }
                        return this.fragment.localConnect.writeSingle(Local12KSetFragment.getStartRegisterByParam(remoteWriteInfo.getTimeParam()).intValue(), ProTool.count(i3, i2)) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return this.fragment.createFailureJSONObject("INTEGER_FORMAT_ERROR");
                    }
                }
                if (i == 5) {
                    Integer registerByFunction = this.fragment.getRegisterByFunction(remoteWriteInfo.getFunctionParam());
                    Integer single032 = this.fragment.localConnect.readSingle03(registerByFunction.intValue());
                    if (single032 != null && (bitByFunction = this.fragment.getBitByFunction(remoteWriteInfo.getFunctionParam())) != null) {
                        if (remoteWriteInfo.isFunctionToggleButtonChecked()) {
                            numValueOf2 = Integer.valueOf(single032.intValue() | (1 << bitByFunction.intValue()));
                        } else {
                            numValueOf2 = Integer.valueOf(single032.intValue() & (~(1 << bitByFunction.intValue())) & 65535);
                        }
                        return this.fragment.localConnect.writeSingle(registerByFunction.intValue(), Integer.valueOf(numValueOf2.intValue() & 65535).intValue()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                    }
                    return this.fragment.createFailureJSONObject("FAILED");
                }
                if (i == 7) {
                    if (remoteWriteInfo.getDatalogParamIndex() == null || remoteWriteInfo.getDatalogParamValues() == null) {
                        return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                    }
                    return this.fragment.localConnect.writeDatalogParam(remoteWriteInfo.getDatalogParamIndex().intValue(), remoteWriteInfo.getDatalogParamValues()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                }
            }
            return this.fragment.createFailureJSONObject("UNKNOWN_ERROR");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0060 A[Catch: all -> 0x009d, Exception -> 0x009f, TryCatch #1 {Exception -> 0x009f, blocks: (B:4:0x0006, B:6:0x000e, B:8:0x001e, B:10:0x002c, B:12:0x003a, B:13:0x0046, B:15:0x0054, B:16:0x0060, B:18:0x0064, B:20:0x0072, B:22:0x007a, B:26:0x008f, B:27:0x0092), top: B:43:0x0006, outer: #2 }] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onPostExecute(org.json.JSONObject r4) {
            /*
                r3 = this;
                super.onPostExecute(r4)
                r0 = 1
                if (r4 == 0) goto L60
                java.lang.String r1 = "success"
                boolean r1 = r4.getBoolean(r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r1 == 0) goto L60
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                r1 = 2131886279(0x7f1200c7, float:1.9407132E38)
                com.nfcx.eg4.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r4 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r4 == 0) goto L97
                com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE r4 = com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE r1 = r1.getRemoteWriteType()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r4 = r4.equals(r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r4 == 0) goto L97
                java.lang.String r4 = "FUNC_BAT_CHARGE_CONTROL"
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                java.lang.String r1 = r1.getFunctionParam()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r4 = r4.equals(r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r4 == 0) goto L46
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r1 = r1.isFunctionToggleButtonChecked()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment.access$29400(r4, r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                goto L97
            L46:
                java.lang.String r4 = "FUNC_BAT_DISCHARGE_CONTROL"
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                java.lang.String r1 = r1.getFunctionParam()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r4 = r4.equals(r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r4 == 0) goto L97
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r1 = r1.isFunctionToggleButtonChecked()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment.access$29300(r4, r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                goto L97
            L60:
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r1 == 0) goto L92
                com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE r1 = com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.REMOTE_WRITE_TYPE r2 = r2.getRemoteWriteType()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r1 == 0) goto L92
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r1 == 0) goto L92
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                android.widget.ToggleButton r2 = r2.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                boolean r2 = r2.isChecked()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                if (r2 != 0) goto L8e
                r2 = r0
                goto L8f
            L8e:
                r2 = 0
            L8f:
                r1.setChecked(r2)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
            L92:
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment r1 = r3.fragment     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment.access$28100(r1, r4)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> L9f
            L97:
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r4 = r3.remoteWriteInfo
                r4.setEnabled(r0)
                goto Lb5
            L9d:
                r4 = move-exception
                goto Lb6
            L9f:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L9d
                com.nfcx.eg4.view.local.fragment.Local12KSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> Lb0
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> Lb0
                r1 = 2131886525(0x7f1201bd, float:1.9407631E38)
                com.nfcx.eg4.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L9d java.lang.Exception -> Lb0
                goto L97
            Lb0:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L9d
                goto L97
            Lb5:
                return
            Lb6:
                com.nfcx.eg4.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.nfcx.eg4.view.local.fragment.Local12KSetFragment$141, reason: invalid class name */
    static /* synthetic */ class AnonymousClass141 {
        static final /* synthetic */ int[] $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE;

        static {
            int[] iArr = new int[REMOTE_WRITE_TYPE.values().length];
            $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE = iArr;
            try {
                iArr[REMOTE_WRITE_TYPE.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.BIT_PARAM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.BIT_MODEL_PARAM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.TIME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.CONTROL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.RESET.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.DATALOG_PARAM.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshBattChgState(boolean z) {
        this.startAcChargeVoltEditText.setEnabled(z);
        this.startAcChargeVoltButton.setEnabled(z);
        this.acChargeEndBatteryVoltageEditText.setEnabled(z);
        this.acChargeEndBatteryVoltageButton.setEnabled(z);
        this.chargeFirstVoltEditText.setEnabled(z);
        this.chargeFirstVoltButton.setEnabled(z);
        this.chargeStartVoltEditText.setEnabled(z);
        this.chargeStartVoltButton.setEnabled(z);
        this.chargeEndVoltEditText.setEnabled(z);
        this.chargeEndVoltButton.setEnabled(z);
        this.startAcChargeSOCEditText.setEnabled(!z);
        this.startAcChargeSOCButton.setEnabled(!z);
        this.acChargeSocLimitEditText.setEnabled(!z);
        this.acChargeSocLimitButton.setEnabled(!z);
        this.forcedChgSocLimitEditText.setEnabled(!z);
        this.forcedChgSocLimitButton.setEnabled(!z);
        this.chargeStartSocEditText.setEnabled(!z);
        this.chargeStartSocButton.setEnabled(!z);
        this.chargeEndSocEditText.setEnabled(!z);
        this.chargeEndSocButton.setEnabled(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshBattDisChgState(boolean z) {
        this.stopDischgVoltEditText.setEnabled(z);
        this.stopDischgVoltButton.setEnabled(z);
        this.onGridEodVoltageEditText.setEnabled(z);
        this.onGridEodVoltageButton.setEnabled(z);
        this.leadAcidDischargeCutOffVoltEditText.setEnabled(z);
        this.leadAcidDischargeCutOffVoltButton.setEnabled(z);
        this.gridPeakShavingVolt1EditText.setEnabled(z);
        this.gridPeakShavingVolt1Button.setEnabled(z);
        this.gridPeakShavingVolt2EditText.setEnabled(z);
        this.gridPeakShavingVolt2Button.setEnabled(z);
        this.smartLoadStartVoltEditText.setEnabled(z);
        this.smartLoadStartVoltButton.setEnabled(z);
        this.smartLoadEndVoltEditText.setEnabled(z);
        this.smartLoadEndVoltButton.setEnabled(z);
        this.acCoupleStartVoltEditText.setEnabled(z);
        this.acCoupleStartVoltButton.setEnabled(z);
        this.acCoupleEndVoltEditText.setEnabled(z);
        this.acCoupleEndVoltButton.setEnabled(z);
        this.forcedDisChgSocLimitEditText.setEnabled(!z);
        this.forcedDisChgSocLimitButton.setEnabled(!z);
        this.onGridDischargeCutoffSocEditText.setEnabled(!z);
        this.onGridDischargeCutoffSocButton.setEnabled(!z);
        this.offGridDischargeCutoffSocEditText.setEnabled(!z);
        this.offGridDischargeCutoffSocButton.setEnabled(!z);
        this.gridPeakShavingSoc1EditText.setEnabled(!z);
        this.gridPeakShavingSoc1Button.setEnabled(!z);
        this.gridPeakShavingSoc2EditText.setEnabled(!z);
        this.gridPeakShavingSoc2Button.setEnabled(!z);
        this.smartLoadStartSocEditText.setEnabled(!z);
        this.smartLoadStartSocButton.setEnabled(!z);
        this.smartLoadEndSocEditText.setEnabled(!z);
        this.smartLoadEndSocButton.setEnabled(!z);
        this.acCoupleStartSocEditText.setEnabled(!z);
        this.acCoupleStartSocButton.setEnabled(!z);
        this.acCoupleEndSocEditText.setEnabled(!z);
        this.acCoupleEndSocButton.setEnabled(!z);
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

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getBitByModelBitParam(String str) {
        str.hashCode();
        if (str.equals("MODEL_BIT_BATTERY_TYPE")) {
            return 8;
        }
        return !str.equals("MODEL_BIT_LITHIUM_TYPE") ? null : 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getBitSizeByModelBitParam(String str) {
        str.hashCode();
        if (str.equals("MODEL_BIT_BATTERY_TYPE")) {
            return 2;
        }
        return !str.equals("MODEL_BIT_LITHIUM_TYPE") ? null : 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getMaskValueByModelBitParam(String str) {
        str.hashCode();
        if (str.equals("MODEL_BIT_BATTERY_TYPE")) {
            return 3;
        }
        return !str.equals("MODEL_BIT_LITHIUM_TYPE") ? null : 31;
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
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.getBitSizeByBitParam(java.lang.String):java.lang.Integer");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static Integer getStartRegisterByParam(String str) {
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
            case -2076784143:
                if (str.equals("_12K_HOLD_LEAD_CAPACITY")) {
                    c = 5;
                    break;
                }
                break;
            case -2070600516:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW_TIME")) {
                    c = 6;
                    break;
                }
                break;
            case -2064663285:
                if (str.equals("HOLD_SET_COMPOSED_PHASE")) {
                    c = 7;
                    break;
                }
                break;
            case -2051169280:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG")) {
                    c = '\b';
                    break;
                }
                break;
            case -2012582261:
                if (str.equals("HOLD_FEED_IN_GRID_POWER_PERCENT")) {
                    c = '\t';
                    break;
                }
                break;
            case -1922746271:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_VOLT_REF")) {
                    c = '\n';
                    break;
                }
                break;
            case -1917271147:
                if (str.equals("_12K_HOLD_START_PV_POWER")) {
                    c = 11;
                    break;
                }
                break;
            case -1910210817:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_1")) {
                    c = '\f';
                    break;
                }
                break;
            case -1910210816:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_2")) {
                    c = '\r';
                    break;
                }
                break;
            case -1907123448:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_END_VOLT")) {
                    c = 14;
                    break;
                }
                break;
            case -1905186084:
                if (str.equals("_12K_HOLD_SMART_LOAD_END_VOLT")) {
                    c = 15;
                    break;
                }
                break;
            case -1876698434:
                if (str.equals("HOLD_FORCED_DISCHG_SOC_LIMIT")) {
                    c = 16;
                    break;
                }
                break;
            case -1862575828:
                if (str.equals("_12K_HOLD_SMART_LOAD_END_SOC")) {
                    c = 17;
                    break;
                }
                break;
            case -1854323434:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG")) {
                    c = 18;
                    break;
                }
                break;
            case -1790606454:
                if (str.equals("HOLD_PEAK_SHAVING_END_TIME_1")) {
                    c = 19;
                    break;
                }
                break;
            case -1790606453:
                if (str.equals("HOLD_PEAK_SHAVING_END_TIME_2")) {
                    c = 20;
                    break;
                }
                break;
            case -1784648912:
                if (str.equals("HOLD_P_TO_USER_START_DISCHG")) {
                    c = 21;
                    break;
                }
                break;
            case -1774171999:
                if (str.equals("_12K_HOLD_OVF_DERATE_START_POINT")) {
                    c = 22;
                    break;
                }
                break;
            case -1738912721:
                if (str.equals("HOLD_EQUALIZATION_VOLTAGE")) {
                    c = 23;
                    break;
                }
                break;
            case -1724289777:
                if (str.equals("HOLD_UVF_DERATE_START_POINT")) {
                    c = 24;
                    break;
                }
                break;
            case -1670871462:
                if (str.equals("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR")) {
                    c = 25;
                    break;
                }
                break;
            case -1662706451:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_1")) {
                    c = 26;
                    break;
                }
                break;
            case -1662706450:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_2")) {
                    c = 27;
                    break;
                }
                break;
            case -1657690225:
                if (str.equals("HOLD_COM_ADDR")) {
                    c = 28;
                    break;
                }
                break;
            case -1612429665:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_1")) {
                    c = 29;
                    break;
                }
                break;
            case -1612429664:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_2")) {
                    c = 30;
                    break;
                }
                break;
            case -1605958379:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG")) {
                    c = 31;
                    break;
                }
                break;
            case -1567068683:
                if (str.equals("_12K_HOLD_SMART_LOAD_START_VOLT")) {
                    c = ' ';
                    break;
                }
                break;
            case -1563900533:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR")) {
                    c = '!';
                    break;
                }
                break;
            case -1563549068:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME")) {
                    c = '\"';
                    break;
                }
                break;
            case -1551617594:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR")) {
                    c = '#';
                    break;
                }
                break;
            case -1551266129:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME")) {
                    c = '$';
                    break;
                }
                break;
            case -1536953061:
                if (str.equals("_12K_HOLD_STOP_DISCHG_VOLT")) {
                    c = '%';
                    break;
                }
                break;
            case -1524255375:
                if (str.equals("HOLD_PV_INPUT_MODE")) {
                    c = '&';
                    break;
                }
                break;
            case -1475031011:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE")) {
                    c = '\'';
                    break;
                }
                break;
            case -1474656009:
                if (str.equals("_12K_HOLD_GRID_REGULATION")) {
                    c = '(';
                    break;
                }
                break;
            case -1448232788:
                if (str.equals("_12K_HOLD_AC_COUPLE_START_VOLT")) {
                    c = ')';
                    break;
                }
                break;
            case -1433471711:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
                    c = '*';
                    break;
                }
                break;
            case -1413838822:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
                    c = '+';
                    break;
                }
                break;
            case -1376342106:
                if (str.equals("HOLD_VREF_ADJUSTMENT_TIME_CONSTANT")) {
                    c = ',';
                    break;
                }
                break;
            case -1367869989:
                if (str.equals("ALL_TO_DEFAULT")) {
                    c = '-';
                    break;
                }
                break;
            case -1352201891:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
                    c = '.';
                    break;
                }
                break;
            case -1345314128:
                if (str.equals("HOLD_EQUALIZATION_PERIOD")) {
                    c = '/';
                    break;
                }
                break;
            case -1323572740:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
                    c = '0';
                    break;
                }
                break;
            case -1294943589:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
                    c = '1';
                    break;
                }
                break;
            case -1288210873:
                if (str.equals("_12K_HOLD_GRID_TYPE")) {
                    c = '2';
                    break;
                }
                break;
            case -1282654362:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
                    c = '3';
                    break;
                }
                break;
            case -1274671800:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_1")) {
                    c = '4';
                    break;
                }
                break;
            case -1274671799:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_2")) {
                    c = '5';
                    break;
                }
                break;
            case -1254025211:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
                    c = '6';
                    break;
                }
                break;
            case -1225396060:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
                    c = '7';
                    break;
                }
                break;
            case -1119226968:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_1")) {
                    c = '8';
                    break;
                }
                break;
            case -1119226967:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_2")) {
                    c = '9';
                    break;
                }
                break;
            case -1033230202:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_1")) {
                    c = ':';
                    break;
                }
                break;
            case -1033230201:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_2")) {
                    c = ';';
                    break;
                }
                break;
            case -1028461968:
                if (str.equals("HOLD_MIN_Q_PERCENT_FOR_QV")) {
                    c = Typography.less;
                    break;
                }
                break;
            case -917268805:
                if (str.equals("HOLD_FORCED_DISCHG_POWER_CMD")) {
                    c = '=';
                    break;
                }
                break;
            case -875057049:
                if (str.equals("HOLD_GRID_FREQ_CONN_HIGH")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case -823881906:
                if (str.equals("HOLD_MAX_AC_INPUT_POWER")) {
                    c = '?';
                    break;
                }
                break;
            case -750853128:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_1")) {
                    c = '@';
                    break;
                }
                break;
            case -750853127:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_2")) {
                    c = 'A';
                    break;
                }
                break;
            case -659862417:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH_TIME")) {
                    c = 'B';
                    break;
                }
                break;
            case -659327994:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH_TIME")) {
                    c = 'C';
                    break;
                }
                break;
            case -625075712:
                if (str.equals("HOLD_VOLT_WATT_DELAY_TIME")) {
                    c = 'D';
                    break;
                }
                break;
            case -613454474:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE")) {
                    c = 'E';
                    break;
                }
                break;
            case -595561232:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW")) {
                    c = 'F';
                    break;
                }
                break;
            case -594637711:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW")) {
                    c = 'G';
                    break;
                }
                break;
            case -593714190:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW")) {
                    c = 'H';
                    break;
                }
                break;
            case -573009974:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
                    c = 'I';
                    break;
                }
                break;
            case -550997124:
                if (str.equals("HOLD_EQUALIZATION_TIME")) {
                    c = 'J';
                    break;
                }
                break;
            case -535537138:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2")) {
                    c = 'K';
                    break;
                }
                break;
            case -444930136:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_1")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_LT;
                    break;
                }
                break;
            case -444930135:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_2")) {
                    c = 'M';
                    break;
                }
                break;
            case -431364128:
                if (str.equals("HOLD_FORCED_CHG_SOC_LIMIT")) {
                    c = 'N';
                    break;
                }
                break;
            case -413095263:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_1")) {
                    c = 'O';
                    break;
                }
                break;
            case -413095262:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_2")) {
                    c = 'P';
                    break;
                }
                break;
            case -386262971:
                if (str.equals("HOLD_OVF_DROOP_KOF")) {
                    c = 'Q';
                    break;
                }
                break;
            case -384118065:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                    break;
                }
                break;
            case -383766600:
                if (str.equals("HOLD_AC_CHARGE_START_TIME")) {
                    c = 'S';
                    break;
                }
                break;
            case -307531482:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE")) {
                    c = 'T';
                    break;
                }
                break;
            case -276744627:
                if (str.equals("HOLD_TIME")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_UT;
                    break;
                }
                break;
            case -276676643:
                if (str.equals("HOLD_VREF")) {
                    c = 'V';
                    break;
                }
                break;
            case -233655236:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_RATE")) {
                    c = 'W';
                    break;
                }
                break;
            case -214155815:
                if (str.equals("HOLD_DELAY_TIME_FOR_OVER_F_DERATE")) {
                    c = 'X';
                    break;
                }
                break;
            case -213248243:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_SOC")) {
                    c = 'Y';
                    break;
                }
                break;
            case -165590897:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_1")) {
                    c = Matrix.MATRIX_TYPE_ZERO;
                    break;
                }
                break;
            case -165590896:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_2")) {
                    c = '[';
                    break;
                }
                break;
            case -135057669:
                if (str.equals("HOLD_DISCHG_POWER_PERCENT_CMD")) {
                    c = '\\';
                    break;
                }
                break;
            case -107172271:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_1")) {
                    c = ']';
                    break;
                }
                break;
            case -107172270:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_2")) {
                    c = '^';
                    break;
                }
                break;
            case -26098721:
                if (str.equals("HOLD_REACTIVE_POWER_CMD_TYPE")) {
                    c = '_';
                    break;
                }
                break;
            case 4556681:
                if (str.equals("HOLD_MODEL")) {
                    c = '`';
                    break;
                }
                break;
            case 41202161:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH_TIME")) {
                    c = 'a';
                    break;
                }
                break;
            case 41736584:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH_TIME")) {
                    c = 'b';
                    break;
                }
                break;
            case 51556766:
                if (str.equals("HOLD_MAX_Q_PERCENT_FOR_QV")) {
                    c = 'c';
                    break;
                }
                break;
            case 106114747:
                if (str.equals("HOLD_CT_POWER_OFFSET")) {
                    c = 'd';
                    break;
                }
                break;
            case 124739648:
                if (str.equals("HOLD_ON_GRID_EOD_VOLTAGE")) {
                    c = 'e';
                    break;
                }
                break;
            case 127930925:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR")) {
                    c = 'f';
                    break;
                }
                break;
            case 128282390:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME")) {
                    c = 'g';
                    break;
                }
                break;
            case 159565091:
                if (str.equals("HOLD_MAINTENANCE_COUNT")) {
                    c = 'h';
                    break;
                }
                break;
            case 168754545:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW_TIME")) {
                    c = 'i';
                    break;
                }
                break;
            case 180157537:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG")) {
                    c = 'j';
                    break;
                }
                break;
            case 219635409:
                if (str.equals("HOLD_PF_CMD")) {
                    c = 'k';
                    break;
                }
                break;
            case 221415899:
                if (str.equals("HOLD_EPS_VOLT_SET")) {
                    c = 'l';
                    break;
                }
                break;
            case 229729985:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_1")) {
                    c = 'm';
                    break;
                }
                break;
            case 229729986:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_2")) {
                    c = 'n';
                    break;
                }
                break;
            case 265183773:
                if (str.equals("HOLD_START_PV_VOLT")) {
                    c = 'o';
                    break;
                }
                break;
            case 268168589:
                if (str.equals("HOLD_V1H")) {
                    c = 'p';
                    break;
                }
                break;
            case 268168593:
                if (str.equals("HOLD_V1L")) {
                    c = 'q';
                    break;
                }
                break;
            case 268168620:
                if (str.equals("HOLD_V2H")) {
                    c = 'r';
                    break;
                }
                break;
            case 268168624:
                if (str.equals("HOLD_V2L")) {
                    c = 's';
                    break;
                }
                break;
            case 305030215:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_START_SOC")) {
                    c = 't';
                    break;
                }
                break;
            case 313840816:
                if (str.equals("HOLD_GRID_VOLT_CONN_HIGH")) {
                    c = 'u';
                    break;
                }
                break;
            case 330144381:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_1")) {
                    c = 'v';
                    break;
                }
                break;
            case 330144382:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_2")) {
                    c = 'w';
                    break;
                }
                break;
            case 365088499:
                if (str.equals("_12K_HOLD_SMART_LOAD_START_SOC")) {
                    c = 'x';
                    break;
                }
                break;
            case 365268050:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW_TIME")) {
                    c = 'y';
                    break;
                }
                break;
            case 367128639:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE")) {
                    c = 'z';
                    break;
                }
                break;
            case 390956452:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR")) {
                    c = '{';
                    break;
                }
                break;
            case 391307917:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME")) {
                    c = '|';
                    break;
                }
                break;
            case 467543035:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE")) {
                    c = '}';
                    break;
                }
                break;
            case 485996371:
                if (str.equals("_12K_HOLD_AC_COUPLE_END_VOLT")) {
                    c = '~';
                    break;
                }
                break;
            case 528065501:
                if (str.equals("HOLD_FORCED_CHG_POWER_CMD")) {
                    c = Ascii.MAX;
                    break;
                }
                break;
            case 561781555:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW_TIME")) {
                    c = 128;
                    break;
                }
                break;
            case 565590738:
                if (str.equals("HOLD_EPS_FREQ_SET")) {
                    c = 129;
                    break;
                }
                break;
            case 567487850:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_1")) {
                    c = 130;
                    break;
                }
                break;
            case 567487851:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_2")) {
                    c = 131;
                    break;
                }
                break;
            case 574683163:
                if (str.equals("HOLD_SET_MASTER_OR_SLAVE")) {
                    c = 132;
                    break;
                }
                break;
            case 614838895:
                if (str.equals("_12K_HOLD_CHARGE_FIRST_VOLT")) {
                    c = 133;
                    break;
                }
                break;
            case 623306801:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_1")) {
                    c = 134;
                    break;
                }
                break;
            case 623306802:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_2")) {
                    c = 135;
                    break;
                }
                break;
            case 667902246:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_1")) {
                    c = 136;
                    break;
                }
                break;
            case 667902247:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_2")) {
                    c = 137;
                    break;
                }
                break;
            case 677731373:
                if (str.equals("HOLD_ACTIVE_POWER_PERCENT_CMD")) {
                    c = 138;
                    break;
                }
                break;
            case 695812793:
                if (str.equals("HOLD_VOLT_WATT_P2")) {
                    c = 139;
                    break;
                }
                break;
            case 695812978:
                if (str.equals("HOLD_VOLT_WATT_V1")) {
                    c = 140;
                    break;
                }
                break;
            case 695812979:
                if (str.equals("HOLD_VOLT_WATT_V2")) {
                    c = 141;
                    break;
                }
                break;
            case 767099658:
                if (str.equals("HOLD_DISCHG_CUT_OFF_SOC_EOD")) {
                    c = 142;
                    break;
                }
                break;
            case 814992216:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_1")) {
                    c = 143;
                    break;
                }
                break;
            case 814992217:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_2")) {
                    c = 144;
                    break;
                }
                break;
            case 866091809:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_START_VOLT")) {
                    c = 145;
                    break;
                }
                break;
            case 894260725:
                if (str.equals("HOLD_AC_CHARGE_SOC_LIMIT")) {
                    c = 146;
                    break;
                }
                break;
            case 946846866:
                if (str.equals("HOLD_SOC_LOW_LIMIT_EPS_DISCHG")) {
                    c = 147;
                    break;
                }
                break;
            case 1039780741:
                if (str.equals("HOLD_FLOATING_VOLTAGE")) {
                    c = 148;
                    break;
                }
                break;
            case 1118506598:
                if (str.equals("HOLD_GRID_VOLT_CONN_LOW")) {
                    c = 149;
                    break;
                }
                break;
            case 1203310617:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW")) {
                    c = 150;
                    break;
                }
                break;
            case 1204234138:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW")) {
                    c = 151;
                    break;
                }
                break;
            case 1205157659:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW")) {
                    c = 152;
                    break;
                }
                break;
            case 1224119563:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_POWER")) {
                    c = 153;
                    break;
                }
                break;
            case 1226871680:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2")) {
                    c = 154;
                    break;
                }
                break;
            case 1457278549:
                if (str.equals("HOLD_RECONNECT_TIME")) {
                    c = 155;
                    break;
                }
                break;
            case 1477300572:
                if (str.equals("_12K_HOLD_AC_COUPLE_START_SOC")) {
                    c = 156;
                    break;
                }
                break;
            case 1504508315:
                if (str.equals("HOLD_VBAT_START_DERATING")) {
                    c = 157;
                    break;
                }
                break;
            case 1527620681:
                if (str.equals("HOLD_MAX_GENERATOR_INPUT_POWER")) {
                    c = 158;
                    break;
                }
                break;
            case 1539695061:
                if (str.equals("_12K_HOLD_AC_COUPLE_END_SOC")) {
                    c = 159;
                    break;
                }
                break;
            case 1600103487:
                if (str.equals("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
                    c = Typography.nbsp;
                    break;
                }
                break;
            case 1613415905:
                if (str.equals("HOLD_DELAY_TIME_FOR_QV_CURVE")) {
                    c = 161;
                    break;
                }
                break;
            case 1809765729:
                if (str.equals("HOLD_P1")) {
                    c = Typography.cent;
                    break;
                }
                break;
            case 1809765730:
                if (str.equals("HOLD_P2")) {
                    c = Typography.pound;
                    break;
                }
                break;
            case 1809765731:
                if (str.equals("HOLD_P3")) {
                    c = 164;
                    break;
                }
                break;
            case 1809765762:
                if (str.equals("HOLD_Q3")) {
                    c = 165;
                    break;
                }
                break;
            case 1809765763:
                if (str.equals("HOLD_Q4")) {
                    c = 166;
                    break;
                }
                break;
            case 1831339770:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW_TIME")) {
                    c = Typography.section;
                    break;
                }
                break;
            case 1838153520:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH_TIME")) {
                    c = 168;
                    break;
                }
                break;
            case 1838687943:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH_TIME")) {
                    c = Typography.copyright;
                    break;
                }
                break;
            case 1853690354:
                if (str.equals("HOLD_AC_CHARGE_POWER_CMD")) {
                    c = 170;
                    break;
                }
                break;
            case 1890826442:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_SOC")) {
                    c = 171;
                    break;
                }
                break;
            case 1910924292:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_37")) {
                    c = 172;
                    break;
                }
                break;
            case 1910924293:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_38")) {
                    c = 173;
                    break;
                }
                break;
            case 1910924294:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_39")) {
                    c = Typography.registered;
                    break;
                }
                break;
            case 1910924316:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_40")) {
                    c = 175;
                    break;
                }
                break;
            case 1910924317:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_41")) {
                    c = Typography.degree;
                    break;
                }
                break;
            case 1910924318:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_42")) {
                    c = Typography.plusMinus;
                    break;
                }
                break;
            case 1910924319:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_43")) {
                    c = 178;
                    break;
                }
                break;
            case 1910924320:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_44")) {
                    c = 179;
                    break;
                }
                break;
            case 1925850636:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_RATE")) {
                    c = 180;
                    break;
                }
                break;
            case 1979328795:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_VOLT")) {
                    c = 181;
                    break;
                }
                break;
            case 1985523803:
                if (str.equals("HOLD_CHARGE_POWER_PERCENT_CMD")) {
                    c = Typography.paragraph;
                    break;
                }
                break;
            case 1989908579:
                if (str.equals("HOLD_PEAK_SHAVING_START_TIME_1")) {
                    c = Typography.middleDot;
                    break;
                }
                break;
            case 1989908580:
                if (str.equals("HOLD_PEAK_SHAVING_START_TIME_2")) {
                    c = 184;
                    break;
                }
                break;
            case 2016686976:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_END_SOC")) {
                    c = 185;
                    break;
                }
                break;
            case 2020464802:
                if (str.equals("HOLD_CONNECT_TIME")) {
                    c = 186;
                    break;
                }
                break;
            case 2027853275:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW_TIME")) {
                    c = 187;
                    break;
                }
                break;
            case 2036981088:
                if (str.equals("HOLD_REACTIVE_POWER_PERCENT_CMD")) {
                    c = 188;
                    break;
                }
                break;
            case 2046998614:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_1")) {
                    c = Typography.half;
                    break;
                }
                break;
            case 2046998615:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_2")) {
                    c = 190;
                    break;
                }
                break;
            case 2069975635:
                if (str.equals("HOLD_POWER_SOFT_START_SLOPE")) {
                    c = 191;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256);
            case 1:
            case '{':
            case R.styleable.AppCompatTheme_windowMinWidthMajor /* 124 */:
                return 84;
            case 2:
                return 27;
            case 3:
            case 4:
            case 'T':
                return 69;
            case 5:
                return 204;
            case 6:
                return 31;
            case 7:
                return 113;
            case '\b':
                return 108;
            case '\t':
                return 103;
            case '\n':
                return 99;
            case 11:
                return 217;
            case '\f':
            case ':':
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 189 */:
                return 86;
            case '\r':
            case ';':
            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 190 */:
                return 88;
            case 14:
                return Integer.valueOf(CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256);
            case 15:
                return 214;
            case 16:
                return 83;
            case 17:
                return 216;
            case 18:
                return 106;
            case 19:
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256 /* 174 */:
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384 /* 175 */:
                return 210;
            case 20:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256 /* 178 */:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384 /* 179 */:
                return 212;
            case 21:
                return 116;
            case 22:
                return 115;
            case 23:
                return Integer.valueOf(CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA);
            case 24:
                return Integer.valueOf(CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA);
            case 25:
                return Integer.valueOf(CipherSuite.TLS_SM4_GCM_SM3);
            case 26:
            case 'v':
            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA /* 136 */:
                return 87;
            case 27:
            case 'w':
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA /* 137 */:
                return 89;
            case 28:
                return 15;
            case 29:
            case '4':
            case 'Z':
                return 79;
            case 30:
            case '5':
            case '[':
                return 81;
            case 31:
                return 107;
            case ' ':
                return 213;
            case '!':
            case '\"':
            case R.styleable.AppCompatTheme_windowMinWidthMinor /* 125 */:
                return 85;
            case '#':
            case '$':
            case 'E':
                return 76;
            case '%':
                return 202;
            case '&':
                return 20;
            case '\'':
            case 'f':
            case 'g':
                return 77;
            case '(':
                return 203;
            case ')':
                return 222;
            case '*':
                return Integer.valueOf(CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256);
            case '+':
                return Integer.valueOf(CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384);
            case ',':
                return Integer.valueOf(CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256);
            case '-':
                return 11;
            case '.':
                return 43;
            case '/':
                return Integer.valueOf(CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA);
            case '0':
                return 47;
            case '1':
                return 51;
            case '2':
                return 205;
            case '3':
                return 30;
            case '6':
                return 34;
            case '7':
                return 38;
            case '8':
            case '@':
            case 'O':
                return 78;
            case '9':
            case 'A':
            case 'P':
                return 80;
            case '<':
                return 121;
            case '=':
                return 82;
            case '>':
                return 28;
            case '?':
                return Integer.valueOf(CipherSuite.TLS_PSK_WITH_NULL_SHA256);
            case 'B':
                return 45;
            case 'C':
                return 32;
            case 'D':
                return Integer.valueOf(CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384);
            case 'F':
                return 29;
            case 'G':
                return 33;
            case 'H':
                return 37;
            case 'I':
                return 100;
            case 'J':
                return Integer.valueOf(CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA);
            case 'K':
                return 219;
            case 'L':
            case ']':
            case CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA /* 143 */:
                return 71;
            case 'M':
            case '^':
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /* 144 */:
                return 73;
            case 'N':
                return 75;
            case 'Q':
                return Integer.valueOf(CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA);
            case 'R':
            case 'S':
            case 'z':
                return 68;
            case 'U':
                return 12;
            case 'V':
                return Integer.valueOf(CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384);
            case 'W':
                return 102;
            case 'X':
                return 97;
            case 'Y':
                return 207;
            case '\\':
                return 65;
            case '_':
                return 59;
            case '`':
                return 0;
            case 'a':
                return 53;
            case 'b':
                return 40;
            case 'c':
                return 54;
            case 'd':
                return 119;
            case 'e':
                return Integer.valueOf(CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384);
            case 'h':
                return 122;
            case 'i':
                return 52;
            case 'j':
                return 109;
            case 'k':
                return 62;
            case 'l':
                return 90;
            case 'm':
            case 130:
            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA /* 134 */:
                return 70;
            case 'n':
            case Constants.SUB_DEVICE_TYPE_OFF_GRID_US /* 131 */:
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA /* 135 */:
                return 72;
            case 'o':
                return 22;
            case 'p':
                return 57;
            case 'q':
                return 55;
            case 'r':
                return 58;
            case 's':
                return 56;
            case 't':
                return Integer.valueOf(CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256);
            case 'u':
                return 26;
            case 'x':
                return 215;
            case 'y':
                return 48;
            case R.styleable.AppCompatTheme_windowNoTitle /* 126 */:
                return 223;
            case 127:
                return 74;
            case 128:
                return 44;
            case 129:
                return 91;
            case CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA /* 132 */:
                return 112;
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA /* 133 */:
                return 201;
            case CipherSuite.TLS_PSK_WITH_RC4_128_SHA /* 138 */:
                return 60;
            case CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA /* 139 */:
                return Integer.valueOf(CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256);
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /* 140 */:
                return Integer.valueOf(CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384);
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /* 141 */:
                return Integer.valueOf(CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256);
            case CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA /* 142 */:
                return 105;
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA /* 145 */:
                return Integer.valueOf(CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256);
            case CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA /* 146 */:
                return 67;
            case CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA /* 147 */:
                return Integer.valueOf(R.styleable.AppCompatTheme_windowMinWidthMinor);
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA /* 148 */:
                return Integer.valueOf(CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA);
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA /* 149 */:
                return 25;
            case CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA /* 150 */:
                return 42;
            case CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA /* 151 */:
                return 46;
            case CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA /* 152 */:
                return 50;
            case CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA /* 153 */:
                return 206;
            case CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA /* 154 */:
                return 218;
            case 155:
                return 24;
            case CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256 /* 156 */:
                return 220;
            case CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384 /* 157 */:
                return 118;
            case CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 /* 158 */:
                return Integer.valueOf(CipherSuite.TLS_PSK_WITH_NULL_SHA384);
            case CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 /* 159 */:
                return 221;
            case CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 /* 160 */:
                return 41;
            case 161:
                return 96;
            case 162:
                return Integer.valueOf(CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256);
            case 163:
                return Integer.valueOf(CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256);
            case 164:
                return Integer.valueOf(CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256);
            case CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384 /* 165 */:
                return Integer.valueOf(CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256);
            case CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256 /* 166 */:
                return 188;
            case CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384 /* 167 */:
                return 39;
            case CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256 /* 168 */:
                return 49;
            case CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384 /* 169 */:
                return 36;
            case 170:
                return 66;
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384 /* 171 */:
                return Integer.valueOf(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256 /* 172 */:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384 /* 173 */:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384 /* 183 */:
                return 209;
            case CipherSuite.TLS_PSK_WITH_NULL_SHA256 /* 176 */:
            case CipherSuite.TLS_PSK_WITH_NULL_SHA384 /* 177 */:
            case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256 /* 184 */:
                return Integer.valueOf(Primes.SMALL_FACTOR_LIMIT);
            case CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA256 /* 180 */:
                return 101;
            case CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384 /* 181 */:
                return 208;
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256 /* 182 */:
                return 64;
            case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384 /* 185 */:
                return Integer.valueOf(CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256);
            case CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 186 */:
                return 23;
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 187 */:
                return 35;
            case 188:
                return 61;
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256 /* 191 */:
                return 63;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:269:0x03b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getValueByParam(java.lang.String r5, java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 1372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.getValueByParam(java.lang.String, java.lang.String):java.lang.Integer");
    }

    private static int checkNegativeValue(String str, int i) {
        str.hashCode();
        return (str.equals("HOLD_CT_POWER_OFFSET") && i < 0) ? 65535 & i : i;
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
                return Integer.valueOf(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384);
            case "FUNC_MIDBOX_EN":
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
    /* JADX WARN: Removed duplicated region for block: B:4:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getBitByFunction(java.lang.String r27) {
        /*
            Method dump skipped, instructions count: 690
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.view.local.fragment.Local12KSetFragment.getBitByFunction(java.lang.String):java.lang.Integer");
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

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getHoldParamsByStartRegister(int i) {
        switch (i) {
            case 0:
                return new String[]{"HOLD_MODEL"};
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            case 21:
            case 92:
            case 93:
            case 94:
            case 95:
            case 98:
            case 104:
            case 110:
            case 111:
            case 114:
            case 117:
            case 120:
            case 123:
            case R.styleable.AppCompatTheme_windowMinWidthMajor /* 124 */:
            case R.styleable.AppCompatTheme_windowNoTitle /* 126 */:
            case 127:
            case 128:
            case 129:
            case 130:
            case Constants.SUB_DEVICE_TYPE_OFF_GRID_US /* 131 */:
            case CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA /* 132 */:
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA /* 133 */:
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA /* 135 */:
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA /* 137 */:
            case CipherSuite.TLS_PSK_WITH_RC4_128_SHA /* 138 */:
            case CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA /* 139 */:
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /* 140 */:
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /* 141 */:
            case CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA /* 142 */:
            case CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA /* 143 */:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA /* 145 */:
            case CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA /* 146 */:
            case CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA /* 147 */:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA /* 148 */:
            case CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA /* 152 */:
            case CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA /* 153 */:
            case CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA /* 154 */:
            case 155:
            case CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256 /* 156 */:
            case CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384 /* 157 */:
            case 161:
            case 162:
            case 163:
            case 164:
            case CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384 /* 165 */:
            case CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256 /* 166 */:
            case CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384 /* 167 */:
            case CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256 /* 168 */:
            case 170:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384 /* 171 */:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256 /* 172 */:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384 /* 173 */:
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256 /* 174 */:
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384 /* 175 */:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256 /* 178 */:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384 /* 179 */:
            case CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA256 /* 180 */:
            case 192:
            case CipherSuite.TLS_SM4_CCM_SM3 /* 199 */:
            case 200:
            default:
                return null;
            case 2:
                return new String[]{"HOLD_SERIAL_NUM"};
            case 7:
                return new String[]{"HOLD_FW_CODE"};
            case 12:
                return new String[]{"HOLD_TIME"};
            case 15:
                return new String[]{"HOLD_COM_ADDR"};
            case 16:
                return new String[]{"HOLD_LANGUAGE"};
            case 20:
                return new String[]{"HOLD_PV_INPUT_MODE"};
            case 22:
                return new String[]{"HOLD_START_PV_VOLT"};
            case 23:
                return new String[]{"HOLD_CONNECT_TIME"};
            case 24:
                return new String[]{"HOLD_RECONNECT_TIME"};
            case 25:
                return new String[]{"HOLD_GRID_VOLT_CONN_LOW"};
            case 26:
                return new String[]{"HOLD_GRID_VOLT_CONN_HIGH"};
            case 27:
                return new String[]{"HOLD_GRID_FREQ_CONN_LOW"};
            case 28:
                return new String[]{"HOLD_GRID_FREQ_CONN_HIGH"};
            case 29:
                return new String[]{"HOLD_GRID_VOLT_LIMIT1_LOW"};
            case 30:
                return new String[]{"HOLD_GRID_VOLT_LIMIT1_HIGH"};
            case 31:
                return new String[]{"HOLD_GRID_VOLT_LIMIT1_LOW_TIME"};
            case 32:
                return new String[]{"HOLD_GRID_VOLT_LIMIT1_HIGH_TIME"};
            case 33:
                return new String[]{"HOLD_GRID_VOLT_LIMIT2_LOW"};
            case 34:
                return new String[]{"HOLD_GRID_VOLT_LIMIT2_HIGH"};
            case 35:
                return new String[]{"HOLD_GRID_VOLT_LIMIT2_LOW_TIME"};
            case 36:
                return new String[]{"HOLD_GRID_VOLT_LIMIT2_HIGH_TIME"};
            case 37:
                return new String[]{"HOLD_GRID_VOLT_LIMIT3_LOW"};
            case 38:
                return new String[]{"HOLD_GRID_VOLT_LIMIT3_HIGH"};
            case 39:
                return new String[]{"HOLD_GRID_VOLT_LIMIT3_LOW_TIME"};
            case 40:
                return new String[]{"HOLD_GRID_VOLT_LIMIT3_HIGH_TIME"};
            case 41:
                return new String[]{"HOLD_GRID_VOLT_MOV_AVG_HIGH"};
            case 42:
                return new String[]{"HOLD_GRID_FREQ_LIMIT1_LOW"};
            case 43:
                return new String[]{"HOLD_GRID_FREQ_LIMIT1_HIGH"};
            case 44:
                return new String[]{"HOLD_GRID_FREQ_LIMIT1_LOW_TIME"};
            case 45:
                return new String[]{"HOLD_GRID_FREQ_LIMIT1_HIGH_TIME"};
            case 46:
                return new String[]{"HOLD_GRID_FREQ_LIMIT2_LOW"};
            case 47:
                return new String[]{"HOLD_GRID_FREQ_LIMIT2_HIGH"};
            case 48:
                return new String[]{"HOLD_GRID_FREQ_LIMIT2_LOW_TIME"};
            case 49:
                return new String[]{"HOLD_GRID_FREQ_LIMIT2_HIGH_TIME"};
            case 50:
                return new String[]{"HOLD_GRID_FREQ_LIMIT3_LOW"};
            case 51:
                return new String[]{"HOLD_GRID_FREQ_LIMIT3_HIGH"};
            case 52:
                return new String[]{"HOLD_GRID_FREQ_LIMIT3_LOW_TIME"};
            case 53:
                return new String[]{"HOLD_GRID_FREQ_LIMIT3_HIGH_TIME"};
            case 54:
                return new String[]{"HOLD_MAX_Q_PERCENT_FOR_QV"};
            case 55:
                return new String[]{"HOLD_V1L"};
            case 56:
                return new String[]{"HOLD_V2L"};
            case 57:
                return new String[]{"HOLD_V1H"};
            case 58:
                return new String[]{"HOLD_V2H"};
            case 59:
                return new String[]{"HOLD_REACTIVE_POWER_CMD_TYPE"};
            case 60:
                return new String[]{"HOLD_ACTIVE_POWER_PERCENT_CMD"};
            case 61:
                return new String[]{"HOLD_REACTIVE_POWER_PERCENT_CMD"};
            case 62:
                return new String[]{"HOLD_PF_CMD"};
            case 63:
                return new String[]{"HOLD_POWER_SOFT_START_SLOPE"};
            case 64:
                return new String[]{"HOLD_CHARGE_POWER_PERCENT_CMD"};
            case 65:
                return new String[]{"HOLD_DISCHG_POWER_PERCENT_CMD"};
            case 66:
                return new String[]{"HOLD_AC_CHARGE_POWER_CMD"};
            case 67:
                return new String[]{"HOLD_AC_CHARGE_SOC_LIMIT"};
            case 68:
                return new String[]{"HOLD_AC_CHARGE_START_HOUR", "HOLD_AC_CHARGE_START_MINUTE"};
            case 69:
                return new String[]{"HOLD_AC_CHARGE_END_HOUR", "HOLD_AC_CHARGE_END_MINUTE"};
            case 70:
                return new String[]{"HOLD_AC_CHARGE_START_HOUR_1", "HOLD_AC_CHARGE_START_MINUTE_1"};
            case 71:
                return new String[]{"HOLD_AC_CHARGE_END_HOUR_1", "HOLD_AC_CHARGE_END_MINUTE_1"};
            case 72:
                return new String[]{"HOLD_AC_CHARGE_START_HOUR_2", "HOLD_AC_CHARGE_START_MINUTE_2"};
            case 73:
                return new String[]{"HOLD_AC_CHARGE_END_HOUR_2", "HOLD_AC_CHARGE_END_MINUTE_2"};
            case 74:
                return new String[]{"HOLD_FORCED_CHG_POWER_CMD"};
            case 75:
                return new String[]{"HOLD_FORCED_CHG_SOC_LIMIT"};
            case 76:
                return new String[]{"HOLD_FORCED_CHARGE_START_HOUR", "HOLD_FORCED_CHARGE_START_MINUTE"};
            case 77:
                return new String[]{"HOLD_FORCED_CHARGE_END_HOUR", "HOLD_FORCED_CHARGE_END_MINUTE"};
            case 78:
                return new String[]{"HOLD_FORCED_CHARGE_START_HOUR_1", "HOLD_FORCED_CHARGE_START_MINUTE_1"};
            case 79:
                return new String[]{"HOLD_FORCED_CHARGE_END_HOUR_1", "HOLD_FORCED_CHARGE_END_MINUTE_1"};
            case 80:
                return new String[]{"HOLD_FORCED_CHARGE_START_HOUR_2", "HOLD_FORCED_CHARGE_START_MINUTE_2"};
            case 81:
                return new String[]{"HOLD_FORCED_CHARGE_END_HOUR_2", "HOLD_FORCED_CHARGE_END_MINUTE_2"};
            case 82:
                return new String[]{"HOLD_FORCED_DISCHG_POWER_CMD"};
            case 83:
                return new String[]{"HOLD_FORCED_DISCHG_SOC_LIMIT"};
            case 84:
                return new String[]{"HOLD_FORCED_DISCHARGE_START_HOUR", "HOLD_FORCED_DISCHARGE_START_MINUTE"};
            case 85:
                return new String[]{"HOLD_FORCED_DISCHARGE_END_HOUR", "HOLD_FORCED_DISCHARGE_END_MINUTE"};
            case 86:
                return new String[]{"HOLD_FORCED_DISCHARGE_START_HOUR_1", "HOLD_FORCED_DISCHARGE_START_MINUTE_1"};
            case 87:
                return new String[]{"HOLD_FORCED_DISCHARGE_END_HOUR_1", "HOLD_FORCED_DISCHARGE_END_MINUTE_1"};
            case 88:
                return new String[]{"HOLD_FORCED_DISCHARGE_START_HOUR_2", "HOLD_FORCED_DISCHARGE_START_MINUTE_2"};
            case 89:
                return new String[]{"HOLD_FORCED_DISCHARGE_END_HOUR_2", "HOLD_FORCED_DISCHARGE_END_MINUTE_2"};
            case 90:
                return new String[]{"HOLD_EPS_VOLT_SET"};
            case 91:
                return new String[]{"HOLD_EPS_FREQ_SET"};
            case 96:
                return new String[]{"HOLD_DELAY_TIME_FOR_QV_CURVE"};
            case 97:
                return new String[]{"HOLD_DELAY_TIME_FOR_OVER_F_DERATE"};
            case 99:
                return new String[]{"HOLD_LEAD_ACID_CHARGE_VOLT_REF"};
            case 100:
                return new String[]{"HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT"};
            case 101:
                return new String[]{"HOLD_LEAD_ACID_CHARGE_RATE"};
            case 102:
                return new String[]{"HOLD_LEAD_ACID_DISCHARGE_RATE"};
            case 103:
                return new String[]{"HOLD_FEED_IN_GRID_POWER_PERCENT"};
            case 105:
                return new String[]{"HOLD_DISCHG_CUT_OFF_SOC_EOD"};
            case 106:
                return new String[]{"HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG"};
            case 107:
                return new String[]{"HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG"};
            case 108:
                return new String[]{"HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG"};
            case 109:
                return new String[]{"HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG"};
            case 112:
                return new String[]{"HOLD_SET_MASTER_OR_SLAVE"};
            case 113:
                return new String[]{"HOLD_SET_COMPOSED_PHASE"};
            case 115:
                return new String[]{"_12K_HOLD_OVF_DERATE_START_POINT"};
            case 116:
                return new String[]{"HOLD_P_TO_USER_START_DISCHG"};
            case 118:
                return new String[]{"HOLD_VBAT_START_DERATING"};
            case 119:
                return new String[]{"HOLD_CT_POWER_OFFSET"};
            case 121:
                return new String[]{"HOLD_MIN_Q_PERCENT_FOR_QV"};
            case 122:
                return new String[]{"HOLD_MAINTENANCE_COUNT"};
            case R.styleable.AppCompatTheme_windowMinWidthMinor /* 125 */:
                return new String[]{"HOLD_SOC_LOW_LIMIT_EPS_DISCHG"};
            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA /* 134 */:
                return new String[]{"HOLD_UVF_DERATE_START_POINT"};
            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA /* 136 */:
                return new String[]{"HOLD_OVF_DROOP_KOF"};
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /* 144 */:
                return new String[]{"HOLD_FLOATING_VOLTAGE"};
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA /* 149 */:
                return new String[]{"HOLD_EQUALIZATION_VOLTAGE"};
            case CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA /* 150 */:
                return new String[]{"HOLD_EQUALIZATION_PERIOD"};
            case CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA /* 151 */:
                return new String[]{"HOLD_EQUALIZATION_TIME"};
            case CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 /* 158 */:
                return new String[]{"HOLD_AC_CHARGE_START_BATTERY_VOLTAGE"};
            case CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 /* 159 */:
                return new String[]{"HOLD_AC_CHARGE_END_BATTERY_VOLTAGE"};
            case CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 /* 160 */:
                return new String[]{"HOLD_AC_CHARGE_START_BATTERY_SOC"};
            case CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384 /* 169 */:
                return new String[]{"HOLD_ON_GRID_EOD_VOLTAGE"};
            case CipherSuite.TLS_PSK_WITH_NULL_SHA256 /* 176 */:
                return new String[]{"HOLD_MAX_AC_INPUT_POWER"};
            case CipherSuite.TLS_PSK_WITH_NULL_SHA384 /* 177 */:
                return new String[]{"HOLD_MAX_GENERATOR_INPUT_POWER"};
            case CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384 /* 181 */:
                return new String[]{"HOLD_VOLT_WATT_V1"};
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256 /* 182 */:
                return new String[]{"HOLD_VOLT_WATT_V2"};
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384 /* 183 */:
                return new String[]{"HOLD_VOLT_WATT_DELAY_TIME"};
            case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256 /* 184 */:
                return new String[]{"HOLD_VOLT_WATT_P2"};
            case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384 /* 185 */:
                return new String[]{"HOLD_VREF"};
            case CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 186 */:
                return new String[]{"HOLD_VREF_ADJUSTMENT_TIME_CONSTANT"};
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 187 */:
                return new String[]{"HOLD_Q3"};
            case 188:
                return new String[]{"HOLD_Q4"};
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 189 */:
                return new String[]{"HOLD_P1"};
            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 190 */:
                return new String[]{"HOLD_P2"};
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256 /* 191 */:
                return new String[]{"HOLD_P3"};
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256 /* 193 */:
                return new String[]{"HOLD_UVF_DROOP_KUF"};
            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256 /* 194 */:
                return new String[]{"OFF_GRID_HOLD_GEN_CHG_START_VOLT"};
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256 /* 195 */:
                return new String[]{"OFF_GRID_HOLD_GEN_CHG_END_VOLT"};
            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256 /* 196 */:
                return new String[]{"OFF_GRID_HOLD_GEN_CHG_START_SOC"};
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256 /* 197 */:
                return new String[]{"OFF_GRID_HOLD_GEN_CHG_END_SOC"};
            case CipherSuite.TLS_SM4_GCM_SM3 /* 198 */:
                return new String[]{"OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR"};
            case 201:
                return new String[]{"_12K_HOLD_CHARGE_FIRST_VOLT"};
            case 202:
                return new String[]{"_12K_HOLD_STOP_DISCHG_VOLT"};
            case 203:
                return new String[]{"_12K_HOLD_GRID_REGULATION"};
            case 204:
                return new String[]{"_12K_HOLD_LEAD_CAPACITY"};
            case 205:
                return new String[]{"_12K_HOLD_GRID_TYPE"};
            case 206:
                return new String[]{"_12K_HOLD_GRID_PEAK_SHAVING_POWER"};
            case 207:
                return new String[]{"_12K_HOLD_GRID_PEAK_SHAVING_SOC"};
            case 208:
                return new String[]{"_12K_HOLD_GRID_PEAK_SHAVING_VOLT"};
            case 209:
                return new String[]{"LSP_HOLD_DIS_CHG_POWER_TIME_37", "LSP_HOLD_DIS_CHG_POWER_TIME_38"};
            case 210:
                return new String[]{"LSP_HOLD_DIS_CHG_POWER_TIME_39", "LSP_HOLD_DIS_CHG_POWER_TIME_40"};
            case Primes.SMALL_FACTOR_LIMIT /* 211 */:
                return new String[]{"LSP_HOLD_DIS_CHG_POWER_TIME_41", "LSP_HOLD_DIS_CHG_POWER_TIME_42"};
            case 212:
                return new String[]{"LSP_HOLD_DIS_CHG_POWER_TIME_43", "LSP_HOLD_DIS_CHG_POWER_TIME_44"};
            case 213:
                return new String[]{"_12K_HOLD_SMART_LOAD_START_VOLT"};
            case 214:
                return new String[]{"_12K_HOLD_SMART_LOAD_END_VOLT"};
            case 215:
                return new String[]{"_12K_HOLD_SMART_LOAD_START_SOC"};
            case 216:
                return new String[]{"_12K_HOLD_SMART_LOAD_END_SOC"};
            case 217:
                return new String[]{"_12K_HOLD_START_PV_POWER"};
            case 218:
                return new String[]{"_12K_HOLD_GRID_PEAK_SHAVING_SOC_2"};
            case 219:
                return new String[]{"_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2"};
            case 220:
                return new String[]{"_12K_HOLD_AC_COUPLE_START_SOC"};
            case 221:
                return new String[]{"_12K_HOLD_AC_COUPLE_END_SOC"};
            case 222:
                return new String[]{"_12K_HOLD_AC_COUPLE_START_VOLT"};
            case 223:
                return new String[]{"_12K_HOLD_AC_COUPLE_END_VOLT"};
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public String getValueShowText(String str, int i, String str2) {
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
            case -2076784143:
                if (str.equals("_12K_HOLD_LEAD_CAPACITY")) {
                    c = 4;
                    break;
                }
                break;
            case -2051169280:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG")) {
                    c = 5;
                    break;
                }
                break;
            case -2012582261:
                if (str.equals("HOLD_FEED_IN_GRID_POWER_PERCENT")) {
                    c = 6;
                    break;
                }
                break;
            case -1922746271:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_VOLT_REF")) {
                    c = 7;
                    break;
                }
                break;
            case -1917271147:
                if (str.equals("_12K_HOLD_START_PV_POWER")) {
                    c = '\b';
                    break;
                }
                break;
            case -1907123448:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_END_VOLT")) {
                    c = '\t';
                    break;
                }
                break;
            case -1905186084:
                if (str.equals("_12K_HOLD_SMART_LOAD_END_VOLT")) {
                    c = '\n';
                    break;
                }
                break;
            case -1854323434:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG")) {
                    c = 11;
                    break;
                }
                break;
            case -1774171999:
                if (str.equals("_12K_HOLD_OVF_DERATE_START_POINT")) {
                    c = '\f';
                    break;
                }
                break;
            case -1738912721:
                if (str.equals("HOLD_EQUALIZATION_VOLTAGE")) {
                    c = '\r';
                    break;
                }
                break;
            case -1724289777:
                if (str.equals("HOLD_UVF_DERATE_START_POINT")) {
                    c = 14;
                    break;
                }
                break;
            case -1662706451:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_1")) {
                    c = 15;
                    break;
                }
                break;
            case -1662706450:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_2")) {
                    c = 16;
                    break;
                }
                break;
            case -1612429665:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_1")) {
                    c = 17;
                    break;
                }
                break;
            case -1612429664:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_2")) {
                    c = 18;
                    break;
                }
                break;
            case -1605958379:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG")) {
                    c = 19;
                    break;
                }
                break;
            case -1579641573:
                if (str.equals("HOLD_FW_CODE")) {
                    c = 20;
                    break;
                }
                break;
            case -1567068683:
                if (str.equals("_12K_HOLD_SMART_LOAD_START_VOLT")) {
                    c = 21;
                    break;
                }
                break;
            case -1563900533:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR")) {
                    c = 22;
                    break;
                }
                break;
            case -1551617594:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR")) {
                    c = 23;
                    break;
                }
                break;
            case -1536953061:
                if (str.equals("_12K_HOLD_STOP_DISCHG_VOLT")) {
                    c = 24;
                    break;
                }
                break;
            case -1475031011:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE")) {
                    c = 25;
                    break;
                }
                break;
            case -1448232788:
                if (str.equals("_12K_HOLD_AC_COUPLE_START_VOLT")) {
                    c = 26;
                    break;
                }
                break;
            case -1433471711:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
                    c = 27;
                    break;
                }
                break;
            case -1422719063:
                if (str.equals("HOLD_BATTERY_WARNING_VOLTAGE")) {
                    c = 28;
                    break;
                }
                break;
            case -1413838822:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
                    c = 29;
                    break;
                }
                break;
            case -1352201891:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
                    c = 30;
                    break;
                }
                break;
            case -1323572740:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
                    c = 31;
                    break;
                }
                break;
            case -1294943589:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
                    c = ' ';
                    break;
                }
                break;
            case -1282654362:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
                    c = '!';
                    break;
                }
                break;
            case -1254025211:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case -1225396060:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
                    c = '#';
                    break;
                }
                break;
            case -1119226968:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_1")) {
                    c = Typography.dollar;
                    break;
                }
                break;
            case -1119226967:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_2")) {
                    c = '%';
                    break;
                }
                break;
            case -1033230202:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_1")) {
                    c = Typography.amp;
                    break;
                }
                break;
            case -1033230201:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_2")) {
                    c = '\'';
                    break;
                }
                break;
            case -917268805:
                if (str.equals("HOLD_FORCED_DISCHG_POWER_CMD")) {
                    c = '(';
                    break;
                }
                break;
            case -915649916:
                if (str.equals("HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE")) {
                    c = ')';
                    break;
                }
                break;
            case -875057049:
                if (str.equals("HOLD_GRID_FREQ_CONN_HIGH")) {
                    c = '*';
                    break;
                }
                break;
            case -823881906:
                if (str.equals("HOLD_MAX_AC_INPUT_POWER")) {
                    c = '+';
                    break;
                }
                break;
            case -750853128:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_1")) {
                    c = ',';
                    break;
                }
                break;
            case -750853127:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_2")) {
                    c = '-';
                    break;
                }
                break;
            case -613454474:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE")) {
                    c = '.';
                    break;
                }
                break;
            case -595561232:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW")) {
                    c = '/';
                    break;
                }
                break;
            case -594637711:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW")) {
                    c = '0';
                    break;
                }
                break;
            case -593714190:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW")) {
                    c = '1';
                    break;
                }
                break;
            case -573009974:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
                    c = '2';
                    break;
                }
                break;
            case -537681431:
                if (str.equals("HOLD_NOMINAL_BATTERY_VOLTAGE")) {
                    c = '3';
                    break;
                }
                break;
            case -535537138:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2")) {
                    c = '4';
                    break;
                }
                break;
            case -444930136:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_1")) {
                    c = '5';
                    break;
                }
                break;
            case -444930135:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_2")) {
                    c = '6';
                    break;
                }
                break;
            case -386262971:
                if (str.equals("HOLD_OVF_DROOP_KOF")) {
                    c = '7';
                    break;
                }
                break;
            case -384118065:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR")) {
                    c = '8';
                    break;
                }
                break;
            case -307531482:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE")) {
                    c = '9';
                    break;
                }
                break;
            case -276744627:
                if (str.equals("HOLD_TIME")) {
                    c = ':';
                    break;
                }
                break;
            case -165590897:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_1")) {
                    c = ';';
                    break;
                }
                break;
            case -165590896:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_2")) {
                    c = Typography.less;
                    break;
                }
                break;
            case -123360169:
                if (str.equals("INPUT_BATTERY_VOLTAGE")) {
                    c = '=';
                    break;
                }
                break;
            case 4556681:
                if (str.equals("HOLD_MODEL")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case 106114747:
                if (str.equals("HOLD_CT_POWER_OFFSET")) {
                    c = '?';
                    break;
                }
                break;
            case 124739648:
                if (str.equals("HOLD_ON_GRID_EOD_VOLTAGE")) {
                    c = '@';
                    break;
                }
                break;
            case 127930925:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR")) {
                    c = 'A';
                    break;
                }
                break;
            case 180157537:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG")) {
                    c = 'B';
                    break;
                }
                break;
            case 225083931:
                if (str.equals("HOLD_SERIAL_NUM")) {
                    c = 'C';
                    break;
                }
                break;
            case 229729985:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_1")) {
                    c = 'D';
                    break;
                }
                break;
            case 229729986:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_2")) {
                    c = 'E';
                    break;
                }
                break;
            case 265183773:
                if (str.equals("HOLD_START_PV_VOLT")) {
                    c = 'F';
                    break;
                }
                break;
            case 268168589:
                if (str.equals("HOLD_V1H")) {
                    c = 'G';
                    break;
                }
                break;
            case 268168593:
                if (str.equals("HOLD_V1L")) {
                    c = 'H';
                    break;
                }
                break;
            case 268168620:
                if (str.equals("HOLD_V2H")) {
                    c = 'I';
                    break;
                }
                break;
            case 268168624:
                if (str.equals("HOLD_V2L")) {
                    c = 'J';
                    break;
                }
                break;
            case 313840816:
                if (str.equals("HOLD_GRID_VOLT_CONN_HIGH")) {
                    c = 'K';
                    break;
                }
                break;
            case 330144381:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_1")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_LT;
                    break;
                }
                break;
            case 330144382:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_2")) {
                    c = 'M';
                    break;
                }
                break;
            case 367128639:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE")) {
                    c = 'N';
                    break;
                }
                break;
            case 390956452:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR")) {
                    c = 'O';
                    break;
                }
                break;
            case 467543035:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE")) {
                    c = 'P';
                    break;
                }
                break;
            case 485996371:
                if (str.equals("_12K_HOLD_AC_COUPLE_END_VOLT")) {
                    c = 'Q';
                    break;
                }
                break;
            case 528065501:
                if (str.equals("HOLD_FORCED_CHG_POWER_CMD")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                    break;
                }
                break;
            case 614838895:
                if (str.equals("_12K_HOLD_CHARGE_FIRST_VOLT")) {
                    c = 'S';
                    break;
                }
                break;
            case 623306801:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_1")) {
                    c = 'T';
                    break;
                }
                break;
            case 623306802:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_2")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_UT;
                    break;
                }
                break;
            case 719765641:
                if (str.equals("HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE")) {
                    c = 'V';
                    break;
                }
                break;
            case 814992216:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_1")) {
                    c = 'W';
                    break;
                }
                break;
            case 814992217:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_2")) {
                    c = 'X';
                    break;
                }
                break;
            case 866091809:
                if (str.equals("OFF_GRID_HOLD_GEN_CHG_START_VOLT")) {
                    c = 'Y';
                    break;
                }
                break;
            case 1039780741:
                if (str.equals("HOLD_FLOATING_VOLTAGE")) {
                    c = Matrix.MATRIX_TYPE_ZERO;
                    break;
                }
                break;
            case 1118506598:
                if (str.equals("HOLD_GRID_VOLT_CONN_LOW")) {
                    c = '[';
                    break;
                }
                break;
            case 1203310617:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW")) {
                    c = '\\';
                    break;
                }
                break;
            case 1204234138:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW")) {
                    c = ']';
                    break;
                }
                break;
            case 1205157659:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW")) {
                    c = '^';
                    break;
                }
                break;
            case 1224119563:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_POWER")) {
                    c = '_';
                    break;
                }
                break;
            case 1504508315:
                if (str.equals("HOLD_VBAT_START_DERATING")) {
                    c = '`';
                    break;
                }
                break;
            case 1527620681:
                if (str.equals("HOLD_MAX_GENERATOR_INPUT_POWER")) {
                    c = 'a';
                    break;
                }
                break;
            case 1600103487:
                if (str.equals("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
                    c = 'b';
                    break;
                }
                break;
            case 1853690354:
                if (str.equals("HOLD_AC_CHARGE_POWER_CMD")) {
                    c = 'c';
                    break;
                }
                break;
            case 1910924292:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_37")) {
                    c = 'd';
                    break;
                }
                break;
            case 1910924293:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_38")) {
                    c = 'e';
                    break;
                }
                break;
            case 1910924294:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_39")) {
                    c = 'f';
                    break;
                }
                break;
            case 1910924316:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_40")) {
                    c = 'g';
                    break;
                }
                break;
            case 1910924317:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_41")) {
                    c = 'h';
                    break;
                }
                break;
            case 1910924318:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_42")) {
                    c = 'i';
                    break;
                }
                break;
            case 1910924319:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_43")) {
                    c = 'j';
                    break;
                }
                break;
            case 1910924320:
                if (str.equals("LSP_HOLD_DIS_CHG_POWER_TIME_44")) {
                    c = 'k';
                    break;
                }
                break;
            case 1979328795:
                if (str.equals("_12K_HOLD_GRID_PEAK_SHAVING_VOLT")) {
                    c = 'l';
                    break;
                }
                break;
            case 2046998614:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_1")) {
                    c = 'm';
                    break;
                }
                break;
            case 2046998615:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_2")) {
                    c = 'n';
                    break;
                }
                break;
            case 2069975635:
                if (str.equals("HOLD_POWER_SOFT_START_SLOPE")) {
                    c = 'o';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case '7':
                return InvTool.formatData(1.0d / getInt2ParamValue(str, i, str2), 2);
            case 1:
            case 15:
            case 16:
            case 25:
            case '$':
            case '%':
            case '&':
            case '\'':
            case '.':
            case '9':
            case ';':
            case '<':
            case 'N':
            case 'P':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'e':
            case 'g':
            case 'i':
            case 'k':
                return ProTool.fillZeros(String.valueOf(getInt2HighParamValue(str, i, str2)), 2);
            case 2:
            case '\f':
            case 14:
            case 30:
            case 31:
            case ' ':
            case '*':
            case '\\':
            case ']':
            case '^':
                return InvTool.formatData(getInt2ParamValue(str, i, str2) / 100.0d);
            case 3:
            case 17:
            case 18:
            case 22:
            case 23:
            case ',':
            case '-':
            case '5':
            case '6':
            case '8':
            case 'A':
            case 'D':
            case 'E':
            case 'L':
            case 'M':
            case 'O':
            case 'd':
            case 'f':
            case 'h':
            case 'j':
            case 'm':
            case 'n':
                return ProTool.fillZeros(String.valueOf(getInt2LowParamValue(str, i, str2)), 2);
            case 4:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case '\r':
            case 21:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            case '!':
            case '\"':
            case '#':
            case '(':
            case ')':
            case '+':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '=':
            case '?':
            case '@':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'Q':
            case 'R':
            case 'S':
            case 'V':
            case 'Y':
            case 'Z':
            case '[':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
            case 'l':
            case 'o':
                return InvTool.formatData(getInt2ParamValue(str, i, str2) / 10.0d);
            case 5:
            case 11:
            case 19:
            case 'B':
                return InvTool.formatData(getInt2ParamValueN(str, i, str2) / 10.0d);
            case 20:
                int valueStartIndex = getValueStartIndex(str, i);
                return str2.substring(valueStartIndex, 4) + "-" + ProTool.showHex(str2.charAt(valueStartIndex + 5)) + ProTool.showHex(str2.charAt(valueStartIndex + 6));
            case ':':
                int valueStartIndex2 = getValueStartIndex(str, i);
                char cCharAt = str2.charAt(valueStartIndex2);
                char cCharAt2 = str2.charAt(valueStartIndex2 + 1);
                char cCharAt3 = str2.charAt(valueStartIndex2 + 2);
                char cCharAt4 = str2.charAt(valueStartIndex2 + 3);
                char cCharAt5 = str2.charAt(valueStartIndex2 + 4);
                char cCharAt6 = str2.charAt(valueStartIndex2 + 5);
                Calendar calendar = Calendar.getInstance();
                calendar.set(cCharAt + 2000, cCharAt2 - 1, cCharAt3, cCharAt4, cCharAt5, cCharAt6);
                return InvTool.formatDateTime(calendar.getTime());
            case '>':
                return "0x" + Long.toHexString(getLong4ParamValue(str, i, str2)).toUpperCase();
            case 'C':
                return str2.substring(getValueStartIndex(str, i), 10);
            default:
                return String.valueOf(getInt2ParamValue(str, i, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getLong4ParamValue(String str, int i, String str2) {
        int valueStartIndex = getValueStartIndex(str, i);
        return ProTool.count(str2.charAt(valueStartIndex + 3), str2.charAt(valueStartIndex + 2), str2.charAt(valueStartIndex + 1), str2.charAt(valueStartIndex));
    }

    private int getInt2ParamValue(String str, int i, String str2) {
        int valueStartIndex = getValueStartIndex(str, i);
        return ProTool.count(str2.charAt(valueStartIndex + 1), str2.charAt(valueStartIndex));
    }

    private int getInt2ParamValueN(String str, int i, String str2) {
        int valueStartIndex = getValueStartIndex(str, i);
        return ProTool.countN(str2.charAt(valueStartIndex + 1), str2.charAt(valueStartIndex));
    }

    private int getInt2LowParamValue(String str, int i, String str2) {
        return str2.charAt(getValueStartIndex(str, i));
    }

    private int getInt2HighParamValue(String str, int i, String str2) {
        return str2.charAt(getValueStartIndex(str, i) + 1);
    }

    private static int getValueStartIndex(String str, int i) {
        return ((getStartRegisterByParam(str).intValue() - i) * 2) + 35;
    }

    public EditText getTimeDateEditText() {
        return this.timeDateEditText;
    }

    public EditText getTimeTimeEditText() {
        return this.timeTimeEditText;
    }
}