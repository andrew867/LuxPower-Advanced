package com.nfcx.eg4.view.local.fragment;

import android.content.Context;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.bean.property.Property;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public final class BatteryDict {
    private static final List<Property> BATTERY_TYPE_LIST = new ArrayList();
    private static final List<Property> LITHIUM_BRAND_LIST = new ArrayList();
    private static volatile boolean initialized = false;

    private BatteryDict() {
    }

    public static void initIfNeeded(Context context) {
        if (initialized) {
            return;
        }
        synchronized (BatteryDict.class) {
            if (initialized) {
                return;
            }
            List<Property> list = BATTERY_TYPE_LIST;
            list.clear();
            list.add(new Property("-1", context.getString(R.string.phrase_please_select)));
            list.add(new Property("0", context.getString(R.string.battery_type_no_battery)));
            list.add(new Property("1", context.getString(R.string.battery_type_lead_acid)));
            list.add(new Property("2", context.getString(R.string.battery_type_lithium)));
            List<Property> list2 = LITHIUM_BRAND_LIST;
            list2.clear();
            list2.add(new Property("-1", context.getString(R.string.phrase_please_select)));
            list2.add(new Property("0", "0: Standard/Zetara/Pytes/EG4/BSL/Stackrack/Ampace/BigBattery"));
            list2.add(new Property("1", "1: HINAESS"));
            list2.add(new Property("2", "2: Pylon/UZ Energy/Shoto/Hubble/FreedomWon/BlueNova/Greenrich/VOLTA"));
            list2.add(new Property("3", "3: Rsvd"));
            list2.add(new Property("4", "4: Rsvd"));
            list2.add(new Property("5", "5: GSL Energy"));
            list2.add(new Property("6", "6: Luxpower/Meritsun/LBSA/HANCHU"));
            list2.add(new Property("7", "7: Rsvd"));
            list2.add(new Property("8", "8: Rsvd"));
            list2.add(new Property("9", "9: Rsvd"));
            list2.add(new Property("10", "10: Rsvd"));
            list2.add(new Property("11", "11: Rsvd"));
            list2.add(new Property("12", "12: Rsvd"));
            list2.add(new Property("13", "13: Rsvd"));
            list2.add(new Property("14", "14: Green Battery"));
            list2.add(new Property("15", "15: Rsvd"));
            list2.add(new Property("16", "16: Rsvd"));
            list2.add(new Property("17", "17: Rsvd"));
            list2.add(new Property("18", "18: Fortress"));
            list2.add(new Property("19", "19: Sunwoda/Renogy"));
            list2.add(new Property("20", "20: Aobo"));
            list2.add(new Property("21", "21: Weco"));
            list2.add(new Property("22", "22: BRIGGS"));
            list2.add(new Property("28", "28"));
            initialized = true;
        }
    }

    public static List<Property> getBatteryTypeList() {
        return Collections.unmodifiableList(BATTERY_TYPE_LIST);
    }

    public static List<Property> getLithiumBrandList() {
        return Collections.unmodifiableList(LITHIUM_BRAND_LIST);
    }
}