# HOWTO - Setup a Quick Charge Automation

This section guides you through setting up a 30-minute boost charge automation using input and time helpers. Follow the instructions to create an easy-to-manage charge control in Home Assistant.

# Helper and Automation Setup Guide

## Step 1: Create the Helpers

1. **Go to Settings:**
   - Open **Home Assistant > Settings > Devices & Services > Helpers**.

2. **Create Two Helpers:**
   - **Helper 1:** Input Datetime
     - **Name:** `lux_helper_start`
     - **Enable Time:** Yes
     - **Enable Date:** No

   - **Helper 2:** Input Datetime
     - **Name:** `lux_helper_end`
     - **Enable Time:** Yes
     - **Enable Date:** No

These helpers store the start and end charge times dynamically.

## Step 2: Setting Up the Automation

1. **Go to Automations:**
   - Navigate to **Settings > Automations & Scenes**.
   - Click **+ Create Automation** and select **Start with an Empty Automation**.

2. **Switch to YAML Editor:**
   - Paste the automation code provided below.
   - Ensure all entities are correctly referenced.

```yaml
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

## Step 3: Customizing the Duration

- For testing purposes, change the delay to 20 seconds:

```yaml
- delay:
    hours: 0
    minutes: 0
    seconds: 20
```

- Once testing is successful, restore the delay to 30 minutes.

## Step 4: Testing the Automation

1. **Manually Trigger the Automation:**
   - Go to **Settings > Automations** and find the automation.
   - Click **Run Actions** to test it.

2. **Monitor Updates:**
   - Open **Settings > Developer Tools > States** and check:
     - `time.lux_ac_charge_start1`
     - `time.lux_ac_charge_end1`
     - `switch.lux_ac_charge_enable`

## Step 5: Troubleshooting

- Ensure the helpers are correctly configured.
- Double-check the YAML entity names.
- Ensure `input_datetime.set_datetime` actions receive proper time formats.

## Conclusion

Congratulations! You’ve set up dynamic charge automation and can test or modify it easily using the helpers and automations. 




---

# LoveLace (GUI Example)

## Setting Up a Lovelace Card

1. **Navigate to Lovelace:**
   - Go to **Settings > Dashboards**.
   - Select the dashboard you want to modify or click **Open** on the Overview dashboard if you don't have a custom one.

2. **Edit the Dashboard:**
   - Click the three dots (`...`) at the top right and select **Edit Dashboard**.
   - At the bottom right, click **Add Card**.
   - Scroll to the bottom and find **Manual**.

3. **Insert the Card:**
   - Delete the prefilled text, then copy and paste the following:

```yaml
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
```

Click **Save** to add the card.

---

## Reconnection Notes (Historical)

We used to have an issue with disconnecting. Below are the legacy notes for reference, but this issue has been resolved.

### To Solve the Issue of Data Not Flowing:
- Import the **reconnection blueprint** from this folder.
- This blueprint allows you to reconnect if the inverter doesn't report for a specified duration (recommendation: 20 minutes, no less than 10).

#### Blueprint Import Link:
[![Blueprint](https://my.home-assistant.io/badges/blueprint_import.svg)](https://my.home-assistant.io/redirect/blueprint_import/?blueprint_url=https://github.com/guybw/LuxPythonCard/blob/main/blueprints/automation/luxpower/reconnect.yaml)

---

### Setting Up the Blueprint

1. **Add a New Automation:**
   - Select **Luxpower Reconnect** as the blueprint.
   - Select **Lux - data receive time** as the trigger.
   - Set an appropriate interval for checks.

![image](https://user-images.githubusercontent.com/435874/188263388-8814be9b-6075-4e66-98a0-8818cdb2b321.png)

---

