package com.nfcx.eg4.tcp;

import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public interface CommandSender {
    void close();

    String getCommandWaitResult(String str);

    void putCommandWaitMap(String str, String str2);

    String sendCommand(String str, DataFrame dataFrame);

    String sendCommand(String str, DataFrame dataFrame, int i);

    boolean sendPureCommand(DataFrame dataFrame);

    boolean startMonitor();
}