package com.lux.luxcloud.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.ProgressBar;
import com.lux.luxcloud.R;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.tcp.TcpManager;
import com.lux.luxcloud.view.ble.BleConnectActivity;
import com.lux.luxcloud.view.local.LocalActivity;
import com.lux.luxcloud.view.updateFirmware.UpdateExtFirmwareActivity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LocalConnectTool {
    public static void go2LocalActivity(final Activity activity, final String str, final int i, final Button button, final ProgressBar progressBar) {
        new AlertDialog.Builder(activity).setTitle(R.string.phrase_local_connect_title).setIcon(R.drawable.ic_launcher).setItems(new String[]{activity.getString(R.string.phrase_tcp_connect), activity.getString(R.string.phrase_bluetooth_connect)}, new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.tool.LocalConnectTool$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                LocalConnectTool.lambda$go2LocalActivity$0(activity, str, i, button, progressBar, dialogInterface, i2);
            }
        }).setNegativeButton(activity.getString(R.string.phrase_button_cancel), new DialogInterface.OnClickListener() { // from class: com.lux.luxcloud.tool.LocalConnectTool$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                button.setEnabled(true);
            }
        }).create().show();
    }

    static /* synthetic */ void lambda$go2LocalActivity$0(Activity activity, String str, int i, Button button, ProgressBar progressBar, DialogInterface dialogInterface, int i2) {
        if (i2 == 1) {
            Intent intent = new Intent(activity, (Class<?>) BleConnectActivity.class);
            intent.putExtra(Constants.TARGET, str);
            activity.startActivity(intent);
            return;
        }
        connectTcpLocal(activity, str, i, button, progressBar);
    }

    public static void connectTcpLocal(final Activity activity, final String str, final int i, final Button button, final ProgressBar progressBar) {
        activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.tool.LocalConnectTool$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                LocalConnectTool.lambda$connectTcpLocal$2(button, progressBar);
            }
        });
        new Thread(new Runnable() { // from class: com.lux.luxcloud.tool.LocalConnectTool$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                LocalConnectTool.lambda$connectTcpLocal$5(activity, i, button, progressBar, str);
            }
        }).start();
    }

    static /* synthetic */ void lambda$connectTcpLocal$2(Button button, ProgressBar progressBar) {
        button.setEnabled(false);
        progressBar.setVisibility(0);
    }

    static /* synthetic */ void lambda$connectTcpLocal$5(final Activity activity, int i, final Button button, final ProgressBar progressBar, final String str) {
        final TcpManager tcpManager = TcpManager.getInstance();
        tcpManager.close();
        tcpManager.setDatalogSn(DonglePskUtil.fetchDongleSnFromWifiSsid(activity, i));
        if (tcpManager.initialize(true)) {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.tool.LocalConnectTool$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocalConnectTool.lambda$connectTcpLocal$3(button, progressBar, str, tcpManager, activity);
                }
            });
        } else {
            activity.runOnUiThread(new Runnable() { // from class: com.lux.luxcloud.tool.LocalConnectTool$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    LocalConnectTool.lambda$connectTcpLocal$4(activity, button, progressBar);
                }
            });
        }
    }

    static /* synthetic */ void lambda$connectTcpLocal$3(Button button, ProgressBar progressBar, String str, TcpManager tcpManager, Activity activity) {
        try {
            button.setEnabled(true);
            progressBar.setVisibility(4);
            if (Constants.TARGET_UPDATE_FIRMWARE.equals(str)) {
                if (tcpManager.getDatalogSn() == null) {
                    tcpManager.initDongleSn();
                }
                Intent intent = new Intent(activity, (Class<?>) UpdateExtFirmwareActivity.class);
                intent.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_TCP);
                activity.startActivity(intent);
                return;
            }
            Intent intent2 = new Intent(activity, (Class<?>) LocalActivity.class);
            intent2.putExtra(Constants.LOCAL_CONNECT_TYPE, Constants.LOCAL_CONNECT_TYPE_TCP);
            activity.startActivity(intent2);
        } catch (Exception unused) {
        }
    }

    static /* synthetic */ void lambda$connectTcpLocal$4(Activity activity, Button button, ProgressBar progressBar) {
        Tool.alert(activity, R.string.phrase_toast_local_connect_error);
        button.setEnabled(true);
        progressBar.setVisibility(4);
    }
}