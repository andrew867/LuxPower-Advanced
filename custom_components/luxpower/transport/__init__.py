"""
Transport abstraction layer for LuxPower integration.

This module provides an abstract base class for different communication
transports (TCP, MQTT, Serial) while maintaining a consistent interface
for the LuxPower client.
"""

import asyncio
import logging
from abc import ABC, abstractmethod
from typing import Callable, Optional, Any

_LOGGER = logging.getLogger(__name__)


class LuxPowerTransport(ABC):
    """
    Abstract base class for LuxPower communication transports.
    
    This class defines the interface that all transport implementations
    must follow, allowing the LuxPower client to work with different
    communication methods (TCP, MQTT, Serial) transparently.
    """

    def __init__(self, dongle_serial: str):
        """
        Initialize the transport.
        
        Args:
            dongle_serial: The dongle serial number for this transport
        """
        self.dongle_serial = dongle_serial
        self._connected = False
        self._callback: Optional[Callable[[bytes], None]] = None
        self._logger = logging.getLogger(f"{__name__}.{self.__class__.__name__}")

    @property
    def connected(self) -> bool:
        """Return True if the transport is connected."""
        return self._connected

    def register_data_received_callback(self, callback: Callable[[bytes], None]) -> None:
        """
        Register a callback function to be called when data is received.
        
        Args:
            callback: Function to call with received data
        """
        self._callback = callback

    @abstractmethod
    async def connect(self) -> bool:
        """
        Establish connection to the transport.
        
        Returns:
            True if connection successful, False otherwise
        """
        pass

    @abstractmethod
    async def disconnect(self) -> None:
        """Disconnect from the transport."""
        pass

    @abstractmethod
    async def send_packet(self, packet: bytes) -> bool:
        """
        Send a packet through the transport.
        
        Args:
            packet: The packet data to send
            
        Returns:
            True if send successful, False otherwise
        """
        pass

    @abstractmethod
    async def read_packet(self) -> Optional[bytes]:
        """
        Read a packet from the transport.
        
        Returns:
            Packet data if available, None otherwise
        """
        pass

    def register_callback(self, callback: Callable[[bytes], None]) -> None:
        """
        Register a callback for received data.
        
        Args:
            callback: Function to call when data is received
        """
        self._callback = callback

    def _data_received(self, data: bytes) -> None:
        """
        Internal method to handle received data.
        
        Args:
            data: The received data
        """
        if self._callback:
            try:
                self._callback(data)
            except Exception as e:
                self._logger.error(f"Error in data callback: {e}")

    @abstractmethod
    async def start_listening(self) -> None:
        """
        Start listening for incoming data.
        
        This method should be implemented to start any background
        tasks needed to receive data from the transport.
        """
        pass

    @abstractmethod
    async def stop_listening(self) -> None:
        """
        Stop listening for incoming data.
        
        This method should clean up any background tasks
        started by start_listening().
        """
        pass

    def __str__(self) -> str:
        """Return string representation of the transport."""
        return f"{self.__class__.__name__}(dongle={self.dongle_serial})"
