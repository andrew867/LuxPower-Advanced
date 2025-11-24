package com.lux.luxcloud.view.plant;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.GlobalInfo;
import com.lux.luxcloud.tool.ImageUtil;
import com.lux.luxcloud.tool.Tool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class PlantListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private JSONArray plantArray;

    public PlantListAdapter(Activity activity, JSONArray jSONArray) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
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
            view = this.inflater.inflate(R.layout.activity_plant_list_item, (ViewGroup) null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        JSONObject jSONObject = (JSONObject) getItem(i);
        try {
            if (jSONObject.has("hasPlantImage") && jSONObject.getBoolean("hasPlantImage")) {
                viewHolder.plantLogoImageView.setImageBitmap(ImageUtil.base64ToBitmap(jSONObject.getString("imageText"), jSONObject.getInt("xBased200"), jSONObject.getInt("yBased200")));
            } else {
                viewHolder.plantLogoImageView.setImageResource(R.drawable.plant_logo);
            }
            viewHolder.plantTitleTextView.setText(jSONObject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
            viewHolder.plantCreateDateTextView.setText(jSONObject.getString("createDateText"));
            viewHolder.plantTodayYieldingTextView.setText(jSONObject.getString("todayYieldingText"));
            viewHolder.plantTotalYieldingTextView.setText(jSONObject.getString("totalYieldingText"));
            if (jSONObject.has("address")) {
                viewHolder.plantAddressTextView.setText(jSONObject.getString("address"));
            }
            viewHolder.editPlantButton.setOnClickListener(new EditPlantListener(jSONObject.getLong("plantId")));
            viewHolder.addWifiModuleButton.setOnClickListener(new AddWifiModuleListener(jSONObject.getLong("plantId")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    class ViewHolder {
        Button addWifiModuleButton;
        Button editPlantButton;
        TextView plantAddressTextView;
        TextView plantCreateDateTextView;
        ImageView plantLogoImageView;
        TextView plantTitleTextView;
        TextView plantTodayYieldingTextView;
        TextView plantTotalYieldingTextView;

        public ViewHolder(View view) {
            this.plantLogoImageView = (ImageView) view.findViewById(R.id.plant_list_item_plantLogo);
            this.plantTitleTextView = (TextView) view.findViewById(R.id.plant_list_item_plantTitle);
            this.plantCreateDateTextView = (TextView) view.findViewById(R.id.plant_list_item_plantCreateDate);
            this.plantTodayYieldingTextView = (TextView) view.findViewById(R.id.plant_list_item_plantTodayYielding);
            this.plantTotalYieldingTextView = (TextView) view.findViewById(R.id.plant_list_item_plantTotalYielding);
            this.plantAddressTextView = (TextView) view.findViewById(R.id.plant_list_item_plantAddress);
            this.editPlantButton = (Button) view.findViewById(R.id.plant_list_item_editPlant);
            this.addWifiModuleButton = (Button) view.findViewById(R.id.plant_list_item_addWifiModule);
        }
    }

    private class EditPlantListener implements View.OnClickListener {
        private long plantId;

        public EditPlantListener(long j) {
            this.plantId = j;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (GlobalInfo.getInstance().isInited()) {
                Intent intent = new Intent(PlantListAdapter.this.activity, (Class<?>) EditPlantActivity.class);
                intent.putExtra("plantId", this.plantId);
                PlantListAdapter.this.activity.startActivity(intent);
            } else {
                Tool.alert(PlantListActivity.instance, R.string.phrase_please_wait_seconds);
                GlobalInfo.getInstance().isIniting();
            }
        }
    }

    private class AddWifiModuleListener implements View.OnClickListener {
        private long plantId;

        public AddWifiModuleListener(long j) {
            this.plantId = j;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intent intent = new Intent(PlantListAdapter.this.activity, (Class<?>) AddDatalogActivity.class);
            intent.putExtra("plantId", this.plantId);
            PlantListAdapter.this.activity.startActivity(intent);
        }
    }
}