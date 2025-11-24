package com.nfcx.eg4.view.updateFirmware;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.CoroutineLiveDataKt;
import com.nfcx.eg4.R;
import com.nfcx.eg4.connect.LocalConnect;
import com.nfcx.eg4.connect.LocalConnectManager;
import com.nfcx.eg4.connect.ble.BleAction;
import com.nfcx.eg4.connect.ble.BluetoothLocalConnect;
import com.nfcx.eg4.global.Constants;
import com.nfcx.eg4.global.bean.inverter.Inverter;
import com.nfcx.eg4.protocol.tcp.DataFrameFactory;
import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;
import com.nfcx.eg4.tool.DonglePskUtil;
import com.nfcx.eg4.tool.ProTool;
import com.nfcx.eg4.tool.StatusBarUtil;
import com.nfcx.eg4.tool.Tool;
import com.nfcx.eg4.view.updateFirmware.bean.StandardUploadCheck;
import com.nfcx.eg4.view.updateFirmware.bean.UPDATE_STATUS;
import com.nfcx.eg4.view.updateFirmware.bean.UpdateFileCache;
import com.nfcx.eg4.view.updateFirmware.bean.UpdateProgressDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class UpdateExtFirmwareActivity extends Activity implements BleAction {
    public static final int MAX_WAIT_MILLS_SECONDS = 600000;
    public static final int MIN_WAIT_MILLS_SECONDS = 360000;
    private static final int REQUEST_WIFI_PERMISSION = 6;
    public static final int _12K_NO_POWER_OFF_MIN_WAIT_MILLS_SECONDS = 30000;
    public static UpdateExtFirmwareActivity instance;
    private static final Map<String, UpdateFileCache> updateFileCacheMap = new HashMap();
    private boolean isDarkTheme;
    private LocalConnect localConnect;
    private List<UpdateFileCache> matchedExtUpdateFileCaches;
    private boolean progressing;
    private StandardUploadCheck standardUploadCheck;
    private List<UpdateFileCache> updateFileCaches;
    private TextView updateFirmwareText;
    private TextView updateFirmwareTextView1;
    private TextView updateFirmwareTextView2;
    private TextView updateFirmwareTextView3;
    private Button updateNormalFileButton;
    private ProgressBar updateProgressBar1;
    private ProgressBar updateProgressBar2;
    private ProgressBar updateProgressBar3;
    private ProgressBar waitProgressBar;
    private TextView waitTimeTextView2;
    private TextView waitTimeTextView3;
    private int currentProgressIndex = -1;
    private final ProgressBar[] updateProgressArray = new ProgressBar[3];
    private int currentIndex = -1;
    private int lastUpdateIndex = -1;
    private final Runnable sendPackageRunnable = new AnonymousClass2();

    /* JADX INFO: Access modifiers changed from: private */
    public int getMinWaitMillsSeconds() {
        return _12K_NO_POWER_OFF_MIN_WAIT_MILLS_SECONDS;
    }

    @Override // com.nfcx.eg4.connect.ble.BleAction
    public void bleConnectClosed() {
    }

    @Override // com.nfcx.eg4.connect.ble.BleAction
    public void bleConnected() {
    }

    static /* synthetic */ int access$608(UpdateExtFirmwareActivity updateExtFirmwareActivity) {
        int i = updateExtFirmwareActivity.currentIndex;
        updateExtFirmwareActivity.currentIndex = i + 1;
        return i;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_update_ext_firmware);
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        instance = this;
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        getWindow().addFlags(128);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(Constants.LOCAL_CONNECT_TYPE);
        if (Constants.LOCAL_CONNECT_TYPE_BLUETOOTH.equals(stringExtra)) {
            LocalConnectManager.updateBluetoothLocalConnect(new BluetoothLocalConnect(this, (BluetoothDevice) intent.getParcelableExtra(Constants.KEY_BLE_DEVICE)));
            this.localConnect = LocalConnectManager.getLocalConnect(stringExtra);
        } else {
            this.localConnect = LocalConnectManager.getLocalConnect(stringExtra);
            this.localConnect.setDatalogSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 6));
            this.localConnect = LocalConnectManager.getLocalConnect(stringExtra);
        }
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m617x52e7d152(view);
            }
        });
        this.currentIndex = 0;
        this.currentProgressIndex = -1;
        this.updateFirmwareText = (TextView) findViewById(R.id.update_firmware);
        this.updateFirmwareTextView1 = (TextView) findViewById(R.id.update_firmware_textView1);
        this.waitTimeTextView2 = (TextView) findViewById(R.id.update_firmware_waitTime_textView2);
        this.updateFirmwareTextView2 = (TextView) findViewById(R.id.update_firmware_textView2);
        this.waitTimeTextView3 = (TextView) findViewById(R.id.update_firmware_waitTime_textView3);
        this.updateFirmwareTextView3 = (TextView) findViewById(R.id.update_firmware_textView3);
        this.updateProgressBar1 = (ProgressBar) findViewById(R.id.update_firmware1_updateProgressBar);
        this.updateProgressBar2 = (ProgressBar) findViewById(R.id.update_firmware2_updateProgressBar);
        this.updateProgressBar3 = (ProgressBar) findViewById(R.id.update_firmware3_updateProgressBar);
        this.updateNormalFileButton = (Button) findViewById(R.id.update_firmware_updateNormalFileButton);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.wait_progressBar);
        this.waitProgressBar = progressBar;
        progressBar.setVisibility(0);
        updateFileCacheMap.clear();
        this.updateFileCaches = DownloadFirmwareActivity.restoreUpdateFileCachesFromLocal(getExternalFilesDir("firmware"));
        System.out.println("Eg4 - Ext updateFileCaches.size = " + this.updateFileCaches.size());
        for (int i = 0; i < this.updateFileCaches.size(); i++) {
            UpdateFileCache updateFileCache = this.updateFileCaches.get(i);
            System.out.println("Eg4 - Ext updateFileCache.fileName = " + updateFileCache.getFileName());
            updateFileCacheMap.put(updateFileCache.getRecordId(), updateFileCache);
        }
        ProgressBar[] progressBarArr = this.updateProgressArray;
        progressBarArr[0] = this.updateProgressBar1;
        progressBarArr[1] = this.updateProgressBar2;
        progressBarArr[2] = this.updateProgressBar3;
        this.matchedExtUpdateFileCaches = new ArrayList();
        initDeviceConnect();
    }

    /* renamed from: lambda$onCreate$0$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m617x52e7d152(View view) {
        if (this.progressing) {
            this.progressing = false;
        }
        instance.finish();
    }

    private void initDeviceConnect() {
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException, NumberFormatException {
                UpdateExtFirmwareActivity.this.localConnect.setDatalogSn(DonglePskUtil.fetchDongleSnFromWifiSsid(UpdateExtFirmwareActivity.instance, 6));
                boolean zInitialize = UpdateExtFirmwareActivity.this.localConnect.initialize(true);
                System.out.println("Eg4 - initResult = " + zInitialize);
                if (zInitialize) {
                    if (!UpdateExtFirmwareActivity.this.localConnect.read03AndInitDevice()) {
                        for (int i = 0; i < 5 && UpdateExtFirmwareActivity.this.progressing; i++) {
                            Tool.sleep(1000L);
                        }
                        if (!UpdateExtFirmwareActivity.this.localConnect.read03AndInitDevice()) {
                            Tool.alertNotInUiThread(UpdateExtFirmwareActivity.this, "Read device information failed");
                            return;
                        }
                    }
                    Inverter inverter = UpdateExtFirmwareActivity.this.localConnect.getInverter();
                    System.out.println("Eg4 - fwCode = " + (inverter != null ? inverter.getFwCode() : null));
                    String strCheckFwCodeValid = UpdateExtFirmwareActivity.this.checkFwCodeValid(inverter);
                    if (Tool.isEmpty(strCheckFwCodeValid)) {
                        UpdateExtFirmwareActivity.this.standardUploadCheck = new StandardUploadCheck();
                        UpdateExtFirmwareActivity.this.standardUploadCheck.fillByInverter(inverter);
                        UpdateExtFirmwareActivity.this.checkExtUpdateInfoAtThread();
                        return;
                    }
                    Tool.alertNotInUiThread(UpdateExtFirmwareActivity.this, UpdateExtFirmwareActivity.this.getString(R.string.update_firmware_invalid) + ": " + strCheckFwCodeValid);
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkExtUpdateInfoAtThread() {
        if (DownloadFirmwareActivity.getCurrentBattFirmwareName() != null) {
            go2StandardUpdateActivity();
        }
        new Thread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() throws JSONException, InterruptedException {
                this.f$0.m614x11f01bb7();
            }
        }).start();
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$9$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m614x11f01bb7() throws JSONException, InterruptedException {
        if (this.currentIndex > 0) {
            this.localConnect.close();
            instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m606xe1d1c0bf();
                }
            });
            for (int i = 0; i < 120 && this.progressing; i++) {
                Tool.sleep(1000L);
            }
        }
        if (!this.localConnect.initialize(true)) {
            for (int i2 = 0; i2 < 60 && this.progressing; i2++) {
                Tool.sleep(1000L);
            }
            if (!this.localConnect.initialize(true)) {
                instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m607xe7d58c1e();
                    }
                });
                return;
            }
        }
        if (!this.localConnect.read03AndInitDevice()) {
            for (int i3 = 0; i3 < 5 && this.progressing; i3++) {
                Tool.sleep(1000L);
            }
            if (!this.localConnect.read03AndInitDevice()) {
                instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m608xedd9577d();
                    }
                });
                return;
            }
        }
        instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m609xf3dd22dc();
            }
        });
        StandardUploadCheck standardUploadCheck = new StandardUploadCheck();
        Inverter inverter = this.localConnect.getInverter();
        standardUploadCheck.setPhase(inverter.getPhase());
        standardUploadCheck.setDeviceType(inverter.getDeviceType());
        standardUploadCheck.setMachineType(Integer.valueOf(inverter.getMachineType()));
        boolean zCheckSkipExtendUpdateForType6 = checkSkipExtendUpdateForType6(inverter);
        System.out.println("Eg4 - inverter.needCheckHoldBeforeStandardUpdate() = " + inverter.needCheckHoldBeforeStandardUpdate());
        System.out.println("Eg4 - skipExtendUpdateForType6 = " + zCheckSkipExtendUpdateForType6);
        if (inverter.needCheckHoldBeforeStandardUpdate() && !zCheckSkipExtendUpdateForType6) {
            if (inverter.isType6() && ((inverter.getSlaveVersionValue() < 29 || inverter.getFwVersionValue() < 29) && !this.localConnect.functionControl(226, 10, false))) {
                Tool.alertNotInUiThread(this, "autoDisableFunctionFail");
                return;
            }
            try {
                JSONObject multiHold = this.localConnect.readMultiHold(244, 2);
                if (multiHold.getBoolean("success")) {
                    String string = multiHold.getString("valueFrame");
                    char cCharAt = string.charAt(0);
                    int iCount = ProTool.count(string.charAt(3), string.charAt(2));
                    System.out.println("Eg4 - startUpdateStandardFirmware - bootloaderVersion 244 = " + ((int) cCharAt));
                    System.out.println("Eg4 - startUpdateStandardFirmware - chipFlashSizeVersion 245 = " + iCount);
                    System.out.println("Eg4 - startUpdateStandardFirmware - inverter.getSlaveVersion() = " + inverter.getSlaveVersion());
                    standardUploadCheck.setBootloaderVersion(Integer.valueOf(cCharAt));
                    String stepFirmwareTypePrefix = getStepFirmwareTypePrefix(inverter);
                    if (Tool.isEmpty(stepFirmwareTypePrefix)) {
                        Tool.alertNotInUiThread(this, "no_Valid_FirmwareTypeMatch");
                        return;
                    }
                    standardUploadCheck.setStepFirmwareTypePrefix(stepFirmwareTypePrefix);
                    if ((cCharAt != 0 && cCharAt != 255) || (iCount != 0 && iCount != 255)) {
                        if (iCount == 256) {
                            Tool.alertNotInUiThread(this, "unSupportFlashVersion256");
                            return;
                        }
                        if (iCount == 512 && (cCharAt == 0 || cCharAt == 255)) {
                            standardUploadCheck.setNeedRunStep3(true);
                        } else if (iCount == 512 && cCharAt > 0 && cCharAt < 255 && inverter.getSlaveVersion() != null && inverter.getSlaveVersion().intValue() == 204) {
                            standardUploadCheck.setNeedRunStep4(true);
                        } else if (iCount != 512 || cCharAt <= 0 || cCharAt >= 255 || inverter.getSlaveVersion() == null || inverter.getSlaveVersion().intValue() == 204) {
                            Tool.alertNotInUiThread(this, "check244245Invalid");
                            return;
                        }
                    } else if (inverter.isType6()) {
                        System.out.println("Eg4 - startUpdateStandardFirmware - Step 1, (1): inverter.isType6() to step 2...");
                        standardUploadCheck.setNeedRunStep2(true);
                    } else if (inverter.isAllInOne()) {
                        System.out.println("Eg4 - startUpdateStandardFirmware - Step 1, (1): inverter.isAllInOne() to step 3...");
                        standardUploadCheck.setNeedRunStep3(true);
                    } else {
                        Tool.alertNotInUiThread(this, "check244245Invalid_1");
                        return;
                    }
                    System.out.println("Eg4 - standardUploadCheck.needRunStep2024() = " + standardUploadCheck.needRunStep2024());
                    System.out.println("Eg4 - standardUploadCheck.isNeedRunStep2() = " + standardUploadCheck.isNeedRunStep2());
                    System.out.println("Eg4 - standardUploadCheck.isNeedRunStep3() = " + standardUploadCheck.isNeedRunStep3());
                    System.out.println("Eg4 - standardUploadCheck.isNeedRunStep4() = " + standardUploadCheck.isNeedRunStep4());
                    if (standardUploadCheck.needRunStep2024()) {
                        String stepFirmwareTypePrefix2 = standardUploadCheck.getStepFirmwareTypePrefix();
                        if (standardUploadCheck.isNeedRunStep2()) {
                            UpdateFileCache updateFileCacheTryStrictMatchV1StandardUploadRecord = tryStrictMatchV1StandardUploadRecord(stepFirmwareTypePrefix2 + "2", 0);
                            if (updateFileCacheTryStrictMatchV1StandardUploadRecord == null) {
                                Tool.alertNotInUiThread(this, "no_" + stepFirmwareTypePrefix2 + "2_FirmwareMatch");
                                return;
                            }
                            Iterator<UpdateFileCache> it = this.matchedExtUpdateFileCaches.iterator();
                            while (it.hasNext()) {
                                if (it.next().getFileName().equals(updateFileCacheTryStrictMatchV1StandardUploadRecord.getFileName())) {
                                    Tool.alertNotInUiThread(this, updateFileCacheTryStrictMatchV1StandardUploadRecord.getFileName() + " not pass");
                                    return;
                                }
                            }
                            this.matchedExtUpdateFileCaches.add(updateFileCacheTryStrictMatchV1StandardUploadRecord);
                            final String fileName = updateFileCacheTryStrictMatchV1StandardUploadRecord.getFileName();
                            this.currentProgressIndex = 0;
                            System.out.println("Eg4 - check at Step 2: currentProgressIndex = " + this.currentProgressIndex + ", fileName = " + fileName);
                            instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m610xf9e0ee3b(fileName);
                                }
                            });
                        } else if (standardUploadCheck.isNeedRunStep3()) {
                            UpdateFileCache strictEqualV1StandardUploadRecordDescV2 = getStrictEqualV1StandardUploadRecordDescV2(stepFirmwareTypePrefix2 + "3", 204);
                            if (strictEqualV1StandardUploadRecordDescV2 == null) {
                                Tool.alertNotInUiThread(this, "no_" + stepFirmwareTypePrefix2 + "3_CC_FirmwareMatch");
                                return;
                            }
                            Iterator<UpdateFileCache> it2 = this.matchedExtUpdateFileCaches.iterator();
                            while (it2.hasNext()) {
                                if (it2.next().getFileName().equals(strictEqualV1StandardUploadRecordDescV2.getFileName())) {
                                    Tool.alertNotInUiThread(this, strictEqualV1StandardUploadRecordDescV2.getFileName() + " not pass");
                                    return;
                                }
                            }
                            this.matchedExtUpdateFileCaches.add(strictEqualV1StandardUploadRecordDescV2);
                            final String fileName2 = strictEqualV1StandardUploadRecordDescV2.getFileName();
                            this.currentProgressIndex = 1;
                            System.out.println("Eg4 - check at Step 3: currentProgressIndex = " + this.currentProgressIndex + ", fileName = " + fileName2);
                            instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m611xffe4b99a(fileName2);
                                }
                            });
                        } else if (standardUploadCheck.isNeedRunStep4()) {
                            if (standardUploadCheck.getBootloaderVersion() == null) {
                                Tool.alertNotInUiThread(this, "no_" + stepFirmwareTypePrefix2 + "4_BootloaderVersion_Empty");
                                return;
                            }
                            UpdateFileCache strictEqualV2StandardUploadRecordDescV1 = getStrictEqualV2StandardUploadRecordDescV1(stepFirmwareTypePrefix2 + "4", standardUploadCheck.getBootloaderVersion().intValue());
                            if (strictEqualV2StandardUploadRecordDescV1 == null) {
                                Tool.alertNotInUiThread(this, "no_" + stepFirmwareTypePrefix2 + "4_" + standardUploadCheck.getBootloaderVersion() + "_FirmwareMatch");
                                return;
                            }
                            Iterator<UpdateFileCache> it3 = this.matchedExtUpdateFileCaches.iterator();
                            while (it3.hasNext()) {
                                if (it3.next().getFileName().equals(strictEqualV2StandardUploadRecordDescV1.getFileName())) {
                                    Tool.alertNotInUiThread(this, strictEqualV2StandardUploadRecordDescV1.getFileName() + " not pass");
                                    return;
                                }
                            }
                            this.matchedExtUpdateFileCaches.add(strictEqualV2StandardUploadRecordDescV1);
                            final String fileName3 = strictEqualV2StandardUploadRecordDescV1.getFileName();
                            this.currentProgressIndex = 2;
                            System.out.println("Eg4 - check at Step 4: currentProgressIndex = " + this.currentProgressIndex + ", fileName = " + fileName3);
                            instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m612x5e884f9(fileName3);
                                }
                            });
                        } else {
                            standardUploadCheck.isNeedRunStep5();
                        }
                        instance.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m613xbec5058();
                            }
                        });
                        System.out.println("Eg4 - currentIndex = " + this.currentIndex + ", matchedExtUpdateFileCaches.size() = " + this.matchedExtUpdateFileCaches.size());
                        int i4 = this.currentIndex;
                        if (i4 <= 0 || i4 >= this.matchedExtUpdateFileCaches.size()) {
                            return;
                        }
                        System.out.println("Eg4 - sendPackageRunnable start...");
                        new Thread(this.sendPackageRunnable).start();
                        return;
                    }
                    doneAndGo2NextUpdateActivity();
                    return;
                }
                Tool.alertNotInUiThread(this, "read244245Fail");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Tool.alertNotInUiThread(this, "Unexpected Exception: " + e.getMessage());
                return;
            }
        }
        doneAndGo2NextUpdateActivity();
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$1$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m606xe1d1c0bf() {
        this.waitProgressBar.setVisibility(0);
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$2$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m607xe7d58c1e() {
        this.waitProgressBar.setVisibility(4);
        Tool.alert(this, R.string.local_regular_set_toast_tcp_init_fail);
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$3$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m608xedd9577d() {
        this.waitProgressBar.setVisibility(4);
        Tool.alert(this, "Read device information failed");
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$4$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m609xf3dd22dc() {
        this.waitProgressBar.setVisibility(4);
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$5$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m610xf9e0ee3b(String str) {
        this.updateFirmwareTextView1.setText(str);
        this.updateFirmwareTextView1.setVisibility(0);
        this.updateProgressBar1.setVisibility(0);
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$6$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m611xffe4b99a(String str) {
        this.updateFirmwareTextView2.setText(str);
        this.updateFirmwareTextView2.setVisibility(0);
        this.waitTimeTextView2.setVisibility(0);
        this.updateProgressBar2.setVisibility(0);
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$7$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m612x5e884f9(String str) {
        this.updateFirmwareTextView3.setText(str);
        this.updateFirmwareTextView3.setVisibility(0);
        this.waitTimeTextView3.setVisibility(0);
        this.updateProgressBar3.setVisibility(0);
    }

    /* renamed from: lambda$checkExtUpdateInfoAtThread$8$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m613xbec5058() {
        this.updateNormalFileButton.setEnabled(true);
    }

    private void doneAndGo2NextUpdateActivity() {
        System.out.println("Eg4 before done - currentIndex = " + this.currentIndex + ", currentProgressIndex = " + this.currentProgressIndex);
        runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m616x7636ecf0();
            }
        });
    }

    /* renamed from: lambda$doneAndGo2NextUpdateActivity$10$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m616x7636ecf0() {
        if (this.currentIndex > 0 || this.currentProgressIndex == 2) {
            showResultDialog(true);
        } else {
            go2StandardUpdateActivity();
        }
    }

    private String getStepFirmwareTypePrefix(Inverter inverter) {
        if (inverter.isType6()) {
            return "EXT";
        }
        if (inverter.is7_10KDevice()) {
            return "GST";
        }
        if (inverter.isAllInOne()) {
            return "AIO";
        }
        return null;
    }

    private void go2StandardUpdateActivity() {
        Intent intent = new Intent(this, (Class<?>) UpdateFirmwareActivity.class);
        intent.putExtra(Constants.LOCAL_CONNECT_TYPE, this.localConnect.getConnectType());
        startActivity(intent);
        finish();
    }

    private boolean checkSkipExtendUpdateForType6(Inverter inverter) {
        UpdateFileCache updateFileCacheTryMatchV2StandardUploadRecord;
        UpdateFileCache updateFileCacheTryMatchV1StandardUploadRecord;
        boolean z = false;
        if (inverter.isType6()) {
            String serialNum = inverter.getSerialNum();
            StandardUploadCheck standardUploadCheck = new StandardUploadCheck();
            standardUploadCheck.fillByInverter(inverter);
            if (standardUploadCheck.getV1() != null && (updateFileCacheTryMatchV1StandardUploadRecord = tryMatchV1StandardUploadRecord(inverter)) != null) {
                standardUploadCheck.setPcs1UpdateMatch(true);
                standardUploadCheck.setLastV1(standardUploadCheck.check12KNoPowerOffUpdateAllowed() ? updateFileCacheTryMatchV1StandardUploadRecord.getV1() : updateFileCacheTryMatchV1StandardUploadRecord.getV2());
            }
            if (standardUploadCheck.getV2() != null && (updateFileCacheTryMatchV2StandardUploadRecord = tryMatchV2StandardUploadRecord(inverter)) != null) {
                standardUploadCheck.setPcs2UpdateMatch(true);
                standardUploadCheck.setLastV2(standardUploadCheck.check12KNoPowerOffUpdateAllowed() ? updateFileCacheTryMatchV2StandardUploadRecord.getV2() : updateFileCacheTryMatchV2StandardUploadRecord.getV1());
            }
            Integer m3Version = standardUploadCheck.getM3Version();
            boolean z2 = m3Version != null && m3Version.intValue() < 29;
            boolean z3 = !standardUploadCheck.check12KNoPowerOffUpdateAllowed() ? standardUploadCheck.getLastV2() == null || standardUploadCheck.getLastV2().intValue() < 29 : standardUploadCheck.getLastV1() == null || standardUploadCheck.getLastV1().intValue() < 29;
            System.out.println("Eg4 - startUpdateStandardFirmware before ext update check: serialNum = " + serialNum + ", deviceType = " + inverter.getDeviceType() + ", v1 = " + standardUploadCheck.getV1() + ", v2 = " + standardUploadCheck.getV2());
            System.out.println("Eg4 - startUpdateStandardFirmware before ext update check: serialNum = " + serialNum + ", deviceType = " + inverter.getDeviceType() + ", lastV1 = " + standardUploadCheck.getLastV1() + ", lastV2 = " + standardUploadCheck.getLastV2());
            if ((!z2 || !z3) && (m3Version == null || m3Version.intValue() != 204)) {
                z = true;
            }
            System.out.println("Eg4 - startUpdateStandardFirmware before ext update check: serialNum = " + serialNum + ", deviceType = " + inverter.getDeviceType() + ", inverterM3LowerThen1D = " + z2 + ", targetM31DorHigher = " + z3 + ", skipExtendUpdateForType6 = " + z);
        }
        return z;
    }

    private UpdateFileCache tryMatchV1StandardUploadRecord(Inverter inverter) {
        String standard = this.standardUploadCheck.getStandard();
        if (Tool.isEmpty(standard) || this.standardUploadCheck.getV1() == null) {
            return null;
        }
        if (inverter.isOffGrid()) {
            if ("cbaa".equals(standard)) {
                standard = "cBaa";
            } else if ("CBAA".equals(standard)) {
                standard = "cBAA";
            }
            return tryStrictMatchV2StandardUploadRecord(standard, this.standardUploadCheck.getV1().intValue());
        }
        if (inverter.isType6()) {
            if ("EAAB".equals(standard) || "FAAA".equals(standard)) {
                standard = "FAAB";
            } else if ("eAAB".equals(standard) || "fAAA".equals(standard)) {
                standard = "fAAB";
            }
            if (this.standardUploadCheck.check12KNoPowerOffUpdateAllowed()) {
                return tryMatchV1StandardUploadRecord(standard, this.standardUploadCheck.getV1().intValue());
            }
            return tryStrictMatchV2StandardUploadRecord(standard, this.standardUploadCheck.getV1().intValue());
        }
        if (inverter.isTrip12K() || inverter.isSna12K()) {
            return tryStrictMatchV1StandardUploadRecord(standard, this.standardUploadCheck.getV1().intValue());
        }
        return tryMatchV1StandardUploadRecord(standard, this.standardUploadCheck.getV1().intValue());
    }

    private UpdateFileCache tryMatchV2StandardUploadRecord(Inverter inverter) {
        String standard = this.standardUploadCheck.getStandard();
        if (Tool.isEmpty(standard) || this.standardUploadCheck.getV2() == null) {
            return null;
        }
        if (inverter.isLsp() || inverter.isTrip12K() || inverter.isSna12K()) {
            return tryMatchV2StandardUploadRecord(standard, this.standardUploadCheck.getV2().intValue());
        }
        if (inverter.isOffGrid()) {
            return tryMatchV1StandardUploadRecord(standard, this.standardUploadCheck.getV2().intValue());
        }
        if (inverter.isType6()) {
            if ("EAAB".equals(standard) || "FAAA".equals(standard)) {
                standard = "FAAB";
            } else if ("eAAB".equals(standard) || "fAAA".equals(standard)) {
                standard = "fAAB";
            }
            if (this.standardUploadCheck.check12KNoPowerOffUpdateAllowed()) {
                return tryStrictMatchV2StandardUploadRecord(standard, this.standardUploadCheck.getV2().intValue());
            }
            return tryMatchV1StandardUploadRecord(standard, this.standardUploadCheck.getV2().intValue());
        }
        return tryStrictMatchV2StandardUploadRecord(standard, this.standardUploadCheck.getV2().intValue());
    }

    private UpdateFileCache tryMatchV3StandardUploadRecord(Inverter inverter) {
        String standard = this.standardUploadCheck.getStandard();
        if (Tool.isEmpty(standard) || this.standardUploadCheck.getV3() == null) {
            return null;
        }
        if ("cbba".equals(standard) || "ccaa".equalsIgnoreCase(standard)) {
            return tryStrictMatchV3StandardUploadRecord("CBAA", this.standardUploadCheck.getV3().intValue());
        }
        return tryMatchV3StandardUploadRecord(standard, this.standardUploadCheck.getV3().intValue());
    }

    private UpdateFileCache tryMatchV1StandardUploadRecord(String str, int i) {
        return tryMatchStandardUploadRecord(str, i, 1, false);
    }

    private UpdateFileCache tryStrictMatchV1StandardUploadRecord(String str, int i) {
        return tryMatchStandardUploadRecord(str, i, 1, true);
    }

    private UpdateFileCache tryMatchV2StandardUploadRecord(String str, int i) {
        return tryMatchStandardUploadRecord(str, i, 2, false);
    }

    private UpdateFileCache tryStrictMatchV2StandardUploadRecord(String str, int i) {
        return tryMatchStandardUploadRecord(str, i, 2, true);
    }

    public UpdateFileCache tryMatchV3StandardUploadRecord(String str, int i) {
        return tryMatchStandardUploadRecord(str, i, 3, false);
    }

    public UpdateFileCache tryStrictMatchV3StandardUploadRecord(String str, int i) {
        return tryMatchStandardUploadRecord(str, i, 3, true);
    }

    private UpdateFileCache getStrictEqualV1StandardUploadRecordDescV2(String str, int i) {
        UpdateFileCache updateFileCache = null;
        int iIntValue = 0;
        for (UpdateFileCache updateFileCache2 : this.updateFileCaches) {
            if (str.equals(updateFileCache2.getStandard()) && updateFileCache2.getV1() != null && updateFileCache2.getV1().intValue() == i && updateFileCache2.getV2() != null && updateFileCache2.getV2().intValue() > iIntValue) {
                iIntValue = updateFileCache2.getV2().intValue();
                updateFileCache = updateFileCache2;
            }
        }
        return updateFileCache;
    }

    private UpdateFileCache getStrictEqualV2StandardUploadRecordDescV1(String str, int i) {
        UpdateFileCache updateFileCache = null;
        int iIntValue = 0;
        for (UpdateFileCache updateFileCache2 : this.updateFileCaches) {
            if (str.equals(updateFileCache2.getStandard()) && updateFileCache2.getV2() != null && updateFileCache2.getV2().intValue() == i && updateFileCache2.getV1() != null && updateFileCache2.getV1().intValue() > iIntValue) {
                iIntValue = updateFileCache2.getV1().intValue();
                updateFileCache = updateFileCache2;
            }
        }
        return updateFileCache;
    }

    private UpdateFileCache tryMatchStandardUploadRecord(String str, int i, int i2, boolean z) {
        UpdateFileCache updateFileCache = null;
        for (UpdateFileCache updateFileCache2 : this.updateFileCaches) {
            if (str.equals(updateFileCache2.getStandard()) || (!z && str.equalsIgnoreCase(updateFileCache2.getStandard()))) {
                if (updateFileCache2.getV(i2) != null && updateFileCache2.getV(i2).intValue() > i && (updateFileCache == null || updateFileCache.getV(i2) == null || updateFileCache.getV(i2).intValue() < updateFileCache2.getV(i2).intValue())) {
                    updateFileCache = updateFileCache2;
                }
            }
        }
        return updateFileCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String checkFwCodeValid(Inverter inverter) throws NumberFormatException {
        String fwCode = inverter.getFwCode();
        if (Tool.isEmpty(fwCode) || fwCode.indexOf("-") != 4) {
            return "inverterFwCodeInvalid";
        }
        if (!inverter.isSnaSeries() && fwCode.length() != 9) {
            return "inverterFwCodeInvalid";
        }
        if (inverter.isSnaSeries() && fwCode.length() != 11) {
            return "inverterFwCodeInvalid";
        }
        if (!inverter.isType6()) {
            return null;
        }
        if (fwCode.startsWith("fAAA") || fwCode.startsWith("FAAA")) {
            int i = Integer.parseInt(fwCode.substring(5, 7), 16);
            int i2 = Integer.parseInt(fwCode.substring(7, 9), 16);
            if (i < 7 || i2 < 7) {
                return "inverter_8_12K_FwCodeInvalid";
            }
            return null;
        }
        if (fwCode.startsWith("EAAB") || fwCode.startsWith("FAAB") || fwCode.startsWith("eAAB") || fwCode.startsWith("fAAB")) {
            return null;
        }
        return "inverter_8_12K_FwCodeInvalid";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showResultDialog(final boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.local_button_update_firmware);
        builder.setMessage(z ? R.string.local_page_update_result_success : R.string.local_page_update_result_failed);
        builder.setPositiveButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m618x2c5b4f1(z, dialogInterface, i);
            }
        });
        builder.show();
    }

    /* renamed from: lambda$showResultDialog$11$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m618x2c5b4f1(boolean z, DialogInterface dialogInterface, int i) {
        if (z) {
            go2StandardUpdateActivity();
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.progressing = false;
    }

    /* renamed from: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            boolean z;
            boolean z2;
            UpdateExtFirmwareActivity.this.progressing = true;
            System.out.println("Eg4 - sendPackageRunnable start... inner lastUpdateIndex = " + UpdateExtFirmwareActivity.this.lastUpdateIndex + ", currentIndex = " + UpdateExtFirmwareActivity.this.currentIndex);
            if (UpdateExtFirmwareActivity.this.currentIndex > 0 && UpdateExtFirmwareActivity.this.lastUpdateIndex < UpdateExtFirmwareActivity.this.currentIndex) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                boolean z3 = false;
                int i = 0;
                for (long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis; jCurrentTimeMillis2 < 600000 && !z3 && UpdateExtFirmwareActivity.this.progressing; jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis) {
                    Tool.sleep(1000L);
                    int i2 = (int) ((600000 - jCurrentTimeMillis2) / 1000);
                    final String str = "00:" + ProTool.fillZeros(String.valueOf(i2 / 60), 2) + ":" + ProTool.fillZeros(String.valueOf(i2 % 60), 2);
                    UpdateExtFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m620xfd9a7a6a(str);
                        }
                    });
                    if (jCurrentTimeMillis2 > UpdateExtFirmwareActivity.this.getMinWaitMillsSeconds()) {
                        if (i == 0) {
                            String strSendCommand = UpdateExtFirmwareActivity.this.localConnect.sendCommand("read_03_1", DataFrameFactory.createReadMultiHoldDataFrame(UpdateExtFirmwareActivity.this.localConnect.getTcpProtocol(), UpdateExtFirmwareActivity.this.localConnect.getDatalogSn(), 0, 40));
                            System.out.println("Eg4 - Ext wait 03 >>> result = " + strSendCommand);
                            if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 60) {
                                z3 = true;
                            }
                        }
                        i++;
                        if (i > 10) {
                            i = 0;
                        }
                    }
                }
                UpdateExtFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m621x91d8ea09();
                    }
                });
            }
            UpdateExtFirmwareActivity updateExtFirmwareActivity = UpdateExtFirmwareActivity.this;
            updateExtFirmwareActivity.lastUpdateIndex = updateExtFirmwareActivity.currentIndex;
            final UpdateProgressDetail updateProgressDetailCreateUpdateProgressDetail = UpdateExtFirmwareActivity.this.createUpdateProgressDetail();
            System.out.println("Eg4 - sendPackageRunnable start... inner progressing = " + UpdateExtFirmwareActivity.this.progressing + ", hasNotFinishedUpdating = true");
            boolean z4 = true;
            boolean z5 = false;
            while (UpdateExtFirmwareActivity.this.progressing && z4) {
                final Map<Integer, String> firmware = ((UpdateFileCache) UpdateExtFirmwareActivity.this.matchedExtUpdateFileCaches.get(UpdateExtFirmwareActivity.this.currentIndex)).getFirmware();
                System.out.println("Eg4 - sendPackageRunnable start... inner updateProgressDetail.getUpdateStatus() = " + updateProgressDetailCreateUpdateProgressDetail.getUpdateStatus());
                int i3 = AnonymousClass3.$SwitchMap$com$nfcx$eg4$view$updateFirmware$bean$UPDATE_STATUS[updateProgressDetailCreateUpdateProgressDetail.getUpdateStatus().ordinal()];
                if (i3 != 1) {
                    if (i3 == 2) {
                        if (updateProgressDetailCreateUpdateProgressDetail.getLastTimeSendPackage() > 1) {
                            if (System.currentTimeMillis() - updateProgressDetailCreateUpdateProgressDetail.getLastTimeSendPackage() < CoroutineLiveDataKt.DEFAULT_TIMEOUT) {
                                Tool.sleep(500L);
                                z4 = true;
                            } else {
                                updateProgressDetailCreateUpdateProgressDetail.setErrorCount(updateProgressDetailCreateUpdateProgressDetail.getErrorCount() + 1);
                            }
                        }
                        if ((!updateProgressDetailCreateUpdateProgressDetail.isSendUpdateStart_0x21() && updateProgressDetailCreateUpdateProgressDetail.getErrorCount() >= 4) || updateProgressDetailCreateUpdateProgressDetail.getErrorCount() >= 10) {
                            updateProgressDetailCreateUpdateProgressDetail.setUpdateStatus(UPDATE_STATUS.FAILURE);
                            UpdateExtFirmwareActivity.this.localConnect.close();
                            UpdateExtFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$2$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m622x261759a8();
                                }
                            });
                        } else {
                            updateProgressDetailCreateUpdateProgressDetail.setLastTimeSendPackage(System.currentTimeMillis());
                            UpdateExtFirmwareActivity.this.sendUpdateDataFrame(updateProgressDetailCreateUpdateProgressDetail);
                            z = z5;
                            z2 = true;
                        }
                    }
                    z = z5;
                    z2 = false;
                } else if (updateProgressDetailCreateUpdateProgressDetail.getPackageIndex() > firmware.size() && updateProgressDetailCreateUpdateProgressDetail.isSendUpdateReset_0x23()) {
                    updateProgressDetailCreateUpdateProgressDetail.setUpdateStatus(UPDATE_STATUS.SUCCESS);
                    System.out.println("Eg4升级成功");
                    z = true;
                    z2 = false;
                } else {
                    if (!updateProgressDetailCreateUpdateProgressDetail.isSendUpdateStart_0x21()) {
                        updateProgressDetailCreateUpdateProgressDetail.setSendUpdateStart_0x21(true);
                    } else if (updateProgressDetailCreateUpdateProgressDetail.getPackageIndex() <= firmware.size()) {
                        updateProgressDetailCreateUpdateProgressDetail.setPackageIndex(updateProgressDetailCreateUpdateProgressDetail.getPackageIndex() + 1);
                    }
                    updateProgressDetailCreateUpdateProgressDetail.setErrorCount(0);
                    updateProgressDetailCreateUpdateProgressDetail.setUpdateStatus(UPDATE_STATUS.READY);
                    updateProgressDetailCreateUpdateProgressDetail.setLastTimeSendPackage(System.currentTimeMillis());
                    UpdateExtFirmwareActivity.this.sendUpdateDataFrame(updateProgressDetailCreateUpdateProgressDetail);
                    z = z5;
                    z2 = true;
                }
                UpdateExtFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$2$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m623xba55c947(updateProgressDetailCreateUpdateProgressDetail, firmware);
                    }
                });
                Tool.sleep(100L);
                z4 = z2;
                z5 = z;
            }
            if (!z5) {
                UpdateExtFirmwareActivity.this.progressing = false;
                UpdateExtFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$2$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m624x4e9438e6();
                    }
                });
            } else {
                UpdateExtFirmwareActivity.access$608(UpdateExtFirmwareActivity.this);
                UpdateExtFirmwareActivity.this.checkExtUpdateInfoAtThread();
            }
        }

        /* renamed from: lambda$run$0$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity$2, reason: not valid java name */
        /* synthetic */ void m620xfd9a7a6a(String str) {
            if (UpdateExtFirmwareActivity.this.currentProgressIndex == 1) {
                UpdateExtFirmwareActivity.this.waitTimeTextView2.setText(UpdateExtFirmwareActivity.this.getString(R.string.login_download_dialog_text) + " " + str);
            } else if (UpdateExtFirmwareActivity.this.currentProgressIndex == 2) {
                UpdateExtFirmwareActivity.this.waitTimeTextView3.setText(UpdateExtFirmwareActivity.this.getString(R.string.login_download_dialog_text) + " " + str);
            }
        }

        /* renamed from: lambda$run$1$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity$2, reason: not valid java name */
        /* synthetic */ void m621x91d8ea09() {
            UpdateExtFirmwareActivity.this.waitTimeTextView2.setVisibility(4);
            UpdateExtFirmwareActivity.this.waitTimeTextView3.setVisibility(4);
        }

        /* renamed from: lambda$run$2$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity$2, reason: not valid java name */
        /* synthetic */ void m622x261759a8() {
            UpdateExtFirmwareActivity.this.showResultDialog(false);
        }

        /* renamed from: lambda$run$3$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity$2, reason: not valid java name */
        /* synthetic */ void m623xba55c947(UpdateProgressDetail updateProgressDetail, Map map) {
            UpdateExtFirmwareActivity.this.updateProgressArray[UpdateExtFirmwareActivity.this.currentProgressIndex].setProgress((updateProgressDetail.getCurrentProgress() * 100) / (map.size() + 2));
        }

        /* renamed from: lambda$run$4$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity$2, reason: not valid java name */
        /* synthetic */ void m624x4e9438e6() {
            UpdateExtFirmwareActivity.this.updateNormalFileButton.setEnabled(true);
        }
    }

    /* renamed from: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$nfcx$eg4$view$updateFirmware$bean$UPDATE_STATUS;

        static {
            int[] iArr = new int[UPDATE_STATUS.values().length];
            $SwitchMap$com$nfcx$eg4$view$updateFirmware$bean$UPDATE_STATUS = iArr;
            try {
                iArr[UPDATE_STATUS.WAITING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$nfcx$eg4$view$updateFirmware$bean$UPDATE_STATUS[UPDATE_STATUS.READY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateDataFrame(UpdateProgressDetail updateProgressDetail) {
        DataFrame dataFrameCreateUpdateResetDataFrame;
        UpdateFileCache updateFileCache = this.matchedExtUpdateFileCaches.get(this.currentIndex);
        Map<Integer, String> firmware = updateFileCache.getFirmware();
        int size = firmware.size();
        int i = 1;
        if (!updateProgressDetail.isSendUpdateStart_0x21()) {
            DataFrame dataFrameCreateUpdatePrepareDataFrame = DataFrameFactory.createUpdatePrepareDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), updateFileCache.getTailEncoded(), size, updateFileCache.getCrc32());
            if (dataFrameCreateUpdatePrepareDataFrame != null) {
                String strSendCommand = this.localConnect.sendCommand("tcpUpdate_Prepare", dataFrameCreateUpdatePrepareDataFrame);
                if (Tool.isEmpty(strSendCommand)) {
                    return;
                }
                int iCount = ProTool.count(strSendCommand.charAt(34), strSendCommand.charAt(33));
                updateProgressDetail.setUpdateStatus(UPDATE_STATUS.WAITING);
                if (iCount > 0 && iCount < size) {
                    i = iCount;
                }
                updateProgressDetail.setPackageIndex(i);
                return;
            }
            return;
        }
        if (updateProgressDetail.isSendUpdateStart_0x21() && updateProgressDetail.getPackageIndex() <= size) {
            int packageIndex = updateProgressDetail.getPackageIndex();
            System.out.println("Eg4 - ble - SEND 0x22 - " + packageIndex);
            DataFrame dataFrameCreateUpdateSendDataDataFrame = DataFrameFactory.createUpdateSendDataDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), packageIndex, updateFileCache.getFileType(), updateFileCache.getPhysicalAddr().get(Integer.valueOf(packageIndex)).intValue(), firmware.get(Integer.valueOf(packageIndex)));
            if (dataFrameCreateUpdateSendDataDataFrame == null || Tool.isEmpty(this.localConnect.sendCommand("tcpUpdate_Send_" + packageIndex, dataFrameCreateUpdateSendDataDataFrame))) {
                return;
            }
            updateProgressDetail.setUpdateStatus(UPDATE_STATUS.WAITING);
            return;
        }
        if (updateProgressDetail.getPackageIndex() <= size || updateProgressDetail.isSendUpdateReset_0x23() || (dataFrameCreateUpdateResetDataFrame = DataFrameFactory.createUpdateResetDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), updateFileCache.getFileType(), size, updateFileCache.getCrc32())) == null) {
            return;
        }
        this.localConnect.sendCommand("tcpUpdate_Reset", dataFrameCreateUpdateResetDataFrame);
        updateProgressDetail.setSendUpdateReset_0x23(true);
        updateProgressDetail.setUpdateStatus(UPDATE_STATUS.WAITING);
    }

    private String generateFirmwareUpdateVerifyCode(long j) {
        String strValueOf = String.valueOf(j);
        return strValueOf.length() > 6 ? strValueOf.substring(strValueOf.length() - 6) : strValueOf;
    }

    public void clickStartUpdateButton(View view) {
        List<UpdateFileCache> list = this.matchedExtUpdateFileCaches;
        if (list == null || list.size() < 1) {
            Tool.alert(this, R.string.update_firmware_no_match_standard_firmware);
        } else {
            new Thread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m615x3cf40e23();
                }
            }).start();
        }
    }

    /* renamed from: lambda$clickStartUpdateButton$12$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m615x3cf40e23() {
        if (startSendFirmwarePackage() == 14) {
            Tool.alertNotInUiThread(this, R.string.local_regular_set_toast_tcp_init_fail);
        }
    }

    private synchronized int startSendFirmwarePackage() {
        if (!this.localConnect.initialize(true)) {
            return 14;
        }
        if (Tool.isEmpty(this.localConnect.getDatalogSn())) {
            this.localConnect.setDatalogSn(Constants.DEFAULT_DATALOG_SN);
        }
        if (this.localConnect.getInverter() == null && !this.localConnect.read03AndInitDevice()) {
            return 16;
        }
        if (!this.progressing) {
            runOnUiThread(new Runnable() { // from class: com.nfcx.eg4.view.updateFirmware.UpdateExtFirmwareActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m619x552f3cae();
                }
            });
            new Thread(this.sendPackageRunnable).start();
        }
        return 0;
    }

    /* renamed from: lambda$startSendFirmwarePackage$13$com-nfcx-eg4-view-updateFirmware-UpdateExtFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m619x552f3cae() {
        this.updateNormalFileButton.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UpdateProgressDetail createUpdateProgressDetail() {
        UpdateProgressDetail updateProgressDetail = new UpdateProgressDetail();
        updateProgressDetail.setInverterSn(this.localConnect.getInverter().getSerialNum());
        updateProgressDetail.setDatalogSn(this.localConnect.getDatalogSn());
        updateProgressDetail.setPackageIndex(1);
        updateProgressDetail.setUpdateStatus(UPDATE_STATUS.READY);
        return updateProgressDetail;
    }
}