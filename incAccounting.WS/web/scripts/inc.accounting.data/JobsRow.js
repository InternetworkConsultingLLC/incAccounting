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
	inc.accounting.data.JobsRow = function() {
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
		obj.getParentJobsGuid = function() { return obj.get("Parent Jobs GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setParentJobsGuid = function(value) { obj.set("Parent Jobs GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getSegment = function() { return obj.get("Segment"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setSegment = function(value) { obj.set("Segment", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getNumber = function() { return obj.get("Number"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setNumber = function(value) { obj.set("Number", value); };
		
				
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
		obj.getNestedName = function() { return obj.get("Nested Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setNestedName = function(value) { obj.set("Nested Name", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsAllowed = function() { return obj.get("Is Allowed"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsAllowed = function(value) { obj.set("Is Allowed", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getContactsGuid = function() { return obj.get("Contacts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setContactsGuid = function(value) { obj.set("Contacts GUID", value); };
		
		

		return obj;
	};
};