package com.lux.luxcloud.protocol.tcp.dataframe;

import com.lux.luxcloud.tool.ProTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public abstract class AbstractDataFrame implements DataFrame {
    protected byte[] frame;

    @Override // com.lux.luxcloud.protocol.tcp.dataframe.DataFrame
    public String show() {
        return ProTool.showData(getFrame());
    }
}