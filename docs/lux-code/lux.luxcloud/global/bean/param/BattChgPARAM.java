package com.lux.luxcloud.global.bean.param;

import androidx.savedstate.serialization.ClassDiscriminatorModeKt;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.UserData;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.global.bean.user.PLATFORM;
import com.lux.luxcloud.global.bean.user.ROLE;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.tls.CipherSuite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum BattChgPARAM {
    _12K_HOLD_AC_COUPLE_START_SOC { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.1
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_couple_start_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_start_soc;
        }

        public int getStartRegister() {
            return 220;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
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

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    _12K_HOLD_AC_COUPLE_START_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.2
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_ac_couple_start_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_couple_start_volt;
        }

        public int getStartRegister() {
            return 222;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
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

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isTrip12K()) {
                    return createInputBox("800", "100");
                }
                return createInputBox("59.5", "40");
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isTrip12K()) {
                return createInputBox("800", "100");
            }
            return createInputBox("59.5", "40");
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_SMART_LOAD_START_SOC { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.3
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_smart_load_start_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_start_soc;
        }

        public int getStartRegister() {
            return 215;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
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

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    _12K_HOLD_SMART_LOAD_START_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.4
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_smart_load_start_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_smart_load_start_volt;
        }

        public int getStartRegister() {
            return 213;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
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

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isTrip12K()) {
                    return createInputBox("800", "100");
                }
                return createInputBox("59.5", "40");
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isTrip12K()) {
                return createInputBox("800", "100");
            }
            return createInputBox("59.5", "40");
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.5
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        public int getStartRegister() {
            return 158;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_start_ac_charge_volt_12k : (inverter.isSnaSeries() || inverter.isHybird() || inverter.isAcCharger()) ? R.string.phrase_param_ac_charge_start_battery_voltage : R.string.phrase_param_start_ac_charge_volt_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_VOLTAGE_TEMP { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.6
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.7
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bat_charge_current_limit;
        }

        public int getStartRegister() {
            return 198;
        }
    },
    MIDBOX_HOLD_SL_START_SOC_1 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.8
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_1_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_1 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.9
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_1_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_1 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.10
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_1_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_1 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.11
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_1_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_SOC_2 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.12
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_2_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_2 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.13
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_2_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_2 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.14
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_2_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_2 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.15
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_2_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_SOC_3 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.16
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_3_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_3 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.17
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_3_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_3 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.18
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_3_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_3 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.19
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_3_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_SOC_4 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.20
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_4_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_4 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.21
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_4_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_4 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.22
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_4_soc;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_4 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.23
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_4_volt_v;
        }

        public int getStartRegister() {
            return 198;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_END_BATTERY_VOLTAGE { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.24
        public int getStartRegister() {
            return 159;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            if (inverter.isType6()) {
                return R.string.phrase_param_stop_ac_chg_volt;
            }
            if (inverter.isSnaSeries()) {
                return R.string.phrase_param_ac_charge_end_battery_voltage;
            }
            return -1;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_CHARGE_FIRST_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.25
        public int getStartRegister() {
            return 201;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            inverter.isMidBox();
            return R.string.phrase_param_charge_first_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_START_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.26
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_charge_start_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_start_volt;
        }

        public int getStartRegister() {
            return 194;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            Inverter currentInverter = userData.getCurrentInverter();
            if (PLATFORM.LUX_POWER.equals(userData.getPlatform())) {
                if (currentInverter.isTrip12K()) {
                    return createInputBox("800", "100");
                }
                return createInputBox("59.5", "40");
            }
            if (!PLATFORM.EG4.equals(userData.getPlatform())) {
                return null;
            }
            if (currentInverter.isTrip12K()) {
                return createInputBox("800", "100");
            }
            return createInputBox("59.5", "40");
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_END_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.27
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.page_setting_tip_charge_end_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_end_volt;
        }

        public int getStartRegister() {
            return 195;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_SOC { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.28
        public int getStartRegister() {
            return 160;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox() || !inverter.isSnaSeries()) ? R.string.phrase_param_start_ac_charge_soc_12k : R.string.phrase_param_ac_charge_start_battery_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
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
                return (currentInverter.isType6Series() && !currentInverter.isAllInOne()) || currentInverter.isMidBox() || currentInverter.isSnaSeries();
            }
            return false;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_AC_CHARGE_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.29
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_soc;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            Inverter currentInverter = userData.getCurrentInverter();
            return currentInverter.isHybird() || currentInverter.isType6Series() || currentInverter.isMidBox();
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_FORCED_CHG_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.30
        public int getStartRegister() {
            return 75;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_forced_chg_soc_limit_12k : R.string.phrase_param_forced_chg_soc_limit;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_START_SOC { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.31
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.phrase_setting_tip_charge_start_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_start_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_END_SOC { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.32
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getDescription(UserData userData) {
            return R.string.phrase_setting_tip_charge_end_soc;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_end_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_SOC_LOW_LIMIT_EPS_DISCHG { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.33
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return (!inverter.isType6() && inverter.isSnaSeries()) ? R.string.fragment_remote_set_soc_cut_off_soc : R.string.phrase_param_soc_low_limit_eps_dischg;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.34
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_lead_acid_cut_off_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public Object getPlatformSpecificData(UserData userData) throws JSONException {
            if (userData.getCurrentInverter().isTrip12K()) {
                return createInputBox("700", "80");
            }
            return createInputBox("56", "40");
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.35
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT_2 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.36
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.37
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_1;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC_2 { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.38
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_2;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_FORCED_DISCHG_SOC_LIMIT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.39
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_dischg_soc_limit_12k;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    _12K_HOLD_STOP_DISCHG_VOLT { // from class: com.lux.luxcloud.global.bean.param.BattChgPARAM.40
        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_stop_dischg_volt;
        }

        @Override // com.lux.luxcloud.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    };

    public boolean checkEnabled(String str) {
        return false;
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

    protected JSONObject createInputBox(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ClassDiscriminatorModeKt.CLASS_DISCRIMINATOR_KEY, "input");
        jSONObject.put("placeholder", "");
        jSONObject.put("min", str2);
        jSONObject.put("max", str);
        return jSONObject;
    }
}