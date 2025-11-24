package com.lux.luxcloud.tcp;

import java.net.Socket;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class TcpCommandSender extends AbstractCommandSender {
    public TcpCommandSender(Socket socket) {
        this.portBean = new TcpBean(socket);
    }

    @Override // com.lux.luxcloud.tcp.CommandSender
    public boolean startMonitor() {
        return this.portBean.initialize(this);
    }
}