if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("List Edit");
		new inc.accounting.html.ListEdit();
	};

	inc.accounting.html.ListEdit = function() {
		var entity = new inc.accounting.business.List();
		var sGuid = null;
		if(inc.html.Dom.findGetParameter("GUID").length === 1)
			sGuid = inc.html.Dom.findGetParameter("GUID")[0];

		var txtGUID = document.getElementById("txtGUID");
		var txtDisplayName = document.getElementById("txtDisplayName");
		var txtSqlQuery = document.getElementById("txtSqlQuery");
		
		var arrFilters = [];

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
		
		var btnAddFilter = document.getElementById("btnAddFilter");
		var btnAddFilter_Callback = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			arrFilters.push(ret);
			
			trTemplate = document.getElementsByClassName("template")[0];

			var html = trTemplate.outerHTML;
			html = html.replace(' class="template"', "");
			html = html.replace(/_GUID_/g, ret.getGuid());

			document.getElementById("filters").insertAdjacentHTML('beforeend', html);
		};
		var btnAddFilter_Clicked = function() {
			var filter = new inc.accounting.business.ListFilter();
			filter.initialize(loadEntityCallback);
		};
		btnAddFilter.onclick = btnAddFilter_Clicked;

		var loadControlsFromObject = function() {
			txtGUID.value = entity.getGuid();
			txtDisplayName.value = entity.getDisplayName();
			txtSqlQuery.value = entity.getSqlQuery();
		};

		var loadObjectFromControls = function() {
			entity.setDisplayName(txtDisplayName.value);
			entity.setSqlQuery(txtSqlQuery.value);
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
			inc.accounting.business.List.loadByGuid(sGuid, loadEntityCallback);
		else
			entity.initialize(loadEntityCallback);
	};
};

