package com.nfcx.eg4.tcp;

import java.net.Socket;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class TcpCommandSender extends AbstractCommandSender {
    public TcpCommandSender(Socket socket) {
        this.portBean = new TcpBean(socket);
    }

    @Override // com.nfcx.eg4.tcp.CommandSender
    public boolean startMonitor() {
        return this.portBean.initialize(this);
    }
}