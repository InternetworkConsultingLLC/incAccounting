if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.MenuController("User Edit");
		new inc.accounting.html.UserEditController();
	};

	inc.accounting.html.UserEditController = function() {
		try {
			var txtGUID = document.getElementById("txtGUID");
			var chkIsAllowed = document.getElementById("chkIsAllowed");
			var txtEmailAddress = document.getElementById("txtEmailAddress");
			var txtPasswordDate = document.getElementById("txtPasswordDate");

			var btnSave = document.getElementById("btnSave");
			btnSave.onclick = btnSave_Clicked;

			var txtPassword = document.getElementById("txtPassword");
			txtPassword.value = "";
			var txtConfirm = document.getElementById("txtConfirm");
			txtConfirm.value = "";

			var btnReset = document.getElementById("btnReset");
			btnReset.onclick = btnReset_Clicked;

			var btnSave_Clicked = function() {};
			var btnReset_Clicked = function() {};

			var loadByGuidCallback = function(user) {
				objUser = user;

				txtGUID.value = objUser.getGuid();
				chkIsAllowed.checked = objUser.getIsAllowed();
				txtEmailAddress.value = objUser.getEmailAddress();
				txtPasswordDate.value = objUser.getPasswordDate().toISOString().substring(0, 10);
			};

			var arrGuidParameters = inc.html.Dom.findGetParameter("GUID");
			var objUser = new inc.accounting.entities.User();
			if(arrGuidParameters.length === 1)
				objUser = inc.accounting.entities.User.loadByGuid(arrGuidParameters[0], loadByGuidCallback);
			else
				objUser.initialize();
		}
		catch(error) {
			alert(error);
		}
	};
};

