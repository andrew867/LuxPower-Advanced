package com.nfcx.eg4.tool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class PinTool {
    private static final String[] numbers = {"2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] characters = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static boolean verifyDatalog(String str, String str2) {
        if (Tool.isEmpty(str) || Tool.isEmpty(str2) || str.length() < 10) {
            return false;
        }
        return generateCheckCode(str).equals(str2);
    }

    private static String generateCheckCode(String str) {
        String str2 = "";
        if (!Tool.isEmpty(str) && str.length() == 10) {
            int i = 0;
            while (i < str.length()) {
                int iCharAt = str.charAt(i) + str.charAt(i + 1) + str.charAt(str.length() - 1) + i + 1;
                i += 2;
                int i2 = iCharAt * i;
                if (i2 % 3 == 0) {
                    str2 = str2 + numbers[i2 % 8];
                } else {
                    str2 = str2 + characters[i2 % 24];
                }
            }
        }
        return str2;
    }
}