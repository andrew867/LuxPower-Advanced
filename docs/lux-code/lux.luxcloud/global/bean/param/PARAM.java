package com.lux.luxcloud.global.bean.param;

import android.util.Pair;
import androidx.savedstate.serialization.ClassDiscriminatorModeKt;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.global.bean.user.ROLE;
import com.lux.luxcloud.global.cache.RemoteSetCacheManager;
import com.lux.luxcloud.version.Custom;
import java.util.ArrayList;
import java.util.Arrays;
import org.bouncycastle.asn1.BERTags;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum PARAM {
    HOLD_PV_INPUT_MODE { // from class: com.lux.luxcloud.global.bean.param.PARAM.1
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_pv_input_mode_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_pv_input_mode;
        }

        public int getStartRegister() {
            return 20;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.is7_10KDevice() && "HAAA".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 7 && currentInverter.getFwVersionValue() >= 6) {
                    return createDropdown(new String[]{"0: NO PV", "1: PV1 in", "2: PV2 in", "3: PV1, PV2 Connect Same Panel String", "4: PV1, PV2 Connect Different Panel String"}, new String[]{"0", "1", "2", "3", "4"});
                }
                if (currentInverter.isTrip12K()) {
                    return createDropdown(new String[]{"0: No PV parallel", "1: PV1, PV2 Connect Same Panel String", "2: PV1, PV3 Connect Same Panel String", "3: PV2, PV3 Connect Same Panel String", "4: PV1, PV2, PV3 Connect Same Panel String"}, new String[]{"0", "1", "2", "3", "4"});
                }
                if (currentInverter.isGen3_6K()) {
                    return createDropdown(new String[]{"0: No PV parallel", "1: PV1 Only", "2: PV2 Only", "3: PV1, PV2 Connect Same Panel String", "4: PV1, PV2 Connect Different Panel String"}, new String[]{"0", "1", "2", "3", "4"});
                }
                if (currentInverter.isType6Series() || currentInverter.is7_10KDevice() || currentInverter.isAllInOne()) {
                    return createDropdown(new String[]{"0: NO PV", "1: PV1 in", "2: PV2 in", "3: PV3 in", "4: PV1&2 in", "5: PV1&3 in", "6: PV2&3 in", "7: PV1&2&3 in"}, new String[]{"0", "1", "2", "3", "4", "5", "6", "7"});
                }
                if (currentInverter.isSnaSeries()) {
                    return createDropdown(new String[]{"0: DC source mode", "3: Two MPPT connects to same string", "4: Two MPPT connects to different string"}, new String[]{"0", "3", "4"});
                }
                if (currentInverter.isSnaSeries() || currentInverter.isLsp()) {
                    return null;
                }
                return createDropdown(new String[]{"No PV Panel", "PV1 Only", "PV2 Only", "PV1, PV2 Connect Same Panel String", "PV1, PV2 Connect Different Panel String"}, new String[]{"0", "1", "2", "3", "4"});
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.is7_10KDevice() && "HAAA".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 7 && currentInverter.getFwVersionValue() >= 6) {
                return createDropdown(new String[]{"0: NO PV", "1: PV1 in", "2: PV2 in", "3: PV1, PV2 Connect Same Panel String", "4: PV1, PV2 Connect Different Panel String"}, new String[]{"0", "1", "2", "3", "4"});
            }
            if (currentInverter.isTrip12K()) {
                return createDropdown(new String[]{"0: No PV parallel", "1: PV1, PV2 Connect Same Panel String", "2: PV1, PV3 Connect Same Panel String", "3: PV2, PV3 Connect Same Panel String", "4: PV1, PV2, PV3 Connect Same Panel String"}, new String[]{"0", "1", "2", "3", "4"});
            }
            if (currentInverter.isGen3_6K()) {
                return createDropdown(new String[]{"0: No PV parallel", "1: PV1 Only", "2: PV2 Only", "3: PV1, PV2 Connect Same Panel String", "4: PV1, PV2 Connect Different Panel String"}, new String[]{"0", "1", "2", "3", "4"});
            }
            if (currentInverter.isType6Series() || currentInverter.is7_10KDevice() || currentInverter.isAllInOne()) {
                return createDropdown(new String[]{"0: NO PV", "1: PV1 in", "2: PV2 in", "3: PV3 in", "4: PV1&2 in", "5: PV1&3 in", "6: PV2&3 in", "7: PV1&2&3 in"}, new String[]{"0", "1", "2", "3", "4", "5", "6", "7"});
            }
            if (currentInverter.isSnaSeries()) {
                return createDropdown(new String[]{"0: DC source mode", "3: Two MPPT connects to same string", "4: Two MPPT connects to different string"}, new String[]{"0", "3", "4"});
            }
            if (currentInverter.isSnaSeries() || currentInverter.isLsp()) {
                return null;
            }
            return createDropdown(new String[]{"No PV Panel", "PV1 Only", "PV2 Only", "PV1, PV2 Connect Same Panel String", "PV1, PV2 Connect Different Panel String"}, new String[]{"0", "1", "2", "3", "4"});
        }
    },
    HOLD_START_PV_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.2
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_start_pv_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_start_pv_volt;
        }

        public int getStartRegister() {
            return 22;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_CONNECT_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.3
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_connectTimeInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_connect_time;
        }

        public int getStartRegister() {
            return 23;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_RECONNECT_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.4
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_reconnect_time;
        }

        public int getStartRegister() {
            return 24;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_CONN_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.5
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_gridVoltConnLowInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_volt_conn_low;
        }

        public int getStartRegister() {
            return 25;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_CONN_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.6
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_gridVoltConnHighInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_volt_conn_high;
        }

        public int getStartRegister() {
            return 26;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_CONN_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.7
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_gridFreqConnLowInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_freq_conn_low;
        }

        public int getStartRegister() {
            return 27;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_CONN_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.8
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_gridFreqConnHighInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_freq_conn_high;
        }

        public int getStartRegister() {
            return 28;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT1_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.9
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l1;
        }

        public int getStartRegister() {
            return 29;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_low_volt : R.string.phrase_param_grid_volt_limit1_low;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT1_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.10
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_h1;
        }

        public int getStartRegister() {
            return 30;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_high_volt : R.string.phrase_param_grid_volt_limit1_high;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT1_LOW_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.11
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l1_time_tip;
        }

        public int getStartRegister() {
            return 31;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_low_time_volt : R.string.phrase_param_grid_volt_limit1_low_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT1_HIGH_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.12
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l1_time_tip;
        }

        public int getStartRegister() {
            return 32;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit1_high_time_volt : R.string.phrase_param_grid_volt_limit1_high_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT2_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.13
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l2;
        }

        public int getStartRegister() {
            return 33;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit2_low_volt : R.string.phrase_param_grid_volt_limit2_low;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT2_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.14
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_h2;
        }

        public int getStartRegister() {
            return 34;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit2_high_volt : R.string.phrase_param_grid_volt_limit2_high;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT2_LOW_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.15
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l2;
        }

        public int getStartRegister() {
            return 35;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.page_setting_tip_grid_limit_l2_time_volt : R.string.page_setting_tip_grid_volt_limit_l2_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT2_HIGH_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.16
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_h2_time_tip;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_h2_time;
        }

        public int getStartRegister() {
            return 36;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT3_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.17
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l3;
        }

        public int getStartRegister() {
            return 37;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit3_low_volt : R.string.phrase_param_grid_volt_limit3_low;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT3_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.18
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_h3;
        }

        public int getStartRegister() {
            return 38;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_limit3_high_volt : R.string.phrase_param_grid_volt_limit3_high;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_LIMIT3_LOW_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.19
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_l3_time;
        }

        public int getStartRegister() {
            return 39;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.page_setting_tip_grid_limit_l3_time_volt : R.string.page_setting_tip_grid_frequency_l3_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_VOLT_LIMIT3_HIGH_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.20
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_volt_limit_h3_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_frequency_h3_time;
        }

        public int getStartRegister() {
            return 40;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_VOLT_MOV_AVG_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.21
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Grid_Volt_Mov_Avg_High_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_volt_mov_avg_high;
        }

        public int getStartRegister() {
            return 41;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT1_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.22
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_l1;
        }

        public int getStartRegister() {
            return 42;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit1_low_hz : R.string.phrase_param_grid_freq_limit1_low;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT1_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.23
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h1;
        }

        public int getStartRegister() {
            return 43;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit1_high_hz : R.string.phrase_param_grid_freq_limit1_high;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT1_LOW_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.24
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h1_time_tip;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_l1_time;
        }

        public int getStartRegister() {
            return 44;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT1_HIGH_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.25
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h1_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_h1_time;
        }

        public int getStartRegister() {
            return 45;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT2_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.26
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_l2;
        }

        public int getStartRegister() {
            return 46;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit2_low_hz : R.string.phrase_param_grid_freq_limit2_low;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT2_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.27
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h2;
        }

        public int getStartRegister() {
            return 47;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit2_high_hz : R.string.phrase_param_grid_freq_limit2_high;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT2_LOW_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.28
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_l2_time;
        }

        public int getStartRegister() {
            return 48;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT2_HIGH_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.29
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h2_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_volt_limit_h2_time;
        }

        public int getStartRegister() {
            return 49;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT3_LOW { // from class: com.lux.luxcloud.global.bean.param.PARAM.30
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_l2;
        }

        public int getStartRegister() {
            return 50;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit3_low_hz : R.string.phrase_param_grid_freq_limit3_low;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT3_HIGH { // from class: com.lux.luxcloud.global.bean.param.PARAM.31
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h3;
        }

        public int getStartRegister() {
            return 51;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_grid_freq_limit3_high_hz : R.string.phrase_param_grid_freq_limit3_high;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_GRID_FREQ_LIMIT3_LOW_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.32
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_frequency_l3_time;
        }

        public int getStartRegister() {
            return 52;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_GRID_FREQ_LIMIT3_HIGH_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.33
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_grid_frequency_h3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_setting_tip_grid_frequency_h3_time;
        }

        public int getStartRegister() {
            return 53;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    INPUT_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.34
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole());
        }
    },
    HOLD_MAX_Q_PERCENT_FOR_QV { // from class: com.lux.luxcloud.global.bean.param.PARAM.35
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 54;
        }
    },
    HOLD_V1L { // from class: com.lux.luxcloud.global.bean.param.PARAM.36
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 55;
        }
    },
    HOLD_V2L { // from class: com.lux.luxcloud.global.bean.param.PARAM.37
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 56;
        }
    },
    HOLD_V1H { // from class: com.lux.luxcloud.global.bean.param.PARAM.38
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 57;
        }
    },
    HOLD_V2H { // from class: com.lux.luxcloud.global.bean.param.PARAM.39
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 58;
        }
    },
    HOLD_REACTIVE_POWER_CMD_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.40
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.reactive_power_type;
        }

        public int getStartRegister() {
            return 59;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_ACTIVE_POWER_PERCENT_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.41
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 60;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_REACTIVE_POWER_PERCENT_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.42
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.reactive_power_percent;
        }

        public int getStartRegister() {
            return 61;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_PF_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.43
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_pf_cmd_input;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 62;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_POWER_SOFT_START_SLOPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.44
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_reactive_power_percent_cmd;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_midbox_soft_start_slope;
        }

        public int getStartRegister() {
            return 63;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getRole().getInstallerLevelCheck()) ? false : true;
        }
    },
    HOLD_CHARGE_POWER_PERCENT_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.45
        public int getStartRegister() {
            return 64;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isTrip12K() ? R.string.phrase_param_charge_power_cmd : R.string.phrase_param_charge_power_percent_cmd;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries() || currentInverter.isTrip12K()) {
                return false;
            }
            if (currentInverter.isType6Series()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return true;
        }
    },
    HOLD_DISCHG_POWER_PERCENT_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.46
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_discharge_power_percent_cmd;
        }

        public int getStartRegister() {
            return 65;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return false;
            }
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    HOLD_AC_CHARGE_POWER_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.47
        public int getStartRegister() {
            return 66;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_ac_charge_power_cmd_12k : R.string.phrase_param_ac_charge_power_cmd;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isType6Series() ? (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true : currentInverter.isAcCharger() || currentInverter.isHybird();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isType6() || currentInverter.isMidBox()) {
                    return createInputBox("25.5", "0");
                }
                return createInputBox("100", "0");
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isType6() || currentInverter.isMidBox()) {
                return createInputBox("25.5", "0");
            }
            return createInputBox("100", "0");
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.phrase_param_tip_ac_charge_power_cmd_12k;
        }
    },
    HOLD_AC_CHARGE_START_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.48
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 68;
        }
    },
    HOLD_AC_CHARGE_START_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.49
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_hint_minute_range;
        }

        public int getStartRegister() {
            return 68;
        }
    },
    HOLD_AC_CHARGE_END_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.50
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time;
        }

        public int getStartRegister() {
            return 69;
        }
    },
    HOLD_AC_CHARGE_END_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.51
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time;
        }

        public int getStartRegister() {
            return 69;
        }
    },
    HOLD_AC_CHARGE_START_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.52
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_1;
        }

        public int getStartRegister() {
            return 70;
        }
    },
    HOLD_AC_CHARGE_START_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.53
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_1;
        }

        public int getStartRegister() {
            return 70;
        }
    },
    HOLD_AC_CHARGE_END_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.54
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_1;
        }

        public int getStartRegister() {
            return 71;
        }
    },
    HOLD_AC_CHARGE_END_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.55
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_1;
        }

        public int getStartRegister() {
            return 71;
        }
    },
    HOLD_AC_CHARGE_START_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.56
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_2;
        }

        public int getStartRegister() {
            return 72;
        }
    },
    HOLD_AC_CHARGE_START_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.57
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_start_time_2;
        }

        public int getStartRegister() {
            return 72;
        }
    },
    HOLD_AC_CHARGE_END_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.58
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_2;
        }

        public int getStartRegister() {
            return 73;
        }
    },
    HOLD_AC_CHARGE_END_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.59
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_time_2;
        }

        public int getStartRegister() {
            return 73;
        }
    },
    HOLD_FORCED_CHG_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.PARAM.60
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_forced_chg_soc_limit;
        }

        public int getStartRegister() {
            return 75;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isMidBox() || inverter.isType6Series()) ? R.string.phrase_param_forced_chg_soc_limit_12k : R.string.phrase_param_forced_chg_soc_limit;
        }
    },
    _12K_HOLD_CHARGE_FIRST_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.61
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_charge_first_volt_eg4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_first_volt;
        }

        public int getStartRegister() {
            return 201;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHG_POWER_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.62
        public int getStartRegister() {
            return 74;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isMidBox() || inverter.isType6Series()) ? R.string.phrase_param_forced_chg_power_cmd_12k : R.string.phrase_param_forced_chg_power_cmd;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isType6Series() ? (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true : !currentInverter.isSnaSeries();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.page_maintain_remote_set_tip_forced_chg_power_cmd;
        }
    },
    HOLD_FORCED_CHARGE_START_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.63
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time;
        }

        public int getStartRegister() {
            return 76;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.64
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time;
        }

        public int getStartRegister() {
            return 76;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.65
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time;
        }

        public int getStartRegister() {
            return 77;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.66
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time;
        }

        public int getStartRegister() {
            return 77;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.67
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_1;
        }

        public int getStartRegister() {
            return 78;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.68
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_1;
        }

        public int getStartRegister() {
            return 78;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.69
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_1;
        }

        public int getStartRegister() {
            return 79;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.70
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_1;
        }

        public int getStartRegister() {
            return 79;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.71
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_2;
        }

        public int getStartRegister() {
            return 80;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_START_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.72
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_start_time_2;
        }

        public int getStartRegister() {
            return 80;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.73
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_2;
        }

        public int getStartRegister() {
            return 81;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_CHARGE_END_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.74
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_charge_end_time_2;
        }

        public int getStartRegister() {
            return 81;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_FORCED_DISCHG_POWER_CMD { // from class: com.lux.luxcloud.global.bean.param.PARAM.75
        public int getStartRegister() {
            return 82;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isMidBox() || inverter.isType6Series()) ? R.string.phrase_param_forced_dischg_power_cmd_12k : R.string.phrase_param_forced_dischg_power_cmd;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.page_setting_tip_2023_forcedDisChgPowerCMDInput_12k;
        }
    },
    HOLD_FORCED_DISCHG_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.PARAM.76
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_forcedDisChgSOCLimitInput_12k;
        }

        public int getStartRegister() {
            return 83;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isMidBox() || inverter.isType6Series()) ? R.string.phrase_param_forced_dischg_soc_limit_12k : R.string.phrase_param_forced_dischg_soc_limit;
        }
    },
    HOLD_FORCED_DISCHARGE_START_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.77
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time;
        }

        public int getStartRegister() {
            return 84;
        }
    },
    HOLD_FORCED_DISCHARGE_START_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.78
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time;
        }

        public int getStartRegister() {
            return 84;
        }
    },
    HOLD_FORCED_DISCHARGE_END_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.79
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time;
        }

        public int getStartRegister() {
            return 85;
        }
    },
    HOLD_FORCED_DISCHARGE_END_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.80
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time;
        }

        public int getStartRegister() {
            return 85;
        }
    },
    HOLD_FORCED_DISCHARGE_START_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.81
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_1;
        }

        public int getStartRegister() {
            return 86;
        }
    },
    HOLD_FORCED_DISCHARGE_START_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.82
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_1;
        }

        public int getStartRegister() {
            return 86;
        }
    },
    HOLD_FORCED_DISCHARGE_END_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.83
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_1;
        }

        public int getStartRegister() {
            return 87;
        }
    },
    HOLD_FORCED_DISCHARGE_END_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.84
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_1;
        }

        public int getStartRegister() {
            return 87;
        }
    },
    HOLD_FORCED_DISCHARGE_START_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.85
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_2;
        }

        public int getStartRegister() {
            return 88;
        }
    },
    HOLD_FORCED_DISCHARGE_START_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.86
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_start_time_2;
        }

        public int getStartRegister() {
            return 88;
        }
    },
    HOLD_FORCED_DISCHARGE_END_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.87
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_2;
        }

        public int getStartRegister() {
            return 89;
        }
    },
    HOLD_FORCED_DISCHARGE_END_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.88
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_discharge_end_time_2;
        }

        public int getStartRegister() {
            return 89;
        }
    },
    HOLD_EPS_VOLT_SET { // from class: com.lux.luxcloud.global.bean.param.PARAM.89
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_eps_voltage_set;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_eps_voltage_set;
        }

        public int getStartRegister() {
            return 90;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            if (userData.getCurrentInverter().isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                if (currentInverter.getDtcValue().intValue() == 1) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    arrayList.add("127/220 three phase");
                    arrayList2.add("127");
                    arrayList.add("100/200 split phase");
                    arrayList2.add("200");
                    arrayList.add("120/208");
                    arrayList2.add("208");
                    arrayList.add("115/230");
                    arrayList2.add("230");
                    arrayList.add("120/240");
                    arrayList2.add("240");
                    return createDropdown((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
                }
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                arrayList3.add("120");
                arrayList4.add("120");
                arrayList3.add("200");
                arrayList4.add("200");
                arrayList3.add("208");
                arrayList4.add("208");
                arrayList3.add("230");
                arrayList4.add("230");
                return createDropdown((String[]) arrayList3.toArray(new String[0]), (String[]) arrayList4.toArray(new String[0]));
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                arrayList5.add("208");
                arrayList6.add("208");
                arrayList5.add("230");
                arrayList6.add("230");
                arrayList5.add("240");
                arrayList6.add("240");
                return createDropdown((String[]) arrayList5.toArray(new String[0]), (String[]) arrayList6.toArray(new String[0]));
            }
            if (!currentInverter.isSna12K()) {
                return null;
            }
            ArrayList arrayList7 = new ArrayList();
            ArrayList arrayList8 = new ArrayList();
            arrayList7.add("120");
            arrayList8.add("120");
            arrayList7.add("200");
            arrayList8.add("200");
            arrayList7.add("208");
            arrayList8.add("208");
            arrayList7.add("230");
            arrayList8.add("230");
            arrayList7.add("240");
            arrayList8.add("240");
            return createDropdown((String[]) arrayList7.toArray(new String[0]), (String[]) arrayList8.toArray(new String[0]));
        }
    },
    HOLD_EPS_FREQ_SET { // from class: com.lux.luxcloud.global.bean.param.PARAM.90
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_eps_frequency_set;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_eps_frequency_set;
        }

        public int getStartRegister() {
            return 91;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_DELAY_TIME_FOR_QV_CURVE { // from class: com.lux.luxcloud.global.bean.param.PARAM.91
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 96;
        }
    },
    HOLD_DELAY_TIME_FOR_OVER_F_DERATE { // from class: com.lux.luxcloud.global.bean.param.PARAM.92
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 97;
        }
    },
    HOLD_LEAD_ACID_CHARGE_VOLT_REF { // from class: com.lux.luxcloud.global.bean.param.PARAM.93
        public int getStartRegister() {
            return 99;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isSnaSeries() || inverter.isType6() || inverter.isMidBox() || inverter.isHybird() || !inverter.isAcCharger()) ? R.string.phrase_param_lead_acid_charge_volt_ref_12k : R.string.phrase_param_lead_acid_charge_volt_ref;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.94
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_active_grid_off_Volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_lead_acid_cut_off_volt;
        }

        public int getStartRegister() {
            return 100;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            if (userData.getCurrentInverter().isTrip12K()) {
                return createInputBox("700", "80");
            }
            return createInputBox("56", "40");
        }
    },
    HOLD_LEAD_ACID_CHARGE_RATE { // from class: com.lux.luxcloud.global.bean.param.PARAM.95
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_leadAcidChargeRateInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_current_limit_adc;
        }

        public int getStartRegister() {
            return 101;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isSnaSeries()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isMidBox()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            return false;
        }
    },
    HOLD_LEAD_ACID_DISCHARGE_RATE { // from class: com.lux.luxcloud.global.bean.param.PARAM.96
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_for_lead_acid;
        }

        public int getStartRegister() {
            return 102;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isSnaSeries() || inverter.isLsp() || inverter.isHybird() || !inverter.isType6()) ? R.string.phrase_param_lead_acid_discharge_rate : R.string.phrase_param_dischg_current;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_FEED_IN_GRID_POWER_PERCENT { // from class: com.lux.luxcloud.global.bean.param.PARAM.97
        public int getStartRegister() {
            return 103;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger()) {
                return true;
            }
            if (currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            if (currentInverter.isSnaSeries() || currentInverter.isType6Series()) {
                return true;
            }
            if (currentInverter.isAllInOne()) {
                return false;
            }
            currentInverter.is12KUsVersion();
            return false;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries() ? R.string.page_setting_tip_export_power_percent : R.string.page_setting_tip_2023_maxBackFlowInput_12k;
        }
    },
    HOLD_DISCHG_CUT_OFF_SOC_EOD { // from class: com.lux.luxcloud.global.bean.param.PARAM.98
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_active_grid_on;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_dischg_cut_off_soc_eod;
        }

        public int getStartRegister() {
            return 105;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_DISCHG { // from class: com.lux.luxcloud.global.bean.param.PARAM.99
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.discharge_tempature_low_limit;
        }

        public int getStartRegister() {
            return 106;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_DISCHG { // from class: com.lux.luxcloud.global.bean.param.PARAM.100
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.discharge_tempature_high_limit;
        }

        public int getStartRegister() {
            return 107;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_LEAD_ACID_TEMPR_LOWER_LIMIT_CHG { // from class: com.lux.luxcloud.global.bean.param.PARAM.101
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_temperature_low_limit;
        }

        public int getStartRegister() {
            return 108;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_LEAD_ACID_TEMPR_UPPER_LIMIT_CHG { // from class: com.lux.luxcloud.global.bean.param.PARAM.102
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_temperature_high_limit;
        }

        public int getStartRegister() {
            return 109;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_SET_MASTER_OR_SLAVE { // from class: com.lux.luxcloud.global.bean.param.PARAM.103
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_set_master_or_slave;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_master_or_slave;
        }

        public int getStartRegister() {
            return 112;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries() || currentInverter.isAcCharger()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isHybird()) {
                return true;
            }
            return currentInverter.isType6Series() && !currentInverter.isAllInOne();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isTrip12K()) {
                    return createDropdown(new String[]{"Master", "Subordinates"}, new String[]{"3", "2"});
                }
                if ((currentInverter.is7_10KDevice() && currentInverter.getPhaseValue().intValue() != 1) || currentInverter.isGen3_6K()) {
                    return createDropdown(new String[]{"1 Phase Master", "Subordinates", "3 Phase Master"}, new String[]{"1", "2", "3"});
                }
                if (currentInverter.isAcCharger()) {
                    return createDropdown(new String[]{"1 Phase Master", "Subordinates", "3 Phase Master"}, new String[]{"1", "2", "3"});
                }
                if (currentInverter.isSnaSeries()) {
                    return createDropdown(new String[]{"No Parallel", "Single Phase Parallel", "Three Phase Parallel"}, new String[]{"0", "1", "3"});
                }
                return createDropdown(new String[]{"1 Phase Master", "Subordinates", "3 Phase Master", "2x208 Master"}, new String[]{"1", "2", "3", "4"});
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isTrip12K()) {
                return createDropdown(new String[]{"Master", "Subordinates"}, new String[]{"3", "2"});
            }
            if ((currentInverter.is7_10KDevice() && currentInverter.getPhaseValue().intValue() != 1) || currentInverter.isGen3_6K()) {
                return createDropdown(new String[]{"1 Phase Master", "Subordinates", "3 Phase Master"}, new String[]{"1", "2", "3"});
            }
            if (currentInverter.isAcCharger()) {
                return createDropdown(new String[]{"1 Phase Master", "Subordinates", "3 Phase Master"}, new String[]{"1", "2", "3"});
            }
            if (currentInverter.isSnaSeries()) {
                return createDropdown(new String[]{"No Parallel", "Single Phase Parallel", "Three Phase Parallel"}, new String[]{"0", "1", "3"});
            }
            return createDropdown(new String[]{"1 Phase Master", "Subordinates", "3 Phase Master", "2x208 Master"}, new String[]{"1", "2", "3", "4"});
        }
    },
    HOLD_SET_COMPOSED_PHASE { // from class: com.lux.luxcloud.global.bean.param.PARAM.104
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_set_composed_phase;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_composed_phase;
        }

        public int getStartRegister() {
            return 113;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries() || currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isType6Series() && !currentInverter.isAllInOne();
        }
    },
    _12K_HOLD_OVF_DERATE_START_POINT { // from class: com.lux.luxcloud.global.bean.param.PARAM.105
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 115;
        }
    },
    HOLD_P_TO_USER_START_DISCHG { // from class: com.lux.luxcloud.global.bean.param.PARAM.106
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_pToUserStartDisChgInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.start_discharge_p_import_w;
        }

        public int getStartRegister() {
            return 116;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird()) {
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
    MODEL_BIT_LITHIUM_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.107
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_lithium_brand_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_type_lithium;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Pair<String[], String[]> lithiumOptions_Hybrid;
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isTrip12K()) {
                lithiumOptions_Hybrid = getLithiumOptions_Trip12K(userData);
            } else if (PLATFORM.EG4.equals(userData.getPlatform())) {
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
        private android.util.Pair<java.lang.String[], java.lang.String[]> getLithiumOptions_US(com.lux.luxcloud.global.UserData r20) {
            /*
                Method dump skipped, instructions count: 271
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.global.bean.param.PARAM.AnonymousClass107.getLithiumOptions_US(com.lux.luxcloud.global.UserData):android.util.Pair");
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
    HOLD_VBAT_START_DERATING { // from class: com.lux.luxcloud.global.bean.param.PARAM.108
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.on_grid_discharge_derate_vbatt_v;
        }

        public int getStartRegister() {
            return 118;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isSnaSeries()) {
                return ((currentInverter.isSna12K() && currentInverter.getPhaseValue().intValue() == 1 && currentInverter.getDtcValue().intValue() == 0) || currentInverter.isOffGrid()) ? false : true;
            }
            return false;
        }
    },
    HOLD_CT_POWER_OFFSET { // from class: com.lux.luxcloud.global.bean.param.PARAM.109
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ct_power_offset;
        }

        public int getStartRegister() {
            return 119;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            userData.getCurrentInverter();
            return R.string.page_setting_tip_ct_power_offset;
        }
    },
    HOLD_MAINTENANCE_COUNT { // from class: com.lux.luxcloud.global.bean.param.PARAM.110
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 122;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_SOC_LOW_LIMIT_EPS_DISCHG { // from class: com.lux.luxcloud.global.bean.param.PARAM.111
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_active_grid_off;
        }

        public int getStartRegister() {
            return 125;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (!inverter.isType6() && inverter.isSnaSeries()) ? R.string.fragment_remote_set_soc_cut_off_soc : R.string.phrase_param_soc_cut_off_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !userData.getCurrentInverter().isAllInOne();
        }
    },
    HOLD_OPTIMAL_CHG_DISCHG_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.112
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 126;
        }
    },
    HOLD_UVF_DERATE_START_POINT { // from class: com.lux.luxcloud.global.bean.param.PARAM.113
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA;
        }
    },
    HOLD_OVF_DROOP_KOF { // from class: com.lux.luxcloud.global.bean.param.PARAM.114
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 136;
        }
    },
    HOLD_SPEC_LOAD_COMPENSATE { // from class: com.lux.luxcloud.global.bean.param.PARAM.115
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.big_load_compensate_power;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return ROLE.ADMIN.equals(role) || role.getOwnerLevelCheck();
            }
            return false;
        }
    },
    HOLD_FLOATING_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.116
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_floating_voltage;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_OUTPUT_CONFIGURATION { // from class: com.lux.luxcloud.global.bean.param.PARAM.117
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_output_configuration;
        }

        public int getStartRegister() {
            return 145;
        }
    },
    HOLD_NOMINAL_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.118
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_norminal_battery_voltage;
        }

        public int getStartRegister() {
            return 148;
        }
    },
    HOLD_EQUALIZATION_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.119
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_equalization_voltage;
        }

        public int getStartRegister() {
            return 149;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    HOLD_EQUALIZATION_PERIOD { // from class: com.lux.luxcloud.global.bean.param.PARAM.120
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_equalization_period;
        }

        public int getStartRegister() {
            return 150;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    HOLD_EQUALIZATION_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.121
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_equalization_time;
        }

        public int getStartRegister() {
            return 151;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_AC_FIRST_START_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.122
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_1;
        }

        public int getStartRegister() {
            return 152;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.123
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_1;
        }

        public int getStartRegister() {
            return 152;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_HOUR { // from class: com.lux.luxcloud.global.bean.param.PARAM.124
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time;
        }

        public int getStartRegister() {
            return 153;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_MINUTE { // from class: com.lux.luxcloud.global.bean.param.PARAM.125
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time;
        }

        public int getStartRegister() {
            return 153;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.126
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 154;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.127
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_1;
        }

        public int getStartRegister() {
            return 154;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.128
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_1;
        }

        public int getStartRegister() {
            return 155;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.129
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_1;
        }

        public int getStartRegister() {
            return 155;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.130
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_2;
        }

        public int getStartRegister() {
            return 156;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_START_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.131
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_start_time_2;
        }

        public int getStartRegister() {
            return 156;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.132
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_2;
        }

        public int getStartRegister() {
            return 157;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_FIRST_END_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.133
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_first_end_time_2;
        }

        public int getStartRegister() {
            return 157;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_AC_CHARGE_END_BATTERY_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.134
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_end_battery_soc;
        }

        public int getStartRegister() {
            return 161;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox() || currentInverter.isSnaSeries();
        }
    },
    HOLD_BATTERY_WARNING_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.135
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_voltage;
        }

        public int getStartRegister() {
            return 162;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM);
            }
            return false;
        }
    },
    HOLD_BATTERY_WARNING_RECOVERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.136
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_recovery_voltage;
        }

        public int getStartRegister() {
            return 163;
        }
    },
    HOLD_BATTERY_WARNING_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.137
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_soc;
        }

        public int getStartRegister() {
            return 164;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM);
            }
            return false;
        }
    },
    HOLD_BATTERY_WARNING_RECOVERY_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.138
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_warning_recovery_soc;
        }

        public int getStartRegister() {
            return 165;
        }
    },
    HOLD_BATTERY_LOW_TO_UTILITY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.139
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_low_to_utility_voltage;
        }

        public int getStartRegister() {
            return 166;
        }
    },
    HOLD_BATTERY_LOW_TO_UTILITY_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.140
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_battery_low_to_utility_soc;
        }

        public int getStartRegister() {
            return 167;
        }
    },
    HOLD_AC_CHARGE_BATTERY_CURRENT { // from class: com.lux.luxcloud.global.bean.param.PARAM.141
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_charge_battery_current;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_chg_bat_current;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    HOLD_ON_GRID_EOD_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.142
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_on_grid_off_Volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_on_grid_eod_voltage;
        }

        public int getStartRegister() {
            return 169;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isSnaSeries();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            if (userData.getCurrentInverter().isTrip12K()) {
                return createInputBox("700", "80");
            }
            return createInputBox("56", "40");
        }
    },
    HOLD_MAX_AC_INPUT_POWER { // from class: com.lux.luxcloud.global.bean.param.PARAM.143
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_maxAcInputPowerInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_max_ac_input_power_kw;
        }

        public int getStartRegister() {
            return 176;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_MAX_GENERATOR_INPUT_POWER { // from class: com.lux.luxcloud.global.bean.param.PARAM.144
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_peak_shaving_power;
        }

        public int getStartRegister() {
            return 177;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isSnaSeries() || currentInverter.isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            userData.getCurrentInverter();
            return R.string.page_setting_tip_2023_genPeakShavingPowerInput_12k;
        }
    },
    HOLD_VOLT_WATT_V1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.145
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 181;
        }
    },
    HOLD_VOLT_WATT_V2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.146
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 182;
        }
    },
    HOLD_VOLT_WATT_DELAY_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.147
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 183;
        }
    },
    HOLD_VOLT_WATT_P2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.148
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 184;
        }
    },
    HOLD_VREF { // from class: com.lux.luxcloud.global.bean.param.PARAM.149
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 185;
        }
    },
    HOLD_VREF_ADJUSTMENT_TIME_CONSTANT { // from class: com.lux.luxcloud.global.bean.param.PARAM.150
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_Q3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.151
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 187;
        }
    },
    HOLD_Q4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.152
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 188;
        }
    },
    HOLD_P1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.153
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256;
        }
    },
    HOLD_P2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.154
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 190;
        }
    },
    HOLD_P3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.155
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 191;
        }
    },
    HOLD_UVF_DROOP_KUF { // from class: com.lux.luxcloud.global.bean.param.PARAM.156
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }

        public int getStartRegister() {
            return 193;
        }
    },
    OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR { // from class: com.lux.luxcloud.global.bean.param.PARAM.157
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_batChargeCurrentLimitInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bat_charge_current_limit;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isMidBox() || (currentInverter.isType6Series() && !currentInverter.isAllInOne())) ? currentInverter.getMachineType() != 1 : currentInverter.isSnaSeries() || currentInverter.isHybird() || currentInverter.isAcCharger();
        }
    },
    _12K_HOLD_STOP_DISCHG_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.158
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_stop_dischg_volt;
        }

        public int getStartRegister() {
            return 202;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_REGULATION { // from class: com.lux.luxcloud.global.bean.param.PARAM.159
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_regulation;
        }

        public int getStartRegister() {
            return 203;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    _12K_HOLD_LEAD_CAPACITY { // from class: com.lux.luxcloud.global.bean.param.PARAM.160
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.lead_acid_capacity_ah;
        }

        public int getStartRegister() {
            return 204;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) throws JSONException {
            int i;
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries()) {
                JSONObject cache = RemoteSetCacheManager.getInstance().getCache(currentInverter.getSerialNum());
                if (!cache.has("HOLD_MODEL_leadAcidType")) {
                    return false;
                }
                try {
                    i = cache.getInt("HOLD_MODEL_leadAcidType");
                } catch (JSONException unused) {
                    i = 0;
                }
                return i == 31;
            }
            if ((!currentInverter.isType6Series() || currentInverter.isAllInOne()) && !currentInverter.isMidBox()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(userData.getRole());
        }
    },
    _12K_HOLD_GRID_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.161
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_gridTypeInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_type;
        }

        public int getStartRegister() {
            return 205;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_POWER { // from class: com.lux.luxcloud.global.bean.param.PARAM.162
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_gridPeakShavingPowerInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_power;
        }

        public int getStartRegister() {
            return 206;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.163
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_start_peakShaving_SOC_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_1;
        }

        public int getStartRegister() {
            return 207;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.164
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_start_peakShaving_Volt_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_1;
        }

        public int getStartRegister() {
            return 208;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_SMART_LOAD_START_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.165
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_smart_load_start_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_start_volt;
        }

        public int getStartRegister() {
            return 213;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_SMART_LOAD_END_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.166
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_smart_load_end_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_end_volt;
        }

        public int getStartRegister() {
            return 214;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_SMART_LOAD_START_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.167
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_smart_load_start_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_start_soc;
        }

        public int getStartRegister() {
            return 215;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_SMART_LOAD_END_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.168
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_smart_load_end_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_end_soc;
        }

        public int getStartRegister() {
            return 216;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_START_PV_POWER { // from class: com.lux.luxcloud.global.bean.param.PARAM.169
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_startPvPowerInput_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_start_pv_power;
        }

        public int getStartRegister() {
            return 217;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_GRID_PEAK_SHAVING_SOC_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.170
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Peak_Shaving_Soc2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_2;
        }

        public int getStartRegister() {
            return 218;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.171
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Peak_Shaving_Volt2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_2;
        }

        public int getStartRegister() {
            return 219;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
            }
            return true;
        }
    },
    _12K_HOLD_AC_COUPLE_START_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.172
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_couple_start_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_start_soc;
        }

        public int getStartRegister() {
            return 220;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_AC_COUPLE_END_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.173
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_couple_end_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_end_soc;
        }

        public int getStartRegister() {
            return 221;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_AC_COUPLE_START_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.174
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_couple_start_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_start_volt;
        }

        public int getStartRegister() {
            return 222;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    _12K_HOLD_AC_COUPLE_END_VOLT { // from class: com.lux.luxcloud.global.bean.param.PARAM.175
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_couple_end_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_end_volt;
        }

        public int getStartRegister() {
            return 223;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_LCD_VERSION { // from class: com.lux.luxcloud.global.bean.param.PARAM.176
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.firmware_lcd_version;
        }

        public int getStartRegister() {
            return BERTags.FLAGS;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    HOLD_CUSTOM_PASSWORD { // from class: com.lux.luxcloud.global.bean.param.PARAM.177
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.lcd_password;
        }

        public int getStartRegister() {
            return 225;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    BASIC_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.178
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.basic_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return GENERAL.checkVisible(userData) || EPS_OUTPUT.checkVisible(userData) || BASIC_PROTECTION_SETTING.checkVisible(userData) || GRID_SELL.checkVisible(userData);
        }
    },
    ADVANCED_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.179
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_advanced;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return PARALLEL.checkVisible(userData) || ADVANCED_PROTECTION_SETTING.checkVisible(userData) || CT_METER_SETTING.checkVisible(userData) || PV_SETTING.checkVisible(userData);
        }
    },
    RESET { // from class: com.lux.luxcloud.global.bean.param.PARAM.180
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_reset_set;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    APPLICATION_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.181
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_application_set;
        }
    },
    GENERAL { // from class: com.lux.luxcloud.global.bean.param.PARAM.182
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.general;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    EPS_OUTPUT { // from class: com.lux.luxcloud.global.bean.param.PARAM.183
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.eps_output;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    BASIC_PROTECTION_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.184
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.protection_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_DCI_EN.checkVisible(userData) || FUNC_ANTI_ISLAND_EN.checkVisible(userData) || FUNC_NEUTRAL_DETECT_EN.checkVisible(userData) || FUNC_N_PE_CONNECT_INNER_EN.checkVisible(userData) || FUNC_GFCI_EN.checkVisible(userData);
        }
    },
    GRID_SELL { // from class: com.lux.luxcloud.global.bean.param.PARAM.185
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.grid_sell;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isGen3_6K() || currentInverter.is7_10KDevice() || currentInverter.isType6Series() || currentInverter.isHybird() || currentInverter.isAcCharger() || currentInverter.isMidBox() || currentInverter.isTrip12K() || ROLE.VIEWER.equals(userData.getRole());
        }
    },
    PARALLEL { // from class: com.lux.luxcloud.global.bean.param.PARAM.186
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_parallel;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_MIDBOX_EN.checkVisible(userData) || HOLD_SET_MASTER_OR_SLAVE.checkVisible(userData) || HOLD_SET_COMPOSED_PHASE.checkVisible(userData) || FUNC_BAT_SHARED.checkVisible(userData) || FUNC_PARALLEL_DATA_SYNC_EN.checkVisible(userData);
        }
    },
    ADVANCED_PROTECTION_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.187
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.protection_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getCurrentInverter();
            return FUNC_RSD_DISABLE.checkVisible(userData) || FUNC_PV_ARC.checkVisible(userData) || FUNC_PV_ARC_FAULT_CLEAR.checkVisible(userData) || FUNC_ISO_EN.checkVisible(userData);
        }
    },
    CT_METER_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.188
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ct_meter_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (MODEL_BIT_MEASUREMENT.checkVisible(userData) || FUNC_TOTAL_LOAD_COMPENSATION_EN.checkVisible(userData) || FUNC_CT_DIRECTION_REVERSED.checkVisible(userData) || HOLD_CT_POWER_OFFSET.checkVisible(userData) || BIT_CT_SAMPLE_RATIO.checkVisible(userData) || BIT_PVCT_SAMPLE_TYPE.checkVisible(userData) || BIT_PVCT_SAMPLE_RATIO.checkVisible(userData) || HOLD_P_TO_USER_START_DISCHG.checkVisible(userData)) && !(userData.getCurrentInverter().is7_10KDevice() && ROLE.VIEWER.equals(userData.getRole()));
        }
    },
    PV_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.189
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.pv_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    ALL_TO_DEFAULT { // from class: com.lux.luxcloud.global.bean.param.PARAM.190
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_all_2_default;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return true;
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
    RESET_TO_FACTORY { // from class: com.lux.luxcloud.global.bean.param.PARAM.191
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_set_label_reset_2_factory;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    BATTERY_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.192
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_setting;
        }
    },
    FUNC_GO_TO_OFFGRID_MODE { // from class: com.lux.luxcloud.global.bean.param.PARAM.193
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_func_param_green_en_12k : R.string.phrase_func_param_go_to_offgrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (!ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM)) && currentInverter.getSlaveVersionValue() >= 38 && currentInverter.getFwVersionValue() >= 38;
        }
    },
    HOLD_OFF_GRID_START_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.194
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_start_hour_1_eg4 : R.string.phrase_param_go_to_off_grid_start_hour_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.195
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_start_minute_1_eg4 : R.string.phrase_param_go_to_off_grid_start_minute_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_HOUR_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.196
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_end_hour_1_eg4 : R.string.phrase_param_go_to_off_grid_end_hour_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_MINUTE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.197
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_end_minute_1_eg4 : R.string.phrase_param_go_to_off_grid_end_minute_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.198
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_start_hour_2_eg4 : R.string.phrase_param_go_to_off_grid_start_hour_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.199
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_start_minute_2_eg4 : R.string.phrase_param_go_to_off_grid_start_minute_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_HOUR_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.200
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_end_hour_2_eg4 : R.string.phrase_param_go_to_off_grid_end_hour_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_MINUTE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.201
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_end_minute_2_eg4 : R.string.phrase_param_go_to_off_grid_end_minute_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_HOUR_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.202
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_start_hour_3_eg4 : R.string.phrase_param_go_to_off_grid_start_hour_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_START_MINUTE_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.203
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_start_minute_3_eg4 : R.string.phrase_param_go_to_off_grid_start_minute_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_HOUR_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.204
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_end_hour_3_eg4 : R.string.phrase_param_go_to_off_grid_end_hour_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    HOLD_OFF_GRID_END_MINUTE_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.205
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM) ? R.string.phrase_param_go_to_off_grid_end_minute_3_eg4 : R.string.phrase_param_go_to_off_grid_end_minute_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return FUNC_GO_TO_OFFGRID_MODE.checkVisible(userData);
        }
    },
    WEEKLY_SET_MODE { // from class: com.lux.luxcloud.global.bean.param.PARAM.206
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_weekly_set_mode;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (!currentInverter.isType6Series()) {
                return true;
            }
            if ((ROLE.MAINTAIN.equals(role) || ROLE.ADMIN.equals(role)) && PLATFORM.LUX_POWER.equals(Custom.APP_USER_PLATFORM)) {
                return currentInverter.getSlaveVersionValue() >= 33 && currentInverter.getFwVersionValue() >= 34;
            }
            return true;
        }
    },
    GENERATOR_PORT_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.207
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.generator_port_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    GENERATOR_FUNCTION { // from class: com.lux.luxcloud.global.bean.param.PARAM.208
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.generator_function;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    AC_COUPLING_FUNCTION { // from class: com.lux.luxcloud.global.bean.param.PARAM.209
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_coupling_function;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    SMART_LOAD_FUNCTION { // from class: com.lux.luxcloud.global.bean.param.PARAM.210
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_function;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    WORKING_MODE_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.211
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.working_mode_setting;
        }
    },
    FUNC_EPS_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.212
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_eps_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_eps_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.is12KUsVersion() || currentInverter.isMidBox();
        }
    },
    FUNC_OVF_LOAD_DERATE_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.213
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_OVF_Load_Derate_Enable_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_ovf_load_derate_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_DRMS_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.214
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_drms_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            return ((!currentInverter.isAcCharger() && !currentInverter.isHybird()) || ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck()) ? (!currentInverter.isType6Series() || currentInverter.isAllInOne() || ROLE.VIEWER.equals(role) || role.getInstallerLevelCheck() || userData.getClusterId() >= 4) ? false : true : userData.getClusterId() < 4;
        }
    },
    FUNC_ANTI_ISLAND_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.215
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.anti_island_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.page_setting_tip_anti_island;
        }
    },
    FUNC_NEUTRAL_DETECT_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.216
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_neutral_detect_enable_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.neutral_detect_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_GRID_ON_POWER_SS_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.217
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Grid_Soft_Start_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_grid_on_power_ss_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_AC_CHARGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.218
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_AC_Charge_Enable_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_ac_charge_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAllInOne()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return currentInverter.isType6Series() || currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isMidBox();
        }
    },
    FUNC_SW_SEAMLESSLY_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.219
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_sw_seamlessly_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isType6Series()) {
                return R.string.page_setting_tip_2023_seamless_eps_switching_12k;
            }
            return -1;
        }
    },
    FUNC_SET_TO_STANDBY { // from class: com.lux.luxcloud.global.bean.param.PARAM.220
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_set_to_standby;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            userData.getPlatform();
            return R.string.page_setting_tip_2023_Normal_Standby_12k;
        }
    },
    FUNC_FORCED_DISCHG_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.221
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Forced_Discharge_Enable_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_forced_dischg_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    FUNC_FORCED_CHG_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.222
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_forced_chg_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_forced_chg_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    FUNC_ISO_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.223
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_ios_enable_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.iso_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_GFCI_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.224
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.gfci_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isType6Series()) {
                return R.string.page_setting_tip_gfci;
            }
            return -1;
        }
    },
    FUNC_DCI_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.225
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.dci_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger()) {
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.page_setting_tip_dci;
        }
    },
    FUNC_FEED_IN_GRID_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.226
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_grid_sell_back_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_feed_in_grid_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger()) {
                return currentInverter.isAllowExport2Grid() && userData.isGigabiz1User();
            }
            if (currentInverter.isSnaSeries()) {
                return currentInverter.isAllowExport2Grid();
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return true;
            }
            if (currentInverter.is12KUsVersion()) {
                return currentInverter.isAllowExport2Grid();
            }
            return currentInverter.isMidBox();
        }
    },
    FUNC_LSP_SET_TO_STANDBY { // from class: com.lux.luxcloud.global.bean.param.PARAM.227
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_set_to_standby;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isLsp()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PV_GRID_OFF_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.228
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_pv_grid_off_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_pv_grid_off_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_RUN_WITHOUT_GRID { // from class: com.lux.luxcloud.global.bean.param.PARAM.229
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Fast_Zero_Export_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fast_zero_export;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isType6() && !currentInverter.isAllInOne()) {
                return (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) ? false : true;
            }
            if (currentInverter.isGen3_6K() || currentInverter.is7_10KDevice() || currentInverter.isTrip12K()) {
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
    FUNC_MICRO_GRID_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.230
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Micro_Grid_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_micro_grid_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird() || currentInverter.isAcCharger() || currentInverter.isTrip12K()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    FUNC_BAT_SHARED { // from class: com.lux.luxcloud.global.bean.param.PARAM.231
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_battery_shared_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.EG4.equals(userData.getPlatform())) {
                return currentInverter.isType6Series() && currentInverter.getSlaveVersion().intValue() >= 29 && currentInverter.getFwVersion().intValue() >= 29;
            }
            if (currentInverter.isAcCharger() || currentInverter.isOffGrid()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if ((currentInverter.isType6() && !currentInverter.isAllInOne()) || currentInverter.isSna12K() || currentInverter.isGen3_6K() || currentInverter.is7_10KDevice() || currentInverter.isTrip12K() || currentInverter.isHybird()) {
                return true;
            }
            if (currentInverter.isMidBox()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return -1;
            }
            return R.string.page_setting_tip_2023_Share_Battery_12k;
        }
    },
    FUNC_CHARGE_LAST { // from class: com.lux.luxcloud.global.bean.param.PARAM.232
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Charge_Last_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_charge_last;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isAcCharger() || currentInverter.isHybird()) ? !ROLE.VIEWER.equals(userData.getRole()) || userData.isGigabiz1User() : (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isGen3_6K() || currentInverter.is7_10KDevice() || currentInverter.isMidBox();
        }
    },
    FUNC_BUZZER_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.233
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_buzzer_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_TAKE_LOAD_TOGETHER { // from class: com.lux.luxcloud.global.bean.param.PARAM.234
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_take_load_together;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6Series()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return currentInverter.isSnaSeries();
        }
    },
    FUNC_GREEN_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.235
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return !inverter.isMidBox() ? ((!inverter.isType6() || inverter.isAllInOne()) && inverter.is12KEUVersion() && inverter.is12KUsVersion()) ? R.string.phrase_func_param_green_en : R.string.phrase_func_param_go_to_off_grid : R.string.phrase_func_param_go_to_off_grid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            ROLE role = userData.getRole();
            Inverter currentInverter = userData.getCurrentInverter();
            if ((!ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(userData.getPlatform()) || PLATFORM.EG4.equals(Custom.APP_USER_PLATFORM)) && currentInverter.getSlaveVersionValue() >= 38 && currentInverter.getFwVersionValue() >= 38) {
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries() ? R.string.page_setting_tip_green_funtion_enable : R.string.phrase_func_param_green_en_12k_tip;
        }
    },
    FUNC_CT_DIRECTION_REVERSED { // from class: com.lux.luxcloud.global.bean.param.PARAM.236
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_ct_direction_reversed;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            userData.getCurrentInverter();
            return R.string.page_setting_tip_2023_ct_direction_reversed_12k;
        }
    },
    FUNC_TOTAL_LOAD_COMPENSATION_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.237
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_total_load_compensation_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PV_ARC_FAULT_CLEAR { // from class: com.lux.luxcloud.global.bean.param.PARAM.238
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_PV_Arc_Fault_Clear_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.pv_arc_fault_clear;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6() || currentInverter.is7_10KDevice() || currentInverter.isAcCharger()) {
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
    FUNC_PV_SELL_TO_GRID_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.239
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_PVSellToGrid_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.export_pv_only;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    MODEL_BIT_MEASUREMENT { // from class: com.lux.luxcloud.global.bean.param.PARAM.240
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_measurement_model_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.measurement;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_GRID_PEAK_SHAVING { // from class: com.lux.luxcloud.global.bean.param.PARAM.241
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Grid_Peak_Shaving_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_grid_peak_shaving;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    BIT_CT_SAMPLE_RATIO { // from class: com.lux.luxcloud.global.bean.param.PARAM.242
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_bit_param_pvct_sample_ratio;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            userData.getCurrentInverter();
            return R.string.page_setting_tip_2023_ct_sample_ratio_12k;
        }
    },
    MODEL_BIT_METER_BRAND { // from class: com.lux.luxcloud.global.bean.param.PARAM.243
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.meter;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            if (currentInverter.isType6Series() && currentInverter.getSlaveVersionValue() >= 33 && currentInverter.getFwVersionValue() >= 34) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isType6()) {
                    return createDropdown(new String[]{"0: Eastron", "1: WattNode", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
                }
                if (currentInverter.isGen3_6K()) {
                    return createDropdown(new String[]{"0: Eastron", "1: Rsvd"}, new String[]{"0", "1"});
                }
                if (currentInverter.isHybird()) {
                    return createDropdown(new String[]{"0: Eastron"}, new String[]{"0"});
                }
                if (currentInverter.isAcCharger()) {
                    return createDropdown(new String[]{"0: Eastron", "1: Rsvd", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
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
            if (currentInverter.isHybird()) {
                return createDropdown(new String[]{"0: Eastron"}, new String[]{"0"});
            }
            if (currentInverter.isAcCharger()) {
                return createDropdown(new String[]{"0: Eastron", "1: Rsvd", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
            }
            return createDropdown(new String[]{"0: Eastron", "1: WattNode", "2: Rsvd", "3: Chint"}, new String[]{"0", "1", "2", "3"});
        }
    },
    METER_BRAND_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.244
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.meter_brand_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (!PLATFORM.EG4.name().equals(Custom.APP_USER_PLATFORM)) {
                return false;
            }
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.getFwVersion().intValue() < 30) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    FUNC_GEN_PEAK_SHAVING { // from class: com.lux.luxcloud.global.bean.param.PARAM.245
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_peak_shaving_power;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_BAT_CHARGE_CONTROL { // from class: com.lux.luxcloud.global.bean.param.PARAM.246
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_bat_charge_control;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
            if (currentInverter.isType6Series()) {
                return true;
            }
            return !currentInverter.isAcCharger() && currentInverter.isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return -1;
            }
            return R.string.page_setting_tip_2023_Batt_Charge_Control_12k;
        }
    },
    FUNC_BAT_DISCHARGE_CONTROL { // from class: com.lux.luxcloud.global.bean.param.PARAM.247
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_bat_discharge_control;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return -1;
            }
            return R.string.page_setting_tip_2023_Batt_Discharge_Control_12k;
        }
    },
    FUNC_AC_COUPLING_FUNCTION { // from class: com.lux.luxcloud.global.bean.param.PARAM.248
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_AC_Couple_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_coupling_function;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_PV_ARC { // from class: com.lux.luxcloud.global.bean.param.PARAM.249
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_PV_Arc_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_pv_arc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isType6() || currentInverter.is7_10KDevice() || currentInverter.isAcCharger()) {
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
    FUNC_SMART_LOAD_ENABLE { // from class: com.lux.luxcloud.global.bean.param.PARAM.250
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Smart_Load_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_RSD_DISABLE { // from class: com.lux.luxcloud.global.bean.param.PARAM.251
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_RSD_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.rsd;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            PLATFORM platform = userData.getPlatform();
            if (!((currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox() || currentInverter.isAcCharger()) || currentInverter.isTrip12K() || currentInverter.isGen3_6K()) {
                return false;
            }
            return PLATFORM.EG4.equals(platform) || !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    FUNC_ON_GRID_ALWAYS_ON { // from class: com.lux.luxcloud.global.bean.param.PARAM.252
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_On_Grid_Always_On_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_on_grid_always_on;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_RUN_WITHOUT_GRID_12K { // from class: com.lux.luxcloud.global.bean.param.PARAM.253
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Run_Without_Grid_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isMidBox() || inverter.is7_10KDevice() || inverter.isType6Series() || inverter.isTrip12K()) ? R.string.phrase_func_param_grid_loss_warning_clear : R.string.phrase_func_param_run_without_grid_en_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.is12KUsVersion() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }
    },
    FUNC_N_PE_CONNECT_INNER_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.254
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_N_PEConnect_offGrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_n_pe_connect_inner_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isType6Series()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_RETAIN_SHUTDOWN { // from class: com.lux.luxcloud.global.bean.param.PARAM.255
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_retain_shutdown;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_RETAIN_STANDBY { // from class: com.lux.luxcloud.global.bean.param.PARAM.256
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_retain_standby;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_OFFLINE_TIMEOUT { // from class: com.lux.luxcloud.global.bean.param.PARAM.257
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_offline_timeout;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird() || currentInverter.isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_PARALLEL_DATA_SYNC_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.258
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Parallel_Setting_Data_Sync_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.parallel_setting_data_sync;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    BIT_AC_CHARGE_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.259
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_charge;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_charge_based_on;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isHybird() || currentInverter.isAcCharger()) ? false : true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isSnaSeries()) {
                    return createDropdown(new String[]{"<Empty>", "Disable", "Time (According to)", "Battery Voltage (According to)", "Battery SOC (According to)", "Battery Voltage and Time (According to)", "Battery SOC and Time (According to)"}, new String[]{"-1", "0", "1", "2", "3", "4", "5"});
                }
                if (currentInverter.isType6Series()) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    arrayList.add("<Empty>");
                    arrayList2.add("-1");
                    arrayList.add("Time (According to)");
                    arrayList2.add("0");
                    arrayList.add("SOC/Volt (According to)");
                    arrayList2.add("1");
                    if (currentInverter.getPhase().intValue() == 1) {
                        arrayList.add("Time+SOC/Volt (According to)");
                        arrayList2.add("2");
                    }
                    return createDropdown((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
                }
                if (!currentInverter.isAllInOne() && !currentInverter.isMidBox() && !currentInverter.isHybird()) {
                    return null;
                }
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                arrayList3.add("<Empty>");
                arrayList4.add("-1");
                arrayList3.add("Time (According to)");
                arrayList4.add("0");
                arrayList3.add("SOC/Volt (According to)");
                arrayList4.add("1");
                return createDropdown((String[]) arrayList3.toArray(new String[0]), (String[]) arrayList4.toArray(new String[0]));
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isSnaSeries()) {
                return createDropdown(new String[]{"<Empty>", "Disable", "Time (According to)", "Battery Voltage (According to)", "Battery SOC (According to)", "Battery Voltage and Time (According to)", "Battery SOC and Time (According to)"}, new String[]{"-1", "0", "1", "2", "3", "4", "5"});
            }
            if (currentInverter.isType6Series()) {
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                arrayList5.add("<Empty>");
                arrayList6.add("-1");
                arrayList5.add("Time (According to)");
                arrayList6.add("0");
                arrayList5.add("SOC/Volt (According to)");
                arrayList6.add("1");
                if (currentInverter.getPhase().intValue() == 1) {
                    arrayList5.add("Time+SOC/Volt (According to)");
                    arrayList6.add("2");
                }
                return createDropdown((String[]) arrayList5.toArray(new String[0]), (String[]) arrayList6.toArray(new String[0]));
            }
            if (!currentInverter.isAllInOne() && !currentInverter.isMidBox() && !currentInverter.isHybird()) {
                return null;
            }
            ArrayList arrayList7 = new ArrayList();
            ArrayList arrayList8 = new ArrayList();
            arrayList7.add("<Empty>");
            arrayList8.add("-1");
            arrayList7.add("Time (According to)");
            arrayList8.add("0");
            arrayList7.add("SOC/Volt (According to)");
            arrayList8.add("1");
            return createDropdown((String[]) arrayList7.toArray(new String[0]), (String[]) arrayList8.toArray(new String[0]));
        }
    },
    FUNC_BATTERY_BACKUP_CTRL { // from class: com.lux.luxcloud.global.bean.param.PARAM.260
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_battery_backup_ctrl;
        }
    },
    MODEL_BIT_BATTERY_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.261
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return Custom.APP_USER_PLATFORM.equals(PLATFORM.EG4) ? R.string.battery_type_eg4 : R.string.battery_type;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!ROLE.VIEWER.equals(userData.getRole()) || PLATFORM.EG4.equals(userData.getPlatform())) {
                return true;
            }
            return currentInverter.getSlaveVersionValue() >= 33 && currentInverter.getFwVersionValue() >= 34 && !userData.isGigabiz1User();
        }
    },
    MODEL_BIT_BATTERY_TYPE_TEXT { // from class: com.lux.luxcloud.global.bean.param.PARAM.262
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return Custom.APP_USER_PLATFORM.equals(PLATFORM.EG4) ? R.string.battery_type_eg4 : R.string.battery_type;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (userData.isGigabiz1User() && ROLE.VIEWER.equals(userData.getRole())) {
                return false;
            }
            if ((currentInverter.getSlaveVersionValue() < 33 || currentInverter.getFwVersionValue() < 34) && !PLATFORM.EG4.equals(userData.getPlatform())) {
                return false;
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MODEL_BIT_LEAD_ACID_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.263
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.lead_acid_capacity_ah;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            if (currentInverter.isSnaSeries()) {
                return PLATFORM.EG4.equals(userData.getPlatform()) || !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    BACKUP_MODE_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.264
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.backup_mode_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    PEAK_SHAVING_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.265
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.peak_shaving_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    AC_CHARGE_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.266
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_charge_setting;
        }
    },
    AC_FIRST_MODE { // from class: com.lux.luxcloud.global.bean.param.PARAM.267
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_first_mode;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    PV_CHARGE_PRIORITY { // from class: com.lux.luxcloud.global.bean.param.PARAM.268
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.pv_charge_priority;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    FORCED_DISCHARGE_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.269
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.forced_discharge_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isAcCharger() || currentInverter.isHybird()) {
                return true;
            }
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    SELF_CONSUMPTION { // from class: com.lux.luxcloud.global.bean.param.PARAM.270
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_working_mode_0;
        }
    },
    INV_REBOOT { // from class: com.lux.luxcloud.global.bean.param.PARAM.271
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Restart_Inverter_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.restart_inverter;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    Battery_Charge_Discharge { // from class: com.lux.luxcloud.global.bean.param.PARAM.272
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.battery_charge_discharge;
        }
    },
    HOLD_SYSTEM_CHARGE_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.PARAM.273
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.system_charge_soc_limit;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (currentInverter.isAllInOne() || currentInverter.isAcCharger()) {
                return false;
            }
            if (currentInverter.isOffGrid()) {
                if (role.getInstallerLevelCheck()) {
                    return ("cbaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 36) || ("cBaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 128) || (("cBAA".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 128) || (currentInverter.getDtcValue().intValue() == 1 && "ccaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 18));
                }
                return false;
            }
            if (currentInverter.isSna12K()) {
                return (role.getInstallerLevelCheck() && "ceaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 6) || ("cfaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 6);
            }
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.page_setting_tip_system_charge_soc_limit;
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_POWER_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.274
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_Peak_Shaving_Power2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.peak_shaving_power_2_kw;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_AC_CHARGE_END_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.275
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkEnabled(boolean z) {
            return z;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_backup_volt;
        }

        public int getStartRegister() {
            return 159;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isType6() ? R.string.phrase_param_stop_ac_chg_volt : inverter.isSnaSeries() ? R.string.phrase_param_ac_charge_end_battery_voltage : inverter.isMidBox() ? R.string.backup_volt_v : R.string.phrase_param_stop_ac_chg_volt;
        }
    },
    HOLD_SYSTEM_CHARGE_VOLT_LIMIT { // from class: com.lux.luxcloud.global.bean.param.PARAM.276
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.system_charge_volt_limit_v;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            ROLE role = userData.getRole();
            if (currentInverter.isHybird()) {
                return role.getInstallerLevelCheck();
            }
            if (currentInverter.isSna6kUsAio() && currentInverter.getSubDeviceTypeValue() == 131) {
                return role.getInstallerLevelCheck();
            }
            if (currentInverter.isOffGrid()) {
                if (role.getInstallerLevelCheck()) {
                    if ("cbaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 36) {
                        return true;
                    }
                    if ("cBaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 128) {
                        return true;
                    }
                    if ("cBAA".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 128) {
                        return true;
                    }
                    if (currentInverter.getDtcValue().intValue() == 1 && "ccaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 18) {
                        return true;
                    }
                }
                return false;
            }
            if (currentInverter.isSna12K()) {
                if (role.getInstallerLevelCheck() && "ceaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 6) {
                    return true;
                }
                return "cfaa".equals(currentInverter.getStandard()) && currentInverter.getSlaveVersionValue() >= 6;
            }
            if (currentInverter.isType6Series() && !currentInverter.isAllInOne()) {
                return role.getInstallerLevelCheck();
            }
            if (currentInverter.isMidBox()) {
                return role.getInstallerLevelCheck();
            }
            return false;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isHybird()) {
                return -1;
            }
            return R.string.page_setting_tip_system_charge_volt_limit;
        }
    },
    HOLD_AC_CHARGE_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.PARAM.277
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_backup_soc;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.backup_soc : (inverter.isHybird() || inverter.isAcCharger()) ? R.string.phrase_param_ac_charge_soc_limit_12k : R.string.phrase_param_ac_charge_soc_limit;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird()) {
                String standard = currentInverter.getStandard();
                int slaveVersionValue = currentInverter.getSlaveVersionValue();
                int fwVersionValue = currentInverter.getFwVersionValue();
                if ((("AAAA".equals(standard) || "aAAA".equals(standard)) && slaveVersionValue >= 36 && fwVersionValue >= 32) || (("AAAB".equals(standard) || "aAAB".equals(standard)) && slaveVersionValue >= 29 && fwVersionValue >= 26)) {
                    return true;
                }
            }
            return currentInverter.isType6Series() || currentInverter.isAcCharger() || currentInverter.isMidBox();
        }
    },
    _12K_HOLD_GEN_COOL_DOWN_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.278
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bat_charge_current_limit;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (!currentInverter.isType6Series() || currentInverter.isAllInOne()) {
                return currentInverter.is12KUsVersion();
            }
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    HOLD_COM_ADDR { // from class: com.lux.luxcloud.global.bean.param.PARAM.279
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_com_addr;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    BIT_FAN_1_MAX_SPEED { // from class: com.lux.luxcloud.global.bean.param.PARAM.280
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_fan1MaxSpeedInput_offGrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_1_max_speed;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    BIT_FAN_2_MAX_SPEED { // from class: com.lux.luxcloud.global.bean.param.PARAM.281
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_fan2MaxSpeedInput_offGrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_2_max_speed;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    BIT_FAN_3_MAX_SPEED { // from class: com.lux.luxcloud.global.bean.param.PARAM.282
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_fan3MaxSpeedInput_offGrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_3_max_speed;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole());
            }
            return false;
        }
    },
    BIT_FAN_4_MAX_SPEED { // from class: com.lux.luxcloud.global.bean.param.PARAM.283
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_fan4MaxSpeedInput_offGrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_4_max_speed;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole());
            }
            return false;
        }
    },
    BIT_FAN_5_MAX_SPEED { // from class: com.lux.luxcloud.global.bean.param.PARAM.284
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_2023_fan5MaxSpeedInput_offGrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_5_max_speed;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FAN_SPEED_SLOPE_CTRL_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.285
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_speed_slope_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FAN_SPEED_SLOPE_CTRL_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.286
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_speed_slope_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FAN_SPEED_SLOPE_CTRL_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.287
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_speed_slope_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FAN_SPEED_SLOPE_CTRL_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.288
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_speed_slope_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_FAN_SPEED_SLOPE_CTRL_5 { // from class: com.lux.luxcloud.global.bean.param.PARAM.289
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.fan_speed_slope_5;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return ROLE.ADMIN.equals(userData.getRole()) || ROLE.MAINTAIN.equals(userData.getRole()) || ROLE.OWNER.equals(userData.getRole());
            }
            return false;
        }
    },
    FAN_SPEED_CONTROL { // from class: com.lux.luxcloud.global.bean.param.PARAM.290
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_fan_speed_control;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isSnaSeries()) {
                return !ROLE.VIEWER.equals(userData.getRole());
            }
            return false;
        }
    },
    FUNC_ENERTEK_WORKING_MODE { // from class: com.lux.luxcloud.global.bean.param.PARAM.291
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return -1;
        }
    },
    MIDBOX_PORT_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.292
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_PORT_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.293
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_PORT_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.294
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_PORT_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.295
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_midbox_smart_port_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.296
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_1_mode;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.297
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_2_mode;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.298
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_3_mode;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MIDBOX_SP_MODE_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.299
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_port_4_mode;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.300
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_func_smart_load_enable_1 : R.string.phrase_param_smart_load;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.301
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_enable_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.302
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_enable_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_EN_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.303
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_enable_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.304
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.305
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.306
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_START_PV_P_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.307
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_start_pv_p_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.308
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.309
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.310
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_SOC_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.311
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_soc_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.312
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.313
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.314
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_SOC_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.315
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_soc_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.316
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.317
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.318
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_START_VOLT_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.319
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_start_volt_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.320
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.321
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.322
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_SL_PS_END_VOLT_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.323
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_hold_sl_ps_end_volt_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.324
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.325
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.326
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SMART_LOAD_GRID_ON_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.327
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_grid_on_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.328
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.329
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.330
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_AC_COUPLE_EN_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.331
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_ac_couple_enable_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.332
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.333
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.334
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_SHEDDING_MODE_EN_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.335
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_shedding_mode_enable_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_1 { // from class: com.lux.luxcloud.global.bean.param.PARAM.336
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_2 { // from class: com.lux.luxcloud.global.bean.param.PARAM.337
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_3 { // from class: com.lux.luxcloud.global.bean.param.PARAM.338
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_3;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_SMART_LOAD_BASE_ON_4 { // from class: com.lux.luxcloud.global.bean.param.PARAM.339
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_smart_load_based_on_4;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    FUNC_MIDBOX_EN { // from class: com.lux.luxcloud.global.bean.param.PARAM.340
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_func_midbox_en;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return PLATFORM.EG4.equals(userData.getPlatform()) && ROLE.VIEWER.equals(userData.getRole()) && currentInverter.isType6Series() && currentInverter.getSlaveVersion().intValue() >= 29 && currentInverter.getFwVersion().intValue() >= 29;
        }
    },
    SYSTEM_GRID_CONNECT_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.341
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_connect;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole()) && userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_MID_INSTALL_POSITION { // from class: com.lux.luxcloud.global.bean.param.PARAM.342
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_mid_install_position;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return ROLE.ADMIN.equals(userData.getRole());
        }
    },
    MIDBOX_HOLD_BUSBAR_PCS_RATING { // from class: com.lux.luxcloud.global.bean.param.PARAM.343
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bus_bar_current;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MIDBOX_GENERATOR_CHARGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.344
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_midbox_generator_charge;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_MANUFACTURER_SETTING { // from class: com.lux.luxcloud.global.bean.param.PARAM.345
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.page_maintain_remote_title_midbox_manufacturer_setting;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    MIDBOX_HOLD_GEN_REMOTE_TURN_OFF_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.346
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_remote_turn_off_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_WARN_UP_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.347
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_warn_up_time;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_COOL_DOWN_TIME { // from class: com.lux.luxcloud.global.bean.param.PARAM.348
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_cool_down_time_midbox;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_REMOTE_CTRL { // from class: com.lux.luxcloud.global.bean.param.PARAM.349
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_remote_ctrl;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_HOLD_GEN_VOLT_SOC_ENABLE { // from class: com.lux.luxcloud.global.bean.param.PARAM.350
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_gen_volt_soc_enable;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    ENERGY_RECORD_CLEAR { // from class: com.lux.luxcloud.global.bean.param.PARAM.351
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_reset_param_energy_record_clear;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    ADJ_RATIO_CLEAR { // from class: com.lux.luxcloud.global.bean.param.PARAM.352
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_reset_param_adj_ratio_clear;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    FAULT_RECORD_CLEAR { // from class: com.lux.luxcloud.global.bean.param.PARAM.353
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_reset_param_fault_record_clear;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_SET_CLEAR_FUNCTION { // from class: com.lux.luxcloud.global.bean.param.PARAM.354
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_set_clear_function;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return ROLE.ADMIN.equals(userData.getRole());
            }
            return false;
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_SOC { // from class: com.lux.luxcloud.global.bean.param.PARAM.355
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_start_ac_charge_soc;
        }

        public int getStartRegister() {
            return 160;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox() || !inverter.isSnaSeries()) ? R.string.phrase_param_start_ac_charge_soc_12k : R.string.phrase_param_ac_charge_start_battery_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            if (currentInverter.isHybird()) {
                String standard = currentInverter.getStandard();
                int slaveVersionValue = currentInverter.getSlaveVersionValue();
                int fwVersionValue = currentInverter.getFwVersionValue();
                if ((("AAAA".equals(standard) || "aAAA".equals(standard)) && slaveVersionValue >= 36 && fwVersionValue >= 32) || (("AAAB".equals(standard) || "aAAB".equals(standard)) && slaveVersionValue >= 29 && fwVersionValue >= 26)) {
                    return true;
                }
            }
            if (PARAM.BIT_AC_CHARGE_TYPE.checkVisible(userData)) {
                return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
            }
            return false;
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.356
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_start_ac_charge_voltage;
        }

        public int getStartRegister() {
            return 158;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_start_ac_charge_volt_12k : (inverter.isSnaSeries() || inverter.isHybird() || inverter.isAcCharger()) ? R.string.phrase_param_ac_charge_start_battery_voltage : R.string.phrase_param_start_ac_charge_volt_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox();
        }
    },
    HOLD_BACK_UP_END_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.PARAM.357
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_stop_ac_chg_volt;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            userData.getCurrentInverter();
            return true;
        }
    },
    MIDBOX_MASTER_SN { // from class: com.lux.luxcloud.global.bean.param.PARAM.358
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_master_inverter_sn;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_SERIAL_NUM_MASTER { // from class: com.lux.luxcloud.global.bean.param.PARAM.359
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_inverter_sn_master;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_TIME_MASTER { // from class: com.lux.luxcloud.global.bean.param.PARAM.360
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            return R.string.page_maintain_remote_set_tip_format;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_time_master;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    HOLD_FW_CODE_MASTER { // from class: com.lux.luxcloud.global.bean.param.PARAM.361
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.firmware_lcd_version_master;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    MIDBOX_DEVICE_INFORMATION { // from class: com.lux.luxcloud.global.bean.param.PARAM.362
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.device_host_information;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }
    },
    BIT_LCD_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.363
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phase_bit_param_lcd_type;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return PLATFORM.EG4.equals(userData.getPlatform()) && !ROLE.VIEWER.equals(userData.getRole());
        }
    },
    MODEL_BIT_RULE { // from class: com.lux.luxcloud.global.bean.param.PARAM.364
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_rule;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    FUNC_SNA_BAT_DISCHARGE_CONTROL { // from class: com.lux.luxcloud.global.bean.param.PARAM.365
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_bat_discharge_control;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getDescription(UserData userData) {
            if (userData.getCurrentInverter().isMidBox()) {
                return -1;
            }
            return R.string.page_setting_tip_2023_Batt_Discharge_Control_12k;
        }
    },
    FUNC_GO_TO_OFFGRID { // from class: com.lux.luxcloud.global.bean.param.PARAM.366
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_func_param_go_to_offgrid;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
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
    BIT_PVCT_SAMPLE_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.367
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_bit_param_pvct_sample_type;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getCurrentInverter().isAcCharger()) ? false : true;
        }
    },
    BIT_PVCT_SAMPLE_RATIO { // from class: com.lux.luxcloud.global.bean.param.PARAM.368
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_bit_param_pvct_sample_ratio;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return (ROLE.VIEWER.equals(userData.getRole()) || userData.getCurrentInverter().isAcCharger()) ? false : true;
        }
    },
    MODEL_BIT_METER_TYPE { // from class: com.lux.luxcloud.global.bean.param.PARAM.369
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_model_meterType;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isHybird() || currentInverter.isAcCharger() || currentInverter.isType6Series() || currentInverter.isGen3_6K();
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            userData.getCurrentInverter();
            return createDropdown((String[]) new ArrayList(Arrays.asList("0: 1 Phase Meter", "1: 3 Phase Meter")).toArray(new String[0]), (String[]) new ArrayList(Arrays.asList("0", "1")).toArray(new String[0]));
        }
    },
    _12K_HOLD_SOC_HYSTERESIS { // from class: com.lux.luxcloud.global.bean.param.PARAM.370
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_soc_hysteresis;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    },
    _12K_HOLD_VOLT_HYSTERESIS { // from class: com.lux.luxcloud.global.bean.param.PARAM.371
        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_volt_hysteresis;
        }

        @Override // com.lux.luxcloud.global.bean.param.PARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isSnaSeries();
        }
    };

    public boolean checkEnabled(boolean z) {
        return z;
    }

    public boolean checkVisible(UserData userData) {
        return false;
    }

    public int getDescription(UserData userData) {
        return -1;
    }

    public Object getPlatformSpecificData(UserData userData) throws JSONException {
        return null;
    }

    public abstract int getResourceId(Inverter inverter);

    public boolean checkInvisible(UserData userData) {
        if (userData.getRole() == null || userData.getCurrentInverter() == null) {
            return false;
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

    protected JSONObject createInputBox(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "input");
        jSONObject.put("placeholder", "");
        jSONObject.put("min", str2);
        jSONObject.put("max", str);
        return jSONObject;
    }
}