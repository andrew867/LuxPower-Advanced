
# Guide for Setting Up Aggregate Sensors for Multiple Lux Inverters in Home Assistant

This guide walks you through integrating two Lux inverters into Home Assistant and configuring **aggregate sensors** that combine data from both inverters. We’ll provide detailed instructions, from setup to sensor configuration, to ensure that even beginners can successfully follow along.

---

## Step 1: Install the Lux Integration
If you haven’t already installed the custom integration you developed or downloaded for the Lux inverters, follow these steps:

1. Go to **Settings > Devices & Services > Integrations**.
2. Click **Add Integration** and search for **Lux Inverter**.
3. Complete the setup process as instructed by the integration.

---

## Step 2: Ensure You Select "Use Serial to Be Part of Entity"
During the setup of each inverter:

1. When prompted to configure the inverters, **check the option labeled “Use serial to be part of the entity name”**.
    - This ensures that each sensor entity includes the unique serial number of the inverter.
2. Note down the serial numbers of both inverters (e.g., `1212016010` and `1212016024`). You will need these when configuring the template sensors.

---

## Step 3: Check Available Sensors for Each Inverter
Once the inverters are set up, verify that you can see the sensors for both inverters.

1. Navigate to **Settings > Developer Tools > States**.
2. In the search bar, type part of the serial number (e.g., `1212016010`) and confirm that sensors are available.
3. Repeat this for the second inverter’s serial number (e.g., `1212016024`).

---

## Step 4: Configure the Template Sensors
The following template YAML code aggregates sensor data from both inverters. Replace the placeholder serial numbers (`1212016010` and `1212016024`) with your actual inverter serial numbers.

### Example Template Configuration
Create or edit a file called `sensors.yaml` in your Home Assistant configuration directory (if you don’t already have one).

```yaml

- sensor:

### Aggregate Lux Sensors Across All Inverters

### Aggregate Sensor for 'Lux - Battery %'
  - name: 'Lux - Battery %'
    unique_id: sensor.lux_battery
    unit_of_measurement: '%'
    device_class: battery
    state: >
      {% if states('sensor.lux_1212016010_battery').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ (states('sensor.lux_1212016010_battery')|float(0) + states('sensor.lux_1212016024_battery')|float(0)) / 2 | round(0) }}
      {% endif %}

### Repeat Similar Configuration for Other Aggregate Sensors
```

---

### **Example of Replacing Serial Numbers**
If your inverters have serial numbers `1234567890` and `0987654321`, replace occurrences of `1212016010` and `1212016024` in the template code like this:

```yaml
- sensor:
  - name: 'Lux - Battery %'
    unique_id: sensor.lux_battery
    unit_of_measurement: '%'
    device_class: battery
    state: >
      {% if states('sensor.lux_1234567890_battery').lower() in ['unknown', 'unavailable'] or states('sensor.lux_0987654321_battery').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ (states('sensor.lux_1234567890_battery')|float(0) + states('sensor.lux_0987654321_battery')|float(0)) / 2 | round(0) }}
      {% endif %}
```

---

## Step 5: Include the Template File in `configuration.yaml`
To make Home Assistant recognize the template sensors, include the `sensors.yaml` file in your `configuration.yaml`:

```yaml
template: !include sensors.yaml
```

Then, restart Home Assistant for the changes to take effect.

---

## Step 6: Verify the Aggregate Sensors
1. Navigate to **Settings > Developer Tools > States**.
2. Search for the newly created sensors (e.g., `sensor.lux_battery`).
3. Ensure that they are updating and displaying the expected aggregated values.

---

## Troubleshooting
- If the sensors show `unavailable`, ensure:
  - Both inverters are online and connected.
  - The serial numbers are correctly entered in the template.
  - The `sensors.yaml` file is correctly included in `configuration.yaml`.

---

## Additional Aggregate Sensors
You can follow the same process to configure additional aggregate sensors. The provided template covers a variety of sensors such as:
- Battery Charge (Daily, Live, and Total)
- Battery Discharge (Daily, Live, and Total)
- Grid Flow and Voltage
- Solar Output (Daily, Live, and Total)

For each, make sure you replace the serial numbers accordingly.

---

## Example: Full YAML for Multiple Sensors
Here’s an expanded example showing multiple sensor configurations:

```yaml
- sensor:

### Aggregate Lux Sensors Across All Inverters

### Aggregate Sensor for 'Lux - Battery %'
  - name: 'Lux - Battery %'
    unique_id: sensor.lux_battery
    unit_of_measurement: '%'
    device_class: battery
    state: >
      {% if states('sensor.lux_1212016010_battery').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ (states('sensor.lux_1212016010_battery')|float(0) + states('sensor.lux_1212016024_battery')|float(0))/2|float(0)|round(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Charge (Daily)'
  - name: 'Lux - Battery Charge (Daily)'
    unique_id: sensor.lux_battery_charge_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_battery_charge_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_charge_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_charge_daily')|float(0) + states('sensor.lux_1212016024_battery_charge_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Charge (Live)'
  - name: 'Lux - Battery Charge (Live)'
    unique_id: sensor.lux_battery_charge_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_battery_charge_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_charge_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_charge_live')|float(0) + states('sensor.lux_1212016024_battery_charge_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Charge (Total)'
  - name: 'Lux - Battery Charge (Total)'
    unique_id: sensor.lux_battery_charge_total
    state_class: total_increasing
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_battery_charge_total').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_charge_total').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_charge_total')|float(0) + states('sensor.lux_1212016024_battery_charge_total')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Discharge (Daily)'
  - name: 'Lux - Battery Discharge (Daily)'
    unique_id: sensor.lux_battery_discharge_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_battery_discharge_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_discharge_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_discharge_daily')|float(0) + states('sensor.lux_1212016024_battery_discharge_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Discharge (Live)'
  - name: 'Lux - Battery Discharge (Live)'
    unique_id: sensor.lux_battery_discharge_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_battery_discharge_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_discharge_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_discharge_live')|float(0) + states('sensor.lux_1212016024_battery_discharge_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Discharge (Total)'
  - name: 'Lux - Battery Discharge (Total)'
    unique_id: sensor.lux_battery_discharge_total
    state_class: total_increasing
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_battery_discharge_total').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_discharge_total').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_discharge_total')|float(0) + states('sensor.lux_1212016024_battery_discharge_total')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Flow (Live)'
  - name: 'Lux - Battery Flow (Live)'
    unique_id: sensor.lux_battery_flow_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_battery_flow_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_flow_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_battery_flow_live')|float(0) + states('sensor.lux_1212016024_battery_flow_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Battery Voltage (Live)'
  - name: 'Lux - Battery Voltage (Live)'
    unique_id: sensor.lux_battery_voltage_live
    unit_of_measurement: 'V'
    device_class: voltage
    state: >
      {% if states('sensor.lux_1212016010_battery_voltage_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_battery_voltage_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ (states('sensor.lux_1212016010_battery_voltage_live')|float(0) + states('sensor.lux_1212016024_battery_voltage_live')|float(0))/2|float(0)|round(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - BMS Limit Charge (Live)'
  - name: 'Lux - BMS Limit Charge (Live)'
    unique_id: sensor.lux_bms_limit_charge_live
    unit_of_measurement: 'A'
    device_class: current
    state: >
      {% if states('sensor.lux_1212016010_bms_limit_charge_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_bms_limit_charge_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_bms_limit_charge_live')|float(0) + states('sensor.lux_1212016024_bms_limit_charge_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - BMS Limit Discharge (Live)'
  - name: 'Lux - BMS Limit Discharge (Live)'
    unique_id: sensor.lux_bms_limit_discharge_live
    unit_of_measurement: 'A'
    device_class: current
    state: >
      {% if states('sensor.lux_1212016010_bms_limit_discharge_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_bms_limit_discharge_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_bms_limit_discharge_live')|float(0) + states('sensor.lux_1212016024_bms_limit_discharge_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Grid Flow (Live)'
  - name: 'Lux - Grid Flow (Live)'
    unique_id: sensor.lux_grid_flow_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_grid_flow_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_grid_flow_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_grid_flow_live')|float(0) + states('sensor.lux_1212016024_grid_flow_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Grid Voltage (Live) '
  - name: 'Lux - Grid Voltage (Live) '
    unique_id: sensor.lux_grid_voltage_live
    unit_of_measurement: 'V'
    device_class: voltage
    state: >
      {% if states('sensor.lux_1212016010_grid_voltage_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_grid_voltage_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ (states('sensor.lux_1212016010_grid_voltage_live')|float(0) + states('sensor.lux_1212016024_grid_voltage_live')|float(0))/2|float(0)|round(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Home Consumption (Daily)'
  - name: 'Lux - Home Consumption (Daily)'
    unique_id: sensor.lux_home_consumption_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_home_consumption_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_home_consumption_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_home_consumption_daily')|float(0) + states('sensor.lux_1212016024_home_consumption_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Home Consumption (Live)'
  - name: 'Lux - Home Consumption (Live)'
    unique_id: sensor.lux_home_consumption_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_home_consumption_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_home_consumption_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_home_consumption_live')|float(0) + states('sensor.lux_1212016024_home_consumption_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power from Grid (Daily)'
  - name: 'Lux - Power from Grid (Daily)'
    unique_id: sensor.lux_power_from_grid_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_power_from_grid_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_from_grid_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_from_grid_daily')|float(0) + states('sensor.lux_1212016024_power_from_grid_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power from Grid (Live)'
  - name: 'Lux - Power from Grid (Live)'
    unique_id: sensor.lux_power_from_grid_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_power_from_grid_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_from_grid_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_from_grid_live')|float(0) + states('sensor.lux_1212016024_power_from_grid_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power from grid to HOUSE (Live)'
  - name: 'Lux - Power from grid to HOUSE (Live)'
    unique_id: sensor.lux_power_from_grid_to_house_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_power_from_grid_to_house_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_from_grid_to_house_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_from_grid_to_house_live')|float(0) + states('sensor.lux_1212016024_power_from_grid_to_house_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power from Grid (Total)'
  - name: 'Lux - Power from Grid (Total)'
    unique_id: sensor.lux_power_from_grid_total
    state_class: total_increasing
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_power_from_grid_total').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_from_grid_total').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_from_grid_total')|float(0) + states('sensor.lux_1212016024_power_from_grid_total')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power from Inverter (Live)'
  - name: 'Lux - Power from Inverter (Live)'
    unique_id: sensor.lux_power_from_inverter_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_power_from_inverter_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_from_inverter_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_from_inverter_live')|float(0) + states('sensor.lux_1212016024_power_from_inverter_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power from Inverter to Home (Daily)'
  - name: 'Lux - Power from Inverter to Home (Daily)'
    unique_id: sensor.lux_power_from_inverter_to_home_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_power_from_inverter_to_home_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_from_inverter_to_home_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_from_inverter_to_home_daily')|float(0) + states('sensor.lux_1212016024_power_from_inverter_to_home_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power To Grid (Daily)'
  - name: 'Lux - Power To Grid (Daily)'
    unique_id: sensor.lux_power_to_grid_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_power_to_grid_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_to_grid_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_to_grid_daily')|float(0) + states('sensor.lux_1212016024_power_to_grid_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power To Grid (Live)'
  - name: 'Lux - Power To Grid (Live)'
    unique_id: sensor.lux_power_to_grid_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_power_to_grid_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_to_grid_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_to_grid_live')|float(0) + states('sensor.lux_1212016024_power_to_grid_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power To Grid (Total)'
  - name: 'Lux - Power To Grid (Total)'
    unique_id: sensor.lux_power_to_grid_total
    state_class: total_increasing
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_power_to_grid_total').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_to_grid_total').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_to_grid_total')|float(0) + states('sensor.lux_1212016024_power_to_grid_total')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power to Inverter (Daily)'
  - name: 'Lux - Power to Inverter (Daily)'
    unique_id: sensor.lux_power_to_inverter_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_power_to_inverter_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_to_inverter_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_to_inverter_daily')|float(0) + states('sensor.lux_1212016024_power_to_inverter_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Power to Inverter (Live)'
  - name: 'Lux - Power to Inverter (Live)'
    unique_id: sensor.lux_power_to_inverter_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_power_to_inverter_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_power_to_inverter_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_power_to_inverter_live')|float(0) + states('sensor.lux_1212016024_power_to_inverter_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Solar Output Array 1 (Live)'
  - name: 'Lux - Solar Output Array 1 (Live)'
    unique_id: sensor.lux_solar_output_array_1_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_solar_output_array_1_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_solar_output_array_1_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_solar_output_array_1_live')|float(0) + states('sensor.lux_1212016024_solar_output_array_1_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Solar Output Array 2 (Live)'
  - name: 'Lux - Solar Output Array 2 (Live)'
    unique_id: sensor.lux_solar_output_array_2_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_solar_output_array_2_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_solar_output_array_2_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_solar_output_array_2_live')|float(0) + states('sensor.lux_1212016024_solar_output_array_2_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Solar Output (Daily)'
  - name: 'Lux - Solar Output (Daily)'
    unique_id: sensor.lux_solar_output_daily
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_solar_output_daily').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_solar_output_daily').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_solar_output_daily')|float(0) + states('sensor.lux_1212016024_solar_output_daily')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Solar Output (Live)'
  - name: 'Lux - Solar Output (Live)'
    unique_id: sensor.lux_solar_output_live
    unit_of_measurement: 'W'
    device_class: power
    state: >
      {% if states('sensor.lux_1212016010_solar_output_live').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_solar_output_live').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_solar_output_live')|float(0) + states('sensor.lux_1212016024_solar_output_live')|float(0) }}
      {% endif %}

### Aggregate Sensor for 'Lux - Solar Output (Total)'
  - name: 'Lux - Solar Output (Total)'
    unique_id: sensor.lux_solar_output_total
    state_class: total_increasing
    unit_of_measurement: 'kWh'
    device_class: energy
    state: >
      {% if states('sensor.lux_1212016010_solar_output_total').lower() in ['unknown', 'unavailable'] or states('sensor.lux_1212016024_solar_output_total').lower() in ['unknown', 'unavailable'] %}
        unavailable
      {% else %}
        {{ states('sensor.lux_1212016010_solar_output_total')|float(0) + states('sensor.lux_1212016024_solar_output_total')|float(0) }}
      {% endif %}


```

---

## Conclusion
By following this guide, you can combine sensor data from two Lux inverters in Home Assistant and create aggregate values that provide a complete overview of your system. Make sure to verify each step and customize the template code as needed to match your setup.
