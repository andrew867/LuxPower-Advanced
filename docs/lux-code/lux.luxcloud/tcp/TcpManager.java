package com.lux.luxcloud.tcp;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.lux.luxcloud.connect.AbstractLocalConnect;
import com.lux.luxcloud.global.Constants;
import com.lux.luxcloud.protocol.tcp.dataframe.DataFrame;
import com.lux.luxcloud.tls.PskTlsConfig;
import com.lux.luxcloud.tls.bc.BcPskSSLSocketFactory;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.version.Version;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.SSLSocket;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class TcpManager extends AbstractLocalConnect {
    public static final int SERVER_PORT = 8000;
    private static Socket currentChannel;
    private static final TcpManager tcpManager = new TcpManager();
    private CommandSender commandSender;
    private PskTlsConfig pskTlsConfig;

    private TcpManager() {
        super(Constants.LOCAL_CONNECT_TYPE_TCP);
    }

    public static TcpManager getInstance() {
        return tcpManager;
    }

    @Override // com.lux.luxcloud.connect.LocalConnect
    public boolean initialize(boolean z) throws IOException {
        Socket socket;
        try {
            if (this.connected) {
                return true;
            }
            System.out.println("LuxPower, TcpManager initialize...");
            try {
                Socket socket2 = new Socket();
                socket2.connect(new InetSocketAddress(Version.LOCAL_TCP_IP, SERVER_PORT), 5000);
                setPskTlsConfig(this.datalogSn);
                SSLSocket sSLSocket = (SSLSocket) new BcPskSSLSocketFactory(this.pskTlsConfig.getPskTlsParams(), this.pskTlsConfig.getPskIdentity()).createSocket(socket2, socket2.getInetAddress().getHostAddress(), socket2.getPort(), true);
                System.out.println("tlsSocket currentTimeMillis = " + Tool.formatDateTime(new Date()));
                if (tryTlsHandshakeWithin(sSLSocket, 5L, TimeUnit.SECONDS)) {
                    socket = sSLSocket;
                } else {
                    closeQuietly(sSLSocket);
                    Socket socket3 = new Socket();
                    socket3.connect(new InetSocketAddress(Version.LOCAL_TCP_IP, SERVER_PORT), PathInterpolatorCompat.MAX_NUM_POINTS);
                    System.out.println("tcpSocket currentTimeMillis == " + Tool.formatDateTime(new Date()));
                    socket = socket3;
                }
                TcpCommandSender tcpCommandSender = new TcpCommandSender(socket);
                this.commandSender = tcpCommandSender;
                if (!tcpCommandSender.startMonitor()) {
                    return false;
                }
                this.connected = true;
                currentChannel = socket;
                System.out.println("LuxPower, " + (socket instanceof SSLSocket ? "TLS" : "TCP") + " connection successful.");
                return true;
            } catch (Exception e) {
                System.err.println("LuxPower, initialize error: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private boolean tryTlsHandshakeWithin(final SSLSocket sSLSocket, long j, TimeUnit timeUnit) {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        try {
            try {
                try {
                    executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: com.lux.luxcloud.tcp.TcpManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws IOException {
                            TcpManager.lambda$tryTlsHandshakeWithin$0(sSLSocket);
                        }
                    }).get(j, timeUnit);
                    executorServiceNewSingleThreadExecutor.shutdownNow();
                    return true;
                } catch (ExecutionException e) {
                    System.err.println("LuxPower, TLS handshake failed: " + e.getCause());
                    executorServiceNewSingleThreadExecutor.shutdownNow();
                    return false;
                } catch (TimeoutException unused) {
                    System.err.println("LuxPower, TLS handshake timeout");
                    executorServiceNewSingleThreadExecutor.shutdownNow();
                    return false;
                }
            } catch (InterruptedException unused2) {
                Thread.currentThread().interrupt();
                executorServiceNewSingleThreadExecutor.shutdownNow();
                return false;
            }
        } catch (Throwable th) {
            executorServiceNewSingleThreadExecutor.shutdownNow();
            throw th;
        }
    }

    static /* synthetic */ void lambda$tryTlsHandshakeWithin$0(SSLSocket sSLSocket) throws IOException {
        try {
            sSLSocket.startHandshake();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isTlsConnection() {
        Socket socket = currentChannel;
        return (!(socket instanceof SSLSocket) || socket == null || socket.isClosed()) ? false : true;
    }

    private static void closeQuietly(Socket socket) throws IOException {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception unused) {
            }
        }
    }

    private synchronized void setPskTlsConfig(String str) {
        try {
            this.pskTlsConfig = new PskTlsConfig(str);
            this.connected = false;
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("PSK-TLS config failed", e);
        }
    }

    @Override // com.lux.luxcloud.connect.LocalConnect, com.lux.luxcloud.tcp.CommandSender
    public String sendCommand(String str, DataFrame dataFrame) {
        CommandSender commandSender = this.commandSender;
        if (commandSender != null) {
            return commandSender.sendCommand(str, dataFrame);
        }
        return null;
    }

    @Override // com.lux.luxcloud.connect.LocalConnect, com.lux.luxcloud.tcp.CommandSender
    public String sendCommand(String str, DataFrame dataFrame, int i) {
        CommandSender commandSender = this.commandSender;
        if (commandSender != null) {
            return commandSender.sendCommand(str, dataFrame, i);
        }
        return null;
    }

    @Override // com.lux.luxcloud.connect.LocalConnect, com.lux.luxcloud.tcp.CommandSender
    public boolean sendPureCommand(DataFrame dataFrame) {
        CommandSender commandSender = this.commandSender;
        if (commandSender != null) {
            return commandSender.sendPureCommand(dataFrame);
        }
        return false;
    }

    public void setConnected(boolean z) {
        System.out.println("LuxPowerTcpManager set connected = " + z);
        this.connected = z;
    }

    @Override // com.lux.luxcloud.connect.LocalConnect, com.lux.luxcloud.tcp.CommandSender
    public String getCommandWaitResult(String str) {
        CommandSender commandSender = this.commandSender;
        if (commandSender != null) {
            return commandSender.getCommandWaitResult(str);
        }
        return null;
    }

    @Override // com.lux.luxcloud.connect.LocalConnect, com.lux.luxcloud.tcp.CommandSender
    public void close() {
        System.out.println("LuxPowerTcpManager close...");
        this.connected = false;
        CommandSender commandSender = this.commandSender;
        if (commandSender != null) {
            commandSender.close();
        }
    }
}