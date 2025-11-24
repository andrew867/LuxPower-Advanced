package com.lux.luxcloud.protocol.lux.command.update;

import com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION;
import com.lux.luxcloud.protocol.lux.command.Command;
import com.lux.luxcloud.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LuxUpdateResetCommand implements Command {
    private long crc32;
    private int dataCount;
    private int fileHandleType;
    private int fileType;
    private String serialNum;

    public LuxUpdateResetCommand(String str, int i, int i2, int i3, long j) {
        this.serialNum = str;
        this.fileType = i;
        this.fileHandleType = i2;
        this.dataCount = i3;
        this.crc32 = j;
    }

    @Override // com.lux.luxcloud.protocol.lux.command.Command
    public byte[] getFrame() {
        byte[] bArr = new byte[21];
        bArr[0] = 0;
        bArr[1] = (byte) DEVICE_FUNCTION.LUX_UPDATE_RESET.getFunctionCode();
        try {
            System.arraycopy(this.serialNum.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 2, this.serialNum.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bArr[12] = (byte) (this.fileType | ((this.fileHandleType & 3) << 6));
        ProTool.convertLong2Byte2(bArr, 13, this.dataCount, 0, true);
        ProTool.convertLong2Byte4(bArr, 15, this.crc32, 0, true);
        ProTool.convertLong2Byte2(bArr, 19, ProTool.modbus_Caluation_CRC16(bArr, 19), 0, true);
        return bArr;
    }
}