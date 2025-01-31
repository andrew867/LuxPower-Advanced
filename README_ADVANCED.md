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





## Guide for Setting Up the Helpers and Automation in Home Assistant
This guide walks you through creating the necessary helpers and automating the process of setting charge times using input and time helpers in Home Assistant. If you’re new to automations or helpers, follow the steps closely, and by the end, you’ll have a working setup.

Step 1: Create the Helpers
Navigate to Helpers

Open Home Assistant and go to Settings > Devices & Services > Helpers.
Click on + Create Helper.
Create Two Helpers

Helper 1: Select Input Datetime and set it up as follows:

Name: lux_helper_start
Enable Time: Yes
Enable Date: No

Helper 2: Create another Input Datetime helper:

Name: lux_helper_end
Enable Time: Yes
Enable Date: No
These helpers will store the starting and ending charge times dynamically.

Step 2: Setting Up the Automation
Go to Automations

Navigate to Settings > Automations & Scenes > + Create Automation.
Select Start with an Empty Automation.


Switch to the YAML editor (at the top of the automation editor) and paste the provided automation code at the bottom of the page.
Ensure you replace or adjust the entities if needed, but if you followed the setup exactly, they should match.
Step 3: Customizing the Duration
For testing purposes, instead of waiting 30 minutes, reduce the delay to 20 seconds:
``
- delay:
    hours: 0
    minutes: 0
    seconds: 20
``
Once testing is successful, you can restore this to a longer duration (like 30 minutes).

Step 4: Testing the Automation
Manually Trigger the Automation

In Settings > Automations, find your newly created automation.
Click Run Actions to test it manually.
Observe whether the times update correctly, and the switch is enabled as expected.
Monitor the Actions

Go to Settings > Developer Tools > States, and check the states of:
time.lux_ac_charge_start1
time.lux_ac_charge_end1
switch.lux_ac_charge_enable

Step 5: Troubleshooting

If you encounter issues, ensure:
Helpers are correctly configured with time enabled.
No typos in entity names within the automation YAML.
The input_datetime.set_datetime action is correctly receiving the time in the proper format.
Conclusion
You’ve now set up an automation that dynamically updates start and end times for charging and enables the process. Adjusting the delay or testing the automation manually can help refine its functionality to fit your needs.


```
alias: Lux boost charge for 30 minutes
description: Automate charging sequence based on lux sensor
triggers: []
actions:
  - target:
      entity_id: input_datetime.lux_helper_start
    data:
      value: "{{ states('time.lux_ac_charge_start') }}"
    action: input_datetime.set_datetime
  - target:
      entity_id: input_datetime.lux_helper_end
    data:
      value: "{{ states('time.lux_ac_charge_end') }}"
    action: input_datetime.set_datetime
  - data:
      time: "00:01:00"
    action: time.set_value
    target:
      entity_id: time.lux_ac_charge_start1
  - delay:
      hours: 0
      minutes: 0
      seconds: 5
  - data:
      time: "23:59:00"
    action: time.set_value
    target:
      entity_id: time.lux_ac_charge_end1
  - delay:
      hours: 0
      minutes: 0
      seconds: 5
  - target:
      entity_id: switch.lux_ac_charge_enable
    data: {}
    action: switch.turn_on
  - delay:
      hours: 0
      minutes: 30
      seconds: 0
      milliseconds: 0
  - target:
      entity_id: time.lux_ac_charge_start
    data:
      value: "{{ states('input_datetime.lux_helper_start') }}"
    action: time.set_value
  - target:
      entity_id: time.lux_ac_charge_end
    data:
      value: "{{ states('input_datetime.lux_helper_end') }}"
    action: time.set_value
```
