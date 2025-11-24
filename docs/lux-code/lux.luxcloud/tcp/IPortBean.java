package com.lux.luxcloud.tcp;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public interface IPortBean {
    String ReadPort(int i);

    void WritePort(char[] cArr, int i);

    boolean WritePort(byte[] bArr);

    void closePort();

    boolean initialize(CommandSender commandSender);
}