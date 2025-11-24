package com.nfcx.eg4.protocol.tcp;

import com.nfcx.eg4.protocol.lux.command.Command;
import com.nfcx.eg4.protocol.lux.command.read.ReadHoldCommand;
import com.nfcx.eg4.protocol.lux.command.read.ReadInputCommand;
import com.nfcx.eg4.protocol.lux.command.update.UpdatePrepareCommand;
import com.nfcx.eg4.protocol.lux.command.update.UpdateResetCommand;
import com.nfcx.eg4.protocol.lux.command.update.UpdateSendDataCommand;
import com.nfcx.eg4.protocol.lux.command.write.WriteMultiCommand;
import com.nfcx.eg4.protocol.lux.command.write.WriteSingleCommand;
import com.nfcx.eg4.protocol.tcp.data.TransferData;
import com.nfcx.eg4.protocol.tcp.data.datalog.WriteDatalogParamData;
import com.nfcx.eg4.protocol.tcp.dataframe.DataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.datalog.ReadDatalogParamDataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.datalog.WriteDatalogParamDataFrame;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.send.SendTranslateDataFrame;
import com.nfcx.eg4.tool.ProTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class DataFrameFactory {
    private static String emtpyInverterSn = "";

    static {
        for (int i = 0; i < 10; i++) {
            emtpyInverterSn += ProTool.getStringFromHex(0L);
        }
    }

    public static DataFrame createReadSingleHoldDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i) {
        return createTransferDataFrame(tcp_protocol, str, new ReadHoldCommand(emtpyInverterSn, i, 1));
    }

    public static DataFrame createReadMultiHoldDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i, int i2) {
        return createTransferDataFrame(tcp_protocol, str, new ReadHoldCommand(emtpyInverterSn, i, i2));
    }

    public static DataFrame createReadMultiInputDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i, int i2) {
        return createTransferDataFrame(tcp_protocol, str, new ReadInputCommand(emtpyInverterSn, i, i2));
    }

    public static DataFrame createWriteSingleDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i, int i2) {
        return createTransferDataFrame(tcp_protocol, str, new WriteSingleCommand(emtpyInverterSn, i, i2));
    }

    public static DataFrame createWriteMultiDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i, int i2, byte[] bArr) {
        return createTransferDataFrame(tcp_protocol, str, new WriteMultiCommand(emtpyInverterSn, i, i2, bArr));
    }

    public static DataFrame createUpdatePrepareDataFrame(TCP_PROTOCOL tcp_protocol, String str, String str2, String str3, int i, long j) {
        return createTransferDataFrame(tcp_protocol, str, new UpdatePrepareCommand(str2, str3, i, j));
    }

    public static DataFrame createUpdateSendDataDataFrame(TCP_PROTOCOL tcp_protocol, String str, String str2, int i, int i2, long j, String str3) {
        return createTransferDataFrame(tcp_protocol, str, new UpdateSendDataCommand(str2, i, i2, j, str3));
    }

    public static DataFrame createUpdateResetDataFrame(TCP_PROTOCOL tcp_protocol, String str, String str2, int i, int i2, long j) {
        return createTransferDataFrame(tcp_protocol, str, new UpdateResetCommand(str2, i, i2, j));
    }

    private static DataFrame createTransferDataFrame(TCP_PROTOCOL tcp_protocol, String str, Command command) {
        TransferData transferData = new TransferData(str);
        transferData.setCommand(command.getFrame());
        return new SendTranslateDataFrame(tcp_protocol, transferData);
    }

    public static DataFrame createReadDatalogParamDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i) {
        return new ReadDatalogParamDataFrame(tcp_protocol, str, i);
    }

    public static DataFrame createWriteDatalogParamDataFrame(TCP_PROTOCOL tcp_protocol, String str, int i, byte[] bArr) {
        return new WriteDatalogParamDataFrame(tcp_protocol, new WriteDatalogParamData(str, i, bArr));
    }
}