# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [4.2.14] - 2024-05-09

### Added

- Addition of additional Sensor CT Clamp.

## [4.2.12] - 2024-02-26

### Added

- Addition of Additional Control (register 168) AC Charge Battery Current

## [4.2.11] - 2024-02-08

### Added

- v4.2.11 Changelog

### Changed

- Updated readme

### Removed

- Remove HACS file - Hacs will never support private repos so I've removed it
- Removed old sensors.yaml file from the good old days when this was not UI driven
- Remove test scripts as they are far too old now and no longer work



## [4.2.11] - 2024-02-08

### Changed

- Update Deprecated 2024.1 Constants To New Constants Enforced 2025.1 Onwards


## [2.0.0] - 2023-01-01

### Changed
- Keeping old history for ease.
- 2.0.0 This version removes the requirement for sensors.yaml and configuration.yaml as these are moved into the integration.
- 1.9.9 Added Grid flow live sensor, rounded the sensor values to one decimal place.
- 1.9.8 Service call at startup refactured and added an init delay.
- 1.9.7 Service bug fix for separating the holding register and input register r…
- 1.9.6 Added functionality to reconnect to the Lux server
- 1.9.5 All multi attribute and state text sensor moved into integration
- 1.9.4 Service updates to handle multiple dongles
- 0.1 - First release. 
