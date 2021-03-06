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
	inc.accounting.data.DocumentTypesRow = function() {
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
		 * @type java.lang.Boolean
		 */
		obj.getIsSalesRelated = function() { return obj.get("Is Sales Related"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsSalesRelated = function(value) { obj.set("Is Sales Related", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsCreditMemo = function() { return obj.get("Is Credit Memo"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsCreditMemo = function(value) { obj.set("Is Credit Memo", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsPosting = function() { return obj.get("Is Posting"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsPosting = function(value) { obj.set("Is Posting", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getAccountsGuid = function() { return obj.get("Accounts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setAccountsGuid = function(value) { obj.set("Accounts GUID", value); };
		
		

		return obj;
	};
};