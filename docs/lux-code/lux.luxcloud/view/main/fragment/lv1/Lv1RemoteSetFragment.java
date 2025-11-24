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
public class Lv1RemoteSetFragment extends AbstractItemFragment {
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
    private Button acChargePowerCmdButton;
    private EditText acChargePowerCmdEditText;
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
    private ConstraintLayout applicationSetActionLayout;
    private TextView applicationSetActionTextView;
    private ToggleButton applicationSetActionToggleButton;
    private LinearLayout applicationSetParamLayout;
    private ConstraintLayout applicationSetTitleLayout;
    private ToggleButton batterySharedFunctionButton;
    private ConstraintLayout batterySharedFunctionLayout;
    private Button chargeCurrentButton;
    private EditText chargeCurrentEditText;
    private ConstraintLayout chargeCurrentLayout;
    private Button chargePowerPercentCmdButton;
    private EditText chargePowerPercentCmdEditText;
    private ConstraintLayout chargePowerPercentCmdLayout;
    private ConstraintLayout chargeSetActionLayout;
    private TextView chargeSetActionTextView;
    private ToggleButton chargeSetActionToggleButton;
    private LinearLayout chargeSetParamLayout;
    private EditText comAddrEditText;
    private ConstraintLayout comAddrLayout;
    private Button composedPhaseButton;
    private ConstraintLayout composedPhaseLayout;
    private Button connectTimeButton;
    private EditText connectTimeEditText;
    private boolean created;
    private Button ctSampleRatioButton;
    private ConstraintLayout ctSampleRatioLayout;
    private Spinner ctSampleRatioSpinner;
    private Button dischargeCurrentLimitButton;
    private EditText dischargeCurrentLimitEditText;
    private ConstraintLayout dischargeCurrentLimitLayout;
    private ConstraintLayout dischargeSetActionLayout;
    private TextView dischargeSetActionTextView;
    private ToggleButton dischargeSetActionToggleButton;
    private LinearLayout dischargeSetParamLayout;
    private Button eodButton;
    private EditText eodEditText;
    private ConstraintLayout eodLayout;
    private ToggleButton epsFunctionButton;
    private ConstraintLayout epsFunctionLayout;
    private Button equalizationPeriodButton;
    private EditText equalizationPeriodEditText;
    private ConstraintLayout equalizationPeriodLayout;
    private Button equalizationTimeButton;
    private EditText equalizationTimeEditText;
    private ConstraintLayout equalizationTimeLayout;
    private Button equalizationVoltageButton;
    private EditText equalizationVoltageEditText;
    private ConstraintLayout equalizationVoltageLayout;
    private ToggleButton feedInGridFunctionButton;
    private ConstraintLayout feedInGridFunctionLayout;
    private Button feedInGridPowerPercentButton;
    private EditText feedInGridPowerPercentEditText;
    private ConstraintLayout feedInGridPowerPercentLayout;
    private Button floatingVoltageButton;
    private EditText floatingVoltageEditText;
    private ConstraintLayout floatingVoltageLayout;
    private EditText forcedChargeEndHour1EditText;
    private EditText forcedChargeEndHour2EditText;
    private EditText forcedChargeEndHourEditText;
    private EditText forcedChargeEndMinute1EditText;
    private EditText forcedChargeEndMinute2EditText;
    private EditText forcedChargeEndMinuteEditText;
    private Button forcedChargeEndTime1Button;
    private ConstraintLayout forcedChargeEndTime1Layout;
    private Button forcedChargeEndTime2Button;
    private ConstraintLayout forcedChargeEndTime2Layout;
    private Button forcedChargeEndTimeButton;
    private ConstraintLayout forcedChargeEndTimeLayout;
    private EditText forcedChargeStartHour1EditText;
    private EditText forcedChargeStartHour2EditText;
    private EditText forcedChargeStartHourEditText;
    private EditText forcedChargeStartMinute1EditText;
    private EditText forcedChargeStartMinute2EditText;
    private EditText forcedChargeStartMinuteEditText;
    private Button forcedChargeStartTime1Button;
    private ConstraintLayout forcedChargeStartTime1Layout;
    private Button forcedChargeStartTime2Button;
    private ConstraintLayout forcedChargeStartTime2Layout;
    private Button forcedChargeStartTimeButton;
    private ConstraintLayout forcedChargeStartTimeLayout;
    private ToggleButton forcedChgFunctionButton;
    private ConstraintLayout forcedChgFunctionLayout;
    private Button forcedChgPowerCmdButton;
    private EditText forcedChgPowerCmdEditText;
    private ConstraintLayout forcedChgPowerCmdLayout;
    private Button forcedChgSocLimitButton;
    private EditText forcedChgSocLimitEditText;
    private ConstraintLayout forcedChgSocLimitLayout;
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
    private Button forcedDisChgPowerPercentCmdButton;
    private EditText forcedDisChgPowerPercentCmdEditText;
    private Button forcedDisChgSocLimitButton;
    private EditText forcedDisChgSocLimitEditText;
    private TextView forcedDischgPowerText;
    private Fragment fragment;
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
    private Button gridVoltMovAvgHighButton;
    private EditText gridVoltMovAvgHighEditText;
    private Inverter inverter;
    private List<Inverter> inverterList;
    private Spinner inverterSpinner;
    private Button leadAcidChargeVoltRefButton;
    private EditText leadAcidChargeVoltRefEditText;
    private ConstraintLayout leadAcidChargeVoltRefLayout;
    private Button leadAcidDischargeCutOffVoltButton;
    private EditText leadAcidDischargeCutOffVoltEditText;
    private ConstraintLayout leadAcidDischargeCutOffVoltLayout;
    private Button masterOrSlaveButton;
    private ConstraintLayout masterOrSlaveLayout;
    private Spinner masterOrSlaveSpinner;
    private Button maxAcInputPowerButton;
    private EditText maxAcInputPowerEditText;
    private ConstraintLayout maxAcInputPowerLayout;
    private ToggleButton microGridFunctionButton;
    private ConstraintLayout microGridFunctionLayout;
    private Button offGridDischargeCutoffSocButton;
    private EditText offGridDischargeCutoffSocEditText;
    private ConstraintLayout offGridDischargeCutoffSocLayout;
    private ToggleButton pvGridOffFunctionButton;
    private ConstraintLayout pvGridOffFunctionLayout;
    private Button pvInputModeButton;
    private ConstraintLayout pvInputModeLayout;
    private Spinner pvInputModeSpinner;
    private Button pvctSampleRatioButton;
    private ConstraintLayout pvctSampleRatioLayout;
    private Spinner pvctSampleRatioSpinner;
    private Button pvctSampleTypeButton;
    private ConstraintLayout pvctSampleTypeLayout;
    private Spinner pvctSampleTypeSpinner;
    private Button readAllButton;
    private Spinner readComposedPhaseSpinner;
    private Button reconnectTimeButton;
    private EditText reconnectTimeEditText;
    private ToggleButton runWithoutGridFunctionButton;
    private ConstraintLayout runWithoutGridFunctionLayout;
    private Button setComAddrButton;
    private Spinner setComposedPhaseSpinner;
    private Button setTimeButton;
    private ToggleButton setToStandbyFunctionButton;
    private ConstraintLayout setToStandbyFunctionLayout;
    private Button startPvVoltButton;
    private EditText startPvVoltEditText;
    private ConstraintLayout startPvVoltLayout;
    private EditText timeDateEditText;
    private ConstraintLayout timeLayout;
    private EditText timeTimeEditText;

    public Lv1RemoteSetFragment() {
        super(41L);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_lv1_remote_set, viewGroup, false);
        this.fragment = this;
        final UserData userData = GlobalInfo.getInstance().getUserData();
        if (!userData.needShowCompanyLogo()) {
            viewInflate.findViewById(R.id.companyLogoImageView).setVisibility(4);
        }
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) (ROLE.VIEWER.equals(userData.getRole()) ? PlantListActivity.class : PlantOverviewActivity.class)));
                MainActivity.instance.finish();
            }
        });
        ((ImageView) viewInflate.findViewById(R.id.userCenterImageView)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) NewUserCenterActivity.class));
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
        button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m645x53b4a47a(view);
            }
        });
        this.timeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_timeLayout);
        EditText editText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeDateEditText);
        this.timeDateEditText = editText;
        editText.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Lv1RemoteSetFragment.this.timeDateEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 10) {
                    Lv1RemoteSetFragment.this.timeDateEditText.setText(InvTool.formatDate(new Date()));
                }
                Lv1RemoteSetFragment.this.getActivity().showDialog(6);
            }
        });
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeTimeEditText);
        this.timeTimeEditText = editText2;
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = Lv1RemoteSetFragment.this.timeTimeEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 5) {
                    Lv1RemoteSetFragment.this.timeTimeEditText.setText(InvTool.formatTime(new Date()).substring(0, 5));
                }
                Lv1RemoteSetFragment.this.getActivity().showDialog(7);
            }
        });
        Button button2 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_timeButton);
        this.setTimeButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                Lv1RemoteSetFragment.this.runNormalRemoteWrite("HOLD_TIME", Lv1RemoteSetFragment.this.timeDateEditText.getText().toString().trim() + " " + Lv1RemoteSetFragment.this.timeTimeEditText.getText().toString().trim() + ":" + String.valueOf(calendar.get(13)), Lv1RemoteSetFragment.this.setTimeButton);
            }
        });
        this.comAddrLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_comAddrLayout);
        this.comAddrEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_comAddrEditText);
        Button button3 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_comAddrButton);
        this.setComAddrButton = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_COM_ADDR", lv1RemoteSetFragment.comAddrEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.setComAddrButton);
            }
        });
        this.pvInputModeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_pvInputModeLayout);
        this.pvInputModeSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_pvInputModeSpinner);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_pv_input_mode_empty)));
        arrayList.add(new Property(String.valueOf(0), getString(R.string.phrase_param_pv_input_mode_0)));
        arrayList.add(new Property(String.valueOf(1), getString(R.string.phrase_param_pv_input_mode_1)));
        arrayList.add(new Property(String.valueOf(2), getString(R.string.phrase_param_pv_input_mode_2)));
        arrayList.add(new Property(String.valueOf(3), getString(R.string.phrase_param_pv_input_mode_3)));
        arrayList.add(new Property(String.valueOf(4), getString(R.string.phrase_param_pv_input_mode_4)));
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.pvInputModeSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        Button button4 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_pvInputModeButton);
        this.pvInputModeButton = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv1RemoteSetFragment.this.pvInputModeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv1RemoteSetFragment.this.runNormalRemoteWrite("HOLD_PV_INPUT_MODE", property.getName(), Lv1RemoteSetFragment.this.pvInputModeButton);
            }
        });
        this.startPvVoltLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_startPvVoltLayout);
        this.startPvVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_startPvVoltEditText);
        Button button5 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_startPvVoltButton);
        this.startPvVoltButton = button5;
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_START_PV_VOLT", lv1RemoteSetFragment.startPvVoltEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.startPvVoltButton);
            }
        });
        this.ctSampleRatioLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioLayout);
        this.ctSampleRatioSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioSpinner);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Property(String.valueOf(-1), getString(R.string.phrase_bit_param_ct_sample_ratio_empty)));
        arrayList2.add(new Property(String.valueOf(0), getString(R.string.phrase_bit_param_ct_sample_ratio_0)));
        arrayList2.add(new Property(String.valueOf(1), getString(R.string.phrase_bit_param_ct_sample_ratio_1)));
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ctSampleRatioSpinner.setAdapter((SpinnerAdapter) arrayAdapter3);
        Button button6 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_ctSampleRatioButton);
        this.ctSampleRatioButton = button6;
        button6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv1RemoteSetFragment.this.ctSampleRatioSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv1RemoteSetFragment.this.runBitRemoteWrite("BIT_CT_SAMPLE_RATIO", property.getName(), Lv1RemoteSetFragment.this.ctSampleRatioButton);
            }
        });
        this.pvctSampleTypeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_pvctSampleTypeLayout);
        this.pvctSampleTypeSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_pvctSampleTypeSpinner);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new Property(String.valueOf(-1), getString(R.string.phrase_bit_param_pvct_sample_type_empty)));
        arrayList3.add(new Property(String.valueOf(0), getString(R.string.phrase_bit_param_pvct_sample_type_0)));
        arrayList3.add(new Property(String.valueOf(1), getString(R.string.phrase_bit_param_pvct_sample_type_1)));
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.pvctSampleTypeSpinner.setAdapter((SpinnerAdapter) arrayAdapter4);
        Button button7 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_pvctSampleTypeButton);
        this.pvctSampleTypeButton = button7;
        button7.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv1RemoteSetFragment.this.pvctSampleTypeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv1RemoteSetFragment.this.runBitRemoteWrite("BIT_PVCT_SAMPLE_TYPE", property.getName(), Lv1RemoteSetFragment.this.pvctSampleTypeButton);
            }
        });
        this.pvctSampleRatioLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_pvctSampleRatioLayout);
        this.pvctSampleRatioSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_pvctSampleRatioSpinner);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(new Property(String.valueOf(-1), getString(R.string.phrase_bit_param_pvct_sample_ratio_empty)));
        arrayList4.add(new Property(String.valueOf(0), getString(R.string.phrase_bit_param_pvct_sample_ratio_0)));
        arrayList4.add(new Property(String.valueOf(1), getString(R.string.phrase_bit_param_pvct_sample_ratio_1)));
        ArrayAdapter arrayAdapter5 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList4);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.pvctSampleRatioSpinner.setAdapter((SpinnerAdapter) arrayAdapter5);
        Button button8 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_pvctSampleRatioButton);
        this.pvctSampleRatioButton = button8;
        button8.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv1RemoteSetFragment.this.pvctSampleRatioSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv1RemoteSetFragment.this.runBitRemoteWrite("BIT_PVCT_SAMPLE_RATIO", property.getName(), Lv1RemoteSetFragment.this.pvctSampleRatioButton);
            }
        });
        this.setToStandbyFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_setToStandbyFunctionLayout);
        ToggleButton toggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_setToStandbyFunctionButton);
        this.setToStandbyFunctionButton = toggleButton;
        toggleButton.setOnClickListener(new AnonymousClass12());
        this.epsFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_epsFunctionLayout);
        ToggleButton toggleButton2 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_epsFunctionButton);
        this.epsFunctionButton = toggleButton2;
        toggleButton2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_EPS_EN", lv1RemoteSetFragment.epsFunctionButton);
            }
        });
        this.runWithoutGridFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_runWithoutGridFunctionLayout);
        ToggleButton toggleButton3 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_runWithoutGridFunctionButton);
        this.runWithoutGridFunctionButton = toggleButton3;
        toggleButton3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_RUN_WITHOUT_GRID", lv1RemoteSetFragment.runWithoutGridFunctionButton);
            }
        });
        this.pvGridOffFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_pvGridOffFunctionLayout);
        ToggleButton toggleButton4 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_pvGridOffFunctionButton);
        this.pvGridOffFunctionButton = toggleButton4;
        toggleButton4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_PV_GRID_OFF_EN", lv1RemoteSetFragment.pvGridOffFunctionButton);
            }
        });
        this.feedInGridFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridFunctionLayout);
        ToggleButton toggleButton5 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridFunctionButton);
        this.feedInGridFunctionButton = toggleButton5;
        toggleButton5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_FEED_IN_GRID_EN", lv1RemoteSetFragment.feedInGridFunctionButton);
            }
        });
        this.feedInGridPowerPercentLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentLayout);
        this.feedInGridPowerPercentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentEditText);
        Button button9 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentButton);
        this.feedInGridPowerPercentButton = button9;
        button9.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_FEED_IN_GRID_POWER_PERCENT", lv1RemoteSetFragment.feedInGridPowerPercentEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.feedInGridPowerPercentButton);
            }
        });
        this.masterOrSlaveLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveLayout);
        this.masterOrSlaveSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveSpinner);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_master_or_slave_empty)));
        arrayList5.add(new Property(String.valueOf(1), getString(R.string.phrase_param_master_or_slave_hybird_1)));
        arrayList5.add(new Property(String.valueOf(2), getString(R.string.phrase_param_master_or_slave_hybird_2)));
        arrayList5.add(new Property(String.valueOf(3), getString(R.string.phrase_param_master_or_slave_hybird_3)));
        ArrayAdapter arrayAdapter6 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList5);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.masterOrSlaveSpinner.setAdapter((SpinnerAdapter) arrayAdapter6);
        Button button10 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveButton);
        this.masterOrSlaveButton = button10;
        button10.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv1RemoteSetFragment.this.masterOrSlaveSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv1RemoteSetFragment.this.runNormalRemoteWrite("HOLD_SET_MASTER_OR_SLAVE", property.getName(), Lv1RemoteSetFragment.this.masterOrSlaveButton);
            }
        });
        this.composedPhaseLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseLayout);
        this.readComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_readComposedPhaseSpinner);
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(new Property(String.valueOf(-1), "--"));
        arrayList6.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList6.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList6.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter7 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList6);
        arrayAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.readComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter7);
        this.readComposedPhaseSpinner.setEnabled(false);
        this.setComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_setComposedPhaseSpinner);
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_composed_phase_empty)));
        arrayList7.add(new Property(String.valueOf(0), getString(R.string.phrase_param_composed_phase_0)));
        arrayList7.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList7.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList7.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter8 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList7);
        arrayAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter8);
        Button button11 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseButton);
        this.composedPhaseButton = button11;
        button11.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) Lv1RemoteSetFragment.this.setComposedPhaseSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                Lv1RemoteSetFragment.this.runNormalRemoteWrite("HOLD_SET_COMPOSED_PHASE", property.getName(), Lv1RemoteSetFragment.this.composedPhaseButton);
            }
        });
        this.microGridFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_microGridFunctionLayout);
        ToggleButton toggleButton6 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_microGridFunctionButton);
        this.microGridFunctionButton = toggleButton6;
        toggleButton6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_MICRO_GRID_EN", lv1RemoteSetFragment.microGridFunctionButton);
            }
        });
        this.batterySharedFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionLayout);
        ToggleButton toggleButton7 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionButton);
        this.batterySharedFunctionButton = toggleButton7;
        toggleButton7.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_BAT_SHARED", lv1RemoteSetFragment.batterySharedFunctionButton);
            }
        });
        this.maxAcInputPowerLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerLayout);
        this.maxAcInputPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerEditText);
        Button button12 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_maxAcInputPowerButton);
        this.maxAcInputPowerButton = button12;
        button12.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_MAX_AC_INPUT_POWER", lv1RemoteSetFragment.maxAcInputPowerEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.maxAcInputPowerButton);
            }
        });
        this.gridVoltConnHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnHighEditText);
        Button button13 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnHighButton);
        this.gridVoltConnHighButton = button13;
        button13.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_CONN_HIGH", lv1RemoteSetFragment.gridVoltConnHighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltConnHighButton);
            }
        });
        this.connectTimeEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_connectTimeEditText);
        Button button14 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_connectTimeButton);
        this.connectTimeButton = button14;
        button14.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_CONNECT_TIME", lv1RemoteSetFragment.connectTimeEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.connectTimeButton);
            }
        });
        this.reconnectTimeEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_reconnectTimeEditText);
        Button button15 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_reconnectTimeButton);
        this.reconnectTimeButton = button15;
        button15.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_RECONNECT_TIME", lv1RemoteSetFragment.reconnectTimeEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.reconnectTimeButton);
            }
        });
        this.gridVoltConnLowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnLowEditText);
        Button button16 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltConnLowButton);
        this.gridVoltConnLowButton = button16;
        button16.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_CONN_LOW", lv1RemoteSetFragment.gridVoltConnLowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltConnLowButton);
            }
        });
        this.gridFreqConnHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnHighEditText);
        Button button17 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnHighButton);
        this.gridFreqConnHighButton = button17;
        button17.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_CONN_HIGH", lv1RemoteSetFragment.gridFreqConnHighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqConnHighButton);
            }
        });
        this.gridFreqConnLowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnLowEditText);
        Button button18 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqConnLowButton);
        this.gridFreqConnLowButton = button18;
        button18.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_CONN_LOW", lv1RemoteSetFragment.gridFreqConnLowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqConnLowButton);
            }
        });
        this.gridVoltLimit1LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1LowEditText);
        Button button19 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1LowButton);
        this.gridVoltLimit1LowButton = button19;
        button19.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT1_LOW", lv1RemoteSetFragment.gridVoltLimit1LowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltLimit1LowButton);
            }
        });
        this.gridVoltLimit1HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1HighEditText);
        Button button20 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit1HighButton);
        this.gridVoltLimit1HighButton = button20;
        button20.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT1_HIGH", lv1RemoteSetFragment.gridVoltLimit1HighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltLimit1HighButton);
            }
        });
        this.gridVoltLimit2LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2LowEditText);
        Button button21 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2LowButton);
        this.gridVoltLimit2LowButton = button21;
        button21.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT2_LOW", lv1RemoteSetFragment.gridVoltLimit2LowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltLimit2LowButton);
            }
        });
        this.gridVoltLimit2HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2HighEditText);
        Button button22 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit2HighButton);
        this.gridVoltLimit2HighButton = button22;
        button22.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT2_HIGH", lv1RemoteSetFragment.gridVoltLimit2HighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltLimit2HighButton);
            }
        });
        this.gridVoltLimit3LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3LowEditText);
        Button button23 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3LowButton);
        this.gridVoltLimit3LowButton = button23;
        button23.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.33
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT3_LOW", lv1RemoteSetFragment.gridVoltLimit3LowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltLimit3LowButton);
            }
        });
        this.gridVoltLimit3HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3HighEditText);
        Button button24 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltLimit3HighButton);
        this.gridVoltLimit3HighButton = button24;
        button24.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_LIMIT3_HIGH", lv1RemoteSetFragment.gridVoltLimit3HighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltLimit3HighButton);
            }
        });
        this.gridVoltMovAvgHighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltMovAvgHighEditText);
        Button button25 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridVoltMovAvgHighButton);
        this.gridVoltMovAvgHighButton = button25;
        button25.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.35
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_VOLT_MOV_AVG_HIGH", lv1RemoteSetFragment.gridVoltMovAvgHighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridVoltMovAvgHighButton);
            }
        });
        this.gridFreqLimit1LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1LowEditText);
        Button button26 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1LowButton);
        this.gridFreqLimit1LowButton = button26;
        button26.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.36
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT1_LOW", lv1RemoteSetFragment.gridFreqLimit1LowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqLimit1LowButton);
            }
        });
        this.gridFreqLimit1HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1HighEditText);
        Button button27 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit1HighButton);
        this.gridFreqLimit1HighButton = button27;
        button27.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.37
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT1_HIGH", lv1RemoteSetFragment.gridFreqLimit1HighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqLimit1HighButton);
            }
        });
        this.gridFreqLimit2LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2LowEditText);
        Button button28 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2LowButton);
        this.gridFreqLimit2LowButton = button28;
        button28.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT2_LOW", lv1RemoteSetFragment.gridFreqLimit2LowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqLimit2LowButton);
            }
        });
        this.gridFreqLimit2HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2HighEditText);
        Button button29 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit2HighButton);
        this.gridFreqLimit2HighButton = button29;
        button29.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.39
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT2_HIGH", lv1RemoteSetFragment.gridFreqLimit2HighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqLimit2HighButton);
            }
        });
        this.gridFreqLimit3LowEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3LowEditText);
        Button button30 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3LowButton);
        this.gridFreqLimit3LowButton = button30;
        button30.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.40
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT3_LOW", lv1RemoteSetFragment.gridFreqLimit3LowEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqLimit3LowButton);
            }
        });
        this.gridFreqLimit3HighEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3HighEditText);
        Button button31 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_gridFreqLimit3HighButton);
        this.gridFreqLimit3HighButton = button31;
        button31.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.41
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_GRID_FREQ_LIMIT3_HIGH", lv1RemoteSetFragment.gridFreqLimit3HighEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.gridFreqLimit3HighButton);
            }
        });
        this.chargePowerPercentCmdLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_chargePowerPercentCmdLayout);
        this.chargePowerPercentCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargePowerPercentCmdEditText);
        Button button32 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargePowerPercentCmdButton);
        this.chargePowerPercentCmdButton = button32;
        button32.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.42
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_CHARGE_POWER_PERCENT_CMD", lv1RemoteSetFragment.chargePowerPercentCmdEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.chargePowerPercentCmdButton);
            }
        });
        this.equalizationVoltageLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_equalizationVoltageLayout);
        this.equalizationVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_equalizationVoltageEditText);
        Button button33 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_equalizationVoltageButton);
        this.equalizationVoltageButton = button33;
        button33.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.43
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_EQUALIZATION_VOLTAGE", lv1RemoteSetFragment.equalizationVoltageEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.equalizationVoltageButton);
            }
        });
        this.equalizationPeriodLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_equalizationPeriodLayout);
        this.equalizationPeriodEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_equalizationPeriodEditText);
        Button button34 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_equalizationPeriodButton);
        this.equalizationPeriodButton = button34;
        button34.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.44
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_EQUALIZATION_PERIOD", lv1RemoteSetFragment.equalizationPeriodEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.equalizationPeriodButton);
            }
        });
        this.equalizationTimeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_equalizationTimeLayout);
        this.equalizationTimeEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_equalizationTimeEditText);
        Button button35 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_equalizationTimeButton);
        this.equalizationTimeButton = button35;
        button35.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.45
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_EQUALIZATION_TIME", lv1RemoteSetFragment.equalizationTimeEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.equalizationTimeButton);
            }
        });
        ToggleButton toggleButton8 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_acChargeFunctionButton);
        this.acChargeFunctionButton = toggleButton8;
        toggleButton8.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.46
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_AC_CHARGE", lv1RemoteSetFragment.acChargeFunctionButton);
            }
        });
        this.acChargePowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargePowerCmdEditText);
        Button button36 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargePowerCmdButton);
        this.acChargePowerCmdButton = button36;
        button36.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.47
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_POWER_CMD", lv1RemoteSetFragment.acChargePowerCmdEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.acChargePowerCmdButton);
            }
        });
        this.acChargeSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeSocLimitEditText);
        Button button37 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeSocLimitButton);
        this.acChargeSocLimitButton = button37;
        button37.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.48
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_SOC_LIMIT", lv1RemoteSetFragment.acChargeSocLimitEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.acChargeSocLimitButton);
            }
        });
        this.acChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHourEditText);
        this.acChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinuteEditText);
        Button button38 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTimeButton);
        this.acChargeStartTimeButton = button38;
        button38.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.49
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME", lv1RemoteSetFragment.acChargeStartHourEditText, Lv1RemoteSetFragment.this.acChargeStartMinuteEditText, Lv1RemoteSetFragment.this.acChargeStartTimeButton);
            }
        });
        this.acChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHourEditText);
        this.acChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinuteEditText);
        Button button39 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTimeButton);
        this.acChargeEndTimeButton = button39;
        button39.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.50
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME", lv1RemoteSetFragment.acChargeEndHourEditText, Lv1RemoteSetFragment.this.acChargeEndMinuteEditText, Lv1RemoteSetFragment.this.acChargeEndTimeButton);
            }
        });
        this.acChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour1EditText);
        this.acChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute1EditText);
        Button button40 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime1Button);
        this.acChargeStartTime1Button = button40;
        button40.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.51
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_1", lv1RemoteSetFragment.acChargeStartHour1EditText, Lv1RemoteSetFragment.this.acChargeStartMinute1EditText, Lv1RemoteSetFragment.this.acChargeStartTime1Button);
            }
        });
        this.acChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour1EditText);
        this.acChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute1EditText);
        Button button41 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime1Button);
        this.acChargeEndTime1Button = button41;
        button41.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.52
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_1", lv1RemoteSetFragment.acChargeEndHour1EditText, Lv1RemoteSetFragment.this.acChargeEndMinute1EditText, Lv1RemoteSetFragment.this.acChargeEndTime1Button);
            }
        });
        this.acChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour2EditText);
        this.acChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute2EditText);
        Button button42 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime2Button);
        this.acChargeStartTime2Button = button42;
        button42.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.53
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_2", lv1RemoteSetFragment.acChargeStartHour2EditText, Lv1RemoteSetFragment.this.acChargeStartMinute2EditText, Lv1RemoteSetFragment.this.acChargeStartTime2Button);
            }
        });
        this.acChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour2EditText);
        this.acChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute2EditText);
        Button button43 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime2Button);
        this.acChargeEndTime2Button = button43;
        button43.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.54
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_2", lv1RemoteSetFragment.acChargeEndHour2EditText, Lv1RemoteSetFragment.this.acChargeEndMinute2EditText, Lv1RemoteSetFragment.this.acChargeEndTime2Button);
            }
        });
        this.forcedChgFunctionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgFunctionLayout);
        ToggleButton toggleButton9 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgFunctionButton);
        this.forcedChgFunctionButton = toggleButton9;
        toggleButton9.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.55
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_FORCED_CHG_EN", lv1RemoteSetFragment.forcedChgFunctionButton);
            }
        });
        this.forcedChgPowerCmdLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdLayout);
        this.forcedChgPowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdEditText);
        Button button44 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgPowerCmdButton);
        this.forcedChgPowerCmdButton = button44;
        button44.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.56
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_CHG_POWER_CMD", lv1RemoteSetFragment.forcedChgPowerCmdEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.forcedChgPowerCmdButton);
            }
        });
        this.forcedChgSocLimitLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitLayout);
        this.forcedChgSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitEditText);
        Button button45 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChgSocLimitButton);
        this.forcedChgSocLimitButton = button45;
        button45.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.57
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_CHG_SOC_LIMIT", lv1RemoteSetFragment.forcedChgSocLimitEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.forcedChgSocLimitButton);
            }
        });
        this.forcedChargeStartTimeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTimeLayout);
        this.forcedChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHourEditText);
        this.forcedChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinuteEditText);
        Button button46 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTimeButton);
        this.forcedChargeStartTimeButton = button46;
        button46.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.58
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME", lv1RemoteSetFragment.forcedChargeStartHourEditText, Lv1RemoteSetFragment.this.forcedChargeStartMinuteEditText, Lv1RemoteSetFragment.this.forcedChargeStartTimeButton);
            }
        });
        this.forcedChargeEndTimeLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTimeLayout);
        this.forcedChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHourEditText);
        this.forcedChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinuteEditText);
        Button button47 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTimeButton);
        this.forcedChargeEndTimeButton = button47;
        button47.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.59
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME", lv1RemoteSetFragment.forcedChargeEndHourEditText, Lv1RemoteSetFragment.this.forcedChargeEndMinuteEditText, Lv1RemoteSetFragment.this.forcedChargeEndTimeButton);
            }
        });
        this.forcedChargeStartTime1Layout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime1Layout);
        this.forcedChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHour1EditText);
        this.forcedChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinute1EditText);
        Button button48 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime1Button);
        this.forcedChargeStartTime1Button = button48;
        button48.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.60
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME_1", lv1RemoteSetFragment.forcedChargeStartHour1EditText, Lv1RemoteSetFragment.this.forcedChargeStartMinute1EditText, Lv1RemoteSetFragment.this.forcedChargeStartTime1Button);
            }
        });
        this.forcedChargeEndTime1Layout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime1Layout);
        this.forcedChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHour1EditText);
        this.forcedChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinute1EditText);
        Button button49 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime1Button);
        this.forcedChargeEndTime1Button = button49;
        button49.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.61
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME_1", lv1RemoteSetFragment.forcedChargeEndHour1EditText, Lv1RemoteSetFragment.this.forcedChargeEndMinute1EditText, Lv1RemoteSetFragment.this.forcedChargeEndTime1Button);
            }
        });
        this.forcedChargeStartTime2Layout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime2Layout);
        this.forcedChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartHour2EditText);
        this.forcedChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartMinute2EditText);
        Button button50 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeStartTime2Button);
        this.forcedChargeStartTime2Button = button50;
        button50.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.62
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_START_TIME_2", lv1RemoteSetFragment.forcedChargeStartHour2EditText, Lv1RemoteSetFragment.this.forcedChargeStartMinute2EditText, Lv1RemoteSetFragment.this.forcedChargeStartTime2Button);
            }
        });
        this.forcedChargeEndTime2Layout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime2Layout);
        this.forcedChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndHour2EditText);
        this.forcedChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndMinute2EditText);
        Button button51 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedChargeEndTime2Button);
        this.forcedChargeEndTime2Button = button51;
        button51.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.63
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_CHARGE_END_TIME_2", lv1RemoteSetFragment.forcedChargeEndHour2EditText, Lv1RemoteSetFragment.this.forcedChargeEndMinute2EditText, Lv1RemoteSetFragment.this.forcedChargeEndTime2Button);
            }
        });
        this.leadAcidChargeVoltRefLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefLayout);
        this.leadAcidChargeVoltRefEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefEditText);
        Button button52 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefButton);
        this.leadAcidChargeVoltRefButton = button52;
        button52.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.64
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_VOLT_REF", lv1RemoteSetFragment.leadAcidChargeVoltRefEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.leadAcidChargeVoltRefButton);
            }
        });
        this.floatingVoltageLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageLayout);
        this.floatingVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageEditText);
        Button button53 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageButton);
        this.floatingVoltageButton = button53;
        button53.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.65
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_FLOATING_VOLTAGE", lv1RemoteSetFragment.floatingVoltageEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.floatingVoltageButton);
            }
        });
        this.chargeCurrentLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentLayout);
        this.chargeCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentEditText);
        Button button54 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentButton);
        this.chargeCurrentButton = button54;
        button54.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.66
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_RATE", lv1RemoteSetFragment.chargeCurrentEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.chargeCurrentButton);
            }
        });
        this.forcedDisChgPowerPercentCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerPercentCmdEditText);
        Button button55 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerPercentCmdButton);
        this.forcedDisChgPowerPercentCmdButton = button55;
        button55.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.67
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_DISCHG_POWER_PERCENT_CMD", lv1RemoteSetFragment.forcedDisChgPowerPercentCmdEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.forcedDisChgPowerPercentCmdButton);
            }
        });
        ToggleButton toggleButton10 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgFunctionButton);
        this.forcedDisChgFunctionButton = toggleButton10;
        toggleButton10.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.68
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runControlRemoteWrite("FUNC_FORCED_DISCHG_EN", lv1RemoteSetFragment.forcedDisChgFunctionButton);
            }
        });
        this.forcedDischgPowerText = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_forced_dischg_power_text);
        this.forcedDisChgPowerCmdEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerCmdEditText);
        Button button56 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgPowerCmdButton);
        this.forcedDisChgPowerCmdButton = button56;
        button56.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.69
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_DISCHG_POWER_CMD", lv1RemoteSetFragment.forcedDisChgPowerCmdEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.forcedDisChgPowerCmdButton);
            }
        });
        this.forcedDisChgSocLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgSocLimitEditText);
        Button button57 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChgSocLimitButton);
        this.forcedDisChgSocLimitButton = button57;
        button57.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.70
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_FORCED_DISCHG_SOC_LIMIT", lv1RemoteSetFragment.forcedDisChgSocLimitEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.forcedDisChgSocLimitButton);
            }
        });
        this.forcedDisChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHourEditText);
        this.forcedDisChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinuteEditText);
        Button button58 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTimeButton);
        this.forcedDisChargeStartTimeButton = button58;
        button58.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.71
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME", lv1RemoteSetFragment.forcedDisChargeStartHourEditText, Lv1RemoteSetFragment.this.forcedDisChargeStartMinuteEditText, Lv1RemoteSetFragment.this.forcedDisChargeStartTimeButton);
            }
        });
        this.forcedDisChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHourEditText);
        this.forcedDisChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinuteEditText);
        Button button59 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTimeButton);
        this.forcedDisChargeEndTimeButton = button59;
        button59.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.72
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME", lv1RemoteSetFragment.forcedDisChargeEndHourEditText, Lv1RemoteSetFragment.this.forcedDisChargeEndMinuteEditText, Lv1RemoteSetFragment.this.forcedDisChargeEndTimeButton);
            }
        });
        this.forcedDisChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHour1EditText);
        this.forcedDisChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinute1EditText);
        Button button60 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTime1Button);
        this.forcedDisChargeStartTime1Button = button60;
        button60.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.73
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME_1", lv1RemoteSetFragment.forcedDisChargeStartHour1EditText, Lv1RemoteSetFragment.this.forcedDisChargeStartMinute1EditText, Lv1RemoteSetFragment.this.forcedDisChargeStartTime1Button);
            }
        });
        this.forcedDisChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHour1EditText);
        this.forcedDisChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinute1EditText);
        Button button61 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTime1Button);
        this.forcedDisChargeEndTime1Button = button61;
        button61.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.74
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME_1", lv1RemoteSetFragment.forcedDisChargeEndHour1EditText, Lv1RemoteSetFragment.this.forcedDisChargeEndMinute1EditText, Lv1RemoteSetFragment.this.forcedDisChargeEndTime1Button);
            }
        });
        this.forcedDisChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartHour2EditText);
        this.forcedDisChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartMinute2EditText);
        Button button62 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeStartTime2Button);
        this.forcedDisChargeStartTime2Button = button62;
        button62.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.75
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_START_TIME_2", lv1RemoteSetFragment.forcedDisChargeStartHour2EditText, Lv1RemoteSetFragment.this.forcedDisChargeStartMinute2EditText, Lv1RemoteSetFragment.this.forcedDisChargeStartTime2Button);
            }
        });
        this.forcedDisChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndHour2EditText);
        this.forcedDisChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndMinute2EditText);
        Button button63 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_forcedDisChargeEndTime2Button);
        this.forcedDisChargeEndTime2Button = button63;
        button63.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.76
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runTimeRemoteWrite("HOLD_FORCED_DISCHARGE_END_TIME_2", lv1RemoteSetFragment.forcedDisChargeEndHour2EditText, Lv1RemoteSetFragment.this.forcedDisChargeEndMinute2EditText, Lv1RemoteSetFragment.this.forcedDisChargeEndTime2Button);
            }
        });
        this.leadAcidDischargeCutOffVoltLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltLayout);
        this.leadAcidDischargeCutOffVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltEditText);
        Button button64 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltButton);
        this.leadAcidDischargeCutOffVoltButton = button64;
        button64.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.77
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT", lv1RemoteSetFragment.leadAcidDischargeCutOffVoltEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.leadAcidDischargeCutOffVoltButton);
            }
        });
        this.eodLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_eodLayout);
        this.eodEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_eodEditText);
        Button button65 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_eodButton);
        this.eodButton = button65;
        button65.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.78
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_DISCHG_CUT_OFF_SOC_EOD", lv1RemoteSetFragment.eodEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.eodButton);
            }
        });
        this.offGridDischargeCutoffSocLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocLayout);
        this.offGridDischargeCutoffSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocEditText);
        Button button66 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_offGridDischargeCutoffSocButton);
        this.offGridDischargeCutoffSocButton = button66;
        button66.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.79
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_SOC_LOW_LIMIT_EPS_DISCHG", lv1RemoteSetFragment.offGridDischargeCutoffSocEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.offGridDischargeCutoffSocButton);
            }
        });
        this.dischargeCurrentLimitLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_dischargeCurrentLimitLayout);
        this.dischargeCurrentLimitEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_dischargeCurrentLimitEditText);
        Button button67 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_dischargeCurrentLimitButton);
        this.dischargeCurrentLimitButton = button67;
        button67.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.80
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
                lv1RemoteSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_RATE", lv1RemoteSetFragment.dischargeCurrentLimitEditText.getText().toString().trim(), Lv1RemoteSetFragment.this.dischargeCurrentLimitButton);
            }
        });
        this.applicationSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_titleLayout);
        this.applicationSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_layout);
        this.applicationSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_textView);
        this.applicationSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_toggleButton);
        this.applicationSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_paramLayout);
        this.applicationSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.81
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.applicationSetParamLayout.setVisibility(0);
                } else {
                    Lv1RemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.applicationSetParamLayout.setVisibility(8);
                }
            }
        });
        this.applicationSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.82
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.applicationSetActionToggleButton.setChecked(false);
                    Lv1RemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.applicationSetParamLayout.setVisibility(8);
                } else {
                    Lv1RemoteSetFragment.this.applicationSetActionToggleButton.setChecked(true);
                    Lv1RemoteSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.applicationSetParamLayout.setVisibility(0);
                }
            }
        });
        this.gridConnectSetTitleLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_titleLayout);
        this.gridConnectSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_layout);
        this.gridConnectSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_textView);
        this.gridConnectSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_toggleButton);
        this.gridConnectSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_grid_connect_set_paramLayout);
        this.gridConnectSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.83
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.gridConnectSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(0);
                } else {
                    Lv1RemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(8);
                }
            }
        });
        this.gridConnectSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.84
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.gridConnectSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.gridConnectSetActionToggleButton.setChecked(false);
                    Lv1RemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(8);
                } else {
                    Lv1RemoteSetFragment.this.gridConnectSetActionToggleButton.setChecked(true);
                    Lv1RemoteSetFragment.this.gridConnectSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.gridConnectSetParamLayout.setVisibility(0);
                }
            }
        });
        this.chargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_layout);
        this.chargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_textView);
        this.chargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_toggleButton);
        this.chargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_paramLayout);
        this.chargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.85
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.chargeSetParamLayout.setVisibility(0);
                } else {
                    Lv1RemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.chargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.chargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.86
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.chargeSetActionToggleButton.setChecked(false);
                    Lv1RemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.chargeSetParamLayout.setVisibility(8);
                } else {
                    Lv1RemoteSetFragment.this.chargeSetActionToggleButton.setChecked(true);
                    Lv1RemoteSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.chargeSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_layout);
        this.dischargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_textView);
        this.dischargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_toggleButton);
        this.dischargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_paramLayout);
        this.dischargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.87
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                } else {
                    Lv1RemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.88
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Lv1RemoteSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    Lv1RemoteSetFragment.this.dischargeSetActionToggleButton.setChecked(false);
                    Lv1RemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    Lv1RemoteSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                } else {
                    Lv1RemoteSetFragment.this.dischargeSetActionToggleButton.setChecked(true);
                    Lv1RemoteSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    Lv1RemoteSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                }
            }
        });
        if (userData.isInstallerLevel() || ROLE.VIEWER.equals(userData.getRole())) {
            this.timeLayout.setVisibility(8);
            this.comAddrLayout.setVisibility(8);
            this.pvInputModeLayout.setVisibility(8);
            this.startPvVoltLayout.setVisibility(8);
            this.pvGridOffFunctionLayout.setVisibility(8);
            this.gridConnectSetTitleLayout.setVisibility(8);
            this.gridConnectSetParamLayout.setVisibility(8);
        }
        if (ROLE.VIEWER.equals(userData.getRole())) {
            this.ctSampleRatioLayout.setVisibility(8);
            this.pvctSampleTypeLayout.setVisibility(8);
            this.pvctSampleRatioLayout.setVisibility(8);
            this.setToStandbyFunctionLayout.setVisibility(8);
            this.masterOrSlaveLayout.setVisibility(8);
            this.composedPhaseLayout.setVisibility(8);
            this.microGridFunctionLayout.setVisibility(8);
            this.batterySharedFunctionLayout.setVisibility(8);
            this.maxAcInputPowerLayout.setVisibility(8);
            this.equalizationVoltageLayout.setVisibility(8);
            this.equalizationPeriodLayout.setVisibility(8);
            this.equalizationTimeLayout.setVisibility(8);
            if (userData.isGigabiz1User()) {
                this.runWithoutGridFunctionLayout.setVisibility(8);
                this.feedInGridFunctionLayout.setVisibility(8);
                this.feedInGridPowerPercentLayout.setVisibility(8);
            }
        }
        this.created = true;
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1RemoteSetFragment, reason: not valid java name */
    /* synthetic */ void m645x53b4a47a(View view) {
        if (this.inverter != null) {
            RemoteSetCacheManager.getInstance().clearCache(this.inverter.getSerialNum());
            clearFragmentData();
            this.readAllButton.setEnabled(false);
            if (this.inverter.supportRead127Register()) {
                new ReadMultiParamTask(this).execute(new RemoteReadInfo(this.inverter.getSerialNum(), 0, 127), new RemoteReadInfo(this.inverter.getSerialNum(), 127, 127));
            } else {
                new ReadMultiParamTask(this).execute(new RemoteReadInfo(this.inverter.getSerialNum(), 0, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 40, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 80, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 120, 40), new RemoteReadInfo(this.inverter.getSerialNum(), 160, 20));
            }
        }
    }

    /* renamed from: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment$12, reason: invalid class name */
    class AnonymousClass12 implements View.OnClickListener {
        AnonymousClass12() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (Lv1RemoteSetFragment.this.inverter != null) {
                boolean zIsChecked = Lv1RemoteSetFragment.this.setToStandbyFunctionButton.isChecked();
                AlertDialog.Builder builder = new AlertDialog.Builder(Lv1RemoteSetFragment.this.fragment.getActivity());
                builder.setTitle(zIsChecked ? R.string.phrase_func_param_normaly : R.string.phrase_func_param_standby).setIcon(android.R.drawable.ic_dialog_info).setMessage(Lv1RemoteSetFragment.this.getString(zIsChecked ? R.string.phrase_func_text_normal : R.string.phrase_func_text_standby) + " " + Lv1RemoteSetFragment.this.inverter.getSerialNum()).setPositiveButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment$12$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.m646x94b6931a(dialogInterface, i);
                    }
                }).setNegativeButton(R.string.phrase_button_cancel, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment$12$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.m647xe2760b1b(dialogInterface, i);
                    }
                });
                builder.show();
                return;
            }
            Lv1RemoteSetFragment.this.setToStandbyFunctionButton.setChecked(!Lv1RemoteSetFragment.this.setToStandbyFunctionButton.isChecked());
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-main-fragment-lv1-Lv1RemoteSetFragment$12, reason: not valid java name */
        /* synthetic */ void m646x94b6931a(DialogInterface dialogInterface, int i) {
            Lv1RemoteSetFragment lv1RemoteSetFragment = Lv1RemoteSetFragment.this;
            lv1RemoteSetFragment.runControlRemoteWrite("FUNC_SET_TO_STANDBY", lv1RemoteSetFragment.setToStandbyFunctionButton);
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-main-fragment-lv1-Lv1RemoteSetFragment$12, reason: not valid java name */
        /* synthetic */ void m647xe2760b1b(DialogInterface dialogInterface, int i) {
            Lv1RemoteSetFragment.this.setToStandbyFunctionButton.setChecked(!Lv1RemoteSetFragment.this.setToStandbyFunctionButton.isChecked());
        }
    }

    private void initInverterSpinnerOnItemSelectedListener() {
        if (this.inverterSpinner.getOnItemSelectedListener() == null) {
            System.out.println("LuxPower - Lv1RemoteSetFragment initInverterSpinnerOnItemSelectedListener...");
            this.inverterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.89
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    Lv1RemoteSetFragment.this.updateSelectInverter((Inverter) Lv1RemoteSetFragment.this.inverterSpinner.getSelectedItem());
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                    if (Lv1RemoteSetFragment.this.inverter != null) {
                        Lv1RemoteSetFragment.this.inverter = null;
                        GlobalInfo.getInstance().getUserData().setCurrentInverter(Lv1RemoteSetFragment.this.inverter, true);
                        Lv1RemoteSetFragment.this.clearFragmentData();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectInverter(Inverter inverter) {
        System.out.println("LuxPower - Lv1RemoteSetFragment selectInverter = " + inverter.getSerialNum() + ", inverter = " + this.inverter);
        Inverter inverter2 = this.inverter;
        if (inverter2 == null || !inverter2.getSerialNum().equals(inverter.getSerialNum())) {
            this.inverter = inverter;
            GlobalInfo.getInstance().getUserData().setCurrentInverter(this.inverter, true);
            if (this.inverter.isSnaSeries() || this.inverter.isType6()) {
                MainActivity mainActivity = (MainActivity) this.fragment.getActivity();
                if (mainActivity != null) {
                    mainActivity.switchRemoteSetFragment(this.inverter.getDeviceTypeValue());
                    return;
                }
                return;
            }
            initFlowChartByDeviceType();
            clearFragmentData();
            fillDataFromCache();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        System.out.println("LuxPower - Lv1RemoteSetFragment onResume...");
        initInverterSpinnerOnItemSelectedListener();
        refreshFragmentParams();
    }

    private void initFlowChartByDeviceType() {
        Inverter inverter = this.inverter;
        if (inverter == null || inverter.getDeviceType() == null) {
            return;
        }
        if (this.inverter.getDeviceType().intValue() == 2) {
            this.pvInputModeLayout.setVisibility(8);
            this.startPvVoltLayout.setVisibility(8);
            this.pvGridOffFunctionLayout.setVisibility(8);
        } else {
            if (this.inverter.isGen3_6K()) {
                this.forcedDischgPowerText.setText(R.string.phrase_param_forced_dischg_power_cmd_12k);
                return;
            }
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.isInstallerLevel() || ROLE.VIEWER.equals(userData.getRole())) {
                return;
            }
            this.pvInputModeLayout.setVisibility(0);
            this.startPvVoltLayout.setVisibility(0);
            this.pvGridOffFunctionLayout.setVisibility(0);
        }
    }

    public void refreshFragmentParams() {
        Inverter inverter;
        if (this.created) {
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.getCurrentInverter() == null || this.inverterList == null) {
                return;
            }
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
    public void runControlRemoteWrite(String str, ToggleButton toggleButton) {
        if (this.inverter != null) {
            RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
            remoteWriteInfo.setSerialNum(this.inverter.getSerialNum());
            remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
            remoteWriteInfo.setFunctionParam(str);
            remoteWriteInfo.setFunctionToggleButtonChecked(toggleButton.isChecked());
            remoteWriteInfo.setFunctionToggleButton(toggleButton);
            new WriteParamTask(this).execute(remoteWriteInfo);
        }
    }

    public void clearFragmentData() {
        if (this.created) {
            this.timeDateEditText.setText("");
            this.timeTimeEditText.setText("");
            this.comAddrEditText.setText("");
            this.pvInputModeSpinner.setSelection(0);
            this.startPvVoltEditText.setText("");
            this.floatingVoltageLayout.setVisibility(8);
            this.leadAcidChargeVoltRefLayout.setVisibility(8);
            this.leadAcidDischargeCutOffVoltLayout.setVisibility(8);
            this.ctSampleRatioSpinner.setSelection(0);
            this.pvctSampleTypeSpinner.setSelection(0);
            this.pvctSampleRatioSpinner.setSelection(0);
            this.setToStandbyFunctionButton.setChecked(false);
            this.epsFunctionButton.setChecked(false);
            this.runWithoutGridFunctionButton.setChecked(false);
            this.pvGridOffFunctionButton.setChecked(false);
            this.feedInGridFunctionButton.setChecked(false);
            this.feedInGridPowerPercentEditText.setText("");
            this.masterOrSlaveSpinner.setSelection(0);
            this.readComposedPhaseSpinner.setSelection(0);
            this.setComposedPhaseSpinner.setSelection(0);
            this.microGridFunctionButton.setChecked(false);
            this.batterySharedFunctionButton.setChecked(false);
            this.maxAcInputPowerEditText.setText("");
            this.connectTimeEditText.setText("");
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
            this.gridVoltMovAvgHighEditText.setText("");
            this.gridFreqLimit1LowEditText.setText("");
            this.gridFreqLimit1HighEditText.setText("");
            this.gridFreqLimit2LowEditText.setText("");
            this.gridFreqLimit2HighEditText.setText("");
            this.gridFreqLimit3LowEditText.setText("");
            this.gridFreqLimit3HighEditText.setText("");
            this.chargePowerPercentCmdEditText.setText("");
            this.equalizationVoltageEditText.setText("");
            this.equalizationPeriodEditText.setText("");
            this.equalizationTimeEditText.setText("");
            this.acChargeFunctionButton.setChecked(false);
            this.acChargePowerCmdEditText.setText("");
            this.acChargeSocLimitEditText.setText("");
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
            this.forcedDisChgPowerPercentCmdEditText.setText("");
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
            this.leadAcidDischargeCutOffVoltEditText.setText("");
            this.eodEditText.setText("");
            this.offGridDischargeCutoffSocEditText.setText("");
            this.dischargeCurrentLimitEditText.setText("");
        }
    }

    public void fillDataFromCache() {
        if (this.created) {
            UserData userData = GlobalInfo.getInstance().getUserData();
            if (userData.getCurrentInverter() != null) {
                JSONObject cache = RemoteSetCacheManager.getInstance().getCache(userData.getCurrentInverter().getSerialNum());
                System.out.println("LuxPowerremote set result from cache = " + cache);
                if (cache != null) {
                    try {
                        analyzeResultToFragment(cache);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Button button = this.readAllButton;
            if (button != null) {
                button.performClick();
            }
        }
    }

    private static class ReadMultiParamTask extends AsyncTask<RemoteReadInfo, JSONObject, Void> {
        private Lv1RemoteSetFragment fragment;

        public ReadMultiParamTask(Lv1RemoteSetFragment lv1RemoteSetFragment) {
            this.fragment = lv1RemoteSetFragment;
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
                        Lv1RemoteSetFragment lv1RemoteSetFragment = this.fragment;
                        if (lv1RemoteSetFragment != null && lv1RemoteSetFragment.inverter != null) {
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
        if (jSONObject.has("HOLD_MODEL_batteryType")) {
            int i = jSONObject.getInt("HOLD_MODEL_batteryType") == 1 ? 0 : 8;
            this.floatingVoltageLayout.setVisibility(i);
            this.leadAcidChargeVoltRefLayout.setVisibility(i);
            this.leadAcidDischargeCutOffVoltLayout.setVisibility(i);
        }
        if (jSONObject.has("HOLD_TIME")) {
            String string = jSONObject.getString("HOLD_TIME");
            this.timeDateEditText.setText(string.substring(0, 10));
            this.timeTimeEditText.setText(string.substring(11, 16));
        }
        if (jSONObject.has("HOLD_COM_ADDR")) {
            this.comAddrEditText.setText(jSONObject.getString("HOLD_COM_ADDR"));
        }
        if (jSONObject.has("HOLD_PV_INPUT_MODE")) {
            int i2 = Integer.parseInt(jSONObject.getString("HOLD_PV_INPUT_MODE"));
            if (i2 >= 0 && i2 <= this.pvInputModeSpinner.getCount() - 2) {
                this.pvInputModeSpinner.setSelection(i2 + 1);
            } else {
                this.pvInputModeSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("HOLD_START_PV_VOLT")) {
            this.startPvVoltEditText.setText(jSONObject.getString("HOLD_START_PV_VOLT"));
        }
        if (jSONObject.has("BIT_CT_SAMPLE_RATIO")) {
            int i3 = Integer.parseInt(jSONObject.getString("BIT_CT_SAMPLE_RATIO"));
            if (i3 >= 0 && i3 <= 1) {
                this.ctSampleRatioSpinner.setSelection(i3 + 1);
            } else {
                this.ctSampleRatioSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("BIT_PVCT_SAMPLE_TYPE")) {
            int i4 = Integer.parseInt(jSONObject.getString("BIT_PVCT_SAMPLE_TYPE"));
            if (i4 >= 0 && i4 <= 1) {
                this.pvctSampleTypeSpinner.setSelection(i4 + 1);
            } else {
                this.pvctSampleTypeSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("BIT_PVCT_SAMPLE_RATIO")) {
            int i5 = Integer.parseInt(jSONObject.getString("BIT_PVCT_SAMPLE_RATIO"));
            if (i5 >= 0 && i5 <= 1) {
                this.pvctSampleRatioSpinner.setSelection(i5 + 1);
            } else {
                this.pvctSampleRatioSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("FUNC_SET_TO_STANDBY")) {
            this.setToStandbyFunctionButton.setChecked(jSONObject.getBoolean("FUNC_SET_TO_STANDBY"));
        }
        if (jSONObject.has("FUNC_EPS_EN")) {
            this.epsFunctionButton.setChecked(jSONObject.getBoolean("FUNC_EPS_EN"));
        }
        if (jSONObject.has("FUNC_RUN_WITHOUT_GRID")) {
            this.runWithoutGridFunctionButton.setChecked(jSONObject.getBoolean("FUNC_RUN_WITHOUT_GRID"));
        }
        if (jSONObject.has("FUNC_PV_GRID_OFF_EN")) {
            this.pvGridOffFunctionButton.setChecked(jSONObject.getBoolean("FUNC_PV_GRID_OFF_EN"));
        }
        if (jSONObject.has("FUNC_FEED_IN_GRID_EN")) {
            this.feedInGridFunctionButton.setChecked(jSONObject.getBoolean("FUNC_FEED_IN_GRID_EN"));
        }
        if (jSONObject.has("HOLD_FEED_IN_GRID_POWER_PERCENT")) {
            this.feedInGridPowerPercentEditText.setText(jSONObject.getString("HOLD_FEED_IN_GRID_POWER_PERCENT"));
        }
        if (jSONObject.has("HOLD_SET_MASTER_OR_SLAVE")) {
            int i6 = Integer.parseInt(jSONObject.getString("HOLD_SET_MASTER_OR_SLAVE"));
            if (i6 >= 0 && i6 <= 3) {
                this.masterOrSlaveSpinner.setSelection(i6);
            } else {
                this.masterOrSlaveSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("HOLD_SET_COMPOSED_PHASE")) {
            int i7 = Integer.parseInt(jSONObject.getString("HOLD_SET_COMPOSED_PHASE"));
            int i8 = i7 >> 8;
            if (i8 >= 1 && i8 <= 3) {
                this.readComposedPhaseSpinner.setSelection(i8);
            } else {
                this.readComposedPhaseSpinner.setSelection(0);
            }
            int i9 = i7 & 255;
            if (i9 >= 0 && i9 <= 3) {
                this.setComposedPhaseSpinner.setSelection(i9 + 1);
            } else {
                this.setComposedPhaseSpinner.setSelection(0);
            }
        }
        if (jSONObject.has("FUNC_MICRO_GRID_EN")) {
            this.microGridFunctionButton.setChecked(jSONObject.getBoolean("FUNC_MICRO_GRID_EN"));
        }
        if (jSONObject.has("FUNC_BAT_SHARED")) {
            this.batterySharedFunctionButton.setChecked(jSONObject.getBoolean("FUNC_BAT_SHARED"));
        }
        if (jSONObject.has("HOLD_MAX_AC_INPUT_POWER")) {
            this.maxAcInputPowerEditText.setText(jSONObject.getString("HOLD_MAX_AC_INPUT_POWER"));
        }
        if (jSONObject.has("HOLD_CONNECT_TIME")) {
            this.connectTimeEditText.setText(jSONObject.getString("HOLD_CONNECT_TIME"));
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
        if (jSONObject.has("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
            this.gridVoltMovAvgHighEditText.setText(jSONObject.getString("HOLD_GRID_VOLT_MOV_AVG_HIGH"));
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
        if (jSONObject.has("HOLD_CHARGE_POWER_PERCENT_CMD")) {
            this.chargePowerPercentCmdEditText.setText(jSONObject.getString("HOLD_CHARGE_POWER_PERCENT_CMD"));
        }
        if (jSONObject.has("HOLD_EQUALIZATION_VOLTAGE")) {
            this.equalizationVoltageEditText.setText(jSONObject.getString("HOLD_EQUALIZATION_VOLTAGE"));
        }
        if (jSONObject.has("HOLD_EQUALIZATION_PERIOD")) {
            this.equalizationPeriodEditText.setText(jSONObject.getString("HOLD_EQUALIZATION_PERIOD"));
        }
        if (jSONObject.has("HOLD_EQUALIZATION_TIME")) {
            this.equalizationTimeEditText.setText(jSONObject.getString("HOLD_EQUALIZATION_TIME"));
        }
        if (jSONObject.has("FUNC_AC_CHARGE")) {
            this.acChargeFunctionButton.setChecked(jSONObject.getBoolean("FUNC_AC_CHARGE"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_POWER_CMD")) {
            this.acChargePowerCmdEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_POWER_CMD"));
        }
        if (jSONObject.has("HOLD_AC_CHARGE_SOC_LIMIT")) {
            this.acChargeSocLimitEditText.setText(jSONObject.getString("HOLD_AC_CHARGE_SOC_LIMIT"));
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
        if (jSONObject.has("HOLD_LEAD_ACID_CHARGE_RATE")) {
            this.chargeCurrentEditText.setText(jSONObject.getString("HOLD_LEAD_ACID_CHARGE_RATE"));
        }
        if (jSONObject.has("HOLD_DISCHG_POWER_PERCENT_CMD")) {
            this.forcedDisChgPowerPercentCmdEditText.setText(jSONObject.getString("HOLD_DISCHG_POWER_PERCENT_CMD"));
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
            this.eodEditText.setText(jSONObject.getString("HOLD_DISCHG_CUT_OFF_SOC_EOD"));
        }
        if (jSONObject.has("HOLD_SOC_LOW_LIMIT_EPS_DISCHG")) {
            this.offGridDischargeCutoffSocEditText.setText(jSONObject.getString("HOLD_SOC_LOW_LIMIT_EPS_DISCHG"));
        }
        if (jSONObject.has("HOLD_LEAD_ACID_DISCHARGE_RATE")) {
            this.dischargeCurrentLimitEditText.setText(jSONObject.getString("HOLD_LEAD_ACID_DISCHARGE_RATE"));
        }
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private Lv1RemoteSetFragment fragment;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(Lv1RemoteSetFragment lv1RemoteSetFragment) {
            this.fragment = lv1RemoteSetFragment;
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
                int i = AnonymousClass90.$SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()];
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
                if (i == 4) {
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
        /* JADX WARN: Removed duplicated region for block: B:7:0x002c A[Catch: all -> 0x0061, Exception -> 0x0063, TryCatch #2 {Exception -> 0x0063, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x002c, B:9:0x0030, B:11:0x003e, B:15:0x0053, B:16:0x0056), top: B:33:0x0006, outer: #0 }] */
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
                if (r4 == 0) goto L2c
                java.lang.String r1 = "success"
                boolean r1 = r4.getBoolean(r1)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                if (r1 == 0) goto L2c
                com.lux.luxcloud.global.cache.RemoteSetCacheManager r4 = com.lux.luxcloud.global.cache.RemoteSetCacheManager.getInstance()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment r1 = r3.fragment     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.global.bean.inverter.Inverter r1 = com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.access$1700(r1)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                java.lang.String r1 = r1.getSerialNum()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                r4.clearCache(r1)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                r1 = 2131886561(0x7f1201e1, float:1.9407704E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                goto L5b
            L2c:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                if (r1 == 0) goto L56
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r1 = com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r2 = r2.getRemoteWriteType()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                if (r1 == 0) goto L56
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                android.widget.ToggleButton r2 = r2.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                boolean r2 = r2.isChecked()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                if (r2 != 0) goto L52
                r2 = r0
                goto L53
            L52:
                r2 = 0
            L53:
                r1.setChecked(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
            L56:
                com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment r1 = r3.fragment     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
                com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.access$18200(r1, r4)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L63
            L5b:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r4 = r3.remoteWriteInfo
                r4.setEnabled(r0)
                goto L79
            L61:
                r4 = move-exception
                goto L7a
            L63:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L61
                com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L74
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L74
                r1 = 2131886563(0x7f1201e3, float:1.9407708E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L74
                goto L5b
            L74:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L61
                goto L5b
            L79:
                return
            L7a:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment$90, reason: invalid class name */
    static /* synthetic */ class AnonymousClass90 {
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
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.TIME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.CONTROL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
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
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment.toast(org.json.JSONObject):void");
    }

    public EditText getTimeDateEditText() {
        return this.timeDateEditText;
    }

    public EditText getTimeTimeEditText() {
        return this.timeTimeEditText;
    }
}