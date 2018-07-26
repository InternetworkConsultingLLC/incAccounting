////////////////////////////////////////////////////////////////////////////////
// 
// WARNING - DO NOT EDIT THIS FILE
//
// These data objects are generated from the database.  If you need to add 
// properties, please update the database, then re-generate.
//
////////////////////////////////////////////////////////////////////////////////
if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.data)
	inc.accounting.data = {};

new function() {
	inc.accounting.data.SettingsRow = function() {
		var obj = new inc.data.Row();

				
		/*
		 * @type java.lang.String
		 */
		obj.getGuid = function() { return obj.get("GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setGuid = function(value) { obj.set("GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getUsersGuid = function() { return obj.get("Users GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setUsersGuid = function(value) { obj.set("Users GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getKey = function() { return obj.get("Key"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setKey = function(value) { obj.set("Key", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getType = function() { return obj.get("Type"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setType = function(value) { obj.set("Type", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getOptionQuery = function() { return obj.get("Option Query"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setOptionQuery = function(value) { obj.set("Option Query", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getValue = function() { return obj.get("Value"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setValue = function(value) { obj.set("Value", value); };
		
		

		return obj;
	};
};