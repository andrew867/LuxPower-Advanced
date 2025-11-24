package com.nfcx.eg4.protocol.lux.command.write;

import com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION;
import com.nfcx.eg4.protocol.lux.command.Command;
import com.nfcx.eg4.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class WriteMultiCommand implements Command {
    private int pointNumber;
    private String serialNum;
    private int startAddress;
    private byte[] values;

    public WriteMultiCommand(String str, int i, int i2, byte[] bArr) {
        this.serialNum = str;
        this.startAddress = i;
        this.pointNumber = i2;
        this.values = bArr;
    }

    @Override // com.nfcx.eg4.protocol.lux.command.Command
    public byte[] getFrame() {
        byte[] bArr = new byte[(this.pointNumber * 2) + 19];
        bArr[0] = 0;
        bArr[1] = (byte) DEVICE_FUNCTION.W_MULTI.getFunctionCode();
        try {
            System.arraycopy(this.serialNum.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 2, this.serialNum.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr, 12, this.startAddress, 0, true);
        ProTool.convertLong2Byte2(bArr, 14, this.pointNumber, 0, true);
        bArr[16] = (byte) (this.pointNumber * 2);
        byte[] bArr2 = this.values;
        System.arraycopy(bArr2, 0, bArr, 17, bArr2.length);
        ProTool.convertLong2Byte2(bArr, (this.pointNumber * 2) + 17, ProTool.modbus_Caluation_CRC16(bArr, r0 - 2), 0, true);
        return bArr;
    }
}