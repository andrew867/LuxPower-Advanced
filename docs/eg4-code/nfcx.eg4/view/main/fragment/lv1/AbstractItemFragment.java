package com.nfcx.eg4.view.main.fragment.lv1;

import androidx.fragment.app.Fragment;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
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