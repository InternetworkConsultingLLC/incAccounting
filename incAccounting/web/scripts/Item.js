function ignoreerror() { return true; }
window.onerror=ignoreerror();

function calculatePrice() {
	var nCost = document.getElementById("LastUnitCost").value;
	var nMarkup = document.getElementById("SalesMarkUp").value;
	var nPrice = document.getElementById("SalesUnitPrice").value;

	if(!isFinite(nCost) || !isFinite(nMarkup))
		return;

	nCost = new Number(nCost).toFixed(nSettingDocumentQuantityDecimals);
	nMarkup = new Number(nMarkup).toFixed(nSettingDocumentRateDecimals);
	nPrice = new Number(nCost * nMarkup).toFixed(nSettingDocumentMoneyDecimals);
	
	document.getElementById("LastUnitCost").value = nCost;
	document.getElementById("SalesMarkUp").value = nMarkup;
	document.getElementById("SalesUnitPrice").value = nPrice;
}

function pageLoaded() {
	document.getElementById("LastUnitCost").onchange = calculatePrice;
	document.getElementById("SalesMarkUp").onchange = calculatePrice;
}