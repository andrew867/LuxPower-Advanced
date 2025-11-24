package com.lux.luxcloud.tcp;

import com.lux.luxcloud.tool.Tool;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ReadSerial extends Thread {
    protected SerialBuffer ComBuffer;
    protected InputStream ComPort;

    public ReadSerial(SerialBuffer serialBuffer, InputStream inputStream) {
        this.ComBuffer = serialBuffer;
        this.ComPort = inputStream;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException, IOException {
        while (true) {
            try {
                int i = this.ComPort.read();
                if (i < 0) {
                    Tool.sleep(50L);
                } else {
                    this.ComBuffer.putChar(i);
                }
            } catch (IOException e) {
                TcpManager.getInstance().setConnected(false);
                e.printStackTrace();
                return;
            }
        }
    }
}