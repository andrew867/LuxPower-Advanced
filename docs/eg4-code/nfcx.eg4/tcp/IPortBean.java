package com.nfcx.eg4.tcp;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public interface IPortBean {
    String ReadPort(int i);

    void WritePort(char[] cArr, int i);

    boolean WritePort(byte[] bArr);

    void closePort();

    boolean initialize(CommandSender commandSender);
}