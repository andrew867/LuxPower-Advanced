package com.nfcx.eg4.tool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class FrameTool {
    public static String getDatalogSn(String str) {
        return str.substring(8, 18);
    }

    public static String getInverterSn(String str) {
        return str.substring(22, 32);
    }

    public static String getRegisterText(String str, int i, int i2) {
        int i3 = (i * 2) + 35;
        return str.substring(i3, (i2 * 2) + i3);
    }

    public static int getRegHighInt(String str, int i) {
        return str.charAt((i * 2) + 35 + 1);
    }

    public static int getRegLowInt(String str, int i) {
        return str.charAt((i * 2) + 35);
    }

    public static int getRegister2(String str, int i) {
        int i2 = (i * 2) + 35;
        return ProTool.count(str.charAt(i2 + 1), str.charAt(i2));
    }

    public static long getRegister4(String str, int i) {
        int i2 = (i * 2) + 35;
        return ProTool.count(str.charAt(i2 + 3), str.charAt(i2 + 2), str.charAt(i2 + 1), str.charAt(i2));
    }
}