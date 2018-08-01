if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("User Edit");
		new inc.accounting.html.UserEdit();
	};

	inc.accounting.html.UserEdit = function() {
		var entity = new inc.accounting.business.User();
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
			entity = ret;
			loadControlsFromObject();
		};
		var btnSave_Clicked = function() {
			loadObjectFromControls();
			entity.save(btnSave_Callback);
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

			entity.resetSqlPassword(txtPassword.value, txtConfirm.value, btnReset_Callback);			
		};
		btnReset.onclick = btnReset_Clicked;

		var loadControlsFromObject = function() {
			txtGUID.value = entity.getGuid();
			chkIsAllowed.checked = entity.getIsAllowed();
			txtEmailAddress.value = entity.getEmailAddress();
			txtPasswordDate.value = inc.date.toIsoDate(entity.getPasswordDate());
			txtDisplayName.value = entity.getDisplayName();

			txtPassword.value = "";
			txtConfirm.value = "";
		};

		var loadObjectFromControls = function() {
			entity.setIsAllowed(chkIsAllowed.checked);
			entity.setEmailAddress(txtEmailAddress.value);
			entity.setPasswordDate(inc.date.parse(txtPasswordDate.value));
			entity.setDisplayName(txtDisplayName.value);
		};

		var loadEntityCallback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}
			
			entity = ret;
			loadControlsFromObject();
		};

		if(sGuid)
			inc.accounting.business.User.loadByGuid(sGuid, loadEntityCallback);
		else
			entity.initialize(loadEntityCallback);
	};
};

