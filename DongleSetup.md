#Finding the IP address
the device is going to be called "MICO" and if you log into your router you should find the name quickly. If you can't you can find tools to scan your network to find them but wifi extenders can cause issues!


# Inverter Setup

By default, the datalogger plugged into the Lux sends statistics about your inverter to LuxPower in China. This is how their web portal and phone app knows all about you.

We need to configure it to open another port that we can talk to. Open a web browser to your datalogger IP (might have to check your DHCP server to find it) and login with username/password admin/admin. Click English in the top right :)

You should see:

![image](https://user-images.githubusercontent.com/64648444/204364408-2554dc33-dbe8-4934-bb74-2a3c6b552b74.png)

Tap on Network Setting in the menu. You should see two forms, the top one is populated with LuxPower's IP in China - the second one we can use. Configure it to look like the below and save:

![image](https://user-images.githubusercontent.com/64648444/204364458-3002037d-3b84-43a6-aaae-9ca66a8e62f3.png)

After the datalogger reboots (this takes only a couple of seconds and does not affect the main inverter operation, it will continue as normal), port 8000 on your inverter IP is accessible to our Ruby script. You should be sure that this port is only accessible via your LAN, and not exposed to the Internet, or anyone can control your inverter.



#Thanks
This was copied from https://github.com/celsworth/octolux/blob/master/doc/INVERTER_SETUP.md as I don't know if he is likely going to delete this page.
