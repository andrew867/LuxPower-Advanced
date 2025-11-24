package com.lux.luxcloud.protocol.tcp;

import com.lux.luxcloud.protocol.lux.command.Command;
import com.lux.luxcloud.protocol.lux.command.read.ReadHoldCommand;
import com.lux.luxcloud.protocol.lux.command.read.ReadInputCommand;
import com.lux.luxcloud.protocol.lux.command.update.LuxUpdatePrepareCommand;
import com.lux.luxcloud.protocol.lux.command.update.LuxUpdateResetCommand;
import com.lux.luxcloud.protocol.lux.command.update.LuxUpdateSendDataCommand;
import com.lux.luxcloud.protocol.lux.command.update.UpdatePrepareCommand;
import com.lux.luxcloud.protocol.lux.command.update.UpdateResetCommand;
import com.lux.luxcloud.protocol.lux.command.update.UpdateSendDataCommand;
import com.lux.luxcloud.protocol.lux.command.write.WriteMultiCommand;
import com.lux.luxcloud.protocol.lux.command.write.WriteSingleCommand;
import com.lux.luxcloud.protocol.tcp.data.TransferData;
import com.lux.luxcloud.protocol.tcp.data.datalog.WriteDatalogParamData;
import com.lux.luxcloud.protocol.tcp.dataframe.DataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.datalog.ReadDatalogParamDataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.datalog.WriteDatalogParamDataFrame;
import com.lux.luxcloud.protocol.tcp.dataframe.tcp.send.SendTranslateDataFrame;
import com.lux.luxcloud.tool.ProTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
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

    public static DataFrame createLuxUpdatePrepareDataFrame(TCP_PROTOCOL tcp_protocol, String str, String str2, String str3, int i, long j) {
        return createTransferDataFrame(tcp_protocol, str, new LuxUpdatePrepareCommand(str2, str3, i, j));
    }

    public static DataFrame createLuxUpdateSendDataDataFrame(TCP_PROTOCOL tcp_protocol, String str, String str2, int i, int i2, String str3, String str4) {
        return createTransferDataFrame(tcp_protocol, str, new LuxUpdateSendDataCommand(str2, i, i2, str3, str4));
    }

    public static DataFrame createLuxUpdateResetDataFrame(TCP_PROTOCOL tcp_protocol, String str, String str2, int i, int i2, int i3, long j) {
        return createTransferDataFrame(tcp_protocol, str, new LuxUpdateResetCommand(str2, i, i2, i3, j));
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