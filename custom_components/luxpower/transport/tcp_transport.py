"""
TCP transport implementation for LuxPower integration.

This module wraps the existing TCP asyncio.Protocol logic from client.py
into the new transport abstraction layer.
"""

import asyncio
import logging
import random
import socket
from typing import Optional, Callable

from . import LuxPowerTransport

_LOGGER = logging.getLogger(__name__)


class TCPTransport(LuxPowerTransport):
    """
    TCP transport implementation using asyncio.Protocol.
    
    This class wraps the existing TCP communication logic from the
    original LuxPowerClient to work with the new transport abstraction.
    """

    def __init__(self, host: str, port: int, dongle_serial: str):
        """
        Initialize TCP transport.
        
        Args:
            host: TCP server hostname or IP address
            port: TCP server port
            dongle_serial: Dongle serial number
        """
        super().__init__(dongle_serial)
        self.host = host
        self.port = port
        self._transport: Optional[asyncio.WriteTransport] = None
        self._protocol: Optional[TCPProtocol] = None
        self._connection_task: Optional[asyncio.Task] = None
        self._listen_task: Optional[asyncio.Task] = None

    async def connect(self) -> bool:
        """
        Establish TCP connection.
        
        Returns:
            True if connection successful, False otherwise
        """
        try:
            self._logger.info(f"Connecting to TCP server {self.host}:{self.port}")
            
            # Create protocol instance
            self._protocol = TCPProtocol(self)
            
            # Create connection
            self._transport, self._protocol = await asyncio.get_event_loop().create_connection(
                lambda: self._protocol,
                self.host,
                self.port
            )
            
            # Enable TCP keepalive if possible
            try:
                sock = self._transport.get_extra_info("socket")
                if isinstance(sock, socket.socket):
                    sock.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)
                    # Platform-specific tuning (best-effort)
                    if hasattr(socket, "TCP_KEEPIDLE"):
                        sock.setsockopt(socket.IPPROTO_TCP, socket.TCP_KEEPIDLE, 30)
                    if hasattr(socket, "TCP_KEEPINTVL"):
                        sock.setsockopt(socket.IPPROTO_TCP, socket.TCP_KEEPINTVL, 10)
                    if hasattr(socket, "TCP_KEEPCNT"):
                        sock.setsockopt(socket.IPPROTO_TCP, socket.TCP_KEEPCNT, 3)
                    # Linux tcp_user_timeout if available (milliseconds)
                    if hasattr(socket, "TCP_USER_TIMEOUT"):
                        sock.setsockopt(socket.IPPROTO_TCP, socket.TCP_USER_TIMEOUT, 30000)
            except Exception as e:
                self._logger.debug(f"Keepalive setup skipped: {e}")

            self._connected = True
            self._logger.info(f"Connected to TCP server {self.host}:{self.port}")
            
            # Start listening task
            self._listen_task = asyncio.create_task(self._listen_loop())
            
            return True
            
        except Exception as e:
            self._logger.error(f"Failed to connect to TCP server: {e}")
            self._connected = False
            return False

    async def disconnect(self) -> None:
        """Disconnect from TCP server."""
        self._logger.info("Disconnecting from TCP server")
        
        # Stop listening task
        if self._listen_task and not self._listen_task.done():
            self._listen_task.cancel()
            try:
                await self._listen_task
            except asyncio.CancelledError:
                pass
        
        # Close transport
        if self._transport and not self._transport.is_closing():
            self._transport.close()
        
        self._connected = False
        self._transport = None
        self._protocol = None

    async def send_packet(self, packet: bytes) -> bool:
        """
        Send packet through TCP connection.
        
        Args:
            packet: Packet data to send
            
        Returns:
            True if send successful, False otherwise
        """
        if not self._connected or not self._transport or self._transport.is_closing():
            self._logger.error("Cannot send packet: not connected")
            return False
        
        try:
            self._transport.write(packet)
            self._logger.debug(f"Sent packet: {packet.hex()}")
            return True
        except Exception as e:
            self._logger.error(f"Failed to send packet: {e}")
            return False

    async def read_packet(self) -> Optional[bytes]:
        """
        Read packet from TCP connection.
        
        Note: TCP transport uses callback-based data reception,
        so this method is not typically used directly.
        
        Returns:
            None (data is handled via callbacks)
        """
        return None

    async def start_listening(self) -> None:
        """Start listening for incoming data."""
        if not self._listen_task or self._listen_task.done():
            self._listen_task = asyncio.create_task(self._listen_loop())

    async def stop_listening(self) -> None:
        """Stop listening for incoming data."""
        if self._listen_task and not self._listen_task.done():
            self._listen_task.cancel()
            try:
                await self._listen_task
            except asyncio.CancelledError:
                pass

    async def _listen_loop(self) -> None:
        """Background task to maintain connection."""
        while True:
            try:
                if self._connected and self._transport and not self._transport.is_closing():
                    await asyncio.sleep(1)
                else:
                    self._logger.warning("TCP transport not connected; entering reconnect loop")
                    self._connected = False
                    await self._reconnect_with_backoff()
            except asyncio.CancelledError:
                break
            except Exception as e:
                self._logger.error(f"Error in TCP listen loop: {e}")
                await asyncio.sleep(1)

        self._connected = False

    async def _reconnect_with_backoff(self) -> None:
        """Attempt reconnection with exponential backoff and jitter."""
        base = 0.5
        cap = 30.0
        attempt = 0
        while True:
            if self._listen_task and self._listen_task.cancelled():
                return
            try:
                ok = await self.connect()
                if ok:
                    self._logger.info("TCP reconnect successful")
                    return
            except Exception as e:
                self._logger.debug(f"Reconnect attempt failed: {e}")

            delay = min(cap, base * (2 ** attempt))
            # jitter ±20%
            jitter = delay * (0.8 + 0.4 * random.random())
            self._logger.warning(f"Reconnect in {jitter:.1f}s (attempt {attempt + 1})")
            try:
                await asyncio.sleep(jitter)
            except asyncio.CancelledError:
                return
            attempt = min(attempt + 1, 10)


class TCPProtocol(asyncio.Protocol):
    """
    TCP protocol handler for LuxPower communication.
    
    This class handles the low-level TCP protocol events and forwards
    data to the transport layer.
    """

    def __init__(self, transport: TCPTransport):
        """
        Initialize TCP protocol.
        
        Args:
            transport: The TCP transport instance
        """
        self.transport = transport
        self._logger = logging.getLogger(f"{__name__}.TCPProtocol")

    def connection_made(self, transport: asyncio.WriteTransport) -> None:
        """Called when TCP connection is established."""
        self._logger.info(f"TCP connection established: {transport.get_extra_info('peername')}")
        self.transport._transport = transport

    def connection_lost(self, exc: Optional[Exception]) -> None:
        """Called when TCP connection is lost."""
        if exc:
            self._logger.error(f"TCP connection lost: {exc}")
        else:
            self._logger.info("TCP connection closed")
        
        self.transport._connected = False

    def data_received(self, data: bytes) -> None:
        """Called when data is received from TCP connection."""
        self._logger.debug(f"Received TCP data: {data.hex()}")
        if self.transport._callback:
            self.transport._callback(data)
