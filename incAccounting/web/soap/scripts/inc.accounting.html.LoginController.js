if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.MenuController("Login");
		new inc.accounting.html.LoginController();
	};

	inc.accounting.html.LoginController = function() {
		// private	
		var txtEmailAddress = document.getElementById("txtEmailAddress");
		var txtPassword = document.getElementById("txtPassword");
		var txtDatabase = document.getElementById("txtDatabase");

		var btnLogin = document.getElementById("btnLogin");
		btnLogin.onclick = function() {
			var user = new inc.accounting.entities.User();
			user.setEmailAddress(txtEmailAddress.value);
			user.setPassword(txtPassword.value);
			user.setDatabase(txtDatabase.value);
			user.login(loginCallback);
		};

		var loginCallback = function(nodes) {
			if(nodes.length === 0)
				window.location.href = "Copyright.html";
			else
				alert(nodes[0].nodeValue);
		};
	};
};


