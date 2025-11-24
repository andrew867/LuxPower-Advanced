package com.lux.luxcloud.protocol.lux.command.read;

import com.lux.luxcloud.protocol.lux.DEVICE_FUNCTION;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ReadInputCommand extends AbstractQueryCommand {
    public ReadInputCommand(String str, int i, int i2) {
        super(0, DEVICE_FUNCTION.R_INPUT, str, i, i2);
    }
}