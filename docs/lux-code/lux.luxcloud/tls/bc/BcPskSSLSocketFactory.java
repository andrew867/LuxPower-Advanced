package com.lux.luxcloud.tls.bc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import org.bouncycastle.tls.BasicTlsPSKExternal;
import org.bouncycastle.tls.PSKTlsClient;
import org.bouncycastle.tls.ProtocolName;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.TlsClientProtocol;
import org.bouncycastle.tls.TlsContext;
import org.bouncycastle.tls.TlsPSKIdentity;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.impl.bc.BcTlsCrypto;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class BcPskSSLSocketFactory extends SSLSocketFactory {
    private static final boolean DEBUG = false;
    private final TlsCrypto crypto = new BcTlsCrypto(new SecureRandom());
    private final BcPskTlsParams params;
    private final TlsPSKIdentity pskIdentity;

    public BcPskSSLSocketFactory(BcPskTlsParams bcPskTlsParams, TlsPSKIdentity tlsPSKIdentity) {
        this.params = bcPskTlsParams;
        this.pskIdentity = tlsPSKIdentity;
    }

    private static class BcPskTlsClientProtocol extends TlsClientProtocol {
        @Override // org.bouncycastle.tls.TlsProtocol
        protected void raiseAlertWarning(short s, String str) throws IOException {
        }

        public BcPskTlsClientProtocol(InputStream inputStream, OutputStream outputStream) {
            super(inputStream, outputStream);
        }

        @Override // org.bouncycastle.tls.TlsProtocol, org.bouncycastle.tls.TlsCloseable
        public void close() throws IOException {
            if (getPeer() == null) {
                cleanupHandshake();
            } else {
                super.close();
            }
        }

        @Override // org.bouncycastle.tls.TlsProtocol
        protected void raiseAlertFatal(short s, String str, Throwable th) throws IOException {
            th.printStackTrace();
        }

        String getCipherSuite() {
            TlsContext context = getContext();
            if (context == null) {
                return null;
            }
            return BcPskTlsParams.toCipherSuiteString(context.getSecurityParameters().getCipherSuite());
        }

        String getProtocol() {
            TlsContext context = getContext();
            if (context == null) {
                return null;
            }
            return BcPskTlsParams.toJavaName(context.getSecurityParameters().getNegotiatedVersion());
        }

        byte[] getSessionId() {
            TlsContext context = getContext();
            if (context == null) {
                return null;
            }
            return context.getSession().getSessionID();
        }

        String getApplicationProtocol() {
            TlsContext context = getContext();
            if (context == null) {
                return null;
            }
            return getApplicationProtocol(context.getSecurityParametersConnection());
        }

        static String getApplicationProtocol(SecurityParameters securityParameters) {
            if (securityParameters == null || !securityParameters.isApplicationProtocolSet()) {
                return null;
            }
            ProtocolName applicationProtocol = securityParameters.getApplicationProtocol();
            return applicationProtocol == null ? "" : applicationProtocol.getUtf8Decoding();
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.params.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.params.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        if (!z) {
            throw new UnsupportedOperationException("Only auto-close sockets can be created");
        }
        if (!socket.isConnected()) {
            throw new UnsupportedOperationException("Socket must be connected prior to be used with this factory");
        }
        return new AnonymousClass1(socket, new BcPskTlsClientProtocol(socket.getInputStream(), socket.getOutputStream()), str, i, socket);
    }

    /* renamed from: com.lux.luxcloud.tls.bc.BcPskSSLSocketFactory$1, reason: invalid class name */
    class AnonymousClass1 extends WrappedSSLSocket {
        private boolean enableSessionCreation;
        private String[] enabledCipherSuites;
        private String[] enabledProtocols;
        final /* synthetic */ String val$host;
        final /* synthetic */ int val$port;
        final /* synthetic */ Socket val$socket;
        final /* synthetic */ BcPskTlsClientProtocol val$tlsClientProtocol;

        @Override // javax.net.ssl.SSLSocket
        public boolean getNeedClientAuth() {
            return false;
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getUseClientMode() {
            return false;
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getWantClientAuth() {
            return false;
        }

        @Override // javax.net.ssl.SSLSocket
        public void setNeedClientAuth(boolean z) {
        }

        @Override // javax.net.ssl.SSLSocket
        public void setUseClientMode(boolean z) {
        }

        @Override // javax.net.ssl.SSLSocket
        public void setWantClientAuth(boolean z) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Socket socket, BcPskTlsClientProtocol bcPskTlsClientProtocol, String str, int i, Socket socket2) {
            super(socket);
            this.val$tlsClientProtocol = bcPskTlsClientProtocol;
            this.val$host = str;
            this.val$port = i;
            this.val$socket = socket2;
            this.enabledCipherSuites = BcPskSSLSocketFactory.this.params.getSupportedCipherSuites();
            this.enabledProtocols = BcPskSSLSocketFactory.this.params.getSupportedProtocols();
            this.enableSessionCreation = true;
        }

        @Override // com.lux.luxcloud.tls.bc.WrappedSSLSocket, java.net.Socket
        public InputStream getInputStream() throws IOException {
            return this.val$tlsClientProtocol.getInputStream();
        }

        @Override // com.lux.luxcloud.tls.bc.WrappedSSLSocket, java.net.Socket
        public OutputStream getOutputStream() throws IOException {
            return this.val$tlsClientProtocol.getOutputStream();
        }

        @Override // com.lux.luxcloud.tls.bc.WrappedSSLSocket, java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            super.close();
            synchronized (this.val$tlsClientProtocol) {
                this.val$tlsClientProtocol.close();
            }
        }

        @Override // javax.net.ssl.SSLSocket
        public String getApplicationProtocol() {
            return this.val$tlsClientProtocol.getApplicationProtocol();
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getEnableSessionCreation() {
            return this.enableSessionCreation;
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getEnabledCipherSuites() {
            return (String[]) this.enabledCipherSuites.clone();
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getEnabledProtocols() {
            return (String[]) this.enabledProtocols.clone();
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getSupportedProtocols() {
            return BcPskSSLSocketFactory.this.params.getSupportedProtocols();
        }

        @Override // javax.net.ssl.SSLSocket
        public void setEnabledCipherSuites(String[] strArr) {
            HashSet hashSet = new HashSet();
            Collections.addAll(hashSet, getSupportedCipherSuites());
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                if (hashSet.contains(str)) {
                    arrayList.add(str);
                }
            }
            this.enabledCipherSuites = (String[]) arrayList.toArray(new String[0]);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setEnableSessionCreation(boolean z) {
            this.enableSessionCreation = z;
        }

        @Override // javax.net.ssl.SSLSocket
        public void setEnabledProtocols(String[] strArr) {
            HashSet hashSet = new HashSet();
            Collections.addAll(hashSet, getSupportedProtocols());
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                if (hashSet.contains(str)) {
                    arrayList.add(str);
                }
            }
            this.enabledProtocols = (String[]) arrayList.toArray(new String[0]);
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getSupportedCipherSuites() {
            return BcPskSSLSocketFactory.this.params.getSupportedCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocket
        public void startHandshake() throws IOException {
            this.val$tlsClientProtocol.connect(new PSKTlsClient(BcPskSSLSocketFactory.this.crypto, BcPskSSLSocketFactory.this.pskIdentity) { // from class: com.lux.luxcloud.tls.bc.BcPskSSLSocketFactory.1.1
                @Override // org.bouncycastle.tls.PSKTlsClient, org.bouncycastle.tls.AbstractTlsPeer
                protected ProtocolVersion[] getSupportedVersions() {
                    return BcPskTlsParams.fromSupportedProtocolVersions(AnonymousClass1.this.getEnabledProtocols());
                }

                @Override // org.bouncycastle.tls.PSKTlsClient, org.bouncycastle.tls.AbstractTlsPeer
                protected int[] getSupportedCipherSuites() {
                    return BcPskTlsParams.fromSupportedCipherSuiteCodes(AnonymousClass1.this.getEnabledCipherSuites());
                }

                @Override // org.bouncycastle.tls.AbstractTlsClient, org.bouncycastle.tls.TlsClient
                public Vector getExternalPSKs() {
                    Vector vector = new Vector();
                    vector.add(new BasicTlsPSKExternal(this.pskIdentity.getPSKIdentity(), BcPskSSLSocketFactory.this.crypto.createSecret(this.pskIdentity.getPSK())));
                    return vector;
                }
            });
        }

        @Override // javax.net.ssl.SSLSocket
        public SSLSession getSession() {
            return new SSLSession() { // from class: com.lux.luxcloud.tls.bc.BcPskSSLSocketFactory.1.2
                protected static final int MAX_CIPHERED_DATA_LENGTH = 18432;
                protected static final int MAX_COMPRESSED_DATA_LENGTH = 17408;
                protected static final int MAX_DATA_LENGTH = 16384;
                protected static final int MAX_SSL_PACKET_SIZE = 18437;
                private boolean isValid = true;
                private final long creationTime = System.currentTimeMillis();
                private final Map<String, Object> valueMap = Collections.synchronizedMap(new HashMap());

                @Override // javax.net.ssl.SSLSession
                public Certificate[] getLocalCertificates() {
                    return null;
                }

                @Override // javax.net.ssl.SSLSession
                public int getPacketBufferSize() {
                    return MAX_SSL_PACKET_SIZE;
                }

                @Override // javax.net.ssl.SSLSession
                public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
                    return new X509Certificate[0];
                }

                @Override // javax.net.ssl.SSLSession
                public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
                    return null;
                }

                @Override // javax.net.ssl.SSLSession
                public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                    return null;
                }

                @Override // javax.net.ssl.SSLSession
                public int getApplicationBufferSize() {
                    return AnonymousClass1.this.val$tlsClientProtocol.getApplicationDataLimit();
                }

                @Override // javax.net.ssl.SSLSession
                public String getCipherSuite() {
                    return AnonymousClass1.this.val$tlsClientProtocol.getCipherSuite();
                }

                @Override // javax.net.ssl.SSLSession
                public long getCreationTime() {
                    return this.creationTime;
                }

                @Override // javax.net.ssl.SSLSession
                public byte[] getId() {
                    return AnonymousClass1.this.val$tlsClientProtocol.getSessionId();
                }

                @Override // javax.net.ssl.SSLSession
                public long getLastAccessedTime() {
                    return getCreationTime();
                }

                @Override // javax.net.ssl.SSLSession
                public Principal getLocalPrincipal() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public String getPeerHost() {
                    return AnonymousClass1.this.val$host;
                }

                @Override // javax.net.ssl.SSLSession
                public int getPeerPort() {
                    return AnonymousClass1.this.val$port;
                }

                @Override // javax.net.ssl.SSLSession
                public String getProtocol() {
                    return AnonymousClass1.this.val$tlsClientProtocol.getProtocol();
                }

                @Override // javax.net.ssl.SSLSession
                public SSLSessionContext getSessionContext() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public Object getValue(String str) {
                    return this.valueMap.get(str);
                }

                @Override // javax.net.ssl.SSLSession
                public String[] getValueNames() {
                    String[] strArr;
                    synchronized (this.valueMap) {
                        strArr = (String[]) this.valueMap.keySet().toArray(new String[this.valueMap.size()]);
                    }
                    return strArr;
                }

                @Override // javax.net.ssl.SSLSession
                public void invalidate() {
                    this.isValid = false;
                }

                @Override // javax.net.ssl.SSLSession
                public boolean isValid() {
                    return (!this.isValid || AnonymousClass1.this.val$socket.isClosed() || AnonymousClass1.this.val$tlsClientProtocol.isClosed()) ? false : true;
                }

                @Override // javax.net.ssl.SSLSession
                public void putValue(String str, Object obj) {
                    notifyUnbound(str, this.valueMap.put(str, obj));
                    notifyBound(str, obj);
                }

                @Override // javax.net.ssl.SSLSession
                public void removeValue(String str) {
                    notifyUnbound(str, this.valueMap.remove(str));
                }

                private void notifyBound(String str, Object obj) {
                    if (obj instanceof SSLSessionBindingListener) {
                        ((SSLSessionBindingListener) obj).valueBound(new SSLSessionBindingEvent(this, str));
                    }
                }

                private void notifyUnbound(String str, Object obj) {
                    if (obj instanceof SSLSessionBindingListener) {
                        ((SSLSessionBindingListener) obj).valueUnbound(new SSLSessionBindingEvent(this, str));
                    }
                }
            };
        }
    }

    public static class EmptyX509TrustManager implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            throw new UnsupportedOperationException();
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            throw new UnsupportedOperationException();
        }
    }
}