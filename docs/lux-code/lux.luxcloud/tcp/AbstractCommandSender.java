package com.lux.luxcloud.tcp;

import com.lux.luxcloud.protocol.tcp.dataframe.DataFrame;
import com.lux.luxcloud.tool.Tool;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public abstract class AbstractCommandSender implements CommandSender {
    private Map<String, String> commandWaitMap = new ConcurrentSkipListMap();
    protected IPortBean portBean;

    @Override // com.lux.luxcloud.tcp.CommandSender
    public void close() {
        this.portBean.closePort();
    }

    @Override // com.lux.luxcloud.tcp.CommandSender
    public String sendCommand(String str, DataFrame dataFrame) {
        return sendCommand(str, dataFrame, 3);
    }

    @Override // com.lux.luxcloud.tcp.CommandSender
    public String sendCommand(String str, DataFrame dataFrame, int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 30) {
            i = 30;
        }
        System.out.println("LuxPower - timeoutSeconds = " + i + ", dataFrame = " + dataFrame.show());
        try {
            this.commandWaitMap.put(str, "");
            if (!sendPureCommand(dataFrame)) {
                return null;
            }
            for (int i2 = 0; i2 < i * 5; i2++) {
                Tool.sleep(200L);
                if (!Tool.isEmpty(this.commandWaitMap.get(str))) {
                    String str2 = this.commandWaitMap.get(str);
                    this.commandWaitMap.remove(str);
                    return str2;
                }
            }
            return null;
        } catch (Exception e) {
            TcpManager.getInstance().setConnected(false);
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.lux.luxcloud.tcp.CommandSender
    public boolean sendPureCommand(DataFrame dataFrame) {
        return this.portBean.WritePort(dataFrame.getFrame());
    }

    @Override // com.lux.luxcloud.tcp.CommandSender
    public void putCommandWaitMap(String str, String str2) {
        this.commandWaitMap.put(str, str2);
    }

    @Override // com.lux.luxcloud.tcp.CommandSender
    public String getCommandWaitResult(String str) {
        return this.commandWaitMap.get(str);
    }
}