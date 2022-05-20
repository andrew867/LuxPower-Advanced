import sys

from LXPPacket import LXPPacket
import asyncio

SERVER_URL = "172.16.255.120"
# SERVER_URL = "127.0.0.1"
SERVER_PORT = 8000


async def tcp_echo_client(loop):

    while True:
        try:
            address = 21
            print("1 - AC Charge ON")
            print("2 - AC Charge OFF")
            print("3 - Force Discharge ON")
            print("4 - Force Discharge OFF")
            print("5 - NORMAL Mode")
            print("6 - Standby Mode")
            selection = int(input("Select by typing 1,2 etc. : "))

            reader, writer = await asyncio.open_connection(SERVER_URL, SERVER_PORT,
                                                           loop=loop)
            print("Connected to server")
            lxpPacket = LXPPacket()
            packet = lxpPacket.prepare_packet_for_read(address, 1)
            writer.write(packet)
            await writer.drain()
            # await asyncio.sleep(1)

            packet = await reader.read(1000)
            print('Received: ', packet)
            data = lxpPacket.parse_packet(packet)
            print(data)

            if not lxpPacket.packet_error:
                if lxpPacket.device_function == lxpPacket.READ_HOLD and lxpPacket.register == address:
                    if len(lxpPacket.value) == 2:
                        old_value = lxpPacket.convert_to_int(lxpPacket.value)
                        if selection == 1:
                            mask = lxpPacket.AC_CHARGE_ENABLE
                            new_value = lxpPacket.update_value(old_value, mask, True)
                        elif selection == 2:
                            mask = lxpPacket.AC_CHARGE_ENABLE
                            new_value = lxpPacket.update_value(old_value, mask, False)
                        elif selection == 3:
                            mask = lxpPacket.FORCED_DISCHARGE_ENABLE
                            new_value = lxpPacket.update_value(old_value, mask, True)
                        elif selection == 4:
                            mask = lxpPacket.FORCED_DISCHARGE_ENABLE
                            new_value = lxpPacket.update_value(old_value, mask, False)
                        elif selection == 5:
                            mask = lxpPacket.NORMAL_OR_STANDBY
                            new_value = lxpPacket.update_value(old_value, mask, True)
                        elif selection == 6:
                            mask = lxpPacket.NORMAL_OR_STANDBY
                            new_value = lxpPacket.update_value(old_value, mask, False)
                        else:
                            print("invalid selection ")
                            continue
                        print("OLD: ",old_value, " MASK: ", mask, " NEW: ", new_value)
                        packet = lxpPacket.prepare_packet_for_write(address, new_value)
                        print("packet to be written ",packet)
                        writer.write(packet)
                        await writer.drain()
                        # await asyncio.sleep(1)
                        packet = await reader.read(1000)
                        print('Received: ', packet)
                        result = lxpPacket.parse_packet(packet)
                        if not lxpPacket.packet_error:
                            print(result)

            #     print("Protocol: ", lxpPacket.protocol_number, 'action: ',
            #           lxpPacket.ADDRESS_ACTION[lxpPacket.address_action])
            #     print("function: ", lxpPacket.DEVICE_FUNCTION[lxpPacket.device_function], 'register: ',
            #           lxpPacket.register)
            print('Close the socket')
            writer.close()
        except Exception as e:
            print("Exception ", e)
            print(sys.gettrace())



if __name__ =="__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(tcp_echo_client(loop))
    loop.close()

