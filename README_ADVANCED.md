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
