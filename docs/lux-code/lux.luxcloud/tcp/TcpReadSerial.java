package com.lux.luxcloud.tcp;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class TcpReadSerial extends ReadSerial {
    public TcpReadSerial(SerialBuffer serialBuffer, InputStream inputStream) {
        super(serialBuffer, inputStream);
    }

    @Override // com.lux.luxcloud.tcp.ReadSerial, java.lang.Thread, java.lang.Runnable
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