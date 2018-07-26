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
	inc.accounting.data.PaymentsRow = function() {
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
		obj.getPaymentTypesGuid = function() { return obj.get("Payment Types GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPaymentTypesGuid = function(value) { obj.set("Payment Types GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBankDepositsGuid = function() { return obj.get("Bank Deposits GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBankDepositsGuid = function(value) { obj.set("Bank Deposits GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getOurNumber = function() { return obj.get("Our Number"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setOurNumber = function(value) { obj.set("Our Number", value); };
		
				
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
		obj.getPostedAccountsGuid = function() { return obj.get("Posted Accounts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPostedAccountsGuid = function(value) { obj.set("Posted Accounts GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getPostedTransactionsGuid = function() { return obj.get("Posted Transactions GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPostedTransactionsGuid = function(value) { obj.set("Posted Transactions GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getPrepaymentDocumentsGuid = function() { return obj.get("Prepayment Documents GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPrepaymentDocumentsGuid = function(value) { obj.set("Prepayment Documents GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getExpenseLinesDocumentsGuid = function() { return obj.get("Expense Lines Documents GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setExpenseLinesDocumentsGuid = function(value) { obj.set("Expense Lines Documents GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getContactsGuid = function() { return obj.get("Contacts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setContactsGuid = function(value) { obj.set("Contacts GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getContactsDisplayName = function() { return obj.get("Contacts Display Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setContactsDisplayName = function(value) { obj.set("Contacts Display Name", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getTheirNumber = function() { return obj.get("Their Number"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setTheirNumber = function(value) { obj.set("Their Number", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingContactsGuid = function() { return obj.get("Billing Contacts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingContactsGuid = function(value) { obj.set("Billing Contacts GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressDisplayName = function() { return obj.get("Billing Address Display Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressDisplayName = function(value) { obj.set("Billing Address Display Name", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressLine1 = function() { return obj.get("Billing Address Line 1"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressLine1 = function(value) { obj.set("Billing Address Line 1", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressLine2 = function() { return obj.get("Billing Address Line 2"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressLine2 = function(value) { obj.set("Billing Address Line 2", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressCity = function() { return obj.get("Billing Address City"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressCity = function(value) { obj.set("Billing Address City", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressState = function() { return obj.get("Billing Address State"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressState = function(value) { obj.set("Billing Address State", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressPostalCode = function() { return obj.get("Billing Address Postal Code"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressPostalCode = function(value) { obj.set("Billing Address Postal Code", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getBillingAddressCountry = function() { return obj.get("Billing Address Country"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setBillingAddressCountry = function(value) { obj.set("Billing Address Country", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getTotal = function() { return obj.get("Total"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setTotal = function(value) { obj.set("Total", value); };
		
		

		return obj;
	};
};