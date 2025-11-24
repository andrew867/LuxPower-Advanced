package com.lux.luxcloud.tool;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String UNIQUE_KEY = "1238e4bc30c247fe987f2ce4d221427f";

    public static String encrypt(String str) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(UNIQUE_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(1, secretKeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes()));
    }
}