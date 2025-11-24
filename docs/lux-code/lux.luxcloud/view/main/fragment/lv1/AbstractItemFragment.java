package com.lux.luxcloud.view.main.fragment.lv1;

import androidx.fragment.app.Fragment;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class AbstractItemFragment extends Fragment {
    public boolean isAdded;
    protected long itemId;

    public AbstractItemFragment(long j) {
        this.itemId = j;
    }

    public long getItemId() {
        return this.itemId;
    }
}