package com.lux.luxcloud.view.updateFirmware;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lux.luxcloud.R;
import com.lux.luxcloud.connect.LocalConnect;
import com.lux.luxcloud.connect.LocalConnectManager;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.global.bean.inverter.Inverter;
import com.lux.luxcloud.protocol.tcp.DataFrameFactory;
import com.lux.luxcloud.protocol.tcp.dataframe.DataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.lux.luxcloud.tool.DonglePskUtil;
import com.lux.luxcloud.tool.ProTool;
import com.lux.luxcloud.tool.StatusBarUtil;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.updateFirmware.bean.StandardUploadCheck;
import com.lux.luxcloud.view.updateFirmware.bean.UPDATE_STATUS;
import com.lux.luxcloud.view.updateFirmware.bean.UpdateFileCache;
import com.lux.luxcloud.view.updateFirmware.bean.UpdateProgressDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class UpdateFirmwareActivity extends Activity {
    public static final int MAX_WAIT_MILLS_SECONDS = 600000;
    public static final int MIN_WAIT_MILLS_SECONDS = 360000;
    private static final int REQUEST_WIFI_PERMISSION = 10;
    public static final int _12K_NO_POWER_OFF_MIN_WAIT_MILLS_SECONDS = 30000;
    public static UpdateFirmwareActivity instance;
    private static final Map<String, UpdateFileCache> updateFileCacheMap = new HashMap();
    private boolean isDarkTheme;
    private UpdateProgressDetail lastUpdateProgressDetail;
    private LocalConnect localConnect;
    private List<UpdateFileCache> matchedUpdateFileCaches;
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
    private TextView waitTimeTextView2;
    private TextView waitTimeTextView3;
    private final ProgressBar[] updateProgressArray = new ProgressBar[3];
    private int currentIndex = -1;
    private int lastUpdateIndex = -1;
    private final Runnable sendPackageRunnable = new AnonymousClass1();

    static /* synthetic */ void lambda$showResultDialog$1(DialogInterface dialogInterface, int i) {
    }

    static /* synthetic */ int access$108(UpdateFirmwareActivity updateFirmwareActivity) {
        int i = updateFirmwareActivity.currentIndex;
        updateFirmwareActivity.currentIndex = i + 1;
        return i;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws NumberFormatException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_update_firmware);
        instance = this;
        this.isDarkTheme = ((UiModeManager) getSystemService("uimode")).getNightMode() == 2;
        new StatusBarUtil(this).setupStatusBar(R.color.transparent, this.isDarkTheme, R.id.container);
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_green));
        getWindow().addFlags(128);
        String stringExtra = getIntent().getStringExtra(Constants.LOCAL_CONNECT_TYPE);
        LocalConnectManager.setupDongleSn(DonglePskUtil.fetchDongleSnFromWifiSsid(this, 10));
        this.localConnect = LocalConnectManager.getLocalConnect(stringExtra);
        ((ConstraintLayout) findViewById(R.id.backImageViewLayout)).setOnClickListener(new View.OnClickListener() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m720x3fc60591(view);
            }
        });
        this.currentIndex = 0;
        Inverter inverter = this.localConnect.getInverter();
        UpdateFileCache updateFileCache = null;
        System.out.println("LuxPower - fwCode = " + (inverter != null ? inverter.getFwCode() : null));
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
        updateFileCacheMap.clear();
        this.updateFileCaches = DownloadFirmwareActivity.restoreUpdateFileCachesFromLocal(getExternalFilesDir("firmware"));
        int i = 0;
        while (true) {
            if (i >= this.updateFileCaches.size()) {
                break;
            }
            UpdateFileCache updateFileCache2 = this.updateFileCaches.get(i);
            updateFileCacheMap.put(updateFileCache2.getRecordId(), updateFileCache2);
            if (DownloadFirmwareActivity.getCurrentBattFirmwareName() != null && DownloadFirmwareActivity.getCurrentBattFirmwareName().equals(updateFileCache2.getFileName())) {
                updateFileCache = updateFileCache2;
                break;
            }
            i++;
        }
        ProgressBar[] progressBarArr = this.updateProgressArray;
        progressBarArr[0] = this.updateProgressBar1;
        progressBarArr[1] = this.updateProgressBar2;
        progressBarArr[2] = this.updateProgressBar3;
        String strCheckFwCodeValid = checkFwCodeValid(inverter);
        if (!Tool.isEmpty(strCheckFwCodeValid)) {
            Tool.alert(this, getString(R.string.update_firmware_invalid) + ": " + strCheckFwCodeValid);
            return;
        }
        StandardUploadCheck standardUploadCheck = new StandardUploadCheck();
        this.standardUploadCheck = standardUploadCheck;
        standardUploadCheck.fillByInverter(inverter);
        ArrayList arrayList = new ArrayList();
        this.matchedUpdateFileCaches = arrayList;
        if (updateFileCache != null) {
            arrayList.add(updateFileCache);
        } else {
            UpdateFileCache updateFileCacheTryMatchV1StandardUploadRecord = tryMatchV1StandardUploadRecord(inverter);
            if (updateFileCacheTryMatchV1StandardUploadRecord != null) {
                this.standardUploadCheck.setPcs1UpdateMatch(true);
                this.matchedUpdateFileCaches.add(updateFileCacheTryMatchV1StandardUploadRecord);
                if ((inverter.isType6() && this.standardUploadCheck.check12KNoPowerOffUpdateAllowed()) || inverter.isTrip12K()) {
                    this.standardUploadCheck.setPcs1ForM3Upldate(true);
                }
            }
            UpdateFileCache updateFileCacheTryMatchV2StandardUploadRecord = tryMatchV2StandardUploadRecord(inverter);
            if (updateFileCacheTryMatchV2StandardUploadRecord != null) {
                this.standardUploadCheck.setPcs2UpdateMatch(true);
                this.matchedUpdateFileCaches.add(updateFileCacheTryMatchV2StandardUploadRecord);
            }
            UpdateFileCache updateFileCacheTryMatchV3StandardUploadRecord = tryMatchV3StandardUploadRecord(inverter);
            if (updateFileCacheTryMatchV3StandardUploadRecord != null) {
                this.matchedUpdateFileCaches.add(updateFileCacheTryMatchV3StandardUploadRecord);
            }
        }
        this.updateFirmwareText.setText(inverter.getSerialNum() + ":" + inverter.getFwCode());
        if (this.matchedUpdateFileCaches.size() >= 1) {
            this.updateFirmwareTextView1.setText(this.matchedUpdateFileCaches.get(0).getFileName());
            this.updateFirmwareTextView1.setVisibility(0);
            this.updateProgressBar1.setVisibility(0);
        }
        if (this.matchedUpdateFileCaches.size() >= 2) {
            this.updateFirmwareTextView2.setText(this.matchedUpdateFileCaches.get(1).getFileName());
            this.updateFirmwareTextView2.setVisibility(0);
            this.waitTimeTextView2.setVisibility(0);
            this.updateProgressBar2.setVisibility(0);
        }
        if (this.matchedUpdateFileCaches.size() >= 3) {
            this.updateFirmwareTextView3.setText(this.matchedUpdateFileCaches.get(2).getFileName());
            this.updateFirmwareTextView3.setVisibility(0);
            this.waitTimeTextView3.setVisibility(0);
            this.updateProgressBar3.setVisibility(0);
        }
        List<UpdateFileCache> list = this.matchedUpdateFileCaches;
        if (list == null || list.size() < 1) {
            Tool.alert(this, R.string.update_firmware_no_match_standard_firmware);
            this.updateNormalFileButton.setEnabled(false);
        }
    }

    /* renamed from: lambda$onCreate$0$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m720x3fc60591(View view) {
        if (this.progressing) {
            this.progressing = false;
        }
        instance.finish();
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

    private String checkFwCodeValid(Inverter inverter) throws NumberFormatException {
        if (inverter == null) {
            return "notInverterFound";
        }
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
    public void showResultDialog(boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.local_button_update_firmware);
        builder.setMessage(z ? R.string.local_page_update_result_success : R.string.local_page_update_result_failed);
        builder.setPositiveButton(R.string.phrase_button_ok, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UpdateFirmwareActivity.lambda$showResultDialog$1(dialogInterface, i);
            }
        });
        builder.show();
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

    /* JADX INFO: Access modifiers changed from: private */
    public int getMinWaitMillsSeconds() {
        UpdateProgressDetail updateProgressDetail = this.lastUpdateProgressDetail;
        if (updateProgressDetail == null || !updateProgressDetail.getTotallyStandardUpdateValue()) {
            return this.standardUploadCheck.check12KNoPowerOffUpdateAllowed() ? 30000 : 360000;
        }
        return 0;
    }

    /* renamed from: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            boolean z;
            boolean z2;
            boolean z3 = true;
            UpdateFirmwareActivity.this.progressing = true;
            if (UpdateFirmwareActivity.this.currentIndex > 0 && UpdateFirmwareActivity.this.lastUpdateIndex < UpdateFirmwareActivity.this.currentIndex) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                boolean z4 = false;
                int i = 0;
                for (long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis; jCurrentTimeMillis2 < 600000 && !z4 && UpdateFirmwareActivity.this.progressing; jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis) {
                    Tool.sleep(1000L);
                    int i2 = (int) ((600000 - jCurrentTimeMillis2) / 1000);
                    final String str = "00:" + ProTool.fillZeros(String.valueOf(i2 / 60), 2) + ":" + ProTool.fillZeros(String.valueOf(i2 % 60), 2);
                    UpdateFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m722x4d2f5a14(str);
                        }
                    });
                    if (jCurrentTimeMillis2 > UpdateFirmwareActivity.this.getMinWaitMillsSeconds()) {
                        if (i == 0) {
                            String strSendCommand = UpdateFirmwareActivity.this.localConnect.sendCommand("read_03_1", DataFrameFactory.createReadMultiHoldDataFrame(UpdateFirmwareActivity.this.localConnect.getTcpProtocol(), UpdateFirmwareActivity.this.localConnect.getDatalogSn(), 0, 40));
                            if (!Tool.isEmpty(strSendCommand) && strSendCommand.length() > 60) {
                                z4 = true;
                            }
                        }
                        i++;
                        if (i > 10) {
                            i = 0;
                        }
                    }
                }
                UpdateFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m723x40bede55();
                    }
                });
            }
            UpdateFirmwareActivity updateFirmwareActivity = UpdateFirmwareActivity.this;
            updateFirmwareActivity.lastUpdateIndex = updateFirmwareActivity.currentIndex;
            final UpdateProgressDetail updateProgressDetailCreateUpdateProgressDetail = UpdateFirmwareActivity.this.createUpdateProgressDetail();
            boolean z5 = true;
            boolean z6 = false;
            while (UpdateFirmwareActivity.this.progressing && z5) {
                final Map<Integer, String> firmware = ((UpdateFileCache) UpdateFirmwareActivity.this.matchedUpdateFileCaches.get(UpdateFirmwareActivity.this.currentIndex)).getFirmware();
                int i3 = AnonymousClass2.$SwitchMap$com$lux$luxcloud$view$updateFirmware$bean$UPDATE_STATUS[updateProgressDetailCreateUpdateProgressDetail.getUpdateStatus().ordinal()];
                if (i3 != 1) {
                    if (i3 == 2) {
                        if (updateProgressDetailCreateUpdateProgressDetail.getLastTimeSendPackage() > 1) {
                            if (System.currentTimeMillis() - updateProgressDetailCreateUpdateProgressDetail.getLastTimeSendPackage() < 5000) {
                                Tool.sleep(500L);
                                z5 = true;
                            } else {
                                updateProgressDetailCreateUpdateProgressDetail.setErrorCount(updateProgressDetailCreateUpdateProgressDetail.getErrorCount() + 1);
                            }
                        }
                        if ((!updateProgressDetailCreateUpdateProgressDetail.isSendUpdateStart_0x21() && updateProgressDetailCreateUpdateProgressDetail.getErrorCount() >= 4) || updateProgressDetailCreateUpdateProgressDetail.getPackageIndex() > firmware.size() || updateProgressDetailCreateUpdateProgressDetail.getErrorCount() >= 10) {
                            updateProgressDetailCreateUpdateProgressDetail.setUpdateStatus(UPDATE_STATUS.FAILURE);
                            UpdateFirmwareActivity.this.localConnect.close();
                            UpdateFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m724x344e6296();
                                }
                            });
                        } else {
                            updateProgressDetailCreateUpdateProgressDetail.setLastTimeSendPackage(System.currentTimeMillis());
                            UpdateFirmwareActivity.this.sendUpdateDataFrame(updateProgressDetailCreateUpdateProgressDetail);
                            z = z6;
                            z2 = true;
                        }
                    }
                    z = z6;
                    z2 = false;
                } else if (updateProgressDetailCreateUpdateProgressDetail.getPackageIndex() > firmware.size() && updateProgressDetailCreateUpdateProgressDetail.isSendUpdateReset_0x23()) {
                    updateProgressDetailCreateUpdateProgressDetail.setUpdateStatus(UPDATE_STATUS.SUCCESS);
                    UpdateFirmwareActivity.this.lastUpdateProgressDetail = updateProgressDetailCreateUpdateProgressDetail;
                    System.out.println("LuxPower升级成功");
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
                    UpdateFirmwareActivity.this.sendUpdateDataFrame(updateProgressDetailCreateUpdateProgressDetail);
                    z = z6;
                    z2 = true;
                }
                UpdateFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m725x27dde6d7(updateProgressDetailCreateUpdateProgressDetail, firmware);
                    }
                });
                Tool.sleep(100L);
                z5 = z2;
                z6 = z;
            }
            if (z6) {
                if (UpdateFirmwareActivity.this.currentIndex < UpdateFirmwareActivity.this.matchedUpdateFileCaches.size() - 1) {
                    UpdateFirmwareActivity.access$108(UpdateFirmwareActivity.this);
                    new Thread(UpdateFirmwareActivity.this.sendPackageRunnable).start();
                    z3 = false;
                } else {
                    UpdateFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m726x1b6d6b18();
                        }
                    });
                }
            }
            if (!z6 || z3) {
                UpdateFirmwareActivity.this.progressing = false;
                UpdateFirmwareActivity.this.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m727xefcef59();
                    }
                });
            }
        }

        /* renamed from: lambda$run$0$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity$1, reason: not valid java name */
        /* synthetic */ void m722x4d2f5a14(String str) {
            if (UpdateFirmwareActivity.this.currentIndex == 1) {
                UpdateFirmwareActivity.this.waitTimeTextView2.setText(UpdateFirmwareActivity.this.getString(R.string.login_download_dialog_text) + " " + str);
            } else if (UpdateFirmwareActivity.this.currentIndex == 2) {
                UpdateFirmwareActivity.this.waitTimeTextView3.setText(UpdateFirmwareActivity.this.getString(R.string.login_download_dialog_text) + " " + str);
            }
        }

        /* renamed from: lambda$run$1$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity$1, reason: not valid java name */
        /* synthetic */ void m723x40bede55() {
            UpdateFirmwareActivity.this.waitTimeTextView2.setVisibility(4);
            UpdateFirmwareActivity.this.waitTimeTextView3.setVisibility(4);
        }

        /* renamed from: lambda$run$2$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity$1, reason: not valid java name */
        /* synthetic */ void m724x344e6296() {
            UpdateFirmwareActivity.this.showResultDialog(false);
        }

        /* renamed from: lambda$run$3$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity$1, reason: not valid java name */
        /* synthetic */ void m725x27dde6d7(UpdateProgressDetail updateProgressDetail, Map map) {
            UpdateFirmwareActivity.this.updateProgressArray[UpdateFirmwareActivity.this.currentIndex].setProgress((updateProgressDetail.getCurrentProgress() * 100) / (map.size() + 2));
        }

        /* renamed from: lambda$run$4$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity$1, reason: not valid java name */
        /* synthetic */ void m726x1b6d6b18() {
            UpdateFirmwareActivity.this.showResultDialog(true);
        }

        /* renamed from: lambda$run$5$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity$1, reason: not valid java name */
        /* synthetic */ void m727xefcef59() {
            UpdateFirmwareActivity.this.updateNormalFileButton.setEnabled(true);
        }
    }

    /* renamed from: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$lux$luxcloud$view$updateFirmware$bean$UPDATE_STATUS;

        static {
            int[] iArr = new int[UPDATE_STATUS.values().length];
            $SwitchMap$com$lux$luxcloud$view$updateFirmware$bean$UPDATE_STATUS = iArr;
            try {
                iArr[UPDATE_STATUS.WAITING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lux$luxcloud$view$updateFirmware$bean$UPDATE_STATUS[UPDATE_STATUS.READY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateDataFrame(UpdateProgressDetail updateProgressDetail) {
        DataFrame dataFrameCreateUpdateResetDataFrame;
        long fileSize;
        DataFrame dataFrameCreateUpdateSendDataDataFrame;
        DataFrame dataFrameCreateUpdatePrepareDataFrame;
        UpdateFileCache updateFileCache = this.matchedUpdateFileCaches.get(this.currentIndex);
        Map<Integer, String> firmware = updateFileCache.getFirmware();
        int size = firmware.size();
        if (!updateProgressDetail.isSendUpdateStart_0x21()) {
            if (updateFileCache.isLuxVersion()) {
                dataFrameCreateUpdatePrepareDataFrame = DataFrameFactory.createLuxUpdatePrepareDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), updateFileCache.getTailEncoded(), size, updateFileCache.getCrc32());
            } else {
                dataFrameCreateUpdatePrepareDataFrame = DataFrameFactory.createUpdatePrepareDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), updateFileCache.getTailEncoded(), size, updateFileCache.getCrc32());
            }
            if (dataFrameCreateUpdatePrepareDataFrame != null) {
                String strSendCommand = this.localConnect.sendCommand("tcpUpdate_Prepare", dataFrameCreateUpdatePrepareDataFrame);
                if (Tool.isEmpty(strSendCommand)) {
                    return;
                }
                char cCharAt = strSendCommand.charAt(32);
                int iCount = ProTool.count(strSendCommand.charAt(34), strSendCommand.charAt(33));
                updateProgressDetail.setUpdateStatus(UPDATE_STATUS.WAITING);
                if (iCount <= 0 || iCount >= size) {
                    iCount = 1;
                }
                updateProgressDetail.setPackageIndex(iCount);
                if (cCharAt == 'A') {
                    updateProgressDetail.setTotallyStandardUpdate(true);
                    return;
                }
                return;
            }
            return;
        }
        if (updateProgressDetail.isSendUpdateStart_0x21() && updateProgressDetail.getPackageIndex() <= size) {
            int packageIndex = updateProgressDetail.getPackageIndex();
            if (updateFileCache.getFileType() == 2) {
                fileSize = updateFileCache.getPhysicalAddr().get(Integer.valueOf(packageIndex)).intValue();
            } else {
                fileSize = (updateFileCache.getFileType() == 1 || updateFileCache.getFileType() == 3) ? updateFileCache.getFileSize() : 0L;
            }
            long j = fileSize;
            int fileType = updateFileCache.getFileType();
            boolean z = updateProgressDetail.getTotallyStandardUpdateValue() && isV1StandardUpdate() && this.standardUploadCheck.isAllowTotallyUpdate();
            if (z) {
                fileType |= 64;
            }
            System.out.println("LuxPower - ble - SEND 0x22 - " + packageIndex + ", totallyAndM3Update = " + z + ", fileType = " + fileType);
            if (updateFileCache.isLuxVersion()) {
                dataFrameCreateUpdateSendDataDataFrame = DataFrameFactory.createLuxUpdateSendDataDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), packageIndex, fileType, updateFileCache.getFirmwareLengthArrayEncoded(), firmware.get(Integer.valueOf(packageIndex)));
            } else {
                dataFrameCreateUpdateSendDataDataFrame = DataFrameFactory.createUpdateSendDataDataFrame(this.localConnect.getTcpProtocol(), updateProgressDetail.getDatalogSn(), updateProgressDetail.getInverterSn(), packageIndex, fileType, j, firmware.get(Integer.valueOf(packageIndex)));
            }
            if (dataFrameCreateUpdateSendDataDataFrame == null || Tool.isEmpty(this.localConnect.sendCommand("tcpUpdate_Send_" + packageIndex, dataFrameCreateUpdateSendDataDataFrame))) {
                return;
            }
            updateProgressDetail.setUpdateStatus(UPDATE_STATUS.WAITING);
            return;
        }
        if (updateProgressDetail.getPackageIndex() <= size || updateProgressDetail.isSendUpdateReset_0x23()) {
            return;
        }
        int fileType2 = updateFileCache.getFileType();
        if (updateFileCache.isLuxVersion() && updateFileCache.getFileHandleType() != null) {
            TCP_PROTOCOL tcpProtocol = this.localConnect.getTcpProtocol();
            String datalogSn = updateProgressDetail.getDatalogSn();
            String inverterSn = updateProgressDetail.getInverterSn();
            int iIntValue = updateFileCache.getFileHandleType().intValue();
            if (updateFileCache.getBmsHeaderId() != null) {
                size = updateFileCache.getBmsHeaderId().intValue();
            }
            dataFrameCreateUpdateResetDataFrame = DataFrameFactory.createLuxUpdateResetDataFrame(tcpProtocol, datalogSn, inverterSn, fileType2, iIntValue, size, updateFileCache.getCrc32());
        } else {
            TCP_PROTOCOL tcpProtocol2 = this.localConnect.getTcpProtocol();
            String datalogSn2 = updateProgressDetail.getDatalogSn();
            String inverterSn2 = updateProgressDetail.getInverterSn();
            if (updateFileCache.getBmsHeaderId() != null) {
                size = updateFileCache.getBmsHeaderId().intValue();
            }
            dataFrameCreateUpdateResetDataFrame = DataFrameFactory.createUpdateResetDataFrame(tcpProtocol2, datalogSn2, inverterSn2, fileType2, size, updateFileCache.getCrc32());
        }
        if (dataFrameCreateUpdateResetDataFrame != null) {
            String strSendCommand2 = this.localConnect.sendCommand("tcpUpdate_Reset", dataFrameCreateUpdateResetDataFrame);
            if (Tool.isEmpty(strSendCommand2) || strSendCommand2.charAt(32) == 1) {
                updateProgressDetail.setSendUpdateReset_0x23(true);
                updateProgressDetail.setUpdateStatus(UPDATE_STATUS.WAITING);
            }
        }
    }

    private boolean isV1StandardUpdate() {
        return this.standardUploadCheck.isPcs1UpdateMatch() && this.currentIndex == 0;
    }

    private String generateFirmwareUpdateVerifyCode(long j) {
        String strValueOf = String.valueOf(j);
        return strValueOf.length() > 6 ? strValueOf.substring(strValueOf.length() - 6) : strValueOf;
    }

    public void clickStartUpdateButton(View view) {
        List<UpdateFileCache> list = this.matchedUpdateFileCaches;
        if (list == null || list.size() < 1) {
            Tool.alert(this, R.string.update_firmware_no_match_standard_firmware);
        } else {
            this.lastUpdateProgressDetail = null;
            new Thread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m719xc48198cd();
                }
            }).start();
        }
    }

    /* renamed from: lambda$clickStartUpdateButton$2$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m719xc48198cd() {
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
            runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.view.updateFirmware.UpdateFirmwareActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m721x912e9c7a();
                }
            });
            new Thread(this.sendPackageRunnable).start();
        }
        return 0;
    }

    /* renamed from: lambda$startSendFirmwarePackage$3$com-lux-luxcloud-view-updateFirmware-UpdateFirmwareActivity, reason: not valid java name */
    /* synthetic */ void m721x912e9c7a() {
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