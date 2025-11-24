package com.nfcx.eg4.tls.bc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.tls.ProtocolVersion;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class BcPskTlsParams {
    private static final HashMap<Integer, String> codeToSuiteMap;
    private static final int[] defaultSupportedCipherSuiteCodes = {CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256};
    private static final ProtocolVersion[] defaultSupportedProtocolVersions = {ProtocolVersion.TLSv12};
    private static final Comparator<ProtocolVersion> protocolComparator = new Comparator() { // from class: com.nfcx.eg4.tls.bc.BcPskTlsParams$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return BcPskTlsParams.lambda$static$0((ProtocolVersion) obj, (ProtocolVersion) obj2);
        }
    };
    private static final HashMap<String, Integer> suiteToCodeMap;
    private final int[] supportedCipherSuiteCodes;
    private final String[] supportedCipherSuites;
    private final ProtocolVersion[] supportedProtocolVersions;
    private final String[] supportedProtocols;

    static {
        HashMap<String, Integer> map = new HashMap<>();
        suiteToCodeMap = map;
        codeToSuiteMap = new HashMap<>();
        map.put("TLS_PSK_WITH_AES_128_GCM_SHA256", Integer.valueOf(CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256));
        map.put("TLS_DHE_PSK_WITH_AES_128_GCM_SHA256", 170);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            codeToSuiteMap.put(entry.getValue(), entry.getKey());
        }
    }

    static /* synthetic */ int lambda$static$0(ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2) {
        if (protocolVersion.equals(protocolVersion2)) {
            return 0;
        }
        return protocolVersion.isEarlierVersionOf(protocolVersion2) ? 1 : -1;
    }

    public BcPskTlsParams() {
        ProtocolVersion[] protocolVersionArr = (ProtocolVersion[]) defaultSupportedProtocolVersions.clone();
        this.supportedProtocolVersions = protocolVersionArr;
        int[] iArr = (int[]) defaultSupportedCipherSuiteCodes.clone();
        this.supportedCipherSuiteCodes = iArr;
        this.supportedCipherSuites = cipherSuiteCodesToStrings(iArr);
        this.supportedProtocols = protocolVersionsToStrings(protocolVersionArr);
    }

    public BcPskTlsParams(ProtocolVersion[] protocolVersionArr, int[] iArr) {
        ProtocolVersion[] protocolVersionArr2 = (ProtocolVersion[]) protocolVersionArr.clone();
        this.supportedProtocolVersions = protocolVersionArr2;
        Arrays.sort(protocolVersionArr2, protocolComparator);
        this.supportedCipherSuiteCodes = (int[]) iArr.clone();
        this.supportedCipherSuites = cipherSuiteCodesToStrings(iArr);
        this.supportedProtocols = protocolVersionsToStrings(protocolVersionArr);
    }

    public static String toJavaName(ProtocolVersion protocolVersion) {
        switch (protocolVersion.getFullVersion()) {
            case 769:
                return "TLSv1.0";
            case 770:
                return "TLSv1.1";
            case 771:
                return "TLSv1.2";
            case 772:
                return "TLSv1.3";
            default:
                throw new IllegalArgumentException("Unable to get java name for: " + protocolVersion);
        }
    }

    public static ProtocolVersion fromJavaName(String str) {
        str.hashCode();
        switch (str) {
            case "TLSv1.0":
                return ProtocolVersion.TLSv10;
            case "TLSv1.1":
                return ProtocolVersion.TLSv11;
            case "TLSv1.2":
                return ProtocolVersion.TLSv12;
            case "TLSv1.3":
                return ProtocolVersion.TLSv13;
            default:
                throw new IllegalArgumentException("Unable to get protocol version for: " + str);
        }
    }

    public static String toCipherSuiteString(int i) {
        String str = codeToSuiteMap.get(Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException("Unsupported TLS cipher code: " + i);
    }

    public static int fromCipherSuiteString(String str) {
        Integer num = suiteToCodeMap.get(str);
        if (num == null) {
            throw new IllegalArgumentException("Unsupported TLS cipher: " + str);
        }
        return num.intValue();
    }

    private static String[] protocolVersionsToStrings(ProtocolVersion[] protocolVersionArr) {
        String[] strArr = new String[protocolVersionArr.length];
        for (int i = 0; i < protocolVersionArr.length; i++) {
            strArr[i] = toJavaName(protocolVersionArr[i]);
        }
        return strArr;
    }

    private static String[] cipherSuiteCodesToStrings(int[] iArr) {
        String[] strArr = new String[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            strArr[i] = toCipherSuiteString(iArr[i]);
        }
        return strArr;
    }

    public String[] getSupportedCipherSuites() {
        return (String[]) this.supportedCipherSuites.clone();
    }

    public String[] getSupportedProtocols() {
        return (String[]) this.supportedProtocols.clone();
    }

    public int[] getSupportedCipherSuiteCodes() {
        return (int[]) this.supportedCipherSuiteCodes.clone();
    }

    public ProtocolVersion[] getSupportedProtocolVersions() {
        return (ProtocolVersion[]) this.supportedProtocolVersions.clone();
    }

    public static int[] fromSupportedCipherSuiteCodes(String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = fromCipherSuiteString(strArr[i]);
        }
        return iArr;
    }

    public static ProtocolVersion[] fromSupportedProtocolVersions(String[] strArr) {
        ProtocolVersion[] protocolVersionArr = new ProtocolVersion[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            protocolVersionArr[i] = fromJavaName(strArr[i]);
        }
        return protocolVersionArr;
    }
}