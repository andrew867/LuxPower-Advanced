package com.lux.luxcloud.global.cache;

import com.lux.luxcloud.tool.Tool;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class RemoteSetCacheManager {
    private static final RemoteSetCacheManager instance = new RemoteSetCacheManager();
    private Map<String, RemoteSetCache> cacheMap = new ConcurrentHashMap();

    public static RemoteSetCacheManager getInstance() {
        return instance;
    }

    public void clearCache(String str) {
        if (Tool.isEmpty(str)) {
            return;
        }
        this.cacheMap.remove(str);
    }

    public JSONObject getCache(String str) {
        RemoteSetCache remoteSetCache;
        if (Tool.isEmpty(str) || (remoteSetCache = this.cacheMap.get(str)) == null) {
            return null;
        }
        if (System.currentTimeMillis() - remoteSetCache.getTime() < 600000) {
            return remoteSetCache.getResult();
        }
        this.cacheMap.remove(str);
        return null;
    }

    public void setCache(String str, JSONObject jSONObject) throws JSONException {
        RemoteSetCache remoteSetCache = this.cacheMap.get(str);
        if (remoteSetCache == null) {
            remoteSetCache = new RemoteSetCache();
        }
        if (remoteSetCache.getResult() == null) {
            remoteSetCache.setResult(jSONObject);
        } else {
            JSONObject result = remoteSetCache.getResult();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    result.put(next, jSONObject.get(next));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        remoteSetCache.setTime(System.currentTimeMillis());
        this.cacheMap.put(str, remoteSetCache);
    }
}