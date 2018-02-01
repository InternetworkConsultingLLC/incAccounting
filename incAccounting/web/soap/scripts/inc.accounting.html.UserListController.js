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
		var txtName = document.getElementById("txtName");
		var btnSearch = document.getElementById("btnSearch");
		var btnAdd = document.getElementById("btnAdd");
		var btnDelete = document.getElementById("btnDelete");
		var trTemplate = document.getElementsByClassName("trTemplate")[0];

		var arrUsers = [];

		btnAdd.onclick = function () {};
		btnDelete.onclick = function () {};
		btnSearch.onclick = function () {
			var sName = null;
			if (typeof txtName.value === "sting" && txtName.value.trim().length > 0)
				sName = txtName.value.trim();

			inc.accounting.entities.User.loadSearch(sName, function(users) {
				arrUsers = users;

				for(var cnt = 0; cnt < arrUsers.length; cnt++) {
					var html = trTemplate.outerHTML;
					html = html.replace(' class="trTemplate"', "");
					html = html.replace("_GUID_", arrUsers[cnt].getGuid());
					html = html.replace("_DisplayName_", arrUsers[cnt].getDisplayName());
					if(arrUsers[cnt].getIsAllowed())
						html = html.replace("_Status_", "Allowed");
					else
						html = html.replace("_Status_", "Disabled");

					trTemplate.insertAdjacentHTML('afterend', html);
				}
			});
		};		
	};
};