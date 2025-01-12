Please read the first readme BEFORE following any instructions on this page, this is more of a notes page and likely will not work for you the first time around.


# LoveLace (GUI Example)
In order to help you off, you can create a new card in LoveLace. 
Simply go to, Settings > Dashboards> Click on the Dashboard you want to add or click on "Open" on Overview if you don't have a custom one.
Click on the three dots ... at the top right and click Edit Dashboard. At the bottom right click "Add Card" , scroll to the bottom and find "Manual" and click on it, delete the prefilled text then copy and paste the below:
```
type: vertical-stack
cards:
  - type: entities
    entities:
      - entity: switch.lux_power_backup_enable
        name: Power Backup (EPS)
    title: Application Setting
    show_header_toggle: false
  - type: entities
    entities:
      - entity: number.lux_system_charge_power_rate
        name: System Charge Power Rate
        icon: mdi:water-percent
    title: Charge Settings
  - type: entities
    entities:
      - entity: switch.lux_ac_charge_enable
      - entity: number.lux_ac_charge_power_rate
      - entity: number.lux_ac_battery_charge_level
      - entity: input_datetime.lux_ac_charge_start1
      - entity: input_datetime.lux_ac_charge_end1
    title: AC Charge
  - type: entities
    entities:
      - entity: switch.lux_charge_priority
      - entity: number.lux_priority_charge_rate
      - entity: number.lux_priority_charge_level
      - entity: input_datetime.lux_force_charge_start1
      - entity: input_datetime.lux_force_charge_end1
    title: Charge Priority
    show_header_toggle: false
  - type: entities
    entities:
      - entity: number.lux_system_discharge_power_rate
      - entity: number.lux_on_grid_discharge_cut_off_soc
        icon: mdi:brightness-percent
      - entity: number.lux_off_grid_discharge_cut_off_soc
        icon: mdi:brightness-percent
      - entity: switch.lux_force_discharge_enable
      - entity: number.lux_forced_discharge_power_rate
      - entity: number.lux_off_grid_discharge_cut_off_soc
      - entity: input_datetime.lux_force_discharge_start1
      - entity: input_datetime.lux_force_discharge_end1
    title: Discharge Settings


We used to have an issue with disconnecting, so below is the notes for this although it's no longer an issue so it's just kept for history:

To solve the issue of data not flowing please import the reconnection blueprint in this folder (or read below). It will allow you to reconnect if the inverter doesn't report for X minutes (I would set it to 20 minutes but absolutely no lower than 10)

The Blueprint import should help below but please report back if it doesn't work for you.
[![Blueprint](https://my.home-assistant.io/badges/blueprint_import.svg)](https://my.home-assistant.io/redirect/blueprint_import/?blueprint_url=https://github.com/guybw/LuxPythonCard/blob/main/blueprints/automation/luxpower/reconnect.yaml)

This is a blueprint that you can add that will automatically try to reconnect if the connection drops for a set time, as the dongles can be a bit flakey.
To install, 
- add a new automation and select the "Luxpower reconnect"
- Select "Lux - data receive time" as a trigger
- Select the interval to check on.
![image](https://user-images.githubusercontent.com/435874/188263388-8814be9b-6075-4e66-98a0-8818cdb2b321.png)
```
then click Save.
If any sensors are wrong, just remove the "_2" off the sensor name. Tip: If you click on the view "Default", you will get an automatically generated list of all the sensors that are known by HA, you can usually find what you are looking for here, especially after clicking on it and inspecting its naming.
