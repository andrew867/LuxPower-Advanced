package com.nfcx.eg4.tcp;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class TcpReadSerial extends ReadSerial {
    public TcpReadSerial(SerialBuffer serialBuffer, InputStream inputStream) {
        super(serialBuffer, inputStream);
    }

    @Override // com.nfcx.eg4.tcp.ReadSerial, java.lang.Thread, java.lang.Runnable
    public void run() throws IOException {
        while (true) {
            try {
                int i = this.ComPort.read();
                if (i < 0) {
                    return;
                } else {
                    this.ComBuffer.putChar(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}