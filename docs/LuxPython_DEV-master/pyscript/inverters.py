# from date import strftime

# from datetime import datetime
import datetime

# import time
# import pytz

dodebug = False

time_triggers = {}


def time_trigger_factory(input_datetime, func_handle, func_name, secs_offset, *args, **kwargs):
    time_val = str((datetime.datetime.strptime(state.get(input_datetime), '%H:%M:%S') + datetime.timedelta(seconds=secs_offset)).time())  # fmt: skip

    log.info(f"Set trigger for {func_name} at {time_val} [HH:MM:SS] offset {secs_offset}")

    @time_trigger(f"once({time_val})")
    def func_trig():
        # nonlocal args, kwargs
        func_handle(*args, **kwargs)

    time_triggers[func_name] = func_trig


@time_trigger
@state_trigger("input_datetime.system_off_peak_start_time")
def time_triggers_setup_for_auto_set_ac_battery_charge_level():
    log.info("Calling time trigger factory")
    time_trigger_factory("input_datetime.system_off_peak_start_time", auto_set_ac_battery_charge_level, "auto_set_ac_battery_charge_level", 0)  # fmt: skip


@time_trigger
@state_trigger("input_datetime.system_peak_rate_finish_time")
def time_triggers_setup_for_auto_balance():
    log.info("Calling time trigger factory")
    time_trigger_factory("input_datetime.system_peak_rate_finish_time", auto_balance, "auto_balance", 120)


# @time_trigger("period(now, 2min)")
# @time_trigger("once(02:00:00)")
@task_unique("auto_set_ac_battery_charge_level", kill_me=True)
@state_trigger(
    "sensor.lux_battery != 'unavailable' \
    and int(float(sensor.target_ac_battery_charge_level) + int(float(sensor.lux_battery)) + 1 > 0 \
    and binary_sensor.allow_processing_of_solar_forecast_changes == 'on') or dodebug"
)
def auto_set_ac_battery_charge_level():
    log.debug(f"auto_set_ac_battery_charge_level has fired")

    import datetime

    charge_level = float(sensor.target_ac_battery_charge_level)

    set_percentage_if_changed_by_x("number.lux_1212016010_ac_battery_charge_level", int(float(charge_level)), 1)
    set_percentage_if_changed_by_x("number.lux_1212016024_ac_battery_charge_level", int(float(charge_level)), 1)

    # Todays end_of_charge Calc
    current_time = datetime.datetime.now().timestamp()
    end_of_charge = datetime.datetime.now().replace(hour=0, minute=00, second=0, microsecond=0).timestamp() + input_datetime.system_off_peak_finish_time.timestamp  # fmt: skip
    # Do we Cross Midnight
    if input_datetime.system_off_peak_start_time.timestamp > input_datetime.system_off_peak_finish_time.timestamp:
        #  Yes - Over Midnight - Is Current time before midnight?
        start_of_charge = datetime.datetime.now().replace(hour=0, minute=00, second=0, microsecond=0).timestamp() + input_datetime.system_off_peak_start_time.timestamp  # fmt: skip
        nearly_midnight = datetime.datetime.now().replace(hour=23, minute=59, second=59, microsecond=9999).timestamp()
        if current_time > start_of_charge and current_time <= nearly_midnight:
            end_of_charge = end_of_charge + 24 * 60 * 60

    secs_until_eoc = end_of_charge - current_time

    mins_until_eoc = secs_until_eoc / 60

    charge_perc_reqd = charge_level - float(sensor.lux_battery)

    if dodebug:
        mins_until_eoc = 243.53
        # charge_perc_reqd = 38

    log.debug(f"Time until End Of Charge mins: {mins_until_eoc}")

    #  Example seen 57% in 3 hours
    #  19% takes 1 hour at max charge rate
    #  1% take 60/19% minutes at max charge rate
    #  60/18% to be safe

    if charge_perc_reqd > 0:
        # Calculate Power Rate When Charging Required
        mins_at_full = charge_perc_reqd * 60 / 18  # mins_in_hour / percent_charge_gain

        # Check If SOC is > 87% - If so charge at full power to top balance
        if float(sensor.lux_battery) > 87:
            rate_perc = 100
        else:
            rate_perc = int(mins_at_full / mins_until_eoc * 100)

            if rate_perc > 100:
                rate_perc = 100

            if rate_perc < 20:
                rate_perc = 20

        log.info(f"Mins To EOC: {mins_until_eoc:6.2f} - Chrg SOC Reqd: {charge_perc_reqd:4.1f}% - Mins At 100%: {mins_at_full:6.2f} - Rate Reqd: {rate_perc}%")  # fmt: skip

        set_percentage_if_changed_by_x("number.lux_1212016010_ac_charge_power_rate", int(float(rate_perc)), 2)
        set_percentage_if_changed_by_x("number.lux_1212016024_ac_charge_power_rate", int(float(rate_perc)), 2)

        set_ac_charge_enable_inverters_switch("on")

    else:
        # Reset To Full Power Rate When At Level Or Requiring Discharge
        set_percentage_if_changed("number.lux_1212016010_ac_charge_power_rate", 100.0)
        set_percentage_if_changed("number.lux_1212016024_ac_charge_power_rate", 100.0)

        if charge_perc_reqd < 0:
            #### switch off ac charging ####
            set_ac_charge_enable_inverters_switch("off")
            log.info(f"Discharge Required: {charge_perc_reqd} ")
        else:
            log.info(f"No Charge Required: {charge_perc_reqd} ")


def set_ac_charge_enable_inverters_switch(action_to_set):
    log.debug(f"Check to turn_{action_to_set} AC enable switch on both inverters")
    if state.get("switch.lux_1212016010_ac_charge_enable") != action_to_set or state.get("switch.lux_1212016024_ac_charge_enable") != action_to_set:  # fmt: skip
        log.info(f"About to turn_{action_to_set} AC enable switch on both inverters")
        set_switch("switch.lux_1212016010_ac_charge_enable", action_to_set)
        set_switch("switch.lux_1212016024_ac_charge_enable", action_to_set)


def set_switch(entity_to_set, action_to_set):
    log.debug(f"Check to turn_{action_to_set} switch {entity_to_set}")
    if state.get(entity_to_set) != action_to_set:
        log.info(f"About to turn_{action_to_set} switch {entity_to_set}")
        service.call("switch", f"turn_{action_to_set}", entity_id=entity_to_set, blocking=True)


MInvSN = "1212016010"  # Master Inverter Serial Number
SInvSN = "1212016024"  # Slave  Inverter Serial Number


@task_unique("auto_charge_batteries_when_ohme_is_charging", kill_me=False)
@state_trigger(
    "1 == 1",
    watch={
        "binary_sensor.ohme_pro_is_charging",
        "input_boolean.system_heat_underfloor_during_off_peak",
        "input_boolean.system_allow_hot_tub_heating_during_on_peak",
        "binary_sensor.ohme_charger_online",
        "binary_sensor.octopus_energy_electricity_21p9000917_1100025345638_off_peak",
    },
)
def auto_charge_batteries_when_ohme_is_charging():
    log.debug(f"auto_charge_batteries_when_ohme_is_charging has fired")

    if (
        binary_sensor.ohme_pro_is_charging == "on"
        and binary_sensor.ohme_charger_online == "on"
        and binary_sensor.octopus_energy_electricity_21p9000917_1100025345638_off_peak == "off"
    ):
        # We regard this as CHEAP Rate
        log.info("Ohme Charge Has STARTED Slot Charging")

        end_time = "23:30"
        start_time = "05:30"

        service.call("time", "set_value", entity_id="time.lux_1212016010_ac_charge_end3", time=end_time, blocking=True)  # fmt: skip
        service.call("time", "set_value", entity_id="time.lux_1212016010_ac_charge_start3", time=start_time, blocking=True)  # fmt: skip

        service.call("time", "set_value", entity_id="time.lux_1212016024_ac_charge_end3", time=end_time, blocking=True)  # fmt: skip
        service.call("time", "set_value", entity_id="time.lux_1212016024_ac_charge_start3", time=start_time, blocking=True)  # fmt: skip

        # Make sure AC Charging Is Enabled
        set_ac_charge_enable_inverters_switch("on")

        charge_level = float(sensor.target_ac_battery_charge_level)

        # Make sure Target Level Is Set
        set_percentage_if_changed_by_x("number.lux_1212016010_ac_battery_charge_level", int(float(charge_level)), 1)
        set_percentage_if_changed_by_x("number.lux_1212016024_ac_battery_charge_level", int(float(charge_level)), 1)

        rate_perc = 100

        # Make sure Power Rate for AC Charging Is 100%
        set_percentage_if_changed_by_x("number.lux_1212016010_ac_charge_power_rate", int(float(rate_perc)), 1)
        set_percentage_if_changed_by_x("number.lux_1212016024_ac_charge_power_rate", int(float(rate_perc)), 1)

        # automation.1_1_hot_tub_heat_to_max_at_off_peak_start

        service.call("climate", "set_temperature", entity_id="climate.walcote_hot_tub_heater", temperature=float(input_number.system_hot_tub_max_temperature), blocking=True)  # fmt: skip
        service.call("climate", "set_preset_mode", entity_id="climate.walcote_hot_tub_heater", preset_mode="Standard", blocking=True)  # fmt: skip

        task.sleep(5)

        service.call("climate", "set_temperature", entity_id="climate.walcote_hot_tub_heater", temperature=float(input_number.system_hot_tub_max_temperature), blocking=True)  # fmt: skip
        service.call("climate", "set_preset_mode", entity_id="climate.walcote_hot_tub_heater", preset_mode="Standard", blocking=True)  # fmt: skip

    else:
        # We regard this as EXPENSIVE rate - Switch off AC Charging

        end_time = "00:00"
        start_time = "00:00"

        service.call("time", "set_value", entity_id="time.lux_1212016010_ac_charge_end3", time=end_time, blocking=True)
        service.call("time", "set_value", entity_id="time.lux_1212016010_ac_charge_start3", time=start_time, blocking=True)  # fmt: skip

        service.call("time", "set_value", entity_id="time.lux_1212016024_ac_charge_end3", time=end_time, blocking=True)
        service.call("time", "set_value", entity_id="time.lux_1212016024_ac_charge_start3", time=start_time, blocking=True)  # fmt: skip

        # Are we in Peak Period and not within 5 minutes of Off Peak and Hot Tub Heating not forced on? - If so Switch Off Hot tub
        if (
            binary_sensor.octopus_energy_electricity_21p9000917_1100025345638_off_peak == "off"
            and datetime.datetime.now().replace(hour=0, minute=00, second=0, microsecond=0).timestamp()
            + input_datetime.system_off_peak_start_time.timestamp
            - datetime.datetime.now().timestamp()
            > 300
            and input_boolean.system_allow_hot_tub_heating_during_on_peak == "off"
        ):
            log.info("Ohme Charge Has STOPPED Slot Charging")

            service.call("climate", "set_temperature", entity_id="climate.walcote_hot_tub_heater", temperature=float(input_number.system_hot_tub_min_temperature), blocking=True)  # fmt: skip
            service.call("climate", "set_preset_mode", entity_id="climate.walcote_hot_tub_heater", preset_mode="Away From Home", blocking=True)  # fmt: skip

            task.sleep(5)

            service.call("climate", "set_temperature", entity_id="climate.walcote_hot_tub_heater", temperature=float(input_number.system_hot_tub_min_temperature), blocking=True)  # fmt: skip
            service.call("climate", "set_preset_mode", entity_id="climate.walcote_hot_tub_heater", preset_mode="Away From Home", blocking=True)  # fmt: skip


# @time_trigger("period(now, 2min)")
@task_unique("auto_balance", kill_me=True)
@state_trigger(
    f"sensor.lux_{SInvSN}_battery != 'unavailable' \
      and sensor.lux_{MInvSN}_battery != 'unavailable' \
      and number.lux_{MInvSN}_system_charge_power_rate != 'unavailable' \
      and number.lux_{MInvSN}_system_discharge_power_rate != 'unavailable' \
      and number.lux_{SInvSN}_system_charge_power_rate != 'unavailable' \
      and number.lux_{SInvSN}_system_discharge_power_rate != 'unavailable' \
      and abs(int(sensor.lux_{SInvSN}_battery)-int(sensor.lux_{MInvSN}_battery)) > 1"
)

#     and binary_sensor.grid_power_is_at_highest_on_peak_rate != 'on'")


def auto_balance():
    log.info(f"auto_balance has fired")
    # log.log(info, f"auto_balance has fired")

    if state.get(f"sensor.lux_{MInvSN}_battery").lower() in ["unknown", "unavailable"] or state.get(
        f"sensor.lux_{SInvSN}_battery"
    ).lower() in ["unknown", "unavailable"]:
        log.info(f"auto_balance has SOC unknown")
        soc1 = 100
        soc2 = 100
    else:
        soc1 = int(state.get(f"sensor.lux_{MInvSN}_battery"))
        soc2 = int(state.get(f"sensor.lux_{SInvSN}_battery"))

    if state.get(f"sensor.lux_{MInvSN}_battery_charge_live").lower() in ["unknown", "unavailable"] or state.get(
        f"sensor.lux_{SInvSN}_battery_charge_live"
    ).lower() in ["unknown", "unavailable"]:
        log.info(f"auto_balance has charge unknown")
        chg1 = 3600
        chg2 = 3600
    else:
        chg1 = int(state.get(f"sensor.lux_{MInvSN}_battery_charge_live"))
        chg2 = int(state.get(f"sensor.lux_{SInvSN}_battery_charge_live"))

    if state.get(f"sensor.lux_{MInvSN}_battery_discharge_live").lower() in ["unknown", "unavailable"] or state.get(
        f"sensor.lux_{SInvSN}_battery_discharge_live"
    ).lower() in ["unknown", "unavailable"]:
        log.info(f"auto_balance has discharge unknown")
        dis1 = 3600
        dis2 = 3600
    else:
        dis1 = int(state.get(f"sensor.lux_{MInvSN}_battery_discharge_live"))
        dis2 = int(state.get(f"sensor.lux_{SInvSN}_battery_discharge_live"))

    # chksoc = input_boolean.system_allow_autobalance_to_check_soc_range == "on"

    if (
        (10 < soc1 < 90 or input_boolean.system_allow_autobalance_to_check_soc_range == "off")
        and (10 < soc2 < 90 or input_boolean.system_allow_autobalance_to_check_soc_range == "off")
        and chg1 < 2500
        and chg2 < 2500
        and dis1 < 2500
        and dis2 < 2500
        and (chg1 + chg2) < 2500
        and (dis1 + dis2) < 2500
    ):
        log.debug(f"auto_balance is taking balancing action")

        if soc2 > soc1:
            log.info(f"auto_balance slave has more charge than master")
            set_percentage_if_changed(f"number.lux_{MInvSN}_system_charge_power_rate", 100.0)
            set_percentage_if_changed(f"number.lux_{MInvSN}_system_discharge_power_rate", 0.0)
            set_percentage_if_changed(f"number.lux_{SInvSN}_system_charge_power_rate", 0.0)
            set_percentage_if_changed(f"number.lux_{SInvSN}_system_discharge_power_rate", 100.0)

        else:
            log.info(f"auto_balance master has more charge than slave")
            set_percentage_if_changed(f"number.lux_{MInvSN}_system_charge_power_rate", 0.0)
            set_percentage_if_changed(f"number.lux_{MInvSN}_system_discharge_power_rate", 100.0)
            set_percentage_if_changed(f"number.lux_{SInvSN}_system_charge_power_rate", 100.0)
            set_percentage_if_changed(f"number.lux_{SInvSN}_system_discharge_power_rate", 0.0)

    else:
        if (
            int(
                float(state.get(f"number.lux_{MInvSN}_system_charge_power_rate"))
                + float(state.get(f"number.lux_{MInvSN}_system_discharge_power_rate"))
                + float(state.get(f"number.lux_{SInvSN}_system_charge_power_rate"))
                + float(state.get(f"number.lux_{SInvSN}_system_discharge_power_rate"))
            )
            != 400
        ):
            log.info(f"auto_balance will reset all rates")
            set_percentage_if_changed(f"number.lux_{MInvSN}_system_charge_power_rate", 100.0)
            set_percentage_if_changed(f"number.lux_{MInvSN}_system_discharge_power_rate", 100.0)
            set_percentage_if_changed(f"number.lux_{SInvSN}_system_charge_power_rate", 100.0)
            set_percentage_if_changed(f"number.lux_{SInvSN}_system_discharge_power_rate", 100.0)


@task_unique("set_dis_and_charge_rates_to_100", kill_me=True)
@state_trigger(
    f"sensor.lux_{SInvSN}_battery != 'unavailable' \
      and sensor.lux_{MInvSN}_battery != 'unavailable' \
      and number.lux_{MInvSN}_system_charge_power_rate != 'unavailable' \
      and number.lux_{MInvSN}_system_discharge_power_rate != 'unavailable' \
      and number.lux_{SInvSN}_system_charge_power_rate != 'unavailable' \
      and number.lux_{SInvSN}_system_discharge_power_rate != 'unavailable' \
      and int(sensor.lux_{SInvSN}_battery) == int(sensor.lux_{MInvSN}_battery) \
      and int(float(number.lux_{MInvSN}_system_charge_power_rate) \
            + float(number.lux_{MInvSN}_system_discharge_power_rate) \
            + float(number.lux_{SInvSN}_system_charge_power_rate) \
            + float(number.lux_{SInvSN}_system_discharge_power_rate)) != 400"
)
def set_dis_and_charge_rates_to_100():
    log.debug(f"set_dis_and_charge_rates_to_100 has fired")

    set_percentage_if_changed(f"number.lux_{MInvSN}_system_charge_power_rate", 100.0)
    set_percentage_if_changed(f"number.lux_{MInvSN}_system_discharge_power_rate", 100.0)
    set_percentage_if_changed(f"number.lux_{SInvSN}_system_charge_power_rate", 100.0)
    set_percentage_if_changed(f"number.lux_{SInvSN}_system_discharge_power_rate", 100.0)


def set_percentage_if_changed(entity_to_set, value_to_set):
    log.debug(f"Checking to set {entity_to_set} to {value_to_set}")
    try:
        value_we_got = float(state.get(entity_to_set))
    except:
        value_we_got = 0
    if value_we_got != value_to_set:
        log.info(f"About to set {entity_to_set} to {value_to_set}")
        service.call("number", "set_value", entity_id=entity_to_set, value=value_to_set, blocking=True)


def set_percentage_if_changed_by_x(entity_to_set, value_to_set, difference):
    log.debug(f"Checking to set {entity_to_set} to {value_to_set}")
    try:
        value_we_got = float(state.get(entity_to_set))
    except:
        value_we_got = 0
    if abs(value_we_got - value_to_set) >= difference:
        log.info(f"About to set {entity_to_set} to {value_to_set}")
        service.call("number", "set_value", entity_id=entity_to_set, value=value_to_set, blocking=True)
