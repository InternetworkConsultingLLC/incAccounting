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
	inc.accounting.data.MembershipsRow = function() {
		var obj = new inc.data.Row();

				
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
		obj.getGroupsGuid = function() { return obj.get("Groups GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setGroupsGuid = function(value) { obj.set("Groups GUID", value); };
		
		

		return obj;
	};
};