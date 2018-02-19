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
			if(typeof ret === "string")
				window.alert("Save failed!\n\n" + ret);

			window.location.href = "UserEdit.html?GUID=" + objUser.getGuid();
		};
		var btnSave_Clicked = function() {
			loadObjectFromControls();
			objUser.save(btnSave_Callback);
		};
		btnSave.onclick = btnSave_Clicked;

		var btnReset = document.getElementById("btnReset");
		var btnReset_Clicked = function() {};
		btnReset.onclick = btnReset_Clicked;

		var loadControlsFromObject = function() {
			txtGUID.value = objUser.getGuid();
			chkIsAllowed.checked = objUser.getIsAllowed();
			txtEmailAddress.value = objUser.getEmailAddress();
			txtPasswordDate.value = inc.Date.toIsoDate(objUser.getPasswordDate());
			txtDisplayName.value = objUser.getDisplayName();

			txtPassword.value = "";
			txtConfirm.value = "";
		};

		var loadObjectFromControls = function() {
			objUser.setIsAllowed(chkIsAllowed.checked);
			objUser.setEmailAddress(txtEmailAddress.value);
			objUser.setPasswordDate(inc.Date.parse(txtPasswordDate.value));
			objUser.setDisplayName(txtDisplayName.value);
		};

		var userCallback = function(user) {
			objUser = user;
			loadControlsFromObject();
		};

		var loadUser = function() {
			if(sGuid)
				inc.accounting.entities.User.loadByGuid(sGuid, userCallback);
			else
				objUser.initialize(userCallback);
		};
		loadUser();
	};
};

