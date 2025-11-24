package com.nfcx.eg4.view.updateFirmware.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nfcx.eg4.R;
import com.nfcx.eg4.view.updateFirmware.DownloadFirmwareActivity;
import com.nfcx.eg4.view.updateFirmware.bean.UpdateFileCache;
import java.util.List;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class DownloadFirmwareAdapter extends BaseAdapter {
    private List<UpdateFileCache> updateFileCaches;

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public DownloadFirmwareAdapter(List<UpdateFileCache> list) {
        this.updateFileCaches = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<UpdateFileCache> list = this.updateFileCaches;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewInflate = DownloadFirmwareActivity.instance.getLayoutInflater().inflate(R.layout.activity_download_firmware_item, (ViewGroup) null);
        UpdateFileCache updateFileCache = this.updateFileCaches.get(i);
        ((TextView) viewInflate.findViewById(R.id.fileTextView)).setText(updateFileCache.getFileName());
        ((ImageView) viewInflate.findViewById(R.id.checkmarkImageView)).setVisibility(updateFileCache.isDoneDownload() ? 0 : 4);
        return viewInflate;
    }
}