package com.lux.luxcloud.view.main.fragment.lv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE;
import com.lux.luxcloud.global.bean.set.RemoteReadInfo;
import com.lux.luxcloud.global.bean.set.RemoteWriteInfo;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.global.cache.RemoteSetCacheManager;
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
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Lv112KRemoteSetFragment extends AbstractItemFragment {
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
    private ConstraintLayout advancedSetTitleLayout;
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
    private ToggleButton batterySharedFunctionButton;
    private ConstraintLayout batterySharedFunctionLayout;
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
    private Button ctSampleRatioButton;
    private ConstraintLayout ctSampleRatioLayout;
    private Spinner ctSampleRatioSpinner;
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
    private ToggleButton fastZeroExportFunctionButton;
    private ConstraintLayout fastZeroExportFunctionLayout;
    private ToggleButton feedInGridFunctionButton;
    private ConstraintLayout feedInGridFunctionLayout;
    private Button feedInGridPowerPercentButton;
    private EditText feedInGridPowerPercentEditText;
    private ConstraintLayout feedInGridPowerPercentLayout;
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
    private Fragment fragment;
    private Button genRatedPowerButton;
    private EditText genRatedPowerEditText;
    private ConstraintLayout generatorSetActionLayout;
    private TextView generatorSetActionTextView;
    private ToggleButton generatorSetActionToggleButton;
    private LinearLayout generatorSetParamLayout;
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
    private ConstraintLayout gridPeakShavingLayout;
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
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private Button leadAcidChargeVoltRefButton;
    private EditText leadAcidChargeVoltRefEditText;
    private Button leadAcidDischargeCutOffVoltButton;
    private EditText leadAcidDischargeCutOffVoltEditText;
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
    private ToggleButton pvArcFunctionButton;
    private Button readAllButton;
    private Spinner readComposedPhaseSpinner;
    private Button reconnectTimeButton;
    private EditText reconnectTimeEditText;
    private ToggleButton runWithoutGridFunctionButton;
    private ConstraintLayout runWithoutGridFunctionLayout;
    private Spinner setComposedPhaseSpinner;
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

    public Lv112KRemoteSetFragment() {
        super(43L);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lv1_12k_remote_set, viewGroup, false);
        this.fragment = this;
        final UserData userData = GlobalInfo.getInstance().getUserData();
        if (!userData.needShowCompanyLogo()) {
            viewInflate.findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                MainActivity.instance.finish();
            }
        });
        ((ImageView) viewInflate.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
            }
        });
        this.inverterSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_inverter_spinner);
        if (userData.getCurrentPlant() != null) {
            this.inverterList = userData.getInvertersByPlant(userData.getCurrentPlant().getPlantId());
        }
        if (this.inverterList == null) {
            this.inverterList = new ArrayList();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, this.inverterList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.inverterSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Button button = (Button) viewInflate.findViewById(R.id.fragment_remote_set_readAllButton);
        this.readAllButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m619x6f315cd4(view);
            }
        });
        this.timeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_timeLayout);
        EditText editText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeDateEditText);
        this.timeDateEditText = editText;
        editText.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Lv112KRemoteSetFragment.this.timeDateEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 10) {
                    Lv112KRemoteSetFragment.this.timeDateEditText.setText(InvTool.formatDate(new Date()));
                }
                Lv112KRemoteSetFragment.this.getActivity().showDialog(6);
            }
        });
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeTimeEditText);
        this.timeTimeEditText = editText2;
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Lv112KRemoteSetFragment.this.timeTimeEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 5) {
                    Lv112KRemoteSetFragment.this.timeTimeEditText.setText(InvTool.formatTime(new Date()).substring(0, 5));
                }
                Lv112KRemoteSetFragment.this.getActivity().showDialog(7);
            }
        });
        Button button2 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_timeButton);
        this.setTimeButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                Lv112KRemoteSetFragment.this.runNormalRemoteWrite("HOLD_TIME", Lv112KRemoteSetFragment.this.timeDateEditText.getText().toString().trim() + " " + Lv112KRemoteSetFragment.this.timeTimeEditText.getText().toString().trim() + ":" + String.valueOf(calendar.get(13)), Lv112KRemoteSetFragment.this.setTimeButton);
            }
        });
        this.ctSampleRatioLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioLayout);
        this.ctSampleRatioSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioSpinner);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Property(String.valueOf(-1), getString(R.string.phrase_bit_param_ct_sample_ratio_empty)));
        arrayList.add(new Property(String.valueOf(0), getString(R.string.phrase_bit_param_ct_sample_ratio_0)));
        arrayList.add(new Property(String.valueOf(1), getString(R.string.phrase_bit_param_ct_sample_ratio_1)));
        arrayList.add(new Property(String.valueOf(2), "1/2000"));
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ctSampleRatioSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        Button button3 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioButton);
        this.ctSampleRatioButton = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.ctSampleRatioSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runBitRemoteWrite("BIT_CT_SAMPLE_RATIO", property.getName(), Lv112KRemoteSetFragment.this.ctSampleRatioButton);
            }
        });
        ToggleButton toggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_setToStandbyFunctionButton);
        this.setToStandbyFunctionButton = toggleButton;
        toggleButton.setOnClickListener(new AnonymousClass7());
        ToggleButton toggleButton2 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_epsFunctionButton);
        this.epsFunctionButton = toggleButton2;
        toggleButton2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_EPS_EN", lv112KRemoteSetFragment.epsFunctionButton);
            }
        });
        this.epsFrequencySetSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_epsFrequencySetSpinner);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_eps_frequency_set_empty)));
        arrayList2.add(new Property(String.valueOf(50), getString(R.string.phrase_param_eps_frequency_set_50)));
        arrayList2.add(new Property(String.valueOf(60), getString(R.string.phrase_param_eps_frequency_set_60)));
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.epsFrequencySetSpinner.setAdapter((SpinnerAdapter) arrayAdapter3);
        Button button4 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_epsFrequencySetButton);
        this.epsFrequencySetButton = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.epsFrequencySetSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runNormalRemoteWrite("HOLD_EPS_FREQ_SET", property.getName(), Lv112KRemoteSetFragment.this.epsFrequencySetButton);
            }
        });
        this.fastZeroExportFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_fastZeroExportFunctionLayout);
        ToggleButton toggleButton3 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_fastZeroExportFunctionButton);
        this.fastZeroExportFunctionButton = toggleButton3;
        toggleButton3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_RUN_WITHOUT_GRID", lv112KRemoteSetFragment.fastZeroExportFunctionButton);
            }
        });
        this.runWithoutGridFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_runWithoutGridFunctionLayout);
        ToggleButton toggleButton4 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_runWithoutGridFunctionButton);
        this.runWithoutGridFunctionButton = toggleButton4;
        toggleButton4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_RUN_WITHOUT_GRID_12K", lv112KRemoteSetFragment.runWithoutGridFunctionButton);
            }
        });
        this.feedInGridFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridFunctionLayout);
        ToggleButton toggleButton5 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridFunctionButton);
        this.feedInGridFunctionButton = toggleButton5;
        toggleButton5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_FEED_IN_GRID_EN", lv112KRemoteSetFragment.feedInGridFunctionButton);
            }
        });
        this.feedInGridPowerPercentLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentLayout);
        this.feedInGridPowerPercentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentEditText);
        Button button5 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentButton);
        this.feedInGridPowerPercentButton = button5;
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_FEED_IN_GRID_POWER_PERCENT", lv112KRemoteSetFragment.feedInGridPowerPercentEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.feedInGridPowerPercentButton);
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
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.masterOrSlaveSpinner.setAdapter((SpinnerAdapter) arrayAdapter4);
        Button button6 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveButton);
        this.masterOrSlaveButton = button6;
        button6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.masterOrSlaveSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runNormalRemoteWrite("HOLD_SET_MASTER_OR_SLAVE", property.getName(), Lv112KRemoteSetFragment.this.masterOrSlaveButton);
            }
        });
        this.composedPhaseLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseLayout);
        this.readComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_readComposedPhaseSpinner);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(new Property(String.valueOf(-1), "--"));
        arrayList4.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList4.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList4.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter5 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList4);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.readComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter5);
        this.readComposedPhaseSpinner.setEnabled(false);
        this.setComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_setComposedPhaseSpinner);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_composed_phase_empty)));
        arrayList5.add(new Property(String.valueOf(0), getString(R.string.phrase_param_composed_phase_0)));
        arrayList5.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList5.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList5.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter6 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList5);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter6);
        Button button7 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseButton);
        this.composedPhaseButton = button7;
        button7.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.setComposedPhaseSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runNormalRemoteWrite("HOLD_SET_COMPOSED_PHASE", property.getName(), Lv112KRemoteSetFragment.this.composedPhaseButton);
            }
        });
        this.batterySharedFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionLayout);
        ToggleButton toggleButton6 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionButton);
        this.batterySharedFunctionButton = toggleButton6;
        toggleButton6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_BAT_SHARED", lv112KRemoteSetFragment.batterySharedFunctionButton);
            }
        });
        this.maxAcInputPowerLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerLayout);
        this.maxAcInputPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerEditText);
        Button button8 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerButton);
        this.maxAcInputPowerButton = button8;
        button8.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_MAX_AC_INPUT_POWER", lv112KRemoteSetFragment.maxAcInputPowerEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.maxAcInputPowerButton);
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
        ArrayAdapter arrayAdapter7 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList6);
        arrayAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.gridRegulationSpinner.setAdapter((SpinnerAdapter) arrayAdapter7);
        Button button9 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridRegulationButton);
        this.gridRegulationButton = button9;
        button9.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.gridRegulationSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runNormalRemoteWrite("_12K_HOLD_GRID_REGULATION", property.getName(), Lv112KRemoteSetFragment.this.gridRegulationButton);
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
        ArrayAdapter arrayAdapter8 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList7);
        arrayAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.gridTypeSpinner.setAdapter((SpinnerAdapter) arrayAdapter8);
        Button button10 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridTypeButton);
        this.gridTypeButton = button10;
        button10.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.gridTypeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runNormalRemoteWrite("_12K_HOLD_GRID_TYPE", property.getName(), Lv112KRemoteSetFragment.this.gridTypeButton);
            }
        });
        ToggleButton toggleButton7 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_drmsFunctionButton);
        this.drmsFunctionButton = toggleButton7;
        toggleButton7.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_DRMS_EN", lv112KRemoteSetFragment.drmsFunctionButton);
            }
        });
        this.reconnectTimeEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_reconnectTimeEditText);
        Button button11 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_reconnectTimeButton);
        this.reconnectTimeButton = button11;
        button11.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_RECONNECT_TIME", lv112KRemoteSetFragment.reconnectTimeEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.reconnectTimeButton);
            }
        });
        this.gridVoltConnHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnHighEditText);
        Button button12 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnHighButton);
        this.gridVoltConnHighButton = button12;
        button12.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_CONN_HIGH", lv112KRemoteSetFragment.gridVoltConnHighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltConnHighButton);
            }
        });
        this.gridVoltConnLowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnLowEditText);
        Button button13 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnLowButton);
        this.gridVoltConnLowButton = button13;
        button13.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_CONN_LOW", lv112KRemoteSetFragment.gridVoltConnLowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltConnLowButton);
            }
        });
        this.gridFreqConnHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnHighEditText);
        Button button14 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnHighButton);
        this.gridFreqConnHighButton = button14;
        button14.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_CONN_HIGH", lv112KRemoteSetFragment.gridFreqConnHighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqConnHighButton);
            }
        });
        this.gridFreqConnLowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnLowEditText);
        Button button15 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnLowButton);
        this.gridFreqConnLowButton = button15;
        button15.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_CONN_LOW", lv112KRemoteSetFragment.gridFreqConnLowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqConnLowButton);
            }
        });
        this.gridVoltLimit1LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1LowEditText);
        Button button16 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1LowButton);
        this.gridVoltLimit1LowButton = button16;
        button16.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT1_LOW", lv112KRemoteSetFragment.gridVoltLimit1LowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltLimit1LowButton);
            }
        });
        this.gridVoltLimit1HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1HighEditText);
        Button button17 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1HighButton);
        this.gridVoltLimit1HighButton = button17;
        button17.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT1_HIGH", lv112KRemoteSetFragment.gridVoltLimit1HighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltLimit1HighButton);
            }
        });
        this.gridVoltLimit2LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2LowEditText);
        Button button18 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2LowButton);
        this.gridVoltLimit2LowButton = button18;
        button18.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT2_LOW", lv112KRemoteSetFragment.gridVoltLimit2LowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltLimit2LowButton);
            }
        });
        this.gridVoltLimit2HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2HighEditText);
        Button button19 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2HighButton);
        this.gridVoltLimit2HighButton = button19;
        button19.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT2_HIGH", lv112KRemoteSetFragment.gridVoltLimit2HighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltLimit2HighButton);
            }
        });
        this.gridVoltLimit3LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3LowEditText);
        Button button20 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3LowButton);
        this.gridVoltLimit3LowButton = button20;
        button20.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT3_LOW", lv112KRemoteSetFragment.gridVoltLimit3LowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltLimit3LowButton);
            }
        });
        this.gridVoltLimit3HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3HighEditText);
        Button button21 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3HighButton);
        this.gridVoltLimit3HighButton = button21;
        button21.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT3_HIGH", lv112KRemoteSetFragment.gridVoltLimit3HighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridVoltLimit3HighButton);
            }
        });
        this.gridFreqLimit1LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1LowEditText);
        Button button22 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1LowButton);
        this.gridFreqLimit1LowButton = button22;
        button22.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT1_LOW", lv112KRemoteSetFragment.gridFreqLimit1LowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqLimit1LowButton);
            }
        });
        this.gridFreqLimit1HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1HighEditText);
        Button button23 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1HighButton);
        this.gridFreqLimit1HighButton = button23;
        button23.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.33
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT1_HIGH", lv112KRemoteSetFragment.gridFreqLimit1HighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqLimit1HighButton);
            }
        });
        this.gridFreqLimit2LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2LowEditText);
        Button button24 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2LowButton);
        this.gridFreqLimit2LowButton = button24;
        button24.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT2_LOW", lv112KRemoteSetFragment.gridFreqLimit2LowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqLimit2LowButton);
            }
        });
        this.gridFreqLimit2HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2HighEditText);
        Button button25 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2HighButton);
        this.gridFreqLimit2HighButton = button25;
        button25.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.35
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT2_HIGH", lv112KRemoteSetFragment.gridFreqLimit2HighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqLimit2HighButton);
            }
        });
        this.gridFreqLimit3LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3LowEditText);
        Button button26 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3LowButton);
        this.gridFreqLimit3LowButton = button26;
        button26.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.36
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT3_LOW", lv112KRemoteSetFragment.gridFreqLimit3LowEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqLimit3LowButton);
            }
        });
        this.gridFreqLimit3HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3HighEditText);
        Button button27 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3HighButton);
        this.gridFreqLimit3HighButton = button27;
        button27.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.37
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT3_HIGH", lv112KRemoteSetFragment.gridFreqLimit3HighEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridFreqLimit3HighButton);
            }
        });
        ToggleButton toggleButton8 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_acChargeFunctionButton);
        this.acChargeFunctionButton = toggleButton8;
        toggleButton8.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_AC_CHARGE", lv112KRemoteSetFragment.acChargeFunctionButton);
            }
        });
        this.acChargePowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargePowerCmdEditText);
        Button button28 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargePowerCmdButton);
        this.acChargePowerButton = button28;
        button28.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.39
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_POWER_CMD", lv112KRemoteSetFragment.acChargePowerEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acChargePowerButton);
            }
        });
        this.startAcChargeSOCEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_SOCEditText);
        Button button29 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_SOCButton);
        this.startAcChargeSOCButton = button29;
        button29.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.40
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_START_BATTERY_SOC", lv112KRemoteSetFragment.startAcChargeSOCEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.startAcChargeSOCButton);
            }
        });
        this.acChargeSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeSocLimitEditText);
        Button button30 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeSocLimitButton);
        this.acChargeSocLimitButton = button30;
        button30.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.41
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_SOC_LIMIT", lv112KRemoteSetFragment.acChargeSocLimitEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acChargeSocLimitButton);
            }
        });
        this.startAcChargeVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_VoltEditText);
        Button button31 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_Start_AC_Charge_VoltButton);
        this.startAcChargeVoltButton = button31;
        button31.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.42
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE", lv112KRemoteSetFragment.startAcChargeVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.startAcChargeVoltButton);
            }
        });
        this.acChargeEndBatteryVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatteryVoltageEditText);
        Button button32 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatteryVoltageButton);
        this.acChargeEndBatteryVoltageButton = button32;
        button32.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.43
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE", lv112KRemoteSetFragment.acChargeEndBatteryVoltageEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acChargeEndBatteryVoltageButton);
            }
        });
        this.acChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHourEditText);
        this.acChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinuteEditText);
        Button button33 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTimeButton);
        this.acChargeStartTimeButton = button33;
        button33.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.44
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME", lv112KRemoteSetFragment.acChargeStartHourEditText, Lv112KRemoteSetFragment.this.acChargeStartMinuteEditText, Lv112KRemoteSetFragment.this.acChargeStartTimeButton);
            }
        });
        this.acChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHourEditText);
        this.acChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinuteEditText);
        Button button34 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTimeButton);
        this.acChargeEndTimeButton = button34;
        button34.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.45
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME", lv112KRemoteSetFragment.acChargeEndHourEditText, Lv112KRemoteSetFragment.this.acChargeEndMinuteEditText, Lv112KRemoteSetFragment.this.acChargeEndTimeButton);
            }
        });
        this.acChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour1EditText);
        this.acChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute1EditText);
        Button button35 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime1Button);
        this.acChargeStartTime1Button = button35;
        button35.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.46
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_1", lv112KRemoteSetFragment.acChargeStartHour1EditText, Lv112KRemoteSetFragment.this.acChargeStartMinute1EditText, Lv112KRemoteSetFragment.this.acChargeStartTime1Button);
            }
        });
        this.acChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour1EditText);
        this.acChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute1EditText);
        Button button36 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime1Button);
        this.acChargeEndTime1Button = button36;
        button36.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.47
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_1", lv112KRemoteSetFragment.acChargeEndHour1EditText, Lv112KRemoteSetFragment.this.acChargeEndMinute1EditText, Lv112KRemoteSetFragment.this.acChargeEndTime1Button);
            }
        });
        this.acChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour2EditText);
        this.acChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute2EditText);
        Button button37 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime2Button);
        this.acChargeStartTime2Button = button37;
        button37.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.48
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_2", lv112KRemoteSetFragment.acChargeStartHour2EditText, Lv112KRemoteSetFragment.this.acChargeStartMinute2EditText, Lv112KRemoteSetFragment.this.acChargeStartTime2Button);
            }
        });
        this.acChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour2EditText);
        this.acChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute2EditText);
        Button button38 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime2Button);
        this.acChargeEndTime2Button = button38;
        button38.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.49
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_2", lv112KRemoteSetFragment.acChargeEndHour2EditText, Lv112KRemoteSetFragment.this.acChargeEndMinute2EditText, Lv112KRemoteSetFragment.this.acChargeEndTime2Button);
            }
        });
        ToggleButton toggleButton9 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgFunctionButton);
        this.forcedChgFunctionButton = toggleButton9;
        toggleButton9.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.50
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_FORCED_CHG_EN", lv112KRemoteSetFragment.forcedChgFunctionButton);
            }
        });
        this.forcedChgPowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdEditText);
        Button button39 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdButton);
        this.forcedChgPowerCmdButton = button39;
        button39.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.51
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_CHG_POWER_CMD", lv112KRemoteSetFragment.forcedChgPowerCmdEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.forcedChgPowerCmdButton);
            }
        });
        this.forcedChgSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitEditText);
        Button button40 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitButton);
        this.forcedChgSocLimitButton = button40;
        button40.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.52
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_CHG_SOC_LIMIT", lv112KRemoteSetFragment.forcedChgSocLimitEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.forcedChgSocLimitButton);
            }
        });
        this.chargeFirstVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeFirstVoltEditText);
        Button button41 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeFirstVoltButton);
        this.chargeFirstVoltButton = button41;
        button41.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.53
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_CHARGE_FIRST_VOLT", lv112KRemoteSetFragment.chargeFirstVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.chargeFirstVoltButton);
            }
        });
        this.forcedChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHourEditText);
        this.forcedChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinuteEditText);
        Button button42 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTimeButton);
        this.forcedChargeStartTimeButton = button42;
        button42.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.54
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME", lv112KRemoteSetFragment.forcedChargeStartHourEditText, Lv112KRemoteSetFragment.this.forcedChargeStartMinuteEditText, Lv112KRemoteSetFragment.this.forcedChargeStartTimeButton);
            }
        });
        this.forcedChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHourEditText);
        this.forcedChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinuteEditText);
        Button button43 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTimeButton);
        this.forcedChargeEndTimeButton = button43;
        button43.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.55
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME", lv112KRemoteSetFragment.forcedChargeEndHourEditText, Lv112KRemoteSetFragment.this.forcedChargeEndMinuteEditText, Lv112KRemoteSetFragment.this.forcedChargeEndTimeButton);
            }
        });
        this.forcedChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHour1EditText);
        this.forcedChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinute1EditText);
        Button button44 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime1Button);
        this.forcedChargeStartTime1Button = button44;
        button44.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.56
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME_1", lv112KRemoteSetFragment.forcedChargeStartHour1EditText, Lv112KRemoteSetFragment.this.forcedChargeStartMinute1EditText, Lv112KRemoteSetFragment.this.forcedChargeStartTime1Button);
            }
        });
        this.forcedChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHour1EditText);
        this.forcedChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinute1EditText);
        Button button45 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime1Button);
        this.forcedChargeEndTime1Button = button45;
        button45.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.57
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME_1", lv112KRemoteSetFragment.forcedChargeEndHour1EditText, Lv112KRemoteSetFragment.this.forcedChargeEndMinute1EditText, Lv112KRemoteSetFragment.this.forcedChargeEndTime1Button);
            }
        });
        this.forcedChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHour2EditText);
        this.forcedChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinute2EditText);
        Button button46 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime2Button);
        this.forcedChargeStartTime2Button = button46;
        button46.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.58
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME_2", lv112KRemoteSetFragment.forcedChargeStartHour2EditText, Lv112KRemoteSetFragment.this.forcedChargeStartMinute2EditText, Lv112KRemoteSetFragment.this.forcedChargeStartTime2Button);
            }
        });
        this.forcedChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHour2EditText);
        this.forcedChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinute2EditText);
        Button button47 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime2Button);
        this.forcedChargeEndTime2Button = button47;
        button47.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.59
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME_2", lv112KRemoteSetFragment.forcedChargeEndHour2EditText, Lv112KRemoteSetFragment.this.forcedChargeEndMinute2EditText, Lv112KRemoteSetFragment.this.forcedChargeEndTime2Button);
            }
        });
        this.leadAcidChargeVoltRefEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefEditText);
        Button button48 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefButton);
        this.leadAcidChargeVoltRefButton = button48;
        button48.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.60
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_VOLT_REF", lv112KRemoteSetFragment.leadAcidChargeVoltRefEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.leadAcidChargeVoltRefButton);
            }
        });
        this.floatingVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageEditText);
        Button button49 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageButton);
        this.floatingVoltageButton = button49;
        button49.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.61
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_FLOATING_VOLTAGE", lv112KRemoteSetFragment.floatingVoltageEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.floatingVoltageButton);
            }
        });
        this.batChargeCurrentLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_batChargeCurrentLimitEditText);
        Button button50 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batChargeCurrentLimitButton);
        this.batChargeCurrentLimitButton = button50;
        button50.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.62
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR", lv112KRemoteSetFragment.batChargeCurrentLimitEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.batChargeCurrentLimitButton);
            }
        });
        this.genRatedPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_genRatedPowerEditText);
        Button button51 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_genRatedPowerButton);
        this.genRatedPowerButton = button51;
        button51.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.63
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_MAX_GENERATOR_INPUT_POWER", lv112KRemoteSetFragment.genRatedPowerEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.genRatedPowerButton);
            }
        });
        this.chargeStartVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartVoltEditText);
        Button button52 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartVoltButton);
        this.chargeStartVoltButton = button52;
        button52.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.64
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_START_VOLT", lv112KRemoteSetFragment.chargeStartVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.chargeStartVoltButton);
            }
        });
        this.chargeEndVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndVoltEditText);
        Button button53 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndVoltButton);
        this.chargeEndVoltButton = button53;
        button53.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.65
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_END_VOLT", lv112KRemoteSetFragment.chargeEndVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.chargeEndVoltButton);
            }
        });
        this.chargeStartSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartSocEditText);
        Button button54 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeStartSocButton);
        this.chargeStartSocButton = button54;
        button54.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.66
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_START_SOC", lv112KRemoteSetFragment.chargeStartSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.chargeStartSocButton);
            }
        });
        this.chargeEndSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndSocEditText);
        Button button55 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeEndSocButton);
        this.chargeEndSocButton = button55;
        button55.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.67
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("OFF_GRID_HOLD_GEN_CHG_END_SOC", lv112KRemoteSetFragment.chargeEndSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.chargeEndSocButton);
            }
        });
        this.chargeCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentEditText);
        Button button56 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentButton);
        this.chargeCurrentButton = button56;
        button56.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.68
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_RATE", lv112KRemoteSetFragment.chargeCurrentEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.chargeCurrentButton);
            }
        });
        this.batChargeControlSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_batChargeControlSpinner);
        ArrayAdapter arrayAdapter9 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, GlobalInfo.getBatControlList());
        arrayAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.batChargeControlSpinner.setAdapter((SpinnerAdapter) arrayAdapter9);
        Button button57 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batChargeControlButton);
        this.batChargeControlButton = button57;
        button57.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.69
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.batChargeControlSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runControlRemoteWrite("FUNC_BAT_CHARGE_CONTROL", Boolean.parseBoolean(property.getName()), Lv112KRemoteSetFragment.this.batChargeControlButton);
            }
        });
        this.batDischargeControlSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_batDischargeControlSpinner);
        ArrayAdapter arrayAdapter10 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, GlobalInfo.getBatControlList());
        arrayAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.batDischargeControlSpinner.setAdapter((SpinnerAdapter) arrayAdapter10);
        Button button58 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batDischargeControlButton);
        this.batDischargeControlButton = button58;
        button58.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.70
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv112KRemoteSetFragment.this.batDischargeControlSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv112KRemoteSetFragment.this.runControlRemoteWrite("FUNC_BAT_DISCHARGE_CONTROL", Boolean.parseBoolean(property.getName()), Lv112KRemoteSetFragment.this.batDischargeControlButton);
            }
        });
        ToggleButton toggleButton10 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgFunctionButton);
        this.forcedDisChgFunctionButton = toggleButton10;
        toggleButton10.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.71
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_FORCED_DISCHG_EN", lv112KRemoteSetFragment.forcedDisChgFunctionButton);
            }
        });
        this.forcedDisChgPowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerCmdEditText);
        Button button59 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerCmdButton);
        this.forcedDisChgPowerCmdButton = button59;
        button59.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.72
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_DISCHG_POWER_CMD", lv112KRemoteSetFragment.forcedDisChgPowerCmdEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.forcedDisChgPowerCmdButton);
            }
        });
        this.forcedDisChgSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgSocLimitEditText);
        Button button60 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgSocLimitButton);
        this.forcedDisChgSocLimitButton = button60;
        button60.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.73
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_DISCHG_SOC_LIMIT", lv112KRemoteSetFragment.forcedDisChgSocLimitEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.forcedDisChgSocLimitButton);
            }
        });
        this.stopDischgVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_stopDischgVoltEditText);
        Button button61 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_stopDischgVoltButton);
        this.stopDischgVoltButton = button61;
        button61.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.74
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_STOP_DISCHG_VOLT", lv112KRemoteSetFragment.stopDischgVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.stopDischgVoltButton);
            }
        });
        this.forcedDisChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHourEditText);
        this.forcedDisChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinuteEditText);
        Button button62 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTimeButton);
        this.forcedDisChargeStartTimeButton = button62;
        button62.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.75
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME", lv112KRemoteSetFragment.forcedDisChargeStartHourEditText, Lv112KRemoteSetFragment.this.forcedDisChargeStartMinuteEditText, Lv112KRemoteSetFragment.this.forcedDisChargeStartTimeButton);
            }
        });
        this.forcedDisChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHourEditText);
        this.forcedDisChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinuteEditText);
        Button button63 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTimeButton);
        this.forcedDisChargeEndTimeButton = button63;
        button63.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.76
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME", lv112KRemoteSetFragment.forcedDisChargeEndHourEditText, Lv112KRemoteSetFragment.this.forcedDisChargeEndMinuteEditText, Lv112KRemoteSetFragment.this.forcedDisChargeEndTimeButton);
            }
        });
        this.forcedDisChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHour1EditText);
        this.forcedDisChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinute1EditText);
        Button button64 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTime1Button);
        this.forcedDisChargeStartTime1Button = button64;
        button64.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.77
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME_1", lv112KRemoteSetFragment.forcedDisChargeStartHour1EditText, Lv112KRemoteSetFragment.this.forcedDisChargeStartMinute1EditText, Lv112KRemoteSetFragment.this.forcedDisChargeStartTime1Button);
            }
        });
        this.forcedDisChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHour1EditText);
        this.forcedDisChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinute1EditText);
        Button button65 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTime1Button);
        this.forcedDisChargeEndTime1Button = button65;
        button65.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.78
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME_1", lv112KRemoteSetFragment.forcedDisChargeEndHour1EditText, Lv112KRemoteSetFragment.this.forcedDisChargeEndMinute1EditText, Lv112KRemoteSetFragment.this.forcedDisChargeEndTime1Button);
            }
        });
        this.forcedDisChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHour2EditText);
        this.forcedDisChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinute2EditText);
        Button button66 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTime2Button);
        this.forcedDisChargeStartTime2Button = button66;
        button66.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.79
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME_2", lv112KRemoteSetFragment.forcedDisChargeStartHour2EditText, Lv112KRemoteSetFragment.this.forcedDisChargeStartMinute2EditText, Lv112KRemoteSetFragment.this.forcedDisChargeStartTime2Button);
            }
        });
        this.forcedDisChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHour2EditText);
        this.forcedDisChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinute2EditText);
        Button button67 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTime2Button);
        this.forcedDisChargeEndTime2Button = button67;
        button67.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.80
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME_2", lv112KRemoteSetFragment.forcedDisChargeEndHour2EditText, Lv112KRemoteSetFragment.this.forcedDisChargeEndMinute2EditText, Lv112KRemoteSetFragment.this.forcedDisChargeEndTime2Button);
            }
        });
        this.leadAcidDischargeCutOffVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltEditText);
        Button button68 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltButton);
        this.leadAcidDischargeCutOffVoltButton = button68;
        button68.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.81
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT", lv112KRemoteSetFragment.leadAcidDischargeCutOffVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.leadAcidDischargeCutOffVoltButton);
            }
        });
        this.dischgCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_dischgCurrentEditText);
        Button button69 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_dischgCurrentButton);
        this.dischgCurrentButton = button69;
        button69.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.82
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_RATE", lv112KRemoteSetFragment.dischgCurrentEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.dischgCurrentButton);
            }
        });
        this.onGridDischargeCutoffSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_onGridDischargeCutoffSocEditText);
        Button button70 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_onGridDischargeCutoffSocButton);
        this.onGridDischargeCutoffSocButton = button70;
        button70.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.83
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_DISCHG_CUT_OFF_SOC_EOD", lv112KRemoteSetFragment.onGridDischargeCutoffSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.onGridDischargeCutoffSocButton);
            }
        });
        this.offGridDischargeCutoffSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocEditText);
        Button button71 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocButton);
        this.offGridDischargeCutoffSocButton = button71;
        button71.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.84
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_SOC_LOW_LIMIT_EPS_DISCHG", lv112KRemoteSetFragment.offGridDischargeCutoffSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.offGridDischargeCutoffSocButton);
            }
        });
        this.gridPeakShavingLayout = (ConstraintLayout) viewInflate.findViewById(R.id.grid_peak_shaving);
        this.onGridEodVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodVoltageEditText);
        Button button72 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodVoltageButton);
        this.onGridEodVoltageButton = button72;
        button72.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.85
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("HOLD_ON_GRID_EOD_VOLTAGE", lv112KRemoteSetFragment.onGridEodVoltageEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.onGridEodVoltageButton);
            }
        });
        ToggleButton toggleButton11 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingFunctionButton);
        this.gridPeakShavingFunctionButton = toggleButton11;
        toggleButton11.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.86
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_GRID_PEAK_SHAVING", lv112KRemoteSetFragment.gridPeakShavingFunctionButton);
            }
        });
        this.gridPeakShavingPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingPowerEditText);
        Button button73 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingPowerButton);
        this.gridPeakShavingPowerButton = button73;
        button73.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.87
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_POWER", lv112KRemoteSetFragment.gridPeakShavingPowerEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridPeakShavingPowerButton);
            }
        });
        this.gridPeakShavingVolt1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt1EditText);
        Button button74 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt1Button);
        this.gridPeakShavingVolt1Button = button74;
        button74.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.88
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_VOLT", lv112KRemoteSetFragment.gridPeakShavingVolt1EditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridPeakShavingVolt1Button);
            }
        });
        this.gridPeakShavingSoc1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc1EditText);
        Button button75 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc1Button);
        this.gridPeakShavingSoc1Button = button75;
        button75.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.89
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_SOC", lv112KRemoteSetFragment.gridPeakShavingSoc1EditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridPeakShavingSoc1Button);
            }
        });
        this.gridPeakShavingVolt2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt2EditText);
        Button button76 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingVolt2Button);
        this.gridPeakShavingVolt2Button = button76;
        button76.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.90
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2", lv112KRemoteSetFragment.gridPeakShavingVolt2EditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridPeakShavingVolt2Button);
            }
        });
        this.gridPeakShavingSoc2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc2EditText);
        Button button77 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridPeakShavingSoc2Button);
        this.gridPeakShavingSoc2Button = button77;
        button77.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.91
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2", lv112KRemoteSetFragment.gridPeakShavingSoc2EditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.gridPeakShavingSoc2Button);
            }
        });
        this.peakShavingStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartHour1EditText);
        this.peakShavingStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartMinute1EditText);
        Button button78 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartTime1Button);
        this.peakShavingStartTime1Button = button78;
        button78.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.92
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_START_TIME_1", lv112KRemoteSetFragment.peakShavingStartHour1EditText, Lv112KRemoteSetFragment.this.peakShavingStartMinute1EditText, Lv112KRemoteSetFragment.this.peakShavingStartTime1Button);
            }
        });
        this.peakShavingEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndHour1EditText);
        this.peakShavingEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndMinute1EditText);
        Button button79 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndTime1Button);
        this.peakShavingEndTime1Button = button79;
        button79.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.93
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_END_TIME_1", lv112KRemoteSetFragment.peakShavingEndHour1EditText, Lv112KRemoteSetFragment.this.peakShavingEndMinute1EditText, Lv112KRemoteSetFragment.this.peakShavingEndTime1Button);
            }
        });
        this.peakShavingStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartHour2EditText);
        this.peakShavingStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartMinute2EditText);
        Button button80 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingStartTime2Button);
        this.peakShavingStartTime2Button = button80;
        button80.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.94
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_START_TIME_2", lv112KRemoteSetFragment.peakShavingStartHour2EditText, Lv112KRemoteSetFragment.this.peakShavingStartMinute2EditText, Lv112KRemoteSetFragment.this.peakShavingStartTime2Button);
            }
        });
        this.peakShavingEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndHour2EditText);
        this.peakShavingEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndMinute2EditText);
        Button button81 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_peakShavingEndTime2Button);
        this.peakShavingEndTime2Button = button81;
        button81.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.95
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runTimeRemoteWrite("HOLD_PEAK_SHAVING_END_TIME_2", lv112KRemoteSetFragment.peakShavingEndHour2EditText, Lv112KRemoteSetFragment.this.peakShavingEndMinute2EditText, Lv112KRemoteSetFragment.this.peakShavingEndTime2Button);
            }
        });
        ToggleButton toggleButton12 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_onGridAlwaysOnFunctionButton);
        this.onGridAlwaysOnFunctionButton = toggleButton12;
        toggleButton12.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.96
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_ON_GRID_ALWAYS_ON", lv112KRemoteSetFragment.onGridAlwaysOnFunctionButton);
            }
        });
        this.startPvPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_startPvPowerEditText);
        Button button82 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_startPvPowerButton);
        this.startPvPowerButton = button82;
        button82.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.97
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_START_PV_POWER", lv112KRemoteSetFragment.startPvPowerEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.startPvPowerButton);
            }
        });
        this.smartLoadStartVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartVoltEditText);
        Button button83 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartVoltButton);
        this.smartLoadStartVoltButton = button83;
        button83.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.98
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_START_VOLT", lv112KRemoteSetFragment.smartLoadStartVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.smartLoadStartVoltButton);
            }
        });
        this.smartLoadEndVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndVoltEditText);
        Button button84 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndVoltButton);
        this.smartLoadEndVoltButton = button84;
        button84.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.99
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_END_VOLT", lv112KRemoteSetFragment.smartLoadEndVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.smartLoadEndVoltButton);
            }
        });
        this.smartLoadStartSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartSocEditText);
        Button button85 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadStartSocButton);
        this.smartLoadStartSocButton = button85;
        button85.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.100
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_START_SOC", lv112KRemoteSetFragment.smartLoadStartSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.smartLoadStartSocButton);
            }
        });
        this.smartLoadEndSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndSocEditText);
        Button button86 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_smartLoadEndSocButton);
        this.smartLoadEndSocButton = button86;
        button86.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.101
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_SMART_LOAD_END_SOC", lv112KRemoteSetFragment.smartLoadEndSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.smartLoadEndSocButton);
            }
        });
        this.acCoupleStartVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartVoltEditText);
        Button button87 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartVoltButton);
        this.acCoupleStartVoltButton = button87;
        button87.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.102
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_START_VOLT", lv112KRemoteSetFragment.acCoupleStartVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acCoupleStartVoltButton);
            }
        });
        this.acCoupleEndVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndVoltEditText);
        Button button88 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndVoltButton);
        this.acCoupleEndVoltButton = button88;
        button88.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.103
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_END_VOLT", lv112KRemoteSetFragment.acCoupleEndVoltEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acCoupleEndVoltButton);
            }
        });
        this.acCoupleStartSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartSocEditText);
        Button button89 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleStartSocButton);
        this.acCoupleStartSocButton = button89;
        button89.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.104
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_START_SOC", lv112KRemoteSetFragment.acCoupleStartSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acCoupleStartSocButton);
            }
        });
        this.acCoupleEndSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndSocEditText);
        Button button90 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acCoupleEndSocButton);
        this.acCoupleEndSocButton = button90;
        button90.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.105
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runNormalRemoteWrite("_12K_HOLD_AC_COUPLE_END_SOC", lv112KRemoteSetFragment.acCoupleEndSocEditText.getText().toString().trim(), Lv112KRemoteSetFragment.this.acCoupleEndSocButton);
            }
        });
        ToggleButton toggleButton13 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_ctDirectionReversedFunctionButton);
        this.ctDirectionReversedFunctionButton = toggleButton13;
        toggleButton13.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.106
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_CT_DIRECTION_REVERSED", lv112KRemoteSetFragment.ctDirectionReversedFunctionButton);
            }
        });
        ToggleButton toggleButton14 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_pvArcFunctionButton);
        this.pvArcFunctionButton = toggleButton14;
        toggleButton14.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.107
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_PV_ARC", lv112KRemoteSetFragment.pvArcFunctionButton);
            }
        });
        Button button91 = (Button) viewInflate.findViewById(R.id.fragment_local_set_all2DefaultButton);
        this.all2DefaultButton = button91;
        button91.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.108
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
                lv112KRemoteSetFragment.runResetRemoteWrite("ALL_TO_DEFAULT", lv112KRemoteSetFragment.all2DefaultButton);
            }
        });
        this.applicationSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_titleLayout);
        this.applicationSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_layout);
        this.applicationSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_textView);
        this.applicationSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_toggleButton);
        this.applicationSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_paramLayout);
        this.applicationSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.109
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.applicationSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.applicationSetParamLayout.setVisibility(8);
                }
            }
        });
        this.applicationSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.110
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.applicationSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.applicationSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.applicationSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.applicationSetParamLayout.setVisibility(0);
                }
            }
        });
        this.gridConnectSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_titleLayout);
        this.gridConnectSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_layout);
        this.gridConnectSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_textView);
        this.gridConnectSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_toggleButton);
        this.gridConnectSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_paramLayout);
        this.gridConnectSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.111
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.gridConnectSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(8);
                }
            }
        });
        this.gridConnectSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.112
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.gridConnectSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.gridConnectSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.gridConnectSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(0);
                }
            }
        });
        this.generatorSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_layout);
        this.generatorSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_textView);
        this.generatorSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_toggleButton);
        this.generatorSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_generator_set_paramLayout);
        this.generatorSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.113
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.generatorSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.generatorSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.generatorSetParamLayout.setVisibility(8);
                }
            }
        });
        this.generatorSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.114
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.generatorSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.generatorSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.generatorSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.generatorSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.generatorSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.generatorSetParamLayout.setVisibility(0);
                }
            }
        });
        this.chargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_layout);
        this.chargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_textView);
        this.chargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_toggleButton);
        this.chargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_paramLayout);
        this.chargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.115
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.chargeSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.chargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.chargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.116
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.chargeSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.chargeSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.chargeSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.chargeSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_layout);
        this.dischargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_textView);
        this.dischargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_toggleButton);
        this.dischargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_paramLayout);
        this.dischargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.117
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.118
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargeSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.dischargeSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargeSetParamLayout.setVisibility(0);
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
        this.dischargePeakShavingSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.119
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargePeakShavingSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.120
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargePeakShavingSetParamLayout.setVisibility(0);
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
        this.dischargeSmartLoadSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.121
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeSmartLoadSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.122
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargeSmartLoadSetParamLayout.setVisibility(0);
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
        this.dischargeAcCoupleSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.123
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeAcCoupleSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.124
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.dischargeAcCoupleSetParamLayout.setVisibility(0);
                }
            }
        });
        this.advancedSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_titleLayout);
        this.advancedSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_layout);
        this.advancedSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_textView);
        this.advancedSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_toggleButton);
        this.advancedSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_advanced_set_paramLayout);
        this.advancedSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.125
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.advancedSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.advancedSetParamLayout.setVisibility(0);
                } else {
                    Lv112KRemoteSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.advancedSetParamLayout.setVisibility(8);
                }
            }
        });
        this.advancedSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.126
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv112KRemoteSetFragment.this.advancedSetActionToggleButton.isChecked()) {
                    Lv112KRemoteSetFragment.this.advancedSetActionToggleButton.setChecked(false);
                    Lv112KRemoteSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv112KRemoteSetFragment.this.advancedSetParamLayout.setVisibility(8);
                } else {
                    Lv112KRemoteSetFragment.this.advancedSetActionToggleButton.setChecked(true);
                    Lv112KRemoteSetFragment.this.advancedSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv112KRemoteSetFragment.this.advancedSetParamLayout.setVisibility(0);
                }
            }
        });
        if (ROLE.VIEWER.equals(userData.getRole())) {
            this.timeLayout.setVisibility(8);
            this.ctSampleRatioLayout.setVisibility(8);
            this.fastZeroExportFunctionLayout.setVisibility(8);
            this.feedInGridFunctionLayout.setVisibility(8);
            this.feedInGridPowerPercentLayout.setVisibility(8);
            this.masterOrSlaveLayout.setVisibility(8);
            this.composedPhaseLayout.setVisibility(8);
            this.batterySharedFunctionLayout.setVisibility(8);
            this.maxAcInputPowerLayout.setVisibility(8);
            this.gridConnectSetTitleLayout.setVisibility(8);
            this.gridConnectSetParamLayout.setVisibility(8);
            this.dischargePeakShavingSetTitleLayout.setVisibility(8);
            this.dischargePeakShavingSetParamLayout.setVisibility(8);
            this.dischargeSmartLoadSetTitleLayout.setVisibility(8);
            this.dischargeSmartLoadSetParamLayout.setVisibility(8);
            this.dischargeAcCoupleSetTitleLayout.setVisibility(8);
            this.dischargeAcCoupleSetParamLayout.setVisibility(8);
            this.advancedSetTitleLayout.setVisibility(8);
            this.advancedSetParamLayout.setVisibility(8);
        }
        this.created = true;
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$0$com-lux-luxcloud-view-main-fragment-lv1-Lv112KRemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m619x6f315cd4(View view) {
        if (this.inverter != null) {
            RemoteSetCacheManager.getInstance().clearCache(this.inverter.getSerialNum());
            clearFragmentData();
            this.readAllButton.setEnabled(false);
            if (this.inverter.supportRead127Register()) {
                new ReadMultiParamTask(this).execute(new RemoteReadInfo(this.inverter.getSerialNum(), 0, 127), new RemoteReadInfo(this.inverter.getSerialNum(), 127, 127));
                return;
            }
            new ReadMultiParamTask(this).execute(new RemoteReadInfo(this.inverter.getSerialNum(), 0, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 40, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 80, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 120, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 160, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 200, 40));
        }
    }

    /* renamed from: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment$7, reason: invalid class name */
    class AnonymousClass7 implements View.OnClickListener {
        AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (Lv112KRemoteSetFragment.this.inverter != null) {
                boolean zIsChecked = Lv112KRemoteSetFragment.this.setToStandbyFunctionButton.isChecked();
                AlertDialog.Builder builder = new AlertDialog.Builder(Lv112KRemoteSetFragment.this.fragment.getActivity());
                builder.setTitle(zIsChecked ? R.string.phrase_func_param_normaly : R.string.phrase_func_param_standby).setIcon(android.R.drawable.ic_dialog_info).setMessage(Lv112KRemoteSetFragment.this.getString(zIsChecked ? R.string.phrase_func_text_normal : R.string.phrase_func_text_standby) + " " + Lv112KRemoteSetFragment.this.inverter.getSerialNum()).setPositiveButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment$7$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.m620x1fd84936(dialogInterface, i);
                    }
                }).setNegativeButton(R.string.phrase_button_cancel, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment$7$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.m621xfb99c4f7(dialogInterface, i);
                    }
                });
                builder.show();
                return;
            }
            Lv112KRemoteSetFragment.this.setToStandbyFunctionButton.setChecked(!Lv112KRemoteSetFragment.this.setToStandbyFunctionButton.isChecked());
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-main-fragment-lv1-Lv112KRemoteSetFragment$7, reason: not valid java name */
        /* synthetic */ void m620x1fd84936(DialogInterface dialogInterface, int i) {
            Lv112KRemoteSetFragment lv112KRemoteSetFragment = Lv112KRemoteSetFragment.this;
            lv112KRemoteSetFragment.runControlRemoteWrite("FUNC_SET_TO_STANDBY", lv112KRemoteSetFragment.setToStandbyFunctionButton);
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-main-fragment-lv1-Lv112KRemoteSetFragment$7, reason: not valid java name */
        /* synthetic */ void m621xfb99c4f7(DialogInterface dialogInterface, int i) {
            Lv112KRemoteSetFragment.this.setToStandbyFunctionButton.setChecked(!Lv112KRemoteSetFragment.this.setToStandbyFunctionButton.isChecked());
        }
    }

    private void initInverterSpinnerOnItemSelectedListener() {
        if (this.inverterSpinner.getOnItemSelectedListener() == null) {
            System.out.println("LuxPower - Lv112KRemoteSetFragment initInverterSpinnerOnItemSelectedListener...");
            this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.127
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    Lv112KRemoteSetFragment.this.updateSelectInverter((Inverter) Lv112KRemoteSetFragment.this.inverterSpinner.getSelectedItem());
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                    if (Lv112KRemoteSetFragment.this.inverter != null) {
                        Lv112KRemoteSetFragment.this.inverter = null;
                        GlobalInfo.getInstance().getUserData().setCurrentInverter(Lv112KRemoteSetFragment.this.inverter, true);
                        Lv112KRemoteSetFragment.this.clearFragmentData();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectInverter(Inverter inverter) {
        System.out.println("LuxPower - Lv112KRemoteSetFragment selectInverter = " + inverter.getSerialNum() + ", inverter = " + this.inverter);
        Inverter inverter2 = this.inverter;
        if (inverter2 == null || !inverter2.getSerialNum().equals(inverter.getSerialNum())) {
            this.inverter = inverter;
            GlobalInfo.getInstance().getUserData().setCurrentInverter(this.inverter, true);
            if (this.inverter.isAllowExport2Grid()) {
                this.feedInGridFunctionLayout.setVisibility(0);
                this.feedInGridPowerPercentLayout.setVisibility(0);
            } else {
                this.feedInGridFunctionLayout.setVisibility(8);
                this.feedInGridPowerPercentLayout.setVisibility(8);
            }
            if (!this.inverter.isType6()) {
                MainActivity mainActivity = (MainActivity) this.fragment.getActivity();
                if (mainActivity != null) {
                    mainActivity.switchRemoteSetFragment(this.inverter.getDeviceTypeValue());
                    return;
                }
                return;
            }
            clearFragmentData();
            fillDataFromCache();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("LuxPower - Lv112KRemoteSetFragment onResume...");
        initInverterSpinnerOnItemSelectedListener();
        refreshFragmentParams();
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        System.out.println("LuxPower - 12K Set refreshFragmentParams created = " + this.created);
        if (this.created) {
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.getCurrentInverter() == null || this.inverterList == null) {
                return;
            }
            System.out.println("LuxPower - 12K Set refreshFragmentParams userData.getCurrentInverter() = " + userData.getCurrentInverter().getSerialNum());
            for (int i = 0; i < this.inverterList.size(); i++) {
                Inverter inverter2 = this.inverterList.get(i);
                if (inverter2.getSerialNum().equals(userData.getCurrentInverter().getSerialNum()) && ((inverter = this.inverter) == null || !inverter.getSerialNum().equals(userData.getCurrentInverter().getSerialNum()))) {
                    if (this.inverterSpinner.getSelectedItemPosition() != i) {
                        this.inverterSpinner.setSelection(i);
                    } else {
                        updateSelectInverter(inverter2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runNormalRemoteWrite(String str, String str2, Button button) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.NORMAL);
            remoteWriteInfo.setHoldParam(str);
            remoteWriteInfo.setValueText(str2);
            remoteWriteInfo.setSetButton(button);
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runResetRemoteWrite(String str, Button button) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.RESET);
            remoteWriteInfo.setResetParam(str);
            remoteWriteInfo.setSetButton(button);
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runBitRemoteWrite(String str, String str2, Button button) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.BIT_PARAM);
            remoteWriteInfo.setBitParam(str);
            remoteWriteInfo.setValueText(str2);
            remoteWriteInfo.setSetButton(button);
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runTimeRemoteWrite(String str, EditText editText, EditText editText2, Button button) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.TIME);
            remoteWriteInfo.setTimeParam(str);
            remoteWriteInfo.setHourText(editText.getText().toString().trim());
            remoteWriteInfo.setMinuteText(editText2.getText().toString().trim());
            remoteWriteInfo.setSetButton(button);
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runControlRemoteWrite(String str, boolean z, Button button) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
            remoteWriteInfo.setFunctionParam(str);
            remoteWriteInfo.setFunctionToggleButtonChecked(z);
            remoteWriteInfo.setSetButton(button);
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runControlRemoteWrite(String str, ToggleButton toggleButton) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
            remoteWriteInfo.setFunctionParam(str);
            if (toggleButton != null) {
                remoteWriteInfo.setFunctionToggleButtonChecked(toggleButton.isChecked());
                remoteWriteInfo.setFunctionToggleButton(toggleButton);
            }
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    public void clearFragmentData() {
        if (this.created) {
            this.timeDateEditText.setText("");
            this.timeTimeEditText.setText("");
            this.ctSampleRatioSpinner.setSelection(0);
            this.setToStandbyFunctionButton.setChecked(false);
            this.epsFunctionButton.setChecked(false);
            this.epsFrequencySetSpinner.setSelection(0);
            this.fastZeroExportFunctionButton.setChecked(false);
            this.runWithoutGridFunctionButton.setChecked(false);
            this.feedInGridFunctionButton.setChecked(false);
            this.feedInGridPowerPercentEditText.setText("");
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
            this.ctDirectionReversedFunctionButton.setChecked(false);
            this.pvArcFunctionButton.setChecked(false);
        }
    }

    public void fillDataFromCache() {
        if (this.created) {
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.getCurrentInverter() != null) {
                JSONObject cache = RemoteSetCacheManager.getInstance().getCache(userData.getCurrentInverter().getSerialNum());
                System.out.println("LuxPower - 12k remote set result from cache = " + cache);
                if (cache != null) {
                    try {
                        analyzeResultToFragment(cache);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            this.readAllButton.performClick();
        }
    }

    private static class ReadMultiParamTask extends AsyncTask<RemoteReadInfo, JSONObject, Void> {
        private Lv112KRemoteSetFragment fragment;

        public ReadMultiParamTask(Lv112KRemoteSetFragment lv112KRemoteSetFragment) {
            this.fragment = lv112KRemoteSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(RemoteReadInfo... remoteReadInfoArr) {
            JSONObject jSONObjectPostJson;
            for (RemoteReadInfo remoteReadInfo : remoteReadInfoArr) {
                HashMap map = new HashMap();
                map.put("inverterSn", remoteReadInfo.getSerialNum());
                map.put("startRegister", String.valueOf(remoteReadInfo.getStartRegister()));
                map.put("pointNumber", String.valueOf(remoteReadInfo.getPointNumber()));
                map.put("autoRetry", String.valueOf(true));
                try {
                    jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteRead/read", map);
                    publishProgress(jSONObjectPostJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jSONObjectPostJson == null || !jSONObjectPostJson.getBoolean("success")) {
                    return null;
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Toast.makeText(this.fragment.getActivity().getApplicationContext(), R.string.page_maintain_remote_set_result_unknown_error, 1).show();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (jSONObject.getBoolean("success")) {
                        Lv112KRemoteSetFragment lv112KRemoteSetFragment = this.fragment;
                        if (lv112KRemoteSetFragment != null && lv112KRemoteSetFragment.inverter != null) {
                            RemoteSetCacheManager.getInstance().setCache(this.fragment.inverter.getSerialNum(), jSONObject);
                        }
                        this.fragment.analyzeResultToFragment(jSONObject);
                    }
                }
                this.fragment.toast(jSONObject);
                return;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r2) {
            super.onPostExecute((ReadMultiParamTask) r2);
            this.fragment.readAllButton.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void analyzeResultToFragment(JSONObject jSONObject) throws Exception {
        if (jSONObject.has("HOLD_TIME")) {
            String string = jSONObject.getString("HOLD_TIME");
            this.timeDateEditText.setText(string.substring(0, 10));
            this.timeTimeEditText.setText(string.substring(11, 16));
        }
        if (jSONObject.has("BIT_CT_SAMPLE_RATIO")) {
            int i = Integer.parseInt(jSONObject.getString("BIT_CT_SAMPLE_RATIO"));
            if (i >= 0 && i <= 2) {
                this.ctSampleRatioSpinner.setSelection(i + 1);
            } else {
                this.ctSampleRatioSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("FUNC_SET_TO_STANDBY")) {
            this.setToStandbyFunctionButton.setChecked(jSONObject.getBoolean("FUNC_SET_TO_STANDBY"));
        }
        if (jSONObject.has("FUNC_EPS_EN")) {
            this.epsFunctionButton.setChecked(jSONObject.getBoolean("FUNC_EPS_EN"));
        }
        if (jSONObject.has("HOLD_EPS_FREQ_SET")) {
            int i2 = Integer.parseInt(jSONObject.getString("HOLD_EPS_FREQ_SET"));
            this.epsFrequencySetSpinner.setSelection(i2 == 50 ? 1 : i2 == 60 ? 2 : 0);
        }
        if (jSONObject.has("FUNC_RUN_WITHOUT_GRID")) {
            this.fastZeroExportFunctionButton.setChecked(jSONObject.getBoolean("FUNC_RUN_WITHOUT_GRID"));
        }
        if (jSONObject.has("FUNC_RUN_WITHOUT_GRID_12K")) {
            this.runWithoutGridFunctionButton.setChecked(jSONObject.getBoolean("FUNC_RUN_WITHOUT_GRID_12K"));
        }
        if (jSONObject.has("FUNC_FEED_IN_GRID_EN")) {
            this.feedInGridFunctionButton.setChecked(jSONObject.getBoolean("FUNC_FEED_IN_GRID_EN"));
        }
        if (jSONObject.has("HOLD_FEED_IN_GRID_POWER_PERCENT")) {
            this.feedInGridPowerPercentEditText.setText(jSONObject.getString("HOLD_FEED_IN_GRID_POWER_PERCENT"));
        }
        if (jSONObject.has("HOLD_SET_MASTER_OR_SLAVE")) {
            int i3 = Integer.parseInt(jSONObject.getString("HOLD_SET_MASTER_OR_SLAVE"));
            if (i3 >= 1 && i3 <= 4) {
                this.masterOrSlaveSpinner.setSelection(i3);
            } else {
                this.masterOrSlaveSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("HOLD_SET_COMPOSED_PHASE")) {
            int i4 = Integer.parseInt(jSONObject.getString("HOLD_SET_COMPOSED_PHASE"));
            int i5 = i4 >> 8;
            if (i5 >= 1 && i5 <= 3) {
                this.readComposedPhaseSpinner.setSelection(i5);
            } else {
                this.readComposedPhaseSpinner.setSelection(0);
            }
            int i6 = i4 & 255;
            if (i6 >= 0 && i6 <= 3) {
                this.setComposedPhaseSpinner.setSelection(i6 + 1);
            } else {
                this.setComposedPhaseSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("FUNC_BAT_SHARED")) {
            this.batterySharedFunctionButton.setChecked(jSONObject.getBoolean("FUNC_BAT_SHARED"));
        }
        if (jSONObject.has("HOLD_MAX_AC_INPUT_POWER")) {
            this.maxAcInputPowerEditText.setText(jSONObject.getString("HOLD_MAX_AC_INPUT_POWER"));
        }
        if (jSONObject.has("_12K_HOLD_GRID_REGULATION")) {
            int i7 = Integer.parseInt(jSONObject.getString("_12K_HOLD_GRID_REGULATION"));
            if (i7 >= 0 && i7 <= 5) {
                this.gridRegulationSpinner.setSelection(i7 + 1);
            } else {
                this.gridRegulationSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("_12K_HOLD_GRID_TYPE")) {
            int i8 = Integer.parseInt(jSONObject.getString("_12K_HOLD_GRID_TYPE"));
            if (i8 >= 0 && i8 <= 4) {
                this.gridTypeSpinner.setSelection(i8 + 1);
            } else {
                this.gridTypeSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("FUNC_DRMS_EN")) {
            this.drmsFunctionButton.setChecked(jSONObject.getBoolean("FUNC_DRMS_EN"));
        }
        if (jSONObject.has("HOLD_RECONNECT_TIME")) {
            this.reconnectTimeEditText.setText(jSONObject.getString("HOLD_RECONNECT_TIME"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_CONN_HIGH")) {
            this.gridVoltConnHighEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_CONN_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_CONN_LOW")) {
            this.gridVoltConnLowEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_CONN_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_CONN_HIGH")) {
            this.gridFreqConnHighEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_CONN_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_CONN_LOW")) {
            this.gridFreqConnLowEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_CONN_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_LIMIT1_LOW")) {
            this.gridVoltLimit1LowEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_LIMIT1_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
            this.gridVoltLimit1HighEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_LIMIT1_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_LIMIT2_LOW")) {
            this.gridVoltLimit2LowEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_LIMIT2_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
            this.gridVoltLimit2HighEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_LIMIT2_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_LIMIT3_LOW")) {
            this.gridVoltLimit3LowEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_LIMIT3_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
            this.gridVoltLimit3HighEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_LIMIT3_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_LIMIT1_LOW")) {
            this.gridFreqLimit1LowEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_LIMIT1_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
            this.gridFreqLimit1HighEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_LIMIT1_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_LIMIT2_LOW")) {
            this.gridFreqLimit2LowEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_LIMIT2_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
            this.gridFreqLimit2HighEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_LIMIT2_HIGH"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_LIMIT3_LOW")) {
            this.gridFreqLimit3LowEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_LIMIT3_LOW"));
        }
        if (jSONObject.has("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
            this.gridFreqLimit3HighEditText.setText(jSONObject.getString("HOLD_GRID_FREQ_LIMIT3_HIGH"));
        }
        if (jSONObject.has("FUNC_AC_CHARGE")) {
            this.acChargeFunctionButton.setChecked(jSONObject.getBoolean("FUNC_AC_CHARGE"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_POWER_CMD")) {
            this.acChargePowerEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_POWER_CMD"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_BATTERY_SOC")) {
            this.startAcChargeSOCEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_BATTERY_SOC"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_SOC_LIMIT")) {
            this.acChargeSocLimitEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_SOC_LIMIT"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
            this.startAcChargeVoltEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
            this.acChargeEndBatteryVoltageEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_HOUR")) {
            this.acChargeStartHourEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_HOUR"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_MINUTE")) {
            this.acChargeStartMinuteEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_MINUTE"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_HOUR")) {
            this.acChargeEndHourEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_HOUR"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_MINUTE")) {
            this.acChargeEndMinuteEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_MINUTE"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_HOUR_1")) {
            this.acChargeStartHour1EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_HOUR_1"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_MINUTE_1")) {
            this.acChargeStartMinute1EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_MINUTE_1"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_HOUR_1")) {
            this.acChargeEndHour1EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_HOUR_1"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_MINUTE_1")) {
            this.acChargeEndMinute1EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_MINUTE_1"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_HOUR_2")) {
            this.acChargeStartHour2EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_HOUR_2"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_START_MINUTE_2")) {
            this.acChargeStartMinute2EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_START_MINUTE_2"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_HOUR_2")) {
            this.acChargeEndHour2EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_HOUR_2"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_END_MINUTE_2")) {
            this.acChargeEndMinute2EditText.setText(jSONObject.getString("HOLD_AC_CHARGE_END_MINUTE_2"));
        }
        if (jSONObject.has("FUNC_FORCED_CHG_EN")) {
            this.forcedChgFunctionButton.setChecked(jSONObject.getBoolean("FUNC_FORCED_CHG_EN"));
        }
        if (jSONObject.has("HOLD_FORCED_CHG_POWER_CMD")) {
            this.forcedChgPowerCmdEditText.setText(jSONObject.getString("HOLD_FORCED_CHG_POWER_CMD"));
        }
        if (jSONObject.has("HOLD_FORCED_CHG_SOC_LIMIT")) {
            this.forcedChgSocLimitEditText.setText(jSONObject.getString("HOLD_FORCED_CHG_SOC_LIMIT"));
        }
        if (jSONObject.has("_12K_HOLD_CHARGE_FIRST_VOLT")) {
            this.chargeFirstVoltEditText.setText(jSONObject.getString("_12K_HOLD_CHARGE_FIRST_VOLT"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_START_HOUR")) {
            this.forcedChargeStartHourEditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_START_HOUR"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_START_MINUTE")) {
            this.forcedChargeStartMinuteEditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_START_MINUTE"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_END_HOUR")) {
            this.forcedChargeEndHourEditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_END_HOUR"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_END_MINUTE")) {
            this.forcedChargeEndMinuteEditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_END_MINUTE"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_START_HOUR_1")) {
            this.forcedChargeStartHour1EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_START_HOUR_1"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_START_MINUTE_1")) {
            this.forcedChargeStartMinute1EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_START_MINUTE_1"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_END_HOUR_1")) {
            this.forcedChargeEndHour1EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_END_HOUR_1"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_END_MINUTE_1")) {
            this.forcedChargeEndMinute1EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_END_MINUTE_1"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_START_HOUR_2")) {
            this.forcedChargeStartHour2EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_START_HOUR_2"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_START_MINUTE_2")) {
            this.forcedChargeStartMinute2EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_START_MINUTE_2"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_END_HOUR_2")) {
            this.forcedChargeEndHour2EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_END_HOUR_2"));
        }
        if (jSONObject.has("HOLD_FORCED_CHARGE_END_MINUTE_2")) {
            this.forcedChargeEndMinute2EditText.setText(jSONObject.getString("HOLD_FORCED_CHARGE_END_MINUTE_2"));
        }
        if (jSONObject.has("HOLD_LEAD_ACID_CHARGE_VOLT_REF")) {
            this.leadAcidChargeVoltRefEditText.setText(jSONObject.getString("HOLD_LEAD_ACID_CHARGE_VOLT_REF"));
        }
        if (jSONObject.has("HOLD_FLOATING_VOLTAGE")) {
            this.floatingVoltageEditText.setText(jSONObject.getString("HOLD_FLOATING_VOLTAGE"));
        }
        if (jSONObject.has("FUNC_BAT_DISCHARGE_CONTROL")) {
            boolean z = jSONObject.getBoolean("FUNC_BAT_DISCHARGE_CONTROL");
            this.batDischargeControlSpinner.setSelection(z ? 1 : 2);
            refreshBattDisChgState(z);
        }
        if (jSONObject.has("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR")) {
            this.batChargeCurrentLimitEditText.setText(jSONObject.getString("OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR"));
        }
        if (jSONObject.has("HOLD_MAX_GENERATOR_INPUT_POWER")) {
            this.genRatedPowerEditText.setText(jSONObject.getString("HOLD_MAX_GENERATOR_INPUT_POWER"));
        }
        if (jSONObject.has("OFF_GRID_HOLD_GEN_CHG_START_VOLT")) {
            this.chargeStartVoltEditText.setText(jSONObject.getString("OFF_GRID_HOLD_GEN_CHG_START_VOLT"));
        }
        if (jSONObject.has("OFF_GRID_HOLD_GEN_CHG_END_VOLT")) {
            this.chargeEndVoltEditText.setText(jSONObject.getString("OFF_GRID_HOLD_GEN_CHG_END_VOLT"));
        }
        if (jSONObject.has("OFF_GRID_HOLD_GEN_CHG_START_SOC")) {
            this.chargeStartSocEditText.setText(jSONObject.getString("OFF_GRID_HOLD_GEN_CHG_START_SOC"));
        }
        if (jSONObject.has("OFF_GRID_HOLD_GEN_CHG_END_SOC")) {
            this.chargeEndSocEditText.setText(jSONObject.getString("OFF_GRID_HOLD_GEN_CHG_END_SOC"));
        }
        if (jSONObject.has("HOLD_LEAD_ACID_CHARGE_RATE")) {
            this.chargeCurrentEditText.setText(jSONObject.getString("HOLD_LEAD_ACID_CHARGE_RATE"));
        }
        if (jSONObject.has("FUNC_BAT_CHARGE_CONTROL")) {
            boolean z2 = jSONObject.getBoolean("FUNC_BAT_CHARGE_CONTROL");
            this.batChargeControlSpinner.setSelection(z2 ? 1 : 2);
            refreshBattChgState(z2);
        }
        if (jSONObject.has("FUNC_FORCED_DISCHG_EN")) {
            this.forcedDisChgFunctionButton.setChecked(jSONObject.getBoolean("FUNC_FORCED_DISCHG_EN"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHG_POWER_CMD")) {
            this.forcedDisChgPowerCmdEditText.setText(jSONObject.getString("HOLD_FORCED_DISCHG_POWER_CMD"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHG_SOC_LIMIT")) {
            this.forcedDisChgSocLimitEditText.setText(jSONObject.getString("HOLD_FORCED_DISCHG_SOC_LIMIT"));
        }
        if (jSONObject.has("_12K_HOLD_STOP_DISCHG_VOLT")) {
            this.stopDischgVoltEditText.setText(jSONObject.getString("_12K_HOLD_STOP_DISCHG_VOLT"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_START_HOUR")) {
            this.forcedDisChargeStartHourEditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_START_HOUR"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_START_MINUTE")) {
            this.forcedDisChargeStartMinuteEditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_START_MINUTE"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_END_HOUR")) {
            this.forcedDisChargeEndHourEditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_END_HOUR"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_END_MINUTE")) {
            this.forcedDisChargeEndMinuteEditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_END_MINUTE"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_START_HOUR_1")) {
            this.forcedDisChargeStartHour1EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_START_HOUR_1"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_START_MINUTE_1")) {
            this.forcedDisChargeStartMinute1EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_START_MINUTE_1"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_END_HOUR_1")) {
            this.forcedDisChargeEndHour1EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_END_HOUR_1"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_END_MINUTE_1")) {
            this.forcedDisChargeEndMinute1EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_END_MINUTE_1"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_START_HOUR_2")) {
            this.forcedDisChargeStartHour2EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_START_HOUR_2"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_START_MINUTE_2")) {
            this.forcedDisChargeStartMinute2EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_START_MINUTE_2"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_END_HOUR_2")) {
            this.forcedDisChargeEndHour2EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_END_HOUR_2"));
        }
        if (jSONObject.has("HOLD_FORCED_DISCHARGE_END_MINUTE_2")) {
            this.forcedDisChargeEndMinute2EditText.setText(jSONObject.getString("HOLD_FORCED_DISCHARGE_END_MINUTE_2"));
        }
        if (jSONObject.has("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
            this.leadAcidDischargeCutOffVoltEditText.setText(jSONObject.getString("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT"));
        }
        if (jSONObject.has("HOLD_DISCHG_CUT_OFF_SOC_EOD")) {
            this.onGridDischargeCutoffSocEditText.setText(jSONObject.getString("HOLD_DISCHG_CUT_OFF_SOC_EOD"));
        }
        if (jSONObject.has("HOLD_SOC_LOW_LIMIT_EPS_DISCHG")) {
            this.offGridDischargeCutoffSocEditText.setText(jSONObject.getString("HOLD_SOC_LOW_LIMIT_EPS_DISCHG"));
        }
        if (jSONObject.has("HOLD_ON_GRID_EOD_VOLTAGE")) {
            this.onGridEodVoltageEditText.setText(jSONObject.getString("HOLD_ON_GRID_EOD_VOLTAGE"));
        }
        if (jSONObject.has("HOLD_LEAD_ACID_DISCHARGE_RATE")) {
            this.dischgCurrentEditText.setText(jSONObject.getString("HOLD_LEAD_ACID_DISCHARGE_RATE"));
        }
        if (jSONObject.has("FUNC_GRID_PEAK_SHAVING")) {
            this.gridPeakShavingFunctionButton.setChecked(jSONObject.getBoolean("FUNC_GRID_PEAK_SHAVING"));
        }
        if (jSONObject.has("_12K_HOLD_GRID_PEAK_SHAVING_POWER")) {
            this.gridPeakShavingPowerEditText.setText(jSONObject.getString("_12K_HOLD_GRID_PEAK_SHAVING_POWER"));
        }
        if (jSONObject.has("_12K_HOLD_GRID_PEAK_SHAVING_VOLT")) {
            this.gridPeakShavingVolt1EditText.setText(jSONObject.getString("_12K_HOLD_GRID_PEAK_SHAVING_VOLT"));
        }
        if (jSONObject.has("_12K_HOLD_GRID_PEAK_SHAVING_SOC")) {
            this.gridPeakShavingSoc1EditText.setText(jSONObject.getString("_12K_HOLD_GRID_PEAK_SHAVING_SOC"));
        }
        if (jSONObject.has("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2")) {
            this.gridPeakShavingVolt2EditText.setText(jSONObject.getString("_12K_HOLD_GRID_PEAK_SHAVING_VOLT_2"));
        }
        if (jSONObject.has("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2")) {
            this.gridPeakShavingSoc2EditText.setText(jSONObject.getString("_12K_HOLD_GRID_PEAK_SHAVING_SOC_2"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_37")) {
            this.peakShavingStartHour1EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_37"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_38")) {
            this.peakShavingStartMinute1EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_38"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_39")) {
            this.peakShavingEndHour1EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_39"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_40")) {
            this.peakShavingEndMinute1EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_40"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_41")) {
            this.peakShavingStartHour2EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_41"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_42")) {
            this.peakShavingStartMinute2EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_42"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_43")) {
            this.peakShavingEndHour2EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_43"));
        }
        if (jSONObject.has("LSP_HOLD_DIS_CHG_POWER_TIME_44")) {
            this.peakShavingEndMinute2EditText.setText(jSONObject.getString("LSP_HOLD_DIS_CHG_POWER_TIME_44"));
        }
        if (jSONObject.has("FUNC_ON_GRID_ALWAYS_ON")) {
            this.onGridAlwaysOnFunctionButton.setChecked(jSONObject.getBoolean("FUNC_ON_GRID_ALWAYS_ON"));
        }
        if (jSONObject.has("_12K_HOLD_START_PV_POWER")) {
            this.startPvPowerEditText.setText(jSONObject.getString("_12K_HOLD_START_PV_POWER"));
        }
        if (jSONObject.has("_12K_HOLD_SMART_LOAD_START_VOLT")) {
            this.smartLoadStartVoltEditText.setText(jSONObject.getString("_12K_HOLD_SMART_LOAD_START_VOLT"));
        }
        if (jSONObject.has("_12K_HOLD_SMART_LOAD_END_VOLT")) {
            this.smartLoadEndVoltEditText.setText(jSONObject.getString("_12K_HOLD_SMART_LOAD_END_VOLT"));
        }
        if (jSONObject.has("_12K_HOLD_SMART_LOAD_START_SOC")) {
            this.smartLoadStartSocEditText.setText(jSONObject.getString("_12K_HOLD_SMART_LOAD_START_SOC"));
        }
        if (jSONObject.has("_12K_HOLD_SMART_LOAD_END_SOC")) {
            this.smartLoadEndSocEditText.setText(jSONObject.getString("_12K_HOLD_SMART_LOAD_END_SOC"));
        }
        if (jSONObject.has("_12K_HOLD_AC_COUPLE_START_VOLT")) {
            this.acCoupleStartVoltEditText.setText(jSONObject.getString("_12K_HOLD_AC_COUPLE_START_VOLT"));
        }
        if (jSONObject.has("_12K_HOLD_AC_COUPLE_END_VOLT")) {
            this.acCoupleEndVoltEditText.setText(jSONObject.getString("_12K_HOLD_AC_COUPLE_END_VOLT"));
        }
        if (jSONObject.has("_12K_HOLD_AC_COUPLE_START_SOC")) {
            this.acCoupleStartSocEditText.setText(jSONObject.getString("_12K_HOLD_AC_COUPLE_START_SOC"));
        }
        if (jSONObject.has("_12K_HOLD_AC_COUPLE_END_SOC")) {
            this.acCoupleEndSocEditText.setText(jSONObject.getString("_12K_HOLD_AC_COUPLE_END_SOC"));
        }
        if (jSONObject.has("FUNC_CT_DIRECTION_REVERSED")) {
            this.ctDirectionReversedFunctionButton.setChecked(jSONObject.getBoolean("FUNC_CT_DIRECTION_REVERSED"));
        }
        if (jSONObject.has("FUNC_PV_ARC")) {
            this.pvArcFunctionButton.setChecked(jSONObject.getBoolean("FUNC_PV_ARC"));
        }
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private Lv112KRemoteSetFragment fragment;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(Lv112KRemoteSetFragment lv112KRemoteSetFragment) {
            this.fragment = lv112KRemoteSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
            this.remoteWriteInfo.setEnabled(false);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public JSONObject doInBackground(RemoteWriteInfo... remoteWriteInfoArr) throws NumberFormatException {
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                publishProgress(new Void[0]);
                int i = AnonymousClass128.$SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()];
                if (i == 1) {
                    if (Tool.isEmpty(remoteWriteInfo.getValueText())) {
                        return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                    }
                    return postWriteParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getHoldParam(), remoteWriteInfo.getValueText());
                }
                if (i == 2) {
                    String valueText = remoteWriteInfo.getValueText();
                    if (Tool.isEmpty(valueText)) {
                        return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                    }
                    return postWriteBitParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getBitParam(), valueText);
                }
                if (i == 3) {
                    return postResetParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getResetParam());
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
                            return this.fragment.createOutRangeJSONObject("0", "23");
                        }
                        if (i3 < 0 || i3 > 59) {
                            return this.fragment.createOutRangeJSONObject("0", "59");
                        }
                        return postWriteTimeParam(remoteWriteInfo.getSerialNum(), remoteWriteInfo.getTimeParam(), hourText, minuteText);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return this.fragment.createFailureJSONObject("INTEGER_FORMAT_ERROR");
                    }
                }
                if (i == 5) {
                    HashMap map = new HashMap();
                    map.put("inverterSn", remoteWriteInfo.getSerialNum());
                    map.put("functionParam", remoteWriteInfo.getFunctionParam());
                    map.put("enable", String.valueOf(remoteWriteInfo.isFunctionToggleButtonChecked()));
                    map.put("clientType", "APP");
                    map.put("remoteSetType", InvTool.STATUS_NORMAL);
                    try {
                        return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/functionControl", map);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            return this.fragment.createFailureJSONObject("UNKNOWN_ERROR");
        }

        private JSONObject postWriteParam(String str, String str2, String str3) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("holdParam", str2);
            map.put("valueText", str3);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/write", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postResetParam(String str, String str2) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("resetParam", str2);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/reset", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postWriteBitParam(String str, String str2, String str3) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("bitParam", str2);
            map.put("value", str3);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/bitParamControl", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject postWriteTimeParam(String str, String str2, String str3, String str4) {
            HashMap map = new HashMap();
            map.put("inverterSn", str);
            map.put("timeParam", str2);
            map.put("hour", str3);
            map.put("minute", str4);
            map.put("clientType", "APP");
            map.put("remoteSetType", InvTool.STATUS_NORMAL);
            try {
                return HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/maintain/remoteSet/writeTime", map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0071 A[Catch: all -> 0x00ae, Exception -> 0x00b0, TryCatch #0 {Exception -> 0x00b0, blocks: (B:4:0x0006, B:6:0x000e, B:8:0x002f, B:10:0x003d, B:12:0x004b, B:13:0x0057, B:15:0x0065, B:16:0x0071, B:18:0x0075, B:20:0x0083, B:22:0x008b, B:26:0x00a0, B:27:0x00a3), top: B:41:0x0006, outer: #1 }] */
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
                if (r4 == 0) goto L71
                java.lang.String r1 = "success"
                boolean r1 = r4.getBoolean(r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r1 == 0) goto L71
                com.lux.luxcloud.global.cache.RemoteSetCacheManager r4 = com.lux.luxcloud.global.cache.RemoteSetCacheManager.getInstance()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment r1 = r3.fragment     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.inverter.Inverter r1 = com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.access$700(r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                java.lang.String r1 = r1.getSerialNum()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                r4.clearCache(r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                r1 = 2131886561(0x7f1201e1, float:1.9407704E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r4 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r4 == 0) goto La8
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r4 = com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r1 = r1.getRemoteWriteType()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r4 = r4.equals(r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r4 == 0) goto La8
                java.lang.String r4 = "FUNC_BAT_CHARGE_CONTROL"
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                java.lang.String r1 = r1.getFunctionParam()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r4 = r4.equals(r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r4 == 0) goto L57
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r1 = r1.isFunctionToggleButtonChecked()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.access$25800(r4, r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                goto La8
            L57:
                java.lang.String r4 = "FUNC_BAT_DISCHARGE_CONTROL"
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                java.lang.String r1 = r1.getFunctionParam()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r4 = r4.equals(r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r4 == 0) goto La8
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r1 = r1.isFunctionToggleButtonChecked()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.access$25900(r4, r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                goto La8
            L71:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r1 == 0) goto La3
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r1 = com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r2 = r2.getRemoteWriteType()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r1 == 0) goto La3
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r1 == 0) goto La3
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                android.widget.ToggleButton r2 = r2.getFunctionToggleButton()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                boolean r2 = r2.isChecked()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                if (r2 != 0) goto L9f
                r2 = r0
                goto La0
            L9f:
                r2 = 0
            La0:
                r1.setChecked(r2)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
            La3:
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment r1 = r3.fragment     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.access$25400(r1, r4)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lb0
            La8:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r4 = r3.remoteWriteInfo
                r4.setEnabled(r0)
                goto Lc6
            Lae:
                r4 = move-exception
                goto Lc7
            Lb0:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> Lae
                com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lc1
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lc1
                r1 = 2131886563(0x7f1201e3, float:1.9407708E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> Lae java.lang.Exception -> Lc1
                goto La8
            Lc1:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> Lae
                goto La8
            Lc6:
                return
            Lc7:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment$128, reason: invalid class name */
    static /* synthetic */ class AnonymousClass128 {
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
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.RESET.ordinal()] = 3;
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
    public JSONObject createOutRangeJSONObject(String str, String str2) throws JSONException {
        try {
            JSONObject jSONObjectCreateFailureJSONObject = createFailureJSONObject("OUT_RANGE_ERROR");
            if (jSONObjectCreateFailureJSONObject == null) {
                return null;
            }
            jSONObjectCreateFailureJSONObject.put("minValue", str);
            jSONObjectCreateFailureJSONObject.put("maxValue", str2);
            return jSONObjectCreateFailureJSONObject;
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
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void toast(org.json.JSONObject r6) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment.toast(org.json.JSONObject):void");
    }

    public EditText getTimeDateEditText() {
        return this.timeDateEditText;
    }

    public EditText getTimeTimeEditText() {
        return this.timeTimeEditText;
    }
}