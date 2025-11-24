package com.lux.luxcloud.protocol.tcp.data.datalog;

import com.lux.luxcloud.protocol.tcp.data.Data;
import com.lux.luxcloud.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class WriteDatalogParamData implements Data {
    private String datalogSn;
    private int paramIndex;
    private byte[] values;

    public WriteDatalogParamData(String str, int i, byte[] bArr) {
        this.datalogSn = str;
        this.paramIndex = i;
        this.values = bArr;
    }

    @Override // com.lux.luxcloud.protocol.tcp.data.Data
    public byte[] getFrame() {
        byte[] bArr = new byte[this.values.length + 14];
        try {
            System.arraycopy(this.datalogSn.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 0, 10);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr, 10, this.paramIndex, 0, true);
        ProTool.convertLong2Byte2(bArr, 12, this.values.length, 0, true);
        byte[] bArr2 = this.values;
        System.arraycopy(bArr2, 0, bArr, 14, bArr2.length);
        return bArr;
    }
}