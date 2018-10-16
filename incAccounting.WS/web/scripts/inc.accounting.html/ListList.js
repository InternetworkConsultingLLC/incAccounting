if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("List List");
		new inc.accounting.html.ListList();
	};

	inc.accounting.html.ListList = function() {
		var txtDisplayName = document.getElementById("txtDisplayName");

		var btnAdd_OnClick = function() { window.location.href = "ListEdit.html"; };
		var btnAdd = document.getElementById("btnAdd");
		btnAdd.onclick = btnAdd_OnClick;

		var tableHtml = document.getElementById("tableList").outerHTML;
		var arrEntities = {};

		var btnSearch = document.getElementById("btnSearch");
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


				document.getElementById("tableList").insertAdjacentHTML('beforeend', html);
			}
		};
		var btnSearch_OnClick = function() {
			document.getElementById("tableList").outerHTML = tableHtml;

			var sName = txtDisplayName.value.trim();
			if(sName.length < 1)
				sName = null;

			inc.accounting.business.ListEntity.loadSearch(sName, btnSearch_Callback);
		};
		btnSearch.onclick = btnSearch_OnClick;
	};
};