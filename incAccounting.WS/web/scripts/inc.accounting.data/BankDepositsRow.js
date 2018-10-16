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
	inc.accounting.data.BankDepositsRow = function() {
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
		obj.getAccountsGuid = function() { return obj.get("Accounts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setAccountsGuid = function(value) { obj.set("Accounts GUID", value); };
		
				
		/*
		 * @type java.util.Date
		 */
		obj.getDate = function() { return obj.get("Date"); };
		/*
		 * @param java.util.Date value 
		 */
		obj.setDate = function(value) { obj.set("Date", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getNumber = function() { return obj.get("Number"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setNumber = function(value) { obj.set("Number", value); };
		
				
		/*
		 * @type java.lang.Integer
		 */
		obj.getItems = function() { return obj.get("Items"); };
		/*
		 * @param java.lang.Integer value 
		 */
		obj.setItems = function(value) { obj.set("Items", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getTotal = function() { return obj.get("Total"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setTotal = function(value) { obj.set("Total", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getPostedTransactionsGuid = function() { return obj.get("Posted Transactions GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPostedTransactionsGuid = function(value) { obj.set("Posted Transactions GUID", value); };
		
		

		return obj;
	};
};