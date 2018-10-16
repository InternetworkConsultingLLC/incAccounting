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
	inc.accounting.data.ReportBlocksRow = function() {
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
		obj.getReportsGuid = function() { return obj.get("Reports GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setReportsGuid = function(value) { obj.set("Reports GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getParentBlockGuid = function() { return obj.get("Parent Block GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setParentBlockGuid = function(value) { obj.set("Parent Block GUID", value); };
		
				
		/*
		 * @type java.lang.Integer
		 */
		obj.getPriority = function() { return obj.get("Priority"); };
		/*
		 * @param java.lang.Integer value 
		 */
		obj.setPriority = function(value) { obj.set("Priority", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getName = function() { return obj.get("Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setName = function(value) { obj.set("Name", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getSqlQuery = function() { return obj.get("SQL Query"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setSqlQuery = function(value) { obj.set("SQL Query", value); };
		
		

		return obj;
	};
};