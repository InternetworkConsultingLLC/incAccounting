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
	inc.accounting.data.ItemPostingsRow = function() {
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
		obj.getIncreasingDocumentLinesGuid = function() { return obj.get("Increasing Document Lines GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setIncreasingDocumentLinesGuid = function(value) { obj.set("Increasing Document Lines GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getDecreasingDocumentLinesGuid = function() { return obj.get("Decreasing Document Lines GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setDecreasingDocumentLinesGuid = function(value) { obj.set("Decreasing Document Lines GUID", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getDecreaseQuantity = function() { return obj.get("Decrease Quantity"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setDecreaseQuantity = function(value) { obj.set("Decrease Quantity", value); };
		
		

		return obj;
	};
};