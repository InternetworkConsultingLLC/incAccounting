function pageLoaded() {
	aceLoad("HtmlTemplate", "HtmlEditor", "html");
	aceLoad("Query", "QueryEditor", "mysql");
	
	arrControls = document.getElementsByClassName("FilterQuery");
	for(var cnt = 0; cnt < arrControls.length; cnt++) {
		var sGuid = arrControls[cnt].name.replace("Query", "");
		aceLoad(arrControls[cnt].id, "FilterEditor" + sGuid, "mysql");
	}

	document.getElementById("Save").addEventListener("click", saveEditors);
}

function saveEditors() {
	aceSave("HtmlTemplate", "HtmlEditor");
	aceSave("Query", "QueryEditor");

	arrControls = document.getElementsByClassName("FilterQuery");
	for(var cnt = 0; cnt < arrControls.length; cnt++) {
		var sGuid = arrControls[cnt].name.replace("Query", "");
		aceSave(arrControls[cnt].id, "FilterEditor" + sGuid);
	}
	
	return true;
}

function aceLoad(textareaId, editorId, mode) {
	var objTextarea = document.getElementById(textareaId);
	var objEditor = ace.edit(editorId);
	
	objEditor.setValue(objTextarea.value, -1);
	objEditor.session.setMode("ace/mode/" + mode);
	objEditor.setFontSize(16);
	objEditor.setReadOnly(false);
}
function aceSave(textareaId, editorId) {
	var objTextarea = document.getElementById(textareaId);
	var objEditor = ace.edit(editorId);

	objTextarea.value = objEditor.getValue();
}