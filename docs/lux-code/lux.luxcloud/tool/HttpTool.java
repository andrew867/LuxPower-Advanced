package com.lux.luxcloud.tool;

import com.google.common.net.HttpHeaders;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.view.login.LoginActivity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
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
        if (System.currentTimeMillis() - lastTimeGetSession < 5000) {
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
            LogUtils.writeLog("LuxPower - postJson responseText = " + strPostString);
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
            System.out.println("LuxPowerresponseText = " + strPostString);
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
        LogUtils.writeLog("LuxPower - multiPartPostJson responseText = " + strMultiPartPostString);
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

    /* JADX WARN: Removed duplicated region for block: B:113:0x0207 A[Catch: all -> 0x026d, Exception -> 0x0271, LOOP:1: B:111:0x0201->B:113:0x0207, LOOP_END, TryCatch #30 {Exception -> 0x0271, all -> 0x026d, blocks: (B:110:0x01fc, B:111:0x0201, B:113:0x0207, B:114:0x020b, B:117:0x0243, B:119:0x0249), top: B:219:0x01fc }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0241 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0286 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0263 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x027b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x024f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0291 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x02a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x02b7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x020b A[EDGE_INSN: B:228:0x020b->B:114:0x020b BREAK  A[LOOP:1: B:111:0x0201->B:113:0x0207], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:232:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:234:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String doPost(java.lang.String r16, java.lang.StringBuffer r17, java.util.Map<java.lang.String, java.lang.String> r18, boolean r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.tool.HttpTool.doPost(java.lang.String, java.lang.StringBuffer, java.util.Map, boolean):java.lang.String");
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
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.tool.HttpTool.getString(java.lang.String, boolean):java.lang.String");
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