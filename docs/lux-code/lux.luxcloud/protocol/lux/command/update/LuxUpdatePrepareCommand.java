package com.lux.luxcloud.protocol.lux.command.update;

import android.util.Base64;
import com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION;
import com.lux.luxcloud.protocol.lux.command.Command;
import com.lux.luxcloud.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LuxUpdatePrepareCommand implements Command {
    private long crc32;
    private int dataCount;
    private String serialNum;
    private byte[] tail;

    public LuxUpdatePrepareCommand(String str, String str2, int i, long j) {
        this.serialNum = str;
        this.tail = Base64.decode(str2, 0);
        this.dataCount = i;
        this.crc32 = j;
    }

    @Override // com.lux.luxcloud.protocol.lux.command.Command
    public byte[] getFrame() {
        byte[] bArr = new byte[24];
        bArr[0] = 0;
        bArr[1] = (byte) DEVICE_FUNCTION.LUX_UPDATE_PREPARE.getFunctionCode();
        try {
            System.arraycopy(this.serialNum.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 2, this.serialNum.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.arraycopy(this.tail, 0, bArr, 12, 4);
        ProTool.convertLong2Byte2(bArr, 16, this.dataCount, 0, true);
        ProTool.convertLong2Byte4(bArr, 18, this.crc32, 0, true);
        ProTool.convertLong2Byte2(bArr, 22, ProTool.modbus_Caluation_CRC16(bArr, 22), 0, true);
        return bArr;
    }
}