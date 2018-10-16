if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.business)
	inc.accounting.business = {};

new function() {
	inc.accounting.business.GroupEntity = function() {
		var obj = new inc.accounting.data.GroupsRow();
		
		obj.initialize = function(callback) {
			var data = {
				Entity: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:group_initializeResponse/return");
				var entity = new inc.accounting.business.GroupEntity();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "group_initialize", data, response);
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
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:group_saveResponse/return");
				var entity = new inc.accounting.business.GroupEntity();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "group_save", data, response);
		};
		
		return obj;
	};
	inc.accounting.business.GroupEntity.ADMINISTRATORS_GUID = "f541b846c9224fc687420fce2a0ec8b1";
	inc.accounting.business.GroupEntity.EVERYONE_GUID = "11eede08a5f34402a2547edc6aad2529";
	inc.accounting.business.GroupEntity.loadSearch = function(display_name, is_allowed, callback) {
		var data = {
			DisplayName: display_name,
			IsAllowed: is_allowed
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrEntities = [];
			var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:group_loadSearchResponse/return");
			for(var index in nodes) {
				var currentEntity = new inc.accounting.business.GroupEntity();
				var currentNode = nodes[index];
				inc.html.Ajax.populateObject(currentNode, currentEntity);				
				arrEntities.push(currentEntity);
			}

			callback(arrEntities);
		};
		inc.html.Ajax.postSoap("API", "group_loadSearch", data, response);
	};
	inc.accounting.business.GroupEntity.loadByGuid = function(guid, callback) {
		var data = {
			Guid: guid
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrNodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:group_loadByGuidResponse/return");
			var entity = new inc.accounting.business.GroupEntity();
			inc.html.Ajax.populateObject(arrNodes[0], entity);
			callback(entity);
		};
		inc.html.Ajax.postSoap("API", "group_loadByGuid", data, response);
	};
};