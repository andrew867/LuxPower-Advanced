package com.lux.luxcloud.tcp;

import com.lux.luxcloud.protocol.tcp.data.response.HeartBeatData;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.response.ResponseHeartBeatDataFrame;
import com.lux.luxcloud.tool.ProTool;
import java.util.Date;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class TcpAnalyzer extends SerialBuffer {
    private static final int PREFIX_CHECK_LENGTH = 2;
    private static final int PREFIX_LENGTH = 6;
    private static final int[] RECEIVE_PREFIX;
    private static String prefixText = "";
    private CommandSender commandSender;
    private int dataLength = 0;
    private StringBuffer dataBuffer = new StringBuffer();
    private boolean prefixValid = false;

    static {
        int[] iArr = {161, 26};
        RECEIVE_PREFIX = iArr;
        for (int i : iArr) {
            prefixText += ProTool.getStringFromHex(i);
        }
    }

    public TcpAnalyzer(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override // com.lux.luxcloud.tcp.SerialBuffer
    public synchronized void putChar(int i) {
        this.dataBuffer.append((char) i);
        checkData(i);
    }

    private void checkData(int i) {
        int length = this.dataBuffer.length();
        if (length == 2) {
            if (this.dataBuffer.toString().startsWith(prefixText)) {
                this.prefixValid = true;
                return;
            }
            return;
        }
        if (length > 2 && !this.prefixValid && i == 26) {
            int iIndexOf = this.dataBuffer.toString().indexOf(prefixText);
            if (iIndexOf > 0) {
                this.dataBuffer.delete(0, iIndexOf);
                this.prefixValid = true;
                return;
            }
            return;
        }
        if (length == 6 && this.prefixValid) {
            this.dataLength = (i * 256) + this.dataBuffer.charAt(length - 2);
        } else if (length == this.dataLength + 6 && this.prefixValid) {
            handleValidData();
        }
    }

    private void handleValidData() {
        System.out.println("LuxPower - " + new Date() + " - Valid: " + ProTool.showData(this.dataBuffer.toString()));
        char cCharAt = this.dataBuffer.charAt(7);
        if (cCharAt > 0) {
            switch (cCharAt) {
                case 193:
                    handleHeartBeatData();
                    break;
                case 194:
                    handleTranslate();
                    break;
                case 195:
                    String string = this.dataBuffer.toString();
                    this.commandSender.putCommandWaitMap("read_datalog_" + ProTool.count(string.charAt(19), string.charAt(18)), string);
                    break;
                case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256 /* 196 */:
                    String string2 = this.dataBuffer.toString();
                    this.commandSender.putCommandWaitMap("write_datalog_" + ProTool.count(string2.charAt(19), string2.charAt(18)), string2);
                    break;
            }
        }
        StringBuffer stringBuffer = this.dataBuffer;
        stringBuffer.delete(0, stringBuffer.length());
        this.dataBuffer.trimToSize();
        this.prefixValid = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0180  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleTranslate() {
        /*
            Method dump skipped, instructions count: 436
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lux.luxcloud.tcp.TcpAnalyzer.handleTranslate():void");
    }

    private void handleHeartBeatData() {
        this.commandSender.putCommandWaitMap("heart_beat", this.dataBuffer.toString());
        char cCharAt = this.dataBuffer.charAt(18);
        ResponseHeartBeatDataFrame responseHeartBeatDataFrame = new ResponseHeartBeatDataFrame(this.dataBuffer.charAt(2) == 2 ? TCP_PROTOCOL._02 : TCP_PROTOCOL._01);
        responseHeartBeatDataFrame.setData(new HeartBeatData(this.dataBuffer.substring(8, 18), cCharAt));
        this.commandSender.sendPureCommand(responseHeartBeatDataFrame);
    }
}