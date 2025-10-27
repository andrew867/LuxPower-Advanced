# Custom Element Not Found - Fix Guide

## 🚨 Error: "Custom element not found: custom:luxpower-energy-flow-card"

This error means the JavaScript file isn't loading properly or has a syntax error preventing the custom element from being registered.

### **🔍 Step 1: Test with Simple Version**

1. **Add the simple test resource:**
   - Go to **Settings → Dashboards → Resources**
   - Add: `/local/www/luxpower-energy-flow-card/luxpower-energy-flow-card-simple.js`
   - Type: `JavaScript Module`

2. **Test the simple card:**
   - Add card: `type: custom:luxpower-energy-flow-card-simple`
   - If this works, the issue is with the main card file
   - If this doesn't work, the issue is with resource loading

### **🔍 Step 2: Check Resource Loading**

1. **Open browser console** (F12)
2. **Go to Network tab**
3. **Refresh the page**
4. **Look for the JavaScript files:**
   - `luxpower-energy-flow-card.js` should show status `200`
   - If it shows `404`, the file path is wrong
   - If it shows `500`, there's a server error

### **🔍 Step 3: Check Console for Errors**

Look for these specific errors in the console:

#### **JavaScript Syntax Errors:**
- `SyntaxError: Unexpected token`
- `ReferenceError: ... is not defined`
- `TypeError: Cannot read property...`

#### **Resource Loading Errors:**
- `Failed to load resource: the server responded with a status of 404`
- `Failed to load resource: the server responded with a status of 500`

#### **Custom Element Errors:**
- `Custom element not found: custom:luxpower-energy-flow-card`
- `customElements.define is not a function`

### **🔧 Step 4: Common Fixes**

#### **Fix A: Wrong File Path**
**Problem:** File path is incorrect
**Solution:** Check your resource URLs:
```
✅ Correct: /local/www/luxpower-energy-flow-card/luxpower-energy-flow-card.js
❌ Wrong: /local/luxpower-energy-flow-card/luxpower-energy-flow-card.js
```

#### **Fix B: File Not Found**
**Problem:** File doesn't exist in the directory
**Solution:** 
1. Verify files exist in `www/luxpower-energy-flow-card/`
2. Check file permissions
3. Make sure Home Assistant can read the files

#### **Fix C: JavaScript Syntax Error**
**Problem:** Syntax error in JavaScript file
**Solution:**
1. Check browser console for syntax errors
2. Use the simple test version to isolate the issue
3. Fix any syntax errors in the main file

#### **Fix D: Resource Not Loading**
**Problem:** Resource shows as "Failed" in Lovelace resources
**Solution:**
1. Delete the resource and re-add it
2. Check the URL is correct
3. Restart Home Assistant
4. Clear browser cache

### **🔧 Step 5: Debugging Steps**

#### **Step 5A: Test Simple Version**
1. Add the simple test resource
2. Try adding the simple card
3. If it works, the issue is with the main card
4. If it doesn't work, the issue is with resource loading

#### **Step 5B: Check Network Tab**
1. Open browser console (F12)
2. Go to Network tab
3. Refresh the page
4. Look for the JavaScript files
5. Check their status codes

#### **Step 5C: Check Console Logs**
1. Look for console.log messages from the JavaScript
2. Check for any error messages
3. Verify the custom element is being registered

### **🔧 Step 6: Manual Testing**

#### **Test 1: Direct File Access**
Try accessing the file directly in your browser:
```
http://your-ha-ip:8123/local/www/luxpower-energy-flow-card/luxpower-energy-flow-card.js
```
If this shows the JavaScript code, the file is accessible.

#### **Test 2: Console Testing**
Open browser console and run:
```javascript
// Check if custom element is defined
console.log(customElements.get('luxpower-energy-flow-card'));

// Check if the class exists
console.log(typeof LuxPowerEnergyFlowCard);
```

### **🔧 Step 7: Complete Reset**

If nothing else works:

1. **Delete all LuxPower resources**
2. **Restart Home Assistant**
3. **Clear browser cache** (Ctrl+F5)
4. **Add resources one by one:**
   - Start with the simple test version
   - Then add the main card
   - Then add the editor
   - Finally add the wizard files

### **✅ Success Indicators**

You'll know it's working when:
- ✅ No "Custom element not found" errors
- ✅ Card appears in the card picker
- ✅ Card renders without loading gif
- ✅ Console shows "Custom element registered successfully"
- ✅ All resources show as "Loaded" in Lovelace resources

### **🚨 Still Having Issues?**

If you're still stuck:

1. **Use the simple test version** to isolate the problem
2. **Check the browser console** for specific error messages
3. **Verify file paths** are correct
4. **Test with a different browser** to rule out browser issues
5. **Check Home Assistant logs** for any server-side errors
