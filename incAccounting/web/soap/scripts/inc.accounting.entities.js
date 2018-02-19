if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.entities)
	inc.accounting.entities = {};

new function() {
	inc.accounting.entities.User = function() {
		var obj = new inc.accounting.data.UserRow();

		var sDatabase = null;
		var sPassword = null;
		
		obj.initialize = function(callback) {
			var data = {
				User: obj
			};
			var response = function(xml) {
				var arrNode = inc.html.Ajax.getNodesByPath(xml, "/S:Envelope/S:Body/ns2:initializeResponse/return");
				var user = new inc.accounting.entities.User();
				inc.html.Ajax.populateObject(arrNode[0], user);					
				callback(user);
			};
			inc.html.Ajax.postSoap("User", "initialize", data, response);
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
			var response = function(xml) {
				var ret = inc.html.Ajax.getNodesByPath(xml, "/S:Envelope/S:Body/ns2:loginResponse/return/#text");
				callback(ret);
			};
			inc.html.Ajax.postSoap("User", "login", data, response);
		};

		obj.resetSqlPassword = function(password, confirm) {};
		obj.changePassword = function(password, confirm) {};
		obj.isPasswordExpired = function() {};

		obj.canCreate = function(securable_guid) {};
		obj.canRead = function(securable_guid) {};
		obj.canUpdate = function(securable_guid) {};
		obj.canDelete = function(securable_guid) {};

		obj.logEvent = function(message, code_guid) {};
		obj.logException = function(exception, code_guid) {};

		obj.loadSettings = function() {};
		obj.getSetting = function(key) {};
		obj.setSetting = function(key, value) {};

		obj.hashPassword = function(password) {};
		
		obj.save = function(callback) {
			var data = {
				User: obj
			};
			var postSoapCallback = function(xml) {
				var arrNode = inc.html.Ajax.getNodesByPath(xml, "/S:Envelope/S:Body/ns2:saveResponse/return");
				var user = new inc.accounting.entities.User();
				inc.html.Ajax.populateObject(arrNode[0], user);					
				callback(user);
			};
			inc.html.Ajax.postSoap("User", "save", data, postSoapCallback);
		};

		return obj;
	};
	inc.accounting.entities.User.ADMINISTRATOR_GUID = "86b41969e95143c090fd93a4819c58a2";
	inc.accounting.entities.User.SETTING_PASSWORD_AGE = "Password Age (Days)";
	inc.accounting.entities.User.SETTING_PASSWORD_COMPLEXITY = "Password Complexity (1-4)";
	inc.accounting.entities.User.SETTING_PASSWORD_LENGTH = "Password Length";
	inc.accounting.entities.User.SETTING_VERSION_NUMBER = "Version Number";

	inc.accounting.entities.User.newGuid = function() {};
	inc.accounting.entities.User.loadOptions = function() {};
	inc.accounting.entities.User.loadByEmailAddress = function() {};
	inc.accounting.entities.User.loadSearch = function(display_name) {};
	inc.accounting.entities.User.loadSearch = function(display_name, is_allowed, loadSearchCallback) {
		var data = {
			DisplayName: display_name,
			IsAllowed: is_allowed
		};
		var postSoapResponse = function(xml) {
			var ret = [];
			var nodes = inc.html.Ajax.getNodesByPath(xml, "/S:Envelope/S:Body/ns2:loadSearchResponse/return");
			
			for(var index in nodes) {
				var currentUser = new inc.accounting.entities.User();
				var currentNode = nodes[index];
				inc.html.Ajax.populateObject(currentNode, currentUser);				
				ret.push(currentUser);
			}

			loadSearchCallback(ret);
		};
		inc.html.Ajax.postSoap("User", "loadSearch", data, postSoapResponse);
	};
	inc.accounting.entities.User.loadByGuid = function(guid, callback) {
		var data = {
			Guid: guid
		};
		var postSoapResponse = function(xml) {
			var arrNodes = inc.html.Ajax.getNodesByPath(xml, "/S:Envelope/S:Body/ns2:loadByGuidResponse/return");
			if(!arrNodes)
				throw new Error("Load by GUID '" + guid + "' returned nothing!");
			
			var ret = new inc.accounting.entities.User();
			inc.html.Ajax.populateObject(arrNodes[0], ret);
			callback(ret);
		};
		inc.html.Ajax.postSoap("User", "loadByGuid", data, postSoapResponse);
	};
};




