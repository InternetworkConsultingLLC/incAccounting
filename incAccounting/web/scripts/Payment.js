function ignoreerror() { return true; }
window.onerror=ignoreerror();

function paymentChanged() {
	var nTotal = new Number(0);
	
	arrControls = document.getElementsByClassName("ApplicationAmount");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		var sGuid = arrControls[cnt].id.replace("ApplicationAmount", "");
		// going back and forth will round to number of decimals defined
		var nAmount = new Number(document.getElementById("ApplicationAmount" + sGuid).value);
		document.getElementById("ApplicationAmount" + sGuid).value = round(nAmount, nSettingDocumentMoneyDecimals);
		nAmount = new Number(document.getElementById("ApplicationAmount" + sGuid).value);
		nTotal = nAmount + nTotal;
	}
	
	document.getElementById("Total").value = round(nTotal, nSettingDocumentMoneyDecimals);		
}

function pageLoaded() {
	var arrControls = document.getElementsByClassName("ApplicationAmount");
	for(var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = paymentChanged;	
}