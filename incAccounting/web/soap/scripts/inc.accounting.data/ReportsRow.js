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
	inc.accounting.data.ReportsRow = function() {
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
		obj.getDisplayName = function() { return obj.get("Display Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setDisplayName = function(value) { obj.set("Display Name", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getHtmlTemplate = function() { return obj.get("HTML Template"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setHtmlTemplate = function(value) { obj.set("HTML Template", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getTitle = function() { return obj.get("Title"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setTitle = function(value) { obj.set("Title", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getAutoLoad = function() { return obj.get("Auto Load"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setAutoLoad = function(value) { obj.set("Auto Load", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getQuery = function() { return obj.get("Query"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setQuery = function(value) { obj.set("Query", value); };
		
		

		return obj;
	};
};