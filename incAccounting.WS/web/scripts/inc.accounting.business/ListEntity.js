if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.business)
	inc.accounting.business = {};

new function() {
	inc.accounting.business.ListEntity = function() {
		var obj = new inc.accounting.data.ListsRow();
		
		obj.initialize = function(callback) {
			var data = {
				Entity: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_initializeResponse/return");
				var entity = new inc.accounting.business.ListEntity();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "list_initialize", data, response);
		};

		obj.loadFilters = function(callback) {
			var data = {
				Entity: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}

				var arrEntities = [];
				var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_loadFiltersResponse/return");
				for(var index in nodes) {
					var currentEntity = new inc.accounting.business.ListFilterEntity();
					var currentNode = nodes[index];
					inc.html.Ajax.populateObject(currentNode, currentEntity);				
					arrEntities.push(currentEntity);
				}

				callback(arrEntities);
			};
			inc.html.Ajax.postSoap("API", "list_loadFilters", data, response);
		};
		
		obj.save = function(filters, callback) {
			var data = {
				List: obj,
				Filters: filters
			};
			var response = function(ret) {
//				if(ret instanceof Error) {
//					callback(ret);
//					return;
//				}

				callback(ret);
			};
			inc.html.Ajax.postSoap("API", "list_save", data, response);
		};		
		
		return obj;
	};
	inc.accounting.business.ListEntity.loadSearch = function(display_name, callback) {
		var data = {
			DisplayName: display_name
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrEntities = [];
			var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_loadSearchResponse/return");
			for(var index in nodes) {
				var currentEntity = new inc.accounting.business.ListEntity();
				var currentNode = nodes[index];
				inc.html.Ajax.populateObject(currentNode, currentEntity);				
				arrEntities.push(currentEntity);
			}

			callback(arrEntities);
		};
		inc.html.Ajax.postSoap("API", "list_loadSearch", data, response);
	};
	inc.accounting.business.ListEntity.loadByDisplayName = function(display_name, callback) {
		var data = {
			DisplayName: display_name
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrNodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_loadByDisplayNameResponse/return");
			var entity = new inc.accounting.business.ListEntity();
			inc.html.Ajax.populateObject(arrNodes[0], entity);
			callback(entity);
		};
		inc.html.Ajax.postSoap("API", "list_loadByDisplayName", data, response);
	};
	inc.accounting.business.ListEntity.loadByGuid = function(guid, callback) {
		var data = {
			Guid: guid
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrNodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_loadByGuidResponse/return");
			var entity = new inc.accounting.business.ListEntity();
			inc.html.Ajax.populateObject(arrNodes[0], entity);
			callback(entity);
		};
		inc.html.Ajax.postSoap("API", "list_loadByGuid", data, response);
	};
};