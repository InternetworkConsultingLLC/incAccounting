if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.ListList();
	};

	inc.accounting.html.ListList = function() {
		var entity = null;
		var arrFilters = [];
		
		var btnAdd_OnClick = function() { window.location.href = "ListEdit.html"; };
		var btnAdd = document.getElementById("btnAdd");
		btnAdd.onclick = btnAdd_OnClick;

		var btnSearch = document.getElementById("btnSearch");
		var btnSearch_Callback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}
		};
		var btnSearch_OnClick = function() {};
		btnSearch.onclick = btnSearch_OnClick;
		
		var loadFiltersCallback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}
			
			arrFilters = ret;
			
			for(var cnt in arrFilters) {
				var filter = arrFilters[cnt];

				var html = "";
				if(filter.getDataType() === "Text")
					html += "<label for=\"txt" + filter.getGuid() + "\">" + filter.getPrompt() + ":</label><input type=\"text\" id=\"txt" + filter.getGuid() + "\"/><br />";
					
				document.getElementById("filters").insertAdjacentHTML('beforeend', html);
			}
		};
		var loadEntityCallback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}

			entity = ret;
			entity.loadFilters(loadFiltersCallback);
		};
		
		var sDisplayName = inc.html.Dom.findGetParameter("DisplayName")[0];

		new inc.accounting.html.Menu(sDisplayName);
		document.getElementById("h1Title").innerHTML = sDisplayName;
		
		inc.accounting.business.ListEntity.loadByDisplayName(sDisplayName, loadEntityCallback);		
	};
};