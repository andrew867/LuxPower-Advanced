package com.nfcx.eg4.global.bean.param;

import com.nfcx.eg4.R;
import com.nfcx.eg4.global.UserData;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public enum BattChgPARAM {
    HOLD_AC_CHARGE_START_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.1
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox()) ? R.string.phrase_param_start_ac_charge_volt_12k : (inverter.isSnaSeries() || inverter.isHybird() || inverter.isAcCharger()) ? R.string.phrase_param_ac_charge_start_battery_voltage : R.string.phrase_param_start_ac_charge_volt_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_VOLTAGE_TEMP { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.2
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.charge_volt;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    OFF_GRID_HOLD_MAX_GEN_CHG_BAT_CURR { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.3
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_bat_charge_current_limit;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }
    },
    MIDBOX_HOLD_SL_START_SOC_1 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.4
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_1_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_1 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.5
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_1_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_1 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.6
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_1_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_1 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.7
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_1_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_SOC_2 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.8
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_2_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_2 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.9
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_2_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_2 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.10
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_2_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_2 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.11
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_2_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_SOC_3 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.12
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_3_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_3 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.13
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_3_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_3 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.14
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_3_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_3 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.15
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_3_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_SOC_4 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.16
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_4_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_SL_START_VOLT_4 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.17
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.smart_load_4_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_SOC_4 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.18
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_4_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    MIDBOX_HOLD_AC_START_VOLT_4 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.19
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.ac_couple_4_volt_v;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_SM4_GCM_SM3;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return userData.getCurrentInverter().isMidBox();
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_END_BATTERY_VOLTAGE { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.20
        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            if (inverter.isType6()) {
                return R.string.phrase_param_stop_ac_chg_volt;
            }
            if (inverter.isSnaSeries()) {
                return R.string.phrase_param_ac_charge_end_battery_voltage;
            }
            return -1;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_CHARGE_FIRST_VOLT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.21
        public int getStartRegister() {
            return 201;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            inverter.isMidBox();
            return R.string.phrase_param_charge_first_volt;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_START_VOLT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.22
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_start_volt;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_END_VOLT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.23
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_end_volt;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    HOLD_AC_CHARGE_START_BATTERY_SOC { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.24
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return (inverter.isType6() || inverter.isMidBox() || !inverter.isSnaSeries()) ? R.string.phrase_param_start_ac_charge_soc_12k : R.string.phrase_param_ac_charge_start_battery_soc;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_AC_CHARGE_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.25
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_ac_charge_soc_limit;
        }

        public int getStartRegister() {
            return 67;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_FORCED_CHG_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.26
        public int getStartRegister() {
            return 75;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return inverter.isMidBox() ? R.string.phrase_param_forced_chg_soc_limit_12k : R.string.phrase_param_forced_chg_soc_limit;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_START_SOC { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.27
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_start_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    OFF_GRID_HOLD_GEN_CHG_END_SOC { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.28
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_charge_end_soc;
        }

        public int getStartRegister() {
            return CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_SOC_LOW_LIMIT_EPS_DISCHG { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.29
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return (!inverter.isType6() && inverter.isSnaSeries()) ? R.string.fragment_remote_set_soc_low_limit_eps_dischg_button : R.string.phrase_param_soc_low_limit_eps_dischg;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_LEAD_ACID_DISCHARGE_CUT_OFF_VOLT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.30
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_lead_acid_discharge_cut_off_volt;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.31
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_VOLT_2 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.32
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_volt_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "VOLT".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.33
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_1;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    _12K_HOLD_GRID_PEAK_SHAVING_SOC_2 { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.34
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_grid_peak_shaving_soc_2;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    HOLD_FORCED_DISCHG_SOC_LIMIT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.35
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_forced_dischg_soc_limit_12k;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkEnabled(String str) {
            return "SOC".equals(str);
        }
    },
    _12K_HOLD_STOP_DISCHG_VOLT { // from class: com.nfcx.eg4.global.bean.param.BattChgPARAM.36
        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public boolean checkVisible(UserData userData) {
            return true;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
        public int getResourceId(Inverter inverter) {
            return R.string.phrase_param_stop_dischg_volt;
        }

        @Override // com.nfcx.eg4.global.bean.param.BattChgPARAM
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

    public abstract int getResourceId(Inverter inverter);

    public boolean checkInvisible(UserData userData) {
        if (userData.getRole() == null || userData.getCurrentInverter() == null) {
            return true;
        }
        return !checkVisible(userData);
    }
}