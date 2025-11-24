package com.lux.luxcloud.global.bean.cluster;

import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.version.Version;
import java.util.Map;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum CLUSTER_GROUP {
    FIRST { // from class: com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP.1
        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public long getDefaultClusterId() {
            return 2L;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getDefaultUrl() {
            return "https://as.luxpowertek.com/WManage/";
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getGroupName() {
            return Constants.CLUSTER_GROUP_FIRST;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public boolean isAlone() {
            return false;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public Map<Long, Cluster> getClusterMap() {
            return GlobalInfo.getInstance().getClusterMap();
        }
    },
    SECOND { // from class: com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP.2
        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public long getDefaultClusterId() {
            return 100L;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getDefaultUrl() {
            return "https://us.luxpowertek.com/WManage/";
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getGroupName() {
            return Constants.CLUSTER_GROUP_SECOND;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public boolean isAlone() {
            return false;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public Map<Long, Cluster> getClusterMap() {
            return GlobalInfo.getInstance().getClusterMap();
        }
    },
    INDIA { // from class: com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP.3
        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public long getDefaultClusterId() {
            return 200L;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getDefaultUrl() {
            return Version.INIDA_DEFAULT_BASE_URL;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getGroupName() {
            return Constants.CLUSTER_GROUP_INDIA;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public boolean isAlone() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public Map<Long, Cluster> getClusterMap() {
            return GlobalInfo.getInstance().getClusterMap();
        }
    },
    VIETNAM { // from class: com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP.4
        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public long getDefaultClusterId() {
            return 400L;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getDefaultUrl() {
            return Version.VN_DEFAULT_BASE_URL;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getGroupName() {
            return Constants.CLUSTER_GROUP_VIETNAM;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public boolean isAlone() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public Map<Long, Cluster> getClusterMap() {
            return GlobalInfo.getInstance().getClusterMap();
        }
    },
    PHILIPPINES { // from class: com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP.5
        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public long getDefaultClusterId() {
            return 300L;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getDefaultUrl() {
            return Version.PHI_DEFAULT_BASE_URL;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getGroupName() {
            return Constants.CLUSTER_GROUP_PHILIPPINES;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public boolean isAlone() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public Map<Long, Cluster> getClusterMap() {
            return GlobalInfo.getInstance().getClusterMap();
        }
    },
    EU2 { // from class: com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP.6
        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public long getDefaultClusterId() {
            return 500L;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getDefaultUrl() {
            return Version.EU2_DEFAULT_BASE_URL;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public String getGroupName() {
            return Constants.CLUSTER_GROUP_EU2;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public boolean isAlone() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.cluster.CLUSTER_GROUP
        public Map<Long, Cluster> getClusterMap() {
            return GlobalInfo.getInstance().getClusterMap();
        }
    };

    public abstract Map<Long, Cluster> getClusterMap();

    public abstract long getDefaultClusterId();

    public abstract String getDefaultUrl();

    public abstract String getGroupName();

    public abstract boolean isAlone();

    public static CLUSTER_GROUP fromName(String str) {
        for (CLUSTER_GROUP cluster_group : values()) {
            if (cluster_group.getGroupName().equals(str)) {
                return cluster_group;
            }
        }
        return FIRST;
    }
}