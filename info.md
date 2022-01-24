{% if installed %}
## Changes as compared to your installed version:

### Breaking Changes

### Changes

### Features

{% if version_installed.replace("v", "").replace(".","") | int < 001  %}

- Added `mode: firstrelease` - Please look [here](https://github.com/guybw/LuxPython/commits/main) for differences between the two public modes.
{% endif %}
{% if version_installed.replace("v", "").replace(".","") | int < 062  %}
- Release notes are shown in HACS depending on your installed version
{% endif %}

### Bugfixes

{% if version_installed.replace("v", "").replace(".","") | int < 062  %}
- Fix for `mode: firstrelease` returning `First Release!`
{% endif %}
---
{% endif %}