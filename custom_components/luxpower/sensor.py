import asyncio
import time
from datetime import timedelta, datetime, date
from typing import Optional, Union, Any, List, Dict
import logging

from homeassistant.components.sensor import SensorEntity, SensorStateClass
from homeassistant.config_entries import ConfigEntry
from homeassistant.const import CONF_MODE, DEVICE_CLASS_POWER, POWER_WATT, ENERGY_KILO_WATT_HOUR, PERCENTAGE, \
    DEVICE_CLASS_BATTERY, DEVICE_CLASS_ENERGY, DEVICE_CLASS_VOLTAGE, ELECTRIC_POTENTIAL_VOLT, DEVICE_CLASS_CURRENT, \
    ELECTRIC_CURRENT_AMPERE, DEVICE_CLASS_TEMPERATURE, TEMP_CELSIUS
from homeassistant.core import HomeAssistant
from homeassistant.helpers.entity import Entity, DeviceInfo
from homeassistant.helpers.event import async_track_time_interval, async_track_point_in_time, track_time_interval
from homeassistant.helpers.typing import StateType

from .const import DOMAIN, ATTR_LUX_HOST, ATTR_LUX_PORT, ATTR_LUX_SERIAL_NUMBER, ATTR_LUX_DONGLE_SERIAL, \
    ATTR_LUX_USE_SERIAL
from .LXPPacket import LXPPacket
from .helpers import Event

_LOGGER = logging.getLogger(__name__)

async def refreshSensors(hass: HomeAssistant, dongle):
    await asyncio.sleep(10)
    _LOGGER.info("Refreshing sensors")
    status = await hass.services.async_call(DOMAIN, 'luxpower_refresh_registers', {'dongle': dongle}, blocking=True)
    _LOGGER.info(f"Refreshing sensors done with status : {status}")


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

    entityID_prefix = SERIAL if USE_SERIAL else ''

    _LOGGER.info(f"Lux sensor platform_config: {platform_config}")

    stateSensors = []
    event = Event(dongle=DONGLE)

    luxpower_client = hass.data[event.CLIENT_DAEMON]

    device_class = CONF_MODE
    unit = ""
    name = f"LUXPower {entityID_prefix}"
    stateSensors.append(LuxStateSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, name, device_class, unit, event))

    sensors = []

    # Attribute sensor
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Discharge (Live)", "entity": 'lux_battery_discharge', 'attribute': LXPPacket.p_discharge, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Charge (Live)", "entity": 'lux_battery_charge', 'attribute': LXPPacket.p_charge, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery %", "entity": 'lux_battery_percent', 'attribute': LXPPacket.soc, 'device_class': DEVICE_CLASS_BATTERY, 'unit_measure': PERCENTAGE})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Discharge (Daily)", "entity": 'lux_daily_battery_discharge', 'attribute': LXPPacket.e_dischg_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Discharge (Total)", "entity": 'lux_total_battery_discharge', 'attribute': LXPPacket.e_dischg_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Charge (Daily)", "entity": 'lux_daily_battery_charge', 'attribute': LXPPacket.e_chg_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Charge (Total)", "entity": 'lux_total_battery_charge', 'attribute': LXPPacket.e_chg_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}- Battery Voltage (Live)", "entity": 'lux_battery_voltage', 'attribute': LXPPacket.v_bat, 'device_class': DEVICE_CLASS_VOLTAGE, 'unit_measure': ELECTRIC_POTENTIAL_VOLT})
    sensors.append({"name": f"Lux {entityID_prefix}- BMS Limit Charge (Live)", "entity": 'lux_bms_limit_charge', 'attribute': LXPPacket.max_chg_curr, 'device_class': DEVICE_CLASS_CURRENT, 'unit_measure': ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": f"Lux {entityID_prefix}- BMS Limit Discharge (Live)", "entity": 'lux_bms_limit_discharge', 'attribute': LXPPacket.max_dischg_curr, 'device_class': DEVICE_CLASS_CURRENT, 'unit_measure': ELECTRIC_CURRENT_AMPERE})
    sensors.append({"name": f"Lux {entityID_prefix}- Power from Inverter (Live)", "entity": 'lux_power_from_inverter_live', 'attribute': LXPPacket.p_inv, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power to Inverter (Live)", "entity": 'lux_power_to_inverter_live', 'attribute': LXPPacket.p_rec, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power from grid to HOUSE (Live)", "entity": 'lux_power_to_home', 'attribute': LXPPacket.p_load, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power to EPS (Live)", "entity": 'lux_power_to_eps', 'attribute': LXPPacket.p_to_eps, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power from Grid (Live)", "entity": 'lux_power_from_grid_live', 'attribute': LXPPacket.p_to_user, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power from Grid (Daily)", "entity": 'lux_power_from_grid_daily', 'attribute': LXPPacket.e_to_user_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Power from Grid (Total)", "entity": 'lux_power_from_grid_total', 'attribute': LXPPacket.e_to_user_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}- Power To Grid (Live)", "entity": 'lux_power_to_grid_live', 'attribute': LXPPacket.p_to_grid, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power To Grid (Daily)", "entity": 'lux_power_to_grid_daily', 'attribute': LXPPacket.e_to_grid_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Power To Grid (Total)", "entity": 'lux_power_to_grid_total', 'attribute': LXPPacket.e_to_grid_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}- Grid Voltage (Live) ", "entity": 'lux_grid_voltage_live', 'attribute': LXPPacket.v_ac_r, 'device_class': DEVICE_CLASS_VOLTAGE, 'unit_measure': ELECTRIC_POTENTIAL_VOLT})
    sensors.append({"name": f"Lux {entityID_prefix}- Power from Inverter to Home (Daily)", "entity": 'lux_power_from_inverter_daily', 'attribute': LXPPacket.e_inv_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Power to Inverter (Daily)", "entity": 'lux_power_to_inverter_daily', 'attribute': LXPPacket.e_rec_day, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Solar Output (Live)", "entity": 'lux_current_solar_output', 'attribute': LXPPacket.p_pv_total, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Solar Output Array 1 (Live)", "entity": 'lux_current_solar_output_1', 'attribute': LXPPacket.p_pv_1, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Solar Output Array 2 (Live)", "entity": 'lux_current_solar_output_2', 'attribute': LXPPacket.p_pv_2, 'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT})
    sensors.append({"name": f"Lux {entityID_prefix}- Solar Output (Daily)", "entity": 'lux_daily_solar', 'attribute': LXPPacket.e_pv_total, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR})
    sensors.append({"name": f"Lux {entityID_prefix}- Solar Output (Total)", "entity": 'lux_total_solar', 'attribute': LXPPacket.e_pv_all, 'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR, 'state_class': SensorStateClass.TOTAL_INCREASING})
    sensors.append({"name": f"Lux {entityID_prefix}- Internal Temperature (Live)", "entity": 'lux_internal_temp', 'attribute': LXPPacket.t_inner, 'device_class': DEVICE_CLASS_TEMPERATURE, 'unit_measure': TEMP_CELSIUS})
    sensors.append({"name": f"Lux {entityID_prefix}- Radiator 1 Temperature (Live)", "entity": 'lux_radiator1_temp', 'attribute': LXPPacket.t_rad_1, 'device_class': DEVICE_CLASS_TEMPERATURE, 'unit_measure': TEMP_CELSIUS})
    sensors.append({"name": f"Lux {entityID_prefix}- Radiator 2 temperature (Live)", "entity": 'lux_radiator2_temp', 'attribute': LXPPacket.t_rad_2, 'device_class': DEVICE_CLASS_TEMPERATURE, 'unit_measure': TEMP_CELSIUS})
    
    sensors.append({"name": f"Lux {entityID_prefix}- Status", "entity": 'lux_status', 'attribute': LXPPacket.status, 'device_class': '', 'unit_measure': ''})
    for sensor_data in sensors:
        stateSensors.append(LuxpowerSensorEntity(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # Setup Data recieved timestamp sensor
    sensor_data = {"name": f"Lux {entityID_prefix}- Data received time", "entity": 'lux_data_last_received_time',
                   'attribute': LXPPacket.status,
                   'device_class': '', 'unit_measure': ''}
    stateSensors.append(LuxPowerDataReceivedTimestampSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # Setup State Text sensor
    sensor_data = {"name": f"Lux {entityID_prefix}- Status (Text)", "entity": 'lux_status_text',
                   'attribute': LXPPacket.status,
                   'device_class': '', 'unit_measure': ''}
    stateSensors.append(LuxPowerStatusTextSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # Multiple attribute models
    # 1. Battery Flow Live
    sensor_data = {"name": f"Lux {entityID_prefix}- Battery Flow (Live)", "entity": 'lux_battery_flow', 'attribute': LXPPacket.p_discharge,
                   'attribute1': LXPPacket.p_discharge, 'attribute2': LXPPacket.p_charge,  # Attribute dependencies
                   'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT}
    stateSensors.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # 2. Grid Flow Live
    sensor_data = {"name": f"Lux {entityID_prefix}- Grid Flow (Live)", "entity": 'lux_grid_flow', 'attribute': LXPPacket.p_to_user,
                   'attribute1': LXPPacket.p_to_user, 'attribute2': LXPPacket.p_to_grid,   # Attribute dependencies
                   'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT}
    stateSensors.append(LuxPowerFlowSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))


    # 3. Home Consumption Live
    sensor_data = {"name": f"Lux {entityID_prefix}- Home Consumption (Live)", "entity": 'lux_home_consumption_live', 'attribute': LXPPacket.p_to_user,
                   'attribute1': LXPPacket.p_to_user, 'attribute2': LXPPacket.p_rec, 'attribute3': LXPPacket.p_inv, 'attribute4': LXPPacket.p_to_grid, # Attribute dependencies
                   # att1. Power from grid to consumer, att2. Power from consumer to invert, att3. power from inv to consumer, att4. power from consumer to grid.
                   'device_class': DEVICE_CLASS_POWER, 'unit_measure': POWER_WATT}
    stateSensors.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    # 4. Home Consumption Daily
    sensor_data = {"name": f"Lux {entityID_prefix}- Home Consumption (Daily)", "entity": 'lux_home_consumption', 'attribute': LXPPacket.e_to_user_day,
                   'attribute1': LXPPacket.e_to_user_day, 'attribute2': LXPPacket.e_rec_day, 'attribute3': LXPPacket.e_inv_day, 'attribute4': LXPPacket.e_to_grid_day, # Attribute dependencies
                   'device_class': DEVICE_CLASS_ENERGY, 'unit_measure': ENERGY_KILO_WATT_HOUR}
    stateSensors.append(LuxPowerHomeConsumptionSensor(hass, HOST, PORT, DONGLE, SERIAL, sensor_data, event))

    async_add_devices(stateSensors, True)

    # delay service call for some time to give the sensors and swiches time to initialise
    hass.async_create_task(refreshSensors(hass, dongle=DONGLE))

    _LOGGER.debug("LuxPower sensor async_setup_platform sensor done %s", DONGLE)
    _LOGGER.info("LuxPower sensor async_setup_platform sensor done")

class LuxpowerSensorEntity(SensorEntity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""
    def __init__(
        self, hass, host, port, dongle, serial, sensor_data, event: Event
    ):
        """Initialize the sensor."""
        self.hass = hass
        self._host = host
        self._port = port
        self._name = sensor_data['name']
        self._state = "Unavailable"
        self._stateval = None
        self.serial = serial
        self.dongle = dongle
        self._device_class = sensor_data['device_class']
        self._unit_of_measurement = sensor_data['unit_measure']
        self.is_added_to_hass = False
        self._data = {}
        self._unique_id = "{}_{}_{}".format(DOMAIN, dongle, sensor_data['entity'])
        self._device_attribute = sensor_data['attribute']
        self._state_class = sensor_data.get('state_class', None)
        self.lastupdated_time = 0
        self.event = event

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_DATA_RECEIVED, self.push_update)
        return result

    def push_update(self, event):
        _LOGGER.info("Sensor: register event received %s", self._name)
        self._data = event.data.get('data', {})
        value = self._data.get(self._device_attribute)
        value = round(value, 1) if isinstance(value, (int, float)) else "unavailable"
        self._state = "{}".format(value)

        self.schedule_update_ha_state()
        return self._state

    def update(self):
        if not self.is_added_to_hass:
            return
        # _LOGGER.info("{} updating state to {}".format(self._dp_id, self._stateval))
        return self._state

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
    '''
    Template equation state = -1*attribute1 if attribute1 > 0 else attribute2
    '''
    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self._device_attribute1 = sensor_data['attribute1']
        self._device_attribute2 = sensor_data['attribute2']

    def push_update(self, event):
        _LOGGER.info("LuxPowerFlowSensor: register event received")
        self._data = event.data.get('data', {})

        negative_value = float(self._data.get(self._device_attribute1, 0.0))
        positive_value = float(self._data.get(self._device_attribute2, 0.0))
        if negative_value > 0:
            flow_value = -1 * negative_value
        else:
            flow_value = positive_value
        self._state = "{}".format(round(flow_value,1))

        self.schedule_update_ha_state()
        return self._state


class LuxPowerHomeConsumptionSensor(LuxpowerSensorEntity): #Used for both live and daily consumption calcuation.
    '''
    Template equation state = attribute1 - attribute2 + attribute3 - attribute4
    '''
    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self._device_attribute1 = sensor_data['attribute1'] # Power from grid to consumer unit
        self._device_attribute2 = sensor_data['attribute2'] # Power from consumer unit to inverter
        self._device_attribute3 = sensor_data['attribute3'] # Power from inverter to consumer unit
        self._device_attribute4 = sensor_data['attribute4'] # Power from consumer unit to grid
                
    def push_update(self, event):
        _LOGGER.info("LuxPowerHomeConsumptionSensor: register event received")
        self._data = event.data.get('data', {})

        grid = float(self._data.get(self._device_attribute1, 0.0))
        to_inverter = float(self._data.get(self._device_attribute2, 0.0))
        from_inverter = float(self._data.get(self._device_attribute3, 0.0))
        to_grid = float(self._data.get(self._device_attribute4, 0.0))
        consumption_value = grid - to_inverter + from_inverter - to_grid
        self._state = "{}".format(round(consumption_value, 1))

        self.schedule_update_ha_state()
        return self._state

class LuxPowerStatusTextSensor(LuxpowerSensorEntity):
    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)

    def push_update(self, event):
        _LOGGER.info("LuxPowerStatusSensor: register event received")
        self._data = event.data.get('data', {})
        state_text = ''
        status = int(self._data.get(self._device_attribute, 0.0))
        if status == 0:
            state_text = 'Standby'
        elif status == 2:
            state_text = 'Inverting'
        elif status == 4:
            state_text = 'Silent'
        elif status == 5:
            state_text = 'Float'
        elif status == 64:
            state_text = 'No AC Power'
        elif status == 7:
            state_text = 'Charger Off'
        elif status == 8:
            state_text = 'Support'
        elif status == 9:
            state_text = 'Selling'
        elif status == 10:
            state_text = 'Pass Through'
        elif status == 12:
            state_text = 'Solar + Battery Charging'
        elif status == 16:
            state_text = 'Battery Discharging'
        elif status == 20:
            state_text = 'Solar + Battery Discharging'
        elif status == 32:
            state_text = 'Battery Charging'
        elif status == 11:
            state_text = 'Offsetting'
        else:
            state_text = 'Unknown'
        self._state = "{}".format(state_text)

        self.schedule_update_ha_state()
        return self._state


class LuxPowerDataReceivedTimestampSensor(LuxpowerSensorEntity):
    def __init__(self, hass, host, port, dongle, serial, sensor_data, event: Event):
        super().__init__(hass, host, port, dongle, serial, sensor_data, event)
        self.datetime_last_received = None

    def push_update(self, event):
        _LOGGER.info("LuxPowerDataReceivedSensor: register event received")
        self._data = event.data.get('data', {})
        self.datetime_last_received = datetime.now()
        self._state = "{}".format(datetime.now().strftime("%A %B %-d, %I:%M %p"))

        self.schedule_update_ha_state()
        return self._state

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes['dongle'] = self.dongle
        if self.datetime_last_received is not None:
            state_attributes['timestamp'] = self.datetime_last_received.timestamp()
        else:
            state_attributes['timestamp'] = 0
        return state_attributes


class LuxStateSensorEntity(Entity):
    """Representation of a sensor of Type HAVC, Pressure, Power, Volume."""

    def __init__(
        self, hass, host, port, dongle, serial, name, device_class, unit_measure, event:Event
    ):
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
        self._data = {}
        self.lastupdated_time = 0
        self.event = event

    @property
    def extra_state_attributes(self) -> Optional[Dict[str, Any]]:
        state_attributes = self.state_attributes or {}
        state_attributes[LXPPacket.status] = "{}".format(self._data.get(LXPPacket.status, "unavailable"), "")
        state_attributes[LXPPacket.v_pv_1] = "{}".format(self._data.get(LXPPacket.v_pv_1, "unavailable"), "")
        state_attributes[LXPPacket.v_pv_2] = "{}".format( self._data.get(LXPPacket.v_pv_2, "unavailable"), "")
        state_attributes[LXPPacket.v_pv_3] = "{}".format( self._data.get(LXPPacket.v_pv_3, "unavailable"), "")
        state_attributes[LXPPacket.v_bat] = "{}".format( self._data.get(LXPPacket.v_bat, "unavailable"), "")
        state_attributes[LXPPacket.soc] = "{}".format( self._data.get(LXPPacket.soc, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_1] = "{}".format( self._data.get(LXPPacket.p_pv_1, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_2] = "{}".format( self._data.get(LXPPacket.p_pv_2, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_3] = "{}".format( self._data.get(LXPPacket.p_pv_3, "unavailable"), "")
        state_attributes[LXPPacket.p_pv_total] = "{}".format( self._data.get(LXPPacket.p_pv_total, "unavailable"), "")
        state_attributes[LXPPacket.p_charge] = "{}".format( self._data.get(LXPPacket.p_charge, "unavailable"), "")
        state_attributes[LXPPacket.p_discharge] = "{}".format( self._data.get(LXPPacket.p_discharge, "unavailable"), "")
        state_attributes[LXPPacket.v_ac_r] = "{}".format( self._data.get(LXPPacket.v_ac_r, "unavailable"), "")
        state_attributes[LXPPacket.v_ac_s] = "{}".format(self._data.get(LXPPacket.v_ac_s, "unavailable"), "")
        state_attributes[LXPPacket.v_ac_t] = "{}".format( self._data.get(LXPPacket.v_ac_t, "unavailable"), "")
        state_attributes[LXPPacket.f_ac] = "{}".format( self._data.get(LXPPacket.f_ac, "unavailable"), "")
        state_attributes[LXPPacket.p_inv] = "{}".format( self._data.get(LXPPacket.p_inv, "unavailable"), "")
        state_attributes[LXPPacket.p_rec] = "{}".format( self._data.get(LXPPacket.p_rec, "unavailable"), "")
        state_attributes[LXPPacket.pf] = "{}".format( self._data.get(LXPPacket.pf, "unavailable"))
        state_attributes[LXPPacket.v_eps_r] = "{}".format( self._data.get(LXPPacket.v_eps_r, "unavailable"), "")
        state_attributes[LXPPacket.v_eps_s] = "{}".format( self._data.get(LXPPacket.v_eps_s, "unavailable"), "")
        state_attributes[LXPPacket.v_eps_t] = "{}".format( self._data.get(LXPPacket.v_eps_t, "unavailable"), "")
        state_attributes[LXPPacket.f_eps] = "{}".format( self._data.get(LXPPacket.f_eps, "unavailable"), "")
        state_attributes[LXPPacket.p_to_eps] = "{}".format( self._data.get(LXPPacket.p_to_eps, "unavailable"), "")
        state_attributes[LXPPacket.p_to_grid] = "{}".format( self._data.get(LXPPacket.p_to_grid, "unavailable"), "")
        state_attributes[LXPPacket.p_to_user] = "{}".format( self._data.get(LXPPacket.p_to_user, "unavailable"), "")
        state_attributes[LXPPacket.p_load] = "{}".format( self._data.get(LXPPacket.p_load, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_1_day] = "{}".format( self._data.get(LXPPacket.e_pv_1_day, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_2_day] = "{}".format( self._data.get(LXPPacket.e_pv_2_day, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_3_day] = "{}".format( self._data.get(LXPPacket.e_pv_3_day, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_total] = "{}".format( self._data.get(LXPPacket.e_pv_total, "unavailable"), "")
        state_attributes[LXPPacket.e_inv_day] = "{}".format( self._data.get(LXPPacket.e_inv_day, "unavailable"), "")
        state_attributes[LXPPacket.e_rec_day] = "{}".format( self._data.get(LXPPacket.e_rec_day, "unavailable"), "")
        state_attributes[LXPPacket.e_chg_day] =  "{}".format(self._data.get(LXPPacket.e_chg_day, "unavailable"), "")
        state_attributes[LXPPacket.e_dischg_day] = "{}".format( self._data.get(LXPPacket.e_dischg_day, "unavailable"), "")
        state_attributes[LXPPacket.e_eps_day] = "{}".format( self._data.get(LXPPacket.e_eps_day, "unavailable"), "")
        state_attributes[LXPPacket.e_to_grid_day] = "{}".format( self._data.get(LXPPacket.e_to_grid_day, "unavailable"), "")
        state_attributes[LXPPacket.e_to_user_day] = "{}".format( self._data.get(LXPPacket.e_to_user_day, "unavailable"), "")
        state_attributes[LXPPacket.v_bus_1] = "{}".format( self._data.get(LXPPacket.v_bus_1, "unavailable"), "")
        state_attributes[LXPPacket.v_bus_2] = "{}".format( self._data.get(LXPPacket.v_bus_2, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_1_all] = "{}".format( self._data.get(LXPPacket.e_pv_1_all, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_2_all] = "{}".format( self._data.get(LXPPacket.e_pv_2_all, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_3_all] = "{}".format( self._data.get(LXPPacket.e_pv_3_all, "unavailable"), "")
        state_attributes[LXPPacket.e_pv_all] = "{}".format( self._data.get(LXPPacket.e_pv_all, "unavailable"), "")
        state_attributes[LXPPacket.e_inv_all] = "{}".format( self._data.get(LXPPacket.e_inv_all, "unavailable"), "")
        state_attributes[LXPPacket.e_rec_all] = "{}".format( self._data.get(LXPPacket.e_rec_all, "unavailable"), "")
        state_attributes[LXPPacket.e_chg_all] = "{}".format( self._data.get(LXPPacket.e_chg_all, "unavailable"), "")
        state_attributes[LXPPacket.e_dischg_all] = "{}".format( self._data.get(LXPPacket.e_dischg_all, "unavailable"), "")
        state_attributes[LXPPacket.e_eps_all] = "{}".format( self._data.get(LXPPacket.e_eps_all, "unavailable"), "")
        state_attributes[LXPPacket.e_to_grid_all] = "{}".format( self._data.get(LXPPacket.e_to_grid_all, "unavailable"), "")
        state_attributes[LXPPacket.e_to_user_all] = "{}".format( self._data.get(LXPPacket.e_to_user_all, "unavailable"), "")
        state_attributes[LXPPacket.t_inner] = "{}".format( self._data.get(LXPPacket.t_inner, "unavailable"), "")
        state_attributes[LXPPacket.t_rad_1] = "{}".format( self._data.get(LXPPacket.t_rad_1, "unavailable"), "")
        state_attributes[LXPPacket.t_rad_2] = "{}".format( self._data.get(LXPPacket.t_rad_2, "unavailable"), "")
        state_attributes[LXPPacket.t_bat] = "{}".format( self._data.get(LXPPacket.t_bat, "unavailable"), "")
        state_attributes[LXPPacket.uptime] = "{}".format( self._data.get(LXPPacket.uptime, "unavailable"), "")
        state_attributes[LXPPacket.max_chg_curr] = "{}".format( self._data.get(LXPPacket.max_chg_curr, "unavailable"), "")
        state_attributes[LXPPacket.max_dischg_curr] = "{}".format( self._data.get(LXPPacket.max_dischg_curr, "unavailable"), "")
        state_attributes[LXPPacket.charge_volt_ref] = "{}".format( self._data.get(LXPPacket.charge_volt_ref, "unavailable"), "")
        state_attributes[LXPPacket.dischg_cut_volt] = "{}".format( self._data.get(LXPPacket.dischg_cut_volt, "unavailable"), "")
        state_attributes[LXPPacket.bat_count] = "{}".format( self._data.get(LXPPacket.bat_count, "unavailable"), "")
        return state_attributes

    async def async_added_to_hass(self) -> None:
        result = await super().async_added_to_hass()
        _LOGGER.info("async_added_to_hasss %s", self._name)
        self.is_added_to_hass = True
        if self.hass is not None:
            self.hass.bus.async_listen(self.event.EVENT_DATA_RECEIVED, self.push_update)
        return result

    def checkonline(self, *args, **kwargs):
        _LOGGER.info("check online")
        if time.time() - self.lastupdated_time > 10:
            self._state = "OFFLINE"
        self.schedule_update_ha_state()

    def push_update(self, event):
        _LOGGER.info("register event received")
        self._data = event.data.get('data', {})
        self._state = "ONLINE"

        self.schedule_update_ha_state()
        return self._state

    def update(self):
        if not self.is_added_to_hass:
            return
        # _LOGGER.info("{} updating state to {}".format(self._dp_id, self._stateval))
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
