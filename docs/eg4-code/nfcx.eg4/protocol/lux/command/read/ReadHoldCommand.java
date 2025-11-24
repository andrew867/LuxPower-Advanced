package com.nfcx.eg4.protocol.lux.command.read;

import com.nfcx.eg4.protocol.lux.DEVICE_FUNCTION;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ReadHoldCommand extends AbstractQueryCommand {
    public ReadHoldCommand(String str, int i, int i2) {
        super(0, DEVICE_FUNCTION.R_HOLD, str, i, i2);
    }
}