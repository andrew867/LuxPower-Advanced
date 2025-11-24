package com.lux.luxcloud.global.bean.cluster;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Cluster {
    private long clusterId;
    private String clusterName;
    private String clusterPrefixUrl;
    private boolean major;

    public long getClusterId() {
        return this.clusterId;
    }

    public void setClusterId(long j) {
        this.clusterId = j;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(String str) {
        this.clusterName = str;
    }

    public String getClusterPrefixUrl() {
        return this.clusterPrefixUrl;
    }

    public void setClusterPrefixUrl(String str) {
        this.clusterPrefixUrl = str;
    }

    public boolean isMajor() {
        return this.major;
    }

    public void setMajor(boolean z) {
        this.major = z;
    }
}