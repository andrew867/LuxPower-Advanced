package com.nfcx.eg4.tool;

import androidx.lifecycle.CoroutineLiveDataKt;
import com.google.common.net.HttpHeaders;
import com.nfcx.eg4.global.GlobalInfo;
import com.nfcx.eg4.view.login.LoginActivity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class HttpTool {
    private static String COOKIE_SESSION_NAME = "JSESSIONID";
    private static String JSESSIONID;
    public static final Map<String, String> JSON_CONTENT_TYPE_HEADERS;
    private static long lastTimeGetSession;

    static {
        HashMap map = new HashMap();
        JSON_CONTENT_TYPE_HEADERS = map;
        map.put(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
    }

    private static synchronized boolean reLoginWhenSessionLost() {
        if (System.currentTimeMillis() - lastTimeGetSession < CoroutineLiveDataKt.DEFAULT_TIMEOUT) {
            return true;
        }
        if (!Tool.isEmpty(LoginActivity.usernameForLogin) && !Tool.isEmpty(LoginActivity.passwordForLogin)) {
            try {
                HashMap map = new HashMap();
                map.put("account", LoginActivity.usernameForLogin);
                map.put("password", LoginActivity.passwordForLogin);
                map.put("language", GlobalInfo.getInstance().getLanguage());
                JSONObject jSONObjectPostJson = postJson(GlobalInfo.getInstance().getBaseUrl() + "api/login", map);
                if (jSONObjectPostJson != null && jSONObjectPostJson.getBoolean("success")) {
                    lastTimeGetSession = System.currentTimeMillis();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static final String postString(String str, Map<String, String> map, Map<String, String> map2) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            for (String str2 : map.keySet()) {
                if (stringBuffer.length() > 0) {
                    stringBuffer.append("&");
                }
                try {
                    stringBuffer.append(str2).append("=").append(URLEncoder.encode(map.get(str2), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return doPost(str, stringBuffer, map2);
    }

    public static final String postString(String str, Map<String, String> map, String str2) throws Exception {
        return new String(postString(str, map, (Map<String, String>) null).getBytes(str2));
    }

    public static final JSONObject postJson(String str, Map<String, String> map) {
        return postJson(str, map, null);
    }

    public static final JSONObject postJson(String str, Map<String, String> map, Map<String, String> map2) {
        try {
            String strPostString = postString(str, map, map2);
            LogUtils.writeLog("Eg4 - postJson responseText = " + strPostString);
            if (Tool.isEmpty(strPostString)) {
                return null;
            }
            return (JSONObject) new JSONTokener(strPostString).nextValue();
        } catch (Exception e) {
            LogUtils.writeLog(e);
            return null;
        }
    }

    public static final JSONArray postJsonArray(String str, Map<String, String> map) {
        return postJsonArray(str, map, null);
    }

    public static final JSONArray postJsonArray(String str, Map<String, String> map, Map<String, String> map2) {
        try {
            String strPostString = postString(str, map, map2);
            System.out.println("Eg4responseText = " + strPostString);
            if (Tool.isEmpty(strPostString)) {
                return null;
            }
            return (JSONArray) new JSONTokener(strPostString).nextValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final JSONObject multiPartPostJson(String str, String str2) throws Exception {
        return multiPartPostJson(str, str2, JSON_CONTENT_TYPE_HEADERS);
    }

    public static final JSONObject multiPartPostJson(String str, String str2, Map<String, String> map) throws Exception {
        String strMultiPartPostString = multiPartPostString(str, str2, map);
        LogUtils.writeLog("Eg4 - multiPartPostJson responseText = " + strMultiPartPostString);
        return (JSONObject) new JSONTokener(strMultiPartPostString).nextValue();
    }

    public static final String multiPartPostString(String str, String str2, Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2);
        return doPost(str, stringBuffer, map);
    }

    public static final String doPost(String str, StringBuffer stringBuffer, Map<String, String> map) {
        return doPost(str, stringBuffer, map, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01e5 A[Catch: all -> 0x024b, Exception -> 0x0250, LOOP:3: B:104:0x01df->B:106:0x01e5, LOOP_END, TryCatch #26 {Exception -> 0x0250, all -> 0x024b, blocks: (B:103:0x01da, B:104:0x01df, B:106:0x01e5, B:107:0x01e9, B:110:0x0221, B:112:0x0227), top: B:215:0x01da }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x022d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0270 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x027f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0295 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x028a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0265 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0241 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x025a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x01e9 A[EDGE_INSN: B:221:0x01e9->B:107:0x01e9 BREAK  A[LOOP:3: B:104:0x01df->B:106:0x01e5], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:222:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String doPost(java.lang.String r15, java.lang.StringBuffer r16, java.util.Map<java.lang.String, java.lang.String> r17, boolean r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 671
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.tool.HttpTool.doPost(java.lang.String, java.lang.StringBuffer, java.util.Map, boolean):java.lang.String");
    }

    public static final String getString(String str) {
        return getString(str, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x013f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0135 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String getString(java.lang.String r8, boolean r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nfcx.eg4.tool.HttpTool.getString(java.lang.String, boolean):java.lang.String");
    }

    public static final JSONObject getJson(String str) throws Exception {
        try {
            String string = getString(str);
            System.out.println("responseText = " + string);
            if (Tool.isEmpty(string)) {
                return null;
            }
            return (JSONObject) new JSONTokener(string).nextValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final JSONObject getJson(String str, String str2) throws Exception {
        return (JSONObject) new JSONTokener(new String(getString(str).getBytes(str2))).nextValue();
    }

    public static String getCurrentSessionId() {
        return JSESSIONID;
    }
}