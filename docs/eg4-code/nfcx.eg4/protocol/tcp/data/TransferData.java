package com.nfcx.eg4.protocol.tcp.data;

import com.nfcx.eg4.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class TransferData implements Data {
    protected byte[] command;
    protected String datalogSn;

    public TransferData(String str) {
        this.datalogSn = str;
    }

    public void setCommand(byte[] bArr) {
        this.command = bArr;
    }

    @Override // com.nfcx.eg4.protocol.tcp.data.Data
    public byte[] getFrame() {
        byte[] bArr = this.command;
        byte[] bArr2 = new byte[(bArr != null ? bArr.length : 0) + 12];
        try {
            System.arraycopy(this.datalogSn.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr2, 0, 10);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr2, 10, this.command != null ? r3.length : 0, 0, true);
        byte[] bArr3 = this.command;
        if (bArr3 != null) {
            System.arraycopy(bArr3, 0, bArr2, 12, bArr3.length);
        }
        return bArr2;
    }
}