package com.lux.luxcloud.protocol.lux.command.update;

import android.util.Base64;
import com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION;
import com.lux.luxcloud.protocol.lux.command.Command;
import com.lux.luxcloud.tool.ProTool;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class UpdateSendDataCommand implements Command {
    private int dataIndex;
    private byte[] dataList;
    private int fileType;
    private long physicalAddr;
    private String serialNum;

    public UpdateSendDataCommand(String str, int i, int i2, long j, String str2) {
        this.serialNum = str;
        this.dataIndex = i;
        this.fileType = i2;
        this.physicalAddr = j;
        this.dataList = Base64.decode(str2, 0);
    }

    private boolean hasPhysicalAddr() {
        return this.fileType == 2;
    }

    @Override // com.lux.luxcloud.protocol.lux.command.Command
    public byte[] getFrame() {
        hasPhysicalAddr();
        int length = this.dataList.length + 19 + 4;
        byte[] bArr = new byte[length];
        bArr[0] = 0;
        bArr[1] = (byte) DEVICE_FUNCTION.UPDATE_SEND_DATA.getFunctionCode();
        try {
            System.arraycopy(this.serialNum.getBytes(LocalizedMessage.DEFAULT_ENCODING), 0, bArr, 2, this.serialNum.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ProTool.convertLong2Byte2(bArr, 12, this.dataIndex, 0, true);
        bArr[14] = (byte) this.fileType;
        ProTool.convertLong2Byte2(bArr, 15, this.dataList.length + 4, 0, true);
        ProTool.convertLong2Byte4(bArr, 17, this.physicalAddr, 0, true);
        byte[] bArr2 = this.dataList;
        System.arraycopy(bArr2, 0, bArr, 21, bArr2.length);
        ProTool.convertLong2Byte2(bArr, length - 2, ProTool.modbus_Caluation_CRC16(bArr, r2), 0, true);
        return bArr;
    }
}