"""

This is a docstring placeholder.

This is where we will describe what this module does

"""
import logging
import time
from datetime import date, datetime
from typing import Any, Dict, List, Optional, Union

from homeassistant.components.sensor import SensorEntity, SensorStateClass
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import (
    CONF_MODE,
    DEVICE_CLASS_BATTERY,
    DEVICE_CLASS_CURRENT,
    DEVICE_CLASS_ENERGY,
    DEVICE_CLASS_POWER,
    DEVICE_CLASS_TEMPERATURE,
    DEVICE_CLASS_VOLTAGE,
    ELECTRIC_CURRENT_AMPERE,
    ELECTRIC_POTENTIAL_VOLT,
    ENERGY_KILO_WATT_HOUR,
    PERCENTAGE,
    POWER_WATT,
    TEMP_CELSIUS,
)
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import DeviceInfo, Entity
from homeassistant.helpers.typing import StateType

from .const import (
    ATTR_LUX_DONGLE_SERIAL,
    ATTR_LUX_HOST,
    ATTR_LUX_PORT,
    ATTR_LUX_SERIAL_NUMBER,
    ATTR_LUX_USE_SERIAL,
    DOMAIN,
    UA,
    VERSION,
)
from .helpers import Event
from .LXPPacket import LXPPacket

_LOGGER = logging.getLogger(__name__)


async def async_setup_entry(hass: HomeAssistant, config_entry: ConfigEntry, async_add_devices):
    """Set up the sensor platform."""
    # We only want this platform to be set up via discovery.
    _LOGGER.info("Loading the Lux sensor platform")

    _LOGGER.info("Options", len(config_entry.options))
    platform_config = config_entry.data or {}
    if len(config_entry.options) > 0:
        platform_config = config_entry.options

    HOST = platform_config.get(ATTR_LUX_HOST, "127.0.0.1")
    PORT = platform_config.get(ATTR_LUX_PORT, 8000)
    DONGLE = platform_config.get(ATTR_LUX_DONGLE_SERIAL, "XXXXXXXXXX")
    SERIAL = platform_config.get(ATTR_LUX_SERIAL_NUMBER, "XXXXXXXXXX")
    USE_SERIAL = platform_config.get(ATTR_LUX_USE_SERIAL, False)

    entityID_prefix = SERIAL if USE_SERIAL else ""
    hyphen = " -" if USE_SERIAL else "-"
    # Get Rid Of Hyphen 15/02/2023
    hyphen = ""

    _LOGGER.info(f"Lux sensor platform_config: {platform_config}")

    stateSensors: List[Entity] = []

    event = Event(dongle=DONGLE)

    luxpower_client = hass.data[event.CLIENT_DAEMON]

    # fmt: off

    device_class = CONF_MODE
    unit = ""
    name = f"LUXPower {entityID_prefix}{hyphen}{hyphen}"
    stateSensors.append(LuxStateSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, name, device_class, unit, luxpower_client, event))

    sensors = []

    # Attribute sensor
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Discharge (Live)", "entity": "lux_battery_discharge", "bank": 0, "attribute": LXPPacket.p_discharge, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Charge (Live)", "entity": "lux_battery_charge", "bank": 0, "attribute": LXPPacket.p_charge, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery %", "entity": "lux_battery_percent", "bank": 0, "attribute": LXPPacket.soc, "device_class": DEVICE_CLASS_BATTERY, "unit_measure": PERCENTAGE})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Discharge (Daily)", "entity": "lux_daily_battery_discharge", "bank": 0, "attribute": LXPPacket.e_dischg_day, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Discharge (Total)", "entity": "lux_total_battery_discharge", "bank": 1, "attribute": LXPPacket.e_dischg_all, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Charge (Daily)", "entity": "lux_daily_battery_charge", "bank": 0, "attribute": LXPPacket.e_chg_day, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Charge (Total)", "entity": "lux_total_battery_charge", "bank": 1, "attribute": LXPPacket.e_chg_all, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Voltage (Live)", "entity": "lux_battery_voltage", "bank": 0, "attribute": LXPPacket.v_bat, "device_class": DEVICE_CLASS_VOLTAGE, "unit_measure": ELECTRIC_POTENTIAL_VOLT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} BMS Limit Charge (Live)", "entity": "lux_bms_limit_charge", "bank": 2, "attribute": LXPPacket.max_chg_curr, "device_class": DEVICE_CLASS_CURRENT, "unit_measure": ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} BMS Limit Discharge (Live)", "entity": "lux_bms_limit_discharge", "bank": 2, "attribute": LXPPacket.max_dischg_curr, "device_class": DEVICE_CLASS_CURRENT, "unit_measure": ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power From Inverter (Live)", "entity": "lux_power_from_inverter_live", "bank": 0, "attribute": LXPPacket.p_inv, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power To Inverter (Live)", "entity": "lux_power_to_inverter_live", "bank": 0, "attribute": LXPPacket.p_rec, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power From Grid to HOUSE (Live)", "entity": "lux_power_to_home", "bank": 0, "attribute": LXPPacket.p_load, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power To EPS (Live)", "entity": "lux_power_to_eps", "bank": 0, "attribute": LXPPacket.p_to_eps, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power From Grid (Live)", "entity": "lux_power_from_grid_live", "bank": 0, "attribute": LXPPacket.p_to_user, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power From Grid (Daily)", "entity": "lux_power_from_grid_daily", "bank": 0, "attribute": LXPPacket.e_to_user_day, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power From Grid (Total)", "entity": "lux_power_from_grid_total", "bank": 1, "attribute": LXPPacket.e_to_user_all, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power To Grid (Live)", "entity": "lux_power_to_grid_live", "bank": 0, "attribute": LXPPacket.p_to_grid, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power To Grid (Daily)", "entity": "lux_power_to_grid_daily", "bank": 0, "attribute": LXPPacket.e_to_grid_day, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power To Grid (Total)", "entity": "lux_power_to_grid_total", "bank": 1, "attribute": LXPPacket.e_to_grid_all, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Grid Voltage (Live) ", "entity": "lux_grid_voltage_live", "bank": 0, "attribute": LXPPacket.v_ac_r, "device_class": DEVICE_CLASS_VOLTAGE, "unit_measure": ELECTRIC_POTENTIAL_VOLT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power from Inverter to Home (Daily)", "entity": "lux_power_from_inverter_daily", "bank": 0, "attribute": LXPPacket.e_inv_day, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Power to Inverter (Daily)", "entity": "lux_power_to_inverter_daily", "bank": 0, "attribute": LXPPacket.e_rec_day, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Solar Output (Live)", "entity": "lux_current_solar_output", "bank": 0, "attribute": LXPPacket.p_pv_total, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Solar Output Array 1 (Live)", "entity": "lux_current_solar_output_1", "bank": 0, "attribute": LXPPacket.p_pv_1, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Solar Output Array 2 (Live)", "entity": "lux_current_solar_output_2", "bank": 0, "attribute": LXPPacket.p_pv_2, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Solar Output Array 3 (Live)", "entity": "lux_current_solar_output_3", "bank": 0, "attribute": LXPPacket.p_pv_3, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Solar Output (Daily)", "entity": "lux_daily_solar", "bank": 0, "attribute": LXPPacket.e_pv_total, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Solar Output (Total)", "entity": "lux_total_solar", "bank": 1, "attribute": LXPPacket.e_pv_all, "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR, "state_class": SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Internal Temperature (Live)", "entity": "lux_internal_temp", "bank": 1, "attribute": LXPPacket.t_inner, "device_class": DEVICE_CLASS_TEMPERATURE, "unit_measure": TEMP_CELSIUS})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Radiator 1 Temperature (Live)", "entity": "lux_radiator1_temp", "bank": 1, "attribute": LXPPacket.t_rad_1, "device_class": DEVICE_CLASS_TEMPERATURE, "unit_measure": TEMP_CELSIUS})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Radiator 2 temperature (Live)", "entity": "lux_radiator2_temp", "bank": 1, "attribute": LXPPacket.t_rad_2, "device_class": DEVICE_CLASS_TEMPERATURE, "unit_measure": TEMP_CELSIUS})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Max Cell Voltage (Live)", "entity": "max_cell_volt", "bank": 2, "attribute": LXPPacket.max_cell_volt, "device_class": DEVICE_CLASS_VOLTAGE, "unit_measure": ELECTRIC_POTENTIAL_VOLT, "decimal_places": 3})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Min Cell Voltage (Live)", "entity": "min_cell_volt", "bank": 2, "attribute": LXPPacket.min_cell_volt, "device_class": DEVICE_CLASS_VOLTAGE, "unit_measure": ELECTRIC_POTENTIAL_VOLT, "decimal_places": 3})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Max Cell Temperature (Live)", "entity": "max_cell_temp", "bank": 2, "attribute": LXPPacket.max_cell_temp, "device_class": DEVICE_CLASS_TEMPERATURE, "unit_measure": TEMP_CELSIUS})
    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Battery Min Cell Temperature (Live)", "entity": "min_cell_temp", "bank": 2, "attribute": LXPPacket.min_cell_temp, "device_class": DEVICE_CLASS_TEMPERATURE, "unit_measure": TEMP_CELSIUS})

    sensors.append({"name": f"Lux {entityID_prefix}{hyphen} Status", "entity": "lux_status", "bank": 0, "attribute": LXPPacket.status})
    for sensor_data in sensors:
        stateSensors.append(LuxpowerSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # Setup Data Received Timestamp sensor
    sensor_data = {"name": f"Lux {entityID_prefix}{hyphen} Data Received Time", "entity": "lux_data_last_received_time", "bank": 0, "attribute": LXPPacket.status}
    stateSensors.append(LuxPowerDataReceivedTimestampSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # Setup State Text sensor
    sensor_data = {"name": f"Lux {entityID_prefix}{hyphen} Status (Text)", "entity": "lux_status_text", "bank": 0, "attribute": LXPPacket.status}
    stateSensors.append(LuxPowerStatusTextSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # Multiple attribute calculated sensors
    # 1. Battery Flow Live
    sensor_data = {"name": f"Lux {entityID_prefix}{hyphen} Battery Flow (Live)", "entity": "lux_battery_flow", "bank": 0, "attribute": LXPPacket.p_discharge, "attribute1": LXPPacket.p_discharge, "attribute2": LXPPacket.p_charge, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT}  # Attribute dependencies
    stateSensors.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # 2. Grid Flow Live
    sensor_data = {"name": f"Lux {entityID_prefix}{hyphen} Grid Flow (Live)", "entity": "lux_grid_flow", "bank": 0, "attribute": LXPPacket.p_to_user, "attribute1": LXPPacket.p_to_user, "attribute2": LXPPacket.p_to_grid, "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT}  # Attribute dependencies
    stateSensors.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # 3. Home Consumption Live
    sensor_data = {
        "name": f"Lux {entityID_prefix}{hyphen} Home Consumption (Live)", "entity": "lux_home_consumption_live", "bank": 0,
        "attribute": LXPPacket.p_to_user, "attribute1": LXPPacket.p_to_user, "attribute2": LXPPacket.p_rec, "attribute3": LXPPacket.p_inv, "attribute4": LXPPacket.p_to_grid,
        "device_class": DEVICE_CLASS_POWER, "unit_measure": POWER_WATT,
    }
    # Attribute dependencies
    # att1. Power from grid to consumer, att2. Power from consumer to invert, att3. power from inv to consumer, att4. power from consumer to grid.
    stateSensors.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # 4. Home Consumption Daily
    sensor_data = {
        "name": f"Lux {entityID_prefix}{hyphen} Home Consumption (Daily)", "entity": "lux_home_consumption", "bank": 0,
        "attribute": LXPPacket.e_to_user_day, "attribute1": LXPPacket.e_to_user_day, "attribute2": LXPPacket.e_rec_day, "attribute3": LXPPacket.e_inv_day, "attribute4": LXPPacket.e_to_grid_day,
        "device_class": DEVICE_CLASS_ENERGY, "unit_measure": ENERGY_KILO_WATT_HOUR,
    }
    stateSensors.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # 5. Firmware Version
    sensor_data = {"name": f"Lux {entityID_prefix}{hyphen} Firmware Version", "entity": "lux_firmware_version", "bank": 0, "register": 7}
    stateSensors.append(LuxPowerFirmwareSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # fmt: on

    async_add_devices(stateSensors, True)

    _LOGGER.info("LuxPower sensor async_setup_platform sensor done %s", DONGLE)


class LuxpowerSensorEntity(SensorEntity):
    """Representation of a general numeric LUXpower sensor."""

    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):  # fmt: skip
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = sensor_data["name"]
        self._state = "Unavailable"
        self._stateval = None
        self.serial = serial
        self.dongle = dongle
        self._device_class = sensor_data.get("device_class", None)
        self._unit_of_measurement = sensor_data.get("unit_measure", None)
        self.is_added_to_hass = False
        self._data: Dict[str, str] = {}
        self._unique_id = "{}_{}_{}".format(DOMAIN, dongle, sensor_data["entity"])
        self._bank = sensor_data.get("bank", 0)
        self._device_attribute = sensor_data.get("attribute", None)
        self._state_class = sensor_data.get("state_class", None)
        self.decimal_places = sensor_data.get("decimal_places", 1)
        self.lastupdated_time = 0
        self.event = event

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            if self._bank == 0:
                self.hass.bus.async_listen(self.event.EVENT_DATA_BANK0_RECEIVED, self.push_update)
            elif self._bank == 1:
                self.hass.bus.async_listen(self.event.EVENT_DATA_BANK1_RECEIVED, self.push_update)
            elif self._bank == 2:
                self.hass.bus.async_listen(self.event.EVENT_DATA_BANK2_RECEIVED, self.push_update)
            else:
                self.hass.bus.async_listen(self.event.EVENT_DATA_RECEIVED, self.push_update)

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._name}"
        )
        self._data = event.data.get("data", {})
        value = self._data.get(self._device_attribute)
        value = round(value, self.decimal_places) if isinstance(value, (int, float)) else UA
        self._state = f"{value}"

        self.schedule_update_ha_state()
        return self._state

    def update(self):
        if not self.is_added_to_hass:
            return
        # _LOGGER.debug("{} updating state to {}".format(self._dp_id, self._stateval))
        return self._state

    @property
    def device_info(self):
        """Return device info."""
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model="LUXPower Inverter",
            name=self.dongle,
            sw_version=VERSION,
        )

    @property
    def state_class(self) -> Union[SensorStateClass, str, None]:
        return self._state_class

    @property
    def unique_id(self):
        """Return the unique id."""
        return self._unique_id

    @property
    def should_poll(self):
        """No polling needed for a demo sensor."""
        return False

    @property
    def device_class(self):
        """Return the device class of the sensor."""
        return self._device_class

    @property
    def name(self):
        """Return the name of the sensor."""
        return self._name

    @property
    def native_value(self) -> Union[StateType, date, datetime]:
        return self._state

    @property
    def native_unit_of_measurement(self) -> Optional[str]:
        return self._unit_of_measurement


class LuxPowerFlowSensor(LuxpowerSensorEntity):
    """
    Representation of a Numeric LUXpower Flow sensor.

    Template equation state = -1*attribute1 if attribute1 > 0 else attribute2
    """

    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self._device_attribute1 = sensor_data["attribute1"]
        self._device_attribute2 = sensor_data["attribute2"]

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._name}"
        )
        self._data = event.data.get("data", {})

        negative_value = float(self._data.get(self._device_attribute1, 0.0))
        positive_value = float(self._data.get(self._device_attribute2, 0.0))
        if negative_value > 0:
            flow_value = -1 * negative_value
        else:
            flow_value = positive_value
        self._state = f"{round(flow_value,1)}"

        self.schedule_update_ha_state()
        return self._state


class LuxPowerHomeConsumptionSensor(LuxpowerSensorEntity):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self._device_attribute1 = sensor_data["attribute1"]  # Power from grid to consumer unit
        self._device_attribute2 = sensor_data["attribute2"]  # Power from consumer unit to inverter
        self._device_attribute3 = sensor_data["attribute3"]  # Power from inverter to consumer unit
        self._device_attribute4 = sensor_data["attribute4"]  # Power from consumer unit to grid

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._name}"
        )
        self._data = event.data.get("data", {})

        grid = float(self._data.get(self._device_attribute1, 0.0))
        to_inverter = float(self._data.get(self._device_attribute2, 0.0))
        from_inverter = float(self._data.get(self._device_attribute3, 0.0))
        to_grid = float(self._data.get(self._device_attribute4, 0.0))
        consumption_value = grid - to_inverter + from_inverter - to_grid
        self._state = f"{round(consumption_value, 1)}"

        self.schedule_update_ha_state()
        return self._state


class LuxPowerRegisterSensor(LuxpowerSensorEntity):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self._register_address = sensor_data["register"]

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            if self._register_address == 21:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_21_RECEIVED, self.push_update)
            elif 0 <= self._register_address <= 39:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK0_RECEIVED, self.push_update)
            elif 40 <= self._register_address <= 79:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK1_RECEIVED, self.push_update)
            elif 80 <= self._register_address <= 119:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK2_RECEIVED, self.push_update)
            elif 120 <= self._register_address <= 159:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK3_RECEIVED, self.push_update)
            elif 160 <= self._register_address <= 199:
                self.hass.bus.async_listen(self.event.EVENT_REGISTER_BANK4_RECEIVED, self.push_update)

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Register: {self._register_address} Name: {self._name}"
        )
        registers = event.data.get("registers", {})
        self._data = registers

        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self._register_address} is in register.keys")
            register_val = registers.get(self._register_address, None)
            if register_val is None:
                return
            oldstate = self._state
            self._state = float(register_val)
            if oldstate != self._state:
                _LOGGER.debug(f"Register sensor has changed from {oldstate} to {self._state}")
                self.schedule_update_ha_state()
        return self._state


class LuxPowerFirmwareSensor(LuxPowerRegisterSensor):
    """
    Used for both live and daily consumption calculation.

    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    """

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Register: {self._register_address} Name: {self._name}"
        )
        registers = event.data.get("registers", {})
        self._data = registers

        if self._register_address in registers.keys():
            _LOGGER.debug(f"Register Address: {self._register_address} is in register.keys")
            reg07_val = registers.get(7, None)
            reg08_val = registers.get(8, None)
            reg09_val = registers.get(9, None)
            reg10_val = registers.get(10, None)
            if reg07_val is None or reg08_val is None:
                return
            reg07_str = int(reg07_val).to_bytes(2, "little").decode()
            reg08_str = int(reg08_val).to_bytes(2, "little").decode()
            reg09_str = int(reg09_val).to_bytes(2, byteorder="big").hex()[0:2]
            reg10_str = int(reg10_val).to_bytes(2, byteorder="little").hex()[0:2]

            oldstate = self._state
            self._state = reg07_str + reg08_str + "-" + reg09_str + reg10_str
            if oldstate != self._state:
                _LOGGER.debug(f"Register sensor has changed from {oldstate} to {self._state}")
                self.schedule_update_ha_state()
        return self._state


class LuxPowerStatusTextSensor(LuxpowerSensorEntity):
    """Representation of a Status sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._name}"
        )
        self._data = event.data.get("data", {})
        state_text = ""
        status = int(self._data.get(self._device_attribute, 0.0))
        if status == 0:
            state_text = "Standby"
        elif status == 2:
            state_text = "Inverting"
        elif status == 4:
            state_text = "Silent"
        elif status == 5:
            state_text = "Float"
        elif status == 64:
            state_text = "No AC Power"
        elif status == 7:
            state_text = "Charger Off"
        elif status == 8:
            state_text = "Support"
        elif status == 9:
            state_text = "Selling"
        elif status == 10:
            state_text = "Pass Through"
        elif status == 12:
            state_text = "Solar + Battery Charging"
        elif status == 16:
            state_text = "Battery Discharging"
        elif status == 17:
            state_text = "Temperature Over Range"
        elif status == 20:
            state_text = "Solar + Battery Discharging"
        elif status == 32:
            state_text = "Battery Charging"
        elif status == 40:
            state_text = "Solar + Grid + Battery Charging"
        elif status == 11:
            state_text = "Offsetting"
        elif status == 192:
            state_text = "No grid power available, using solar + battery"
        else:
            state_text = "Unknown"
        self._state = f"{state_text}"

        self.schedule_update_ha_state()
        return self._state


class LuxPowerDataReceivedTimestampSensor(LuxpowerSensorEntity):
    """Representation of an Date & Time updated sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):  # fmt: skip
        """Initialize the sensor."""
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self.datetime_last_received = None

    def push_update(self, event):
        _LOGGER.debug(
            f"Sensor: register event received Bank: {self._bank} Attrib: {self._device_attribute} Name: {self._name}"
        )
        self._data = event.data.get("data", {})
        self.datetime_last_received = datetime.now()
        self._state = "{}".format(datetime.now().strftime("%A %B %-d, %I:%M %p"))

        self.schedule_update_ha_state()
        return self._state

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes["dongle"] = self.dongle
        if self.datetime_last_received is not None:
            state_attributes["timestamp"] = self.datetime_last_received.timestamp()
        else:
            state_attributes["timestamp"] = 0
        return state_attributes


class LuxStateSensorEntity(Entity):
    """Representation of an overall sensor for a LUXPower Inverter."""

    def __init__(self, hass, host, port, dongle, serial, name, device_class, unit_measure, luxpower_client, event: Event):  # fmt: skip
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = name
        self.dongle = dongle
        self.serial = serial
        self._state = "Waiting"
        self._stateval = None
        self._device_class = device_class
        self._unit_of_measurement = unit_measure
        self.is_added_to_hass = False
        self._data: Dict[str, str] = {}
        self.luxpower_client = luxpower_client
        self.lastupdated_time = 0
        self.event = event
        self.totaldata: Dict[str, str] = {}

    # fmt: off

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes[LXPPacket.status] = f"{self.totaldata.get(LXPPacket.status, UA)}"
        state_attributes[LXPPacket.v_pv_1] = f"{self.totaldata.get(LXPPacket.v_pv_1, UA)}"
        state_attributes[LXPPacket.v_pv_2] = f"{self.totaldata.get(LXPPacket.v_pv_2, UA)}"
        state_attributes[LXPPacket.v_pv_3] = f"{self.totaldata.get(LXPPacket.v_pv_3, UA)}"
        state_attributes[LXPPacket.v_bat] = f"{self.totaldata.get(LXPPacket.v_bat, UA)}"
        state_attributes[LXPPacket.soc] = f"{self.totaldata.get(LXPPacket.soc, UA)}"
        state_attributes[LXPPacket.p_pv_1] = f"{self.totaldata.get(LXPPacket.p_pv_1, UA)}"
        state_attributes[LXPPacket.p_pv_2] = f"{self.totaldata.get(LXPPacket.p_pv_2, UA)}"
        state_attributes[LXPPacket.p_pv_3] = f"{self.totaldata.get(LXPPacket.p_pv_3, UA)}"
        state_attributes[LXPPacket.p_pv_total] = f"{self.totaldata.get(LXPPacket.p_pv_total, UA)}"
        state_attributes[LXPPacket.p_charge] = f"{self.totaldata.get(LXPPacket.p_charge, UA)}"
        state_attributes[LXPPacket.p_discharge] = f"{self.totaldata.get(LXPPacket.p_discharge, UA)}"
        state_attributes[LXPPacket.v_ac_r] = f"{self.totaldata.get(LXPPacket.v_ac_r, UA)}"
        state_attributes[LXPPacket.v_ac_s] = f"{self.totaldata.get(LXPPacket.v_ac_s, UA)}"
        state_attributes[LXPPacket.v_ac_t] = f"{self.totaldata.get(LXPPacket.v_ac_t, UA)}"
        state_attributes[LXPPacket.f_ac] = f"{self.totaldata.get(LXPPacket.f_ac, UA)}"
        state_attributes[LXPPacket.p_inv] = f"{self.totaldata.get(LXPPacket.p_inv, UA)}"
        state_attributes[LXPPacket.p_rec] = f"{self.totaldata.get(LXPPacket.p_rec, UA)}"
        state_attributes[LXPPacket.pf] = f"{self.totaldata.get(LXPPacket.pf, UA)}"
        state_attributes[LXPPacket.v_eps_r] = f"{self.totaldata.get(LXPPacket.v_eps_r, UA)}"
        state_attributes[LXPPacket.v_eps_s] = f"{self.totaldata.get(LXPPacket.v_eps_s, UA)}"
        state_attributes[LXPPacket.v_eps_t] = f"{self.totaldata.get(LXPPacket.v_eps_t, UA)}"
        state_attributes[LXPPacket.f_eps] = f"{self.totaldata.get(LXPPacket.f_eps, UA)}"
        state_attributes[LXPPacket.p_to_eps] = f"{self.totaldata.get(LXPPacket.p_to_eps, UA)}"
        state_attributes[LXPPacket.p_to_grid] = f"{self.totaldata.get(LXPPacket.p_to_grid, UA)}"
        state_attributes[LXPPacket.p_to_user] = f"{self.totaldata.get(LXPPacket.p_to_user, UA)}"
        state_attributes[LXPPacket.p_load] = f"{self.totaldata.get(LXPPacket.p_load, UA)}"
        state_attributes[LXPPacket.e_pv_1_day] = f"{self.totaldata.get(LXPPacket.e_pv_1_day, UA)}"
        state_attributes[LXPPacket.e_pv_2_day] = f"{self.totaldata.get(LXPPacket.e_pv_2_day, UA)}"
        state_attributes[LXPPacket.e_pv_3_day] = f"{self.totaldata.get(LXPPacket.e_pv_3_day, UA)}"
        state_attributes[LXPPacket.e_pv_total] = f"{self.totaldata.get(LXPPacket.e_pv_total, UA)}"
        state_attributes[LXPPacket.e_inv_day] = f"{self.totaldata.get(LXPPacket.e_inv_day, UA)}"
        state_attributes[LXPPacket.e_rec_day] = f"{self.totaldata.get(LXPPacket.e_rec_day, UA)}"
        state_attributes[LXPPacket.e_chg_day] = f"{self.totaldata.get(LXPPacket.e_chg_day, UA)}"
        state_attributes[LXPPacket.e_dischg_day] = f"{self.totaldata.get(LXPPacket.e_dischg_day, UA)}"
        state_attributes[LXPPacket.e_eps_day] = f"{self.totaldata.get(LXPPacket.e_eps_day, UA)}"
        state_attributes[LXPPacket.e_to_grid_day] = f"{self.totaldata.get(LXPPacket.e_to_grid_day, UA)}"
        state_attributes[LXPPacket.e_to_user_day] = f"{self.totaldata.get(LXPPacket.e_to_user_day, UA)}"
        state_attributes[LXPPacket.v_bus_1] = f"{self.totaldata.get(LXPPacket.v_bus_1, UA)}"
        state_attributes[LXPPacket.v_bus_2] = f"{self.totaldata.get(LXPPacket.v_bus_2, UA)}"
        state_attributes[LXPPacket.e_pv_1_all] = f"{self.totaldata.get(LXPPacket.e_pv_1_all, UA)}"
        state_attributes[LXPPacket.e_pv_2_all] = f"{self.totaldata.get(LXPPacket.e_pv_2_all, UA)}"
        state_attributes[LXPPacket.e_pv_3_all] = f"{self.totaldata.get(LXPPacket.e_pv_3_all, UA)}"
        state_attributes[LXPPacket.e_pv_all] = f"{self.totaldata.get(LXPPacket.e_pv_all, UA)}"
        state_attributes[LXPPacket.e_inv_all] = f"{self.totaldata.get(LXPPacket.e_inv_all, UA)}"
        state_attributes[LXPPacket.e_rec_all] = f"{self.totaldata.get(LXPPacket.e_rec_all, UA)}"
        state_attributes[LXPPacket.e_chg_all] = f"{self.totaldata.get(LXPPacket.e_chg_all, UA)}"
        state_attributes[LXPPacket.e_dischg_all] = f"{self.totaldata.get(LXPPacket.e_dischg_all, UA)}"
        state_attributes[LXPPacket.e_eps_all] = f"{self.totaldata.get(LXPPacket.e_eps_all, UA)}"
        state_attributes[LXPPacket.e_to_grid_all] = f"{self.totaldata.get(LXPPacket.e_to_grid_all, UA)}"
        state_attributes[LXPPacket.e_to_user_all] = f"{self.totaldata.get(LXPPacket.e_to_user_all, UA)}"
        state_attributes[LXPPacket.t_inner] = f"{self.totaldata.get(LXPPacket.t_inner, UA)}"
        state_attributes[LXPPacket.t_rad_1] = f"{self.totaldata.get(LXPPacket.t_rad_1, UA)}"
        state_attributes[LXPPacket.t_rad_2] = f"{self.totaldata.get(LXPPacket.t_rad_2, UA)}"
        state_attributes[LXPPacket.t_bat] = f"{self.totaldata.get(LXPPacket.t_bat, UA)}"
        state_attributes[LXPPacket.uptime] = f"{self.totaldata.get(LXPPacket.uptime, UA)}"
        state_attributes[LXPPacket.max_chg_curr] = f"{self.totaldata.get(LXPPacket.max_chg_curr, UA)}"
        state_attributes[LXPPacket.max_dischg_curr] = f"{self.totaldata.get(LXPPacket.max_dischg_curr, UA)}"
        state_attributes[LXPPacket.charge_volt_ref] = f"{self.totaldata.get(LXPPacket.charge_volt_ref, UA)}"
        state_attributes[LXPPacket.dischg_cut_volt] = f"{self.totaldata.get(LXPPacket.dischg_cut_volt, UA)}"
        state_attributes[LXPPacket.bat_count] = f"{self.totaldata.get(LXPPacket.bat_count, UA)}"
        state_attributes[LXPPacket.bat_capacity] = f"{self.totaldata.get(LXPPacket.bat_capacity, UA)}"
        state_attributes[LXPPacket.max_cell_volt] = f"{self.totaldata.get(LXPPacket.max_cell_volt, UA)}"
        state_attributes[LXPPacket.min_cell_volt] = f"{self.totaldata.get(LXPPacket.min_cell_volt, UA)}"
        state_attributes[LXPPacket.max_cell_temp] = f"{self.totaldata.get(LXPPacket.max_cell_temp, UA)}"
        state_attributes[LXPPacket.min_cell_temp] = f"{self.totaldata.get(LXPPacket.min_cell_temp, UA)}"
        return state_attributes

    # fmt: on

    async def async_added_to_hass(self) -> None:
        await super().async_added_to_hass()
        _LOGGER.debug("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_DATA_BANK0_RECEIVED, self.push_update)

    def checkonline(self, *args, **kwargs):
        _LOGGER.debug("check online")
        if time.time() - self.lastupdated_time > 10:
            self._state = "OFFLINE"
        self.schedule_update_ha_state()

    def push_update(self, event):
        _LOGGER.debug(f"LUXPOWER State Sensor: register event received Name: {self._name}")
        self._data = event.data.get("data", {})
        self._state = "ONLINE"

        self.totaldata = self.luxpower_client.lxpPacket.data
        _LOGGER.debug(f"TotalData: {self.totaldata}")

        self.schedule_update_ha_state()
        return self._state

    def update(self):
        if not self.is_added_to_hass:
            return
        # _LOGGER.debug("{} updating state to {}".format(self._dp_id, self._stateval))
        return self._state

    @property
    def unique_id(self) -> Optional[str]:
        return "{}_{}_{}".format(DOMAIN, self.dongle, "states")

    @property
    def should_poll(self):
        """No polling needed for a demo sensor."""
        return False

    @property
    def device_class(self):
        """Return the device class of the sensor."""
        return self._device_class

    @property
    def name(self):
        """Return the name of the sensor."""
        return self._name

    @property
    def device_info(self):
        """Return device info."""
        return DeviceInfo(
            identifiers={(DOMAIN, self.dongle)},
            manufacturer="LuxPower",
            model="LUXPower Inverter",
            name=self.dongle,
            sw_version="1.1",
        )

    @property
    def state(self):
        """Return the state of the sensor."""
        return self._state

    @property
    def unit_of_measurement(self):
        """Return the unit this state is expressed in."""
        return self._unit_of_measurement
