package com.nfcx.eg4.view.main.fragment.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.bean.event.EventRecord;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.main.MainActivity;
import java.util.List;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class EventRecordAdapter extends BaseAdapter {
    private List<EventRecord> eventRecords;

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public EventRecordAdapter(List<EventRecord> list) {
        this.eventRecords = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.eventRecords.size();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewInflate = MainActivity.instance.getLayoutInflater().inflate(R.layout.fragment_lv1_event_item, (ViewGroup) null);
        EventRecord eventRecord = this.eventRecords.get(i);
        ((TextView) viewInflate.findViewById(R.id.fragment_lv1_event_item_title)).setText(eventRecord.getEventTypeText() + ": " + eventRecord.getEventText());
        ((TextView) viewInflate.findViewById(R.id.fragment_lv1_event_item_device)).setText(eventRecord.getSerialNum());
        ((TextView) viewInflate.findViewById(R.id.fragment_lv1_event_item_time)).setText(eventRecord.getStartTime() + (!Tool.isEmpty(eventRecord.getRenormalTime()) ? " - " + eventRecord.getRenormalTime() : ""));
        return viewInflate;
    }
}