if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.business)
	inc.accounting.business = {};

new function() {
	inc.accounting.business.User = function() {
		var obj = new inc.accounting.data.UsersRow();

		var sDatabase = null;
		var sPassword = null;
		
		obj.initialize = function(callback) {
			var data = {
				Entity: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:user_initializeResponse/return");
				var entity = new inc.accounting.business.User();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "user_initialize", data, response);
		};

		obj.getDatabase = function() { return sDatabase; };
		obj.setDatabase = function(value) { sDatabase = value; };

		obj.getPassword = function() { return sPassword; };
		obj.setPassword = function(value) { sPassword = value; };

		obj.login = function(callback) {
			var data = {
				EmailAddress: obj.getEmailAddress(),
				Password: obj.getPassword(),
				Database: obj.getDatabase()
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:user_loginResponse/return/#text");
				callback(nodes);
			};
			inc.html.Ajax.postSoap("API", "user_login", data, response);
		};

		obj.resetSqlPassword = function(password, confirm, callback) {
			var data = {
				Entity: obj,
				Password: password,
				Confirm: confirm
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:user_resetPasswordResponse/return");
				callback(arrNode[0]);
			};
			inc.html.Ajax.postSoap("API", "user_resetPassword", data, response);
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
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:user_saveResponse/return");
				var entity = new inc.accounting.business.User();
				inc.html.Ajax.populateObject(arrNode[0], entity);					
				callback(entity);
			};
			inc.html.Ajax.postSoap("API", "user_save", data, response);
		};

		return obj;
	};
	inc.accounting.business.User.ADMINISTRATOR_GUID = "86b41969e95143c090fd93a4819c58a2";
	inc.accounting.business.User.SETTING_PASSWORD_AGE = "Password Age (Days)";
	inc.accounting.business.User.SETTING_PASSWORD_COMPLEXITY = "Password Complexity (1-4)";
	inc.accounting.business.User.SETTING_PASSWORD_LENGTH = "Password Length";
	inc.accounting.business.User.SETTING_VERSION_NUMBER = "Version Number";
	inc.accounting.business.User.loadSearch = function(display_name, is_allowed, callback) {
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
			var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:user_loadSearchResponse/return");
			for(var index in nodes) {
				var currentEntity = new inc.accounting.business.User();
				var currentNode = nodes[index];
				inc.html.Ajax.populateObject(currentNode, currentEntity);				
				arrEntities.push(currentEntity);
			}

			callback(arrEntities);
		};
		inc.html.Ajax.postSoap("API", "user_loadSearch", data, response);
	};
	inc.accounting.business.User.loadByGuid = function(guid, callback) {
		var data = {
			Guid: guid
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrNodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:user_loadByGuidResponse/return");
			var entity = new inc.accounting.business.User();
			inc.html.Ajax.populateObject(arrNodes[0], entity);
			callback(entity);
		};
		inc.html.Ajax.postSoap("API", "user_loadByGuid", data, response);
	};
};