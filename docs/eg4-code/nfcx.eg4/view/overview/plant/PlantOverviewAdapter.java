package com.nfcx.eg4.view.overview.plant;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.messaging.Constants;
import com.nfcx.eg4.R;
import com.nfcx.eg4.version.Version;
import com.nfcx.eg4.view.plant.AddDatalogActivity;
import com.nfcx.eg4.view.plant.EditPlantActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class PlantOverviewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private JSONArray plantArray;

    public PlantOverviewAdapter(Context context, JSONArray jSONArray) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.plantArray = jSONArray;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.plantArray.length();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        try {
            return this.plantArray.getJSONObject(i);
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
                return jSONObject.getLong("plantId");
            }
            return -1L;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) throws JSONException {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.inflater.inflate(R.layout.activity_plant_overview_list_item, (ViewGroup) null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        JSONObject jSONObject = (JSONObject) getItem(i);
        try {
            viewHolder.plantTitleTextView.setText(jSONObject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
            String string = jSONObject.getString("statusText");
            if (Constants.IPC_BUNDLE_KEY_SEND_ERROR.equals(string)) {
                viewHolder.plantStatusTextView.setTextColor(this.context.getResources().getColor(R.color.colorError));
            } else if ("warning".equals(string)) {
                viewHolder.plantStatusTextView.setTextColor(this.context.getResources().getColor(R.color.colorWarning));
            } else if ("normal".equals(string)) {
                viewHolder.plantStatusTextView.setTextColor(this.context.getResources().getColor(R.color.colorNormal));
            } else {
                viewHolder.plantStatusTextView.setTextColor(this.context.getResources().getColor(R.color.mainGray));
            }
            viewHolder.plantStatusTextView.setText(jSONObject.getString("statusLocaleText"));
            viewHolder.plantCreateDateTextView.setText(jSONObject.getString("createDateText"));
            if (jSONObject.has("address")) {
                viewHolder.plantAddressTextView.setText(jSONObject.getString("address"));
            }
            viewHolder.editPlantButton.setOnClickListener(new EditPlantListener(jSONObject.getLong("plantId")));
            viewHolder.addWifiModuleButton.setOnClickListener(new AddWifiModuleListener(jSONObject.getLong("plantId")));
        } catch (Exception e) {
            Log.e(Version.TAG, e.getMessage(), e);
        }
        return view;
    }

    class ViewHolder {
        Button addWifiModuleButton;
        Button editPlantButton;
        TextView plantAddressTextView;
        TextView plantCreateDateTextView;
        TextView plantStatusTextView;
        TextView plantTitleTextView;

        public ViewHolder(View view) {
            this.plantTitleTextView = (TextView) view.findViewById(R.id.plant_overview_list_item_plantTitle);
            this.plantStatusTextView = (TextView) view.findViewById(R.id.plant_overview_list_item_plantStatus);
            this.plantCreateDateTextView = (TextView) view.findViewById(R.id.plant_overview_list_item_plantCreateDate);
            this.plantAddressTextView = (TextView) view.findViewById(R.id.plant_overview_list_item_plantAddress);
            this.editPlantButton = (Button) view.findViewById(R.id.plant_overview_list_item_editPlant);
            this.addWifiModuleButton = (Button) view.findViewById(R.id.plant_overview_list_item_addWifiModule);
        }
    }

    private class EditPlantListener implements View.OnClickListener {
        private long plantId;

        public EditPlantListener(long j) {
            this.plantId = j;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intent intent = new Intent(PlantOverviewAdapter.this.context, (Class<?>) EditPlantActivity.class);
            intent.putExtra("plantId", this.plantId);
            PlantOverviewAdapter.this.context.startActivity(intent);
        }
    }

    private class AddWifiModuleListener implements View.OnClickListener {
        private long plantId;

        public AddWifiModuleListener(long j) {
            this.plantId = j;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intent intent = new Intent(PlantOverviewAdapter.this.context, (Class<?>) AddDatalogActivity.class);
            intent.putExtra("plantId", this.plantId);
            PlantOverviewAdapter.this.context.startActivity(intent);
        }
    }
}