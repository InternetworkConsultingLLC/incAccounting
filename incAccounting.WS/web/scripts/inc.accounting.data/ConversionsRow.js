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
	inc.accounting.data.ConversionsRow = function() {
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
		obj.getLeftUnitMeasuresGuid = function() { return obj.get("Left Unit Measures GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setLeftUnitMeasuresGuid = function(value) { obj.set("Left Unit Measures GUID", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getLeftQuantity = function() { return obj.get("Left Quantity"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setLeftQuantity = function(value) { obj.set("Left Quantity", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getRightUnitMeasuresGuid = function() { return obj.get("Right Unit Measures GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setRightUnitMeasuresGuid = function(value) { obj.set("Right Unit Measures GUID", value); };
		
				
		/*
		 * @type java.math.BigDecimal
		 */
		obj.getRightQuantity = function() { return obj.get("Right Quantity"); };
		/*
		 * @param java.math.BigDecimal value 
		 */
		obj.setRightQuantity = function(value) { obj.set("Right Quantity", value); };
		
		

		return obj;
	};
};