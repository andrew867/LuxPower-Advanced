package com.nfcx.eg4.protocol.tcp.data.response;

import com.nfcx.eg4.protocol.tcp.data.Data;
import com.nfcx.eg4.tool.ProTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class HeartBeatData implements Data {
    private int datalogType;
    private String serialNum;

    public HeartBeatData(String str, int i) {
        this.serialNum = str;
        this.datalogType = i;
    }

    @Override // com.nfcx.eg4.protocol.tcp.data.Data
    public byte[] getFrame() {
        byte[] bArr = new byte[11];
        ProTool.convertAsciiString2Byte(bArr, 0, this.serialNum);
        bArr[10] = (byte) this.datalogType;
        return bArr;
    }
}