if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.entities)
	inc.accounting.entities = {};

new function() {
	inc.accounting.entities.User = function() {
		var obj = new inc.accounting.data.UsersRow();

		var sDatabase = null;
		var sPassword = null;
		
		obj.initialize = function(callback) {
			var data = {
				User: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:userInitializeResponse/return");
				var user = new inc.accounting.entities.User();
				inc.html.Ajax.populateObject(arrNode[0], user);					
				callback(user);
			};
			inc.html.Ajax.postSoap("API", "userInitialize", data, response);
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
				
				var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:userLoginResponse/return/#text");
				callback(nodes);
			};
			inc.html.Ajax.postSoap("API", "userLogin", data, response);
		};

		obj.resetSqlPassword = function(password, confirm, callback) {
			var data = {
				User: obj,
				Password: password,
				Confirm: confirm
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:userResetSqlPasswordResponse/return");
				callback(arrNode[0]);
			};
			inc.html.Ajax.postSoap("API", "userResetSqlPassword", data, response);
		};
		
		obj.save = function(callback) {
			var data = {
				User: obj
			};
			var response = function(ret) {
				if(ret instanceof Error) {
					callback(ret);
					return;
				}
				var arrNode = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:userSaveResponse/return");
				var user = new inc.accounting.entities.User();
				inc.html.Ajax.populateObject(arrNode[0], user);					
				callback(user);
			};
			inc.html.Ajax.postSoap("API", "userSave", data, response);
		};

		return obj;
	};
	inc.accounting.entities.User.ADMINISTRATOR_GUID = "86b41969e95143c090fd93a4819c58a2";
	inc.accounting.entities.User.SETTING_PASSWORD_AGE = "Password Age (Days)";
	inc.accounting.entities.User.SETTING_PASSWORD_COMPLEXITY = "Password Complexity (1-4)";
	inc.accounting.entities.User.SETTING_PASSWORD_LENGTH = "Password Length";
	inc.accounting.entities.User.SETTING_VERSION_NUMBER = "Version Number";
	inc.accounting.entities.User.loadSearch = function(display_name, is_allowed, callback) {
		var data = {
			DisplayName: display_name,
			IsAllowed: is_allowed
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrUsers = [];
			var nodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:userLoadSearchResponse/return");
			for(var index in nodes) {
				var currentUser = new inc.accounting.entities.User();
				var currentNode = nodes[index];
				inc.html.Ajax.populateObject(currentNode, currentUser);				
				arrUsers.push(currentUser);
			}

			callback(arrUsers);
		};
		inc.html.Ajax.postSoap("API", "userLoadSearch", data, response);
	};
	inc.accounting.entities.User.loadByGuid = function(guid, callback) {
		var data = {
			Guid: guid
		};
		var response = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			var arrNodes = inc.html.Ajax.getNodesByPath(ret, "/S:Envelope/S:Body/ns2:userLoadByGuidResponse/return");
			var user = new inc.accounting.entities.User();
			inc.html.Ajax.populateObject(arrNodes[0], user);
			callback(user);
		};
		inc.html.Ajax.postSoap("API", "userLoadByGuid", data, response);
	};
	
	inc.accounting.entities.Group = function() {
		
	}
};




