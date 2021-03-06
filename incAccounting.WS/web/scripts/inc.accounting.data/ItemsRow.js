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
	inc.accounting.data.ItemsRow = function() {
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
		obj.getParentItemsGuid = function() { return obj.get("Parent Items GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setParentItemsGuid = function(value) { obj.set("Parent Items GUID", value); };
		
				
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
		obj.getSalesAccountsGuid = function() { return obj.get("Sales Accounts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setSalesAccountsGuid = function(value) { obj.set("Sales Accounts GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getSalesDescription = function() { return obj.get("Sales Description"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setSalesDescription = function(value) { obj.set("Sales Description", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getSalesMarkUp = function() { return obj.get("Sales Mark Up"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setSalesMarkUp = function(value) { obj.set("Sales Mark Up", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getSalesUnitPrice = function() { return obj.get("Sales Unit Price"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setSalesUnitPrice = function(value) { obj.set("Sales Unit Price", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsSalesTaxed = function() { return obj.get("Is Sales Taxed"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsSalesTaxed = function(value) { obj.set("Is Sales Taxed", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getPurchaseAccountsGuid = function() { return obj.get("Purchase Accounts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPurchaseAccountsGuid = function(value) { obj.set("Purchase Accounts GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getPurchaseDescription = function() { return obj.get("Purchase Description"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPurchaseDescription = function(value) { obj.set("Purchase Description", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getPurchaseContactsGuid = function() { return obj.get("Purchase Contacts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setPurchaseContactsGuid = function(value) { obj.set("Purchase Contacts GUID", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getLastUnitCost = function() { return obj.get("Last Unit Cost"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setLastUnitCost = function(value) { obj.set("Last Unit Cost", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getInventoryUnitMeasuresGuid = function() { return obj.get("Inventory Unit Measures GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setInventoryUnitMeasuresGuid = function(value) { obj.set("Inventory Unit Measures GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getInventoryAccountsGuid = function() { return obj.get("Inventory Accounts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setInventoryAccountsGuid = function(value) { obj.set("Inventory Accounts GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getInventoryDescription = function() { return obj.get("Inventory Description"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setInventoryDescription = function(value) { obj.set("Inventory Description", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsSerialized = function() { return obj.get("Is Serialized"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsSerialized = function(value) { obj.set("Is Serialized", value); };
		
		

		return obj;
	};
};