
# LuxPython (A custom Home Assistant Integration for LuxPowerTek and EG4)

LuxPython is a custom integration for Home Assistant (HA) that enables local access to Luxpower Tek and EG4 Inverters.

---

## IMPORTANT PLEASE READ!
Before you begin, please read this **entire** README from start to finish. Nearly 99% of the questions I receive by email are already answered below, so taking the time to read everything now will save you a lot of troubleshooting later. Once you've read it in full, **then** you can begin the setup process!

Please **do not** rush to install new Home Assistant updates. We occasionally encounter issues when HA changes something that breaks this integration. Give it a few days after a new release; while I keep my dev platform on the cutting edge, I want my production system to remain stable 24/7. Remember, I originally wrote this for my own system.

Please ensure you are running **Home Assistant version 2025.01 or higher** to use this integration. If you are on an older version and encounter issues, you **must** upgrade to the latest Home Assistant release and the latest LuxPython.

If your dongle’s model number begins with **BA…**, you should be fine. If you have a different model, it might cause issues. (See [SETUP THE DONGLE / Important Information About Dongles](#setup-the-dongle--important-information-about-dongles) for details.)

If you implement any fixes or improvements, please open an **Issue** or start a **Discussion** on GitHub.

---

## Cost

This project has cost me a significant amount of time and money to develop, and the first few versions were a paid project for a developer, coming directly out of my pocket. As we move forward, **Mark** has done a massive amount of work to improve this code and add new features/fixes. Mark runs on beer, and I run on coffee—so if this integration works for you, once you have it installed, please consider donating to the development fund.

A suggested donation of **£20** would be greatly appreciated, as it all goes toward keeping this project running. **Solar Assistant** costs more and doesn't provide as many sensors as this project, and **LXP-Bridge** has closed down—your support will help ensure this project continues to run and remains available and supported.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.me/guybw)

If that link doesn't work, my PayPal is **guybw@hotmail.com**. Please use friends and family, as PayPal takes a significant percentage otherwise. Thank you!

---

## Videos

I've created some quick tutorial videos that might help you. You may notice audio/video issues, but if you get stuck, let me know, and I’ll re-upload a clearer version:

- [How to Install Samba Share on Home Assistant](https://youtu.be/Pld5siYLNL8)
- [How to Install an Integration into Home Assistant (NO AUDIO)](https://youtu.be/wk_9kCNjLRE)
- [How to Set Up a Basic Refresh and Reconnect in Home Assistant](https://youtu.be/iCmv_w5aAJE)
- [How to Set Up Energy Monitoring in Home Assistant](https://youtu.be/pnp1y5CQRPY)
- [How to Install Power Card in Home Assistant](https://youtu.be/2petWQXWue0)

---

## SETUP THE DONGLE / Important Information About Dongles

If you have a **BA** dongle, you need to set up your inverter by following these instructions first:  
[Dongle Setup Instructions](https://github.com/guybw/LuxPython_DEV/blob/master/DongleSetup.md)

Make sure you **do not** change the port from 8000. Currently, only **Wi-Fi dongles** are supported—**Ethernet dongles are not**.

**If you have a different dongle**, the webpage will likely not display. If the version is lower than **2.02**, port 8000 will not work, and you’ll need to ask your installer to update the dongle firmware.

---

## INSTALL THE INTEGRATION

**THIS INTEGRATION WILL NOT WORK WITH HACS**

1. Get the ZIP file from here: https://github.com/guybw/LuxPython_DEV/releases you want to latest stable release. 
2. Copy the **luxpower** integration to your Home Assistant instance in the `/config/` folder (the same folder where your `configuration.yaml` is located).  
3. You should see a `custom_components` folder. Simply copy `./custom_components/luxpower/` there. (Details are explained below.)  
4. If you are new to HA, you may need to create a `custom_components` folder. If you use HACS, this folder should already exist.  
5. **Reboot** Home Assistant. This step is mandatory; otherwise, the next steps will not work.

I strongly suggest installing the **Samba share** in HA (if you know how to transfer via SSH go ahead, if you don't just use the samba share.

Watch this video for guidance: [How to Install Samba Share on Home Assistant](https://www.youtube.com/watch?v=udqY2CYzYGk)

**The files should look like this** (replace the IP with your HA device’s IP—in the example, it’s `172.16.255.30`):

In Windows File Explorer:  
```
\\172.16.255.30\config\custom_components\luxpower
```
**Do not** copy the entire folder over in one go. Copy only the `luxpower` folder contents from the ZIP file into `custom_components`. It should look like this:

![image](https://user-images.githubusercontent.com/64648444/204362676-f96ca53a-8713-45a8-a0ee-38edea1c132a.png)

### Setting Up the Integration

1. Open **Settings > Devices and Services > Add Integration** in Home Assistant.  
2. Search for **LuxPower Inverter**.  
   - **Note:** If it doesn’t appear, clear your browser cache; that often resolves the issue.

![Integration Setup](https://user-images.githubusercontent.com/64648444/169526481-d261df8b-ecaa-48c4-a6df-f7abae382316.png)

3. Fill in your IP and dongle serial. The port is preset to `8000` and the inverter serial defaults to `0000000000`.

![Integration Details](https://user-images.githubusercontent.com/64648444/169526428-a508e905-19ef-45e5-ab2c-185b454489e3.png)

4. After adding the integration, you should see some sensors in Home Assistant.

![HA Sensors](https://user-images.githubusercontent.com/64648444/169526605-0f667815-87dc-4ab7-86f5-dbffe85ff765.png)

5. Under **Developer Tools**, look for `sensor.luxpower`. Initially, its state will be `Waiting`. After a few minutes—once the inverter updates—the state will change to `ONLINE`, and data will populate in the attributes.

---

## HOW TO REFRESH THE DATA

On a dashboard, create a new card and enter the following YAML (replace the dongle serial with **your** dongle’s serial):

```yaml
show_name: true
show_icon: true
type: button
tap_action:
  action: call-service
  service: luxpower.luxpower_refresh_registers
  service_data:
    dongle: BA********
  target: {}
entity: ''
icon_height: 50px
icon: mdi:cloud-refresh
name: Refresh LUX Data
show_state: false
```

This will give you a button to refresh your data at any time.

---

## Changing the Refresh Interval

The LUX dongle updates the website every 5–6 minutes, which can be either too short or too long for some users. To refresh data more frequently (or at a different interval), we’ve included a blueprint located at:
```
/blueprints/automation/luxpower/refresh_interval.yaml
```

- **Never** refresh more frequently than every **20 seconds**. The dongle needs time to respond.  
- Some claim they refresh every few seconds, but that is **not recommended**.  
- If you refresh faster than every 2 minutes while connected to LUX servers, consider blocking the dongle’s access via your firewall, as their servers in Europe have been experiencing issues.

If you’re still connected to LUX servers, please don’t go below a 2–3 minute interval. If you block internet access to the dongle, 20 seconds is possible. You can click below to add the blueprint automatically:

[![Blueprint](https://my.home-assistant.io/badges/blueprint_import.svg)](https://my.home-assistant.io/redirect/blueprint_import/?blueprint_url=https://github.com/guybw/LuxPythonCard/blob/main/blueprints/automation/luxpower/refresh_interval.yaml)

---

## LUX INVERTER DISCONNECTS OFTEN – IMPORTANT

The Wi-Fi dongle can be unreliable and may disconnect frequently, which is a dongle issue—**not** a code issue. The Ethernet dongle has port 8000 closed, so it **cannot** work with this integration.

We have code to handle common disconnects. If you still have problems, there’s a blueprint in the advanced readme, but most people won’t need it.

---

## GUI SETUP

I've updated my recommendations here:

1. **Lux Power Distribution Card**  
   Install [DanteWinters’ lux-power-distribution-card](https://github.com/DanteWinters/lux-power-distribution-card).  
   ![image](https://github.com/guybw/LuxPython_DEV/assets/64648444/572761b3-3ba3-45aa-8cfc-25c038cf243b)

2. **Power Wheel Card**  
   Install the [Power Wheel Card](https://github.com/gurbyz/power-wheel-card#readme). Once imported, you can add a card with the following YAML:
   ```yaml
   type: custom:power-wheel-card
   title: Solar Status
   solar_power_entity: sensor.lux_solar_output_live
   grid_power_entity: sensor.lux_grid_flow_live
   battery_soc_entity: sensor.lux_battery
   battery_power_entity: sensor.lux_battery_flow_live
   ```

At this point, you should be able to add the relevant sensors to **HA Energy**, and it will start tracking:

![image](https://user-images.githubusercontent.com/64648444/149421208-c1e57277-a076-4727-8d23-74715d4d5541.png)

For a completely different view, consider:
[slipx06’s sunsynk-power-flow-card examples for LUX](https://slipx06.github.io/sunsynk-power-flow-card/examples/lux.html)

---

## ACS Inverter (AC ONLY)

If you have an **ACS Inverter**, you should modify your `sensors.yaml` as follows (**unverified**, but theoretically should work):

1. In `configuration.yaml`, add:
   ```yaml
   sensor: !include sensors.yaml
   ```
2. Create a `sensors.yaml` file with the following content:  
   ```yaml
   ## Custom LUX Sensors for ACS Systems. Intended to replace the two existing sensor codes.
   ## However, there's a new name to prevent conflict.

   - platform: template
     sensors:
       lux_new_home_consumption:
         friendly_name: "Lux Home Consumption (Daily)"
         icon_template: mdi:lightning-bolt
         unit_of_measurement: 'kWh'
         value_template: >
           {{ '%0.1f' | format(
             states('sensor.lux_power_from_grid_daily') | float(0) +
             states('sensor.lux_power_from_inverter_to_home_daily') | float(0) +
             states('sensor.lux_solar_output_daily') | float(0) -
             states('sensor.lux_power_to_inverter_daily') | float(0) -
             states('sensor.lux_power_to_grid_daily') | float(0)
           ) }}
       lux_new_home_consumption_live:
         friendly_name: "Lux Home Consumption (Live)"
         icon_template: mdi:lightning-bolt
         unique_id: sensor.lux_home_consumption_live
         unit_of_measurement: 'W'
         value_template: >
           {{ '%0.1f' | format(
             states('sensor.lux_power_from_grid_live') | float(0) +
             states('sensor.lux_power_from_inverter_live') | float(0) +
             states('sensor.lux_solar_output_live') | float(0) -
             states('sensor.lux_power_to_inverter_live') | float(0) -
             states('sensor.lux_power_to_grid_live') | float(0)
           ) }}
   ```

---

## IMPORTANT – Things to Note

- We **cannot** support the LUX Ethernet dongle—only Wi-Fi—because the Ethernet dongle does not open port 8000.
- If you have used LXP-Bridge or Solar Assistant you MUST disable them before using LUXPython, you will find that running 2 connects will break everything!

---

## Fix Your IP

Most home routers do not automatically assign a fixed IP address, so your dongle might pick up a new IP. This can happen every few hours or weeks, and your HA configuration will break if the IP changes.

I **highly** recommend assigning static IPs (DHCP reservations) to both the inverter and your HA instance. Most routers have this capability—consult your router’s documentation or search online.

---

## 2 (or More) Inverters

**Mark** has written template sensors to combine data from two inverters into a single sensor, which should help if you have multiple inverters. I can’t test this (I have only one inverter), but if you do try it, let me know. The file is [**dualinverters_template.yaml**.](https://github.com/guybw/LuxPython_DEV/blob/master/dual_inverter_setup.md)

---

## BACKUPS

Home Assistant can fail or become corrupted—this happens more often than you'd think. If you’re on a Raspberry Pi, **do not** install this integration first. Instead, set up a backup solution (like backups to Google Drive). That way, if your Pi fails, you can restore easily. You have been warned.

Even on a VM, corruption can occur. I **highly** recommend:
[https://github.com/sabeechen/hassio-google-drive-backup](https://github.com/sabeechen/hassio-google-drive-backup)

---

## Upgrades

Many people set this up and never update it. However, Mark and others have spent a lot of time fixing bugs and adding new features, so I recommend upgrading periodically.

We never remove functionality. If something breaks, we maintain a comprehensive version history so you can roll back. Remember, this integration was originally made for **my** use—if it’s not stable, I won’t run it! I test new releases on a dev box for a few hours/days, then run them 24/7 on my production system.

### How to Upgrade the LuxPower Integration

1. Back up your current `luxpower` folder in `custom_components`.  
2. Delete all files in that folder (including any hidden cache folders).  
3. Paste in the latest files from the update.  
4. Reboot Home Assistant.

It takes about 20 seconds and is usually painless.

---

## Breaking Changes

**@maegibbons** has invested significant time refining time settings. In **LUXPython for HA 2023.06**, we now have native time settings, so any custom helpers/automations can be removed. We won’t remove the old method immediately, but it will be phased out. Going forward, just add time entities like `time.lux_ac_charge_start1`.

---

## LUX Installer Code / Installer Account
I have been given an installer account which allows me to have a few more options / allows me to test extra items (most are on here anyway) but if you don't have an installer or are a DIY installer I'm happy to help with updates / simple setup issues. Just let me know your station name and your Dongle ID and inverter serial and I can get it moved onto my account (with your permission). If you are struggling to register let me know and I will send you my installer code. Hopefully it's not needed but it's helped in the past.

---

## Thanks!

- **celsworth** for [lxp-packet documentation](https://github.com/celsworth/lxp-packet/blob/master/doc/LXP_REGISTERS.txt)  
- **@elementzonline** for writing the original Python code that links HA to the Lux inverter (it was a paid gig, but he’s fantastic!)  
- **@maegibbons** for fixing countless bugs and expanding support for more inverters/setups  
- Everyone else who has identified bugs or contributed to this project!

---

## WHY PRIVATE

I decided long ago to keep this project private to avoid misuse, abuse, or reselling. Everyone here has responded to my setup email and shown at least a basic knowledge of HA, or I’ve worked with them via a remote session.

If HACS supported private repositories, I would put it there, but it does not—so I can’t.

---

## BUGS

If you find a bug, please open an **Issue** on GitHub with as much detail as you can.

To help us debug, edit your `configuration.yaml`:
```yaml
logger:
  default: warning
  logs:
    custom_components.luxpower: debug
```
Restart HA, then go to **Settings > System > Logs** and copy any errors. Logs starting with `REGISTERS:` are especially useful for debugging certain settings/values.

---

## Legal

```
Copyright (c) 2025, Guy Wells
All rights reserved.

This software may not be sold, resold, redistributed or otherwise conveyed
to a third party.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GUY WELLS OR ANY OTHER PERSON BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.


```
Congratuations - you read the entire document!
```
