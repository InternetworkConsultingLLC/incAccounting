if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("Login");
		new inc.accounting.html.Login();
	};

	inc.accounting.html.Login = function() {
		// private	
		var txtEmailAddress = document.getElementById("txtEmailAddress");
		var txtPassword = document.getElementById("txtPassword");
		var txtDatabase = document.getElementById("txtDatabase");

		var btnLogin = document.getElementById("btnLogin");
		var btnLogin_Callback = function(nodes) {
			if(nodes instanceof Error) {
				alert(nodes.toString());
				return;
			}
			
			if(nodes.length === 0)
				window.location.href = "Copyright.html";
		};
		var btnLogin_OnClick = function() {
			var user = new inc.accounting.business.User();
			user.setEmailAddress(txtEmailAddress.value);
			user.setPassword(txtPassword.value);
			user.setDatabase(txtDatabase.value);
			user.login(btnLogin_Callback);
		};
		btnLogin.onclick = btnLogin_OnClick;

	};
};


