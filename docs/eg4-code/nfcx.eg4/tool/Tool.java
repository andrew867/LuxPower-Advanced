package com.nfcx.eg4.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.bean.inverter.BATTERY_TYPE;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.global.bean.plant.Plant;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class Tool {
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static synchronized String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    public static void alert(Activity activity, int i) {
        if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.phrase_message).setIcon(android.R.drawable.ic_dialog_info).setMessage(i).setNegativeButton(R.string.phrase_button_ok, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    public static void alert(Activity activity, String str, DialogInterface.OnDismissListener onDismissListener) {
        if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.phrase_message).setIcon(android.R.drawable.ic_dialog_info).setMessage(str).setNegativeButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnDismissListener(onDismissListener);
        alertDialogCreate.show();
    }

    public static void alert(Activity activity, int i, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(activity).setMessage(i).setPositiveButton(R.string.phrase_button_ok, onClickListener).create().show();
    }

    public static void alert(Activity activity, String str) {
        if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.phrase_message).setIcon(android.R.drawable.ic_dialog_info).setMessage(str).setNegativeButton(R.string.phrase_button_ok, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    public static void alertDialog(Activity activity, String str, String str2, boolean z, boolean z2, int i, String str3, int i2, String str4, final DialogInterface.OnClickListener onClickListener, final DialogInterface.OnClickListener onClickListener2) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_for_alert);
        dialog.setCancelable(true);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView textView2 = (TextView) dialog.findViewById(R.id.dialog_subtitle);
        EditText editText = (EditText) dialog.findViewById(R.id.dialog_input);
        TextView textView3 = (TextView) dialog.findViewById(R.id.dialog_left);
        TextView textView4 = (TextView) dialog.findViewById(R.id.dialog_mid);
        TextView textView5 = (TextView) dialog.findViewById(R.id.dialog_right);
        textView.setText(str);
        textView2.setText(str2);
        editText.setVisibility(8);
        if (z) {
            textView3.setVisibility(0);
            textView3.setTextColor(i);
            textView3.setText(str3);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Tool.lambda$alertDialog$1(onClickListener, dialog, view);
                }
            });
        } else {
            textView3.setVisibility(8);
        }
        textView4.setVisibility(8);
        if (z2) {
            textView5.setVisibility(0);
            textView5.setTextColor(i2);
            textView5.setText(str4);
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Tool.lambda$alertDialog$2(onClickListener2, dialog, view);
                }
            });
        } else {
            textView5.setVisibility(8);
        }
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setLayout(-2, -2);
            window.setGravity(17);
            window.setSoftInputMode(48);
        }
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        dialog.show();
    }

    static /* synthetic */ void lambda$alertDialog$1(DialogInterface.OnClickListener onClickListener, Dialog dialog, View view) {
        if (onClickListener != null) {
            onClickListener.onClick(dialog, -2);
        }
        dialog.dismiss();
    }

    static /* synthetic */ void lambda$alertDialog$2(DialogInterface.OnClickListener onClickListener, Dialog dialog, View view) {
        if (onClickListener != null) {
            onClickListener.onClick(dialog, -1);
        }
        dialog.dismiss();
    }

    public static void showInputDialog(Activity activity, String str, String str2, String str3, final boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i, String str4, int i2, String str5, int i3, String str6, final Consumer<String> consumer, final Runnable runnable) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_for_alert);
        dialog.setCancelable(true);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView textView2 = (TextView) dialog.findViewById(R.id.dialog_subtitle);
        final EditText editText = (EditText) dialog.findViewById(R.id.dialog_input);
        TextView textView3 = (TextView) dialog.findViewById(R.id.dialog_left);
        textView3.setTextColor(i);
        textView3.setText(str4);
        TextView textView4 = (TextView) dialog.findViewById(R.id.dialog_mid);
        textView4.setTextColor(i2);
        textView4.setText(str5);
        TextView textView5 = (TextView) dialog.findViewById(R.id.dialog_right);
        textView5.setTextColor(i3);
        textView5.setText(str6);
        textView.setText(str);
        textView2.setText(str2);
        if (z) {
            editText.setVisibility(0);
            if (z2) {
                editText.setInputType(129);
            }
            editText.setHint(str3);
        } else {
            editText.setVisibility(8);
        }
        if (z4) {
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(8);
        }
        if (z3) {
            textView4.setVisibility(0);
        } else {
            textView4.setVisibility(8);
        }
        if (z5) {
            textView5.setVisibility(0);
        } else {
            textView5.setVisibility(8);
        }
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Tool.lambda$showInputDialog$3(dialog, runnable, view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Tool.lambda$showInputDialog$4(dialog, runnable, view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Tool.lambda$showInputDialog$5(dialog, z, editText, consumer, view);
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setLayout(-2, -2);
            window.setGravity(17);
            window.setSoftInputMode(48);
        }
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        dialog.show();
    }

    static /* synthetic */ void lambda$showInputDialog$3(Dialog dialog, Runnable runnable, View view) {
        dialog.dismiss();
        if (runnable != null) {
            runnable.run();
        }
    }

    static /* synthetic */ void lambda$showInputDialog$4(Dialog dialog, Runnable runnable, View view) {
        dialog.dismiss();
        if (runnable != null) {
            runnable.run();
        }
    }

    static /* synthetic */ void lambda$showInputDialog$5(Dialog dialog, boolean z, EditText editText, Consumer consumer, View view) {
        dialog.dismiss();
        String strTrim = z ? editText.getText().toString().trim() : "";
        if (consumer != null) {
            consumer.accept(strTrim);
        }
    }

    public static HashMap<String, String> jsonStringToMap(String str) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.getString(next));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static boolean isValidIP(String str) {
        return str.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    public static boolean isValidSubnetMask(String str) throws NumberFormatException {
        try {
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit.length != 4) {
                return false;
            }
            int i = 0;
            for (String str2 : strArrSplit) {
                int i2 = Integer.parseInt(str2);
                if (i2 < 0 || i2 > 255) {
                    return false;
                }
                i = (i << 8) + i2;
            }
            boolean z = false;
            for (int i3 = 31; i3 >= 0; i3--) {
                if (((1 << i3) & i) == 0) {
                    z = true;
                } else if (z) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static void alertNotInUiThread(final Activity activity, final String str) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                Tool.alert(activity, str);
            }
        });
    }

    public static void alertNotInUiThread(final Activity activity, final int i) {
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.tool.Tool$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                Tool.alert(activity, i);
            }
        });
    }

    public static long mac2Long(String str) {
        try {
            return Long.parseLong(str.replaceAll(":", ""), 16);
        } catch (Exception unused) {
            return -1L;
        }
    }

    public static String extractHost(String str) {
        return !str.contains(",") ? str : str.split(",")[0].trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isEmptyNoTrim(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmail(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile("^([a-z0-9A-Z]+[_|-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$").matcher(str).matches();
    }

    public static boolean containInvalidCharacter(String str) {
        return str.indexOf(38) >= 0 || str.indexOf(39) >= 0 || str.indexOf(34) >= 0 || str.indexOf(60) >= 0 || str.indexOf(62) >= 0 || str.indexOf(44) >= 0 || str.indexOf(92) >= 0;
    }

    public static boolean hasDoubleSpace(String str) {
        if (str == null) {
            return false;
        }
        return str.contains("  ");
    }

    public static boolean containInvalidPassword(String str) {
        return (str.matches(".*[a-zA-Z].*") && str.matches(".*\\d.*")) ? false : true;
    }

    public static void sleep(long j) throws InterruptedException {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getTechSupportPrefix(Activity activity, JSONObject jSONObject, int i) throws JSONException {
        String string = jSONObject.getString("techInfoType" + i);
        return "WHATSAPP_ID".equals(string) ? "Whatsapp ID: " : "FACEBOOK_ID".equals(string) ? "Facebook ID: " : "PHONE_NUMBER".equals(string) ? activity.getString(R.string.page_register_text_tel_number) + ": " : "";
    }

    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0048: MOVE (r4 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:16:0x0048 */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Long getCRC32(java.io.File r9) throws java.lang.Throwable {
        /*
            java.lang.String r0 = " error"
            java.lang.String r1 = "Get CRC32 for file "
            java.lang.String r2 = "Eg4"
            java.util.zip.CRC32 r3 = new java.util.zip.CRC32
            r3.<init>()
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L7c
            r5.<init>(r9)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L7c
            r6 = 8192(0x2000, float:1.148E-41)
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
        L15:
            int r7 = r5.read(r6)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
            r8 = -1
            if (r7 == r8) goto L21
            r8 = 0
            r3.update(r6, r8, r7)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
            goto L15
        L21:
            long r6 = r3.getValue()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
            java.lang.Long r3 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
            r5.close()     // Catch: java.io.IOException -> L2d
            goto L46
        L2d:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r1)
            java.lang.String r9 = r9.getAbsolutePath()
            java.lang.StringBuilder r9 = r5.append(r9)
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r2, r9, r4)
        L46:
            return r3
        L47:
            r3 = move-exception
            r4 = r5
            goto Lba
        L4b:
            r3 = move-exception
            goto L53
        L4d:
            r3 = move-exception
            goto L7e
        L4f:
            r3 = move-exception
            goto Lba
        L51:
            r3 = move-exception
            r5 = r4
        L53:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L47
            r6.<init>()     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r6 = r6.append(r1)     // Catch: java.lang.Throwable -> L47
            java.lang.String r7 = r9.getAbsolutePath()     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch: java.lang.Throwable -> L47
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L47
            android.util.Log.e(r2, r6, r3)     // Catch: java.lang.Throwable -> L47
            if (r5 == 0) goto Lb9
            r5.close()     // Catch: java.io.IOException -> L75
            goto Lb9
        L75:
            r3 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r1)
            goto La6
        L7c:
            r3 = move-exception
            r5 = r4
        L7e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L47
            r6.<init>()     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r6 = r6.append(r1)     // Catch: java.lang.Throwable -> L47
            java.lang.String r7 = r9.getAbsolutePath()     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch: java.lang.Throwable -> L47
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L47
            android.util.Log.e(r2, r6, r3)     // Catch: java.lang.Throwable -> L47
            if (r5 == 0) goto Lb9
            r5.close()     // Catch: java.io.IOException -> La0
            goto Lb9
        La0:
            r3 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r1)
        La6:
            java.lang.String r9 = r9.getAbsolutePath()
            java.lang.StringBuilder r9 = r5.append(r9)
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r2, r9, r3)
        Lb9:
            return r4
        Lba:
            if (r4 == 0) goto Ld9
            r4.close()     // Catch: java.io.IOException -> Lc0
            goto Ld9
        Lc0:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r1)
            java.lang.String r9 = r9.getAbsolutePath()
            java.lang.StringBuilder r9 = r5.append(r9)
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r2, r9, r4)
        Ld9:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.tool.Tool.getCRC32(java.io.File):java.lang.Long");
    }

    public static String getTimezoneText(String str) {
        if (isEmpty(str)) {
            return "";
        }
        str.hashCode();
        switch (str) {
            case "WEST10":
                return "GMT -10";
            case "WEST11":
                return "GMT -11";
            case "WEST12":
                return "GMT -12";
            case "ZERO":
                return "GMT 0";
            case "EAST1":
                return "GMT +1";
            case "EAST2":
                return "GMT +2";
            case "EAST3":
                return "GMT +3";
            case "EAST4":
                return "GMT +4";
            case "EAST5":
                return "GMT +5";
            case "EAST6":
                return "GMT +6";
            case "EAST7":
                return "GMT +7";
            case "EAST8":
                return "GMT +8";
            case "EAST9":
                return "GMT +9";
            case "WEST1":
                return "GMT -1";
            case "WEST2":
                return "GMT -2";
            case "WEST3":
                return "GMT -3";
            case "WEST4":
                return "GMT -4";
            case "WEST5":
                return "GMT -5";
            case "WEST6":
                return "GMT -6";
            case "WEST7":
                return "GMT -7";
            case "WEST8":
                return "GMT -8";
            case "WEST9":
                return "GMT -9";
            case "EAST10":
                return "GMT +10";
            case "EAST11":
                return "GMT +11";
            case "EAST12":
                return "GMT +12";
            default:
                return "";
        }
    }

    public static Inverter getInverterByJsonObj(JSONObject jSONObject, Plant plant) throws JSONException {
        Inverter inverter = new Inverter();
        inverter.setSerialNum(jSONObject.getString("serialNum"));
        inverter.setPlantId(Long.valueOf(plant.getPlantId()));
        inverter.setPhase(Integer.valueOf(jSONObject.getInt(TypedValues.CycleType.S_WAVE_PHASE)));
        inverter.setDeviceType(Integer.valueOf(jSONObject.getInt("deviceType")));
        inverter.setDtc(Integer.valueOf(jSONObject.getInt("dtc")));
        inverter.setSubDeviceType(Integer.valueOf(jSONObject.getInt("subDeviceType")));
        inverter.setAllowGenExercise(jSONObject.getBoolean("allowGenExercise"));
        inverter.setAllowExport2Grid(jSONObject.getBoolean("allowExport2Grid"));
        inverter.setWithbatteryData(Boolean.valueOf(jSONObject.getBoolean("withbatteryData")));
        inverter.setMachineType(jSONObject.getInt("machineType"));
        inverter.setBatteryTypeFromModel(BATTERY_TYPE.valueOf(jSONObject.getString("batteryType")));
        inverter.setProtocolVersion(Integer.valueOf(jSONObject.getInt("protocolVersion")));
        inverter.setStandard(jSONObject.getString("standard"));
        inverter.setSlaveVersion(Integer.valueOf(jSONObject.getInt("slaveVersion")));
        inverter.setFwVersion(Integer.valueOf(jSONObject.getInt("fwVersion")));
        return inverter;
    }
}