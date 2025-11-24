package com.nfcx.eg4.tool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ProTool {
    public static double count(int i, int i2, int i3, int i4, int i5) {
        return (((((i * 256) + i2) * 65536) + (i3 * 256)) + i4) / i5;
    }

    public static int count(int i, int i2) {
        return (i * 256) + i2;
    }

    public static long count(int i, int i2, int i3, int i4) {
        return (((i * 256) + i2) * 65536) + (i3 * 256) + i4;
    }

    public static long count4(int i, int i2, int i3, int i4) {
        return (i << 24) + (i2 << 16) + (i3 << 8) + i4;
    }

    public static int countN(int i, int i2) {
        int i3 = (i * 256) + i2;
        return (32768 & i3) > 0 ? -((65535 - i3) + 1) : i3;
    }

    public static int countN(int i, int i2, int i3, int i4) {
        return ((((((i | 0) << 8) | i2) << 8) | i3) << 8) | i4;
    }

    public static int getInt(byte b) {
        return b & 255;
    }

    public static int getInt(byte b, int i) {
        return (b + i) & 255;
    }

    public static String formatPf(int i) {
        if (i > 0 && i <= 1000) {
            return InvTool.formatData(i / 1000.0d, 3);
        }
        if (i > 1000 && i < 2000) {
            return InvTool.formatData((1000 - i) / 1000.0d, 3);
        }
        return "[" + i + "]";
    }

    public static byte[] convertLong2Byte2(byte[] bArr, int i, long j, int i2, boolean z) {
        if (bArr == null) {
            bArr = new byte[2];
            i = 0;
        }
        bArr[z ? i + 1 : i] = (byte) (j / 256);
        if (!z) {
            i++;
        }
        bArr[i] = (byte) Math.abs(j % 256);
        return bArr;
    }

    public static byte[] convertLong2Byte4(byte[] bArr, int i, long j, int i2, boolean z) {
        if (bArr == null) {
            bArr = new byte[4];
            i = 0;
        }
        bArr[z ? i + 3 : i] = (byte) ((j >> 24) & 255);
        bArr[z ? i + 2 : i + 1] = (byte) ((j >> 16) & 255);
        bArr[z ? i + 1 : i + 2] = (byte) ((j >> 8) & 255);
        if (!z) {
            i += 3;
        }
        bArr[i] = (byte) (j & 255);
        return bArr;
    }

    public static byte[] convertAsciiString2Byte(byte[] bArr, int i, String str) {
        return convertAsciiString2Byte(bArr, i, str, 0, false);
    }

    public static byte[] convertAsciiString2Byte(byte[] bArr, int i, String str, int i2, boolean z) {
        int length = str.length();
        if (bArr == null) {
            bArr = new byte[length];
            i = 0;
        }
        for (int i3 = 0; i3 < length; i3++) {
            bArr[i3 + i] = (byte) str.charAt(z ? (length - i3) - 1 : i3);
        }
        return bArr;
    }

    public static int count2Low(byte[] bArr, int i) {
        return count2(bArr, i + 1, i);
    }

    public static int count2(byte[] bArr, int i, int i2) {
        return count(getInt(bArr, i), getInt(bArr, i2));
    }

    public static long count4Low(byte[] bArr, int i) {
        return count4(getInt(bArr, i + 3), getInt(bArr, i + 2), getInt(bArr, i + 1), getInt(bArr, i));
    }

    public static float count(int i, int i2, int i3) {
        return count(i, i2) / i3;
    }

    public static float countN(int i, int i2, int i3) {
        return countN(i, i2) / i3;
    }

    public static double countN(int i, int i2, int i3, int i4, int i5) {
        return countN(i, i2, i3, i4) / i5;
    }

    public static int[] count2(int i) {
        return new int[]{i / 256, Math.abs(i % 256)};
    }

    public static String count2TextN(int i) {
        if (i < 0) {
            i = i + 65535 + 1;
        }
        return count2Text(i);
    }

    public static String count2Text(int i) {
        int[] iArrCount2 = count2(i);
        return getStringFromHex(iArrCount2[0]) + getStringFromHex(iArrCount2[1]);
    }

    public static String count2TextLow(int i) {
        int[] iArrCount2 = count2(i);
        return getStringFromHex(iArrCount2[1]) + getStringFromHex(iArrCount2[0]);
    }

    public static String count4TextLow(long j) {
        long j2 = j / 65536;
        long jAbs = Math.abs(j % 65536);
        long[] jArr = {jAbs % 256, jAbs / 256, j2 % 256, j2 / 256};
        return getStringFromHex(jArr[0]) + getStringFromHex(jArr[1]) + getStringFromHex(jArr[2]) + getStringFromHex(jArr[3]);
    }

    public static String count4TextHigh(long j) {
        long j2 = j / 65536;
        long jAbs = Math.abs(j % 65536);
        long[] jArr = {jAbs % 256, jAbs / 256, j2 % 256, j2 / 256};
        return getStringFromHex(jArr[3]) + getStringFromHex(jArr[2]) + getStringFromHex(jArr[1]) + getStringFromHex(jArr[0]);
    }

    public static String showHex(int i) {
        return fillZeros(Integer.toHexString(i), 2).toUpperCase();
    }

    public static String showData(String str) {
        String str2 = "";
        for (char c : str.toCharArray()) {
            String hexString = Integer.toHexString(c);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            str2 = str2 + hexString + " ";
        }
        return str2;
    }

    public static String showData(byte[] bArr) {
        return bArr != null ? showData(bArr, bArr.length) : "";
    }

    public static int getInt(byte[] bArr, int i) {
        return getInt(bArr, i, 0);
    }

    public static int getInt(byte[] bArr, int i, int i2) {
        return getInt(bArr[i], i2);
    }

    public static String showData(byte[] bArr, int i) {
        if (bArr == null) {
            i = 0;
        } else if (i > bArr.length) {
            i = bArr.length;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(fillZeros(Integer.toHexString(getInt(bArr[i2])), 2));
            stringBuffer.append(" ");
        }
        return stringBuffer.toString().toUpperCase().trim();
    }

    public static String getStringFromHex(long j) {
        return new Character((char) j).toString();
    }

    public static String fillZeros(String str, int i) {
        if (str != null) {
            while (str.length() < i) {
                str = "0" + str;
            }
        }
        return str;
    }

    public static int modbus_Caluation_CRC16(byte[] bArr, int i) {
        return modbus_Caluation_CRC16(bArr, 0, i);
    }

    public static int modbus_Caluation_CRC16(byte[] bArr, int i, int i2) {
        int i3 = 65535;
        for (int i4 = i; i4 < bArr.length && i4 < i + i2; i4++) {
            i3 ^= getInt(bArr, i4);
            for (int i5 = 0; i5 < 8; i5++) {
                int i6 = i3 >> 1;
                i3 = (i3 & 1) > 0 ? 40961 ^ i6 : i6;
            }
        }
        return i3;
    }
}