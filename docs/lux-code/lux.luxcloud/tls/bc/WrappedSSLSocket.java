package com.lux.luxcloud.tls.bc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
abstract class WrappedSSLSocket extends SSLSocket {
    private final List<HandshakeCompletedListener> listeners = new ArrayList(2);
    private final Socket socket;

    @Override // java.net.Socket
    public SocketChannel getChannel() {
        return null;
    }

    @Override // java.net.Socket
    public abstract InputStream getInputStream() throws IOException;

    @Override // java.net.Socket
    public boolean getOOBInline() throws SocketException {
        return false;
    }

    @Override // java.net.Socket
    public abstract OutputStream getOutputStream() throws IOException;

    public WrappedSSLSocket(Socket socket) {
        this.socket = socket;
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress, int i) throws IOException {
        this.socket.connect(socketAddress, i);
    }

    @Override // java.net.Socket
    public InetAddress getInetAddress() {
        return this.socket.getInetAddress();
    }

    @Override // java.net.Socket
    public InetAddress getLocalAddress() {
        return this.socket.getLocalAddress();
    }

    @Override // java.net.Socket
    public int getPort() {
        return this.socket.getPort();
    }

    @Override // java.net.Socket
    public int getLocalPort() {
        return this.socket.getLocalPort();
    }

    @Override // java.net.Socket
    public void setTcpNoDelay(boolean z) throws SocketException {
        this.socket.setTcpNoDelay(z);
    }

    @Override // java.net.Socket
    public boolean getTcpNoDelay() throws SocketException {
        return this.socket.getTcpNoDelay();
    }

    @Override // java.net.Socket
    public void setSoLinger(boolean z, int i) throws SocketException {
        this.socket.setSoLinger(z, i);
    }

    @Override // java.net.Socket
    public int getSoLinger() throws SocketException {
        return this.socket.getSoLinger();
    }

    @Override // java.net.Socket
    public void setSoTimeout(int i) throws SocketException {
        this.socket.setSoTimeout(i);
    }

    @Override // java.net.Socket
    public int getSoTimeout() throws SocketException {
        return this.socket.getSoTimeout();
    }

    @Override // java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.socket.close();
    }

    @Override // java.net.Socket
    public boolean isClosed() {
        return this.socket.isClosed();
    }

    @Override // java.net.Socket
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    @Override // java.net.Socket
    public boolean isBound() {
        return this.socket.isBound();
    }

    @Override // java.net.Socket
    public void shutdownInput() throws IOException {
        this.socket.shutdownInput();
    }

    @Override // java.net.Socket
    public void shutdownOutput() throws IOException {
        this.socket.shutdownOutput();
    }

    @Override // java.net.Socket
    public boolean isInputShutdown() {
        return this.socket.isInputShutdown();
    }

    @Override // java.net.Socket
    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown();
    }

    @Override // java.net.Socket
    public SocketAddress getRemoteSocketAddress() {
        return this.socket.getRemoteSocketAddress();
    }

    @Override // java.net.Socket
    public SocketAddress getLocalSocketAddress() {
        return this.socket.getLocalSocketAddress();
    }

    @Override // java.net.Socket
    public void setOOBInline(boolean z) throws SocketException {
        throw new UnsupportedOperationException();
    }

    @Override // java.net.Socket
    public void setSendBufferSize(int i) throws SocketException {
        this.socket.setSendBufferSize(i);
    }

    @Override // java.net.Socket
    public int getSendBufferSize() throws SocketException {
        return this.socket.getSendBufferSize();
    }

    @Override // java.net.Socket
    public void setReceiveBufferSize(int i) throws SocketException {
        this.socket.setReceiveBufferSize(i);
    }

    @Override // java.net.Socket
    public int getReceiveBufferSize() throws SocketException {
        return this.socket.getReceiveBufferSize();
    }

    @Override // java.net.Socket
    public void setKeepAlive(boolean z) throws SocketException {
        this.socket.setKeepAlive(z);
    }

    @Override // java.net.Socket
    public boolean getKeepAlive() throws SocketException {
        return this.socket.getKeepAlive();
    }

    @Override // java.net.Socket
    public void setTrafficClass(int i) throws SocketException {
        this.socket.setTrafficClass(i);
    }

    @Override // java.net.Socket
    public int getTrafficClass() throws SocketException {
        return this.socket.getTrafficClass();
    }

    @Override // java.net.Socket
    public void setReuseAddress(boolean z) throws SocketException {
        this.socket.setReuseAddress(z);
    }

    @Override // java.net.Socket
    public boolean getReuseAddress() throws SocketException {
        return this.socket.getReuseAddress();
    }

    @Override // java.net.Socket
    public void setPerformancePreferences(int i, int i2, int i3) {
        this.socket.setPerformancePreferences(i, i2, i3);
    }

    @Override // java.net.Socket
    public void bind(SocketAddress socketAddress) throws IOException {
        this.socket.bind(socketAddress);
    }

    @Override // java.net.Socket
    public void sendUrgentData(int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.net.ssl.SSLSocket
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        if (handshakeCompletedListener == null) {
            throw new IllegalArgumentException("'listener' cannot be null");
        }
        this.listeners.add(handshakeCompletedListener);
    }

    @Override // javax.net.ssl.SSLSocket
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        if (handshakeCompletedListener == null) {
            throw new IllegalArgumentException("'listener' cannot be null");
        }
        if (!this.listeners.remove(handshakeCompletedListener)) {
            throw new IllegalArgumentException("'listener' is not registered");
        }
    }

    @Override // javax.net.ssl.SSLSocket, java.net.Socket
    public String toString() {
        return "WrappedSSL" + this.socket.toString();
    }
}