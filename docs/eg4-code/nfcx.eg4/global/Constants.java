package com.nfcx.eg4.global;

import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.bean.user.PLATFORM;
import com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.version.Custom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Constants {
    public static final String BLUETOOTH_TARGET_LOCAL_CONNECT = "LocalConnect";
    public static final String BLUETOOTH_TARGET_WIFI_CONFIG = "WifiConfig";
    public static final String CLUSTER_GROUP_FIRST = "firstCluster";
    public static final String CLUSTER_GROUP_SECOND = "secondCluster";
    public static String DEFAULT_DATALOG_SN = "";
    public static final int DEVICE_TYPE_12K_HYBRID = 6;
    public static final int DEVICE_TYPE_AC_CHARGER = 2;
    public static final int DEVICE_TYPE_ALL_IN_ONE = 7;
    public static final int DEVICE_TYPE_GEN_3_6K = 10;
    public static final int DEVICE_TYPE_GEN_7_10K = 8;
    public static final int DEVICE_TYPE_HV_HYBRID = 4;
    public static final int DEVICE_TYPE_HYBRID = 0;
    public static final int DEVICE_TYPE_LSP_100K = 5;
    public static final int DEVICE_TYPE_MID_BOX = 9;
    public static final int DEVICE_TYPE_OFF_GRID = 3;
    public static final int DEVICE_TYPE_PV_INVERTER = 1;
    public static final int DEVICE_TYPE_SNA_12K = 11;
    public static final int DONGLE_CHANGE_DHCP_IP_MODE = 18;
    public static final int DONGLE_CHANGE_STATIC_IP_MODE = 17;
    public static final int DONGLE_CONNECT_PARAM = 16;
    public static final int DONGLE_PARAM_INDEX_DONGLE_TYPE = 11;
    public static final int DONGLE_PARAM_INDEX_FIRMWARE_VERSION = 7;
    public static final int DONGLE_PARAM_INDEX_RESET_2_FACTORY = 3;
    public static final int DONGLE_PARAM_INDEX_SERVER_IP_AND_PORT = 6;
    public static final int DONGLE_PARAM_INDEX_SN = 1;
    public static final int DONGLE_PARAM_INDEX_SSID = 5;
    public static final int DONGLE_PARAM_INDEX_SSID_PASSWORD = 4;
    public static final int DONGLE_PARAM_INDEX_UPDATE_FIRMWARE = 9;
    public static final int DONGLE_QUERY_AP_PASSWORD = 14;
    public static final int DONGLE_QUERY_FIRST_USE = 19;
    public static final int DONGLE_QUERY_PASSWORD_STATUS = 15;
    public static final String DONGLE_SERVER_AFRICA = "47.91.87.102,4346";
    public static final String DONGLE_SERVER_ASIA = "120.79.53.27,4346";
    public static final String DONGLE_SERVER_EU2 = "eu2.luxpowertek.com,4346";
    public static final String DONGLE_SERVER_EUROPE = "8.208.83.249,4346";
    public static final String DONGLE_SERVER_INDIA = "ind.luxpowertek.com,4346";
    public static final String DONGLE_SERVER_LOCAL = "dongle.luxpowertek.com,4346";
    public static final String DONGLE_SERVER_LOCAL_SSL = "dongle_ssl.solarcloudsystem.com,4348";
    public static final String DONGLE_SERVER_NORTH_AMERICA = "us2.solarcloudsystem.com,4346";
    public static final String DONGLE_SERVER_NORTH_AMERICA_IP = "3.101.7.137,4346";
    public static final String DONGLE_SERVER_NORTH_AMERICA_SSL = "us2_ssl.solarcloudsystem.com,4346";
    public static final String DONGLE_SERVER_PHI = "phi.luxpowertek.com,4346";
    public static final String DONGLE_SERVER_SOUTHEAST_ASIA = "47.81.11.236,4346";
    public static final String DONGLE_SERVER_USA = "47.254.33.206,4346";
    public static final String DONGLE_SERVER_VN = "vn.luxpowertek.com,4346";
    public static final int DONGLE_SET_AP_PASSWORD_STATUS = 12;
    public static final int DONGLE_SOFT_RESET = 13;
    public static String KEY_BLE_DEVICE = null;
    public static final String LOCAL_CONNECT_TYPE = "localConnectType";
    public static final String LOCAL_CONNECT_TYPE_BLUETOOTH = "bluetooth";
    public static final String LOCAL_CONNECT_TYPE_TCP = "tcp";
    public static int MODEL_US_VERSION_US = 0;
    public static final String SELECTED_FIRMWARE_ID = "selectedFirmwareId";
    public static final String SELECTED_FIRMWARE_TYPE = "selectedFirmwareType";
    public static final int SUB_DEVICE_TYPE_12K_EU = 162;
    public static final int SUB_DEVICE_TYPE_12K_US = 164;
    public static final int SUB_DEVICE_TYPE_8_10K_EU = 161;
    public static final int SUB_DEVICE_TYPE_8_10K_US = 163;
    public static final int SUB_DEVICE_TYPE_OFF_GRID_US = 131;
    public static final int SUB_DEVICE_TYPE_SNA_12K_US = 1111;
    public static final String TARGET = "TARGET";
    public static final String TARGET_LOCAL_CONNECT = "LocalConnect";
    public static final String TARGET_UPDATE_FIRMWARE = "UpdateFirmware";
    public static final long USER_ID_gigabiz1 = 13;
    public static final List<Property> firmwareTypeProperties;
    public static final List<Property> firmwareTypePropertiesExceptE_WIFI;
    public static String useNewSettingPage;
    public static final Map<String, Integer> validServerHostIndexMap;
    public static final Map<String, Integer> validServerIndexMap;

    static {
        for (int i = 0; i < 10; i++) {
            DEFAULT_DATALOG_SN += ProTool.getStringFromHex(255L);
        }
        useNewSettingPage = "New_Setting_Page";
        KEY_BLE_DEVICE = "key_ble_device";
        validServerIndexMap = new HashMap();
        validServerHostIndexMap = new HashMap();
        firmwareTypeProperties = new ArrayList();
        firmwareTypePropertiesExceptE_WIFI = new ArrayList();
        for (FIRMWARE_DEVICE_TYPE firmware_device_type : FIRMWARE_DEVICE_TYPE.values()) {
            firmwareTypeProperties.add(new Property(firmware_device_type.name(), firmware_device_type.getText()));
        }
        for (FIRMWARE_DEVICE_TYPE firmware_device_type2 : FIRMWARE_DEVICE_TYPE.values()) {
            if (!FIRMWARE_DEVICE_TYPE.DONGLE_E_WIFI_DONGLE.equals(firmware_device_type2)) {
                firmwareTypePropertiesExceptE_WIFI.add(new Property(firmware_device_type2.name(), firmware_device_type2.getText()));
            }
        }
        MODEL_US_VERSION_US = 1;
    }

    public static void initValidServerIndexMap() {
        Map<String, Integer> map = validServerIndexMap;
        map.clear();
        String currentClusterGroup = GlobalInfo.getInstance().getCurrentClusterGroup();
        if (PLATFORM.LUX_POWER.equals(Custom.APP_PLATFORM) || PLATFORM.MID.equals(Custom.APP_PLATFORM)) {
            if (CLUSTER_GROUP_FIRST.equals(currentClusterGroup)) {
                map.put(DONGLE_SERVER_ASIA, 0);
                map.put(DONGLE_SERVER_EUROPE, 1);
                map.put(DONGLE_SERVER_AFRICA, 2);
                map.put(DONGLE_SERVER_USA, 3);
                map.put(DONGLE_SERVER_SOUTHEAST_ASIA, 4);
            }
            map.put(DONGLE_SERVER_LOCAL, Integer.valueOf(map.size()));
            map.put(DONGLE_SERVER_LOCAL_SSL, Integer.valueOf(map.size()));
        } else if (PLATFORM.EG4.equals(Custom.APP_PLATFORM)) {
            map.put(DONGLE_SERVER_NORTH_AMERICA, 0);
            map.put(DONGLE_SERVER_NORTH_AMERICA_IP, 0);
            map.put(DONGLE_SERVER_NORTH_AMERICA_SSL, 0);
        }
        validServerHostIndexMap.clear();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            validServerHostIndexMap.put(Tool.extractHost(entry.getKey()).toLowerCase(), entry.getValue());
        }
    }

    public static Integer getIndexByHost(String str) {
        String strExtractHost = Tool.extractHost(str);
        Map<String, Integer> map = validServerHostIndexMap;
        Integer num = map.get(strExtractHost.toLowerCase());
        if (num != null) {
            return num;
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(strExtractHost)) {
                return entry.getValue();
            }
        }
        return null;
    }
}