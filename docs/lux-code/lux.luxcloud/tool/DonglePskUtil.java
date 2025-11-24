package com.lux.luxcloud.tool;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.tls.PSK_TLS_CONSTANT;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class DonglePskUtil {
    public static final String TAG = "LuxPower:Dongle";

    public static byte[] calcPsk(String str) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(PSK_TLS_CONSTANT.SALT.getBytes(), PSK_TLS_CONSTANT.SN_HASH_ALGORITHM);
        Mac mac = Mac.getInstance(PSK_TLS_CONSTANT.SN_HASH_ALGORITHM);
        mac.init(secretKeySpec);
        return Arrays.copyOfRange(mac.doFinal(str.getBytes()), 0, 16);
    }

    public static String fetchDongleSnFromWifiSsid(Context context, int i) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, i);
            return Constants.DEFAULT_DATALOG_SN;
        }
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager == null || !wifiManager.isWifiEnabled()) {
            Log.i(TAG, "Wifi is not enable or wifi manager is null");
            return Constants.DEFAULT_DATALOG_SN;
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        Log.i(TAG, "localConnect wifi info: " + connectionInfo);
        if (connectionInfo != null) {
            String ssid = connectionInfo.getSSID();
            if (ssid != null && ssid.length() >= 12 && ssid.charAt(0) == '\"' && ssid.charAt(11) == '\"') {
                return ssid.substring(1, 11);
            }
            "<unknown ssid>".equals(ssid);
            return Constants.DEFAULT_DATALOG_SN;
        }
        return Constants.DEFAULT_DATALOG_SN;
    }

    public static void getFineLocationPermission(Activity activity, int i, int i2, String[] strArr, int[] iArr) {
        if (i2 == i) {
            if (iArr.length > 0 && iArr[0] == 0) {
                Log.i(TAG, activity.getLocalClassName() + ": requestCode:" + i2 + ", permissions: " + Arrays.toString(strArr) + ", grantResults: " + Arrays.toString(iArr));
            } else {
                Tool.alertNotInUiThread(activity, R.string.phrase_toast_denied_location_permission);
            }
        }
    }
}