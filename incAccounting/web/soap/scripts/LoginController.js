window.onload = function() {
	new MenuController("Login");
	new LoginController();
};

var LoginController = function() {
	// private	
	var txtEmailAddress = document.getElementById("txtEmailAddress");
	var txtPassword = document.getElementById("txtPassword");
	var txtDatabase = document.getElementById("txtDatabase");
	
	var btnLogin = document.getElementById("btnLogin");
	btnLogin.onclick = function() {
		var model = new User();
		model.EmailAddress = txtEmailAddress.value;
		model.Password = txtPassword.value;
		model.Database = txtDatabase.value;
		model.login(loginCallback);
	};
	
	var loginCallback = function(value) {
		if(typeof value === "undefined" || (typeof value === "string" && value.length === 0))
			window.location.href = "Copyright.html";
		else
			alert(value);
	};
};
