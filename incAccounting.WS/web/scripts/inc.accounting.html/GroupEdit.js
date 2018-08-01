if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("Group Edit");
		new inc.accounting.html.GroupEdit();
	};

	inc.accounting.html.GroupEdit = function() {
		var entity = new inc.accounting.business.Group();
		var sGuid = null;
		if(inc.html.Dom.findGetParameter("GUID").length === 1)
			sGuid = inc.html.Dom.findGetParameter("GUID")[0];

		var txtGUID = document.getElementById("txtGUID");
		var chkIsAllowed = document.getElementById("chkIsAllowed");
		var txtDisplayName = document.getElementById("txtDisplayName");
		var txtEmailAddress = document.getElementById("txtEmailAddress");

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

		var loadControlsFromObject = function() {
			txtGUID.value = entity.getGuid();
			chkIsAllowed.checked = entity.getIsAllowed();
			txtDisplayName.value = entity.getDisplayName();
			txtEmailAddress.value = entity.getEmailAddress();
		};

		var loadObjectFromControls = function() {
			entity.setIsAllowed(chkIsAllowed.checked);
			entity.setDisplayName(txtDisplayName.value);
			entity.setEmailAddress(txtEmailAddress.value);
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
			inc.accounting.business.Group.loadByGuid(sGuid, loadEntityCallback);
		else
			entity.initialize(loadEntityCallback);
	};
};

