package com.nfcx.eg4.global.bean.param;

import android.util.Pair;
import androidx.savedstate.serialization.ClassDiscriminatorModeKt;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.BATTERY_TYPE;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.bean.user.ROLE;
import java.util.ArrayList;
import java.util.Arrays;
import org.bouncycastle.asn1.BERTags;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public enum PARAM {
    HOLD_PV_INPUT_MODE { // from class: com.nfcx.eg4.global.bean.param.PARAM.1
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_pv_input_mode;
        }

        public int getStartRegister() {
            return 20;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_START_PV_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.2
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_start_pv_volt;
        }

        public int getStartRegister() {
            return 22;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_CONNECT_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.3
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_connect_time;
        }

        public int getStartRegister() {
            return 23;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_RECONNECT_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.4
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_reconnect_time;
        }

        public int getStartRegister() {
            return 24;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_CONN_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.5
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_volt_conn_low;
        }

        public int getStartRegister() {
            return 25;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_CONN_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.6
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_volt_conn_high;
        }

        public int getStartRegister() {
            return 26;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_CONN_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.7
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_freq_conn_low;
        }

        public int getStartRegister() {
            return 27;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_CONN_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.8
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_freq_conn_high;
        }

        public int getStartRegister() {
            return 28;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT1_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.9
        public int getStartRegister() {
            return 29;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_low_volt : R.string.phrase_param_grid_volt_limit1_low;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT1_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.10
        public int getStartRegister() {
            return 30;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_high_volt : R.string.phrase_param_grid_volt_limit1_high;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT1_LOW_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.11
        public int getStartRegister() {
            return 31;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_low_time_volt : R.string.phrase_param_grid_volt_limit1_low_time;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT1_HIGH_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.12
        public int getStartRegister() {
            return 32;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_high_time_volt : R.string.phrase_param_grid_volt_limit1_high_time;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT2_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.13
        public int getStartRegister() {
            return 33;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit2_low_volt : R.string.phrase_param_grid_volt_limit2_low;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT2_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.14
        public int getStartRegister() {
            return 34;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit2_high_volt : R.string.phrase_param_grid_volt_limit2_high;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT2_LOW_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.15
        public int getStartRegister() {
            return 35;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.page_setting_tip_grid_limit_l2_time_volt : R.string.page_setting_tip_grid_volt_limit_l2_time;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT2_HIGH_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.16
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_h2_time;
        }

        public int getStartRegister() {
            return 36;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT3_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.17
        public int getStartRegister() {
            return 37;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit3_low_volt : R.string.phrase_param_grid_volt_limit3_low;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT3_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.18
        public int getStartRegister() {
            return 38;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit3_high_volt : R.string.phrase_param_grid_volt_limit3_high;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT3_LOW_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.19
        public int getStartRegister() {
            return 39;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.page_setting_tip_grid_limit_l3_time_volt : R.string.page_setting_tip_grid_frequency_l3_time;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT3_HIGH_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.20
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_frequency_h3_time;
        }

        public int getStartRegister() {
            return 40;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_MOV_AVG_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.21
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_volt_mov_avg_high;
        }

        public int getStartRegister() {
            return 41;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT1_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.22
        public int getStartRegister() {
            return 42;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit1_low_hz : R.string.phrase_param_grid_freq_limit1_low;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT1_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.23
        public int getStartRegister() {
            return 43;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit1_high_hz : R.string.phrase_param_grid_freq_limit1_high;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT1_LOW_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.24
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_l1_time;
        }

        public int getStartRegister() {
            return 44;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT1_HIGH_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.25
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_h1_time;
        }

        public int getStartRegister() {
            return 45;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT2_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.26
        public int getStartRegister() {
            return 46;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit2_low_hz : R.string.phrase_param_grid_freq_limit2_low;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT2_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.27
        public int getStartRegister() {
            return 47;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit2_high_hz : R.string.phrase_param_grid_freq_limit2_high;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT2_LOW_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.28
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_l2_time;
        }

        public int getStartRegister() {
            return 48;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT2_HIGH_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.29
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_h2_time;
        }

        public int getStartRegister() {
            return 49;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT3_LOW { // from class: com.nfcx.eg4.global.bean.param.PARAM.30
        public int getStartRegister() {
            return 50;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit3_low_hz : R.string.phrase_param_grid_freq_limit3_low;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT3_HIGH { // from class: com.nfcx.eg4.global.bean.param.PARAM.31
        public int getStartRegister() {
            return 51;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit3_high_hz : R.string.phrase_param_grid_freq_limit3_high;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT3_LOW_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.32
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_frequency_l3_time;
        }

        public int getStartRegister() {
            return 52;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT3_HIGH_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.33
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_frequency_h3_time;
        }

        public int getStartRegister() {
            return 53;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    INPUT_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.34
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole());
        }
    },
    HOLD_MAX_Q_PERCENT_FOR_QV { // from class: com.nfcx.eg4.global.bean.param.PARAM.35
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 54;
        }
    },
    HOLD_V1L { // from class: com.nfcx.eg4.global.bean.param.PARAM.36
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 55;
        }
    },
    HOLD_V2L { // from class: com.nfcx.eg4.global.bean.param.PARAM.37
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 56;
        }
    },
    HOLD_V1H { // from class: com.nfcx.eg4.global.bean.param.PARAM.38
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 57;
        }
    },
    HOLD_V2H { // from class: com.nfcx.eg4.global.bean.param.PARAM.39
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 58;
        }
    },
    HOLD_REACTIVE_POWER_CMD_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.40
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.reactive_power_type;
        }

        public int getStartRegister() {
            return 59;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_ACTIVE_POWER_PERCENT_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.41
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 60;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_REACTIVE_POWER_PERCENT_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.42
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.reactive_power_percent;
        }

        public int getStartRegister() {
            return 61;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_PF_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.43
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 62;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_POWER_SOFT_START_SLOPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.44
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_midbox_soft_start_slope;
        }

        public int getStartRegister() {
            return 63;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_CHARGE_POWER_PERCENT_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.45
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_power_percent_cmd;
        }

        public int getStartRegister() {
            return 64;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return !currentInverter.isSnaSeries();
        }
    },
    HOLD_DISCHG_POWER_PERCENT_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.46
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_discharge_power_percent_cmd;
        }

        public int getStartRegister() {
            return 65;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_AC_CHARGE_POWER_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.47
        public int getStartRegister() {
            return 66;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_ac_charge_power_cmd_12k : R.string.phrase_param_ac_charge_power_cmd;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isType6Series() ? (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true : !currentInverter.isSnaSeries();
        }
    },
    HOLD_AC_CHARGE_START_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.48
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 68;
        }
    },
    HOLD_AC_CHARGE_START_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.49
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_hint_minute_range;
        }

        public int getStartRegister() {
            return 68;
        }
    },
    HOLD_AC_CHARGE_END_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.50
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time;
        }

        public int getStartRegister() {
            return 69;
        }
    },
    HOLD_AC_CHARGE_END_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.51
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time;
        }

        public int getStartRegister() {
            return 69;
        }
    },
    HOLD_AC_CHARGE_START_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.52
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_1;
        }

        public int getStartRegister() {
            return 70;
        }
    },
    HOLD_AC_CHARGE_START_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.53
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_1;
        }

        public int getStartRegister() {
            return 70;
        }
    },
    HOLD_AC_CHARGE_END_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.54
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_1;
        }

        public int getStartRegister() {
            return 71;
        }
    },
    HOLD_AC_CHARGE_END_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.55
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_1;
        }

        public int getStartRegister() {
            return 71;
        }
    },
    HOLD_AC_CHARGE_START_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.56
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_2;
        }

        public int getStartRegister() {
            return 72;
        }
    },
    HOLD_AC_CHARGE_START_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.57
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_2;
        }

        public int getStartRegister() {
            return 72;
        }
    },
    HOLD_AC_CHARGE_END_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.58
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_2;
        }

        public int getStartRegister() {
            return 73;
        }
    },
    HOLD_AC_CHARGE_END_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.59
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_2;
        }

        public int getStartRegister() {
            return 73;
        }
    },
    HOLD_FORCED_CHG_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.PARAM.60
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        public int getStartRegister() {
            return 75;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_forced_chg_soc_limit_12k : R.string.phrase_param_forced_chg_soc_limit;
        }
    },
    _12K_HOLD_CHARGE_FIRST_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.61
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_first_volt;
        }

        public int getStartRegister() {
            return 201;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHG_POWER_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.62
        public int getStartRegister() {
            return 74;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isMidBox() || inverter.isType6Series()) ? R.string.phrase_param_forced_chg_power_cmd_12k : R.string.phrase_param_forced_chg_power_cmd;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isType6Series() ? (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true : !currentInverter.isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.63
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time;
        }

        public int getStartRegister() {
            return 76;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.64
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time;
        }

        public int getStartRegister() {
            return 76;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.65
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time;
        }

        public int getStartRegister() {
            return 77;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.66
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time;
        }

        public int getStartRegister() {
            return 77;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.67
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_1;
        }

        public int getStartRegister() {
            return 78;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.68
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_1;
        }

        public int getStartRegister() {
            return 78;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.69
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_1;
        }

        public int getStartRegister() {
            return 79;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.70
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_1;
        }

        public int getStartRegister() {
            return 79;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.71
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_2;
        }

        public int getStartRegister() {
            return 80;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.72
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_2;
        }

        public int getStartRegister() {
            return 80;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.73
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_2;
        }

        public int getStartRegister() {
            return 81;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.74
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_2;
        }

        public int getStartRegister() {
            return 81;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_DISCHG_POWER_CMD { // from class: com.nfcx.eg4.global.bean.param.PARAM.75
        public int getStartRegister() {
            return 82;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_forced_dischg_power_cmd_12k : R.string.phrase_param_forced_dischg_power_cmd;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
        }
    },
    HOLD_FORCED_DISCHG_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.PARAM.76
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        public int getStartRegister() {
            return 83;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_forced_dischg_soc_limit_12k : R.string.phrase_param_forced_dischg_soc_limit;
        }
    },
    HOLD_FORCED_DISCHARGE_START_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.77
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time;
        }

        public int getStartRegister() {
            return 84;
        }
    },
    HOLD_FORCED_DISCHARGE_START_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.78
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time;
        }

        public int getStartRegister() {
            return 84;
        }
    },
    HOLD_FORCED_DISCHARGE_END_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.79
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time;
        }

        public int getStartRegister() {
            return 85;
        }
    },
    HOLD_FORCED_DISCHARGE_END_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.80
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time;
        }

        public int getStartRegister() {
            return 85;
        }
    },
    HOLD_FORCED_DISCHARGE_START_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.81
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_1;
        }

        public int getStartRegister() {
            return 86;
        }
    },
    HOLD_FORCED_DISCHARGE_START_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.82
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_1;
        }

        public int getStartRegister() {
            return 86;
        }
    },
    HOLD_FORCED_DISCHARGE_END_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.83
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_1;
        }

        public int getStartRegister() {
            return 87;
        }
    },
    HOLD_FORCED_DISCHARGE_END_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.84
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_1;
        }

        public int getStartRegister() {
            return 87;
        }
    },
    HOLD_FORCED_DISCHARGE_START_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.85
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_2;
        }

        public int getStartRegister() {
            return 88;
        }
    },
    HOLD_FORCED_DISCHARGE_START_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.86
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_2;
        }

        public int getStartRegister() {
            return 88;
        }
    },
    HOLD_FORCED_DISCHARGE_END_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.87
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_2;
        }

        public int getStartRegister() {
            return 89;
        }
    },
    HOLD_FORCED_DISCHARGE_END_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.88
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_2;
        }

        public int getStartRegister() {
            return 89;
        }
    },
    HOLD_EPS_VOLT_SET { // from class: com.nfcx.eg4.global.bean.param.PARAM.89
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_eps_voltage_set;
        }

        public int getStartRegister() {
            return 90;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            if (userData.getCurrentInverter().isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_EPS_FREQ_SET { // from class: com.nfcx.eg4.global.bean.param.PARAM.90
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_eps_frequency_set;
        }

        public int getStartRegister() {
            return 91;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(role);
            }
            if (currentInverter.isMidBox()) {
                return false;
            }
            return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
        }
    },
    HOLD_DELAY_TIME_FOR_QV_CURVE { // from class: com.nfcx.eg4.global.bean.param.PARAM.91
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 96;
        }
    },
    HOLD_DELAY_TIME_FOR_OVER_F_DERATE { // from class: com.nfcx.eg4.global.bean.param.PARAM.92
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 97;
        }
    },
    HOLD_LEAD_ACID_CHARGE_VOLT_REF { // from class: com.nfcx.eg4.global.bean.param.PARAM.93
        public int getStartRegister() {
            return 99;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_lead_acid_charge_volt_ref_12k : (inverter.isSnaSeries() || inverter.isHybird() || inverter.isAcCharger()) ? R.string.phrase_param_lead_acid_charge_volt_ref : R.string.phrase_param_lead_acid_charge_volt_ref_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.94
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_lead_acid_discharge_cut_off_volt;
        }

        public int getStartRegister() {
            return 100;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_LEAD_ACID_CHARGE_RATE { // from class: com.nfcx.eg4.global.bean.param.PARAM.95
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_current;
        }

        public int getStartRegister() {
            return 101;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isMidBox()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            return false;
        }
    },
    HOLD_LEAD_ACID_DISCHARGE_RATE { // from class: com.nfcx.eg4.global.bean.param.PARAM.96
        public int getStartRegister() {
            return 102;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isSnaSeries() || inverter.isLsp() || inverter.isHybird() || !inverter.isType6()) ? R.string.phrase_param_lead_acid_discharge_rate : R.string.phrase_param_dischg_current;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isSnaSeries()) {
                return true;
            }
            if (currentInverter.isMidBox()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            return false;
        }
    },
    HOLD_FEED_IN_GRID_POWER_PERCENT { // from class: com.nfcx.eg4.global.bean.param.PARAM.97
        public int getStartRegister() {
            return 103;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            if (inverter.isType6()) {
                return R.string.phrase_param_feed_in_grid_power_percent_12k;
            }
            if (inverter.isSnaSeries()) {
                return R.string.phrase_param_feed_in_grid_power_percent;
            }
            if (!inverter.isHybird()) {
                inverter.isAcCharger();
            }
            return R.string.phrase_param_feed_in_grid_power_percent_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries()) {
                return true;
            }
            if (currentInverter.isAllInOne() || currentInverter.is12KUsVersion()) {
                return false;
            }
            return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
        }
    },
    HOLD_DISCHG_CUT_OFF_SOC_EOD { // from class: com.nfcx.eg4.global.bean.param.PARAM.98
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_dischg_cut_off_soc_eod;
        }

        public int getStartRegister() {
            return 105;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG { // from class: com.nfcx.eg4.global.bean.param.PARAM.99
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.discharge_tempature_low_limit;
        }

        public int getStartRegister() {
            return 106;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG { // from class: com.nfcx.eg4.global.bean.param.PARAM.100
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.discharge_tempature_high_limit;
        }

        public int getStartRegister() {
            return 107;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG { // from class: com.nfcx.eg4.global.bean.param.PARAM.101
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_temperature_low_limit;
        }

        public int getStartRegister() {
            return 108;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG { // from class: com.nfcx.eg4.global.bean.param.PARAM.102
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_temperature_high_limit;
        }

        public int getStartRegister() {
            return 109;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_SET_MASTER_OR_SLAVE { // from class: com.nfcx.eg4.global.bean.param.PARAM.103
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_master_or_slave;
        }

        public int getStartRegister() {
            return 112;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries() || currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isType6Series() && !currentInverter.isAllInOne();
        }
    },
    HOLD_SET_COMPOSED_PHASE { // from class: com.nfcx.eg4.global.bean.param.PARAM.104
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_composed_phase;
        }

        public int getStartRegister() {
            return 113;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries() || currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isType6Series() && !currentInverter.isAllInOne();
        }
    },
    _12K_HOLD_OVF_DERATE_START_POINT { // from class: com.nfcx.eg4.global.bean.param.PARAM.105
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 115;
        }
    },
    HOLD_P_TO_USER_START_DISCHG { // from class: com.nfcx.eg4.global.bean.param.PARAM.106
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.start_discharge_p_import_w;
        }

        public int getStartRegister() {
            return 116;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    BIT_LCD_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.107
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phase_bit_param_lcd_type;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return PLATFORM.EG4.equals(userData.getPlatform()) && !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MODEL_BIT_RULE { // from class: com.nfcx.eg4.global.bean.param.PARAM.108
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_rule;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            String username = userData.getUsername();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger() || currentInverter.isType6Series()) {
                return ROLE.ADMIN.equals(role) || ROLE.MAINTAIN.equals(role) || "SignatureSolar".equals(username) || "gigabiz1".equals(username);
            }
            if (!currentInverter.isSnaSeries() || (ROLE.VIEWER.equals(role) && !PLATFORM.EG4.equals(userData.getPlatform()))) {
                return false;
            }
            return (currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getSubDeviceTypeValue() == 1111) ? false : true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.getDtcValue().intValue() == 1) {
                ArrayList arrayList = new ArrayList(Arrays.asList("0: UL1741&IEEE1547", "1: HECO", "2: USA(rule21)", "3: PR-LUMA", "4: KIUC"));
                ArrayList arrayList2 = new ArrayList(Arrays.asList("0", "1", "2", "3", "4"));
                if (currentInverter.isType6Series()) {
                    arrayList.add("5: Brazil");
                    arrayList2.add("5");
                }
                return createDropdown((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
            }
            if (currentInverter.isSnaSeries()) {
                return createDropdown(new String[]{"0: EU (General 50Hz)", "1: US (Off-grid 60Hz)", "2: US (General 60Hz)", "3: BR (220V 60Hz)", "4: BR (120V 60Hz)", "5: EU (Off-grid 50Hz)", "8: IT (CEI0-21)", "16: SA (NRS097-1)"}, new String[]{"0", "1", "2", "3", "4", "5", "8", "16"});
            }
            ArrayList arrayList3 = new ArrayList(Arrays.asList("0: Normal - Same as VDE0126", "1: VDE0126 - Germany", "2: AS4777 - Australia", "3: NEWZEALAND", "4: CGC - China", "5: G99", "6: G98", "7: N4105 - Germany", "8: CEI0-21 - Italy", "9: EN50438", "10: EN50438_Finland", "11: Japan", "12: PEA", "13: MEA", "14: EN50438_Ireland", "15: Czech", "16: South Africa", "21: PTPiREE (Poland)", "23: C10_11"));
            ArrayList arrayList4 = new ArrayList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "21", "23"));
            if (currentInverter.getPhaseValue().intValue() == 3 && currentInverter.getDeviceTypeValue() == 0) {
                arrayList3.add("19: EN50549");
                arrayList4.add("19");
                arrayList3.add("20: Denmark");
                arrayList4.add("20");
            }
            if (!currentInverter.isSnaSeries()) {
                arrayList3.add("21: PTPiREE (Poland)");
                arrayList4.add("21");
            }
            if (currentInverter.getPhaseValue().intValue() == 3 && currentInverter.getDeviceTypeValue() == 0) {
                arrayList3.add("22: SPAIN");
                arrayList4.add("22");
                arrayList3.add("23: C10_11");
                arrayList4.add("23");
            }
            if (!currentInverter.isSnaSeries()) {
                arrayList3.add("24: TOR");
                arrayList4.add("24");
            }
            if (currentInverter.getPhaseValue().intValue() == 3 && currentInverter.getDeviceTypeValue() == 0) {
                arrayList3.add("25: Denmark-DK2");
                arrayList4.add("25");
            }
            return createDropdown((String[]) arrayList3.toArray(new String[0]), (String[]) arrayList4.toArray(new String[0]));
        }
    },
    FUNC_SNA_BAT_DISCHARGE_CONTROL { // from class: com.nfcx.eg4.global.bean.param.PARAM.109
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_bat_discharge_control;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    MODEL_BIT_LITHIUM_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.110
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return "EG4".equals(PLATFORM.EG4.name()) ? R.string.battery_type_eg4 : R.string.battery_type;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return true;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Pair<String[], String[]> lithiumOptions_Hybrid;
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isTrip12K()) {
                lithiumOptions_Hybrid = getLithiumOptions_Trip12K(userData);
            } else if ((PLATFORM.EG4.equals(userData.getPlatform()) && currentInverter.getPhase().intValue() == 1 && currentInverter.isType6()) || currentInverter.is7_10KDevice() || currentInverter.isMidBox()) {
                lithiumOptions_Hybrid = getLithiumOptions_US(userData);
            } else if (currentInverter.isAllInOne()) {
                lithiumOptions_Hybrid = getLithiumOptions_AllInOne(userData);
            } else if (userData.getClusterId() == 4) {
                lithiumOptions_Hybrid = getLithiumOptions_12KSeries_NA(userData);
            } else if (currentInverter.isGen3_6K() || currentInverter.isAcCharger()) {
                lithiumOptions_Hybrid = getLithiumOptions_Hybrid(userData);
            } else if (currentInverter.isSnaSeries()) {
                lithiumOptions_Hybrid = getLithiumOptions_SNA(userData);
            } else {
                lithiumOptions_Hybrid = getLithiumOptions_12KSeries_Normal(userData);
            }
            return createDropdown((String[]) lithiumOptions_Hybrid.first, (String[]) lithiumOptions_Hybrid.second);
        }

        private Pair<String[], String[]> getLithiumOptions_SNA(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                arrayList.add("0: Standard Battery");
                arrayList2.add("0");
                arrayList.add("1: HINA Battery");
                arrayList2.add("1");
                arrayList.add("2: Pylon/Freedom Won/Solar MD/Hubble/Blue Nova");
                arrayList2.add("2");
                arrayList.add("3: Enopte");
                arrayList2.add("3");
                arrayList.add("4: MSUN");
                arrayList2.add("4");
                arrayList.add("5: GSL1 Battery");
                arrayList2.add("5");
                if (userData.isGigabiz1User()) {
                    arrayList.add("6: LUX / HANCHU");
                } else {
                    arrayList.add("6: Luxpower");
                }
                arrayList2.add("6");
                arrayList.add("7: Aobo Battery");
                arrayList2.add("7");
                if (currentInverter.getDeviceType().intValue() == 4) {
                    arrayList.add("8: Dyness");
                } else {
                    arrayList.add("8: Rsvd");
                }
                arrayList2.add("8");
                arrayList.add("9: Stealth");
                arrayList2.add("9");
                arrayList.add("10: TeLongMei");
                arrayList2.add("10");
                arrayList.add("11: Merit");
                arrayList2.add("11");
                arrayList.add("14: WECO");
                arrayList2.add("14");
                arrayList.add("15: Murata");
                arrayList2.add("15");
                arrayList.add("16: BITEK");
                arrayList2.add("16");
                arrayList.add("17: OKSolar");
                arrayList2.add("17");
                arrayList.add("18: GW Battery");
                arrayList2.add("18");
                arrayList.add("19: CROWN");
                arrayList2.add("19");
                arrayList.add("20: Revov");
                arrayList2.add("20");
                arrayList.add("21: Beebeejump");
                arrayList2.add("21");
            } else {
                arrayList.add("0: EG4");
                arrayList2.add("0");
                arrayList.add("1");
                arrayList2.add("1");
                arrayList.add("2");
                arrayList2.add("2");
                arrayList.add("3");
                arrayList2.add("3");
                arrayList.add("4");
                arrayList2.add("4");
                arrayList.add("5");
                arrayList2.add("5");
                arrayList.add("6");
                arrayList2.add("6");
                arrayList.add("7");
                arrayList2.add("7");
                arrayList.add("8");
                arrayList2.add("8");
                arrayList.add("9");
                arrayList2.add("9");
                arrayList.add("10");
                arrayList2.add("10");
                arrayList.add("11");
                arrayList2.add("11");
                arrayList.add("14");
                arrayList2.add("14");
                arrayList.add("15");
                arrayList2.add("15");
                arrayList.add("16");
                arrayList2.add("16");
                arrayList.add("17");
                arrayList2.add("17");
                arrayList.add("18");
                arrayList2.add("18");
                arrayList.add("19");
                arrayList2.add("19");
                arrayList.add("20");
                arrayList2.add("20");
                arrayList.add("21");
                arrayList2.add("21");
            }
            return new Pair<>((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
        }

        private Pair<String[], String[]> getLithiumOptions_Hybrid(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.add("-1: <Empty>");
            arrayList2.add("-1");
            arrayList.add("0: Standard Battery");
            arrayList2.add("0");
            arrayList.add("1: HINA Battery");
            arrayList2.add("1");
            arrayList.add("2: Pylon/Freedom Won/Solar MD/Hubble/Blue Nova");
            arrayList2.add("2");
            arrayList.add("3: Enopte");
            arrayList2.add("3");
            arrayList.add("4: MSUN");
            arrayList2.add("4");
            arrayList.add("5: GSL1 Battery");
            arrayList2.add("5");
            arrayList.add("6: ".concat(userData.isGigabiz1User() ? "LUX / HANCHU" : "Luxpower"));
            arrayList2.add("6");
            arrayList.add("7: Aobo Battery");
            arrayList2.add("7");
            arrayList.add("8: ".concat(currentInverter.getDeviceType().intValue() == 4 ? "Dyness" : "Rsvd"));
            arrayList2.add("8");
            arrayList.add("9: Stealth");
            arrayList2.add("9");
            arrayList.add("10: TeLongMei");
            arrayList2.add("10");
            arrayList.add("11: Merit");
            arrayList2.add("11");
            arrayList.add("14: WECO");
            arrayList2.add("14");
            arrayList.add("15: Murata");
            arrayList2.add("15");
            arrayList.add("16: BITEK");
            arrayList2.add("16");
            arrayList.add("17: OKSolar");
            arrayList2.add("17");
            arrayList.add("18: GW Battery");
            arrayList2.add("18");
            if (currentInverter.getDeviceType().intValue() == 10) {
                arrayList.add("19: CROWN");
                arrayList2.add("19");
            }
            arrayList.add("20: Revov");
            arrayList2.add("20");
            arrayList.add("21: Beebeejump");
            arrayList2.add("21");
            arrayList.add("28: Gotion");
            arrayList2.add("28");
            return new Pair<>((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private android.util.Pair<java.lang.String[], java.lang.String[]> getLithiumOptions_US(com.nfcx.eg4.global.UserData r19) {
            /*
                Method dump skipped, instructions count: 279
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.global.bean.param.PARAM.AnonymousClass110.getLithiumOptions_US(com.nfcx.eg4.global.UserData):android.util.Pair");
        }

        private Pair<String[], String[]> getLithiumOptions_12KSeries_NA(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.add("-1: <Empty>");
            arrayList2.add("-1");
            arrayList.add("0: Standard/Pytes/EG4/BSL/Stackrack/Ampace/BigBattery");
            arrayList2.add("0");
            arrayList.add("1: HINAESS");
            arrayList2.add("1");
            arrayList.add("2: Pylon/UZ Energy/Shoto/Hubble/FreedomWon/BlueNova");
            arrayList2.add("2");
            arrayList.add("3: Rsvd");
            arrayList2.add("3");
            arrayList.add("4: GSL Energy");
            arrayList2.add("4");
            arrayList.add("5: Luxpower/Meritsun");
            arrayList2.add("5");
            int i = 6;
            while (i <= 18) {
                arrayList.add(i == 14 ? "14: Green Battery" : i + ": Rsvd");
                arrayList2.add(String.valueOf(i));
                i++;
            }
            arrayList.add("18: Fortress");
            arrayList2.add("18");
            arrayList.add("19: " + ((currentInverter.getDeviceType().intValue() == 8 || currentInverter.getDeviceType().intValue() == 10) ? "CROWN" : "Sunwoda/Renogy"));
            arrayList2.add("19");
            arrayList.add("20: Aobo");
            arrayList2.add("20");
            arrayList.add("21: Weco");
            arrayList2.add("21");
            arrayList.add("22: Vero Electric");
            arrayList2.add("22");
            arrayList.add("28: Gotion");
            arrayList2.add("28");
            return new Pair<>((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
        }

        private Pair<String[], String[]> getLithiumOptions_AllInOne(UserData userData) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.add("-1: <Empty>");
            arrayList2.add("-1");
            arrayList.add("0: Rsvd");
            arrayList2.add("0");
            arrayList.add("1: HINAESS");
            arrayList2.add("1");
            arrayList.add("2: Rsvd");
            arrayList2.add("2");
            arrayList.add("3: Rsvd");
            arrayList2.add("3");
            arrayList.add("4: ATL");
            arrayList2.add("4");
            arrayList.add("5: GSL");
            arrayList2.add("5");
            arrayList.add("6: Luxpower");
            arrayList2.add("6");
            for (int i = 7; i <= 21; i++) {
                arrayList.add(i + ": Rsvd");
                arrayList2.add(String.valueOf(i));
            }
            return new Pair<>((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
        }

        private Pair<String[], String[]> getLithiumOptions_12KSeries_Normal(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.add("-1: <Empty>");
            arrayList2.add("-1");
            arrayList.add("0: Standard/Pytes/EG4/BSL/Stackrack/Ampace/BigBattery");
            arrayList2.add("0");
            arrayList.add("1: HINAESS");
            arrayList2.add("1");
            arrayList.add("2: Pylon/UZ Energy/Shoto/Hubble/FreedomWon/BlueNova");
            arrayList2.add("2");
            arrayList.add("3: Rsvd");
            arrayList2.add("3");
            arrayList.add("4: GSL Energy");
            arrayList2.add("4");
            arrayList.add("5: Luxpower/Meritsun/LSSA/HANCHU");
            arrayList2.add("5");
            int i = 6;
            while (i <= 18) {
                arrayList.add(i == 14 ? "14: Green Battery" : i + ": Rsvd");
                arrayList2.add(String.valueOf(i));
                i++;
            }
            arrayList.add("18: Fortress");
            arrayList2.add("18");
            arrayList.add("19: " + ((currentInverter.getDeviceType().intValue() == 8 || currentInverter.getDeviceType().intValue() == 10) ? "CROWN" : "Sunwoda/Renogy"));
            arrayList2.add("19");
            arrayList.add("20: Aobo");
            arrayList2.add("20");
            arrayList.add("21: Weco");
            arrayList2.add("21");
            arrayList.add("22: Vero Electric");
            arrayList2.add("22");
            arrayList.add("28: Gotion");
            arrayList2.add("28");
            return new Pair<>((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
        }

        private Pair<String[], String[]> getLithiumOptions_Trip12K(UserData userData) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            boolean zIsGigabiz1User = userData.isGigabiz1User();
            arrayList.add("-1: <Empty>");
            arrayList2.add("-1");
            arrayList.add("0: Standard");
            arrayList2.add("0");
            arrayList.add("1: HINAESS");
            arrayList2.add("1");
            arrayList.add("2: Pylon");
            arrayList2.add("2");
            arrayList.add("3: Rsvd");
            arrayList2.add("3");
            arrayList.add("4: ATL");
            arrayList2.add("4");
            arrayList.add("5: GSL");
            arrayList2.add("5");
            arrayList.add("6: ".concat(zIsGigabiz1User ? "Luxpower/Aoboet/Hanchu" : "Luxpower/Zetara/Aoboet"));
            arrayList2.add("6");
            for (int i = 7; i <= 21; i++) {
                arrayList.add(i + ": Rsvd");
                arrayList2.add(String.valueOf(i));
            }
            return new Pair<>((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
        }
    },
    HOLD_VBAT_START_DERATING { // from class: com.nfcx.eg4.global.bean.param.PARAM.111
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.on_grid_discharge_derate_vbatt_v;
        }

        public int getStartRegister() {
            return 118;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_CT_POWER_OFFSET { // from class: com.nfcx.eg4.global.bean.param.PARAM.112
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ct_power_offset;
        }

        public int getStartRegister() {
            return 119;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_MAINTENANCE_COUNT { // from class: com.nfcx.eg4.global.bean.param.PARAM.113
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 122;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_SOC_LOW_LIMIT_EPS_DISCHG { // from class: com.nfcx.eg4.global.bean.param.PARAM.114
        public int getStartRegister() {
            return R.styleable.AppCompatTheme_windowMinWidthMinor;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (!inverter.isType6() && inverter.isSnaSeries()) ? R.string.fragment_remote_set_soc_low_limit_eps_dischg_button : R.string.phrase_param_soc_low_limit_eps_dischg;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_OPTIMAL_CHG_DISCHG_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.115
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return R.styleable.AppCompatTheme_windowNoTitle;
        }
    },
    HOLD_UVF_DERATE_START_POINT { // from class: com.nfcx.eg4.global.bean.param.PARAM.116
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA;
        }
    },
    HOLD_OVF_DROOP_KOF { // from class: com.nfcx.eg4.global.bean.param.PARAM.117
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
        }
    },
    HOLD_SPEC_LOAD_COMPENSATE { // from class: com.nfcx.eg4.global.bean.param.PARAM.118
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.big_load_compensate_power;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
            }
            return false;
        }
    },
    HOLD_FLOATING_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.119
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_floating_voltage;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    HOLD_OUTPUT_CONFIGURATION { // from class: com.nfcx.eg4.global.bean.param.PARAM.120
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_output_configuration;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA;
        }
    },
    HOLD_NOMINAL_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.121
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_norminal_battery_voltage;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA;
        }
    },
    HOLD_EQUALIZATION_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.122
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_equalization_voltage;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    HOLD_EQUALIZATION_PERIOD { // from class: com.nfcx.eg4.global.bean.param.PARAM.123
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_equalization_period;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    HOLD_EQUALIZATION_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.124
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_equalization_time;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.125
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.126
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_HOUR { // from class: com.nfcx.eg4.global.bean.param.PARAM.127
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_MINUTE { // from class: com.nfcx.eg4.global.bean.param.PARAM.128
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.129
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.130
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.131
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_1;
        }

        public int getStartRegister() {
            return 155;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.132
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_1;
        }

        public int getStartRegister() {
            return 155;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.133
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_2;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.134
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_2;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.135
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_2;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.136
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_2;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_CHARGE_END_BATTERY_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.137
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_battery_soc;
        }

        public int getStartRegister() {
            return 161;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_BATTERY_WARNING_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.138
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_voltage;
        }

        public int getStartRegister() {
            return 162;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.name().equals("EG4");
            }
            return false;
        }
    },
    HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.139
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_recovery_voltage;
        }

        public int getStartRegister() {
            return 163;
        }
    },
    HOLD_BATTERY_WARNING_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.140
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_soc;
        }

        public int getStartRegister() {
            return 164;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.name().equals("EG4");
            }
            return false;
        }
    },
    HOLD_BATTERY_WARNING_RECOVERY_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.141
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_recovery_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384;
        }
    },
    HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.142
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_low_to_utility_voltage;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256;
        }
    },
    HOLD_BATTERY_LOW_TO_UTILITY_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.143
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_low_to_utility_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384;
        }
    },
    HOLD_AC_CHARGE_BATTERY_CURRENT { // from class: com.nfcx.eg4.global.bean.param.PARAM.144
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_chg_bat_current;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_ON_GRID_EOD_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.145
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_on_grid_eod_voltage;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isSnaSeries();
        }
    },
    HOLD_MAX_AC_INPUT_POWER { // from class: com.nfcx.eg4.global.bean.param.PARAM.146
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_max_ac_input_power;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_PSK_WITH_NULL_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_MAX_GENERATOR_INPUT_POWER { // from class: com.nfcx.eg4.global.bean.param.PARAM.147
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_peak_shaving_power;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_PSK_WITH_NULL_SHA384;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isSnaSeries() || currentInverter.isMidBox();
        }
    },
    HOLD_VOLT_WATT_V1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.148
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384;
        }
    },
    HOLD_VOLT_WATT_V2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.149
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256;
        }
    },
    HOLD_VOLT_WATT_DELAY_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.150
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384;
        }
    },
    HOLD_VOLT_WATT_P2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.151
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256;
        }
    },
    HOLD_VREF { // from class: com.nfcx.eg4.global.bean.param.PARAM.152
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384;
        }
    },
    HOLD_VREF_ADJUSTMENT_TIME_CONSTANT { // from class: com.nfcx.eg4.global.bean.param.PARAM.153
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_Q3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.154
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_Q4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.155
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 188;
        }
    },
    HOLD_P1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.156
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_P2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.157
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_P3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.158
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_UVF_DROOP_KUF { // from class: com.nfcx.eg4.global.bean.param.PARAM.159
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256;
        }
    },
    OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR { // from class: com.nfcx.eg4.global.bean.param.PARAM.160
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bat_charge_current_limit;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isMidBox() || currentInverter.isType6Series()) ? currentInverter.getMachineType() != 1 : currentInverter.isSnaSeries() || currentInverter.isHybird() || currentInverter.isAcCharger();
        }
    },
    _12K_HOLD_STOP_DISCHG_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.161
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_stop_dischg_volt;
        }

        public int getStartRegister() {
            return 202;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_REGULATION { // from class: com.nfcx.eg4.global.bean.param.PARAM.162
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_regulation;
        }

        public int getStartRegister() {
            return 203;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MODEL_BIT_LEAD_ACID_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.163
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.lead_acid_capacity_ah;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isSnaSeries()) {
                return PLATFORM.EG4.equals(userData.getPlatform());
            }
            return false;
        }
    },
    _12K_HOLD_LEAD_CAPACITY { // from class: com.nfcx.eg4.global.bean.param.PARAM.164
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.lead_acid_capacity_ah;
        }

        public int getStartRegister() {
            return 204;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isMidBox() && PLATFORM.EG4.equals(userData.getPlatform()) && BATTERY_TYPE.LEAD_ACID.equals(currentInverter.getBatteryTypeFromModel()) && !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    _12K_HOLD_GRID_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.165
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_type;
        }

        public int getStartRegister() {
            return 205;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_POWER { // from class: com.nfcx.eg4.global.bean.param.PARAM.166
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_power;
        }

        public int getStartRegister() {
            return 206;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.167
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_1;
        }

        public int getStartRegister() {
            return 207;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.168
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_1;
        }

        public int getStartRegister() {
            return 208;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_SMART_LOAD_START_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.169
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_start_volt;
        }

        public int getStartRegister() {
            return 213;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (currentInverter.is12KUsVersion() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
        }
    },
    _12K_HOLD_SMART_LOAD_END_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.170
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_end_volt;
        }

        public int getStartRegister() {
            return 214;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (currentInverter.is12KUsVersion() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
        }
    },
    _12K_HOLD_SMART_LOAD_START_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.171
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_start_soc;
        }

        public int getStartRegister() {
            return 215;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (currentInverter.is12KUsVersion() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
        }
    },
    _12K_HOLD_SMART_LOAD_END_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.172
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_end_soc;
        }

        public int getStartRegister() {
            return 216;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (currentInverter.is12KUsVersion() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
        }
    },
    _12K_HOLD_START_PV_POWER { // from class: com.nfcx.eg4.global.bean.param.PARAM.173
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_start_pv_power;
        }

        public int getStartRegister() {
            return 217;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne() && currentInverter.getMachineType() != 1) {
                return true;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.174
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_2;
        }

        public int getStartRegister() {
            return 218;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.175
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_2;
        }

        public int getStartRegister() {
            return 219;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
            }
            return true;
        }
    },
    _12K_HOLD_AC_COUPLE_START_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.176
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_start_soc;
        }

        public int getStartRegister() {
            return 220;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck()) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
            }
            return false;
        }
    },
    _12K_HOLD_AC_COUPLE_END_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.177
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_end_soc;
        }

        public int getStartRegister() {
            return 221;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck()) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
            }
            return false;
        }
    },
    _12K_HOLD_AC_COUPLE_START_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.178
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_start_volt;
        }

        public int getStartRegister() {
            return 222;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck()) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
            }
            return false;
        }
    },
    _12K_HOLD_AC_COUPLE_END_VOLT { // from class: com.nfcx.eg4.global.bean.param.PARAM.179
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_end_volt;
        }

        public int getStartRegister() {
            return 223;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck()) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() > 11;
            }
            return false;
        }
    },
    HOLD_LCD_VERSION { // from class: com.nfcx.eg4.global.bean.param.PARAM.180
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.firmware_lcd_version;
        }

        public int getStartRegister() {
            return BERTags.FLAGS;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if ((currentInverter.isAcCharger() || currentInverter.isHybird()) && ROLE.ADMIN.equals(userData.getRole())) {
                return true;
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne() || currentInverter.isGen3_6K()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_CUSTOM_PASSWORD { // from class: com.nfcx.eg4.global.bean.param.PARAM.181
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.lcd_password;
        }

        public int getStartRegister() {
            return 225;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    BASIC_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.182
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.basic_setting;
        }
    },
    ADVANCED_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.183
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_advanced;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return PARALLEL.checkVisible(userData) || ADVANCED_PROTECTION_SETTING.checkVisible(userData) || CT_METER_SETTING.checkVisible(userData) || PV_SETTING.checkVisible(userData);
        }
    },
    RESET { // from class: com.nfcx.eg4.global.bean.param.PARAM.184
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_reset_set;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    APPLICATION_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.185
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_application_set;
        }
    },
    GENERAL { // from class: com.nfcx.eg4.global.bean.param.PARAM.186
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.general;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return true;
        }
    },
    EPS_OUTPUT { // from class: com.nfcx.eg4.global.bean.param.PARAM.187
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.eps_output;
        }
    },
    BASIC_PROTECTION_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.188
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.protection_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    GRID_SELL { // from class: com.nfcx.eg4.global.bean.param.PARAM.189
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.grid_sell;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.isGigabiz1User()) {
                return false;
            }
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isHybird() || currentInverter.isAcCharger() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    PARALLEL { // from class: com.nfcx.eg4.global.bean.param.PARAM.190
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_parallel;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_MIDBOX_EN.checkVisible(userData) || HOLD_SET_MASTER_OR_SLAVE.checkVisible(userData) || HOLD_SET_COMPOSED_PHASE.checkVisible(userData) || FUNC_BAT_SHARED.checkVisible(userData) || FUNC_PARALLEL_DATA_SYNC_EN.checkVisible(userData);
        }
    },
    ADVANCED_PROTECTION_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.191
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.protection_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isType6Series()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    CT_METER_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.192
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ct_meter_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isType6Series() && !currentInverter.isAllInOne();
        }
    },
    PV_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.193
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.pv_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    ALL_TO_DEFAULT { // from class: com.nfcx.eg4.global.bean.param.PARAM.194
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_all_2_default;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    RESET_TO_FACTORY { // from class: com.nfcx.eg4.global.bean.param.PARAM.195
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_reset_2_factory;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    BATTERY_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.196
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_setting;
        }
    },
    FUNC_GO_TO_OFFGRID { // from class: com.nfcx.eg4.global.bean.param.PARAM.197
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_go_to_offgrid;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() && !currentInverter.isPhase3()) {
                String standard = currentInverter.getStandard();
                int slaveVersionValue = currentInverter.getSlaveVersionValue();
                int fwVersionValue = currentInverter.getFwVersionValue();
                if ((("AAAA".equals(standard) || "aAAA".equals(standard)) && slaveVersionValue >= 36 && fwVersionValue >= 32) || (("AAAB".equals(standard) || "aAAB".equals(standard)) && slaveVersionValue >= 29 && fwVersionValue >= 26)) {
                    return !ROLE.VIEWER.equals(userData.getRole());
                }
            }
            return currentInverter.isAcCharger();
        }
    },
    FUNC_GO_TO_OFFGRID_MODE { // from class: com.nfcx.eg4.global.bean.param.PARAM.198
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_func_param_green_en_12k : R.string.phrase_func_param_go_to_offgrid;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (!ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.EG4.name().equals("EG4")) && currentInverter.getSlaveVersionValue() >= 38 && currentInverter.getFwVersionValue() >= 38;
        }
    },
    HOLD_OFF_GRID_START_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.199
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_start_hour_1_eg4 : R.string.phrase_param_go_to_off_grid_start_hour_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.200
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_start_minute_1_eg4 : R.string.phrase_param_go_to_off_grid_start_minute_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_HOUR_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.201
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_end_hour_1_eg4 : R.string.phrase_param_go_to_off_grid_end_hour_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_MINUTE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.202
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_end_minute_1_eg4 : R.string.phrase_param_go_to_off_grid_end_minute_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.203
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_start_hour_2_eg4 : R.string.phrase_param_go_to_off_grid_start_hour_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.204
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_start_minute_2_eg4 : R.string.phrase_param_go_to_off_grid_start_minute_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_HOUR_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.205
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_end_hour_2_eg4 : R.string.phrase_param_go_to_off_grid_end_hour_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_MINUTE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.206
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_end_minute_2_eg4 : R.string.phrase_param_go_to_off_grid_end_minute_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_HOUR_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.207
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_start_hour_3_eg4 : R.string.phrase_param_go_to_off_grid_start_hour_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_MINUTE_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.208
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_start_minute_3_eg4 : R.string.phrase_param_go_to_off_grid_start_minute_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_HOUR_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.209
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_end_hour_3_eg4 : R.string.phrase_param_go_to_off_grid_end_hour_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_MINUTE_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.210
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.name().equals("EG4") ? R.string.phrase_param_go_to_off_grid_end_minute_3_eg4 : R.string.phrase_param_go_to_off_grid_end_minute_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    WEEKLY_SET_MODE { // from class: com.nfcx.eg4.global.bean.param.PARAM.211
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_weekly_set_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (!currentInverter.isType6Series()) {
                return true;
            }
            if ((ROLE.MAINTAIN.equals(role) || ROLE.ADMIN.equals(role)) && PLATFORM.LUX_POWER.equals("EG4")) {
                return currentInverter.getSlaveVersionValue() >= 33 && currentInverter.getFwVersionValue() >= 34;
            }
            return true;
        }
    },
    GENERATOR_PORT_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.212
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.generator_port_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.getMachineType() == 1) {
                return false;
            }
            if (currentInverter.isType6Series()) {
                return currentInverter.getMachineType() == 0;
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck()) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.isSna12K() || currentInverter.getHardwareVersion() >= 11;
            }
            return false;
        }
    },
    GENERATOR_FUNCTION { // from class: com.nfcx.eg4.global.bean.param.PARAM.213
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.generator_function;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return currentInverter.getMachineType() == 0;
            }
            if (currentInverter.isSnaSeries() && (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck())) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
            }
            return false;
        }
    },
    AC_COUPLING_FUNCTION { // from class: com.nfcx.eg4.global.bean.param.PARAM.214
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_coupling_function;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !ROLE.VIEWER.equals(userData.getRole())) {
                return currentInverter.getMachineType() == 0;
            }
            if (currentInverter.isSnaSeries() && (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck())) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
            }
            return false;
        }
    },
    SMART_LOAD_FUNCTION { // from class: com.nfcx.eg4.global.bean.param.PARAM.215
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_function;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return currentInverter.getMachineType() == 0;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
        }
    },
    WORKING_MODE_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.216
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.working_mode_setting;
        }
    },
    FUNC_EPS_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.217
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_eps_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.is12KUsVersion() || currentInverter.isMidBox();
        }
    },
    FUNC_OVF_LOAD_DERATE_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.218
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_ovf_load_derate_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return (ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? false : true;
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return (ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? false : true;
            }
            return false;
        }
    },
    FUNC_DRMS_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.219
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_drms_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            return ((!currentInverter.isAcCharger() && !currentInverter.isHybird()) || ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? (!currentInverter.isType6Series() || currentInverter.isAllInOne() || ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck() || userData.getClusterId() >= 4) ? false : true : userData.getClusterId() < 4;
        }
    },
    FUNC_ANTI_ISLAND_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.220
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.anti_island_enable;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (currentInverter.isType6() && !currentInverter.isAllInOne()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_NEUTRAL_DETECT_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.221
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.neutral_detect_enable;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_GRID_ON_POWER_SS_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.222
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_grid_on_power_ss_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return (ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? false : true;
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return (ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? false : true;
            }
            if (currentInverter.isMidBox()) {
                return (ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? false : true;
            }
            return false;
        }
    },
    FUNC_AC_CHARGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.223
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_ac_charge_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isType6Series() || currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isMidBox();
        }
    },
    FUNC_SW_SEAMLESSLY_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.224
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_sw_seamlessly_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_SET_TO_STANDBY { // from class: com.nfcx.eg4.global.bean.param.PARAM.225
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_set_to_standby;
        }
    },
    FUNC_FORCED_DISCHG_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.226
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_forced_dischg_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    FUNC_FORCED_CHG_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.227
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_forced_chg_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    FUNC_ISO_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.228
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.iso_enable;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(role);
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
            }
            if (currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
            }
            if (currentInverter.isMidBox()) {
                return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
            }
            return false;
        }
    },
    FUNC_GFCI_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.229
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.gfci_enable;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_DCI_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.230
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.dci_enable;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FEED_IN_GRID_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.231
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_feed_in_grid_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return currentInverter.isAllowExport2Grid();
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return currentInverter.isAllowExport2Grid();
            }
            if (currentInverter.is12KUsVersion()) {
                return currentInverter.isAllowExport2Grid();
            }
            return currentInverter.isMidBox();
        }
    },
    FUNC_LSP_SET_TO_STANDBY { // from class: com.nfcx.eg4.global.bean.param.PARAM.232
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_set_to_standby;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isLsp()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PV_GRID_OFF_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.233
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_pv_grid_off_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_RUN_WITHOUT_GRID { // from class: com.nfcx.eg4.global.bean.param.PARAM.234
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_run_without_grid_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isType6() && !currentInverter.isAllInOne()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.is12KUsVersion()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_MICRO_GRID_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.235
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_micro_grid_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    FUNC_BAT_SHARED { // from class: com.nfcx.eg4.global.bean.param.PARAM.236
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_battery_shared_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.EG4.equals(userData.getPlatform())) {
                return currentInverter.isType6Series() && currentInverter.getSlaveVersion().intValue() >= 29 && currentInverter.getFwVersion().intValue() >= 29;
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isOffGrid()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_CHARGE_LAST { // from class: com.nfcx.eg4.global.bean.param.PARAM.237
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_charge_last;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isAcCharger() || currentInverter.isHybird()) ? !ROLE.VIEWER.equals(userData.getRole()) || userData.isGigabiz1User() : (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    FUNC_BUZZER_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.238
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_buzzer_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_TAKE_LOAD_TOGETHER { // from class: com.nfcx.eg4.global.bean.param.PARAM.239
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_take_load_together;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    FUNC_GREEN_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.240
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_off_grid_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if ((!ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.EG4.name().equals("EG4")) && currentInverter.getSlaveVersionValue() >= 38 && currentInverter.getFwVersionValue() >= 38) {
                return true;
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_CT_DIRECTION_REVERSED { // from class: com.nfcx.eg4.global.bean.param.PARAM.241
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_ct_direction_reversed;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isType6Series()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
        }
    },
    FUNC_TOTAL_LOAD_COMPENSATION_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.242
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_total_load_compensation_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PV_ARC_FAULT_CLEAR { // from class: com.nfcx.eg4.global.bean.param.PARAM.243
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.pv_arc_fault_clear;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6() || currentInverter.is7_10KDevice()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (userData.getClusterId() == 4 || userData.getClusterId() == 100) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PV_SELL_TO_GRID_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.244
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.export_pv_only;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    MODEL_BIT_MEASUREMENT { // from class: com.nfcx.eg4.global.bean.param.PARAM.245
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.measurement;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() || currentInverter.is7_10KDevice() || currentInverter.isGen3_6K()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_GRID_PEAK_SHAVING { // from class: com.nfcx.eg4.global.bean.param.PARAM.246
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_grid_peak_shaving;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    BIT_CT_SAMPLE_RATIO { // from class: com.nfcx.eg4.global.bean.param.PARAM.247
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ct;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
        }
    },
    MODEL_BIT_METER_BRAND { // from class: com.nfcx.eg4.global.bean.param.PARAM.248
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.meter;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isType6()) {
                    return createDropdown(new String[]{"0: Eastron", "1: WattNode", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
                }
                if (currentInverter.isGen3_6K()) {
                    return createDropdown(new String[]{"0: Eastron", "1: Rsvd"}, new String[]{"0", "1"});
                }
                return createDropdown(new String[]{"0: Eastron", "1: WattNode", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isType6()) {
                return createDropdown(new String[]{"0: Eastron", "1: WattNode", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
            }
            if (currentInverter.isGen3_6K()) {
                return createDropdown(new String[]{"0: Eastron", "1: WattNode"}, new String[]{"0", "1"});
            }
            return createDropdown(new String[]{"0: Eastron", "1: WattNode", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
        }
    },
    METER_BRAND_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.249
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.meter_brand_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (!PLATFORM.EG4.name().equals("EG4")) {
                return false;
            }
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.getFwVersion().intValue() < 30) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    FUNC_GEN_PEAK_SHAVING { // from class: com.nfcx.eg4.global.bean.param.PARAM.250
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_peak_shaving_power;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_BAT_CHARGE_CONTROL { // from class: com.nfcx.eg4.global.bean.param.PARAM.251
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_bat_charge_control;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    FUNC_BAT_DISCHARGE_CONTROL { // from class: com.nfcx.eg4.global.bean.param.PARAM.252
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_bat_discharge_control;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird()) {
                String standard = currentInverter.getStandard();
                boolean z = ("AAAA".equals(standard) || "aAAA".equals(standard)) && currentInverter.getSlaveVersionValue() >= 36 && currentInverter.getFwVersionValue() >= 32;
                boolean z2 = ("AAAB".equals(standard) || "aAAB".equals(standard)) && currentInverter.getSlaveVersionValue() >= 29 && currentInverter.getFwVersionValue() >= 26;
                if (z || z2) {
                    return true;
                }
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.isSnaSeries()) {
                return false;
            }
            currentInverter.isAcCharger();
            return false;
        }
    },
    FUNC_AC_COUPLING_FUNCTION { // from class: com.nfcx.eg4.global.bean.param.PARAM.253
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_coupling_function;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !ROLE.VIEWER.equals(userData.getRole())) {
                return currentInverter.getMachineType() == 0;
            }
            if (currentInverter.isSnaSeries() && (ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck())) {
                return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
            }
            return false;
        }
    },
    FUNC_PV_ARC { // from class: com.nfcx.eg4.global.bean.param.PARAM.254
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_pv_arc;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6() || currentInverter.is7_10KDevice()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (userData.getClusterId() == 4 || userData.getClusterId() == 100) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_SMART_LOAD_ENABLE { // from class: com.nfcx.eg4.global.bean.param.PARAM.255
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return currentInverter.getMachineType() == 0;
            }
            if (!currentInverter.isSnaSeries() || ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return currentInverter.getSubDeviceTypeValue() == 131 || currentInverter.getHardwareVersion() >= 11;
        }
    },
    FUNC_RSD_DISABLE { // from class: com.nfcx.eg4.global.bean.param.PARAM.256
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.rsd;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isSnaSeries()) {
                return false;
            }
            if (userData.getClusterId() == 4 || userData.getClusterId() == 100) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_ON_GRID_ALWAYS_ON { // from class: com.nfcx.eg4.global.bean.param.PARAM.257
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_on_grid_always_on;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_RUN_WITHOUT_GRID_12K { // from class: com.nfcx.eg4.global.bean.param.PARAM.258
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_func_param_grid_loss_warning_clear : R.string.phrase_func_param_run_without_grid_en_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.is12KUsVersion() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    FUNC_N_PE_CONNECT_INNER_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.259
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_n_pe_connect_inner_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_RETAIN_SHUTDOWN { // from class: com.nfcx.eg4.global.bean.param.PARAM.260
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_retain_shutdown;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_RETAIN_STANDBY { // from class: com.nfcx.eg4.global.bean.param.PARAM.261
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_retain_standby;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_OFFLINE_TIMEOUT { // from class: com.nfcx.eg4.global.bean.param.PARAM.262
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_offline_timeout;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PARALLEL_DATA_SYNC_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.263
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.parallel_setting_data_sync;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            if (ROLE.ADMIN.equals(userData.getRole())) {
                return true;
            }
            if (ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            return userData.getClusterId() == 4 || userData.getClusterId() == 100;
        }
    },
    BIT_AC_CHARGE_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.264
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_charge_based_on;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isHybird() || currentInverter.isAcCharger()) ? false : true;
        }
    },
    FUNC_BATTERY_BACKUP_CTRL { // from class: com.nfcx.eg4.global.bean.param.PARAM.265
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_battery_backup_ctrl;
        }
    },
    MODEL_BIT_BATTERY_TYPE { // from class: com.nfcx.eg4.global.bean.param.PARAM.266
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_type;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return true;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MODEL_BIT_BATTERY_TYPE_TEXT { // from class: com.nfcx.eg4.global.bean.param.PARAM.267
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_type;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.VIEWER.equals(userData.getRole());
        }
    },
    BACKUP_MODE_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.268
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.backup_mode_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    PEAK_SHAVING_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.269
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.peak_shaving_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    AC_CHARGE_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.270
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_charge_setting;
        }
    },
    AC_FIRST_MODE { // from class: com.nfcx.eg4.global.bean.param.PARAM.271
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_first_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    PV_CHARGE_PRIORITY { // from class: com.nfcx.eg4.global.bean.param.PARAM.272
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.pv_charge_priority;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    FORCED_DISCHARGE_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.273
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.forced_discharge_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    SELF_CONSUMPTION { // from class: com.nfcx.eg4.global.bean.param.PARAM.274
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_working_mode_0;
        }
    },
    INV_REBOOT { // from class: com.nfcx.eg4.global.bean.param.PARAM.275
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.restart_inverter;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    Battery_Charge_Discharge { // from class: com.nfcx.eg4.global.bean.param.PARAM.276
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_charge_discharge;
        }
    },
    HOLD_SYSTEM_CHARGE_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.PARAM.277
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.system_charge_soc_limit;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_POWER_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.278
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.peak_shaving_power_2_kw;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_AC_CHARGE_END_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.279
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkEnabled(boolean z) {
            return z;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isType6() ? R.string.phrase_param_stop_ac_chg_volt : inverter.isSnaSeries() ? R.string.phrase_param_ac_charge_end_battery_voltage : inverter.isMidBox() ? R.string.backup_volt_v : R.string.phrase_param_stop_ac_chg_volt;
        }
    },
    HOLD_SYSTEM_CHARGE_VOLT_LIMIT { // from class: com.nfcx.eg4.global.bean.param.PARAM.280
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.system_charge_volt_limit_v;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_AC_CHARGE_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.PARAM.281
        public int getStartRegister() {
            return 67;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.backup_soc : R.string.phrase_param_ac_charge_soc_limit;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isHybird() || currentInverter.isAcCharger() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GEN_COOL_DOWN_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.282
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bat_charge_current_limit;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return currentInverter.is12KUsVersion();
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_COM_ADDR { // from class: com.nfcx.eg4.global.bean.param.PARAM.283
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_com_addr;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    BIT_FAN_1_MAX_SPEED { // from class: com.nfcx.eg4.global.bean.param.PARAM.284
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_1_max_speed;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    BIT_FAN_2_MAX_SPEED { // from class: com.nfcx.eg4.global.bean.param.PARAM.285
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_2_max_speed;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FAN_SPEED_SLOPE_CTRL_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.286
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_speed_slope_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FAN_SPEED_CONTROL { // from class: com.nfcx.eg4.global.bean.param.PARAM.287
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_fan_speed_control;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_ENERTEK_WORKING_MODE { // from class: com.nfcx.eg4.global.bean.param.PARAM.288
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }
    },
    MIDBOX_PORT_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.289
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_PORT_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.290
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_PORT_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.291
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_PORT_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.292
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.293
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_1_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.294
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_2_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.295
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_3_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.296
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_4_mode;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.297
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_func_smart_load_enable_1 : R.string.phrase_param_smart_load;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.298
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_enable_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.299
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_enable_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.300
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_enable_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.301
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.302
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.303
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.304
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.305
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.306
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.307
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.308
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.309
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.310
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.311
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.312
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.313
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.314
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.315
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.316
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.317
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.318
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.319
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.320
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.321
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.322
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.323
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.324
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.325
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.326
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.327
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.328
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.329
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.330
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.331
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.332
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_1 { // from class: com.nfcx.eg4.global.bean.param.PARAM.333
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_2 { // from class: com.nfcx.eg4.global.bean.param.PARAM.334
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_3 { // from class: com.nfcx.eg4.global.bean.param.PARAM.335
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_3;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_4 { // from class: com.nfcx.eg4.global.bean.param.PARAM.336
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_4;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_MIDBOX_EN { // from class: com.nfcx.eg4.global.bean.param.PARAM.337
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_func_midbox_en;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return PLATFORM.EG4.equals(userData.getPlatform()) && ROLE.VIEWER.equals(userData.getRole()) && currentInverter.isType6Series() && currentInverter.getSlaveVersion().intValue() >= 29 && currentInverter.getFwVersion().intValue() >= 29;
        }
    },
    SYSTEM_GRID_CONNECT_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.338
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_connect;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole()) && userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MID_INSTALL_POSITION { // from class: com.nfcx.eg4.global.bean.param.PARAM.339
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_mid_install_position;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    MIDBOX_HOLD_BUSBAR_PCS_RATING { // from class: com.nfcx.eg4.global.bean.param.PARAM.340
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bus_bar_current;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MIDBOX_GENERATOR_CHARGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.341
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_midbox_generator_charge;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_MANUFACTURER_SETTING { // from class: com.nfcx.eg4.global.bean.param.PARAM.342
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_midbox_manufacturer_setting;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    MIDBOX_HOLD_GEN_REMOTE_TURN_OFF_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.343
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_remote_turn_off_time;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_WARN_UP_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.344
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_warn_up_time;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_COOL_DOWN_TIME { // from class: com.nfcx.eg4.global.bean.param.PARAM.345
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_cool_down_time_midbox;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_REMOTE_CTRL { // from class: com.nfcx.eg4.global.bean.param.PARAM.346
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_remote_ctrl;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_VOLT_SOC_ENABLE { // from class: com.nfcx.eg4.global.bean.param.PARAM.347
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_volt_soc_enable;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    ENERGY_RECORD_CLEAR { // from class: com.nfcx.eg4.global.bean.param.PARAM.348
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_reset_param_energy_record_clear;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    ADJ_RATIO_CLEAR { // from class: com.nfcx.eg4.global.bean.param.PARAM.349
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_reset_param_adj_ratio_clear;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FAULT_RECORD_CLEAR { // from class: com.nfcx.eg4.global.bean.param.PARAM.350
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_reset_param_fault_record_clear;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_SET_CLEAR_FUNCTION { // from class: com.nfcx.eg4.global.bean.param.PARAM.351
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_set_clear_function;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_SOC { // from class: com.nfcx.eg4.global.bean.param.PARAM.352
        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox() || !inverter.isSnaSeries()) ? R.string.phrase_param_start_ac_charge_soc_12k : R.string.phrase_param_ac_charge_start_battery_soc;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.353
        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_start_ac_charge_volt_12k : (inverter.isSnaSeries() || inverter.isHybird() || inverter.isAcCharger()) ? R.string.phrase_param_ac_charge_start_battery_voltage : R.string.phrase_param_start_ac_charge_volt_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_BACK_UP_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.PARAM.354
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_soc_limit_12k;
        }

        public int getStartRegister() {
            return 67;
        }
    },
    HOLD_BACK_UP_END_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.PARAM.355
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_stop_ac_chg_volt;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getCurrentInverter();
            return true;
        }
    },
    MIDBOX_MASTER_SN { // from class: com.nfcx.eg4.global.bean.param.PARAM.356
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_master_inverter_sn;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_SERIAL_NUM_MASTER { // from class: com.nfcx.eg4.global.bean.param.PARAM.357
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_inverter_sn_master;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_TIME_MASTER { // from class: com.nfcx.eg4.global.bean.param.PARAM.358
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_time_master;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_FW_CODE_MASTER { // from class: com.nfcx.eg4.global.bean.param.PARAM.359
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.firmware_lcd_version_master;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_DEVICE_INFORMATION { // from class: com.nfcx.eg4.global.bean.param.PARAM.360
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.device_host_information;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_EXPORT_LOCK_POWER { // from class: com.nfcx.eg4.global.bean.param.PARAM.361
        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_export_lock_power_en_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            if (userData.getCurrentInverter().isType6Series() && PLATFORM.EG4.name().equals("EG4")) {
                return ROLE.ADMIN.equals(role) || ROLE.MAINTAIN.equals(role);
            }
            return false;
        }
    };

    public boolean checkEnabled(boolean z) {
        return z;
    }

    public boolean checkVisible(UserData userData) {
        return false;
    }

    public Object getPlatformSpecificData(UserData userData) throws JSONException {
        return null;
    }

    public abstract int getResourceId(Inverter inverter);

    public boolean checkInvisible(UserData userData) {
        if (userData.getRole() == null || userData.getCurrentInverter() == null) {
            return true;
        }
        return !checkVisible(userData);
    }

    protected JSONObject createDropdown(String[] strArr, String[] strArr2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "select");
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < strArr.length; i++) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(TextBundle.TEXT_ENTRY, strArr[i]);
            jSONObject2.put("value", strArr2[i]);
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("column", jSONArray);
        return jSONObject;
    }
}