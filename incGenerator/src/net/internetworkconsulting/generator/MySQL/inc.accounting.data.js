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
	inc.accounting.data.__table_cc__Row = function() {
		var obj = new inc.data.Row();

		// BEGIN COLUMNS		
		/*
		 * @type __type_java__
		 */
		obj.get__column_cc__ = function() { return obj.get(__column_qs__); };
		/*
		 * @param __type_java__ value 
		 */
		obj.set__column_cc__ = function(value) { obj.set(__column_qs__, value); };
		
		// END COLUMNS

		return obj;
	};
};