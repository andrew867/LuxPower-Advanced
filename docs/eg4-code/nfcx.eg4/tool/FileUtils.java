package com.nfcx.eg4.tool;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class FileUtils {
    public static String getFilePathFromContentUri(Uri uri, ContentResolver contentResolver) {
        String[] strArr = {"_data"};
        Cursor cursorQuery = contentResolver.query(uri, strArr, null, null, null);
        cursorQuery.moveToFirst();
        String string = cursorQuery.getString(cursorQuery.getColumnIndex(strArr[0]));
        cursorQuery.close();
        return string;
    }
}