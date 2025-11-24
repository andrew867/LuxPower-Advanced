package com.nfcx.eg4.protocol.tcp.dataframe;

import com.nfcx.eg4.tool.ProTool;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public abstract class AbstractDataFrame implements DataFrame {
    protected byte[] frame;

    @Override // com.nfcx.eg4.protocol.tcp.dataframe.DataFrame
    public String show() {
        return ProTool.showData(getFrame());
    }
}