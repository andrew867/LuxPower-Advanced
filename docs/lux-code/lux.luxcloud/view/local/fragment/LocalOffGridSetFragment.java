package com.lux.luxcloud.view.local.fragment;

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
import com.alibaba.fastjson2.JSONB;
import com.alibaba.fastjson2.internal.asm.Opcodes;
import com.google.common.base.Ascii;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnect;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.property.Property;
import com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE;
import com.lux.luxcloud.global.bean.set.RemoteReadInfo;
import com.lux.luxcloud.global.bean.set.RemoteWriteInfo;
import com.lux.luxcloud.protocol.tcp.DataFrameFactory;
import com.lux.luxcloud.tool.InvTool;
import com.lux.luxcloud.tool.PinTool;
import com.lux.luxcloud.tool.ProTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.local.LocalActivity;
import com.lux.luxcloud.view.login.LoginActivity;
import com.lux.luxcloud.view.main.fragment.lv1.AbstractItemFragment;
import com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import kotlin.text.Typography;
import org.bouncycastle.i18n.LocalizedMessage;
import org.bouncycastle.pqc.legacy.math.linearalgebra.Matrix;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LocalOffGridSetFragment extends AbstractItemFragment {
    private String[] _110Functions;
    private String[] _21Functions;
    private Button acChargeEndBatterySocButton;
    private EditText acChargeEndBatterySocEditText;
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
    private Button acChargeStartBatterySocButton;
    private EditText acChargeStartBatterySocEditText;
    private Button acChargeStartBatteryVoltageButton;
    private EditText acChargeStartBatteryVoltageEditText;
    private EditText acChargeStartHour1EditText;
    private EditText acChargeStartHour2EditText;
    private EditText acChargeStartHourEditText;
    private EditText acChargeStartMinute1EditText;
    private EditText acChargeStartMinute2EditText;
    private EditText acChargeStartMinuteEditText;
    private Button acChargeStartTime1Button;
    private Button acChargeStartTime2Button;
    private Button acChargeStartTimeButton;
    private Button acChargeTypeButton;
    private Spinner acChargeTypeSpinner;
    private Button acChgBatCurrentButton;
    private EditText acChgBatCurrentEditText;
    private EditText acFirstEndHour1EditText;
    private EditText acFirstEndHour2EditText;
    private EditText acFirstEndHourEditText;
    private EditText acFirstEndMinute1EditText;
    private EditText acFirstEndMinute2EditText;
    private EditText acFirstEndMinuteEditText;
    private Button acFirstEndTime1Button;
    private Button acFirstEndTime2Button;
    private Button acFirstEndTimeButton;
    private EditText acFirstStartHour1EditText;
    private EditText acFirstStartHour2EditText;
    private EditText acFirstStartHourEditText;
    private EditText acFirstStartMinute1EditText;
    private EditText acFirstStartMinute2EditText;
    private EditText acFirstStartMinuteEditText;
    private Button acFirstStartTime1Button;
    private Button acFirstStartTime2Button;
    private Button acFirstStartTimeButton;
    private Button all2DefaultButton;
    private ConstraintLayout applicationSetActionLayout;
    private TextView applicationSetActionTextView;
    private ToggleButton applicationSetActionToggleButton;
    private LinearLayout applicationSetParamLayout;
    private Button batteryCapacityButton;
    private EditText batteryCapacityEditText;
    private ToggleButton batterySharedFunctionButton;
    private Button batteryWarningSocButton;
    private EditText batteryWarningSocEditText;
    private Button batteryWarningVoltageButton;
    private EditText batteryWarningVoltageEditText;
    private ToggleButton buzzerFunctionButton;
    private Button chargeCurrentButton;
    private EditText chargeCurrentEditText;
    private ConstraintLayout chargeSetActionLayout;
    private TextView chargeSetActionTextView;
    private ToggleButton chargeSetActionToggleButton;
    private LinearLayout chargeSetParamLayout;
    private Button composedPhaseButton;
    private boolean created;
    private Button datalogSnButton;
    private EditText datalogSnEditText;
    private TextView datalogSnTextView;
    private Button disChgControlButton;
    private Spinner disChgControlSpinner;
    private ConstraintLayout dischargeSetActionLayout;
    private TextView dischargeSetActionTextView;
    private ToggleButton dischargeSetActionToggleButton;
    private LinearLayout dischargeSetParamLayout;
    private Button dischgCurrentButton;
    private EditText dischgCurrentEditText;
    private Button epsFrequencySetButton;
    private Spinner epsFrequencySetSpinner;
    private Button epsVoltageSetButton;
    private Spinner epsVoltageSetSpinner;
    private Button equalizationPeriodButton;
    private EditText equalizationPeriodEditText;
    private Button equalizationTimeButton;
    private EditText equalizationTimeEditText;
    private Button equalizationVoltageButton;
    private EditText equalizationVoltageEditText;
    private ToggleButton feedInGridFunctionButton;
    private Button feedInGridPowerPercentButton;
    private EditText feedInGridPowerPercentEditText;
    private Button floatingVoltageButton;
    private EditText floatingVoltageEditText;
    private ToggleButton greenFunctionButton;
    private TextView inverterSnTextView;
    private Button leadAcidChargeVoltRefButton;
    private EditText leadAcidChargeVoltRefEditText;
    private Button leadAcidDischargeCutOffVoltButton;
    private EditText leadAcidDischargeCutOffVoltEditText;
    private Button lineModeInputButton;
    private Spinner lineModeInputSpinner;
    private LocalConnect localConnect;
    private Button masterOrSlaveButton;
    private Spinner masterOrSlaveSpinner;
    private Button maxGeneratorInputPowerButton;
    private EditText maxGeneratorInputPowerEditText;
    private Button norminalBatteryVoltageButton;
    private EditText norminalBatteryVoltageEditText;
    private Button onGridEodSocButton;
    private EditText onGridEodSocEditText;
    private Button onGridEodVoltageButton;
    private EditText onGridEodVoltageEditText;
    private EditText pinEditText;
    private Button pvInputModeButton;
    private Spinner pvInputModeSpinner;
    private Button readAllButton;
    private Spinner readComposedPhaseSpinner;
    private Button reset2FactoryButton;
    private ConstraintLayout resetSetActionLayout;
    private TextView resetSetActionTextView;
    private ToggleButton resetSetActionToggleButton;
    private LinearLayout resetSetParamLayout;
    private Spinner setComposedPhaseSpinner;
    private Button setTimeButton;
    private ToggleButton setToStandbyFunctionButton;
    private Button socLowLimitEpsDischgButton;
    private EditText socLowLimitEpsDischgEditText;
    private ToggleButton takeLoadTogetherFunctionButton;
    private EditText timeDateEditText;
    private EditText timeTimeEditText;
    private Button updateFirmwareButton;
    private ConstraintLayout wifiModuleSetActionLayout;
    private TextView wifiModuleSetActionTextView;
    private ToggleButton wifiModuleSetActionToggleButton;
    private LinearLayout wifiModuleSetParamLayout;

    private static boolean checkIfRangeValid(int i, int i2, int i3) {
        return i3 >= i && i3 <= i2;
    }

    public LocalOffGridSetFragment(LocalConnect localConnect) {
        super(22L);
        this._21Functions = new String[]{"FUNC_AC_CHARGE", "FUNC_FORCED_CHG_EN", "FUNC_FORCED_DISCHG_EN", "FUNC_SET_TO_STANDBY", "FUNC_EPS_EN", "FUNC_FEED_IN_GRID_EN"};
        this._110Functions = new String[]{"FUNC_PV_GRID_OFF_EN", "FUNC_RUN_WITHOUT_GRID", "FUNC_MICRO_GRID_EN", "FUNC_BAT_SHARED", "FUNC_BUZZER_EN", "FUNC_TAKE_LOAD_TOGETHER", "FUNC_GREEN_EN"};
        this.localConnect = localConnect;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_local_off_grid_set, viewGroup, false);
        new Thread(new Runnable() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m566x8282bd73();
            }
        }).start();
        ((ConstraintLayout) viewInflate.findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment.this.startActivity(new Intent(view.getContext(), (Class<?>) LoginActivity.class));
                LocalActivity.instance.finish();
            }
        });
        this.datalogSnTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_datalogSn_TextView);
        this.inverterSnTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_inverterSn_TextView);
        Button button = (Button) viewInflate.findViewById(R.id.fragment_remote_set_readAllButton);
        this.readAllButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Inverter inverter = LocalOffGridSetFragment.this.localConnect.getInverter();
                if (inverter != null) {
                    LocalOffGridSetFragment.this.clearFragmentData();
                    LocalOffGridSetFragment.this.readAllButton.setEnabled(false);
                    new ReadMultiParamTask(LocalOffGridSetFragment.this).execute(new RemoteReadInfo(inverter.getSerialNum(), 0, 40), new RemoteReadInfo(inverter.getSerialNum(), 40, 40), new RemoteReadInfo(inverter.getSerialNum(), 80, 40), new RemoteReadInfo(inverter.getSerialNum(), 120, 40), new RemoteReadInfo(inverter.getSerialNum(), 160, 20));
                    new ReadDatalogParamTask(LocalOffGridSetFragment.this).execute(1);
                }
            }
        });
        EditText editText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeDateEditText);
        this.timeDateEditText = editText;
        editText.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = LocalOffGridSetFragment.this.timeDateEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 10) {
                    LocalOffGridSetFragment.this.timeDateEditText.setText(InvTool.formatDate(new Date()));
                }
                LocalOffGridSetFragment.this.getActivity().showDialog(0);
            }
        });
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_timeTimeEditText);
        this.timeTimeEditText = editText2;
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = LocalOffGridSetFragment.this.timeTimeEditText.getText().toString();
                if (Tool.isEmpty(string) || string.length() != 5) {
                    LocalOffGridSetFragment.this.timeTimeEditText.setText(InvTool.formatTime(new Date()).substring(0, 5));
                }
                LocalOffGridSetFragment.this.getActivity().showDialog(1);
            }
        });
        Button button2 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_timeButton);
        this.setTimeButton = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_TIME", LocalOffGridSetFragment.this.timeDateEditText.getText().toString().trim() + " " + LocalOffGridSetFragment.this.timeTimeEditText.getText().toString().trim() + ":" + String.valueOf(calendar.get(13)), LocalOffGridSetFragment.this.setTimeButton);
            }
        });
        this.pvInputModeSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_pvInputModeSpinner);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_pv_input_mode_empty)));
        arrayList.add(new Property(String.valueOf(3), getString(R.string.phrase_param_pv_input_mode_off_grid_3)));
        arrayList.add(new Property(String.valueOf(4), getString(R.string.phrase_param_pv_input_mode_off_grid_4)));
        ArrayAdapter arrayAdapter = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.pvInputModeSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        Button button3 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_pvInputModeButton);
        this.pvInputModeButton = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.pvInputModeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_PV_INPUT_MODE", property.getName(), LocalOffGridSetFragment.this.pvInputModeButton);
            }
        });
        ToggleButton toggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_setToStandbyFunctionButton);
        this.setToStandbyFunctionButton = toggleButton;
        toggleButton.setOnClickListener(new AnonymousClass7());
        this.batteryCapacityEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_batteryCapacityEditText);
        Button button4 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batteryCapacityButton);
        this.batteryCapacityButton = button4;
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_BATTERY_CAPACITY", localOffGridSetFragment.batteryCapacityEditText.getText().toString().trim(), LocalOffGridSetFragment.this.batteryCapacityButton);
            }
        });
        this.norminalBatteryVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_norminalBatteryVoltageEditText);
        Button button5 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_norminalBatteryVoltageButton);
        this.norminalBatteryVoltageButton = button5;
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_NOMINAL_BATTERY_VOLTAGE", localOffGridSetFragment.norminalBatteryVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.norminalBatteryVoltageButton);
            }
        });
        ToggleButton toggleButton2 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_buzzerFunctionButton);
        this.buzzerFunctionButton = toggleButton2;
        toggleButton2.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runControlRemoteWrite("FUNC_BUZZER_EN", localOffGridSetFragment.buzzerFunctionButton);
            }
        });
        ToggleButton toggleButton3 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_greenFunctionButton);
        this.greenFunctionButton = toggleButton3;
        toggleButton3.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runControlRemoteWrite("FUNC_GREEN_EN", localOffGridSetFragment.greenFunctionButton);
            }
        });
        this.epsVoltageSetSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_epsVoltageSetSpinner);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_eps_voltage_set_empty)));
        arrayList2.add(new Property(String.valueOf(208), getString(R.string.phrase_param_eps_voltage_set_208)));
        arrayList2.add(new Property(String.valueOf(220), getString(R.string.phrase_param_eps_voltage_set_220)));
        arrayList2.add(new Property(String.valueOf(230), getString(R.string.phrase_param_eps_voltage_set_230)));
        arrayList2.add(new Property(String.valueOf(240), getString(R.string.phrase_param_eps_voltage_set_240)));
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.epsVoltageSetSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        Button button6 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_epsVoltageSetButton);
        this.epsVoltageSetButton = button6;
        button6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.epsVoltageSetSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_EPS_VOLT_SET", property.getName(), LocalOffGridSetFragment.this.epsVoltageSetButton);
            }
        });
        this.epsFrequencySetSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_epsFrequencySetSpinner);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_eps_frequency_set_empty)));
        arrayList3.add(new Property(String.valueOf(50), getString(R.string.phrase_param_eps_frequency_set_50)));
        arrayList3.add(new Property(String.valueOf(60), getString(R.string.phrase_param_eps_frequency_set_60)));
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.epsFrequencySetSpinner.setAdapter((SpinnerAdapter) arrayAdapter3);
        Button button7 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_epsFrequencySetButton);
        this.epsFrequencySetButton = button7;
        button7.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.epsFrequencySetSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_EPS_FREQ_SET", property.getName(), LocalOffGridSetFragment.this.epsFrequencySetButton);
            }
        });
        this.lineModeInputSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_lineModeInputSpinner);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_line_mode_input_empty)));
        arrayList4.add(new Property(String.valueOf(0), getString(R.string.phrase_param_line_mode_input_0)));
        arrayList4.add(new Property(String.valueOf(1), getString(R.string.phrase_param_line_mode_input_1)));
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList4);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lineModeInputSpinner.setAdapter((SpinnerAdapter) arrayAdapter4);
        Button button8 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_lineModeInputButton);
        this.lineModeInputButton = button8;
        button8.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.lineModeInputSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_LINE_MODE_INPUT", property.getName(), LocalOffGridSetFragment.this.lineModeInputButton);
            }
        });
        this.maxGeneratorInputPowerEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_maxGeneratorInputPowerEditText);
        Button button9 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_maxGeneratorInputPowerButton);
        this.maxGeneratorInputPowerButton = button9;
        button9.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_MAX_GENERATOR_INPUT_POWER", localOffGridSetFragment.maxGeneratorInputPowerEditText.getText().toString().trim(), LocalOffGridSetFragment.this.maxGeneratorInputPowerButton);
            }
        });
        this.acFirstStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartHourEditText);
        this.acFirstStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartMinuteEditText);
        Button button10 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartTimeButton);
        this.acFirstStartTimeButton = button10;
        button10.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_FIRST_START_TIME", localOffGridSetFragment.acFirstStartHourEditText, LocalOffGridSetFragment.this.acFirstStartMinuteEditText, LocalOffGridSetFragment.this.acFirstStartTimeButton);
            }
        });
        this.acFirstEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndHourEditText);
        this.acFirstEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndMinuteEditText);
        Button button11 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndTimeButton);
        this.acFirstEndTimeButton = button11;
        button11.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_FIRST_END_TIME", localOffGridSetFragment.acFirstEndHourEditText, LocalOffGridSetFragment.this.acFirstEndMinuteEditText, LocalOffGridSetFragment.this.acFirstEndTimeButton);
            }
        });
        this.acFirstStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartHour1EditText);
        this.acFirstStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartMinute1EditText);
        Button button12 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartTime1Button);
        this.acFirstStartTime1Button = button12;
        button12.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_FIRST_START_TIME_1", localOffGridSetFragment.acFirstStartHour1EditText, LocalOffGridSetFragment.this.acFirstStartMinute1EditText, LocalOffGridSetFragment.this.acFirstStartTime1Button);
            }
        });
        this.acFirstEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndHour1EditText);
        this.acFirstEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndMinute1EditText);
        Button button13 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndTime1Button);
        this.acFirstEndTime1Button = button13;
        button13.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_FIRST_END_TIME_1", localOffGridSetFragment.acFirstEndHour1EditText, LocalOffGridSetFragment.this.acFirstEndMinute1EditText, LocalOffGridSetFragment.this.acFirstEndTime1Button);
            }
        });
        this.acFirstStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartHour2EditText);
        this.acFirstStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartMinute2EditText);
        Button button14 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acFirstStartTime2Button);
        this.acFirstStartTime2Button = button14;
        button14.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_FIRST_START_TIME_2", localOffGridSetFragment.acFirstStartHour2EditText, LocalOffGridSetFragment.this.acFirstStartMinute2EditText, LocalOffGridSetFragment.this.acFirstStartTime2Button);
            }
        });
        this.acFirstEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndHour2EditText);
        this.acFirstEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndMinute2EditText);
        Button button15 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acFirstEndTime2Button);
        this.acFirstEndTime2Button = button15;
        button15.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_FIRST_END_TIME_2", localOffGridSetFragment.acFirstEndHour2EditText, LocalOffGridSetFragment.this.acFirstEndMinute2EditText, LocalOffGridSetFragment.this.acFirstEndTime2Button);
            }
        });
        ToggleButton toggleButton4 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_batterySharedFunctionButton);
        this.batterySharedFunctionButton = toggleButton4;
        toggleButton4.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runControlRemoteWrite("FUNC_BAT_SHARED", localOffGridSetFragment.batterySharedFunctionButton);
            }
        });
        this.masterOrSlaveSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveSpinner);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_master_or_slave_empty)));
        arrayList5.add(new Property(String.valueOf(0), getString(R.string.phrase_param_master_or_slave_offgrid_0)));
        arrayList5.add(new Property(String.valueOf(1), getString(R.string.phrase_param_master_or_slave_offgrid_1)));
        arrayList5.add(new Property(String.valueOf(3), getString(R.string.phrase_param_master_or_slave_offgrid_3)));
        ArrayAdapter arrayAdapter5 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList5);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.masterOrSlaveSpinner.setAdapter((SpinnerAdapter) arrayAdapter5);
        Button button16 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_masterOrSlaveButton);
        this.masterOrSlaveButton = button16;
        button16.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.masterOrSlaveSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_SET_MASTER_OR_SLAVE", property.getName(), LocalOffGridSetFragment.this.masterOrSlaveButton);
            }
        });
        this.readComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_readComposedPhaseSpinner);
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(new Property(String.valueOf(-1), "--"));
        arrayList6.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList6.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList6.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter6 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList6);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.readComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter6);
        this.readComposedPhaseSpinner.setEnabled(false);
        this.setComposedPhaseSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_setComposedPhaseSpinner);
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_composed_phase_empty)));
        arrayList7.add(new Property(String.valueOf(1), getString(R.string.phrase_param_composed_phase_1)));
        arrayList7.add(new Property(String.valueOf(2), getString(R.string.phrase_param_composed_phase_2)));
        arrayList7.add(new Property(String.valueOf(3), getString(R.string.phrase_param_composed_phase_3)));
        ArrayAdapter arrayAdapter7 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList7);
        arrayAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setComposedPhaseSpinner.setAdapter((SpinnerAdapter) arrayAdapter7);
        Button button17 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_composedPhaseButton);
        this.composedPhaseButton = button17;
        button17.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.setComposedPhaseSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runNormalRemoteWrite("HOLD_SET_COMPOSED_PHASE", property.getName(), LocalOffGridSetFragment.this.composedPhaseButton);
            }
        });
        ToggleButton toggleButton5 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_takeLoadTogetherFunctionButton);
        this.takeLoadTogetherFunctionButton = toggleButton5;
        toggleButton5.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runControlRemoteWrite("FUNC_TAKE_LOAD_TOGETHER", localOffGridSetFragment.takeLoadTogetherFunctionButton);
            }
        });
        ToggleButton toggleButton6 = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridFunctionButton);
        this.feedInGridFunctionButton = toggleButton6;
        toggleButton6.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runControlRemoteWrite("FUNC_FEED_IN_GRID_EN", localOffGridSetFragment.feedInGridFunctionButton);
            }
        });
        this.feedInGridPowerPercentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentEditText);
        Button button18 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_feedInGridPowerPercentButton);
        this.feedInGridPowerPercentButton = button18;
        button18.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_FEED_IN_GRID_POWER_PERCENT", localOffGridSetFragment.feedInGridPowerPercentEditText.getText().toString().trim(), LocalOffGridSetFragment.this.feedInGridPowerPercentButton);
            }
        });
        this.onGridEodVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodVoltageEditText);
        Button button19 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodVoltageButton);
        this.onGridEodVoltageButton = button19;
        button19.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_ON_GRID_EOD_VOLTAGE", localOffGridSetFragment.onGridEodVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.onGridEodVoltageButton);
            }
        });
        this.onGridEodSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodSocEditText);
        Button button20 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_onGridEodSocButton);
        this.onGridEodSocButton = button20;
        button20.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_DISCHG_CUT_OFF_SOC_EOD", localOffGridSetFragment.onGridEodSocEditText.getText().toString().trim(), LocalOffGridSetFragment.this.onGridEodSocButton);
            }
        });
        this.chargeCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentEditText);
        Button button21 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_chargeCurrentButton);
        this.chargeCurrentButton = button21;
        button21.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_RATE", localOffGridSetFragment.chargeCurrentEditText.getText().toString().trim(), LocalOffGridSetFragment.this.chargeCurrentButton);
            }
        });
        this.leadAcidChargeVoltRefEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefEditText);
        Button button22 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidChargeVoltRefButton);
        this.leadAcidChargeVoltRefButton = button22;
        button22.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_CHARGE_VOLT_REF", localOffGridSetFragment.leadAcidChargeVoltRefEditText.getText().toString().trim(), LocalOffGridSetFragment.this.leadAcidChargeVoltRefButton);
            }
        });
        this.floatingVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageEditText);
        Button button23 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_floatingVoltageButton);
        this.floatingVoltageButton = button23;
        button23.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_FLOATING_VOLTAGE", localOffGridSetFragment.floatingVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.floatingVoltageButton);
            }
        });
        this.equalizationVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_equalizationVoltageEditText);
        Button button24 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_equalizationVoltageButton);
        this.equalizationVoltageButton = button24;
        button24.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.33
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_EQUALIZATION_VOLTAGE", localOffGridSetFragment.equalizationVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.equalizationVoltageButton);
            }
        });
        this.equalizationPeriodEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_equalizationPeriodEditText);
        Button button25 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_equalizationPeriodButton);
        this.equalizationPeriodButton = button25;
        button25.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_EQUALIZATION_PERIOD", localOffGridSetFragment.equalizationPeriodEditText.getText().toString().trim(), LocalOffGridSetFragment.this.equalizationPeriodButton);
            }
        });
        this.equalizationTimeEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_equalizationTimeEditText);
        Button button26 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_equalizationTimeButton);
        this.equalizationTimeButton = button26;
        button26.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.35
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_EQUALIZATION_TIME", localOffGridSetFragment.equalizationTimeEditText.getText().toString().trim(), LocalOffGridSetFragment.this.equalizationTimeButton);
            }
        });
        this.acChargeTypeSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_acChargeTypeSpinner);
        ArrayList arrayList8 = new ArrayList();
        arrayList8.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_ac_charge_type_empty)));
        arrayList8.add(new Property(String.valueOf(0), getString(R.string.phrase_param_ac_charge_type_0)));
        arrayList8.add(new Property(String.valueOf(1), getString(R.string.phrase_param_ac_charge_type_1)));
        arrayList8.add(new Property(String.valueOf(2), getString(R.string.phrase_param_ac_charge_type_2)));
        arrayList8.add(new Property(String.valueOf(3), getString(R.string.phrase_param_ac_charge_type_3)));
        arrayList8.add(new Property(String.valueOf(4), getString(R.string.phrase_param_ac_charge_type_4)));
        arrayList8.add(new Property(String.valueOf(5), getString(R.string.phrase_param_ac_charge_type_5)));
        ArrayAdapter arrayAdapter8 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList8);
        arrayAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.acChargeTypeSpinner.setAdapter((SpinnerAdapter) arrayAdapter8);
        Button button27 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeTypeButton);
        this.acChargeTypeButton = button27;
        button27.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.36
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.acChargeTypeSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runBitRemoteWrite("BIT_AC_CHARGE_TYPE", property.getName(), LocalOffGridSetFragment.this.acChargeTypeButton);
            }
        });
        this.acChgBatCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChgBatCurrentEditText);
        Button button28 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChgBatCurrentButton);
        this.acChgBatCurrentButton = button28;
        button28.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.37
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_BATTERY_CURRENT", localOffGridSetFragment.acChgBatCurrentEditText.getText().toString().trim(), LocalOffGridSetFragment.this.acChgBatCurrentButton);
            }
        });
        this.acChargeStartHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHourEditText);
        this.acChargeStartMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinuteEditText);
        Button button29 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTimeButton);
        this.acChargeStartTimeButton = button29;
        button29.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME", localOffGridSetFragment.acChargeStartHourEditText, LocalOffGridSetFragment.this.acChargeStartMinuteEditText, LocalOffGridSetFragment.this.acChargeStartTimeButton);
            }
        });
        this.acChargeEndHourEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHourEditText);
        this.acChargeEndMinuteEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinuteEditText);
        Button button30 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTimeButton);
        this.acChargeEndTimeButton = button30;
        button30.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.39
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME", localOffGridSetFragment.acChargeEndHourEditText, LocalOffGridSetFragment.this.acChargeEndMinuteEditText, LocalOffGridSetFragment.this.acChargeEndTimeButton);
            }
        });
        this.acChargeStartHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour1EditText);
        this.acChargeStartMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute1EditText);
        Button button31 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime1Button);
        this.acChargeStartTime1Button = button31;
        button31.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.40
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_1", localOffGridSetFragment.acChargeStartHour1EditText, LocalOffGridSetFragment.this.acChargeStartMinute1EditText, LocalOffGridSetFragment.this.acChargeStartTime1Button);
            }
        });
        this.acChargeEndHour1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour1EditText);
        this.acChargeEndMinute1EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute1EditText);
        Button button32 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime1Button);
        this.acChargeEndTime1Button = button32;
        button32.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.41
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_1", localOffGridSetFragment.acChargeEndHour1EditText, LocalOffGridSetFragment.this.acChargeEndMinute1EditText, LocalOffGridSetFragment.this.acChargeEndTime1Button);
            }
        });
        this.acChargeStartHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartHour2EditText);
        this.acChargeStartMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartMinute2EditText);
        Button button33 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartTime2Button);
        this.acChargeStartTime2Button = button33;
        button33.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.42
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_START_TIME_2", localOffGridSetFragment.acChargeStartHour2EditText, LocalOffGridSetFragment.this.acChargeStartMinute2EditText, LocalOffGridSetFragment.this.acChargeStartTime2Button);
            }
        });
        this.acChargeEndHour2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndHour2EditText);
        this.acChargeEndMinute2EditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndMinute2EditText);
        Button button34 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndTime2Button);
        this.acChargeEndTime2Button = button34;
        button34.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.43
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runTimeRemoteWrite("HOLD_AC_CHARGE_END_TIME_2", localOffGridSetFragment.acChargeEndHour2EditText, LocalOffGridSetFragment.this.acChargeEndMinute2EditText, LocalOffGridSetFragment.this.acChargeEndTime2Button);
            }
        });
        this.acChargeStartBatteryVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartBatteryVoltageEditText);
        Button button35 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartBatteryVoltageButton);
        this.acChargeStartBatteryVoltageButton = button35;
        button35.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.44
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE", localOffGridSetFragment.acChargeStartBatteryVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.acChargeStartBatteryVoltageButton);
            }
        });
        this.acChargeEndBatteryVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatteryVoltageEditText);
        Button button36 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatteryVoltageButton);
        this.acChargeEndBatteryVoltageButton = button36;
        button36.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.45
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE", localOffGridSetFragment.acChargeEndBatteryVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.acChargeEndBatteryVoltageButton);
            }
        });
        this.acChargeStartBatterySocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartBatterySocEditText);
        Button button37 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeStartBatterySocButton);
        this.acChargeStartBatterySocButton = button37;
        button37.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.46
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_START_BATTERY_SOC", localOffGridSetFragment.acChargeStartBatterySocEditText.getText().toString().trim(), LocalOffGridSetFragment.this.acChargeStartBatterySocButton);
            }
        });
        this.acChargeEndBatterySocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatterySocEditText);
        Button button38 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_acChargeEndBatterySocButton);
        this.acChargeEndBatterySocButton = button38;
        button38.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.47
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_AC_CHARGE_END_BATTERY_SOC", localOffGridSetFragment.acChargeEndBatterySocEditText.getText().toString().trim(), LocalOffGridSetFragment.this.acChargeEndBatterySocButton);
            }
        });
        this.disChgControlSpinner = (Spinner) viewInflate.findViewById(R.id.fragment_remote_set_disChgControlSpinner);
        ArrayList arrayList9 = new ArrayList();
        arrayList9.add(new Property(String.valueOf(-1), getString(R.string.phrase_param_discharge_control_empty)));
        arrayList9.add(new Property(String.valueOf(0), getString(R.string.phrase_param_discharge_control_0)));
        arrayList9.add(new Property(String.valueOf(1), getString(R.string.phrase_param_discharge_control_1)));
        ArrayAdapter arrayAdapter9 = new ArrayAdapter(viewInflate.getContext(), android.R.layout.simple_spinner_item, arrayList9);
        arrayAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.disChgControlSpinner.setAdapter((SpinnerAdapter) arrayAdapter9);
        Button button39 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_disChgControlButton);
        this.disChgControlButton = button39;
        button39.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.48
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Property property = (Property) LocalOffGridSetFragment.this.disChgControlSpinner.getSelectedItem();
                if ("-1".equals(property.getName())) {
                    return;
                }
                LocalOffGridSetFragment.this.runBitRemoteWrite("BIT_DISCHG_CONTROL_TYPE", property.getName(), LocalOffGridSetFragment.this.disChgControlButton);
            }
        });
        this.dischgCurrentEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_dischgCurrentEditText);
        Button button40 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_dischgCurrentButton);
        this.dischgCurrentButton = button40;
        button40.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.49
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_RATE", localOffGridSetFragment.dischgCurrentEditText.getText().toString().trim(), LocalOffGridSetFragment.this.dischgCurrentButton);
            }
        });
        this.batteryWarningVoltageEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_batteryWarningVoltageEditText);
        Button button41 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batteryWarningVoltageButton);
        this.batteryWarningVoltageButton = button41;
        button41.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.50
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_BATTERY_WARNING_VOLTAGE", localOffGridSetFragment.batteryWarningVoltageEditText.getText().toString().trim(), LocalOffGridSetFragment.this.batteryWarningVoltageButton);
            }
        });
        this.batteryWarningSocEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_batteryWarningSocEditText);
        Button button42 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_batteryWarningSocButton);
        this.batteryWarningSocButton = button42;
        button42.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.51
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_BATTERY_WARNING_SOC", localOffGridSetFragment.batteryWarningSocEditText.getText().toString().trim(), LocalOffGridSetFragment.this.batteryWarningSocButton);
            }
        });
        this.leadAcidDischargeCutOffVoltEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltEditText);
        Button button43 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_leadAcidDischargeCutOffVoltButton);
        this.leadAcidDischargeCutOffVoltButton = button43;
        button43.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.52
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT", localOffGridSetFragment.leadAcidDischargeCutOffVoltEditText.getText().toString().trim(), LocalOffGridSetFragment.this.leadAcidDischargeCutOffVoltButton);
            }
        });
        this.socLowLimitEpsDischgEditText = (EditText) viewInflate.findViewById(R.id.fragment_remote_set_socLowLimitEpsDischgEditText);
        Button button44 = (Button) viewInflate.findViewById(R.id.fragment_remote_set_socLowLimitEpsDischgButton);
        this.socLowLimitEpsDischgButton = button44;
        button44.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.53
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runNormalRemoteWrite("HOLD_SOC_LOW_LIMIT_EPS_DISCHG", localOffGridSetFragment.socLowLimitEpsDischgEditText.getText().toString().trim(), LocalOffGridSetFragment.this.socLowLimitEpsDischgButton);
            }
        });
        this.datalogSnEditText = (EditText) viewInflate.findViewById(R.id.fragment_local_set_datalogSnEditText);
        this.pinEditText = (EditText) viewInflate.findViewById(R.id.fragment_local_set_pinEditText);
        Button button45 = (Button) viewInflate.findViewById(R.id.fragment_local_set_datalogSnButton);
        this.datalogSnButton = button45;
        button45.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.54
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String string = LocalOffGridSetFragment.this.datalogSnEditText.getText().toString();
                String string2 = LocalOffGridSetFragment.this.pinEditText.getText().toString();
                if (Tool.isEmpty(string) || Tool.isEmpty(string2)) {
                    Toast.makeText(LocalOffGridSetFragment.this.getActivity().getApplicationContext(), R.string.page_maintain_remote_set_alert_param_empty, 1).show();
                    return;
                }
                if (string.length() != 10) {
                    Toast.makeText(LocalOffGridSetFragment.this.getActivity().getApplicationContext(), R.string.page_register_error_datalogSn_length, 1).show();
                    return;
                }
                if (PinTool.verifyDatalog(string, string2)) {
                    try {
                        LocalOffGridSetFragment.this.runDatalogParamWrite(1, string.getBytes(LocalizedMessage.DEFAULT_ENCODING), LocalOffGridSetFragment.this.datalogSnButton);
                        return;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                Toast.makeText(LocalOffGridSetFragment.this.getActivity().getApplicationContext(), R.string.page_register_error_check_code_not_match, 1).show();
            }
        });
        Button button46 = (Button) viewInflate.findViewById(R.id.fragment_local_set_all2DefaultButton);
        this.all2DefaultButton = button46;
        button46.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.55
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment.this.runNormalRemoteWrite("ALL_TO_DEFAULT", String.valueOf(2), LocalOffGridSetFragment.this.all2DefaultButton);
            }
        });
        Button button47 = (Button) viewInflate.findViewById(R.id.fragment_local_set_reset2FactoryButton);
        this.reset2FactoryButton = button47;
        button47.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.56
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
                localOffGridSetFragment.runDatalogParamWrite(3, new byte[]{JSONB.Constants.BC_OBJECT_END}, localOffGridSetFragment.reset2FactoryButton);
            }
        });
        this.applicationSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_layout);
        this.applicationSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_textView);
        this.applicationSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_toggleButton);
        this.applicationSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_application_set_paramLayout);
        this.applicationSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.57
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.applicationSetParamLayout.setVisibility(0);
                } else {
                    LocalOffGridSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.applicationSetParamLayout.setVisibility(8);
                }
            }
        });
        this.applicationSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.58
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.applicationSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.applicationSetActionToggleButton.setChecked(false);
                    LocalOffGridSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.applicationSetParamLayout.setVisibility(8);
                } else {
                    LocalOffGridSetFragment.this.applicationSetActionToggleButton.setChecked(true);
                    LocalOffGridSetFragment.this.applicationSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.applicationSetParamLayout.setVisibility(0);
                }
            }
        });
        this.chargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_layout);
        this.chargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_textView);
        this.chargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_toggleButton);
        this.chargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_charge_set_paramLayout);
        this.chargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.59
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.chargeSetParamLayout.setVisibility(0);
                } else {
                    LocalOffGridSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.chargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.chargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.60
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.chargeSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.chargeSetActionToggleButton.setChecked(false);
                    LocalOffGridSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.chargeSetParamLayout.setVisibility(8);
                } else {
                    LocalOffGridSetFragment.this.chargeSetActionToggleButton.setChecked(true);
                    LocalOffGridSetFragment.this.chargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.chargeSetParamLayout.setVisibility(0);
                }
            }
        });
        this.dischargeSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_layout);
        this.dischargeSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_textView);
        this.dischargeSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_toggleButton);
        this.dischargeSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_discharge_set_paramLayout);
        this.dischargeSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.61
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                } else {
                    LocalOffGridSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                }
            }
        });
        this.dischargeSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.62
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.dischargeSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.dischargeSetActionToggleButton.setChecked(false);
                    LocalOffGridSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.dischargeSetParamLayout.setVisibility(8);
                } else {
                    LocalOffGridSetFragment.this.dischargeSetActionToggleButton.setChecked(true);
                    LocalOffGridSetFragment.this.dischargeSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.dischargeSetParamLayout.setVisibility(0);
                }
            }
        });
        this.resetSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_reset_set_layout);
        this.resetSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_reset_set_textView);
        this.resetSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_reset_set_toggleButton);
        this.resetSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_reset_set_paramLayout);
        this.resetSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.63
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.resetSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.resetSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.resetSetParamLayout.setVisibility(0);
                } else {
                    LocalOffGridSetFragment.this.resetSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.resetSetParamLayout.setVisibility(8);
                }
            }
        });
        this.resetSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.64
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.resetSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.resetSetActionToggleButton.setChecked(false);
                    LocalOffGridSetFragment.this.resetSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.resetSetParamLayout.setVisibility(8);
                } else {
                    LocalOffGridSetFragment.this.resetSetActionToggleButton.setChecked(true);
                    LocalOffGridSetFragment.this.resetSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.resetSetParamLayout.setVisibility(0);
                }
            }
        });
        this.wifiModuleSetActionLayout = (ConstraintLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_layout);
        this.wifiModuleSetActionTextView = (TextView) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_textView);
        this.wifiModuleSetActionToggleButton = (ToggleButton) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_toggleButton);
        this.wifiModuleSetParamLayout = (LinearLayout) viewInflate.findViewById(R.id.fragment_remote_set_label_wifi_module_set_paramLayout);
        this.wifiModuleSetActionToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.65
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.wifiModuleSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.wifiModuleSetParamLayout.setVisibility(0);
                } else {
                    LocalOffGridSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.wifiModuleSetParamLayout.setVisibility(8);
                }
            }
        });
        this.wifiModuleSetActionLayout.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.66
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LocalOffGridSetFragment.this.wifiModuleSetActionToggleButton.isChecked()) {
                    LocalOffGridSetFragment.this.wifiModuleSetActionToggleButton.setChecked(false);
                    LocalOffGridSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_expand);
                    LocalOffGridSetFragment.this.wifiModuleSetParamLayout.setVisibility(8);
                } else {
                    LocalOffGridSetFragment.this.wifiModuleSetActionToggleButton.setChecked(true);
                    LocalOffGridSetFragment.this.wifiModuleSetActionTextView.setText(R.string.phrase_button_collapse);
                    LocalOffGridSetFragment.this.wifiModuleSetParamLayout.setVisibility(0);
                }
            }
        });
        Button button48 = (Button) viewInflate.findViewById(R.id.fragment_local_set_updateFirmwareButton);
        this.updateFirmwareButton = button48;
        button48.setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m567x16c12d12(view);
            }
        });
        this.created = true;
        return viewInflate;
    }

    /* renamed from: lambda$onCreateView$0$com-lux-luxcloud-view-local-fragment-LocalOffGridSetFragment, reason: not valid java name */
    /* synthetic */ void m566x8282bd73() {
        this.localConnect.initialize(true);
    }

    /* renamed from: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment$7, reason: invalid class name */
    class AnonymousClass7 implements View.OnClickListener {
        AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            boolean zIsChecked = LocalOffGridSetFragment.this.setToStandbyFunctionButton.isChecked();
            AlertDialog.Builder builder = new AlertDialog.Builder(LocalActivity.instance);
            builder.setTitle(zIsChecked ? R.string.phrase_func_param_normaly : R.string.phrase_func_param_standby).setIcon(android.R.drawable.ic_dialog_info).setMessage(LocalOffGridSetFragment.this.getString(zIsChecked ? R.string.phrase_func_text_normal : R.string.phrase_func_text_standby) + " " + ((Object) LocalOffGridSetFragment.this.inverterSnTextView.getText())).setPositiveButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment$7$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m568x65e32ed7(dialogInterface, i);
                }
            }).setNegativeButton(R.string.phrase_button_cancel, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment$7$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m569xe44432b6(dialogInterface, i);
                }
            });
            builder.show();
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-local-fragment-LocalOffGridSetFragment$7, reason: not valid java name */
        /* synthetic */ void m568x65e32ed7(DialogInterface dialogInterface, int i) {
            LocalOffGridSetFragment localOffGridSetFragment = LocalOffGridSetFragment.this;
            localOffGridSetFragment.runControlRemoteWrite("FUNC_SET_TO_STANDBY", localOffGridSetFragment.setToStandbyFunctionButton);
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-local-fragment-LocalOffGridSetFragment$7, reason: not valid java name */
        /* synthetic */ void m569xe44432b6(DialogInterface dialogInterface, int i) {
            LocalOffGridSetFragment.this.setToStandbyFunctionButton.setChecked(!LocalOffGridSetFragment.this.setToStandbyFunctionButton.isChecked());
        }
    }

    /* renamed from: lambda$onCreateView$1$com-lux-luxcloud-view-local-fragment-LocalOffGridSetFragment, reason: not valid java name */
    /* synthetic */ void m567x16c12d12(View view) {
        Intent intent = new Intent(view.getContext(), (Class<?>) UpdateFirmwareActivity.class);
        intent.putExtra(Constants.LOCAL_CONNECT_TYPE, this.localConnect.getConnectType());
        startActivity(intent);
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
        System.out.println("LuxPower - LocalOffGridFragment onResume...");
        refreshFragmentParams();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        System.out.println("LuxPower - LocalSetFragment onDestroy...");
    }

    public void clearFragmentData() {
        if (this.created) {
            this.timeDateEditText.setText("");
            this.timeTimeEditText.setText("");
            this.pvInputModeSpinner.setSelection(0);
            this.setToStandbyFunctionButton.setChecked(false);
            this.batteryCapacityEditText.setText("");
            this.norminalBatteryVoltageEditText.setText("");
            this.buzzerFunctionButton.setChecked(false);
            this.greenFunctionButton.setChecked(false);
            this.epsVoltageSetSpinner.setSelection(0);
            this.epsFrequencySetSpinner.setSelection(0);
            this.lineModeInputSpinner.setSelection(0);
            this.maxGeneratorInputPowerEditText.setText("");
            this.acFirstStartHourEditText.setText("");
            this.acFirstStartMinuteEditText.setText("");
            this.acFirstEndHourEditText.setText("");
            this.acFirstEndMinuteEditText.setText("");
            this.acFirstStartHour1EditText.setText("");
            this.acFirstStartMinute1EditText.setText("");
            this.acFirstEndHour1EditText.setText("");
            this.acFirstEndMinute1EditText.setText("");
            this.acFirstStartHour2EditText.setText("");
            this.acFirstStartMinute2EditText.setText("");
            this.acFirstEndHour2EditText.setText("");
            this.acFirstEndMinute2EditText.setText("");
            this.batterySharedFunctionButton.setChecked(false);
            this.masterOrSlaveSpinner.setSelection(0);
            this.readComposedPhaseSpinner.setSelection(0);
            this.setComposedPhaseSpinner.setSelection(0);
            this.takeLoadTogetherFunctionButton.setChecked(false);
            this.feedInGridFunctionButton.setChecked(false);
            this.feedInGridPowerPercentEditText.setText("");
            this.onGridEodVoltageEditText.setText("");
            this.onGridEodSocEditText.setText("");
            this.chargeCurrentEditText.setText("");
            this.leadAcidChargeVoltRefEditText.setText("");
            this.floatingVoltageEditText.setText("");
            this.equalizationVoltageEditText.setText("");
            this.equalizationPeriodEditText.setText("");
            this.equalizationTimeEditText.setText("");
            this.acChargeTypeSpinner.setSelection(0);
            this.acChgBatCurrentEditText.setText("");
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
            this.acChargeStartBatteryVoltageEditText.setText("");
            this.acChargeEndBatteryVoltageEditText.setText("");
            this.acChargeStartBatterySocEditText.setText("");
            this.acChargeEndBatterySocEditText.setText("");
            this.disChgControlSpinner.setSelection(0);
            this.dischgCurrentEditText.setText("");
            this.batteryWarningVoltageEditText.setText("");
            this.batteryWarningSocEditText.setText("");
            this.leadAcidDischargeCutOffVoltEditText.setText("");
            this.socLowLimitEpsDischgEditText.setText("");
            this.datalogSnEditText.setText("");
            this.pinEditText.setText("");
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
    public void runControlRemoteWrite(String str, ToggleButton toggleButton) {
        RemoteWriteInfo remoteWriteInfo = new RemoteWriteInfo();
        remoteWriteInfo.setRemoteWriteType(REMOTE_WRITE_TYPE.CONTROL);
        remoteWriteInfo.setFunctionParam(str);
        remoteWriteInfo.setFunctionToggleButtonChecked(toggleButton.isChecked());
        remoteWriteInfo.setFunctionToggleButton(toggleButton);
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

    private static class ReadDatalogParamTask extends AsyncTask<Integer, JSONObject, Void> {
        private LocalOffGridSetFragment fragment;

        public ReadDatalogParamTask(LocalOffGridSetFragment localOffGridSetFragment) {
            this.fragment = localOffGridSetFragment;
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
                    }
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
                        if (jSONObject.has(String.valueOf(1))) {
                            this.fragment.datalogSnEditText.setText(jSONObject.getString(String.valueOf(1)));
                            this.fragment.pinEditText.setText("");
                        }
                    }
                }
                this.fragment.toast(jSONObject);
                return;
            }
        }
    }

    private static class ReadMultiParamTask extends AsyncTask<RemoteReadInfo, JSONObject, Void> {
        private LocalOffGridSetFragment fragment;

        public ReadMultiParamTask(LocalOffGridSetFragment localOffGridSetFragment) {
            this.fragment = localOffGridSetFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(RemoteReadInfo... remoteReadInfoArr) throws JSONException {
            RemoteReadInfo[] remoteReadInfoArr2 = remoteReadInfoArr;
            int length = remoteReadInfoArr2.length;
            int i = 0;
            while (i < length) {
                RemoteReadInfo remoteReadInfo = remoteReadInfoArr2[i];
                int startRegister = remoteReadInfo.getStartRegister();
                int pointNumber = remoteReadInfo.getPointNumber();
                String strSendCommand = this.fragment.localConnect.sendCommand("read_multi_03_" + startRegister + "_" + (pointNumber * 2), DataFrameFactory.createReadMultiHoldDataFrame(this.fragment.localConnect.getTcpProtocol(), this.fragment.localConnect.getDatalogSn(), startRegister, pointNumber));
                JSONObject jSONObjectCreateSuccessJSONObject = this.fragment.createSuccessJSONObject();
                try {
                    if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 36) {
                        int i2 = startRegister;
                        while (i2 < startRegister + pointNumber) {
                            String[] holdParamsByStartRegister = this.fragment.getHoldParamsByStartRegister(i2);
                            if (holdParamsByStartRegister != null) {
                                int length2 = holdParamsByStartRegister.length;
                                int i3 = 0;
                                while (i3 < length2) {
                                    int i4 = pointNumber;
                                    String str = holdParamsByStartRegister[i3];
                                    if (this.fragment.getStartRegisterByParam(str) != null) {
                                        jSONObjectCreateSuccessJSONObject.put(str, this.fragment.getValueShowText(str, startRegister, strSendCommand));
                                    }
                                    i3++;
                                    pointNumber = i4;
                                }
                            }
                            int i5 = pointNumber;
                            if (i2 == 21) {
                                int i6 = ((21 - startRegister) * 2) + 35;
                                int iCount = ProTool.count(strSendCommand.charAt(i6 + 1), strSendCommand.charAt(i6));
                                String[] strArr = this.fragment._21Functions;
                                int length3 = strArr.length;
                                int i7 = 0;
                                while (i7 < length3) {
                                    String str2 = strArr[i7];
                                    String[] strArr2 = strArr;
                                    jSONObjectCreateSuccessJSONObject.put(str2, ((1 << this.fragment.getBitByFunction(str2).intValue()) & iCount) > 0);
                                    i7++;
                                    strArr = strArr2;
                                }
                            }
                            if (i2 == 110) {
                                int i8 = ((110 - startRegister) * 2) + 35;
                                int iCount2 = ProTool.count(strSendCommand.charAt(i8 + 1), strSendCommand.charAt(i8));
                                String[] strArr3 = this.fragment._110Functions;
                                int length4 = strArr3.length;
                                int i9 = 0;
                                while (i9 < length4) {
                                    String str3 = strArr3[i9];
                                    String[] strArr4 = strArr3;
                                    jSONObjectCreateSuccessJSONObject.put(str3, ((1 << this.fragment.getBitByFunction(str3).intValue()) & iCount2) > 0);
                                    i9++;
                                    strArr3 = strArr4;
                                }
                                jSONObjectCreateSuccessJSONObject.put("BIT_WORKING_MODE", (iCount2 >> this.fragment.getBitByBitParam("BIT_WORKING_MODE").intValue()) & 1);
                            }
                            if (i2 == 120) {
                                int i10 = ((120 - startRegister) * 2) + 35;
                                int iCount3 = ProTool.count(strSendCommand.charAt(i10 + 1), strSendCommand.charAt(i10));
                                jSONObjectCreateSuccessJSONObject.put("BIT_AC_CHARGE_TYPE", (iCount3 >> this.fragment.getBitByBitParam("BIT_AC_CHARGE_TYPE").intValue()) & 7);
                                jSONObjectCreateSuccessJSONObject.put("BIT_DISCHG_CONTROL_TYPE", (iCount3 >> this.fragment.getBitByBitParam("BIT_DISCHG_CONTROL_TYPE").intValue()) & 3);
                                jSONObjectCreateSuccessJSONObject.put("BIT_ON_GRID_EOD_TYPE", (iCount3 >> this.fragment.getBitByBitParam("BIT_ON_GRID_EOD_TYPE").intValue()) & 1);
                            }
                            i2++;
                            pointNumber = i5;
                        }
                    }
                    JSONObject[] jSONObjectArr = new JSONObject[1];
                    try {
                        jSONObjectArr[0] = jSONObjectCreateSuccessJSONObject;
                        publishProgress(jSONObjectArr);
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        i++;
                        remoteReadInfoArr2 = remoteReadInfoArr;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
                i++;
                remoteReadInfoArr2 = remoteReadInfoArr;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't wrap try/catch for region: R(8:(2:355|6)|(125:8|(7:10|11|345|12|13|357|14)(1:17)|18|(3:20|(1:22)(1:(1:24)(1:25))|26)|27|(1:29)|30|(1:32)|33|(1:35)|36|(1:38)|39|(1:41)|42|(3:44|(1:46)(2:47|(1:49)(2:50|(1:52)(2:53|(1:55)(1:56))))|57)|58|(3:60|(1:62)(2:63|(1:65)(1:66))|67)|68|(2:70|(1:75)(1:74))|76|(1:78)|79|339|80|(1:82)|83|365|84|(1:86)|87|353|88|(4:90|91|347|92)(1:96)|97|349|98|(4:100|101|333|102)(1:106)|107|343|108|(4:110|111|337|112)(1:116)|117|361|118|(5:341|120|121|363|122)(1:125)|126|(1:128)|129|(1:131)|132|(1:134)|135|(1:137)|138|(1:140)|141|(1:143)|144|(1:146)|147|(3:149|(1:151)(2:152|(1:154)(2:155|(1:157)(1:158)))|159)|160|(5:162|163|(1:168)(2:351|167)|169|(1:174)(1:173))(1:175)|176|(1:178)|179|(1:181)|182|(1:184)|185|(1:187)|188|(1:190)|191|(1:193)|194|(1:196)|197|(1:199)|200|(1:202)|203|(1:205)|206|(1:208)|209|(2:211|(1:216)(1:215))|217|(1:219)|220|(1:222)|223|(1:225)|226|(1:228)|229|(1:231)|232|(1:234)|235|(1:237)|238|(1:240)|241|(1:243)|244|(1:246)|247|(1:249)|250|(1:252)|253|(1:255)|256|(1:258)|259|(1:261)|262|(1:264)|265|(1:267)|268|(11:278|279|(1:281)|282|(1:284)|285|(1:287)|288|(1:290)|291|(2:293|371)(1:370))(2:270|(14:275|276|335|277|279|(0)|282|(0)|285|(0)|288|(0)|291|(0)(0))(12:274|278|279|(0)|282|(0)|285|(0)|288|(0)|291|(0)(0)))|331)|325|326|359|327|372|331) */
        /* JADX WARN: Code restructure failed: missing block: B:329:0x0727, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:330:0x0728, code lost:
        
            r0.printStackTrace();
         */
        /* JADX WARN: Removed duplicated region for block: B:281:0x0633 A[Catch: Exception -> 0x070d, TryCatch #1 {Exception -> 0x070d, blocks: (B:277:0x0626, B:279:0x062b, B:281:0x0633, B:282:0x0642, B:284:0x064a, B:285:0x0659, B:287:0x0661, B:288:0x0670, B:290:0x0678, B:291:0x0687, B:293:0x068f, B:323:0x06f2), top: B:335:0x0626 }] */
        /* JADX WARN: Removed duplicated region for block: B:284:0x064a A[Catch: Exception -> 0x070d, TryCatch #1 {Exception -> 0x070d, blocks: (B:277:0x0626, B:279:0x062b, B:281:0x0633, B:282:0x0642, B:284:0x064a, B:285:0x0659, B:287:0x0661, B:288:0x0670, B:290:0x0678, B:291:0x0687, B:293:0x068f, B:323:0x06f2), top: B:335:0x0626 }] */
        /* JADX WARN: Removed duplicated region for block: B:287:0x0661 A[Catch: Exception -> 0x070d, TryCatch #1 {Exception -> 0x070d, blocks: (B:277:0x0626, B:279:0x062b, B:281:0x0633, B:282:0x0642, B:284:0x064a, B:285:0x0659, B:287:0x0661, B:288:0x0670, B:290:0x0678, B:291:0x0687, B:293:0x068f, B:323:0x06f2), top: B:335:0x0626 }] */
        /* JADX WARN: Removed duplicated region for block: B:290:0x0678 A[Catch: Exception -> 0x070d, TryCatch #1 {Exception -> 0x070d, blocks: (B:277:0x0626, B:279:0x062b, B:281:0x0633, B:282:0x0642, B:284:0x064a, B:285:0x0659, B:287:0x0661, B:288:0x0670, B:290:0x0678, B:291:0x0687, B:293:0x068f, B:323:0x06f2), top: B:335:0x0626 }] */
        /* JADX WARN: Removed duplicated region for block: B:293:0x068f A[Catch: Exception -> 0x070d, TryCatch #1 {Exception -> 0x070d, blocks: (B:277:0x0626, B:279:0x062b, B:281:0x0633, B:282:0x0642, B:284:0x064a, B:285:0x0659, B:287:0x0661, B:288:0x0670, B:290:0x0678, B:291:0x0687, B:293:0x068f, B:323:0x06f2), top: B:335:0x0626 }] */
        /* JADX WARN: Removed duplicated region for block: B:370:0x072b A[SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onProgressUpdate(org.json.JSONObject... r26) throws org.json.JSONException, java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 1860
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.ReadMultiParamTask.onProgressUpdate(org.json.JSONObject[]):void");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r2) {
            super.onPostExecute((ReadMultiParamTask) r2);
            this.fragment.readAllButton.setEnabled(true);
        }
    }

    private static class WriteParamTask extends AsyncTask<RemoteWriteInfo, Void, JSONObject> {
        private LocalOffGridSetFragment fragment;
        private RemoteWriteInfo remoteWriteInfo;

        public WriteParamTask(LocalOffGridSetFragment localOffGridSetFragment) {
            this.fragment = localOffGridSetFragment;
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
            RemoteWriteInfo remoteWriteInfo = remoteWriteInfoArr[0];
            if (remoteWriteInfo != null && remoteWriteInfo.getRemoteWriteType() != null) {
                this.remoteWriteInfo = remoteWriteInfo;
                publishProgress(new Void[0]);
                int i = AnonymousClass67.$SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[remoteWriteInfo.getRemoteWriteType().ordinal()];
                if (i == 1) {
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
                        return this.fragment.localConnect.writeSingle(this.fragment.getStartRegisterByParam(remoteWriteInfo.getTimeParam()).intValue(), ProTool.count(i3, i2)) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return this.fragment.createFailureJSONObject("INTEGER_FORMAT_ERROR");
                    }
                }
                if (i == 4) {
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
                if (i == 5) {
                    if (remoteWriteInfo.getDatalogParamIndex() == null || remoteWriteInfo.getDatalogParamValues() == null) {
                        return this.fragment.createFailureJSONObject("PARAM_EMPTY");
                    }
                    return this.fragment.localConnect.writeDatalogParam(remoteWriteInfo.getDatalogParamIndex().intValue(), remoteWriteInfo.getDatalogParamValues()) ? this.fragment.createSuccessJSONObject() : this.fragment.createFailureJSONObject("FAILED");
                }
            }
            return this.fragment.createFailureJSONObject("UNKNOWN_ERROR");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:7:0x001b A[Catch: all -> 0x0050, Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x001b, B:9:0x001f, B:11:0x002d, B:15:0x0042, B:16:0x0045), top: B:33:0x0006, outer: #0 }] */
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
                if (r4 == 0) goto L1b
                java.lang.String r1 = "success"
                boolean r1 = r4.getBoolean(r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L1b
                com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                r1 = 2131886305(0x7f1200e1, float:1.9407185E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                goto L4a
            L1b:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L45
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r1 = com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE.CONTROL     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.global.bean.set.REMOTE_WRITE_TYPE r2 = r2.getRemoteWriteType()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r1 == 0) goto L45
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                android.widget.ToggleButton r1 = r1.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r2 = r3.remoteWriteInfo     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                android.widget.ToggleButton r2 = r2.getFunctionToggleButton()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                boolean r2 = r2.isChecked()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                if (r2 != 0) goto L41
                r2 = r0
                goto L42
            L41:
                r2 = 0
            L42:
                r1.setChecked(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            L45:
                com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment r1 = r3.fragment     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
                com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.access$13400(r1, r4)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            L4a:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r4 = r3.remoteWriteInfo
                r4.setEnabled(r0)
                goto L68
            L50:
                r4 = move-exception
                goto L69
            L52:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment r4 = r3.fragment     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L63
                androidx.fragment.app.FragmentActivity r4 = r4.getActivity()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L63
                r1 = 2131886563(0x7f1201e3, float:1.9407708E38)
                com.lux.luxcloud.tool.Tool.alert(r4, r1)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L63
                goto L4a
            L63:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
                goto L4a
            L68:
                return
            L69:
                com.lux.luxcloud.global.bean.set.RemoteWriteInfo r1 = r3.remoteWriteInfo
                r1.setEnabled(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.WriteParamTask.onPostExecute(org.json.JSONObject):void");
        }
    }

    /* renamed from: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment$67, reason: invalid class name */
    static /* synthetic */ class AnonymousClass67 {
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
            try {
                $SwitchMap$com$lux$luxcloud$global$bean$set$REMOTE_WRITE_TYPE[REMOTE_WRITE_TYPE.DATALOG_PARAM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
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
    /* JADX WARN: Removed duplicated region for block: B:41:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void toast(org.json.JSONObject r5) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.toast(org.json.JSONObject):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getRegisterByBitParam(String str) {
        str.hashCode();
        switch (str) {
            case "BIT_WORKING_MODE":
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
            case "BIT_WORKING_MODE":
                return 11;
            case "BIT_DISCHG_CONTROL_TYPE":
                return 4;
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
    /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getBitSizeByBitParam(java.lang.String r7) {
        /*
            r6 = this;
            r7.hashCode()
            int r0 = r7.hashCode()
            r1 = 3
            r2 = 2
            r3 = 1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            r5 = -1
            switch(r0) {
                case -503707613: goto L33;
                case 189011893: goto L2a;
                case 1642564362: goto L1f;
                case 1775981274: goto L14;
                default: goto L12;
            }
        L12:
            r3 = r5
            goto L3d
        L14:
            java.lang.String r0 = "BIT_AC_CHARGE_TYPE"
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L1d
            goto L12
        L1d:
            r3 = r1
            goto L3d
        L1f:
            java.lang.String r0 = "BIT_ON_GRID_EOD_TYPE"
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L28
            goto L12
        L28:
            r3 = r2
            goto L3d
        L2a:
            java.lang.String r0 = "BIT_DISCHG_CONTROL_TYPE"
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L3d
            goto L12
        L33:
            java.lang.String r0 = "BIT_WORKING_MODE"
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L3c
            goto L12
        L3c:
            r3 = 0
        L3d:
            switch(r3) {
                case 0: goto L4d;
                case 1: goto L48;
                case 2: goto L47;
                case 3: goto L42;
                default: goto L40;
            }
        L40:
            r7 = 0
            return r7
        L42:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)
            return r7
        L47:
            return r4
        L48:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r2)
            return r7
        L4d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.getBitSizeByBitParam(java.lang.String):java.lang.Integer");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public Integer getStartRegisterByParam(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2110570028:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE")) {
                    c = 0;
                    break;
                }
                break;
            case -2106433585:
                if (str.equals("HOLD_GRID_FREQ_CONN_LOW")) {
                    c = 1;
                    break;
                }
                break;
            case -2092080778:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR")) {
                    c = 2;
                    break;
                }
                break;
            case -2091729313:
                if (str.equals("HOLD_AC_CHARGE_END_TIME")) {
                    c = 3;
                    break;
                }
                break;
            case -2070600516:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW_TIME")) {
                    c = 4;
                    break;
                }
                break;
            case -2064663285:
                if (str.equals("HOLD_SET_COMPOSED_PHASE")) {
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
            case -1910210817:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_1")) {
                    c = '\b';
                    break;
                }
                break;
            case -1910210816:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME_2")) {
                    c = '\t';
                    break;
                }
                break;
            case -1876698434:
                if (str.equals("HOLD_FORCED_DISCHG_SOC_LIMIT")) {
                    c = '\n';
                    break;
                }
                break;
            case -1738912721:
                if (str.equals("HOLD_EQUALIZATION_VOLTAGE")) {
                    c = 11;
                    break;
                }
                break;
            case -1662706451:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_1")) {
                    c = '\f';
                    break;
                }
                break;
            case -1662706450:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_2")) {
                    c = '\r';
                    break;
                }
                break;
            case -1657690225:
                if (str.equals("HOLD_COM_ADDR")) {
                    c = 14;
                    break;
                }
                break;
            case -1612429665:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_1")) {
                    c = 15;
                    break;
                }
                break;
            case -1612429664:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_2")) {
                    c = 16;
                    break;
                }
                break;
            case -1563900533:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR")) {
                    c = 17;
                    break;
                }
                break;
            case -1563549068:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME")) {
                    c = 18;
                    break;
                }
                break;
            case -1551617594:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR")) {
                    c = 19;
                    break;
                }
                break;
            case -1551266129:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME")) {
                    c = 20;
                    break;
                }
                break;
            case -1524255375:
                if (str.equals("HOLD_PV_INPUT_MODE")) {
                    c = 21;
                    break;
                }
                break;
            case -1475031011:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE")) {
                    c = 22;
                    break;
                }
                break;
            case -1433471711:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
                    c = 23;
                    break;
                }
                break;
            case -1422719063:
                if (str.equals("HOLD_BATTERY_WARNING_VOLTAGE")) {
                    c = 24;
                    break;
                }
                break;
            case -1413838822:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
                    c = 25;
                    break;
                }
                break;
            case -1367869989:
                if (str.equals("ALL_TO_DEFAULT")) {
                    c = 26;
                    break;
                }
                break;
            case -1352201891:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
                    c = 27;
                    break;
                }
                break;
            case -1345314128:
                if (str.equals("HOLD_EQUALIZATION_PERIOD")) {
                    c = 28;
                    break;
                }
                break;
            case -1323572740:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
                    c = 29;
                    break;
                }
                break;
            case -1294943589:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
                    c = 30;
                    break;
                }
                break;
            case -1282654362:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
                    c = 31;
                    break;
                }
                break;
            case -1274671800:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_1")) {
                    c = ' ';
                    break;
                }
                break;
            case -1274671799:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME_2")) {
                    c = '!';
                    break;
                }
                break;
            case -1254025211:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
                    c = '\"';
                    break;
                }
                break;
            case -1225396060:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
                    c = '#';
                    break;
                }
                break;
            case -1120810945:
                if (str.equals("HOLD_AC_FIRST_START_HOUR_1")) {
                    c = Typography.dollar;
                    break;
                }
                break;
            case -1120810944:
                if (str.equals("HOLD_AC_FIRST_START_HOUR_2")) {
                    c = '%';
                    break;
                }
                break;
            case -1119226968:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_1")) {
                    c = '&';
                    break;
                }
                break;
            case -1119226967:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE_2")) {
                    c = '\'';
                    break;
                }
                break;
            case -1033230202:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_1")) {
                    c = '(';
                    break;
                }
                break;
            case -1033230201:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE_2")) {
                    c = ')';
                    break;
                }
                break;
            case -983412291:
                if (str.equals("HOLD_AC_FIRST_START_MINUTE")) {
                    c = '*';
                    break;
                }
                break;
            case -917268805:
                if (str.equals("HOLD_FORCED_DISCHG_POWER_CMD")) {
                    c = '+';
                    break;
                }
                break;
            case -915649916:
                if (str.equals("HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE")) {
                    c = ',';
                    break;
                }
                break;
            case -875057049:
                if (str.equals("HOLD_GRID_FREQ_CONN_HIGH")) {
                    c = '-';
                    break;
                }
                break;
            case -783053080:
                if (str.equals("HOLD_AC_FIRST_START_TIME_1")) {
                    c = '.';
                    break;
                }
                break;
            case -783053079:
                if (str.equals("HOLD_AC_FIRST_START_TIME_2")) {
                    c = '/';
                    break;
                }
                break;
            case -750853128:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_1")) {
                    c = '0';
                    break;
                }
                break;
            case -750853127:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR_2")) {
                    c = '1';
                    break;
                }
                break;
            case -659327994:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH_TIME")) {
                    c = '2';
                    break;
                }
                break;
            case -613454474:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE")) {
                    c = '3';
                    break;
                }
                break;
            case -595561232:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW")) {
                    c = '4';
                    break;
                }
                break;
            case -594637711:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW")) {
                    c = '5';
                    break;
                }
                break;
            case -593714190:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW")) {
                    c = '6';
                    break;
                }
                break;
            case -573009974:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
                    c = '7';
                    break;
                }
                break;
            case -550997124:
                if (str.equals("HOLD_EQUALIZATION_TIME")) {
                    c = '8';
                    break;
                }
                break;
            case -537681431:
                if (str.equals("HOLD_NOMINAL_BATTERY_VOLTAGE")) {
                    c = '9';
                    break;
                }
                break;
            case -535548714:
                if (str.equals("HOLD_AC_FIRST_END_MINUTE_1")) {
                    c = ':';
                    break;
                }
                break;
            case -535548713:
                if (str.equals("HOLD_AC_FIRST_END_MINUTE_2")) {
                    c = ';';
                    break;
                }
                break;
            case -444930136:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_1")) {
                    c = Typography.less;
                    break;
                }
                break;
            case -444930135:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_2")) {
                    c = '=';
                    break;
                }
                break;
            case -431364128:
                if (str.equals("HOLD_FORCED_CHG_SOC_LIMIT")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case -413095263:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_1")) {
                    c = '?';
                    break;
                }
                break;
            case -413095262:
                if (str.equals("HOLD_FORCED_CHARGE_START_TIME_2")) {
                    c = '@';
                    break;
                }
                break;
            case -384118065:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR")) {
                    c = 'A';
                    break;
                }
                break;
            case -383766600:
                if (str.equals("HOLD_AC_CHARGE_START_TIME")) {
                    c = 'B';
                    break;
                }
                break;
            case -351354951:
                if (str.equals("HOLD_LINE_MODE_INPUT")) {
                    c = 'C';
                    break;
                }
                break;
            case -307531482:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE")) {
                    c = 'D';
                    break;
                }
                break;
            case -276744627:
                if (str.equals("HOLD_TIME")) {
                    c = 'E';
                    break;
                }
                break;
            case -233655236:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_RATE")) {
                    c = 'F';
                    break;
                }
                break;
            case -166403537:
                if (str.equals("HOLD_AC_FIRST_START_MINUTE_1")) {
                    c = 'G';
                    break;
                }
                break;
            case -166403536:
                if (str.equals("HOLD_AC_FIRST_START_MINUTE_2")) {
                    c = 'H';
                    break;
                }
                break;
            case -165590897:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_1")) {
                    c = 'I';
                    break;
                }
                break;
            case -165590896:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_2")) {
                    c = 'J';
                    break;
                }
                break;
            case -135057669:
                if (str.equals("HOLD_DISCHG_POWER_PERCENT_CMD")) {
                    c = 'K';
                    break;
                }
                break;
            case -107172271:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_1")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_LT;
                    break;
                }
                break;
            case -107172270:
                if (str.equals("HOLD_AC_CHARGE_END_TIME_2")) {
                    c = 'M';
                    break;
                }
                break;
            case 20809401:
                if (str.equals("HOLD_AC_CHARGE_BATTERY_CURRENT")) {
                    c = 'N';
                    break;
                }
                break;
            case 33634221:
                if (str.equals("HOLD_BATTERY_LOW_TO_UTILITY_SOC")) {
                    c = 'O';
                    break;
                }
                break;
            case 84521027:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_SOC")) {
                    c = 'P';
                    break;
                }
                break;
            case 120498750:
                if (str.equals("HOLD_OPTIMAL_CHG_DISCHG_TIME")) {
                    c = 'Q';
                    break;
                }
                break;
            case 124739648:
                if (str.equals("HOLD_ON_GRID_EOD_VOLTAGE")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                    break;
                }
                break;
            case 127930925:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR")) {
                    c = 'S';
                    break;
                }
                break;
            case 128282390:
                if (str.equals("HOLD_FORCED_CHARGE_END_TIME")) {
                    c = 'T';
                    break;
                }
                break;
            case 221415899:
                if (str.equals("HOLD_EPS_VOLT_SET")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_UT;
                    break;
                }
                break;
            case 229729985:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_1")) {
                    c = 'V';
                    break;
                }
                break;
            case 229729986:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_2")) {
                    c = 'W';
                    break;
                }
                break;
            case 265183773:
                if (str.equals("HOLD_START_PV_VOLT")) {
                    c = 'X';
                    break;
                }
                break;
            case 313840816:
                if (str.equals("HOLD_GRID_VOLT_CONN_HIGH")) {
                    c = 'Y';
                    break;
                }
                break;
            case 330144381:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_1")) {
                    c = Matrix.MATRIX_TYPE_ZERO;
                    break;
                }
                break;
            case 330144382:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_2")) {
                    c = '[';
                    break;
                }
                break;
            case 367128639:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE")) {
                    c = '\\';
                    break;
                }
                break;
            case 390956452:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR")) {
                    c = ']';
                    break;
                }
                break;
            case 391307917:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_TIME")) {
                    c = '^';
                    break;
                }
                break;
            case 467543035:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE")) {
                    c = '_';
                    break;
                }
                break;
            case 469864614:
                if (str.equals("HOLD_AC_FIRST_END_HOUR_1")) {
                    c = '`';
                    break;
                }
                break;
            case 469864615:
                if (str.equals("HOLD_AC_FIRST_END_HOUR_2")) {
                    c = 'a';
                    break;
                }
                break;
            case 528065501:
                if (str.equals("HOLD_FORCED_CHG_POWER_CMD")) {
                    c = 'b';
                    break;
                }
                break;
            case 530676685:
                if (str.equals("HOLD_AC_FIRST_START_HOUR")) {
                    c = 'c';
                    break;
                }
                break;
            case 531028150:
                if (str.equals("HOLD_AC_FIRST_START_TIME")) {
                    c = 'd';
                    break;
                }
                break;
            case 565590738:
                if (str.equals("HOLD_EPS_FREQ_SET")) {
                    c = 'e';
                    break;
                }
                break;
            case 567487850:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_1")) {
                    c = 'f';
                    break;
                }
                break;
            case 567487851:
                if (str.equals("HOLD_AC_CHARGE_START_TIME_2")) {
                    c = 'g';
                    break;
                }
                break;
            case 574683163:
                if (str.equals("HOLD_SET_MASTER_OR_SLAVE")) {
                    c = 'h';
                    break;
                }
                break;
            case 607263268:
                if (str.equals("HOLD_AC_FIRST_END_MINUTE")) {
                    c = 'i';
                    break;
                }
                break;
            case 623306801:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_1")) {
                    c = 'j';
                    break;
                }
                break;
            case 623306802:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_2")) {
                    c = 'k';
                    break;
                }
                break;
            case 667902246:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_1")) {
                    c = 'l';
                    break;
                }
                break;
            case 667902247:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_TIME_2")) {
                    c = 'm';
                    break;
                }
                break;
            case 689327954:
                if (str.equals("HOLD_BATTERY_WARNING_SOC")) {
                    c = 'n';
                    break;
                }
                break;
            case 719765641:
                if (str.equals("HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE")) {
                    c = 'o';
                    break;
                }
                break;
            case 767099658:
                if (str.equals("HOLD_DISCHG_CUT_OFF_SOC_EOD")) {
                    c = 'p';
                    break;
                }
                break;
            case 807622479:
                if (str.equals("HOLD_AC_FIRST_END_TIME_1")) {
                    c = 'q';
                    break;
                }
                break;
            case 807622480:
                if (str.equals("HOLD_AC_FIRST_END_TIME_2")) {
                    c = 'r';
                    break;
                }
                break;
            case 814992216:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_1")) {
                    c = 's';
                    break;
                }
                break;
            case 814992217:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_2")) {
                    c = 't';
                    break;
                }
                break;
            case 885404148:
                if (str.equals("HOLD_AC_FIRST_END_HOUR")) {
                    c = 'u';
                    break;
                }
                break;
            case 885755613:
                if (str.equals("HOLD_AC_FIRST_END_TIME")) {
                    c = 'v';
                    break;
                }
                break;
            case 894260725:
                if (str.equals("HOLD_AC_CHARGE_SOC_LIMIT")) {
                    c = 'w';
                    break;
                }
                break;
            case 946846866:
                if (str.equals("HOLD_SOC_LOW_LIMIT_EPS_DISCHG")) {
                    c = 'x';
                    break;
                }
                break;
            case 976683416:
                if (str.equals("HOLD_OUTPUT_CONFIGURATION")) {
                    c = 'y';
                    break;
                }
                break;
            case 977893426:
                if (str.equals("HOLD_BATTERY_WARNING_RECOVERY_SOC")) {
                    c = 'z';
                    break;
                }
                break;
            case 1039780741:
                if (str.equals("HOLD_FLOATING_VOLTAGE")) {
                    c = '{';
                    break;
                }
                break;
            case 1118506598:
                if (str.equals("HOLD_GRID_VOLT_CONN_LOW")) {
                    c = '|';
                    break;
                }
                break;
            case 1203310617:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW")) {
                    c = '}';
                    break;
                }
                break;
            case 1204234138:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW")) {
                    c = '~';
                    break;
                }
                break;
            case 1205157659:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW")) {
                    c = Ascii.MAX;
                    break;
                }
                break;
            case 1527620681:
                if (str.equals("HOLD_MAX_GENERATOR_INPUT_POWER")) {
                    c = 128;
                    break;
                }
                break;
            case 1600103487:
                if (str.equals("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
                    c = 129;
                    break;
                }
                break;
            case 1853690354:
                if (str.equals("HOLD_AC_CHARGE_POWER_CMD")) {
                    c = 130;
                    break;
                }
                break;
            case 1890826442:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_SOC")) {
                    c = 131;
                    break;
                }
                break;
            case 1925850636:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_RATE")) {
                    c = 132;
                    break;
                }
                break;
            case 1947566668:
                if (str.equals("HOLD_BATTERY_CAPACITY")) {
                    c = 133;
                    break;
                }
                break;
            case 1985523803:
                if (str.equals("HOLD_CHARGE_POWER_PERCENT_CMD")) {
                    c = 134;
                    break;
                }
                break;
            case 2046998614:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_1")) {
                    c = 135;
                    break;
                }
                break;
            case 2046998615:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_2")) {
                    c = 136;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case ']':
            case '^':
                return 84;
            case 1:
                return 27;
            case 2:
            case 3:
            case 'D':
                return 69;
            case 4:
                return 31;
            case 5:
                return 113;
            case 6:
                return 103;
            case 7:
                return 99;
            case '\b':
            case '(':
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA /* 135 */:
                return 86;
            case '\t':
            case ')':
            case 136:
                return 88;
            case '\n':
                return 83;
            case 11:
                return 149;
            case '\f':
            case 'Z':
            case 'l':
                return 87;
            case '\r':
            case '[':
            case 'm':
                return 89;
            case 14:
                return 15;
            case 15:
            case ' ':
            case 'I':
                return 79;
            case 16:
            case '!':
            case 'J':
                return 81;
            case 17:
            case 18:
            case '_':
                return 85;
            case 19:
            case 20:
            case '3':
                return 76;
            case 21:
                return 20;
            case 22:
            case 'S':
            case 'T':
                return 77;
            case 23:
                return 158;
            case 24:
                return 162;
            case 25:
                return 159;
            case 26:
                return 11;
            case 27:
                return 43;
            case 28:
                return 150;
            case 29:
                return 47;
            case 30:
                return 51;
            case 31:
                return 30;
            case '\"':
                return 34;
            case '#':
                return 38;
            case '$':
            case '.':
            case 'G':
                return 154;
            case '%':
            case '/':
            case 'H':
                return 156;
            case '&':
            case '0':
            case '?':
                return 78;
            case '\'':
            case '1':
            case '@':
                return 80;
            case '*':
            case 'c':
            case 'd':
                return 152;
            case '+':
                return 82;
            case ',':
                return 166;
            case '-':
                return 28;
            case '2':
                return 32;
            case '4':
                return 29;
            case '5':
                return 33;
            case '6':
                return 37;
            case '7':
                return 100;
            case '8':
                return 151;
            case '9':
                return 148;
            case ':':
            case '`':
            case 'q':
                return 155;
            case ';':
            case 'a':
            case 'r':
                return 157;
            case '<':
            case 'L':
            case 's':
                return 71;
            case '=':
            case 'M':
            case 't':
                return 73;
            case '>':
                return 75;
            case 'A':
            case 'B':
            case '\\':
                return 68;
            case 'C':
                return 146;
            case 'E':
                return 12;
            case 'F':
                return 102;
            case 'K':
                return 65;
            case 'N':
                return Integer.valueOf(CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256);
            case 'O':
                return 167;
            case 'P':
                return 161;
            case 'Q':
                return 126;
            case 'R':
                return 169;
            case 'U':
                return 90;
            case 'V':
            case 'f':
            case 'j':
                return 70;
            case 'W':
            case 'g':
            case 'k':
                return 72;
            case 'X':
                return 22;
            case 'Y':
                return 26;
            case 'b':
                return 74;
            case 'e':
                return 91;
            case 'h':
                return 112;
            case 'i':
            case 'u':
            case 'v':
                return 153;
            case 'n':
                return 164;
            case 'o':
                return 163;
            case 'p':
                return 105;
            case 'w':
                return 67;
            case 'x':
                return 125;
            case 'y':
                return 145;
            case 'z':
                return 165;
            case '{':
                return Integer.valueOf(CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA);
            case '|':
                return 25;
            case '}':
                return 42;
            case '~':
                return 46;
            case 127:
                return 50;
            case 128:
                return 177;
            case Opcodes.LOR /* 129 */:
                return 41;
            case Opcodes.IXOR /* 130 */:
                return 66;
            case 131:
                return 160;
            case 132:
                return 101;
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA /* 133 */:
                return 147;
            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA /* 134 */:
                return 64;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public Integer getValueByParam(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2106433585:
                if (str.equals("HOLD_GRID_FREQ_CONN_LOW")) {
                    c = 0;
                    break;
                }
                break;
            case -2051169280:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG")) {
                    c = 1;
                    break;
                }
                break;
            case -1922746271:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_VOLT_REF")) {
                    c = 2;
                    break;
                }
                break;
            case -1854323434:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG")) {
                    c = 3;
                    break;
                }
                break;
            case -1738912721:
                if (str.equals("HOLD_EQUALIZATION_VOLTAGE")) {
                    c = 4;
                    break;
                }
                break;
            case -1605958379:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG")) {
                    c = 5;
                    break;
                }
                break;
            case -1433471711:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
                    c = 6;
                    break;
                }
                break;
            case -1422719063:
                if (str.equals("HOLD_BATTERY_WARNING_VOLTAGE")) {
                    c = 7;
                    break;
                }
                break;
            case -1413838822:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
                    c = '\b';
                    break;
                }
                break;
            case -1352201891:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
                    c = '\t';
                    break;
                }
                break;
            case -1323572740:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
                    c = '\n';
                    break;
                }
                break;
            case -1294943589:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
                    c = 11;
                    break;
                }
                break;
            case -1282654362:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
                    c = '\f';
                    break;
                }
                break;
            case -1254025211:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
                    c = '\r';
                    break;
                }
                break;
            case -1225396060:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
                    c = 14;
                    break;
                }
                break;
            case -915649916:
                if (str.equals("HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE")) {
                    c = 15;
                    break;
                }
                break;
            case -875057049:
                if (str.equals("HOLD_GRID_FREQ_CONN_HIGH")) {
                    c = 16;
                    break;
                }
                break;
            case -595561232:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW")) {
                    c = 17;
                    break;
                }
                break;
            case -594637711:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW")) {
                    c = 18;
                    break;
                }
                break;
            case -593714190:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW")) {
                    c = 19;
                    break;
                }
                break;
            case -573009974:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
                    c = 20;
                    break;
                }
                break;
            case -537681431:
                if (str.equals("HOLD_NOMINAL_BATTERY_VOLTAGE")) {
                    c = 21;
                    break;
                }
                break;
            case 124739648:
                if (str.equals("HOLD_ON_GRID_EOD_VOLTAGE")) {
                    c = 22;
                    break;
                }
                break;
            case 180157537:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG")) {
                    c = 23;
                    break;
                }
                break;
            case 265183773:
                if (str.equals("HOLD_START_PV_VOLT")) {
                    c = 24;
                    break;
                }
                break;
            case 268168589:
                if (str.equals("HOLD_V1H")) {
                    c = 25;
                    break;
                }
                break;
            case 268168593:
                if (str.equals("HOLD_V1L")) {
                    c = 26;
                    break;
                }
                break;
            case 268168620:
                if (str.equals("HOLD_V2H")) {
                    c = 27;
                    break;
                }
                break;
            case 268168624:
                if (str.equals("HOLD_V2L")) {
                    c = 28;
                    break;
                }
                break;
            case 313840816:
                if (str.equals("HOLD_GRID_VOLT_CONN_HIGH")) {
                    c = 29;
                    break;
                }
                break;
            case 719765641:
                if (str.equals("HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE")) {
                    c = 30;
                    break;
                }
                break;
            case 1039780741:
                if (str.equals("HOLD_FLOATING_VOLTAGE")) {
                    c = 31;
                    break;
                }
                break;
            case 1118506598:
                if (str.equals("HOLD_GRID_VOLT_CONN_LOW")) {
                    c = ' ';
                    break;
                }
                break;
            case 1203310617:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW")) {
                    c = '!';
                    break;
                }
                break;
            case 1204234138:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case 1205157659:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW")) {
                    c = '#';
                    break;
                }
                break;
            case 1504508315:
                if (str.equals("HOLD_VBAT_START_DERATING")) {
                    c = Typography.dollar;
                    break;
                }
                break;
            case 1600103487:
                if (str.equals("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
                    c = '%';
                    break;
                }
                break;
            case 2069975635:
                if (str.equals("HOLD_POWER_SOFT_START_SLOPE")) {
                    c = Typography.amp;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case '\t':
            case '\n':
            case 11:
            case 16:
            case '!':
            case '\"':
            case '#':
                Double d = toDouble(str2);
                if (d != null) {
                    return Integer.valueOf((int) Double.valueOf(d.doubleValue() * 100.0d).doubleValue());
                }
                return null;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '$':
            case '%':
            case '&':
                Double d2 = toDouble(str2);
                if (d2 != null) {
                    return Integer.valueOf((int) Double.valueOf(d2.doubleValue() * 10.0d).doubleValue());
                }
                return null;
            default:
                return toInt(str2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:466:0x0631 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean checkValueValid(java.lang.String r17, int r18) {
        /*
            Method dump skipped, instructions count: 2374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.checkValueValid(java.lang.String, int):boolean");
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
            case "FUNC_BAT_SHARED":
                return 110;
            case "FUNC_AC_CHARGE":
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
    /* JADX WARN: Removed duplicated region for block: B:4:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getBitByFunction(java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.view.local.fragment.LocalOffGridSetFragment.getBitByFunction(java.lang.String):java.lang.Integer");
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
        if (i == 0) {
            return new String[]{"HOLD_MODEL"};
        }
        if (i == 2) {
            return new String[]{"HOLD_SERIAL_NUM"};
        }
        if (i != 7) {
            if (i == 15) {
                return new String[]{"HOLD_COM_ADDR"};
            }
            if (i == 16) {
                return new String[]{"HOLD_LANGUAGE"};
            }
            if (i == 112) {
                return new String[]{"HOLD_SET_MASTER_OR_SLAVE"};
            }
            if (i != 113) {
                switch (i) {
                    case 7:
                        break;
                    case 12:
                        return new String[]{"HOLD_TIME"};
                    case 20:
                        return new String[]{"HOLD_PV_INPUT_MODE"};
                    case 116:
                        return new String[]{"HOLD_P_TO_USER_START_DISCHG"};
                    case 118:
                        return new String[]{"HOLD_VBAT_START_DERATING"};
                    case 122:
                        return new String[]{"HOLD_MAINTENANCE_COUNT"};
                    case 125:
                        return new String[]{"HOLD_SOC_LOW_LIMIT_EPS_DISCHG"};
                    case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA /* 137 */:
                        return new String[]{"HOLD_SPEC_LOAD_COMPENSATE"};
                    case 177:
                        return new String[]{"HOLD_MAX_GENERATOR_INPUT_POWER"};
                    default:
                        switch (i) {
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
                            default:
                                switch (i) {
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
                                    default:
                                        switch (i) {
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
                                            default:
                                                switch (i) {
                                                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /* 144 */:
                                                        return new String[]{"HOLD_FLOATING_VOLTAGE"};
                                                    case 145:
                                                        return new String[]{"HOLD_OUTPUT_CONFIGURATION"};
                                                    case 146:
                                                        return new String[]{"HOLD_LINE_MODE_INPUT"};
                                                    case 147:
                                                        return new String[]{"HOLD_BATTERY_CAPACITY"};
                                                    case 148:
                                                        return new String[]{"HOLD_NOMINAL_BATTERY_VOLTAGE"};
                                                    case 149:
                                                        return new String[]{"HOLD_EQUALIZATION_VOLTAGE"};
                                                    case 150:
                                                        return new String[]{"HOLD_EQUALIZATION_PERIOD"};
                                                    case 151:
                                                        return new String[]{"HOLD_EQUALIZATION_TIME"};
                                                    case 152:
                                                        return new String[]{"HOLD_AC_FIRST_START_HOUR", "HOLD_AC_FIRST_START_MINUTE"};
                                                    case 153:
                                                        return new String[]{"HOLD_AC_FIRST_END_HOUR", "HOLD_AC_FIRST_END_MINUTE"};
                                                    case 154:
                                                        return new String[]{"HOLD_AC_FIRST_START_HOUR_1", "HOLD_AC_FIRST_START_MINUTE_1"};
                                                    case 155:
                                                        return new String[]{"HOLD_AC_FIRST_END_HOUR_1", "HOLD_AC_FIRST_END_MINUTE_1"};
                                                    case 156:
                                                        return new String[]{"HOLD_AC_FIRST_START_HOUR_2", "HOLD_AC_FIRST_START_MINUTE_2"};
                                                    case 157:
                                                        return new String[]{"HOLD_AC_FIRST_END_HOUR_2", "HOLD_AC_FIRST_END_MINUTE_2"};
                                                    case 158:
                                                        return new String[]{"HOLD_AC_CHARGE_START_BATTERY_VOLTAGE"};
                                                    case 159:
                                                        return new String[]{"HOLD_AC_CHARGE_END_BATTERY_VOLTAGE"};
                                                    case 160:
                                                        return new String[]{"HOLD_AC_CHARGE_START_BATTERY_SOC"};
                                                    case 161:
                                                        return new String[]{"HOLD_AC_CHARGE_END_BATTERY_SOC"};
                                                    case 162:
                                                        return new String[]{"HOLD_BATTERY_WARNING_VOLTAGE"};
                                                    case 163:
                                                        return new String[]{"HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE"};
                                                    case 164:
                                                        return new String[]{"HOLD_BATTERY_WARNING_SOC"};
                                                    case 165:
                                                        return new String[]{"HOLD_BATTERY_WARNING_RECOVERY_SOC"};
                                                    case 166:
                                                        return new String[]{"HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE"};
                                                    case 167:
                                                        return new String[]{"HOLD_BATTERY_LOW_TO_UTILITY_SOC"};
                                                    case CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256 /* 168 */:
                                                        return new String[]{"HOLD_AC_CHARGE_BATTERY_CURRENT"};
                                                    case 169:
                                                        return new String[]{"HOLD_ON_GRID_EOD_VOLTAGE"};
                                                    default:
                                                        return null;
                                                }
                                        }
                                }
                        }
                }
            } else {
                return new String[]{"HOLD_SET_COMPOSED_PHASE"};
            }
        }
        return new String[]{"HOLD_FW_CODE"};
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public String getValueShowText(String str, int i, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2110570028:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_MINUTE")) {
                    c = 0;
                    break;
                }
                break;
            case -2106433585:
                if (str.equals("HOLD_GRID_FREQ_CONN_LOW")) {
                    c = 1;
                    break;
                }
                break;
            case -2092080778:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR")) {
                    c = 2;
                    break;
                }
                break;
            case -2070600516:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW_TIME")) {
                    c = 3;
                    break;
                }
                break;
            case -2064663285:
                if (str.equals("HOLD_SET_COMPOSED_PHASE")) {
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
            case -1876698434:
                if (str.equals("HOLD_FORCED_DISCHG_SOC_LIMIT")) {
                    c = '\b';
                    break;
                }
                break;
            case -1854323434:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG")) {
                    c = '\t';
                    break;
                }
                break;
            case -1784648912:
                if (str.equals("HOLD_P_TO_USER_START_DISCHG")) {
                    c = '\n';
                    break;
                }
                break;
            case -1738912721:
                if (str.equals("HOLD_EQUALIZATION_VOLTAGE")) {
                    c = 11;
                    break;
                }
                break;
            case -1662706451:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_1")) {
                    c = '\f';
                    break;
                }
                break;
            case -1662706450:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE_2")) {
                    c = '\r';
                    break;
                }
                break;
            case -1657690225:
                if (str.equals("HOLD_COM_ADDR")) {
                    c = 14;
                    break;
                }
                break;
            case -1612429665:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_1")) {
                    c = 15;
                    break;
                }
                break;
            case -1612429664:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR_2")) {
                    c = 16;
                    break;
                }
                break;
            case -1605958379:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG")) {
                    c = 17;
                    break;
                }
                break;
            case -1579641573:
                if (str.equals("HOLD_FW_CODE")) {
                    c = 18;
                    break;
                }
                break;
            case -1563900533:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR")) {
                    c = 19;
                    break;
                }
                break;
            case -1551617594:
                if (str.equals("HOLD_FORCED_CHARGE_START_HOUR")) {
                    c = 20;
                    break;
                }
                break;
            case -1524255375:
                if (str.equals("HOLD_PV_INPUT_MODE")) {
                    c = 21;
                    break;
                }
                break;
            case -1475031011:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE")) {
                    c = 22;
                    break;
                }
                break;
            case -1433471711:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_VOLTAGE")) {
                    c = 23;
                    break;
                }
                break;
            case -1422719063:
                if (str.equals("HOLD_BATTERY_WARNING_VOLTAGE")) {
                    c = 24;
                    break;
                }
                break;
            case -1413838822:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_VOLTAGE")) {
                    c = 25;
                    break;
                }
                break;
            case -1352201891:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH")) {
                    c = 26;
                    break;
                }
                break;
            case -1345314128:
                if (str.equals("HOLD_EQUALIZATION_PERIOD")) {
                    c = 27;
                    break;
                }
                break;
            case -1323572740:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH")) {
                    c = 28;
                    break;
                }
                break;
            case -1294943589:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH")) {
                    c = 29;
                    break;
                }
                break;
            case -1282654362:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH")) {
                    c = 30;
                    break;
                }
                break;
            case -1254025211:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH")) {
                    c = 31;
                    break;
                }
                break;
            case -1225396060:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH")) {
                    c = ' ';
                    break;
                }
                break;
            case -1177670356:
                if (str.equals("HOLD_SPEC_LOAD_COMPENSATE")) {
                    c = '!';
                    break;
                }
                break;
            case -1120810945:
                if (str.equals("HOLD_AC_FIRST_START_HOUR_1")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case -1120810944:
                if (str.equals("HOLD_AC_FIRST_START_HOUR_2")) {
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
            case -983412291:
                if (str.equals("HOLD_AC_FIRST_START_MINUTE")) {
                    c = '(';
                    break;
                }
                break;
            case -917268805:
                if (str.equals("HOLD_FORCED_DISCHG_POWER_CMD")) {
                    c = ')';
                    break;
                }
                break;
            case -915649916:
                if (str.equals("HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE")) {
                    c = '*';
                    break;
                }
                break;
            case -875057049:
                if (str.equals("HOLD_GRID_FREQ_CONN_HIGH")) {
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
            case -659862417:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_HIGH_TIME")) {
                    c = '.';
                    break;
                }
                break;
            case -659327994:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_HIGH_TIME")) {
                    c = '/';
                    break;
                }
                break;
            case -613454474:
                if (str.equals("HOLD_FORCED_CHARGE_START_MINUTE")) {
                    c = '0';
                    break;
                }
                break;
            case -595561232:
                if (str.equals("HOLD_GRID_VOLT_LIMIT1_LOW")) {
                    c = '1';
                    break;
                }
                break;
            case -594637711:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW")) {
                    c = '2';
                    break;
                }
                break;
            case -593714190:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW")) {
                    c = '3';
                    break;
                }
                break;
            case -573009974:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT")) {
                    c = '4';
                    break;
                }
                break;
            case -550997124:
                if (str.equals("HOLD_EQUALIZATION_TIME")) {
                    c = '5';
                    break;
                }
                break;
            case -537681431:
                if (str.equals("HOLD_NOMINAL_BATTERY_VOLTAGE")) {
                    c = '6';
                    break;
                }
                break;
            case -535548714:
                if (str.equals("HOLD_AC_FIRST_END_MINUTE_1")) {
                    c = '7';
                    break;
                }
                break;
            case -535548713:
                if (str.equals("HOLD_AC_FIRST_END_MINUTE_2")) {
                    c = '8';
                    break;
                }
                break;
            case -444930136:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_1")) {
                    c = '9';
                    break;
                }
                break;
            case -444930135:
                if (str.equals("HOLD_AC_CHARGE_END_HOUR_2")) {
                    c = ':';
                    break;
                }
                break;
            case -431364128:
                if (str.equals("HOLD_FORCED_CHG_SOC_LIMIT")) {
                    c = ';';
                    break;
                }
                break;
            case -384118065:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR")) {
                    c = Typography.less;
                    break;
                }
                break;
            case -351354951:
                if (str.equals("HOLD_LINE_MODE_INPUT")) {
                    c = '=';
                    break;
                }
                break;
            case -307531482:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case -276744627:
                if (str.equals("HOLD_TIME")) {
                    c = '?';
                    break;
                }
                break;
            case -233655236:
                if (str.equals("HOLD_LEAD_ACID_DISCHARGE_RATE")) {
                    c = '@';
                    break;
                }
                break;
            case -166403537:
                if (str.equals("HOLD_AC_FIRST_START_MINUTE_1")) {
                    c = 'A';
                    break;
                }
                break;
            case -166403536:
                if (str.equals("HOLD_AC_FIRST_START_MINUTE_2")) {
                    c = 'B';
                    break;
                }
                break;
            case -165590897:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_1")) {
                    c = 'C';
                    break;
                }
                break;
            case -165590896:
                if (str.equals("HOLD_FORCED_CHARGE_END_MINUTE_2")) {
                    c = 'D';
                    break;
                }
                break;
            case -135057669:
                if (str.equals("HOLD_DISCHG_POWER_PERCENT_CMD")) {
                    c = 'E';
                    break;
                }
                break;
            case -26098721:
                if (str.equals("HOLD_REACTIVE_POWER_CMD_TYPE")) {
                    c = 'F';
                    break;
                }
                break;
            case 4556681:
                if (str.equals("HOLD_MODEL")) {
                    c = 'G';
                    break;
                }
                break;
            case 20809401:
                if (str.equals("HOLD_AC_CHARGE_BATTERY_CURRENT")) {
                    c = 'H';
                    break;
                }
                break;
            case 33634221:
                if (str.equals("HOLD_BATTERY_LOW_TO_UTILITY_SOC")) {
                    c = 'I';
                    break;
                }
                break;
            case 41202161:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_HIGH_TIME")) {
                    c = 'J';
                    break;
                }
                break;
            case 41736584:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_HIGH_TIME")) {
                    c = 'K';
                    break;
                }
                break;
            case 84521027:
                if (str.equals("HOLD_AC_CHARGE_END_BATTERY_SOC")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_LT;
                    break;
                }
                break;
            case 124739648:
                if (str.equals("HOLD_ON_GRID_EOD_VOLTAGE")) {
                    c = 'M';
                    break;
                }
                break;
            case 127930925:
                if (str.equals("HOLD_FORCED_CHARGE_END_HOUR")) {
                    c = 'N';
                    break;
                }
                break;
            case 159565091:
                if (str.equals("HOLD_MAINTENANCE_COUNT")) {
                    c = 'O';
                    break;
                }
                break;
            case 168754545:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW_TIME")) {
                    c = 'P';
                    break;
                }
                break;
            case 180157537:
                if (str.equals("HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG")) {
                    c = 'Q';
                    break;
                }
                break;
            case 221415899:
                if (str.equals("HOLD_EPS_VOLT_SET")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                    break;
                }
                break;
            case 225083931:
                if (str.equals("HOLD_SERIAL_NUM")) {
                    c = 'S';
                    break;
                }
                break;
            case 229729985:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_1")) {
                    c = 'T';
                    break;
                }
                break;
            case 229729986:
                if (str.equals("HOLD_AC_CHARGE_START_HOUR_2")) {
                    c = Matrix.MATRIX_TYPE_RANDOM_UT;
                    break;
                }
                break;
            case 265183773:
                if (str.equals("HOLD_START_PV_VOLT")) {
                    c = 'V';
                    break;
                }
                break;
            case 268168589:
                if (str.equals("HOLD_V1H")) {
                    c = 'W';
                    break;
                }
                break;
            case 268168593:
                if (str.equals("HOLD_V1L")) {
                    c = 'X';
                    break;
                }
                break;
            case 268168620:
                if (str.equals("HOLD_V2H")) {
                    c = 'Y';
                    break;
                }
                break;
            case 268168624:
                if (str.equals("HOLD_V2L")) {
                    c = Matrix.MATRIX_TYPE_ZERO;
                    break;
                }
                break;
            case 313840816:
                if (str.equals("HOLD_GRID_VOLT_CONN_HIGH")) {
                    c = '[';
                    break;
                }
                break;
            case 330144381:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_1")) {
                    c = '\\';
                    break;
                }
                break;
            case 330144382:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_HOUR_2")) {
                    c = ']';
                    break;
                }
                break;
            case 365268050:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW_TIME")) {
                    c = '^';
                    break;
                }
                break;
            case 367128639:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE")) {
                    c = '_';
                    break;
                }
                break;
            case 390956452:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR")) {
                    c = '`';
                    break;
                }
                break;
            case 467543035:
                if (str.equals("HOLD_FORCED_DISCHARGE_END_MINUTE")) {
                    c = 'a';
                    break;
                }
                break;
            case 469864614:
                if (str.equals("HOLD_AC_FIRST_END_HOUR_1")) {
                    c = 'b';
                    break;
                }
                break;
            case 469864615:
                if (str.equals("HOLD_AC_FIRST_END_HOUR_2")) {
                    c = 'c';
                    break;
                }
                break;
            case 528065501:
                if (str.equals("HOLD_FORCED_CHG_POWER_CMD")) {
                    c = 'd';
                    break;
                }
                break;
            case 530676685:
                if (str.equals("HOLD_AC_FIRST_START_HOUR")) {
                    c = 'e';
                    break;
                }
                break;
            case 561781555:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW_TIME")) {
                    c = 'f';
                    break;
                }
                break;
            case 565590738:
                if (str.equals("HOLD_EPS_FREQ_SET")) {
                    c = 'g';
                    break;
                }
                break;
            case 574683163:
                if (str.equals("HOLD_SET_MASTER_OR_SLAVE")) {
                    c = 'h';
                    break;
                }
                break;
            case 607263268:
                if (str.equals("HOLD_AC_FIRST_END_MINUTE")) {
                    c = 'i';
                    break;
                }
                break;
            case 623306801:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_1")) {
                    c = 'j';
                    break;
                }
                break;
            case 623306802:
                if (str.equals("HOLD_AC_CHARGE_START_MINUTE_2")) {
                    c = 'k';
                    break;
                }
                break;
            case 677731373:
                if (str.equals("HOLD_ACTIVE_POWER_PERCENT_CMD")) {
                    c = 'l';
                    break;
                }
                break;
            case 689327954:
                if (str.equals("HOLD_BATTERY_WARNING_SOC")) {
                    c = 'm';
                    break;
                }
                break;
            case 719765641:
                if (str.equals("HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE")) {
                    c = 'n';
                    break;
                }
                break;
            case 767099658:
                if (str.equals("HOLD_DISCHG_CUT_OFF_SOC_EOD")) {
                    c = 'o';
                    break;
                }
                break;
            case 814992216:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_1")) {
                    c = 'p';
                    break;
                }
                break;
            case 814992217:
                if (str.equals("HOLD_AC_CHARGE_END_MINUTE_2")) {
                    c = 'q';
                    break;
                }
                break;
            case 885404148:
                if (str.equals("HOLD_AC_FIRST_END_HOUR")) {
                    c = 'r';
                    break;
                }
                break;
            case 894260725:
                if (str.equals("HOLD_AC_CHARGE_SOC_LIMIT")) {
                    c = 's';
                    break;
                }
                break;
            case 946846866:
                if (str.equals("HOLD_SOC_LOW_LIMIT_EPS_DISCHG")) {
                    c = 't';
                    break;
                }
                break;
            case 976683416:
                if (str.equals("HOLD_OUTPUT_CONFIGURATION")) {
                    c = 'u';
                    break;
                }
                break;
            case 977893426:
                if (str.equals("HOLD_BATTERY_WARNING_RECOVERY_SOC")) {
                    c = 'v';
                    break;
                }
                break;
            case 1039780741:
                if (str.equals("HOLD_FLOATING_VOLTAGE")) {
                    c = 'w';
                    break;
                }
                break;
            case 1118506598:
                if (str.equals("HOLD_GRID_VOLT_CONN_LOW")) {
                    c = 'x';
                    break;
                }
                break;
            case 1203310617:
                if (str.equals("HOLD_GRID_FREQ_LIMIT1_LOW")) {
                    c = 'y';
                    break;
                }
                break;
            case 1204234138:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_LOW")) {
                    c = 'z';
                    break;
                }
                break;
            case 1205157659:
                if (str.equals("HOLD_GRID_FREQ_LIMIT3_LOW")) {
                    c = '{';
                    break;
                }
                break;
            case 1457278549:
                if (str.equals("HOLD_RECONNECT_TIME")) {
                    c = '|';
                    break;
                }
                break;
            case 1504508315:
                if (str.equals("HOLD_VBAT_START_DERATING")) {
                    c = '}';
                    break;
                }
                break;
            case 1527620681:
                if (str.equals("HOLD_MAX_GENERATOR_INPUT_POWER")) {
                    c = '~';
                    break;
                }
                break;
            case 1600103487:
                if (str.equals("HOLD_GRID_VOLT_MOV_AVG_HIGH")) {
                    c = Ascii.MAX;
                    break;
                }
                break;
            case 1831339770:
                if (str.equals("HOLD_GRID_VOLT_LIMIT3_LOW_TIME")) {
                    c = 128;
                    break;
                }
                break;
            case 1838153520:
                if (str.equals("HOLD_GRID_FREQ_LIMIT2_HIGH_TIME")) {
                    c = 129;
                    break;
                }
                break;
            case 1838687943:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_HIGH_TIME")) {
                    c = 130;
                    break;
                }
                break;
            case 1853690354:
                if (str.equals("HOLD_AC_CHARGE_POWER_CMD")) {
                    c = 131;
                    break;
                }
                break;
            case 1890826442:
                if (str.equals("HOLD_AC_CHARGE_START_BATTERY_SOC")) {
                    c = 132;
                    break;
                }
                break;
            case 1925850636:
                if (str.equals("HOLD_LEAD_ACID_CHARGE_RATE")) {
                    c = 133;
                    break;
                }
                break;
            case 1947566668:
                if (str.equals("HOLD_BATTERY_CAPACITY")) {
                    c = 134;
                    break;
                }
                break;
            case 1985523803:
                if (str.equals("HOLD_CHARGE_POWER_PERCENT_CMD")) {
                    c = 135;
                    break;
                }
                break;
            case 2020464802:
                if (str.equals("HOLD_CONNECT_TIME")) {
                    c = 136;
                    break;
                }
                break;
            case 2027853275:
                if (str.equals("HOLD_GRID_VOLT_LIMIT2_LOW_TIME")) {
                    c = 137;
                    break;
                }
                break;
            case 2036981088:
                if (str.equals("HOLD_REACTIVE_POWER_PERCENT_CMD")) {
                    c = 138;
                    break;
                }
                break;
            case 2046998614:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_1")) {
                    c = 139;
                    break;
                }
                break;
            case 2046998615:
                if (str.equals("HOLD_FORCED_DISCHARGE_START_HOUR_2")) {
                    c = 140;
                    break;
                }
                break;
            case 2069975635:
                if (str.equals("HOLD_POWER_SOFT_START_SLOPE")) {
                    c = 141;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case '\f':
            case '\r':
            case 22:
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case '0':
            case '7':
            case '8':
            case '>':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case '_':
            case 'a':
            case 'i':
            case 'j':
            case 'k':
            case 'p':
            case 'q':
                return ProTool.fillZeros(String.valueOf(getInt2HighParamValue(str, i, str2)), 2);
            case 1:
            case 26:
            case 28:
            case 29:
            case '+':
            case 'y':
            case 'z':
            case '{':
                return InvTool.formatData(getInt2ParamValue(str, i, str2) / 100.0d);
            case 2:
            case 15:
            case 16:
            case 19:
            case 20:
            case '\"':
            case '#':
            case ',':
            case '-':
            case '9':
            case ':':
            case '<':
            case 'N':
            case 'T':
            case 'U':
            case '\\':
            case ']':
            case '`':
            case 'b':
            case 'c':
            case 'e':
            case 'r':
            case 139:
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /* 140 */:
                return ProTool.fillZeros(String.valueOf(getInt2LowParamValue(str, i, str2)), 2);
            case 3:
            case 4:
            case 6:
            case '\b':
            case '\n':
            case 14:
            case 21:
            case 27:
            case '!':
            case ')':
            case '.':
            case '/':
            case '5':
            case ';':
            case '=':
            case '@':
            case 'E':
            case 'F':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'O':
            case 'P':
            case 'R':
            case '^':
            case 'd':
            case 'f':
            case 'g':
            case 'h':
            case 'l':
            case 'm':
            case 'o':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case '|':
            case '~':
            case 128:
            case Opcodes.LOR /* 129 */:
            case Opcodes.IXOR /* 130 */:
            case 131:
            case 132:
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA /* 133 */:
            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA /* 134 */:
            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA /* 135 */:
            case 136:
            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA /* 137 */:
            case CipherSuite.TLS_PSK_WITH_RC4_128_SHA /* 138 */:
                return String.valueOf(getInt2ParamValue(str, i, str2));
            case 5:
            case '\t':
            case 17:
            case 'Q':
                return InvTool.formatData(getInt2ParamValueN(str, i, str2) / 10.0d);
            case 7:
            case 11:
            case 23:
            case 24:
            case 25:
            case 30:
            case 31:
            case ' ':
            case '*':
            case '1':
            case '2':
            case '3':
            case '4':
            case '6':
            case 'M':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case 'n':
            case 'w':
            case 'x':
            case '}':
            case 127:
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /* 141 */:
                return InvTool.formatData(getInt2ParamValue(str, i, str2) / 10.0d);
            case 18:
                int valueStartIndex = getValueStartIndex(str, i);
                return str2.substring(valueStartIndex, 4) + "-" + ProTool.showHex(str2.charAt(valueStartIndex + 5)) + ProTool.showHex(str2.charAt(valueStartIndex + 6));
            case '?':
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
            case 'G':
                return "0x" + Long.toHexString(getLong4ParamValue(str, i, str2)).toUpperCase();
            case 'S':
                return str2.substring(getValueStartIndex(str, i), 10);
            default:
                return "";
        }
    }

    private long getLong4ParamValue(String str, int i, String str2) {
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

    private int getValueStartIndex(String str, int i) {
        return ((getStartRegisterByParam(str).intValue() - i) * 2) + 35;
    }

    public EditText getTimeDateEditText() {
        return this.timeDateEditText;
    }

    public EditText getTimeTimeEditText() {
        return this.timeTimeEditText;
    }
}