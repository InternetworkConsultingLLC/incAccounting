if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.MenuController("User Edit");
		new inc.accounting.html.UserListController();
	};

	inc.accounting.html.UserListController = function() {
		try {
			var txtName = document.getElementById("txtName");
			var btnSearch = document.getElementById("btnSearch");
			var btnAdd = document.getElementById("btnAdd");
			var btnDelete = document.getElementById("btnDelete");
			var ddlIsAllowed = document.getElementById("ddlIsAllowed");

			var tableHtml = document.getElementById("tableList").outerHTML;
			var arrUsers = [];

			btnAdd.onclick = function() {};
			btnDelete.onclick = function() {};
			btnSearch.onclick = btnSearch_OnClick;

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

				inc.accounting.entities.User.loadSearch(sName, bIsAllowed, loadSearchCallback);
			};

			var loadSearchCallback = function(users) {
				arrUsers = users;

				trTemplate = document.getElementsByClassName("trTemplate")[0];
				for(var cnt = 0; cnt < arrUsers.length; cnt++) {
					var html = trTemplate.outerHTML;
					html = html.replace(' class="trTemplate"', "");
					html = html.replace(/_GUID_/g, arrUsers[cnt].getGuid());
					html = html.replace(/_DisplayName_/g, arrUsers[cnt].getDisplayName());
					if(arrUsers[cnt].getIsAllowed())
						html = html.replace(/_Status_/g, "Allowed");
					else
						html = html.replace(/_Status_/g, "Disabled");

					document.getElementById("tableList").insertAdjacentHTML('beforeend', html);
				}
			};
		}
		catch(error) {
			alert(error);
		}
	};
};