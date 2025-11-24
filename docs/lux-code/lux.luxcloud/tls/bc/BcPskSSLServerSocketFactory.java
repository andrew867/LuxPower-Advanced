package com.lux.luxcloud.tls.bc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import org.bouncycastle.tls.BasicTlsPSKExternal;
import org.bouncycastle.tls.PSKTlsServer;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.PskIdentity;
import org.bouncycastle.tls.TlsPSKExternal;
import org.bouncycastle.tls.TlsPSKIdentityManager;
import org.bouncycastle.tls.TlsServer;
import org.bouncycastle.tls.TlsServerProtocol;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.impl.bc.BcTlsCrypto;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class BcPskSSLServerSocketFactory extends SSLServerSocketFactory {
    private static final boolean DEBUG = false;
    private final TlsCrypto crypto = new BcTlsCrypto(new SecureRandom());
    private final BcPskTlsParams params;
    private final TlsPSKIdentityManager pskIdentityMgr;

    public BcPskSSLServerSocketFactory(BcPskTlsParams bcPskTlsParams, TlsPSKIdentityManager tlsPSKIdentityManager) {
        this.params = bcPskTlsParams;
        this.pskIdentityMgr = tlsPSKIdentityManager;
    }

    private static class BcPskTlsServerProtocol extends TlsServerProtocol {
        @Override // org.bouncycastle.tls.TlsProtocol
        protected void raiseAlertWarning(short s, String str) throws IOException {
        }

        public BcPskTlsServerProtocol(InputStream inputStream, OutputStream outputStream) {
            super(inputStream, outputStream);
        }

        @Override // org.bouncycastle.tls.TlsProtocol
        protected void raiseAlertFatal(short s, String str, Throwable th) throws IOException {
            th.printStackTrace();
        }
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket() throws IOException {
        return createSSLServerSocket(createTlsServer());
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i) throws IOException {
        return createServerSocket(i, 50, null);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i, int i2) throws IOException {
        return createServerSocket(i, i2, null);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i, int i2, InetAddress inetAddress) throws IOException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Port value out of range: " + i);
        }
        ServerSocket serverSocketCreateServerSocket = createServerSocket();
        if (i2 < 1) {
            i2 = 50;
        }
        try {
            serverSocketCreateServerSocket.bind(new InetSocketAddress(inetAddress, i), i2);
            return serverSocketCreateServerSocket;
        } catch (IOException | SecurityException e) {
            serverSocketCreateServerSocket.close();
            throw e;
        }
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.params.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.params.getSupportedCipherSuites();
    }

    private SSLServerSocket createSSLServerSocket(final TlsServer tlsServer) throws IOException {
        return new SSLServerSocket() { // from class: com.lux.luxcloud.tls.bc.BcPskSSLServerSocketFactory.1
            private boolean enableSessionCreation = true;
            private String[] enabledCipherSuites;
            private String[] enabledProtocols;
            private TlsServerProtocol tlsServerProtocol;

            @Override // javax.net.ssl.SSLServerSocket
            public boolean getNeedClientAuth() {
                return false;
            }

            @Override // javax.net.ssl.SSLServerSocket
            public boolean getUseClientMode() {
                return false;
            }

            @Override // javax.net.ssl.SSLServerSocket
            public boolean getWantClientAuth() {
                return false;
            }

            @Override // javax.net.ssl.SSLServerSocket
            public void setNeedClientAuth(boolean z) {
            }

            @Override // javax.net.ssl.SSLServerSocket
            public void setUseClientMode(boolean z) {
            }

            @Override // javax.net.ssl.SSLServerSocket
            public void setWantClientAuth(boolean z) {
            }

            {
                this.enabledCipherSuites = BcPskSSLServerSocketFactory.this.params.getSupportedCipherSuites();
                this.enabledProtocols = BcPskSSLServerSocketFactory.this.params.getSupportedProtocols();
            }

            @Override // java.net.ServerSocket, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                TlsServerProtocol tlsServerProtocol = this.tlsServerProtocol;
                if (tlsServerProtocol != null && !tlsServerProtocol.isClosed()) {
                    tlsServerProtocol.close();
                }
                super.close();
            }

            @Override // javax.net.ssl.SSLServerSocket
            public boolean getEnableSessionCreation() {
                return this.enableSessionCreation;
            }

            @Override // javax.net.ssl.SSLServerSocket
            public String[] getEnabledCipherSuites() {
                return (String[]) this.enabledCipherSuites.clone();
            }

            @Override // javax.net.ssl.SSLServerSocket
            public String[] getEnabledProtocols() {
                return (String[]) this.enabledProtocols.clone();
            }

            @Override // javax.net.ssl.SSLServerSocket
            public String[] getSupportedProtocols() {
                return BcPskSSLServerSocketFactory.this.params.getSupportedProtocols();
            }

            @Override // javax.net.ssl.SSLServerSocket
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

            @Override // javax.net.ssl.SSLServerSocket
            public void setEnableSessionCreation(boolean z) {
                this.enableSessionCreation = z;
            }

            @Override // javax.net.ssl.SSLServerSocket
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

            @Override // javax.net.ssl.SSLServerSocket
            public String[] getSupportedCipherSuites() {
                return BcPskSSLServerSocketFactory.this.params.getSupportedCipherSuites();
            }

            @Override // java.net.ServerSocket
            public Socket accept() throws IOException {
                Socket socketAccept = super.accept();
                BcPskTlsServerProtocol bcPskTlsServerProtocol = new BcPskTlsServerProtocol(socketAccept.getInputStream(), socketAccept.getOutputStream());
                this.tlsServerProtocol = bcPskTlsServerProtocol;
                bcPskTlsServerProtocol.accept(tlsServer);
                return new WrappedSocket(socketAccept, this.tlsServerProtocol.getInputStream(), this.tlsServerProtocol.getOutputStream());
            }
        };
    }

    private TlsServer createTlsServer() {
        return new PSKTlsServer(this.crypto, this.pskIdentityMgr) { // from class: com.lux.luxcloud.tls.bc.BcPskSSLServerSocketFactory.2
            @Override // org.bouncycastle.tls.PSKTlsServer, org.bouncycastle.tls.AbstractTlsPeer
            protected ProtocolVersion[] getSupportedVersions() {
                return BcPskSSLServerSocketFactory.this.params.getSupportedProtocolVersions();
            }

            @Override // org.bouncycastle.tls.PSKTlsServer, org.bouncycastle.tls.AbstractTlsPeer
            protected int[] getSupportedCipherSuites() {
                return BcPskSSLServerSocketFactory.this.params.getSupportedCipherSuiteCodes();
            }

            @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
            public TlsPSKExternal getExternalPSK(Vector vector) {
                if (vector == null || vector.isEmpty()) {
                    return null;
                }
                Iterator it = vector.iterator();
                while (it.hasNext()) {
                    PskIdentity pskIdentity = (PskIdentity) it.next();
                    byte[] psk = BcPskSSLServerSocketFactory.this.pskIdentityMgr.getPSK(pskIdentity.getIdentity());
                    if (psk != null) {
                        return new BasicTlsPSKExternal(pskIdentity.getIdentity(), BcPskSSLServerSocketFactory.this.crypto.createSecret(psk));
                    }
                }
                return null;
            }
        };
    }
}