package com.nfcx.eg4.view.overview.inverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.json.JSONArray;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class InverterOverviewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private JSONArray inverterArray;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public InverterOverviewAdapter(Context context, JSONArray jSONArray) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.inverterArray = jSONArray;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.inverterArray.length();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        try {
            return this.inverterArray.getJSONObject(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}