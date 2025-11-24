package com.nfcx.eg4.tool;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import com.nfcx.eg4.version.Version;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class LogUtils {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final String LINE_END = ProTool.getStringFromHex(13) + ProTool.getStringFromHex(10);
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static BufferedWriter bufferedWriter = null;

    public static void verifyStoragePermissions(Activity activity) {
        try {
            if (ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openLogWriter() throws IOException {
        try {
            BufferedWriter bufferedWriter2 = bufferedWriter;
            if (bufferedWriter2 != null) {
                try {
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (IOException e) {
                            Log.e(Version.TAG, e.getMessage(), e);
                        }
                    }
                    bufferedWriter = null;
                } catch (Throwable th) {
                    bufferedWriter = null;
                    throw th;
                }
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/LuxPower_" + InvTool.formatShortDateTime(new Date()) + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
        } catch (Exception e2) {
            Log.e(Version.TAG, e2.getMessage(), e2);
        }
    }

    public static synchronized void writeLog(Exception exc) {
        if (exc != null) {
            writeLog("Exception: " + exc.getMessage());
            for (StackTraceElement stackTraceElement : exc.getStackTrace()) {
                writeLog("\r\n\t" + stackTraceElement);
            }
        }
    }

    public static synchronized void writeLog(String str) {
        Log.i(Version.TAG, str);
    }
}