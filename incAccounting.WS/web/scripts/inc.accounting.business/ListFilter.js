if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.business)
	inc.accounting.business = {};

new function() {
	inc.accounting.business.ListFilter = function() {
		var obj = new inc.accounting.data.ListFiltersRow();
		
		obj.initialize = function(callback) {
			var data = {
				Entity: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:listFilter_initializeResponse/return");
				var entity = new inc.accounting.business.List();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "listFilter_initialize", data, response);
		};
		
		return obj;
	};
};