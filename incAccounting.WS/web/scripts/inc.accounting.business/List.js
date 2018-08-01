if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.business)
	inc.accounting.business = {};

new function() {
	inc.accounting.business.List = function() {
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
				var entity = new inc.accounting.business.List();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "list_initialize", data, response);
		};
		
		obj.save = function(callback) {
			var data = {
				Entity: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_saveResponse/return");
				var entity = new inc.accounting.business.List();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "list_save", data, response);
		};
		
		return obj;
	};
	inc.accounting.business.List.loadSearch = function(display_name, callback) {
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
				var currentEntity = new inc.accounting.business.List();
				var currentNode = nodes[index];
				inc.html.Ajax.populateObject(currentNode, currentEntity);				
				arrEntities.push(currentEntity);
			}

			callback(arrEntities);
		};
		inc.html.Ajax.postSoap("API", "list_loadSearch", data, response);
	};
	inc.accounting.business.List.loadByGuid = function(guid, callback) {
		var data = {
			Guid: guid
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrNodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:list_loadByGuidResponse/return");
			var entity = new inc.accounting.business.List();
			inc.html.Ajax.populateObject(arrNodes[0], entity);
			callback(entity);
		};
		inc.html.Ajax.postSoap("API", "list_loadByGuid", data, response);
	};
};