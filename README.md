# LuxPython (Full Home Assistant UI Integration)
LuxPython is a custom integration into Home Assistant to allow local access to the Lux Powertek Inverter

# IMPORTANT PLEASE READ!

Please don't rush to install HA updates, we have issues from time to time when HA changes an item and it breaks this! Give it a few days as I try and keep my dev platform on the bleeding edge but like you my production system I want working 24/7



IF YOU USE THE OLD CONFIGURATION.YAML BE WARNED!  This is a MAJOR CHANGE and you need to delete all of the old sensors / customisation / configuration.yaml and start again. Yes, it's a pain but this is SO much better!


If you do any fixes, improvements etc, please let me know so I can bring them into this.
Please keep in touch at guybw@hotmail.com I would like to know how you get on and if this works for you!

This has cost me money to develop and all the money goes to the developer (paid gigs) to fix issues!
Once you have installed this and have it working, please click below to donate to the development fund.  A suggested donation of £20  to the development fund would be great as it ALL goes to bug fixes and features!
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?business=UAUYJ83UYRHSG&no_recurring=1&item_name=Home+Assistant+Development+costs&currency_code=GBP)






You need to set up your inverter by following these instructions first:
https://github.com/celsworth/octolux/blob/master/doc/INVERTER_SETUP.md
(make sure you do not change the port from 8000) I only support WIFI dongles, not ethernet dongles right now.


Copy the "luxpower" integration to your Home Assistant instance into the "custom_components" folder

./custom_components/luxpower/ to your HA data directory (where your configuration.yaml lives)

If you are new to HA you will likely have to create this folder but if you use HACS it should already be created.


Next REBOOT, it's mandatory otherwise the next bit will not work.

Open up Settings>Devices and Services> Add Integration and search for "LuxPower Inverter"
![image](https://user-images.githubusercontent.com/64648444/169526481-d261df8b-ecaa-48c4-a6df-f7abae382316.png)

Fill in your IP, Port (8000), dongle serial and inverter serial ( this can be found on the Lux website at server.luxpowertek.com

![image](https://user-images.githubusercontent.com/64648444/169526428-a508e905-19ef-45e5-ab2c-185b454489e3.png)

Once you have added this into HA, you should see some sensors in HA.
![image](https://user-images.githubusercontent.com/64648444/169526605-0f667815-87dc-4ab7-86f5-dbffe85ff765.png)

Use Developer Tools to view `sensor.luxpower`.  Initially, the state will be `Waiting` but after a few minutes when the inverter pushes an update the state will change to `ONLINE` and data will be populated in the attributes.

On a dashboard create a new card and type this (CHANGE THE DONGLE TO YOUR DONGLE SERIAL)

```
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
This will then give you a button to refresh your data as often as you like.

There is also a blueprint that you can add that will automatically try to reconnect if the connection drops for a set time, as the dongles can be a bit flakey.
To install, 
- add a new automation and select the "Luxpower reconnect"
- Select "Lux - data receive time" as a trigger
- Select the interval to check on.
![image](https://user-images.githubusercontent.com/435874/188263388-8814be9b-6075-4e66-98a0-8818cdb2b321.png)


I HIGHLY recommend you install this power card:
https://github.com/gurbyz/power-wheel-card#readme
Import that and then use the below yaml for the card.
```
type: custom:power-wheel-card
title: Solar Status
solar_power_entity: sensor.lux_solar_output_live
grid_power_entity: sensor.lux_grid_flow_live
battery_soc_entity: sensor.lux_battery
battery_power_entity: sensor.lux_battery_flow_live
```


At the end of this, you should be able to add the following sensors to HA Energy and it will start tracking:
![image](https://user-images.githubusercontent.com/64648444/149421208-c1e57277-a076-4727-8d23-74715d4d5541.png)

If you have an ACS Inverter you should modify the sensors.yaml with the following (This has NOT been tested but It should work!):
```
## Custom LUX Sensors for ACS Systems. Intended to replace the two existing sensor code. However, there's a new name to prevent conflict. 
    lux_new_home_consumption:
      friendly_name: "Lux - Home Consumption (Daily)"
      unit_of_measurement: 'kWh'
      value_template: >
        {{ '%0.1f' | format(states('sensor.lux_power_from_grid_daily') | float(0) + 
                            states('sensor.lux_power_from_inverter_daily') | float(0) +
                            states('sensor.lux_daily_solar') | float(0) - 
                            states('sensor.lux_power_to_inverter_daily') | float(0) - 
                            states('sensor.lux_power_to_grid_daily') | float(0)) }}
    lux_new_home_consumption_live:
      friendly_name: "Lux - Home Consumption (Live)"
      unit_of_measurement: 'W'
      value_template: >
        {{ '%0.1f' | format(states('sensor.lux_power_from_grid_live') | float(0) + 
                            states('sensor.lux_power_from_inverter_live') | float(0) +
                            states('sensor.lux_current_solar_output') | float(0) - 
                            states('sensor.lux_power_to_inverter_live') | float(0) - 
                            states('sensor.lux_power_to_grid_live') | float(0)) }}
                            
## ##### END OF Custom Lux Sensors   ######
```
# Things to note
The inverter dongle is fairly poor and often disconnects, this is not a fault of this code but the dongle (wifi dongle) the ethernet dongle I'm told still isn't stable and this will NOT work as I can't query the inverter via it.

To solve the issue of LuxPython not showing please import the reconnection blueprint in this folder. It will allow you to reconnect if the inverter doesn't report for X minutes (I would set it to 20)

The Blueprint import should help below but please report back if it doesn't work for you.
[![Blueprint](https://my.home-assistant.io/badges/blueprint_import.svg)](https://my.home-assistant.io/redirect/blueprint_import/?blueprint_url=https://github.com/guybw/LuxPythonCard/blob/main/blueprints/automation/luxpower/reconnect.yaml)

Also be aware that you can set the times in HA but they will not pull (new) times from the Lux Server so if you set times in the app / website they will not change in HA.
There is a blueprint and you need to create a helper (please contact me for a demo on how to do this if required) I don't recomend it as I would use the AC Charge Switch.

# 2 (or more inverters)
Mark has helped write template sensors that will allow you to add 2 inverters together and make a single sensor which should help with 2 inverters.
I can't test this as I don't have 2 inverters but if you do try it out and let me know how you get on! dualinverters_templae.yaml is the file.

#BACKUPS
The amount of times people (and me included) that HA has HA is a concern. PLEASE - if you are running HA on a PI don't install this first, go and install a backup solution (you can backup to Google Drive or many other products) and when your HA dies, it's easy to replace. YOU HAVE BEEN WARNED! Even on a VM I it can corrupt / fail!

# Thanks!

Using the great work from here: https://github.com/celsworth/lxp-packet/blob/master/doc/LXP_REGISTERS.txt

@elementzonline Did the amazing work of writing the Python code to link from HA to the Lux inverter, it was a paid gig but he is incredible! 
 
# BUGS
I'm aware that inverters sold in South Africa have issues right now. If you do have issues or you want to use it please let me know I'm working on a solution!
