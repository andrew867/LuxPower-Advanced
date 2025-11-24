package com.lux.luxcloud.view.overview.dongle;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.tool.HttpTool;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Version;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class DongleManageAdapter extends BaseAdapter {
    private Context context;
    private JSONArray datalogArray;
    private LayoutInflater inflater;

    public DongleManageAdapter(Context context, JSONArray jSONArray) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.datalogArray = jSONArray;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.datalogArray.length();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        try {
            return this.datalogArray.getJSONObject(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        try {
            JSONObject jSONObject = (JSONObject) getItem(i);
            if (jSONObject != null) {
                return jSONObject.getLong("datalogSn");
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
            view = this.inflater.inflate(R.layout.activity_datalog_manage_list_item, (ViewGroup) null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        JSONObject jSONObject = (JSONObject) getItem(i);
        try {
            viewHolder.datalogSnTextView.setText(jSONObject.getString("datalogSn"));
            viewHolder.datalogTypeTextView.setText(jSONObject.getString("datalogTypeText"));
            if (jSONObject.getBoolean("lost")) {
                viewHolder.datalogStatusTextView.setTextColor(this.context.getResources().getColor(R.color.mainGray));
                viewHolder.datalogStatusTextView.setText("Offline");
            } else {
                viewHolder.datalogStatusTextView.setTextColor(this.context.getResources().getColor(R.color.colorNormal));
                viewHolder.datalogStatusTextView.setText("Online");
            }
            viewHolder.lastUpdateTimeTextView.setText(jSONObject.getString("lastUpdateTime"));
            viewHolder.refreshDatalogButton.setOnClickListener(new refreshInverterListener(jSONObject.getString("datalogSn")));
            viewHolder.removeDatalogButton.setOnClickListener(new removeListener(jSONObject.getString("datalogSn")));
        } catch (Exception e) {
            Log.e(Version.TAG, e.getMessage(), e);
        }
        return view;
    }

    class ViewHolder {
        TextView datalogSnTextView;
        TextView datalogStatusTextView;
        TextView datalogTypeTextView;
        TextView lastUpdateTimeTextView;
        Button refreshDatalogButton;
        Button removeDatalogButton;

        public ViewHolder(View view) {
            this.datalogSnTextView = (TextView) view.findViewById(R.id.datalog_manage_list_item_datalogSn);
            this.datalogStatusTextView = (TextView) view.findViewById(R.id.datalog_manage_list_item_plantStatus);
            this.lastUpdateTimeTextView = (TextView) view.findViewById(R.id.datalog_manage_list_item_lastUpdateTime);
            this.datalogTypeTextView = (TextView) view.findViewById(R.id.datalog_manage_list_item_datalogType);
            this.refreshDatalogButton = (Button) view.findViewById(R.id.datalog_manage_list_item_refresh);
            this.removeDatalogButton = (Button) view.findViewById(R.id.datalog_manage_list_item_remove);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class refreshInverterListener implements View.OnClickListener {
        private String datalogSn;

        public refreshInverterListener(String str) {
            this.datalogSn = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter$refreshInverterListener$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f$0.m661xfd7b1f6a();
                }
            }).start();
        }

        /* renamed from: lambda$onClick$2$com-lux-luxcloud-view-overview-dongle-DongleManageAdapter$refreshInverterListener, reason: not valid java name */
        /* synthetic */ void m661xfd7b1f6a() throws JSONException {
            try {
                HashMap map = new HashMap();
                map.put("serialNum", String.valueOf(this.datalogSn));
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/datalog/readInvInfo", map);
                if (jSONObjectPostJson != null) {
                    final String string = jSONObjectPostJson.getString("msg");
                    if (Tool.isEmpty(string)) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter$refreshInverterListener$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m659xae836be8();
                            }
                        });
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter$refreshInverterListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m660x55ff45a9(string);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* renamed from: lambda$onClick$0$com-lux-luxcloud-view-overview-dongle-DongleManageAdapter$refreshInverterListener, reason: not valid java name */
        /* synthetic */ void m659xae836be8() {
            Toast.makeText(DongleManageAdapter.this.context, "Finished", 1).show();
        }

        /* renamed from: lambda$onClick$1$com-lux-luxcloud-view-overview-dongle-DongleManageAdapter$refreshInverterListener, reason: not valid java name */
        /* synthetic */ void m660x55ff45a9(String str) {
            Toast.makeText(DongleManageAdapter.this.context, str, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class removeListener implements View.OnClickListener {
        private String datalogSn;

        public removeListener(String str) {
            this.datalogSn = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Tool.alert(DongleManageAdapter.this.context, DongleManageAdapter.this.context.getString(R.string.phrase_remove_dongle_text, this.datalogSn), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter.removeListener.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeListener.this.removeDongle();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDongle() {
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter$removeListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m664xc74de52e();
                }
            }).start();
        }

        /* renamed from: lambda$removeDongle$2$com-lux-luxcloud-view-overview-dongle-DongleManageAdapter$removeListener, reason: not valid java name */
        /* synthetic */ void m664xc74de52e() {
            try {
                HashMap map = new HashMap();
                map.put("serialNum", String.valueOf(this.datalogSn));
                JSONObject jSONObjectPostJson = HttpTool.postJson(GlobalInfo.getInstance().getBaseUrl() + "web/config/datalog/remove", map);
                if (jSONObjectPostJson != null) {
                    if (jSONObjectPostJson.getBoolean("success")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter$removeListener$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m662x926bf2f0();
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lux.luxcloud.view.overview.dongle.DongleManageAdapter$removeListener$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m663xacdcec0f();
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* renamed from: lambda$removeDongle$0$com-lux-luxcloud-view-overview-dongle-DongleManageAdapter$removeListener, reason: not valid java name */
        /* synthetic */ void m662x926bf2f0() {
            ((DongleManageActivity) DongleManageAdapter.this.context).m658xf95450f7();
            Toast.makeText(DongleManageAdapter.this.context, "success", 0).show();
        }

        /* renamed from: lambda$removeDongle$1$com-lux-luxcloud-view-overview-dongle-DongleManageAdapter$removeListener, reason: not valid java name */
        /* synthetic */ void m663xacdcec0f() {
            Toast.makeText(DongleManageAdapter.this.context, "fail", 0).show();
        }
    }
}