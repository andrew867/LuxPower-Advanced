import sys

from LXPPacket import LXPPacket
import asyncio

SERVER_URL = "172.16.255.120"
# SERVER_URL = "127.0.0.1"
SERVER_PORT = 8000


async def tcp_echo_client(loop):
    reader, writer = await asyncio.open_connection(SERVER_URL, SERVER_PORT,
                                                   loop=loop)
    print("Connected to server")
    lxpPacket = LXPPacket()
    isConnected = True
    while isConnected:
        try:
            packet = await reader.read(1000)
            print('Received: ', packet)
            result = lxpPacket.parse_packet(packet)
            if not lxpPacket.packet_error:
                print(result)
        except Exception as e:
            print("Exception ", e)
            print(sys.gettrace())
            isConnected = False
    print('Close the socket')
    writer.close()


if __name__ =="__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(tcp_echo_client(loop))
    loop.close()

