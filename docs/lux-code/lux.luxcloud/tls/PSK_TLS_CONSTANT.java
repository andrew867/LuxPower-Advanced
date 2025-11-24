package com.lux.luxcloud.tls;

import org.bouncycastle.tls.ProtocolVersion;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public interface PSK_TLS_CONSTANT {
    public static final int CIPHER_SUITE = 170;
    public static final ProtocolVersion PROTOCOL_VERSION = ProtocolVersion.TLSv12;
    public static final String PSK_IDENTITY = "Client_identity";
    public static final String SALT = "LuxPowerTek!";
    public static final String SN_HASH_ALGORITHM = "HmacSHA256";
}