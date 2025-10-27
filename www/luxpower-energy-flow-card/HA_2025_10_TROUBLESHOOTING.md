# Home Assistant 2025.10 Troubleshooting Guide

## JavaScript Module Loading Issues

If you're experiencing issues with the LuxPower Energy Flow Card not loading properly in Home Assistant 2025.10, follow these steps:

### 1. Complete Resource Configuration

Make sure you have ALL required resources in your Lovelace configuration:

```yaml
lovelace:
  resources:
    # Main card component
    - url: /local/www/luxpower-energy-flow-card/luxpower-energy-flow-card.js
      type: module
    # Card editor for configuration
    - url: /local/www/luxpower-energy-flow-card/luxpower-energy-flow-card-editor.js
      type: module
    # Card wizard configuration
    - url: /local/www/luxpower-energy-flow-card/card-wizard-config.js
      type: module
    # Card wizard interface
    - url: /local/www/luxpower-energy-flow-card/luxpower-energy-flow-card-wizard.js
      type: module
```

### 2. Common Issues and Solutions

#### Issue: "Failed to load resource" errors
**Solution:** 
- Clear browser cache (Ctrl+F5 or Cmd+Shift+R)
- Check file paths are correct (note the `/local/www/` prefix)
- Ensure all files exist in the correct directory

#### Issue: Card doesn't appear in card picker
**Solution:**
- Make sure all 4 JavaScript files are loaded
- Restart Home Assistant completely
- Check browser console for JavaScript errors

#### Issue: "Custom element doesn't exist" error
**Solution:**
- Verify the main card file is loaded first
- Check that customElements.define() is being called
- Ensure no JavaScript syntax errors

#### Issue: Editor doesn't work
**Solution:**
- Make sure the editor file is loaded
- Check that the card has the getConfigElement() method
- Verify entity pickers are working

### 3. Browser Console Debugging

Open your browser's developer console (F12) and look for:

1. **Network errors**: Check if files are loading (404, 403 errors)
2. **JavaScript errors**: Look for syntax errors or undefined variables
3. **Module loading errors**: Check for ES6 module import/export issues

### 4. File Structure Verification

Ensure your file structure looks like this:
```
www/
└── luxpower-energy-flow-card/
    ├── luxpower-energy-flow-card.js
    ├── luxpower-energy-flow-card-editor.js
    ├── card-wizard-config.js
    ├── luxpower-energy-flow-card-wizard.js
    └── lovelace-resources.yaml
```

### 5. Alternative Loading Methods

If the standard method doesn't work, try:

#### Method A: Direct URL loading
```yaml
resources:
  - url: /local/www/luxpower-energy-flow-card/luxpower-energy-flow-card.js
    type: module
```

#### Method B: HACS installation
1. Install via HACS if available
2. Use HACS resource paths: `/hacsfiles/luxpower-energy-flow-card/`

### 6. Home Assistant 2025.10 Specific Notes

- **Stricter Module Loading**: HA 2025.10 has stricter ES6 module requirements
- **Custom Elements**: Ensure proper custom element registration
- **Shadow DOM**: Make sure shadow DOM is properly configured
- **Event Handling**: Verify event listeners are properly attached

### 7. Testing Steps

1. **Check Resources**: Go to Settings → Dashboards → Resources
2. **Verify Loading**: Check that all 4 files show as "Loaded"
3. **Test Card**: Try adding the card to a dashboard
4. **Test Editor**: Click the card's edit button to test the editor
5. **Test Wizard**: Try using the card wizard

### 8. Still Having Issues?

If you're still experiencing problems:

1. **Check Home Assistant Logs**: Look for JavaScript errors in the logs
2. **Browser Compatibility**: Ensure you're using a modern browser
3. **File Permissions**: Make sure Home Assistant can read the files
4. **Network Issues**: Check if there are any network/firewall issues

### 9. Quick Fix Checklist

- [ ] All 4 JavaScript files are in the correct directory
- [ ] All 4 files are added to Lovelace resources
- [ ] Browser cache is cleared
- [ ] Home Assistant is restarted
- [ ] No JavaScript errors in browser console
- [ ] File paths use `/local/www/` prefix
- [ ] All files have correct MIME types (application/javascript)
