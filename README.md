# LuxPython
LuxPython is a custom integration into Home Assistant to allow local access to the Lux Powertek Inverter

# IMPORTANT PLEASE READ!



HOMEASSISTANT 2022.04 should now be working - please report back if it doesn't!


This is in active development - it DOES have bugs in.

If you do any fixes, improvements etc, please let me know so I can bring them into this.

I'm NOT a dev, I've paid to have the basics of this developed however, I am working to have some of the bugs fixed.
Please keep in touch at guybw@hotmail.com I would really like to know how you get on and if this actually works for you!


# How to Install

[![hacs_badge](https://img.shields.io/badge/HACS-Custom-41BDF5.svg)](https://github.com/guybw/LuxPython)
HACS Support will be enabled with this is changed from Private to Public

For now,  follow below:

You need to setup your inverter by following this intstructions first:
https://github.com/celsworth/octolux/blob/master/doc/INVERTER_SETUP.md


Copy the "luxpower" integration to your Home Assistant instance into the "custom_components" folder

./custom_components/luxpower/ to your hass data directory (where your configuration.yaml lives)

If you are new to HA you will likely have to create this folder but if you use HACS it should already be created.

Next REBOOT, it's mandatory otherwise the next bit will not work.


In configuration.yaml add the block below but change your Host IP and dongle serial and serial number to your own information.

```YAML
# Lux Powertek Solar Inverter 
luxpower:
  host: 192.168.1.2
  port: 8000
  dongle_serial: "BA********"
  serial_number: "*********"
  
homeassistant:
  customize: 
    sensor.lux_total_solar:
      icon: mdi:solar-power
      state_class: total_increasing
    
    sensor.lux_power_to_grid_total:
      icon: mdi:transmission-tower-import
      state_class: total_increasing
      
    sensor.lux_power_from_grid_total:
      icon: mdi:transmission-tower-export
      state_class: total_increasing
      
    sensor.lux_total_battery_charge:
      icon: mdi:battery-positive
      state_class: total_increasing  
      
    sensor.lux_total_battery_discharge:
      icon: mdi:battery-negative
      state_class: total_increasing   
        
    sensor.lux_home_consumption:
      icon: mdi:home 
    
    sensor.lux_battery_discharge:
      icon: mdi:battery-negative
      
    sensor.lux_battery_charge:
      icon: mdi:battery-positive
      
    sensor.lux_current_solar_output_1:
      state_class: measurement
    sensor.lux_current_solar_output_2:
      state_class: measurement
```


Once you have added this into HA, you should be able to reboot and see some sensors in HA.
Use Developer Tools to view `sensor.luxpower`. Initially the state will be `Waiting` but after a few minutes when the inverter pushes an update the state will change to `ONLINE` and data will be populated in the attributes.

You should also then then this line to the configuration.yaml
```YAML
sensor: !include sensors.yaml
```
**** NB sensor: !include sensors.yaml must go above any other sensor: command in your configuration.yaml

and then copy the sensors.yaml in this github into the root of your HA folder and then reboot HA and you should then see more sensors!

You can also use the card luxpowercard.yaml which will give you a clone of the lux powertek website.


I HIGHLY recomend you install this powercard:
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

@elementzonline Did the amazing work of writing the Python code to link from HA to the Lux inverter, it was a paid gig but he was incrediable! 
 
If you want to chip in to the devopment costs then feel free to below. This has cost me monney to develop.
To give you an idea the bug fixes cost about £40 per time.
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?business=UAUYJ83UYRHSG&no_recurring=1&item_name=Home+Assistant+Development+costs&currency_code=GBP)
