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
	inc.accounting.data.PaymentApplicationsRow = function() {
		var obj = new inc.data.Row();

				
		/*
		 * @type java.lang.String
		 */
		obj.getPaymentsGuid = function() { return obj.get("Payments GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPaymentsGuid = function(value) { obj.set("Payments GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getDocumentsGuid = function() { return obj.get("Documents GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setDocumentsGuid = function(value) { obj.set("Documents GUID", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getAmount = function() { return obj.get("Amount"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setAmount = function(value) { obj.set("Amount", value); };
		
		

		return obj;
	};
};