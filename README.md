# LuxPython (Full Home Assistant UI Integration)
LuxPython is a custom integration into Home Assistant to allow local access to the Lux Powertek Inverter

# IMPORTANT PLEASE READ!

UPDATE - I have our dev creating a new version of this right now - 2022-05-11. This is in active development - it DOES have bugs!

IF YOU USE THE OLD CONFIGURATION.YAML BE WARNED!  This is a MAJOR CHANGE and you need to delete all of the old sensors / customisation / configuration.yaml and start again. Yes, it's a pain but this is SO much better!


If you do any fixes, improvements etc, please let me know so I can bring them into this.
Please keep in touch at guybw@hotmail.com I would like to know how you get on and if this works for you!

This has cost me money to develop and all the money goes to the developer (paid gigs) to fix issues!
Once you have installed this and have it working, please click below to donate to the development fund.  A suggested donation of £20  to the development fund would be great as it ALL goes to bug fixes and features!
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?business=UAUYJ83UYRHSG&no_recurring=1&item_name=Home+Assistant+Development+costs&currency_code=GBP)

# How to Install

You need to set up your inverter by following these instructions first:
https://github.com/celsworth/octolux/blob/master/doc/INVERTER_SETUP.md
(make sure you do not change the port from 8000)


Copy the "luxpower" integration to your Home Assistant instance into the "custom_components" folder

./custom_components/luxpower/ to your HA data directory (where your configuration.yaml lives)

If you are new to HA you will likely have to create this folder but if you use HACS it should already be created.


Next REBOOT, it's mandatory otherwise the next bit will not work.

Open up Settings>Devices and Services> Add Integration and search for "LuxPower Inverter"
![image](https://user-images.githubusercontent.com/64648444/169526481-d261df8b-ecaa-48c4-a6df-f7abae382316.png)

Fill in your IP, Port (8000), dongle serial and inverter serial ( this can be found on the Lux website at server.luxpowertek.com

![image](https://user-images.githubusercontent.com/64648444/169526428-a508e905-19ef-45e5-ab2c-185b454489e3.png)

Once you have added this into HA, you should see some sensors in HA.

Use Developer Tools to view `sensor.luxpower`.  Initially, the state will be `Waiting` but after a few minutes when the inverter pushes an update the state will change to `ONLINE` and data will be populated in the attributes.





You can also use the card luxpowercard.yaml which will give you a clone of the lux powertek website.


I HIGHLY recommend you install this power card:
https://github.com/gurbyz/power-wheel-card#readme


At the end of this, you should be able to add the following sensors to HA Energy and it will start tracking:
![image](https://user-images.githubusercontent.com/64648444/149421208-c1e57277-a076-4727-8d23-74715d4d5541.png)

If you have an ACS Inverter you should modify the sensors.yaml with the following:
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
# Thanks!

Using the great work from here: https://github.com/celsworth/lxp-packet/blob/master/doc/LXP_REGISTERS.txt

@elementzonline Did the amazing work of writing the Python code to link from HA to the Lux inverter, it was a paid gig but he is incredible! 
 
# BUGS
I'm aware that inverters sold in South Africa have issues right now. If you do have issues or you want to use it please let me know I'm working on a solution!
