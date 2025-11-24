package com.lux.luxcloud.tool;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
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