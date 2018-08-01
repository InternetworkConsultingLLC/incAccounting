if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("User List");
		new inc.accounting.html.UserList();
	};

	inc.accounting.html.UserList = function() {
		var txtName = document.getElementById("txtName");
		var btnSearch = document.getElementById("btnSearch");

		var btnAdd_OnClick = function() { window.location.href = "UserEdit.html"; };
		var btnAdd = document.getElementById("btnAdd");
		btnAdd.onclick = btnAdd_OnClick;

		var btnDelete_Callback = function(ret) {
			if(!(ret instanceof Error)) {
				btnSearch.onclick();
				return;
			}
			
			var err = ret.toString();
			if(err.includes("foreign key constraint fails"))
				alert("This record cannnot be deleted - it is being referenced by other records!");
			else
				alert(err);
		};
		var btnDelete_OnCLick = function() {
			var arrDeletes = document.getElementsByClassName("chkDelete");
			
			var iDeleteCount = 0;
			for(var cnt = 0; cnt < arrDeletes.length; cnt++)
				if(arrDeletes[cnt].checked)
					iDeleteCount++;
			if(iDeleteCount > 1) {
				alert("You may only delete one record at a time!");
				return;
			}
			
			for(var cnt = 0; cnt < arrDeletes.length; cnt++) {
				var chkDelete = arrDeletes[cnt];
				if(chkDelete.checked) {
					var user = arrEntities[chkDelete.id];
					user.setIsDeleted(true);
					user.save(btnDelete_Callback);
				}
			}
		};
		var btnDelete = document.getElementById("btnDelete");
		btnDelete.onclick = btnDelete_OnCLick;

		var ddlIsAllowed = document.getElementById("ddlIsAllowed");

		var tableHtml = document.getElementById("tableList").outerHTML;
		var arrEntities = {};

		var btnSearch_Callback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}
			
			arrEntities = {};
			trTemplate = document.getElementsByClassName("trTemplate")[0];
			for(var cnt = 0; cnt < ret.length; cnt++) {
				var user = ret[cnt];
				arrEntities[user.getGuid()] = user;
				
				var html = trTemplate.outerHTML;
				html = html.replace(' class="trTemplate"', "");
				html = html.replace(/_GUID_/g, user.getGuid());
				html = html.replace(/_DisplayName_/g, user.getDisplayName());
				if(user.getIsAllowed())
					html = html.replace(/_Status_/g, "Allowed");
				else
					html = html.replace(/_Status_/g, "Disabled");

				document.getElementById("tableList").insertAdjacentHTML('beforeend', html);
			}
		};
		var btnSearch_OnClick = function() {
			document.getElementById("tableList").outerHTML = tableHtml;

			var sName = txtName.value.trim();
			if(sName.length < 1)
				sName = null;

			var bIsAllowed = null;
			if(ddlIsAllowed.value === "1")
				bIsAllowed = true;
			else if(ddlIsAllowed.value === "0")
				bIsAllowed = false;

			inc.accounting.business.User.loadSearch(sName, bIsAllowed, btnSearch_Callback);
		};
		btnSearch.onclick = btnSearch_OnClick;
	};
};