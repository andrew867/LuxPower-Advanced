package com.lux.luxcloud.protocol.lux.command.update;

import android.util.Base64;
import com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION;
import com.lux.luxcloud.protocol.lux.command.Command;
import com.lux.luxcloud.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class LuxUpdateSendDataCommand implements Command {
    private int dataIndex;
    private byte[] dataList;
    private int fileType;
    private byte[] firmwareLengthArray;
    private String serialNum;

    public LuxUpdateSendDataCommand(String str, int i, int i2, String str2, String str3) {
        this.serialNum = str;
        this.dataIndex = i;
        this.fileType = i2;
        this.firmwareLengthArray = Base64.decode(str2, 0);
        this.dataList = Base64.decode(str3, 0);
    }

    @Override // com.lux.luxcloud.protocol.lux.command.Command
    public byte[] getFrame() {
        int length = this.dataList.length + 19 + 4;
        byte[] bArr = new byte[length];
        bArr[0] = 0;
        bArr[1] = (byte) DEVICE_FUNCTION.LUX_UPDATE_SEND_DATA.getFunctionCode();
        try {
            System.arraycopy(this.serialNum.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 2, this.serialNum.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr, 12, this.dataIndex, 0, true);
        bArr[14] = (byte) this.fileType;
        ProTool.convertLong2Byte2(bArr, 15, this.dataList.length + 4, 0, true);
        System.arraycopy(this.firmwareLengthArray, 0, bArr, 17, 4);
        byte[] bArr2 = this.dataList;
        System.arraycopy(bArr2, 0, bArr, 21, bArr2.length);
        ProTool.convertLong2Byte2(bArr, length - 2, ProTool.modbus_Caluation_CRC16(bArr, r3), 0, true);
        return bArr;
    }
}