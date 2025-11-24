package com.nfcx.eg4.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ImageUtil {
    private static Map<String, Bitmap> mMemoryCache = new ConcurrentHashMap();
    private static int cacheSize = 0;

    /* JADX WARN: Removed duplicated region for block: B:27:0x004d A[Catch: IOException -> 0x0038, TRY_ENTER, TRY_LEAVE, TryCatch #0 {IOException -> 0x0038, blocks: (B:16:0x0031, B:27:0x004d), top: B:30:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String bitmapToBase64(android.graphics.Bitmap r4) throws java.lang.Throwable {
        /*
            java.lang.String r0 = ""
            r1 = 0
            if (r4 == 0) goto L4b
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            r2.<init>()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r3 = 100
            r4.compress(r1, r3, r2)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r2.flush()     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r2.close()     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            byte[] r4 = r2.toByteArray()     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r1 = 0
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r1)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r0 = r4
            r1 = r2
            goto L4b
        L23:
            r4 = move-exception
            r1 = r2
            goto L3d
        L26:
            r4 = move-exception
            r1 = r2
            goto L2c
        L29:
            r4 = move-exception
            goto L3d
        L2b:
            r4 = move-exception
        L2c:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L29
            if (r1 == 0) goto L53
            r1.flush()     // Catch: java.io.IOException -> L38
            r1.close()     // Catch: java.io.IOException -> L38
            goto L53
        L38:
            r4 = move-exception
            r4.printStackTrace()
            goto L53
        L3d:
            if (r1 == 0) goto L4a
            r1.flush()     // Catch: java.io.IOException -> L46
            r1.close()     // Catch: java.io.IOException -> L46
            goto L4a
        L46:
            r0 = move-exception
            r0.printStackTrace()
        L4a:
            throw r4
        L4b:
            if (r1 == 0) goto L53
            r1.flush()     // Catch: java.io.IOException -> L38
            r1.close()     // Catch: java.io.IOException -> L38
        L53:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.tool.ImageUtil.bitmapToBase64(android.graphics.Bitmap):java.lang.String");
    }

    public static Bitmap base64ToBitmap(String str, int i, int i2) {
        if (cacheSize == 0) {
            cacheSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8);
        }
        if (str == null || str.trim().isEmpty()) {
            Log.e("ImageUtil", "Base64 data is null or empty.");
            return null;
        }
        Bitmap bitmap = mMemoryCache.get(str);
        if (bitmap != null) {
            return bitmap;
        }
        String strReplaceAll = str.replaceAll("^data:image/[^;]+;base64,", "");
        try {
            byte[] bArrDecode = Base64.decode(strReplaceAll, 0);
            try {
                Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
                if (bitmapDecodeByteArray == null) {
                    Log.e("ImageUtil", "Failed to decode byte array to bitmap.");
                    return null;
                }
                int iMin = Math.min(200, bitmapDecodeByteArray.getWidth() - i);
                int iMin2 = Math.min(200, bitmapDecodeByteArray.getHeight() - i2);
                if (iMin > 0 && iMin2 > 0) {
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeByteArray, i, i2, iMin, iMin2);
                    mMemoryCache.put(strReplaceAll, bitmapCreateBitmap);
                    return bitmapCreateBitmap;
                }
                Log.e("ImageUtil", "Invalid crop dimensions: width=" + iMin + ", height=" + iMin2);
                return null;
            } catch (IllegalArgumentException | OutOfMemoryError e) {
                Log.e("ImageUtil", "Error while creating bitmap: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        } catch (IllegalArgumentException e2) {
            Log.e("ImageUtil", "Base64 decoding failed: " + e2.getMessage());
            return null;
        }
    }
}