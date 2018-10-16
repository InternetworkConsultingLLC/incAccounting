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
	inc.accounting.data.PayrollFieldTypesRow = function() {
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
		obj.getName = function() { return obj.get("Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setName = function(value) { obj.set("Name", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getDebitRequired = function() { return obj.get("Debit Required"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setDebitRequired = function(value) { obj.set("Debit Required", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getCreditRequired = function() { return obj.get("Credit Required"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setCreditRequired = function(value) { obj.set("Credit Required", value); };
		
		

		return obj;
	};
};