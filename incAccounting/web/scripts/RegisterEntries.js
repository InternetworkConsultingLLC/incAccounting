function ignoreerror() { return true; }
window.onerror=ignoreerror();

function pageChanged() {
	var nBalance = new Number(document.getElementById("BeginningBalance").value);
	
	arrControls = document.getElementsByClassName("RowDebit");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		var sGuid = arrControls[cnt].id.replace("RowDebit", "");
		var nDebit = new Number(document.getElementById("RowDebit" + sGuid).value);
		var nBalance = nBalance + nDebit;
		
		document.getElementById("RowBalance" + sGuid).value = new Number(nBalance).toFixed(nSettingDocumentMoneyDecimals)
	}
	
	document.getElementById("EndingBalance").value = new Number(nBalance).toFixed(nSettingDocumentMoneyDecimals);
}

function pageLoaded() {
	var arrControls = document.getElementsByClassName("RowDebit");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = pageChanged;
	
	pageChanged()
}