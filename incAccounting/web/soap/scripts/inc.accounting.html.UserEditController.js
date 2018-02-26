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
		var objUser = new inc.accounting.entities.User();
		var sGuid = null;
		if(inc.html.Dom.findGetParameter("GUID").length === 1)
			sGuid = inc.html.Dom.findGetParameter("GUID")[0];

		var txtGUID = document.getElementById("txtGUID");
		var chkIsAllowed = document.getElementById("chkIsAllowed");
		var txtEmailAddress = document.getElementById("txtEmailAddress");
		var txtPasswordDate = document.getElementById("txtPasswordDate");
		var txtDisplayName = document.getElementById("txtDisplayName");

		var txtPassword = document.getElementById("txtPassword");
		var txtConfirm = document.getElementById("txtConfirm");


		var btnSave = document.getElementById("btnSave");
		var btnSave_Callback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}

			alert("Saved!");
			objUser = ret;
			loadControlsFromObject();
		};
		var btnSave_Clicked = function() {
			loadObjectFromControls();
			objUser.save(btnSave_Callback);
		};
		btnSave.onclick = btnSave_Clicked;

		var btnReset = document.getElementById("btnReset");
		var btnReset_Callback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}				

			alert("Password reset!");
			txtConfirm.value = "";
			txtPassword.value = "";
		};
		var btnReset_Clicked = function() {
			if(txtPassword.value !== txtConfirm.value) {
				alert("The password and confirmation do not match!");
				return;
			}

			objUser.resetSqlPassword(txtPassword.value, txtConfirm.value, btnReset_Callback);			
		};
		btnReset.onclick = btnReset_Clicked;

		var loadControlsFromObject = function() {
			txtGUID.value = objUser.getGuid();
			chkIsAllowed.checked = objUser.getIsAllowed();
			txtEmailAddress.value = objUser.getEmailAddress();
			txtPasswordDate.value = inc.date.toIsoDate(objUser.getPasswordDate());
			txtDisplayName.value = objUser.getDisplayName();

			txtPassword.value = "";
			txtConfirm.value = "";
		};

		var loadObjectFromControls = function() {
			objUser.setIsAllowed(chkIsAllowed.checked);
			objUser.setEmailAddress(txtEmailAddress.value);
			objUser.setPasswordDate(inc.date.parse(txtPasswordDate.value));
			objUser.setDisplayName(txtDisplayName.value);
		};

		var loadUserCallback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}
			
			objUser = ret;
			loadControlsFromObject();
		};

		if(sGuid)
			inc.accounting.entities.User.loadByGuid(sGuid, loadUserCallback);
		else
			objUser.initialize(loadUserCallback);
	};
};

