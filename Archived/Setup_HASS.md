# Installation of homeassistant in virtual environment

```
cd /home/pi
python3 -m venv homeassistant
```

# Run the homeassistant

## Activate the virtual environment
```
cd /home/pi
source homeassistantbin/activate
```

## Run the HASS
```
hass
```

# Create custom component


## Create custom_components folder in the homeassistant configuration folder ( Note : this is a hidden folder)

cd /home/pi/.homeassistant
mkdir custom_components

## Copy the luxpower folder to the custom_compoenents folder created above

```
cp -R <path_to_>/luxpower /home/pi/.homeassistant/custom_components
```

# Install custom component

## Open the configuration file 

/home/pi/.homeassistant/configuration.yaml

```
nano /home/pi/.homeassistant/configuration.yaml
```

## Add the custom component to the end of the configuration ( Note: correct the IP if its different)

```
luxpower:
  host: 172.16.255.120
  port: 8000
