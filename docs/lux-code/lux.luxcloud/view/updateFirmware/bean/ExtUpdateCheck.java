package com.lux.luxcloud.view.updateFirmware.bean;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ExtUpdateCheck {
    private boolean ext5CheckOnly;
    private boolean skipExtendUpdate;

    public ExtUpdateCheck(boolean z, boolean z2) {
        this.ext5CheckOnly = z;
        this.skipExtendUpdate = z2;
    }

    public boolean isExt5CheckOnly() {
        return this.ext5CheckOnly;
    }

    public void setExt5CheckOnly(boolean z) {
        this.ext5CheckOnly = z;
    }

    public boolean isSkipExtendUpdate() {
        return this.skipExtendUpdate;
    }

    public void setSkipExtendUpdate(boolean z) {
        this.skipExtendUpdate = z;
    }
}