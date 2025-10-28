"""
TCP transport implementation for LuxPower integration.

This module wraps the existing TCP asyncio.Protocol logic from client.py
into the new transport abstraction layer.
"""

import asyncio
import logging
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
        while self._connected:
            try:
                # Check if transport is still valid
                if not self._transport or self._transport.is_closing():
                    self._logger.warning("TCP transport closed, attempting reconnection")
                    self._connected = False
                    break
                
                await asyncio.sleep(1)
                
            except Exception as e:
                self._logger.error(f"Error in TCP listen loop: {e}")
                break
        
        self._connected = False


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
