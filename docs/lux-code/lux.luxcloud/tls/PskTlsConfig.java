package com.lux.luxcloud.tls;

import com.lux.luxcloud.tls.bc.BcPskTlsParams;
import com.lux.luxcloud.tool.DonglePskUtil;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.tls.BasicTlsPSKIdentity;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsPSKIdentity;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class PskTlsConfig {
    private final byte[] donglePsk;
    private final String dongleSn;
    private final TlsPSKIdentity pskIdentity;
    private final BcPskTlsParams pskTlsParams = new BcPskTlsParams(new ProtocolVersion[]{PSK_TLS_CONSTANT.PROTOCOL_VERSION}, new int[]{170});

    public PskTlsConfig(String str) throws NoSuchAlgorithmException, InvalidKeyException {
        this.dongleSn = str;
        byte[] bArrCalcPsk = DonglePskUtil.calcPsk(str);
        this.donglePsk = bArrCalcPsk;
        this.pskIdentity = new BasicTlsPSKIdentity(PSK_TLS_CONSTANT.PSK_IDENTITY, bArrCalcPsk);
    }

    public String getDongleSn() {
        return this.dongleSn;
    }

    public BcPskTlsParams getPskTlsParams() {
        return this.pskTlsParams;
    }

    public TlsPSKIdentity getPskIdentity() {
        return this.pskIdentity;
    }
}