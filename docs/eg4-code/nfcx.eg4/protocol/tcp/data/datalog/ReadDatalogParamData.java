package com.nfcx.eg4.protocol.tcp.data.datalog;

import com.nfcx.eg4.protocol.tcp.data.Data;
import com.nfcx.eg4.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ReadDatalogParamData implements Data {
    private String datalogSn;
    private int paramIndex;

    public ReadDatalogParamData(String str, int i) {
        this.datalogSn = str;
        this.paramIndex = i;
    }

    @Override // com.nfcx.eg4.protocol.tcp.data.Data
    public byte[] getFrame() {
        byte[] bArr = new byte[12];
        try {
            System.arraycopy(this.datalogSn.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 0, 10);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr, 10, this.paramIndex, 0, true);
        return bArr;
    }
}