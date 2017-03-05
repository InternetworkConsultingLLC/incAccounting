function pageLoaded() {
	aceLoad("SqlQuery", "SqlEditor", "mysql");

	document.getElementById("Save").addEventListener("click", saveEditors);
}

function saveEditors() {
	aceSave("SqlQuery", "SqlEditor");
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