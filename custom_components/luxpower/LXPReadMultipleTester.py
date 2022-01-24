import sys

from LXPPacket import LXPPacket
import asyncio

SERVER_URL = "172.16.255.120"
# SERVER_URL = "127.0.0.1"
SERVER_PORT = 8000


async def tcp_echo_client(loop):
    isConnected = True
    while isConnected:
        try:
            address = int(input("Register Address: "))
            num_registers = int(input("Number of reg to read: "))
            reader, writer = await asyncio.open_connection(SERVER_URL, SERVER_PORT,
                                                           loop=loop)
            print("Connected to server")
            lxpPacket = LXPPacket()
            packet = lxpPacket.prepare_packet_for_read(address, num_registers)
            writer.write(packet)
            await writer.drain()
            # await asyncio.sleep(1)
            packet = await reader.read(1000)
            print('Received: ', packet)
            result = lxpPacket.parse_packet(packet)
            if not lxpPacket.packet_error:
                print(result)
            print('Close the socket')
            writer.close()
        except Exception as e:
            print("Exception ", e)
            print(sys.gettrace())


if __name__ =="__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(tcp_echo_client(loop))
    loop.close()

