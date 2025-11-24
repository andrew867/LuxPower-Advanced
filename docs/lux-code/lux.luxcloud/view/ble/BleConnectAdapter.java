package com.lux.luxcloud.view.ble;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lux.luxcloud.R;
import com.lux.luxcloud.tool.Tool;
import java.util.List;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class BleConnectAdapter extends BaseAdapter {
    private Context context;
    private List<ScanResult> devices;
    private LayoutInflater inflater;

    public BleConnectAdapter(Context context, List<ScanResult> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.devices = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.devices.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        try {
            return this.devices.get(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        try {
            ScanResult scanResult = this.devices.get(i);
            if (scanResult != null) {
                return Tool.mac2Long(scanResult.getDevice().getAddress());
            }
            return -1L;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.inflater.inflate(R.layout.activity_ble_connect_item, (ViewGroup) null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ScanResult scanResult = (ScanResult) getItem(i);
        try {
            viewHolder.text1TextView.setText(scanResult.getDevice().getName() == null ? this.context.getString(R.string.string_unknown) : scanResult.getDevice().getName());
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append((CharSequence) "Mac:").append((CharSequence) scanResult.getDevice().getAddress()).append((CharSequence) " RSSI:").append((CharSequence) String.valueOf(scanResult.getRssi()));
            spannableStringBuilder.setSpan(new ForegroundColorSpan(-6381922), 0, 21, 34);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(-7508381), 21, spannableStringBuilder.length(), 34);
            viewHolder.text2TextView.setText(spannableStringBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    class ViewHolder {
        TextView text1TextView;
        TextView text2TextView;

        public ViewHolder(View view) {
            this.text1TextView = (TextView) view.findViewById(R.id.text1);
            this.text2TextView = (TextView) view.findViewById(R.id.text2);
        }
    }
}