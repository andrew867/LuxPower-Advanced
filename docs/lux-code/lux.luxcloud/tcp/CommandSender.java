package com.lux.luxcloud.tcp;

import com.lux.luxcloud.protocol.tcp.dataframe.DataFrame;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public interface CommandSender {
    void close();

    String getCommandWaitResult(String str);

    void putCommandWaitMap(String str, String str2);

    String sendCommand(String str, DataFrame dataFrame);

    String sendCommand(String str, DataFrame dataFrame, int i);

    boolean sendPureCommand(DataFrame dataFrame);

    boolean startMonitor();
}