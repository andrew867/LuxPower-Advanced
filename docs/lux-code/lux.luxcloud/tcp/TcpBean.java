package com.lux.luxcloud.tcp;

import java.io.IOException;
import java.net.Socket;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class TcpBean extends AbstractPortBean {
    private Socket socket;

    public TcpBean(Socket socket) {
        this.socket = socket;
    }

    @Override // com.lux.luxcloud.tcp.IPortBean
    public boolean initialize(CommandSender commandSender) {
        Socket socket = this.socket;
        if (socket == null) {
            return false;
        }
        try {
            this.in = socket.getInputStream();
            this.out = this.socket.getOutputStream();
            this.SB = new TcpAnalyzer(commandSender);
            this.RT = new TcpReadSerial(this.SB, this.in);
            this.RT.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.lux.luxcloud.tcp.IPortBean
    public void closePort() throws IOException {
        try {
            if (this.in != null) {
                this.in.close();
            }
            if (this.out != null) {
                this.out.close();
            }
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}