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
	inc.accounting.data.DocumentLinesRow = function() {
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
		obj.getDocumentsGuid = function() { return obj.get("Documents GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setDocumentsGuid = function(value) { obj.set("Documents GUID", value); };
		
				
		/*
		 * @type java.lang.Integer
		 */
		obj.getSortOrder = function() { return obj.get("Sort Order"); };
		/*
		 * @param java.lang.Integer value 
		 */
		obj.setSortOrder = function(value) { obj.set("Sort Order", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getQuantity = function() { return obj.get("Quantity"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setQuantity = function(value) { obj.set("Quantity", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getItemsGuid = function() { return obj.get("Items GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setItemsGuid = function(value) { obj.set("Items GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getUnitMeasuresGuid = function() { return obj.get("Unit Measures GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setUnitMeasuresGuid = function(value) { obj.set("Unit Measures GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getDescription = function() { return obj.get("Description"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setDescription = function(value) { obj.set("Description", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getUnitPrice = function() { return obj.get("Unit Price"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setUnitPrice = function(value) { obj.set("Unit Price", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getExtension = function() { return obj.get("Extension"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setExtension = function(value) { obj.set("Extension", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsTaxed = function() { return obj.get("Is Taxed"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsTaxed = function(value) { obj.set("Is Taxed", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsBackwardsCalculated = function() { return obj.get("Is Backwards Calculated"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsBackwardsCalculated = function(value) { obj.set("Is Backwards Calculated", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getJobsGuid = function() { return obj.get("Jobs GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setJobsGuid = function(value) { obj.set("Jobs GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getDepartmentsGuid = function() { return obj.get("Departments GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setDepartmentsGuid = function(value) { obj.set("Departments GUID", value); };
		
				
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