please read the first readme BEFORE following any instructions on this page, this is more of a notes page and likely will not work the first time around.

#Changing the time in HA for Lux times
This is a work in progess  - do not use it yet.

Below is to just change the AC start and end time, you can do it for the others if you wish for discharging etc.
Create 2 helpers
HELPER -  AC Charge Start
HELPER -  AC Charge End
This should be a time helper

Create 2 automations:

First 
```
alias: Auto Set HA Input On Lux Timer Change
description: ""
trigger:
  - platform: state
    entity_id:
      - number.lux_ac_charge_start
      - number.lux_ac_charge_end
      - number.lux_force_charge_start
      - number.lux_force_charge_end
      - number.lux_force_discharge_start
      - number.lux_force_discharge_end
condition: []
action:
  - service: input_datetime.set_datetime
    data:
      time: >
        {% set minutes = (states(trigger.entity_id)|int / 256)|int  %}  {% set
        hours = states(trigger.entity_id)|int - minutes * 256  %} {{
        '%0.2d'|format(hours)+':'+'%0.2d'|format(minutes)+':00' }}
    target:
      entity_id: >
        {% set entity_suffix = trigger.entity_id[10:] %}
        {{'input_datetime.lux'~entity_suffix}}
mode: single
```

Second
```
alias: Auto Set Lux Timer On HA Input Change
description: ""
trigger:
  - platform: state
    entity_id:
      - input_datetime.lux_ac_charge_start
      - input_datetime.lux_ac_charge_end
      - input_datetime.lux_force_charge_start
      - input_datetime.lux_force_charge_end
      - input_datetime.lux_force_discharge_start
      - input_datetime.lux_force_discharge_end
condition: []
action:
  - service: number.set_value
    data:
      value: >-
        {{state_attr(trigger.entity_id, 'minute')|int * 256 +
        state_attr(trigger.entity_id, 'hour')|int }}
    target:
      entity_id: >-
        {% set entity_suffix = trigger.entity_id[18:] %}
        {{'number.lux'~entity_suffix}}
mode: single
```



You can then add this YAML into lovelace to show the times in HA:
```
type: entities
entities:
  - entity: input_datetime.lux_ac_charge_start
  - entity: input_datetime.lux_ac_charge_end
  - entity: input_datetime.lux_force_charge_start
  - entity: input_datetime.lux_force_charge_end
title: Lux Charge Times
show_header_toggle: false
```


# below will refresh the data in HA every 30 seconds.
This is a work in progess  

Import the automation below, this will refresh the data every 30 seconds. DO NOT SET IT LOWER IT WILL BREAK! this is due to the inverter taking time to respond, not my code!

You MUST include your dongle number below!
```
alias: "DEBUG - refresh Lux regularly "
description: ""
trigger:
  - platform: time_pattern
    seconds: "30"
condition: []
action:
  - service: luxpower.luxpower_refresh_registers
    data:
      dongle: BA*******
mode: single

```

# LoveLace (GUI Example)
In order to help you off, you can create a new card in LoveLace. 
Simply go to, Settings > Dashboards> Click on the Dashboard you want to add or click on "Open" on Overview if you don't have a custom one.
Click on the three dots ... at the top right and click Edit Dashboard. At the bottom right click "Add Card" , scroll to the bottom and find "Manual" and click on it, delete the type: etc and paste below:
```
type: vertical-stack
cards:
  - type: entities
    entities:
      - entity: switch.lux_power_backup_enable_2
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
      - entity: number.lux_ac_charge_power_rate
      - entity: number.lux_ac_battery_charge_level
      - entity: input_datetime.lux_ac_charge_start
      - entity: input_datetime.lux_ac_charge_end
    title: AC Charge
  - type: entities
    entities:
      - entity: switch.lux_charge_priority_2
      - entity: number.lux_priority_charge_rate
      - entity: number.lux_priority_charge_level
      - entity: number.lux_force_charge_start
      - entity: number.lux_force_charge_end
    title: Charge Priority
    show_header_toggle: false
  - type: entities
    entities:
      - entity: number.lux_system_discharge_power_rate
      - entity: number.lux_on_grid_discharge_cut_off_soc
        icon: mdi:brightness-percent
      - entity: number.lux_off_grid_discharge_cut_off_soc_2
        icon: mdi:brightness-percent
      - entity: switch.lux_force_discharge_enable_2
      - entity: number.lux_forced_discharge_power_rate
      - entity: number.lux_off_grid_discharge_cut_off_soc_2
      - entity: number.lux_force_discharge_start
      - entity: number.lux_force_discharge_end
    title: Discharge Settings
```
then click save.
If any sensors are wrong, just remove the "_2" off the sensor name.
