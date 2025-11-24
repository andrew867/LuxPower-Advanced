package com.lux.luxcloud.protocol.tcp.dataframe.tcp;

import com.lux.luxcloud.protocol.tcp.ProtocolConstants;
import com.lux.luxcloud.protocol.tcp.TCP_FUNCTION;
import com.lux.luxcloud.protocol.tcp.data.Data;
import com.lux.luxcloud.protocol.tcp.dataframe.AbstractDataFrame;
import com.lux.luxcloud.tool.ProTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public abstract class AbstractTcpDataFrame extends AbstractDataFrame {
    protected Data data;
    protected TCP_FUNCTION function;
    private TCP_PROTOCOL tcpProtocol;

    public AbstractTcpDataFrame(TCP_FUNCTION tcp_function, TCP_PROTOCOL tcp_protocol) {
        this.function = tcp_function;
        this.tcpProtocol = tcp_protocol;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override // com.lux.luxcloud.protocol.tcp.dataframe.DataFrame
    public byte[] getFrame() {
        if (this.frame != null) {
            return this.frame;
        }
        byte[] frame = this.data.getFrame();
        this.frame = new byte[frame.length + 8];
        this.frame[0] = ProtocolConstants.TCP_PREFIX_0;
        this.frame[1] = 26;
        ProTool.convertLong2Byte2(this.frame, 2, this.tcpProtocol.getPrefixData(), 0, true);
        ProTool.convertLong2Byte2(this.frame, 4, frame.length + 2, 0, true);
        this.frame[6] = 1;
        this.frame[7] = this.function.getFunctionCode();
        System.arraycopy(frame, 0, this.frame, 8, frame.length);
        return this.frame;
    }
}