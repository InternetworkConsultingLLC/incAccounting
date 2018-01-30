window.onload = function() {
	new MenuController("User List");
	new UserListController();
};

var UserListController = function() {
	// private	
	var txtName = document.getElementById("txtName");
	var txtStatus = document.getElementById("txtStatus");
	var btnSearch = document.getElementById("btnSearch");
	var btnAdd = document.getElementById("btnAdd");
	var btnDelete = document.getElementById("btnDelete");
	var trTemplate = document.getElementsByClassName("trTemplate")[0];
	
	var arrUsers = [];

	btnAdd.onclick = function() {};
	btnDelete.onclick = function() {};
	btnSearch.onclick = function() {
		var sName = null;
		if(typeof txtName.value === "sting" && txtName.value.trim().length > 0)
			sName = txtName.value.trim();

		var sStatus = null;
		if(typeof txtStatus.value === "sting" && txtStatus.value.trim().length > 0)
			sStatus = txtStatus.value.trim();

		var callback = function(response) {
			console.log(response);
		};
		
		User.loadSearch(sName, sStatus, callback);		
	};
};