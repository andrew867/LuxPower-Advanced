# LuxPython
LuxPython is a custom intergation into Home Assistant to allow local access to the Lux Powertek Inverter

# IMPORTANT PLEASE READ!
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


Copy the "luxpower" intergation to your Home Assistant instance into the "custom_components" folder

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
      
    sensor.lux_total_batttery_discharge:
      icon: mdi:battery-negative
      state_class: total_increasing   
        
    sensor.lux_home_consumtion:
      icon: mdi:home 
    
    sensor.lux_batttery_discharge:
      icon: mdi:battery-negative
      
    sensor.lux_battery_charge:
      icon: mdi:battery-positive
      
    sensor.lux_current_solar_output_1:
      state_class: measurement
    sensor.lux_current_solar_output_1:
      state_class: measurement
    sensor.lux_current_solar_output_2:
      state_class: measurement
```


Once you have added this into HA, you should be able to reboot and see some sensors in HA.

You should also then then this line to the configuration.yaml
```YAML
sensor: !include sensors.yaml
```
**** NB sensor: !include sensors.yaml must go above any other sensor: command in your configuration.yaml

and then copy the sensors.yaml in this github into the root of your HA folder and then reboot HA and you should then see more sensors!


I HIGHLY recomend you install this powercard:
https://github.com/gurbyz/power-wheel-card#readme


At the end of this, you should be able to add the following sensors to HA Energy and it will start tracking:
![image](https://user-images.githubusercontent.com/64648444/149421208-c1e57277-a076-4727-8d23-74715d4d5541.png)


# Thanks!

Using the great work from here: https://github.com/celsworth/lxp-packet/blob/master/doc/LXP_REGISTERS.txt

@elementzonline Did the amazing work of writing the Python code to link from HA to the Lux inverter, it was a paid gig but he was incrediable! 
