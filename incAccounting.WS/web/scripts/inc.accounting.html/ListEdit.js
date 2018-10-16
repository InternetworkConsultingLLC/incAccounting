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
		var entity = new inc.accounting.business.ListEntity();

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
			window.location = window.location.pathname + "?GUID=" + entity.getGuid();
		};
		var btnSave_Clicked = function() {
			loadObjectFromControls();
			entity.save(arrFilters, btnSave_Callback);
		};
		btnSave.onclick = btnSave_Clicked;
		
		var btnAddFilter = document.getElementById("btnAddFilter");
		var btnAddFilter_Callback = function(ret) {
			if(ret instanceof Error) {
				callback(ret);
				return;
			}
			
			addFilter(ret);
		};
		var btnAddFilter_Clicked = function() {
			var filter = new inc.accounting.business.ListFilterEntity();
			filter.initialize(btnAddFilter_Callback);
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
			
			for(var cnt in arrFilters) {
				var filter = arrFilters[cnt];
				filter.setPrompt(document.getElementById("txtFilterPrompt" + filter.getGuid()).value);
				filter.setQuery(document.getElementById("txtFilterQuery" + filter.getGuid()).value);
				
				var selFilterDataType = document.getElementById("selFilterDataType" + filter.getGuid());
				filter.setDataType(inc.html.getSelectionsValue(selFilterDataType));
				
				filter.setListsGuid(entity.getGuid());
			}			
		};

		var loadEntityCallback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}
			
			entity = ret;
			loadControlsFromObject();
			
			entity.loadFilters(loadFiltersCallback);
		};
		var loadFiltersCallback = function(ret) {
			if(ret instanceof Error) {
				alert(ret.toString());
				return;
			}

			for(var item in ret)
				addFilter(ret[item]);			
		};
		var addFilter = function(filter) {
			arrFilters.push(filter);
			
			trTemplate = document.getElementsByClassName("template")[0];

			var html = trTemplate.outerHTML;
			html = html.replace(' class="template"', ' class="filter"');
			html = html.replace(/_GUID_/g, filter.getGuid());

			document.getElementById("filters").insertAdjacentHTML('beforeend', html);
			
			document.getElementById("txtFilterGUID" + filter.getGuid()).value = filter.getGuid();			
			document.getElementById("txtFilterPrompt" + filter.getGuid()).value = filter.getPrompt();
			document.getElementById("txtFilterQuery" + filter.getGuid()).value = filter.getQuery();
			
			var selFilterDataType = document.getElementById("selFilterDataType" + filter.getGuid());
			inc.html.setSelectionsValue(selFilterDataType, filter.getDataType());
		};

		if(inc.html.Dom.findGetParameter("GUID").length === 1) {
			var sGuid = inc.html.Dom.findGetParameter("GUID")[0];
			inc.accounting.business.ListEntity.loadByGuid(sGuid, loadEntityCallback);
		} else
			entity.initialize(loadEntityCallback);
	};
};

