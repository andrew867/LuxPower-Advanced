package com.lux.luxcloud.global.bean.user;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public enum ROLE {
    ADMIN { // from class: com.lux.luxcloud.global.bean.user.ROLE.1
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.admin";
        }
    },
    MAINTAIN { // from class: com.lux.luxcloud.global.bean.user.ROLE.2
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.maintain";
        }
    },
    OWNER { // from class: com.lux.luxcloud.global.bean.user.ROLE.3
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getOwnerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.owner";
        }
    },
    P_ASSISTANT { // from class: com.lux.luxcloud.global.bean.user.ROLE.4
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getOwnerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.assistant";
        }
    },
    OWNER_L2 { // from class: com.lux.luxcloud.global.bean.user.ROLE.5
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getOwnerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.owner.l2";
        }
    },
    P_ASSISTANT_L2 { // from class: com.lux.luxcloud.global.bean.user.ROLE.6
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getOwnerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.assistant.l2";
        }
    },
    OWNER_L3 { // from class: com.lux.luxcloud.global.bean.user.ROLE.7
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getOwnerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.owner.l3";
        }
    },
    P_ASSISTANT_L3 { // from class: com.lux.luxcloud.global.bean.user.ROLE.8
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getOwnerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.assistant.l3";
        }
    },
    INSTALLER { // from class: com.lux.luxcloud.global.bean.user.ROLE.9
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getInstallerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.installer";
        }
    },
    I_ASSISTANT { // from class: com.lux.luxcloud.global.bean.user.ROLE.10
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public boolean getInstallerLevelCheck() {
            return true;
        }

        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.assistant.i";
        }
    },
    VIEWER { // from class: com.lux.luxcloud.global.bean.user.ROLE.11
        @Override // com.lux.luxcloud.global.bean.user.ROLE
        public String getTextResourceId() {
            return "phase.role.viewer";
        }
    };

    public boolean getInstallerLevelCheck() {
        return false;
    }

    public boolean getOwnerLevelCheck() {
        return false;
    }

    public abstract String getTextResourceId();

    public String getName() {
        return name();
    }

    public static ROLE getEnumByName(String str) {
        for (ROLE role : values()) {
            if (role.name().equals(str)) {
                return role;
            }
        }
        return null;
    }
}