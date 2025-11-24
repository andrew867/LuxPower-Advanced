package com.lux.luxcloud.protocol.lux.command.write;

import com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION;
import com.lux.luxcloud.protocol.lux.command.Command;
import com.lux.luxcloud.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class WriteSingleCommand implements Command {
    private int register;
    private String serialNum;
    private int value;

    public WriteSingleCommand(String str, int i, int i2) {
        this.serialNum = str;
        this.register = i;
        this.value = i2;
    }

    @Override // com.lux.luxcloud.protocol.lux.command.Command
    public byte[] getFrame() {
        byte[] bArr = new byte[18];
        bArr[0] = 0;
        bArr[1] = (byte) DEVICE_FUNCTION.W_SINGLE.getFunctionCode();
        try {
            System.arraycopy(this.serialNum.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 2, this.serialNum.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr, 12, this.register, 0, true);
        ProTool.convertLong2Byte2(bArr, 14, this.value, 0, true);
        ProTool.convertLong2Byte2(bArr, 16, ProTool.modbus_Caluation_CRC16(bArr, 16), 0, true);
        return bArr;
    }
}