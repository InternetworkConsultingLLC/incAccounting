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
			var objUser = new inc.accounting.entities.User();
			var sGuid = null;
			if(inc.html.Dom.findGetParameter("GUID").length === 1)
				sGuid = inc.html.Dom.findGetParameter("GUID")[0];
			
			var txtGUID = document.getElementById("txtGUID");
			var chkIsAllowed = document.getElementById("chkIsAllowed");
			var txtEmailAddress = document.getElementById("txtEmailAddress");
			var txtPasswordDate = document.getElementById("txtPasswordDate");

			var txtPassword = document.getElementById("txtPassword");
			var txtConfirm = document.getElementById("txtConfirm");

			var btnSave = document.getElementById("btnSave");
			var btnSave_Clicked = function() {};
			btnSave.onclick = btnSave_Clicked;

			var btnReset = document.getElementById("btnReset");
			var btnReset_Clicked = function() {};
			btnReset.onclick = btnReset_Clicked;

			var loadControlsFromObject = function() {
				txtGUID.value = objUser.getGuid();
				chkIsAllowed.checked = objUser.getIsAllowed();
				txtEmailAddress.value = objUser.getEmailAddress();
				txtPasswordDate.value = objUser.getPasswordDate().toISOString().substring(0, 10);

				txtPassword.value = "";
				txtConfirm.value = "";
			};
			
			var loadObjectFromControls = function() {
				objUser.setIsAllowed(chkIsAllowed.checked);
				objUser.setEmailAddress(txtEmailAddress.value);
				objUser.setPasswordDate(new Date(txtPasswordDate.value));
			};
			
			var loadByGuidCallback = function(user) {
				objUser = user;
				loadControlsFromObject();
			};

			if(sGuid)
				inc.accounting.entities.User.loadByGuid(sGuid, loadByGuidCallback);
			else {
				objUser = objUser.initialize();
				loadControlsFromObject();
			}

		
		}
		catch(error) {
			alert(error);
		}
	};
};

