# LuxPython (A custom Home Assistant Integration for LuxPowerTek and EG4)
LuxPython is a custom integration for Home Assistant (HA) that enables local access to LuxpowerTek and EG4 Inverters.

# IMPORTANT PLEASE READ!
Please do not rush to install new HA updates. We occasionally encounter issues when Home Assistant changes something that breaks this integration. Give it a few days after an update is released; I try to keep my development platform on the cutting edge, but like you, I want my production system to remain stable 24/7 remember, I wrote this for my own system.

Please ensure you are running Home Assistant version 2025.01 or higher to use this integration. If you are on an older version and encounter issues, you must upgrade to the latest Home Assistant release and the latest #LuxPython

If your dongle’s model number begins with BA…, you should be fine. If you have a different model, it might cause issues. (See #SETUP THE DONGLE / Important information about dongles for details.)

If you implement any fixes or improvements, please open an Issue or start a Discussion on GitHub.


# Cost
This project has cost me a significant amount of time and money to develop, and the first few versions were a paid project for a developer, which came directly out of my pocket!
As we move forward, Mark has done a massive amount of work to improve this code and add new features and fixes. Mark runs on beer, and I run on coffee—so if this integration works for you, once you have it installed, please consider donating to the development fund. A suggested donation of **£20** would be greatly appreciated as it all goes toward keeping this project running.
Solar Assistant costs more and doesn't provide as many sensors as this project does, and LXP-Bridge has closed down. Your support will help ensure this project continues to run and remains available to everyone!

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?business=UAUYJ83UYRHSG&no_recurring=1&item_name=Home+Assistant+Development+costs&currency_code=GBP)

If that link doesn't work then my paypal is guybw@hotmail.com please use friends and family as Paypal take a good % otherwise. Thank you!


```markdown
# Videos

I've created some quick tutorial videos that might help you. You may notice some audio/video issues, but if you get stuck, let me know and I will re-upload a clearer version:

- [How to Install Samba Share on Home Assistant](https://youtu.be/Pld5siYLNL8)
- [How to Install an Integration into Home Assistant (NO AUDIO)](https://youtu.be/wk_9kCNjLRE)
- [How to Set Up a Basic Refresh and Reconnect in Home Assistant](https://youtu.be/iCmv_w5aAJE)
- [How to Set Up Energy Monitoring in Home Assistant](https://youtu.be/pnp1y5CQRPY)
- [How to Install Power Card in Home Assistant](https://youtu.be/2petWQXWue0)

---

# SETUP THE DONGLE / Important Information About Dongles

If you have a **BA** dongle, you need to set up your inverter by following these instructions first:  
[Dongle Setup Instructions](https://github.com/guybw/LuxPython_DEV/blob/master/DongleSetup.md)

Make sure you **do not** change the port from 8000. Currently, only **Wi-Fi dongles** are supported—Ethernet dongles are not.

If you have a different dongle, then the webpage will likely not display, and if the version is lower than 2.02, port 8000 will not work. You will need to ask your installer to update the dongle firmware.

---

# INSTALL THE INTEGRATION

**THIS INTEGRATION WILL NOT WORK WITH HACS**

1. Copy the **luxpower** integration to your Home Assistant instance in the `/config/` folder (the same folder where your `configuration.yaml` is located).  
2. You should see a `custom_components` folder. Simply copy `./custom_components/luxpower/` there. (Details are explained below.)  
3. If you are new to HA, you may need to create a `custom_components` folder. If you use HACS, this folder should already exist.  
4. **Reboot** Home Assistant. This is mandatory; otherwise, the next steps will not work.

I strongly suggest you install the **Samba share** in HA. Watch this video for guidance: [How to Install Samba Share on Home Assistant](https://www.youtube.com/watch?v=udqY2CYzYGk)

The files **should** look like this (note that the IP should be replaced with the actual IP address of your HA device—in the example below, it's `172.16.255.30`):

Type into Windows File Explorer:  
```
\\172.16.255.30\config\custom_components\luxpower
```
**Do not** copy the entire folder over. It will not work. Only copy the `luxpower` folder contents from the ZIP file into the `custom_components` folder. It should look like this:

![image](https://user-images.githubusercontent.com/64648444/204362676-f96ca53a-8713-45a8-a0ee-38edea1c132a.png)

## Setting Up the Integration

1. Open **Settings > Devices and Services > Add Integration** in Home Assistant.  
2. Search for **LuxPower Inverter**.  
   - **Note:** If it doesn't appear, try clearing your browser cache; that often resolves the issue.

![Integration Setup](https://user-images.githubusercontent.com/64648444/169526481-d261df8b-ecaa-48c4-a6df-f7abae382316.png)

3. Fill in your IP, Port (8000), dongle serial, and inverter serial (found on the Lux website at [server.luxpowertek.com](http://server.luxpowertek.com)).

![Integration Details](https://user-images.githubusercontent.com/64648444/169526428-a508e905-19ef-45e5-ab2c-185b454489e3.png)

4. After adding the integration, you should see some sensors in HA.

![HA Sensors](https://user-images.githubusercontent.com/64648444/169526605-0f667815-87dc-4ab7-86f5-dbffe85ff765.png)

5. Under **Developer Tools**, look for `sensor.luxpower`. Initially, its state will be `Waiting`. After a few minutes—once the inverter updates—the state will change to `ONLINE`, and data will populate in the attributes.

---

# HOW TO REFRESH THE DATA

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

# Changing the Refresh Interval

The LUX dongle updates the website every 5–6 minutes, which is often too short or too long for some users. To help you refresh data more frequently (or at a different interval), we’ve included a blueprint located at:  
```
/blueprints/automation/luxpower/refresh_interval.yaml
```

You should **never** refresh more frequently than every 20 seconds. The dongle needs time to request and respond; 20 seconds is the safest setting I’ve found. Some claim they refresh every few seconds, but that is not recommended.

**Also note**: It appears LUX’s servers in Europe are currently experiencing issues. Although I don't believe we're causing the problem, if you set a refresh interval shorter than 2 minutes, you should consider blocking your dongle from accessing the LUX servers via your firewall.

If you are using this blueprint **while still connected** to the LUX servers, please avoid going below a 2–3 minute interval. If you are blocking internet access to the dongle, 20 seconds is possible. You can also click below to add it automatically to HA:

[![Blueprint](https://my.home-assistant.io/badges/blueprint_import.svg)](https://my.home-assistant.io/redirect/blueprint_import/?blueprint_url=https://github.com/guybw/LuxPythonCard/blob/main/blueprints/automation/luxpower/refresh_interval.yaml)

---

# LUX INVERTER DISCONNECTS OFTEN – IMPORTANT

The inverter dongle (Wi-Fi) is fairly unreliable and often disconnects; this is **not** a fault of the code but rather a limitation of the dongle itself. The Ethernet dongle doesn’t have port 8000 open, so it will **never** work with this integration.

Code has been added to handle common disconnect issues. If you’re still having problems, there’s a blueprint in the advanced readme, though most people shouldn’t need it.

---

# GUI SETUP

I've updated my recommendations here:

1. Install this Lux Power Distribution Card:  
   [https://github.com/DanteWinters/lux-power-distribution-card](https://github.com/DanteWinters/lux-power-distribution-card)

   ![image](https://github.com/guybw/LuxPython_DEV/assets/64648444/572761b3-3ba3-45aa-8cfc-25c038cf243b)

2. I also recommend installing the **Power Wheel Card**:  
   [https://github.com/gurbyz/power-wheel-card#readme](https://github.com/gurbyz/power-wheel-card#readme)  
   Once imported, you can use the following YAML for your card:

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

For a completely different view, you can try this card as well:  
[https://slipx06.github.io/sunsynk-power-flow-card/examples/lux.html](https://slipx06.github.io/sunsynk-power-flow-card/examples/lux.html)

---

# ACS Inverter (AC ONLY)

If you have an **ACS Inverter**, you should modify your `sensors.yaml` as follows (this has **not** been tested, but it *should* work):

1. Add the line `sensor: !include sensors.yaml` into your `configuration.yaml`.  
2. Create a `sensors.yaml` file with the following content.  
3. For more guidance, see [https://opensource.com/article/21/2/home-assistant-custom-sensors](https://opensource.com/article/21/2/home-assistant-custom-sensors)

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

## ##### END OF Custom Lux Sensors ######
```

---

# IMPORTANT – Things to Note

- We cannot support the LUX Ethernet dongle—only Wi-Fi—because required ports are not open on the Ethernet dongle.

---

# Fix Your IP

Most home routers do not automatically assign a fixed IP address, so your dongle might get a new IP (anything from every few hours to every few weeks). When that happens, the IP in HA will no longer be valid.

I **highly** recommend assigning both the inverter and your Home Assistant device a fixed IP. Typically, this is done via a DHCP reservation in your router. This step is beyond the scope of this project, but most routers have this feature—Google is your friend here. If your router absolutely cannot do this, you can manually assign a static IP to both the dongle and your HA instance.

---

# 2 (or More) Inverters

Mark has written template sensors that let you add two inverters together and produce a single sensor, which should help with multiple inverters. I can’t test this since I only have one inverter, but if you do try it, let me know how it goes! The file is **dualinverters_template.yaml**.

---

# BACKUPS

There have been numerous reports of Home Assistant failing or corrupting data—this can happen to anyone, including me! If you’re running HA on a Raspberry Pi, do **not** install this integration first. Instead, install a backup solution (e.g., backing up to Google Drive or another product) so if your HA instance fails, it’s easy to restore. **You have been warned!** Even on a virtual machine, corruption/failure is still possible.

I **highly** recommend this backup solution:  
[https://github.com/sabeechen/hassio-google-drive-backup](https://github.com/sabeechen/hassio-google-drive-backup)

---

# Upgrades

Many users set this up and then never change it. However, people like Mark have spent a lot of time fixing bugs and adding new features, so I personally recommend upgrading periodically.

We never remove functionality. If something isn’t working, I keep a comprehensive history of integration versions so we can always roll back. Remember, this integration was originally created for **my** use—if it’s not stable, I won’t run it! I do test new versions on a dev box for a few hours/days, and then it runs 24/7/365 on my production system.

### How to Upgrade the LuxPower Integration

1. Back up your current `luxpower` folder in `custom_components`.  
2. Delete all files in the `luxpower` folder (including any hidden cache folders).  
3. Paste the latest files from the update.  
4. Reboot Home Assistant.

This process takes around 20 seconds and generally ensures a smooth upgrade.

---

# Breaking Changes

@maegibbons has spent a great deal of time refining the time settings. In the latest release of **LUXPython for HA 2023.06**, we now have native time settings, so any custom helpers/automations can be removed. We won’t remove the old method immediately, but it will be phased out in future releases. Moving forward, you can simply add `time.lux_ac_charge_start1` (for example).

---

# Thanks!

- **celsworth**: for the great work at [lxp-packet documentation](https://github.com/celsworth/lxp-packet/blob/master/doc/LXP_REGISTERS.txt)  
- **@elementzonline**: who did the amazing Python coding to link HA with the Lux inverter (it was a paid gig, but he’s fantastic!).  
- **@maegibbons**: for helping fix countless bugs for people and expanding support to more inverters/setups.  

And thanks to everyone else who has helped identify bugs and contributed to this project!

---

# WHY PRIVATE

I decided a while ago to keep this project private because it’s challenging to prevent people from using, abusing, or selling it. Everyone here has responded to my setup email and has at least a basic understanding of HA, or I’ve walked them through a remote session to get them set up.

If HACS supported private repositories, I would absolutely put it there, but since it doesn’t, I can’t.

---

# BUGS

If you find a bug, please open an **issue** on GitHub with as much detail as possible.

To assist with debugging, edit your `configuration.yaml`:

```yaml
logger:
  default: warning
  logs:
    custom_components.luxpower: debug
```

Restart Home Assistant, then go to **Settings > System > Logs** and copy any errors that appear. Logs beginning with `REGISTERS:` are especially useful if we need to troubleshoot specific settings/values.

---

# Legal

```
Copyright (c) 2025, Guy Wells
All rights reserved.

This software may not be sold, resold, redistributed, or otherwise conveyed
to a third party.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```
```
