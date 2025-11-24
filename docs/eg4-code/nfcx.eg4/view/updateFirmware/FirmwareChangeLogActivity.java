package com.nfcx.eg4.view.updateFirmware;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.messaging.Constants;
import com.nfcx.eg4.R;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.bean.property.Property;
import com.nfcx.eg4.global.firmware.FIRMWARE_DEVICE_TYPE;
import com.nfcx.eg4.tool.HttpTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.updateFirmware.adapter.ChangelogAdapter;
import com.nfcx.eg4.view.updateFirmware.item.ChangelogItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class FirmwareChangeLogActivity extends Activity {
    List<ChangelogItem> changelogItems = new ArrayList();
    private RecyclerView changelogRecyclerView;
    private Spinner firmwareTypeSpinner;
    private TextView firmwareTypeTextView;
    private boolean fromDownloadFirmware;
    private boolean fromWifiConnect;
    private boolean isDarkTheme;
    private Property selectedFileTypeProperty;
    private int selectedItemPosition;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_firmware_changelog_main);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        Intent intent = getIntent();
        if (intent != null) {
            this.selectedItemPosition = intent.getIntExtra("selectedItemPosition", 0);
        }
        ChangelogAdapter changelogAdapter = new ChangelogAdapter(this.changelogItems);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.firmware_changelogRecyclerView);
        this.changelogRecyclerView = recyclerView;
        recyclerView.setAdapter(changelogAdapter);
        this.firmwareTypeSpinner = (Spinner) findViewById(R.id.firmware_firmwareTypeSpinner);
        this.firmwareTypeTextView = (TextView) findViewById(R.id.firmware_firmwareTypeText);
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FirmwareChangeLogActivity.this.startActivity(new Intent(FirmwareChangeLogActivity.this, (Class<?>) DownloadFirmwareActivity.class));
                FirmwareChangeLogActivity.this.finish();
            }
        });
    }

    private void fromDownloadFirmware() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constants.firmwareTypeProperties);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.firmwareTypeSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.firmwareTypeSpinner.setSelection(this.selectedItemPosition);
        this.firmwareTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                FirmwareChangeLogActivity firmwareChangeLogActivity = FirmwareChangeLogActivity.this;
                firmwareChangeLogActivity.selectedFileTypeProperty = (Property) firmwareChangeLogActivity.firmwareTypeSpinner.getSelectedItem();
                FirmwareChangeLogActivity firmwareChangeLogActivity2 = FirmwareChangeLogActivity.this;
                firmwareChangeLogActivity2.loadFirmwareChangeLog(firmwareChangeLogActivity2.selectedFileTypeProperty.getName());
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
                FirmwareChangeLogActivity.this.selectedFileTypeProperty = null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadFirmwareChangeLog(final String str) {
        this.changelogItems.clear();
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m605xf7f18afa(str);
            }
        }).start();
    }

    /* renamed from: lambda$loadFirmwareChangeLog$0$com-nfcx-eg4-view-updateFirmware-FirmwareChangeLogActivity, reason: not valid java name */
    /* synthetic */ void m605xf7f18afa(String str) {
        Runnable runnable;
        try {
            try {
                HashMap map = new HashMap();
                System.out.println("Eg4 - " + FIRMWARE_DEVICE_TYPE.getEnumWithoutExt(str));
                map.put("platform", "EG4");
                map.put("firmwareDeviceType", FIRMWARE_DEVICE_TYPE.getEnumWithoutExt(str));
                JSONObject jSONObjectPostJson = HttpTool.postJson("https://res.solarcloudsystem.com:8443/resource/findAllTypeInfo", map);
                if (jSONObjectPostJson != null && jSONObjectPostJson.getInt("code") == 200) {
                    JSONArray jSONArray = jSONObjectPostJson.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        ChangelogItem changelogItem = new ChangelogItem();
                        String string = jSONObject.getString("fwCode");
                        if (string.startsWith("EG4_")) {
                            string = string.substring(4);
                        }
                        changelogItem.setFwCode(string);
                        changelogItem.setCreateTime(jSONObject.getString("createTime").substring(0, 10));
                        String string2 = jSONObject.getString("description");
                        changelogItem.setDescription(!Tool.isEmpty(string2) ? (string2.trim() + "\n").replace("\n", "\n\n") : "");
                        this.changelogItems.add(changelogItem);
                    }
                }
                runnable = new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity.3
                    @Override // java.lang.Runnable
                    public void run() {
                        FirmwareChangeLogActivity.this.changelogRecyclerView.setLayoutManager(new LinearLayoutManager(FirmwareChangeLogActivity.this.changelogRecyclerView.getContext()));
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
                runnable = new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity.3
                    @Override // java.lang.Runnable
                    public void run() {
                        FirmwareChangeLogActivity.this.changelogRecyclerView.setLayoutManager(new LinearLayoutManager(FirmwareChangeLogActivity.this.changelogRecyclerView.getContext()));
                    }
                };
            }
            runOnUiThread(runnable);
        } catch (Throwable th) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.FirmwareChangeLogActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    FirmwareChangeLogActivity.this.changelogRecyclerView.setLayoutManager(new LinearLayoutManager(FirmwareChangeLogActivity.this.changelogRecyclerView.getContext()));
                }
            });
            throw th;
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            boolean booleanExtra = intent.getBooleanExtra("fromWifiConnect", false);
            this.fromWifiConnect = booleanExtra;
            if (booleanExtra) {
                this.firmwareTypeSpinner.setVisibility(4);
                this.firmwareTypeTextView.setVisibility(0);
                this.firmwareTypeTextView.setText("E-WiFi Dongle");
                loadFirmwareChangeLog("E-WiFi Dongle");
            }
            boolean booleanExtra2 = intent.getBooleanExtra("fromDownloadFirmware", false);
            this.fromDownloadFirmware = booleanExtra2;
            if (booleanExtra2) {
                this.selectedItemPosition = intent.getIntExtra("selectedItemPosition", 0);
                fromDownloadFirmware();
            }
        }
    }
}