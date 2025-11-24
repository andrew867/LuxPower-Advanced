package com.lux.luxcloud.global.bean.set;

import android.widget.Button;
import android.widget.ToggleButton;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class RemoteWriteInfo {
    private String bitParam;
    private Integer datalogParamIndex;
    private byte[] datalogParamValues;
    private boolean functionEnable;
    private String functionParam;
    private ToggleButton functionToggleButton;
    private boolean functionToggleButtonChecked;
    private String holdParam;
    private String hourText;
    private String minuteText;
    private String modelText;
    private REMOTE_WRITE_TYPE remoteWriteType;
    private String resetParam;
    private String serialNum;
    private Button setButton;
    private String timeParam;
    private String valueText;

    public void setEnabled(boolean z) {
        ToggleButton toggleButton = this.functionToggleButton;
        if (toggleButton != null) {
            toggleButton.setEnabled(z);
        }
        Button button = this.setButton;
        if (button != null) {
            button.setEnabled(z);
        }
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }

    public REMOTE_WRITE_TYPE getRemoteWriteType() {
        return this.remoteWriteType;
    }

    public void setRemoteWriteType(REMOTE_WRITE_TYPE remote_write_type) {
        this.remoteWriteType = remote_write_type;
    }

    public String getHoldParam() {
        return this.holdParam;
    }

    public void setHoldParam(String str) {
        this.holdParam = str;
    }

    public String getValueText() {
        return this.valueText;
    }

    public void setValueText(String str) {
        this.valueText = str;
    }

    public String getModelText() {
        return this.modelText;
    }

    public void setModelText(String str) {
        this.modelText = str;
    }

    public String getResetParam() {
        return this.resetParam;
    }

    public void setResetParam(String str) {
        this.resetParam = str;
    }

    public String getBitParam() {
        return this.bitParam;
    }

    public void setBitParam(String str) {
        this.bitParam = str;
    }

    public String getTimeParam() {
        return this.timeParam;
    }

    public void setTimeParam(String str) {
        this.timeParam = str;
    }

    public String getHourText() {
        return this.hourText;
    }

    public void setHourText(String str) {
        this.hourText = str;
    }

    public String getMinuteText() {
        return this.minuteText;
    }

    public void setMinuteText(String str) {
        this.minuteText = str;
    }

    public String getFunctionParam() {
        return this.functionParam;
    }

    public void setFunctionParam(String str) {
        this.functionParam = str;
    }

    public boolean isFunctionEnable() {
        return this.functionEnable;
    }

    public void setFunctionEnable(boolean z) {
        this.functionEnable = z;
    }

    public boolean isFunctionToggleButtonChecked() {
        return this.functionToggleButtonChecked;
    }

    public void setFunctionToggleButtonChecked(boolean z) {
        this.functionToggleButtonChecked = z;
    }

    public ToggleButton getFunctionToggleButton() {
        return this.functionToggleButton;
    }

    public void setFunctionToggleButton(ToggleButton toggleButton) {
        this.functionToggleButton = toggleButton;
    }

    public Integer getDatalogParamIndex() {
        return this.datalogParamIndex;
    }

    public void setDatalogParamIndex(Integer num) {
        this.datalogParamIndex = num;
    }

    public byte[] getDatalogParamValues() {
        return this.datalogParamValues;
    }

    public void setDatalogParamValues(byte[] bArr) {
        this.datalogParamValues = bArr;
    }

    public Button getSetButton() {
        return this.setButton;
    }

    public void setSetButton(Button button) {
        this.setButton = button;
    }
}