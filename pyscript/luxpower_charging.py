dodebug = False

 

 

#@time_trigger("period(now, 2min)")

@task_unique("auto_set_ac_battery_charge_level", kill_me=True)
@state_trigger("(int(float(sensor.target_ac_battery_charge_level)) + int(float(sensor.lux_battery)) + 1 > 0 and binary_sensor.allow_processing_of_solar_forecast_changes == 'on') or dodebug")
def auto_set_ac_battery_charge_level():

    log.info(f"auto_set_ac_battery_charge_level has fired")

 

    import datetime

 

    charge_level = float(sensor.target_ac_battery_charge_level)

 

    set_percentage_if_changed_by_x("number.lux_1212016010_ac_battery_charge_level", int(float(charge_level)), 1)

    set_percentage_if_changed_by_x("number.lux_1212016024_ac_battery_charge_level", int(float(charge_level)), 1)

 

    #end_of_charge = datetime.datetime.now().replace(hour=5, minute=30, second=0, microsecond=0).timestamp()

    end_of_charge = datetime.datetime.now().replace(hour=5, minute=00, second=0, microsecond=0).timestamp()

    secs_until_eoc = end_of_charge - datetime.datetime.now().timestamp()

 

    mins_until_eoc = (secs_until_eoc / 60)

 

    charge_perc_reqd = charge_level - float(sensor.lux_battery)

 

    if dodebug:

        mins_until_eoc = 243.53

        #charge_perc_reqd = 38

 

    log.info(f"Time until End Of Charge mins: {mins_until_eoc}")

 

    #  Example seen 57% in 3 hours

    #  19% takes 1 hour at max charge rate

    #  1% take 60/19% minutes at max charge rate

    #  60/18% to be safe

 

    if charge_perc_reqd > 0:

 

        # Calculate Power Rate Whan Charging Required

        mins_at_full  = charge_perc_reqd * 60/18    #mins_in_hour / percent_charge_gain

 

        rate_perc = int(mins_at_full/mins_until_eoc*100)

 

        if rate_perc > 100:

            rate_perc = 100

 

        if rate_perc < 20:

            rate_perc = 20

 

        log.info(f"Values reqd: {charge_perc_reqd} mins: {mins_at_full} rate: {rate_perc}")

 

        set_percentage_if_changed_by_x("number.lux_1212016010_ac_charge_power_rate", int(float(rate_perc)),2)

        set_percentage_if_changed_by_x("number.lux_1212016024_ac_charge_power_rate", int(float(rate_perc)),2)

 

        set_ac_charge_enable_inverters_switch("turn_on")

 

    else:

 

        # Reset To Full Power Rate Whan At Level Or Requiring Discharge

        set_percentage_if_changed("number.lux_1212016010_ac_charge_power_rate", 100.0)

        set_percentage_if_changed("number.lux_1212016024_ac_charge_power_rate", 100.0)

 

        if charge_perc_reqd < 0:

            #### switch off ac charging ####

            set_ac_charge_enable_inverters_switch("turn_off")

            log.info(f"Discharge Required: {charge_perc_reqd} ")

        else:

            log.info(f"No Charge Required: {charge_perc_reqd} ")

 

 

def set_ac_charge_enable_inverters_switch(action_to_set):

 

    log.info(f"About to {action_to_set} AC enable switch on both inverters")

    set_switch("switch.lux_1212016010_ac_charge_enable", action_to_set)

    set_switch("switch.lux_1212016024_ac_charge_enable", action_to_set)

 

 

def set_switch(entity_to_set, action_to_set):

 

    log.info(f"About to {action_to_set} switch {entity_to_set}")

    service.call("switch", action_to_set, entity_id=entity_to_set, blocking=True)

 

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